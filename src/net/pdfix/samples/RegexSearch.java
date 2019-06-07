////////////////////////////////////////////////////////////////////////////////////////////////////
// RegexSearch.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*!
\page JAVA_Samples Java Samples
- \subpage RegexSearch_java
*/
/*!
\page RegexSearch_java Regex Search Sample
Example how to search the regex pattern at the first page of the document.
\snippet /RegexSearch.java RegexSearch_java
*/

//! [RegexSearch_java]
package net.pdfix.samples;

import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class RegexSearch {
    public static void run (
      String email,               
      String licenseKey,
      String openPath,
      String regexPattern
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
        
        PdfPage page = doc.AcquirePage(0);
        if (page == null)
            throw new Exception(pdfix.GetError());
        
        PdePageMap pageMap = page.AcquirePageMap();
        if (pageMap == null)
            throw new Exception(pdfix.GetError());
        PsRegex regex = pdfix.CreateRegex();
        regex.SetPattern(regexPattern);
        
        PdeElement element = pageMap.GetElement();
        PdeContainer container = (PdeContainer)element;
        int count = container.GetNumChildren();
        for (int i = 0; i < count; i = i + 1) {
            PdeElement elem = container.GetChild(i);
            if (elem.GetType() == PdfElementType.kPdeText) {
                PdeText textElem = (PdeText)elem;
                String text = textElem.GetText();
                int startPos = 0;
                while (startPos < (int)text.length()) {
                    if (regex.Search(text, startPos)) {
                        int pos = regex.GetPosition();
                        int len = regex.GetLength();
                        String match = text.substring((startPos + pos), len);
                        startPos += pos + 1;
                    }
                    else 
                        startPos = text.length();
                }
            }
        }
        regex.Destroy();
        
        page.Release();
        doc.Close();
        pdfix.Destroy();
    }
}
//! [RegexSearch_java]