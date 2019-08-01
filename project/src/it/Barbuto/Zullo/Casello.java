/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.Barbuto.Zullo;

import static it.Barbuto.Zullo.DataFrame.APPEND_QUERY;
import static it.Barbuto.Zullo.DataFrame.BROWSE;
import static it.Barbuto.Zullo.DataFrame.UPDATE;
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

public class Casello extends DataFrame {

    public Casello() {
        initComponents();
        setModalita(APPEND_QUERY);
        setFrameTable(tableCasello);
        setNomeTabella("Casello");
        ComboBoxInitialize(comboIdentificativo);
        ComboBoxAutostrada(comboAutostrada);
    }
    
    @Override
   public void ComboBoxInitialize(JComboBox cb){
       try {
            super.ComboBoxInitialize(cb);
            Connection conn = Database.getDefaultConnection();
            dcbm = new DefaultComboBoxModel();
            comboIdentificativo.setModel(dcbm);
            query = query + "Casello";
            System.out.println(query);
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
   
   private void ComboBoxAutostrada(JComboBox cb){
       try {
            Connection conn = Database.getDefaultConnection();
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
            comboAutostrada.setModel(dcbm);
            query = "SELECT Identificativo,Nome,AltraDenominazione FROM Autostrada";
            System.out.println(query);
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){ 
               dcbm.addElement(rs.getInt(1)+ " - "+ rs.getString(2)+ " "+rs.getString(3));
               System.out.println(rs.getInt(1)+ " - "+rs.getString(2)+" "+rs.getString(3));
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
            tPosizione.setEnabled(true);
            tPedaggio.setEnabled(true);
            tIdAutostrada.setEnabled(true);
            comboAutostrada.setEnabled(true);
            break;
         case BROWSE:
            tNome.setEnabled(false);
            tPosizione.setEnabled(false);
            tPedaggio.setEnabled(false);
            tIdAutostrada.setEnabled(false);
            comboAutostrada.setEnabled(false);
            break;
         case UPDATE:
            tNome.setEnabled(true);
            tPosizione.setEnabled(true);
            tPedaggio.setEnabled(true);
            tIdAutostrada.setEnabled(true);
            comboAutostrada.setEnabled(false);
            break;
      }
   }
    
    protected void mostraErrori(SQLException e, String query, int contesto) {
      String msg;
      if (e.getErrorCode() == 1) {
         msg = "Esiste già un'altro Casello con lo stesso identificativo";
         JOptionPane.showMessageDialog(this, msg, "Errore",
                 JOptionPane.ERROR_MESSAGE);
      } else {
         super.mostraErrori(e, query, contesto);
      }
   }
    
     protected void premutoOK() {
      if (getPadre() != null) {
         getPadre().setProprietaPadre("Casello", getIdText().getText());
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
         tPosizione.setText(rs.getString("PosizioneCasello"));
         tPedaggio.setText(rs.getString("Pedaggio"));
         tIdAutostrada.setText(rs.getString("IdAutostrada"));
         super.mostraDati();
      } catch (SQLException e) {
         mostraErrori(e);
      }
   }
     
     protected void pulisci() {
      super.pulisci();
      tNome.setText("");
      tPosizione.setText("");
      tPedaggio.setText("");
      tIdAutostrada.setText("");
     }
   
   protected PreparedStatement creaSelectStatement() {
      Connection con;
      PreparedStatement st;
      String id, nome, posizione,pedaggio,idautostrada;
      Pattern pat;
      Matcher matc;
      int k = 1;
      super.creaSelectStatement();
      id = getIdText().getText();
      nome = tNome.getText();
      posizione  = tPosizione.getText();
      pedaggio = tPedaggio.getText();
      idautostrada = tIdAutostrada.getText();
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
      if (posizione.length() > 0) {
         if (posizione.contains("%")) {
            query += " PosizioneCasello like ? and";
         } else {
            query += " PosizioneCasello = ? and";
         }
      }
      if (pedaggio.length() > 0) {
         if (pedaggio.contains("%")) {
            query += " Pedaggio like ? and";
         } else {
            query += " Pedaggio = ? and";
         }
      }
      if (idautostrada.length() > 0) {
         if (idautostrada.contains("%")) {
            query += " IdAutostrada like ? and";
         } else {
            query += " IdAutostrada = ? and";
         }
      }
      pat = Pattern.compile("where$|and$");
      matc = pat.matcher(query);
      query = matc.replaceAll("");
      query+=" ORDER BY Identificativo";
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
         if (posizione.length() > 0) {
            st.setString(k++, posizione);
         }
         if (pedaggio.length() > 0) {
            st.setString(k++, pedaggio);
         }
         if (idautostrada.length() > 0) {
            st.setString(k++, idautostrada);
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
      cmdIns = "insert into " + Database.schema + ".Casello (Identificativo,Nome,"
              + "PosizioneCasello,Pedaggio,IdAutostrada) values(?,?,?,?,?)";
      st = c.prepareStatement(cmdIns);
      st.setInt(1, Integer.decode(getIdText().getText()));
      st.setString(2, tNome.getText());
      st.setString(3, tPosizione.getText());
      st.setString(4, tPedaggio.getText());
      st.setString(5, tIdAutostrada.getText());
      return st;
   }
   
   protected PreparedStatement getComandoAggiornamento(Connection c)
           throws SQLException {
      String cmdUp;
      PreparedStatement st;
      cmdUp = "update " + Database.schema + ".Casello set Nome=?,PosizioneCasello=?,Pedaggio=?,IdAutostrada=?"
              + "where Identificativo=?";
      st = c.prepareStatement(cmdUp);
      st.setInt(5, Integer.decode(getIdText().getText()));
      st.setString(1, tNome.getText());
      st.setString(2, tPosizione.getText());
      st.setString(3, tPedaggio.getText());
      st.setString(4, tIdAutostrada.getText());
      return st;
   }
    
   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelPosizione = new javax.swing.JLabel();
        tPosizione = new javax.swing.JTextField();
        labelNome = new javax.swing.JLabel();
        tNome = new javax.swing.JTextField();
        labelPedaggio = new javax.swing.JLabel();
        tPedaggio = new javax.swing.JTextField();
        labelIdAutostrada = new javax.swing.JLabel();
        tIdAutostrada = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCasello = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        comboAutostrada = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rete Autostradale - Casello");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        labelPosizione.setText("Posizione");

        tPosizione.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tPosizioneActionPerformed(evt);
            }
        });

