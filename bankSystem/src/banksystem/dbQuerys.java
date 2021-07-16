package banksystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

public class dbQuerys {
  //session vars conta
  private int tipo;
  private int key;
  private int i;
  private int identificador;
  private String nome;
  private String dtNascimento;
  private String situacaoLaboral;
  private String numTelemovel;
  
  private float saldo;

  private String[] agencias = new String[20];
  private String[] gestores = new String[100];
  private String[] nomesAgencia = new String[100];
  private String[] totalAgencias = new String[100];
  private String infosSolicitada;

  private int cont;

  //Variaveis da Data Base
  String caminhoDB = "jdbc:mysql://localhost:3306/banksystem?zeroDateTimeBehavior=CONVERT_TO_NULL";
  
  
  String userDB = "root";
  String passDB = "";

  public dbQuerys() {
    this.tipo = 0;
    this.identificador = 0;
  }


  public boolean registarConta(int nif, String nome, String dtNascimento, int idade, String codcc, String situacaoLaboral, String numTelemovel, String username, String password, String morada, String codigoP, String cidade, int tipo) {
      
    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
        

      conn.setAutoCommit(false);

      Statement stmt = conn.createStatement();
      String dadosPQue = "INSERT INTO dadospessoais (nif, nome, dtNascimento, idade, CodCC, situacaoLaboral, numTelemovel) VALUES (?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement preparedStmt = conn.prepareStatement(dadosPQue);
      preparedStmt.setInt(1, nif);
      preparedStmt.setString(2, nome);
      preparedStmt.setString(3, dtNascimento);
      preparedStmt.setInt(4, idade);
      preparedStmt.setString(5, codcc);
      preparedStmt.setString(6, situacaoLaboral);
      preparedStmt.setString(7, numTelemovel);
      preparedStmt.execute();

      String dadosQue = "INSERT INTO dadoslogin (idDadosPessoais, username, password, tipo) VALUES (?, ?, SHA1(?), ?)";
      PreparedStatement preparedStmt1 = conn.prepareStatement(dadosQue);
      preparedStmt1.setInt(1, nif);
      preparedStmt1.setString(2, username);
      preparedStmt1.setString(3, password);
      preparedStmt1.setInt(4, tipo);
      preparedStmt1.execute();

      String moradaQue = "INSERT INTO morada (morada, codigoP, cidade, idDadosPessoais) VALUES (?, ?, ?, ?)";
      PreparedStatement preparedStmt2 = conn.prepareStatement(moradaQue);
      preparedStmt2.setString(1, morada);
      preparedStmt2.setString(2, codigoP);
      preparedStmt2.setString(3, cidade);
      preparedStmt2.setInt(4, nif);
      preparedStmt2.execute();
      conn.commit();
      conn.close();
      stmt.close();

      System.out.println("------------- Deu para inserir ! ----------------");
      return true;

    } catch (SQLException e) {
      System.err.println("Erro ao inserir na base de dados ");
      System.err.println(e.getMessage());
      return false;
    }
  }
  //verificação de campos na data base
  public int verificaLogin(String username, String password) {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT username, password, idDadosPessoais, tipo FROM dadoslogin WHERE username = ? AND password = SHA(?)";

      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setString(1, username);
      preparedStmt.setString(2, password);
      ResultSet rs = preparedStmt.executeQuery();

      // caso encontre entra dentro deste if
 
      if (rs.next()) {
          
        this.tipo = rs.getInt("tipo");
        this.identificador = rs.getInt("idDadosPessoais");
        
        String teste = "SELECT nome, dtNascimento, situacaoLaboral, numTelemovel FROM dadospessoais WHERE nif = ?";
        PreparedStatement preparedStmt2 = conn.prepareStatement(teste);
        preparedStmt2.setInt(1, identificador);
        ResultSet rsdp = preparedStmt2.executeQuery();
        
        if(rsdp.next()) {
            this.nome = rsdp.getString("nome");
            this.dtNascimento = rsdp.getString("dtNascimento");
            this.situacaoLaboral = rsdp.getString("situacaoLaboral");
            this.numTelemovel = rsdp.getString("numTelemovel");
        }
        
        
        personalData.identificador = identificador;
        personalData.tipo = tipo;
        personalData.nome = nome;
        personalData.dtNascimento = dtNascimento;
        personalData.situacaoLaboral = situacaoLaboral;
        personalData.numTelemovel = numTelemovel;
 
        
        conn.close();
        rs.close();
        stmt.close();
        return tipo;
      }

        conn.close();
        rs.close();
        stmt.close();
      return -1;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
    return -1;
  }

  
  
  
  
  public boolean verificaNIFCriado(int nif) {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT * FROM dadospessoais WHERE nif = ?";

      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setInt(1, nif);
      ResultSet rs = preparedStmt.executeQuery();

      if (rs.next()) {
          conn.close();
          rs.close();
        stmt.close();
        return true;
      }

        conn.close();
        rs.close();
        stmt.close();
      return false;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
    return false;
  }

  public boolean verificaUsernameCriado(String username) {
    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT * FROM dadoslogin WHERE username = ?";

      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setString(1, username);
      ResultSet rs = preparedStmt.executeQuery();

      if (rs.next()) {
         conn.close();
        rs.close();
        stmt.close();
        return true;
      }

        conn.close();
        rs.close();
        stmt.close();
      return false;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
    return false;
  }

  public boolean verificaSolicitacoesBancarias() {
    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT * FROM contasolicitada WHERE idDadosPessoais = ? AND estado = ?";

      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setInt(1, personalData.identificador);
      preparedStmt.setInt(2, 1);
      ResultSet rs = preparedStmt.executeQuery();

      if (rs.next()) {
          conn.close();
        rs.close();
        stmt.close();
        return true;
      }

        conn.close();
        rs.close();
        stmt.close();
      return false;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
    return false;
  }

  public String[] verificarAgencias(int estado) {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT id, nomeAgencia FROM agencias WHERE estadoAgencia=?;";
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setInt(1, estado);
      ResultSet rs = preparedStmt.executeQuery();
      cont = 0;

      for (i = 0; i < agencias.length; i++) {
        if (agencias[i] != null) {
          agencias[i] = null;
        }
      }

      while (rs.next()) {
        agencias[cont] = String.valueOf(rs.getInt("id")) + "|" + rs.getString("nomeAgencia");
        cont++;
      }

      // System.out.println("sair");
        conn.close();
        rs.close();
        stmt.close();
      return agencias;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
    return agencias;
}
 
  public boolean solicitarConta(int idAgencia) {
    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      conn.setAutoCommit(false);
      Statement stmt = conn.createStatement();
      String dadosPQue = "INSERT INTO contasolicitada (idDadosPessoais, idAgencia) VALUES (?, ?)";
      PreparedStatement preparedStmt = conn.prepareStatement(dadosPQue);
      preparedStmt.setInt(1, personalData.identificador);
      preparedStmt.setInt(2, idAgencia);
      
        System.out.println("ID PESSOA: " + personalData.identificador);
        System.out.println("ID AGENCIA " + idAgencia);
      preparedStmt.execute();
      conn.commit();
      conn.close();
      stmt.close();
      return true;
    } catch (SQLException e) {
      System.err.println("Erro ao inserir na base de dados ");
      System.err.println(e.getMessage());
      return false;
    }
  }

  public String estadoSolicitacao() {
    cont = 0;
    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT CS.idAgencia, A.nomeAgencia, est.label FROM contasolicitada CS INNER JOIN agencias A ON CS.idAgencia=A.id " +
        "INNER JOIN estadocsolicitadas est ON CS.estado= est.idEstado " +
        "WHERE CS.estado=? AND CS.idDadosPessoais = ?;";
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setInt(1, 1);
      preparedStmt.setInt(2, personalData.identificador);
      ResultSet rs = preparedStmt.executeQuery();

      if (rs.next()) {
        infosSolicitada = "IDENTIFICADOR: [" + rs.getString("CS.idAgencia") + "] | AGÊNCIA: [" + rs.getString("A.nomeAgencia") + "] | ESTADO: [" + rs.getString("est.label") + "]";
          conn.close();
        rs.close();
        stmt.close();
        return infosSolicitada;
      }

      // System.out.println("sair");
        conn.close();
        rs.close();
        stmt.close();
      return infosSolicitada;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
    return infosSolicitada;
  }

  public boolean desativarAgencia(int idAgencia) {
    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      conn.setAutoCommit(false);
      Statement stmt = conn.createStatement();
      String dadosPQue = "UPDATE agencias set estadoAgencia=? WHERE id =?";
      PreparedStatement preparedStmt = conn.prepareStatement(dadosPQue);
      preparedStmt.setInt(1, 1);
      preparedStmt.setInt(2, idAgencia);
      preparedStmt.execute();
      conn.commit();
        conn.close();
        stmt.close();
      return true;
    } catch (SQLException e) {
      System.err.println("Erro ao inserir na base de dados ");
      System.err.println(e.getMessage());
      return false;
    }
  }

  public boolean ativarAgencia(int idAgencia) {
    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      conn.setAutoCommit(false);
      Statement stmt = conn.createStatement();
      String dadosPQue = "UPDATE agencias set estadoAgencia=? WHERE id =?";
      PreparedStatement preparedStmt = conn.prepareStatement(dadosPQue);
      preparedStmt.setInt(1, 0);
      preparedStmt.setInt(2, idAgencia);
      preparedStmt.execute();
      conn.commit();
        conn.close();
        stmt.close();
      return true;
    } catch (SQLException e) {
      System.err.println("Erro ao inserir na base de dados ");
      System.err.println(e.getMessage());
      return false;
    }
  }

  public boolean existeAgencia(int idAgencia) {
    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {

      Statement stmt = conn.createStatement();
      String dadosPQue = "SELECT * FROM agencias WHERE estadoAgencia=?";
      PreparedStatement preparedStmt = conn.prepareStatement(dadosPQue);
      preparedStmt.setInt(1, idAgencia);

      ResultSet rs = preparedStmt.executeQuery();

      if(rs.next()) {
          conn.close();
        rs.close();
        stmt.close();
        return true;
      }

        conn.close();
        rs.close();
        stmt.close();
      return false;
    } catch (SQLException e) {
      System.err.println("Erro ao inserir na base de dados ");
      System.err.println(e.getMessage());
      return false;
    }
  }
  
  public boolean verificaNomeAgencia(String nomeAg) {
      try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
        Statement stmt = conn.createStatement();
        String dadosPQue = "SELECT * FROM agencias WHERE nomeAgencia = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(dadosPQue);
        preparedStmt.setString(1, nomeAg);

        ResultSet rs = preparedStmt.executeQuery();

        if(rs.next()) {
            conn.close();
        rs.close();
        stmt.close();
          return true;
        }

          conn.close();
        rs.close();
        stmt.close();
        return false;
    } catch (SQLException e) {
        System.err.println("Erro ao inserir na base de dados ");
        System.err.println(e.getMessage());
        return false;
    }
  }

  public String[] nomesGestores() {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT DP.nif, DP.nome, DL.id FROM dadospessoais AS DP "
              + "INNER JOIN dadoslogin AS DL ON DP.nif=DL.idDadosPessoais WHERE DL.tipo=?";
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setInt(1, 2);
      ResultSet rs = preparedStmt.executeQuery();
      cont = 0;

      for (i = 0; i < gestores.length; i++) {
        if (gestores[i] != null) {
          gestores[i] = null;
        }
      }

      while (rs.next()) {
        gestores[cont] = String.valueOf(rs.getInt("DL.id"))+"|"+rs.getString("DP.nome")+"|"+String.valueOf(rs.getInt("DP.nif"));
        cont++;
      }

      // System.out.println("sair");
       conn.close();
        rs.close();
        stmt.close();
      return gestores;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
    return gestores;
}
  
  public String[] nomesAgencias() {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT nomeAgencia FROM agencias WHERE estadoAgencia = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setInt(1, 0);
      ResultSet rs = preparedStmt.executeQuery();
      cont = 0;

      for (i = 0; i < nomesAgencia.length; i++) {
        if (nomesAgencia[i] != null) {
          nomesAgencia[i] = null;
        }
      }

      while(rs.next()) {
        nomesAgencia[cont] = rs.getString("nomeAgencia");
        cont++;
      }

      // System.out.println("sair");
       conn.close();
        rs.close();
        stmt.close();
      return nomesAgencia;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
    return nomesAgencia;
}

  public boolean existeGestor(int idConta) {
      try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
        Statement stmt = conn.createStatement();
        String dadosPQue = "SELECT * FROM dadoslogin WHERE id=? AND tipo=?";
        PreparedStatement preparedStmt = conn.prepareStatement(dadosPQue);
        preparedStmt.setInt(1, idConta);
        preparedStmt.setInt(2, 2);

        ResultSet rs = preparedStmt.executeQuery();

        if(rs.next()) {
            conn.close();
            rs.close();
            stmt.close();
          return true;
        }

          conn.close();
        rs.close();
        stmt.close();
        return false;
    } catch (SQLException e) {
        System.err.println("Erro ao inserir na base de dados ");
        System.err.println(e.getMessage());
        return false;
    }
  }
  
  
  
  public boolean existeCB(int idConta) {
      try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
        Statement stmt = conn.createStatement();
        String dadosPQue = "SELECT * FROM contabancaria WHERE idConta=?";
        PreparedStatement preparedStmt = conn.prepareStatement(dadosPQue);
        preparedStmt.setInt(1, idConta);

        ResultSet rs = preparedStmt.executeQuery();

        if(rs.next()) {
            conn.close();
            rs.close();
            stmt.close();
          return true;
        }

          conn.close();
        rs.close();
        stmt.close();
        return false;
    } catch (SQLException e) {
        System.err.println("Erro ao inserir na base de dados ");
        System.err.println(e.getMessage());
        return false;
    }
  }
  
  
  
  public boolean inserirAgencia(String nomeAgencia, int idGestor) {
    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      conn.setAutoCommit(false);
      Statement stmt = conn.createStatement();
      String qry = "INSERT INTO agencias (idDadosPessoais, nomeAgencia) "
              + "VALUES ((SELECT idDadosPessoais FROM dadoslogin WHERE id=?), ?);";
      PreparedStatement preparedStmt = conn.prepareStatement(qry);
      preparedStmt.setInt(1, idGestor);
      preparedStmt.setString(2, nomeAgencia);
      preparedStmt.execute();
      
      System.out.println("Deu para inserir");
      
      conn.commit();
        conn.close();
        stmt.close();
      return true;
    } catch (SQLException e) {
      System.err.println("Erro ao inserir na base de dados ");
      System.err.println(e.getMessage());
      return false;
    }
  }
  
  public boolean verificaIDAgencia(int idAg) {
      try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
        Statement stmt = conn.createStatement();
        String dadosPQue = "SELECT * FROM agencias WHERE id = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(dadosPQue);
        preparedStmt.setInt(1, idAg);

        ResultSet rs = preparedStmt.executeQuery();

        if(rs.next()) {
           conn.close();
        rs.close();
        stmt.close();
          return false;
        }

        conn.close();
        rs.close();
        stmt.close();
        return true;
    } catch (SQLException e) {
        System.err.println("Erro ao inserir na base de dados ");
        System.err.println(e.getMessage());
        return true;
    }
  }
  
  
    public String[] nomesAgenciasID() {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT id, nomeAgencia FROM agencias WHERE estadoAgencia = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setInt(1, 0);
      ResultSet rs = preparedStmt.executeQuery();
      cont = 0;

      for (i = 0; i < nomesAgencia.length; i++) {
        if (nomesAgencia[i] != null) {
          nomesAgencia[i] = null;
        }
      }

      while(rs.next()) {
        nomesAgencia[cont] = rs.getInt("id") +"|"+ rs.getString("nomeAgencia");
        cont++;
      }

      // System.out.println("sair");
      conn.close();
      rs.close();
      stmt.close();
      return nomesAgencia;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
    return nomesAgencia;
}
    
