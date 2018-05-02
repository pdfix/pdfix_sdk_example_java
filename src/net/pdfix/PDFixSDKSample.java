////////////////////////////////////////////////////////////////////////////////////////////////////
// PDFixSDKSample.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

package net.pdfix;

import net.pdfix.ocrtesseract.OcrTesseractParams;
import net.pdfix.pdfixlib.PdfWatermarkParams;
import net.pdfix.pdftohtml.PdfHtmlParams;
import net.pdfix.samples.*;

public class PDFixSDKSample {
  public static void main(String[] args) {
    try {
      String email = "YOUR_EMAIL";                                          // authorization email
      String licenseKey = "LICENSE_KEY";                                    // license key
      String openPath = Utils.getAbsolutePath("resources/test.pdf");        // source PDF document
      String configPath = Utils.getAbsolutePath("resources/config.json");   // configuration file
      
      Initialization.run(email, licenseKey);
      
      AddComment.run(email, licenseKey, openPath, Utils.getAbsolutePath("output/AddComment.pdf"));      
      AddTags.run(email, licenseKey, openPath, Utils.getAbsolutePath("output/AddTags.pdf"), 
        configPath);      
      AddWatermark.run(email, licenseKey, openPath, 
        Utils.getAbsolutePath("output/AddWatermark.pdf"),
        Utils.getAbsolutePath("resources/watermark.png"), 
        new PdfWatermarkParams());
      
      ConvertToHtml.run(email, licenseKey, openPath, Utils.getAbsolutePath("output/index.html"), 
        configPath, new PdfHtmlParams());
      
      OcrWithTesseract.run(email, licenseKey, openPath, 
        Utils.getAbsolutePath("output/OcrWithTesseract.pdf"), 
        Utils.getAbsolutePath("resources/tesseract"), "eng", new OcrTesseractParams());
      
      SetFormFieldValue.run(email, licenseKey, openPath, 
        Utils.getAbsolutePath("output/SetFormFieldValue.pdf"));      
    }
    catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }  
}
