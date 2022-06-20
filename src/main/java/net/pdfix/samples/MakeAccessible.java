////////////////////////////////////////////////////////////////////////////////////////////////////
// MakeAccessible.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to make PDF document accessible.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

public class MakeAccessible {
    public static void run (
      String openPath,
      String savePath,
      String language,
      String title,
      String configPath
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        if (isNull(pdfix))
            throw new Exception("Pdfix initialization fail");

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        // customize auto-tagging
        if (!configPath.isEmpty()) {
            PsFileStream stm = pdfix.CreateFileStream(configPath, PsFileMode.kPsReadOnly);
            if (stm != null) {
                PdfDocTemplate doc_preflight = doc.GetTemplate();
                if (doc_preflight == null)
                    throw new Exception(pdfix.GetError());
                if (!doc_preflight.LoadFromStream(stm, PsDataFormat.kDataFormatJson)) 
                    throw new Exception(pdfix.GetError());
                stm.Destroy();
            }
        }
        // convert to PDF/UA
        if (!doc.MakeAccessible(new PdfAccessibleParams(), title, language))
           throw new Exception(pdfix.GetError());
        
        if (!doc.Save(savePath, Pdfix.kSaveFull))
            throw new Exception(pdfix.GetError());
        
        doc.Close();
        pdfix.Destroy();
    }
}
