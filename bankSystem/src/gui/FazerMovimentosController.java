/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import banksystem.dbQuerys;
import banksystem.personalData;
import java.io.IOException;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import java.lang.StackWalker.Option;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */

public class FazerMovimentosController implements Initializable {
    dbQuerys db = new dbQuerys();
    
    private float saldoLog;

    
    @FXML
    void depositarDinheiro(MouseEvent event) {

        TextInputDialog levantarDinheiro = new TextInputDialog("");
        levantarDinheiro.setTitle("Deposito");
        levantarDinheiro.setHeaderText("");
        levantarDinheiro.setContentText("Insira a quantidade a depositar: ");
        Optional<String> saldoLevantar = levantarDinheiro.showAndWait();
        
        if(saldoLevantar.isPresent()) {
            try{
                 Float saldo = parseFloat(saldoLevantar.get());
                 saldoLog = saldo;

               if(db.depositarDinheiro(saldo)) {
                personalData.saldo = db.verificaSaldo();
                Alert alertaExtrato = new Alert(Alert.AlertType.INFORMATION);
                alertaExtrato.setTitle("Deposito Efetuado");
                alertaExtrato.setHeaderText("");
                alertaExtrato.setContentText("Deposito - " + saldo + "€\nSalto Total - " + personalData.saldo+"€");
                
                int nifLog = personalData.identificador;
                String tipoLog = "DEPOSITO";
                String descricao = "DEPOSITOU " + saldoLog + "EUR";
                int idContaB = personalData.idContaBancaria;
                db.inserirLog(nifLog, tipoLog , descricao, idContaB);
                alertaExtrato.showAndWait();
                return;
             }
             else {
                Alert alertaSaldo = new Alert(Alert.AlertType.ERROR);
                alertaSaldo.setTitle("Deposito");
                alertaSaldo.setHeaderText("Ocorreu um erro a depositar.");
                alertaSaldo.showAndWait();
                return;
             }
  
            }catch(Exception e) {
                Alert alertNifCriado = new Alert(Alert.AlertType.ERROR);
                alertNifCriado.setTitle("Levantamento de dinheiro");
                alertNifCriado.setHeaderText("Montante Invalido 2222.22 ");
                alertNifCriado.show(); 
                return;
            }  
            
        }
        
    }

    @FXML
    void fazerPagamentos(MouseEvent event) {
        

        TextInputDialog ibanDestinoVoid = new TextInputDialog("");
        ibanDestinoVoid.setTitle("Pagamentos");
        ibanDestinoVoid.setHeaderText("");
        ibanDestinoVoid.setContentText("IBAN do destino: ");

        Optional<String> ibanDestString = ibanDestinoVoid.showAndWait();
        
        if(ibanDestString.isPresent()) {
           
         String ibanVoid = ibanDestString.get(); 
        personalData.saldo = db.verificaSaldo();

        TextInputDialog montantPagarVoid = new TextInputDialog("");
        montantPagarVoid.setTitle("Pagamentos");
        montantPagarVoid.setHeaderText("Saldo Atual " + personalData.saldo+"€");
        montantPagarVoid.setContentText("Insira o montante a pagar: ");

        Optional<String> montanTransferirTotal = montantPagarVoid.showAndWait();
        
        if(montanTransferirTotal.isPresent()) {
            try{
               Float saldo = parseFloat(montanTransferirTotal.get()); 
               saldoLog = saldo;
              
                if(personalData.saldo > saldo) {
                    
                    
                    if(db.levantarDinheiro(saldo)) {
                        personalData.saldo = db.verificaSaldo();
                        Alert alertTransferido = new Alert(Alert.AlertType.INFORMATION);
                        alertTransferido.setTitle("Pagamento Efectuado");
                        alertTransferido.setHeaderText("Montante Pago - " + saldo+"€\nDestino - " + ibanVoid+"\nSaldo Total - " + personalData.saldo+"€");
                        alertTransferido.showAndWait();
                        
                        int nifLog = personalData.identificador;
                        String tipoLog = "PAGAMENTO";
                        String descricao = "PAGAMENTO " + saldoLog + "EUR";
                        int idContaB = personalData.idContaBancaria;
                        db.inserirLog(nifLog, tipoLog , descricao, idContaB);
                        
                        
                           return;
                    } else {
                        personalData.saldo = db.verificaSaldo();
                        Alert alertTransferido = new Alert(Alert.AlertType.ERROR);
                        alertTransferido.setTitle("Pagamentos");
                        alertTransferido.setHeaderText("Ocorreu um erro. Tente mais tarde.");
                        alertTransferido.showAndWait();
                           return;
                    }
                    
                }else {
                    Alert alertSemSaldo = new Alert(Alert.AlertType.ERROR);
                    alertSemSaldo.setTitle("Pagamentos");
                    alertSemSaldo.setHeaderText("Saldo Insuficiente");
                    alertSemSaldo.show(); 
             
                return;
                    
                }
            
  
            }catch(Exception e) {
                Alert alertNifCriado = new Alert(Alert.AlertType.ERROR);
                alertNifCriado.setTitle("Pagamentos");
                alertNifCriado.setHeaderText("Montante Invalido 2222.22 ");
                alertNifCriado.show(); 
                return;
            }
  
            
        }

        }
        

    }

    
  
    
    
    
    
    
    
