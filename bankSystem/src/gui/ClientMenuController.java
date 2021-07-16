/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import banksystem.dbQuerys;
import gui.LoginController;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class ClientMenuController implements Initializable {
dbQuerys db = new dbQuerys();

    @FXML
    private Text label_estadoContaAceite;
    
     @FXML
    private Text label_estadoContaPendete;

    @FXML
    void escolherContaScene(MouseEvent event) {
           Map[] maps = db.allContasBancarias();
            if(maps.length>0){
            
               try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/selectContasBancarias.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Escolher Conta Bancaria");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            } else {
                Alert alertSemConta = new Alert(Alert.AlertType.INFORMATION);
                alertSemConta.setTitle("Escolher Conta Bancaria");
                alertSemConta.setHeaderText("Ainda não tens nenhuma conta! Solicita uma");
                alertSemConta.showAndWait();
            }
    }

    @FXML
    void logout(MouseEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/login.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Login");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ClientMenuController.class.getName()).log(Level.SEVERE, null, ex);
            } 
    }

    @FXML
    void solicitarContaScene(MouseEvent event) {
        System.out.println("ESTOU CA");
        
       String[] ag = db.verificarAgencias(0);
       int contador = 0;
       
       
       
       for(int i = 0; i < ag.length; i++) {
           if(ag[i] != null) {
               contador++;
           }
       }  
        
        if(contador > 0 ) {
            if(db.verificaSolicitacoesBancarias()) {
            Alert alertLogin = new Alert(Alert.AlertType.WARNING);
            alertLogin.setTitle("Solicitar Conta");
            alertLogin.setHeaderText("De momento já tens uma conta pendente!");
            alertLogin.showAndWait();
        } else {
          try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/solicitarConta.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Solicitar Conta Bancaria");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ClientMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        } else {
             Alert alertLogin = new Alert(Alert.AlertType.WARNING);
            alertLogin.setTitle("Solicitar Conta");
            alertLogin.setHeaderText("De momento, não temos agencias registada no nosso banco!");
            alertLogin.showAndWait();
            
        }
        
        
        
        

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if(db.verificaSolicitacoesBancarias()) {
            label_estadoContaPendete.setText("");
            label_estadoContaAceite.setText("Conta Pendente");
        } else {
            label_estadoContaAceite.setText("");
            label_estadoContaPendete.setText("Pode Solicitar Conta");
        }
        
    }   
    
    
    
    
    
}
