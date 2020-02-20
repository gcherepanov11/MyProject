/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loginingfilegui;

import java.awt.*; 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
/**
 *
 * @author nazarov
 */
public class MainFrameGUI extends javax.swing.JFrame {

    /**
     * Creates new form MainFrameGUI
     */
    public MainFrameGUI() {
        
        setLayout(new GridLayout());
        setUndecorated(true); // убрать рамку у окна что бы нельяз было его двигать.
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
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("файл загрузить");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jTextPane1);

        jLabel1.setText("v.01b");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        // Цвет фона окнас логами
        Color bgColor = Color.BLACK;
        UIDefaults defaults = new UIDefaults();
        defaults.put("EditorPane[Enabled].backgroundPainter", bgColor);
        jTextPane1.putClientProperty("Nimbus.Overrides", defaults);
        jTextPane1.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
        jTextPane1.setBackground(bgColor);
        jTextPane1.setActionMap(null);
        
       StyledDocument doc = jTextPane1.getStyledDocument();
        String fPath = "";
        JFileChooser fileopen = new JFileChooser(System.getProperty("user.dir"));
        int ret = fileopen.showDialog(null, "Чтение файла с данными");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            fPath = file.getPath();
        }
         try {
     // FileReader textFileReader = new FileReader(fPath);
      InputStreamReader inputsream = new InputStreamReader(new FileInputStream(fPath), "UTF8"); // Правильное отображение русскаого языка чтение буыера такое себе
      BufferedReader bufReader = new BufferedReader(inputsream);
     // BufferedReader bufReader = new BufferedReader(new InputStreamReader(
      //                new FileInputStream(fPath), "UTF8"));
      
      char[] buffer = new char[8096];
      int numberOfCharsRead = bufReader.read(buffer); // read will be from
      // memory
      int tmpColor = 1;
      while (numberOfCharsRead != -1) {
         System.out.println(tmpColor);
         Color clr2 = new Color(200, 255, 0);  // не вариант это использовать красит весл текст
         Color clr3 = new Color(225, 200, 0);  // не вариант это использовать красит весл текст
        String strTmp = String.valueOf(buffer, 0, numberOfCharsRead);
        String[] tmpSplitStr = strTmp.split("\n");
        System.out.println(tmpSplitStr.length);
        for (int i=0; i<tmpSplitStr.length; ++i){ // это просто понять что там выходит
           String[] podstroki = tmpSplitStr[i].split("\t"); // Создаем подстроки уже табом.
           for(String n : podstroki){
             System.out.println(n);
        //тут пробуем добавлять текст цветом
        SimpleAttributeSet randomColor = new SimpleAttributeSet();
        SimpleAttributeSet redColor = new SimpleAttributeSet();
        // StyleConstants.setForeground(pink, Color.pink); 
        StyleConstants.setForeground(randomColor, new Color(rnd(255), rnd(255), rnd(255))); 
        StyleConstants.setForeground(redColor, Color.RED); 
        
        try
        {
             // тут нужно сделать отдельную функцию для переборазначений и цветов под них пока так
              if (check(n) == 1) { // передам в функцию для проверки пока одного значения
                doc.insertString(doc.getLength(), n + " ", randomColor);}// так вносим с определенным стилем
              else if (check(n) == 2) {
                doc.insertString(doc.getLength(), n + " ", redColor);}// так вносим с определенным стилем}
              else doc.insertString(doc.getLength(), n + " ", null); 
        }
        catch(Exception e) { System.out.println(e); }
           }
             try {
                 doc.insertString(doc.getLength(), "\n ", null); // так просто новая строка
             } catch (BadLocationException ex) {
                 Logger.getLogger(MainFrameGUI.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
        numberOfCharsRead = inputsream.read(buffer);
       
        jTextPane1.setStyledDocument(doc);  // тут мы апрсто засовываем сформированный документ
        
        /* // Старая конструкция тестовая пробовал разное
        jTextPane1.setContentType("text/html");
        jTextPane1.setEditable(false);

        jTextPane1.setForeground(clr2);
        //jTextPane1.setText("<a href=\"abc\">" + strTmp + "</a>" + "\n" + "Строка  <b>слово</b>" + "\n" +   "<hr color=\"red\" width=\"300\">"); // это работает
        jTextPane1.setText(strTmp + "</a>" + "\n" + "Строка  <b>слово</b>"); // это работает
        */
        //if (tmpColor <=255){
        ++tmpColor;//}
      }

      bufReader.close();

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  


    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(MainFrameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrameGUI().setVisible(true);                
            }
        });
    }

    
    // Функция рандома для тестов
    public static int rnd(final int max)
    {
        return (int) (Math.random() * max);
    }
    
    //  проверка патернов для раскраски
    public static int check(final String checS){
        Pattern pattern1 = Pattern.compile("^.*РАБОТАЕТ.*$|^.*ЗАПУСКАЕТСЯ.*$|^.*ГОТОВ.*$"); 
        Pattern pattern2 = Pattern.compile("^.*ГОРЯЧИЙ СТАРТ.*$"); 
        //Pattern pattern3 = Pattern.compile("^.*РАБОТАЕТ.*$"); 
        Matcher matcher1 = pattern1.matcher(checS);
        Matcher matcher2 = pattern2.matcher(checS);
        if (matcher1.matches()){ 
          return 1;
       }
        if (matcher2.matches()){ 
          return 2;
       }
        return 0;
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}