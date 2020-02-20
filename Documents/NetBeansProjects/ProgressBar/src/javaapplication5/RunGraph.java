/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication5;

/**
 *
 * @author nazarov
 */

import com.opencsv.CSVReader;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
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
public class RunGraph extends javax.swing.JFrame implements ChartMouseListener {
    //private static final String SAMPLE_CSV_FILE_PATH = "AO_TREND_20190527_020929.log";
    //private static final String SAMPLE_CSV_FILE_PATH = "C:\\Users\\Nazarov\\Desktop\\Info_script_file_work\\построить графики\\135_k-m_m-k.txt";
    //private static String SAMPLE_CSV_FILE_PATH = "134_m-k_k-m.txt";
    //private static String SAMPLE_CSV_FILE_PATH = "134_k-m_m-k.txt";
    //private static String SAMPLE_CSV_FILE_PATH = "/home/ad/IdeaProjects/GraphicsViewerCSV/построить графики/AO_TREND_20190527_021831.log";
    private static String SAMPLE_CSV_FILE_PATH = "";
            
    static Point2D po; // должна быть глобальная и для клавиш и мыши
    static ArrayList<String> listTime = new ArrayList<String>();

    private ChartPanel chartPanel;

    private Crosshair xCrosshair;

    private Crosshair yCrosshair;
    XYSeriesCollection  xyDataset; // создадим отдельным элементом для перебора в итератор

    //TimeSeriesCollection  xyDataset = (TimeSeriesCollection ) createDataset(); // создадим отдельным элементом для перебора в итератор
   
    /**
     * Creates new form NewJFrame
     */
    public RunGraph(String SAMPLE_CSV_FILE_PATH) throws FileNotFoundException, IOException{ // вот так инициализация проходит
      this.SAMPLE_CSV_FILE_PATH = SAMPLE_CSV_FILE_PATH;
        //       super(s);
        //JPanel jpanel = createDemoPanel();
        //jpanel.setPreferredSize(new Dimension(640, 480));
        //add(jpanel);
        xyDataset = (XYSeriesCollection ) constructorGraph(); // Инициализация тоже должна быть в конструкторе
        initComponents();//эта часть кода не реорганизована 
    }
   
       public RunGraph() throws FileNotFoundException, IOException{ // вот так инициализация проходит

        initComponents();
    }

        //этот метод не реализуется
        public JPanel createDemoPanel() throws IOException {
        JFreeChart jfreechart = ChartFactory.createScatterPlot(
           // "Scatter Plot Demo", "X", "Y", samplexydataset2(),
             "Scatter Plot Demo", "X", "Y", constructorGraph(),
            PlotOrientation.VERTICAL, true, true, false);//vurtigo 
        Shape cross = ShapeUtilities.createDiagonalCross(3, 1); // Крестики
        XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
        xyPlot.setDomainCrosshairVisible(true); // отображение по Y
        xyPlot.setRangeCrosshairVisible(true); // отображение по X
        XYItemRenderer renderer = xyPlot.getRenderer();
       // renderer.setSeriesShape(0, cross);
       // renderer.setSeriesPaint(0, Color.red);
        return new ChartPanel(jfreechart);
    }

