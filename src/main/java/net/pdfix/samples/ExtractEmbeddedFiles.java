////////////////////////////////////////////////////////////////////////////////////////////////////
// ExtractEmbeddedFiles.java
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to extract embedded files from a PDF document.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;

public class ExtractEmbeddedFiles {

    /**
     * Writes a PDF stream's raw bytes to a file on disk.
     *
     * @param stmObj   the PDFix PdsStream to read bytes from
     * @param savePath absolute or relative file path to create/overwrite
     * @throws Exception if the file stream cannot be created or writing fails
     */
    private static void saveStreamToFile(PdsStream stmObj, String savePath) throws Exception {
        Pdfix pdfix = new Pdfix();
        PsStream fileStm = pdfix.CreateFileStream(savePath, PsFileMode.kPsTruncate);
        byte[] buffer = new byte[stmObj.GetSize()];
        boolean ok = stmObj.Read(0, buffer);
        fileStm.Write(0, buffer);
        fileStm.Destroy();
    }

    /**
     * Extracts a single embedded file referenced by a File Specification dictionary
     * and saves it to {@code savePath}/{@code UF}.
     * @param dict     a Filespec dictionary that may reference an embedded file
     * @param savePath output directory to write the extracted file into
     * @throws Exception if reading the stream or writing to disk fails
     */
    private static void saveEmbeddedFile(PdsDictionary dict, String savePath) throws Exception {
        if (dict.GetText("Type").compareTo("Filespec") != 0) {
            System.out.println(dict.GetText("Type"));
            return; // not an embedded file
        }

        String fileName = dict.GetText("UF"); // embedded file name
        PdsDictionary efDict = dict.GetDictionary("EF");
        if (efDict != null) {
            PdsStream fStm = efDict.GetStream("F");
            saveStreamToFile(fStm, savePath + "/" + fileName);
        }
    }

    /**
     * Opens a PDF document, enumerates the "EmbeddedFiles" name tree (single level),
     * and saves each referenced embedded file into the provided directory.
     * @param openPath path to the source PDF document
     * @param savePath directory where extracted files will be written
     * @throws Exception if the document cannot be opened or I/O fails
     */    
    public static void run(
            String openPath, // source PDF document
            String savePath // directory where to extract files
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());

        // Name tree enumeration methods are not supported in Jave, must iterate all
        // NameTree items
        // manually. This example does not support multi-level name trees
        PdfNameTree nameTree = doc.GetNameTree("EmbeddedFiles", false);
        if (nameTree != null) {
            PdsDictionary nameTreeObj = (PdsDictionary) nameTree.GetObject();
            PdsArray namesArr = nameTreeObj.GetArray("Names");
            if (namesArr != null) {
                for (int i = 0; i + 1 < namesArr.GetNumObjects(); i += 2) {
                    PdsDictionary embeddedObj = namesArr.GetDictionary(i + 1);
                    if (embeddedObj != null) {
                        saveEmbeddedFile(embeddedObj, savePath);
                    }
                }
            }
        }

        // cleanup
        doc.Close();
        pdfix.Destroy();
    }
}