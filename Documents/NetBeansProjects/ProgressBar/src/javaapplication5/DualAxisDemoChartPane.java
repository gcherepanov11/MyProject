package javaapplication5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.TimeZone;
import javax.swing.JFrame;
import static jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.getObjectSize;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.CategoryStepRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYStepRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RefineryUtilities;

// Версия основанная на NewJFrameSimpleGraph_1
// Рисует в XYpot и при этом быстро
public class DualAxisDemoChartPane implements Runnable {
// так изначально работает тоже
//public class DualAxisDemo2 extends ApplicationFrame implements ChartMouseListener {

    ChartPanel chartPanel = null;
    boolean viewL = false;
    List listDiscret = null;
    int SumGraph = 0;
    List<TimeSeriesCollection> listGraf = null;

    // -- Конструктор вызова с передачей наших графиков XYSeriesCollection --
    public DualAxisDemoChartPane(final List<XYSeriesCollection> listGraf, boolean viewL) {
        this.viewL = viewL;
        final JFreeChart chart = createChart(createXYPlot(listGraf), viewL);
        this.chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        //chartPanel.addChartMouseListener(this); // Разрешаем слушать мышь
        //setContentPane(chartPanel);
    }

    // -- Конструктор вызова с передачей наших графиков TimeSeriesCollection --
    public DualAxisDemoChartPane(final List<TimeSeriesCollection> listGraf, boolean viewL, List listDiscret) {
        ProgresBarMy.getInstance().setMax(SumGraph); // пока тут так
        this.SumGraph = listGraf.size() + listDiscret.size();
        this.listGraf = listGraf;
        this.viewL = viewL;
        this.listDiscret = listDiscret;
        //listGraf.clear();
        //chartPanel.addChartMouseListener(this); // Разрешаем слушать мышь
        //setContentPane(chartPanel);

    }

    public ChartPanel getChart() {
        return this.chartPanel;
    }

    // --- Второй Создаем XYplot на основе массива Коллекций ---
    private XYPlot createXYPlot(List<XYSeriesCollection> listgraf) {
        final JFreeChart chartTesting = ChartFactory.createXYAreaChart(
                "Окно просмотра графиков -1",
                "X1", "Y",
                null,
                PlotOrientation.VERTICAL,
                false, false, false);
        XYPlot xyplotFinal = chartTesting.getXYPlot(); // Почему то рисует только так
        ListIterator<XYSeriesCollection> iterator = listgraf.listIterator();
        int i = 0;
        while (iterator.hasNext()) {
            xyplotFinal.setDataset(i, iterator.next());
            Color color1 = new Color(rnd(255), rnd(255), rnd(255));
            StandardXYItemRenderer rendererXY = new StandardXYItemRenderer();
            rendererXY.setSeriesPaint(0, color1); // так задаем цветность
            xyplotFinal.setRenderer(i, rendererXY); // так расим график
            ++i;
        }
        //System.out.println("Skolko collekcii zavodim -->" +i);
        //xyplotFinal.setDomainCrosshairVisible(true); // вертикаль

        return xyplotFinal;
    }

