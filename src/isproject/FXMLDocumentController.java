/*
    Login module
 */
package isproject;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author hp
 */
public class FXMLDocumentController implements Initializable {
    
    String caseId;
    public static String CId;
    ObservableList list = FXCollections.observableArrayList();
    private Label label;
    @FXML
    private Button loginButton;
    @FXML
    private TextField idTextfield;
    @FXML
    private TextField passwordTextfield;
    @FXML
    private Label idLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private ChoiceBox<String> caseIdChoicebox;
    @FXML
    private Label caseIdLabel;
    @FXML
    private Label welcomeLabel;
    @FXML
    private ChoiceBox<String> roleChoicebox;
    @FXML
    private Label roleLabel;
    @FXML
    private Button exitButton;
    
    Stage window;
    @FXML
    private Label messageLabel;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setRoleData();
        setCaseId();
    }    

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
        
        //Ensure no fields are empty
        if (passwordTextfield.getText().isEmpty()== false && idTextfield.getText().isEmpty()== false){
            
            String role =roleCheck(roleChoicebox.getValue());
            Data.role_id = Integer.parseInt(role);
            Data.case_id = caseIdChoicebox.getValue();
            userLogin(idTextfield.getText(),passwordTextfield.getText(), role, caseIdChoicebox.getValue(), event);
        }else{
            messageLabel.setText("Please Ensure Password and Id field are filled");
            
        }
    }
    

    @FXML
    //Close the program
    private void handleExitButtonAction(ActionEvent event) {
        
        Stage stage = (Stage) exitButton.getScene().getWindow();
        boolean answer = ConfirmBox.display("EXIT?", "Sure You wanna exit?");
        if (answer){ stage.close();}
      
    }
    
    //Login Validation
    private void userLogin (String idNo, String password, String role, String caseId, ActionEvent event) throws IOException {
        
        Connection dbConn = DatabaseConnection.connectDB();
        if(dbConn != null){
        try {
            
        PreparedStatement st = (PreparedStatement)
                dbConn.prepareStatement ("select * from litigants where idNo = ? and password = ? and roleId = ? and caseId = ?");
        st.setString(1, idNo);
        st.setString(2, password);
        st.setString(3, role);
        st.setString(4, caseId);
        ResultSet res = st.executeQuery();
        
        if (res.next()){
            
            messageLabel.setText("welcome");
            
            int check = Integer.parseInt(role);
            //Load the various menus respectively
            switch (check){
                
                case 1:{
                    
                    Parent menuParent = FXMLLoader.load(getClass().getResource("AccuserDefendant.fxml"));
                    Scene menuScene = new Scene(menuParent);
            
                    Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            
                    window.setScene(menuScene);
                    window.show();
                    break;
                }
                
                case 2:{
                    
                    Parent menuParent = FXMLLoader.load(getClass().getResource("JuryMenu.fxml"));
                    Scene menuScene = new Scene(menuParent);
            
                    Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            
                    window.setScene(menuScene);
                    window.show();
                    break;
                }
                
                case 3:{
                    
                    Parent menuParent = FXMLLoader.load(getClass().getResource("prosecutionMenu.fxml"));
                    Scene menuScene = new Scene(menuParent);
            
                    Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            
                    window.setScene(menuScene);
                    window.show();
                    break;
                }
                
                case 4:{
                    
                    Parent menuParent = FXMLLoader.load(getClass().getResource("defenseAttorneyMenu.fxml"));
                    Scene menuScene = new Scene(menuParent);
            
                    Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            
                    window.setScene(menuScene);
                    window.show();
                    break;
                }
            }
            
            
            
        }else{
            
            System.out.println("ID number" + idNo);
            System.out.print("password" + password);
            messageLabel.setText("Unable to find such details. Try Again.");
            passwordTextfield.setText("");
            idTextfield.setText("");
            
            
        }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }else{
            System.out.println("The connection is not available");
        }

    }
    
    //Set choices of the roleChoiceBox
    private void setRoleData(){
        list.removeAll(list);
        String a = "Accuser/Defendant";
        String b = "Jury";
        String c = "Prosecution";
        String d = "Defense Attorney";
        list.addAll(a,b,c,d);
        roleChoicebox.getItems().addAll(list);
        
    }
    
    //Loading all case id into the choice box from the DB
    private void setCaseId(){
        Connection dbConn = DatabaseConnection.connectDB();
        if(dbConn != null){
        try {
            
            PreparedStatement st = (PreparedStatement)dbConn.prepareStatement ("select caseId from Chargesheet");
            ResultSet res = st.executeQuery();

            while (res.next()){
                String caseId = res.getString("caseId");
                caseIdChoicebox.getItems().addAll(caseId);
                System.out.println(caseId);

            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }else{
            System.out.println("The connection is not available");
        }
    }
    
    //Convert roles in role choice box to respective role ids
    public String roleCheck(String role){
        
        switch (role){
                
                case "Accuser/Defendant":{
                    role = "1";
                    break;
                }
                
                case "Jury":{
                    role = "2";
                    break;
                }
                
                case "Prosecution":{
                    role = "3";
                    break;
                }
                
                case "Defense Attorney":{
                    role = "4";
                    break;
                }
            }
        return role;
    }
    
    public String getCaseId(){
        
        return caseId;
    }

    
}
