package javaapplication5;

import com.opencsv.CSVReader;
import com.sun.javafx.collections.MappingChange.Map;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import static java.awt.image.ImageObserver.WIDTH;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Set; 
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import static javaapplication5.RunGraph.listTime;
import javafx.beans.value.ObservableValue;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIDefaults;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import static jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.getObjectSize;
import loginingfilegui.*;
import loginingfilegui.MainFrameGUI;
import static loginingfilegui.MainFrameGUI.check;
import static loginingfilegui.MainFrameGUI.rnd;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.labels.IntervalCategoryToolTipGenerator;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.GanttRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimePeriodAnchor;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ShapeUtilities;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nazarov
 */
public class NewJFrameSimpleGraph_1 extends javax.swing.JFrame implements ChartMouseListener, KeyListener {
    
    //private static final String SAMPLE_CSV_FILE_PATH = "AO_TREND_20190527_020929.log";
   
    private static String SAMPLE_CSV_FILE_PATH = "";//вялая     
    static Point2D po; // должна быть глобальная и для клавиш и мыши
   // static ArrayList<String> listTime = new ArrayList<String>();
    private ChartPanel chartPanel;
    private Crosshair xCrosshair;
    private Crosshair yCrosshair;
    //XYSeriesCollection  xyDataset;
    List<String[]> allRows; // главный лист всех даных обрабатываемых и отображаемых
    String[] listNamedGraph = null; // Список таблиц
    int[] massColum = {1,2,3,4,5,6,7,8,9,10};  // какие номера столбцов рисуем просто по умолчаю вот это
    String[] massName; // Имена полученные в конструкторе
    boolean inversTime = false;
    boolean viewLegend = true;
    XYPlot plot = new XYPlot();
    //List<XYSeriesCollection> listToPlot = new ArrayList();
    List<TimeSeriesCollection> listToPlot = new ArrayList();
    int markF=0; // Переменная для двух маркеров
    int colTwoD = 2;
    Object[] oldDataT = null; // объект предыдущих значений от нажатия
    String beforD = ""; // объект предыдущих значений от нажатия
    List<String> listDiscret = null; //Список найденных дискретов при разборе
    HashMap<String, String> ColorEvent; // Карта цветов которую передадим
    String date1 = "";
    String date2 = "";
    private DefaultTableModel tableModel;
    private DefaultTableModel tableModelEvent;
     // Заголовки столбцов
    private Object[] columnsHeader = new String[] {"Цвет", "Название", date1 , date2};
    //TimeSeriesCollection  xyDataset = (TimeSeriesCollection ) createDataset(); // создадим отдельным элементом для перебора в итератор
    /**
     * Creates new form NewJFrame
     */
     public NewJFrameSimpleGraph_1(String SAMPLE_CSV_FILE_PATH, int[] massColum ,String[] massName , List<String[]> allRows,
             boolean inversTime, boolean viewLegend, HashMap ColorEvent) throws FileNotFoundException, IOException{ 
      this.viewLegend = viewLegend;//
      this.inversTime = inversTime;//
      this.massName = massName;
      this.allRows = null; // пробую чистить память
      this.allRows = allRows; // Тут внес последнее это
      this.massColum = massColum;
      this.SAMPLE_CSV_FILE_PATH = SAMPLE_CSV_FILE_PATH;
      this.ColorEvent = ColorEvent;
      //tableModel = new DefaultTableModel(); // модель по умолчанию
      tableModel = (DefaultTableModel) getTableData((String[]) columnsHeader);
      // Определение столбцов
      //tableModel.setColumnIdentifiers(columnsHeader);

        //       super(s);
        //JPanel jpanel = createDemoPanel();
        //jpanel.setPreferredSize(new Dimension(640, 480));
        //add(jpanel);
        //xyDataset = (XYSeriesCollection ) constructorGraph(massColum); // Инициализация тоже должна быть в конструкторе это была рабочая версия
        //plot = constructorGraph(massColum); // вот это и есть две разные шкалы в одном 
        //listToPlot = (List<XYSeriesCollection>) constructorGraph(massColum); // Получаем список объектов для рисования
        listToPlot = (List<TimeSeriesCollection>) constructorGraph(massColum);
        this.addKeyListener(this);
        initComponents();
    }
    public NewJFrameSimpleGraph_1() throws FileNotFoundException, IOException{ 
         //       super(s);
        //JPanel jpanel = createDemoPanel();
        //jpanel.setPreferredSize(new Dimension(640, 480));
        //add(jpanel);
        initComponents();
    }

     private JPanel createContent() {

        { // Это работает с plot но нет работы с мышью и прочим =(
        //JFreeChart  chart = createChart(plot); //  это основное что работало c Plot
            
        // --- Вот новая реализация графика через стронний класс, b ktutyle gthtlftv
       // System.out.println("size listToPlot bytes " + getObjectSize(listToPlot));
       // System.out.println("size listToPlot Mbyte " + getObjectSize(listToPlot)/1024/1024);
        //System.out.println("size int 15 bytes " + getObjectSize(15));
        //byte b = 127;
        //System.out.println("size b 127 bytes " + getObjectSize(b));
        //System.out.println("size String Hellow byte " + getObjectSize("Hellow"));
        //System.out.println("size String Hellow_1 byte " + getObjectSize("Hellow_1"));
        
            
        DualAxisDemoChartPane demo = new DualAxisDemoChartPane(listToPlot, viewLegend, listDiscret);
        // запустим отрисовыку как процесс
        Thread childTread = new Thread(demo);
        childTread.start();
            try {
                childTread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(NewJFrameSimpleGraph_1.class.getName()).log(Level.SEVERE, null, ex);
            }
        listToPlot.clear();
        System.gc();
        this.chartPanel = demo.getChart();
        // this.chartPanel = new ChartPanel(chart); // Так заносим его в панель
        this.chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        this.chartPanel.setBackground(Color.white);  
        this.chartPanel.addChartMouseListener(this);       
        this.chartPanel.setFocusable(true);
        this.chartPanel.addKeyListener(this);        
        CrosshairOverlay crosshairOverlay = new CrosshairOverlay();
        this.xCrosshair = new Crosshair(Double.NaN, Color.RED, new BasicStroke(0f)); // Эта отоброжается
        //this.xCrosshair.setLabelVisible(true); // не отображать показатель по шкале Y
        this.yCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        this.yCrosshair.setLabelVisible(true);
        crosshairOverlay.addDomainCrosshair(xCrosshair);
        crosshairOverlay.addRangeCrosshair(yCrosshair);  //с этим не работает  
        chartPanel.addOverlay(crosshairOverlay); // с этим не работает но в изначальном пашет
         }
        return chartPanel;
    }
     
     

