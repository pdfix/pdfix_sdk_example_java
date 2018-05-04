////////////////////////////////////////////////////////////////////////////////////////////////////
// OcrWithTesseract.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*!
\page JAVA_Samples Java Samples
- \subpage OcrWithTesseract_java
*/
/*!
\page OcrWithTesseract_java Ocr With Tesseract Sample
Example how to convert an image based PDF to searchable document.
NOTE: If your tessdata dir is in the /usr/share/tesseract-ocr dir, data_path should be set to /usr/share/tesseract-ocr.
\snippet /OcrWithTesseract.java OcrWithTesseract_java
*/

//! [OcrWithTesseract_java]
package net.pdfix.samples;

import net.pdfix.Utils;
import net.pdfix.ocrtesseract.*;
import net.pdfix.pdfixlib.*;

public class OcrWithTesseract {
    public static void run(      
      String email,                         // authorization email   
      String licenseKey,                    // authorization license key
      String openPath,                      // source PDF document
      String savePath,                      // output PDF document
      String dataPath,                      // path to OCR data
      String language,                      // default OCR language
      OcrTesseractParams ocrParams          // OCR params    
    ) throws Exception {        
        System.out.println("OcrWithTesseract");
    
        System.load(Utils.getAbsolutePath(Utils.getModuleName("pdfix")));
        System.load(Utils.getAbsolutePath(Utils.getModuleName("ocr_tesseract")));

        Pdfix pdfix = new Pdfix();
        if (pdfix == null)
            throw new Exception("Pdfix initialization fail");

        if (!pdfix.Authorize(email, licenseKey))
            throw new Exception(pdfix.GetError());

        OcrTesseract ocr = new OcrTesseract();
        if (ocr == null)
            throw new Exception("OcrTesseract initialization fail");

        System.out.println( ocr.GetVersionMajor() + "." +  
          ocr.GetVersionMinor() + "." + ocr.GetVersionPatch());

        if (!ocr.Initialize(pdfix))
            throw new Exception(pdfix.GetError());

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
    
        ocr.SetLanguage(language);
        ocr.SetData(dataPath);
    
        TesseractDoc ocrDoc = ocr.OpenOcrDoc(doc);
        if (ocrDoc == null)
            throw new Exception(pdfix.GetError());
    
        if (!ocrDoc.Save(savePath, ocrParams))
            throw new Exception(pdfix.GetError());
    
        ocrDoc.Close();
        doc.Close();       
        pdfix.Destroy();
    }
}
//! [OcrWithTesseract_java]