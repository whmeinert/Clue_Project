package clueGame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BadConfigFormatException extends Exception {
	public static String logFile = "logfile.txt";
    private BufferedWriter append;

    public BadConfigFormatException() {
        super("The file was not configured properly");
    }

    public BadConfigFormatException(String string) {
        super(string);
        File file = new File(logFile);
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            this.append = new BufferedWriter(fileWriter);
            if (this.append != null) {
                this.append.write(String.valueOf(string) + "\n");
                this.append.flush();
            }
        }
        catch (IOException iOException) {
            System.out.println("Error writing to log: " + logFile);
            System.out.println(iOException.getMessage());
        }
    }
}
