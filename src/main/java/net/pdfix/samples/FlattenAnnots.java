////////////////////////////////////////////////////////////////////////////////////////////////////
// FlattenAnnots.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to flatten annotations in PDF.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

public class FlattenAnnots {
    public static void run (
      String openPath,
      String savePath,
      PdfFlattenAnnotsParams params
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        if (isNull(pdfix))
            throw new Exception("Pdfix initialization fail");

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        if (!doc.FlattenAnnots(params)) {
            throw new Exception(pdfix.GetError());
        }
        if (!doc.Save(savePath, Pdfix.kSaveFull)) {
            throw new Exception(pdfix.GetError());
        }
        doc.Close();
        pdfix.Destroy();
    }
}
