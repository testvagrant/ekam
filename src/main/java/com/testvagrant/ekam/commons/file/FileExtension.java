package com.testvagrant.ekam.commons.file;

public enum FileExtension {
  JAVA(".java"),
  TEXT(".txt"),
  SQL(".sql"),
  FEATURE(".feature"),
  EXCEL(".xls"),
  JSON(".json");

  private final String fileExtension;

  FileExtension(String fileExtension) {
    this.fileExtension = fileExtension;
  }

  public String getFileExtension() {
    return fileExtension;
  }
}
