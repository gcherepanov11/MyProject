/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication5;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;

public class OpenLevBrowser {

    static String str = "";
    static List<String> listTmpString;
    static List<String> listStstist;
    static boolean changeIn = true;

    public OpenLevBrowser(String str) {
        this.str = str;
        this.changeIn = true;
    }

    public OpenLevBrowser(List<String> listTmpString, List<String> listStstist) {
        this.listTmpString = listTmpString;
        this.listStstist = listStstist;
        this.changeIn = false;
        System.out.println("In costructro OpenLevBrouser");
    }

    public static WebView getWebView(String str) throws MalformedURLException {//начало построения приемщика данных
        WebView webView = new WebView();

        final WebEngine engine = webView.getEngine();
        //engine.load("http://www.google.com");
        //File file = new File("C:\\Users\\Nazarov\\Documents\\NetBeansProjects\\JavaFXMyBrovserToLev\\graphics\\drawTrend.htm");
        File file = new File("web_graph\\drawTrend.htm");
        URL url = file.toURI().toURL();
        engine.setJavaScriptEnabled(true);
        engine.load(url.toString());
        // -- Выполнение сценария без кнопки --- 
        engine.getLoadWorker().stateProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal) {
                if (newVal == Worker.State.SUCCEEDED) {
                    for (String s : str.split("\n")) {
                        engine.executeScript("putTrend('" + s + "');");
                    }
                    // --- Выполняем последовательность загрузки графиков ----
                    engine.executeScript("setAllTrendList();");
                    engine.executeScript("drawInCanvas();");
                    engine.executeScript("drawVarTable();");
                } else {
                    System.out.println("Did not succeed");
                }
            }
        });

        return webView;
    }

    // --- Второй но с входом Array двумя ---
    public static WebView getWebView(List<String> listTmpString, List<String> listStstist) throws MalformedURLException {
        WebView webView = new WebView();

        final WebEngine engine = webView.getEngine();//ради проверки
        //engine.load("http://www.google.com");
        //File file = new File("C:\\Users\\Nazarov\\Documents\\NetBeansProjects\\JavaFXMyBrovserToLev\\graphics\\drawTrend.htm");
        File file = new File("web_graph\\drawTrend.htm");
        URL url = file.toURI().toURL();
        engine.setJavaScriptEnabled(true);
        engine.load(url.toString());
        // -- Выполнение сценария без кнопки --- 
        engine.getLoadWorker().stateProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal) {
                if (newVal == Worker.State.SUCCEEDED) {
                    if (!listTmpString.isEmpty()) {

                        for (String s : listTmpString) {
                            //System.out.println(s);
                            //engine.executeScript("putTrend('" + s +  "');");
                            engine.executeScript("putTrend('" + s + "');");
                        }
                        // listTmpString.clear();// попробуем сохранирть память
                        // --- Выполняем последовательность загрузки графиков ----
                        //engine.executeScript("setAllTrendList();");
                        //engine.executeScript("drawInCanvas();");
                        //engine.executeScript("drawVarTable();");
                        engine.executeScript("drawTrendsFromJava();");
                    }
                    // -- запуск второго
                    if (!listStstist.isEmpty()) {
                        //System.out.println("Enter in two");
                        engine.executeScript("startLogFromJava();");
                        for (String s : listStstist) {
                            //System.out.println(s);
                            engine.executeScript("putLog('" + s + "');");
                        }
                        engine.executeScript("drawLogFromJava();");

                          // -- Тестовый вывод лога
                          /*List<?> tmp1String = new ArrayList(); 
                         tmp1String = (List<String[][]>) engine.executeScript("logArr;");
                         Iterator<?> iterator = tmp1String.iterator();
                         while (iterator.hasNext())
                         {
                         Object obj = iterator.next();    
                         }
                         */
                          // --- Тестовые выводы  ---
                     /*   int i1 =0;
                         for(String s : listStstist){
                         System.out.println(engine.executeScript("logArr["+i1+"].dt;") + 
                         "  " + engine.executeScript("logArr["+i1+"].textMsg;") +
                         " " + engine.executeScript("logArr["+i1+"].type;") +
                         " " + engine.executeScript("logArr["+i1+"].direct;") );
                         ++i1;
                         }
                         */
                        //System.out.println(engine.executeScript("knowDataFormat;")); 
                        //System.out.println(engine.executeScript("backgroundColor.length"));
                        //System.out.println(engine.executeScript("backgroundColor[0].t;"));
                        //System.out.println(engine.executeScript("backgroundColor[1].t;"));
                    }
                } else {
                    System.out.println("Did not succeed");
                }
            }
        });
        return webView;
    }

    private static void show() {
        JFrame frame = new JFrame("JavaFX in Swing");
        final JFXPanel panel = new JFXPanel();
        frame.add(panel);
        frame.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // В весь экран
        frame.setDefaultLookAndFeelDecorated(true); // С такими параметрами завелось отображение 2 Фрейма на windows7
        frame.setLocationRelativeTo(null); // position in the center of the screen
        frame.setVisible(true);
        //frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE); // закрыть полностью приложение
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    initFX(panel);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(OpenLevBrowser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private static Scene createScene() throws MalformedURLException {
        Scene scene;
        if (changeIn) {
            scene = new Scene(getWebView(str), null);
        } else {
            scene = new Scene(getWebView(listTmpString, listStstist), null);
        }
        return scene;
    }

    static void initFX(JFXPanel panel) throws MalformedURLException {
        Scene scene = createScene();
        panel.setScene(scene);
        System.out.println("In initFX OpenLevBrouser");

    }

    static void startFx(JFXPanel panel) throws MalformedURLException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                show();
            }
        });
    }
    /*   public static void main(String[] args) {
     SwingUtilities.invokeLater(new Runnable() {
     @Override
     public void run() {
     show();
     }
     });
     }
     */
}
