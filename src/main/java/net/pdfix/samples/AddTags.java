////////////////////////////////////////////////////////////////////////////////////////////////////
// AddTags.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to automatically add tags into the document.
package net.pdfix.samples;

import net.pdfix.pdfixlib.*;

public class AddTags {
  public static void run(
      String openPath, // source PDF document
      String savePath, // output HTML file
      String configPath // configuration file
  ) throws Exception {
    Pdfix pdfix = new Pdfix();
    PdfDoc doc = pdfix.OpenDoc(openPath, "");
    if (doc == null)
      throw new Exception(pdfix.GetError());

    // Clear document title. Document title will be set when taggging with value of
    // H1 heading
    if (!doc.SetInfo("Title", "")) {
      throw new Exception(pdfix.GetError());
    }

    // customize auto-tagging by loading a template configuration JSON
    PsFileStream stm = pdfix.CreateFileStream(configPath, PsFileMode.kPsReadOnly);
    if (stm != null) {
      PdfDocTemplate doc_preflight = doc.GetTemplate();
      if (doc_preflight == null)
        throw new Exception(pdfix.GetError());
      if (!doc_preflight.LoadFromStream(stm, PsDataFormat.kDataFormatJson))
        throw new Exception(pdfix.GetError());
      stm.Destroy();
    }

    // Autotag PDF with the loaded template
    if (!doc.AddTags(new PdfTagsParams()))
      throw new Exception(pdfix.GetError());

    // OPTIONAL: Merge tables spanned across multiple pages into sing Table 

    // Set document language
    if (!doc.SetLang("de"))
      throw new Exception(pdfix.GetError());

    // create bookmarks from headings
    if (!doc.CreateBookmarks()) {
      throw new Exception(pdfix.GetError());
    }

    // set PDF/UA comliance standard
    if (!doc.SetPdfStandard(Pdfix.kPdfStandardPdfUA, "1")) {
      throw new Exception(pdfix.GetError());
    }

    // set display fot titile preferences
    PdsDictionary root = doc.GetRootObject();
    PdsDictionary vp = root.GetDictionary("ViewerPreferences");
    if (vp == null) {
      vp = root.PutDict("ViewerPreferences");
    }
    vp.PutBool("DisplayDocTitle", true);

    if (!doc.Save(savePath, Pdfix.kSaveFull | Pdfix.kSaveCompressedStructureOnly))
      throw new Exception(pdfix.GetError());

    doc.Close();
    pdfix.Destroy();
  }
}
