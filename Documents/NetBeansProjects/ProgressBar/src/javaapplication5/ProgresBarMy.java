/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication5 ;

/**
 *
 * @author nazarov
 */
public class ProgresBarMy extends javax.swing.JProgressBar {
    private static ProgresBarMy instance;
   /* public ProgresBarMy() {

    }*/
      public static ProgresBarMy getInstance(){ // #3
      if(instance == null){		//если объект еще не создан
        instance = new ProgresBarMy();	//создать новый объект
      }
      return instance;		// вернуть ранее созданный объект
    }
    
    public void setMin(int s){
        this.setMinimum(s);
    }
    public void setMax(int s){
        this.setMaximum(s);
    }
    public void setVal(int s){
        this.setValue(instance.getValue()+s);
    }
}
