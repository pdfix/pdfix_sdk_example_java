////////////////////////////////////////////////////////////////////////////////////////////////////
// ExtractImages.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to extract images from PDF.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

public class ExtractImages {
    public static void SaveImage(      
      PdeElement element, 
      String savePath, 
      PdfImageParams imgParams,
      PdfPage page, 
      PdfPageView pageView
    ) throws Exception {
        Pdfix pdfix = new Pdfix();

        PdfElementType elemType = element.GetType();

        if (elemType == PdfElementType.kPdeImage) {
            PdeImage image = (PdeImage)element;

            PdfRect elemRect = element.GetBBox();
            PdfDevRect elemDevRect = pageView.RectToDevice(elemRect);
            int elem_width = elemDevRect.right - elemDevRect.left;
            int elem_height = elemDevRect.bottom - elemDevRect.top;
            if (elem_height == 0 || elem_width == 0)
                return;

            PsImage psImage = pdfix.CreateImage(pageView.GetDeviceWidth(),
                pageView.GetDeviceHeight(), PsImageDIBFormat.kImageDIBFormatArgb);
            if (psImage == null)
                throw new Exception(pdfix.GetError());
            PdfPageRenderParams renderParams = new PdfPageRenderParams();
            renderParams.image = psImage;
            renderParams.matrix = pageView.GetDeviceMatrix();
            page.DrawContent(renderParams);

            psImage.SaveRect(savePath, imgParams, elemDevRect);
            psImage.Destroy();
        }
        int count = element.GetNumChildren();
        if (count == 0)
            return;
        for (int i = 0; i < count; i++) {
            PdeElement child = element.GetChild(i);
            if (child != null)
                SaveImage(child, savePath, imgParams, page, pageView);
        }       
    }  
    
    // Extracts all images from the document and saves them to save_path.
    public static void run(
      String openPath,                      // source PDF document
      String savePath,                      // directory where to extract images
      int renderWidth,                      // with of the rendered page in pixels (image )
      PdfImageParams imgParams              // image parameters
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        if (isNull(pdfix))
            throw new Exception("Pdfix initialization fail");

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());

        imgParams.format = PdfImageFormat.kImageFormatPng;

        int numPages = doc.GetNumPages();

        for (int i = 0; i < numPages; i++) {
            PdfPage page = doc.AcquirePage(i);
            if (page == null)
              throw new Exception(pdfix.GetError());

            PdfRect cropBox = page.GetCropBox();
            float pageWidth = (cropBox.right - cropBox.left);
            float zoom = renderWidth / pageWidth;
            PdfPageView pageView = page.AcquirePageView(zoom, 
              PdfRotate.kRotate0);
            if (pageView == null)
               throw new Exception(pdfix.GetError());

            PdePageMap pageMap = page.AcquirePageMap();
            if (pageMap == null)
                throw new Exception(pdfix.GetError());

            PdeElement element = pageMap.GetElement();
            if (element == null)
                throw new Exception(pdfix.GetError());
            SaveImage(element, savePath, imgParams, page, pageView);

            page.Release();
        }
        
        doc.Close();
        pdfix.Destroy();
    }
}
