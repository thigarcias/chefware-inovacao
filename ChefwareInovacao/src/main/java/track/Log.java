package track;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private static String LOG_FILE_PATH = "";
    private static String data;
    private static FileWriter fileWriter;
    private static PrintWriter logger;
    private String log;

    private static void setDataFile() {
        ZonedDateTime zone = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        data = zone.format(formatter);
        LOG_FILE_PATH = "src/main/logs/chefware" + data + ".log";
    }

    public Log() {
        try {
            if (data == null) {
                setDataFile();
            }
            fileWriter = new FileWriter(LOG_FILE_PATH, true);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar o logger");
        }
    }

    public static void printLog(String log){
        logger = new PrintWriter(fileWriter);
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
        logger.print("[" + zonedDateTime.format(formatter) + "]");
        logger.print(" - ");
        logger.println(log);
    }

    public static void close(){
        logger.close();
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
