package net.pdfix;

import net.pdfix.pdfixlib.PdfHtmlParams;
import net.pdfix.pdfixlib.PdfImageParams;
import net.pdfix.samples.*;

import java.nio.file.Files;
import java.nio.file.Paths;

public class App {

        static String testName = "";

        static boolean runTest(String name) {
                boolean run = (testName.isEmpty() || (testName.compareTo(name) == 0));
                if (run) {
                        System.out.println("Test: " + name);
                }
                return run;
        }

        public static void main(String[] args) throws Exception {

                String basePath = System.getProperty("user.dir"); // path to current folder

                for (int i = 0; i < args.length; i++) {
                        if (args[i].equals("--test") && i + 1 < args.length)
                                testName = args[i + 1]; // update test name
                }

                String inputPath = basePath + "/resources";
                String outputPath = basePath + "/output";
                String configPath = "";

                if (!Files.exists(Paths.get(outputPath)))
                        Files.createDirectory(Paths.get(outputPath));

                Initialization.run();

                if (runTest("GetBookmarks")) {
                        GetBookmarks.run(inputPath + "/test.pdf");
                }

                if (runTest("ParsePdsObjects")) {
                        ParsePdsObjects.run(inputPath + "/test.pdf");
                }

                if (runTest("RenderPage")) {
                        RenderPage.run(inputPath + "/test.pdf",
                                        outputPath + "/RenderPage.jpg",
                                        2.0f);
                }

                if (runTest("OpenDocFromStream")) {
                        OpenDocFromStream.run(inputPath + "/test.pdf");
                }
                ////////////////////////////////////////////////////
                // Basic, Professional, Enterprise version
                if (runTest("AddComment")) {
                        AddComment.run(inputPath + "/test.pdf",
                                        outputPath + "/AddComment.pdf");
                }

                if (runTest("AddTags")) {
                        System.out.println("AddTags");
                        AddTags.run(inputPath + "/test.pdf",
                                        outputPath + "/AddTags.pdf", configPath);
                }

                if (runTest("AddWatermark")) {
                        AddWatermark.run(inputPath + "/test.pdf",
                                        outputPath + "/AddWatermark.pdf",
                                        inputPath + "/watermark.png");
                }

                if (runTest("ConvertToHtml")) {
                        ConvertToHtml.run(inputPath + "/test.pdf",
                                        outputPath + "/index.html", configPath,
                                        new PdfHtmlParams());
                }

                if (runTest("ConvertToHtmlByPages")) {
                        ConvertToHtmlByPages.run(inputPath + "/test.pdf");
                }

                if (runTest("DigitalSignature")) {
                        DigitalSignature.run(inputPath + "/test.pdf",
                                        outputPath + "/DigitalSignature.pdf",
                                        inputPath + "/test.pfx", "TEST_PASSWORD");
                }

                if (runTest("DocumentMetadata")) {
                        DocumentMetadata.run(inputPath + "/test.pdf",
                                        outputPath + "/DocumentMetadata.pdf");
                }

                if (runTest("EmbedFonts")) {
                        EmbedFonts.run(inputPath + "/test.pdf",
                                        outputPath + "/EmbedFonts.pdf");
                }

                if (runTest("ExportFormFieldValues")) {
                        ExportFormFieldValues.run(inputPath + "/test.pdf");
                }

                if (runTest("ExtractImages")) {
                        ExtractImages.run(inputPath + "/test.pdf",
                                        outputPath + "/",
                                        800,
                                        new PdfImageParams());
                }

                if (runTest("ExtractTables")) {
                        ExtractTables.run(inputPath + "/test.pdf",
                                        outputPath + "/",
                                        configPath);
                }

                if (runTest("ExtractText")) {
                        ExtractText.run(inputPath + "/test.pdf",
                                        outputPath + "/ExtractText.txt",
                                        configPath);
                }

                if (runTest("ExtractEmbeddedFiles")) {
                        ExtractEmbeddedFiles.run(inputPath + "/test.pdf", outputPath + "/");
                }

                if (runTest("FlattenAnnots")) {
                        FlattenAnnots.run(inputPath + "/test.pdf",
                                        outputPath + "/FlattenAnnots.pdf");
                }

                if (runTest("GetWhitespace")) {
                        GetWhitespace.run(inputPath + "/test.pdf");
                }

                if (runTest("MakeAccessible")) {
                        MakeAccessible.run(inputPath + "/test.pdf",
                                        outputPath + "/MakeAccessible.pdf",
                                        basePath + "/resources/make-accessible.json");
                }

                // if (runTest("OcrWithTesseract")) {
                // OcrWithTesseract.run(inputPath + "/scanned.pdf",
                // outputPath + "/OcrWithTesseract.pdf",
                // inputPath + ("/tessdata"),
                // "eng", 2.0F, PdfRotate.kRotate0);
                // }

                if (runTest("RegexSearch")) {
                        RegexSearch.run(inputPath + "/test.pdf",
                                        "(\\d{4}[- ]){3}\\d{4}");
                }

                if (runTest("ParsePdsObjects")) {
                        RegexSetPattern.run(inputPath + "/test.pdf");
                }

                if (runTest("RemoveComments")) {
                        RemoveComments.run(inputPath + "/test.pdf",
                                        outputPath + "/RemoveComments.pdf");
                }

                if (runTest("SetFieldFlags")) {
                        SetFieldFlags.run(inputPath + "/test.pdf",
                                        outputPath + "/SetFieldFlags.pdf");
                }

                if (runTest("SetFormFieldValue")) {
                        SetFormFieldValue.run(inputPath + "/test.pdf",
                                        outputPath + "/SetFormFieldValue.pdf");
                }

                if (runTest("ThreadSafePdfix")) {
                        ThreadSafePdfix.run(inputPath + "/test.pdf");
                }

                // tags samples
                if (runTest("TagsEditStructTree")) {
                        TagsEditStructTree.run(inputPath + "/test.pdf",
                                        outputPath + "/TagsEditStructTree.pdf");
                }

                if (runTest("TagsReadStructureTree")) {
                        TagsReadStructureTree.run(inputPath + "/test.pdf",
                                        outputPath + "/TagsEditStructTree.pdf");
                }

                System.out.println("SUCCESS");
        }
}
