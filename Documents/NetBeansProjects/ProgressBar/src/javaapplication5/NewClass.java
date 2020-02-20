/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication5;

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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static javaapplication5.RunGraph.listTime;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
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
/**
 *
 * @author nazarov
 */

// это рисует перекрытие.

public class NewClass {
    static XYPlot plot = new XYPlot();
    private static JFreeChart createChart(XYSeries dataset) {

   /* JFreeChart chart = ChartFactory.createTimeSeriesChart(
        "Legal & General Unit Trust Prices",  // title
        "Date",             // x-axis label
        "Price Per Unit",   // y-axis label
        dataset,            // data
        true,               // create legend?
        true,               // generate tooltips?
        false               // generate URLs?
    );
    */
     XYSeriesCollection xyserColl = new XYSeriesCollection();
     xyserColl.addSeries(dataset);
     plot.setDataset(2, xyserColl);
     JFreeChart chart = new JFreeChart("MyPlot", plot);


   // chart.setBackgroundPaint(Color.white);

    
//    plot.setBackgroundPaint(Color.lightGray);
//    plot.setDomainGridlinePaint(Color.white);
//    plot.setRangeGridlinePaint(Color.white);
//    plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
   // plot.setDomainCrosshairVisible(true); // вертикаль
   // plot.setRangeCrosshairVisible(true); // это похоже горизонт

 /*   XYItemRenderer r = plot.getRenderer();
    if (r instanceof XYLineAndShapeRenderer) {
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
        renderer.setBaseShapesVisible(true);
        renderer.setBaseShapesFilled(true);
        renderer.setDrawSeriesLineAsPath(true);
    }
*/
  //  DateAxis axis = (DateAxis) plot.getDomainAxis();
  //  axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));

    return chart;

}
    
     public static void main(String[] args) {

      XYSeries series = new XYSeries("sin(a)");
 
    for(float i = 0; i < Math.PI; i+=0.1){
      series.add(i, Math.sin(i));
    }
    
    //XYDataset xyDataset = new XYSeriesCollection(series);
    JFreeChart chart = createChart(series);
       
    
    //XYPlot plot = (XYPlot) chart.getPlot();

    
    JFrame frame = 
        new JFrame("MinimalStaticChart");
    // Помещаем график на фрейм
    ChartPanel chartPanel = new ChartPanel(chart);
    frame.getContentPane()
        .add(chartPanel);
    frame.setSize(400,300);
    frame.show();
    Rectangle2D _plotArea = chartPanel.getChartRenderingInfo().getPlotInfo().getSubplotInfo(0).getDataArea();
    Rectangle2D _plotAr=chartPanel.getChartRenderingInfo().getPlotInfo().getSubplotInfo(0).getDataArea();

  
         }
    
}
