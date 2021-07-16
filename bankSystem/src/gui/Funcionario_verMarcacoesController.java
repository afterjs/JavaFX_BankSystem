/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import banksystem.dbQuerys;
import banksystem.snippets;
import java.io.IOException;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class Funcionario_verMarcacoesController implements Initializable {
    dbQuerys db = new dbQuerys();
    snippets snp = new snippets();

    
    @FXML
    private ListView<String> viewMarcacoes;

    @FXML
    private Label label_nome;

    @FXML
    private Label label_data;

    @FXML
    private Label label_descri;


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
                  String idMarc = (String) marcAtri[i].get("id");
                  String nome = (String) marcAtri[i].get("nomeCliente");
                  
                  viewMarcacoes.getItems().addAll( idMarc +" | " +nome);
              }
            }
         
         
          viewMarcacoes.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> arg2, String arg3, String arg4) -> {
                    
                    String currentDataAccount = (viewMarcacoes.getSelectionModel().getSelectedItem()).trim();
                   

                    for(int x = 0; x < marcAtri.length;x++) {  
                        if (marcAtri[x] != null) {
                            String id = ((String) marcAtri[x].get("id")).trim();
                            String nome = ((String) marcAtri[x].get("nomeCliente")).trim();
                            String compare = null;
                            compare = id + " | " + nome;
                            
                            if(compare.equals(currentDataAccount)) {
                                
                                String name = (String)marcAtri[x].get("nomeCliente");
                                String date = (String)marcAtri[x].get("data");
                                String descricao = (String)marcAtri[x].get("descricao");


                                label_nome.setText(name);
                                label_data.setText(date);
                                label_descri.setText(descricao);
                             
                                
                                
                                break;
                            }
                           
                        }
                      }
                    
              });
         
         
         
    }    
    
}