    private XYDataset samplexydataset2() {
        int cols = 15;
        int rows = 13;
        double[][] values = new double[cols][rows];
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        XYSeries series = new XYSeries("Random");
        Random rand = new Random();
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                double x = rand.nextGaussian();
                double y = rand.nextGaussian();
                series.add(x, y);
            }
        }
        xySeriesCollection.addSeries(series);
        return xySeriesCollection;
    }
    
     private JPanel createContent() {
        //JFreeChart chart = createChart(createDataset());
       
        JFreeChart chart = createChart(xyDataset);
        this.chartPanel = new ChartPanel(chart);
        this.chartPanel.addChartMouseListener(this);
        CrosshairOverlay crosshairOverlay = new CrosshairOverlay();
        this.xCrosshair = new Crosshair(Double.NaN, Color.RED, new BasicStroke(0f)); // Эта отоброжается
        this.xCrosshair.setLabelVisible(true);//с этого момента начну завтра
        
        this.yCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        this.yCrosshair.setLabelVisible(true);
        crosshairOverlay.addDomainCrosshair(xCrosshair);
        crosshairOverlay.addRangeCrosshair(yCrosshair);
        chartPanel.addOverlay(crosshairOverlay);
        return chartPanel;
    }

    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart("Crosshair Demo", 
                "X", "Y", dataset);
        return chart;
    }
    
    // тут создаем массивы рандомные и еще в разных системах координат
    private XYDataset createDataset() {
        XYSeries series = new XYSeries("S1");
        for (int x = 0; x < 10; x++) {
            series.add(x, x + Math.random() * 4.0);
        }
               XYSeries series2 = new XYSeries("S2");
        for (int x = 0; x < 10; x++) {
            series2.add(x, x + Math.random() * 4.0);
        }
               XYSeries series3 = new XYSeries("S3");
         for (int x = 0; x < 20; x++) {
            series2.add(x, x + Math.random() * 4.0);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        return dataset;
    }
    
    // наши графики из другого проекта
    private XYDataset constructorGraph() throws FileNotFoundException, IOException{
    
        InputStreamReader reader1 = new InputStreamReader(new FileInputStream(SAMPLE_CSV_FILE_PATH), "UTF8");  // тут отличия от оригинала так как нужно декодировать
        CSVReader reader = new CSVReader(reader1, '\t', '"');
    List<String[]> allRows = reader.readAll();
     
    

     int colcolumn = 0; // тут не верно это все данные
     int strColumn =0; // Строка с именами
     int propusk = 0;// Сколько строк пропускаем с начала изначально 4
     int tmpColumn =0; 
     // получить масив имен что бы вставить в инвертированный файл
     String[] listName = allRows.get(strColumn);
     //Удаляем лист элемент с именами
     allRows.remove(strColumn);
     // Теперь инвертируем коолекцию переворачиваем даные сверху вниз(нужно это сделать по анализу времени)
     Collections.reverse(allRows); // если надо инвертируем список с именами трудность
     //и добавляем имена
     allRows.add(0, listName);
     
     String[] nameColumn = null; // массив имен
     for(String[] row : allRows){ // выводим все даные
      if (row.length > colcolumn) colcolumn = row.length ;
        //System.out.println(row.length); // длина массива
        // Еще получаем имена столбцов 4 строка идентификатор
        if (tmpColumn== strColumn){
            nameColumn = row.clone(); // копируем объектом
        }
        ++tmpColumn;
     }
    
       // Создание массива времени   
      int xe =0;
      for(String[] row : allRows){
        if (xe<=propusk){ // пропуск трех строк которые идут первые (Архив сигналов) --- и тд
        ++xe;
        continue;
        }
        Pattern pattern1 = Pattern.compile("^(.*) (.*)$"); 
        Matcher matcher1 = pattern1.matcher(row[0]);
        String date =""; // годы месяцы число
        String time = ""; // время
        if (matcher1.matches()){ 
        date = matcher1.group(1); // годы месяцы число
        time = matcher1.group(2); // время
       }
        listTime.add(time);
      }
      // тут создаем коллекцию из массива графиков по количеству столбцов
      XYSeriesCollection xyserColl = new XYSeriesCollection();
      //int[] massColum = {5,6,7,8,9,10,11,15,70,100,201}; // для дальнейшего формирования массива
      int[] massColum = {1,2,3,4,5,6,7}; // какие колонки используем дл рисования
    /*
      // Тут по фору создаем массив из количества имен что то не то с ним
      int i3 =0;
      for(i3=0; i3<listTime.toArray().length; ++i3){ // это надо переделать конструкция жопа
          
      }
      int[] massColum = new int[i3]; 
      // ну и заполняем его
        for(i3=0; i3<listTime.toArray().length; ++i3){ // это надо переделать конструкция жопа
            if (i3==0){ continue;}
            massColum[i3] = i3;
          
      }
      */ 
      
      
      for(int j=0; j<massColum.length; ++j){
      int stolb = massColum[j]; // Определенные столбцы

      for (int ic=0; ic<colcolumn; ++ic ){ //перебираем нужные столбцы
         if (ic==stolb){ // нужный столбец и столбец времени      
         String nameG = nameColumn[ic];
         //nameG = new String(nameG.getBytes("KOI8_R"));
         //nameG = new String(nameG.getBytes("Cp1251"));


         XYSeries tmp = new XYSeries(nameG);
         tmp.setDescription(nameG);// вносим описание это тмя что бы вывести в поле в дальнейшем
         
         int xi =0;
         for(String[] row : allRows){

                   if (xi<=3){ // пропуск трех строк которые идут первые (Архив сигналов) --- и тд
                   ++xi;
                 continue;
             }
        if (row.length > ic && row[ic] != null){ // Проверяем на пустоту  элемента массива
               // System.out.println(row[ic]);
                String item = row[ic];
             if (item.equals("false") || item.equals("true") || item.equals(null)){ // ищет false true еще надо время
               // System.out.println("Find True false");  
             }else{
                              // регулярка для времени
                 Pattern pattern1 = Pattern.compile("^(.*) (.*)$"); 
                 Matcher matcher1 = pattern1.matcher(item);
                 String date =""; // годы месяцы число
                  String time = ""; // время
                 if (matcher1.matches()){ 
                  date = matcher1.group(1); // годы месяцы число
                  time = matcher1.group(2); // время
                  //System.out.println(time);
                  //Date currentD = new Date();
                  //System.out.println(currentD + " <-- Current data");

                }
                tmp.add(xi, Float.valueOf(row[ic]));
                 }
            }

         ++xi;
      }
      xyserColl.addSeries(tmp); // Добавляем в коллекцию график
      } else continue;// if проверки столбца
    }
    } 
    return xyserColl;
    }
    /*
        // тут временные массив не победил его
        private TimeSeriesCollection   createDataset() {

        TimeSeries  series = new TimeSeries ("S1", "S1", "S1");
        for (int x = 1; x < 10; x++) {
            series.add(new Millisecond(x, 0, 7, 0, 7, 12, 2018), x + Math.random() * 4.0);
        }
               TimeSeries  series2 = new TimeSeries ("S2", "S2", "S2");
        for (int x = 1; x < 50; x++) {
            
            series2.add(new Millisecond(x, 0, 7, 0, 7, 12, 2018), x + Math.random() * 4.0);
        }
        
        TimeSeriesCollection  dataset = new TimeSeriesCollection (series);
        dataset.addSeries(series2);
        return dataset;
    }
*/
    @Override
    public void chartMouseClicked(ChartMouseEvent cme) {
        
       /* 
        Rectangle2D dataArea = this.chartPanel.getScreenDataArea();
        JFreeChart chart = cme.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        
            Point2D  po = chartPanel.translateScreenToJava2D(cme.getTrigger().getPoint()); // даем точки нажатия клафиши мыши
            
            Rectangle2D plotArea = chartPanel.getScreenDataArea();
            plot = (XYPlot) chart.getPlot(); 
            ValueAxis xAxis = plot.getDomainAxis();

            double x = xAxis.java2DToValue(cme.getTrigger().getX(), dataArea, RectangleEdge.BOTTOM);
            float chartX = (float) plot.getDomainAxis().java2DToValue(po.getX(), plotArea, plot.getDomainAxisEdge());  // тут различается получение данных
            float charty = (float) plot.getDomainAxis().java2DToValue(po.getY(), plotArea, plot.getDomainAxisEdge());  // тут различается получение данных
            
            System.out.println(x + "  " + chartX + "  " + charty);
            
            // это почти что нужно. надо как то по иксу по Y пройти и получим значение
           
            ChartEntity ce = cme.getEntity();
            System.out.println(ce.toString());
            
                XYItemEntity e2 = new XYItemEntity(
                  plotArea,
                new TimeSeriesCollection(), 1, 9, "ToolTip", "URL"
                );
        */
            /*
            if (ce instanceof XYItemEntity) {
             XYItemEntity e = (XYItemEntity) ce;
             System.out.println(ce.getToolTipText());
             System.out.println(e.toString());
             XYDataset d = e.getDataset();
             int s = e.getSeriesIndex();
             int i = e.getItem();
             String name = ce.getURLText();
             System.out.println("Ingraphics - X:" + d.getX(s, i) + ",Ingraphics  Y:" + d.getY(s, i) + "Name = " + name);
            } 
            */
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent event) {
        // тут похоже реализовавает движение по графику
        Rectangle2D dataArea = this.chartPanel.getScreenDataArea();
        JFreeChart chart = event.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        // узнаем что такое XYPlot
         
        
        ValueAxis xAxis = plot.getDomainAxis();
        double x = xAxis.java2DToValue(event.getTrigger().getX(), dataArea, RectangleEdge.BOTTOM);
        double y = DatasetUtilities.findYValue(plot.getDataset(), 0, x); // тут похоже находим координаты нашего y
        
        this.xCrosshair.setValue(x); // это реализация движения шкалы по x y внизу
      //  this.yCrosshair.setValue(y);
        
        
       //данные получаем с графиков для обычных
            /*  List<XYSeries> werries = xyDataset.getSeries();
              ListIterator<XYSeries> iterator = werries.listIterator(); 
              while (iterator.hasNext()) { 
              double yinterpol = interpolate(iterator.next(), x);  // с Интерполяцией трудность
              System.out.println("Item interpolicia = " + yinterpol );
              }
              */
              //данные получаем с графиков пробуем для времени
              List<XYSeries> werries = xyDataset.getSeries();
              ListIterator<XYSeries> iterator = werries.listIterator(); 
              String toFieadTXT = "";
              String curentTime = "";
              while (iterator.hasNext()) { 
                  XYSeries tmpElemXY = iterator.next();
                  String  XYDescription = tmpElemXY.getDescription();// получить описание
                new dataFromGraph(tmpElemXY, x); // передаем данные в статический клас вместо метода Интерполяции
                dataFromGraph.setItem();
                int iTime = dataFromGraph.getCurrentTime();// получим время из созданного листа
                curentTime = listTime.get(iTime);
                toFieadTXT += XYDescription + "->" + dataFromGraph.getItemData() +"\n" ;
              }
              toFieadTXT += "\n" + "Time graph --> " + curentTime + "\n";  
       /* try {
            toFieadTXT = new String(toFieadTXT.getBytes("Cp1251"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(JavaApplication5.class.getName()).log(Level.SEVERE, null, ex);
        }
              */
   
              jTextArea1.setText(toFieadTXT);
              
    }
    
    
    
     // реализация интерполяции
    /*    private static double interpolate(XYSeries s, double x)
    {
        //return s.getY(0).doubleValue(); // Надо разобраться с механизмом
        if (x <= s.getMinX())
        {
            return s.getY(0).doubleValue();
        }
        if (x >= s.getMaxX())
        {
            return s.getY(s.getItemCount()-1).doubleValue();
        }
        List<?> items = s.getItems();
        System.out.println("Size item =" + items.size());
        for (int i=0; i<items.size()-1; i++)
        {
            XYDataItem i0 = (XYDataItem) items.get(i);
            XYDataItem i1 = (XYDataItem) items.get(i+1);
            double x0 = i0.getXValue();
            double y0 = i0.getYValue();
            double x1 = i1.getXValue();
            double y1 = i1.getYValue();

            if (x >= x0 && x <= x1)
            {
                double d = x - x0;
                double a = d / (x1-x0);
                double y = y0 + a * (y1 - y0);
                return y;
            }
        }
        // Should never happen
        return 0;
                
    }
*/

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = createContent();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setAutoscrolls(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 288, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        /*
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RunGraph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RunGraph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RunGraph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RunGraph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        */
        //</editor-fold>

        /* Create and display the form */
        
        /*
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new RunGraph().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(RunGraph.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        */
    }

    // Variables declaration - do not modify                     
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton jButton3;

    // End of variables declaration     
    
    
        private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        JFileChooser fileopen = new JFileChooser("C:\\Users\\");
        int ret = fileopen.showDialog(null, "Загрузка Exel в базу");                
        if (ret == JFileChooser.APPROVE_OPTION) {
        File file = fileopen.getSelectedFile();
       /* 
        try {
            // TODO add your handling code here:
            //загрузка в базу изх файла
            Main.fillDB(file.getPath());
        }
       catch(IOException e){
            System.out.println(e);
        }*/
        }
    } 
}

