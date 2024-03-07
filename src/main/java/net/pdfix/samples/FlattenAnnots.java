////////////////////////////////////////////////////////////////////////////////////////////////////
// FlattenAnnots.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to flatten annotations in PDF.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;

public class FlattenAnnots {
    public static void run (
      String openPath,
      String savePath
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        for (int i = 0; i < doc.GetNumPages(); i++) {
            PdfPage page = doc.AcquirePage(i);
            if (page == null)
                throw new Exception(pdfix.GetError());
            
            for (int j = page.GetNumAnnots() - 1; j >= 0; j--) {
                PdfAnnot annot = page.GetAnnot(j);
                if (annot != null && annot.GetSubtype() != PdfAnnotSubtype.kAnnotLink) {
                    page.FlattenAnnot(annot);
                }
            }
        }
        
        if (!doc.Save(savePath, Pdfix.kSaveFull)) {
            throw new Exception(pdfix.GetError());
        }
        doc.Close();
        pdfix.Destroy();
    }
}
