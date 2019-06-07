////////////////////////////////////////////////////////////////////////////////////////////////////
// ParsePdsObjects.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*! 
\page JAVA_Samples Java Samples
- \subpage ParsePdsObjects_java
*/
/*! 
\page ParsePdsObjects_java Parse PDF Objects Sample
// Example how to parse low level PDF objects.
\snippet /ParsePdsObjects.java ParsePdsObjects_java
*/

//! [ParsePdsObjects_java]
package net.pdfix.samples;

import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class ParsePdsObjects {
    public static void parseObject(PdsObject object) {
    }
    
    public static void run (
      String email,               
      String licenseKey,
      String openPath,
      String savePath
    ) throws Exception {
        System.load(Utils.getAbsolutePath(Utils.getModuleName("pdfix")));

        Pdfix pdfix = new Pdfix();
        if (pdfix == null)
            throw new Exception("Pdfix initialization fail");
        if (!pdfix.Authorize(email, licenseKey))
            throw new Exception(pdfix.GetError());

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        PdsObject root = doc.GetRootObject();
        
        parseObject(root);

        doc.Close();
        pdfix.Destroy();
    }    
}
//! [ParsePdsObjects_java]