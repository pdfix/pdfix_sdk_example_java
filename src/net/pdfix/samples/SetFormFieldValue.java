////////////////////////////////////////////////////////////////////////////////////////////////////
// SetFormFieldValue.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*! 
\page JAVA_Samples Java Samples
- \subpage SetFormFieldValue_java
*/
/*! 
\page SetFormFieldValue_java Set Form Field Value Sample
// Example how to get and set the form field balue.
\snippet /SetFormFieldValue.java SetFormFieldValue_java
*/

//! [SetFormFieldValue_java]
package net.pdfix.samples;
import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class SetFormFieldValue {
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
            throw new Exception(Integer.toString(pdfix.GetErrorType()));

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
//! [SetFormFieldValue_java]