package it.Barbuto.Zullo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Barbuto Salvatore e Zullo Stefano
 */
public class PercorsoTraCaselli extends javax.swing.JFrame {

    public PercorsoTraCaselli() {
        initComponents();
        String cmdCombo = null;
        try {
            Connection conn = Database.getDefaultConnection();
            DefaultComboBoxModel dcbm1 = new DefaultComboBoxModel();
            DefaultComboBoxModel dcbm2 = new DefaultComboBoxModel();
            comboCasello1.setModel(dcbm1);
            comboCasello2.setModel(dcbm2);
            cmdCombo = "SELECT NOME FROM "+Database.schema+".CASELLO "
                         +"ORDER BY NOME";
            System.out.println(cmdCombo);
            PreparedStatement ps = conn.prepareStatement(cmdCombo);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
               dcbm1.addElement(rs.getString(1));
               dcbm2.addElement(rs.getString(1));
            }
            rs.close();
            ps.close();
            conn.close();
        }
        catch (SQLException ex) {
            mostraErrori(ex,cmdCombo,1);
        }
        catch (NullPointerException npe){
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelTitle = new javax.swing.JLabel();
        labelCasello1 = new javax.swing.JLabel();
        tCasello1 = new javax.swing.JTextField();
        labelCasello2 = new javax.swing.JLabel();
        tCasello2 = new javax.swing.JTextField();
        bCalcola = new javax.swing.JButton();
        tCalcoloInserisci = new javax.swing.JTextField();
        bMenu = new javax.swing.JButton();
        comboCasello1 = new javax.swing.JComboBox<>();
        comboCasello2 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rete Autostradale - Percorso tra Caselli");

        labelTitle.setFont(new java.awt.Font("Yu Gothic UI", 0, 24)); // NOI18N
        labelTitle.setText("Percorso tra Caselli");

        labelCasello1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelCasello1.setText("Nome Casello 1");

        tCasello1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tCasello1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tCasello1ActionPerformed(evt);
            }
        });

        labelCasello2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelCasello2.setText("Nome Casello 2");

        tCasello2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tCasello2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tCasello2ActionPerformed(evt);
            }
        });

        bCalcola.setText("Calcola!");
        bCalcola.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCalcolaActionPerformed(evt);
            }
        });

        tCalcoloInserisci.setEditable(false);
        tCalcoloInserisci.setBackground(new java.awt.Color(153, 153, 153));
        tCalcoloInserisci.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tCalcoloInserisci.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        bMenu.setText("Torna al Menù");
        bMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMenuActionPerformed(evt);
            }
        });

        comboCasello1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCasello1ActionPerformed(evt);
            }
        });

        comboCasello2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCasello2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelCasello2)
                            .addComponent(labelCasello1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tCasello1, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                            .addComponent(tCasello2))
                        .addGap(195, 195, 195))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bCalcola)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 219, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(labelTitle)
                                .addGap(218, 218, 218))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bMenu)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(comboCasello2, javax.swing.GroupLayout.Alignment.LEADING, 0, 84, Short.MAX_VALUE)
                                        .addComponent(comboCasello1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(62, 62, 62))))))
            .addComponent(tCalcoloInserisci)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTitle)
                .addGap(17, 17, 17)
                .addComponent(bMenu)
                .addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboCasello1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelCasello1)
                        .addComponent(tCasello1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboCasello2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelCasello2)
                        .addComponent(tCasello2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addComponent(bCalcola)
                .addGap(76, 76, 76)
                .addComponent(tCalcoloInserisci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tCasello1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tCasello1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tCasello1ActionPerformed

    private void tCasello2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tCasello2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tCasello2ActionPerformed

    private void bCalcolaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCalcolaActionPerformed
        String cmdCalcola = null,nomeCasello1,nomeCasello2;
        int id_casello1,id_casello2;
        try {
            Connection conn = Database.getDefaultConnection();
            nomeCasello1 = tCasello1.getText();
            nomeCasello2 = tCasello2.getText();
            cmdCalcola = "SELECT IDENTIFICATIVO FROM "+Database.schema+".CASELLO "
                         +"WHERE NOME = "+"'"+nomeCasello1+"'";
            PreparedStatement ps = conn.prepareStatement(cmdCalcola);
            ResultSet rs = ps.executeQuery();
            rs.next();
            id_casello1 = rs.getInt(1);
            rs.close();
            cmdCalcola = "SELECT IDENTIFICATIVO FROM "+Database.schema+".CASELLO "
                         +"WHERE NOME = "+"'"+nomeCasello2+"'";
            ps = conn.prepareStatement(cmdCalcola);
            rs = ps.executeQuery();
            rs.next();
            id_casello2 = rs.getInt(1);
            cmdCalcola = "{?= call caselli_collegati(?,?)}";
            CallableStatement cs;
            cs = conn.prepareCall(cmdCalcola);
            cs.registerOutParameter(1, Types.VARCHAR);
            cs.setInt(2,id_casello1);
            cs.setInt(3,id_casello2);
            cs.executeUpdate();
            tCalcoloInserisci.setText(cs.getString(1));
        }
        catch (SQLException ex) {
            tCalcoloInserisci.setText("ERRORE. Il nome di uno dei due caselli non riconosciuto!");
        }
        catch (NumberFormatException nfe){
        }
    }//GEN-LAST:event_bCalcolaActionPerformed

    private void bMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMenuActionPerformed
        Principale p = new Principale(0);
        setVisible(false);
        dispose();
    }//GEN-LAST:event_bMenuActionPerformed

    private void comboCasello1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCasello1ActionPerformed
        tCasello1.setText(comboCasello1.getSelectedItem().toString());     
    }//GEN-LAST:event_comboCasello1ActionPerformed

    private void comboCasello2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCasello2ActionPerformed
        tCasello2.setText(comboCasello2.getSelectedItem().toString());
    }//GEN-LAST:event_comboCasello2ActionPerformed

    protected void mostraErrori(SQLException e, String query, int contesto) {
      String msg;
      if ((e.getErrorCode() == 17068 | e.getErrorCode() == 17011) & 
              contesto == 0) {
         return; //questo errore non mi interessa
      }
      msg = "ErrorCode= " + e.getErrorCode() + "\n";
      msg += "Message= " + e.getMessage() + "\n";
      msg += "SQLState= " + e.getSQLState() + "\n";

      JOptionPane.showMessageDialog(this, msg, "Errore",
              JOptionPane.ERROR_MESSAGE);
   }
    
    protected void mostraErrori(SQLException e) {
      mostraErrori(e, "", 0);
   }
    
    protected void mostraErrori(Exception e, int contesto) {
      String msg;
      msg = "Message= " + e.getMessage() + "\n";

      JOptionPane.showMessageDialog(this, msg, "Errore",
              JOptionPane.ERROR_MESSAGE);
   }
    
   protected void mostraErrori(Exception e) {
      mostraErrori(e, 0);
   }
   
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
            java.util.logging.Logger.getLogger(PercorsoTraCaselli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PercorsoTraCaselli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PercorsoTraCaselli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PercorsoTraCaselli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PercorsoTraCaselli().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCalcola;
    private javax.swing.JButton bMenu;
    private javax.swing.JComboBox<String> comboCasello1;
    private javax.swing.JComboBox<String> comboCasello2;
    private javax.swing.JLabel labelCasello1;
    private javax.swing.JLabel labelCasello2;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JTextField tCalcoloInserisci;
    private javax.swing.JTextField tCasello1;
    private javax.swing.JTextField tCasello2;
    // End of variables declaration//GEN-END:variables
}
