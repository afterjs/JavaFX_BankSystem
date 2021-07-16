/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banksystem;
import static java.lang.Integer.parseInt;
import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;




public class loginRegister {

    //variaveis utilizadas no register
    private int idade;
    private int nif;
    private int tipoConta;
    private String nifCheck;
    private String nome;
    private String dtNascimento;
    private String codcc;
    private String situacaoLaboral;
    private String numTelemovel;
    private String morada;
    private String codigoP;
    private String cidade;
    private boolean existe;



    //variaveis utilizadas no register/login
    private String username;
    private String password;


    //scanners
    Scanner menuScan = new Scanner(System.in);
    Scanner infosInt = new Scanner(System.in);
    Scanner infos = new Scanner(System.in);

    //variavies snippets
    private int opcao;
    private boolean mostraErro;


    //criar instancia do ficheiro de inserir na db
    dbQuerys db = new dbQuerys();
    snippets snp = new snippets();

    // inicializar as vars como "nulas"
    public loginRegister() {
        this.nif = 0;
        this.nifCheck = "";
        this.idade = 0;
        this.nome = "";
        this.dtNascimento = "";
        this.codcc = "";
        this.situacaoLaboral = "";
        this.numTelemovel = "";
        this.mostraErro = false;
        this.existe = false;
        this.username = "";
        this.password = "";
        this.morada = "";
        this.codigoP = "";
        this.cidade = "";
        this.tipoConta = -1;
    }


    public void menuLogin() {
        snp.clear();
        System.out.println("=======BEM VINDO========");
        do {
            snp.clear();
            if (mostraErro == true) {
                System.out.println();
                System.out.println("\nPor favor, seleciona uma opção valida.");
                mostraErro = false;
            }
            System.out.println("1 - Login");
            System.out.println("2 - Registar Conta");
            System.out.println("0 - Fechar");
            System.out.print("\nEscolha a opção: ");
            opcao = menuScan.nextInt();
            if (opcao < 0 || opcao > 2) {
                mostraErro = true;
            }
        } while (opcao < 0 || opcao > 2);
        switch (opcao) {
            case 1:
                login();
                break;
            case 2:
                registar();
                break;
            case 0:
                snp.closeProgramm();
                break;
        }
    }

    private void login() {
        
        snp.clear();
        System.out.println("\n====LOGIN====");
        System.out.print("Introduza o [username]: ");
        username = infos.nextLine();
        System.out.print("Introduza a [Password]: ");
        password = infos.nextLine();

        System.out.println();
        System.out.println();
        tipoConta = db.verificaLogin(username, password);

         

        switch (tipoConta) {
            case 0: // MENU CLIENTE
                clientArea CA = new clientArea();
                CA.opcoesBanco();
            break;
            case 1: //conta de funcionario
                funcionarioArea FC = new funcionarioArea();
                FC.menuFunc();
             break;
            case 2: //conta de gestor
                gestorArea gst = new gestorArea();
                gst.menuGestor();
             break;
            case 3 :
            //conta de admin
                adminArea adm = new adminArea();
                adm.mainMenu();
            break;
            case -1:
                snp.clear();
                System.out.println("Conta invalida");
                login();
            break;
        }

    }

    private void registar() {
        snp.clear();
        System.out.println("Vamos começar a criar a sua conta!");
        System.out.println();
        System.out.println("====DADOS LOGIN====");

        do {
            if (existe == true) {
                snp.clear();
                System.out.println("====DADOS LOGIN====");
                System.out.println("Esse username já está em uso! ");
            }
            System.out.print("Introduza um [Username]: ");
            username = infos.nextLine();
            existe = db.verificaUsernameCriado(username);
        } while (existe == true);

        System.out.print("Introduza uma [Passowrd]: ");
        password = infos.nextLine();
        snp.clear();
        System.out.println("====DADOS PESSOAIS====");

        do {
            if (existe == true) {
                snp.clear();
                System.out.println("====DADOS PESSOAIS====");
                System.out.println("Esse NIF já se encontra a ser utilizado! ");
            }
            System.out.print("Introduza um [NIF]: ");
            nifCheck = infosInt.nextLine();
            if (nifCheck.length() > 9) {
                nif = parseInt(nifCheck.substring(0, 9));
            } else {
                nif = parseInt(nifCheck);
            }
            existe = db.verificaNIFCriado(nif);
        } while (existe == true);

        System.out.print("Introduza o [Nome Completo]: ");
        nome = infos.nextLine();
        System.out.print("Introduza a [Data nascimento (dd/mm/aaaa)]: ");
        dtNascimento = infos.nextLine();
        idade = snp.calcularIdade(dtNascimento);
        System.out.print("Introduza o [nº Civil]: ");
        codcc = infos.nextLine();
        System.out.print("Introduza a sua [Situação Laboral (Exp: Desempregado/a)]: ");
        situacaoLaboral = infos.nextLine();
        System.out.print("Introduza o seu [Numero de telemovel]: ");
        numTelemovel = infos.nextLine();
        snp.clear();
        System.out.println("====Morada====");
        System.out.print("Introduza a sua [Morada]: ");
        morada = infos.nextLine();
        System.out.print("Introduza o [Código Postal xxxx-xxx]: ");
        codigoP = infos.nextLine();
        System.out.print("Introduza a [Cidade]: ");
        cidade = infos.nextLine();

        if (db.registarConta(nif, nome, dtNascimento, idade, codcc, situacaoLaboral, numTelemovel, username, password, morada, codigoP, cidade, 0)) {
            snp.clear();
            System.out.println("========SUCESSO========");
            System.out.println("A tua conta foi registada como cliente!");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {
                Logger.getLogger(loginRegister.class.getName()).log(Level.SEVERE, null, ex);
            }
            login();
        } else {
            snp.clear();
            System.out.println("========ERRO========");
            System.out.println("Ocorreu um erro ao criar a conta. Tente novamente.");
        }

    }

}