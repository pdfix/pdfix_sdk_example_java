////////////////////////////////////////////////////////////////////////////////////////////////////
// SetFormFieldValue.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to get and set the form field balue.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;

public class SetFormFieldValue {
    public static void run (
      String openPath,
      String savePath
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(Integer.toString(pdfix.GetErrorType()));

        PdfFormField field = doc.GetFormFieldByName("Text1");
        if (field != null) {
            String value = field.GetValue();
            if (value.length() == 0)
                value = "Default value";
            else 
                value = new StringBuilder(value).reverse().toString();
            field.SetValue(value);
        }
    }
}
