////////////////////////////////////////////////////////////////////////////////////////////////////
// RegexSearch.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to search the regex pattern at the first page of the document.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

public class RegexSearch {
    public static void run (
      String openPath,
      String regexPattern
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        if (isNull(pdfix))
            throw new Exception("Pdfix initialization fail");

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
                        // int len = regex.GetLength();
                        // String match = text.substring((startPos + pos), len);
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