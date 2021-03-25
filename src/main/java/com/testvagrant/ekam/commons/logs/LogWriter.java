package com.testvagrant.ekam.commons.logs;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class LogWriter {

    private static final String ROOT_FOLDER = "logs";

    public String createLogFolder() {
        DateTime now = DateTime.now( DateTimeZone.UTC );
        String runName = now.toString().replace( ":" , "-" );
        String folderName = String.format("%s/%s", ROOT_FOLDER, runName);
        File f = new File(folderName);
        f.mkdirs();
        return folderName;
    }

    public static void writeLog(String logFolder, String testName, List<String> output) {
        String logFile = String.format("%s/%s.log", logFolder, testName);
        try {
            FileUtils.writeLines(new File(logFile), Charset.defaultCharset().displayName(), output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
