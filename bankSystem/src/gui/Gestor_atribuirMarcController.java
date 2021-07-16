/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import banksystem.dbQuerys;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class Gestor_atribuirMarcController implements Initializable {
    dbQuerys db = new dbQuerys();

     @FXML
    private Label label_ag;

    @FXML
    private Button btn_marc;

    @FXML
    private ChoiceBox<String> funcCombo;
    
    @FXML
    private ChoiceBox<String> marcCombo;
    
    @FXML
    private TextField field_marcacao;

    @FXML
    private Label label_func;

    @FXML
    private Label label_marcacao;

    @FXML
    private Label label_data;

    @FXML
    void atribuirMarcacao(MouseEvent event) {
        
        
        String dataHora = label_data.getText();
        String func = label_func.getText();

        if(dataHora.equals("")) {
              Alert alertAtivarAg = new Alert(Alert.AlertType.WARNING);
                alertAtivarAg.setTitle("Atribuir Marcação");
                alertAtivarAg.setHeaderText("");
                alertAtivarAg.setContentText("Escolhe uma marcação");
                alertAtivarAg.show();
            
            return;
        }
        
        if(func.equals("")) {
                Alert alertAtivarAg = new Alert(Alert.AlertType.WARNING);
                alertAtivarAg.setTitle("Atribuir Marcação");
                alertAtivarAg.setHeaderText("");
                alertAtivarAg.setContentText("Escolhe um Gestor.");
                alertAtivarAg.show();
            
            return;
        }
        
        
        String currentComboMarc = (marcCombo.getSelectionModel().getSelectedItem()).trim();
        String currentDataGestor = (funcCombo.getSelectionModel().getSelectedItem()).trim();
        
        
        String idMarc = currentComboMarc.substring(0, currentComboMarc.indexOf("|"));
        String nifGestor = currentDataGestor.substring(currentDataGestor.indexOf("|") + 1);
       
        int idMarcFinal = parseInt(idMarc.trim());
        int nifGestorFinal = parseInt(nifGestor.trim());
        
        
        
        if(db.atribuirMarcacao(idMarcFinal, nifGestorFinal)) {
            Alert alertSucessoEliminar = new Alert(Alert.AlertType.INFORMATION);
                alertSucessoEliminar.setTitle("Atribuir Marcação");
                alertSucessoEliminar.setHeaderText("");
                alertSucessoEliminar.setContentText("Marcação Atribuida com Sucesso!");
                alertSucessoEliminar.show(); 

                try {
               Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_menu.fxml"));
               Scene regCena = new Scene (root);
               Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
               stage.setScene(regCena);
               stage.setTitle("Area Gestor");
               stage.show();
           } catch (IOException ex) {
               Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
           }
        } else {
             Alert alertSucessoEliminar = new Alert(Alert.AlertType.ERROR);
                alertSucessoEliminar.setTitle("Atribuir Marcação");
                alertSucessoEliminar.setHeaderText("");
                alertSucessoEliminar.setContentText("Ocorreu um erro ao atribuir uma marcação.");
                alertSucessoEliminar.show(); 

                try {
               Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_menu.fxml"));
               Scene regCena = new Scene (root);
               Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
               stage.setScene(regCena);
               stage.setTitle("Area Gestor");
               stage.show();
           } catch (IOException ex) {
               Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
       // )
        
        
        
        
        
        

    }

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
    void voltar(MouseEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_menu.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Admin");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    boolean firsCombo = false;
    boolean secondCombo = false;
         
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
         btn_marc.setDisable(true);
         Map[] funcs = db.todosFuncionarios();
         Map[] marc = db.marcacoes();
    
         
        for(int i= 0; i < funcs.length; i++) {
            System.out.println("ENTROU 1");
            if(funcs[i] != null) {
                 String name  = ((String)funcs[i].get("nome")).trim();
                 String nif = ((String)funcs[i].get("nif")).trim();
                 funcCombo.getItems().addAll(name + " | " + nif); 
            }
        }
        
        
        for(int x= 0; x < marc.length; x++) {
            System.out.println("ENTROU 2");
            if(marc[x] != null) {
                 String idMarc  = ((String)marc[x].get("id")).trim();
                 String NomeMarc = ((String)marc[x].get("nome")).trim();
                 marcCombo.getItems().addAll(idMarc + " | " + NomeMarc);
            }
        }
        

        
        funcCombo.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> arg2, String arg3, String arg4) -> {
            
            firsCombo = true;
            
            
            if(firsCombo && secondCombo) {
                 btn_marc.setDisable(false);
            }
            
            
            String currentDataAccount = (funcCombo.getSelectionModel().getSelectedItem()).trim();
            String funcCombo = currentDataAccount;
            funcCombo = funcCombo.substring(0, funcCombo.indexOf(("|")));
            label_func.setText(funcCombo);
        });
         
        marcCombo.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> arg2, String arg3, String arg4) -> {
           secondCombo = true;
           
            if(firsCombo && secondCombo) {
                 btn_marc.setDisable(false);
            }
           String marcNome;
           String dataHora;
           
           String currentDataAccount = (marcCombo.getSelectionModel().getSelectedItem()).trim();
           
           
           
            for (int x = 0; x < marc.length; x++) {
                if (marc[x] != null) {
                    
                    String idP = ((String) marc[x].get("id")).trim();
                    String nomeP = ((String) marc[x].get("nome")).trim();
                    String compare = null;
                    compare = idP + " | " + nomeP;

                   if (compare.equals(currentDataAccount)) {
                        marcNome = (String) marc[x].get("nome");
                        dataHora = (String) marc[x].get("dataHora");
                        label_marcacao.setText(marcNome);
                        label_data.setText(dataHora);
                       break;
                    }

                }
            }
            
            
           
           

        });
        
        
        
        
    }    
    
}