    // --- Второй Создаем XYplot на основе массива Коллекций времени---
    private JFreeChart createXYPlotTime(List<TimeSeriesCollection> listgraf) {
        JFreeChart chartTesting = ChartFactory.createTimeSeriesChart(
                null, // тут должно быть название графиков
                "date", "Val", null, viewL, false, false);
        int current_graph = 0;
        final DateAxis axis = (DateAxis) chartTesting.getXYPlot().getDomainAxis(); // так сделаем дату  вместо просто цифр
        axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy-dd-hh-mm-ss"));
        XYPlot xyplotFinal = chartTesting.getXYPlot(); // Почему то рисует только так
        ListIterator<TimeSeriesCollection> iterator = listgraf.listIterator();
        int i = 0;
        while (iterator.hasNext()) {
            TimeSeriesCollection tmpTimeCol = iterator.next();
            List tmpDataList = tmpTimeCol.getSeries();
            boolean finD = false;
            for (int ij = 0; ij < tmpDataList.size(); ++ij) {
                TimeSeries tmpTimeSeries = (TimeSeries) tmpDataList.get(ij);
                String nameInterGraph = tmpTimeSeries.getDescription();
                System.out.println(current_graph); // номер Текущий график
                ProgresBarMy.getInstance().setVal(current_graph); // не работает, отображает  только после загрузки
                ++current_graph;
                for (int d = 0; d < listDiscret.size(); ++d) {
                    if (nameInterGraph.equals(listDiscret.get(d))) {
                        finD = true;
                    }
                }
            }
            if (finD) { // Если нашли дискреты в коллекции
                XYStepRenderer rendererStep = new XYStepRenderer(); // рендер для дискретов
                for (int c = 0; c < tmpDataList.size(); ++c) {
                    Color colorD = new Color(rnd(50), rnd(50), rnd(255), 170);// 170 прозрачность
                    rendererStep.setSeriesPaint(c, colorD);
                    rendererStep.setSeriesStroke(c, new BasicStroke(5)); // ширина линии
                }
                xyplotFinal.setRenderer(i, rendererStep); // так красим график Не может закрасить такой график шагом

                final NumberAxis axisINpraph = new NumberAxis("Discret");
                axisINpraph.setAutoRangeIncludesZero(true);
                axisINpraph.setVisible(false); // Выключаем шкалу по умолчанию
                xyplotFinal.setRangeAxis(i, axisINpraph);
                xyplotFinal.mapDatasetToRangeAxis(i, i);
            } else {
                StandardXYItemRenderer rendererXY = new StandardXYItemRenderer();
                for (int c = 0; c < tmpDataList.size(); ++c) { // должны быть по одному но на всякий
                    Color color1 = new Color(rnd(255), rnd(255), rnd(255));
                    rendererXY.setSeriesPaint(c, color1); // так задаем цветность            
                    // Так вносим шкалу что то верно она показывает
                    TimeSeries tmpTimeSeries = (TimeSeries) tmpDataList.get(c);
                    String nameInterGraph = tmpTimeSeries.getDescription();
                    final NumberAxis axisINpraph = new NumberAxis(nameInterGraph);
                    axisINpraph.setAutoRangeIncludesZero(true);
                    axisINpraph.setVisible(false); // Выключаем шкалу по умолчанию
                    xyplotFinal.setRangeAxis(i, axisINpraph);
                    xyplotFinal.mapDatasetToRangeAxis(i, i);
                }
                xyplotFinal.setRenderer(i, rendererXY); // так красим график

            }
            // 0 по тому что у нас он должен один у нас быть. а вот может и нет может быть дискреты в 1 коллекции
            //String nameInterGraph = tmpTimeCol.getSeries(0).getDescription(); // было до while       
            xyplotFinal.setDataset(i, tmpTimeCol);
            ++i;
        }
        //System.out.println("Skolko collekcii zavodim -->" +i);
        //xyplotFinal.setDomainCrosshairVisible(true); // вертикаль
        listgraf.clear();
        return chartTesting;
    }

    // тут создадим график на основе XYPlot лажа с пустыми элементами получилась
    private JFreeChart createChart(XYPlot plot, boolean viewLegend) {
        // Зачем то тут переопределяю? 
        JFreeChart chart = new JFreeChart("Поле просмотра графиков",
                null,
                plot,
                true);
        chart.setBackgroundPaint(Color.white);
        return chart;
    }

    // Функция рандома для Цветов
    public int rnd(final int max) {
        return (int) (Math.random() * max);
    }

    @Override
    public void run() {
        final JFreeChart chart = createXYPlotTime(listGraf);
        // смотрим сколько занимает памяти
        //System.out.println("size JFreeChart bytes " + getObjectSize(chart));
        //chart.getLegend().setVisible(false);
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        //System.out.println("size Jthis.chartPanel bytes " + getObjectSize(this.chartPanel));
    }

}
