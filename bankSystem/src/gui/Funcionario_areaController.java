/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import banksystem.dbQuerys;
import banksystem.personalData;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.Map;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class Funcionario_areaController implements Initializable {

    dbQuerys db = new dbQuerys();

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
    void verAlterarDadosConta(MouseEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Definições de Conta");
        alert.setHeaderText("");
        alert.setContentText("Escolha a opção.");

        ButtonType buttonTypeOne = new ButtonType("Ver Dados");
        ButtonType buttonTypeTwo = new ButtonType("Editar Dados");
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeOne) {

            TextInputDialog dialogVerDados = new TextInputDialog("");
            dialogVerDados.setTitle("Verificar Dados Bancarios");
            dialogVerDados.setHeaderText("");
            dialogVerDados.setContentText("Identificador da Conta  ");

            Optional<String> resultEscolha = dialogVerDados.showAndWait();

            if (resultEscolha.isPresent()) {

                try {
                    int id = parseInt(resultEscolha.get());

                    if (db.existeCB(id)) {
                        personalData.gestor_idBancario_user = id;
                        System.out.println("AQUI ID " + personalData.gestor_idBancario_user);
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("/GUI/funcionario_mostrarDados.fxml"));
                            Scene regCena = new Scene(root);
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(regCena);
                            stage.setTitle("Visualizar Dados");
                            stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(Funcionario_areaController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {
                        Alert alertNifUsado = new Alert(Alert.AlertType.ERROR);
                        alertNifUsado.setTitle("Dados da Conta");
                        alertNifUsado.setHeaderText("Identificador Invalido");
                        alertNifUsado.show();
                    }

                } catch (Exception e) {
                    Alert alertNifUsado = new Alert(Alert.AlertType.ERROR);
                    alertNifUsado.setTitle("Dados da Conta");
                    alertNifUsado.setHeaderText("Apenas numeros");
                    alertNifUsado.show();

                }
            }

        } else if (result.get() == buttonTypeTwo) {

            TextInputDialog dialogEditarDados = new TextInputDialog("");
            dialogEditarDados.setTitle("Editar Dados Bancarios");
            dialogEditarDados.setHeaderText("");
            dialogEditarDados.setContentText("NIF da Conta  ");

            Optional<String> resultaNifE = dialogEditarDados.showAndWait();

            if (resultaNifE.isPresent()) {

                try {
                    int nifInput = parseInt(resultaNifE.get().trim());
                    Map[] data = db.dadosContaFunc(nifInput);
                        if(data.length > 0) {
                            System.out.println("Pode dar redirect");
                            personalData.funcinario_nif_user = nifInput;
                            
                            System.out.println("debug " + personalData.funcinario_nif_user);

                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("/GUI/funcionario_editarDadosConta.fxml"));
                            Scene regCena = new Scene(root);
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(regCena);
                            stage.setTitle("Visualizar Dados");
                            stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(Funcionario_areaController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }  else {  
                        System.out.println(" NAO Pode dar redirect");
                        Alert alertNifInvalido = new Alert(Alert.AlertType.ERROR);
                        alertNifInvalido.setTitle("Dados da Conta");
                        alertNifInvalido.setHeaderText("NIF Invalido");
                        alertNifInvalido.show();
                    }

                } catch (Exception e) {
                    Alert alertApenasNum = new Alert(Alert.AlertType.ERROR);
                    alertApenasNum.setTitle("Dados da Conta");
                    alertApenasNum.setHeaderText("Apenas numeros");
                    alertApenasNum.show();

                }
            }
   
        }

    }

    @FXML
    void verAlterarMarcacoes(MouseEvent event) {
        
       Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Gestão de Marcações");
        alert.setHeaderText("O que deseja visualizar?");
        alert.setContentText("Escolha a opção");

        ButtonType buttonTypeOne = new ButtonType("Ver Marcações");
        ButtonType buttonTypeTwo = new ButtonType("Atualizar Marcaçoes");
        ButtonType buttonTypeCancel = new ButtonType("Fechar", ButtonData.CANCEL_CLOSE);
        
       
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
        
             Map[] marcAtri = db.marcacoesAtribuidas();
            
            if(marcAtri.length > 0 ) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/GUI/funcionario_verMarcacoes.fxml"));
                    Scene regCena = new Scene (root);
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(regCena);
                    stage.setTitle("Marcações");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Alert alertApenasNum = new Alert(Alert.AlertType.WARNING);
                alertApenasNum.setTitle("Atribuição de Marcações");
                alertApenasNum.setHeaderText("Não tens nenhum marcação atribuida");
                alertApenasNum.show();
            }     
        } else if (result.get() == buttonTypeTwo) {
           
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/funcionario_editarMarcacao.fxml"));
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

    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }

}
