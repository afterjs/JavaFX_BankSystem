/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banksystem;

import static java.lang.Integer.parseInt;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author After
 */
public class adminArea {

  private int opcaoM;
  private int agenciaEscolhida;
  private int i;
  private int controlador;
  private int identificador;
  private boolean temIgual;
  private boolean existe;
  private String[] agencias = new String[20];
  private String[] gestores = new String[100];
  private String[] nomesAgencias = new String[100];
  private String[] totalAgencias = new String[100];
  private String agencia;
  
  Scanner escolha = new Scanner(System.in);
  Scanner inputAgencia = new Scanner(System.in);
  Scanner ScanAgencia = new Scanner(System.in);

  public adminArea() {}

  dbQuerys db = new dbQuerys();
  snippets snp = new snippets();
  loginRegister  lg = new loginRegister();
  

  public void mainMenu() {

    snp.clear();
    System.out.println("=======AREA ADMIN========");
    do {
      if (opcaoM < 0 || opcaoM > 6) {
        snp.clear();
        System.out.println("Opção Invalida! ! ");
      }
      System.out.println("\n1 - Adicionar Agência");
      System.out.println("2 - Remover Agência");
      System.out.println("3 - Ativar Agência");
      System.out.println("4 - Desativar Agência");
      System.out.println("5 - Ver Agências");
      System.out.println("6 - Logout");
      System.out.println("0 - Fechar");
      System.out.print("\nEscolha a opção: ");
      opcaoM = escolha.nextInt();
    } while (opcaoM < 0 || opcaoM > 6);

    switch (opcaoM) {
    case 1:
      //outro menu admin
      snp.clear();
      if (db.existeAlgumGestor()) {
            snp.clear();
            adicionaAge();
            try {
              TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {}
            mainMenu();
        } else {
            snp.clear();
            System.out.println("Não existe nenhum GESTOR para associar a uma AGENCIA!");
            try {
              TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {}
            mainMenu();
        };
      
      break;
    case 2:
      if (db.verificaExisteAgencias()) {
            snp.clear();
             removerAge();
            try {
              TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {}
            mainMenu();
        } else {
            snp.clear();
            System.out.println("Não existe nenhuma Agência!");
            try {
              TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {}
            mainMenu();
        };
      break;
    case 3:
        if(db.existeAgencia(1)) {
            ativarAge();
        }else{
            snp.clear();
            System.out.println("De momento não existe nenhuma Agência desativada");
            try {
              TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {}
            mainMenu();
        }
      break;
    case 4:
         if(db.existeAgencia(0)) {
            desativarAge();
        }else{
            snp.clear();
            System.out.println("De momento não existe nenhuma Agência Ativas");
            try {
              TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {}
            mainMenu();
        }

      break;
    case 5:
    mostrarAgencias();  
    break;
    case 6:
    lg.menuLogin();
    break;
    case 0:
      snp.closeProgramm();
    break;
    }
  }

  private void ativarAge() {

    snp.clear();
    temIgual = true;

    System.out.println("====ATIVAR AGENCIA====");

    for (i = 0; i < agencias.length; i++) {
      if (agencias[i] != null) {
        agencias[i] = null;
      }
    }

    agencias = db.verificarAgencias(1);
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
        System.out.println("====ATIVAR AGENCIA====");
        for (i = 0; i < agencias.length; i++) {
          if (agencias[i] != null) {
            System.out.println("\nIdentificador: [" + agencias[i].substring(0, agencias[i].indexOf("|")) + "] " + "| " +
              "Agência: [" + agencias[i].substring(agencias[i].indexOf("|") + 1) + "]");
          }
        }
      }

      System.out.print("\n\nIntroduza o [ID] da Agência que deseja ativar: ");
      agenciaEscolhida = inputAgencia.nextInt();

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
    if (db.ativarAgencia(agenciaEscolhida)) {
      snp.clear();
      System.out.println("Agência Ativada \nPodes desativar a mesma através da opção [Desativar Agências]");
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException ex) {}
      mainMenu();
    } else {
      snp.clear();
      System.out.println("Ocorreu um erro ao tentar solicitar a conta.\nTente Novamente.");
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException ex) {}
      mainMenu();
    };
  }

  private void desativarAge() {

    snp.clear();
    temIgual = true;

    System.out.println("====DESATIVAR AGENCIA====");

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
        System.out.println("====DESATIVAR AGENCIA====");
        for (i = 0; i < agencias.length; i++) {
          if (agencias[i] != null) {
            System.out.println("\nIdentificador: [" + agencias[i].substring(0, agencias[i].indexOf("|")) + "] " + "| " +
              "Agência: [" + agencias[i].substring(agencias[i].indexOf("|") + 1) + "]");
          }
        }
      }

      System.out.print("\n\nIntroduza o [ID] da Agência que deseja desativar: ");
      agenciaEscolhida = inputAgencia.nextInt();

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
    if (db.desativarAgencia(agenciaEscolhida)) {
      snp.clear();
      System.out.println("Agência Desativada \nPodes ativar a mesma através da opção [Ativar Agências]");
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException ex) {}
      mainMenu();
    } else {
      snp.clear();
      System.out.println("Ocorreu um erro ao tentar solicitar a conta.\nTente Novamente.");
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException ex) {}
      mainMenu();
    };

  }
  
