////////////////////////////////////////////////////////////////////////////////////////////////////
// ExtractImages.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*! 
\page JAVA_Samples Java Samples
- \subpage ExtractImages_java
*/
/*! 
\page ExtractImages_java Export Extract Images Sample
// Example how to extract images from PDF.
\snippet /ExtractImages.java ExtractImages_java
*/

//! [ExtractImages_java]
package net.pdfix.samples;

import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class ExtractImages {
    private static int imageIndex = 0;
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

            image.SetRender(true);
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
            image.SetRender(true);
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
      String email,                         // authorization email   
      String licenseKey,                    // authorization license key
      String openPath,                      // source PDF document
      String savePath,                      // directory where to extract images
      int renderWidth,                      // with of the rendered page in pixels (image )
      PdfImageParams imgParams              // image parameters
    ) throws Exception {
        System.load(Utils.getAbsolutePath(Utils.getModuleName("pdfix")));

        Pdfix pdfix = new Pdfix();
        if (pdfix == null)
            throw new Exception("Pdfix initialization fail");
        if (!pdfix.Authorize(email, licenseKey))
            throw new Exception(pdfix.GetError());

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
            double pageWidth = (cropBox.right - cropBox.left);
            double zoom = renderWidth / pageWidth;
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
//! [ExtractImages_java]