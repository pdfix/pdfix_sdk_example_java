////////////////////////////////////////////////////////////////////////////////////////////////////
// MakeAccessible.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

package net.pdfix.samples;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.pdfix.pdfixlib.*;

public class MakeAccessible {

    public static void run(
            String openPath,
            String savePath,
            String commandPath) throws Exception {
        run(openPath, savePath, commandPath, "", "");
    }

    public static void run(
            String openPath,
            String savePath,
            String commandPath,
            String language,
            String title) throws Exception {

        Pdfix pdfix = new Pdfix();
        PdfDoc doc = null;
        PsStream cmdStm = null;

        try {
            doc = pdfix.OpenDoc(openPath, "");
            if (doc == null) {
                throw new Exception(pdfix.GetError());
            }

            PsCommand command = doc.GetCommand();
            if (command == null) {
                throw new Exception(pdfix.GetError());
            }

            // If commandPath is empty, find the embedded custom action with name
            // "make_accessible".
            // Otherwise load JSON from the provided file.
            if (commandPath == null || commandPath.isEmpty()) {
                int cmdCount = command.GetNumCustomActions();

                for (int i = 0; i < cmdCount; i++) {
                    PsStream tmpStm = pdfix.CreateMemStream();
                    if (tmpStm == null) {
                        throw new Exception(pdfix.GetError());
                    }

                    try {
                        if (!command.SaveCustomActionToStream(
                                i,
                                tmpStm,
                                PsDataFormat.kDataFormatJson,
                                Pdfix.kSaveFull)) {
                            throw new Exception(pdfix.GetError());
                        }

                        String json = readStreamAsString(tmpStm);
                        String name = extractJsonName(json);

                        if ("make_accessible".equals(name)) {
                            cmdStm = tmpStm;
                            tmpStm = null; // ownership transferred
                            break;
                        }
                    } catch (Exception ex) {
                        System.out.println("[WARNING]: Loading JSON command failed. [" + i + "] " + ex.getMessage());
                    } finally {
                        if (tmpStm != null) {
                            tmpStm.Destroy();
                        }
                    }
                }

                if (cmdStm == null) {
                    throw new Exception("Embedded custom action 'make_accessible' was not found.");
                }
            } else {
                cmdStm = pdfix.CreateFileStream(commandPath, PsFileMode.kPsReadOnly);
                if (cmdStm == null) {
                    throw new Exception(pdfix.GetError());
                }
            }

            if (!command.LoadParamsFromStream(cmdStm, PsDataFormat.kDataFormatJson)) {
                throw new Exception(pdfix.GetError());
            }

            if (!command.Run()) {
                throw new Exception(pdfix.GetError());
            }

            if (language != null && !language.isEmpty()) {
                if (!doc.SetLang(language)) {
                    throw new Exception(pdfix.GetError());
                }
            }

            if (title != null && !title.isEmpty()) {
                if (!doc.SetInfo("Title", title)) {
                    throw new Exception(pdfix.GetError());
                }
            }

            if (!doc.Save(savePath, Pdfix.kSaveFull)) {
                throw new Exception(pdfix.GetError());
            }

        } finally {
            if (cmdStm != null) {
                cmdStm.Destroy();
            }
            if (doc != null) {
                doc.Close();
            }
            pdfix.Destroy();
        }
    }

    private static String readStreamAsString(PsStream stm) throws Exception {
        long size = stm.GetSize();
        if (size < 0 || size > Integer.MAX_VALUE) {
            throw new Exception("Invalid stream size: " + size);
        }

        byte[] buffer = new byte[(int) size];
        if (size > 0) {
            if (!stm.Read(0, buffer)) {
                throw new Exception("Failed to read stream.");
            }
        }

        return new String(buffer, StandardCharsets.UTF_8);
    }

    private static String extractJsonName(String json) {
        if (json == null) {
            return null;
        }

        Pattern pattern = Pattern.compile("\"name\"\\s*:\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}