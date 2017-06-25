package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Eike on 17.06.2017.
 */
public class Updater_Main {

    public static String build = "0";

    public static void main(String[] args) throws Exception {
        /*
        if(!File_Handler.fileExist("ver")){
            File_Handler.createDir("ver");
        }
        if(!File_Handler.fileExist("data")){
            File_Handler.createDir("data");
        }


        if(File_Handler.fileExist("ver/build.txt")) {
        } else {
            String[] content = {build};
            File_Handler.fileWriter("ver/build.txt", content);
        }

            try (BufferedReader reader = new BufferedReader((new InputStreamReader(new FileInputStream("ver/build.txt"), "UTF-8")))) {
                //Wenn durch Windows TextoCOdierung der UTF-8 Stream nicht mit readable Code anfängt, dann lösche es raus
                reader.mark(1);
                if (reader.read() != 0xFEFF)
                    reader.reset();
                build = reader.readLine();

                Updater updater = new Updater();
                boolean update = updater.checkForUpdate();
                if (update) {
                    updater.showChangeLog();
                } else {
                    //Runtime runTime = Runtime.getRuntime();
                    //runTime.exec("java -jar TimeStamp.jar");
                    System.out.println("neues Programm gestartet");
                }
            }*/
    }

    public boolean start(String version) throws Exception {
        Updater_Main.build = version;
        if(!File_Handler.fileExist("ver")){
            File_Handler.createDir("ver");
        }
        if(!File_Handler.fileExist("data")){
            File_Handler.createDir("data");
        }

        /*
        if(File_Handler.fileExist("ver/build.txt")) {
        } else {
            String[] content = {build};
            File_Handler.fileWriter("ver/build.txt", content);
        }

        try (BufferedReader reader = new BufferedReader((new InputStreamReader(new FileInputStream("ver/build.txt"), "UTF-8")))) {
            //Wenn durch Windows TextoCOdierung der UTF-8 Stream nicht mit readable Code anfängt, dann lösche es raus
            reader.mark(1);
            if (reader.read() != 0xFEFF)
                reader.reset();
            build = reader.readLine();*/
        System.out.println("old build main: " + build);

            Updater updater = new Updater();
            boolean update = updater.checkForUpdate();
            if (update) {
                updater.showChangeLog();
                return true;
            } else {
                //Runtime runTime = Runtime.getRuntime();
                //runTime.exec("java -jar TimeStamp.jar");
                System.out.println("altes Programm gestartet");
                return false;
            }
        }

}
