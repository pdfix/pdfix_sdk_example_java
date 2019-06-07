////////////////////////////////////////////////////////////////////////////////////////////////////
// ConvertToHtml.cpp
// Copyright (c) 2018 Pdfix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////
/*!
\page JAVA_Samples C++ Samples
- \subpage ConvertToHtml_java
*/
/*!
\page ConvertToHtml_java Pdf To Html Sample
Example how to convert whole PDF document to HTML.
\snippet /ConvertToHtml.java ConvertToHtml_java
*/

//! [ConvertToHtml_java]
package net.pdfix.samples;

import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;
import net.pdfix.pdftohtml.*;

public class ConvertToHtml {  
    public static void run(
      String email,                             // authorization email   
      String licenseKey,                        // authorization license key
      String openPath,                          // source PDF document
      String savePath,                          // output HTML file
      String configPath,                        // configuration file
      PdfHtmlParams htmlParams
    ) throws Exception {     
        System.load(Utils.getAbsolutePath(Utils.getModuleName("pdfix")));
        System.load(Utils.getAbsolutePath(Utils.getModuleName("pdf_to_html")));

        Pdfix pdfix = new Pdfix();
        if (pdfix == null)
          throw new Exception("Pdfix initialization fail");

        if (!pdfix.Authorize(email, licenseKey))
          throw new Exception(pdfix.GetError());

        PdfToHtml pdfToHtml = new PdfToHtml();
        if (pdfToHtml == null)
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
          PdfDocTemplate docTmpl = doc.GetDocTemplate();
          if (docTmpl == null)
            throw new Exception(pdfix.GetError());
          if (!docTmpl.LoadFromStream(stm, PsDataFormat.kDataFormatJson))
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
//! [ConvertToHtml_java]