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
public class clientArea {

  //variaveis do menu
  private int subMenu;
  private String iban;
  private String ibanScan;
  private int escolha;
  private int idEscolhido;
  private int agenciaEscolhida;
  private int i;
  private boolean temIgual;
  private boolean controler;

  private String[] agencias = new String[20];
  Scanner opcaoM = new Scanner(System.in);
  Scanner hr = new Scanner(System.in);
  Scanner cash = new Scanner(System.in);
  Scanner ler = new Scanner(System.in);
  //
  private float dinheiro;

  
  
  
  
  public clientArea() {
    this.escolha = 0;
  }

  dbQuerys db = new dbQuerys();
  snippets snp = new snippets();
  personalData personalData = new personalData();
  loginRegister  lg = new loginRegister();
  

  public void opcoesBanco() {
      
    snp.clear();
    System.out.println("=======AREA CLIENTE========");
    do {
      if (escolha < 0 || escolha > 4) {
        snp.clear();
        System.out.println("Opção Invalida! ! ");
      }
      System.out.println("\n1 - Escolher Conta Bancaria");
      System.out.println("2 - Solicitar Conta");
      System.out.println("3 - Estado Conta Solicitada");
      System.out.println("4 - Logout");
      System.out.println("0 - Fechar");
      System.out.print("\nEscolha a opção: ");
      escolha = opcaoM.nextInt();
    } while (escolha < 0 || escolha > 4);

    switch (escolha) {
    case 1:
      contasBancarias();
      break;
    case 2:
      solicitarConta();
      break;
    case 3:
      verEstadoConta();
    break;
    case 4:
        lg.menuLogin();
    break;
    case 0:
      snp.closeProgramm();
      break;
    }
  }

  private void solicitarConta() {

    snp.clear();
    if (!db.verificaSolicitacoesBancarias()) {
      System.out.println("====DESEJA SOLICITAR UMA CONTA?====");
      do {
        if (escolha < 1 || escolha > 2) {
          snp.clear();
          System.out.println("Opção Invalida!");
        }
        System.out.println("1 - Sim");
        System.out.println("2 - Não");
        System.out.print("\nEscolha a opção: ");
        escolha = opcaoM.nextInt();
      } while (escolha < 1 || escolha > 2);

      switch (escolha) {
      case 1:
        snp.clear();
        temIgual = true;

        System.out.println("====ESCOLHA A AGENCIA====");

        for (i = 0; i < agencias.length; i++) {
          if (agencias[i] != null) {
            agencias[i] = null;
          }
        }

        agencias = db.verificarAgencias(0);
        for (i = 0; i < agencias.length; i++) {
          if (agencias[i] != null) {
            System.out.println("\nIdentificador: [" + agencias[i].substring(0, agencias[i].indexOf("|")) + "] " + "| " +
              "Agência: [" + agencias[i].substring(agencias[i].indexOf("|") + 1) + "]");
          }
        }
        do {
          if (!temIgual) {
            snp.clear();
            System.out.println("Agencia invalida!");
            try {
              TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {}
            snp.clear();
            System.out.println("====ESCOLHA A AGENCIA====");
            for (i = 0; i < agencias.length; i++) {
              if (agencias[i] != null) {
                System.out.println("\nIdentificador: [" + agencias[i].substring(0, agencias[i].indexOf("|")) + "] " + "| " +
                  "Agência: [" + agencias[i].substring(agencias[i].indexOf("|") + 1) + "]");
              }
            }
          }

          System.out.print("\n\nIntroduza o [ID] da Agência que deseja escolher: ");
          agenciaEscolhida = opcaoM.nextInt();

          for (i = 0; i < agencias.length; i++) {
            if (agencias[i] != null) {
              if (parseInt(agencias[i].substring(0, agencias[i].indexOf("|"))) == agenciaEscolhida) {
                temIgual = true;
                break;
              }
              temIgual = false;
            }
          }
        } while (!temIgual);
          System.out.println("Agencia escolhida: " + agenciaEscolhida);
        if (db.solicitarConta(agenciaEscolhida)) {
          snp.clear();
          System.out.println("Conta solicitada com sucesso! \nPodes acompanhar o estado da mesma atravéz da opção [Estado Conta Solicitada]");
          try {
            TimeUnit.SECONDS.sleep(2);
          } catch (InterruptedException ex) {}
          opcoesBanco();
        } else {
          snp.clear();
          System.out.println("Ocorreu um erro ao tentar solicitar a conta.\nTente Novamente.");
          try {
            TimeUnit.SECONDS.sleep(2);
          } catch (InterruptedException ex) {}
          opcoesBanco();
        };

        break;
      case 2:
        opcoesBanco();
        break;

      }
    } else {
      snp.clear();
      System.out.println("====AVISO====");
      System.out.println("Aguarda que aceitem o ultimo pedido!");
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException ex) {}
      opcoesBanco();
    }

  }

