package it.Barbuto.Zullo;

import java.sql.*;
import javax.swing.*;

/**
 *
 * @author Barbuto Salvatore e Zullo Stefano
 */

abstract public class DataFrame extends javax.swing.JFrame implements UsaLookup {

    public DataFrame() {
        initComponents();
        OKButton.setVisible(false);
        AnnullaButton.setVisible(false);
    }

   final static public int APPEND_QUERY = 1;
 
   final static public int BROWSE = 2;
  
   final static public int UPDATE = 3;
 
   final static public int CONTESTO_ESEGUI_QUERY = 1;
   
   private int modalita; // Stato corrente del Frame
   private int pos = 1; // Posizione del record corrente nel ResultSet.
   private DataTableModel modelloTabella; // modello della tabella di navigazione
  
   protected ResultSet rs;
   protected DefaultComboBoxModel dcbm;    
   protected String query;
   private String nomeTabella; // nome della tabella (o vista)
   private javax.swing.JTable tabFrameTable;
   private UsaLookup padre = null; // form che usa quello corrente come Lookup

   /**
    * Imposta il Puntatore al Form che usa quello corrente cone Lookup.
    * 
    * @param p la finestra che ha chiamato quella corrente
    */
   public void ComboBoxInitialize(JComboBox cb){
        dcbm = new DefaultComboBoxModel();
        comboIdentificativo.setModel(dcbm);
        query = "SELECT Identificativo FROM "+Database.schema+".";
   }
   
   public void setPadre(UsaLookup p) {
      padre = p;
      if (padre == null) {
         OKButton.setVisible(false);
      } else {
         OKButton.setVisible(true);
      }
   }

   /**
    * Restituisce il puntatore al Form che usa quello corrente come Lookup.
    *
    * @return la finestra che ha chiamato quella corrente
    */
   public UsaLookup getPadre() {
      return padre;
   }

   DataTableModel getModelloTabella() {
      return modelloTabella;
   }

   void setModelloTabella(DataTableModel gmt) {
      modelloTabella = gmt;
   }

   String getQuery() {
      return query;
   }

   void setQuery(String query) {
      this.query = query;
   }

   String getNomeTabella() {
      return nomeTabella;
   }

   void setNomeTabella(String nomeTabella) {
      this.nomeTabella = nomeTabella;
   }
   
 public void setProprietaPadre(String proprieta, String valore) {
      //non puo' essere abstract, per alcuni frame non e' necessario
   }
 protected void setFrameTable(JTable t) {
      tabFrameTable = t;
      modelloTabella = new DataTableModel();
      tabFrameTable.setModel(modelloTabella);
      tabFrameTable.getSelectionModel().setSelectionMode(
              ListSelectionModel.SINGLE_SELECTION);
      tabFrameTable.addMouseListener(new java.awt.event.MouseAdapter() {

         public void mouseReleased(java.awt.event.MouseEvent evt) {
                  selezioneTabellaCambiata();
         }
      });

      tabFrameTable.getSelectionModel().addListSelectionListener(
              new javax.swing.event.ListSelectionListener() {

                 public void valueChanged(
                         javax.swing.event.ListSelectionEvent e) {
                    selezioneTabellaCambiata();
                 }
              });
   }
 
 private void selezioneTabellaCambiata() {
      try {
         rs.absolute(
                 tabFrameTable.getSelectionModel().getMinSelectionIndex() + 1);
         mostraDati();
      } catch (SQLException e) {
         mostraErrori(e);
      } catch (Exception a) {
      }
   }
 
 protected javax.swing.JTextField getIdText() {
      return IdText;
   }
 
