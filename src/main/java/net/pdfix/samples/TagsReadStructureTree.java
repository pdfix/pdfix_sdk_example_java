////////////////////////////////////////////////////////////////////////////////////////////////////
// TagsReadStructureTree.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

public class TagsReadStructureTree {
  public static void run(
    String openPath,
    String savePath
  ) throws Exception {
    Pdfix pdfix = new Pdfix();
    if (isNull(pdfix))
      throw new Exception("Pdfix initialization fail");

    PdfDoc doc = pdfix.OpenDoc(openPath, "");
    if (doc == null) {
      throw new Exception(pdfix.GetError());
    }

    PdsStructTree structTree = doc.GetStructTree();
    if (structTree != null) {
      for (int i = 0; i < structTree.GetNumChildren(); i++) {
        PdsObject childDict = structTree.GetChildObject(i);
        parseStructElement(structTree.GetStructElementFromObject(childDict));
      }
    }

    doc.Close();
  }

  public static void parseStructElement(PdsStructElement structElem) throws Exception {    
    String type = structElem.GetType(false);
    String alt = structElem.GetAlt();
    if (alt.isEmpty() && type == "Figure") {
      // update the alternate text of the image
      if (false == structElem.SetAlt("Image")) {
        throw new Exception("Error setting the image alt text.");
      }
    }

    PdsStructTree structTree = structElem.GetStructTree();

    // process children
    for (int i = 0; i < structElem.GetNumChildren(); i++) {
      switch (structElem.GetChildType(i)) {
        case kPdsStructChildElement: {
          parseStructElement(structTree.GetStructElementFromObject(structElem.GetChildObject(i)));
        }
          break;
        case kPdsStructChildPageContent:
        case kPdsStructChildStreamContent:
          // handle mcid content
          break;
        case kPdsStructChildObject:
          // handle struct object 
          break;
        default:
          ;
      }
    }
  }

}
