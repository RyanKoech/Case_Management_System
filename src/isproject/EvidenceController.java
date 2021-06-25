/*
    Functions and event handlers for the evidences scene
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class EvidenceController implements Initializable {

    @FXML
    private Label evidenceTopLabel;
    @FXML
    private ChoiceBox<String> evidenceChoicebox;
    @FXML
    private TextArea evidenceTextarea;
    @FXML
    private Label evidenceEnterLabel;
    @FXML
    private Button backButton;
    @FXML
    private Button uploadButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setEvidenceId();
        evidenceChoicebox.getSelectionModel().selectedItemProperty().addListener((v , oldValue, newValue) -> setEvidenceTextArea(newValue));
    }    

    @FXML
    //Go back to the previous scene (Main menu where each user has their unique menu)
    private void handleBackButton(ActionEvent event) throws IOException {
        if (Data.role_id == 3){
            Parent menuParent = FXMLLoader.load(getClass().getResource("prosecutionMenu.fxml"));
            Scene menuScene = new Scene(menuParent);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();

            window.setScene(menuScene);
            window.show();
        }else if (Data.role_id == 4){
            Parent menuParent = FXMLLoader.load(getClass().getResource("defenseAttorneyMenu.fxml"));
            Scene menuScene = new Scene(menuParent);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();

            window.setScene(menuScene);
            window.show();
        }
    }

    @FXML
    //Uploads new evidence to the database from the text area
    private void handleUploadButton(ActionEvent event) {
        
        Connection dbConn = DatabaseConnection.connectDB();
        if(dbConn != null){
            try {

                PreparedStatement st = (PreparedStatement)dbConn.prepareStatement ("INSERT INTO `evidence` (`description`, `evidenceParty`, `eCaseId`) VALUES (?, ?, ?)");
                st.setString(1, evidenceTextarea.getText());
                if (Data.role_id == 3){st.setString(2, "accusing");} else if (Data.role_id == 4) {st.setString(2, "defense");}
                st.setString(3, Data.case_id);
                int res = st.executeUpdate();
                evidenceChoicebox.getItems().clear();
                setEvidenceId();
                PopUpaAlert.display("SUCCESS", "New Evidence Created.");
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("The connection is not available");
        } 
        
    }

    @FXML
    //Updates existing evidence in the database
    private void handleEditButton(ActionEvent event) {
        
        Connection dbConn = DatabaseConnection.connectDB();
        if(dbConn != null){
            try {

                PreparedStatement st = (PreparedStatement)dbConn.prepareStatement ("UPDATE `evidence` SET `description` = ? WHERE `evidence`.`evidenceId` = ?");
                st.setString(1, evidenceTextarea.getText());
                st.setString(2, evidenceChoicebox.getValue());
                int res = st.executeUpdate();
                evidenceChoicebox.getItems().clear();
                setEvidenceId();
                PopUpaAlert.display("SUCCESS", "Evidence Successfully Updated.");
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("The connection is not available");
        }
        
    }
    
    @FXML
    //Deletes existing evidence from the database
    private void handleDeleteButton(ActionEvent event) {
        
        Connection dbConn = DatabaseConnection.connectDB();
        if(dbConn != null){
            try {

                PreparedStatement st = (PreparedStatement)dbConn.prepareStatement ("DELETE FROM `evidence` WHERE `evidence`.`evidenceId` = ?");
                st.setString(1, evidenceChoicebox.getValue());
                int res = st.executeUpdate();
                evidenceChoicebox.getItems().clear();
                setEvidenceId();
                PopUpaAlert.display("SUCCESS", "Evidence Successfully Deleted.");
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("The connection is not available");
        }   
        
    }
    
    //Loads the respective evidence id into the choicebox according to the party(accusing or defense) and caseId
    private void setEvidenceId(){
        Connection dbConn = DatabaseConnection.connectDB();
        if(dbConn != null){
            try {
                
                PreparedStatement st = (PreparedStatement)dbConn.prepareStatement ("SELECT evidenceId FROM `evidence` WHERE eCaseId = ? and evidenceParty = ?");
                st.setString(1, Data.case_id);
                if (Data.role_id == 3){st.setString(2, "accusing");} else if (Data.role_id == 4) {st.setString(2, "defense");}
                ResultSet res = st.executeQuery();

                while (res.next()){
                    String evidenceId = res.getString("evidenceId");
                    evidenceChoicebox.getItems().addAll(evidenceId);
                    System.out.println(evidenceId);

                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("The connection is not available");
        }
    }
    
    //Loads selected evidence contents to the text area
    private void setEvidenceTextArea(String evidenceId){
        Connection dbConn = DatabaseConnection.connectDB();
        if(dbConn != null){
            try {

                PreparedStatement st = (PreparedStatement)dbConn.prepareStatement ("SELECT description FROM `evidence` WHERE eCaseId = ? AND evidenceId = ? ");
                st.setString(1, Data.case_id);
                st.setString(2, evidenceId);
                ResultSet res = st.executeQuery();

                while (res.next()){
                    String description = res.getString("description");
                    evidenceTextarea.setText(description);
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
