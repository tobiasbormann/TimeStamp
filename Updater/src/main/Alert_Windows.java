package main;


import javax.swing.*;

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

}
