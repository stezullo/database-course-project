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

public class CheckpointTappaViaggio extends DataFrame {

    public CheckpointTappaViaggio() {
        initComponents();
        setModalita(APPEND_QUERY);
        setFrameTable(tableCheckpointTappaViaggio);
        setNomeTabella("CheckpointTappaViaggio");
        ComboBoxInitialize(comboIdentificativo);
        ComboBoxVeicolo(comboVeicolo);
        ComboBoxControlloElettronico(comboControlloElettronico);
        ComboBoxCasello(comboCasello);
        ComboBoxSvincolo(comboSvincolo);
    }
    
    @Override
   public void ComboBoxInitialize(JComboBox cb){
       try {
            super.ComboBoxInitialize(cb);
            Connection conn = Database.getDefaultConnection();
            dcbm = new DefaultComboBoxModel();
            comboIdentificativo.setModel(dcbm);
            query = query + "CheckPointTappaViaggio";
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
   
   private void ComboBoxCasello(JComboBox cb){
       try {
            Connection conn = Database.getDefaultConnection();
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
            comboCasello.setModel(dcbm);
            query = "SELECT Identificativo,Nome FROM Casello";
            PreparedStatement ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
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
   
   private void ComboBoxSvincolo(JComboBox cb) {
       try {
            Connection conn = Database.getDefaultConnection();
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
            comboSvincolo.setModel(dcbm);
            query = "SELECT Identificativo FROM Svincolo";
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
            tPosizioneVeicolo.setEnabled(true);
            tVelocitaIstantaneaVeicolo.setEnabled(true);
            tLuogo.setEnabled(true);
            tTempoEvento.setEnabled(true);
            tIdVeicolo.setEnabled(true);
            tIdCasello.setEnabled(true);
            tIdControlloElettronico.setEnabled(true);
            tIdSvincolo.setEnabled(true);
            comboVeicolo.setEnabled(true);
            comboCasello.setEnabled(true);
            comboControlloElettronico.setEnabled(true);
            comboSvincolo.setEnabled(true);
            break;
         case BROWSE:
            tPosizioneVeicolo.setEnabled(false);
            tVelocitaIstantaneaVeicolo.setEnabled(false);
            tLuogo.setEnabled(false);
            tTempoEvento.setEnabled(false);
            tIdVeicolo.setEnabled(false);
            tIdCasello.setEnabled(false);
            tIdControlloElettronico.setEnabled(false);
            tIdSvincolo.setEnabled(false);
            comboVeicolo.setEnabled(false);
            comboCasello.setEnabled(false);
            comboControlloElettronico.setEnabled(false);
            comboSvincolo.setEnabled(false);
            break;
         case UPDATE:
            tPosizioneVeicolo.setEnabled(true);
            tVelocitaIstantaneaVeicolo.setEnabled(true);
            tLuogo.setEnabled(true);
            tTempoEvento.setEnabled(true);
            tIdVeicolo.setEnabled(true);
            tIdCasello.setEnabled(true);
            tIdControlloElettronico.setEnabled(true);
            tIdSvincolo.setEnabled(true);
            comboCasello.setEnabled(false);
            comboVeicolo.setEnabled(false);
            comboControlloElettronico.setEnabled(false);
            comboSvincolo.setEnabled(false);
            break;
      }
   }
    
    protected void mostraErrori(SQLException e, String query, int contesto) {
      String msg;
      if (e.getErrorCode() == 1) {
         msg = "Esiste già un altro Checkpoint con lo stesso identificativo";
         JOptionPane.showMessageDialog(this, msg, "Errore",
                 JOptionPane.ERROR_MESSAGE);
      } else {
         super.mostraErrori(e, query, contesto);
      }
   }
    
     protected void premutoOK() {
      if (getPadre() != null) {
         getPadre().setProprietaPadre("CheckpointTappaViaggio", getIdText().getText());
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
         tPosizioneVeicolo.setText(rs.getString("PosizioneVeicolo"));
         tVelocitaIstantaneaVeicolo.setText(rs.getString("VelocitaIstantaneaVeicolo"));
         tLuogo.setText(rs.getString("Luogo"));
         tTempoEvento.setText(rs.getString("TempoEvento"));
         tIdVeicolo.setText(rs.getString("IdVeicolo"));
         tIdCasello.setText(rs.getString("IdCasello"));
         tIdControlloElettronico.setText(rs.getString("IdControlloElettronico"));
         tIdSvincolo.setText(rs.getString("IdSvincolo"));
         super.mostraDati();
      } catch (SQLException e) {
         mostraErrori(e);
      }
   }
     
     protected void pulisci() {
      super.pulisci();
      tPosizioneVeicolo.setText("");
         tVelocitaIstantaneaVeicolo.setText("");
         tLuogo.setText("");
         tTempoEvento.setText("");
         tIdVeicolo.setText("");
         tIdCasello.setText("");
         tIdControlloElettronico.setText("");
         tIdSvincolo.setText("");
     }
   
   protected PreparedStatement creaSelectStatement() {
      Connection con;
      PreparedStatement st;
      String id, posizioneveicolo, velocitaistantanea,luogo,tempoevento,idveicolo,idcasello,idcontrolloelettronico,idsvincolo,idarea;
      Pattern pat;
      Matcher matc;
      int k = 1;
      super.creaSelectStatement();
      id = getIdText().getText();
      posizioneveicolo = tPosizioneVeicolo.getText();
      velocitaistantanea  = tVelocitaIstantaneaVeicolo.getText();
      luogo = tLuogo.getText();
      tempoevento = tTempoEvento.getText();
      idveicolo = tIdVeicolo.getText();
      idcasello = tIdCasello.getText();
      idcontrolloelettronico = tIdControlloElettronico.getText();
      idsvincolo = tIdSvincolo.getText();
      query += " where";
      //}
      if (id.length() > 0) {
         query += " Identificativo= ? and";
      }
      if (posizioneveicolo.length() > 0) {
         if (posizioneveicolo.contains("%")) {
            query += " PosizioneVeicolo like ? and";
         } else {
            query += " PosizioneVeicolo = ? and";
         }
      }
      if (velocitaistantanea.length() > 0) {
         if (velocitaistantanea.contains("%")) {
            query += " VelocitaIstantaneaVeicolo like ? and";
         } else {
            query += " VelocitaIstantaneaVeicolo = ? and";
         }
      }
      if (luogo.length() > 0) {
         if (luogo.contains("%")) {
            query += " Luogo like ? and";
         } else {
            query += " Luogo = ? and";
         }
      }
      if (tempoevento.length() > 0) {
         if (tempoevento.contains("%")) {
            query += " TempoEvento like ? and";
         } else {
            query += " TempoEvento = ? and";
         }
      }
      if (idveicolo.length() > 0) {
         if (idveicolo.contains("%")) {
            query += " IdVeicolo like ? and";
         } else {
            query += " IdVeicolo = ? and";
         }
      }
      if (idcasello.length() > 0) {
         if (idcasello.contains("%")) {
            query += " IdCasello like ? and";
         } else {
            query += " IdCasello = ? and";
         }
      }
      if (idcontrolloelettronico.length() > 0) {
         if (idcontrolloelettronico.contains("%")) {
            query += " IdControlloElettronico like ? and";
         } else {
            query += " IdControlloElettronico = ? and";
         }
      }
      if (idsvincolo.length() > 0) {
         if (idsvincolo.contains("%")) {
            query += " IdSvincolo like ? and";
         } else {
            query += " IdSvincolo = ? and";
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
         if (posizioneveicolo.length() > 0) {
            st.setString(k++, posizioneveicolo);
         }
         if (velocitaistantanea.length() > 0) {
            st.setString(k++, velocitaistantanea);
         }
         if (luogo.length() > 0) {
            st.setString(k++, luogo);
         }
         if (tempoevento.length() > 0) {
            st.setString(k++, tempoevento);
         }
         if (idveicolo.length() > 0) {
            st.setString(k++, idveicolo);
         }
         if (idcasello.length() > 0) {
            st.setString(k++, idcasello);
         }
         if (idcontrolloelettronico.length() > 0) {
            st.setString(k++, idcontrolloelettronico);
         }
         if (idsvincolo.length() > 0) {
            st.setString(k++, idsvincolo);
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
      cmdIns = "insert into " + Database.schema + ".CheckpointTappaViaggio (Identificativo,PosizioneVeicolo,"
              + "VelocitaIstantaneaVeicolo,Luogo,TempoEvento,IdVeicolo,IdCasello,IdControlloElettronico,IdSvincolo,IdArea) values(?,?,?,?,?,?,?,?,?,?)";
      st = c.prepareStatement(cmdIns);
      st.setInt(1, Integer.decode(getIdText().getText()));
      st.setString(2, tPosizioneVeicolo.getText());
      st.setString(3, tVelocitaIstantaneaVeicolo.getText());
      st.setString(4, tLuogo.getText());
      st.setString(5, tTempoEvento.getText());
      st.setString(6, tIdVeicolo.getText());
      st.setString(7, tIdCasello.getText());
      st.setString(8, tIdControlloElettronico.getText());
      st.setString(9, tIdSvincolo.getText());
      return st;
   }
   
   protected PreparedStatement getComandoAggiornamento(Connection c)
           throws SQLException {
      String cmdUp;
      PreparedStatement st;
      cmdUp = "update " + Database.schema + ".CheckpointTappaViaggio set PosizioneVeicolo=?,VelocitaIstantaneaVeicolo=?,Luogo=?,TempoEvento=?,IdVeicolo=?,IdCasello=?,IdControlloElettronico=?,IdSvincolo=? "
              + "where Identificativo=?";
      st = c.prepareStatement(cmdUp);
      st.setInt(9, Integer.decode(getIdText().getText()));
      st.setString(1, tPosizioneVeicolo.getText());
      st.setString(2, tVelocitaIstantaneaVeicolo.getText());
      st.setString(3, tLuogo.getText());
      st.setString(4, tTempoEvento.getText());
      st.setString(5, tIdVeicolo.getText());
      st.setString(6, tIdCasello.getText());
      st.setString(7, tIdControlloElettronico.getText());
      st.setString(8, tIdSvincolo.getText());
      return st;
   }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tPosizioneVeicolo = new javax.swing.JTextField();
        tLuogo = new javax.swing.JTextField();
        tTempoEvento = new javax.swing.JTextField();
        tIdVeicolo = new javax.swing.JTextField();
        tIdCasello = new javax.swing.JTextField();
        tIdControlloElettronico = new javax.swing.JTextField();
        tIdSvincolo = new javax.swing.JTextField();
        labelPosizioneVeicolo = new javax.swing.JLabel();
        labelLuogo = new javax.swing.JLabel();
        labelTempoEvento = new javax.swing.JLabel();
        labelIdVeicolo = new javax.swing.JLabel();
        labelIdCasello = new javax.swing.JLabel();
        labelIdcontrolloElettronico = new javax.swing.JLabel();
        labelIdSvincolo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCheckpointTappaViaggio = new javax.swing.JTable();
        labelVelocitaIstantaneaVeicolo = new javax.swing.JLabel();
        tVelocitaIstantaneaVeicolo = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        comboVeicolo = new javax.swing.JComboBox<>();
        comboCasello = new javax.swing.JComboBox<>();
        comboControlloElettronico = new javax.swing.JComboBox<>();
        comboSvincolo = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rete Autostradale - Checkpoint Tappa Viaggio");

        tPosizioneVeicolo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tPosizioneVeicoloActionPerformed(evt);
            }
        });

        tLuogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tLuogoActionPerformed(evt);
            }
        });

        tTempoEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tTempoEventoActionPerformed(evt);
            }
        });

        tIdCasello.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tIdCaselloActionPerformed(evt);
            }
        });

        labelPosizioneVeicolo.setText("Posizione Veicolo");

        labelLuogo.setText("Luogo");

        labelTempoEvento.setText("Tempo Evento");

        labelIdVeicolo.setText("Id Veicolo");

        labelIdCasello.setText("Id Casello");

        labelIdcontrolloElettronico.setText("Id Controllo Elettronico");

        labelIdSvincolo.setText("Id Svincolo");

        tableCheckpointTappaViaggio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id Checkpoint", "Posizione Veicolo", "Velocita I. Veicolo", "Luogo", "Tempo Evento", "Id Veicolo", "Id Casello", "Id C. Elettronico", "Id Svincolo"
            }
        ));
        jScrollPane1.setViewportView(tableCheckpointTappaViaggio);

        labelVelocitaIstantaneaVeicolo.setText("Velocita Istantanea Veicolo");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Posizione Veicolo (KM), Velocità Istantanea Veicolo (KM/H) sono numeri reali.\nLuogo è una stringa.\nTempo Evento è una data.\nId Veicolo, Id Casello,Id Controllo Elettronico, Id Svincolo sono chiavi esterne intere.\n");
        jScrollPane2.setViewportView(jTextArea1);

        comboVeicolo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboVeicoloActionPerformed(evt);
            }
        });

        comboCasello.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCaselloActionPerformed(evt);
            }
        });

        comboControlloElettronico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboControlloElettronicoActionPerformed(evt);
            }
        });

        comboSvincolo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSvincoloActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 877, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelIdVeicolo)
                    .addComponent(labelIdcontrolloElettronico)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelVelocitaIstantaneaVeicolo)
                            .addComponent(labelLuogo)
                            .addComponent(labelIdCasello)
                            .addComponent(labelPosizioneVeicolo)
                            .addComponent(labelIdSvincolo)
                            .addComponent(labelTempoEvento))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tLuogo, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                                .addComponent(tVelocitaIstantaneaVeicolo, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                                .addComponent(tPosizioneVeicolo, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                                .addComponent(tTempoEvento))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(tIdSvincolo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                                    .addComponent(tIdControlloElettronico, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tIdCasello, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tIdVeicolo, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(comboControlloElettronico, 0, 65, Short.MAX_VALUE)
                                        .addComponent(comboSvincolo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(comboVeicolo, 0, 218, Short.MAX_VALUE)
                                    .addComponent(comboCasello, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPosizioneVeicolo)
                    .addComponent(tPosizioneVeicolo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelVelocitaIstantaneaVeicolo)
                    .addComponent(tVelocitaIstantaneaVeicolo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelLuogo)
                    .addComponent(tLuogo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTempoEvento)
                    .addComponent(tTempoEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIdVeicolo)
                    .addComponent(tIdVeicolo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboVeicolo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIdCasello)
                    .addComponent(tIdCasello, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboCasello, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIdcontrolloElettronico)
                    .addComponent(tIdControlloElettronico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboControlloElettronico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIdSvincolo)
                    .addComponent(tIdSvincolo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboSvincolo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tTempoEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tTempoEventoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tTempoEventoActionPerformed

    private void tPosizioneVeicoloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tPosizioneVeicoloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tPosizioneVeicoloActionPerformed

    private void tIdCaselloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tIdCaselloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tIdCaselloActionPerformed

    private void tLuogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tLuogoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tLuogoActionPerformed

    private void comboVeicoloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboVeicoloActionPerformed
        String numberOnly = comboVeicolo.getSelectedItem().toString().substring(0, 5).replaceAll("[^0-9]", "");
        tIdVeicolo.setText(numberOnly);
    }//GEN-LAST:event_comboVeicoloActionPerformed

    private void comboCaselloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCaselloActionPerformed
        String numberOnly = comboCasello.getSelectedItem().toString().substring(0, 5).replaceAll("[^0-9]", "");
        tIdCasello.setText(numberOnly);
    }//GEN-LAST:event_comboCaselloActionPerformed

    private void comboControlloElettronicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboControlloElettronicoActionPerformed
        tIdControlloElettronico.setText(comboControlloElettronico.getSelectedItem().toString());
    }//GEN-LAST:event_comboControlloElettronicoActionPerformed

    private void comboSvincoloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSvincoloActionPerformed
        tIdSvincolo.setText(comboSvincolo.getSelectedItem().toString());
    }//GEN-LAST:event_comboSvincoloActionPerformed

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
            java.util.logging.Logger.getLogger(CheckpointTappaViaggio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CheckpointTappaViaggio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CheckpointTappaViaggio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CheckpointTappaViaggio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CheckpointTappaViaggio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboCasello;
    private javax.swing.JComboBox<String> comboControlloElettronico;
    private javax.swing.JComboBox<String> comboSvincolo;
    private javax.swing.JComboBox<String> comboVeicolo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labelIdCasello;
    private javax.swing.JLabel labelIdSvincolo;
    private javax.swing.JLabel labelIdVeicolo;
    private javax.swing.JLabel labelIdcontrolloElettronico;
    private javax.swing.JLabel labelLuogo;
    private javax.swing.JLabel labelPosizioneVeicolo;
    private javax.swing.JLabel labelTempoEvento;
    private javax.swing.JLabel labelVelocitaIstantaneaVeicolo;
    private javax.swing.JTextField tIdCasello;
    private javax.swing.JTextField tIdControlloElettronico;
    private javax.swing.JTextField tIdSvincolo;
    private javax.swing.JTextField tIdVeicolo;
    private javax.swing.JTextField tLuogo;
    private javax.swing.JTextField tPosizioneVeicolo;
    private javax.swing.JTextField tTempoEvento;
    private javax.swing.JTextField tVelocitaIstantaneaVeicolo;
    private javax.swing.JTable tableCheckpointTappaViaggio;
    // End of variables declaration//GEN-END:variables

    
}
