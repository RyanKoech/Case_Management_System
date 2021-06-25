/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author hp
 */
public class ISproject extends Application {
    
    Stage window;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        window = stage;
        stage.show();
        
        stage.setOnCloseRequest( e -> {
            
            e.consume();//We dont close intead we take over
            closeProgram();});
        }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    //Overide the exit button
    private void closeProgram(){
        
        boolean answer = ConfirmBox.display("EXIT?", "Sure You wanna exit?");
        if (answer){ window.close();}
        
    }
    
}