  private void adicionaAge() {
      snp.clear();
       

      for (i = 0; i < nomesAgencias.length; i++) {
            if (nomesAgencias[i] != null) {
                nomesAgencias[i] = null;
            }
      }
      
      nomesAgencias = db.nomesAgencias();
      System.out.println("=======LISTA DE AGÊNCIAS========\n");
      
      for (i = 0; i < nomesAgencias.length; i++) {
            if (nomesAgencias[i] != null) {
               System.out.format("ID - [%d] | Nome da Agência: [%s]\n", i + 1, nomesAgencias[i]);             
            }
      }
      
      
      System.out.println("\n====ESCOLHA O NOME AGENCIA====");
      do {

            if (existe == true) {
                snp.clear();
                System.out.println("=======LISTA DE AGÊNCIAS========\n");
      
                for (i = 0; i < nomesAgencias.length; i++) {
                      if (nomesAgencias[i] != null) {
                         System.out.format("ID - [%d] | Nome da Agência: [%s]\n", i + 1, nomesAgencias[i]);             
                      }
                }
                
                System.out.println("\n====ESCOLHA O NOME AGENCIA====");
                System.out.println("Já existe uma Agência com esse nome ");
            }
            System.out.print("\nIntroduza o nome da [Agência]: ");
            agencia = ScanAgencia.nextLine();
            existe = db.verificaNomeAgencia(agencia);
        } while (existe == true);
      
      
      for (i = 0; i < gestores.length; i++) {
            if (gestores[i] != null) {
                gestores[i] = null;
            }
       }
      
       gestores = db.nomesGestores();
       snp.clear();
       System.out.println("====LISTA DE GESTORES====");
        for (i = 0; i < gestores.length; i++) {
            if (gestores[i] != null) {
                System.out.println("\nID: [" +gestores[i].substring(0, gestores[i].indexOf("|"))+"]"
                        + " | Nome do Gestor: ["+gestores[i].substring(gestores[i].indexOf("|") + 1,gestores[i].lastIndexOf("|"))+"]"
                        + " | NIF: ["+gestores[i].substring(gestores[i].lastIndexOf("|") + 1)+"]");
            }
        }
        System.out.println("\n====ASSOCIAR GESTOR - ["+agencia+"]====");
        existe = true;
        do{
            if (existe == false) {
            snp.clear();
            System.out.println("====LISTA DE GESTORES====");

             for (i = 0; i < gestores.length; i++) {
                 if (gestores[i] != null) {
                     System.out.println("\nID: [" +gestores[i].substring(0, gestores[i].indexOf("|"))+"]"
                             + " | Nome do Gestor: ["+gestores[i].substring(gestores[i].indexOf("|") + 1,gestores[i].lastIndexOf("|"))+"]"
                             + " | NIF: ["+gestores[i].substring(gestores[i].lastIndexOf("|") + 1)+"]");
                 }
             }
             System.out.println("\n====ASSOCIAR GESTOR - ["+agencia+"]====");
            }

         System.out.print("\nIntroduza o ID do [Gestor]: ");
         identificador = inputAgencia.nextInt();;
         existe =db.existeGestor(identificador);
        }
        while(existe==false);
          if (db.inserirAgencia(agencia, identificador)) {
            snp.clear();
            System.out.println("Agência Criada! \nPodes gerir a mesma através do Menu");
            try {
              TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {}
            mainMenu();
        } else {
            snp.clear();
            System.out.println("Ocorreu um Erro! \nTenta Novamente");
            try {
              TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {}
            mainMenu();
        };
  }

  private void removerAge() {
  
      snp.clear();
      existe = false;
      
      for (i = 0; i < nomesAgencias.length; i++) {
            if (nomesAgencias[i] != null) {
                nomesAgencias[i] = null;
            }
      }
      
      nomesAgencias = db.nomesAgenciasID();
      System.out.println("=======LISTA DE AGÊNCIAS========\n");
      
      for (i = 0; i < nomesAgencias.length; i++) {
            if (nomesAgencias[i] != null) {
                
                System.out.println("ID: "+nomesAgencias[i].substring(0, nomesAgencias[i].indexOf("|")) +"] | "
                        + "Nome Agência: [" +nomesAgencias[i].substring(nomesAgencias[i].indexOf("|") + 1)+"]");      
            }
      }
         
      System.out.println("\n====ELIMINAR AGENCIA====");
      do {
            if (existe == true) {
                snp.clear();
                System.out.println("=======LISTA DE AGÊNCIAS========\n");
      
                for (i = 0; i < nomesAgencias.length; i++) {
                      if (nomesAgencias[i] != null) {
                         System.out.println("ID: "+nomesAgencias[i].substring(0, nomesAgencias[i].indexOf("|")) +"] | "
                        + "Nome Agência: [" +nomesAgencias[i].substring(nomesAgencias[i].indexOf("|") + 1)+"]");         
                      }
                }
                
                System.out.println("\n====ELIMINAR AGENCIA====");
                System.out.println("Não existe nenhuma Agência com esse ID,");
            }
            
            
            System.out.print("\nIntroduza o ID da [Agência]: ");
            identificador = inputAgencia.nextInt();
             System.out.println("Id poara eliminar " + identificador);
            existe = db.verificaIDAgencia(identificador);
           
        } while (existe == true);
      
         if (db.apagarAgencia(identificador)) {
            snp.clear();
            System.out.println("Agência Eliminada! \nPodes criar uma nova no Menu.");
            try {
              TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {}
            mainMenu();
        } else {
            snp.clear();
            System.out.println("Ocorreu ao Eliminar! \nTenta Novamente");
            try {
              TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {}
            mainMenu();
        };
  
  }
  
  private void mostrarAgencias() {
    controlador = 0;
    snp.clear();
    
    for (i = 0; i < totalAgencias.length; i++) {
          if (totalAgencias[i] != null) {
              totalAgencias[i] = null;
          }
    }
      
    totalAgencias = db.todasAgencias();
    System.out.println("====AGÊNCIAS====");
    for (i = 0; i < totalAgencias.length; i++) {
      if (totalAgencias[i] != null) {
        System.out.println("\nAGÊNCIA: [" +totalAgencias[i].substring(0, totalAgencias[i].indexOf("|"))+"]"
                        + " | ESTADO: ["+totalAgencias[i].substring(totalAgencias[i].indexOf("|") + 1,totalAgencias[i].lastIndexOf("|"))+"]"
                        + " | GESTOR: ["+totalAgencias[i].substring(totalAgencias[i].lastIndexOf("|") + 1)+"]");
        controlador++;
      }
    }
    
        if(controlador == 0) {
         snp.clear();
         System.out.println("\nNão existe nenhuma Agência de momento!");
         try {
         TimeUnit.SECONDS.sleep(2);
       } catch (InterruptedException ex) {}
       mainMenu();
        } else {
            try {
System.out.println("\nDentro de 10 segundos serás redirecionado para o Menu!\n");
              for(i=0; i < 10; i++) {
                  System.out.print("-");
                  TimeUnit.SECONDS.sleep(1);
              }
              
            } catch (InterruptedException ex) {}
            mainMenu();
       };
  
  }
  
}