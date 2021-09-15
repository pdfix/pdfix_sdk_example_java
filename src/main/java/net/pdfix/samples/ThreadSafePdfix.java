////////////////////////////////////////////////////////////////////////////////////////////////////
// ThreadSafePdfix.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to use PDFix SDK in a muti-threaded environment.
package net.pdfix.samples;

import java.util.concurrent.Semaphore;
import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

public class ThreadSafePdfix {
    static Semaphore semaphore = new Semaphore(1);
    
    static class MyPdfDocThread extends Thread {
        PdfDoc doc = null;
        String operation = "";
        MyPdfDocThread(PdfDoc doc, String operation) {
            this.doc = doc;
            this.operation = operation;
        }
        public void run() {
            try {
                System.out.println(operation + " : acquiring lock...");
                System.out.println(operation + " : available Semaphore permits now: " 
                    + semaphore.availablePermits());
                semaphore.acquire();
                System.out.println(operation + " : got the permit!");
                try {
                    System.out.println(operation + " : executing operation...");
                    PdfPage page = doc.AcquirePage(0);
                    PdePageMap pageMap = page.AcquirePageMap();
                    pageMap.CreateElements();
                    // some processing
                    Thread.sleep(1000);
                    page.Release();
                    System.out.println(operation + " : finished execution...");
                } finally {
                    // calling release() after a successful acquire()
                    System.out.println(operation + " : releasing lock...");
                    semaphore.release();
                    System.out.println(operation + " : available Semaphore permits now: " 
                        + semaphore.availablePermits());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    
    public static void run (
      String openPath) throws Exception {
        System.out.println("ThreadSafePdfix");
        
        Pdfix pdfix = new Pdfix();
        if (isNull(pdfix))
            throw new Exception("Pdfix initialization fail");

        PdfDoc doc = pdfix.OpenDoc(openPath, "");
        if (doc == null)
            throw new Exception(pdfix.GetError());
        
        MyPdfDocThread thread1 = new MyPdfDocThread(doc, "count pages");
        thread1.start();
        MyPdfDocThread thread2 = new MyPdfDocThread(doc, "remove annotations");
        thread2.start();
        MyPdfDocThread thread3 = new MyPdfDocThread(doc, "place watermark");
        thread3.start();
        MyPdfDocThread thread4 = new MyPdfDocThread(doc, "extract tables");
        thread4.start();
        
        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        
        // cleanup
        doc.Close();        
        pdfix.Destroy();
    }
}
