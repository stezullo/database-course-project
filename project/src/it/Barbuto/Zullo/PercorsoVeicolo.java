package it.Barbuto.Zullo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Barbuto Salvatore e Zullo Stefano
 */

public class PercorsoVeicolo extends javax.swing.JFrame {

    public PercorsoVeicolo() {
        initComponents();
        ComboBoxPercorso(comboPercorso);
    }
    
    protected void mostraErrori(SQLException e, String query, int contesto) {
      String msg;
      if (e.getErrorCode() == 1) {
         msg = "Esiste già un'altro Percorso con lo stesso identificativo";
         JOptionPane.showMessageDialog(this, msg, "Errore",
                 JOptionPane.ERROR_MESSAGE);
      } else {
      }
   }
    
   protected void mostraErrori(Exception e, int contesto) {
      String msg;
      msg = "Message= " + e.getMessage() + "\n";

      JOptionPane.showMessageDialog(this, msg, "Errore",
              JOptionPane.ERROR_MESSAGE);
   }

   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        labelTitolo = new javax.swing.JLabel();
        labelIdVeicolo = new javax.swing.JLabel();
        bCalcolaPercorso = new javax.swing.JButton();
        tCalcoloPercorso = new javax.swing.JTextField();
        bMenu = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePercorsoVeicolo = new javax.swing.JTable();
        comboPercorso = new javax.swing.JComboBox();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rete Autostradale - Percorso Veicolo");

        labelTitolo.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        labelTitolo.setText("Calcolo del percorso di un veicolo");

        labelIdVeicolo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelIdVeicolo.setText("Id Veicolo : ");

        bCalcolaPercorso.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        bCalcolaPercorso.setText("Calcola!");
        bCalcolaPercorso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCalcolaPercorsoActionPerformed(evt);
            }
        });

        tCalcoloPercorso.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        bMenu.setText("Torna al Menù");
        bMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMenuActionPerformed(evt);
            }
        });

        tablePercorsoVeicolo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Percorso"
            }
        ));
        jScrollPane1.setViewportView(tablePercorsoVeicolo);

        comboPercorso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPercorsoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(labelIdVeicolo)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelTitolo)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tCalcoloPercorso, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(comboPercorso, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 235, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bCalcolaPercorso)
                    .addComponent(bMenu))
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTitolo)
                .addGap(39, 39, 39)
                .addComponent(bMenu)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIdVeicolo)
                    .addComponent(tCalcoloPercorso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bCalcolaPercorso)
                    .addComponent(comboPercorso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ComboBoxPercorso(JComboBox cb){
       String query = null;
        try {
            Connection conn = Database.getDefaultConnection();
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
            comboPercorso.setModel(dcbm);
            query = "SELECT Identificativo, Marca, Modello FROM Veicolo";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
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
            System.out.println("ERRORE.\n");
        }
   }
    
    private void bCalcolaPercorsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCalcolaPercorsoActionPerformed
       int id_veicolo = Integer.decode(tCalcoloPercorso.getText());
       String cmd = "{call percorso_veicolo(?)}";
       Connection conn;
        try {
            InizializzaTabellaDatabase();
            conn = Database.getDefaultConnection();
            CallableStatement cs = conn.prepareCall(cmd);
            cs.setInt(1, id_veicolo);
            cs.executeUpdate(); // inserimento righe all'interno della tabella
            cmd = "SELECT AzioneVeicolo FROM " + Database.schema + "." + "tempPercorso"
                    + " WHERE IdVeicolo = "+ id_veicolo;
            PreparedStatement st = conn.prepareCall(cmd);
            ResultSet rs = st.executeQuery();
            DefaultTableModel model = (DefaultTableModel) tablePercorsoVeicolo.getModel();
            ResultSetMetaData meta = rs.getMetaData();
            int ncolonne = meta.getColumnCount();
            while(rs.next()){ 
                Object [] datiRighe = new Object[ncolonne];
                for (int i = 0; i < datiRighe.length; ++i)
                    datiRighe[i] = rs.getObject(i+1);
                model.addRow(datiRighe);
            }
        } catch (SQLException ex) {
            mostraErrori(ex,cmd,1);
        }
       
    }//GEN-LAST:event_bCalcolaPercorsoActionPerformed

    private void bMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMenuActionPerformed
        Principale p = new Principale(0);
        setVisible(false);
        dispose();
    }//GEN-LAST:event_bMenuActionPerformed

    private void comboPercorsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPercorsoActionPerformed
        String numberOnly = comboPercorso.getSelectedItem().toString().substring(0, 5).replaceAll("[^0-9]", "");
        tCalcoloPercorso.setText(numberOnly);
    }//GEN-LAST:event_comboPercorsoActionPerformed

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
            java.util.logging.Logger.getLogger(PercorsoVeicolo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PercorsoVeicolo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PercorsoVeicolo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PercorsoVeicolo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PercorsoVeicolo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCalcolaPercorso;
    private javax.swing.JButton bMenu;
    private javax.swing.JComboBox comboPercorso;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel labelIdVeicolo;
    private javax.swing.JLabel labelTitolo;
    private javax.swing.JTextField tCalcoloPercorso;
    private javax.swing.JTable tablePercorsoVeicolo;
    // End of variables declaration//GEN-END:variables

    private void InizializzaTabellaDatabase() throws SQLException {
        Connection conn = Database.getDefaultConnection();
        Statement st = conn.createStatement();
        st.execute("delete from tempPercorso");
    }
}
