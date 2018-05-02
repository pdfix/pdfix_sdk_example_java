////////////////////////////////////////////////////////////////////////////////////////////////////
// AddWatermark.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

/*! 
\page JAVA_Samples Java Samples
- \subpage AddWatermark_java
*/
/*! 
\page AddWatermark_java Add Watermark Sample
// Example how to add a watermark.
\snippet /AddWatermark.java AddWatermark_java
*/

//\cond INTERNAL
//! [AddWatermark_java]
package net.pdfix.samples;
import net.pdfix.Utils;
import net.pdfix.pdfixlib.*;

public class AddWatermark {
    public static void run (
      String email,               
      String licenseKey,
      String openPath,
      String savePath,
      String imgPath,
      PdfWatermarkParams watermarkParams
    ) throws Exception {
        System.out.println("AddWatermark");
        
        System.load(Utils.getAbsolutePath(Utils.getModuleName("pdfix")));
        
        Pdfix pdfix = new Pdfix();
        if (pdfix == null)
          throw new Exception("Pdfix initialization fail");
        
        if (!pdfix.Authorize(email, licenseKey))
          throw new Exception(pdfix.GetError());

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
          throw new Exception(pdfix.GetError());
        
        watermarkParams.h_value = .5;
        watermarkParams.v_value = .5;
        watermarkParams.percentage_vals = 1;
        watermarkParams.scale = 1.5;
        watermarkParams.opacity = .5;
        watermarkParams.order_top = 1;
        
        if (!doc.AddWatermarkFromImage(watermarkParams, imgPath))
          throw new Exception(pdfix.GetError());
        
        if (!doc.Save(savePath, PdfSaveFlags.kSaveFull))
          throw new Exception(pdfix.GetError());
        
        doc.Close();
        pdfix.Destroy();
    }
}
//! [AddWatermark_java]
//\endcond