  private void verEstadoConta() {
    snp.clear();
    if (db.verificaSolicitacoesBancarias()) {
      System.out.println("====ESTADO DA CONTA====");
      System.out.println("\n" + db.estadoSolicitacao());
      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException ex) {}
      opcoesBanco();
    } else {
      snp.clear();
      System.out.println("====AVISO====");
      System.out.println("De momento não tens nenuma solicitação pendente!");
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException ex) {}
      opcoesBanco();
    }
  }
  
  
  private void contasBancarias() {
       try {
            idEscolhido = 0;
            Map[] maps = db.allContasBancarias();
            if(maps.length>0){
            snp.clear();
            System.out.println("=======CONTAS BANCARIAS========\n");

            for (i = 0; i < maps.length; i++) {
                System.out.format("ID: [%d] | IBAN - [%s] | AGENCIA : [%s]\n", i, maps[i].get("iban"),  maps[i].get("nomeAgencia"));
            }

            do{
                if(idEscolhido < 0 || idEscolhido > i) {
                    snp.clear();
                    
                    System.out.println("=======CONTAS BANCARIAS========");
                    System.out.println("ID DE CONTA INVALIDO.\n");
                    for (i = 0; i < maps.length; i++) {
                        System.out.format("ID: [%d] | IBAN - [%s] | AGENCIA : [%s]\n", i, maps[i].get("iban"),  maps[i].get("nomeAgencia"));
                    }  
                }

                System.out.println("\nVoltar ao menu - " + i);
                System.out.print("INSIRA O ID DA CONTA BANCARIA: ");
                
                idEscolhido = opcaoM.nextInt();  
                
                if(idEscolhido == i) {
                    opcoesBanco();
                }
                
            }
            while(idEscolhido < 0 || idEscolhido > i);
 
            personalData.iban = (String) maps[idEscolhido].get("iban");
            personalData.idContaBancaria = parseInt((String) maps[idEscolhido].get("idConta"));
            personalData.nomeAgencia = (String) maps[idEscolhido].get("nomeAgencia");
            personalData.idAgencia = parseInt((String) maps[idEscolhido].get("idAgencia"));
            menuBancario();

        } else {
                snp.clear();
                        System.out.println("Não tens nenhuma conta bancaria!");
                        try {
                          TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException ex) {} 
                      opcoesBanco();  
            }
        } catch (Exception e) {
            System.out.println("BUG");
            System.err.println(e.getMessage());
            opcoesBanco();
        }

    }
  
    private void menuBancario() {         
        snp.clear();
        System.out.println("=======CONTA BANCARIA========");
        
        System.out.println("AGENCIA - " + personalData.nomeAgencia + " | IBAN - " + ((String) personalData.iban.substring(0,10)) + "*********" );
       
        do {
          if (escolha < 0 || escolha > 5) {
            snp.clear();
            System.out.println("=======CONTA BANCARIA========");
            System.out.println("Opção Invalida! ! ");
             System.out.println("\nAGENCIA - " + personalData.nomeAgencia + " | IBAN - " + ((String) personalData.iban.substring(0,10)) + "*********" );
       
          }
          System.out.println("\n1 - Ver Saldo e Movimentos");
          System.out.println("2 - Efetuar Transações");
          System.out.println("3 - Marcações");
          System.out.println("4 - Ver&/Editar Dados da Conta");
          System.out.println("5 - Logout");
          System.out.println("0 - Fechar");
          System.out.print("\nEscolha a opção: ");
          escolha = opcaoM.nextInt();
        } while (escolha < 0 || escolha > 5);
        
        switch(escolha) {
            case 1: 
                saldoMovimento();
            break;
            case 2: 
               movimentos();
            break;
            case 3:
                marcacao();
            break;
            case 4:
                menuDados();
            break;
            case 5:
            lg.menuLogin();
            break;
            case 0:
            snp.closeProgramm();
            break;
        }

    }
    
