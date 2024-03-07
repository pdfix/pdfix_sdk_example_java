////////////////////////////////////////////////////////////////////////////////////////////////////
// GetBookmarks.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to access bookmarks in PDF.

package net.pdfix.samples;

import static java.lang.System.out;
import net.pdfix.pdfixlib.*;

public class GetBookmarks {
    private static void ProcessBookmark(PdfBookmark bmk, String indent){
        if (bmk.GetParent() != null) {
            String title;
            title = bmk.GetTitle();
            out.println(indent + title);
        }
        indent += "  ";
        int num = bmk.GetNumChildren();
        if (num > 0) 
            for (int i = 0; i < num; i = i + 1) {
                PdfBookmark child = bmk.GetChild(i);
                ProcessBookmark(child, indent);
            }
    }
    public static void run(
      String openPath
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());

        PdfBookmark parent = doc.GetBookmarkRoot();
        if (parent == null) 
            throw new Exception(pdfix.GetError());

        ProcessBookmark(parent, " ");
    }
}
