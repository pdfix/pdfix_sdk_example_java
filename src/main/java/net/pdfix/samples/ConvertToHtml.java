////////////////////////////////////////////////////////////////////////////////////////////////////
// ConvertToHtml.cpp
// Copyright (c) 2018 Pdfix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to convert PDF document to HTML.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;
import net.pdfix.pdftohtml.*;
import static java.util.Objects.isNull;

public class ConvertToHtml {  
    public static void run(
      String openPath,                          // source PDF document
      String savePath,                          // output HTML file
      String configPath,                        // configuration file
      PdfHtmlParams htmlParams
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

        // customize conversion 
        PsFileStream stm = pdfix.CreateFileStream(configPath, PsFileMode.kPsReadOnly);
        if (stm != null) {
          PdfDocTemplate doc_preflight = doc.GetTemplate();
          if (doc_preflight == null)
            throw new Exception(pdfix.GetError());
          if (!doc_preflight.LoadFromStream(stm, PsDataFormat.kDataFormatJson))
            throw new Exception(pdfix.GetError());
          stm.Destroy();
        }
    
        // set htmlParam 
        //htmlParams.type = PdfHtmlType.kPdfHtmlResponsive;
        //htmlParams.width = 1200;    
        //htmlParams.flags |= PdfToHtml.kHtmlExportJavaScripts;
        //htmlParams.flags |= PdfToHtml.kHtmlExportFonts;
        //htmlParams.flags |= PdfToHtml.kHtmlRetainFontSize;
        //htmlParams.flags |= PdfToHtml.kHtmlRetainTextColor;
        //htmlParams.flags |= PdfToHtml.kHtmlNoExternalCSS | PdfToHtml.kHtmlNoExternalJS | 
        //  PdfToHtml.kHtmlNoExternalIMG | PdfToHtml.kHtmlNoExternalFONT;
        
        PdfHtmlDoc htmlDoc = pdfToHtml.OpenHtmlDoc(doc);
        if (htmlDoc == null)
          throw new Exception(pdfix.GetError());

        if (!htmlDoc.Save(savePath, htmlParams))
          throw new Exception(pdfix.GetError());

        htmlDoc.Close();    
        doc.Close();
        pdfToHtml.Destroy();
        pdfix.Destroy();
      }
}