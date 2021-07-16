/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import banksystem.dbQuerys;
import banksystem.snippets;

import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.Optional;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class RegisterController implements Initializable {

        @FXML
    private TextField field_username;

    @FXML
    private TextField field_Situacao;

    @FXML
    private TextField field_nomeCompleto;

    @FXML
    private TextField field_nif;

    @FXML
    private TextField field_morada;

    @FXML
    private TextField field_cidade;

    @FXML
    private TextField field_cPostal;

    @FXML
    private DatePicker field_DataN;

    @FXML
    private PasswordField field_password;

    @FXML
    private TextField field_numTelemovel;

    @FXML
    private TextField field_numCivil;
    
    
    snippets snp = new snippets();
    dbQuerys db = new dbQuerys();
    
    
    
   @FXML
    void backLogin(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/login.fxml"));
            Scene regCena = new Scene (root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
               stage.setTitle("Login");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
   public void registerAccount(MouseEvent event) {
        try{
           
            String nifCheck = field_nif.getText();
            int nif;
            
            if (nifCheck.length() > 9) {
                nif = parseInt(nifCheck.substring(0, 9));
            } else {
                nif = parseInt(nifCheck);
            }
            
            if(db.verificaNIFCriado(nif)) {
               Alert alertNifCriado = new Alert(Alert.AlertType.WARNING);
               alertNifCriado.setTitle("Erro ao criar conta");
               alertNifCriado.setHeaderText("Esse nif já se encontra em uso.");
               alertNifCriado.show(); 
                return;
            }
            
            if(field_DataN.getValue() == null) {
               Alert alertDatInv = new Alert(Alert.AlertType.WARNING);
               alertDatInv.setTitle("Erro ao criar conta");
               alertDatInv.setHeaderText("DATA INVALIDA dd/mm/aaaa");
               alertDatInv.show();  
                return;
            } 

            if(field_username.getText().equals("") || field_Situacao.getText().equals("") 
            || field_nomeCompleto.getText().equals("") || field_morada.getText().equals("")
            || field_cidade.getText().equals("") || field_cPostal.getText().equals("")
            || field_password.getText().equals("")   
            || field_numTelemovel.getText().equals("") || field_numCivil.getText().equals("")) {
                
            Alert alertCamposPreenc = new Alert(Alert.AlertType.ERROR);
            alertCamposPreenc.setTitle("Erro ao criar conta");
            alertCamposPreenc.setHeaderText("Todos os campos tem de ser preenchidos corretamente.");
            alertCamposPreenc.show();  
            } else {
             
            String username = field_username.getText();
            
            if(db.verificaUsernameCriado(username)) {
               Alert alertUser = new Alert(Alert.AlertType.ERROR);
               alertUser.setTitle("Erro ao criar conta");
               alertUser.setHeaderText("Esse username já se encontra em uso.");
               alertUser.show(); 
               return;
            }
            
            
            String situacao = field_Situacao.getText();
            String nomeC = field_nomeCompleto.getText();
            
            
           
            String morada = field_morada.getText();
            String cidade = field_cidade.getText();
            String cPostal = field_cPostal.getText();


            String dataN = field_DataN.getValue() + "";
            int barraInicial = dataN.indexOf("-");
            int barraFinal = dataN.lastIndexOf("-");
            String dia = "";
            String mes = "";
            String ano = "";
            ano = dataN.substring(0, barraInicial);
            mes = dataN.substring(barraInicial + 1, barraFinal);
            dia= dataN.substring(barraFinal + 1);
            String dtFinal = dia+"/"+mes+"/"+ano;


            String password = field_password.getText();
            String numTelemovel = field_numTelemovel.getText();
            String numCivil = field_numCivil.getText();
            int idade = snp.calcularIdade(dtFinal);  
            
            if(idade < 18) {
               Alert alertIdade = new Alert(Alert.AlertType.WARNING);
               alertIdade.setTitle("Erro ao criar conta");
               alertIdade.setHeaderText("Para criar conta tens de ser maior de idade");
               alertIdade.show();  
               return;
            } 
                
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Criar Conta");
            alert.setHeaderText("Desejas criar uma nova conta?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){
               boolean registarConta = db.registarConta(nif, nomeC, dtFinal, idade, numCivil, situacao, numTelemovel, username, password, morada, cPostal, cidade, 0);
            
               if(registarConta) {
                   Alert cnCriada = new Alert(Alert.AlertType.INFORMATION);
                   cnCriada.setTitle("Registar Conta");
                   cnCriada.setHeaderText("A sua conta foi registada com sucesso!");
                   cnCriada.show();  
                  try {
                    Parent root = FXMLLoader.load(getClass().getResource("/GUI/login.fxml"));
                    Scene regCena = new Scene (root);
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(regCena);
                       stage.setTitle("Login");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                } else {
                     Alert alertNifUsado = new Alert(Alert.AlertType.ERROR);
                    alertNifUsado.setTitle("Erro ao criar conta");
                    alertNifUsado.setHeaderText("Ocorreu um erro ao criar a conta. Tente mais tarde.");
                    alertNifUsado.show();  
               }
            
            }
            } 
        } catch(Exception e) {
               Alert alertNifUsado = new Alert(Alert.AlertType.ERROR);
               alertNifUsado.setTitle("Erro ao criar conta");
               alertNifUsado.setHeaderText("O NIF tem de ser um numero");
               alertNifUsado.show();  
        }
 

        
    }
   

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