 public void setModalita(int modo) {
      modalita = modo;
      switch (modo) {
         case APPEND_QUERY:
            NuovoButton.setEnabled(true);
            ApriButton.setEnabled(false);
            SalvaButton.setEnabled(true);
            CercaButton.setEnabled(true);
            AnnullaButton.setVisible(false);
            EliminaButton.setEnabled(false);
            if (tabFrameTable != null) {
               tabFrameTable.setEnabled(false);
            }
            IdText.setEnabled(true);
            comboIdentificativo.setEnabled(true);
            OKButton.setEnabled(false);
            break;
         case BROWSE:
            NuovoButton.setEnabled(true);
            ApriButton.setEnabled(true);
            SalvaButton.setEnabled(false);
            CercaButton.setEnabled(false);
            AnnullaButton.setVisible(false);
            EliminaButton.setEnabled(false);
            if (tabFrameTable != null) {
               tabFrameTable.setEnabled(true);
            }
            IdText.setEnabled(false);
            comboIdentificativo.setEnabled(false);
            OKButton.setEnabled(true);
            break;
         case UPDATE:
            NuovoButton.setEnabled(true);
            ApriButton.setEnabled(false);
            SalvaButton.setEnabled(true);
            CercaButton.setEnabled(false);
            AnnullaButton.setVisible(false);
            EliminaButton.setEnabled(true);
            if (tabFrameTable != null) {
               tabFrameTable.setEnabled(false);
            }
            IdText.setEnabled(false);
            comboIdentificativo.setEnabled(false);
            OKButton.setEnabled(false);
            break;
      }
   }
 
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
   
   /**protected void impostaId() {
      String identificativo;
      identificativo = Database.leggiValore("select nvl(max(codice)+1,1) from "
              + Database.schema + "." + this.nomeTabella).toString();
      IdText.setText(identificativo);
   }*/
   
