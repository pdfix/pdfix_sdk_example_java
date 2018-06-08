////////////////////////////////////////////////////////////////////////////////////////////////////
// PDFixSDKSample.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

package net.pdfix;

import net.pdfix.ocrtesseract.OcrTesseractParams;
import net.pdfix.pdfixlib.PdfFlattenAnnotsParams;
import net.pdfix.pdfixlib.PdfImageParams;
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

            AddComment.run(email, licenseKey, openPath, 
              Utils.getAbsolutePath("output/AddComment.pdf"));

            AddTags.run(email, licenseKey, openPath, 
              Utils.getAbsolutePath("output/AddTags.pdf"), 
              configPath);

            AddWatermark.run(email, licenseKey, openPath, 
              Utils.getAbsolutePath("output/AddWatermark.pdf"), 
              Utils.getAbsolutePath("resources/watermark.png"), 
              new PdfWatermarkParams());

            ConvertToHtml.run(email, licenseKey, openPath, 
              Utils.getAbsolutePath("output/index.html"), configPath, 
              new PdfHtmlParams());

            DigitalSignature.run(email, licenseKey, openPath, 
              Utils.getAbsolutePath("output/DigitalSignature.pdf"),
              Utils.getAbsolutePath("resources/test.pfx"), 
              "TEST_PASSWORD");

            DocumentMetadata.run(email, licenseKey, openPath, 
              Utils.getAbsolutePath("output/DocumentMetadata.pdf"),
              Utils.getAbsolutePath("output/metadata.xml"));

            EmbedFonts.run(email, licenseKey, openPath, 
              Utils.getAbsolutePath("output/EmbedFonts.pdf"));

            ExportFormFieldValues.run(email, licenseKey, openPath, 
              Utils.getAbsolutePath("output/ExportFormFieldValues.txt"));

            ExtractImages.run(email, licenseKey, openPath,
              Utils.getAbsolutePath("output/ExtractImages/"),
              800,
              new PdfImageParams());

            ExtractTables.run(email, licenseKey, openPath, 
              Utils.getAbsolutePath("output/ExtractTables/"),
              configPath);

            ExtractText.run(email, licenseKey, openPath, 
              Utils.getAbsolutePath("output/ExportFormFieldValues.txt"), 
              configPath);

            FlattenAnnots.run(email, licenseKey, openPath, 
              Utils.getAbsolutePath("output/FlattenAnnots.pdf"), 
              new PdfFlattenAnnotsParams());

            GetBookmarks.run(email, licenseKey, openPath);

            GetWhitespace.run(email, licenseKey, openPath);

            MakeAccessible.run(email, licenseKey, openPath, 
              Utils.getAbsolutePath("output/MakeAccessible.pdf"), 
              "en-us", 
              "", 
              configPath);

            OcrWithTesseract.run(email, licenseKey, openPath, 
              Utils.getAbsolutePath("output/OcrWithTesseract.pdf"), 
              Utils.getAbsolutePath("resources/"), 
              "eng", 
              new OcrTesseractParams());

            RegexSearch.run(email, licenseKey, openPath, "(\\d{4}[- ]){3}\\d{4}");

            RegexSetPattern.run(email, licenseKey, openPath);

            RemoveComments.run(email, licenseKey, openPath, 
              Utils.getAbsolutePath("output/RemoveComments.pdf"));

            RenderPage.run(email, licenseKey, openPath, 
              Utils.getAbsolutePath("output/RenderPage.jpg"), 
              2.0);

            SetFieldFlags.run(email, licenseKey, openPath,
              Utils.getAbsolutePath("output/SetFieldFlags.pdf"));

            SetFormFieldValue.run(email, licenseKey, openPath, 
              Utils.getAbsolutePath("output/SetFormFieldValue.pdf"));
        }
            catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }  
}
