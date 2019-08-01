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

public class Veicolo extends DataFrame {

    public Veicolo() {
        initComponents();
         setModalita(APPEND_QUERY);
         setFrameTable(tableVeicolo);
         setNomeTabella("Veicolo");
         ComboBoxInitialize(comboIdentificativo);
    }

    final public void setModalita(int modo) {
      super.setModalita(modo);
      switch (modo) {
         case APPEND_QUERY:
            tTarga.setEnabled(true);
            tMarca.setEnabled(true);
            tModello.setEnabled(true);
            tCilindrata.setEnabled(true);
            tClassePedaggio.setEnabled(true);
            break;
         case BROWSE:
            tTarga.setEnabled(false);
            tMarca.setEnabled(false);
            tModello.setEnabled(false);
            tCilindrata.setEnabled(false);
            tClassePedaggio.setEnabled(false);
            break;
         case UPDATE:
            tTarga.setEnabled(true);
            tMarca.setEnabled(true);
            tModello.setEnabled(true);
            tCilindrata.setEnabled(true);
            tClassePedaggio.setEnabled(true);
            break;
      }
   }
    @Override
   public void ComboBoxInitialize(JComboBox cb){
       try {
            super.ComboBoxInitialize(cb);
            Connection conn = Database.getDefaultConnection();
            dcbm = new DefaultComboBoxModel();
            comboIdentificativo.setModel(dcbm);
            query = query + "Veicolo";
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
    
    protected void mostraErrori(SQLException e, String query, int contesto) {
      String msg;
      if (e.getErrorCode() == 1) {
         msg = "Esiste già un'altro Veicolo con lo stesso identificativo";
         JOptionPane.showMessageDialog(this, msg, "Errore",
                 JOptionPane.ERROR_MESSAGE);
      } else {
         super.mostraErrori(e, query, contesto);
      }
   }
    
     protected void premutoOK() {
      if (getPadre() != null) {
         getPadre().setProprietaPadre("Veicolo", getIdText().getText());
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
         tTarga.setText(rs.getString("Targa"));
         tMarca.setText(rs.getString("Marca"));
         tModello.setText(rs.getString("Modello"));
         tCilindrata.setText(rs.getString("Cilindrata"));
         tClassePedaggio.setText(rs.getString("ClassePedaggio"));
         super.mostraDati();
      } catch (SQLException e) {
         mostraErrori(e);
      }
   }
     
     protected void pulisci() {
      super.pulisci();
      tTarga.setText("");
      tMarca.setText("");
      tModello.setText("");
      tCilindrata.setText("");
      tClassePedaggio.setText("");
   }
   
   protected PreparedStatement creaSelectStatement() {
      Connection con;
      PreparedStatement st;
      String id, targa, marca, modello, cilindrata, classepedaggio;
      Pattern pat;
      Matcher matc;
      int k = 1;
      super.creaSelectStatement();
      id = getIdText().getText();
      targa = tTarga.getText();
      marca = tMarca.getText();
      modello = tModello.getText();
      cilindrata = tCilindrata.getText();
      classepedaggio = tClassePedaggio.getText();

      query += " where";
      //}
      if (id.length() > 0) {
         query += " Identificativo= ? and";
      }
      if (targa.length() > 0) {
         if (targa.contains("%")) {
            query += " Targa like ? and";
         } else {
            query += " Targa = ? and";
         }
      }
      if (marca.length() > 0) {
         if (marca.contains("%")) {
            query += " Marca like ? and";
         } else {
            query += " Marca = ? and";
         }
      }
      if (modello.length() > 0) {
         if (modello.contains("%")) {
            query += " Modello like ? and";
         } else {
            query += " Modello = ? and";
         }
      }
      if (cilindrata.length() > 0) {
         if (cilindrata.contains("%")) {
            query += " Cilindrata like ? and";
         } else {
            query += " Cilindrata = ? and";
         }
      }
      if (classepedaggio.length() > 0) {
         if (classepedaggio.contains("%")) {
            query += " ClassePedaggio like ?";
         } else {
            query += " ClassePedaggio = ?";
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
         if (targa.length() > 0) {
            st.setString(k++, targa);
         }
         if (marca.length() > 0) {
            st.setString(k++, marca);
         }
         if (modello.length() > 0) {
            st.setString(k++, modello);
         }
         if (cilindrata.length() > 0) {
            st.setString(k++, cilindrata);
         }
         if (classepedaggio.length() > 0) {
            st.setInt(k++, Integer.decode(classepedaggio));
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
      cmdIns = "insert into " + Database.schema + ".Veicolo (Identificativo,Targa,"
              + "Marca,Modello,Cilindrata,ClassePedaggio) values(?,?,?,?,?,?)";
      st = c.prepareStatement(cmdIns);
      st.setInt(1, Integer.decode(getIdText().getText()));
      st.setString(2, tTarga.getText());
      st.setString(3, tMarca.getText());
      st.setString(4, tModello.getText());
      st.setString(5, tCilindrata.getText());
      st.setInt(6, Integer.decode(tClassePedaggio.getText()));
      return st;
   }
   
   protected PreparedStatement getComandoAggiornamento(Connection c)
           throws SQLException {
      String cmdUp;
      PreparedStatement st;
      cmdUp = "update " + Database.schema + ".Veicolo set Targa=?,Marca=?,Modello=?,Cilindrata=?,ClassePedaggio=? "
              + "where Identificativo=?";
      st = c.prepareStatement(cmdUp);
      st.setInt(6, Integer.decode(getIdText().getText()));
      st.setString(1, tTarga.getText());
      st.setString(2, tMarca.getText());
      st.setString(3, tModello.getText());
      st.setString(4, tCilindrata.getText());
      st.setString(5, tClassePedaggio.getText());
      return st;
   }

@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tTarga = new javax.swing.JTextField();
        tMarca = new javax.swing.JTextField();
        tModello = new javax.swing.JTextField();
        tCilindrata = new javax.swing.JTextField();
        tClassePedaggio = new javax.swing.JTextField();
        labelTarga = new javax.swing.JLabel();
        labelMarca = new javax.swing.JLabel();
        labelModello = new javax.swing.JLabel();
        labelCilindrata = new javax.swing.JLabel();
        labelClassePedaggio = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableVeicolo = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rete Autostradale - Veicolo");

        tCilindrata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tCilindrataActionPerformed(evt);
            }
        });

        labelTarga.setText("Targa");

        labelMarca.setText("Marca");

        labelModello.setText("Modello");

        labelCilindrata.setText("Cilindrata");

        labelClassePedaggio.setText("Classe Pedaggio");

        tableVeicolo.setModel(new javax.swing.table.DefaultTableModel(
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
                "Id Veicolo", "Targa", "Marca", "Modello", "Cilindrata", "Classe Pedaggio"
            }
        ));
        jScrollPane1.setViewportView(tableVeicolo);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Targa,Marca e Modello sono stringhe.\nCilindrata è un numero intero.\nClasse Pedaggio può essere A,B,3,4,5.");
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 882, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelTarga)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelMarca)
                            .addComponent(labelModello)
                            .addComponent(labelCilindrata)
                            .addComponent(labelClassePedaggio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tTarga, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                            .addComponent(tMarca)
                            .addComponent(tModello)
                            .addComponent(tCilindrata)
                            .addComponent(tClassePedaggio)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTarga)
                    .addComponent(tTarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMarca)
                    .addComponent(tMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelModello)
                    .addComponent(tModello, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCilindrata)
                    .addComponent(tCilindrata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelClassePedaggio)
                    .addComponent(tClassePedaggio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tCilindrataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tCilindrataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tCilindrataActionPerformed

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
            java.util.logging.Logger.getLogger(Veicolo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Veicolo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Veicolo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Veicolo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Veicolo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labelCilindrata;
    private javax.swing.JLabel labelClassePedaggio;
    private javax.swing.JLabel labelMarca;
    private javax.swing.JLabel labelModello;
    private javax.swing.JLabel labelTarga;
    private javax.swing.JTextField tCilindrata;
    private javax.swing.JTextField tClassePedaggio;
    private javax.swing.JTextField tMarca;
    private javax.swing.JTextField tModello;
    private javax.swing.JTextField tTarga;
    private javax.swing.JTable tableVeicolo;
    // End of variables declaration//GEN-END:variables
}
