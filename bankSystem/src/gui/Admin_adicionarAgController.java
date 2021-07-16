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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class Admin_adicionarAgController implements Initializable {
dbQuerys db = new dbQuerys();
    @FXML
    private Label label_ag;

    @FXML
    private Button btn_addAg;

    @FXML
    private ChoiceBox<String> gestorCombo;

    @FXML
    private TextField field_agencia;

    @FXML
    void adicionarAg(MouseEvent event) {
        String gestor = gestorCombo.getValue();
        String agencia = field_agencia.getText().trim();

        
        if(agencia.equals("") || gestor == null ) {
            Alert alertCamposPreenc = new Alert(Alert.AlertType.ERROR);
            alertCamposPreenc.setTitle("Erro ao criar Agencia");
            alertCamposPreenc.setHeaderText("Todos os campos tem de ser preenchidos corretamente.");
            alertCamposPreenc.show(); 
            return;
        } else {
            
            if(db.verificaNomeAgencia(agencia)) {
               Alert alertCamposPreenc = new Alert(Alert.AlertType.ERROR);
               alertCamposPreenc.setTitle("Erro ao criar Agencia");
               alertCamposPreenc.setHeaderText("Já existe uma Agência com esse nome.");
               alertCamposPreenc.show(); 
               return;
            } else {
                
                String[] checkGestId = db.nomesGestores();
                
                int idGestor = 0;
    
                for(int i= 0; i < checkGestId.length; i++) {
                    if(checkGestId[i] != null) {
                        String nome = (checkGestId[i].substring(checkGestId[i].indexOf("|") + 1,checkGestId[i].lastIndexOf("|"))).trim();
                         if(nome.equals(gestor)) {
                             idGestor = parseInt(checkGestId[i].substring(0, checkGestId[i].indexOf("|")));
                             break;
                         }
                    }
                }
                
               
                if(db.inserirAgencia(agencia, idGestor)) {
                    Alert alertCamposPreenc = new Alert(Alert.AlertType.INFORMATION);
                    alertCamposPreenc.setTitle("Adicionar Agencina");
                    alertCamposPreenc.setHeaderText("Agencia Adicionada com Sucesso!");
                    alertCamposPreenc.show(); 
                    
                     try {
                        Parent root = FXMLLoader.load(getClass().getResource("/GUI/admin_menu.fxml"));
                        Scene regCena = new Scene (root);
                        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        stage.setScene(regCena);
                        stage.setTitle("Gestão de Agencias");
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(SolicitarContaController.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                } else {
                    
                    Alert alertCamposPreenc = new Alert(Alert.AlertType.ERROR);
                    alertCamposPreenc.setTitle("Adicionar Agencina");
                    alertCamposPreenc.setHeaderText("Ocorreu um erro ao adicionar uma Agencia. Tente mais tarde.");
                    alertCamposPreenc.show(); 
                    
                     try {
                        Parent root = FXMLLoader.load(getClass().getResource("/GUI/admin_menu.fxml"));
                        Scene regCena = new Scene (root);
                        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        stage.setScene(regCena);
                        stage.setTitle("Gestão de Agencias");
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(SolicitarContaController.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    
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
                stage.setTitle("Menu Cliente");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
            }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
       String[] gestores = db.nomesGestores();
    
        for(int i= 0; i < gestores.length; i++) {
            if(gestores[i] != null) {
                 gestorCombo.getItems().addAll(gestores[i].substring(gestores[i].indexOf("|") + 1,gestores[i].lastIndexOf("|")));
            }
        }
    }    
    
  
    
}
