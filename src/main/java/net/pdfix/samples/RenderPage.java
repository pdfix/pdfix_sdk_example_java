////////////////////////////////////////////////////////////////////////////////////////////////////
// RenderPage.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to render a PDF document page into an image.
package net.pdfix.samples;

import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

public class RenderPage {
    public static void run (
      String openPath,
      String imgPath,
      Double zoom
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        if (isNull(pdfix))
            throw new Exception("Pdfix initialization fail");
        
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
        
        params.render_flags = Pdfix.kRenderAnnot;
        if (!page.DrawContent(params))
            throw new Exception(pdfix.GetError());
        
        PsStream stream = pdfix.CreateFileStream(imgPath, PsFileMode.kPsWrite);
        if (stream == null)
            throw new Exception(pdfix.GetError());

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