public boolean apagarAgencia(int id) {
      try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
        Statement stmt = conn.createStatement();
 
 
      String query = "DELETE FROM agencias WHERE id = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setInt(1, id);

      // execute the preparedstatement
      preparedStmt.executeUpdate();
      
     
      
        conn.close();
        stmt.close();
        return true;
    } catch (SQLException e) {
        System.err.println("Erro ao inserir na base de dados ");
        System.err.println(e.getMessage());
        return false;
    }
  }

  
  
   public boolean verificaExisteAgencias() {
      try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
        Statement stmt = conn.createStatement();
        String dadosPQue = "SELECT * FROM agencias";
        PreparedStatement preparedStmt = conn.prepareStatement(dadosPQue);

        ResultSet rs = preparedStmt.executeQuery();

        if(rs.next()) {
           conn.close();
           rs.close();
           stmt.close();
           return true;
        }

        conn.close();
        rs.close();
        stmt.close();
        return false;
    } catch (SQLException e) {
        System.err.println("Erro ao inserir na base de dados ");
        System.err.println(e.getMessage());
        return false;
    }
  }

     public boolean existeAlgumGestor() {
      try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
        Statement stmt = conn.createStatement();
        String dadosPQue = "SELECT * FROM dadoslogin WHERE tipo=?";
        PreparedStatement preparedStmt = conn.prepareStatement(dadosPQue);
        preparedStmt.setInt(1, 2);

        ResultSet rs = preparedStmt.executeQuery();

        if(rs.next()) {
            conn.close();
        rs.close();
        stmt.close();
          return true;
        }

         conn.close();
        rs.close();
        stmt.close();
        return false;
    } catch (SQLException e) {
        System.err.println("Erro ao inserir na base de dados ");
        System.err.println(e.getMessage());
        return false;
    }
  }
     
     
