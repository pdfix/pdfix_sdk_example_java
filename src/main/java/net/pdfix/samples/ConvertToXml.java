////////////////////////////////////////////////////////////////////////////////////////////////////
// ConvertToXml.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to convert whole PDF document to XML.

package net.pdfix.samples;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConvertToXml {
    public static void ElementToXml(PdeElement elem, Document xml, Element node) {
        
    }
    
    public static void PageToXml(PdfPage page, Document xml, Element node) {
        // export some basic page information
        PdfRect cropBox = page.GetCropBox();
        node.setAttribute("width", Double.toString(cropBox.right - cropBox.left));
        node.setAttribute("height", Double.toString(cropBox.top - cropBox.bottom));        
        node.setAttribute("rotation", Integer.toString(page.GetRotate().getValue()));
    }
    
    
    public static void run (
      String openPath,
      String savePath
    ) throws Exception {      
        Pdfix pdfix = new Pdfix();
        if (isNull(pdfix))
            throw new Exception("Pdfix initialization fail");

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document xml = docBuilder.newDocument();
        Element root = xml.createElement("Document");
        
        // save metadata
        PsMetadata meta = doc.GetMetadata();
        PsStream metaStm = pdfix.CreateMemStream();        
        meta.SaveToStream(metaStm);
        byte[] metadata = new byte[metaStm.GetSize()];
        metaStm.Read(0, metadata);
        
        Element metaNode = xml.createElement("metadata");       
        metaNode.setTextContent(metadata.toString());
        root.appendChild(metaNode);
        metaStm.Destroy();
        
        
        // save content by pages
        for (int i = 0; i < doc.GetNumPages(); i++) {
           Element pageNode = xml.createElement("page");
           PdfPage page = doc.AcquirePage(i);
           
           PageToXml(page, xml, pageNode);
           
           page.Release();
        }
    }
    
}
