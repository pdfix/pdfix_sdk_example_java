////////////////////////////////////////////////////////////////////////////////////////////////////
// Initialization.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to initialize PDFix SDK for Java.
package net.pdfix.samples;

import net.pdfix.pdfixlib.Pdfix;
import static java.util.Objects.isNull;

public class Initialization {
    
    private static String getLibraryName(String name) throws Exception {

        String os = System.getProperty("os.name").toLowerCase();
        if ((os.indexOf("mac") >= 0) || (os.indexOf("darwin") >= 0)) {
            return "/darwin/lib" + name + ".dylib";
        }
        else if (os.indexOf("win") >= 0) {
            if ("64".equals(System.getProperty("sun.arch.data.model")))
                return "/x64/" + name + ".dll";
            else
                return "/x86/" + name + ".dll";
        }
        else if (os.indexOf("nux") >= 0) {
            return "/linux/lib" + name + ".so";
        }
        throw new Exception("Unsupported platform " + os);
    }

    public static void run(String libPath) throws Exception {

        // load pdfix libraries
        System.load(libPath + "/" + getLibraryName("pdfix"));
        System.load(libPath + "/" + getLibraryName("ocr_tesseract"));

        Pdfix pdfix = new Pdfix();        
        if (isNull(pdfix))
            throw new Exception("Pdfix initialization fail");

        System.out.println( "PDFix SDK " + pdfix.GetVersionMajor() + "." +
                pdfix.GetVersionMinor() + "." + pdfix.GetVersionPatch());

        // Developer license authorization
        if (!pdfix.IsAuthorizaed()) {
            String devLicenseName = "";
            String devLicenseKey = "";
            if (!devLicenseName.isEmpty() && !devLicenseKey.isEmpty()) {
                if (!pdfix.GetAccountAuthorization().Authorize(devLicenseName, devLicenseKey)) {                
                    throw new Exception("Pdfix authorization fail");
                }
            }
        }
            
        // Production license authorization
        if (!pdfix.IsAuthorizaed()) {
            String licenseName = "";
            String licenseKey = "";
            if (licenseName.isEmpty()) {
                // Standard license type (activation)
                if (!pdfix.GetStandardAuthorization().Activate(licenseKey)) {
                    throw new Exception(pdfix.GetError());
                }
            }
            else {
                // Account license type
                if (!pdfix.GetAccountAuthorization().Authorize(licenseName, licenseKey)) {
                    throw new Exception(pdfix.GetError());
                }        
            }
        }
        // your code goes here

        pdfix.Destroy();
    }  
}
