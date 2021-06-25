/*
 * Scheduling of events
 */
package isproject;

import com.mysql.jdbc.interceptors.SessionAssociationInterceptor;
import java.io.IOException;
import java.net.Authenticator;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import sun.rmi.transport.Transport;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class SchedulerController implements Initializable {
    
    ObservableList list = FXCollections.observableArrayList();
    @FXML
    private Label scheduleLabel;
    @FXML
    private ChoiceBox<String> hourChoicebox;
    @FXML
    private Label timeLabel;
    @FXML
    private ChoiceBox<String> minuteChoicebox;
    @FXML
    private Label dateLabel;
    @FXML
    private ChoiceBox<String> dateChoicebox;
    @FXML
    private ChoiceBox<String> yearChoicebox;
    @FXML
    private ChoiceBox<String> monthChoicebox;
    @FXML
    private Button backButton;
    @FXML
    private Label hourLabel;
    @FXML
    private Label minuteLabel;
    @FXML
    private Label monthLabel;
    @FXML
    private Label yearLabel;
    @FXML
    private Button setButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setHour();
        setMinute();
        setMonth();
        setYear();
        setDate();
        
    }
    //setting the house choice box
    public void setHour(){
        list.removeAll(list);
        list.addAll("0", "1", "2", "3", "4", "5", "6", "7", "8" ,"9", "10", "11", "12", 
                    "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        hourChoicebox.getItems().addAll(list);
        hourChoicebox.setValue("0");
    }
    
    //setting the minute chouce box
    public void setMinute(){
        list.removeAll(list);
        list.addAll("0", "1", "2", "3", "4", "5", "6", "7", "8" ,"9", "10", "11", "12", "13", "14", "15", 
                    "16", "17", "18", "19", "20", "21", "22", "23", "24","25", "26", "27", "28", "29", "30",
                    "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45",
                    "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59");
        minuteChoicebox.getItems().addAll(list);
        minuteChoicebox.setValue("0");
    }
    
    //Setting the year choice box
    public void setYear(){
        list.removeAll(list);
        list.addAll("2020", "2021", "2022", "2023", "2024", "2025", "2026");
        yearChoicebox.getItems().addAll(list);
        yearChoicebox.setValue("2020");
    }
    
    //Setting month choice box
    public void setMonth(){
        list.removeAll(list);
        list.addAll("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", 
                    "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");
        monthChoicebox.getItems().addAll(list);
        monthChoicebox.setValue("JANUARY");
    }
    
    //set date choice box
    public void setDate(){
        list.removeAll(list);
        list.addAll( "1", "2", "3", "4", "5", "6", "7", "8" ,"9", "10", "11", "12", "13", "14", "15", 
                    "16", "17", "18", "19", "20", "21", "22", "23", "24","25", "26", "27", "28", "29", "30",
                    "31");
        dateChoicebox.getItems().addAll(list);
        dateChoicebox.setValue("1");
    }

    @FXML
    //Go back to the previous scene (Main menu)
    private void handleBackButton(ActionEvent event) throws IOException {
        Parent menuParent = FXMLLoader.load(getClass().getResource("prosecutionMenu.fxml"));
        Scene menuScene = new Scene(menuParent);
            
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            
        window.setScene(menuScene);
        window.show();
    }
    
    //Timeout function
    public void timeTracker(int year, int month, int dates, int hour, int minute) throws MessagingException{
        System.out.println("This class is called");
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR,year);
        date.set(Calendar.MONTH,month);
        date.set(Calendar.DAY_OF_MONTH,dates - 1);
        date.set(Calendar.HOUR_OF_DAY,hour);
        date.set(Calendar.MINUTE,minute);
        date.set(Calendar.SECOND,0);
        date.set(Calendar.MILLISECOND,0);
        Timer timer = new Timer();
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                
                try {
                    //Calls send email function on set date
                    notification(getRecepients(Data.case_id));
                    
                } catch (MessagingException ex) {
                    Logger.getLogger(SchedulerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        };
        
        System.out.println("Time is set");
        timer.schedule(task,date.getTime());
    }

    @FXML
    private void handleSetButton(ActionEvent event) throws MessagingException {
        
        int hour =Integer.parseInt(hourChoicebox.getValue()) ;
        int minute = Integer.parseInt(minuteChoicebox.getValue());
        int date = Integer.parseInt(dateChoicebox.getValue());
        int month = monthChoicebox.getSelectionModel().getSelectedIndex();
        int year = Integer.parseInt(yearChoicebox.getValue());
        timeTracker(year, month, date, hour, minute);
        uploadSchedule(minuteChoicebox.getValue(), hourChoicebox.getValue(), dateChoicebox.getValue(), String.valueOf(monthChoicebox.getSelectionModel().getSelectedIndex() + 1), yearChoicebox.getValue(), Data.case_id);
                    
        
    }
    
    //Send email functions
    public static void notification(String recepient) throws MessagingException{
        
        System.err.println("Prepareing to send message...");
        Properties properties = new Properties();
        
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        /*
            NOTE YOU NEED TO TURN NO 'Allow less secure apps in the security settings of the email address'
        */
        String email = "address@email.random";
        String password = "password";
        
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password); //To change body of generated methods, choose Tools | Templates.
            }
       
       });
         
        Message message = prepareMessage(session, email, recepient);
        javax.mail.Transport.send(message);
        System.out.println("Message Sent Successfully");
    }
    
    //Prepare email to be sent
    public static Message prepareMessage(Session session, String email, String recepient){
        
        try {
            String[] recipientList = recepient.split(",");
            InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
            int counter = 0;
            for (String recipient : recipientList) {
                recipientAddress[counter] = new InternetAddress(recipient.trim());
                counter++;
            }
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, recipientAddress);
            message.setSubject("Upcoming Court Meeting For Case ID: " + Data.case_id);
            message.setText("Dear Litigant,\n\nWe hope you have been doing well. This is to remind you that you are expected to report to court within the next 24hours. Kindly avail yourself early enough to avoid inconviences \n\n"
                            + "Make Sure you carry all that you are required to a have and incase you see that you may not attend communicae in due time which is 48hrs before the meeting else face the penalty.\n\n"
                            + "Thank you and stay safe making sure you abid by the law at all times.\n\n"
                            + "Kind Regards,\n"
                            + "The Judiciary");
            return message;
            
        } catch (Exception ex) {
            Logger.getLogger(SchedulerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    //Uploads the schedule to the database
     private void uploadSchedule(String minute, String hour, String date, String month, String year, String caseId) {
        
        Connection dbConn = DatabaseConnection.connectDB();
        if(dbConn != null){
        try {
            
            
            PreparedStatement st = (PreparedStatement) 
            dbConn.prepareStatement ("INSERT INTO `schedule` (`minutes`, `hours`, `dates`, `months`, `years`, `sCaseId`) VALUES (?, ?, ?, ?, ?, ?);");
            st.setString(1, minute);
            st.setString(2, hour);
            st.setString(3, date);
            st.setString(4, month);
            st.setString(5, year);
            st.setString(6, caseId);

            st.executeUpdate();
            PopUpaAlert.display("Success" ,"Schedule set Successfully");
            

    
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }else{
            System.out.println("The connection is not available");
        }

        
    }
    
    //Fetches email addresses from the database
    public String getRecepients(String case_Id){
        
        String recepients;
        Connection dbConn = DatabaseConnection.connectDB();
        if(dbConn != null){
            try {

                PreparedStatement st = (PreparedStatement)dbConn.prepareStatement ("SELECT email FROM `litigants` WHERE `caseId` = ?");
                st.setString(1, case_Id);
                ResultSet res = st.executeQuery();
                recepients = "default@gmail.com";
                while (res.next()){
                    if (res.getString("email").isEmpty() == false)
                    recepients = recepients + ", " + res.getString("email");

                }
            return recepients;
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("The connection is not available");
        }
        
        
        return "Error";
    }
     
}
