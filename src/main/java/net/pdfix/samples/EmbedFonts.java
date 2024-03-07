////////////////////////////////////////////////////////////////////////////////////////////////////
// EmbedFonts.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to embed fonts in PDF document.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;

public class EmbedFonts {
    public static void run (
      String openPath,
      String savePath
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        if (!doc.EmbedFonts(true)) {
            throw new Exception(pdfix.GetError());
        }
        if (!doc.Save(savePath, Pdfix.kSaveFull)) {
            throw new Exception(pdfix.GetError());
        }
        doc.Close();
        pdfix.Destroy();
    }
}
