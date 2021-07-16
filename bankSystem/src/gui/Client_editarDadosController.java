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
import java.time.LocalDate;
import java.util.Date;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class Client_editarDadosController implements Initializable {
    private boolean editar = false;
    dbQuerys db = new dbQuerys();
    snippets snp = new snippets();

    @FXML
    private TextField field_nome;

    @FXML
    private TextField field_numero;
    
    @FXML
    private TextField field_situacao;

    @FXML
    private DatePicker field_dataN;
    
    @FXML
    private TextField field_dtNastring;
    
    @FXML
    private Button btn_editar;
    
     @FXML
    private Button btn_atualizar;

    @FXML
    void atualizarDados(MouseEvent event) {
        int cont = 0;

         if(!field_nome.getText().equals("")  ) { 
               db.updateNome(field_nome.getText(), personalData.identificador);
               personalData.nome = field_nome.getText();
               cont++;
         } 
         
          if(!field_numero.getText().equals("")  ) { 
              db.updateTelemovel(field_numero.getText(),personalData.identificador);
              personalData.numTelemovel = field_numero.getText();
               cont++;
         } 
          
         if(!field_situacao.getText().equals("")  ) { 
              db.updateSituacaoLaboral(field_situacao.getText(), personalData.identificador);
               personalData.situacaoLaboral = field_situacao.getText();
               cont++;
         } 
         
         if(field_dataN.getValue() != null) {
           
             
            String dataN = field_dataN.getValue() + "";
            int barraInicial = dataN.indexOf("-");
            int barraFinal = dataN.lastIndexOf("-");
            String dia = "";
            String mes = "";
            String ano = "";
            ano = dataN.substring(0, barraInicial);
            mes = dataN.substring(barraInicial + 1, barraFinal);
            dia= dataN.substring(barraFinal + 1);
            String dtFinal = dia+"/"+mes+"/"+ano;
            
            int idade = snp.calcularIdade(dtFinal);  
            
            if(idade < 18) {
               Alert alertIdade = new Alert(Alert.AlertType.WARNING);
               alertIdade.setTitle("Erro ao criar conta");
               alertIdade.setHeaderText("Para criar conta tens de ser maior de idade");
               alertIdade.show();  
               return;
            } 
             db.updateDataNascimento(dtFinal, idade, personalData.identificador);
             personalData.dtNascimento = dtFinal;
             cont++;
        }
         
         
         if(cont == 0) {
             Alert alertIdade = new Alert(Alert.AlertType.WARNING);
             alertIdade.setTitle("Editar Dados");
             alertIdade.setHeaderText("Nenhum campo foi editado.");
             alertIdade.show();  
             editarOFF();
             return;
         } else {
              Alert alertIdade = new Alert(Alert.AlertType.INFORMATION);
               alertIdade.setTitle("Editar Dados");
               alertIdade.setHeaderText("Os dados foram editados com sucesso!");
               alertIdade.show();  
               editarOFF();
               return;
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

    @FXML
    void modoEdit(MouseEvent event) {
        
        if(!editar) {
            editarON();
        } else {
            editarOFF();
        }

    }

    
    
    private void editarON() {
        editar = true;
        btn_editar.setText("Modo Edição ON");
        field_dataN.setVisible(true);
        field_dtNastring.setVisible(false);
        field_nome.setText("");         
        field_numero.setText(""); 
        field_situacao.setText(""); 
        field_dtNastring.setText("");           
        btn_atualizar.setDisable(false);
        field_nome.setDisable(false);
        field_nome.setDisable(false);       
        field_numero.setDisable(false);
        field_situacao.setDisable(false); 
    }
    
    private void editarOFF() {
        editar = false;
        btn_editar.setText("Modo Edição OFF");
        field_dataN.setVisible(false);
        field_dtNastring.setVisible(true);
        field_nome.setText(personalData.nome);         
        field_numero.setText(personalData.numTelemovel); 
        field_situacao.setText(personalData.situacaoLaboral); 
        field_dtNastring.setText(personalData.dtNascimento);
        btn_atualizar.setDisable(true);
        field_nome.setDisable(true);
        field_nome.setDisable(true);       
        field_numero.setDisable(true);
        field_situacao.setDisable(true);
        
    }
 
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO


        field_dataN.setVisible(false);
        field_dtNastring.setVisible(true);
        field_nome.setDisable(true);
        field_nome.setDisable(true);       
        field_numero.setDisable(true);
        field_situacao.setDisable(true);
        field_nome.setText(personalData.nome);         
        field_numero.setText(personalData.numTelemovel); 
        field_situacao.setText(personalData.situacaoLaboral); 
        field_dtNastring.setText(personalData.dtNascimento);
 
    }    
    
}
