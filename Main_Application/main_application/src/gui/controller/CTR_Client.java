package gui.controller;

import handling.Manager;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.Main_Application;
import object.Client_Object;

import java.util.ArrayList;

/**
 * Created by Eike on 10.06.2017.
 */
public class CTR_Client {

    private ArrayList<Client_Object> uiClients;
    private ScrollPane scrollPane = new ScrollPane();
    public GridPane gridPane;
    private VBox mainVBox;

    public CTR_Client() {
        uiClients = new ArrayList<>();
        int i = 0;
        for(String client : Manager.clients) {
            uiClients.add(new Client_Object(client, i, this));
            i++;
        }
    }

    public void initialize() {
        //mainVBox = new VBox();
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        int col = 0;
        int row = 0;
        for(Client_Object client : uiClients) {
            gridPane.add(client.createNode(), col, row);
            if(col == 5){
                col = 0;
                row++;
            } else col++;
        }
        //gridPane.getChildren().add
        scrollPane.setContent(gridPane);
        Main_Application.ctr_dashboard.borderpane.setCenter(null);
        Main_Application.ctr_dashboard.borderpane.setCenter(scrollPane);
    }

}
