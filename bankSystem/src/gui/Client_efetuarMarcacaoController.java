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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class Client_efetuarMarcacaoController implements Initializable {
    
    snippets snp = new snippets();
    dbQuerys db = new dbQuerys();

    @FXML
    private Text label_agencia;

    @FXML
    private DatePicker data_marc;

    @FXML
    private TextField horas_marc;

    @FXML
    void efetuarMarcacao(MouseEvent event) {
        
         try{
             
             int hora = parseInt(horas_marc.getText());
             
             if(hora < 9 || hora > 14 ) {
                  Alert alertHoraInv = new Alert(Alert.AlertType.WARNING);
                alertHoraInv.setTitle("Erro fazer marcação");
                alertHoraInv.setHeaderText("Só podes fazer marcações enter as 09:00 e 14:00");
                alertHoraInv.show();  
                return;
             }
             
             if(data_marc.getValue() == null) {
               Alert alertDatInv = new Alert(Alert.AlertType.WARNING);
               alertDatInv.setTitle("Erro fazer marcação");
               alertDatInv.setHeaderText("DATA INVALIDA dd/mm/aaaa");
               alertDatInv.show();  
                return;
            } 
           
            String data = data_marc.getValue() + "";
            
             if(db.inserirMarcacao(data + " | "+hora + " horas"))  {
                 Alert alertMarcado = new Alert(Alert.AlertType.INFORMATION);
               alertMarcado.setTitle("Marcações");
               alertMarcado.setHeaderText("A sua marcação foi efetuada.\nData - " + data + " | "+hora + " horas");
               alertMarcado.show(); 
               
               try {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/menuBancarioClient.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Menu Bancario");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(Client_efetuarMarcacaoController.class.getName()).log(Level.SEVERE, null, ex);
            }
               
                return;
             } else {
                  Alert alertErroMarc = new Alert(Alert.AlertType.ERROR);
               alertErroMarc.setTitle("Erro ao fazer marcação");
               alertErroMarc.setHeaderText("Ocorreu um erro, tente mais tarde.");
               alertErroMarc.show();  
             }
            
             
        } catch(Exception e) {
               Alert alertNifUsado = new Alert(Alert.AlertType.ERROR);
               alertNifUsado.setTitle("Erro ao criar conta");
               alertNifUsado.setHeaderText("Formato Invalido. Formato Exemplo - 14");
               alertNifUsado.show();  
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
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/menuBancarioClient.fxml"));
                Scene regCena = new Scene (root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Menu Cliente");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
            }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
