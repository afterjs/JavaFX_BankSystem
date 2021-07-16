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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class MenuBancarioClientController implements Initializable {
dbQuerys db = new dbQuerys();
    
    @FXML
    private Text label_estadoContaPendete;

    @FXML
    private Text label_estadoContaAceite;

    @FXML
    void dadosPessoais(MouseEvent event) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Menu Bancario.");
        alert.setHeaderText("O que deseja visualizar?");
        alert.setContentText("Escolha a opção");

        ButtonType buttonTypeOne = new ButtonType("Ver Dados Pessoais");
        ButtonType buttonTypeTwo = new ButtonType("Editar Dados");
        ButtonType buttonTypeCancel = new ButtonType("Fechar", ButtonData.CANCEL_CLOSE);
        
       
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
          try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/client_mostrarDados.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Ver dados Pessoais");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (result.get() == buttonTypeTwo) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/client_editarDados.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Editar dados da Conta");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }         

    }

    @FXML
    void efetuarMarcacao(MouseEvent event) {
        
        if(!db.verificaMarcacoes()) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/client_efetuarMarcacao.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Efetuar Marcação");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            
             Alert cnSolicitada = new Alert(Alert.AlertType.ERROR);
            cnSolicitada.setTitle("Efetuar Marcação");
            cnSolicitada.setHeaderText("Já tem uma marcação Pendente!");
            cnSolicitada.show(); 
            
        }
        
        
        

    }

    @FXML
    void efetuarMovimentos(MouseEvent event) {
         try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/fazerMovimentos.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Movimentos");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MenuBancarioClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    

    @FXML
    void saldoMovimentos(MouseEvent event) {
        
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Menu Bancario.");
        alert.setHeaderText("O que deseja visualizar?");
        alert.setContentText("Escolha a opção");

        ButtonType buttonTypeOne = new ButtonType("Ver Saldo");
        ButtonType buttonTypeTwo = new ButtonType("Extrato");
        ButtonType buttonTypeCancel = new ButtonType("Fechar", ButtonData.CANCEL_CLOSE);
        
       
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            personalData.saldo = db.verificaSaldo();
            
            Alert alertSaldo = new Alert(Alert.AlertType.INFORMATION);
            alertSaldo.setTitle("Visualizar Saldo");
            alertSaldo.setHeaderText("Saldo Total Liquido -  " + personalData.saldo+"€");
            alertSaldo.showAndWait();

        } else if (result.get() == buttonTypeTwo) {
             if( db.gerarcsv(personalData.idContaBancaria)) {
                Alert alertaExtrato = new Alert(Alert.AlertType.INFORMATION);
                alertaExtrato.setTitle("Extrato Bancario");
                alertaExtrato.setHeaderText("Local do extrato - " + System.getProperty("user.dir"));
                alertaExtrato.showAndWait();
             }
             else {
                Alert alertaExtrato = new Alert(Alert.AlertType.ERROR);
                alertaExtrato.setTitle("Extrato Bancario");
                alertaExtrato.setHeaderText("Ocorreu um erro ao fazer o extrato bancario.");
                alertaExtrato.showAndWait();
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
                Logger.getLogger(MenuBancarioClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    void voltar(MouseEvent event) {
       
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/SelectContasBancarias.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Menu Cliente");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MenuBancarioClientController.class.getName()).log(Level.SEVERE, null, ex);
            }

    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
