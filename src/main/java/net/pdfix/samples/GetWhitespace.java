////////////////////////////////////////////////////////////////////////////////////////////////////
// GetWhitespace.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to get whitespace areas on PDF page.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;

public class GetWhitespace {
    public static void run (
      String openPath
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        PdfPage page = doc.AcquirePage(0);
        if (page == null)
            throw new Exception(pdfix.GetError());
        
        PdePageMap pageMap = page.AcquirePageMap();
        if (pageMap == null)
            throw new Exception(pdfix.GetError());
        
        PdfWhitespaceParams whitespaceParams = new PdfWhitespaceParams();
        // set watermark width in user space coordinates
        whitespaceParams.width = 100;
        // set watermark height in user space coordinates
        whitespaceParams.height = 50;
        // PdfRect bbox = pageMap.GetWhitespace(whitespaceParams, 0);
        
        // use the bbox to place watermark into it - AddWatermark example
        // ...
        
        page.Release();
        doc.Close();
        pdfix.Destroy();
    }
}