public String[] todasAgencias() {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT A.nomeAgencia, AE.estadoLabel, DP.nome FROM agencias A "
              + "INNER JOIN dadospessoais DP ON A.idDadosPessoais=DP.nif "
              + "INNER JOIN agenciasestado AE ON A.estadoAgencia=AE.id";
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      ResultSet rs = preparedStmt.executeQuery();
      cont = 0;

      for (i = 0; i < totalAgencias.length; i++) {
        if (totalAgencias[i] != null) {
          totalAgencias[i] = null;
        }
      }

      while (rs.next()) {
        totalAgencias[cont] = rs.getString("A.nomeAgencia") + "|"+ rs.getString("AE.estadoLabel")+ "|"+rs.getString("DP.nome");
        cont++;
      }

      // System.out.println("sair");
       conn.close();
        rs.close();
        stmt.close();
      return totalAgencias;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
    return totalAgencias;
}
   

public Map[] contasSolicitadasAll() {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT dp.nif, dp.nome, dp.dtNascimento, dp.idade, dp.CodCC, dp.situacaoLaboral, dp.numTelemovel, ag.nomeAgencia, ag.id, ecs.label, "
              + "(SELECT dadospessoais.nome FROM dadospessoais INNER JOIN agencias ON agencias.idDadosPessoais = dadospessoais.nif WHERE id = ag.id) AS gestor FROM dadospessoais dp "
              + "INNER JOIN contasolicitada cs ON dp.nif=cs.idDadosPessoais "
              + "INNER JOIN agencias ag ON cs.idAgencia=ag.id "
              + "INNER JOIN estadocsolicitadas ecs ON cs.estado=ecs.idEstado WHERE cs.estado = ?";
      
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setInt(1, 1);
      ResultSet rs = preparedStmt.executeQuery();
     

        List<Map<String, String>> contasInf = new ArrayList<Map<String, String>>();

        while(rs.next()) {
            Map<String, String> map = new HashMap<String, String>();

            map.put("nif", rs.getString("dp.nif"));
            map.put("nome", rs.getString("dp.nome"));
            map.put("dtNasc", rs.getString("dp.dtNascimento"));
            map.put("idade", rs.getString("dp.idade"));
            map.put("codcc", rs.getString("dp.CodCC"));
            map.put("situacaoLaboral", rs.getString("dp.situacaoLaboral"));
            map.put("numTelemovel", rs.getString("dp.numTelemovel"));
            map.put("nomeAgencia", rs.getString("ag.nomeAgencia"));
            map.put("idAgencia", rs.getString("ag.id"));
            map.put("label", rs.getString("ecs.label"));
            map.put("gestor", rs.getString("gestor"));
            contasInf.add(map);
 
        }
        Map[] mapContas = contasInf.toArray(new HashMap[contasInf.size()]);
        
       conn.close();
       rs.close();
       stmt.close();
       return mapContas;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
      return null; 
}



 public boolean mudarEstadoSolicitada(int nif, int novoEstado, int estado, String nota) {
    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      conn.setAutoCommit(false);
      Statement stmt = conn.createStatement();
      String dadosPQue = "UPDATE contasolicitada set estado=?, nota = ? WHERE idDadosPessoais = ? AND estado = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(dadosPQue);
      preparedStmt.setInt(1, novoEstado);
      preparedStmt.setString(2, nota);
      preparedStmt.setInt(3, nif);
      preparedStmt.setInt(4, estado);
      preparedStmt.execute();
      conn.commit();
      conn.close();
      stmt.close();
      return true;
    } catch (SQLException e) {
      System.err.println("Erro ao inserir na base de dados ");
      System.err.println(e.getMessage());
      return false;
    }
  }

 
 public Map[] todosGestores() {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT dp.nif, dp.nome, dp.dtNascimento, dp.idade, dp.CodCC, dp.numTelemovel FROM dadospessoais AS dp "
              + "INNER JOIN dadoslogin AS dl ON dp.nif=dl.idDadosPessoais WHERE dl.tipo = ?";
      
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setInt(1, 2);
      ResultSet rs = preparedStmt.executeQuery();
     

        List<Map<String, String>> contasInf = new ArrayList<Map<String, String>>();

        while(rs.next()) {
            Map<String, String> map = new HashMap<String, String>();

            map.put("nif", rs.getString("dp.nif"));
            map.put("nome", rs.getString("dp.nome"));
            map.put("dtNasc", rs.getString("dp.dtNascimento"));
            map.put("idade", rs.getString("dp.idade"));
            map.put("codcc", rs.getString("dp.CodCC"));
            map.put("numTelemovel", rs.getString("dp.numTelemovel"));
            contasInf.add(map);
 
        }
        Map[] mapContas = contasInf.toArray(new HashMap[contasInf.size()]);
        
       conn.close();
       rs.close();
       stmt.close();
       return mapContas;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
      return null; 
}
 
  public Map[] todosFunc() {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT dp.nif, dp.nome, dp.dtNascimento, dp.idade, dp.CodCC, dp.numTelemovel FROM dadospessoais AS dp "
              + "INNER JOIN dadoslogin AS dl ON dp.nif=dl.idDadosPessoais WHERE dl.tipo = ?";
      
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setInt(1, 1);
      ResultSet rs = preparedStmt.executeQuery();
     

        List<Map<String, String>> contasInf = new ArrayList<Map<String, String>>();

        while(rs.next()) {
            Map<String, String> map = new HashMap<String, String>();

                map.put("nif", rs.getString("dp.nif"));
                map.put("nome", rs.getString("dp.nome"));
                map.put("dtNasc", rs.getString("dp.dtNascimento"));
                map.put("idade", rs.getString("dp.idade"));
                map.put("codcc", rs.getString("dp.CodCC"));
                map.put("numTelemovel", rs.getString("dp.numTelemovel"));
            contasInf.add(map);
 
        }
        Map[] mapContas = contasInf.toArray(new HashMap[contasInf.size()]);
        
       conn.close();
       rs.close();
       stmt.close();
       return mapContas;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
      return null; 
}
 
 
 public boolean eliminarFuncionario(int nif) {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {

      conn.setAutoCommit(false);

      Statement stmt = conn.createStatement();
      String morada = "DELETE FROM morada WHERE idDadosPessoais = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(morada);
      preparedStmt.setInt(1, nif);
      preparedStmt.execute();

      String dadosLogin = "DELETE FROM dadoslogin WHERE idDadosPessoais = ?";
      PreparedStatement preparedStmt1 = conn.prepareStatement(dadosLogin);
      preparedStmt1.setInt(1, nif);
      preparedStmt1.execute();

      String dadosP = "DELETE FROM dadospessoais WHERE nif = ?";
      PreparedStatement preparedStmt2 = conn.prepareStatement(dadosP);
      preparedStmt2.setInt(1, nif);
      preparedStmt2.execute();
      conn.commit();
      conn.close();
      stmt.close();

      System.out.println("------------- Deu para eliminar ! ----------------");
      return true;

    } catch (SQLException e) {
      System.err.println("Erro ao inserir na base de dados ");
      System.err.println(e.getMessage());
      return false;
    }
  }
 
 
 public boolean gerarConta(int nif, String iban, int idAgencia) {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {

      conn.setAutoCommit(false);

      Statement stmt = conn.createStatement();
      String idConta = "INSERT INTO contaid (idDadosPessoais) VALUES(?);";
      PreparedStatement preparedStmt = conn.prepareStatement(idConta);
      preparedStmt.setInt(1, nif);
      preparedStmt.execute();

      String id = "SELECT idConta FROM contaid WHERE idDadosPessoais=? ORDER BY idConta DESC LIMIT 1;";
      PreparedStatement preparedStmt1 = conn.prepareStatement(id);
      preparedStmt1.setInt(1, nif);

      ResultSet rs = preparedStmt1.executeQuery();
     
        System.out.println("RS " + rs);

        if(rs.next()) {
            key= rs.getInt("idConta");
            rs.close();
        } else {
            rs.close();
            conn.close();
            stmt.close();
        }
      

      String contaBancaria = "INSERT INTO contabancaria (idConta, saldo, iban, idAgencia) VALUES (?,?,?,?);";
      PreparedStatement preparedStmt2 = conn.prepareStatement(contaBancaria);
      preparedStmt2.setInt(1, key);
      preparedStmt2.setFloat(2, 0);
      preparedStmt2.setString(3, iban);
      preparedStmt2.setInt(4, idAgencia);
      preparedStmt2.execute();
     
      
      
      conn.commit();
      conn.close();
      stmt.close();

      System.out.println("------------- Deu para inserir conta ! ----------------");
      return true;

    } catch (SQLException e) {
      System.err.println("Erro ao inserir na base de dados ");
      System.err.println(e.getMessage());
      return false;
    }
  }
 
 
 public Map[] allContasBancarias() {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT cb.idConta, cb.iban, cb.idAgencia, ag.nomeAgencia FROM contabancaria cb INNER JOIN agencias ag ON cb.idAgencia=ag.id INNER JOIN contaid ci ON ci.idConta=cb.idConta WHERE ci.idDadosPessoais = ?";
      
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setInt(1, personalData.identificador);
      ResultSet rs = preparedStmt.executeQuery();
     

        List<Map<String, String>> contasB = new ArrayList<Map<String, String>>();

        while(rs.next()) {
            Map<String, String> map = new HashMap<String, String>();

            map.put("idConta", rs.getString("cb.idConta"));
            map.put("iban", rs.getString("cb.iban"));
            map.put("idAgencia", rs.getString("cb.idAgencia"));
            map.put("nomeAgencia", rs.getString("ag.nomeAgencia"));
            contasB.add(map);
 
        }
        Map[] mapContas = contasB.toArray(new HashMap[contasB.size()]);
        
       conn.close();
       rs.close();
       stmt.close();
       return mapContas;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
      return null; 
}
 
 
 
  public float verificaSaldo() {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT saldo FROM contabancaria WHERE idConta = ?";
      
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setInt(1, personalData.idContaBancaria);
      ResultSet rs = preparedStmt.executeQuery();


        if(rs.next()) {
            saldo = rs.getFloat("saldo");
        } else {
            saldo = 0; 
        }

       conn.close();
       rs.close();
       stmt.close();
       
       return saldo;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
      return saldo; 
}
 
 
   public boolean levantarDinheiro(float dinheiro) {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "UPDATE contabancaria SET saldo = saldo - ? WHERE idConta = ?";
      
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setFloat(1, dinheiro);
      preparedStmt.setInt(2, personalData.idContaBancaria);
      preparedStmt.executeUpdate();

       conn.close();
       stmt.close();
       
       return true;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
      return false; 
}
  
 public boolean depositarDinheiro(float dinheiro) {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "UPDATE contabancaria SET saldo = saldo + ? WHERE idConta = ?";
      
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setFloat(1, dinheiro);
      preparedStmt.setInt(2, personalData.idContaBancaria);
      preparedStmt.executeUpdate();

       conn.close();
       stmt.close();
       
       return true;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
      return false; 
}
 
  public boolean verificaIban(String iban) {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT iban FROM contabancaria WHERE iban = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setString(1, iban);
      ResultSet rs = preparedStmt.executeQuery();


        if(rs.next()) {
            return true;
        } 

       conn.close();
       stmt.close();
       return false;
    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
      return false; 
}
  
  
  
  public boolean transferirDinheiro(String ibanDestino, float quantia) {   
    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
        

      conn.setAutoCommit(false);

      Statement stmt = conn.createStatement();
      String conta1 = "UPDATE contabancaria set saldo = saldo - ? WHERE idConta = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(conta1);
      preparedStmt.setFloat(1, quantia);
      preparedStmt.setInt(2, personalData.idContaBancaria);
      preparedStmt.executeUpdate();

      
      String conta2 = "UPDATE contabancaria set saldo = saldo + ? WHERE iban = ?";
      PreparedStatement preparedStmt1 = conn.prepareStatement(conta2);
      preparedStmt1.setFloat(1, quantia);
      preparedStmt1.setString(2, ibanDestino);
      preparedStmt1.executeUpdate();


      conn.commit();
      conn.close();
      stmt.close();

    
      return true;

    } catch (SQLException e) {
      System.err.println("Erro ao inserir na base de dados ");
      System.err.println(e.getMessage());
      return false;
    }
  }
  
  
  
  public boolean gerarcsv(int idContaBancaria) {
  try {
      String file = "_extrato_"+idContaBancaria+".csv";
      boolean header = false;
    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
        Statement stmt = conn.createStatement();
        String querie = "SELECT lg.descricao, lg.data, cb.iban,dp.nome, ag.nomeAgencia FROM logs lg INNER JOIN contabancaria cb ON lg.idContaBancaria=cb.idConta INNER JOIN dadospessoais dp ON dp.nif=lg.idDadosPessoais INNER JOIN agencias ag ON cb.idAgencia=ag.id WHERE lg.idContaBancaria=?";
        PreparedStatement preparedStmt = conn.prepareStatement(querie);
        preparedStmt.setInt(1, idContaBancaria);
        ResultSet rs = preparedStmt.executeQuery();
        PrintWriter pw= new PrintWriter(new File(System.getProperty("user.dir")+file));
        StringBuilder scv=new StringBuilder();
        int contador = 0;
           while(rs.next()) {
               cont++;
               
            if(!header) {
                Random rand = new Random();
                String iban = rs.getString("cb.iban");
                String ID = rand.nextInt(1000) + "";
                String titular = rs.getString("dp.nome");    
                scv.append("IBAN:,"+iban+"\n");
                scv.append("ID:,"+ID+"\n");
                scv.append("AGENCIA:,BPINET\n");
                scv.append("TITULAR:,"+titular+"\n\n");
                scv.append("CONSULTA MOVIMENTO DE CONTA\n\n");
                header = true;                 
            }

            String data = rs.getString("lg.data");
            String descricao = rs.getString("lg.descricao");

            data = data.substring(0,10);
            data = data.replace('-', '|');

            scv.append(data+",,"+descricao + "EUR\n");

            } 
            float saldo = verificaSaldo();
            scv.append("\nSaldo Contabilistico,,"+saldo+"EUR\n");
       
         pw.write(scv.toString());
         pw.close();
         conn.close();
         stmt.close();
         if(cont > 0) {
                 return true;
         } else {
              return false;
         }
        
      } catch (SQLException e) { 
        System.err.println("Erro ao selecionar dados ao db");
        System.err.println(e.getMessage());
         return false;
      }
  } catch (Exception e) {
       // TODO: handle exception
   }
  return false; 
  }
  

  
  
   public Map[] dadosConta(int idConta) {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT cb.iban, cb.dataAbertura, ag.nomeAgencia, dp.nome, dp.nif ,dp.dtNascimento, dp.idade, dp.CodCC, dp.situacaoLaboral, dp.numTelemovel FROM contabancaria cb "
              + "INNER JOIN agencias ag ON cb.idAgencia = ag.id "
              + "INNER JOIN contaid ci on ci.idConta=cb.idConta "
              + "INNER JOIN dadospessoais dp ON dp.nif=ci.idDadosPessoais "
              + "WHERE cb.idConta = ?";
      
     PreparedStatement preparedStmt = conn.prepareStatement(querie);
     preparedStmt.setInt(1, idConta);
     ResultSet rs = preparedStmt.executeQuery();
     

    List<Map<String, String>> dadosP = new ArrayList<Map<String, String>>();

    while(rs.next()) {
        Map<String, String> map = new HashMap<String, String>();

        map.put("iban", rs.getString("cb.iban"));
        map.put("dataAbertura", rs.getString("cb.dataAbertura"));
        map.put("nomeAgencia", rs.getString("ag.nomeAgencia"));
        map.put("nome", rs.getString("dp.nome"));
        map.put("dtNascimento", rs.getString("dp.dtNascimento"));
        map.put("idade", rs.getString("dp.idade"));
        map.put("nif", rs.getString("dp.nif"));
        map.put("CodCC", rs.getString("dp.CodCC"));
        map.put("situacaoLaboral", rs.getString("dp.situacaoLaboral"));
        map.put("numTelemovel", rs.getString("dp.numTelemovel"));
        
        dadosP.add(map);

    }
    
      Map[] mapDados = dadosP.toArray(new HashMap[dadosP.size()]);
        
       conn.close();
       rs.close();
       stmt.close();
       return mapDados;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
      return null; 
}
   
   
   
 public boolean updateNome(String novoNome, int id) {
        try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
          conn.setAutoCommit(false);
          Statement stmt = conn.createStatement();
          String data = "UPDATE dadospessoais set nome=? WHERE nif =?";
          PreparedStatement preparedStmt = conn.prepareStatement(data);
          preparedStmt.setString(1, novoNome);
          preparedStmt.setInt(2, id);
          preparedStmt.execute();
          conn.commit();
          conn.close();
          stmt.close();
          return true;
        } catch (SQLException e) {
          System.err.println("Erro ao inserir na base de dados ");
          System.err.println(e.getMessage());
          return false;
        }
  }
 
 
 public boolean updateDataNascimento(String dataNascimento, int idade, int id) {
        try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
          conn.setAutoCommit(false);
          Statement stmt = conn.createStatement();
          String data = "UPDATE dadospessoais set dtNascimento=?, idade=? WHERE nif =?";
          PreparedStatement preparedStmt = conn.prepareStatement(data);
          preparedStmt.setString(1, dataNascimento);
          preparedStmt.setInt(2, idade);
          preparedStmt.setInt(3, id);
          preparedStmt.execute();
          conn.commit();
          conn.close();
          stmt.close();
          return true;
        } catch (SQLException e) {
          System.err.println("Erro ao inserir na base de dados ");
          System.err.println(e.getMessage());
          return false;
        }
  }
 
 
 public boolean updateSituacaoLaboral(String situacao, int id) {
        try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
          conn.setAutoCommit(false);
          Statement stmt = conn.createStatement();
          String data = "UPDATE dadospessoais set situacaoLaboral=? WHERE nif =?";
          PreparedStatement preparedStmt = conn.prepareStatement(data);
          preparedStmt.setString(1, situacao);
          preparedStmt.setInt(2, id);
          preparedStmt.execute();
          conn.commit();
          conn.close();
          stmt.close();
          return true;
        } catch (SQLException e) {
          System.err.println("Erro ao inserir na base de dados ");
          System.err.println(e.getMessage());
          return false;
        }
  }
 
 