    @FXML
    void levantarDinheiro(MouseEvent event) {
        personalData.saldo = db.verificaSaldo();

        TextInputDialog levantarDinheiro = new TextInputDialog("");
        levantarDinheiro.setTitle("Levantamentos");
        levantarDinheiro.setHeaderText("Saldo Atual " + personalData.saldo+"€");
        levantarDinheiro.setContentText("Insira o montante a levantar: ");

        Optional<String> saldoLevantar = levantarDinheiro.showAndWait();
        
        if(saldoLevantar.isPresent()) {
            try{
               Float saldo = parseFloat(saldoLevantar.get()); 
               saldoLog = saldo;
              
                personalData.saldo = personalData.saldo - saldo;
                
                
               if(db.levantarDinheiro(saldo)) {
                Alert alertaExtrato = new Alert(Alert.AlertType.INFORMATION);
                alertaExtrato.setTitle("Levantamento de Saldo");
                alertaExtrato.setHeaderText("Saldo Levantado - " + saldo + "€\nSaldo Total - " + personalData.saldo+"€");
                alertaExtrato.showAndWait();
                
                 int nifLog = personalData.identificador;
                String tipoLog = "LEVANTAMENTO";
                String descricao = "LEVANTOU " + saldoLog + "EUR";
                int idContaB = personalData.idContaBancaria;
                db.inserirLog(nifLog, tipoLog , descricao, idContaB);
                
                return;
             }
             else {
                Alert alertaSaldo = new Alert(Alert.AlertType.WARNING);
                alertaSaldo.setTitle("Levantamento de Dinheiro");
                alertaSaldo.setHeaderText("Saldo Insuficiente");
                alertaSaldo.showAndWait();
                return;
             }
               
               
            }catch(Exception e) {
                Alert alertNifCriado = new Alert(Alert.AlertType.ERROR);
                alertNifCriado.setTitle("Levantamento de dinheiro");
                alertNifCriado.setHeaderText("Montante Invalido 2222.22 ");
                alertNifCriado.show(); 
                return;
            }
  
            
        }
    }
    
    
    private String ibanTrans;
    @FXML
    void transferencias(MouseEvent event) {
        modalIbanTrans();
    }
    
    
    private void modalIbanTrans() {
        
        personalData.saldo = db.verificaSaldo();

        TextInputDialog ibanDestino = new TextInputDialog("");
        ibanDestino.setTitle("Tranferencias");
        ibanDestino.setHeaderText("");
        ibanDestino.setContentText("IBAN DO DESTINATARIO: ");

        Optional<String> ibanDestString = ibanDestino.showAndWait();
        
        if(ibanDestString.isPresent()) {
           
             String iban = ibanDestString.get(); 

             if(db.verificaIban(iban)) {
                 ibanTrans = iban;
                modelMontanteTrans();
             } else {
                Alert alertaExtrato = new Alert(Alert.AlertType.ERROR);
                alertaExtrato.setTitle("Transferencias");
                alertaExtrato.setHeaderText("");
                alertaExtrato.setContentText("IBAN INVALIDO");
                alertaExtrato.showAndWait();
                return;
               }
    
        }
        
    }
    
    private void modelMontanteTrans() {
        personalData.saldo = db.verificaSaldo();

        TextInputDialog MontanteTransAlert = new TextInputDialog("");
        MontanteTransAlert.setTitle("TRANSFERENCIAS");
        MontanteTransAlert.setHeaderText("Saldo Atual " + personalData.saldo+"€");
        MontanteTransAlert.setContentText("Insira o montante a transferir: ");

        Optional<String> montanTransferirTotal = MontanteTransAlert.showAndWait();
        
        if(montanTransferirTotal.isPresent()) {
            try{
               Float saldo = parseFloat(montanTransferirTotal.get()); 
               saldoLog = saldo;
              
                if(personalData.saldo > saldo) {
                    
                    
                    if(db.transferirDinheiro(ibanTrans, saldo)) {
                        personalData.saldo = db.verificaSaldo();
                        Alert alertTransferido = new Alert(Alert.AlertType.INFORMATION);
                        alertTransferido.setTitle("Transferencia Efectuada");
                        alertTransferido.setHeaderText("Montante Transferido - " + saldo+"€\nDestino - " + ibanTrans+"\nSaldo Total - " + personalData.saldo+"€");
                        alertTransferido.showAndWait();
                        
                        
                        int nifLog = personalData.identificador;
                String tipoLog = "TRANSFERENCIA";
                String descricao = "TRANSFERENCIA " + saldoLog + "EUR";
                int idContaB = personalData.idContaBancaria;
                db.inserirLog(nifLog, tipoLog , descricao, idContaB);
                        
                           return;
                    } else {
                        personalData.saldo = db.verificaSaldo();
                        Alert alertTransferido = new Alert(Alert.AlertType.ERROR);
                        alertTransferido.setTitle("Transferencia");
                        alertTransferido.setHeaderText("Ocorreu um erro. Tente mais tarde.");
                        alertTransferido.showAndWait();
                           return;
                    }
                    
                }else {
                    Alert alertSemSaldo = new Alert(Alert.AlertType.ERROR);
                    alertSemSaldo.setTitle("Transferencias");
                    alertSemSaldo.setHeaderText("Saldo Insuficiente");
                    alertSemSaldo.show(); 
             
                return;
                    
                }
            
  
            }catch(Exception e) {
                Alert alertNifCriado = new Alert(Alert.AlertType.ERROR);
                alertNifCriado.setTitle("Levantamento de dinheiro");
                alertNifCriado.setHeaderText("Montante Invalido 2222.22 ");
                alertNifCriado.show(); 
                return;
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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
