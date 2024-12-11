////////////////////////////////////////////////////////////////////////////////////////////////////
// OpenFromStream.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to open/save PDF from/to stream.

package net.pdfix.samples;

import java.io.File;
import java.nio.file.Files;

import net.pdfix.pdfixlib.*;

public class OpenDocFromStream {
    public static void run (
      String openPath
    ) throws Exception {
        Pdfix pdfix = new Pdfix();

        byte[] bytes = Files.readAllBytes(new File(openPath).toPath());

        PsMemoryStream openStm = pdfix.CreateMemStream();
        openStm.Write(0, bytes);

        PdfDoc doc = pdfix.OpenDocFromStream(openStm, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        PsMemoryStream saveStm = pdfix.CreateMemStream();
        doc.SaveToStream(saveStm, Pdfix.kSaveFull);

        byte[] saveBytes = new byte[saveStm.GetSize()];
        saveStm.Read(0, saveBytes);

        openStm.Destroy();
        saveStm.Destroy();

        doc.Close();
        pdfix.Destroy();
    }
}