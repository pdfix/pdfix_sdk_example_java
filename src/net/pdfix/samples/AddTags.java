////////////////////////////////////////////////////////////////////////////////////////////////////
// AddTags.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*!
\page JAVA_Samples Java Samples
- \subpage AddTags_java
*/
/*!
\page AddTags_java Add Tags Sample
Example how to automatically add tags into the document.
\snippet /AddTags.java AddTags_java
*/

//! [AddTags_java]
package net.pdfix.samples;

import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class AddTags {
    public static void run(
      String email,                             // authorization email   
      String licenseKey,                        // authorization license key
      String openPath,                          // source PDF document
      String savePath,                          // output HTML file
      String configPath                         // configuration file
    ) throws Exception {        
        System.out.println("AddTags");

        System.load(Utils.getAbsolutePath(Utils.getModuleName("pdfix")));

        Pdfix pdfix = new Pdfix();
        if (pdfix == null)
            throw new Exception("Pdfix initialization fail");

        if (!pdfix.Authorize(email, licenseKey))
            throw new Exception(pdfix.GetError());

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());

        // customize auto-tagging 
        PsFileStream stm = pdfix.CreateFileStream(configPath, PsFileMode.kPsReadOnly);
        if (stm != null) {
            PdfDocTemplate docTmpl = doc.GetDocTemplate();
            if (docTmpl == null)
                throw new Exception(pdfix.GetError());
            if (!docTmpl.LoadFromStream(stm, PsDataFormat.kDataFormatJson))
                throw new Exception(pdfix.GetError());
            stm.Destroy();
        }

        if (!doc.AddTags())
            throw new Exception(pdfix.GetError());

        if (!doc.Save(savePath, PdfSaveFlags.kSaveFull))
            throw new Exception(pdfix.GetError());

        doc.Close();
        pdfix.Destroy();
    }  
}
//! [AddTags_java]