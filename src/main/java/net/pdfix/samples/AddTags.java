////////////////////////////////////////////////////////////////////////////////////////////////////
// AddTags.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to automatically add tags into the document.
package net.pdfix.samples;

import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

public class AddTags {
    public static void run(
      String openPath,                          // source PDF document
      String savePath,                          // output HTML file
      String configPath                         // configuration file
    ) throws Exception {        
        Pdfix pdfix = new Pdfix();
        if (isNull(pdfix))
            throw new Exception("Pdfix initialization fail");

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());

        // customize auto-tagging 
        PsFileStream stm = pdfix.CreateFileStream(configPath, PsFileMode.kPsReadOnly);
        if (stm != null) {
            PdfDocTemplate doc_preflight = doc.GetTemplate();
            if (doc_preflight == null)
                throw new Exception(pdfix.GetError());
            if (!doc_preflight.LoadFromStream(stm, PsDataFormat.kDataFormatJson))
                throw new Exception(pdfix.GetError());
            stm.Destroy();
        }

        if (!doc.AddTags(new PdfTagsParams()))
            throw new Exception(pdfix.GetError());

        if (!doc.Save(savePath, Pdfix.kSaveFull | Pdfix.kSaveCompressedStructureOnly))
            throw new Exception(pdfix.GetError());

        doc.Close();
        pdfix.Destroy();
    }  
}
