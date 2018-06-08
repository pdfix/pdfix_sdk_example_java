////////////////////////////////////////////////////////////////////////////////////////////////////
// DocumentMetadata.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*! 
\page JAVA_Samples Java Samples
- \subpage DocumentMetadata_java
*/
/*! 
\page DocumentMetadata_java Document Metadata Sample
// Example how to access PDF document metadata.
\snippet /DocumentMetadata.java DocumentMetadata_java
*/

//! [DocumentMetadata_java]
package net.pdfix.samples;

import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class DocumentMetadata {
    public static void run (
      String email,               
      String licenseKey,
      String openPath,
      String savePath,
      String xmlPath
    ) throws Exception {
        System.out.println("DocumentMetadata");
        
        System.load(Utils.getAbsolutePath(Utils.getModuleName("pdfix")));

        Pdfix pdfix = new Pdfix();
        if (pdfix == null)
            throw new Exception("Pdfix initialization fail");
        if (!pdfix.Authorize(email, licenseKey))
            throw new Exception(pdfix.GetError());

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        String title = doc.GetInfo("Title");
        doc.GetInfo("Title");
        doc.SetInfo("Title", "My next presenttion");
        
        PsMetadata metadata = doc.GetMetadata();
        if (metadata == null) {
            throw new Exception(pdfix.GetError());
        }
        PsFileStream stream = pdfix.CreateFileStream(xmlPath,
                PsFileMode.kPsWrite);
        stream.Destroy();
        
        if (!doc.Save(savePath, PdfSaveFlags.kSaveFull)) {
            throw new Exception(pdfix.GetError());
        }
        doc.Close();
        pdfix.Destroy();
    }
}
//! [DocumentMetadata_java]