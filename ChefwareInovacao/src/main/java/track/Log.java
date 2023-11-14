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
    private static Integer limitLine = 0;
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
            logger = new PrintWriter(fileWriter);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar o logger");
        }
    }

    public static void printLog(String log) {
        if (limitLine < 1000) {
            limitLine++;
            dataHoraLog();
            logger.println(log);
        } else {
            dataHoraLog();
            logger.println(log);
            dataHoraLog();
            logger.println("[CHEFWARE] Limite de linhas atingido, encerrando arquivo log");
            limitLine = 0;
            Log.close();
            setDataFile();
            try {
                fileWriter = new FileWriter(LOG_FILE_PATH, true);
                logger = new PrintWriter(fileWriter);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void close() {
        try {
            fileWriter.close();
            logger.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void dataHoraLog() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
        logger.print("[" + zonedDateTime.format(formatter) + "]");
        logger.print(" - ");
    }

}
