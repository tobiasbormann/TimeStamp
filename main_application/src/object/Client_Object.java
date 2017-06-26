package object;

import gui.controller.CTR_Client;
import gui.controller.CTR_Project_Module;
import handling.Alert_Windows;
import handling.CSV_ClientHandler;
import handling.CSV_ProjectHandler;
import handling.Manager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by Eike on 10.06.2017.
 */
public class Client_Object {

    private VBox mainVBox;
    private String name;
    private int index;
    private CTR_Client ctr_client;
            ;

    public Client_Object(String name, int index, CTR_Client ctr_client) {
        this.name = name;
        this.index = index;
        this.ctr_client = ctr_client;
    }

    public VBox createNode() {
        mainVBox = new VBox();
        mainVBox.setStyle("-fx-background-color: rgb(89,39,255);" +
        "-fx-padding: 20;");
        Label labelName = new Label();
        Label labelactiveProjects = new Label();
        Label labelclosedProjects = new Label();
        Button btn_rename = new Button();
        Button btn_delete = new Button();

        labelName.setText(name);
        labelName.setStyle("-fx-font-size: 18px;" +
                "-fx-font-weight: bold;");
        labelactiveProjects.setText(activeProjects() + " aktive Projekte");

        btn_rename.setText("umb");
        btn_delete.setText("entf");

        btn_rename.setOnAction(event -> {
            try {
                rename();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btn_delete.setOnAction(event -> {
            try {
                delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        mainVBox.getChildren().addAll(labelName, labelactiveProjects, btn_rename, btn_delete);
        return mainVBox;
    }

    private int activeProjects() {
        int count = 0;
        for(CTR_Project_Module project : Manager.projectList) {
            if(project.getClient().equals(name)){
             count++;
            }
        }
        return count;
    }

    private void rename() throws IOException {
        String newName = Alert_Windows.inputBox("Kunde umbennen", "Neuen Kundennamen vergeben", "Gib den neuen Namen für den Kunden ein");
        // Iterate Kundenliste und suche nach gleichem Namen - ersetze mit neuem
        for(int i=0; i<Manager.clients.size(); i++) {
            if(Manager.clients.get(i).equals(name)){
                Manager.clients.set(i, newName);
            }
        }
        CSV_ClientHandler.csvWriter();
        //Iteratte Projektliste und sucht nach Kundenname und ersetzt diesen mit neuem + setzt Clientlabel entsprechend
        for(CTR_Project_Module project : Manager.projectList) {
            if(project.getClient().equals(name)){
                project.setClient(newName);
                project.label_client.setText(newName);
            }
        }
        CSV_ProjectHandler.csvWriter();
        name = newName;
    }

    private void delete() throws IOException {
        System.out.println(name + " gelöscht");
        Manager.clients.remove(index);
        ctr_client.gridPane.getChildren().remove(index);
        CSV_ClientHandler.csvWriter();
    }

    public String getName() {
        return name;
    }
}
