package gui.controller;

import handling.Alert_Windows;
import handling.CSV_ClientHandler;
import handling.CSV_ProjectHandler;
import handling.Manager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import object.StorageObject;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Eike on 22.05.2017.
 */
public class CTR_newProject implements Initializable {

    @FXML
    private ChoiceBox chb_customer;
    @FXML
    private Button btn_newCustomer;
    @FXML
    private TextField input_name;
    @FXML
    private TextField input_maxHours;
    @FXML
    private Label label_output;

    private CSV_ClientHandler csv_clientHandler = new CSV_ClientHandler();
    private CSV_ProjectHandler csv_projectHandler = new CSV_ProjectHandler();

    // ggf. ausprobieren über static main auf Dashboard zuzugreifen und den Controller nicht über Java definieren
    CTR_Dashboard ctr_dashboard;

    public CTR_newProject(CTR_Dashboard ctr_dashboard) {
        this.ctr_dashboard = ctr_dashboard;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chb_customer.getItems().clear();
        for(String customer : Manager.clients) {
            chb_customer.getItems().add(customer);
        }
        chb_customer.getSelectionModel().selectLast();
    }

    public void save() {
        // ist ein Projektname vergeben?
        if(!input_name.getText().isEmpty()) {
            //ist mindestens ein Kunde angelegt
            if(Manager.clients.size() == 0) {
                System.out.println("lege bitte zunächst einen Kunden an");
                label_output.setText("Bitte lege zunächst einen Kunden an");
            //erstmal einen Kunden anlegen
            } else {
                Integer customerIndex = chb_customer.getSelectionModel().getSelectedIndex();
                //Ist ein Kunde ausgewählt worden
                if (customerIndex != null) {
                    //Ist in Feld ein Integerwert eingetragen
                    try {
                        int maxHours = Integer.parseInt(input_maxHours.getText());

                        //erstelle neues Projekt!!!
                        try {

                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/project_module.fxml"));
                            CTR_Project_Module project_module = new CTR_Project_Module(Manager.clients.get(customerIndex), input_name.getText(), maxHours, Manager.projectList.size());
                            fxmlLoader.setController(project_module);
                            Manager.projectList.add(project_module);
                            project_module.getStorageObjects().add(new StorageObject(LocalDate.now(), 0, "Projekt angelegt"));

                            Parent project = fxmlLoader.load();
                            ctr_dashboard.vbox_projList.getChildren().add(project);
                            Manager.projectUIList.add(project);

                            CTR_Dashboard.stageNewProject.close();
                            csv_projectHandler.csvWriter();

                        } catch (Exception e){
                            System.out.println("Das Projekt konnte nicht erstellt werden " + e);
                            label_output.setText("Das Projekt konnte nicht erstellt werden");
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        System.out.println("unzulässiger Wert in Maximale Stunden");
                        label_output.setText("Unzulässiger Wert für maximale Stunden");
                    }

                } else {
                    System.out.println("Wähle bitte einen Kunden aus");
                    label_output.setText("Bitte wähle einen Kunden aus");
                }
            }
        } else {
            System.out.println("Gibt deinem Projekt bitte einen Namen");
            label_output.setText("Bitte gib deinem Projekt einen Namen");
        }
    }


    public void newCustomer() {
        String client = Alert_Windows.inputBox("Neuer Kunde", "Lege einen neuen Kunden an",
                "Bitte gib den Namen des Kunden an");
        if(client.isEmpty()) {
            System.out.println("Kunde konnte nicht erstellt werden, da kein Name vergeben wurde");
        } else if(Manager.clients.contains(client)){
            System.out.println("Der Kunde existiert bereits");
        } else {
            Manager.clients.add(client);
            chb_customer.getItems().add(Manager.clients.get(Manager.clients.size() - 1));
            chb_customer.getSelectionModel().selectLast();
            //speicher Clientdaten in Datei
            try {
                csv_clientHandler.csvWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void abort(){
        ctr_dashboard.stageNewProject.close();
    }


}
