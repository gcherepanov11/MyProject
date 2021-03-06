package FrameCreate;

import XMLTools.XMLSAX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.parsers.ParserConfigurationException;
import DataBaseConnect.StructSelectData;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactoryConfigurationException;
import org.xml.sax.SAXException;
import DataBaseConnect.DataBase;
import Main.Main_JPanel;
import XMLTools.UUID;

/**
 *
 * @author cherepanov
 */
public class FrameTabel extends javax.swing.JPanel {

    Main_JPanel mj = new Main_JPanel();

    int filepath;
    String filepatch;
    String nameTable = "";
    String UUIDHigth = "";
    String Graphname, TypeName;
    String UUIDBlockPref,UUIDBlockName;
    String TypeAI_DRV = "REAL", TypeDI_DRV = "BOOL";
    String nameSignal1, nameSignal2, nameSignal3, nameSignal4, nameSignal5, nameSignal6,
            UUID_Type1, UUID_Type2, UUID_Type3, UUID_Type4,
            UUID_Parent1, UUID_Parent2, UUID_Parent3, UUID_Parent4
            ;

    private final String UUIDType_AI_ToProcessing = "187F76AE49C59E950138DDA3067101D0";//уиды типов
    private final String UUIDType_AI_Settings = "668FE9B94A603427C8EDBBB8917A7594";
    private final String UUID_AI_OnlyToHMI = "3F156B284DE592D62F031C9791BF07EF";
    private final String UUID_AI_FromProcessing = "2318F94F4C3CEA2681DE4C8C432A66E9";
    private final String UUID_DI_FromProcessing = "9F0EDD3143DC10ACF03CBA8C4A870F2D";
    private final String UUID_DI_Settings = "94C521C642227325371AE7BCC363E527";
    private final String UUID_DI_ToProcessing = "55B37D104E6E69A8BD7466B4968651A1";
    private final String UUID_AO_FromHMI = "28BBA2F94E53F5E7D050CBA8820F76E4";
    private final String UUID_AO_ToHMI = "D225A65348D2770B341BAC9762DA1766";
    private final String UUID_DO_FromHMI = "032CB0403C4B8FB66F9B68B2E5D50373";
    private final String UUID_DO_ToHMI = "7474D14B284DB0A574420580A1FFD7C0";

    private final String UUID_Parent_AI_FromProcessing = "D48C3F1549ACD994A366A1A51FE00772";
    private final String UUID_Parent_AI_OnlyToHMI = "ABDF10D048FB61AB90F474996CF9B3B2";
    private final String UUID_Parent_AI_Settings = "E10A238347B7C26C7C1FDFA77F91B1F5";
    private final String UUID_Parent_AI_ToProcessing = "2BDAC6F849D13DED3A2A5B9190720FD7";
    private final String UUID_Parent_AO_FromHMI = "136536604B0AD5911BA545A82E7DF913";
    private final String UUID_Parent_AO_ToHMI = "00B934F5461BB0C8938D42A9118D2143";
    private final String UUID_Parent_DI_FromProcessing = "2694959C4B5180695BAA0E9209463FE3";
    private final String UUID_Parent_DI_Setting = "9D3CCA014F76318C4B750981ED2CEA67";
    private final String UUID_Parent_DI_ToProcessing = "2A86C1F64D326C195FE5CB898889601B";
    private final String UUID_Parent_DO_FromHMI = "E151BCB02540B85F326AF1B1DD593EE6";
    private final String UUID_Parent_DO_ToHMI = "150F53385749F03C0BB1E29A7ED64D95";

    private final String UUIDSourcePref = "8F544E934F31327C4EBC88930CB7AD32";
    private final String UUIDSourceNameRU = "E543C4CD4FF3878196D61C86A31A4CE0";

    XMLSAX createXMLSax = new XMLSAX();

    DataBase workbase = DataBase.getInstance();

    public FrameTabel(String nameTable) {
        this.nameTable = nameTable;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(999, 530));