private void marcacao() {
    snp.clear();
    int horas = 9;
    String data = null;
    boolean controlador;
    

     
    if(!db.verificaMarcacoes()) {
        
       System.out.println("===AGENDAR MARCAÇÃO===");
       System.out.println("Data da marcação [dia/mes]: ");
       data = ler.nextLine();
       System.out.println("Horas da marcação (Entre as 9 e as 14: ");
       horas =  hr.nextInt();
    
     
            if(horas > 9 && horas < 14 ) {
               
                
                
                if(db.inserirMarcacao(data + " | "+horas + " horas")) {
                    snp.clear();
                    System.out.println("Marcação efectuada");
                    try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException ex) {} menuBancario(); 
                }else {
                    snp.clear();
                    System.out.println("Ocorreu um erro na marcação!");
                    try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException ex) {} menuBancario(); 
                }
                
                
            } else {
                 snp.clear();
                System.out.println("Só podes marcar entre as 09 e as 14!");
                try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException ex) {} menuBancario(); 
                
            }
       
    } else {
        System.out.println("Já tens uma marcação pendente!");
        try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException ex) {} menuBancario(); 
    }

    
}
    
private void menuDados() {
        escolha = 1;
     snp.clear();
        System.out.println("=======DADOS PESSOAIS========");

        do {
          if (escolha < 0 || escolha > 3) {
            snp.clear();
             System.out.println("=======DADOS PESSOAIS========");
             System.out.println("Opção Invalida! ! ");
       
          }
          System.out.println("\n1 - Ver Dados Pessoais");
          System.out.println("2 - Editar Dados Pessoais");
          System.out.println("3 - Logout");
          System.out.println("0 - Fechar");
          System.out.print("\nEscolha a opção: ");
          escolha = opcaoM.nextInt();
        } while (escolha < 0 || escolha > 3);
        
        switch(escolha) {
            case 1:
               getdadosConta(); 
            break;
            case 2:
                editarDados();
            break;
            case 3:
                 lg.menuLogin();
            break;
            case 0:
                  snp.closeProgramm();
            break;
        }

}
    
private void editarDados(){
    snp.clear();
    String nome;
    int idade;
    int controlMSG = 0;
    String dtNascimento;
    String situacaoLaboral;
    String numTelemovel;
    

    System.out.println("===EDITAR DADOS===");
    System.out.print("\nNOME ATUAL: " + personalData.nome);
    System.out.print("\nNOVO NOME (Avançar para não alterar): ");
    nome = ler.nextLine();
    
    System.out.print("\nDATA NASCIMENTO ATUAL: " + personalData.dtNascimento);
    System.out.print("\nNOVA DATA (Avançar para não alterar): ");
    dtNascimento = ler.nextLine();
    
    System.out.print("\nSITUAÇÃO LABORAL ATUAL: " + personalData.situacaoLaboral);
    System.out.print("\nNOVA SITUAÇÃO LABORAL (Avançar para não alterar): ");
    situacaoLaboral = ler.nextLine();
    
    System.out.print("\nNºTELEMOVEL: " + personalData.numTelemovel);
    System.out.print("\nNOVO NUMERO (Avançar para não alterar): ");
    numTelemovel = ler.nextLine();
 
   
    try{
        
        if(!nome.equals("")){
           db.updateNome(nome, personalData.identificador);
           controlMSG++;
        }

       if(!dtNascimento.equals("")){
           idade = snp.calcularIdade(dtNascimento);
           db.updateDataNascimento(dtNascimento, idade, personalData.identificador);
           controlMSG++;
       }

       if(!situacaoLaboral.equals("")){
           db.updateSituacaoLaboral(situacaoLaboral, personalData.identificador);
            controlMSG++;
       }

       if(!numTelemovel.equals("")){
           db.updateTelemovel(numTelemovel,personalData.identificador);
            controlMSG++;
       }
       
       
       if(controlMSG>0) {
              try {
                  snp.clear();
                  System.out.println("Dados atualizados com sucesso!");
                  TimeUnit.SECONDS.sleep(3);} catch (InterruptedException ex) {} menuBancario(); 
        }
       
    }catch(Exception e) {
        System.out.println("Erro " + e);
    }
   
    
}    
    
    
private void getdadosConta() {
        snp.clear();

        Map[] maps = db.dadosConta(personalData.idContaBancaria);
        
        System.out.println("=======DADOS CONTA BANCARIA========");
        System.out.println("\n**Dados Pessoais**");
        System.out.println("Nome - ["+maps[0].get("iban")+"] | Idade - ["+maps[0].get("idade")+"] | Data Nascimento - ["+maps[0].get("dtNascimento")+"] | [Codigo CC - " +maps[0].get("CodCC")+ "] | Situação Laboral - ["+maps[0].get("situacaoLaboral") + "] | Num.Telemovel - ["+ maps[0].get("numTelemovel")+"]");   
        System.out.println("\n**Dados Conta Bancaria**");
        System.out.println("Agencia - ["+maps[0].get("nomeAgencia")+"] | IBAN - ["+maps[0].get("iban")+"] | Data Abertura - ["+maps[0].get("dtNascimento")+"]");
     
        try {TimeUnit.SECONDS.sleep(8);} catch (InterruptedException ex) {} menuBancario(); 

    }

    
    private void movimentos() {
  
               snp.clear();
               System.out.println("=======MOVIMENTOS========");

               System.out.println("AGENCIA - " + personalData.nomeAgencia + " | IBAN - " + ((String) personalData.iban.substring(0,10)) + "*********" );

               do {
                 if (subMenu < 0 || subMenu > 5) {
                   snp.clear();
                   System.out.println("=======MOVIMENTOS========");
                   System.out.println("Opção Invalida! ! "); 
                 }
                 
                 System.out.println("\n1 - Levantar Dinheiro");
                 System.out.println("2 - Depositar Dinheiro");
                 System.out.println("3 - Fazer Pagamentos");
                 System.out.println("4 - Transferencias");
                 System.out.println("5 - Logout");
                 System.out.println("0 - Fechar");
                 System.out.print("\nEscolha a opção: ");
                 subMenu = opcaoM.nextInt();
               } while (subMenu < 0 || subMenu > 5);   
                switch(subMenu) {
                    case 1:
                        levantarDinheiro();
                    break; 
                    case 2:
                        depositarDinheiro();
                    break;  
                    case 3:
                        fazerPagamento();
                    break;
                    case 4:
                        transfererir();
                    break;
                    case 5:
                    lg.menuLogin();
                    break;
                    case 0:
                    snp.closeProgramm();
                    break;
                }
    }
    

    
