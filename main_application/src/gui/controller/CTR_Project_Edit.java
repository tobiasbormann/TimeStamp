package gui.controller;

import handling.Alert_Windows;
import handling.CSV_ProjectHandler;
import handling.Manager;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import object.StorageObject;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by Eike on 30.05.2017.
 */
public class CTR_Project_Edit {

    @FXML
    public TextField textfield_name;
    @FXML
    public TextField textfield_hour;
    @FXML
    public TextField textfield_min;
    @FXML
    public TextField textfield_maxHour;
    @FXML
    public ChoiceBox chb_client;
    @FXML
    public Label label_fail;

    private CSV_ProjectHandler csv_projectHandler = new CSV_ProjectHandler();

    private CTR_Project_Module project;
    private String name;
    private int rawSeconds;
    private int minutes;
    private int hours;
    private int maxHour;
    private Stage stage;
    private String client;

    public CTR_Project_Edit(CTR_Project_Module project, Stage stage, String name, int seconds, int maxHour, String client){
        this.project = project;
        this.name = name;
        rawSeconds = seconds;
        this.minutes = (seconds / 60) % 60;
        hours = seconds / 3600;
        this.maxHour = maxHour;
        this.stage = stage;
        this.client = client;
    }

    public void initialize() {
        textfield_name.setPromptText(name);
        textfield_hour.setPromptText(Integer.toString(hours));
        textfield_min.setPromptText(Integer.toString(minutes));
        textfield_maxHour.setPromptText(Integer.toString(maxHour));

        int i = 0;
        int index = 0;
        for(String c : Manager.clients) {
            if(c.equals(client)) {
                index = i;
            }
            chb_client.getItems().add(c);
            i++;
        }
        chb_client.getSelectionModel().select(index);
    }


    public void save() throws IOException {
        boolean approved = true;

        //Nsme ändern was auch jede Namensvariable in den Storageobjekten umfasst
        if(!textfield_name.getText().equals("")) {
            System.out.println("name geändert: " + textfield_name.getText());
            project.setName(textfield_name.getText());
            project.label_projName.setText(textfield_name.getText());
        }
        //Zeit Korrektur überprüfen ------------------------------------------------------------------------------------
        //Stunden als auch Minuten wurde geändert
        if(!textfield_hour.getText().equals("") && !textfield_min.getText().equals("")){
            try {
                hours = Integer.parseInt(textfield_hour.getText());
                minutes = Integer.parseInt(textfield_min.getText());
                setProjectTime();
                System.out.println("Es wurden Stunden und MInuten geändert");
            } catch (Exception e) {
                e.printStackTrace();
                approved = false;
                label_fail.setText("Stunden und Minuten enthalten einen Fehler");
            }
        //Ausschließlich Stunden wurden geändert
        } else if (!textfield_hour.getText().equals("") && textfield_min.getText().equals("")){
            try {
                hours = Integer.parseInt(textfield_hour.getText());
                setProjectTime();

                System.out.println("Es wurden nur Stunden geändert");
            }catch (Exception e) {
                e.printStackTrace();
                approved = false;
                label_fail.setText("das Stundenfeld enthält einen Fehler");
            }
        //Nur das Minutenfeld wurde geändert
        } else if(textfield_hour.getText().equals("") && !textfield_min.getText().equals("")) {
            try {
                minutes = Integer.parseInt(textfield_min.getText());
                setProjectTime();
                System.out.println("Es wurden nur Minuten geändert");
            }catch (Exception e) {
                e.printStackTrace();
                approved = false;
                label_fail.setText("das Minutenfeld enthält einen Fehler");
            }
        }
        //Und wenn alles das nicht der Fall ist, dann weiß ich auch nicht
        else {
            label_fail.setText("unvorhergesehener Fehler");
        }
        //--------------------------------------------------------------------------------------------------------------

        //überprüfen ob Kunde geändert wurde
        if(!chb_client.getSelectionModel().getSelectedItem().equals(client)) {
            System.out.println("Kunde geändert: " + (String) chb_client.getSelectionModel().getSelectedItem());
            String clientElement = (String) chb_client.getSelectionModel().getSelectedItem();
            project.setClient(clientElement);
            project.label_client.setText(clientElement);
        }

        //Überprüfen ob maxTime geänder wurde
        if(!textfield_maxHour.getText().equals("")) {
            try {
                maxHour = Integer.parseInt(textfield_maxHour.getText());
                project.setMaxTimeHours(maxHour);
            } catch (Exception e) {
                label_fail.setText("Die maximale Zeit konnte nicht geändert werden");
            }
        }

        if(approved) {
            csv_projectHandler.csvWriter();
            stage.close();
        }
    }

    //rechnet zunächst die Stunden und Minuten in reine Minuten um und ersetzt sie im Projekt
    //dann wird die Zeit für das letzte StorageObjekt berechnet
    private void setProjectTime() {
        minutes = (hours * 60) + minutes;
        int seconds = minutes * 60;
        project.setMainSec(seconds);
        project.label_time.setText(Manager.printTime(seconds));
        //StorageObject store = project.getStorageObjects().get(project.getStorageObjects().size()-1);
        //nimmt die differenz der alten und neuen Zeit und addiert sie zum letzten Storeage Eintrag
        //store.setSeconds((seconds-rawSeconds)+store.getSec());
        String comment = Alert_Windows.inputBox("Kommentar", "Kommentar für die korrigierte Zeit einfügen", "Was hast du in dieser Zeit gemacht?");
        project.addStorageObject(new StorageObject(LocalDate.now(),seconds-rawSeconds, comment));
    }

    public void abort() {
        stage.close();
    }

}
