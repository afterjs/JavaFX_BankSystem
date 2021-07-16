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
public class Funcionario_editarDadosContaController implements Initializable {
     private boolean editar = false;
     dbQuerys db = new dbQuerys();
     snippets snp = new snippets();

      private String nome;
    private String numTele;
    private String situacaoLaboral;
    private String codCc;
    private String dataN;
     
    @FXML
    private TextField field_dtNastring;

    @FXML
    private Button btn_editar;

    @FXML
    private TextField field_nome;

    @FXML
    private TextField field_numero;

    @FXML
    private TextField field_situacao;

    @FXML
    private DatePicker field_dataN;

    @FXML
    private Button btn_atualizar;

    @FXML
    private TextField field_cc;

    @FXML
    void atualizarDados(MouseEvent event) {
 int cont = 0;

         if(!field_nome.getText().equals("")  ) { 
               db.updateNome(field_nome.getText(), personalData.funcinario_nif_user);
               personalData.nome = field_nome.getText();
               cont++;
         } 
         
         if(!field_cc.getText().equals("")  ) { 
               db.updateCodCC(field_cc.getText() , personalData.funcinario_nif_user);
               personalData.nome = field_nome.getText();
               cont++;
         } 
         
          if(!field_numero.getText().equals("")  ) { 
              db.updateTelemovel(field_numero.getText(),personalData.funcinario_nif_user);
              personalData.numTelemovel = field_numero.getText();
               cont++;
         } 
          
         if(!field_situacao.getText().equals("")  ) { 
              db.updateSituacaoLaboral(field_situacao.getText(), personalData.funcinario_nif_user);
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
               alertIdade.setHeaderText("Precisas de ser maior de idade.");
               alertIdade.show();  
               return;
            } 
             db.updateDataNascimento(dtFinal, idade, personalData.funcinario_nif_user);
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
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/funcionario_area.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Funcionario Area");
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
        field_cc.setText(""); 
        field_situacao.setText(""); 
        field_dtNastring.setText("");           
        btn_atualizar.setDisable(false);
        field_nome.setDisable(false);
        field_nome.setDisable(false);  
        field_cc.setDisable(false);
        field_numero.setDisable(false);
        field_situacao.setDisable(false); 
    }
    
    private void editarOFF() {
        
        editar = false;
        Map[] data = db.dadosContaFunc(personalData.funcinario_nif_user);
        nome = (String)data[0].get("nome");
        numTele = (String)data[0].get("numTelemovel");
        situacaoLaboral = (String)data[0].get("situacaoLaboral");
        codCc = (String)data[0].get("CodCC");
        dataN = (String)data[0].get("dtNascimento");
        btn_editar.setText("Modo Edição OFF");
        field_dataN.setVisible(false);
        field_dtNastring.setVisible(true);
        field_nome.setText(nome);         
        field_numero.setText(numTele); 
        field_cc.setText(codCc); 
        field_situacao.setText(situacaoLaboral); 
        field_dtNastring.setText(dataN);
        btn_atualizar.setDisable(true);
        field_nome.setDisable(true);
        field_cc.setDisable(true);
        field_nome.setDisable(true);       
        field_numero.setDisable(true);
        field_situacao.setDisable(true);
        
    }
 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Map[] data = db.dadosContaFunc(personalData.funcinario_nif_user);
        
        nome = (String)data[0].get("nome");
        numTele = (String)data[0].get("numTelemovel");
        situacaoLaboral = (String)data[0].get("situacaoLaboral");
        codCc = (String)data[0].get("CodCC");
        dataN = (String)data[0].get("dtNascimento");
        
        // TODO

        field_dataN.setVisible(false);
        field_dtNastring.setVisible(true);
        field_nome.setDisable(true);
        field_nome.setDisable(true);       
        field_numero.setDisable(true);
        field_cc.setDisable(true);
        field_situacao.setDisable(true);
        field_nome.setText(nome);         
        field_numero.setText(numTele); 
        field_cc.setText(codCc); 
        field_situacao.setText(situacaoLaboral); 
        field_dtNastring.setText(dataN);
 
    }    
    
}
