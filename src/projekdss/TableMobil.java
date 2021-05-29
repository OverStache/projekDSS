/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekdss;


import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Ivan
 */
final class TableMobil extends javax.swing.JFrame {

    Connection conn;
    Statement st;
    ResultSet rs;
    String sql,sql1;
    JFrame frame = new JFrame();
    DecimalFormat df = new DecimalFormat("0.00");
    double r1,r2,r3,r4;
    static double b1,b2,b3,b4;
    double c1,c2,c3,c4;
    int i;
    static String row, sort, sortRange1, sortRange2;
    static double maxPower,sumPower,maxPenumpang,sumPenumpang,maxBensin,sumBensin,minHarga,sumHarga,count=1;
    ArrayList<Double> nilai=new ArrayList<>();
    DefaultTableModel alternatif,Hasil,Bobot;
    
        
    public TableMobil() {
        initComponents();
        showTableAlt();
//        sum();
        getBobot();
        label();
        cmbSort.setSelectedIndex(0);
    }
    public final void showTableAlt(){
        try{       
            conn = new DatabaseConnection().setConnection();
            sql= "select namaMobil as 'Nama Mobil', power as 'Horsepower', penumpang as 'Kapasitas Penumpang'"
                    + " , bensin as 'Fuel Efficiency', harga as 'Harga' from alternatif";
            st = conn.createStatement();
            rs=st.executeQuery(sql);
            tblAlternatif.setModel(DbUtils.resultSetToTableModel(rs));                               
        }
        catch(Exception e){
            System.out.println("showTable"+e);
        }
    }
    public final void showTableHasil(){
        try{       
            conn = new DatabaseConnection().setConnection();
            sql= "select namaMobil as 'Nama Mobil', nilai as 'Nilai' from hasil "
                    + "order by nilai desc";
            st = conn.createStatement();
            rs=st.executeQuery(sql);
            tblHasil.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception e){
            System.out.println("showTable"+e);
        }
    }
    public final void getBobot(){
        try{       
            conn = new DatabaseConnection().setConnection();
            sql= "select * from alt_bobot ";
            st = conn.createStatement();
            rs=st.executeQuery(sql); 
            while(rs.next()){
                b1=rs.getDouble("power");
                b2=rs.getDouble("penumpang");
                b3=rs.getDouble("bensin");
                b4=rs.getDouble("harga");
                txfB1.setText(rs.getString("power"));
                txfB2.setText(rs.getString("penumpang"));
                txfB3.setText(rs.getString("bensin"));
                txfB4.setText(rs.getString("harga"));
            }
        }
        catch(Exception e){
            System.out.println("showTable"+e);
        } 
    }
    public final void sum(){
//        try{
//            conn = new DatabaseConnection().setConnection();
//            sql="SELECT max(power), sum(power), max(penumpang), sum(penumpang), max(bensin), sum(bensin), "
//                    + "MIN(harga), sum(harga) , count(namaMobil) from alternatif";
//            st=conn.createStatement();
//            rs=st.executeQuery(sql);
//            while(rs.next()){
//                maxPower=rs.getDouble("max(power)"); 
////                sumPower=rs.getInt("sum(power)");
//                maxPenumpang=rs.getInt("max(penumpang)"); 
////                sumPenumpang=rs.getInt("sum(penumpang)");
//                maxBensin=rs.getDouble("max(bensin)"); 
////                sumBensin=rs.getInt("sum(bensin)");
//                minHarga=rs.getDouble("min(harga)"); 
////                sumHarga=rs.getInt("sum(harga)");
//                count=rs.getInt("count(namaMobil)");
//            }
//        }
//        catch(Exception e){
//            System.out.println(e);
//        }    
        ArrayList<Double> crit1=new ArrayList<>();
        ArrayList<Double> crit2=new ArrayList<>();
        ArrayList<Double> crit3=new ArrayList<>();
        ArrayList<Double> crit4=new ArrayList<>();
        alternatif= (DefaultTableModel)tblAlternatif.getModel();
        for(i=0; i<tblAlternatif.getRowCount();i++){
            crit1.add(Double.parseDouble(alternatif.getValueAt(i,1).toString()));
            crit2.add(Double.parseDouble(alternatif.getValueAt(i,2).toString()));
            crit3.add(Double.parseDouble(alternatif.getValueAt(i,3).toString()));
            crit4.add(Double.parseDouble(alternatif.getValueAt(i,4).toString()));
        }
        maxPower=Collections.max(crit1);
        maxPenumpang=Collections.max(crit2);
        maxBensin=Collections.max(crit3);
        minHarga=Collections.min(crit4);
    }
    public final void label(){
        try{       
            conn = new DatabaseConnection().setConnection();
            sql= "select namaMobil from hasil order by -nilai limit 1";
            st = conn.createStatement();
            rs=st.executeQuery(sql); 
            if(rs.next()){
                lblHasilAkhir.setText("Alternatif terbaik adalah "+rs.getString("namaMobil"));
            }
            else{
                lblHasilAkhir.setText("Alternatif terbaik adalah ");
            }
        }
        catch(Exception e){
            System.out.println("showTable"+e);
        } 
    }
    public final void hitung(){
        DecimalFormat df = new DecimalFormat("0.00");
        String mobil;
        sum();
        int Row;
        Row=tblAlternatif.getRowCount();
        alternatif= (DefaultTableModel)tblAlternatif.getModel();
        try{                
                sql = "delete from hasil where 1";
                st = conn.createStatement();
                st.executeUpdate(sql);
//                showTableAlt();
            }
            catch(Exception e){
                System.out.println("update bobot "+e);
            }        
        for(i=0; i<Row;i++){ 
            mobil=alternatif.getValueAt(i,0).toString();
            r1=(int)alternatif.getValueAt(i,1)/maxPower*b1;
            r2=(int) alternatif.getValueAt(i,2)/maxPenumpang*b2;
            r3=(int) alternatif.getValueAt(i,3)/maxBensin*b3;
            r4=minHarga/(int) alternatif.getValueAt(i,4)*b4;
            
//            System.out.println(alternatif.getValueAt(i,1)+" / "+maxPower+" * "+b1+" = "+r1);
//            System.out.println(alternatif.getValueAt(i,2)+" / "+maxPenumpang+" * "+b2+" = "+r2);
//            System.out.println(alternatif.getValueAt(i,3)+" / "+maxBensin+" * "+b3+" = "+r3);
//            System.out.println(alternatif.getValueAt(i,4)+" / "+minHarga+" * "+b4+" = "+r4);
            double hasil=r1+r2+r3+r4;   
            try{
                    sql = "INSERT INTO hasil (namaMobil,nilai) VALUES ('"+mobil+"','"+df.format(hasil)+"')";
                    st = conn.createStatement();
                    st.executeUpdate(sql); 
                    showTableHasil();
                    label();
                    System.out.println(df.format(hasil));
                    
            }
            catch(Exception e){;
                System.out.println("insert "+e);
            }
        }
        JOptionPane.showMessageDialog(frame,"Updated");    
    }
    final void sort(){
        try{       
            conn = new DatabaseConnection().setConnection();
            sql= "select namaMobil as 'Nama Mobil', power as 'Horsepower', penumpang as 'Kapasitas Penumpang'"
                    + " , bensin as 'Fuel Efficiency', harga as 'Harga' from alternatif "
                    + "where "+sort+" between "+sortRange1+" and "+sortRange2+" ";
            st = conn.createStatement();
            rs=st.executeQuery(sql);
            tblAlternatif.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception e){
            System.out.println("showTable"+e);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        pnlTblPembayaranZakat = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAlternatif = new javax.swing.JTable();
        lblPembayaranZakat = new javax.swing.JLabel();
        lblNama = new javax.swing.JLabel();
        lblPower = new javax.swing.JLabel();
        lblPenumpang = new javax.swing.JLabel();
        lblBensin = new javax.swing.JLabel();
        cmbSort = new javax.swing.JComboBox<>();
        txfNama = new javax.swing.JTextField();
        txfC1 = new javax.swing.JTextField();
        txfC2 = new javax.swing.JTextField();
        txfC3 = new javax.swing.JTextField();
        txfC4 = new javax.swing.JTextField();
        lblHarga = new javax.swing.JLabel();
        pnlSort = new javax.swing.JPanel();
        txfSortBottom = new javax.swing.JTextField();
        txfSortTop = new javax.swing.JTextField();
        btnClear = new javax.swing.JButton();
        btnSort = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnTambahKandi = new javax.swing.JButton();
        btnHapusKandi = new javax.swing.JButton();
        btnHitung = new javax.swing.JButton();
        lblDelete = new javax.swing.JLabel();
        lblTotalZakat = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHasil = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        lblTotalZakat1 = new javax.swing.JLabel();
        txfB1 = new javax.swing.JTextField();
        txfB2 = new javax.swing.JTextField();
        txfB3 = new javax.swing.JTextField();
        txfB4 = new javax.swing.JTextField();
        btnUpdateBobot = new javax.swing.JButton();
        lblB1 = new javax.swing.JLabel();
        lblB2 = new javax.swing.JLabel();
        lblB3 = new javax.swing.JLabel();
        lblB4 = new javax.swing.JLabel();
        lblHasilAkhir = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(229, 229, 229));

        pnlTblPembayaranZakat.setBackground(new java.awt.Color(153, 153, 255));

        tblAlternatif.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        tblAlternatif.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"pajero",  new Double(150.0),  new Double(1000.0),  new Double(4.0)},
                {"fortuner",  new Double(155.0),  new Double(750.0),  new Double(7.0)},
                {"xtrail",  new Double(140.0),  new Double(520.0),  new Double(4.0)},
                {"hrv",  new Double(130.0),  new Double(400.0),  new Double(4.0)},
                {"BMW x3",  new Double(200.0),  new Double(1000.0),  new Double(5.0)}
            },
            new String [] {
                "kandidat", "c1", "c2", "c3"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblAlternatif.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAlternatifMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAlternatif);

        lblPembayaranZakat.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblPembayaranZakat.setText("Tabel Alternatif");

        lblNama.setText("Nama Mobil");

        lblPower.setText("Horsepower");

        lblPenumpang.setText("Kapasitas Penumpang");

        lblBensin.setText("Fuel Efficiency (mpg)");

        cmbSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sort By", "Horsepower", "Kapasitas Penumpang", "Fuel Efficiency", "Harga" }));
        cmbSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSortActionPerformed(evt);
            }
        });

        txfNama.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        txfC1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        txfC2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        txfC3.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        txfC4.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        lblHarga.setText("Harga (ratus juta Rupiah)");

        txfSortBottom.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        txfSortTop.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        btnClear.setBackground(new java.awt.Color(229, 229, 229));
        btnClear.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnClear.setText("Clear");
        btnClear.setBorder(null);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnSort.setBackground(new java.awt.Color(229, 229, 229));
        btnSort.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnSort.setText("Sort");
        btnSort.setBorder(null);
        btnSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel1.setText("-");

        javax.swing.GroupLayout pnlSortLayout = new javax.swing.GroupLayout(pnlSort);
        pnlSort.setLayout(pnlSortLayout);
        pnlSortLayout.setHorizontalGroup(
            pnlSortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSortLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txfSortBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel1)
                .addGap(2, 2, 2)
                .addComponent(txfSortTop, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSort, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlSortLayout.setVerticalGroup(
            pnlSortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSortLayout.createSequentialGroup()
                .addGroup(pnlSortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfSortBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txfSortTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSort, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btnTambahKandi.setBackground(new java.awt.Color(229, 229, 229));
        btnTambahKandi.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnTambahKandi.setText("Tambah Kandidat");
        btnTambahKandi.setBorder(null);
        btnTambahKandi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahKandiActionPerformed(evt);
            }
        });

        btnHapusKandi.setBackground(new java.awt.Color(229, 229, 229));
        btnHapusKandi.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnHapusKandi.setText("Hapus Kandidat");
        btnHapusKandi.setBorder(null);
        btnHapusKandi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusKandiActionPerformed(evt);
            }
        });

        btnHitung.setBackground(new java.awt.Color(229, 229, 229));
        btnHitung.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnHitung.setText("HITUNG");
        btnHitung.setBorder(null);
        btnHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHitungActionPerformed(evt);
            }
        });

        lblDelete.setText("*klik baris pada tabel");

        javax.swing.GroupLayout pnlTblPembayaranZakatLayout = new javax.swing.GroupLayout(pnlTblPembayaranZakat);
        pnlTblPembayaranZakat.setLayout(pnlTblPembayaranZakatLayout);
        pnlTblPembayaranZakatLayout.setHorizontalGroup(
            pnlTblPembayaranZakatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTblPembayaranZakatLayout.createSequentialGroup()
                .addGap(169, 169, 169)
                .addComponent(lblPembayaranZakat)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlTblPembayaranZakatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTblPembayaranZakatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTblPembayaranZakatLayout.createSequentialGroup()
                        .addGroup(pnlTblPembayaranZakatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNama)
                            .addComponent(lblPower)
                            .addComponent(lblPenumpang)
                            .addComponent(lblBensin)
                            .addComponent(cmbSort, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblHarga))
                        .addGroup(pnlTblPembayaranZakatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlSort, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlTblPembayaranZakatLayout.createSequentialGroup()
                                .addGroup(pnlTblPembayaranZakatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txfC1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txfC2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txfC3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txfC4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txfNama, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                                .addGroup(pnlTblPembayaranZakatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnTambahKandi, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                    .addComponent(btnHapusKandi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnHitung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblDelete, javax.swing.GroupLayout.Alignment.LEADING)))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlTblPembayaranZakatLayout.setVerticalGroup(
            pnlTblPembayaranZakatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTblPembayaranZakatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPembayaranZakat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlTblPembayaranZakatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbSort)
                    .addComponent(pnlSort, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlTblPembayaranZakatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNama)
                    .addComponent(txfNama)
                    .addComponent(btnTambahKandi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlTblPembayaranZakatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPower)
                    .addComponent(lblDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlTblPembayaranZakatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPenumpang)
                    .addComponent(btnHapusKandi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlTblPembayaranZakatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlTblPembayaranZakatLayout.createSequentialGroup()
                        .addGroup(pnlTblPembayaranZakatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txfC3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBensin))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlTblPembayaranZakatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txfC4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblHarga)))
                    .addComponent(btnHitung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        lblTotalZakat.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblTotalZakat.setText("Hasil");

        tblHasil.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        tblHasil.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Alternatif", "Mobil", "Nilai"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHasil.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tblHasil);
        if (tblHasil.getColumnModel().getColumnCount() > 0) {
            tblHasil.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));

        lblTotalZakat1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblTotalZakat1.setText("Bobot");

        txfB1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        txfB2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        txfB3.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        txfB4.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        btnUpdateBobot.setBackground(new java.awt.Color(229, 229, 229));
        btnUpdateBobot.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnUpdateBobot.setText("Update Bobot");
        btnUpdateBobot.setBorder(null);
        btnUpdateBobot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateBobotActionPerformed(evt);
            }
        });

        lblB1.setText("Horsepower");

        lblB2.setText("Kapasitas Penumpang");

        lblB3.setText("Fuel Efficiency");

        lblB4.setText("Harga");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(lblTotalZakat1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnUpdateBobot, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblB1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txfB1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblB2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(txfB2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblB3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txfB3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblB4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txfB4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotalZakat1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfB1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblB1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfB2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblB2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfB3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblB3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfB4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblB4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdateBobot, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        lblHasilAkhir.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblHasilAkhir.setText("Alternatif terbaik adalah ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHasilAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlTblPembayaranZakat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTotalZakat)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblTotalZakat)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlTblPembayaranZakat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblHasilAkhir)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateBobotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateBobotActionPerformed
            conn = new DatabaseConnection().setConnection(); 
            try{                
                sql = "UPDATE alt_bobot SET power='"+txfB1.getText()+"', penumpang='"+txfB2.getText()+"',"
                        + " bensin='"+txfB3.getText()+"', harga='"+txfB4.getText()+"' ";
                st = conn.createStatement();
                st.executeUpdate(sql);
//                JOptionPane.showMessageDialog(frame,"updated"); 
                getBobot();
//                showTable();
            }
            catch(Exception e){
                System.out.println("update bobot "+e);
            }
//            try{                
//                sql = "delete from hasil where 1";
//                st = conn.createStatement();
//                st.executeUpdate(sql);
//                JOptionPane.showMessageDialog(frame,"deleted"); 
//                showTable();
//                label();
//            }
//            catch(Exception e){
//                System.out.println("update bobot "+e);
//            }
            hitung();
            label();
    }//GEN-LAST:event_btnUpdateBobotActionPerformed

    private void cmbSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSortActionPerformed
        pnlSort.setVisible(true);
        switch (cmbSort.getSelectedIndex()){
            case 0: pnlSort.setVisible(false);
            break;
            case 1: sort="power";
            break;
            case 2: sort="penumpang";
            break;
            case 3: sort="bensin";
            break;
            case 4: sort="harga";
            break;
        }
    }//GEN-LAST:event_cmbSortActionPerformed

    private void tblAlternatifMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAlternatifMouseClicked
        row=(String) tblAlternatif.getValueAt(tblAlternatif.getSelectedRow(),0);
        //        System.out.println(row);
    }//GEN-LAST:event_tblAlternatifMouseClicked

    private void btnTambahKandiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahKandiActionPerformed
        if(txfC1.getText().equals("")||txfC2.getText().equals("")||txfC3.getText().equals("")||txfC4.getText().equals("")){
            JOptionPane.showMessageDialog(frame,"Input belum lengkap");
        }
        else{
            c1=Integer.parseInt(txfC1.getText());
            c2=Integer.parseInt(txfC2.getText());
            c3=Integer.parseInt(txfC3.getText());
            c4=Integer.parseInt(txfC4.getText());
            conn = new DatabaseConnection().setConnection();
            try{
                sql = "INSERT INTO alternatif (namaMobil,power,penumpang,bensin,harga) "
                    + "VALUES ('"+txfNama.getText()+"', '"+c1+"','"+c2+"', '"+c3+"', '"+c4+"')";
                st = conn.createStatement();
                st.executeUpdate(sql);
                txfNama.setText("");
                txfC1.setText("");
                txfC2.setText("");
                txfC3.setText("");
                txfC4.setText("");
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(frame,e);
                System.out.println(e);
            }
        }
        showTableAlt();
    }//GEN-LAST:event_btnTambahKandiActionPerformed

    private void btnHapusKandiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusKandiActionPerformed
        conn = new DatabaseConnection().setConnection();
        try{
                    sql = "delete from alternatif where namaMobil='"+row+"'";
                    st = conn.createStatement();
                    st.executeUpdate(sql);      
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(frame,"anda harus login");
                    System.out.println(e);
                }
        showTableAlt();
    }//GEN-LAST:event_btnHapusKandiActionPerformed

    private void btnHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHitungActionPerformed
        hitung();
    }//GEN-LAST:event_btnHitungActionPerformed

    private void btnSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortActionPerformed
        if(cmbSort.getSelectedIndex()==0||txfSortBottom.getText().equals("")||txfSortTop.getText().equals("")){
            JOptionPane.showMessageDialog(frame,"apa yaa");
        }
        else{
            sortRange1=txfSortBottom.getText();
            sortRange2=txfSortTop.getText();
            sort();
        }
        System.out.println(sort+sortRange1+sortRange2);
    }//GEN-LAST:event_btnSortActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        cmbSort.setSelectedIndex(0);
        txfSortBottom.setText("");
        txfSortTop.setText("");
        showTableAlt();
    }//GEN-LAST:event_btnClearActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TableMobil().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnHapusKandi;
    private javax.swing.JButton btnHitung;
    private javax.swing.JButton btnSort;
    private javax.swing.JButton btnTambahKandi;
    private javax.swing.JButton btnUpdateBobot;
    private javax.swing.JComboBox<String> cmbSort;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblB1;
    private javax.swing.JLabel lblB2;
    private javax.swing.JLabel lblB3;
    private javax.swing.JLabel lblB4;
    private javax.swing.JLabel lblBensin;
    private javax.swing.JLabel lblDelete;
    private javax.swing.JLabel lblHarga;
    private javax.swing.JLabel lblHasilAkhir;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblPembayaranZakat;
    private javax.swing.JLabel lblPenumpang;
    private javax.swing.JLabel lblPower;
    private javax.swing.JLabel lblTotalZakat;
    private javax.swing.JLabel lblTotalZakat1;
    private javax.swing.JPanel pnlSort;
    public javax.swing.JPanel pnlTblPembayaranZakat;
    public static javax.swing.JTable tblAlternatif;
    private javax.swing.JTable tblHasil;
    private javax.swing.JTextField txfB1;
    private javax.swing.JTextField txfB2;
    private javax.swing.JTextField txfB3;
    private javax.swing.JTextField txfB4;
    private javax.swing.JTextField txfC1;
    private javax.swing.JTextField txfC2;
    private javax.swing.JTextField txfC3;
    private javax.swing.JTextField txfC4;
    private javax.swing.JTextField txfNama;
    private javax.swing.JTextField txfSortBottom;
    private javax.swing.JTextField txfSortTop;
    // End of variables declaration//GEN-END:variables
}
