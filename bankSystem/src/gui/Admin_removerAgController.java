/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import banksystem.dbQuerys;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class Admin_removerAgController implements Initializable {
dbQuerys db = new dbQuerys();
    
    @FXML
    private ListView<String> viewAg;

    @FXML
    private Label label_ag;

    @FXML
    private Button btn_removerAg;

     @FXML
    void removerAg(MouseEvent event) {

     String[] agencias = db.nomesAgenciasID();
     String agRemove = label_ag.getText().trim();

      for(int i = 0; i < agencias.length;i++) {  
            if (agencias[i] != null) {
                String agStr = agencias[i].substring(agencias[i].indexOf("|") + 1).trim();
                    if(agStr.equals(agRemove)) {
                        
                       int idAgencia = parseInt(agencias[i].substring(0, agencias[i].indexOf("|")));
                       
                        System.out.println("ID DA AGENCIA A ELININAR "  + idAgencia);

                    
                        if (db.apagarAgencia(idAgencia)) {

                                 Alert alertEliminarAg = new Alert(Alert.AlertType.INFORMATION);
                                 alertEliminarAg.setTitle("Remover Agencias");
                                 alertEliminarAg.setHeaderText("");
                                 alertEliminarAg.setContentText("Ag??ncia Removida com Sucesso!");
                                 alertEliminarAg.show(); 

                                 try {
                                Parent root = FXMLLoader.load(getClass().getResource("/GUI/admin_menu.fxml"));
                                Scene regCena = new Scene (root);
                                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                                stage.setScene(regCena);
                                stage.setTitle("Gest??o de Agencias");
                                stage.show();
                             } catch (IOException ex) {
                                 Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
                             }
                         } else {

                             Alert alertErroAg = new Alert(Alert.AlertType.ERROR);
                             alertErroAg.setTitle("Remover Agencias");
                             alertErroAg.setHeaderText("");
                             alertErroAg.setContentText("A Agencia neste momento est?? associada a contas bancarias");
                             alertErroAg.show(); 

                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("/GUI/admin_menu.fxml"));
                                Scene regCena = new Scene (root);
                                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                                stage.setScene(regCena);
                                stage.setTitle("Gest??o de Agencias");
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                         }
                        
                       
                       
                        break;
                    }
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
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/admin_menu.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Menu Admin");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
            }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         btn_removerAg.setDisable(true);
         String[] agencias = db.nomesAgenciasID();
        
         for(int i = 0; i < agencias.length;i++) {  
              if (agencias[i] != null) {
                 viewAg.getItems().addAll(agencias[i].substring(agencias[i].indexOf("|") + 1));
              }
            }
         
               viewAg.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> arg2, String arg3, String arg4) -> {
                    btn_removerAg.setDisable(false);
                    String currentDataAccount = viewAg.getSelectionModel().getSelectedItem();
                    String agencia;
                    agencia = currentDataAccount.substring(currentDataAccount.indexOf("|")+1);
                    label_ag.setText(agencia);
              });
    }  
    
    
}
