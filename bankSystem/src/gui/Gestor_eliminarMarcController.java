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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class Gestor_eliminarMarcController implements Initializable {
    dbQuerys db = new dbQuerys();
    
    @FXML
    private Button btn_removeFunc;

    @FXML
    private ListView<String> listViewMarc;

    @FXML
    private Label label_nome;

    @FXML
    private Label label_data;

    @FXML
    private Label label_nif;

    @FXML
    private Label label_estado;

    @FXML
    private Label label_id;

        @FXML
    void logout(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/login.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Admin");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void removerFunc(MouseEvent event) {
  
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Eliminar Marcação");
        alert.setHeaderText("");
        alert.setContentText("Deseja eliminar a marcação?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            
            int idMarca = parseInt(label_id.getText());
            if(db.updateEstadoMarcacao(idMarca)) {
                    Alert alertAtivarAg = new Alert(Alert.AlertType.INFORMATION);
                    alertAtivarAg.setTitle("Eliminar Marcação");
                    alertAtivarAg.setHeaderText("");
                    alertAtivarAg.setContentText("Conta Recusada com Sucesso!");
                    alertAtivarAg.show();


                     try {
                        Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_menu.fxml"));
                        Scene regCena = new Scene (root);
                        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        stage.setScene(regCena);
                        stage.setTitle("Gestor Area");
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
            } else {
                 Alert alertAtivarAg = new Alert(Alert.AlertType.ERROR);
                    alertAtivarAg.setTitle("Eliminar Marcação");
                    alertAtivarAg.setHeaderText("");
                    alertAtivarAg.setContentText("Ocorreu um erro. Tente novamente!");
                    alertAtivarAg.show();

            }
    
        } 
  
    }

   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          btn_removeFunc.setDisable(true);
        Map[] marc = db.marcacoesAll();

        for (int i = 0; i < marc.length; i++) {
            if (marc[i] != null) {
                String id = ((String) marc[i].get("id")).trim();
                String nome = ((String) marc[i].get("nome")).trim();
                listViewMarc.getItems().addAll(id + " | " + nome);
            }
        }
        
          listViewMarc.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> arg2, String arg3, String arg4) -> {
            btn_removeFunc.setDisable(false);
           
            String currentDataAccount = (listViewMarc.getSelectionModel().getSelectedItem()).trim();

            for (int x = 0; x < marc.length; x++) {
                if (marc[x] != null) {
                    String idP = ((String) marc[x].get("id")).trim();
                    String nomeP = ((String) marc[x].get("nome")).trim();
                    String compare = null;
                    compare = idP + " | " + nomeP;

                    if (compare.equals(currentDataAccount)) {
                      
                     
                       
                       String name = (String )marc[x].get("nome");
                       String date = (String )marc[x].get("dataHora");
                       String nifP = (String )marc[x].get("nif");
                       String state = (String )marc[x].get("estado");
                       String idL =(String )marc[x].get("id");
                       
                       
                       
                       
                       
                       label_nome.setText(name);
                       label_data.setText(date);
                       label_nif.setText(nifP);
                       label_estado.setText(state);
                       label_id.setText(idL); 
                       break;
                    }

                }
            }

        });
        
        
        
        
        
    }    
    
}
