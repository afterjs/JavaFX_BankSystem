/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import banksystem.dbQuerys;
import banksystem.personalData;
import banksystem.snippets;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class Client_mostrarDadosController implements Initializable {
    dbQuerys db = new dbQuerys();
    snippets snp = new snippets();
    
    
    @FXML
    private TextField field_nomeAg;

    @FXML
    private TextField field_num;

    @FXML
    private TextField field_situacao;

    @FXML
    private TextField field_nomeC;

    @FXML
    private TextField field_abertura;

    @FXML
    private TextField field_iban;

    @FXML
    private TextField field_CodCC;

    @FXML
    private TextField field_nif;

    @FXML
    private TextField field_dataN;

    @FXML
    private TextField field_idade;

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
                Logger.getLogger(MenuBancarioClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    void voltar(MouseEvent event) {
       
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/menuBancarioClient.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Menu Bancario");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MenuBancarioClientController.class.getName()).log(Level.SEVERE, null, ex);
            }

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Map[] maps = db.dadosConta(personalData.idContaBancaria);

      field_nomeAg.setText((String)maps[0].get("nomeAgencia"));
      field_num.setText((String)maps[0].get("numTelemovel"));      
      field_situacao.setText((String)maps[0].get("situacaoLaboral"));
      field_nomeC.setText((String)maps[0].get("nome"));
      field_abertura.setText((String)maps[0].get("dataAbertura"));
      field_iban.setText((String)maps[0].get("iban"));
      field_CodCC.setText((String)maps[0].get("CodCC"));
      field_nif.setText(personalData.identificador+"");
      field_dataN.setText((String)maps[0].get("dtNascimento"));
      field_idade.setText((String)maps[0].get("idade"));
  
        
    }    
    
}
