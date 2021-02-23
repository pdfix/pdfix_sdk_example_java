////////////////////////////////////////////////////////////////////////////////////////////////////
// ConvertToHtmlByPages.java
// Copyright (c) 2018 Pdfix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to convert PDF document to HTML by pages.

package net.pdfix.samples;


import net.pdfix.pdfixlib.*;
import net.pdfix.pdftohtml.*;
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

        PdfToHtml pdfToHtml = new PdfToHtml();
        if (isNull(pdfToHtml))
          throw new Exception("PdfToHtml initialization fail");

        System.out.println( pdfToHtml.GetVersionMajor() + "." +  
          pdfToHtml.GetVersionMinor() + "." + pdfToHtml.GetVersionPatch());

        if (!pdfToHtml.Initialize(pdfix))
          throw new Exception(pdfix.GetError());

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
          throw new Exception(pdfix.GetError());
    
        PdfHtmlDoc htmlDoc = pdfToHtml.OpenHtmlDoc(doc);
        if (htmlDoc == null)
          throw new Exception(pdfix.GetError());
        
        // prepare HTML params for conversion
        PdfHtmlParams htmlParams = new PdfHtmlParams();
        htmlParams.flags |= PdfToHtml.kHtmlExportJavaScripts;
        htmlParams.flags |= PdfToHtml.kHtmlExportFonts;
        htmlParams.flags |= PdfToHtml.kHtmlRetainFontSize;
        htmlParams.flags |= PdfToHtml.kHtmlRetainTextColor;
        htmlParams.flags |= PdfToHtml.kHtmlNoExternalCSS | PdfToHtml.kHtmlNoExternalJS | 
          PdfToHtml.kHtmlNoExternalIMG | PdfToHtml.kHtmlNoExternalFONT;
        
        // save the document JavaScript 
        PsMemoryStream stmJS = pdfix.CreateMemStream();
        pdfToHtml.SaveJavaScript(stmJS);
        // process JS stream 
        dumpStream(stmJS);
        stmJS.Destroy();
        
        // save document CSS
        PsMemoryStream stmCSS = pdfix.CreateMemStream();
        pdfToHtml.SaveCSS(stmCSS);
        // process CSS stream
        dumpStream(stmCSS);
        stmCSS.Destroy();
                
        // save document HTML (does not contain pages)
        PsMemoryStream stmDoc = pdfix.CreateMemStream();
        htmlDoc.SaveDocHtml(stmDoc, htmlParams);
        // process HTML Doc stream
        dumpStream(stmDoc);
        stmDoc.Destroy();
        
        // save page HTML
        for (int i = 0; i < doc.GetNumPages(); i++) {
            PsMemoryStream stmPage = pdfix.CreateMemStream();
            htmlDoc.SavePageHtml(stmPage, htmlParams, i);
            // process HTML Page stream
            dumpStream(stmPage);
            stmPage.Destroy();
        }
        
        
        htmlDoc.Close();    
        doc.Close();
        pdfToHtml.Destroy();
        pdfix.Destroy();
      }
}