////////////////////////////////////////////////////////////////////////////////////////////////////
// OcrWithTesseract.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to convert an image based PDF to searchable document.
package net.pdfix.samples;

import net.pdfix.ocrtesseract.*;
import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

public class OcrWithTesseract {
    public static void run(      
      String openPath,                      // source PDF document
      String savePath,                      // output PDF document
      String dataPath,                      // path to OCR data
      String language                       // default OCR language
    ) throws Exception {   
        Pdfix pdfix = new Pdfix();
        if (isNull(pdfix))
            throw new Exception("Pdfix initialization fail");

        OcrTesseract ocr = new OcrTesseract();
        if (isNull(ocr))
            throw new Exception("OcrTesseract initialization fail");

        if (!ocr.Initialize(pdfix))
            throw new Exception(pdfix.GetError());

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());

        ocr.SetLanguage(language);
        ocr.SetDataPath(dataPath);

        TesseractDoc ocrDoc = ocr.OpenOcrDoc(doc);
        if (ocrDoc == null)
            throw new Exception(pdfix.GetError());
        
//        if (!ocrDoc.Save(savePath, ocrParams))
//            throw new Exception(pdfix.GetError());

        ocrDoc.Close();
        doc.Close();
        pdfix.Destroy();
    }
}
