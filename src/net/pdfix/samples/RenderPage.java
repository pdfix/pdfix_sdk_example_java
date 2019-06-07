////////////////////////////////////////////////////////////////////////////////////////////////////
// RenderPage.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*!
\page JAVA_Samples Java Samples
- \subpage RenderPage_java
*/
/*!
\page RenderPage_java Render Page Sample
Example how to render a PDF document page into an image.
\snippet /RenderPage.java RenderPage_java
*/

//! [RenderPage_java]
package net.pdfix.samples;

import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class RenderPage {
    public static void run (
      String email,               
      String licenseKey,
      String openPath,
      String imgPath,
      Double zoom
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
        
        PdfPage page = doc.AcquirePage(0);
        if (page == null)
            throw new Exception(pdfix.GetError());
        
        PdfPageView pageView = page.AcquirePageView(zoom, PdfRotate.kRotate0);
        if (pageView == null)
            throw new Exception(pdfix.GetError());
        
        int width = pageView.GetDeviceWidth();
        int height = pageView.GetDeviceHeight();
        
        PsImage image = pdfix.CreateImage(width, height,
                PsImageDIBFormat.kImageDIBFormatArgb);
        if (image == null)
            throw new Exception(pdfix.GetError());
        
        PdfPageRenderParams params = new PdfPageRenderParams();
        params.image = image;
        params.matrix = pageView.GetDeviceMatrix();
        
        params.render_flags = pdfix.kRenderAnnot;
        if (!page.DrawContent(params))
            throw new Exception(pdfix.GetError());
        
        PsStream stream = pdfix.CreateFileStream(imgPath, PsFileMode.kPsWrite);
        PdfImageParams imgParams = new PdfImageParams();
        imgParams.format = PdfImageFormat.kImageFormatJpg;
        imgParams.quality = 75;
        if (!image.SaveToStream(stream, imgParams))
            throw new Exception(pdfix.GetError());
        stream.Destroy();
        

        pageView.Release();
        page.Release();
        doc.Close();
    }
}
//! [RenderPage_java]