// Какой хитрый  класс для выдергивания данных
    class dataFromGraph{
        
      static double itemData;
      static int currentTime;
      static XYSeries s;
      static double x;
      static String nameG = "";
      
      public dataFromGraph(XYSeries s, double x){
          this.s = s;
          this.x =x;
          this.nameG = "";
      }
        static void setItem()
        {
   
            if (x <= s.getMinX())
            {
                itemData = s.getY(0).doubleValue();
            }
            if (x >= s.getMaxX())
            {
                itemData = s.getY(s.getItemCount()-1).doubleValue();
            }
        
            List<?> items = s.getItems();
            for (int i=0; i<items.size()-1; i++)
            {
                XYDataItem i0 = (XYDataItem) items.get(i);
                XYDataItem i1 = (XYDataItem) items.get(i+1);
                double x0 = i0.getXValue();
                double x1 = i1.getXValue();
            
                double y0 = i0.getYValue();
                double y1 = i1.getYValue();

                if (x >= x0 && x <= x1)  // если находится в этом диапазоне 
                {
                    double d = x - x0;
                    double e = x1 - x;
                    if (d < e) {//Вычисляем к какой точке ближе
                    currentTime = i; // текущий элемент    
                    itemData = y0;}
                    else{
                        currentTime = (i+1); // Пока так выводим время
                        itemData = y1;}
                
                    /*
                    double d = x - x0;
                    double a = d / (x1-x0);
                    double y = y0 + a * (y1 - y0);
                    return y;
                */
                }
            }
    
        }
        static double getItemData(){
        return itemData;}
        static int getCurrentTime(){
        return currentTime;}
        static String getNameG(){
        return nameG;}
        }


