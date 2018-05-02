////////////////////////////////////////////////////////////////////////////////////////////////////
// Utils.java
// Copyright (c) 2018 PDFix. All Rights Reserved.
////////////////////////////////////////////////////////////////////////////////////////////////////

//\cond INTERNAL
package net.pdfix;

import java.io.File;

public class Utils {
  public static boolean debug = false;
  public static String getModuleName(String prefix) {
    String name = prefix;
    if (System.getProperty("sun.arch.data.model").contains("64"))
      name += "64";
    String os = System.getProperty("os.name").toLowerCase();
    if (os.contains("win")) name += ".dll";
    else if (os.contains("linux")) {          
      name = "lib" + name + ".so";        
    }
    else if (os.contains("mac")) {          
      name = "lib" + name + ".dylib";        
    }
    else 
      throw new RuntimeException("Unknown os");
    return name;
  }
    
  public static String getAbsolutePath(String name) {
    String path = System.getProperty("user.dir");
    return path + File.separator + name;
  }  
}
//\endcond
