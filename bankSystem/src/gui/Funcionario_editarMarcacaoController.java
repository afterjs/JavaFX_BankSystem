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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class Funcionario_editarMarcacaoController implements Initializable {
    dbQuerys db = new dbQuerys();
    snippets snp = new snippets();
    private int cont = 0;

    @FXML
    private Label label_ag;

    @FXML
    private Button btn_atua;

    @FXML
    private ChoiceBox<String> funcCombo;

    @FXML
    private Label label_func;

    @FXML
    private Label label_marcacao;

    @FXML
    private Label label_data;

    @FXML
    private TextField input_comment;

    @FXML
    private ListView<String> listView;

    @FXML
    void atualizar(MouseEvent event) {
        try {
            String currentDataAccount = (listView.getSelectionModel().getSelectedItem()).trim();
            String currentDataState = (funcCombo.getSelectionModel().getSelectedItem()).trim();
      
        if(input_comment.getText().equals("") && currentDataState != null) {
            Alert alertAtivarAg = new Alert(Alert.AlertType.WARNING);
            alertAtivarAg.setTitle("Atualizar Dados");
            alertAtivarAg.setHeaderText("");
            alertAtivarAg.setContentText("Sem dados para atualizar");
            alertAtivarAg.show(); 
            return;
        }
   
        String idEstado = null;
        int idEstadoo = 0;
        int idMarcacaoo = 0;
        String comentario = null;
        
        
         if(currentDataState != null) { 
            idEstado = (currentDataState.substring(0, currentDataState.indexOf("|"))).trim();
            idEstadoo = parseInt(idEstado.trim());
         }
        
         
          if(!input_comment.getText().equals("")  ) { 
            String idMarcacao = currentDataAccount.substring(0, currentDataAccount.indexOf("|"));
            idMarcacaoo = parseInt(idMarcacao.trim());
            comentario = input_comment.getText();
          }
        
        
        
        
        
        if(!input_comment.getText().equals("")  ) { 
            System.out.println("ENTROU AQUI ID" + idMarcacaoo + " | COMMENTT"  + comentario);
             db.updateComentarioMarcacoes(idMarcacaoo, comentario);
             cont++;
         }
        
        
         if(currentDataState != null) { 
              System.out.println("ENTROU AQUI ID" + idMarcacaoo + " | estado"  + idEstadoo);
             db.updateEstadoMarcacoes(idMarcacaoo, idEstadoo);
             cont++;
         }
        

            
        if(cont > 0 ) {
           Alert alertAtivarAg = new Alert(Alert.AlertType.WARNING);
            alertAtivarAg.setTitle("Atualizar Dados");
            alertAtivarAg.setHeaderText("");
            alertAtivarAg.setContentText("Dados Atualizados");
            alertAtivarAg.show();  
            
              try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/funcionario_area.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Funcionario Area");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
            
        }catch(Exception e) {
             Alert alertAtivarAg = new Alert(Alert.AlertType.WARNING);
            alertAtivarAg.setTitle("Atualizar Dados");
            alertAtivarAg.setHeaderText("");
            alertAtivarAg.setContentText("Todos os Campos devem estar preenchidos");
            alertAtivarAg.show(); 
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
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/funcionario_area.fxml"));
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
          
          Map[] marcAtri = db.marcacoesAtribuidas();
        
          
          for(int i = 0; i < marcAtri.length;i++) {  
              if (marcAtri[i] != null) {
                  String id = ((String) marcAtri[i].get("id")).trim();
                  String nome = ((String) marcAtri[i].get("nomeCliente")).trim();
                  String data = (String) marcAtri[i].get("data");
                  String descricao = null;
                  try {
                        descricao = ((String) marcAtri[i].get("descricao")).trim();
                  }catch(Exception e) {
                        descricao = "Sem Descrição";
                  }
                 
                  String estado = (String) marcAtri[i].get("estado");
                  listView.getItems().addAll(id+" | " +nome+ " | " + descricao);
              }
            }

                funcCombo.getItems().addAll("1 | Pendente");
                funcCombo.getItems().addAll("2 | Atribuido");
                funcCombo.getItems().addAll("3 | Finalizada");
                funcCombo.getItems().addAll("4 | Em Progresso");
                funcCombo.getItems().addAll("5 | Eliminada");
 
            
                
          
    }    
    
}
