package main;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.swing.*;
import java.util.Optional;

/**
 * Created by Eike on 20.05.2017.
 */
public class Alert_Windows {


    public boolean confirmDialog(String title, String header, String text) {
        int response = JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.NO_OPTION) {
            System.out.println("No button clicked");
            return false;
        } else if (response == JOptionPane.YES_OPTION) {
            System.out.println("Yes button clicked");
            return true;
        } else if (response == JOptionPane.CLOSED_OPTION) {
            System.out.println("JOptionPane closed");
            return false;
        }
        return false;
    }

    public boolean confirmDialogFX(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        //alert.initOwner(Main_Application.primaryStage);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }

}
