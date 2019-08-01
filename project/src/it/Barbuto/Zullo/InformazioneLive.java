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

public class InformazioneLive extends DataFrame {

    /**
     * Creates new form InformazioneLive
     */
    public InformazioneLive() {
        initComponents();
         setModalita(APPEND_QUERY);
         setFrameTable(tableInformazioneLive);
         setNomeTabella("InformazioneLive");
         ComboBoxInitialize(comboIdentificativo);
         ComboBoxDirezione(comboDirezione);
    }
    
    @Override
   public void ComboBoxInitialize(JComboBox cb){
       try {
            super.ComboBoxInitialize(cb);
            Connection conn = Database.getDefaultConnection();
            dcbm = new DefaultComboBoxModel();
            comboIdentificativo.setModel(dcbm);
            query = query + "InformazioneLive";
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
   
   private void ComboBoxDirezione(JComboBox cb){
       try {
            Connection conn = Database.getDefaultConnection();
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
            comboDirezione.setModel(dcbm);
            query = "SELECT Identificativo, NomeDirezione FROM DirezioneTratta";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("PROVA3\n");
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
            tInizioPosizioneEvento.setEnabled(true);
            tFinePosizioneEvento.setEnabled(true);
            tInfoAggiuntive.setEnabled(true);
            tTempoEvento.setEnabled(true);
            tIdDirezioneTratta.setEnabled(true);
            break;
         case BROWSE:
            tInizioPosizioneEvento.setEnabled(false);
            tFinePosizioneEvento.setEnabled(false);
            tInfoAggiuntive.setEnabled(false);
            tTempoEvento.setEnabled(false);
            tIdDirezioneTratta.setEnabled(false);
            break;
         case UPDATE:
            tInizioPosizioneEvento.setEnabled(true);
            tFinePosizioneEvento.setEnabled(true);
            tInfoAggiuntive.setEnabled(true);
            tTempoEvento.setEnabled(true);
            tIdDirezioneTratta.setEnabled(true);
            break;
      }
   }
    
    protected void mostraErrori(SQLException e, String query, int contesto) {
      String msg;
      if (e.getErrorCode() == 1) {
         msg = "Esiste già un'altra InformazioneLive con lo stesso identificativo";
         JOptionPane.showMessageDialog(this, msg, "Errore",
                 JOptionPane.ERROR_MESSAGE);
      } else {
         super.mostraErrori(e, query, contesto);
      }
   }
    
     protected void premutoOK() {
      if (getPadre() != null) {
         getPadre().setProprietaPadre("InformazioneLive", getIdText().getText());
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
         tInizioPosizioneEvento.setText(rs.getString("InizioPosizioneEvento"));
         tFinePosizioneEvento.setText(rs.getString("FinePosizioneEvento"));
         tInfoAggiuntive.setText(rs.getString("Inizio"));
         tTempoEvento.setText(rs.getString("TempoEvento"));
         tIdDirezioneTratta.setText(rs.getString("IdDirezioneTratta"));
         super.mostraDati();
      } catch (SQLException e) {
         mostraErrori(e);
      }
   }
     
     protected void pulisci() {
      super.pulisci();
      tInizioPosizioneEvento.setText("");
      tFinePosizioneEvento.setText("");
      tInfoAggiuntive.setText("");
      tTempoEvento.setText("");
      tIdDirezioneTratta.setText("");
   }
   
   protected PreparedStatement creaSelectStatement() {
      Connection con;
      PreparedStatement st;
      String id, inizioposizioneevento, fineposizioneevento, infoaggiuntive, tempoevento, iddirezionetratta;
      Pattern pat;
      Matcher matc;
      int k = 1;
      super.creaSelectStatement();
      id = getIdText().getText();
      inizioposizioneevento = tInizioPosizioneEvento.getText();
      fineposizioneevento = tFinePosizioneEvento.getText();
      infoaggiuntive = tInfoAggiuntive.getText();
      tempoevento = tTempoEvento.getText();
      iddirezionetratta = tIdDirezioneTratta.getText();

      query += " where";
      //}
      if (id.length() > 0) {
         query += " Identificativo= ? and";
      }
      if (inizioposizioneevento.length() > 0) {
         if (inizioposizioneevento.contains("%")) {
            query += " InizioPosizioneEvento like ? and";
         } else {
            query += " InizioPosizioneEvento = ? and";
         }
      }
      if (fineposizioneevento.length() > 0) {
         if (fineposizioneevento.contains("%")) {
            query += " FinePosizioneEvento like ? and";
         } else {
            query += " FinePosizioneEvento = ? and";
         }
      }
      if (infoaggiuntive.length() > 0) {
         if (infoaggiuntive.contains("%")) {
            query += " InfoAggiuntive like ? and";
         } else {
            query += " InfoAggiuntive = ? and";
         }
      }
      if (tempoevento.length() > 0) {
         if (tempoevento.contains("%")) {
            query += " TempoEvento like ? and";
         } else {
            query += " TempoEvento = ? and";
         }
      }
      if (iddirezionetratta.length() > 0) {
         if (iddirezionetratta.contains("%")) {
            query += " IdDirezioneTratta like ?";
         } else {
            query += " IdDirezioneTratta = ?";
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
         if (inizioposizioneevento.length() > 0) {
            st.setString(k++, inizioposizioneevento);
         }
         if (fineposizioneevento.length() > 0) {
            st.setString(k++, fineposizioneevento);
         }
         if (infoaggiuntive.length() > 0) {
            st.setString(k++, infoaggiuntive);
         }
         if (tempoevento.length() > 0) {
            st.setString(k++, tempoevento);
         }
         if (iddirezionetratta.length() > 0) {
            st.setInt(k++, Integer.decode(iddirezionetratta));
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
      cmdIns = "insert into " + Database.schema + ".InformazioneLive (Identificativo,InizioPosizioneEvento,"
              + "FinePosizioneEvento,InfoAggiuntive,TempoEvento,IdDirezioneTratta) values(?,?,?,?,?,?)";
      st = c.prepareStatement(cmdIns);
      st.setInt(1, Integer.decode(getIdText().getText()));
      st.setString(2, tInizioPosizioneEvento.getText());
      st.setString(3, tFinePosizioneEvento.getText());
      st.setString(4, tInfoAggiuntive.getText());
      st.setString(5, tTempoEvento.getText());
      st.setInt(6, Integer.decode(tIdDirezioneTratta.getText()));
      return st;
   }
   
   protected PreparedStatement getComandoAggiornamento(Connection c)
           throws SQLException {
      String cmdUp;
      PreparedStatement st;
      cmdUp = "update " + Database.schema + ".InformazioneLive set InizioPosizioneEvento=?,FinePosizioneEvento=?,InfoAggiuntive=?,TempoEvento=?,IdDirezioneTratta=? "
              + "where Identificativo=?";
      st = c.prepareStatement(cmdUp);
      st.setInt(6, Integer.decode(getIdText().getText()));
      st.setString(1, tInizioPosizioneEvento.getText());
      st.setString(2, tFinePosizioneEvento.getText());
      st.setString(3, tInfoAggiuntive.getText());
      st.setString(4, tTempoEvento.getText());
      st.setString(5, tIdDirezioneTratta.getText());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tableInformazioneLive = new javax.swing.JTable();
        labelInizioPosizioneEvento = new javax.swing.JLabel();
        labelFinePosizioneEvento = new javax.swing.JLabel();
        labelInfoAggiuntive = new javax.swing.JLabel();
        labelTempoEvento = new javax.swing.JLabel();
        labelIdDirezioneTratta = new javax.swing.JLabel();
        tInizioPosizioneEvento = new javax.swing.JTextField();
        tFinePosizioneEvento = new javax.swing.JTextField();
        tInfoAggiuntive = new javax.swing.JTextField();
        tTempoEvento = new javax.swing.JTextField();
        tIdDirezioneTratta = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        comboDirezione = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rete Autostradale - Informazione Live");

        tableInformazioneLive.setModel(new javax.swing.table.DefaultTableModel(
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
                "Id InfoLive", "Inizio", "Fine", "Informazioni", "Ora", "Id Dir. Tratta"
            }
        ));
        jScrollPane1.setViewportView(tableInformazioneLive);

        labelInizioPosizioneEvento.setText("Inizio Posizione Evento");

        labelFinePosizioneEvento.setText("Fine Posizione Evento");

        labelInfoAggiuntive.setText("Info Evento");

        labelTempoEvento.setText("Tempo Evento");

        labelIdDirezioneTratta.setText("Id Direzione Tratta");

        tFinePosizioneEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tFinePosizioneEventoActionPerformed(evt);
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Inizio Posizione Evento e Fine Posizione Evento (KM) sono numeri reali.\nInfo Evento è una stringa di descrizione dell'evento.\nTempo Evento è una data.\nId Direzione Tratta è una chiave esterna intera.");
        jScrollPane2.setViewportView(jTextArea1);

        comboDirezione.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDirezioneActionPerformed(evt);
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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelFinePosizioneEvento)
                            .addComponent(labelInfoAggiuntive)
                            .addComponent(labelTempoEvento)
                            .addComponent(labelIdDirezioneTratta)
                            .addComponent(labelInizioPosizioneEvento))
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tInfoAggiuntive, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(tTempoEvento, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(tFinePosizioneEvento, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(tInizioPosizioneEvento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tIdDirezioneTratta, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(comboDirezione, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(71, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelInizioPosizioneEvento)
                    .addComponent(tInizioPosizioneEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelFinePosizioneEvento)
                    .addComponent(tFinePosizioneEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelInfoAggiuntive)
                    .addComponent(tInfoAggiuntive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTempoEvento)
                    .addComponent(tTempoEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIdDirezioneTratta)
                    .addComponent(tIdDirezioneTratta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboDirezione, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tFinePosizioneEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tFinePosizioneEventoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tFinePosizioneEventoActionPerformed

    private void comboDirezioneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDirezioneActionPerformed
        String numberOnly = comboDirezione.getSelectedItem().toString().substring(0, 5).replaceAll("[^0-9]", "");
        tIdDirezioneTratta.setText(numberOnly);
    }//GEN-LAST:event_comboDirezioneActionPerformed

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
            java.util.logging.Logger.getLogger(InformazioneLive.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InformazioneLive.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InformazioneLive.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InformazioneLive.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InformazioneLive().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboDirezione;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labelFinePosizioneEvento;
    private javax.swing.JLabel labelIdDirezioneTratta;
    private javax.swing.JLabel labelInfoAggiuntive;
    private javax.swing.JLabel labelInizioPosizioneEvento;
    private javax.swing.JLabel labelTempoEvento;
    private javax.swing.JTextField tFinePosizioneEvento;
    private javax.swing.JTextField tIdDirezioneTratta;
    private javax.swing.JTextField tInfoAggiuntive;
    private javax.swing.JTextField tInizioPosizioneEvento;
    private javax.swing.JTextField tTempoEvento;
    private javax.swing.JTable tableInformazioneLive;
    // End of variables declaration//GEN-END:variables
}
