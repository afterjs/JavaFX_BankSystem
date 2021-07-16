/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import banksystem.dbQuerys;
import java.io.IOException;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class Admin_mostrarAgenciasController implements Initializable {
     dbQuerys db = new dbQuerys();

    @FXML
    private ListView<String> view_allAgencias;

   @FXML
    private Label label_agencia;

    @FXML
    private Label label_estado;

    @FXML
    private Label label_gestor;

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
         
         String[] totalAgencias = db.todasAgencias();
        
         for(int i = 0; i < totalAgencias.length;i++) {  
              if (totalAgencias[i] != null) {
                 view_allAgencias.getItems().addAll(totalAgencias[i].substring(0, totalAgencias[i].indexOf("|")));
              }
            }
         
         
          view_allAgencias.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> arg2, String arg3, String arg4) -> {
                    String estado = null;
                    String gestor = null;
                    String agencia = null;
                    String agenciaSelect = null;
                    
                    String currentDataAccount = (view_allAgencias.getSelectionModel().getSelectedItem()).trim();
                  
                    agencia = currentDataAccount.substring(currentDataAccount.indexOf("|")+1);

                    
                    for(int x = 0; x < totalAgencias.length;x++) {  
                        if (totalAgencias[x] != null) {
                            agenciaSelect = totalAgencias[x].substring(0, totalAgencias[x].indexOf("|")).trim();
                           if(agenciaSelect.equals(agencia)) {
                              estado = totalAgencias[x].substring(totalAgencias[x].indexOf("|") + 1,totalAgencias[x].lastIndexOf("|"));
                              gestor = totalAgencias[x].substring(totalAgencias[x].lastIndexOf("|") + 1);      
                              label_agencia.setText(agencia);
                              label_estado.setText(estado);
                              label_gestor.setText(gestor);
                              break;
                           }
                           
                        }
                      }
                   
  
                  
              });
         
               
    }    
    
}
