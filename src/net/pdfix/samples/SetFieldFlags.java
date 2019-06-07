////////////////////////////////////////////////////////////////////////////////////////////////////
// SetFieldFlags.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*!
\page JAVA_Samples Java Samples
- \subpage SetFieldFlags_java
*/
/*!
\page SetFieldFlags_java Set Field Flags Sample
Set Field Flags Sample
\snippet /SetFieldFlags.java SetFieldFlags_java
*/

//! [SetFieldFlags_java]
package net.pdfix.samples;

import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class SetFieldFlags {
    public static void run (
      String email,               
      String licenseKey,
      String openPath,
      String savePath
    ) throws Exception {
        System.load(Utils.getAbsolutePath(Utils.getModuleName("pdfix")));

        Pdfix pdfix = new Pdfix();
        if (pdfix == null)
            throw new Exception("Pdfix initialization fail");

        if (!pdfix.Authorize(email, licenseKey))
            throw new Exception(pdfix.GetError());

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        int i;
        
        for (i=0; i < doc.GetNumFormFields(); i = i + 1) {
            String name, value;
            PdfFormField field = doc.GetFormField(i);
            if (!field.SetFlags(i)) {
                throw new Exception(pdfix.GetError());
            } 
        
            int flags = field.GetFlags();
            flags = pdfix.kFieldFlagReadOnly;
            if (field.SetFlags(flags) == false)
                throw new Exception(pdfix.GetError());
        }
        if (!doc.Save(savePath, PdfSaveFlags.kSaveFull)) {
            throw new Exception(pdfix.GetError());
        } 
        doc.Close();
        pdfix.Destroy();
    }
} 
//! [SetFieldFlags_java]