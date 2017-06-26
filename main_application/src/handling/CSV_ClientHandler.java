package handling;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Eike on 29.05.2017.
 */
public class CSV_ClientHandler {

    public static void csvWriter() throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter("data/clients.csv"));

        for(String client : Manager.clients) {
            String[] entries = {client};
            writer.writeNext(entries);
        }
        writer.close();
    }

    public void csvLoader() throws IOException{
        Manager.clients.clear();

        CSVReader reader = new CSVReader(new FileReader("data/clients.csv"));
        List<String[]> data = reader.readAll();
        for(String[] client : data) {
            Manager.clients.add(client[0]);
        }
    }

    public boolean fileExist(String path) {
        File file = new File(path);
        if(file.exists()) {
            return true;
        }
        return false;
    }

    public void deleteFile(String fileName){
        File f = new File(fileName);
        if(f.exists()){
            f.delete();
        }
    }

}
