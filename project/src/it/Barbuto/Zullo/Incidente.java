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

public class Incidente extends DataFrame {

    public Incidente() {
        initComponents();
        setModalita(APPEND_QUERY);
        setFrameTable(tableIncidente);
        setNomeTabella("Incidente");
        ComboBoxInitialize(comboIdentificativo);
        ComboBoxVeicolo(comboVeicolo1);
        ComboBoxVeicolo(comboVeicolo2);
        ComboBoxInfoLive(comboInfoLive);
    }
    
    @Override
   public void ComboBoxInitialize(JComboBox cb){
       try {
            super.ComboBoxInitialize(cb);
            Connection conn = Database.getDefaultConnection();
            dcbm = new DefaultComboBoxModel();
            comboIdentificativo.setModel(dcbm);
            query = query + "Incidente";
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
   
   private void ComboBoxVeicolo(JComboBox cb){
       try {
            Connection conn = Database.getDefaultConnection();
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
            cb.setModel(dcbm);
            query = "SELECT Identificativo, Marca, Modello FROM Veicolo";
            PreparedStatement ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){ 
                dcbm.addElement(rs.getInt(1)+ " - "+rs.getString(2)+" "+rs.getString(3));
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
   
   private void ComboBoxInfoLive(JComboBox cb){
       try {
            Connection conn = Database.getDefaultConnection();
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
            comboInfoLive.setModel(dcbm);
            query = "SELECT Identificativo, InfoAggiuntive FROM InformazioneLive";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){ 
               dcbm.addElement(rs.getInt(1)+ " - "+rs.getString(2));
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
            tCodice.setEnabled(true);
            tIdVeicolo1.setEnabled(true);
            tIdVeicolo2.setEnabled(true);
            tIdInfoLive.setEnabled(true);
            break;
         case BROWSE:
            tCodice.setEnabled(false);
            tIdVeicolo1.setEnabled(false);
            tIdVeicolo2.setEnabled(false);
            tIdInfoLive.setEnabled(false);
            break;
         case UPDATE:
            tCodice.setEnabled(true);
            tIdVeicolo1.setEnabled(true);
            tIdVeicolo2.setEnabled(true);
            tIdInfoLive.setEnabled(true);
            break;
      }
   }
    
    protected void mostraErrori(SQLException e, String query, int contesto) {
      String msg;
      if (e.getErrorCode() == 1) {
         msg = "Esiste già un'altra Area di Incidente con lo stesso identificativo";
         JOptionPane.showMessageDialog(this, msg, "Errore",
                 JOptionPane.ERROR_MESSAGE);
      } else {
         super.mostraErrori(e, query, contesto);
      }
   }
    
     protected void premutoOK() {
      if (getPadre() != null) {
         getPadre().setProprietaPadre("Incidente", getIdText().getText());
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
         tCodice.setText(rs.getString("CodiceIncidente"));
         tIdVeicolo1.setText(rs.getString("IdVeicolo1"));
         tIdVeicolo2.setText(rs.getString("IdVeicolo2"));
         tIdInfoLive.setText(rs.getString("IdInfoLive"));
         super.mostraDati();
      } catch (SQLException e) {
         mostraErrori(e);
      }
   }
     
     protected void pulisci() {
      super.pulisci();
      tCodice.setText("");
         tIdVeicolo1.setText("");
         tIdVeicolo2.setText("");
         tIdInfoLive.setText("");
     }
   
   protected PreparedStatement creaSelectStatement() {
      Connection con;
      PreparedStatement st;
      String id, codiceincidente, idveicolo1,idveicolo2,idinfolive;
      Pattern pat;
      Matcher matc;
      int k = 1;
      super.creaSelectStatement();
      id = getIdText().getText();
      codiceincidente = tCodice.getText();
      idveicolo1  = tIdVeicolo1.getText();
      idveicolo2 = tIdVeicolo2.getText();
      idinfolive = tIdInfoLive.getText();
      query += " where";
      //}
      if (id.length() > 0) {
         query += " Identificativo= ? and";
      }
      if (codiceincidente.length() > 0) {
         if (codiceincidente.contains("%")) {
            query += " CodiceIncidente like ? and";
         } else {
            query += " CodiceIncidente = ? and";
         }
      }
      if (idveicolo1.length() > 0) {
         if (idveicolo1.contains("%")) {
            query += " IdVeicolo1 like ? and";
         } else {
            query += " IdVeicolo1 = ? and";
         }
      }
      if (idveicolo2.length() > 0) {
         if (idveicolo2.contains("%")) {
            query += " IdVeicolo2 like ? and";
         } else {
            query += " IdVeicolo2 = ? and";
         }
      }
      if (idinfolive.length() > 0) {
         if (idinfolive.contains("%")) {
            query += " IdInfoLive like ? and";
         } else {
            query += " IdInfoLive = ? and";
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
         if (codiceincidente.length() > 0) {
            st.setString(k++, codiceincidente);
         }
         if (idveicolo1.length() > 0) {
            st.setString(k++, idveicolo1);
         }
         if (idveicolo2.length() > 0) {
            st.setString(k++, idveicolo2);
         }
         if (idinfolive.length() > 0) {
            st.setString(k++, idinfolive);
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
      cmdIns = "insert into " + Database.schema + ".Incidente (Identificativo,CodiceIncidente,"
              + "IdVeicolo1,IdVeicolo2,IdInfoLive) values(?,?,?,?,?)";
      st = c.prepareStatement(cmdIns);
      st.setInt(1, Integer.decode(getIdText().getText()));
      st.setString(2, tCodice.getText());
      st.setString(3, tIdVeicolo1.getText());
      st.setString(4, tIdVeicolo2.getText());
      st.setString(5, tIdInfoLive.getText());
      return st;
   }
   
   protected PreparedStatement getComandoAggiornamento(Connection c)
           throws SQLException {
      String cmdUp;
      PreparedStatement st;
      cmdUp = "update " + Database.schema + ".Incidente set CodiceIncidente=?,IdVeicolo1=?,IdVeicolo2=?,IdInfoLive=? "
              + "where Identificativo=?";
      st = c.prepareStatement(cmdUp);
      st.setInt(5, Integer.decode(getIdText().getText()));
      st.setString(1, tCodice.getText());
      st.setString(2, tIdVeicolo1.getText());
      st.setString(3, tIdVeicolo2.getText());
      st.setString(4, tIdInfoLive.getText());
      return st;
   }

@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableIncidente = new javax.swing.JTable();
        tCodice = new javax.swing.JTextField();
        tIdVeicolo1 = new javax.swing.JTextField();
        tIdVeicolo2 = new javax.swing.JTextField();
        tIdInfoLive = new javax.swing.JTextField();
        labelCodice = new javax.swing.JLabel();
        labelIdVeicolo1 = new javax.swing.JLabel();
        labelIdInfoLive = new javax.swing.JLabel();
        labelIdVeicolo2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        comboVeicolo1 = new javax.swing.JComboBox<>();
        comboVeicolo2 = new javax.swing.JComboBox<>();
        comboInfoLive = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rete Autostradale - Incidente");

        tableIncidente.setModel(new javax.swing.table.DefaultTableModel(
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
                "Id Incidente", "Codice ", "Id Veicolo1", "Id Veicolo2", "Id Info Live"
            }
        ));
        jScrollPane1.setViewportView(tableIncidente);

        labelCodice.setText("Codice");

        labelIdVeicolo1.setText("Id Veicolo 1");

        labelIdInfoLive.setText("Id Info Live");

        labelIdVeicolo2.setText("Id Veicolo 2");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Codice è una stringa.\nId Veicolo 1, Id Veicolo 2 e Id Info Live sono chiavi esterne intere.\n");
        jScrollPane2.setViewportView(jTextArea1);

        comboVeicolo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboVeicolo1ActionPerformed(evt);
            }
        });

        comboVeicolo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboVeicolo2ActionPerformed(evt);
            }
        });

