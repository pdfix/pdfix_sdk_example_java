////////////////////////////////////////////////////////////////////////////////////////////////////
// RemoveComments.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*!
\page JAVA_Samples Java Samples
- \subpage RemoveComments_java
*/
/*!
\page RemoveComments_java Remove Comments Sample
Example how to remove from a first highlight annot with it's popup and all replies.
\snippet /RemoveComments.java RemoveComments_java
*/

//! [RemoveComments_java]
package net.pdfix.samples;

import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class RemoveComments {
    public static void run (
      String email,               
      String licenseKey,
      String openPath,
      String savePath
    ) throws Exception {
        System.out.println("RemoveComments");
        
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
        
        for (int i = 0; i < page.GetNumAnnots(); i = i + 1) {
            PdfAnnot annot = page.GetAnnot(i);
            if (annot.GetSubtype() == PdfAnnotSubtype.kAnnotHighlight){
                page.RemoveAnnot(i,
                        pdfix.kRemoveAnnotPopup | pdfix.kRemoveAnnotReply);
                break;
            }
        }
        doc.ReleasePage(page);
        doc.Save(savePath, PdfSaveFlags.kSaveFull);
        doc.Close();
        pdfix.Destroy();        
    }
}
//! [RemoveComments_java]