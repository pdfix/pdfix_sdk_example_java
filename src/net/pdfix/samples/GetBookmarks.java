////////////////////////////////////////////////////////////////////////////////////////////////////
// GetBookmarks.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*! 
\page JAVA_Samples Java Samples
- \subpage GetBookmarks_java
*/
/*! 
\page GetBookmarks_java Get Bookmarks Sample
// Example how to access bookmarks in PDF.
\snippet /GetBookmarks.java GetBookmarks_java
*/

//! [GetBookmarks_java]
package net.pdfix.samples;

import static java.lang.System.out;
import net.pdfix.Utils;
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
      String email,               
      String licenseKey,
      String openPath
    ) throws Exception {
        System.out.println("GetBookmarks");
        
        System.load(Utils.getAbsolutePath(Utils.getModuleName("pdfix")));

        Pdfix pdfix = new Pdfix();
        if (pdfix == null)
            throw new Exception("Pdfix initialization fail");
        if (!pdfix.Authorize(email, licenseKey))
            throw new Exception(pdfix.GetError());

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        PdfBookmark parent = doc.GetBookmarkRoot();
        if (parent == null) 
            throw new Exception(pdfix.GetError());
        ProcessBookmark(parent, " ");
    }
}
//! [GetBookmarks_java]