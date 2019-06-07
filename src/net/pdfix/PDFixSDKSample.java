////////////////////////////////////////////////////////////////////////////////////////////////////
// PDFixSDKSample.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

package net.pdfix;

import java.io.File;
import net.pdfix.samples.*;
import net.pdfix.pdfixlib.*;
import net.pdfix.pdftohtml.*;
import net.pdfix.ocrtesseract.*;

public class PDFixSDKSample {
    public static void main(String[] args) {
        try {
            String email = "YOUR_EMAIL";                                          // authorization email
            String licenseKey = "LICENSE_KEY";                                    // license key
            String openPath = Utils.getAbsolutePath("resources/test.pdf");        // source PDF document
            String configPath = Utils.getAbsolutePath("resources/config.json");   // configuration file
            
            // create output folder
            new File(Utils.getAbsolutePath("output")).mkdirs();

            Initialization.run(email, licenseKey);

            System.out.println("AddComment");
            AddComment.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/AddComment.pdf"));

            System.out.println("AddTags");
            AddTags.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/AddTags.pdf"),
                    configPath);

            System.out.println("AddWatermark");
            AddWatermark.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/AddWatermark.pdf"),
                    Utils.getAbsolutePath("resources/watermark.png"),
                    new PdfWatermarkParams());

            System.out.println("ConvertToHtml");
            ConvertToHtml.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/index.html"), configPath,
                    new PdfHtmlParams());

            System.out.println("DigitalSignature");
            DigitalSignature.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/DigitalSignature.pdf"),
                    Utils.getAbsolutePath("resources/test.pfx"),
                    "TEST_PASSWORD");

            System.out.println("DocumentMetadata");
            DocumentMetadata.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/DocumentMetadata.pdf"),
                    Utils.getAbsolutePath("output/metadata.xml"));

            System.out.println("EmbedFonts");
            EmbedFonts.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/EmbedFonts.pdf"));

            System.out.println("ExportFormFieldValues");
            ExportFormFieldValues.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/ExportFormFieldValues.txt"));

            System.out.println("ExtractImages");
            ExtractImages.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/"),
                    800,
                    new PdfImageParams());

            System.out.println("ExtractTables");
            ExtractTables.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/"),
                    configPath);

            System.out.println("ExtractText");
            ExtractText.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/ExportFormFieldValues.txt"),
                    configPath);

            System.out.println("FlattenAnnots");
            FlattenAnnots.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/FlattenAnnots.pdf"),
                    new PdfFlattenAnnotsParams());

            System.out.println("GetBookmarks");
            GetBookmarks.run(email, licenseKey, openPath);

            System.out.println("GetWhitespace");
            GetWhitespace.run(email, licenseKey, openPath);

            System.out.println("MakeAccessible");
            MakeAccessible.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/MakeAccessible.pdf"),
                    "en-us",
                    "",
                    configPath);

            System.out.println("OcrWithTesseract");
            OcrWithTesseract.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/OcrWithTesseract.pdf"),
                    Utils.getAbsolutePath("../libs/tesseract/tessdata"),
                    "eng");

            System.out.println("ParsePdsObjects");
            ParsePdsObjects.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/ParsePdsObjects.txt"));

            //System.out.println("PrintPage");
//                PrintPage.run(email, licenseKey, openPath);
            System.out.println("RegexSearch");
            RegexSearch.run(email, licenseKey, openPath, "(\\d{4}[- ]){3}\\d{4}");

            System.out.println("AddComment");
            RegexSetPattern.run(email, licenseKey, openPath);

//                System.out.println("RegisterEvent");
//                RegisterEvent(email, key, open_path);
            System.out.println("RemoveComments");
            RemoveComments.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/RemoveComments.pdf"));

            System.out.println("RenderPage");
            RenderPage.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/RenderPage.jpg"),
                    2.0);

            System.out.println("SetFieldFlags");
            SetFieldFlags.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/SetFieldFlags.pdf"));

            System.out.println("SetFormFieldValue");
            SetFormFieldValue.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/SetFormFieldValue.pdf"));

            System.out.println("ThreadSafePdfix");
            ThreadSafePdfix.run(email, licenseKey, openPath);

            // tags samples
            System.out.println("TagsEditStructTree");
            TagsEditStructTree.run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/TagsEditStructTree.pdf"));

            System.out.println("TagTableAsFigure");
            TagTableAsFigure.Run(email, licenseKey, openPath,
                    Utils.getAbsolutePath("output/TagTableAsFigure.pdf"));
            
            System.out.println("SUCCESS");
        }
            catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }  
}
