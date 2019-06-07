////////////////////////////////////////////////////////////////////////////////////////////////////
// ExportFormFieldValues.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*! 
\page JAVA_Samples Java Samples
- \subpage ExportFormFieldValues_java
*/
/*! 
\page ExportFormFieldValues_java Export Form Field Values Sample
// Example how to export form field values from PDF form.
\snippet /ExportFormFieldValues.java ExportFormFieldValues_java
*/

//! [ExportFormFieldValues_java]
package net.pdfix.samples;

import java.io.PrintWriter;
import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class ExportFormFieldValues {
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
        
        PrintWriter out = new PrintWriter(savePath);
        
        for (int i = 0; i < doc.GetNumFormFields(); i = i + 1) {
            String name, value;
            PdfFormField field = doc.GetFormField(i);
            if (field == null) 
                throw new Exception(pdfix.GetError());
            name = field.GetFullName();
            value = field.GetValue();
            
            out.println(name + " : " + value);
        }
        
        doc.Close();
        pdfix.Destroy();
    }
}
//! [ExportFormFieldValues_java]