private void saldoMovimento() {
  
                snp.clear();
               System.out.println("=======SALDO E MOVIMENTOS========");

               System.out.println("AGENCIA - " + personalData.nomeAgencia + " | IBAN - " + ((String) personalData.iban.substring(0,10)) + "*********" );

               do {
                 if (subMenu < 0 || subMenu > 4) {
                   snp.clear();
                   System.out.println("=======SALDO E MOVIMENTOS========");
                   System.out.println("Opção Invalida! ! "); 
                 }
                 
                 System.out.println("\n1 - Visualizar Saldo");
                 System.out.println("2 - Extrato Bancario");
                 System.out.println("3 - Voltar");
                 System.out.println("0 - Fechar");
                 System.out.print("\nEscolha a opção: ");
                 subMenu = opcaoM.nextInt();
               } while (subMenu < 0 || subMenu > 4);
                
                switch(subMenu) {
                    case 1:
                        saldo();
                    break; 
                    case 2:
                        extrato();
                    break;  
                    case 3:
                        menuBancario();
                    break;
                    case 0:
                    snp.closeProgramm();
                    break;
                }
    }
    
   
    
    private void extrato() {
       snp.clear();
       if( db.gerarcsv(personalData.idContaBancaria)) {
           System.out.println("Extrato bancario gerado.");
           System.out.println("Local do extrato - " + System.getProperty("user.dir"));
           try {
                  TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ex) {} 
               menuBancario(); 
       } else {
           System.out.println("Problemas ao gerar o extrato. Já se encontra um ficheiro gerado.");
            try {
                  TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException ex) {} 
               menuBancario(); 
       }
    }
    

    private void levantarDinheiro() {
        snp.clear();
        System.out.println("=======LEVANTAR DINHEIRO=======");
        personalData.saldo = db.verificaSaldo();
        if(personalData.saldo > 0.00) {
            System.out.println("Saldo : " + personalData.saldo);
            do{
                if(dinheiro > personalData.saldo || dinheiro < 0) {
                    snp.clear();
                    System.out.println("=======LEVANTAR DINHEIRO=======");
                    System.out.println("QUANTIA INVALIDA");

                }
                System.out.println("\nQuando desejas levantar? [MAX "+personalData.saldo+"] ");
                System.out.print("Montante : ");
                dinheiro = cash.nextFloat(); 
            }

            while(dinheiro > personalData.saldo || dinheiro < 0);

            if(db.levantarDinheiro(dinheiro)) {
                personalData.saldo = personalData.saldo - dinheiro;
                System.out.println("Levantamento efetuado  | Saldo - " + personalData.saldo);
                try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException ex) {} 
                menuBancario(); 
            } else {
             System.out.println("Ocorreu um erro! Tente mais tarde");
                try {
                  TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ex) {} 
               menuBancario(); 
            }  
            
        } else {
            System.out.println("Sem saldo");
                try {
                  TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ex) {} 
               menuBancario(); 
        }
       
    }

    private void depositarDinheiro() {
            snp.clear();
            System.out.println("=======DEPOSITAR DINHEIRO=======");
            personalData.saldo = db.verificaSaldo();
       
            do{
                if(dinheiro < 0 ) {
                snp.clear();
                 System.out.println("=======DEPOSITAR DINHEIRO=======");
                    System.out.println("QUANTIA INVALIDA!");
                }
                
                System.out.print("\nQuantia a Depositar: ");
                dinheiro = cash.nextFloat(); 
            }
            while(dinheiro < 0);

            if(db.depositarDinheiro(dinheiro)) {
                personalData.saldo = personalData.saldo + dinheiro;
                System.out.println("DEPOSITO EFETUADO  | Saldo - " + personalData.saldo);
                try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException ex) {} 
                menuBancario(); 
            } else {
             System.out.println("Ocorreu um erro! Tente mais tarde");
                try {
                  TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ex) {} 
                menuBancario(); 
            }
            
            
         
    }

    private void fazerPagamento() {
        
       
        temIgual = true;
        snp.clear();
        System.out.println("=======FAZER PAGAMENTOS=======");
        personalData.saldo = db.verificaSaldo();
        if(personalData.saldo > 0.00) {
           
            System.out.print("\nIBAN do Destinatario: ");   
            ibanScan = ler.nextLine();
            
            do{
                if(dinheiro > personalData.saldo || dinheiro < 0) {
                    snp.clear();
                    System.out.println("=======QUANTIDADE A PAGAR=======");
                    System.out.println("QUANTIA INVALIDA");

                }
         
                System.out.print("Total a pagar [MAX "+personalData.saldo+"] : ");
                dinheiro = cash.nextFloat(); 
            }
            while(dinheiro > personalData.saldo || dinheiro < 0);
     
            if(db.levantarDinheiro(dinheiro)) {
                personalData.saldo = personalData.saldo - dinheiro;
                System.out.println("PAGAMENTO EFETUADO  | Saldo - " + personalData.saldo);
                try {TimeUnit.SECONDS.sleep(4);} catch (InterruptedException ex) {} 
                menuBancario(); 
            } else {
             System.out.println("Ocorreu um erro! Tente mais tarde");
                try {
                  TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException ex) {} 
                menuBancario(); 
            }
     
        } else {
              System.out.println("Sem saldo");
            try {
                  TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException ex) {} 
            menuBancario();
        }
     
    }

    private void transfererir() {
        temIgual = true;
        snp.clear();
        System.out.println("=======TRANSFERIR DINHEIRO=======");
        personalData.saldo = db.verificaSaldo();
        if(personalData.saldo > 0.00) {
            do{
                if(!temIgual) {
                snp.clear();
                 System.out.println("=======TRANSFERIR DINHEIRO=======");
                    System.out.println("IBAN INVALIDO");
                }

             System.out.print("\nIBAN do Destinatario: ");   
             ibanScan = ler.nextLine();
             temIgual = db.verificaIban(ibanScan);   
            }
            while(!temIgual);


            do{
                if(dinheiro > personalData.saldo || dinheiro < 0) {
                    snp.clear();
                    System.out.println("=======TRANSFERIR DINHEIRO=======");
                    System.out.println("QUANTIA INVALIDA");

                }
         
                System.out.print("Quantia a transferir [MAX "+personalData.saldo+"] : ");
                dinheiro = cash.nextFloat(); 
            }
            while(dinheiro > personalData.saldo || dinheiro < 0);


           if(db.transferirDinheiro(ibanScan,dinheiro)) {
                personalData.saldo = personalData.saldo - dinheiro;
                System.out.println("TRANSFERENCIA EFETUADA  | Saldo - " + personalData.saldo);
                try {TimeUnit.SECONDS.sleep(4);} catch (InterruptedException ex) {} 
                menuBancario(); 
            } else {
             System.out.println("Ocorreu um erro! Tente mais tarde");
                try {
                  TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException ex) {} 
                menuBancario(); 
            }
            
            
        } else {
            System.out.println("Sem saldo");
            try {
                  TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException ex) {} 
            menuBancario();
        }
            
        
    }
    
    
    private void saldo() {
         snp.clear();
        System.out.println("=======SALDO=======");
        personalData.saldo = db.verificaSaldo();
        if(personalData.saldo > 0.00) {
           System.out.println("Saldo Liquido - " + personalData.saldo);
           System.out.println("A redirecionar...");
                try {
                  TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ex) {} 
                menuBancario(); 
        } else {
         System.out.println("Saldo - 0€");
          System.out.println("A redirecionar...");
                try {
                  TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ex) {} 
                menuBancario(); 
        
        }
    }
   
}