public boolean updateTelemovel(String num, int id) {
        try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
          conn.setAutoCommit(false);
          Statement stmt = conn.createStatement();
          String data = "UPDATE dadospessoais set numTelemovel=? WHERE nif =?";
          PreparedStatement preparedStmt = conn.prepareStatement(data);
          preparedStmt.setString(1, num);
          preparedStmt.setInt(2, id);
          preparedStmt.execute();
          conn.commit();
          conn.close();
          stmt.close();
          return true;
        } catch (SQLException e) {
          System.err.println("Erro ao inserir na base de dados ");
          System.err.println(e.getMessage());
          return false;
        }
}
  

public boolean updateCodCC(String num, int id) {
        try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
          conn.setAutoCommit(false);
          Statement stmt = conn.createStatement();
          String data = "UPDATE dadospessoais set CodCC=? WHERE nif =?";
          PreparedStatement preparedStmt = conn.prepareStatement(data);
          preparedStmt.setString(1, num);
          preparedStmt.setInt(2, id);
          preparedStmt.execute();
          conn.commit();
          conn.close();
          stmt.close();
          return true;
        } catch (SQLException e) {
          System.err.println("Erro ao inserir na base de dados ");
          System.err.println(e.getMessage());
          return false;
        }
}
  




public boolean verificaMarcacoes() {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT * FROM marcacoespendentes WHERE nifCliente = ? AND estado = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setInt(1, personalData.identificador);
      preparedStmt.setInt(2, 1);
      ResultSet rs = preparedStmt.executeQuery();


        if(rs.next()) {
            return true;
        } 

       conn.close();
       stmt.close();
       return false;
    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
      return false; 
}


