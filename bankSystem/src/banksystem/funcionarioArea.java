/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banksystem;

import static java.lang.Integer.parseInt;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author After
 */
public class funcionarioArea {
    private  int i;
    private int opcaoM;
    private boolean controller;
    Scanner escolha = new Scanner(System.in);
    Scanner comment = new Scanner(System.in);
    Scanner Scanner = new Scanner(System.in);
    public funcionarioArea(){}
    
    dbQuerys db = new dbQuerys();
    snippets snp = new snippets();
    loginRegister lg = new loginRegister();
    

    public void menuFunc() {

        snp.clear();
        System.out.println("=======AREA FUNCIONARIO========");

        do {
            if (opcaoM < 0 || opcaoM > 6) {
                snp.clear();
                System.out.println("Opção Invalida! ! ");
            }
            System.out.println("\n1 - Ver dados de uma conta");
            System.out.println("2 - Alterar dados de uma conta");
            System.out.println("3 - Ver Descrição a Marcações");
            System.out.println("4 - Adicionar/Modificiar Estado da Marcação");
            System.out.println("5 - Marcações Atribuidas");
            System.out.println("6 - Logout");
            System.out.println("0 - Fechar");
            System.out.print("\nEscolha a opção: ");
            opcaoM = escolha.nextInt();
        } while (opcaoM < 0 || opcaoM > 6);


        switch (opcaoM) {
            case 1:
                verDadosConta();
                break;
            case 2:
                alterarDadosConta();
                break;
            case 3:
                verDescrição();
                break;
            case 4:
                mudarDescricao();
                break;
            case 5:
                marcAtribuidas();
            break;
            case 6:
                lg.menuLogin();
                break;
            case 0:
                snp.closeProgramm();    
                break;
        }

    }
    

    
    private void alterarDadosConta(){
    snp.clear();
    String nome;
    int idade;
    int controlMSG = 0;
    String dtNascimento;
    String situacaoLaboral;
    String numTelemovel;
    String codCC;
    int nif;
    

    System.out.print("Insira o nif da pessoa: ");
    nif = escolha.nextInt();
    
    Map[] data = db.dadosContaFunc(nif);
     
    if(data.length > 0) {
        
    
    System.out.println("===EDITAR DADOS===");
    System.out.print("\nNOME ATUAL: " + data[0].get("nome"));
    System.out.print("\nNOVO NOME (Avançar para não alterar): ");
    nome = Scanner.nextLine();
    
    System.out.print("\nDATA NASCIMENTO ATUAL: " + data[0].get("dtNascimento"));
    System.out.print("\nNOVA DATA (Avançar para não alterar): ");
    dtNascimento = Scanner.nextLine();
    
    System.out.print("\nSITUAÇÃO LABORAL ATUAL: " + data[0].get("situacaoLaboral"));
    System.out.print("\nNOVA SITUAÇÃO LABORAL (Avançar para não alterar): ");
    situacaoLaboral = Scanner.nextLine();
    
    System.out.print("\nNºTELEMOVEL: " + data[0].get("numTelemovel"));
    System.out.print("\nNOVO NUMERO (Avançar para não alterar): ");
    numTelemovel = Scanner.nextLine();
    
    System.out.print("\nCodCC: " + data[0].get("CodCC"));
    System.out.print("\nNOVO CC (Avançar para não alterar): ");
    codCC = Scanner.nextLine();
 
   
    try{
        
        if(!nome.equals("")){
           db.updateNome(nome, nif);
           controlMSG++;
        }

       if(!dtNascimento.equals("")){
           idade = snp.calcularIdade(dtNascimento);
           db.updateDataNascimento(dtNascimento, idade, nif);
           controlMSG++;
       }

       if(!situacaoLaboral.equals("")){
           db.updateSituacaoLaboral(situacaoLaboral, nif);
            controlMSG++;
       }

       if(!numTelemovel.equals("")){
           db.updateTelemovel(numTelemovel,nif);
            controlMSG++;
       }
       
       if(!codCC.equals("")){
           db.updateCodCC(codCC , nif);
            controlMSG++;
       }

       if(controlMSG>0) {
              try {
                  snp.clear();
                  System.out.println("Dados atualizados com sucesso!");
                  TimeUnit.SECONDS.sleep(5);} catch (InterruptedException ex) {} menuFunc(); 
        }
       
       
    }catch(Exception e) {
        System.out.println("Erro " + e);
    }
   
    } else {    
        System.out.println("IDENTIFICADOR INVALIDO");
        try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException ex) {} menuFunc();      
      }
   
    
}    
    
    private void mudarDescricao() {
        int idEscolhido;
        String comentario;
        Map[] marcAtri = db.marcacoesAtribuidas();
        int arrId[] = new int[marcAtri.length];
        snp.clear();
        System.out.println("===LISTA DE MARCAÇÕES===\n");
        if(marcAtri.length> 0) {
            for(i = 0; i < marcAtri.length; i++) {
                  System.out.format("ID - [%s] | NOME - [%s] | DATA - [%s] | DESCRIÇÃO - [%s] | ESTADO - [%s]\n", (String)marcAtri[i].get("id"), (String) marcAtri[i].get("nomeCliente"), (String) marcAtri[i].get("data"), (String) marcAtri[i].get("descricao"), (String) marcAtri[i].get("estado"));
                arrId[i] = parseInt((String) marcAtri[i].get("id"));
            }

           do{
              
              if(controller) {
                  snp.clear();
                  
                  System.out.println("===LISTA DE MARCAÇÕES===");
                  System.out.println("Id Invalido.\n");
                   for(i = 0; i < marcAtri.length; i++) {
                System.out.format("ID - [%s] | NOME - [%s] | DATA - [%s] | DESCRIÇÃO - [%s] | ESTADO - [%s]\n", (String)marcAtri[i].get("id"),(String) marcAtri[i].get("nomeCliente"), (String) marcAtri[i].get("data"), (String) marcAtri[i].get("descricao"), (String) marcAtri[i].get("estado"));
                arrId[i] = parseInt((String) marcAtri[i].get("id"));
            }
              }
              
            controller = true;
            System.out.print("\nQue descrição deseja alterar? (ID): ");
            idEscolhido = escolha.nextInt();
            
            for(int x = 0; x < arrId.length; x++) {
                if(arrId[x]== idEscolhido) {
                    controller = false;
                    break;
                }
            }
              
          }while(controller);
           
           
        System.out.print("Insira um comentario: ");
        comentario = comment.nextLine();

        if (db.updateComentarioMarcacoes(idEscolhido, comentario)) {
            snp.clear();
            System.out.println("Descrição Alterada com sucesso!");
            try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException ex) {} menuFunc();
        } else {
            snp.clear();
            System.out.println("Ocorreu um erro ao tentar solicitar a conta.\nTente Novamente.");
            try {TimeUnit.SECONDS.sleep(2); } catch (InterruptedException ex) {} menuFunc();
        };
           
        
      } else {
        System.out.println("IDENTIFICADOR INVALIDO");
        try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException ex) {} menuFunc(); 
      }
        
        
    }
    
    private void marcAtribuidas() {

        Map[] marcAtri = db.marcacoesAtribuidas();
        snp.clear();
        System.out.println("===LISTA DE MARCAÇÕES===\n");
        if(marcAtri.length> 0) {
            for(i = 0; i < marcAtri.length; i++) {
                System.out.format("NOME - [%s] | DATA - [%s] | DESCRIÇÃO - [%s] | ESTADO - [%s]\n", (String) marcAtri[i].get("nomeCliente"), (String) marcAtri[i].get("data"), (String) marcAtri[i].get("descricao"), (String) marcAtri[i].get("estado"));
            }
             System.out.println("\nIra ser redirecionado em 10 segundos.");
       try {TimeUnit.SECONDS.sleep(10);} catch (InterruptedException ex) {} menuFunc();
         } else {
            System.out.println("IDENTIFICADOR INVALIDO");
            try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException ex) {} menuFunc(); 
      }

    }
    
    private void verDescrição() {
        int idAccount = 0;
        String descricao;
        snp.clear();
    
        System.out.print("Insira o ID da marcação: ");
        idAccount = escolha.nextInt();
    
        descricao = db.descricaoFunc(idAccount);
       
        if(!descricao.equals(null)) {
            snp.clear();
            System.out.print("DESCRIÇÃO DA CONTA: " + descricao);
            System.out.println("\nIra ser redirecionado em 10 segundos.");
            try {TimeUnit.SECONDS.sleep(10);} catch (InterruptedException ex) {} menuFunc();
        } else {
            snp.clear();
            System.out.println("IDENTIFICADOR INVALIDO");
            try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException ex) {} menuFunc(); 
        }
   
    }
    
    private void verDadosConta() {
     snp.clear();
       int id;
       System.out.print("Insira o ID da conta bancaria: ");
       id = escolha.nextInt();
       Map[] cont = db.dadosConta(id);
        
        if(cont.length> 0) {
            System.out.println("=======DADOS CONTA BANCARIA========");
                System.out.println("\n**Dados Pessoais**");
                System.out.println("Nome - ["+cont[0].get("iban")+"] | Idade - ["+cont[0].get("idade")+"] | Data Nascimento - ["+cont[0].get("dtNascimento")+"] | [Codigo CC - " +cont[0].get("CodCC")+ "] | Situação Laboral - ["+cont[0].get("situacaoLaboral") + "] | Num.Telemovel - ["+ cont[0].get("numTelemovel")+"]");   
                System.out.println("\n**Dados Conta Bancaria**");
                System.out.println("Agencia - ["+cont[0].get("nomeAgencia")+"] | IBAN - ["+cont[0].get("iban")+"] | Data Abertura - ["+cont[0].get("dtNascimento")+"]");

                try {TimeUnit.SECONDS.sleep(8);} catch (InterruptedException ex) {} menuFunc(); 
        } else {
            snp.clear();
            System.out.println("IDENTIFICADOR INVALIDO");
            try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException ex) {} menuFunc(); 
        }
     
   } 
    
}
