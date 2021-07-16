/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import banksystem.dbQuerys;
import banksystem.personalData;
import java.io.IOException;

import java.net.URL;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;





/**
 * FXML Controller class
 *
 * @author After
 */
public class LoginController implements Initializable {
    dbQuerys db = new dbQuerys();
    
     
    
    @FXML
    private TextField username_field;

    @FXML
    private PasswordField password_filed;
    
    
    @FXML
    void login(MouseEvent event) {
        int tipoConta;
        tipoConta = db.verificaLogin(username_field.getText(), password_filed.getText());
        
        switch (tipoConta) {
            case 0: // MENU CLIENTE
                try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/clientMenu.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Escolher conta bancaria");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            case 1: //conta de funcionario
                    Alert alertEscolha1 = new Alert(Alert.AlertType.INFORMATION);
                    alertEscolha1.setTitle("Selecionar Conta");
                    alertEscolha1.setHeaderText("Conta Selecionada com Sucesso!");
                    alertEscolha1.setContentText("Seja muito bem vindo Sr/a " + personalData.nome);
                    alertEscolha1.show(); 
                 try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/funcionario_area.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Menu Funcionario");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
                
             break;
            case 2: 
                    Alert alertEscolha2 = new Alert(Alert.AlertType.INFORMATION);
                    alertEscolha2.setTitle("Selecionar Conta");
                    alertEscolha2.setHeaderText("Conta Selecionada com Sucesso!");
                    alertEscolha2.setContentText("Seja muito bem vindo Sr/a " + personalData.nome);
                    alertEscolha2.show(); 
                 try {
                    Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_menu.fxml"));
                    Scene regCena = new Scene (root);
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(regCena);
                    stage.setTitle("Area Gestor");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
             break;
            case 3 :
                
                    Alert alertEscolha3 = new Alert(Alert.AlertType.INFORMATION);
                    alertEscolha3.setTitle("Selecionar Conta");
                    alertEscolha3.setHeaderText("Conta Selecionada com Sucesso!");
                    alertEscolha3.setContentText("Seja muito bem vindo Sr/a " + personalData.nome);
                    alertEscolha3.show(); 
                
                try {
                   Parent root = FXMLLoader.load(getClass().getResource("/GUI/admin_menu.fxml"));
                   Scene regCena = new Scene (root);
                   Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                   stage.setScene(regCena);
                   stage.setTitle("Gestão de Agências");
                   stage.show();
               } catch (IOException ex) {
                   Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
               }
            break;
            case -1:
            Alert alertLogin = new Alert(AlertType.ERROR);
            alertLogin.setTitle("Login");
            alertLogin.setHeaderText("O username ou a password são invalidos!");
            alertLogin.showAndWait();
            username_field.setText("");
            password_filed.setText("");
            break;
        }
        
    }
    
    @FXML
    void register(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/register.fxml"));
            Scene regCena = new Scene (root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Crir nova conta");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
