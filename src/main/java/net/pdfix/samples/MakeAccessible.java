////////////////////////////////////////////////////////////////////////////////////////////////////
// MakeAccessible.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to make PDF document accessible.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;

public class MakeAccessible {
    public static void run (
      String openPath,
      String savePath,
      String commandPath
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());

        PsCommand cmd = doc.GetCommand();

        // customize auto-tagging
        PsFileStream stm = pdfix.CreateFileStream(commandPath, PsFileMode.kPsReadOnly);
        if (stm == null) {
            throw new Exception(pdfix.GetError());
        }
        if (!cmd.LoadParamsFromStream(stm, PsDataFormat.kDataFormatJson)) {
            throw new Exception(pdfix.GetError());
        }
        stm.Destroy();

        if (!cmd.Run()) {
            throw new Exception(pdfix.GetError());
        }
        
        if (!doc.Save(savePath, Pdfix.kSaveFull))
            throw new Exception(pdfix.GetError());
        
        doc.Close();
        pdfix.Destroy();
    }
}
