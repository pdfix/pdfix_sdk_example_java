////////////////////////////////////////////////////////////////////////////////////////////////////
// AddComment.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*! 
\page JAVA_Samples Java Samples
- \subpage AddComment_java
*/
/*! 
\page AddComment_java Add Comment Sample
// Example how to add a comment on PDF page.
\snippet /AddComment.java AddComment_java
*/

//\cond INTERNAL
//! [AddComment_java]
package net.pdfix.samples;

import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class AddComment {
    public static void run (
      String email,               
      String licenseKey,
      String openPath,
      String savePath
    ) throws Exception {
        System.out.println("AddComment");
        
        System.load(Utils.getAbsolutePath(Utils.getModuleName("pdfix")));

        Pdfix pdfix = new Pdfix();
        if (pdfix == null)
            throw new Exception("Pdfix initialization fail");
        if (!pdfix.Authorize(email, licenseKey))
            throw new Exception(pdfix.GetError());

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        PdfPage page = doc.AcquirePage(0);
        if (page == null)
            throw new Exception(pdfix.GetError());

        PdfRect cropBox = page.GetCropBox();
        
        // place annotation to the middle of the page
        PdfRect annotRect = new PdfRect();
        annotRect.left = (cropBox.right + cropBox.left) / 2. - 10;
        annotRect.bottom = (cropBox.top + cropBox.bottom) / 2. - 10;
        annotRect.right = (cropBox.right + cropBox.left) / 2. + 10;
        annotRect.top = (cropBox.top + cropBox.bottom) / 2. + 10;
        
        PdfTextAnnot annot = page.AddTextAnnot(-1, annotRect);
        annot.SetAuthor("Peter brown");
        annot.SetContents("This is my comment");
        annot.AddReply("Mark Fish", "This is some reply");
        
        doc.ReleasePage(page);
        doc.Save(savePath, PdfSaveFlags.kSaveFull);
        doc.Close();
        pdfix.Destroy();
    }    
}
//! [AddComment_java]
//\endcond