    // это для XYDataset
    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart("Окно просмотра графиков",  // Название графика
              //  "X1", "Y", dataset, PlotOrientation.VERTICAL, false, false, false);  // Первое труе убирает подписи
                  "X1", "Y", dataset, PlotOrientation.VERTICAL, viewLegend, false, false);  // Первое труе убирает подписи
               //JFreeChart chart = ChartFactory.createXYLineChart("Окно просмотра графиков", null, null, dataset); // убрать название осей
        //PlotOrientation.HORIZONTAL, //это направление графиков
        //true, true, false);  // тут названия систем координат
        return chart;
    }
    
    // тут создадим график на основе XYPlot
    private JFreeChart createChart( XYPlot plot) {
        JFreeChart chart = new JFreeChart("MyPlot", getFont(), plot, false);
        chart.setBackgroundPaint(Color.WHITE);
        return chart;
    }
    
    // Чтение файла с данными
    // получаем имена столбцов и может быть формируем правильный arraylist массивов данных
    // Почему то я это не использую
    public String[] getNamedGraph() throws UnsupportedEncodingException, FileNotFoundException, IOException{
      //  InputStreamReader reader1 = new InputStreamReader(new FileInputStream(SAMPLE_CSV_FILE_PATH), "UTF8");  // тут отличия от оригинала так как нужно декодировать
       // CSVReader reader = new CSVReader(reader1, '\t', '"');
        // this.allRows = reader.readAll(); // вот тут я просмотрел
        int strN =0; // Переменная для определнеие строки имени
        for(String[] row : allRows){ 
            if (row.length <=1){ // проверяем есть ли хоть два столбца
              ++strN;
              continue;}
            else {
              boolean acces = false;
              for(int i=0; i<row.length; ++i) // пробегаем по строке 
              {
                  if (checkString(row[i])) acces = false; // Определяем числа ли это если хоть один цыфры то пропуск
                  else acces = true;
              }
              if (acces){
              listNamedGraph = new String[row.length]; // создаем массив такой же длинны как row
              System.arraycopy( row,0 ,listNamedGraph, 0, row.length); // Копируем полностью массив имени
              break;
              }
              ++strN; // Конечное число от куда начинаются данные
              } 
        }
    return listNamedGraph;}
    
    //Фукция проверки строка ли это
    public boolean checkString(String string) {
        try {
            Double.parseDouble(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    // --- Возвращает список с названиями столбцов ---
    String[] getlistNamedGraph(){
        return listNamedGraph;
    }
    
    // --- устанавливаем что рисуем ---
    void setMassColum(int[] massColum ){
      //this.massColum =  = new int[massColum.length]; // создаем массив такой же длинны как row
    System.arraycopy( massColum,0 ,listNamedGraph, 0, massColum.length); // Копируем полностью массив имени
    }
    
    // наши графики из другого проекта // Уже более-менее правильно
   private List<?>  constructorGraph(int[] massColum) throws FileNotFoundException, IOException{ 
       if (listDiscret != null) // Ощищаем этот массив если еще раз загружаем файл
          {listDiscret.clear(); }
       listDiscret= new ArrayList(); // Обнуляем коллекцию Дискретов если второй раз загружаем
      // List<XYSeriesCollection> listXYSerColl = new ArrayList(); // Тут все храним коллекцию
       List<TimeSeriesCollection> listTmeColl = new ArrayList(); // перейти на временную коллекцию
       //getNamedGraph();
        this.massColum = massColum;  // какие столбцы рисуем при вызове
        // какие колонки используем дл рисования 0 это время его не дергаем
        int colcolumn = 0; // тут не верно это все данные тут вообще херня
        int strColumn =0; // Строка с именами
        int propusk = 0;// Сколько строк пропускаем с начала изначально 4
        int tmpColumn =0; 
        if (inversTime){ // если надо инвертируем список с именами и время
          Collections.reverse(listTime);  
        Collections.reverse(allRows); 
        }     
        String[] nameColumn = massName; // массив имен
        // тут упрощенно что бы не накосячить мохоже максимальная длинна
        for(String[] row : allRows){ // выводим все даные
        if (row.length > colcolumn) colcolumn = row.length ;
            ++tmpColumn;
        }
     
        // Создание массива времени  
        /*
        int xe =0;
        for(String[] row : allRows){
      
            Pattern pattern1 = Pattern.compile("^(.*) (.*)$"); 
            Matcher matcher1 = pattern1.matcher(row[0]);
            String date =""; // годы месяцы число
            String time = ""; // время
            if (matcher1.matches()){ 
                date = matcher1.group(1); // годы месяцы число
                time = matcher1.group(2); // время
                //System.out.println(time);
            }
            listTime.add(time);
        }
        */
        // тут создаем коллекцию из массива графиков по количеству столбцов
        //XYSeriesCollection xyserColl = new XYSeriesCollection();

        Set<String> listAddedN = new HashSet<String>();  // Лист для вносимых графиков(исключить повторение)
        int tegDiscret = 0; // на сколько сдвигать дискреты
        //не видит какие столбцы мы передали
        TimeSeriesCollection timeCollToDiscret = new TimeSeriesCollection(); // Специально для дескретов
        for(int j=0; j<massColum.length; ++j){
            int stolb = massColum[j]; // Определенные столбцы
            boolean fDiscret = false; // Переменные для поиска дискретов
            for (int ic=0; ic<colcolumn; ++ic ){ //перебираем нужные столбцы
                if (ic==stolb){ // нужный столбец      
                String nameG = nameColumn[ic];
                
                // тут создаем коллекцию из массива графиков по количеству столбцов
                //XYSeriesCollection xyserColl = new XYSeriesCollection(); // Эта коллекция больше не нужна
                TimeSeriesCollection timeColl = new TimeSeriesCollection();
                //nameG = new String(nameG.getBytes("KOI8_R"));
                //nameG = new String(nameG.getBytes("Cp1251"));

         // Переборка данных для анализа нет ли повторения, почему то это не работает
         /*       Iterator<String> i = listAddedN.iterator();
                boolean accesaddGra = true;
                while (i.hasNext()){
                  String tmpelemL = i.next();
                  //System.out.println(tmpelemL);
                    if (tmpelemL.equals(nameG)){
                        // Всплывающий диалок сообщение
                        JOptionPane.showMessageDialog(null, "Есть совпадения столбцов, следующий " + nameG + " будет исключен"); // Сообщение о повторении солбцов
                        accesaddGra = false;
                    }
                }
          */
            // Тригер проверки на совпадение
            //if (!accesaddGra){continue;}
            listAddedN.add(nameG); // а тут уже в список добавляем после проверки если нет
              
            //XYSeries tmp = new XYSeries(nameG); // вот тут как то надо смотреть списки имен
            TimeSeries timetmp = new TimeSeries(nameG);
            //tmp.setDescription(nameG);// вносим описание это тмя что бы вывести в поле в дальнейшем
            timetmp.setDescription(nameG); // вносим описание это тмя что бы вывести в поле в дальнейшем
            
            int xi =0;
            boolean beforeEv = false; // значение для предыдущего элемента для True FALSE
            for(String[] row : allRows){
                int disTrig = 0;// триггер true для отрисовки Дискретов(или я дебеил или лыжи не едут, рисует по две точки)
                Date date1 = null;  
                    try {
                        // такой формат даты его не устраивает нужно TimeSeriesDataItem
                        date1 = new SimpleDateFormat("dd.MM.yyyy hh:mm:sss.SSS").parse(row[0]);
                    } catch (ParseException ex) {
                        Logger.getLogger(NewJFrameSimpleGraph_1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // тогда так
                 // регулярка для времени 31.05.19 11:40:47.000
                 Pattern pattern1 = Pattern.compile("^(([0-9]{1,2})\\.([0-9]{1,2})\\.([0-9]{1,4})) (([0-9]{1,2}):([0-9]{1,2}):([0-9]{1,2})\\.([0-9]{1,3}))$"); 
                 // еще один патерн времени 2019.05.27 02:04:29.507
                 Pattern pattern2 = Pattern.compile("^(([0-9]{4})\\.([0-9]{2})\\.([0-9]{2})) (([0-9]{2}):([0-9]{2}):([0-9]{2})\\.([0-9]{3}))$"); 
                 Matcher matcher1 = pattern1.matcher(row[0]);
                 Matcher matcher2 = pattern2.matcher(row[0]);
                 int millisecond,  second, minute, hour, day, month,year;
                 Millisecond currentTimeinit = null;
                 if (matcher1.matches()){ 
                  day = Integer.parseInt(matcher1.group(2)); 
                  month = Integer.parseInt(matcher1.group(3)); 
                  year = Integer.parseInt("20" + matcher1.group(4)); // такие приколы с годом
                  hour = Integer.parseInt(matcher1.group(6)); 
                  minute = Integer.parseInt(matcher1.group(7)); 
                  second = Integer.parseInt(matcher1.group(8)); 
                  millisecond = Integer.parseInt(matcher1.group(9));
                  // парсер работает
                  //System.out.println(month + "\t" + day + "\t" + year + "\t" + hour + "\t" + minute + "\t" + second + "\t" + millisecond);
                  currentTimeinit = new Millisecond(millisecond,  second, minute, hour, day, month,year);
                }
                  if (matcher2.matches()){ 
                  day = Integer.parseInt(matcher2.group(4)); 
                  month = Integer.parseInt(matcher2.group(3)); 
                  year = Integer.parseInt(matcher2.group(2));
                  hour = Integer.parseInt(matcher2.group(6)); 
                  minute = Integer.parseInt(matcher2.group(7)); 
                  second = Integer.parseInt(matcher2.group(8)); 
                  millisecond = Integer.parseInt(matcher2.group(9));
                  // парсер работает
                  //System.out.println(month + "\t" + day + "\t" + year + "\t" + hour + "\t" + minute + "\t" + second + "\t" + millisecond);
                  currentTimeinit = new Millisecond(millisecond,  second, minute, hour, day, month,year);
                }
                
            // Как то их надо помещать в одну коллекцию    
           if (row.length > ic && row[ic] != null){ // Проверяем на пустоту  элемента массива
               // System.out.println(row[ic]);
                String item = row[ic];
             if (item.equals("false") || item.equals("true") || item.equals("FALSE") || item.equals("TRUE") || item.equals(null) || item.equals("")){ // ищет false true еще надо время
               // System.out.println("Find True false");
                if (item.equals(null) || item.equals("")){
                    //tmp.add(xi, Float.valueOf(0));
                    timetmp.add( currentTimeinit, Float.valueOf(0));
                }
                if (item.equals("false") || item.equals("FALSE") || item.equals("true") || item.equals("TRUE")){
                    if (fDiscret == false){ // если первый рах нашли Дискрет
                        fDiscret = true;
                        ++tegDiscret;
                    }
                        if (item.equals("false") || item.equals("FALSE")){
                            //tmp.add(xi, Float.valueOf(0+tegDiscret));
                           // timetmp.add( currentTimeinit, Float.valueOf(0+tegDiscret));
                            if (disTrig%2==0){ // а вот и тупизм по модулю. для отрисовки
                                //tmp.add(xi, Float.valueOf(0));
                                timetmp.add( currentTimeinit, 0);
                            }else{
                                //tmp.add(xi, 0);
                                timetmp.add( currentTimeinit, 0);
                            }
                        }
                        if( item.equals("true") || item.equals("TRUE")){
                            //tmp.add(xi, Float.valueOf(0+tegDiscret));  // Изменил  на 100 что бы видно на графиках было
                            timetmp.add( currentTimeinit, Float.valueOf(0+tegDiscret));
                            ++disTrig;   
                        }
                }
                
                }else{
                 float tmpErrorF;
                 try{
                     tmpErrorF = Float.valueOf(row[ic]);}
                 catch(Exception e){
                     System.out.println("Find bed data in file--> " + row[ic]);
                     tmpErrorF = 0;
                 }
                //tmp.add(xi, Float.valueOf(row[ic]));
                //System.out.println("whis data currentTimeinit " + currentTimeinit +  "error " + row[ic]);
                timetmp.add( currentTimeinit, tmpErrorF);
                 }
            }
         ++xi;
      }
    // Пробуем красить но тут не катит в другом месте определяеся Plot
        if(fDiscret == true){ // если дискрет         
         //System.out.println("This item Discret Name--> " +nameG+ " " + listTmeColl.size());
         timeCollToDiscret.addSeries(timetmp);
         listDiscret.add(nameG);
        }else{
            //xyserColl.addSeries(tmp); // Добавляем в коллекцию график // если одинаковое вхождение надо это обралить.
            timeColl.addSeries(timetmp); // и так же временные тоже добавляем      
        }
        if (timeColl.getSeries().size()>0){
            listTmeColl.add(timeColl); // так вносим массив коллексии времени
        }
        //System.out.println("Size_listDiscret  " +    listDiscret.size());
      break; // если нашли нужные столбец и не нужно еще раз прогонять
      } else continue;// if проверки столбца
    }
    
    } 
    //listTmeColl.add(timeCollToDiscret);
    //return xyserColl; // это возвращали до этого
      
     plot.setDomainAxis(new NumberAxis("X Axis")); // это как называется X     
     plot.setDomainCrosshairVisible(true);
     //return listXYSerColl;
     if (timeCollToDiscret.getSeries().size()>0){
       listTmeColl.add(timeCollToDiscret);
     }
     return listTmeColl;
    }
   
    // нажатие мыши на графике
    @Override
    public void chartMouseClicked(ChartMouseEvent event) {
        
        // при нажатии очищаме таблицу и сохраняем если что то есть во втором столюце
        int columnTM = tableModel.getRowCount();
        oldDataT = new Object[columnTM];
        for(int i=0; i<columnTM; ++i){
          //System.out.println(tableModel.getValueAt(i, colTwoD));
          Object tmpObj = tableModel.getValueAt(i, colTwoD);
          oldDataT[i]=tmpObj;
        }
        tableModel.setRowCount(0);
      
        // Собираем данные с экрана крафика
        Rectangle2D dataArea = this.chartPanel.getScreenDataArea();
        JFreeChart chart = event.getChart();
        XYPlot plot_m = (XYPlot) chart.getPlot();
        
        ValueAxis xAxis = plot_m.getDomainAxis();
        DateAxis dAxis = (DateAxis) plot_m.getDomainAxis();// так попробуем дату достать
        double x = xAxis.java2DToValue(event.getTrigger().getX(), dataArea, RectangleEdge.BOTTOM);
     // ---  Далеее не нужно сделаем так ---
        Calendar calendar = Calendar.getInstance();
        String dateTimeFromGraph = "";
        calendar.setTimeInMillis((long) x);
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss.S");
        dateTimeFromGraph = formatter.format(calendar.getTime());
        jTextArea1.setText(dateTimeFromGraph + " -- " + new String(beforD));
        beforD = dateTimeFromGraph;
        /*
        // нашел между двумя точками 
        int onePoint;
        int secondPoint;
        if (DatasetUtilities.findItemIndicesForX(plot_m.getDataset(), 0, x).length == 2){
            // Какое то блядство по этому условие костыль
          onePoint = DatasetUtilities.findItemIndicesForX(plot_m.getDataset(), 0, x)[0];
          //if (onePoint<0){onePoint=0;}
          secondPoint = DatasetUtilities.findItemIndicesForX(plot_m.getDataset(), 0, x)[1];
          //if (secondPoint<0){secondPoint=0;}
        } else{
          onePoint = DatasetUtilities.findItemIndicesForX(plot_m.getDataset(), 0, x)[0];
          //if (onePoint<0){onePoint=0;}
          secondPoint = 0;
        }
        */
       // System.out.println("Lenght --> " + DatasetUtilities.findItemIndicesForX(plot_m.getDataset(), 0, x).length);
       // System.out.println("info 0 --> " + DatasetUtilities.findItemIndicesForX(plot_m.getDataset(), 0, x)[0]);
       // System.out.println("info 1 --> " + DatasetUtilities.findItemIndicesForX(plot_m.getDataset(), 0, x)[1]);
       // this.xCrosshair.setValue(x); // это реализация движения шкалы по x y внизу
       //  this.yCrosshair.setValue(y);
        
       // --- Так вводим два маркера ---
        ValueMarker marker1 = new ValueMarker(x);
        ValueMarker marker2 = new ValueMarker(x);
        marker1.setPaint(Color.red);
        marker1.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
        marker1.setLabelTextAnchor(TextAnchor.TOP_LEFT);
        marker1.setLabelBackgroundColor(java.awt.Color.WHITE); // не работает
        marker1.setLabelFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        //marker1.setLabelPaint(Color.CYAN);
        //marker1.setLabelPaint(Color.yellow);
        marker1.setLabel("Mark1");
        
        marker2.setPaint(Color.BLUE);
        marker2.setLabel("Mark2");
        marker2.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
        marker2.setLabelTextAnchor(TextAnchor.TOP_LEFT);
        marker2.setLabelBackgroundColor(java.awt.Color.WHITE); // не работает
        marker2.setLabelFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
       // marker2.setLabelBackgroundColor(Color.yellow);
        //marker2.setLabelPaint(Color.darkGray);
         switch(markF){
              case(0):{ // Первое нажатие
              ++markF;
              plot_m.addDomainMarker(marker1);
              date1 = dateTimeFromGraph;
              //date2 = beforD;
              break;
              }
              case(1):{
              ++markF;
              plot_m.addDomainMarker(marker2);
              //date1 = beforD;
              date2 = dateTimeFromGraph;
              break;
              }
              case(2):{
              markF = 1;
              List tmpL =  new ArrayList(plot_m.getDomainMarkers(Layer.FOREGROUND));
              Iterator iter =  tmpL.iterator();
              while(iter.hasNext()){
                  ValueMarker tmpMarker = (ValueMarker) iter.next();
                  plot_m.removeDomainMarker(tmpMarker);
              }
              //oldDataT = null; // для ощищения второго столбаца
              date1 = dateTimeFromGraph;
              date2 = null;
              oldDataT = null;
              plot_m.addDomainMarker(marker1);
              marker1.setPaint(Color.red);
              break;
              }
              default:
               markF=0;   
               plot_m.clearDomainMarkers(); // Очищаме маркеры  Y
               break;
          } 
       
        int tmppoti = plot_m.getDatasetCount();
        //XYDataset testDataset = plot_tmp.getDataset();
        //System.out.println("Skolko collection ->> " +tmppoti); // Сколько коллекций Plot не факт
        List<?> tmp_lise = new ArrayList();
//        XYItemRenderer renderMyPlot = plot_m.getRenderer();// получим чем рисовали
        // Просто так не отрисовать по этому надо массив сначала вытачить цветов или нет?
        List<Color>  listColorGraph = new ArrayList(); // массив цвета
        boolean whocol = true;
        for(int i=0; i<tmppoti; ++i){
            XYItemRenderer renderMyPlot = plot_m.getRenderer(i);// получим чем рисовали
            Object ob = plot_m.getDataset();
            //System.out.println("Color?--> " + renderMyPlot.getSeriesPaint(0)); // Точно ли цвет?         
            //Paint  paintHZgraph = renderMyPlot.getSeriesPaint(0);
            
            //listColorGraph.add((Color)renderMyPlot.getSeriesPaint(0)); // Заносим цвет в массив
            //System.out.println(plot_m.getDataset(i).getClass().getSimpleName());
            String nameObj = ob.getClass().getSimpleName(); // таже ошибка если моим методом
            if(nameObj.equals("XYSeriesCollection")){
                XYSeriesCollection tmpSeries = (XYSeriesCollection) plot_m.getDataset(i);
                tmp_lise = tmpSeries.getSeries();
                whocol = true;
            }
            if(nameObj.equals("TimeSeriesCollection")){
                TimeSeriesCollection tmpSeries = (TimeSeriesCollection) plot_m.getDataset(i);
                tmp_lise  = tmpSeries.getSeries();
                whocol = false;
            }
              
              //System.out.println(nameObj);
             // System.out.println(plot_tmp.getDataset(i).getSeriesCount());
             
              if (whocol){
              ListIterator<XYSeries> iterator = (ListIterator<XYSeries>) tmp_lise.listIterator(); 
              String toFieadTXT = "";
              String curentTime = "";
              
              int iWhile = i; // это если есть в коллекции еще графики. но должно работать
              int intSeries =0;
              while (iterator.hasNext()) {  
                
                XYSeries tmpElemXY = iterator.next();
                String  XYDescription = tmpElemXY.getDescription();// получить описание
                new dataFromGraph(tmpElemXY, x); // передаем данные в статический клас вместо метода Интерполяции
                dataFromGraph.setItem(); // тут вываливаемся.
                int iTime = dataFromGraph.getCurrentTime();// получим время из созданного листа
                //curentTime = listTime.get(iTime);
                String formattedDouble = new DecimalFormat("#0.000000").format(dataFromGraph.getItemData());
                toFieadTXT += XYDescription + "->" + formattedDouble +"\n" ;
                
                // так заносим в таблицу
                int idx = jTable1.getSelectedRow();// Номер выделенной строки 
                //System.out.println("Number idx --> " + idx);
                
                // Вставка новой строки 
                ValueAxis range = plot_m.getRangeAxis(i);
                boolean axVus = range.isVisible();
                boolean showG = plot_m.getRenderer(i).isSeriesVisible(intSeries); // визуализирован ли график не работает                    
                //boolean showG = true;
                range.setVisible(false);
                // предыдущее занчение от нажатие
                Object beforeVoid = null;
                if (oldDataT.length>iWhile){
                  beforeVoid = oldDataT[iWhile];
                } 
                tableModel.addRow( new Object[] {
                                              null,
                                              XYDescription, 
                                              formattedDouble, beforeVoid, axVus, showG});
                
                // Так красим столбец нам не годится
                int xT = jTable1.getRowCount();
                int yT =  jTable1.getColumnCount();
                //System.out.println("Stroki -- > " + jTable1.getRowCount() +"Stolbec -- > " + jTable1.getColumnCount());
                //TableColumn column = jTable1.getColumnModel().getColumn(idx +1);  
                DefaultTableCellRenderer renderer1 = new DefaultTableCellRenderer();
                //myTableCellRenderer_ver2 renderer = new myTableCellRenderer_ver2( (Color) paintHZgraph);
                //renderer.setBackground((Color) paintHZgraph);
                renderer1.setBackground(Color.yellow);
                //renderer.setForegound(Color.yellow);
                //column.setCellRenderer(renderer);
                
               
                //jTable1.setDefaultRenderer(jTable1.getColumnClass(i), new myTableCellRenderer_ver3((Color) paintHZgraph));
                ++iWhile;
                ++intSeries;
              }              
              toFieadTXT += "\n" + "Time graph --> " + curentTime + "\n";  
              jTextArea1.setText(curentTime);
              }// закончили проверку что коллекция XYSeriesCollection
                 if (!whocol){
                   ListIterator<TimeSeries> iterator = (ListIterator<TimeSeries>) tmp_lise.listIterator(); 
                    int iWhile = i;
                    int intSeries = 0;
                    while (iterator.hasNext()) {                      
                     TimeSeries tmpElemXY = iterator.next();
                     String  XYDescription = tmpElemXY.getDescription();// получить описание
                     // сразу проверяем не дискрет ли этот график
                    boolean finD = false;                     
                    for( int d =0; d<listDiscret.size(); ++d){
                      if(XYDescription.equals(listDiscret.get(d))){
                        finD = true;
                      }
                    }
                     double valueY = DatasetUtilities.findYValue(plot_m.getDataset(i), intSeries , x);                     String formattedDouble = new DecimalFormat("#0.000000").format(valueY);
                     formattedDouble = new DecimalFormat("#0.00000").format(valueY);
                     // Если нашли дискрет прикручиваем ему 1 в значение
                     if (finD == true & valueY>0){
                       valueY = 1;
                       formattedDouble = new DecimalFormat("#0").format(valueY);
                     }
                     listColorGraph.add((Color)renderMyPlot.getSeriesPaint(intSeries)); // Заносим цвет в массив
                     
                     //System.out.println(valueY + "valueX" + x);
                     ValueAxis range = plot_m.getRangeAxis(i);
                     boolean showG = plot_m.getRenderer(i).isSeriesVisible(intSeries); // визуализирован ли график не работает                    
                     //System.out.println(plot_m.getRenderer(i).isSeriesVisible(intSeries));
                     //boolean showG = true;
                     boolean axVus = range.isVisible();
                    // предыдущее занчение от нажатие
                    Object beforeVoid = null;
                    if (oldDataT != null && oldDataT.length>iWhile){
                      beforeVoid = oldDataT[iWhile];
                    } 
                    if (markF%2 == 0){
                     tableModel.addRow( new Object[] {
                                              null,
                                              XYDescription, 
                                              beforeVoid, formattedDouble, axVus, showG});
                    }else{
                     tableModel.addRow( new Object[] {
                                              null,
                                              XYDescription, 
                                              formattedDouble, beforeVoid, axVus, showG});
                    }
                    //  System.out.println(i);
                     ++iWhile;
                     ++intSeries;
                    }
                 }
        }
         // так красим нашу таблицу
         jTable1.getColumnModel().getColumn(0).setCellRenderer(new myTableCellRenderer_ver2(listColorGraph));
        // Данные времени  в названиях таблицы
        JTableHeader th = jTable1.getTableHeader();
        jTable1.getColumnModel().getColumn(2).setHeaderValue(date1);
        jTable1.getColumnModel().getColumn(3).setHeaderValue(date2);
        th.repaint(); // без этого не работает отрисовка
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent event) {
        //закоментировал что бы можно было следить за данными по клику
        // тут похоже реализовавает движение по графику
        Rectangle2D dataArea = this.chartPanel.getScreenDataArea();
        JFreeChart chart = event.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis xAxis = plot.getDomainAxis();
        double x = xAxis.java2DToValue(event.getTrigger().getX(), dataArea, RectangleEdge.BOTTOM);
       // double y = DatasetUtilities.findYValue(plot.getDataset(), 0, x); // тут похоже находим координаты нашего y
        
        this.xCrosshair.setValue(x); // это реализация движения шкалы по x y внизу

      //  this.yCrosshair.setValue(y);
    }
    
    
    // --- Метобы реализации слушателя клавиш ---    
    //клавиша нажата и отпущена
    @Override
    public void keyTyped(KeyEvent e) {
         System.out.println(e.getKeyCode());
      /*  if (e.getKeyCode()==KeyEvent.KEY_LOCATION_LEFT | e.getKeyCode()==KeyEvent.KEY_LOCATION_RIGHT) {
            System.out.println("cod_key " + e.getKeyCode());
        }
        */
         /*
        Rectangle2D dataArea = this.chartPanel.getScreenDataArea();
        JFreeChart chart = this.chartPanel.getChart();
        XYPlot plot_m = (XYPlot) chart.getPlot();
        List tmpL =  new ArrayList(plot_m.getDomainMarkers(Layer.FOREGROUND));
        Iterator iter =  tmpL.iterator();
        while(iter.hasNext()){
                  ValueMarker tmpMarker =  (ValueMarker) iter.next();
                  double tmpVmark = tmpMarker.getValue();
                  //plot_m.removeDomainMarker(tmpMarker);
                  tmpMarker.setValue(tmpVmark+100);
                  //plot_m.removeDomainMarker(tmpMarker);
                  //marker1.setPaint(Color.red);
                  
                  System.out.println(tmpMarker.getLabel());
                 // plot_m.clearDomainMarkers();
              }
              
               //plot_m.clearDomainMarkers(); // Очищаме маркеры  Y
           */    
    }      
    //клавиша нажата, но не отпущена
    // странно по стрелкам только тут работает
    @Override
    public void keyPressed(KeyEvent e) {
      System.out.println(e.getKeyCode());  
      JFreeChart chart = this.chartPanel.getChart();
      XYPlot plot_m = (XYPlot) chart.getPlot();
      List tmpL =  new ArrayList(plot_m.getDomainMarkers(Layer.FOREGROUND));
      Iterator iter =  tmpL.iterator();
      int smesh =0;
      String nameCaseM = "";
      
      switch(e.getKeyCode()){
          case (37):{  // лево
              smesh =-100;
              nameCaseM = "Mark1";
              break;
          }
          case (65):{  // A
              smesh =-100;
              nameCaseM = "Mark2";
              break;
          }
          case (39):{ // Право
              smesh =100;
              nameCaseM = "Mark1";
              break;
          }
          case (68):{ // D
              smesh =100;
              nameCaseM = "Mark2";
              break;
          }
      }
        while(iter.hasNext()){
                  ValueMarker tmpMarker =  (ValueMarker) iter.next();
                  double tmpVmark = tmpMarker.getValue();
                  if(tmpMarker.getLabel().equals(nameCaseM)){
                      tmpMarker.setValue(tmpVmark+smesh);
                      updateYdata(nameCaseM, tmpVmark+smesh, chart);
                  }
              }
    }
    //клавиша отпущена
    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println(e.getKeyCode());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = createContent();
        jPanel5 = new javax.swing.JPanel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFocusable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jSplitPane2.setAutoscrolls(true);

        jPanel3.setFocusable(false);

        jScrollPane1.setFocusable(false);

        jTable1.setModel(tableModel);
        jTable1.setToolTipText("");
        jTable1.setFocusable(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
            .addComponent(jTextArea1)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jTextArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE))
        );

        jSplitPane2.setRightComponent(jPanel3);

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel1.setFocusCycleRoot(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 742, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 356, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jToggleButton1.setText("Загрузить журнал событий");
        jToggleButton1.setToolTipText("");
        jToggleButton1.setFocusable(false);
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToggleButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToggleButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel5);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jSplitPane2.setLeftComponent(jPanel4);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane2)
                .addContainerGap())
        );

        jMenu1.setText("File");
        JMenuItem print = new JMenuItem("Print");
        jMenu1.add(print);
        //print.addActionListener();
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // Цвет фона окнас логами // работает в отдельно тут нет
        // покрасить удалось только так(Наркомания надо разобраться почему)
        Color bgColor = Color.DARK_GRAY;
        UIDefaults defaults = new UIDefaults();
        defaults.put("TextPane.background", new ColorUIResource(bgColor));
        defaults.put("TextPane[Enabled].backgroundPainter", bgColor);
       // jTextPane1.putClientProperty("Nimbus.Overrides", defaults);
       // jTextPane1.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
       // jTextPane1.setBackground(bgColor);
        
       //StyledDocument doc = jTextPane1.getStyledDocument();
        File file = null;
        List<String> listEventFormat = null;
        String fPath = "";
        List<Color>  listColorGraph = new ArrayList(); // массив цвета
        JFileChooser fileopen = new JFileChooser(System.getProperty("user.dir"));
        int ret = fileopen.showDialog(null, "Чтение файла с данными");
        if (ret == JFileChooser.APPROVE_OPTION) {
            file = fileopen.getSelectedFile();
            fPath = file.getPath();
        }
        int maxLsplit=0;
        try {
             listEventFormat = new CheckAndConstractionDEvent(file).getData();
             if (!listEventFormat.isEmpty()){
             for(String s : listEventFormat){
                 int LinS =  s.split("\t").length;
                 if (maxLsplit > LinS){
                     continue;
                 }else maxLsplit = LinS;
             }
        }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JavaApplication5.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JavaApplication5.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Object[] columnsHeaderEvent = new String[]{null, null, null};
        tableModelEvent = new DefaultTableModel();
        //сколько столбцов по разделителям
        for (int i=0; i<maxLsplit; ++i){tableModelEvent.addColumn(i);}
        jTable2.setModel(tableModelEvent);
        int sumC = jTable2.getColumnModel().getColumnCount(); // сколько столбцов
        // заполняем таблицу
        if (!listEventFormat.isEmpty()){
             int row =0;
             for(String s : listEventFormat){
                String fC = checkStringColor(s);
                Color c =stringToColor(fC);
                listColorGraph.add(c);
                String[] tmpMas =  s.split("\t");
                tableModelEvent.addRow(tmpMas);
                
                //for (int i=0; i<sumC; ++i){
                  // так не работает красит только последнюю строку
                  //jTable2.getColumnModel().getColumn(i).setCellRenderer(new myTableCellRenderer(row,i,c));
                //}
                //jTable2.getColumnModel().getColumn(1).setCellRenderer(new myTableCellRenderer(row,1,c));
                ++row;
                
             }
        jTable2.getColumnModel().getColumn(0).setCellRenderer(new myTableCellRenderer_ver2(listColorGraph));
        /*for (int i=0; i<sumC; ++i){// красим все столбцы
            //jTable2.getColumnModel().getColumn(i).setCellRenderer(new myTableCellRenderer_ver2(listColorGraph));
            Color c = new Color(200,100,200);
            Color c1 = new Color(100,220,220);
            jTable2.getColumnModel().getColumn(i).setCellRenderer(new myTableCellRenderer(2,i,c));
            //jTable2.getColumnModel().getColumn(i).setCellRenderer(new myTableCellRenderer(0,i,c1));
        }
        */
        }
        

        /*
        InputStreamReader inputsream; 
        try {
            inputsream = new InputStreamReader(new FileInputStream(fPath), "UTF8"); // Правильное отображение русскаого языка чтение буыера такое себе
       
        try(BufferedReader bufferedReader = new BufferedReader(inputsream)) {
        String line = bufferedReader.readLine();
        while(line != null) {
            String fC = checkStringColor(line);
            if(fC !=null){
              // так преобразовываем цвет
              Color c =stringToColor(fC);
              SimpleAttributeSet atrFromF = new SimpleAttributeSet();
              StyleConstants.setForeground(atrFromF, c);
              doc.insertString(doc.getLength(), line + " ", atrFromF);
            }else doc.insertString(doc.getLength(), line + " ", null); 
               doc.insertString(doc.getLength(), "\n ", null); // так просто новая строка
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        } catch (FileNotFoundException e) {
            // exception handling
        } catch (IOException e) {
            // exception handling
        } 
        }catch (UnsupportedEncodingException ex) {
            Logger.getLogger(NewJFrameSimpleGraph_1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewJFrameSimpleGraph_1.class.getName()).log(Level.SEVERE, null, ex);
        
        } catch (BadLocationException ex) {
                      Logger.getLogger(NewJFrameSimpleGraph_1.class.getName()).log(Level.SEVERE, null, ex);
                  }
        jTextPane1.setStyledDocument(doc);  // тут мы апрсто засовываем сформированный документ
        */
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyPressed
 
    
    // Фукция нажатия на кнопку Таблицы определение по правой кнопке и левой кнопке
    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
    if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) { // right mouse
        JFreeChart chart = chartPanel.getChart();
        XYPlot plot_m = (XYPlot) chart.getPlot();

        int row = jTable1.rowAtPoint(evt.getPoint()); // хитрое вычисление где нажато
        int column = jTable1.columnAtPoint(evt.getPoint());
        if (column == 0){        
            ++column;
            String nameGra =  (String) jTable1.getValueAt(row, column); // так получим имя графика
            PopUpDemo menu = new PopUpDemo(new EditGraph(plot_m, nameGra));
            menu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
        // перерисовываем таблицу из данных новых графиков
         plot_m = (XYPlot) chartPanel.getChart().getPlot();
        List<Color> tmpC =  new EditGraph(plot_m).getListColor();
        jTable1.getColumnModel().getColumn(0).setCellRenderer(new myTableCellRenderer_ver2(tmpC));       
        JTableHeader th = jTable1.getTableHeader();
        th.repaint(); // без этого не работает отрисовка , тут с ней тоже
    }
  /*  if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) { // Left mouse
        JFreeChart chart = chartPanel.getChart();
        XYPlot plot_m = (XYPlot) chart.getPlot();
        int row = jTable1.rowAtPoint(evt.getPoint()); // хитрое вычисление где нажато
        int column = jTable1.columnAtPoint(evt.getPoint());
        if (column == 0){        
        String nameGra =  (String) jTable1.getValueAt(row, column); // так получим имя графика
        PopUpDemo menu = new PopUpDemo(new EditGraph(plot_m, nameGra));
        menu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    
    }
*/    
    }//GEN-LAST:event_jTable1MousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
      
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    try {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(NewJFrameSimpleGraph_1.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(NewJFrameSimpleGraph_1.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(NewJFrameSimpleGraph_1.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(NewJFrameSimpleGraph_1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
         
        //</editor-fold>

        /* Create and display the form */
        //Запуск в потоке из этого класса
        /*
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new NewJFrameSimpleGraph_1().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(NewJFrameSimpleGraph_1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }); 
        */
    }
    }
    
    // -- Тестовая для возвращения XYplot ---
    public XYPlot getXYplot(){
     return this.plot;
    }
    
    // --- поискстроки по шаблонам возвращаем название цвета--
    String checkStringColor(String str){
     String fcolor = "gray";
     for(java.util.Map.Entry<String, String> entry : ColorEvent.entrySet()) { // еще раз прогнать получить имена
        String key = entry.getKey();
        String color = entry.getValue();
        Pattern pattern1 = Pattern.compile("^.*"+key+".*$");
        Matcher matcher1 = pattern1.matcher(str);
        if (matcher1.matches()){
            fcolor = new String(color);
       }      
     }
        return fcolor;
    }
    
    // --- Преобразование строки в Цвет --- 
    public static Color stringToColor(final String value) {
    if (value == null) {
      return Color.black;
    }
    try {
      // get color by hex or octal value
      return Color.decode(value);
    } catch (NumberFormatException nfe) {
      // if we can't decode lets try to get it by name
      try {
        // try to get a color by name using reflection
        final Field f = Color.class.getField(value);

        return (Color) f.get(null);
      } catch (Exception ce) {
        // if we can't get any color return black
        return Color.black;
      }
    }
  }
    
 // --- функция для создания списка из таблиц базы так же возращаем объект для конструкции таблицы при запуске ---
 TableModel getTableData(String[] columnNames)  { 
    // Можно так сложно не соединять, аппендицит от предыдущего что бы не запутаться
    String[] columnDop = {"Шкала измерений", "Показать график"};// до поля для галок или еще чего
    String[] resultColumn = Stream.concat(Arrays.stream(columnNames), Arrays.stream(columnDop))
            .toArray(String[]::new); // соединяем два массива
    return new DefaultTableModel(null, resultColumn){ 
    @Override           
    public Class<?> getColumnClass(int columnIndex) { // структура для отображения таблицы с галками
      Class clazz = String.class;
      switch (columnIndex) {
     //   case 0:
     //     clazz = Integer.class;
     //     break;
     /*   case 1:
          clazz = Integer.class;
          break; */
        case 4:
          clazz = Boolean.class;
          break;
      case 5:
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
        JFreeChart chart = chartPanel.getChart();
        XYPlot plot_m = (XYPlot) chart.getPlot();
      // Условие проверки галочки скрывать легенду
      if (aValue instanceof Boolean && column == 4) {
        //System.out.println("Posution - > " + row + " " + aValue);        
        ValueAxis range = plot_m.getRangeAxis(row);
        Vector rowData = (Vector)getDataVector().get(row); // не помню для чего но без этого только скрывает =(
        rowData.set(4, (boolean)aValue);
        fireTableCellUpdated(row, column);
        // Само действие
        try {
        if((boolean) aValue == true){
          range.setVisible(true);
        }
        if((boolean) aValue == false){
          range.setVisible(false);
        }
        }catch (NullPointerException e) {
        JOptionPane.showMessageDialog(null, "Невозможно скрыть шкалу отдельно, работает только первая в списке. ");
        }
      }
      // Условие проверки галочки скрывать график
      if (aValue instanceof Boolean && column == 5) {
        //System.out.println("Posution - > " + row + " " + aValue); 
      try {
        XYItemRenderer tmpRender = plot_m.getRenderer(row);
        Vector rowData = (Vector)getDataVector().get(row); // не помню для чего но без этого только скрывает =(
        rowData.set(5, (boolean)aValue);
        String nameGra =  (String) rowData.get(1); // так получим имя графика
        fireTableCellUpdated(row, column);
        
        // Прогоним все это для поиске данных в коллекциях
        new EditGraph(plot_m, nameGra).setView((boolean)aValue);
        // Само действие(временно закоментировали)
        if((boolean) aValue == true){
          //tmpRender.setSeriesVisible(true);
        }
        if((boolean) aValue == false){
          //tmpRender.setSeriesVisible(false);
        }
      }catch (NullPointerException e) {
        JOptionPane.showMessageDialog(null, "Невозможно скрыть отдель, работает только первая в списке. ");

      }
      }
    }
    };
}

 
 // Главная фукция определения данных по осям и рисованию маркеров(Какой маркер и координаты X)
 // На вход какой маркер, координаты X , и поле с графиком
 void updateYdata(String Mark, double x, JFreeChart chart){
         
        if (Mark.equals("Mark1")){
          colTwoD =3;
        }if (Mark.equals("Mark2")){
          colTwoD =2;
        }
        //System.out.println("colTwoD " + colTwoD);
        // при нажатии очищаме таблицу и сохраняем если что то есть во втором столюце
        int columnTM = tableModel.getRowCount();
        oldDataT = new Object[columnTM];
        for(int i=0; i<columnTM; ++i){
          //System.out.println(tableModel.getValueAt(i, colTwoD));
          Object tmpObj = tableModel.getValueAt(i, colTwoD);
          oldDataT[i]=tmpObj;
        }
        tableModel.setRowCount(0);
      
        // Собираем данные с экрана графика
        Rectangle2D dataArea = this.chartPanel.getScreenDataArea();
        XYPlot plot_m = (XYPlot) chart.getPlot();
        // ---  Далеее не нужно сделаем так ---
        Calendar calendar = Calendar.getInstance();
        String dateTimeFromGraph = "";
        calendar.setTimeInMillis((long) x);
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss.S");
        dateTimeFromGraph = formatter.format(calendar.getTime());
        
        if (Mark.equals("Mark1")){
          date1 = dateTimeFromGraph;
        }if (Mark.equals("Mark2")){
          date2 = dateTimeFromGraph;
        }
        
        jTextArea1.setText(dateTimeFromGraph + " -- " + new String(beforD));
        beforD = dateTimeFromGraph;
        int tmppoti = plot_m.getDatasetCount();
        List<?> tmp_lise = new ArrayList();
        List<Color>  listColorGraph = new EditGraph(plot_m).getListColor(); // массив цвета
        boolean whocol = true;
        for(int i=0; i<tmppoti; ++i){
            Object ob = plot_m.getDataset();
            String nameObj = ob.getClass().getSimpleName(); // таже ошибка если моим методом
            if(nameObj.equals("XYSeriesCollection")){
                XYSeriesCollection tmpSeries = (XYSeriesCollection) plot_m.getDataset(i);
                tmp_lise = tmpSeries.getSeries();
                whocol = true;
            }
            if(nameObj.equals("TimeSeriesCollection")){
                TimeSeriesCollection tmpSeries = (TimeSeriesCollection) plot_m.getDataset(i);
                tmp_lise  = tmpSeries.getSeries();
                whocol = false;
            }
              if (whocol){
              ListIterator<XYSeries> iterator = (ListIterator<XYSeries>) tmp_lise.listIterator(); 
              String toFieadTXT = "";
              String curentTime = "";
              
              int iWhile = i; // это если есть в коллекции еще графики. но должно работать
              int intSeries =0;
              while (iterator.hasNext()) {                 
                XYSeries tmpElemXY = iterator.next();
                String  XYDescription = tmpElemXY.getDescription();// получить описание
                new dataFromGraph(tmpElemXY, x); // передаем данные в статический клас вместо метода Интерполяции
                dataFromGraph.setItem(); // тут вываливаемся.
                int iTime = dataFromGraph.getCurrentTime();// получим время из созданного листа
                //curentTime = listTime.get(iTime);
                String formattedDouble = new DecimalFormat("#0.000000").format(dataFromGraph.getItemData());
                toFieadTXT += XYDescription + "->" + formattedDouble +"\n" ;
                
                // так заносим в таблицу
                int idx = jTable1.getSelectedRow();// Номер выделенной строки 
                //System.out.println("Number idx --> " + idx);
                
                // Вставка новой строки 
                ValueAxis range = plot_m.getRangeAxis(i);
                boolean axVus = range.isVisible();
                boolean showG = plot_m.getRenderer(i).isSeriesVisible(intSeries); // визуализирован ли график не работает                    
                //boolean showG = true;
                range.setVisible(false);
                // предыдущее занчение от нажатие
                Object beforeVoid = null;
                if (oldDataT.length>iWhile){
                  beforeVoid = oldDataT[iWhile];
                } 
                tableModel.addRow( new Object[] {
                                                null, 
                                              XYDescription, 
                                              formattedDouble, beforeVoid, axVus, showG});
                // Так красим столбец нам не годится
                int xT = jTable1.getRowCount();
                int yT =  jTable1.getColumnCount();
                DefaultTableCellRenderer renderer1 = new DefaultTableCellRenderer();
                renderer1.setBackground(Color.yellow);
                ++iWhile;
                ++intSeries;
              }              
              toFieadTXT += "\n" + "Time graph --> " + curentTime + "\n";  
              jTextArea1.setText(curentTime);
              }// закончили проверку что коллекция XYSeriesCollection
                 if (!whocol){
                   ListIterator<TimeSeries> iterator = (ListIterator<TimeSeries>) tmp_lise.listIterator(); 
                    int iWhile = i;
                    int intSeries = 0;
                    while (iterator.hasNext()) {                      
                     TimeSeries tmpElemXY = iterator.next();
                     String  XYDescription = tmpElemXY.getDescription();// получить описание
                     double valueY = DatasetUtilities.findYValue(plot_m.getDataset(i), intSeries , x);
                     
                                          // сразу проверяем не дискрет ли этот график
                    boolean finD = false;                     
                    for( int d =0; d<listDiscret.size(); ++d){
                      if(XYDescription.equals(listDiscret.get(d))){
                        finD = true;
                      }
                    }
                     String formattedDouble = new DecimalFormat("#0.000000").format(valueY);
                     // Если нашли дискрет прикручиваем ему 1 в значение
                     if (finD == true & valueY>0){
                       valueY = 1;
                       formattedDouble = new DecimalFormat("#0").format(valueY);
                     }
                     ValueAxis range = plot_m.getRangeAxis(i);
                     boolean showG = plot_m.getRenderer(i).isSeriesVisible(intSeries); // визуализирован ли график не работает                    
                     boolean axVus = range.isVisible();
                    // предыдущее занчение от нажатие
                    Object beforeVoid = null;
                    if (oldDataT.length>iWhile){
                      beforeVoid = oldDataT[iWhile];
                    } 
                    if (Mark.equals("Mark2")){
                     tableModel.addRow( new Object[] {
                                              null,
                                              XYDescription, 
                                              beforeVoid, formattedDouble, axVus, showG});
                    }if (Mark.equals("Mark1")){
                    tableModel.addRow( new Object[] {
                                              null,
                                              XYDescription, 
                                              formattedDouble, beforeVoid, axVus, showG});
                    }
                     ++iWhile;
                     ++intSeries;
                    }
                 }
        }
        // так красим нашу таблицу
        jTable1.getColumnModel().getColumn(0).setCellRenderer(new myTableCellRenderer_ver2(listColorGraph));
        // Данные времени  в названиях таблицы
        JTableHeader th = jTable1.getTableHeader();
        jTable1.getColumnModel().getColumn(2).setHeaderValue(date1);
        jTable1.getColumnModel().getColumn(3).setHeaderValue(date2);
        th.repaint(); // без этого не работает отрисовка
 }
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables


}


class myTableCellRenderer implements TableCellRenderer {
	DefaultTableCellRenderer dtcr =new DefaultTableCellRenderer();
	int row,column;
	Color c;
	public myTableCellRenderer(int row,int column,Color c)
	{
		this.row=row;this.column=column;this.c=c;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,

                boolean isSelected, boolean hasFocus, int row, int column) {
		Component renderer =dtcr.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		if (row==this.row&&column==this.column) 
		{
			renderer.setBackground(c);
		} 
		return renderer;
	}
}

// Таким классом красим нашу 1 таблицу
class myTableCellRenderer_ver2 implements TableCellRenderer {
	DefaultTableCellRenderer dtcr =new DefaultTableCellRenderer();
	Color c;
        int it;
        List<Color> listColorGraph;
	public myTableCellRenderer_ver2(List<Color> listColorGraph)
	{
            this.c=c;
            this.it=it;
            this.listColorGraph = listColorGraph;
	}
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column){
        Component cell = dtcr.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        ListIterator<Color> iterator = listColorGraph.listIterator(); 
        int i =0;
        while (iterator.hasNext()) { 
         Color colorSet = iterator.next();
         if (column == 0 && row == i){
          cell.setBackground(colorSet);
        };
         ++i;
        }
  	/*if (column == 0 && row == 0){
          cell.setBackground(c);
        }else if (column == 0 && row == 1) {
              cell.setBackground(Color.BLUE);
              }else if (column == 0 && row == 2) {
                    cell.setBackground(Color.YELLOW);
                    }*/
  	return cell;
    }
}

class myTableCellRenderer_ver3 extends DefaultTableCellRenderer {
   Color paintHZgraph;
   public myTableCellRenderer_ver3(Color paintHZgraph){
       this.paintHZgraph = paintHZgraph;
   }
   public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column)
   {
  
      if (column == 0 && row == 1) {
         setBackground(paintHZgraph);
      }
      else  if (column == 0 && row == 1){ 
         setBackground(Color.CYAN);
      }  
      return super.getTableCellRendererComponent(table, value, isSelected,
                                                 hasFocus, row, column);
   }
}
// -- мини меню по мыши первого столбца таблицы---
class PopUpDemo extends JPopupMenu {
    EditGraph editGraph;
    JMenuItem anItem;
    int i1 =0;
    public PopUpDemo() {
        //anItem = new JMenuItem("Click Me!");
        JSlider slider = new JSlider();
        add(anItem);
        add(slider);
        int value = slider.getValue();
        JMenuItem anItem2;
        anItem2 = new JMenuItem(Integer.toString(value));
        add(anItem2);
        
        
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            int value = ((JSlider)e.getSource()).getValue();
            //System.out.println(value);
            
            }
        });
    }
    PopUpDemo(EditGraph editGraph) {
        this.editGraph = editGraph;
        JSlider slider = new JSlider();
        JColorChooser chooser = new JColorChooser();
        chooser.addChooserPanel(new MyChooserPanel());
        JButton buttonPopmenuC = new javax.swing.JButton("Цвет");
        JTextField jTextFieldMenu = new javax.swing.JTextField();
        JButton plus = new javax.swing.JButton("+");
        JButton minus = new javax.swing.JButton("-");
        JLabel sizeN = new JLabel("толщина");
       //GridLayout layout = new GridLayout(2, 0, 5, 12);
        GridBagLayout layout = new GridBagLayout(); 
        setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints(); 
        
        // По умолчанию натуральная высота, максимальная ширина 
        constraints.fill = GridBagConstraints.HORIZONTAL; 
        constraints.weightx = 0.5;
        constraints.gridy   = 0  ;  // нулевая ячейка таблицы по вертикали 

        constraints.fill = GridBagConstraints.HORIZONTAL; 
        constraints.gridx = 0;      // 0 ячейка таблицы по горизонтали
        add(sizeN, constraints); 
        constraints.fill = GridBagConstraints.HORIZONTAL; 
        constraints.gridx = 1;      // 1 ячейка таблицы по горизонтали
        add(plus, constraints); 
        constraints.fill = GridBagConstraints.HORIZONTAL; 
        constraints.gridx = 2;      // 2 ячейка таблицы по горизонтали
        add(minus, constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL; 
        constraints.gridx = 3;      // 3 ячейка таблицы по горизонтали
        constraints.ipadx   = 20; // ширина по X
        add(jTextFieldMenu, constraints);
        
        constraints.fill = GridBagConstraints.HORIZONTAL; 
        constraints.ipady     = 15;   // кнопка высокая 
        constraints.weightx   = 0.0; 
        constraints.gridwidth = 4;    // размер кнопки в три ячейки
        constraints.gridx     = 0;    // нулевая ячейка по горизонтали
        constraints.gridy     = 1;    // первая ячейка по вертикали
        add(buttonPopmenuC, constraints); 
        
        /* Это прикольный слайдер который не понравился.
        add(slider); 
        add(buttonPopmenuC);
        gbc.gridy = 1;
        add(buttonPopmenuW);
        */
        buttonPopmenuC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Color c = chooser.showDialog(null, "выбрать цвет для графика", null);
                if (c != null){
                    editGraph.setColor(c);
                }
            }
        });
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            int value = ((JSlider)e.getSource()).getValue();
            //System.out.println(value);
            editGraph.setWidht(value, false);
            
            }
        });
        // Слушатели кнопок
        plus.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            //System.out.println(value);
            editGraph.setWidht((float) +(0.3), true);
            
            }
        });
        minus.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            editGraph.setWidht((float) -(0.3), true);
            
            }
        });
        // слушатель поля
        jTextFieldMenu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
        String getTmpS = jTextFieldMenu.getText();      
        if (isNumber(getTmpS)){
         //передаем конвертируемые данные в функцию
         editGraph.setWidht(Float.parseFloat(getTmpS), false);
        }
        }
        });
    }
    
    // Фукция проверки строки на число float
    public static boolean isNumber(String strNum) {
    if (strNum == null) {
        return false;
    }
    try {
        float d = Float.parseFloat(strNum);
    } catch (NumberFormatException nfe) {
        return false;
    }
    return true;
}
}

