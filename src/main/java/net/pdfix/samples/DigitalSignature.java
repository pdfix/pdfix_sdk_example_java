////////////////////////////////////////////////////////////////////////////////////////////////////
// DigitalSignature.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to sign PDF with digital signature.

package net.pdfix.samples;


import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

public class DigitalSignature {
    public static void run (
      String openPath,
      String savePath,
      String pfxPath,
      String pfxPassword
    ) throws Exception {     
        Pdfix pdfix = new Pdfix();
        if (isNull(pdfix))
            throw new Exception("Pdfix initialization fail");

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        PdfDigSig digSig = pdfix.CreateDigSig();
        if (digSig == null)
            throw new Exception(pdfix.GetError());
        
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