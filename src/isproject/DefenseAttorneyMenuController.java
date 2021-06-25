/*
 * Contains event handles for the defense attorney options menu 
 */
package isproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DefenseAttorneyMenuController implements Initializable {

    @FXML
    private Button evidenceButton;
    @FXML
    private Button reportButton;
    @FXML
    private Button backButton;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    //Loads the Eveidences Scene
    private void handleEvidenceButton(ActionEvent event) throws IOException {
        Parent evidenceParent = FXMLLoader.load(getClass().getResource("Evidence.fxml"));
        Scene evidenceScene = new Scene(evidenceParent);
            
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            
        window.setScene(evidenceScene);
        window.show();
    }

    @FXML
    //Loads the Reports Scene
    private void handleReportButton(ActionEvent event) throws IOException {
        Parent evidenceParent = FXMLLoader.load(getClass().getResource("Report.fxml"));
        Scene evidenceScene = new Scene(evidenceParent);
            
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            
        window.setScene(evidenceScene);
        window.show();
    }

    @FXML
    //Go back to the previous scene (Login Module)
    private void handleBackButtton(ActionEvent event) throws IOException {
        Parent evidenceParent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene evidenceScene = new Scene(evidenceParent);
            
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            
        window.setScene(evidenceScene);
        window.show();
    }
    
}