        comboInfoLive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboInfoLiveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 882, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(labelIdVeicolo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelIdVeicolo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelCodice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(labelIdInfoLive, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tCodice)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(tIdInfoLive, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                    .addComponent(tIdVeicolo2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tIdVeicolo1, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboVeicolo1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(comboVeicolo2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(comboInfoLive, 0, 293, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(358, 358, 358))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCodice)
                    .addComponent(tCodice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tIdVeicolo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelIdVeicolo1)
                    .addComponent(comboVeicolo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIdVeicolo2)
                    .addComponent(tIdVeicolo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboVeicolo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIdInfoLive)
                    .addComponent(tIdInfoLive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboInfoLive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboVeicolo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboVeicolo1ActionPerformed
       String numberOnly = comboVeicolo1.getSelectedItem().toString().substring(0, 5).replaceAll("[^0-9]", "");
       tIdVeicolo1.setText(numberOnly);
    }//GEN-LAST:event_comboVeicolo1ActionPerformed

    private void comboVeicolo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboVeicolo2ActionPerformed
        String numberOnly = comboVeicolo2.getSelectedItem().toString().substring(0, 5).replaceAll("[^0-9]", "");
        tIdVeicolo2.setText(numberOnly);
    }//GEN-LAST:event_comboVeicolo2ActionPerformed

    private void comboInfoLiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboInfoLiveActionPerformed
       String numberOnly = comboInfoLive.getSelectedItem().toString().substring(0, 5).replaceAll("[^0-9]", "");
       tIdInfoLive.setText(numberOnly);
    }//GEN-LAST:event_comboInfoLiveActionPerformed

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
            java.util.logging.Logger.getLogger(Incidente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Incidente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Incidente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Incidente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Incidente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboInfoLive;
    private javax.swing.JComboBox<String> comboVeicolo1;
    private javax.swing.JComboBox<String> comboVeicolo2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labelCodice;
    private javax.swing.JLabel labelIdInfoLive;
    private javax.swing.JLabel labelIdVeicolo1;
    private javax.swing.JLabel labelIdVeicolo2;
    private javax.swing.JTextField tCodice;
    private javax.swing.JTextField tIdInfoLive;
    private javax.swing.JTextField tIdVeicolo1;
    private javax.swing.JTextField tIdVeicolo2;
    private javax.swing.JTable tableIncidente;
    // End of variables declaration//GEN-END:variables
}