public boolean inserirMarcacao(String dataHora) {
        try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
          conn.setAutoCommit(false);
          Statement stmt = conn.createStatement();
          String data = "INSERT INTO marcacoespendentes (nifCliente, dataHora) VALUES (?,?)";
          PreparedStatement preparedStmt = conn.prepareStatement(data);
          preparedStmt.setInt(1, personalData.identificador);
          preparedStmt.setString(2, dataHora);
          preparedStmt.execute();
          conn.commit();
          conn.close();
          stmt.close();
          return true;
        } catch (SQLException e) {
          System.err.println("Erro ao inserir na base de dados ");
          System.err.println(e.getMessage());
          return false;
        }
}
     



public Map[] marcacoes() {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT m.id, m.nifCliente, m.dataHora, dp.nome, em.label FROM marcacoespendentes m INNER JOIN dadospessoais dp ON m.nifCliente=dp.nif INNER JOIN estadomarcacoes em ON m.estado=em.id WHERE m.estado=?";
      
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setInt(1, 1);
      ResultSet rs = preparedStmt.executeQuery();
     

        List<Map<String, String>> marcaP = new ArrayList<Map<String, String>>();

        while(rs.next()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", rs.getString("m.id"));
            map.put("nif", rs.getString("m.nifCliente"));
            map.put("dataHora", rs.getString("m.dataHora"));
            map.put("nome", rs.getString("dp.nome"));
            map.put("estado", rs.getString("em.label"));
            marcaP.add(map);
        }
        Map[] mapMarcacoes = marcaP.toArray(new HashMap[marcaP.size()]);
        
       conn.close();
       rs.close();
       stmt.close();
       return mapMarcacoes;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
      return null; 
}


