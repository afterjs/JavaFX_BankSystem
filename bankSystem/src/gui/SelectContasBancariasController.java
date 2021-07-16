/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import banksystem.dbQuerys;
import banksystem.snippets;
import banksystem.personalData;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author After
 */
public class SelectContasBancariasController implements Initializable {

    String currentIbanS;
    String currentAgenciaS;
    String currentDataAccount;
   
    
    snippets snp = new snippets();
    dbQuerys db = new dbQuerys();
    
      @FXML
    private ListView<String> viewContas;

    @FXML
    private Label label_iban;
    
    @FXML
    private Button btn_escolher;

    @FXML
    private Label label_agencia;

    @FXML
    void avancarConta(MouseEvent event) {
        
       
       Map[] maps = db.allContasBancarias();
       personalData.iban = label_iban.getText();
       personalData.nomeAgencia = label_agencia.getText();
      
  
        for(int i = 0; i < maps.length; i++) {            
            String iban1 = ((String)personalData.iban).trim();
            String iban2 = ((String)maps[i].get("iban")).trim();
            String agencia1 = ((String)personalData.nomeAgencia).trim();
            String agencia2 = ((String)maps[i].get("nomeAgencia")).trim();
            if(iban1.equals(iban2) && agencia1.equals(agencia2)) {
                personalData.idContaBancaria = parseInt((String) maps[i].get("idConta"));
                personalData.idAgencia = parseInt((String) maps[i].get("idAgencia"));
                break;
            }
        }

        Alert alertEscolhida = new Alert(Alert.AlertType.INFORMATION);
        alertEscolhida.setTitle("Selecionar Conta");
        alertEscolhida.setHeaderText("Conta Selecionada com Sucesso!");
        alertEscolhida.setContentText("Seja muito bem vindo Sr/a " + personalData.nome);
        alertEscolhida.show(); 
        
         try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/MenuBancarioClient.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Menu Bancario");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        btn_escolher.setDisable(true);
       Map[] maps = db.allContasBancarias();

            for(int i = 0; i < maps.length;i++) {  
                 viewContas.getItems().addAll(maps[i].get("iban") +" | "+ maps[i].get("nomeAgencia"));
            }
           viewContas.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> arg2, String arg3, String arg4) -> {
                btn_escolher.setDisable(false);
           currentDataAccount = viewContas.getSelectionModel().getSelectedItem();
           int posBar = currentDataAccount.indexOf("|");
           currentIbanS = currentDataAccount.substring(0, posBar);   
           currentAgenciaS = currentDataAccount.substring(posBar + 1);

           label_iban.setText(currentIbanS);
           label_agencia.setText(currentAgenciaS);
       });
 
    }    
    
    
}
