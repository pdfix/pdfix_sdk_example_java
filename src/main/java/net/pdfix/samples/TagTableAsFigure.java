////////////////////////////////////////////////////////////////////////////////////////////////////
// TagAsFigure.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to manually change table structure element to a figure using initial elements.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

public class TagTableAsFigure {

    static Pdfix pdfix = null;

    // GetParagraphBBox
    // get the text state of the text objects inside paragraph by iterating content kid objects
    private static PdfRect GetStructElementBBox(PdsStructElement struct_elem) {
        PdfRect bbox = null;
        for (int i = 0; i < struct_elem.GetNumKids(); i++) {
            if (struct_elem.GetKidType(i) == PdfStructElementType.kPdsStructKidPageContent) {
                // acquire page on which the element is present
                PdfDoc doc = struct_elem.GetStructTree().GetDoc();
                PdfPage page = doc.AcquirePage(struct_elem.GetKidPageNumber(i));
                // find text object with mcid on the page to get the text state
                int mcid = struct_elem.GetKidMcid(i);
                PdsContent content = page.GetContent();
                for (int j = 0; j < content.GetNumObjects(); j++) {
                    PdsPageObject page_object = content.GetObject(j);
                    // check if this text page object has the same mcid
                    PdsContentMark content_mark = page_object.GetContentMark();
                    if (content_mark != null && content_mark.GetTagMcid() == mcid) {
                        PdfRect elem_bbox = page_object.GetBBox();
                        if (bbox == null) {
                            bbox = elem_bbox;
                        } else {
                            bbox.left = Math.min(bbox.left, elem_bbox.left);
                            bbox.right = Math.max(bbox.right, elem_bbox.right);
                            bbox.top = Math.max(bbox.top, elem_bbox.top);
                            bbox.bottom = Math.min(bbox.bottom, elem_bbox.bottom);
                        }
                    }
                }
            } else if (struct_elem.GetKidType(i) == PdfStructElementType.kPdsStructKidElement) {
                PdsObject kid_obj = struct_elem.GetKidObject(i);
                PdsStructElement kid_elem = struct_elem.GetStructTree().AcquireStructElement(kid_obj);
                PdfRect kid_bbox = GetStructElementBBox(kid_elem);
                if (kid_bbox != null) {
                    if (bbox == null) {
                        bbox = new PdfRect();
                    }
                    bbox.left = Math.min(bbox.left, kid_bbox.left);
                    bbox.right = Math.max(bbox.right, kid_bbox.right);
                    bbox.top = Math.max(bbox.top, kid_bbox.top);
                    bbox.bottom = Math.min(bbox.bottom, kid_bbox.bottom);
                }
            }
        }
        return bbox;
    }

    // GetFirstTable
    // get reference to the first table on the page
    private static PdsStructElement GetFirstTable(PdsStructElement struct_elem) throws Exception {
        // search kid struct elements
        for (int i = 0; i < struct_elem.GetNumKids(); i++) {
            if (struct_elem.GetKidType(i) == PdfStructElementType.kPdsStructKidElement) {
                PdsObject kid_obj = struct_elem.GetKidObject(i);
                PdsStructElement kid_elem = struct_elem.GetStructTree().AcquireStructElement(kid_obj);
                if (kid_elem == null) {
                    throw new Exception(Integer.toString(pdfix.GetErrorType()));
                }
                String type = kid_elem.GetType(true);
                if (type.compareTo("Table") == 0) {
                    return kid_elem;
                }
                PdsStructElement table = GetFirstTable(kid_elem);
                if (table != null) {
                    kid_elem.Release();
                    return table;
                }
                kid_elem.Release();
            }
        }
        return null;
    }

    private static PdsStructElement GetFirstTable(PdsStructTree struct_tree) throws Exception {
        for (int i = 0; i < struct_tree.GetNumKids(); i++) {
            PdsObject kid_obj = struct_tree.GetKidObject(i);
            PdsStructElement kid_elem = struct_tree.AcquireStructElement(kid_obj);
            PdsStructElement paragraph = GetFirstTable(kid_elem);
            if (paragraph != null) {
                kid_elem.Release();
                return paragraph;
            }
            kid_elem.Release();
        }
        return null;
    }

    public static void Run(
            String openPath, // source PDF document
            String savePath // dest PDF document
    ) throws Exception {
        pdfix = new Pdfix();
        if (isNull(pdfix)) 
            throw new Exception("Pdfix initialization fail");
        
        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null) {
            throw new Exception(Integer.toString(pdfix.GetErrorType()));
        }
        // cleanup any previous structure tree
        if (!doc.RemoveTags()) {
            throw new Exception(Integer.toString(pdfix.GetErrorType()));
        }
        // autotag document first
        if (!doc.AddTags()) {
            throw new Exception(Integer.toString(pdfix.GetErrorType()));
        }
        // get the struct tree
        PdsStructTree struct_tree = doc.GetStructTree();
        if (struct_tree == null) {
            throw new Exception(Integer.toString(pdfix.GetErrorType()));
        }
        PdsStructElement table = GetFirstTable(struct_tree);
        if (table == null) {
            throw new Exception("No table found.");
        }
        PdfRect bbox = GetStructElementBBox(table);
        // remove all items from the table to make it untagged cotnent
        for (int i = table.GetNumKids() - 1; i >= 0; i--) {
            table.RemoveKid(i);
        }
        // tag page
        PdfPage page = doc.AcquirePage(0);
        PdePageMap page_map = page.CreatePageMap();
        PdeElement elem = page_map.CreateElement(PdfElementType.kPdeImage, null);
        elem.SetBBox(bbox);
        elem.SetAlt("This is image caption");
        // prepare document template to ignore already tagged content
        PdfDocTemplate doc_preflight = doc.GetTemplate();
        doc_preflight.SetProperty("ignore_tags", 1);
        // re-tag non-tagged page content
        if (!page_map.CreateElements()) {
            throw new Exception(Integer.toString(pdfix.GetErrorType()));
        }
        if (!page_map.AddTags(table)) {
            throw new Exception(Integer.toString(pdfix.GetErrorType()));
        }
        // udpate the table element type
        if (!table.SetType("Sect")) {
            throw new Exception(Integer.toString(pdfix.GetErrorType()));
        }
        if (!doc.Save(savePath, Pdfix.kSaveFull)) {
            throw new Exception(Integer.toString(pdfix.GetErrorType()));
        }
        doc.Close();
        pdfix.Destroy();
    }
}
