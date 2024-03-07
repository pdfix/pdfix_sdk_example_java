////////////////////////////////////////////////////////////////////////////////////////////////////
// TagEditStructTree.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to make hchanges in the document structure tree

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;

public class TagsEditStructTree {

    public static void run(
            String openPath,
            String savePath
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null) {
            throw new Exception(pdfix.GetError());
        }

        if (!doc.RemoveTags()) {
            throw new Exception(pdfix.GetError());
        }

        if (!doc.AddTags(new PdfTagsParams())) {
            throw new Exception(pdfix.GetError());
        }

        if (!doc.Save(savePath, Pdfix.kSaveFull)) {
            throw new Exception(pdfix.GetError());
        }

        doc.Close();
    }
}
