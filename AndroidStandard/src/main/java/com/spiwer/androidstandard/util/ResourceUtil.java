package com.spiwer.androidstandard.util;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author spiwer.com - Herman Leonardo Rey Baquero - leoreyb@gmail.com
 */
public class ResourceUtil
{

  public static void close(Closeable resource)
  {
    if (resource == null) {
      return;
    }
    try {
      resource.close();
    } catch (IOException ex) {
      Logger.getLogger(ResourceUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
