////////////////////////////////////////////////////////////////////////////////////////////////////
// RenderPage.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*!
\page JAVA_Samples Java Samples
- \subpage RenderPage_java
 */
 /*!
\page RenderPage_java Render Page Sample
Example how to render a PDF document page into an image.
\snippet /RenderPage.java RenderPage_java
 */
//! [RenderPage_java]
package net.pdfix.samples;

import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class TagsEditStructTree {

    public static void run(
            String email,
            String licenseKey,
            String openPath,
            String savePath
    ) throws Exception {
        System.load(Utils.getAbsolutePath(Utils.getModuleName("pdfix")));

        Pdfix pdfix = new Pdfix();
        if (pdfix == null) {
            throw new Exception("Pdfix initialization fail");
        }
        if (!pdfix.Authorize(email, licenseKey)) {
            throw new Exception(pdfix.GetError());
        }

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null) {
            throw new Exception(pdfix.GetError());
        }

        if (!doc.RemoveTags()) {
            throw new Exception(pdfix.GetError());
        }

        if (!doc.AddTags()) {
            throw new Exception(pdfix.GetError());
        }

        if (!doc.Save(savePath, PdfSaveFlags.kSaveFull)) {
            throw new Exception(pdfix.GetError());
        }

        doc.Close();
    }
}
//! [RenderPage_java]
