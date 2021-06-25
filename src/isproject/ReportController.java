/*
 * Reports Scene
 */
package isproject;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class ReportController implements Initializable {

    @FXML
    private Label reportLabel;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private TextArea reportTextArea;
    @FXML
    private Button backButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        getReport();
    }    

    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        if ( Data.role_id == 1){
            Parent menuParent = FXMLLoader.load(getClass().getResource("AccuserDefendant.fxml"));
            Scene menuScene = new Scene(menuParent);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();

            window.setScene(menuScene);
            window.show();
        }else if (Data.role_id == 2) {
            Parent menuParent = FXMLLoader.load(getClass().getResource("JuryMenu.fxml"));
            Scene menuScene = new Scene(menuParent);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();

            window.setScene(menuScene);
            window.show();
            
        }else if (Data.role_id == 3) {
            
            Parent menuParent = FXMLLoader.load(getClass().getResource("prosecutionMenu.fxml"));
            Scene menuScene = new Scene(menuParent);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();

            window.setScene(menuScene);
            window.show();
            
        }else if (Data.role_id == 4) {
            
            Parent menuParent = FXMLLoader.load(getClass().getResource("defenseAttorneyMenu.fxml"));
            Scene menuScene = new Scene(menuParent);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();

            window.setScene(menuScene);
            window.show();
        }
    }
    
    //Automatically generates a report of the whole court case from the database and loads into the text area
    private void getReport(){
        
        reportTextArea.setText("Report from case ID:" + Data.case_id + "\nCHARGESHEET DESCRIPTION\n");
        Connection dbConn = DatabaseConnection.connectDB();
        if(dbConn != null){
            try {

                PreparedStatement st = (PreparedStatement)dbConn.prepareStatement ("SELECT description FROM `chargesheet` WHERE caseId = ?");
                st.setString(1, Data.case_id);
                ResultSet res = st.executeQuery();

                while (res.next()){
                    
                    String description = res.getString("description");
                    reportTextArea.appendText("\n" + description + "\n");
                    System.out.println(description);
                    
                }
                
                st = (PreparedStatement)dbConn.prepareStatement ("SELECT `evidenceId` , `evidenceParty`, `description` FROM `evidence` WHERE eCaseId = ?");
                st.setString(1, Data.case_id);
                res = st.executeQuery();

                while (res.next()){
                    
                    String description = res.getString("evidenceId");
                    reportTextArea.appendText("\n\nEVIDENCE ID : " + description);
                    System.out.println(description);
                    description = res.getString("evidenceParty");
                    reportTextArea.appendText(" : " + description);
                    System.out.println(description);
                    description = res.getString("description");
                    reportTextArea.appendText("\n\n\tEVIDENCE DESCRIPTION\n\t" + description);
                    System.out.println(description);
                   
                }
                
                st = (PreparedStatement)dbConn.prepareStatement ("SELECT `hours`, `minutes`, `dates`, `months`, `years` FROM `schedule` WHERE sCaseId = ?");
                st.setString(1, Data.case_id);
                res = st.executeQuery();
                reportTextArea.appendText("\n\nSCHEDULE\n");
                while (res.next()){
                    
                    String description = res.getString("hours");
                    reportTextArea.appendText("\nTime and Date " + description);
                    System.out.println(description);
                    description = res.getString("minutes");
                    reportTextArea.appendText(description + "hrs ");
                    System.out.println(description);
                    description = res.getString("dates");
                    reportTextArea.appendText(" " + description);
                    System.out.println(description);
                    description = res.getString("months");
                    reportTextArea.appendText("/" + description);
                    System.out.println(description);
                    description = res.getString("years");
                    reportTextArea.appendText("/" + description + "\n");
                    System.out.println(description);
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("The connection is not available");
        } 
        
    }
    
}
