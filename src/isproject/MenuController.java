/*
 * Event handlers for Prosecution Option Menu
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

/**
 * FXML Controller class
 *
 * @author hp
 */
public class MenuController implements Initializable {

    @FXML
    private Button chargeSheetButton;
    @FXML
    private Button scheduleButton;
    @FXML
    private Button evidenceButton;
    @FXML
    private Button reportButton;
    @FXML
    private Button backToLoginButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    //Loads the chargesheet scene
    private void handleChargeSheetButton(ActionEvent event) throws IOException {
        Parent chargesheetParent = FXMLLoader.load(getClass().getResource("Charge.fxml"));
        Scene chargesheetScene = new Scene(chargesheetParent);
            
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            
        window.setScene(chargesheetScene);
        window.show();
    }

    @FXML
    //Loads the schedules scene
    private void handleScheduleButton(ActionEvent event) throws IOException {
        Parent scheduleParent = FXMLLoader.load(getClass().getResource("Scheduler.fxml"));
        Scene scheduleScene = new Scene(scheduleParent);
            
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            
        window.setScene(scheduleScene);
        window.show();
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
    private void handleBackToLoginButton(ActionEvent event) throws IOException {
        Parent evidenceParent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene evidenceScene = new Scene(evidenceParent);
            
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            
        window.setScene(evidenceScene);
        window.show();
    }
    
}