public Map[] marcacoesAll() {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT m.id, m.nifCliente, m.dataHora, dp.nome, em.label FROM marcacoespendentes m INNER JOIN dadospessoais dp ON m.nifCliente=dp.nif INNER JOIN estadomarcacoes em ON m.estado=em.id WHERE m.estado IN (?,?,?,?)";
      
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setInt(1, 1);
      preparedStmt.setInt(2, 2);
      preparedStmt.setInt(3, 3);
      preparedStmt.setInt(4, 4);
      ResultSet rs = preparedStmt.executeQuery();
     

        List<Map<String, String>> marcaP = new ArrayList<Map<String, String>>();

        while(rs.next()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", rs.getString("m.id"));
            map.put("nif", rs.getString("m.nifCliente"));
            map.put("dataHora", rs.getString("m.dataHora"));
            map.put("nome", rs.getString("dp.nome"));
            map.put("estado", rs.getString("em.label"));
            marcaP.add(map);
        }
        Map[] mapMarcacoes = marcaP.toArray(new HashMap[marcaP.size()]);
        
       conn.close();
       rs.close();
       stmt.close();
       return mapMarcacoes;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
      return null; 
}


public boolean updateEstadoMarcacao(int id) {
        try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
          conn.setAutoCommit(false);
          Statement stmt = conn.createStatement();
          String data = "UPDATE marcacoespendentes set estado=? WHERE id =?";
          PreparedStatement preparedStmt = conn.prepareStatement(data);
          preparedStmt.setInt(1, 5);
          preparedStmt.setInt(2, id);
          preparedStmt.execute();
          conn.commit();
          conn.close();
          stmt.close();
          return true;
        } catch (SQLException e) {
          System.err.println("Erro ao inserir na base de dados ");
          System.err.println(e.getMessage());
          return false;
        }
}


