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
import java.util.logging.Level;
import java.util.logging.Logger;



public class gestorArea {

    private int opcaoM;
    private String nif;
    private String idAgencia;
    private String nota;
    private int id;
    private int i;
    
    
    private int idade;
    private int nifGestor;
    private String iban;
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


   
    Scanner infosInt = new Scanner(System.in);
    Scanner infos = new Scanner(System.in);


 

    Scanner escolha = new Scanner(System.in);
    Scanner inputNota = new Scanner(System.in);

    public gestorArea() {
        this.nifGestor = 0;
        this.nifCheck = "";
        this.idade = 0;
        this.nome = "";
        this.dtNascimento = "";
        this.codcc = "";
        this.situacaoLaboral = "";
        this.numTelemovel = "";
        this.existe = false;
        this.username = "";
        this.password = "";
        this.morada = "";
        this.codigoP = "";
        this.cidade = "";

    }



    dbQuerys db = new dbQuerys();
    snippets snp = new snippets();
    loginRegister lg = new loginRegister();

    public void menuGestor() {

        snp.clear();
        System.out.println("=======AREA GESTOR========");

        do {
            if (opcaoM < 0 || opcaoM > 5) {
                snp.clear();
                System.out.println("Opção Invalida! ! ");
            }
            System.out.println("\n1 - Gerir Funcionarios");
            System.out.println("2 - Gerir Contas Solicitadas");
            System.out.println("3 - Gerir Marcações");
            System.out.println("4 - Movimentos Contas ");
            System.out.println("5 - Informações da Conta ");
            System.out.println("6 - Logout");
            System.out.println("0 - Fechar");
            System.out.print("\nEscolha a opção: ");
            opcaoM = escolha.nextInt();
        } while (opcaoM < 0 || opcaoM > 5);


        switch (opcaoM) {
            case 1:
               gerirFuncionario();
                break;
            case 2:
                gerirContas();
                break;
            case 3:
                gerirMarcacoes();
                break;
            case 4:
                extrato();
                break;
            case 5:
            verDadosConta();
            break;
            case 6:
                lg.menuLogin();
                break;
            case 0:
                snp.closeProgramm();    
                break;
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

                try {TimeUnit.SECONDS.sleep(8);} catch (InterruptedException ex) {} menuGestor(); 
        } else {
            snp.clear();
            System.out.println("IDENTIFICADOR INVALIDO");
            try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException ex) {} menuGestor(); 
        }
     
   } 
    
    
    private void gerirMarcacoes() {
        
        snp.clear();
        System.out.println("===GESTÃO DE MARCAÇÕES===");
        do{
            if(opcaoM < 0 || opcaoM > 3) {
                snp.clear();
                System.out.println("===GESTÃO DE MARCAÇÕES===");
                System.out.println("OPÇÃO INVALIDA!");
            }
            
            System.out.println("1 - Atribuir Marcação");
            System.out.println("2 - Eliminar Marcação");
            System.out.println("3 - Voltar");
            System.out.print("Escolha a opção: ");
            opcaoM = escolha.nextInt();
        }
        while(opcaoM < 0 || opcaoM > 3);
    
        switch(opcaoM) {
            case 1:
                atribuirMarcacao();
            break;
            case 2:
                eliminarMarcacao();
            break;
            case 3:
                menuGestor();
            break;
        }
    }
    
    
      private void extrato() {
       int identificador;
   
       snp.clear();
       
          System.out.print("Insira o ID da conta bancaria: ");
          identificador = escolha.nextInt();
       if( db.gerarcsv(identificador)) {
           System.out.println("Extrato bancario gerado.");
           System.out.println("Local do extrato - " + System.getProperty("user.dir"));
           try {
                  TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ex) {} 
               menuGestor(); 
       } else {
           System.out.println("Problemas ao gerar o extrato. Já se encontra um ficheiro gerado.");
            try {
                  TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException ex) {} 
               menuGestor(); 
       }
    }
    
    
    
  private void atribuirMarcacao() {
      Map[] funcs = db.todosFuncionarios();
      Map[] marc = db.marcacoes();
      int ids[] = new int[marc.length];
      int option = 0;
      int idFunc = 0;
      boolean controller = false;
      
       snp.clear();
       
       
       
       System.out.println("===LISTA DE MARCAÇÕES===");
          for(i=0;i < marc.length; i++) {
              System.out.println("ID - ["+marc[i].get("id")+"] | Nome - NIF " + "["+marc[i].get("nome") + " - " + marc[i].get("nif")+"] | Data - [" + marc[i].get("dataHora")+"] Estado- ["+ marc[i].get("estado")+"]");
              ids[i] = parseInt((String) marc[i].get("id"));
          }
          
          do{
              
              if(controller) {
                  snp.clear();
                 
                  System.out.println("===LISTA DE MARCAÇÕES===");
                  System.out.println("Id Invalido.\n");
                   for(i=0;i < marc.length; i++) {
                        System.out.println("ID - ["+marc[i].get("id")+"] | Nome - NIF " + "["+marc[i].get("nome") + " - " + marc[i].get("nif")+"] | Data - [" + marc[i].get("dataHora")+"] Estado- ["+ marc[i].get("estado")+"]");
                        ids[i] = parseInt((String) marc[i].get("id"));
                    }
              }
              
            controller = true;
            System.out.print("\nEscolha a marcação que deseja atribuir (ID): ");
            option = escolha.nextInt();
            
            for(int x = 0; x < ids.length; x++) {
                if(ids[x]== option) {
                    controller = false;
                    break;
                }
            }
              
          }while(controller);
       
       
       snp.clear();
       System.out.println("====LISTA DE FUNCIONARIOS PARA ATRIBUIR====\n");
       
        for (i = 0; i < funcs.length; i++) {
            System.out.format("IDENTIFICADOR - [%d] | NOME - [%s] | NIF - [%s] | IDADE - [%s] |  NTELEMOVEL - [%s]\n", i , (String)funcs[i].get("nome"), (String)funcs[i].get("nif"), (String)funcs[i].get("idade"), (String)funcs[i].get("numTelemovel"));
        }
 
            do{
                   if(idFunc < 0 || idFunc > (i-1)) {
                     snp.clear();
                     System.out.println("====LISTA DE FUNCIONARIOS PARA ATRIBUIR====");
                     for (i = 0; i < funcs.length; i++) {
                     System.out.format("IDENTIFICADOR - [%d] | NOME - [%s] | NIF - [%s] | IDADE - [%s] |  NTELEMOVEL - [%s]\n", i , (String)funcs[i].get("nome"), (String)funcs[i].get("nif"), (String)funcs[i].get("idade"), (String)funcs[i].get("numTelemovel"));
            }
                     System.out.println("\nID INVALIDO");
                }
            
           System.out.print("\nQue funcionario deseja atribuir? (ID):");
                idFunc = escolha.nextInt(); 

        }while(idFunc < 0 || idFunc > (i-1));
        

            
            if (db.atribuirMarcacao(option, parseInt((String)funcs[idFunc].get("nif")))) {
                 snp.clear();
                 System.out.println("Marcação Atribuida Sucesso!");
                 try {
                   TimeUnit.SECONDS.sleep(2);
                 } catch (InterruptedException ex) {}
                 menuGestor();
             } else {
                 snp.clear();
                 System.out.println("Ocorreu um erro durante o processo.");
                 try {
                   TimeUnit.SECONDS.sleep(2);
                 } catch (InterruptedException ex) {}
                 menuGestor();
             };
            
            
            
  }  
    
    
 private void eliminarMarcacao()  {
     snp.clear();
      int option;
      boolean controller = false;
      
      Map[] marc = db.marcacoes();
      int ids[] = new int[marc.length];
     
      if(marc.length>0){
      
          
          System.out.println("===APAGAR MARCAÇÃO===");
          for(int i=0;i < marc.length; i++) {
              System.out.println("ID - ["+marc[i].get("id")+"] | Nome - NIF " + "["+marc[i].get("nome") + " - " + marc[i].get("nif")+"] | Data - [" + marc[i].get("dataHora")+"] Estado- ["+ marc[i].get("estado")+"]");
              ids[i] = parseInt((String) marc[i].get("id"));
          }
          
          do{
              
              if(controller) {
                  snp.clear();
                  
                  System.out.println("===APAGAR MARCAÇÃO===");
                  System.out.println("Id Invalido.\n");
                   for(int i=0;i < marc.length; i++) {
                        System.out.println("ID - ["+marc[i].get("id")+"] | Nome - NIF " + "["+marc[i].get("nome") + " - " + marc[i].get("nif")+"] | Data - [" + marc[i].get("dataHora")+"] Estado- ["+ marc[i].get("estado")+"]");
                        ids[i] = parseInt((String) marc[i].get("id"));
                    }
              }
              
            controller = true;
            System.out.print("\nIntroduza o ID: ");
            option = escolha.nextInt();
            
            for(int x = 0; x < ids.length; x++) {
                if(ids[x]== option) {
                    controller = false;
                    break;
                }
            }
              
          }while(controller);

            if (db.updateEstadoMarcacao(option)) {
                 snp.clear();
                 System.out.println("Marcação Eliminada com Sucesso!");
                 try {
                   TimeUnit.SECONDS.sleep(2);
                 } catch (InterruptedException ex) {}

             } else {
                 snp.clear();
                 System.out.println("Ocorreu um erro durante o processo.");
                 try {
                   TimeUnit.SECONDS.sleep(2);
                 } catch (InterruptedException ex) {}

             };
     
          
      } else {
            snp.clear();
            System.out.println("Não existe nenhuma marcação pendente de momento!");
            try {
              TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {} 
            }
    
 }   
    
    

    private void gerirContas() {
        
        try {
            opcaoM = 0;
            Map[] maps = db.contasSolicitadasAll();
            if(maps.length>0){
            snp.clear();
            System.out.println("=======GESTÃO DE CONTAS========\n");

            for (i = 0; i < maps.length; i++) {
                System.out.format("ID: [%d] | NOME - [%s] | AGÊNCIA: [%s] | NIF : [%s]\n", i, maps[i].get("nome"), maps[i].get("nomeAgencia"), maps[i].get("nif"));
            }

            do {
                System.out.println("\n\n==Que conta deseja ver?==");
                if (id < 0 || id > 5) {

                    snp.clear();
                    System.out.println("=======GESTÃO DE CONTAS========");

                    for (i = 0; i < maps.length; i++) {
                        System.out.format("ID: [%d] | NOME - [%s] | AGÊNCIA: [%s] | NIF : [%s]\n", i, maps[i].get("nome"), maps[i].get("nomeAgencia"), maps[i].get("nif"));
                    }
                    System.out.println("ID INVALIDO.");
                }
                System.out.println("\nVoltar ao menu - " + (i+1));
                System.out.print("Que conta deseja verificar? ID: ");
                id = escolha.nextInt();
                
                if(id == (i + 1)) {
                    menuGestor();
                }
 
            }
            while (id > i || id < 0);

              snp.clear();
              System.out.println("=======INFORMAÇÕES DA CONTA========");
              System.out.format("\nID: [%d] | NOME - [%s] | AGÊNCIA: [%s] | NIF : [%s]\n", id, maps[id].get("nome"), maps[id].get("nomeAgencia"), maps[id].get("nif"));
            

                do {
                    if (opcaoM < 0 || opcaoM > 3) {
                        snp.clear();
                        System.out.println("=======INFORMAÇÕES DA CONTA========");
                        System.out.println("OPÇÃO INVALIDA");
                        System.out.format("\nID: [%d] | NOME - [%s] | AGÊNCIA: [%s] | NIF : [%s]\n", id, maps[id].get("nome"), maps[id].get("nomeAgencia"), maps[id].get("nif"));
                    }
               System.out.println("1 - Aceitar");
               System.out.println("2 - Rejeitar");
               System.out.println("3 - Voltar");
               System.out.print("\nIntroduza a opção: ");
               opcaoM = escolha.nextInt();  
            }
            while (opcaoM < 0 || id > 3);

               switch(opcaoM) {
                   case 1:
                        nif = (String) maps[id].get("nif");
                        idAgencia = (String ) maps[id].get("idAgencia");
                        System.out.print("Comentario (Não obrigatorio):");
                        nota = inputNota.nextLine();
                        iban = snp.gerar();
                        System.out.println("iban gerado  - " + iban);
                        if (db.mudarEstadoSolicitada(parseInt(nif),0,1, nota)) {
                        db.gerarConta(parseInt(nif), iban, parseInt(idAgencia)); 
                        snp.clear();
                        System.out.println("Conta Aceite! \nPodes gerir a mesma atraves do Menu.");
                        try {
                          TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException ex) {}
                        menuGestor();
                    
                    } else {
                        snp.clear();
                        System.out.println("Ocorreu um erro durante o processo.");
                        try {
                          TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException ex) {}
                        menuGestor();
                    };
                   break;
                   case 2:
                         nif = (String) maps[id].get("nif");
                        System.out.print("Comentario (Não obrigatorio):");
                        nota = inputNota.nextLine();
                        
                        if (db.mudarEstadoSolicitada(parseInt(nif),2,1, nota)) {
                        snp.clear();
                        System.out.println("Conta Rejeitada! \nPodes gerir a mesma atraves do Menu.");
                        try {
                          TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException ex) {}
                    
                    } else {
                        snp.clear();
                        System.out.println("Ocorreu um erro durante o processo.");
                        try {
                          TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException ex) {}
                
                    };
                   break;
                   case 3:
                       System.out.println("Voltar");
                       gerirContas();
                   break;
               }
 
        } else {
                snp.clear();
                        System.out.println("Não existe nenhuma conta pendente de momento!");
                        try {
                          TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException ex) {} 
            }
        } catch (Exception e) {
            System.out.println("Não existe nenhuma conta pendente");
            System.err.println(e.getMessage());
        }

    }
    
    private void gerirFuncionario() {
        snp.clear();
        System.out.println("===GESTÃO DE FUNCIONARIOS===");
        do{
            if(opcaoM < 0 || opcaoM > 3) {
                System.out.println("===GESTÃO DE FUNCIONARIOS===");
                System.out.println("OPÇÃO INVALIDA!");
            }
            
            System.out.println("1 - Adicionar Funcionario");
            System.out.println("2 - Remover Funcionario");
            System.out.println("3 - Voltar");
            System.out.print("Escolha a opção: ");
            opcaoM = escolha.nextInt();
        }
        while(opcaoM < 0 || opcaoM > 3);
    
        switch(opcaoM) {
            case 1:
                registarFuncionario();
            break;
            case 2:
                eliminarFuncinonario();
            break;
            case 3:
                menuGestor();
            break;
        }
        
      
    }

    private void registarFuncionario() {
        snp.clear();
        System.out.println("CRIAR CONTA GESTOR!");
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
                nifGestor = parseInt(nifCheck.substring(0, 9));
            } else {
                nifGestor = parseInt(nifCheck);
            }
            existe = db.verificaNIFCriado(nifGestor);
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

        if (db.registarConta(nifGestor, nome, dtNascimento, idade, codcc, situacaoLaboral, numTelemovel, username, password, morada, codigoP, cidade, 2)) {
            snp.clear();
            System.out.println("========SUCESSO========");
            System.out.println("Conta Funcionario Criada");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {
                Logger.getLogger(loginRegister.class.getName()).log(Level.SEVERE, null, ex);
            }
            menuGestor();
        } else {
            snp.clear();
            System.out.println("========ERRO========");
            System.out.println("Ocorreu um erro ao criar a conta. Tente novamente.");
        }

    }
  
    private void eliminarFuncinonario(){
         try {
            opcaoM = 0;
            Map[] maps = db.todosGestores();
            if(maps.length>0){
            snp.clear();
            System.out.println("=======ELIMINAR FUNCIONARIOS========\n");

            for (i = 0; i < maps.length; i++) {
                System.out.format("ID: [%d] | NOME - [%s] | NIF : [%s]\n", i, maps[i].get("nome"),  maps[i].get("nif"));
            }

         
            do{
                
                if (opcaoM < 0 || opcaoM > i) {
                        snp.clear();
                        System.out.println("=======ELIMINAR FUNCIONARIOS========");
                        System.out.println("ID INVALIDO");
                        for (i = 0; i < maps.length; i++) {
                       System.out.format("ID: [%d] | NOME - [%s] | NIF : [%s]\n", i, maps[i].get("nome"),  maps[i].get("nif"));
                        }        
                }
                
                
                System.out.println("\nVoltar para o menu - " + (i));
                System.out.print("Introduza o ID para eliminar: ");
                opcaoM = escolha.nextInt(); 
                
                if(opcaoM == i) {
                    menuGestor();
                }
                
            }
            while(opcaoM < 0 || opcaoM > i );
            
            System.out.println("Eliminar id : "  + opcaoM);
    
             
            snp.clear();
            nif = (String) maps[opcaoM].get("nif");
            if (db.eliminarFuncionario(parseInt(nif))) {
              System.out.println("====FUNCIONARIO ELIMINADO COM SUCCESSO====");
              try {
                TimeUnit.SECONDS.sleep(5);
              } catch (InterruptedException ex) {}
              menuGestor();
            } else {
              snp.clear();
              System.out.println("====AVISO====");
              System.out.println("De momento não tens nenuma solicitação pendente!");
              try {
                TimeUnit.SECONDS.sleep(2);
              } catch (InterruptedException ex) {}
              menuGestor();
            }
                

        } else {
                snp.clear();
                        System.out.println("Não existe nenhuma conta pendente de momento!");
                        try {
                          TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException ex) {} 
            }
        } catch (Exception e) {
            System.out.println("Não existe nenhuma conta pendente");
            System.err.println(e.getMessage());
        }

    }
       
}