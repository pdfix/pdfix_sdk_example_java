////////////////////////////////////////////////////////////////////////////////////////////////////
// EmbedFonts.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*! 
\page JAVA_Samples Java Samples
- \subpage EmbedFonts_java
*/
/*! 
\page EmbedFonts_java Embed Fonts Sample
// Example how to embed fonts in PDF document.
\snippet /EmbedFonts.java EmbedFonts_java
*/

//! [EmbedFonts_java]
package net.pdfix.samples;

import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class EmbedFonts {
    public static void run (
      String email,               
      String licenseKey,
      String openPath,
      String savePath
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
        
        if (!doc.EmbedFonts(true)) {
            throw new Exception(pdfix.GetError());
        }
        if (!doc.Save(savePath, PdfSaveFlags.kSaveFull)) {
            throw new Exception(pdfix.GetError());
        }
        doc.Close();
        pdfix.Destroy();
    }
}
//! [EmbedFonts_java]
