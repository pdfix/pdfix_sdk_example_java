////////////////////////////////////////////////////////////////////////////////////////////////////
// SetFieldFlags.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Set Field Flags Sample

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;

public class SetFieldFlags {
    public static void run (
      String openPath,
      String savePath
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        int i;
        
        for (i=0; i < doc.GetNumFormFields(); i = i + 1) {
            PdfFormField field = doc.GetFormField(i);
            if (!field.SetFlags(i)) {
                throw new Exception(pdfix.GetError());
            } 
        
            int flags = field.GetFlags();
            flags = Pdfix.kFieldFlagReadOnly;
            if (field.SetFlags(flags) == false)
                throw new Exception(pdfix.GetError());
        }
        if (!doc.Save(savePath, Pdfix.kSaveFull)) {
            throw new Exception(pdfix.GetError());
        } 
        doc.Close();
        pdfix.Destroy();
    }
} 
