////////////////////////////////////////////////////////////////////////////////////////////////////
// AddWatermark.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to add a watermark.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

public class AddWatermark {
    public static void run (
      String openPath,
      String savePath,
      String imgPath,
      PdfWatermarkParams watermarkParams
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
        
        watermarkParams.h_value = .5;
        watermarkParams.v_value = .5;
        watermarkParams.percentage_vals = 1;
        watermarkParams.scale = 1.5;
        watermarkParams.opacity = .5;
        watermarkParams.order_top = 1;
        
        if (!doc.AddWatermarkFromImage(watermarkParams, imgObj))
          throw new Exception(pdfix.GetError());
        
        if (!doc.Save(savePath, Pdfix.kSaveFull))
          throw new Exception(pdfix.GetError());
        
        doc.Close();
        pdfix.Destroy();
    }
}