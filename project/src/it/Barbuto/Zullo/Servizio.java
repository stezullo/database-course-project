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

public class Servizio extends DataFrame {

    public Servizio() {
        initComponents();
        setModalita(APPEND_QUERY);
        setFrameTable(tableServizio);
        setNomeTabella("Servizio");
        ComboBoxInitialize(comboIdentificativo);
        ComboBoxArea(comboArea);
    }
    
    @Override
   public void ComboBoxInitialize(JComboBox cb){
       try {
            super.ComboBoxInitialize(cb);
            Connection conn = Database.getDefaultConnection();
            dcbm = new DefaultComboBoxModel();
            comboIdentificativo.setModel(dcbm);
            query = query + "Servizio";
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
   
   private void ComboBoxArea(JComboBox cb){
       try {
            Connection conn = Database.getDefaultConnection();
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
            cb.setModel(dcbm);
            query = "SELECT Identificativo, PosizioneArea FROM Area";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){ 
               dcbm.addElement(rs.getInt(1)+ " - Area al km "+rs.getFloat(2));
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
            tGPL.setEnabled(true);
            tMetano.setEnabled(true);
            tArea_Camper.setEnabled(true);
            tWiFi.setEnabled(true);
            tHiPoint.setEnabled(true);
            tBancomat.setEnabled(true);
            tIdrogeno.setEnabled(true);
            tElettrica.setEnabled(true);
            tIdArea.setEnabled(true);
            break;
         case BROWSE:
            tGPL.setEnabled(false);
            tMetano.setEnabled(false);
            tArea_Camper.setEnabled(false);
            tWiFi.setEnabled(false);
            tHiPoint.setEnabled(false);
            tBancomat.setEnabled(false);
            tIdrogeno.setEnabled(false);
            tElettrica.setEnabled(false);
            tIdArea.setEnabled(false);
            break;
         case UPDATE:
            tGPL.setEnabled(true);
            tMetano.setEnabled(true);
            tArea_Camper.setEnabled(true);
            tWiFi.setEnabled(true);
            tHiPoint.setEnabled(true);
            tBancomat.setEnabled(true);
            tIdrogeno.setEnabled(true);
            tElettrica.setEnabled(true);
            tIdArea.setEnabled(true);
            break;
      }
   }
    
    protected void mostraErrori(SQLException e, String query, int contesto) {
      String msg;
      if (e.getErrorCode() == 1) {
         msg = "Esiste già un'altra Area di Servizio con lo stesso identificativo";
         JOptionPane.showMessageDialog(this, msg, "Errore",
                 JOptionPane.ERROR_MESSAGE);
      } else {
         super.mostraErrori(e, query, contesto);
      }
   }
    
     protected void premutoOK() {
      if (getPadre() != null) {
         getPadre().setProprietaPadre("Servizio", getIdText().getText());
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
         tGPL.setText(rs.getString("GPL"));
         tMetano.setText(rs.getString("Metano"));
         tArea_Camper.setText(rs.getString("Area_Camper"));
         tWiFi.setText(rs.getString("WiFi"));
         tHiPoint.setText(rs.getString("HiPoint"));
         tBancomat.setText(rs.getString("Bancomat"));
         tIdrogeno.setText(rs.getString("Idrogeno"));
         tElettrica.setText(rs.getString("Elettrica"));
         tIdArea.setText(rs.getString("IdArea"));
         super.mostraDati();
      } catch (SQLException e) {
         mostraErrori(e);
      }
   }
     
     protected void pulisci() {
      super.pulisci();
      tGPL.setText("");
         tMetano.setText("");
         tArea_Camper.setText("");
         tWiFi.setText("");
         tHiPoint.setText("");
         tBancomat.setText("");
         tIdrogeno.setText("");
         tElettrica.setText("");
         tIdArea.setText("");
     }
   
   protected PreparedStatement creaSelectStatement() {
      Connection con;
      PreparedStatement st;
      String id, gpl, metano,areacamper,wifi,hipoint,bancomat,idrogeno,elettrica,idarea;
      Pattern pat;
      Matcher matc;
      int k = 1;
      super.creaSelectStatement();
      id = getIdText().getText();
      gpl = tGPL.getText();
      metano  = tMetano.getText();
      areacamper = tArea_Camper.getText();
      wifi = tWiFi.getText();
      hipoint = tHiPoint.getText();
      bancomat = tBancomat.getText();
      idrogeno = tIdrogeno.getText();
      elettrica = tElettrica.getText();
      idarea = tIdArea.getText();
      query += " where";
      //}
      if (id.length() > 0) {
         query += " Identificativo= ? and";
      }
      if (gpl.length() > 0) {
         if (gpl.contains("%")) {
            query += " GPL like ? and";
         } else {
            query += " GPL = ? and";
         }
      }
      if (metano.length() > 0) {
         if (metano.contains("%")) {
            query += " METANO like ? and";
         } else {
            query += " METANO = ? and";
         }
      }
      if (areacamper.length() > 0) {
         if (areacamper.contains("%")) {
            query += " AREA_CAMPER like ? and";
         } else {
            query += " AREA_CAMPER = ? and";
         }
      }
      if (wifi.length() > 0) {
         if (wifi.contains("%")) {
            query += " WiFi like ? and";
         } else {
            query += " WiFi = ? and";
         }
      }
      if (hipoint.length() > 0) {
         if (hipoint.contains("%")) {
            query += " HiPoint like ? and";
         } else {
            query += " HiPoint = ? and";
         }
      }
      if (bancomat.length() > 0) {
         if (bancomat.contains("%")) {
            query += " Bancomat like ? and";
         } else {
            query += " Bancomat = ? and";
         }
      }
      if (idrogeno.length() > 0) {
         if (idrogeno.contains("%")) {
            query += " Idrogeno like ? and";
         } else {
            query += " Idrogeno = ? and";
         }
      }
      if (elettrica.length() > 0) {
         if (elettrica.contains("%")) {
            query += " Elettrica like ? and";
         } else {
            query += " Elettrica = ? and";
         }
      }
      if (idarea.length() > 0) {
         if (idarea.contains("%")) {
            query += " IdArea like ? and";
         } else {
            query += " IdArea = ? and";
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
         if (gpl.length() > 0) {
            st.setString(k++, gpl);
         }
         if (metano.length() > 0) {
            st.setString(k++, metano);
         }
         if (areacamper.length() > 0) {
            st.setString(k++, areacamper);
         }
         if (wifi.length() > 0) {
            st.setString(k++, wifi);
         }
         if (hipoint.length() > 0) {
            st.setString(k++, hipoint);
         }
         if (bancomat.length() > 0) {
            st.setString(k++, bancomat);
         }
         if (idrogeno.length() > 0) {
            st.setString(k++, idrogeno);
         }
         if (elettrica.length() > 0) {
            st.setString(k++, elettrica);
         }
         if (idarea.length() > 0) {
            st.setString(k++, idarea);
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
      cmdIns = "insert into " + Database.schema + ".Servizio (Identificativo,GPL,"
              + "METANO,Area_Camper,WiFi,HiPoint,Bancomat,Idrogeno,Elettrica,IdArea) values(?,?,?,?,?,?,?,?,?,?)";
      st = c.prepareStatement(cmdIns);
      st.setInt(1, Integer.decode(getIdText().getText()));
      st.setString(2, tGPL.getText());
      st.setString(3, tMetano.getText());
      st.setString(4, tArea_Camper.getText());
      st.setString(5, tWiFi.getText());
      st.setString(6, tHiPoint.getText());
      st.setString(7, tBancomat.getText());
      st.setString(8, tIdrogeno.getText());
      st.setString(9, tElettrica.getText());
      st.setString(10, tIdArea.getText());
      return st;
   }
   
   protected PreparedStatement getComandoAggiornamento(Connection c)
           throws SQLException {
      String cmdUp;
      PreparedStatement st;
      cmdUp = "update " + Database.schema + ".Servizio set GPL=?,METANO=?,AREA_CAMPER=?,WiFi=?,HiPoint=?,Bancomat=?,Idrogeno=?,Elettrica=?,IdArea=? "
              + "where Identificativo=?";
      st = c.prepareStatement(cmdUp);
      st.setInt(10, Integer.decode(getIdText().getText()));
      st.setString(1, tGPL.getText());
      st.setString(2, tMetano.getText());
      st.setString(3, tArea_Camper.getText());
      st.setString(4, tWiFi.getText());
      st.setString(5, tHiPoint.getText());
      st.setString(6, tBancomat.getText());
      st.setString(7, tIdrogeno.getText());
      st.setString(8, tElettrica.getText());
      st.setString(9, tIdArea.getText());
      return st;
   }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelGPL = new javax.swing.JLabel();
        labelMetano = new javax.swing.JLabel();
        labelArea_Camper = new javax.swing.JLabel();
        labelWifi = new javax.swing.JLabel();
        labelHiPoint = new javax.swing.JLabel();
        labelBancomat = new javax.swing.JLabel();
        labelIdrogeno = new javax.swing.JLabel();
        labelElettrica = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tGPL = new javax.swing.JTextField();
        tMetano = new javax.swing.JTextField();
        tArea_Camper = new javax.swing.JTextField();
        tWiFi = new javax.swing.JTextField();
        tHiPoint = new javax.swing.JTextField();
        tBancomat = new javax.swing.JTextField();
        tIdrogeno = new javax.swing.JTextField();
        tElettrica = new javax.swing.JTextField();
        tIdArea = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableServizio = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        comboArea = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rete Autostradale - Area di Servizio");

        labelGPL.setText("GPL");

        labelMetano.setText("Metano");

        labelArea_Camper.setText("Area Camper");

        labelWifi.setText("WiFi");

        labelHiPoint.setText("Hi Point");

        labelBancomat.setText("Bancomat");

        labelIdrogeno.setText("Idrogeno");

        labelElettrica.setText("Elettrica");

        jLabel9.setText("IdArea");

        tGPL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tGPLActionPerformed(evt);
            }
        });

        tMetano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tMetanoActionPerformed(evt);
            }
        });

        tWiFi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tWiFiActionPerformed(evt);
            }
        });

        tHiPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tHiPointActionPerformed(evt);
            }
        });

        tableServizio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "IdServizio", "GPL", "Metano", "Area_Camper", "WiFi", "Hi Point", "Bancomat", "Idrogeno", "Elettrica", "IdArea"
            }
        ));
        jScrollPane1.setViewportView(tableServizio);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("GPL,Metano, Area Camper, WiFi, Hi Point, Bancomat, Idrogeno, Elettrica\npossono essere T o F.\nId Area è una chiave esterna intera.");
        jScrollPane2.setViewportView(jTextArea1);

        comboArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAreaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(labelWifi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelArea_Camper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelMetano, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelGPL, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tWiFi)
                    .addComponent(tMetano)
                    .addComponent(tArea_Camper)
                    .addComponent(tGPL, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelElettrica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelIdrogeno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelBancomat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelHiPoint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tHiPoint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                    .addComponent(tBancomat, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tIdrogeno, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tElettrica, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tIdArea))
                .addGap(18, 18, 18)
                .addComponent(comboArea, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 872, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelHiPoint)
                            .addComponent(tHiPoint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelBancomat)
                            .addComponent(tBancomat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelIdrogeno)
                            .addComponent(tIdrogeno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelElettrica)
                            .addComponent(tElettrica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(tIdArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelGPL)
                            .addComponent(tGPL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelMetano)
                            .addComponent(tMetano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelArea_Camper)
                            .addComponent(tArea_Camper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelWifi)
                            .addComponent(tWiFi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tGPLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tGPLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tGPLActionPerformed

    private void tMetanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tMetanoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tMetanoActionPerformed

    private void tWiFiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tWiFiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tWiFiActionPerformed

    private void tHiPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tHiPointActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tHiPointActionPerformed

    private void comboAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAreaActionPerformed
        String numberOnly = comboArea.getSelectedItem().toString().substring(0, 5).replaceAll("[^0-9]", "");
        tIdArea.setText(numberOnly);
    }//GEN-LAST:event_comboAreaActionPerformed

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
            java.util.logging.Logger.getLogger(Servizio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Servizio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Servizio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Servizio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Servizio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboArea;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labelArea_Camper;
    private javax.swing.JLabel labelBancomat;
    private javax.swing.JLabel labelElettrica;
    private javax.swing.JLabel labelGPL;
    private javax.swing.JLabel labelHiPoint;
    private javax.swing.JLabel labelIdrogeno;
    private javax.swing.JLabel labelMetano;
    private javax.swing.JLabel labelWifi;
    private javax.swing.JTextField tArea_Camper;
    private javax.swing.JTextField tBancomat;
    private javax.swing.JTextField tElettrica;
    private javax.swing.JTextField tGPL;
    private javax.swing.JTextField tHiPoint;
    private javax.swing.JTextField tIdArea;
    private javax.swing.JTextField tIdrogeno;
    private javax.swing.JTextField tMetano;
    private javax.swing.JTextField tWiFi;
    private javax.swing.JTable tableServizio;
    // End of variables declaration//GEN-END:variables
}
