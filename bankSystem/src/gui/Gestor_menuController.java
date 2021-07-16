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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class Gestor_menuController implements Initializable {
dbQuerys db = new dbQuerys();
    snippets snp = new snippets();
    
     @FXML
    void dadosConta(MouseEvent event) {
        
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Verificar Dados Bancarios");
        dialog.setHeaderText("");
        dialog.setContentText("Identificador da Conta  ");

        Optional<String> result = dialog.showAndWait();
        
        if (result.isPresent()) {
            
            try{
                int id = parseInt(result.get());
                
                if(db.existeCB(id)) {
                        personalData.gestor_idBancario_user = id;
                        
                         try {
                            Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_verDados.fxml"));
                            Scene regCena = new Scene (root);
                            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                            stage.setScene(regCena);
                            stage.setTitle("Visualizar Dados");
                            stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        
                } else {
                    Alert alertNifUsado = new Alert(Alert.AlertType.ERROR);
                    alertNifUsado.setTitle("Dados da Conta");
                    alertNifUsado.setHeaderText("Identificador Invalido");
                    alertNifUsado.show(); 
                }
                

              }catch(Exception e) {
                Alert alertNifUsado = new Alert(Alert.AlertType.ERROR);
                alertNifUsado.setTitle("Dados da Conta");
                alertNifUsado.setHeaderText("Apenas numeros");
                alertNifUsado.show(); 
              }
            
        }

    }

    @FXML
    void extrato(MouseEvent event) {
        
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Extrato Bancario");
        dialog.setHeaderText("");
        dialog.setContentText("Identificador da Conta  ");

        Optional<String> result = dialog.showAndWait();
        
        if (result.isPresent()) {
            
            try{

                int idConta = parseInt(result.get());
            
                if( db.gerarcsv(idConta)) {
                    
                    Alert alertSucesso = new Alert(Alert.AlertType.INFORMATION);
                    alertSucesso.setTitle("Extrato Bancario");
                    alertSucesso.setHeaderText("Ficheiro Gerado em " + System.getProperty("user.dir"));
                    alertSucesso.show(); 

                } else {
                    Alert alertNifUsado = new Alert(Alert.AlertType.ERROR);
                    alertNifUsado.setTitle("Erro ao gerar ficheiro");
                    alertNifUsado.setHeaderText("ID INVALIDO");
                    alertNifUsado.show(); 
                }

              }catch(Exception e) {
                Alert alertNifUsado = new Alert(Alert.AlertType.ERROR);
                alertNifUsado.setTitle("Extrato Bancario");
                alertNifUsado.setHeaderText("Identificador Invalido");
                alertNifUsado.show(); 
              }
            
        }
        
    }

    @FXML
    void gerirFunc(MouseEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Area Gestor");
        alert.setHeaderText("O que deseja visualizar?");
        alert.setContentText("Escolha a opção");

        ButtonType buttonTypeOne = new ButtonType("Adicionar Funcionario");
        ButtonType buttonTypeTwo = new ButtonType("Eliminar Funcionario");
        ButtonType buttonTypeCancel = new ButtonType("Fechar", ButtonBar.ButtonData.CANCEL_CLOSE);
        
       
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
          try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_adicionarFunc.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Adicionar Funcionario");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (result.get() == buttonTypeTwo) {
           try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_removerFunc.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Remover Funcionario");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }         
        
        

    }

    @FXML
    void gerirMarc(MouseEvent event) {
          Map[] funcs = db.todosFuncionarios();
          Map[] marc = db.marcacoes();
        
        if(funcs.length != 0 && marc.length != 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Area Gestor");
            alert.setHeaderText("O que deseja visualizar?");
            alert.setContentText("Escolha a opção");

            ButtonType buttonTypeOne = new ButtonType("Atribuir Marcações");
            ButtonType buttonTypeTwo = new ButtonType("Eliminar Marcações");
            ButtonType buttonTypeCancel = new ButtonType("Fechar", ButtonBar.ButtonData.CANCEL_CLOSE);
        
       
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
          try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_atribuirMarc.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Eliminar Marcação");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (result.get() == buttonTypeTwo) {
           try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_eliminarMarc.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Remover Marcação");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }         
        } else {
                Alert alertNifUsado = new Alert(Alert.AlertType.ERROR);
                    alertNifUsado.setTitle("Atribuir Marcação");
                    alertNifUsado.setHeaderText("De momento não temos marcações ou funcionarios.");
                    alertNifUsado.show(); 
        }
        
        
        

    }

    @FXML
    void gerirPedidosConta(MouseEvent event) {
        
        Map[] maps = db.contasSolicitadasAll();
        int contador = 0;

        for (int i = 0; i < maps.length; i++) {
            if (maps[i] != null) {
               contador++;
            }
        }
        
        
        if(contador >0) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_gerirContas.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Gerir Pedidos");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alertSemContas = new Alert(Alert.AlertType.ERROR);
            alertSemContas.setTitle("Gerir Pedidos");
            alertSemContas.setHeaderText("");
            alertSemContas.setContentText("Sem nenhum pedido pendente.");
            alertSemContas.show();
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
     
    }    
    
}
