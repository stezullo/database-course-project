package it.Barbuto.Zullo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/**
 *
 * @author Barbuto Salvatore e Zullo Stefano
 */

public class Login extends javax.swing.JDialog {
     /**
    * Costante per indicare la pressione del pulsante Annulla.
    */
   public static final int PREMUTO_ANNULLA = 0;
   /**
    * Costante per indicare la pressione del pulsante OK.
    */
   public static final int PREMUTO_LOGIN = 1;

    public Login(JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.tHost.setText(Database.host);
        this.tPorta.setText("" + Database.porta);
        this.tServizio.setText(Database.servizio);
        this.tSchema.setText(Database.schema);
        this.tUser.setText(Database.user);
        this.tPassword.setText(Database.password);
        this.tUser.requestFocus();
        getRootPane().setDefaultButton(bLogin);
        
    }
       /**
    * Variabile intera che registra quale pulsante &egrave; stato premuto.
    */
   private int premuto;

   /**
    * Restituisce il contenuto del campo premuto, che
    * rappresenta il pulsante premuto dall'utente
    *
    * @return intero che descrive il pulsante premuto
    */
   public int getPremuto() {
      return premuto;
   }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bLogin = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        bAnnulla = new javax.swing.JButton();
        tPassword = new javax.swing.JPasswordField();
        tSchema = new javax.swing.JTextField();
        tUser = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tHost = new javax.swing.JTextField();
        tServizio = new javax.swing.JTextField();
        tPorta = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Rete Autostradale");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        bLogin.setText("Login");
        bLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLoginActionPerformed(evt);
            }
        });

        jLabel5.setText("Password");

        bAnnulla.setText("Annulla");
        bAnnulla.setDefaultCapable(false);
        bAnnulla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAnnullaActionPerformed(evt);
            }
        });

        tPassword.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tPassword.setPreferredSize(new java.awt.Dimension(30, 20));
        tPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tPasswordActionPerformed(evt);
            }
        });

        tSchema.setEditable(false);
        tSchema.setForeground(new java.awt.Color(204, 204, 204));

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 0, 24)); // NOI18N
        jLabel6.setText("Log in");

        jLabel1.setText("Host");

        jLabel2.setText("Porta");

        jLabel3.setText("Servizio");

        jLabel7.setText("Schema");

        tHost.setText("localhost");
        tHost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tHostActionPerformed(evt);
            }
        });

        tServizio.setText("xe");

        tPorta.setText("1521");
        tPorta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tPortaActionPerformed(evt);
            }
        });

        jLabel4.setText("Utente");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel1)
                        .addComponent(jLabel3)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(bLogin))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tSchema, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                            .addComponent(tUser, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tServizio)
                            .addComponent(tPorta)
                            .addComponent(tHost))
                        .addGap(128, 128, 128))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bAnnulla)
                        .addGap(47, 47, 47))))
            .addGroup(layout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tPorta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tServizio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tSchema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 57, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bLogin)
                    .addComponent(bAnnulla))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLoginActionPerformed
        Database.host = tHost.getText();
        try {
            Database.porta = Integer.parseInt(tPorta.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La porta dev'essere"
                + " un numero", "Errore", JOptionPane.ERROR_MESSAGE);
        }
        
        Database.servizio = tServizio.getText();
        Database.user = tUser.getText();
        Database.schema = Database.user;
        Database.password = new String(tPassword.getPassword());
        try {
            Database.setDefaultConnection(Database.nuovaConnessione());
            //new Main().setVisible(true);
            premuto = PREMUTO_LOGIN;
            dispose();
        } catch (SQLException e) {
            Database.mostraErroriSwing(this, e);
            // Per dare informazioni piÃ¹ comprensibili all'utente:
            //JOptionPane.showMessageDialog(this, "Connessione fallita (credenziali"
                //        + " errate?)", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bLoginActionPerformed

    private void bAnnullaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAnnullaActionPerformed
        premuto = PREMUTO_ANNULLA;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        System.exit(0);
    }//GEN-LAST:event_bAnnullaActionPerformed

    private void tPortaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tPortaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tPortaActionPerformed

    private void tHostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tHostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tHostActionPerformed

    private void tPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tPasswordActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
       
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       System.exit(0);
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAnnulla;
    private javax.swing.JButton bLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField tHost;
    private javax.swing.JPasswordField tPassword;
    private javax.swing.JTextField tPorta;
    private javax.swing.JTextField tSchema;
    private javax.swing.JTextField tServizio;
    private javax.swing.JTextField tUser;
    // End of variables declaration//GEN-END:variables
}