        jTable1.setModel(getTableData());
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Добавить в мнемосхему");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Сигнал нижнего уровня");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 964, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab1", jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        JFileChooser fileload = new JFileChooser("C:\\Users\\cherepanov\\Desktop\\ArchNew\\Design");
        fileload.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        filepath = fileload.showOpenDialog(this);
        if (filepath == JFileChooser.APPROVE_OPTION) {
            try {
                String filename = fileload.getSelectedFile().getName();

                filepatch = fileload.getSelectedFile().getCanonicalPath();
            } catch (IOException ex) {
                Logger.getLogger(FrameTabel.class.getName()).log(Level.SEVERE, null, ex);//
            }
        }

        DataBase workbase = DataBase.getInstance();
        //workbase.connectionToBase();
        ArrayList<String[]> dataFromDbGPA = workbase.getSelectData(nameTable);//пока передаю через AI но необходимо это исправить,чтобы принимал все параметры
        ArrayList<String[]> dataToXML = new ArrayList<>();
        int number = 0;
        UUID uuid = new UUID();

//        String[] tmp = new String[44];
//        for(String[] sa : dataFromDbGPA){// созданная львом штука которую надо сдлеать
//            tmp[0] = "Name"; tmp[1]="BaseAnPar_Test_"+(number++);
//            tmp[2] = "Type"; tmp[3]="BaseAnPar_Test_"+(number++);
//            tmp[4]="UUID";  tmp[5]=uuid.getUIID();
//            tmp[2] = "Type"; tmp[3]="BaseAnPar_Test_"+(number++);
//            tmp[2] = "Type"; tmp[3]="BaseAnPar_Test_"+(number++);
//            tmp[2] = "Type"; tmp[3]="BaseAnPar_Test_"+(number++);
//            tmp[2] = "Type"; tmp[3]="BaseAnPar_Test_"+(number++);
//            tmp[2] = "Type"; tmp[3]="BaseAnPar_Test_"+(number++);
//            tmp[2] = "Value"; tmp[3] = sa[0];
//            tmp[2] = "Variable"; tmp[3]="NameAlg";
//            dataToXML.add(tmp);
//        }
        if (nameTable.equals("dies_ai")) {
            UUIDHigth = "C06031C04C13BB17463EB1B889813E68";//уид верхенего уровня
            Graphname = "TGraphicsCompositeTypeAI";
            TypeName = "T_BaseAnIn";
            UUIDBlockPref = "7DF53A3B47B1075B9D3AE78253FC271B";
            UUIDBlockName="31E704A94C1D0BCA16C48C8F563CAB4B";

        } else if (nameTable.equals("dies_ao")) {
            UUIDHigth = "CDB0D9974B89FB4417F2139064F22857";
            Graphname = "TGraphicsCompositeTypeAO";
            TypeName = "T_BaseAnOut";
            UUIDBlockPref = "7DF53A3B47B1075B9D3AE78253FC271B";
            UUIDBlockName="31E704A94C1D0BCA16C48C8F563CAB4B";

        } else if (nameTable.equals("dies_do")) {
            UUIDHigth = "DD87E9C742BB69689219F08BAD99F4B8";
            Graphname = "TGraphicsCompositeTypeDO";
            TypeName = "T_BaseDO";
            UUIDBlockPref = "FB48BEA74A28EB85591DC0B68AA08A74";//поменять местами DO и DI
            UUIDBlockName="524F2EBB4524C97C16682CAD9668D4CC";

        } else if (nameTable.equals("dies_di")) {
            UUIDHigth = "5298F3F94A79E35B818384B694BB561B";
            Graphname = "TGraphicsCompositeTypeDI";
            TypeName = "T_BaseDI";
            UUIDBlockPref = "FB48BEA74A28EB85591DC0B68AA08A74";
            UUIDBlockName="524F2EBB4524C97C16682CAD9668D4CC";
        }

        try {
            try {
                // Тут передаем данные тестовый вызов
                createXMLSax.addSignalesMnemo(dataFromDbGPA, "GPA", filepatch, UUIDHigth, Graphname, UUIDSourcePref, UUIDBlockPref, TypeName,UUIDSourceNameRU,UUIDBlockName);
            } catch (IOException ex) {
                Logger.getLogger(FrameTabel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (XPathExpressionException ex) {
                Logger.getLogger(FrameTabel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerFactoryConfigurationError ex) {
                Logger.getLogger(FrameTabel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(FrameTabel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(FrameTabel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (XPathFactoryConfigurationException ex) {
                Logger.getLogger(FrameTabel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(FrameTabel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SAXException ex) {
            Logger.getLogger(FrameTabel.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JFileChooser fileload = new JFileChooser();
        fileload.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//эта строка отвечает за путь файла
        filepath = fileload.showOpenDialog(this);//эта строка отвечает за само открытие
        if (filepath == JFileChooser.APPROVE_OPTION) {
            try {
                String filename = fileload.getSelectedFile().getName();//записывает в переменную путь,так что теперь надо обЪяснить методу как его юзать
                try {
                    filepatch = fileload.getSelectedFile().getCanonicalPath();
                } catch (IOException ex) {
                    Logger.getLogger(Main_JPanel.class.getName()).log(Level.SEVERE, null, ex);

                }

                switch (nameTable) {
                    case "dies_ai":

                        nameSignal1 = "T_GPA_AI_FromProcessing";
                        nameSignal2 = "T_GPA_AI_ToProcessing";
                        nameSignal3 = "T_GPA_AI_Settings";
                        nameSignal4 = "T_GPA_AI_OnlyToHMI";
                        nameSignal5 = "T_AI_DRV";
                        nameSignal6 = "T_DI_DRV";

                        UUID_Type1 = UUID_AI_FromProcessing;
                        UUID_Type2 = UUIDType_AI_ToProcessing;
                        UUID_Type3 = UUIDType_AI_Settings;
                        UUID_Type4 = UUID_AI_OnlyToHMI;

                        UUID_Parent1 = UUID_Parent_AI_FromProcessing;
                        UUID_Parent2 = UUID_Parent_AI_ToProcessing;
                        UUID_Parent3 = UUID_Parent_AI_Settings;
                        UUID_Parent4 = UUID_Parent_AI_OnlyToHMI;

                        createXMLSax.runBasecreateTypeAll(filepatch, nameTable, nameSignal1, UUID_Type1, UUID_Parent1);//
                        createXMLSax.runBasecreateTypeAll(filepatch, nameTable, nameSignal2, UUID_Type2, UUID_Parent2);
                        createXMLSax.runBasecreateTypeAll(filepatch, nameTable, nameSignal3, UUID_Type3, UUID_Parent3);
                        createXMLSax.runBasecreateTypeAll(filepatch, nameTable, nameSignal4, UUID_Type4, UUID_Parent4);

                        createXMLSax.runBasecreateTypeAll(nameTable, nameSignal5, filepatch, TypeAI_DRV);
                        createXMLSax.runBasecreateTypeAll(nameTable, nameSignal6, filepatch, TypeDI_DRV);

                        break;
                    case "dies_ao":

                        nameSignal1 = "T_GPA_AO_FromHMI";
                        nameSignal2 = "T_GPA_AO_ToHMI";

                        UUID_Type1 = UUID_AO_FromHMI;
                        UUID_Type2 = UUID_AO_ToHMI;

                        UUID_Parent1 = UUID_Parent_AO_FromHMI;
                        UUID_Parent2 = UUID_Parent_AO_ToHMI;

                        createXMLSax.runBasecreateTypeAll(filepatch, nameTable, nameSignal1, UUID_Type1, UUID_Parent1);
                        createXMLSax.runBasecreateTypeAll(filepatch, nameTable, nameSignal2, UUID_Type2, UUID_Parent2);

                        break;
                    case "dies_do":

                        nameSignal1 = "T_GPA_DO_FromHMI";
                        nameSignal2 = "T_GPA_DO_ToHMI";

                        UUID_Type1 = UUID_DO_FromHMI;
                        UUID_Type2 = UUID_DO_ToHMI;

                        UUID_Parent1 = UUID_Parent_DO_FromHMI;
                        UUID_Parent2 = UUID_Parent_DO_ToHMI;

                        createXMLSax.runBasecreateTypeAll(filepatch, nameTable, nameSignal1, UUID_Type1, UUID_Parent1);
                        createXMLSax.runBasecreateTypeAll(filepatch, nameTable, nameSignal2, UUID_Type2, UUID_Parent2);

                        break;
                    case "dies_di":

                        nameSignal1 = "T_GPA_DI_FromProcessing";
                        nameSignal2 = "T_GPA_DI_Settings";
                        nameSignal3 = "T_GPA_DI_ToProcessing";

                        UUID_Type1 = UUID_DI_FromProcessing;
                        UUID_Type2 = UUID_DI_Settings;
                        UUID_Type3 = UUID_DI_ToProcessing;

                        UUID_Parent1 = UUID_Parent_DI_FromProcessing;
                        UUID_Parent2 = UUID_Parent_DI_Setting;
                        UUID_Parent3 = UUID_Parent_DI_ToProcessing;

                        createXMLSax.runBasecreateTypeAll(filepatch, nameTable, nameSignal1, UUID_Type1, UUID_Parent1);
                        createXMLSax.runBasecreateTypeAll(filepatch, nameTable, nameSignal2, UUID_Type2, UUID_Parent2);
                        createXMLSax.runBasecreateTypeAll(filepatch, nameTable, nameSignal3, UUID_Type3, UUID_Parent3);

                }

            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Main_JPanel.class.getName()).log(Level.SEVERE, null, ex);

            }

        }

    }//GEN-LAST:event_jButton2ActionPerformed
    public TableModel getTableData() { // функция для создания списка из талиц базы так же возращаем объект для конструкции таблицы при запуске
        // Можно так сложно не соединять, аппендицит от предыдущего что бы не запутаться
        String[] columnDop = {"Выбор"};// до поля для галок или еще чего
        String[] columnNames = StructSelectData.getColumns();
        String[] resultColumn = Stream.concat(Arrays.stream(columnDop), Arrays.stream(columnNames))
                .toArray(String[]::new); // соединяем два массива
        Object[][] data = StructSelectData.getcurrentSelectTable(); // От куда беру данные
        Object[] streamArray;
        Object[] streamNull = new Object[1];
        streamNull[0] = null;
        Object[][] tmp2 = new Object[data.length][];
        for (int i = 0; i < data.length; i++) {
            streamArray = new Object[data[i].length + 1];
            // преобразовываем массив
            Object[] testStream = Stream.concat(Arrays.stream(streamNull), Arrays.stream(data[i])).toArray(Object[]::new);
            tmp2[i] = testStream;
        }
        return new DefaultTableModel(tmp2, resultColumn) {
            @Override
            public Class<?> getColumnClass(int columnIndex) { // структура для отображения таблицы с галками
                Class clazz = String.class;
                switch (columnIndex) {
                    case 0:
                        clazz = Boolean.class;
                        break;
                }
                return clazz;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == column;
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                // Условие проверки галочки скрывать легенду
                if (aValue instanceof Boolean && column == 0) {
                    System.out.println("Posution - > " + row + " " + aValue);
                    Vector rowData = (Vector) getDataVector().get(row); // не помню для чего но без этого только скрывает =(
                    rowData.set(0, (boolean) aValue);
                    fireTableCellUpdated(row, column);

                    try {
                        // Само действие не реализованно
                        if ((boolean) aValue == true) {
                            System.out.println("true");
                        }
                        if ((boolean) aValue == false) {
                            System.out.println("false");
                        }
                    } catch (NullPointerException e) {
                        JOptionPane.showMessageDialog(null, "Трудности с добавлением");
                    }
                }

            }
        };
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
