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

    private static String getLibraryName(String name) throws Exception {

        String os = System.getProperty("os.name").toLowerCase();
        if ((os.indexOf("mac") >= 0) || (os.indexOf("darwin") >= 0)) {
            if ("aarch64".equals(System.getProperty("os.arch")))
                return "/arm64/lib" + name + ".dylib";
            else
                return "/x86_64/lib" + name + ".dylib";
        } else if (os.indexOf("win") >= 0) {
            if ("64".equals(System.getProperty("sun.arch.data.model")))
                return "/x86_64/" + name + ".dll";
            else
                throw new Exception("Missing platform support");
                // return "/x86/" + name + ".dll";
        } else if (os.indexOf("nux") >= 0) {
            return "/x64/lib" + name + ".so";
        }
        throw new Exception("Unsupported platform " + os);
    }

    public static void run(String libPath) throws Exception {

        // load pdfix libraries
        System.out.println("Load PDFix dynamic libraries:");

        System.load(libPath + "/" + getLibraryName("pdf"));

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
