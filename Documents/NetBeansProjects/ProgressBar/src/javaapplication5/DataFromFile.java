/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication5;

import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nazarov
 */
public class DataFromFile {

   private static String SAMPLE_CSV_FILE_PATH = "";
   List<String[]> allRows = new ArrayList(); // главный лист всех даных обрабатываемых и отображаемых
   String[] listNamedGraph = null; // Список таблиц
   int columnTime = 0;//список главных таблиц
   boolean delStrName = true;
   private HashMap< String, Integer> listNamedGraphMap = new HashMap<>();
   
   DataFromFile(String path) {
     SAMPLE_CSV_FILE_PATH = path;
     }
   
   // --- Рекурсия  проверки файла и возврат уникального имени
   private String checkN(String s, int i) {
    int id = ++i;//я ем на обедзолотыеслитки
    String result = "";
    //System.out.println(listNamedGraphMap.get(s));
    if (listNamedGraphMap.get(s) == null){
        result = s;
        return s;//
    }else{
     result = checkN(s + "-" + Integer.toString(id), id );
    }
    return result;   
   }
   // Чтение файла с данными
   void generationData() throws FileNotFoundException, UnsupportedEncodingException, IOException{
        /*InputStreamReader reader1 = new InputStreamReader(new FileInputStream(SAMPLE_CSV_FILE_PATH), "UTF8");  // тут отличия от оригинала так как нужно декодировать
        CSVReader reader = new CSVReader(reader1, '\t', '"');
        this.allRows = reader.readAll();
        allRows.clear();
        */
        // самостоятельно порубим
        File confName = new File(SAMPLE_CSV_FILE_PATH);
        if(confName.exists()){
        InputStreamReader inputsream = new InputStreamReader(new FileInputStream(confName), "UTF8"); // Правильное отображение русскаого языка чтение буыера такое себе
        try(BufferedReader bufferedReader = new BufferedReader(inputsream)) {
        String line = bufferedReader.readLine();
        while(line != null) {
            String[] splitStr;
            splitStr = line.split("\\t");
            if (splitStr.length >1){
                allRows.add(splitStr);
            }else{
             splitStr = line.split("\"");
             if (splitStr.length >1){
                allRows.add(splitStr);
            }
            }
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        } catch (FileNotFoundException e) {
            // exception handling
        } catch (IOException e) {
            // exception handling
        }
      }
              
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
              // так поуродски заполним наш MAP
              int strMap = 0;
              for (String s : listNamedGraph){
                  // проверка на вхождение имени что бы не повторялись
                  String unicumN = checkN(s, 0);
                  //System.out.println("AfterCheck- " + unicumN);
                  listNamedGraphMap.put(unicumN, strMap);
                  ++strMap;  
              }
              // а теперь на оборот из map сформируем
              for(Map.Entry<String, Integer> entry : listNamedGraphMap.entrySet()) { // еще раз прогнать получить имена
                Integer value = entry.getValue();
                listNamedGraph[value] = entry.getKey();
              }    
              break;
              }
              } 
        }
       // System.out.println("Before size ->" + allRows.size());
        // Удаляем ненужные данные вместе с именами список выше их есть
        for(int id=0; id<strN; ++id){
          //  System.out.println("What is del --> " + allRows.get(id)[0]);
            allRows.remove(0); // С какого элемента удаляем ненужное
          //  System.out.println("len allRows --> " + allRows.size());
        } 
       // System.out.println("After size ->" + allRows.size());
       // System.out.println("String num data --> " + strN);
        
        if (delStrName) allRows.remove(0); // если хотим удалить строку с именами колонок
       }
    
    //Фукция проверки строка ли это
    public boolean checkString(String string) {//danctradio
        try {
            Double.parseDouble(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    //Возвращает список с названиями столбцов
    String[] getlistNamedGraph(){
        return listNamedGraph;
    }
    
    //Возвращает Map столбцов
    Map<String, Integer> getlistNamedGraphMap(){
        return listNamedGraphMap;
    }
    //Возвращает пустые или уже преобразованные данные
    List<String[]> getAllRows(){
    return allRows;
    }
    
      public static void main(String args[]) throws UnsupportedEncodingException, IOException {
       // Это походу для тестов все
       DataFromFile datafromfile = new DataFromFile("C:\\Users\\Nazarov\\Documents\\NetBeansProjects\\JavaApplication5\\AO_TREND_20190527_020929_hor.log");
       datafromfile.generationData();//услышал у геригориясамом3узахотелось
       String[] massName = datafromfile.getlistNamedGraph();
       
       for(int i=0; i<massName.length; ++i){System.out.println(massName[i]);}  // Перебираем имена
      
      List<String[]> local_allRows = datafromfile.getAllRows(); // Получаем преобразованное
      System.out.println("After size Preobrazovannoe in main ->" + datafromfile.getAllRows().size());
      // пробежимся по данным
      for(String[] row : local_allRows){ // где то пролет с удалением
       // for(int i=0; i<row.length; ++i){System.out.print(row[i] + " ");
       // }
          if(row[1].equals("")){ // так находим пустоту
              System.out.print("Find Null --> " + row[1]);
          }
          
        System.out.println();
      }
      }
      
}
      
