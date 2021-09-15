////////////////////////////////////////////////////////////////////////////////////////////////////
// RegexSetPattern.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

// Example how to use PsRegex::SetPattern method. It uses ECMAScript regular expressions pattern syntax.

package net.pdfix.samples;

import net.pdfix.pdfixlib.*;
import static java.util.Objects.isNull;

public class RegexSetPattern {
    public static void run (
      String text
    ) throws Exception {
        Pdfix pdfix = new Pdfix();
        if (isNull(pdfix))
            throw new Exception("Pdfix initialization fail");
        
        PsRegex regex = pdfix.CreateRegex();
        if (regex == null) 
            throw new Exception(pdfix.GetError());
        String[] pattern = new String[10];
        // All major credit cards regex
        pattern[0] = "^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|6011[0-9]{12}|622((12[6-9]|"+
    "1[3-9][0-9])|([2-8][0-9][0-9])|(9(([0-1][0-9])|(2[0-5]))))[0-9]{10}|64[4-9][0-9]{13}|"+
    "65[0-9]{14}|3(?:0[0-5]|[68][0-9])[0-9]{11}|3[47][0-9]{13})*$";
    // American Express Credit Card
        pattern[1] = "^(3[47][0-9]{13})*$";
        // MasterCard Credit Card
        pattern[2] = "^(5[1-5][0-9]{14})*$";
        // Visa Credit Card
        pattern[3] = "^(4[0-9]{12}(?:[0-9]{3})?)*$";
         // Phone Numbers(North American)
        pattern[4] = "^((([0-9]{1})*[- .(]*([0-9]{3})[- .)]*[0-9]{3}[- .]*[0-9]{4})+)*$";
        // Social Security Numbers
        pattern[5] = "^([0-9]{3}[-]*[0-9]{2}[-]*[0-9]{4})*$";
        // UK Postal Codes
        pattern[6] = "^([A-Z]{1,2}[0-9][A-Z0-9]? [0-9][ABD-HJLNP-UW-Z]{2})*$";
        // URLs
        pattern[7] = "^((http|https|ftp)://)?([[a-zA-Z0-9]\\-\\.])+(\\.)([[a-zA-Z0-9]]){2,4}"+
          "([[a-zA-Z0-9]/+=%&_\\.~?\\-]*)$";
        // Emails
        pattern[8] = "^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
        // Passwords
        pattern[9] = "(?=^.{6,}$)((?=.*[A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z]))^.*";
        
        for (int i = 0; i < 10; i = i + 1) {
            regex.SetPattern(pattern[i]);
            if (regex.Search(text, i)) {
                // int pos = regex.GetPosition();
                // int len = regex.GetLength();
                // String matchText = regex.GetText();
            }
        }
        regex.Destroy();
        pdfix.Destroy();
    }
}
