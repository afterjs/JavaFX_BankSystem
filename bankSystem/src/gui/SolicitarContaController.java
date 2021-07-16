/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import banksystem.dbQuerys;
import banksystem.snippets;
import banksystem.personalData;
import java.awt.event.ActionEvent;
import java.io.IOException;
import static java.lang.Integer.parseInt;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class SolicitarContaController implements Initializable {

    snippets snp = new snippets();
    dbQuerys db = new dbQuerys();
    
     @FXML
    private ListView<String> listViewAg;

    @FXML
    private Text label_agencia;

    @FXML
    private Button btn_solicitar;
    
     


    @FXML
    void solicitarConta(MouseEvent event) {
         int idAgencia = 0;
         String[] agencias = db.verificarAgencias(0);
         
          for(int i = 0; i < agencias.length;i++) {  
              if (agencias[i] != null) {
                  String nomeAg = (agencias[i].substring(agencias[i].indexOf("|")+1)).trim();
                  String nomeField = label_agencia.getText().trim();
                  if(nomeAg.equals(nomeField)) {
                      idAgencia = parseInt(agencias[i].substring(0,agencias[i].indexOf("|")));
                      break;
                  } 
              } 
              }
          
        if (db.solicitarConta(idAgencia)) {   
            
            Alert cnSolicitada = new Alert(Alert.AlertType.INFORMATION);
            cnSolicitada.setTitle("Solicitar Conta");
            cnSolicitada.setHeaderText("A sua conta foi solicitada");
            cnSolicitada.show();  

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/clientMenu.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Escolher conta bancaria");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SolicitarContaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            Alert cnSolicitadaErro = new Alert(Alert.AlertType.ERROR);
            cnSolicitadaErro.setTitle("Solicitar Conta");
            cnSolicitadaErro.setHeaderText("Ocorreu um erro ao socilitar a conta.");
            cnSolicitadaErro.show();  
           try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/clientMenu.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Menu Cliente");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SolicitarContaController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
                Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    void voltar(MouseEvent event) {
       
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/clientMenu.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Menu Cliente");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SolicitarContaController.class.getName()).log(Level.SEVERE, null, ex);
            }

    }
         
       
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
         
         btn_solicitar.setDisable(true);
         String[] agencias = db.verificarAgencias(0);
        
         for(int i = 0; i < agencias.length;i++) {  
              if (agencias[i] != null) {
                 listViewAg.getItems().addAll(agencias[i].substring(agencias[i].indexOf("|")+ 1));
              }
            }
              listViewAg.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> arg2, String arg3, String arg4) -> {
              btn_solicitar.setDisable(false);
              String currentDataAccount = listViewAg.getSelectionModel().getSelectedItem();
              String agencia;
              agencia = currentDataAccount.substring(currentDataAccount.indexOf("|")+1);
              label_agencia.setText(agencia);
          });
    } 
  
}
