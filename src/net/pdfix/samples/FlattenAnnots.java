////////////////////////////////////////////////////////////////////////////////////////////////////
// FlattenAnnots.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*! 
\page JAVA_Samples Java Samples
- \subpage FlattenAnnots_java
*/
/*! 
\page FlattenAnnots_java Flatten Annotations Sample
// Example how to flatten annotations in PDF.
\snippet /FlattenAnnots.java FlattenAnnots_java
*/

//! [FlattenAnnots_java]
package net.pdfix.samples;

import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class FlattenAnnots {
    public static void run (
      String email,               
      String licenseKey,
      String openPath,
      String savePath,
      PdfFlattenAnnotsParams params
    ) throws Exception {
        System.out.println("FlattenAnnots");
        
        System.load(Utils.getAbsolutePath(Utils.getModuleName("pdfix")));

        Pdfix pdfix = new Pdfix();
        if (pdfix == null)
            throw new Exception("Pdfix initialization fail");
        if (!pdfix.Authorize(email, licenseKey))
            throw new Exception(pdfix.GetError());

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        if (!doc.FlattenAnnots(params)) {
            throw new Exception(pdfix.GetError());
        }
        if (!doc.Save(savePath, PdfSaveFlags.kSaveFull)) {
            throw new Exception(pdfix.GetError());
        }
        doc.Close();
        pdfix.Destroy();
    }
}
//! [FlattenAnnots_java]