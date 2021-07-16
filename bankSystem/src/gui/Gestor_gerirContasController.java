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
import java.util.Map;
import java.util.Optional;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author After
 */
public class Gestor_gerirContasController implements Initializable {

    dbQuerys db = new dbQuerys();
    snippets snp = new snippets();
    @FXML
    private ListView<String> ViewContas;

    @FXML
    private Label label_nome;

    @FXML
    private Button btnAceitar;

    @FXML
    private Button btn_recusar;

    @FXML
    private Label label_agencia;

    @FXML
    private Label label_idAgencia;

    @FXML
    private Label label_nif;

    @FXML
    void aceitarP(MouseEvent event) {

        String nif = label_nif.getText();
        String nota = null;
        String iban = snp.gerar();
        String idAg = label_idAgencia.getText();

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Inserir Nota");
        dialog.setHeaderText("");
        dialog.setContentText("Não Obrigatorio");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            nota = result.get();

            if (!nota.equals((""))) {
                if (db.mudarEstadoSolicitada(parseInt(nif), 0, 1, nota)) {
                    db.gerarConta(parseInt(nif), iban, parseInt(idAg));

                    Alert alertAtivarAg = new Alert(Alert.AlertType.INFORMATION);
                    alertAtivarAg.setTitle("Pedidos de Conta");
                    alertAtivarAg.setHeaderText("");
                    alertAtivarAg.setContentText("Conta Aceite com Sucesso!");
                    alertAtivarAg.show();

                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_menu.fxml"));
                        Scene regCena = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(regCena);
                        stage.setTitle("Gestão de Agencias");
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    Alert alertErroAg = new Alert(Alert.AlertType.ERROR);
                    alertErroAg.setTitle("Pedidos de Conta");
                    alertErroAg.setHeaderText("");
                    alertErroAg.setContentText("Ocorreu um erro ao ativar a agência!\nTente mais tarde");
                    alertErroAg.show();

                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_menu.fxml"));
                        Scene regCena = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(regCena);
                        stage.setTitle("Gestão de Agencias");
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            } else {
                nota = "Sem Nota";
                if (db.mudarEstadoSolicitada(parseInt(nif), 0, 1, nota)) {
                    db.gerarConta(parseInt(nif), iban, parseInt(idAg));
                    Alert alertAtivarAg = new Alert(Alert.AlertType.INFORMATION);
                    alertAtivarAg.setTitle("Pedidos de Conta");
                    alertAtivarAg.setHeaderText("");
                    alertAtivarAg.setContentText("Conta Aceite com Sucesso!");
                    alertAtivarAg.show();

                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_menu.fxml"));
                        Scene regCena = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(regCena);
                        stage.setTitle("Gestão de Agencias");
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    Alert alertErroAg = new Alert(Alert.AlertType.ERROR);
                    alertErroAg.setTitle("Pedidos de Conta");
                    alertErroAg.setHeaderText("");
                    alertErroAg.setContentText("Ocorreu um erro ao ativar a agência!\nTente mais tarde");
                    alertErroAg.show();

                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_menu.fxml"));
                        Scene regCena = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(regCena);
                        stage.setTitle("Gestão de Agencias");
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        } else {
            return;
        }

    }

    @FXML
    void recusarP(MouseEvent event) {

        String nif = label_nif.getText();
        String nota = null;
        String iban = snp.gerar();
        String idAg = label_idAgencia.getText();

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Inserir Nota");
        dialog.setHeaderText("");
        dialog.setContentText("Não Obrigatorio");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            
            nota = result.get();

            if (!nota.equals((""))) {
                
                if (db.mudarEstadoSolicitada(parseInt(nif), 2, 1, nota)) {

                Alert alertAtivarAg = new Alert(Alert.AlertType.INFORMATION);
                alertAtivarAg.setTitle("Pedidos de Conta");
                alertAtivarAg.setHeaderText("");
                alertAtivarAg.setContentText("Conta Recusada com Sucesso!");
                alertAtivarAg.show();

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_menu.fxml"));
                    Scene regCena = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(regCena);
                    stage.setTitle("Area Gestor");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                Alert alertErroAg = new Alert(Alert.AlertType.ERROR);
                alertErroAg.setTitle("Pedidos de Conta");
                alertErroAg.setHeaderText("");
                alertErroAg.setContentText("Ocorreu um erro ao ativar a agência!\nTente mais tarde");
                alertErroAg.show();

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_menu.fxml"));
                    Scene regCena = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(regCena);
                    stage.setTitle("Area Gestor");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
                
            } else {
                
                nota = "Sem Nota";

            if (db.mudarEstadoSolicitada(parseInt(nif), 2, 1, nota)) {

                Alert alertAtivarAg = new Alert(Alert.AlertType.INFORMATION);
                alertAtivarAg.setTitle("Pedidos de Conta");
                alertAtivarAg.setHeaderText("");
                alertAtivarAg.setContentText("Conta Recusada com Sucesso!");
                alertAtivarAg.show();

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_menu.fxml"));
                    Scene regCena = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(regCena);
                    stage.setTitle("Area Gestor");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Alert alertErroAg = new Alert(Alert.AlertType.ERROR);
                alertErroAg.setTitle("Pedidos de Conta");
                alertErroAg.setHeaderText("");
                alertErroAg.setContentText("Ocorreu um erro ao ativar a agência!\nTente mais tarde");
                alertErroAg.show();

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/GUI/gestor_menu.fxml"));
                    Scene regCena = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(regCena);
                    stage.setTitle("Area Gestor");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(SelectContasBancariasController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            }

        } else {
            return;

        }

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAceitar.setDisable(true);
        btn_recusar.setDisable(true);

        Map[] maps = db.contasSolicitadasAll();

        for (int i = 0; i < maps.length; i++) {
            if (maps[i] != null) {
                String gestor = (String) maps[i].get("nome");
                String nif = (String) maps[i].get("nif");

                ViewContas.getItems().addAll((gestor.trim() + " | " + nif.trim()));
            }
        }

        ViewContas.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> arg2, String arg3, String arg4) -> {
            btnAceitar.setDisable(false);
            btn_recusar.setDisable(false);
            String currentDataAccount = (ViewContas.getSelectionModel().getSelectedItem()).trim();

            for (int x = 0; x < maps.length; x++) {
                if (maps[x] != null) {
                    String gestor = ((String) maps[x].get("nome")).trim();
                    String nif = ((String) maps[x].get("nif")).trim();
                    String compare = null;
                    compare = gestor + " | " + nif;

                    if (compare.equals(currentDataAccount)) {
                        String nomeP = (String) maps[x].get("nome");
                        String agP = (String) maps[x].get("nomeAgencia");
                        String nifP = (String) maps[x].get("nif");
                        String idAg = (String) maps[x].get("idAgencia");

                        label_nome.setText(nomeP);
                        label_idAgencia.setText(idAg);
                        label_agencia.setText(agP);
                        label_nif.setText(nifP);

                        break;
                    }

                }
            }

        });

    }

}
