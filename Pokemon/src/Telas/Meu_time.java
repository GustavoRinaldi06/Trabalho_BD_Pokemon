/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Telas;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
/**
 *
 * @author Gustavo Rinaldi
 */
public class Meu_time extends javax.swing.JFrame {

    char estado = 'n';
    Connection conn;
    String nome_time;
    
    public Meu_time(Connection conexao, String nome_do_time) {
        nome_time = nome_do_time;
        conn = conexao;
        initComponents();
        adicionarTabelaPokemon();
        Neutro();
        conn = conexao;
        txt_apelido.setEnabled(false);
        bttn_escolhe_poke.setEnabled(false);
        bttn_ok.setEnabled(false);
        bttn_confirm.setEnabled(false);
    }
     
    public void adicionarTabelaPokemon(){
        DefaultTableModel modelo = new DefaultTableModel(new Object[] {"Pokemon", "Apelido", "Item", "Habilidade", "Atq1", "Atq2", "Atq3", "Atq4"}, 0);
        // iterar pelos valors da tabela do seu time
        String query = "SELECT nome, apelido, item, habilidade, ataque1, ataque2, ataque3, ataque4, posicao FROM membros_tabela WHERE equipe = '"+nome_time+"' AND dono = session_user;";
        try{
            var cursor = conn.createStatement();
            var retorno = cursor.executeQuery(query);
            while (retorno.next()){
                Object linha[] = new Object[]{retorno.getString("nome"), retorno.getString("apelido"), retorno.getString("item"), retorno.getString("habilidade"), retorno.getString("ataque1"), retorno.getString("ataque2"), retorno.getString("ataque3"), retorno.getString("ataque4") };
                modelo.addRow(linha);
            }
        } catch (SQLException ex){
            System.err.println("carregar tabela: "+ex.getMessage());
        }
        
        tblMeuTime.setModel(modelo);
    }
    public void Neutro(){
        box_pokemons.removeAllItems();
        box_pokemons.addItem("--");
        box_pokemons.setEnabled(false);
        
        hab_box.removeAllItems();
        hab_box.addItem("--");
        hab_box.setEnabled(false);
        
        box_pokemon.removeAllItems();
        box_pokemon.addItem("--");
        box_pokemon.setEnabled(false);
        
        box_I.removeAllItems();
        box_I.addItem("--");
        box_I.setEnabled(false);
        
        box_a1.removeAllItems();
        box_a1.addItem("--");
        box_a1.setEnabled(false);
        
        box_a2.removeAllItems();
        box_a2.addItem("--");
        box_a2.setEnabled(false);
        
        box_a3.removeAllItems();
        box_a3.addItem("--");
        box_a3.setEnabled(false);
        
        box_a4.removeAllItems();
        box_a4.addItem("--");
        box_a4.setEnabled(false);
    }
    // mesma lógica dos outros, utiliza valores do BD
    public void CarregarPokedex(){
        //carrega pokedex
        box_pokemons.removeAllItems();
        String query = "SELECT nome FROM pokemons WHERE selecionavel = true;";
        try{
            var cursor = conn.createStatement();
            var retorno = cursor.executeQuery(query);
            while (retorno.next()){
                box_pokemons.addItem(retorno.getString("nome"));
            }
        } catch (SQLException ex){
            System.err.println("carregar pokedex: "+ex.getMessage());
        }
    }
     public void CarregarHab(String nome){
        //carrega habilidades
        hab_box.removeAllItems();
        hab_box.addItem("");
        String query = "SELECT pode_ter_habilidade.nome_habilidade FROM pode_ter_habilidade, pokemons WHERE pokemons.nome = '"+nome+"' AND pokemons.numero = pode_ter_habilidade.num_poke AND pokemons.forma = pode_ter_habilidade.num_forma;";
        try{
            var cursor = conn.createStatement();
            var retorno = cursor.executeQuery(query);
            while (retorno.next()){
                hab_box.addItem(retorno.getString("nome_habilidade"));
            }
        } catch (SQLException ex){
            System.err.println("carregar habilidades: "+ex.getMessage());
        }
    }
    public void CarregarMeutime(){
        box_pokemon.removeAllItems();
        box_pokemon.addItem("");
        String query = "SELECT nome FROM membros_tabela WHERE equipe = '"+nome_time+"' AND dono = session_user;";
        try{
            var cursor = conn.createStatement();
            var retorno = cursor.executeQuery(query);
            while (retorno.next()){
                box_pokemon.addItem(retorno.getString("nome"));
            }
        } catch (SQLException ex){
            System.err.println("carregar meu time: "+ex.getMessage());
        }
    }
    public void CarregarItens(){
        box_I.removeAllItems();
        box_I.addItem("");
        String query = "SELECT nome FROM itens;";
        try{
            var cursor = conn.createStatement();
            var retorno = cursor.executeQuery(query);
            while (retorno.next()){
                box_I.addItem(retorno.getString("nome"));
            }
        } catch (SQLException ex){
            System.err.println("carregar itens: "+ex.getMessage());
        }
    }
    public void CarregarAtaques1(String nome){
        box_a1.removeAllItems();
        box_a1.addItem("");
        String query = "SELECT aprende_ataque.nome_ataque FROM aprende_ataque, pokemons WHERE pokemons.nome = '"+nome+"' AND pokemons.numero = aprende_ataque.num_poke AND pokemons.forma = aprende_ataque.num_forma;";
        try{
            var cursor = conn.createStatement();
            var retorno = cursor.executeQuery(query);
            while (retorno.next()){
                box_a1.addItem(retorno.getString("nome_ataque"));
            }
        } catch (SQLException ex){
            System.err.println("carregar ataques: "+ex.getMessage());
        }
    }
    public void CarregarAtaques2(String nome){
        box_a2.removeAllItems();
        box_a2.addItem("");
        String query = "SELECT aprende_ataque.nome_ataque FROM aprende_ataque, pokemons WHERE pokemons.nome = '"+nome+"' AND pokemons.numero = aprende_ataque.num_poke AND pokemons.forma = aprende_ataque.num_forma;";
        try{
            var cursor = conn.createStatement();
            var retorno = cursor.executeQuery(query);
            while (retorno.next()){
                box_a2.addItem(retorno.getString("nome_ataque"));
            }
        } catch (SQLException ex){
            System.err.println("carregar ataques: "+ex.getMessage());
        }
    }
    public void CarregarAtaques3(String nome){
        box_a3.removeAllItems();
        box_a3.addItem("");
        String query = "SELECT aprende_ataque.nome_ataque FROM aprende_ataque, pokemons WHERE pokemons.nome = '"+nome+"' AND pokemons.numero = aprende_ataque.num_poke AND pokemons.forma = aprende_ataque.num_forma;";
        try{
            var cursor = conn.createStatement();
            var retorno = cursor.executeQuery(query);
            while (retorno.next()){
                box_a3.addItem(retorno.getString("nome_ataque"));
            }
        } catch (SQLException ex){
            System.err.println("carregar ataques: "+ex.getMessage());
        }
    }
    public void CarregarAtaques4(String nome){
        box_a4.removeAllItems();
        box_a4.addItem("");
        String query = "SELECT aprende_ataque.nome_ataque FROM aprende_ataque, pokemons WHERE pokemons.nome = '"+nome+"' AND pokemons.numero = aprende_ataque.num_poke AND pokemons.forma = aprende_ataque.num_forma;";
        try{
            var cursor = conn.createStatement();
            var retorno = cursor.executeQuery(query);
            while (retorno.next()){
                box_a4.addItem(retorno.getString("nome_ataque"));
            }
        } catch (SQLException ex){
            System.err.println("carregar ataques: "+ex.getMessage());
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        bttn_add = new javax.swing.JButton();
        bttn_ed = new javax.swing.JButton();
        bttn_del = new javax.swing.JButton();
        box_pokemon = new javax.swing.JComboBox<>();
        bttn_ok = new javax.swing.JButton();
        box_I = new javax.swing.JComboBox<>();
        box_a1 = new javax.swing.JComboBox<>();
        box_a2 = new javax.swing.JComboBox<>();
        box_a3 = new javax.swing.JComboBox<>();
        box_a4 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMeuTime = new javax.swing.JTable();
        bttn_confirm = new javax.swing.JButton();
        txt_apelido = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        box_pokemons = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        hab_box = new javax.swing.JComboBox<>();
        bttn_escolhe_poke = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        jLabel9.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        jLabel9.setText("Item:");

        setTitle("Meu time");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Imagens/mini_pokebola_minimalista.png")).getImage());

        jPanel1.setBackground(new java.awt.Color(98, 7, 7));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/pokebola_minimalista.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("Showcard Gothic", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Meu time pokemon");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addComponent(jLabel2))
        );

        bttn_add.setBackground(new java.awt.Color(204, 204, 204));
        bttn_add.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        bttn_add.setText("Adicionar");
        bttn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttn_addActionPerformed(evt);
            }
        });

        bttn_ed.setBackground(new java.awt.Color(204, 204, 204));
        bttn_ed.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        bttn_ed.setText("Editar");
        bttn_ed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttn_edActionPerformed(evt);
            }
        });

        bttn_del.setBackground(new java.awt.Color(204, 204, 204));
        bttn_del.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        bttn_del.setText("Remover");
        bttn_del.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttn_delActionPerformed(evt);
            }
        });

        box_pokemon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        bttn_ok.setBackground(new java.awt.Color(204, 204, 204));
        bttn_ok.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        bttn_ok.setText("OK");
        bttn_ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttn_okActionPerformed(evt);
            }
        });

        box_I.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        box_I.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                box_IActionPerformed(evt);
            }
        });

        box_a1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        box_a1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                box_a1ActionPerformed(evt);
            }
        });

        box_a2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        box_a3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        box_a3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                box_a3ActionPerformed(evt);
            }
        });

        box_a4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tblMeuTime.setBackground(new java.awt.Color(0, 0, 0));
        tblMeuTime.setForeground(new java.awt.Color(255, 255, 255));
        tblMeuTime.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Pokemon", "Apelido", "Item", "Habilidade", "Ataque 1", "Ataque 2", "Ataque 3", "Ataque 4"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblMeuTime.setEnabled(false);
        tblMeuTime.setShowGrid(true);
        jScrollPane1.setViewportView(tblMeuTime);

        bttn_confirm.setBackground(new java.awt.Color(204, 204, 204));
        bttn_confirm.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        bttn_confirm.setText("Confirmar");
        bttn_confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttn_confirmActionPerformed(evt);
            }
        });

        txt_apelido.setBackground(new java.awt.Color(86, 86, 86));
        txt_apelido.setToolTipText("Coloque um apelido");
        txt_apelido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_apelidoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Apelido:");

        box_pokemons.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        box_pokemons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                box_pokemonsActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Pokemon:");

        jLabel5.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Item:");

        jLabel6.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Ataque 1:");

        jLabel7.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Ataque 2:");

        jLabel8.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Ataque 3:");

        jLabel10.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Ataque 4:");

        jLabel11.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Integrante:");

        jLabel12.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Habilidade:");

        hab_box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        bttn_escolhe_poke.setBackground(new java.awt.Color(204, 204, 204));
        bttn_escolhe_poke.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        bttn_escolhe_poke.setText("Escolher Pokemon");
        bttn_escolhe_poke.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttn_escolhe_pokeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(box_a1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(box_a2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(box_a3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(box_a4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(box_I, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(hab_box, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_apelido, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(box_pokemons, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bttn_escolhe_poke))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(946, 946, 946)
                        .addComponent(bttn_confirm))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1071, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(bttn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bttn_ed, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(bttn_del)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(box_pokemon, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bttn_ok)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bttn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bttn_ed, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bttn_del, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(box_pokemon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bttn_ok)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_apelido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(box_pokemons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bttn_escolhe_poke))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(box_I, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hab_box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(box_a1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(box_a2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(box_a3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(box_a4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bttn_confirm)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bttn_delActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttn_delActionPerformed
        CarregarMeutime();
        box_pokemon.setEnabled(true);
        estado = 'd';
        bttn_ok.setEnabled(true);
        bttn_add.setEnabled(false);
        bttn_ed.setEnabled(false);
        bttn_del.setEnabled(false);
    }//GEN-LAST:event_bttn_delActionPerformed

    private void bttn_edActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttn_edActionPerformed
        CarregarMeutime();
        box_pokemon.setEnabled(true);
        estado = 'e';
        bttn_ok.setEnabled(true);
        bttn_add.setEnabled(false);
        bttn_del.setEnabled(false);
        bttn_ed.setEnabled(false);
    }//GEN-LAST:event_bttn_edActionPerformed

    private void bttn_okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttn_okActionPerformed
        if(estado == 'd'){
            //remover o valor da caixa:
            //box_pokemon.getSelectedItem()
            String comando = "CALL delete_pokemon_time(CAST('"+nome_time+"' AS VARCHAR),CAST("+box_pokemon.getSelectedIndex()+" AS SMALLINT));";
            try{
                var statement = conn.createStatement();
                var result = statement.execute(comando);
                JOptionPane.showMessageDialog(null, "Pokemon removido com sucesso.", "Tchau pokemon...", JOptionPane.PLAIN_MESSAGE);
                box_pokemon.setEnabled(false);
                bttn_ok.setEnabled(false);
                bttn_add.setEnabled(true);
                bttn_del.setEnabled(true);
                bttn_ed.setEnabled(true);
                Neutro();
                adicionarTabelaPokemon();
            } catch (SQLException ex){
                JOptionPane.showMessageDialog(null, "Pokemon não foi removido com sucesso.", "Também não entendi", JOptionPane.PLAIN_MESSAGE);
                System.err.println("bttn ok estado d: "+ex.getMessage());
            }
           
           
        }else if(estado == 'e'){
            //pokemon do time escolhido na box_pokemon
            //popular caixas com base nisso.
            //guardar info dos estados?
            try{
                String query = "SELECT nome, apelido, item, habilidade, ataque1, ataque2, ataque3, ataque4 FROM membros_tabela WHERE dono = session_user AND equipe = '"+nome_time+"' AND posicao = "+box_pokemon.getSelectedIndex()+";";
                var statement = conn.createStatement();
                var result = statement.executeQuery(query);
                result.next();
                var nome = result.getString("nome");
                box_pokemons.removeAllItems();
                box_pokemons.addItem("--");
                box_pokemons.setEnabled(false);
                //----colocar apelido V
                txt_apelido.setText(result.getString("apelido"));
                txt_apelido.setEnabled(true);
                CarregarHab(nome);
                CarregarItens();
                CarregarAtaques1(nome);
                CarregarAtaques2(nome);
                CarregarAtaques3(nome);
                CarregarAtaques4(nome);
                hab_box.setEnabled(true);
                box_I.setEnabled(true);
                box_a1.setEnabled(true);
                box_a2.setEnabled(true);
                box_a3.setEnabled(true);
                box_a4.setEnabled(true);
                bttn_ok.setEnabled(false);
                bttn_confirm.setEnabled(true);
            } catch(SQLException ex) {
                System.err.println("bttn ok estado e: "+ex.getMessage());
            }

        }
    }//GEN-LAST:event_bttn_okActionPerformed

    private void bttn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttn_addActionPerformed
        estado = 'a';
        bttn_ed.setEnabled(false);
        bttn_del.setEnabled(false);
        bttn_add.setEnabled(false);
        CarregarPokedex();
        box_pokemons.setEnabled(true);
        bttn_escolhe_poke.setEnabled(true);
    }//GEN-LAST:event_bttn_addActionPerformed

    private void bttn_confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttn_confirmActionPerformed
        // se puder adicionar, adiciona:
        if(estado == 'a'){
            // adiciona no banco de dados
            //item habilidade ataques 1-4
            try{
                String query = "SELECT numero,forma FROM pokemons WHERE nome= '"+box_pokemons.getSelectedItem()+"';";
                var stmtQuery = conn.createStatement();
                var resultP = stmtQuery.executeQuery(query);
                resultP.next();
                String num = resultP.getString("numero");
                String forma = resultP.getString("forma");
                String comando = "CALL insere_pokemon_time(CAST('"+nome_time+"' AS VARCHAR), CAST("+forma+" AS SMALLINT), CAST("+num+" AS SMALLINT));";
                var statement = conn.createStatement();
                var resultS = statement.execute(comando);
                var numeroAdd = statement.executeQuery("SELECT count(pos_equipe) AS num FROM membros_de_time WHERE nome_dono = session_user AND nome_equipe = '"+nome_time+"';");
                numeroAdd.next();
                int pos = numeroAdd.getInt("num");
                
                //apelido
                //item
                if (!(box_I.getSelectedItem().equals(""))){
                    comando = "UPDATE membros_de_time SET nome_item = '"+box_I.getSelectedItem()+"' WHERE nome_equipe = '"+nome_time+"' AND nome_dono = session_user AND pos_equipe = "+pos+";";
                    statement.execute(comando);
                }
                //habilidade
                if (!(hab_box.getSelectedItem().equals(""))){
                comando = "UPDATE membros_de_time SET nome_habilidade = '"+hab_box.getSelectedItem()+"' WHERE nome_equipe = '"+nome_time+"' AND nome_dono = session_user AND pos_equipe = "+pos+";";
                statement.execute(comando);
                }
                //a1
                if (!(box_a1.getSelectedItem().equals(""))){
                comando = "CALL edit_ataque_pokemon(CAST('"+nome_time+"' AS VARCHAR),CAST("+pos+" AS SMALLINT),CAST(1 AS SMALLINT),CAST('"+box_a1.getSelectedItem()+"' AS VARCHAR));";
                statement.execute(comando);
                }
                //a2
                if (!(box_a2.getSelectedItem().equals(""))){
                comando = "CALL edit_ataque_pokemon(CAST('"+nome_time+"' AS VARCHAR),CAST("+pos+" AS SMALLINT),CAST(2 AS SMALLINT),CAST('"+box_a2.getSelectedItem()+"' AS VARCHAR));";
                statement.execute(comando);
                }
                //a3
                if (!(box_a3.getSelectedItem().equals(""))){
                comando = "CALL edit_ataque_pokemon(CAST('"+nome_time+"' AS VARCHAR),CAST("+pos+" AS SMALLINT),CAST(3 AS SMALLINT),CAST('"+box_a3.getSelectedItem()+"' AS VARCHAR));";
                statement.execute(comando);
                }
                //a4
                if (!(box_a4.getSelectedItem().equals(""))){
                comando = "CALL edit_ataque_pokemon(CAST('"+nome_time+"' AS VARCHAR),CAST("+pos+" AS SMALLINT),CAST(4 AS SMALLINT),CAST('"+box_a4.getSelectedItem()+"' AS VARCHAR));";
                statement.execute(comando);
                }
                
                JOptionPane.showMessageDialog(null, "Pokemon adicionado com sucesso!", "Mais um integrante da equipe", JOptionPane.PLAIN_MESSAGE);
                Neutro();
                adicionarTabelaPokemon();
                bttn_confirm.setEnabled(false);
                bttn_add.setEnabled(true);
                bttn_del.setEnabled(true);
                bttn_ed.setEnabled(true);
                txt_apelido.setEnabled(false);
                txt_apelido.setText("");
            } catch (SQLException ex){
                JOptionPane.showMessageDialog(null, "Pokemon não foi adicionado com sucesso.", "Também não entendi", JOptionPane.PLAIN_MESSAGE);
                System.err.println("bttn confirm estado a ="+ex.getMessage());
            }
            
        }else{
            //ver o que foi alterado e editar
            // atk1 atk2 atk3 atk4 apelido item habilidade
            try{
                var statement = conn.createStatement();
                //apelido
                if (!(txt_apelido.getText().equals(""))){
                    String comando = "UPDATE membros_de_time SET apelido = '"+txt_apelido.getText()+"' WHERE nome_equipe = '"+nome_time+"' AND nome_dono = session_user AND pos_equipe = "+box_pokemon.getSelectedIndex()+";";
                    statement.execute(comando);
                }
                //item
                if (!(box_I.getSelectedItem().equals(""))){
                    String comando = "UPDATE membros_de_time SET nome_item = '"+box_I.getSelectedItem()+"' WHERE nome_equipe = '"+nome_time+"' AND nome_dono = session_user AND pos_equipe = "+box_pokemon.getSelectedIndex()+";";
                    statement.execute(comando);
                }
                //habilidade
                if (!(hab_box.getSelectedItem().equals(""))){
                    String comando = "UPDATE membros_de_time SET nome_habilidade = '"+hab_box.getSelectedItem()+"' WHERE nome_equipe = '"+nome_time+"' AND nome_dono = session_user AND pos_equipe = "+box_pokemon.getSelectedIndex()+";";
                    statement.execute(comando);
                }
                //a1
                if (!(box_a1.getSelectedItem().equals(""))){
                    String comando = "CALL edit_ataque_pokemon(CAST('"+nome_time+"' AS VARCHAR),CAST("+box_pokemon.getSelectedIndex()+"AS SMALLINT),CAST(1 AS SMALLINT),CAST('"+box_a1.getSelectedItem()+"' AS VARCHAR));";
                    statement.execute(comando);
                }
                //a2
                if (!(box_a2.getSelectedItem().equals(""))){
                    String comando = "CALL edit_ataque_pokemon(CAST('"+nome_time+"' AS VARCHAR),CAST("+box_pokemon.getSelectedIndex()+"AS SMALLINT),CAST(2 AS SMALLINT),CAST('"+box_a2.getSelectedItem()+"' AS VARCHAR));";
                    statement.execute(comando);
                }
                //a3
                if (!(box_a3.getSelectedItem().equals(""))){
                    String comando = "CALL edit_ataque_pokemon(CAST('"+nome_time+"' AS VARCHAR),CAST("+box_pokemon.getSelectedIndex()+"AS SMALLINT),CAST(3 AS SMALLINT),CAST('"+box_a3.getSelectedItem()+"' AS VARCHAR));";
                    statement.execute(comando);
                }
                //a4
                if (!(box_a4.getSelectedItem().equals(""))){
                    String comando = "CALL edit_ataque_pokemon(CAST('"+nome_time+"' AS VARCHAR),CAST("+box_pokemon.getSelectedIndex()+"AS SMALLINT),CAST(4 AS SMALLINT),CAST('"+box_a4.getSelectedItem()+"' AS VARCHAR));";
                    statement.execute(comando);
                }
                
                Neutro();
                adicionarTabelaPokemon();
                bttn_confirm.setEnabled(false);
                bttn_add.setEnabled(true);
                bttn_del.setEnabled(true);
                bttn_ed.setEnabled(true);
                txt_apelido.setEnabled(false);
                txt_apelido.setText("");
                JOptionPane.showMessageDialog(null, "Pokemon atualizado com sucesso.", "Que evolução!", JOptionPane.PLAIN_MESSAGE);
            } catch (SQLException ex){
                JOptionPane.showMessageDialog(null, "Pokemon não foi atualizado com sucesso.", "Deu erro.", JOptionPane.PLAIN_MESSAGE);
                System.err.println("bttn confirm else: "+ex.getMessage());
            }
            
        }
    }//GEN-LAST:event_bttn_confirmActionPerformed

    private void txt_apelidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_apelidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_apelidoActionPerformed

    private void box_pokemonsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_box_pokemonsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_box_pokemonsActionPerformed

    private void box_IActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_box_IActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_box_IActionPerformed

    private void box_a1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_box_a1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_box_a1ActionPerformed

    private void box_a3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_box_a3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_box_a3ActionPerformed

    private void bttn_escolhe_pokeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttn_escolhe_pokeActionPerformed
         bttn_escolhe_poke.setEnabled(false);
         box_pokemons.setEnabled(false);
        
        
        txt_apelido.setEnabled(true);
        CarregarHab(box_pokemons.getSelectedItem().toString());
        CarregarItens();
        CarregarAtaques1(box_pokemons.getSelectedItem().toString());
        CarregarAtaques2(box_pokemons.getSelectedItem().toString());
        CarregarAtaques3(box_pokemons.getSelectedItem().toString());
        CarregarAtaques4(box_pokemons.getSelectedItem().toString());
        hab_box.setEnabled(true);
        box_I.setEnabled(true);
        box_a1.setEnabled(true);
        box_a2.setEnabled(true);
        box_a3.setEnabled(true);
        box_a4.setEnabled(true);
        bttn_confirm.setEnabled(true);
    }//GEN-LAST:event_bttn_escolhe_pokeActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> box_I;
    private javax.swing.JComboBox<String> box_a1;
    private javax.swing.JComboBox<String> box_a2;
    private javax.swing.JComboBox<String> box_a3;
    private javax.swing.JComboBox<String> box_a4;
    private javax.swing.JComboBox<String> box_pokemon;
    private javax.swing.JComboBox<String> box_pokemons;
    private javax.swing.JButton bttn_add;
    private javax.swing.JButton bttn_confirm;
    private javax.swing.JButton bttn_del;
    private javax.swing.JButton bttn_ed;
    private javax.swing.JButton bttn_escolhe_poke;
    private javax.swing.JButton bttn_ok;
    private javax.swing.JComboBox<String> hab_box;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblMeuTime;
    private javax.swing.JTextField txt_apelido;
    // End of variables declaration//GEN-END:variables
}