        labelNome.setText("Nome");

        labelPedaggio.setText("Pedaggio");

        tPedaggio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tPedaggioActionPerformed(evt);
            }
        });

        labelIdAutostrada.setText("Id Autostrada");

        tIdAutostrada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tIdAutostradaKeyPressed(evt);
            }
        });

        tableCasello.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id Casello", "Nome", "Posizione", "Pedaggio", "Id Autostrada"
            }
        ));
        jScrollPane2.setViewportView(tableCasello);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Nome è una stringa.\nPosizione (KM) è un numero reale.\nPedaggio è un intero.\nId Autostrada è chiave esterna intera.");
        jScrollPane3.setViewportView(jTextArea1);

        comboAutostrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAutostradaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 882, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tNome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(labelPosizione, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(labelPedaggio, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelIdAutostrada))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tIdAutostrada, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(comboAutostrada, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(tPedaggio)
                            .addComponent(tPosizione))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNome)
                    .addComponent(tNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPosizione)
                    .addComponent(tPosizione, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPedaggio)
                    .addComponent(tPedaggio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIdAutostrada)
                    .addComponent(tIdAutostrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboAutostrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tPosizioneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tPosizioneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tPosizioneActionPerformed

    private void tPedaggioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tPedaggioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tPedaggioActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
    }//GEN-LAST:event_formKeyPressed

    private void tIdAutostradaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tIdAutostradaKeyPressed
 
    }//GEN-LAST:event_tIdAutostradaKeyPressed

    private void comboAutostradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAutostradaActionPerformed
       String numberOnly = comboAutostrada.getSelectedItem().toString().substring(0, 4).replaceAll("[^0-9]", "");
        tIdAutostrada.setText(numberOnly);
    }//GEN-LAST:event_comboAutostradaActionPerformed

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
            java.util.logging.Logger.getLogger(Casello.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Casello.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Casello.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Casello.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Casello().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboAutostrada;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labelIdAutostrada;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelPedaggio;
    private javax.swing.JLabel labelPosizione;
    private javax.swing.JTextField tIdAutostrada;
    private javax.swing.JTextField tNome;
    private javax.swing.JTextField tPedaggio;
    private javax.swing.JTextField tPosizione;
    private javax.swing.JTable tableCasello;
    // End of variables declaration//GEN-END:variables
}
