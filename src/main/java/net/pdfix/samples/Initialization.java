////////////////////////////////////////////////////////////////////////////////////////////////////
// Initialization.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to initialize PDFix SDK for Java.
package net.pdfix.samples;

import net.pdfix.pdfixlib.*;

public class Initialization {
    private static void dumpStream(PsStream stm) {
        byte[] bytes = new byte[stm.GetSize()];
        stm.Read(0, bytes);
        String str = new String(bytes);
        System.out.print(str);
    }

    public static void run() throws Exception {

        Pdfix pdfix = new Pdfix();
        System.out.println("PDFix SDK " + pdfix.GetVersionMajor() + "." +
                pdfix.GetVersionMinor() + "." + pdfix.GetVersionPatch());

        // license authorization
        System.out.print("License Authorization:");
        if (!pdfix.GetAuthorization().IsAuthorized()) {
            String licenseName = "";
            String licenseKey = "";
            if (licenseName.isEmpty() && !licenseKey.isEmpty()) {
                // Standard license type (activation)
                if (!pdfix.GetStandardAuthorization().Activate(licenseKey)) {
                    throw new Exception(pdfix.GetError());
                }
            } else if (!licenseKey.isEmpty()) {
                // Account license type
                if (!pdfix.GetAccountAuthorization().Authorize(licenseName, licenseKey)) {
                    throw new Exception(pdfix.GetError());
                }
            }
        }

        // read license status
        System.out.println("License Status:");
        PsStream memStm = pdfix.CreateMemStream();
        if (!pdfix.GetAuthorization().SaveToStream(memStm, PsDataFormat.kDataFormatJson)) {
            throw new Exception(pdfix.GetError());            
        }
        dumpStream(memStm);

        // your code goes here

        pdfix.Destroy();
    }
}