public boolean inserirLog(int nif, String tipoLog, String descricao, int idConta) {
        try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
          conn.setAutoCommit(false);
          Statement stmt = conn.createStatement();
          String data = "INSERT INTO logs (idDadosPessoais, tipolog, descricao, idContaBancaria) VALUES(?,?,?,?)";
          PreparedStatement preparedStmt = conn.prepareStatement(data);
          preparedStmt.setInt(1, nif);
          preparedStmt.setString(2, tipoLog);
          preparedStmt.setString(3, descricao);
          preparedStmt.setInt(4, idConta);
          preparedStmt.execute();
          conn.commit();
          conn.close();
          stmt.close();
          return true;
        } catch (SQLException e) {
          System.err.println("Erro ao inserir na base de dados ");
          System.err.println(e.getMessage());
          return false;
        }
}


public Map[] todosFuncionarios() {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT dp.nif, dp.nome, dp.dtNascimento, dp.idade, dp.CodCC, dp.numTelemovel FROM dadospessoais AS dp "
              + "INNER JOIN dadoslogin AS dl ON dp.nif=dl.idDadosPessoais WHERE dl.tipo = ?";
      
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setInt(1, 1);
      ResultSet rs = preparedStmt.executeQuery();
     

        List<Map<String, String>> contasInf = new ArrayList<Map<String, String>>();

        while(rs.next()) {
            Map<String, String> map = new HashMap<String, String>();

            map.put("nif", rs.getString("dp.nif"));
            map.put("nome", rs.getString("dp.nome"));
            map.put("dtNasc", rs.getString("dp.dtNascimento"));
            map.put("idade", rs.getString("dp.idade"));
            map.put("codcc", rs.getString("dp.CodCC"));
            map.put("numTelemovel", rs.getString("dp.numTelemovel"));
            contasInf.add(map);
 
        }
        Map[] mapContas = contasInf.toArray(new HashMap[contasInf.size()]);
        
       conn.close();
       rs.close();
       stmt.close();
       return mapContas;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
      return null; 
}


  public boolean atribuirMarcacao(int idMarcacao, int nifFunc) {   
    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
        

      conn.setAutoCommit(false);

      Statement stmt = conn.createStatement();
      String conta1 = "INSERT INTO marcacoesatribuidas (idMarcacao, nifFuncionario) VALUES(?,?)";
      PreparedStatement preparedStmt = conn.prepareStatement(conta1);
      preparedStmt.setInt(1, idMarcacao);
      preparedStmt.setInt(2, nifFunc);
      preparedStmt.executeUpdate();

      
      String conta2 = "UPDATE marcacoespendentes SET estado = ? WHERE id = ?";
      PreparedStatement preparedStmt1 = conn.prepareStatement(conta2);
      preparedStmt1.setInt(1, 2);
      preparedStmt1.setInt(2, idMarcacao);
      preparedStmt1.executeUpdate();


      conn.commit();
      conn.close();
      stmt.close();

    
      return true;

    } catch (SQLException e) {
      System.err.println("Erro ao inserir na base de dados ");
      System.err.println(e.getMessage());
      return false;
    }
  }

  
  
  
  
  public Map[] marcacoesAtribuidas() {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT ma.idMarcacao, dp.nome, mp.dataHora, mp.descricao, em.label FROM marcacoespendentes mp INNER JOIN marcacoesatribuidas ma ON mp.id=ma.idMarcacao INNER JOIN estadomarcacoes em ON em.id=mp.estado INNER JOIN dadospessoais dp ON dp.nif=mp.nifCliente WHERE mp.estado = ? AND ma.nifFuncionario = ?";
      
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setInt(1, 2);
      preparedStmt.setInt(2, personalData.identificador);
      ResultSet rs = preparedStmt.executeQuery();
   
      
      
        List<Map<String, String>> marcaP = new ArrayList<Map<String, String>>();

        while(rs.next()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", rs.getString("ma.idMarcacao"));
            map.put("nomeCliente", rs.getString("dp.nome"));
            map.put("data", rs.getString("mp.dataHora"));
            map.put("descricao", rs.getString("mp.descricao"));
            map.put("estado", rs.getString("em.label"));
            marcaP.add(map);
        }
        Map[] mapMarcacoes = marcaP.toArray(new HashMap[marcaP.size()]);
        
       
       conn.close();
       rs.close();
       stmt.close();
       return mapMarcacoes;

    } catch (SQLException e) {
      
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
      return null; 
}
  
  

  
  
  public boolean updateComentarioMarcacoes(int idMarcacao, String comentario) {
        try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
          conn.setAutoCommit(false);
          Statement stmt = conn.createStatement();
          String data = "UPDATE marcacoespendentes SET descricao = ? WHERE id = ?";
          PreparedStatement preparedStmt = conn.prepareStatement(data);
          preparedStmt.setString(1, comentario);
          preparedStmt.setInt(2, idMarcacao);
          preparedStmt.execute();
          conn.commit();
          conn.close();
          stmt.close();
          return true;
        } catch (SQLException e) {
          System.err.println("Erro ao inserir na base de dados ");
          System.err.println(e.getMessage());
          return false;
        }
}
  
  
    
  public boolean updateEstadoMarcacoes(int idMarcacao, int estado) {
        try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
          conn.setAutoCommit(false);
          Statement stmt = conn.createStatement();
          String data = "UPDATE marcacoespendentes SET estado = ? WHERE id = ?";
          PreparedStatement preparedStmt = conn.prepareStatement(data);
          preparedStmt.setInt(1, estado);
          preparedStmt.setInt(2, idMarcacao);
          preparedStmt.execute();
          conn.commit();
          conn.close();
          stmt.close();
          return true;
        } catch (SQLException e) {
          System.err.println("Erro ao inserir na base de dados ");
          System.err.println(e.getMessage());
          return false;
        }
}
  
  

