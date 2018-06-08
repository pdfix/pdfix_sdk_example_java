////////////////////////////////////////////////////////////////////////////////////////////////////
// ExtractTables.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*! 
\page JAVA_Samples Java Samples
- \subpage ExtractTables_java
*/
/*! 
\page ExtractTables_java Export Extract Tables Sample
// Example how to extract tables from PDF.
\snippet /ExtractTables.java ExtractTables_java
*/

//! [ExtractTables_java]
package net.pdfix.samples;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class ExtractTables {
    private static int tableIndex = 0;
    private static void GetText (PdeElement element, StringBuilder ss,
            boolean eof) {
        PdfElementType elemType = element.GetType();
        if (PdfElementType.kPdeText == elemType) {
            PdeText textElem = (PdeText)element;
            String str = textElem.GetText();
            ss.append(str);
            if (eof) 
                ss.append("\n");
            else {
                int count = element.GetNumChildren();
                if (count == 0) 
                    return;
                for (int i = 0; i < count; i = i + 1) {
                    PdeElement child = element.GetChild(i);
                    if (child != null) 
                        GetText(child, ss, eof);
                }
            }
        }
    }
    
    private static void SaveTable(
      PdeElement element,
      String savePath
    ) throws Exception {
        PdfElementType elemType = element.GetType();
        if (elemType == PdfElementType.kPdeTable) {
            PdeTable table = (PdeTable)element;

            StringBuilder ofs = new StringBuilder();
            String path = savePath + "/ExtractTables_" + Integer.toString (tableIndex++) + ".csv";

            int rowCount = table.GetNumRows();
            int colCount = table.GetNumCols();

            for (int row = 0; row < rowCount; row++) {
                for (int col = 0; col < colCount; col++) {
                    PdeCell cell = (PdeCell)table.GetCell(row, col);
                    if (cell == null) 
                        continue;
                    int rowSpan = cell.GetRowSpan();
                    int colSpan = cell.GetColSpan();

                    int count = cell.GetNumChildren();
                    if ((rowSpan != 0) && (colSpan != 0) && (count > 0)) {
                        ofs.append("\"");
                        for (int i = 0; i < count; i++) {
                            PdeElement child = cell.GetChild(i);
                            if (child.GetType() == PdfElementType.kPdeText) {
                                GetText(child, ofs, false);
                            }
                            if (i < count)
                                ofs.append(" ");
                        }
                        ofs.append("\"");
                    }
                if (col < colCount)
                    ofs.append("\n");
                }
                if (row < rowCount)
                    ofs.append("\n");
            }
            
            File file = new File(path);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(ofs.toString());
            }
        }
        else {
            int count = element.GetNumChildren();
            if (count == 0)
                return;
            for (int i = 0; i < count; i++) {
                PdeElement child = element.GetChild(i);
                if (child != null)
                    SaveTable(child, savePath);
            }
        }
    }
    
    public static void run(
        String email,                       // authorization email   
        String licenseKey,                  // license key
        String openPath,                    // source PDF document
        String savePath,                    // directory where to extract tables
        String configPath
    ) throws Exception {
        System.out.println("ExtractTables");

        System.load(Utils.getAbsolutePath(Utils.getModuleName("pdfix")));

        Pdfix pdfix = new Pdfix();
        if (pdfix == null)
            throw new Exception("Pdfix initialization fail");
        if (!pdfix.Authorize(email, licenseKey))
            throw new Exception(pdfix.GetError());

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        int tableIndex= 1;
        
        int numPages = doc.GetNumPages();
        for (int i = 0; i < numPages; i++) {
            System.out.println("Processing pages ..." + (i + 1) + "/" + numPages);
            
            PdfPage page = doc.AcquirePage(i);
            if (page == null)
                throw new Exception(pdfix.GetError());
            PdePageMap pageMap = page.AcquirePageMap();
            if (pageMap == null)
                throw new Exception(pdfix.GetError());
            
            PdeElement element = pageMap.GetElement();
            if (element == null)
                throw new Exception(pdfix.GetError());
            
            SaveTable(element, savePath);
            
            doc.ReleasePage(page);
        }
        System.out.println((tableIndex - 1) + " tables found");
        doc.Close();
        pdfix.Destroy();
    }
}
//! [ExtractTables_java]