package net.pdfix;

import net.pdfix.samples.*;

import java.nio.file.Files;
import java.nio.file.Paths;

import net.pdfix.pdfixlib.*;

public class App 
{
    public static void main(String[] args) throws Exception {
        String basePath = System.getProperty("user.dir"); // path to current folder
        String pdfixPath = basePath + "/pdfix/bin";       // path to pdfix bin folder

        for (int i = 0; i < args.length; i++) {
                if (args[i].equals("--pdfix-bin") && i + 1 < args.length)
                        pdfixPath = args[i + 1]; // update path to pdfix binaries
        }
        
        System.out.println("pdfixPath: " + pdfixPath);
        
        String inputPath = basePath + "/resources";
        String outputPath = basePath + "/output";
        String configPath = "";

        if (!Files.exists(Paths.get(outputPath)))
            Files.createDirectory(Paths.get(outputPath));

        Initialization.run(pdfixPath);
        
        System.out.println("GetBookmarks");
        GetBookmarks.run(inputPath + "/test.pdf");

        System.out.println("ParsePdsObjects");
        ParsePdsObjects.run(inputPath + "/test.pdf");

        System.out.println("RenderPage");
        RenderPage.run(inputPath + "/test.pdf",
                outputPath + "/RenderPage.jpg",
                2.0f);

        ////////////////////////////////////////////////////
        // Basic, Professional, Enterprise version
        System.out.println("AddComment");
        AddComment.run(inputPath + "/test.pdf",
                outputPath + "/AddComment.pdf");

        System.out.println("AddTags");
        AddTags.run(inputPath + "/test.pdf",
                outputPath + "/AddTags.pdf", configPath);

        System.out.println("AddWatermark");
        AddWatermark.run(inputPath + "/test.pdf",
                outputPath + "/AddWatermark.pdf",
                inputPath + "/watermark.png");

        System.out.println("ConvertToHtml");
        ConvertToHtml.run(inputPath + "/test.pdf",
                outputPath + "/index.html", configPath,
                new PdfHtmlParams());
                    
        System.out.println("ConvertToHtmlByPages");
        ConvertToHtmlByPages.run(inputPath + "/test.pdf");

        System.out.println("DigitalSignature");
        DigitalSignature.run(inputPath + "/test.pdf",
                outputPath + "/DigitalSignature.pdf",
                inputPath + "/test.pfx", "TEST_PASSWORD");

        System.out.println("DocumentMetadata");
        DocumentMetadata.run(inputPath + "/test.pdf",
                outputPath + "/DocumentMetadata.pdf");

        System.out.println("EmbedFonts");
        EmbedFonts.run(inputPath + "/test.pdf",
                outputPath + "/EmbedFonts.pdf");

        System.out.println("ExportFormFieldValues");
        ExportFormFieldValues.run(inputPath + "/test.pdf");

        System.out.println("ExtractImages");
        ExtractImages.run(inputPath + "/test.pdf",
                outputPath + "/",
                800,
                new PdfImageParams());

        System.out.println("ExtractTables");
        ExtractTables.run(inputPath + "/test.pdf",
                outputPath + "/",
                configPath);

        System.out.println("ExtractText");
        ExtractText.run(inputPath + "/test.pdf",
                outputPath + "/ExtractText.txt",
                configPath);

        System.out.println("FlattenAnnots");
        FlattenAnnots.run(inputPath + "/test.pdf",
                outputPath + "/FlattenAnnots.pdf");

        System.out.println("GetWhitespace");
        GetWhitespace.run(inputPath + "/test.pdf");

        System.out.println("MakeAccessible");
        MakeAccessible.run(inputPath + "/test.pdf",
                outputPath + "/MakeAccessible.pdf",
                "en-us",
                "",
                configPath);

//            System.out.println("OcrWithTesseract");
//            OcrWithTesseract.run(inputPath + "/smoke_test/AutoTag_Sample.pdf",
//                    outputPath + "/OcrWithTesseract.pdf",
//                    Utils.getAbsolutePath("../libs/tesseract/tessdata"),
//                    "eng");

        //System.out.println("PrintPage");
//                PrintPage.run(openPath);
        System.out.println("RegexSearch");
        RegexSearch.run(inputPath + "/test.pdf", 
                "(\\d{4}[- ]){3}\\d{4}");

        System.out.println("AddComment");
        RegexSetPattern.run(inputPath + "/test.pdf");

//                System.out.println("RegisterEvent");
//                RegisterEvent(email, key, open_path);
        System.out.println("RemoveComments");
        RemoveComments.run(inputPath + "/test.pdf",
                outputPath + "/RemoveComments.pdf");

        System.out.println("SetFieldFlags");
        SetFieldFlags.run(inputPath + "/test.pdf",
                outputPath + "/SetFieldFlags.pdf");

        System.out.println("SetFormFieldValue");
        SetFormFieldValue.run(inputPath + "/test.pdf",
                outputPath + "/SetFormFieldValue.pdf");

        System.out.println("ThreadSafePdfix");
        ThreadSafePdfix.run(inputPath + "/test.pdf");

        // tags samples
        System.out.println("TagsEditStructTree");
        TagsEditStructTree.run(inputPath + "/test.pdf",
                outputPath + "/TagsEditStructTree.pdf");

        System.out.println("TagsEditStructTree");
        TagsReadStructureTree.run(inputPath + "/test.pdf",
                outputPath + "/TagsEditStructTree.pdf");
        
        // System.out.println("TagTableAsFigure");
        // TagTableAsFigure.Run(inputPath + "/test.pdf",
        //         outputPath + "/TagTableAsFigure.pdf");

        System.out.println("SUCCESS");        
    }
}
