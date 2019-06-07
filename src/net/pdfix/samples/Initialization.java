////////////////////////////////////////////////////////////////////////////////////////////////////
// Initialization.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*! 
\page JAVA_Samples Java Samples
- \subpage Initialization_java
*/
/*! 
\page Initialization_java Initialization Sample
// Example how to initialize PDFix SDK for Java.
\snippet /Initialization.java Initialization_java
*/

//! [Initialization_java]
package net.pdfix.samples;

import net.pdfix.Utils;
import net.pdfix.pdfixlib.Pdfix;

public class Initialization {  
    public static void run(
      String email,                                     // authorization email   
      String licenseKey                                 // license key
    ) throws Exception {       
        System.load(Utils.getAbsolutePath(Utils.getModuleName("pdfix")));

        Pdfix pdfix = new Pdfix();
        if (pdfix == null)
            throw new Exception("Pdfix initialization fail");

        if (!pdfix.Authorize(email, licenseKey))
            throw new Exception(pdfix.GetError());

        if (pdfix.GetVersionMajor() != Pdfix.PDFIX_VERSION_MAJOR || 
          pdfix.GetVersionMinor() != Pdfix.PDFIX_VERSION_MINOR ||
          pdfix.GetVersionPatch() != Pdfix.PDFIX_VERSION_PATCH)
            throw new Exception("Pdfix invalid version");

        // ...

        pdfix.Destroy();
    }  
}
//! [Initialization_java]