public String descricaoFunc(int id) {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT descricao FROM marcacoespendentes WHERE id = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setInt(1, id);
      ResultSet rs = preparedStmt.executeQuery();
      String comment;

        if(rs.next()) {
            return rs.getString("descricao");
        } 

       conn.close();
       stmt.close();
    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
      return null; 
}
  
  
public Map[] dadosContaFunc(int nif) {

    try (Connection conn = DriverManager.getConnection(caminhoDB, userDB, passDB)) {
      Statement stmt = conn.createStatement();
      String querie = "SELECT nome, dtNascimento, idade, CodCC, situacaoLaboral, numTelemovel FROM dadospessoais WHERE nif = ?";
      
      PreparedStatement preparedStmt = conn.prepareStatement(querie);
      preparedStmt.setInt(1, nif);
      ResultSet rs = preparedStmt.executeQuery();
     

        List<Map<String, String>> marcaP = new ArrayList<Map<String, String>>();

        if(rs.next()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("nome", rs.getString("nome"));
            map.put("dtNascimento", rs.getString("dtNascimento"));
            map.put("idade", rs.getString("idade"));
            map.put("CodCC", rs.getString("CodCC"));
            map.put("situacaoLaboral", rs.getString("situacaoLaboral"));
            map.put("numTelemovel", rs.getString("numTelemovel"));
            marcaP.add(map);
        }
        
        Map[] mapMarcacoes = marcaP.toArray(new HashMap[marcaP.size()]);
        
       conn.close();
       rs.close();
       stmt.close();
       return mapMarcacoes;

    } catch (SQLException e) {
      System.err.println("Erro ao selecionar dados ao db");
      System.err.println(e.getMessage());
    }
      return null; 
}



}