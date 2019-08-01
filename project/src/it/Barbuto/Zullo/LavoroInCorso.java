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

public class LavoroInCorso extends DataFrame {

    public LavoroInCorso() {
        initComponents();
        setModalita(APPEND_QUERY);
        setFrameTable(tableLavoroInCorso);
        setNomeTabella("LavoroInCorso");
        ComboBoxInitialize(comboIdentificativo);
        ComboBoxInfoLive(comboInfoLive);
    }
    
    @Override
   public void ComboBoxInitialize(JComboBox cb){
       try {
            super.ComboBoxInitialize(cb);
            Connection conn = Database.getDefaultConnection();
            dcbm = new DefaultComboBoxModel();
            comboIdentificativo.setModel(dcbm);
            query = query + "LavoroInCorso";
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
            tIdInfoLive.setEnabled(true);
            break;
         case BROWSE:
            tIdInfoLive.setEnabled(false);
            break;
         case UPDATE:
            tIdInfoLive.setEnabled(true);
            break;
      }
   }
    
    protected void mostraErrori(SQLException e, String query, int contesto) {
      String msg;
      if (e.getErrorCode() == 1) {
         msg = "Esiste già un'altro Lavoro In Corso con lo stesso identificativo";
         JOptionPane.showMessageDialog(this, msg, "Errore",
                 JOptionPane.ERROR_MESSAGE);
      } else {
         super.mostraErrori(e, query, contesto);
      }
   }
    
     protected void premutoOK() {
      if (getPadre() != null) {
         getPadre().setProprietaPadre("LavoroInCorso", getIdText().getText());
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
         tIdInfoLive.setText(rs.getString("IdInfoLive"));
         super.mostraDati();
      } catch (SQLException e) {
         mostraErrori(e);
      }
   }
     
     protected void pulisci() {
      super.pulisci();
      tIdInfoLive.setText("");
     }
   
   protected PreparedStatement creaSelectStatement() {
      Connection con;
      PreparedStatement st;
      String id, idinfolive;
      Pattern pat;
      Matcher matc;
      int k = 1;
      super.creaSelectStatement();
      id = getIdText().getText();
      idinfolive = tIdInfoLive.getText();
      query += " where";
      //}
      if (id.length() > 0) {
         query += " Identificativo= ? and";
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
      cmdIns = "insert into " + Database.schema + ".LavoroInCorso (Identificativo,IdInfoLive) values(?,?)";
      st = c.prepareStatement(cmdIns);
      st.setInt(1, Integer.decode(getIdText().getText()));
      st.setString(2, tIdInfoLive.getText());
      return st;
   }
   
   protected PreparedStatement getComandoAggiornamento(Connection c)
           throws SQLException {
      String cmdUp;
      PreparedStatement st;
      cmdUp = "update " + Database.schema + ".LavoroInCorso set IdInfoLive=?"
              + "where Identificativo=?";
      st = c.prepareStatement(cmdUp);
      st.setInt(2, Integer.decode(getIdText().getText()));
      st.setString(1, tIdInfoLive.getText());
      return st;
   }


@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableLavoroInCorso = new javax.swing.JTable();
        tIdInfoLive = new javax.swing.JTextField();
        labelIdInfoLive = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        comboInfoLive = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rete Autostradale - Lavoro in Corso");

        tableLavoroInCorso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Id Lavoro in Corso", "Id Info  Live"
            }
        ));
        jScrollPane1.setViewportView(tableLavoroInCorso);

        labelIdInfoLive.setText("Id Info Live");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Id Info Live è una chiave esterna intera.");
        jScrollPane2.setViewportView(jTextArea1);

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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelIdInfoLive)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tIdInfoLive, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(comboInfoLive, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIdInfoLive)
                    .addComponent(tIdInfoLive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboInfoLive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(83, 83, 83)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 257, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(LavoroInCorso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LavoroInCorso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LavoroInCorso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LavoroInCorso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LavoroInCorso().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboInfoLive;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labelIdInfoLive;
    private javax.swing.JTextField tIdInfoLive;
    private javax.swing.JTable tableLavoroInCorso;
    // End of variables declaration//GEN-END:variables
}
