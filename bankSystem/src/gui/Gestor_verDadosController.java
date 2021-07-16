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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class Gestor_verDadosController implements Initializable {
    dbQuerys db = new dbQuerys();

    @FXML
    private Label label_iban;

    @FXML
    private Label label_agencia;

    @FXML
    private Label label_data;

    @FXML
    private Label label_nif;

    @FXML
    private Label label_nome;

    @FXML
    private Label label_civil;

    @FXML
    private Label label_situacao;

    @FXML
    private Label label_dataN;

    @FXML
    private Label label_idade;

    @FXML
    private Label label_num;

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
        Map[] cont = db.dadosConta(personalData.gestor_idBancario_user);
        
        if(cont.length> 0) {

            label_iban.setText((String) cont[0].get("iban"));
            label_agencia.setText((String) cont[0].get("nomeAgencia"));
            label_data.setText((String) cont[0].get("dataAbertura"));
            label_nif.setText((String) cont[0].get("nif"));
            label_nome.setText((String) cont[0].get("nome"));
            label_civil.setText((String) cont[0].get("CodCC"));
            label_situacao.setText((String) cont[0].get("situacaoLaboral"));
            label_dataN.setText((String) cont[0].get("dtNascimento"));
            label_idade.setText((String) cont[0].get("idade"));
            label_num.setText((String) cont[0].get("numTelemovel"));
 
        } else {
            
            Alert alertSucessoEliminar = new Alert(Alert.AlertType.ERROR);
            alertSucessoEliminar.setTitle("Dados da Conta");
            alertSucessoEliminar.setHeaderText("");
            alertSucessoEliminar.setContentText("Ocorreu um erro. Tente mais tarde.");
            alertSucessoEliminar.show(); 
            
        }
    }    
    
}
