package object;

import gui.controller.CTR_Dashboard;
import gui.controller.CTR_Project_Module;
import handling.Archiv_Handler;
import handling.CSV_ProjectHandler;
import handling.Manager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import static main.Main_Application.ctr_dashboard;

/**
 * Created by Eike on 11.06.2017.
 */
public class Report_Object {
    @FXML
    Label label_projName;
    @FXML
    Label label_client;
    @FXML
    Label label_time;
    @FXML
    Label label_firstChar;
    @FXML
    VBox vbox_detail;
    @FXML
    public TitledPane titlepane;

    private String name, client;
    private int index, maxTime;
    private LocalDate date = LocalDate.now();
    private ArrayList<StorageObject> storageObjects = new ArrayList<>();
    private boolean closed = false;

    public Report_Object(String name, String client, int maxTime, ArrayList<StorageObject> storageObjects, int index) {
        this.name = name;
        this.client = client;
        this.maxTime = maxTime;
        this.storageObjects = storageObjects;
        this.index = index;

    }

    public Report_Object(String name, String client, int maxTime, int index) {
        this.name = name;
        this.client = client;
        this.maxTime = maxTime;
        this.index = index;
    }

    public void initialize() {
        label_projName.setText(name);
        label_client.setText(client);
        label_time.setText(Manager.printTimeWithoutSec(timeOfDay()));
        char p = name.charAt(0);
        label_firstChar.setText(String.valueOf(p));
        if(closed) {
            label_firstChar.setStyle("-fx-background-color: rgb(180,180,180);");
        }
        setTitlepane();
    }

    private void setTitlepane() {
        vbox_detail.getChildren().clear();
        for(StorageObject store : storageObjects) {
            if(date.equals(store.getDate())){
                Label label = new Label();
                label.setText(Manager.printTime(store.getSec()) + "   -   " + store.getComment());
                vbox_detail.getChildren().add(label);
            }
        }
    }

    public void addStorageObject(StorageObject store) {
        storageObjects.add(store);
    }

    public ArrayList<LocalDate> getAllDates() {
        ArrayList<LocalDate> dates = new ArrayList<>();
        for(StorageObject store : storageObjects) {
            dates.add(store.getDate());
        }
        return dates;
    }

    private int timeOfDay() {
        int time = 0;
        for(StorageObject store : storageObjects) {
            if(date.equals(store.getDate())) {
                time += store.getSec();
            }
        }
        return time;
    }

    public int getSecondsByDate(LocalDate date) {
        int time = 0;
        for(StorageObject store : storageObjects) {
            if(date.equals(store.getDate())) {
                time += store.getSec();
            }
        }
        return time;
    }

    public void reopen() throws IOException {
        //Erstelle ein neues Projekt mit den Report-Daten--------------------------------------------------------
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/fxml/project_module.fxml"));
        CTR_Project_Module project_module = new CTR_Project_Module(client, name, maxTime, Manager.projectList.size());
        project_module.setStorageObjects(storageObjects);
        fxmlLoader.setController(project_module);
        Manager.projectList.add(project_module);

        Parent project = fxmlLoader.load();
        ctr_dashboard.vbox_projList.getChildren().add(project);
        Manager.projectUIList.add(project);

        CTR_Dashboard.stageNewProject.close();
        CSV_ProjectHandler.csvWriter();
        //---------------------------------------------------------------------------------------------------------
        //Lösche dieses Objekt aus dem Archiv-------------------------------------------------------------------
        Archiv_Handler.getArchivObjects().remove(index);
    }

    public void setClosed(boolean value) {
        closed = value;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
