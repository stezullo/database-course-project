package it.Barbuto.Zullo;

import static it.Barbuto.Zullo.DataFrame.APPEND_QUERY;
import static it.Barbuto.Zullo.DataFrame.BROWSE;
import static it.Barbuto.Zullo.DataFrame.UPDATE;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author Barbuto Salvatore e Zullo Stefano
 */

public class Pedaggio extends javax.swing.JFrame{

    public Pedaggio() {
        initComponents();
        ComboBoxPedaggio(comboPedaggio);
    }
    
    private void ComboBoxPedaggio(JComboBox cb){
       String query = null;
        try {
            Connection conn = Database.getDefaultConnection();
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
            comboPedaggio.setModel(dcbm);
            query = "SELECT Identificativo, Marca, Modello FROM Veicolo";
            System.out.println(query);
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("PROVA3\n");
            while(rs.next()){ 
               dcbm.addElement(rs.getInt(1)+ " - "+rs.getString(2)+" "+rs.getString(3));
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
            System.out.println("ERRORE.\n");
        }
   }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelPedaggio = new javax.swing.JLabel();
        labelCalcoloPedaggio = new javax.swing.JLabel();
        tPedaggio = new javax.swing.JTextField();
        tPedaggioInserisci = new javax.swing.JTextField();
        bCalcola = new javax.swing.JButton();
        bMenu = new javax.swing.JButton();
        comboPedaggio = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rete Autostradale - Calcolo Pedaggio");

        labelPedaggio.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelPedaggio.setText("Id Veicolo");

        labelCalcoloPedaggio.setFont(new java.awt.Font("Yu Gothic UI", 0, 24)); // NOI18N
        labelCalcoloPedaggio.setText("Calcolo del Pedaggio");

        tPedaggio.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tPedaggio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tPedaggioActionPerformed(evt);
            }
        });

        tPedaggioInserisci.setEditable(false);
        tPedaggioInserisci.setBackground(new java.awt.Color(153, 153, 153));
        tPedaggioInserisci.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tPedaggioInserisci.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        bCalcola.setText("Calcola!");
        bCalcola.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCalcolaActionPerformed(evt);
            }
        });

        bMenu.setText("Torna al Menù");
        bMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMenuActionPerformed(evt);
            }
        });

        comboPedaggio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPedaggioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelCalcoloPedaggio)
                .addGap(198, 198, 198))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bMenu)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(labelPedaggio)
                            .addGap(18, 18, 18)
                            .addComponent(tPedaggio, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(32, 32, 32)
                            .addComponent(comboPedaggio, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(25, 25, 25)
                            .addComponent(tPedaggioInserisci, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(bCalcola))))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(labelCalcoloPedaggio)
                .addGap(17, 17, 17)
                .addComponent(bMenu)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPedaggio)
                    .addComponent(tPedaggio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboPedaggio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addComponent(bCalcola)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addComponent(tPedaggioInserisci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tPedaggioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tPedaggioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tPedaggioActionPerformed

    private void bCalcolaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCalcolaActionPerformed
        String cmdCalcola = null;
        try {
            cmdCalcola = "{?= call pedaggio_veicolo(?)}";
            Connection conn = Database.getDefaultConnection();
            int idveicolo = Integer.decode(tPedaggio.getText());
            CallableStatement cs;
            cs = conn.prepareCall(cmdCalcola);
            cs.registerOutParameter(1, Types.VARCHAR);
            cs.setInt(2,idveicolo);
            cs.executeUpdate();
            tPedaggioInserisci.setText(cs.getString(1));
        } 
        catch (SQLException ex) {
            //mostraErrori(ex,cmdCalcola,1);
            tPedaggioInserisci.setText("ERRORE. Id Veicolo non presente in Checkpoint Tappa Viaggio!");
        }
        catch (NumberFormatException nfe){
            tPedaggioInserisci.setText("ERRORE. Id non riconosciuto!");
        }
    }//GEN-LAST:event_bCalcolaActionPerformed

    private void bMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMenuActionPerformed
        Principale p = new Principale(0);
        setVisible(false);
        dispose();
    }//GEN-LAST:event_bMenuActionPerformed

    private void comboPedaggioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPedaggioActionPerformed
       String numberOnly = comboPedaggio.getSelectedItem().toString().substring(0, 5).replaceAll("[^0-9]", "");
        tPedaggio.setText(numberOnly);
    }//GEN-LAST:event_comboPedaggioActionPerformed

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
            java.util.logging.Logger.getLogger(Pedaggio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pedaggio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pedaggio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pedaggio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pedaggio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCalcola;
    private javax.swing.JButton bMenu;
    private javax.swing.JComboBox<String> comboPedaggio;
    private javax.swing.JLabel labelCalcoloPedaggio;
    private javax.swing.JLabel labelPedaggio;
    private javax.swing.JTextField tPedaggio;
    private javax.swing.JTextField tPedaggioInserisci;
    // End of variables declaration//GEN-END:variables
}
