////////////////////////////////////////////////////////////////////////////////////////////////////
// ExtractText.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*! 
\page JAVA_Samples Java Samples
- \subpage ExtractText_java
*/
/*! 
\page ExtractText_java Export Extract Text Sample
// Example how to extract text from PDF.
\snippet /ExtractText.java ExtractText_java
*/

//! [ExtractText_java]
package net.pdfix.samples;

import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class ExtractText {
    private static void GetText (PdeElement element, StringBuilder ss,
            boolean eof) {
        PdfElementType elemType = element.GetType();
        if (PdfElementType.kPdeText == elemType) {
            PdeText textElem = (PdeText)element;
            String str = textElem.GetText();
            ss.append(str);
            if (eof) 
                ss.append("\n");
            else {
                int count = element.GetNumChildren();
                if (count == 0) 
                    return;
                for (int i = 0; i < count; i = i + 1) {
                    PdeElement child = element.GetChild(i);
                    if (child != null) 
                        GetText(child, ss, eof);
                }
            }
        }
    }
    
    public static void run (
      String email,               
      String licenseKey,
      String openPath,
      String savePath,
      String configPath
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
        int numPages = doc.GetNumPages();
        
        StringBuilder ss = new StringBuilder();
        
        for (int i = 0; i < numPages; i = i + 1) {           
            PdfPage page = doc.AcquirePage(i);
            if (page == null)
                throw new Exception(pdfix.GetError());
            PdePageMap pageMap = page.AcquirePageMap();
            if (pageMap == null)
                throw new Exception(pdfix.GetError());
            
            PdeElement container = pageMap.GetElement();
            if (container == null)
                throw new Exception(pdfix.GetError());
            
            GetText(container, ss, true);
            
            page.Release();
        }
        PsFileStream stream = pdfix.CreateFileStream(savePath, PsFileMode.kPsWrite);
        if (stream == null)
            throw new Exception(pdfix.GetError());
        stream.Write(stream.GetPos(), ss.toString().getBytes());
        stream.Destroy();
        
        doc.Close();
        pdfix.Destroy();
    }
}
//! [ExtractText_java]