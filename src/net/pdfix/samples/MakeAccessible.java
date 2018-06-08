////////////////////////////////////////////////////////////////////////////////////////////////////
// MakeAccessible.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*! 
\page JAVA_Samples Java Samples
- \subpage MakeAccessible_java
*/
/*! 
\page MakeAccessible_java Make PDF Accessible Sample
// Example how to make PDF document accessible.
\snippet /MakeAccessible.java MakeAccessible_java
*/

//! [MakeAccessible_java]
package net.pdfix.samples;

import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class MakeAccessible {
    public static void run (
      String email,               
      String licenseKey,
      String openPath,
      String savePath,
      String language,
      String title,
      String configPath
    ) throws Exception {
        System.out.println("MakeAccessible");

        System.load(Utils.getAbsolutePath(Utils.getModuleName("pdfix")));
        // initialize Pdfix
        Pdfix pdfix = new Pdfix();
        if (pdfix == null)
            throw new Exception("Pdfix initialization fail");
        if (!pdfix.Authorize(email, licenseKey))
            throw new Exception(pdfix.GetError());

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        // customize auto-tagging
        if (!configPath.isEmpty()) {
            PsFileStream stm = pdfix.CreateFileStream(configPath, PsFileMode.kPsReadOnly);
            if (stm != null) {
                PdfDocTemplate docTmpl = doc.GetDocTemplate();
                if (docTmpl == null)
                    throw new Exception(pdfix.GetError());
                if (!docTmpl.LoadFromStream(stm, PsDataFormat.kDataFormatJson)) 
                    throw new Exception(pdfix.GetError());
                stm.Destroy();
            }
        }
        // set document language
        if (!language.isEmpty())
            doc.SetLang(language);
        
        // set document title
        if (!title.isEmpty())
            doc.SetInfo("Title", title);
        
        // convert to PDF/UA
        //PdfAccessibleParams params = new PdfAccessibleParams();
        //if (!doc.MakeAccessible)
        //    throw new Exception(pdfix.GetError());
        
        if (!doc.Save(savePath, PdfSaveFlags.kSaveFull))
            throw new Exception(pdfix.GetError());
        
        doc.Close();
        pdfix.Destroy();
    }
}
//! [MakeAccessible_java]