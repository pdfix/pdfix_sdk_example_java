////////////////////////////////////////////////////////////////////////////////////////////////////
// ExtractTables.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to extract tables from PDF.

package net.pdfix.samples;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

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
        String openPath,                    // source PDF document
        String savePath,                    // directory where to extract tables
        String configPath
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        if (isNull(pdfix))
            throw new Exception("Pdfix initialization fail");

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        int numPages = doc.GetNumPages();
        for (int i = 0; i < numPages; i++) {
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
            
            page.Release();
        }
        doc.Close();
        pdfix.Destroy();
    }
}