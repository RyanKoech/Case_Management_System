/*
 * Contains function of manipulating the charge sheet
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
public class ChargeController implements Initializable {

    @FXML
    private Label chargesheetTopLabel;
    @FXML
    private TextArea chargesheetTextarea;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Button backButton;
    @FXML
    private Button saveButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setChargesheetTextArea();
    }    

    @FXML
    //Go back to the previous scene
    private void handleBackButton(ActionEvent event) throws IOException {
        if (Data.role_id == 3){
            Parent menuParent = FXMLLoader.load(getClass().getResource("prosecutionMenu.fxml"));
            Scene menuScene = new Scene(menuParent);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();

            window.setScene(menuScene);
            window.show();
        }
        else if(Data.role_id == 2){
            Parent menuParent = FXMLLoader.load(getClass().getResource("JuryMenu.fxml"));
            Scene menuScene = new Scene(menuParent);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();

            window.setScene(menuScene);
            window.show();
        }
    }

    @FXML
    //Updates the contents of the chargesheet to the database
    private void handleSaveButton(ActionEvent event) {
        
        Connection dbConn = DatabaseConnection.connectDB();
        if(dbConn != null){
            try {

                PreparedStatement st = (PreparedStatement)dbConn.prepareStatement ("UPDATE `chargesheet` SET `description` = ? WHERE `chargesheet`.`caseId` = ?");
                st.setString(1, chargesheetTextarea.getText());
                st.setString(2, Data.case_id);
                int res = st.executeUpdate();
                
                PopUpaAlert.display("SUCCESS", "Chargesheet Successfully Updated.");
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("The connection is not available");
        }
    }
    
    //Load Chargesheet contents to the text area
    private void setChargesheetTextArea(){

        Connection dbConn = DatabaseConnection.connectDB();
        if(dbConn != null){
            try {

                PreparedStatement st = (PreparedStatement)dbConn.prepareStatement ("SELECT `description` FROM `chargesheet` WHERE caseId = ?");
                st.setString(1, Data.case_id);
                ResultSet res = st.executeQuery();

                while (res.next()){
                    String description = res.getString("description");
                    chargesheetTextarea.setText(description);
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
