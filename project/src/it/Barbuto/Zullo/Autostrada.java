/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.Barbuto.Zullo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;


/**
 *
 * @author Barbuto Salvatore e Zullo Stefano
 */


public class Autostrada extends DataFrame {

    /**
     * Creates new form Autostrada
     */
    public Autostrada() {
        initComponents();
         setModalita(APPEND_QUERY);
         setFrameTable(tableAutostrada);
         setNomeTabella("Autostrada");
         ComboBoxInitialize(comboIdentificativo);
    }
    
    @Override
   public void ComboBoxInitialize(JComboBox cb){
       try {
            super.ComboBoxInitialize(cb);
            Connection conn = Database.getDefaultConnection();
            dcbm = new DefaultComboBoxModel();
            comboIdentificativo.setModel(dcbm);
            query = query + "Autostrada";
            PreparedStatement ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
               dcbm.addElement(rs.getInt(1));
            }
            rs.close();
            ps.close();
            conn.close();
        }
        catch (SQLException ex) {
            mostraErrori(ex,query,1);
        }
        catch (NullPointerException npe){
        }
   }

    final public void setModalita(int modo) {
      super.setModalita(modo);
      switch (modo) {
         case APPEND_QUERY:
            tNome.setEnabled(true);
            tAltraDenominazione.setEnabled(true);
            tInizio.setEnabled(true);
            tFine.setEnabled(true);
            tLunghezzaTratta.setEnabled(true);
            break;
         case BROWSE:
            tNome.setEnabled(false);
            tAltraDenominazione.setEnabled(false);
            tInizio.setEnabled(false);
            tFine.setEnabled(false);
            tLunghezzaTratta.setEnabled(false);
            break;
         case UPDATE:
            tNome.setEnabled(true);
            tAltraDenominazione.setEnabled(true);
            tInizio.setEnabled(true);
            tFine.setEnabled(true);
            tLunghezzaTratta.setEnabled(true);
            break;
      }
   }
    
    protected void mostraErrori(SQLException e, String query, int contesto) {
      String msg;
      if (e.getErrorCode() == 1) {
         msg = "Esiste già un'altra autostrada con lo stesso identificativo";
         JOptionPane.showMessageDialog(this, msg, "Errore",
                 JOptionPane.ERROR_MESSAGE);
      } else {
         super.mostraErrori(e, query, contesto);
      }
   }
    
     protected void premutoOK() {
      if (getPadre() != null) {
         getPadre().setProprietaPadre("Autostrada", getIdText().getText());
         try {
            rs.close();
         } catch (SQLException e) {
            mostraErrori(e);
         }
         dispose();
      }
   }
     protected void mostraDati() {
      try {
         rs.previous(); rs.next();
         tNome.setText(rs.getString("Nome"));
         tAltraDenominazione.setText(rs.getString("AltraDenominazione"));
         tInizio.setText(rs.getString("Inizio"));
         tFine.setText(rs.getString("Fine"));
         tLunghezzaTratta.setText(rs.getString("LunghezzaTratta"));
         super.mostraDati();
      } catch (SQLException e) {
         mostraErrori(e);
      }
   }
     
     protected void pulisci() {
      super.pulisci();
      tNome.setText("");
      tAltraDenominazione.setText("");
      tInizio.setText("");
      tFine.setText("");
      tLunghezzaTratta.setText("");
   }
   
   protected PreparedStatement creaSelectStatement() {
      Connection con;
      PreparedStatement st;
      String id, nome, altradenom, inizio, fine, ltratta;
      Pattern pat;
      Matcher matc;
      int k = 1;
      super.creaSelectStatement();
      id = getIdText().getText();
      nome = tNome.getText();
      altradenom = tAltraDenominazione.getText();
      inizio = tInizio.getText();
      fine = tFine.getText();
      ltratta = tLunghezzaTratta.getText();

      query += " where";
      //}
      if (id.length() > 0) {
         query += " Identificativo= ? and";
      }
      if (nome.length() > 0) {
         if (nome.contains("%")) {
            query += " Nome like ? and";
         } else {
            query += " Nome = ? and";
         }
      }
      if (altradenom.length() > 0) {
         if (altradenom.contains("%")) {
            query += " AltraDenominazione like ? and";
         } else {
            query += " AltraDenominazione = ? and";
         }
      }
      if (inizio.length() > 0) {
         if (inizio.contains("%")) {
            query += " Inizio like ? and";
         } else {
            query += " Inizio = ? and";
         }
      }
      if (fine.length() > 0) {
         if (fine.contains("%")) {
            query += " Fine like ? and";
         } else {
            query += " Fine = ? and";
         }
      }
      if (ltratta.length() > 0) {
         if (ltratta.contains("%")) {
            query += " LunghezzaTratta like ?";
         } else {
            query += " LunghezzaTratta = ?";
         }
      }
      pat = Pattern.compile("where$|and$"); //cancella where o and finali
      matc = pat.matcher(query);
      query = matc.replaceAll("");
      //query+=" order by codice";
      try {
         con = Database.getDefaultConnection();
         st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                 ResultSet.CONCUR_READ_ONLY);

         if (id.length() > 0) {
            st.setInt(k++, Integer.decode(id));
         }
         if (nome.length() > 0) {
            st.setString(k++, nome);
         }
         if (altradenom.length() > 0) {
            st.setString(k++, altradenom);
         }
         if (inizio.length() > 0) {
            st.setString(k++, inizio);
         }
         if (fine.length() > 0) {
            st.setString(k++, fine);
         }
         if (ltratta.length() > 0) {
            st.setInt(k++, Integer.decode(ltratta));
         }
         return st;
      } catch (SQLException e) {
         mostraErrori(e);
         return null;
      }
   }
   protected PreparedStatement getComandoInserimento(Connection c)
           throws SQLException {
      String cmdIns;
      PreparedStatement st;
      cmdIns = "insert into " + Database.schema + ".Autostrada (Identificativo,Nome,"
              + "AltraDenominazione,Inizio,Fine,LunghezzaTratta) values(?,?,?,?,?,?)";
      st = c.prepareStatement(cmdIns);
      st.setInt(1, Integer.decode(getIdText().getText()));
      st.setString(2, tNome.getText());
      st.setString(3, tAltraDenominazione.getText());
      st.setString(4, tInizio.getText());
      st.setString(5, tFine.getText());
      st.setInt(6, Integer.decode(tLunghezzaTratta.getText()));
      return st;
   }
   
   protected PreparedStatement getComandoAggiornamento(Connection c)
           throws SQLException {
      String cmdUp;
      PreparedStatement st;
      cmdUp = "update " + Database.schema + ".Autostrada set Nome=?,AltraDenominazione=?,Inizio=?,Fine=?,LunghezzaTratta=? "
              + "where Identificativo=?";
      st = c.prepareStatement(cmdUp);
      st.setInt(6, Integer.decode(getIdText().getText()));
      st.setString(1, tNome.getText());
      st.setString(2, tAltraDenominazione.getText());
      st.setString(3, tInizio.getText());
      st.setString(4, tFine.getText());
      st.setString(5, tLunghezzaTratta.getText());
      return st;
   }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelAltraDenominazione = new javax.swing.JLabel();
        tAltraDenominazione = new javax.swing.JTextField();
        labelNome = new javax.swing.JLabel();
        tNome = new javax.swing.JTextField();
        labelInizio = new javax.swing.JLabel();
        tInizio = new javax.swing.JTextField();
        labelFine = new javax.swing.JLabel();
        tFine = new javax.swing.JTextField();
        labelLunghezzaTratta = new javax.swing.JLabel();
        tLunghezzaTratta = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableAutostrada = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rete Autostradale - Autostrada");

        labelAltraDenominazione.setText("Altra Denominazione");

        tAltraDenominazione.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tAltraDenominazioneActionPerformed(evt);
            }
        });

        labelNome.setText("Nome");

        tNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tNomeActionPerformed(evt);
            }
        });

        labelInizio.setText("Inizio Tratta");

        tInizio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tInizioActionPerformed(evt);
            }
        });

        labelFine.setText("Fine Tratta");

        labelLunghezzaTratta.setText("Lunghezza Tratta");

        tableAutostrada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id Autostrada", "Nome", "Altra Denominazione", "Inizio", "Fine", "LunghezzaTratta"
            }
        ));
        jScrollPane2.setViewportView(tableAutostrada);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Nome,AltraDenominazione,Inizio e Fine Tratta sono stringhe.\nLunghezza Tratta (KM) è un numero reale.");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 882, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelAltraDenominazione)
                            .addComponent(labelInizio, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelNome)
                            .addComponent(labelFine)
                            .addComponent(labelLunghezzaTratta, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tLunghezzaTratta)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(tFine, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tInizio, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tAltraDenominazione, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tNome, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE))
                                .addGap(2, 2, 2)))
                        .addGap(264, 264, 264))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAltraDenominazione)
                    .addComponent(tAltraDenominazione, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelInizio)
                    .addComponent(tInizio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelFine)
                    .addComponent(tFine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLunghezzaTratta)
                    .addComponent(tLunghezzaTratta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tAltraDenominazioneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tAltraDenominazioneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tAltraDenominazioneActionPerformed

    private void tNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tNomeActionPerformed

    private void tInizioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tInizioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tInizioActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Autostrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Autostrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Autostrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Autostrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Autostrada().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labelAltraDenominazione;
    private javax.swing.JLabel labelFine;
    private javax.swing.JLabel labelInizio;
    private javax.swing.JLabel labelLunghezzaTratta;
    private javax.swing.JLabel labelNome;
    private javax.swing.JTextField tAltraDenominazione;
    private javax.swing.JTextField tFine;
    private javax.swing.JTextField tInizio;
    private javax.swing.JTextField tLunghezzaTratta;
    private javax.swing.JTextField tNome;
    private javax.swing.JTable tableAutostrada;
    // End of variables declaration//GEN-END:variables
}
