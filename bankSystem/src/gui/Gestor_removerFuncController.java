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
import java.util.Map;
import java.util.Optional;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class Gestor_removerFuncController implements Initializable {
dbQuerys db = new dbQuerys();
      @FXML
    private Button btn_removeFunc;

    @FXML
    private ListView<String> listViewFun;

    @FXML
    private Label label_func;

    @FXML
    private Label label_dtNas;

    @FXML
    private Label label_nif;

    @FXML
    private Label label_cc;

    @FXML
    private Label label_idade;

  

    @FXML
    void removerFunc(MouseEvent event) {
        
        
        Alert alertRemover = new Alert(AlertType.CONFIRMATION);
        alertRemover.setTitle("Eliminar Funcionario");
        alertRemover.setHeaderText("");
        alertRemover.setContentText("Deseja remover o Funcionario " + label_func.getText() + " ?");

        Optional<ButtonType> result = alertRemover.showAndWait();
        if (result.get() == ButtonType.OK){
            if (db.eliminarFuncionario(parseInt(label_nif.getText()))) {
             
                Alert alertSucessoEliminar = new Alert(Alert.AlertType.INFORMATION);
                alertSucessoEliminar.setTitle("Remover Funcionario");
                alertSucessoEliminar.setHeaderText("");
                alertSucessoEliminar.setContentText("O Funcionario foi removido com sucesso!!");
                alertSucessoEliminar.show(); 

                try {
               Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_menu.fxml"));
               Scene regCena = new Scene (root);
               Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
               stage.setScene(regCena);
               stage.setTitle("Area Gestor");
               stage.show();
           } catch (IOException ex) {
               Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
           }
         
            } else {
                
                 Alert alertSucessoEliminar = new Alert(Alert.AlertType.ERROR);
                alertSucessoEliminar.setTitle("Remover Funcionario");
                alertSucessoEliminar.setHeaderText("");
                alertSucessoEliminar.setContentText("Ocorreu um erro ao eliminar o Funcionario.");
                alertSucessoEliminar.show(); 

                try {
               Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_menu.fxml"));
               Scene regCena = new Scene (root);
               Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
               stage.setScene(regCena);
               stage.setTitle("Area Gestor");
               stage.show();
           } catch (IOException ex) {
               Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
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
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_menu.fxml"));
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
        // TODO
        
          btn_removeFunc.setDisable(true);
          Map[] maps = db.todosFunc();
        
         for(int i = 0; i < maps.length;i++) {  
              if (maps[i] != null) {
                  String gestor = (String) maps[i].get("nome");
                  String nif = (String) maps[i].get("nif");
                  
                 listViewFun.getItems().addAll((gestor.trim()+" | " +nif.trim()));
              }
            }
         
               listViewFun.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> arg2, String arg3, String arg4) -> {
                    btn_removeFunc.setDisable(false);
                    String currentDataAccount = (listViewFun.getSelectionModel().getSelectedItem()).trim();
                   

                    for(int x = 0; x < maps.length;x++) {  
                        if (maps[x] != null) {
                            String gestor = ((String) maps[x].get("nome")).trim();
                            String nif = ((String) maps[x].get("nif")).trim();
                            String compare = null;
                           compare = gestor + " | " + nif;
                            
                            if(compare.equals(currentDataAccount)) {
                                String dtNasc = (String) maps[x].get("dtNasc");
                                String func = (String) maps[x].get("nome");
                                String cc = (String) maps[x].get("codcc");
                                String idade = (String) maps[x].get("idade");
                                String nifFunc = (String) maps[x].get("nif");
                                label_dtNas.setText(dtNasc);
                                label_nif.setText(nif);
                                label_cc.setText(cc);
                                label_idade.setText(idade);
                                label_func.setText(func);
                                break;
                            }
                           
                        }
                      }
                    
              });
        
        
        
        
        
    }    
    
}
