////////////////////////////////////////////////////////////////////////////////////////////////////
// DigitalSignature.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*! 
\page JAVA_Samples Java Samples
- \subpage DigitalSignature_java
*/
/*! 
\page DigitalSignature_java Digital Signature Sample
// Example how to sign PDF with digital signature.
\snippet /DigitalSignature.java DigitalSignature_java
*/

//! [DigitalSignature_java]
package net.pdfix.samples;

import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class DigitalSignature {
    public static void run (
      String email,               
      String licenseKey,
      String openPath,
      String savePath,
      String pfxPath,
      String pfxPassword
    ) throws Exception {
        System.out.println("DigitalSignature");
        
        System.load(Utils.getAbsolutePath(Utils.getModuleName("pdfix")));

        Pdfix pdfix = new Pdfix();
        if (pdfix == null)
            throw new Exception("Pdfix initialization fail");
        if (!pdfix.Authorize(email, licenseKey))
            throw new Exception(pdfix.GetError());

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        PdfDigSig digSig = pdfix.CreateDigSig();
        if (digSig == null) {
            throw new Exception(pdfix.GetError());
        }
        digSig.SetReason("Testing PDFix API");
        digSig.SetLocation("Location");
        digSig.SetContactInfo("info@pdfix.net");
        if (!digSig.SetPfxFile(pfxPath, pfxPassword)) {
            throw new Exception(pdfix.GetError());
        }
        if (!digSig.SignDoc(doc, savePath)) {
            throw new Exception(pdfix.GetError());
        }
        digSig.Destroy();
        
        doc.Close();
        pdfix.Destroy();
    }
}
//! [DigitalSignature_java]