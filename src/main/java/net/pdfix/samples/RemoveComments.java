////////////////////////////////////////////////////////////////////////////////////////////////////
// RemoveComments.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to remove from a first highlight annot with it's popup and all replies.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;

public class RemoveComments {
    public static void run (
      String openPath,
      String savePath
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        PdfPage page = doc.AcquirePage(0);
        if (page == null)
            throw new Exception(pdfix.GetError());
        
        for (int i = 0; i < page.GetNumAnnots(); i = i + 1) {
            PdfAnnot annot = page.GetAnnot(i);
            if (annot.GetSubtype() == PdfAnnotSubtype.kAnnotHighlight){
                page.RemoveAnnot(i, Pdfix.kRemoveAnnotPopup | Pdfix.kRemoveAnnotReply);
                break;
            }
        }
        
        page.Release();
        doc.Save(savePath, Pdfix.kSaveFull);
        doc.Close();
        pdfix.Destroy();        
    }
}
