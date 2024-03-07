////////////////////////////////////////////////////////////////////////////////////////////////////
// AddTags.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to automatically add tags into the document.
package net.pdfix.samples;

import net.pdfix.pdfixlib.*;

public class AddTags {
    public static void run(
      String openPath,                          // source PDF document
      String savePath,                          // output HTML file
      String configPath                         // configuration file
    ) throws Exception {        
        Pdfix pdfix = new Pdfix();
        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());

        // customize auto-tagging by loading a template configuration JSON
        PsFileStream stm = pdfix.CreateFileStream(configPath, PsFileMode.kPsReadOnly);
        if (stm != null) {
            PdfDocTemplate doc_preflight = doc.GetTemplate();
            if (doc_preflight == null)
                throw new Exception(pdfix.GetError());
            if (!doc_preflight.LoadFromStream(stm, PsDataFormat.kDataFormatJson))
                throw new Exception(pdfix.GetError());
            stm.Destroy();
        }
        else {
            // customize auto-tagging by running preflight
            PdfDocTemplate doc_preflight = doc.GetTemplate();
            for (int i = 0; i < doc.GetNumPages(); i++) {
                if (!doc_preflight.AddPage(i))
                    throw new Exception(pdfix.GetError());
            }
            if (!doc_preflight.Update())
                throw new Exception(pdfix.GetError());
        }

        if (!doc.AddTags(new PdfTagsParams()))
            throw new Exception(pdfix.GetError());

        if (!doc.Save(savePath, Pdfix.kSaveFull | Pdfix.kSaveCompressedStructureOnly))
            throw new Exception(pdfix.GetError());

        doc.Close();
        pdfix.Destroy();
    }  
}
