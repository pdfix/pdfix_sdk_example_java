////////////////////////////////////////////////////////////////////////////////////////////////////
// OcrWithTesseract.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to convert an image based PDF to searchable document.
package net.pdfix.samples;

import java.util.ArrayList;

import net.pdfix.ocrtesseract.*;
import net.pdfix.pdfixlib.*;

public class OcrWithTesseract {
    private static void parse_page_element(PdeElement elem, ArrayList<PdfRect> image_bbox_arr) {
        if (elem == null)
            return;
        if (elem.GetType() == PdfElementType.kPdeImage) {
            PdfRect bbox = elem.GetBBox();
            image_bbox_arr.add(bbox);
        } else {
            for (int i = 0; i < elem.GetNumChildren(); i++) {
                PdeElement child = elem.GetChild(i);
                parse_page_element(child, image_bbox_arr);
            }
        }
    }

    public static void run(
            String openPath, // source PDF document
            String savePath, // output PDF document
            String dataPath, // path to OCR data
            String language, // default OCR language
            float zoom, // zoom to control page rendering quality
            PdfRotate rotate // page rotation to be applied
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        OcrTesseract ocr = new OcrTesseract();
        if (!ocr.Initialize(pdfix))
            throw new Exception(pdfix.GetError());

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());

        ArrayList<PdfRect> image_bbox_arr = new ArrayList<PdfRect>(); // array to collect image bounding boxes on page
        PdfPage page = doc.AcquirePage(0);
        if (page == null)
            throw new Exception(pdfix.GetError());

        // find images on the page and collect bounding boxes to ocr
        PdePageMap page_map = page.AcquirePageMap();
        if (page_map == null)
            throw new Exception(pdfix.GetError());
        if (!page_map.CreateElements())
            throw new Exception(pdfix.GetError());

        PdeElement elem = page_map.GetElement();
        parse_page_element(elem, image_bbox_arr);
        page_map.Release();

        // set tesseract ocr resources
        ocr.SetLanguage(language);
        ocr.SetDataPath(dataPath);

        // process tessteract document
        TesseractDoc ocrDoc = ocr.OpenOcrDoc(doc);
        if (ocrDoc == null)
            throw new Exception(pdfix.GetError());

        // prepare page rendering and matrix
        PdfPageView page_view = page.AcquirePageView(zoom, rotate);

        // run ocr on each image bbox
        for (int j = image_bbox_arr.size() - 1; j >= 0; j--) {
            PdfRect bbox = image_bbox_arr.get(j);
            // render page to image
            PdfDevRect dev_rect = page_view.RectToDevice(bbox);
            int width = dev_rect.right - dev_rect.left;
            int height = dev_rect.bottom - dev_rect.top;

            PsImage image = pdfix.CreateImage(width, height, PsImageDIBFormat.kImageDIBFormatArgb);

            // render portion of the page - the image
            PdfPageRenderParams render_params = new PdfPageRenderParams();
            render_params.matrix = page_view.GetDeviceMatrix();
            render_params.image = image;
            render_params.clip_box = bbox;
            if (!page.DrawContent(render_params))
                throw new Exception(pdfix.GetError());

            // calculate PdfMatrix to position the recognized text on the page
            PdfMatrix matrix = new PdfMatrix();
            matrix = Utils.PdfMatrixRotate(matrix, rotate.getValue() * Utils.kPi / 2, false);
            Utils.PdfMatrixScale(matrix, 1 / zoom, 1 / zoom, false);
            switch (rotate) {
                case kRotate0:
                    matrix = Utils.PdfMatrixTranslate(matrix, bbox.left, bbox.bottom, false);
                    break;
                case kRotate90:
                    matrix = Utils.PdfMatrixTranslate(matrix, bbox.right, bbox.bottom, false);
                    break;
                case kRotate180:
                    matrix = Utils.PdfMatrixTranslate(matrix, bbox.right, bbox.top, false);
                    break;
                case kRotate270:
                    matrix = Utils.PdfMatrixTranslate(matrix, bbox.left, bbox.top, false);
                    break;
            }

            if (!ocrDoc.OcrImageToPage(image, matrix, page))
                throw new Exception(pdfix.GetError());

            image.Destroy();
        }

        page.Release();

        if (!doc.Save(savePath,  Pdfix.kSaveFull))
            throw new Exception(pdfix.GetError());

        ocrDoc.Close();
        doc.Close();
        pdfix.Destroy();
    }
}