// самодельная панель выбора цвета . пока почему то такая же , доделать
class MyChooserPanel extends AbstractColorChooserPanel {
  public void buildChooser() {
    setLayout(new GridLayout(0, 3));
    makeAddButton("Red", Color.red);
    makeAddButton("Green", Color.green);
    makeAddButton("Blue", Color.blue);
  }
  public void updateChooser() {
  }
  public String getDisplayName() {
    return "MyChooserPanel";
  }
  public Icon getSmallDisplayIcon() {
    return null;
  }
  public Icon getLargeDisplayIcon() {
    return null;
  }
  private void makeAddButton(String name, Color color) {
    JButton button = new JButton(name);
    button.setBackground(color);
    button.setAction(setColorAction);
    add(button);
  }
  Action setColorAction = new AbstractAction() {
    public void actionPerformed(ActionEvent evt) {
      JButton button = (JButton) evt.getSource();
      getColorSelectionModel().setSelectedColor(button.getBackground());
    }
  };
}


// --- Класс работы с графиками по имени которое указаннов комментарии ---
class EditGraph{
    XYPlot plot_m;
    String nameGra;
    int tmppoti = 0;

    EditGraph(XYPlot plot_m, String nameGra) {
      this.plot_m = plot_m;
      this.nameGra = nameGra;
      this.tmppoti = plot_m.getDatasetCount();
    }

