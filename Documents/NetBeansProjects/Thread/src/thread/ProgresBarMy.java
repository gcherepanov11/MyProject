package thread;




public class ProgresBarMy extends javax.swing.JProgressBar {
    private static ProgresBarMy instance;
   // public ProgresBarMy() {
   // }
    
      public static ProgresBarMy getInstance(){ // #3
      if(instance == null){		//если объект еще не создан
        instance = new ProgresBarMy();	//создать новый объект
      }
      return instance;		// вернуть ранее созданный объект
    }
    
    public void setMin(int s){
        instance.setMinimum(s);
    }
    public void setMax(int s){
        instance.setMaximum(s);
    }
    public void setVal(int s){
        System.out.println(instance.getValue());
        this.setValue(instance.getValue()+s);//
    }
}
