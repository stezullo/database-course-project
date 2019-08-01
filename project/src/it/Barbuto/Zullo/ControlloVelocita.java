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

public class ControlloVelocita extends DataFrame {

    public ControlloVelocita() {
        initComponents();
        setModalita(APPEND_QUERY);
        setFrameTable(tableControlloVelocita);
        setNomeTabella("ControlloVelocita");
        ComboBoxInitialize(comboIdentificativo);
        ComboBoxVeicolo(comboVeicolo);
        ComboBoxControlloElettronico(comboControlloElettronico);
    }
    
    @Override
   public void ComboBoxInitialize(JComboBox cb){
       try {
            super.ComboBoxInitialize(cb);
            Connection conn = Database.getDefaultConnection();
            dcbm = new DefaultComboBoxModel();
            comboIdentificativo.setModel(dcbm);
            query = query + "ControlloVelocita";
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
            comboVeicolo.setModel(dcbm);
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
   
   private void ComboBoxControlloElettronico(JComboBox cb){
       try {
            Connection conn = Database.getDefaultConnection();
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
            comboControlloElettronico.setModel(dcbm);
            query = "SELECT Identificativo FROM ControlloElettronico";
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
            tMulta.setEnabled(true);
            tIdVeicolo.setEnabled(true);
            tIdCElettronico.setEnabled(true);
            break;
         case BROWSE:
            tMulta.setEnabled(false);
            tIdVeicolo.setEnabled(false);
            tIdCElettronico.setEnabled(false);
            break;
         case UPDATE:
            tMulta.setEnabled(true);
            tIdVeicolo.setEnabled(true);
            tIdCElettronico.setEnabled(true);
            break;
      }
   }
    
    protected void mostraErrori(SQLException e, String query, int contesto) {
      String msg;
      if (e.getErrorCode() == 1) {
         msg = "Esiste già un altro Controllo Velocità con lo stesso identificativo";
         JOptionPane.showMessageDialog(this, msg, "Errore",
                 JOptionPane.ERROR_MESSAGE);
      } else {
         super.mostraErrori(e, query, contesto);
      }
   }
    
     protected void premutoOK() {
      if (getPadre() != null) {
         getPadre().setProprietaPadre("ControlloVelocita", getIdText().getText());
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
         tMulta.setText(rs.getString("Multa"));
         tIdVeicolo.setText(rs.getString("IdVeicolo"));
         tIdCElettronico.setText(rs.getString("IdCElettronico"));
         super.mostraDati();
      } catch (SQLException e) {
         mostraErrori(e);
      }
   }
     
     protected void pulisci() {
      super.pulisci();
      tMulta.setText("");
      tIdVeicolo.setText("");
      tIdCElettronico.setText("");
     }
   
   protected PreparedStatement creaSelectStatement() {
      Connection con;
      PreparedStatement st;
      String id, multa, idveicolo,idcelettronico;
      Pattern pat;
      Matcher matc;
      int k = 1;
      super.creaSelectStatement();
      id = getIdText().getText();
      multa = tMulta.getText();
      idveicolo  = tIdVeicolo.getText();
      idcelettronico = tIdCElettronico.getText();
      query += " where";
      //}
      if (id.length() > 0) {
         query += " Identificativo= ? and";
      }
      if (multa.length() > 0) {
         if (multa.contains("%")) {
            query += " Multa like ? and";
         } else {
            query += " Multa = ? and";
         }
      }
      if (idveicolo.length() > 0) {
         if (idveicolo.contains("%")) {
            query += " IdVeicolo like ? and";
         } else {
            query += " IdVeicolo = ? and";
         }
      }
      if (idcelettronico.length() > 0) {
         if (idcelettronico.contains("%")) {
            query += " Tipologia like ? and";
         } else {
            query += " Tipologia = ? and";
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
         if (multa.length() > 0) {
            st.setString(k++, multa);
         }
         if (idveicolo.length() > 0) {
            st.setString(k++, idveicolo);
         }
         if (idcelettronico.length() > 0) {
            st.setString(k++, idcelettronico);
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
      cmdIns = "insert into " + Database.schema + ".ControlloVelocita (Identificativo,Multa,"
              + "IdVeicolo,IdCElettronico) values(?,?,?,?)";
      st = c.prepareStatement(cmdIns);
      st.setInt(1, Integer.decode(getIdText().getText()));
      st.setString(2, tMulta.getText());
      st.setString(3, tIdVeicolo.getText());
      st.setString(4, tIdCElettronico.getText());
      return st;
   }
   
   protected PreparedStatement getComandoAggiornamento(Connection c)
           throws SQLException {
      String cmdUp;
      PreparedStatement st;
      cmdUp = "update " + Database.schema + ".ControlloVelocita set Multa=?,IdVeicolo=?,IdCElettronico=? "
              + "where Identificativo=?";
      st = c.prepareStatement(cmdUp);
      st.setInt(4, Integer.decode(getIdText().getText()));
      st.setString(1, tMulta.getText());
      st.setString(2, tIdVeicolo.getText());
      st.setString(3, tIdCElettronico.getText());
      return st;
   }

@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelMulta = new javax.swing.JLabel();
        labelIdVeicolo = new javax.swing.JLabel();
        labelIdCElettronico = new javax.swing.JLabel();
        tMulta = new javax.swing.JTextField();
        tIdVeicolo = new javax.swing.JTextField();
        tIdCElettronico = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableControlloVelocita = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        comboVeicolo = new javax.swing.JComboBox<>();
        comboControlloElettronico = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rete Autostradale - Controllo Velocità");

        labelMulta.setText("Multa");

        labelIdVeicolo.setText("Id Veicolo");

        labelIdCElettronico.setText("Id Controllo Elettronico");

        tIdVeicolo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tIdVeicoloActionPerformed(evt);
            }
        });

        tableControlloVelocita.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id Controllo Velocita", "Multa", "Id Veicolo", "Id Controllo Elettronico"
            }
        ));
        jScrollPane1.setViewportView(tableControlloVelocita);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Multa può essere : T o F.\nId Veicolo, Id Controllo Elettronico sono chiavi esterne intere.");
        jScrollPane2.setViewportView(jTextArea1);

        comboVeicolo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboVeicoloActionPerformed(evt);
            }
        });

        comboControlloElettronico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboControlloElettronicoActionPerformed(evt);
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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelIdVeicolo)
                            .addComponent(labelIdCElettronico)
                            .addComponent(labelMulta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tMulta, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(tIdVeicolo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                                    .addComponent(tIdCElettronico, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboControlloElettronico, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboVeicolo, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMulta)
                    .addComponent(tMulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIdVeicolo)
                    .addComponent(tIdVeicolo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboVeicolo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIdCElettronico)
                    .addComponent(tIdCElettronico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboControlloElettronico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tIdVeicoloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tIdVeicoloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tIdVeicoloActionPerformed

    private void comboVeicoloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboVeicoloActionPerformed
       String numberOnly = comboVeicolo.getSelectedItem().toString().substring(0, 5).replaceAll("[^0-9]", "");
        tIdVeicolo.setText(numberOnly);
    }//GEN-LAST:event_comboVeicoloActionPerformed

    private void comboControlloElettronicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboControlloElettronicoActionPerformed
        tIdCElettronico.setText(comboControlloElettronico.getSelectedItem().toString());
    }//GEN-LAST:event_comboControlloElettronicoActionPerformed

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
            java.util.logging.Logger.getLogger(ControlloVelocita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ControlloVelocita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ControlloVelocita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ControlloVelocita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ControlloVelocita().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboControlloElettronico;
    private javax.swing.JComboBox<String> comboVeicolo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labelIdCElettronico;
    private javax.swing.JLabel labelIdVeicolo;
    private javax.swing.JLabel labelMulta;
    private javax.swing.JTextField tIdCElettronico;
    private javax.swing.JTextField tIdVeicolo;
    private javax.swing.JTextField tMulta;
    private javax.swing.JTable tableControlloVelocita;
    // End of variables declaration//GEN-END:variables
}
