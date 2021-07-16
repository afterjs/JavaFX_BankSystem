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
import java.util.Optional;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class Admin_menuController implements Initializable {
    dbQuerys db = new dbQuerys();
    
            
    @FXML
    void mostrarAgencias(MouseEvent event) {
        
        
         if (db.verificaExisteAgencias()) {
                //redirect
                try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/admin_mostrarAgencias.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Mostrar Agencias");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SolicitarContaController.class.getName()).log(Level.SEVERE, null, ex);
            } 
            } else {
                Alert alertNoAg = new Alert(Alert.AlertType.ERROR);
                alertNoAg.setTitle("Mostrar Agencias");
                alertNoAg.setHeaderText("Neste momento não temos nenhuma Agência Registada!");
                alertNoAg.showAndWait();
            }

    }   
    
    @FXML      
    void addRemoveAg(MouseEvent event) {
        
        Alert alertAg = new Alert(Alert.AlertType.CONFIRMATION);
        alertAg.setTitle("Gesão de Angências");
        alertAg.setHeaderText("O que deseja fazer?");
        alertAg.setContentText("Escolha a opção");

        ButtonType ativarAgBTN = new ButtonType("Adicionar Agência");
        ButtonType desativarAgBTN = new ButtonType("Remover Agências");
        ButtonType BTNCancelar = new ButtonType("Fechar", ButtonBar.ButtonData.CANCEL_CLOSE);
        
       
        alertAg.getButtonTypes().setAll(ativarAgBTN, desativarAgBTN, BTNCancelar);

        Optional<ButtonType> result = alertAg.showAndWait();
        if (result.get() == ativarAgBTN){
          if (db.existeAlgumGestor()) { 
          
               try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/admin_adicionarAg.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Adicionar Agenência");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SolicitarContaController.class.getName()).log(Level.SEVERE, null, ex);
            } 
              
              
          }else {
              Alert alertNoGest = new Alert(Alert.AlertType.ERROR);
                alertNoGest.setTitle("Adicionar Agências");
                alertNoGest.setHeaderText("Não existe gestores disponiveis para tal.");
                alertNoGest.showAndWait();
              
          }
          
        } else if (result.get() == desativarAgBTN) {

           if (db.verificaExisteAgencias()) {
                //redirect
                try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/admin_removerAg.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Remover Agenência");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SolicitarContaController.class.getName()).log(Level.SEVERE, null, ex);
            } 
            } else {
                Alert alertNoAg = new Alert(Alert.AlertType.ERROR);
                alertNoAg.setTitle("Remover Agências");
                alertNoAg.setHeaderText("Neste momento não temos nenhuma Agência Registada!");
                alertNoAg.showAndWait();
            }
     
        }         

    }

    @FXML
    void ativarDesativarAg(MouseEvent event) {
        
        Alert alertAg = new Alert(Alert.AlertType.CONFIRMATION);
        alertAg.setTitle("Gesão de Angências");
        alertAg.setHeaderText("O que deseja fazer?");
        alertAg.setContentText("Escolha a opção");

        ButtonType ativarAgBTN = new ButtonType("Ativar Agência");
        ButtonType desativarAgBTN = new ButtonType("Desativar Agências");
        ButtonType BTNCancelar = new ButtonType("Fechar", ButtonBar.ButtonData.CANCEL_CLOSE);
        
       
        alertAg.getButtonTypes().setAll(ativarAgBTN, desativarAgBTN, BTNCancelar);

        Optional<ButtonType> result = alertAg.showAndWait();
        if (result.get() == ativarAgBTN){
            if(db.existeAgencia(1)) {
                //redirect
                try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/admin_ativarAg.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Ativar Agenência");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SolicitarContaController.class.getName()).log(Level.SEVERE, null, ex);
            } 
            } else {
                 Alert alertNoAg = new Alert(Alert.AlertType.ERROR);
                alertNoAg.setTitle("Ativar Agências");
                alertNoAg.setHeaderText("Neste momento nenhuma agencia está desativada!");
                alertNoAg.showAndWait();
            }
          
        } else if (result.get() == desativarAgBTN) {

            if(db.existeAgencia(0)) {
                //redirect
                try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/admin_desativarAg.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Desativar Agenência");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SolicitarContaController.class.getName()).log(Level.SEVERE, null, ex);
            } 
            } else {
                 Alert alertNoAg = new Alert(Alert.AlertType.ERROR);
                alertNoAg.setTitle("Desativar Agências");
                alertNoAg.setHeaderText("Neste momento nenhuma agencia está ativa!");
                alertNoAg.showAndWait();
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }    
    
}
