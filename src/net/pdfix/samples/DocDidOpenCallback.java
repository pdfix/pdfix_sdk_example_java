/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.pdfix.samples;

import net.pdfix.pdfixlib.PdfEventProc;

/**
 *
 * @author jozef
 */
public class DocDidOpenCallback extends PdfEventProc {
    public void Callback(int data) {
        System.out.println("MyCallback " + data);
    }
    
    public void sample() {
//        DocDidOpenCallback cb = new DocDidOpenCallback();
//        pdfix.RegisterEvent(PdfEventType.kEventDocDidOpen, cb, 1);

    }
}
