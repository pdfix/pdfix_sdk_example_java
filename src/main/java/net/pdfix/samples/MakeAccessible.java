////////////////////////////////////////////////////////////////////////////////////////////////////
// MakeAccessible.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to make PDF document accessible.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;

public class MakeAccessible {
    public static void run(
            String openPath,
            String savePath,
            String commandPath) throws Exception {
        Pdfix pdfix = new Pdfix();
        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());

        PsCommand command = doc.GetCommand();

        PsStream cmdStm = null;

        if (commandPath.isEmpty()) {
            cmdStm = pdfix.CreateMemStream();
            if ((cmdStm == null) || !command.SaveCommandsToStream(PsCommandType.kCommandMakeAccessible, cmdStm, PsDataFormat.kDataFormatJson,
                                                        Pdfix.kSaveFull))
            {
                throw new Exception(pdfix.GetError());
            }
        } else {
            cmdStm = pdfix.CreateFileStream(commandPath, PsFileMode.kPsReadOnly);
        }
        if (cmdStm == null) {
            throw new Exception(pdfix.GetError());
        }
        
        if (!command.LoadParamsFromStream(cmdStm, PsDataFormat.kDataFormatJson)) {
            throw new Exception(pdfix.GetError());
        }
        cmdStm.Destroy();

        if (!command.Run()) {
            throw new Exception(pdfix.GetError());
        }

        if (!doc.Save(savePath, Pdfix.kSaveFull))
            throw new Exception(pdfix.GetError());

        doc.Close();
        pdfix.Destroy();
    }
}
