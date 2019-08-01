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

public class Svincolo extends DataFrame {

    public Svincolo() {
        initComponents();
        setModalita(APPEND_QUERY);
        setFrameTable(tableSvincolo);
        setNomeTabella("Svincolo");
        ComboBoxInitialize(comboIdentificativo);
        ComboBoxAutostradaE(comboAutostradaE);
        ComboBoxAutostradaU(comboAutostradaU);
        ComboBoxDirezioneAE(comboDirezioneAE);
        
    }
    
    @Override
   public void ComboBoxInitialize(JComboBox cb){
       try {
            super.ComboBoxInitialize(cb);
            Connection conn = Database.getDefaultConnection();
            dcbm = new DefaultComboBoxModel();
            comboIdentificativo.setModel(dcbm);
            query = query + "Svincolo";
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
    
    final public void setModalita(int modo) {
      super.setModalita(modo);
      switch (modo) {
         case APPEND_QUERY:
            tPosizioneSvincolo.setEnabled(true);
            tAutostradaUscente.setEnabled(true);
            tAutostradaEntrante.setEnabled(true);
            tIdDirezioneAutostradaEntrante.setEnabled(true);
            break;
         case BROWSE:
            tPosizioneSvincolo.setEnabled(false);
            tAutostradaUscente.setEnabled(false);
            tAutostradaEntrante.setEnabled(false);
            tIdDirezioneAutostradaEntrante.setEnabled(false);
            break;
         case UPDATE:
            tPosizioneSvincolo.setEnabled(true);
            tAutostradaUscente.setEnabled(true);
            tAutostradaEntrante.setEnabled(true);
            tIdDirezioneAutostradaEntrante.setEnabled(true);
            break;
      }
   }
    
    private void ComboBoxDirezioneAE(JComboBox cb){
       try {
            Connection conn = Database.getDefaultConnection();
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
            comboDirezioneAE.setModel(dcbm);
            query = "SELECT Identificativo, NomeDirezione FROM DirezioneTratta";
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
    
    private void ComboBoxAutostradaU(JComboBox cb){
       try {
            Connection conn = Database.getDefaultConnection();
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
            comboAutostradaU.setModel(dcbm);
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
    
   private void ComboBoxAutostradaE(JComboBox cb){
       try {
            Connection conn = Database.getDefaultConnection();
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
            comboAutostradaE.setModel(dcbm);
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
    
    protected void mostraErrori(SQLException e, String query, int contesto) {
      String msg;
      if (e.getErrorCode() == 1) {
         msg = "Esiste già un altro Svincolo con lo stesso identificativo";
         JOptionPane.showMessageDialog(this, msg, "Errore",
                 JOptionPane.ERROR_MESSAGE);
      } else {
         super.mostraErrori(e, query, contesto);
      }
   }
    
     protected void premutoOK() {
      if (getPadre() != null) {
         getPadre().setProprietaPadre("Svincolo", getIdText().getText());
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
         tPosizioneSvincolo.setText(rs.getString("PosizioneSvincolo"));
         tAutostradaUscente.setText(rs.getString("AutostradaUscente"));
         tAutostradaEntrante.setText(rs.getString("AutostradaEntrante"));
         tIdDirezioneAutostradaEntrante.setText(rs.getString("IdDirezioneAutostradaEntrante"));
         super.mostraDati();
      } catch (SQLException e) {
         mostraErrori(e);
      }
   }
     
     protected void pulisci() {
      super.pulisci();
      tPosizioneSvincolo.setText("");
      tAutostradaUscente.setText("");
      tAutostradaEntrante.setText("");
      tIdDirezioneAutostradaEntrante.setText("");
     }
   
   protected PreparedStatement creaSelectStatement() {
      Connection con;
      PreparedStatement st;
      String id, posizionesvincolo, autostradauscente,autostradaentrante,iddirautostradaentrante;
      Pattern pat;
      Matcher matc;
      int k = 1;
      super.creaSelectStatement();
      id = getIdText().getText();
      posizionesvincolo = tPosizioneSvincolo.getText();
      autostradauscente  = tAutostradaUscente.getText();
      autostradaentrante = tAutostradaEntrante.getText();
      iddirautostradaentrante = tIdDirezioneAutostradaEntrante.getText();
      query += " where";
      //}
      if (id.length() > 0) {
         query += " Identificativo= ? and";
      }
      if (posizionesvincolo.length() > 0) {
         if (posizionesvincolo.contains("%")) {
            query += " PosizioneSvincolo like ? and";
         } else {
            query += " PosizioneSvincolo = ? and";
         }
      }
      if (autostradauscente.length() > 0) {
         if (autostradauscente.contains("%")) {
            query += " AutostradaUscente like ? and";
         } else {
            query += " AutostradaUscente = ? and";
         }
      }
      if (autostradaentrante.length() > 0) {
         if (autostradaentrante.contains("%")) {
            query += " AutostradaEntrante like ? and";
         } else {
            query += " AutostradaEntrante = ? and";
         }
      }
      if (iddirautostradaentrante.length() > 0) {
         if (iddirautostradaentrante.contains("%")) {
            query += " IdDirezioneAutostradaEntrante like ? and";
         } else {
            query += " IdDirezioneAutostradaEntrante = ? and";
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
         if (posizionesvincolo.length() > 0) {
            st.setString(k++, posizionesvincolo);
         }
         if (autostradauscente.length() > 0) {
            st.setString(k++, autostradauscente);
         }
         if (autostradaentrante.length() > 0) {
            st.setString(k++, autostradaentrante);
         }
         if (iddirautostradaentrante.length() > 0) {
            st.setString(k++, iddirautostradaentrante);
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
      cmdIns = "insert into " + Database.schema + ".Svincolo (Identificativo,PosizioneSvincolo,"
              + "AutostradaUscente,AutostradaEntrante,IdDirezioneAutostradaEntrante) values(?,?,?,?,?)";
      st = c.prepareStatement(cmdIns);
      st.setInt(1, Integer.decode(getIdText().getText()));
      st.setString(2, tPosizioneSvincolo.getText());
      st.setString(3, tAutostradaUscente.getText());
      st.setString(4, tAutostradaEntrante.getText());
      st.setString(5, tIdDirezioneAutostradaEntrante.getText());
      return st;
   }
   
   protected PreparedStatement getComandoAggiornamento(Connection c)
           throws SQLException {
      String cmdUp;
      PreparedStatement st;
      cmdUp = "update " + Database.schema + ".Svincolo set PosizioneSvincolo=?,AutostradaUscente=?,AutostradaEntrante=?,IdDirezioneAutostradaEntrante=? "
              + "where Identificativo=?";
      st = c.prepareStatement(cmdUp);
      st.setInt(5, Integer.decode(getIdText().getText()));
      st.setString(1, tPosizioneSvincolo.getText());
      st.setString(2, tAutostradaUscente.getText());
      st.setString(3, tAutostradaEntrante.getText());
      st.setString(4, tIdDirezioneAutostradaEntrante.getText());
      return st;
   }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        labelPosizioneSvincolo = new javax.swing.JLabel();
        labelAutostradaUscente = new javax.swing.JLabel();
        labelAutostradaEntrante = new javax.swing.JLabel();
        labelIdDirezioneAutostradaEntrante = new javax.swing.JLabel();
        tPosizioneSvincolo = new javax.swing.JTextField();
        tAutostradaUscente = new javax.swing.JTextField();
        tAutostradaEntrante = new javax.swing.JTextField();
        tIdDirezioneAutostradaEntrante = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableSvincolo = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        comboAutostradaU = new javax.swing.JComboBox<>();
        comboAutostradaE = new javax.swing.JComboBox<>();
        comboDirezioneAE = new javax.swing.JComboBox<>();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rete Autostradale - Svincolo");

        labelPosizioneSvincolo.setText("Posizione Svincolo");

        labelAutostradaUscente.setText("Autostrada Uscente");

        labelAutostradaEntrante.setText("Autostrada Entrante");

        labelIdDirezioneAutostradaEntrante.setText("Direzione A. Entrante");

        tableSvincolo.setModel(new javax.swing.table.DefaultTableModel(
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
                "Id Svincolo", "Posizione", "A Uscente", "A Entrante", "Direzione"
            }
        ));
        jScrollPane2.setViewportView(tableSvincolo);

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setText("Posizione Svincolo (KM) è un numero reale.\nAutostrada Uscente, Autostrada Entrante e Direzione A. Entrante\nsono chiavi esterne intere.");
        jScrollPane4.setViewportView(jTextArea2);

        comboAutostradaU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAutostradaUActionPerformed(evt);
            }
        });

        comboAutostradaE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAutostradaEActionPerformed(evt);
            }
        });

        comboDirezioneAE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDirezioneAEActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 882, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(labelAutostradaEntrante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelAutostradaUscente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelIdDirezioneAutostradaEntrante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(tAutostradaEntrante, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tAutostradaUscente, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tIdDirezioneAutostradaEntrante, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboAutostradaE, 0, 264, Short.MAX_VALUE)
                                    .addComponent(comboAutostradaU, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(comboDirezioneAE, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelPosizioneSvincolo, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tPosizioneSvincolo, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPosizioneSvincolo)
                    .addComponent(tPosizioneSvincolo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAutostradaUscente)
                    .addComponent(tAutostradaUscente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboAutostradaU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAutostradaEntrante)
                    .addComponent(tAutostradaEntrante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboAutostradaE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIdDirezioneAutostradaEntrante)
                    .addComponent(tIdDirezioneAutostradaEntrante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboDirezioneAE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboAutostradaEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAutostradaEActionPerformed
      String numberOnly = comboAutostradaE.getSelectedItem().toString().substring(0, 5).replaceAll("[^0-9]", "");
        tAutostradaEntrante.setText(numberOnly);
    }//GEN-LAST:event_comboAutostradaEActionPerformed

    private void comboAutostradaUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAutostradaUActionPerformed
        String numberOnly = comboAutostradaU.getSelectedItem().toString().substring(0, 5).replaceAll("[^0-9]", "");
        tAutostradaUscente.setText(numberOnly);
    }//GEN-LAST:event_comboAutostradaUActionPerformed

    private void comboDirezioneAEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDirezioneAEActionPerformed
        String numberOnly = comboDirezioneAE.getSelectedItem().toString().substring(0, 5).replaceAll("[^0-9]", "");
        tIdDirezioneAutostradaEntrante.setText(numberOnly);
    }//GEN-LAST:event_comboDirezioneAEActionPerformed

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
            java.util.logging.Logger.getLogger(Svincolo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Svincolo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Svincolo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Svincolo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Svincolo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboAutostradaE;
    private javax.swing.JComboBox<String> comboAutostradaU;
    private javax.swing.JComboBox<String> comboDirezioneAE;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JLabel labelAutostradaEntrante;
    private javax.swing.JLabel labelAutostradaUscente;
    private javax.swing.JLabel labelIdDirezioneAutostradaEntrante;
    private javax.swing.JLabel labelPosizioneSvincolo;
    private javax.swing.JTextField tAutostradaEntrante;
    private javax.swing.JTextField tAutostradaUscente;
    private javax.swing.JTextField tIdDirezioneAutostradaEntrante;
    private javax.swing.JTextField tPosizioneSvincolo;
    private javax.swing.JTable tableSvincolo;
    // End of variables declaration//GEN-END:variables
}
