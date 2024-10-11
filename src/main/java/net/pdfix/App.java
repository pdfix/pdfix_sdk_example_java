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

                if (!Files.exists(Paths.get(outputPath)))
                        Files.createDirectory(Paths.get(outputPath));

                AddTags.run( inputPath + "/db/source1.pdf",
                                inputPath + "/db/source1_tagged.pdf", 
                                inputPath + "/db/template2.json");

                AddTags.run( inputPath + "/db/source2.pdf",
                                inputPath + "/db/source2_tagged.pdf", 
                                inputPath + "/db/template2.json");

                // MakeAccessible.run( inputPath + "/db/source1.pdf",
                //                 inputPath + "/db/source1_command_tagged.pdf", 
                //                 inputPath + "/db/command.json");

                // MakeAccessible.run( inputPath + "/db/source2.pdf",
                //                 inputPath + "/db/source2_command_tagged.pdf", 
                //                 inputPath + "/db/command.json");

                System.out.println("SUCCESS");
        }
}
