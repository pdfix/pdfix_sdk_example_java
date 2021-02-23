////////////////////////////////////////////////////////////////////////////////////////////////////
// DocumentMetadata.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to access PDF document metadata.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

public class DocumentMetadata {
    public static void run (
      String openPath,
      String savePath
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        if (isNull(pdfix))
            throw new Exception("Pdfix initialization fail");

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        String title = doc.GetInfo("Title");
        doc.GetInfo("Title");
        doc.SetInfo("Title", "My next presenttion");
        
        PsMetadata metadata = doc.GetMetadata();
        if (metadata == null) {
            throw new Exception(pdfix.GetError());
        }
        
        if (!doc.Save(savePath, Pdfix.kSaveFull)) {
            throw new Exception(pdfix.GetError());
        }
        doc.Close();
        pdfix.Destroy();
    }
}