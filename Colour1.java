package loginingfilegui;

// с этим не работает 
/*
import javafx.scene.paint.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
*/
//вот с этим колор работает выше нет
import java.awt.*; 
import javax.swing.*;

public class Colour1 extends JFrame { 
  
    // constructor 
    Colour1(int colorINT, int SizeINT) 
    { 
        super("colour"); 
  
        // create a new Color 
        // RGB value of Yellow is 225, 255, 0 
        Color c = new Color(colorINT, colorINT, 0); 
  
        // create a panel 
        JPanel p = new JPanel(); 
  
        // set the background of the  
        // frame to the specified Color 
        p.setBackground(c); 
        
        setSize(200, 200); 
        setLocation(SizeINT, SizeINT*2);
        //setSize(SizeINT, SizeINT); 
        add(p); 
        show(); 
    } 
  
      public static int rnd(final int max)
{
	return (int) (Math.random() );
}
    // Main Method 
    public static void main(String args[]) 
    { 
        //Colour1 c = new Colour1(); 
        for(int i=0; i<10; ++i){
           int tmpint = rnd(10);
           System.out.println("ghbrjkmyj xnj i --> " + i + "\nRandom i --> " + tmpint);
           Colour1 c = new Colour1(i, tmpint); 
        }
    } 
} 
