////////////////////////////////////////////////////////////////////////////////////////////////////
// ExportFormFieldValues.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to export form field values from PDF form.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

public class ExportFormFieldValues {
    public static void run (
      String openPath
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        if (isNull(pdfix))
            throw new Exception("Pdfix initialization fail");

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        for (int i = 0; i < doc.GetNumFormFields(); i++) {
            String name, value;
            PdfFormField field = doc.GetFormField(i);
            if (field == null) 
                throw new Exception(pdfix.GetError());
            name = field.GetFullName();
            value = field.GetValue();
            
            System.out.println(name + " : " + value);
        }
        
        doc.Close();
        pdfix.Destroy();
    }
}
