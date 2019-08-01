package it.Barbuto.Zullo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import javax.swing.JOptionPane;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author Barbuto Salvatore e Zullo Stefano
 */

public class Database {
   static public String host = "localhost";
   static public String servizio = "xe";
   static public int porta=1521;
   static public String user = "";
   static public String password = "";
   static public String schema = "Uguale all'Utente";
   static private OracleDataSource ods;
   static private Connection defaultConnection;


   public Database() {
   }

   /**
    * Restituisce la connessione di default al DB.
    *
    * @return Connessione di default (quella gi&agrave; attiva, o una nuova
    * ottenuta in base ai parametri di connessione attualmente impostati
    * @throws SQLException In caso di problemi di connessione
    */
   static public Connection getDefaultConnection() throws SQLException {
      if (defaultConnection == null || defaultConnection.isClosed()) {
         defaultConnection = nuovaConnessione();
      }

      return defaultConnection;
   }
  static public void setDefaultConnection(Connection c) {
      defaultConnection = c;
   }

   /**
    * Restituisce una nuova connessione al DB.
    *
    * @return Connessione al DB secondo i parametri attualmente impostati
    * @throws java.sql.SQLException in caso di problemi di connessione
    */
   static public Connection nuovaConnessione() throws SQLException {
      ods = new OracleDataSource();
      ods.setDriverType("thin");
      ods.setServerName(host);
      ods.setPortNumber(porta);
      ods.setUser(user);
      ods.setPassword(password);
      ods.setDatabaseName(servizio);
      return ods.getConnection();
   }
   static public void mostraErroriSwing(java.awt.Component thrower,
           SQLException e) {
      String msg;
      msg = e.getMessage() + "\n";
      msg += "SQLState= " + e.getSQLState() + "\n";

      JOptionPane.showMessageDialog(thrower, msg, "Errore " + e.getErrorCode(),
              JOptionPane.ERROR_MESSAGE);
   }


static public Object leggiValore(String query) {
      Object ret;
      Connection con;
      Statement st;
      ResultSet rs;
      ret = null;
      try {
         con = getDefaultConnection();
         st = con.createStatement();
         rs = st.executeQuery(query);
         rs.next();
         ret = rs.getObject(1);
      } catch (SQLException e) {  //nessuna azione
      }
      return ret;
   }

static public Object leggiValore(String query, int codice) {
      Object ret;
      Connection con;
      PreparedStatement st;
      ResultSet rs;
      ret = null;
      try {
         con = getDefaultConnection();
         st = con.prepareStatement(query);
         st.setInt(1, codice);
         rs = st.executeQuery();
         rs.next();
         ret = rs.getObject(1);
      } catch (SQLException e) {
      }
      return ret;
   }
}
