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
        System.out.println("ExtractText");
        
        System.load(Utils.getAbsolutePath(Utils.getModuleName("pdfix")));

        Pdfix pdfix = new Pdfix();
        if (pdfix == null)
            throw new Exception("Pdfix initialization fail");
        if (!pdfix.Authorize(email, licenseKey))
            throw new Exception(pdfix.GetError());

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        System.out.println("<!--PDFix SDK " + pdfix.GetVersionMajor() + "." + 
                pdfix.GetVersionMinor() + "." + pdfix.GetVersionPatch() + ""
                        + " conversion PDF to TXT. License: http://pdfix.net/terms -->");
        int numPages = doc.GetNumPages();
        
        StringBuilder ss = new StringBuilder();
        
        for (int i = 0; i < numPages; i = i + 1) {
            System.out.println("Processing pages..." + i + 1 + "/" + numPages);
            System.out.println("Page:" + i);
            
            PdfPage page = doc.AcquirePage(i);
            if (page == null)
                throw new Exception("Pdfix initialization fail");
            PdePageMap pageMap = page.AcquirePageMap();
            if (pageMap == null)
                throw new Exception("Pdfix initialization fail");
            
            PdeElement container = pageMap.GetElement();
            if (container == null)
                throw new Exception("Pdfix initialization fail");
            
            GetText(container, ss, true);
            
            doc.ReleasePage(page);
        }
        PsFileStream stream = pdfix.CreateFileStream(savePath, PsFileMode.kPsWrite);
        if (stream == null)
            throw new Exception("Pdfix initialization fail");
        stream.Write(stream.GetPos(), ss.toString().getBytes());
        stream.Destroy();
        
        doc.Close();
        pdfix.Destroy();
    }
}
//! [ExtractText_java]