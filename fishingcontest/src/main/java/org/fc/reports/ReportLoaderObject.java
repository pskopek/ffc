package org.fc.reports;

/**
 * @author pskopek
 *
 */
public class ReportLoaderObject {
  //
  private static ReportLoaderObject singleton = new ReportLoaderObject();
  
  public static ReportLoaderObject getLoader() {
    return singleton;
  }
  
}