   protected void premutoOK() {
      //non può essere abstract, per alcuni frame non è necessario
   }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NuovoButton = new javax.swing.JButton();
        ApriButton = new javax.swing.JButton();
        SalvaButton = new javax.swing.JButton();
        EliminaButton = new javax.swing.JButton();
        CercaButton = new javax.swing.JButton();
        IdLb = new javax.swing.JLabel();
        IdText = new javax.swing.JTextField();
        OKButton = new javax.swing.JButton();
        AnnullaButton = new javax.swing.JButton();
        bIndietro = new javax.swing.JButton();
        comboIdentificativo = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        NuovoButton.setText("Pulisci");
        NuovoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuovoButtonActionPerformed(evt);
            }
        });

        ApriButton.setText("Apri");
        ApriButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApriButtonActionPerformed(evt);
            }
        });

        SalvaButton.setText("Inserisci");
        SalvaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalvaButtonActionPerformed(evt);
            }
        });

        EliminaButton.setText("Elimina");
        EliminaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminaButtonActionPerformed(evt);
            }
        });

        CercaButton.setText("Cerca");
        CercaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CercaButtonActionPerformed(evt);
            }
        });

        IdLb.setText("Identificativo");

        IdText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IdTextActionPerformed(evt);
            }
        });

        OKButton.setText("OK");
        OKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKButtonActionPerformed(evt);
            }
        });

        AnnullaButton.setText("Annulla");
        AnnullaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnnullaButtonActionPerformed(evt);
            }
        });

        bIndietro.setText("Torna al Menù");
        bIndietro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bIndietroActionPerformed(evt);
            }
        });

        comboIdentificativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboIdentificativoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(IdLb)
                        .addGap(33, 33, 33)
                        .addComponent(IdText, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(comboIdentificativo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 405, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(NuovoButton, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                            .addComponent(ApriButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CercaButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(OKButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AnnullaButton))
                            .addComponent(bIndietro, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(SalvaButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(EliminaButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NuovoButton)
                    .addComponent(IdLb)
                    .addComponent(IdText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboIdentificativo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SalvaButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EliminaButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ApriButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CercaButton)
                .addGap(23, 23, 23)
                .addComponent(bIndietro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OKButton)
                    .addComponent(AnnullaButton))
                .addContainerGap(348, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NuovoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuovoButtonActionPerformed
        pulisci();
       pos = 1;
       try {
          if (rs != null) {
             rs.close();
          }
          rs = null;
          modelloTabella.setRS(rs);
          setModalita(APPEND_QUERY);
       } catch (SQLException e) {
          mostraErrori(e);
       }
    }//GEN-LAST:event_NuovoButtonActionPerformed

    private void ApriButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApriButtonActionPerformed
         setModalita(UPDATE);
    }//GEN-LAST:event_ApriButtonActionPerformed

    private void SalvaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalvaButtonActionPerformed
        PreparedStatement st;
       boolean ret;
       Connection c = null;

       /**if (this.IdText.getText().trim().length() == 0) {
          impostaId();
       }*/
       
       try {
          c = Database.nuovaConnessione();
          if (modalita == APPEND_QUERY) {
             st = getComandoInserimento(c);
          } else {
             st = getComandoAggiornamento(c);
          }
          c.setAutoCommit(false);
          ret = st.executeUpdate() >= 0;
          if (ret) {
             ret = eseguiSalva(c);
          }
          if (ret) {
             c.commit();
          } else {
             c.rollback();
          }
          c.setAutoCommit(true);
       } catch (SQLException e) {
          mostraErrori(e);
          ret = false;
       }
       if (ret) {
          if (modalita == APPEND_QUERY) {
             CercaButtonActionPerformed(evt);
          } else {
             eseguiQuery();
          }
       } else {
          try {
             c.rollback();
             c.setAutoCommit(true);
          } catch (SQLException e) {
             mostraErrori(e);
             //ret = false;
          }
       }
    }//GEN-LAST:event_SalvaButtonActionPerformed

    private void EliminaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminaButtonActionPerformed
       String cmd;
       cmd = "delete from " + Database.schema + "." + nomeTabella
               + " where identificativo=?";
       try {
          if (rs.isLast()) {
             pos--;   
          }
          Connection conn = Database.getDefaultConnection();
          PreparedStatement st = conn.prepareStatement(cmd);
          st.setString(1, IdText.getText());
          st.executeUpdate();
       } catch (SQLException e) {
       }
       pulisci();
       eseguiQuery();
    }//GEN-LAST:event_EliminaButtonActionPerformed

    private void CercaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CercaButtonActionPerformed
        eseguiQuery();
    }//GEN-LAST:event_CercaButtonActionPerformed

    private void IdTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IdTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IdTextActionPerformed

    private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKButtonActionPerformed
        premutoOK();
    }//GEN-LAST:event_OKButtonActionPerformed

    private void AnnullaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnnullaButtonActionPerformed
        eseguiQuery();
       if (getPadre() != null) {
          try {
             rs.close();
          } catch (SQLException e) {
             mostraErrori(e);
          } finally {
             dispose();
          }
       }
    }//GEN-LAST:event_AnnullaButtonActionPerformed

    private void bIndietroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bIndietroActionPerformed
       this.setVisible(false);
       new Principale(0);
    }//GEN-LAST:event_bIndietroActionPerformed

    private void comboIdentificativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboIdentificativoActionPerformed
        IdText.setText(comboIdentificativo.getSelectedItem().toString());
    }//GEN-LAST:event_comboIdentificativoActionPerformed

    protected boolean eseguiSalva(Connection con) {
      return true;
   }

     private void eseguiQuery() {
      PreparedStatement st;
      try {
         st = creaSelectStatement();
         rs = st.executeQuery();
         modelloTabella.setRS(rs);
         rs.absolute(pos);
         mostraDati();
         setModalita(BROWSE);
      } catch (SQLException e) {
      } catch (java.lang.NullPointerException e) {
      }
      catch(NumberFormatException nfe)
      {
          JOptionPane.showMessageDialog(this,
           "Non è possibile inserire quest'Identificativo.",
           "Errore in inserimento Identificativo",
           JOptionPane.ERROR_MESSAGE);
      }
   }
     
     protected void pulisci() {
      IdText.setText("");
   }
    
    abstract protected PreparedStatement getComandoInserimento(Connection c)
           throws SQLException;
    
    abstract protected PreparedStatement getComandoAggiornamento(Connection c)
           throws SQLException;

    
    protected void mostraDati() {
      try {
         IdText.setText(rs.getString("IDENTIFICATIVO"));
         pos = rs.getRow();
         //tabFrameTable.getSelectionModel().setSelectionInterval(pos-1,pos-1);
         tabFrameTable.setRowSelectionInterval(pos - 1, pos - 1);
      } catch (SQLException e) {
         mostraErrori(e);
      }
   }
    
    protected PreparedStatement creaSelectStatement() {
      query = "select * from " + Database.schema + "." + nomeTabella + " ";
      return null;
   }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AnnullaButton;
    private javax.swing.JButton ApriButton;
    private javax.swing.JButton CercaButton;
    private javax.swing.JButton EliminaButton;
    private javax.swing.JLabel IdLb;
    private javax.swing.JTextField IdText;
    private javax.swing.JButton NuovoButton;
    private javax.swing.JButton OKButton;
    private javax.swing.JButton SalvaButton;
    private javax.swing.JButton bIndietro;
    protected javax.swing.JComboBox<String> comboIdentificativo;
    // End of variables declaration//GEN-END:variables
}
