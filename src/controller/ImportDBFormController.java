package controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ImportDBFormController {

    
    public RadioButton rdoRestore;
    public ToggleGroup abc;
    public TextField txtBrowse;
    public Button btnBrowse;
    public RadioButton rdoFirstTime;
    public Button btnOK;
    private SimpleObjectProperty<File> fileProperty;

    public void initialize(){
        txtBrowse.setEditable(false);
    }

    
    public void btnBrowse_OnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a backup file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Backup file",".samsbackup"));
        File file = fileChooser.showOpenDialog(btnOK.getScene().getWindow());
        txtBrowse.setText(file!=null?file.getAbsolutePath():"");
        fileProperty.setValue(file);
    }

    public void btnOK_OnAction(ActionEvent event) {
        if (rdoFirstTime.isSelected()){
            fileProperty.setValue(null);
            /*Todo: Create database and proceed*/
        }
        ((Stage)btnOK.getScene().getWindow()).close();

    }

}

