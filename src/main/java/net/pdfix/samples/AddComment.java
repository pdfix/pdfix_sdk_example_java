////////////////////////////////////////////////////////////////////////////////////////////////////
// AddComment.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to add a comment on PDF page.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

public class AddComment {
    public static void run (
      String openPath,
      String savePath
    ) throws Exception {        
        Pdfix pdfix = new Pdfix();
        if (isNull(pdfix))
            throw new Exception("Pdfix initialization fail");
        
        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        PdfPage page = doc.AcquirePage(0);
        if (page == null)
            throw new Exception(pdfix.GetError());

        PdfRect cropBox = page.GetCropBox();
        
        // place annotation to the middle of the page
        PdfRect annotRect = new PdfRect();
        annotRect.left = (cropBox.right + cropBox.left) / 2.f - 10;
        annotRect.bottom = (cropBox.top + cropBox.bottom) / 2.f - 10;
        annotRect.right = (cropBox.right + cropBox.left) / 2.f + 10;
        annotRect.top = (cropBox.top + cropBox.bottom) / 2.f + 10;
        
        PdfTextAnnot annot = (PdfTextAnnot)page.CreateAnnot(PdfAnnotSubtype.kAnnotText, annotRect);
        page.AddAnnot(-1, annot);
        if (annot == null)
            throw new Exception(pdfix.GetError());
            
        annot.SetAuthor("Peter brown");
        annot.SetContents("This is my comment");
        annot.AddReply("Mark Fish", "This is some reply");
        
        page.Release();
        
        if (!doc.Save(savePath, Pdfix.kSaveFull))
            throw new Exception(pdfix.GetError());
        
        doc.Close();
        pdfix.Destroy();
    }    
}