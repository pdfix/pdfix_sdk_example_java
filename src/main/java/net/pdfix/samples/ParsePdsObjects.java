////////////////////////////////////////////////////////////////////////////////////////////////////
// ParsePdsObjects.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to parse low level PDF objects.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;

public class ParsePdsObjects {
    public static void parseObject(PdsObject object) {
    }
    
    public static void run (
      String openPath
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        PdsObject root = doc.GetRootObject();
        
        parseObject(root);

        doc.Close();
        pdfix.Destroy();
    }    
}
