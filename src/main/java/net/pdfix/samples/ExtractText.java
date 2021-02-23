////////////////////////////////////////////////////////////////////////////////////////////////////
// ExtractText.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to extract text from PDF.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

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
      String openPath,
      String savePath,
      String configPath
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        if (isNull(pdfix))
            throw new Exception("Pdfix initialization fail");

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