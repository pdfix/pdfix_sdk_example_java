////////////////////////////////////////////////////////////////////////////////////////////////////
// ConvertToHtml.cpp
// Copyright (c) 2018 Pdfix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to convert PDF document to HTML.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;

public class ConvertToHtml {  
    public static void run(
      String openPath,                          // source PDF document
      String savePath,                          // output HTML file
      String configPath,                        // configuration file
      PdfHtmlParams htmlParams
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
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
        //htmlParams.type = Pdfix.kPdfHtmlResponsive;
        //htmlParams.width = 1200;    
        //htmlParams.flags |= Pdfix.kHtmlExportJavaScripts;
        //htmlParams.flags |= Pdfix.kHtmlExportFonts;
        //htmlParams.flags |= Pdfix.kHtmlRetainFontSize;
        //htmlParams.flags |= Pdfix.kHtmlRetainTextColor;
        //htmlParams.flags |= Pdfix.kHtmlNoExternalCSS | Pdfix.kHtmlNoExternalJS | 
        //  Pdfix.kHtmlNoExternalIMG | Pdfix.kHtmlNoExternalFONT;
        
        PdfHtmlConversion htmlConv = doc.CreateHtmlConversion();
        if (htmlConv == null)
          throw new Exception(pdfix.GetError());
        
        if (!htmlConv.SetParams(htmlParams))
          throw new Exception(pdfix.GetError());

        if (!htmlConv.Save(savePath))
          throw new Exception(pdfix.GetError());

        htmlConv.Destroy();
        doc.Close();
        pdfix.Destroy();
      }
}