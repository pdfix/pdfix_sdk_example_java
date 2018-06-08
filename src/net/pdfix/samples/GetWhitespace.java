////////////////////////////////////////////////////////////////////////////////////////////////////
// GetWhitespace.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*! 
\page JAVA_Samples Java Samples
- \subpage GetWhitespace_java
*/
/*! 
\page GetWhitespace_java Get Page Whitespace Sample
// Example how to get whitespace areas on PDF page.
\snippet /GetWhitespace.java GetWhitespace_java
*/

//! [GetWhitespace_java]
package net.pdfix.samples;

import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class GetWhitespace {
    public static void run (
      String email,               
      String licenseKey,
      String openPath
    ) throws Exception {
        System.out.println("GetWhitespace");
        
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
        
        PdePageMap pageMap = page.AcquirePageMap();
        if (pageMap == null)
            throw new Exception(pdfix.GetError());
        
        PdfWhitespaceParams whitespaceParams = new PdfWhitespaceParams();
        // set watermark width in user space coordinates
        whitespaceParams.width = 100;
        // set watermark height in user space coordinates
        whitespaceParams.height = 50;
        PdfRect bbox = pageMap.GetWhitespace(whitespaceParams, 0);
        
            // use the bbox to place watermark into it - AddWatermark example
            // ...
        
        doc.ReleasePage(page);
        doc.Close();
        pdfix.Destroy();
    }
}
//! [GetWhitespace_java]