////////////////////////////////////////////////////////////////////////////////////////////////////
// ConvertToHtmlByPages.java
// Copyright (c) 2018 Pdfix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to convert PDF document to HTML by pages.

package net.pdfix.samples;


import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

public class ConvertToHtmlByPages {  
    private static void dumpStream(PsStream stm) {
        byte[] bytes = new byte[stm.GetSize()];
        stm.Read(0, bytes);
        String str = new String(bytes);
        System.out.print(str);
    }
    
    public static void run(
      String openPath                          // source PDF document
    ) throws Exception {     
        Pdfix pdfix = new Pdfix();
        if (isNull(pdfix))
          throw new Exception("Pdfix initialization fail");

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
          throw new Exception(pdfix.GetError());

        PdfHtmlConversion htmlConv = doc.CreateHtmlConversion();
        if (htmlConv == null)
          throw new Exception(pdfix.GetError());
                
        // save the document JavaScript 
        PsMemoryStream stmJS = pdfix.CreateMemStream();
        htmlConv.SaveJavaScript(stmJS);
        // process JS stream 
        dumpStream(stmJS);
        stmJS.Destroy();
        
        // save document CSS
        PsMemoryStream stmCSS = pdfix.CreateMemStream();
        htmlConv.SaveCSS(stmCSS);
        // process CSS stream
        dumpStream(stmCSS);
        stmCSS.Destroy();

        // prepare HTML params for conversion
        PdfHtmlParams htmlParams = new PdfHtmlParams();
        htmlParams.flags |= Pdfix.kHtmlExportJavaScripts;
        htmlParams.flags |= Pdfix.kHtmlExportFonts;
        htmlParams.flags |= Pdfix.kHtmlRetainFontSize;
        htmlParams.flags |= Pdfix.kHtmlRetainTextColor;
        htmlParams.flags |= Pdfix.kHtmlNoExternalCSS | Pdfix.kHtmlNoExternalJS | Pdfix.kHtmlNoExternalIMG
            | Pdfix.kHtmlNoExternalFONT;

        if (!htmlConv.SetParams(htmlParams))
          throw new Exception(pdfix.GetError());

        // create memory stream where document will be written
        PsMemoryStream stmDoc = pdfix.CreateMemStream();

        // convert only some pages
        for (int i = 0; i < doc.GetNumPages(); i++) {
          if (!htmlConv.AddPage(i))
            throw new Exception(pdfix.GetError());
        }

        if (!htmlConv.SaveToStream(stmDoc))
          throw new Exception(pdfix.GetError());

        // process HTML Doc stream
        dumpStream(stmDoc);
        stmDoc.Destroy();
        
        htmlConv.Destroy();
        doc.Close();
        pdfix.Destroy();
      }
}