    EditGraph(XYPlot plot_m) {
      this.plot_m = plot_m;
      this.tmppoti = plot_m.getDatasetCount();
    }

    
    // показать или скрыть график
    public void setView(boolean aValue){
                // Прогоним все это для поиске данных в коллекциях
        for(int i=0; i<tmppoti; ++i){
          TimeSeriesCollection tmpSeries = (TimeSeriesCollection) plot_m.getDataset(i);
          List<?> tmp_lise  = tmpSeries.getSeries();
          ListIterator<TimeSeries> iterator =  (ListIterator<TimeSeries>) tmp_lise.listIterator(); 
          int intSeries = 0;
          while (iterator.hasNext()) { 
          TimeSeries tmpTimeS = iterator.next();
          if (tmpTimeS.getDescription().equals(nameGra)){
              //вот так хитровыключаем при поиске заметки и имени в таблице
              plot_m.getRenderer(i).setSeriesVisible(intSeries, aValue);;
          };
          ++intSeries;
          }
        }
    }
    // Установить цвет графиков
    public void setColor(Color c){
                // Прогоним все это для поиске данных в коллекциях
        for(int i=0; i<tmppoti; ++i){
          TimeSeriesCollection tmpSeries = (TimeSeriesCollection) plot_m.getDataset(i);
          List<?> tmp_lise  = tmpSeries.getSeries();
          ListIterator<TimeSeries> iterator =  (ListIterator<TimeSeries>) tmp_lise.listIterator(); 
          int intSeries = 0;
          while (iterator.hasNext()) { 
          TimeSeries tmpTimeS = iterator.next();
          if (tmpTimeS.getDescription().equals(nameGra)){
              //красим
              plot_m.getRenderer(i).setSeriesPaint(intSeries, c);
          };
          ++intSeries;
          }
        }
    }
    // Установить ширину графиков
    public void setWidht(float widthG, boolean bTrig){
                // Прогоним все это для поиске данных в коллекциях
        for(int i=0; i<tmppoti; ++i){
          TimeSeriesCollection tmpSeries = (TimeSeriesCollection) plot_m.getDataset(i);
          List<?> tmp_lise  = tmpSeries.getSeries();
          ListIterator<TimeSeries> iterator =  (ListIterator<TimeSeries>) tmp_lise.listIterator(); 
          int intSeries = 0;
          while (iterator.hasNext()) { 
          TimeSeries tmpTimeS = iterator.next();
          if (tmpTimeS.getDescription().equals(nameGra)){
              //установим ширину
              BasicStroke getBstroke= (BasicStroke) plot_m.getRenderer(i).getSeriesStroke(intSeries);
              if (bTrig){
                float widthL = getBstroke.getLineWidth(); // получаем ширину линии
                // выставляем ширину от полученных данных
                plot_m.getRenderer(i).setSeriesStroke(intSeries, new BasicStroke(widthL+widthG));
              }else plot_m.getRenderer(i).setSeriesStroke(intSeries, new BasicStroke(widthG));
          };
          ++intSeries;
          }
        }
    }
    // лист цветов графиков
    public List<Color>  getListColor(){
        List<Color>  listColorGraph = new ArrayList(); // массив цвета
        for(int i=0; i<tmppoti; ++i){
          TimeSeriesCollection tmpSeries = (TimeSeriesCollection) plot_m.getDataset(i);
          List<?> tmp_lise  = tmpSeries.getSeries();
          ListIterator<TimeSeries> iterator =  (ListIterator<TimeSeries>) tmp_lise.listIterator(); 
          int intSeries = 0;
          while (iterator.hasNext()) { 
          TimeSeries tmpTimeS = iterator.next();
              listColorGraph.add((Color) plot_m.getRenderer(i).getSeriesPaint(intSeries));
          ++intSeries;
          }
        }
        return listColorGraph;
        }
}
