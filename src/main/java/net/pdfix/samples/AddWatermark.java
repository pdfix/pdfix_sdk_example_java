////////////////////////////////////////////////////////////////////////////////////////////////////
// AddWatermark.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to add a watermark.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

import javafx.scene.transform.MatrixType;

public class AddWatermark {
    public static void run (
      String openPath,
      String savePath,
      String imgPath
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        if (isNull(pdfix))
          throw new Exception("Pdfix initialization fail");
        
        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
          throw new Exception(pdfix.GetError());
        
        PsStream imgStm = pdfix.CreateFileStream(imgPath, PsFileMode.kPsReadOnly);
        if (imgStm == null)
            throw new Exception(pdfix.GetError());
        
        PdfImageFormat format = PdfImageFormat.kImageFormatJpg;
        if (imgPath.substring(imgPath.lastIndexOf(".")+1) == "png")
            format = PdfImageFormat.kImageFormatPng;
        PdsStream imgObj = doc.CreateXObjectFromImage(imgStm, format);
        

        PdsStream image_obj = doc.CreateXObjectFromImage(imgStm, format);
        if (image_obj == null)
            throw new Exception(pdfix.GetError());

        PdfPage page = doc.AcquirePage(0);
        if (page == null)
            throw new Exception(pdfix.GetError());

        PdsContent content = page.GetContent();
        if (content == null)
            throw new Exception(pdfix.GetError());

        PdsDictionary xobjdict = image_obj.GetStreamDict();
        float scale = 0.5f;
        float width = xobjdict.GetNumber("Width") * scale;
        float height = xobjdict.GetNumber("Height") * scale;
        float h_value = 20.0f;
        float v_value = 50.0f;

        //z order represented by the position in the content, -1 or last add to the top, 0 to the background
        PdfMatrix matrix = new PdfMatrix();
        matrix.a = width;
        matrix.d = height;
        matrix.e = h_value;
        matrix.f = v_value;
        PdsImage imageobject = content.AddNewImage(-1, image_obj, matrix);

        //set opacity of the image 0-255
        PdfGraphicState graphicState = imageobject.GetGState();
        graphicState.color_state.fill_opacity = 200;
        imageobject.SetGState(graphicState);

        page.SetContent();

        page.Release();
        
        if (!doc.Save(savePath, Pdfix.kSaveFull))
          throw new Exception(pdfix.GetError());
        
        doc.Close();
        pdfix.Destroy();
    }
}