/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckAndConstractionDEvent {
    File file;
    List<String> listStstist = new ArrayList();  // Лист статистики дл передачи в WEB

    public CheckAndConstractionDEvent(File file){
        this.file = file;
    }
    
    public List<String> getData () throws UnsupportedEncodingException, FileNotFoundException, IOException{
            InputStreamReader inputsream = new InputStreamReader(new FileInputStream(file), "UTF8"); // Правильное отображение русскаого языка чтение буыера такое себе
            BufferedReader bufferedReader = new BufferedReader(inputsream); //goryachiefanatky
            String line = bufferedReader.readLine();
            while(line != null) {
            String[] masTmp = line.split("\t");
            String tmp ="";
            
           
            
           String checkedStr = null;//
              if ( masTmp.length>1 ){ // проверка на разбитие есть ли что там
                   checkedStr = checkSplitError(line);
                   // Пока исключим эту проверку но зачем то я это ведь сделал до этого 
              /*      if (!checkedStr.equals(null)){ // прогоним по регуляркам
                // Если программа по анализу не справилась то еще оин анализ
                for(int i=1; i<masTmp.length; ++i){
                    tmp = tmp + masTmp[i];
                    }
                    tmp = dateConvert(masTmp[0])  + "\t" + checkDataLog(tmp);
                    listStstist.add(tmp);
                    //System.out.println(tmp);
                    //System.out.println(dateConvert(masTmp[0]));
                }
            else for (String s : masTmp) System.out.println("Bad_data "+ s);
            line = bufferedReader.readLine();
              */
            }
              if (checkedStr != null){
                  listStstist.add(checkedStr);//koronoval
                  line = bufferedReader.readLine();
              } else line = bufferedReader.readLine();
              
            }
            bufferedReader.close();
        return listStstist;
    }

    // --- ПРоверка на неверные разделители и прочее ---
    String checkSplitError(String str){
        String data = "";
        String time = "";
        String tmpStr = "";
        // регулярка для даты 31.05.19 и 2019.05.27 
        Pattern pattern1 = Pattern.compile("^(.*)([0-9]{4}\\.[0-9]{1,2}\\.[0-9]{1,4})(.*)$");
        Pattern pattern2 = Pattern.compile("^(.*)([0-9]{4}\\.[0-9]{2}\\.[0-9]{2})(.*)$"); 
        // патерн времени  02:04:29.507 11:40:47.000
        Pattern pattern3 = Pattern.compile("^(.*)([0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{3})(.*)$");
        Pattern pattern4 = Pattern.compile("^(.*)([0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}\\.[0-9]{1,3})(.*)$"); 
        Matcher matcher1 = pattern1.matcher(str);
        Matcher matcher2 = pattern2.matcher(str);
        if (matcher1.matches()){ // ищем дату находит
            if (matcher1.group(2).isEmpty()){ // нет нечего во времени
                if (matcher2.matches()){       
                    if (matcher2.group(2).isEmpty()){ // нет нечего во времени
                        // Вообще ненашли времени
                                return null;
                    }
                    else {
                    data = matcher2.group(2);
                    }
                }
            }
            else {
            tmpStr = matcher1.group(1) +   matcher1.group(3);
            data = matcher1.group(2);
            Matcher matcher3 = pattern3.matcher(tmpStr);
            Matcher matcher4 = pattern4.matcher(tmpStr);
            if (matcher3.matches()){ // ищем дату находит
              if (matcher3.group(2).isEmpty()){ // нет нечего во времени
                if (matcher4.matches()){       
                    if (matcher4.group(2).isEmpty()){ // нет нечего во времени
                        // Вообще ненашли времени
                                return null;
                    }
                    else {
                    tmpStr = matcher4.group(1) +   matcher4.group(3);
                    time = matcher4.group(2);
                    }
                }
            }
            tmpStr = matcher3.group(1) +   matcher3.group(3);
            time = matcher3.group(2);
            }
            }
          
            }
        String  t1 ="";
        for(String s : tmpStr.split("\t")){
            t1 = t1 + s;
        }
        return dateConvert(data + " " + time) + "\t" + checkDataLog(t1);
    }

    // --- отдельный метод для преобразования строк ---
    String dateConvert(String str){
        String  returnDate = "";
        // регулярка для времени 31.05.19 11:40:47.000
        Pattern pattern1 = Pattern.compile("^(([0-9]{1,2})\\.([0-9]{1,2})\\.([0-9]{1,4})) (([0-9]{1,2}):([0-9]{1,2}):([0-9]{1,2})\\.([0-9]{1,3}))$"); 
        // еще один патерн времени 2019.05.27 02:04:29.507
        Pattern pattern2 = Pattern.compile("^(([0-9]{4})\\.([0-9]{2})\\.([0-9]{2})) (([0-9]{2}):([0-9]{2}):([0-9]{2})\\.([0-9]{3}))$"); 
        Matcher matcher1 = pattern1.matcher(str);
        Matcher matcher2 = pattern2.matcher(str);
        String millisecond,  second, minute, hour, day, month,year;
        if (matcher1.matches()){ 
            day = matcher1.group(2); 
            month = matcher1.group(3); 
            year = "20" + matcher1.group(4); // такие приколы с годом
            hour = matcher1.group(6); 
            minute = matcher1.group(7); 
            second = matcher1.group(8); 
            millisecond = matcher1.group(9);
            // парсер работает
            //System.out.println(month + "\t" + day + "\t" + year + "\t" + hour + "\t" + minute + "\t" + second + "\t" + millisecond);
            //2016-07-19T20:24:01.804
            returnDate = year +"-"+ month+"-"+day+"T"+hour+":"+minute+":"+second+"."+millisecond;
            return returnDate;
        }
        if (matcher2.matches()){ 
            day = matcher2.group(4); 
            month = matcher2.group(3); 
            year = matcher2.group(2);
            hour = matcher2.group(6); 
            minute = matcher2.group(7); 
            second = matcher2.group(8); 
            millisecond = matcher2.group(9);
            // парсер работает
            //System.out.println(month + "\t" + day + "\t" + year + "\t" + hour + "\t" + minute + "\t" + second + "\t" + millisecond);
            returnDate = year +"-"+ month+"-"+day+"T"+hour+":"+minute+":"+second+"."+millisecond;
            return returnDate;
        }
        return null;            
    }

        // --- разбор строки логов ---
    String checkDataLog(String str){
        Pattern pattern1 = Pattern.compile("^(.*)\\. (.*)$");
        Pattern pattern2 = Pattern.compile("^(.*)(снялось)(.*)$");
        //Pattern pattern3 = Pattern.compile("^(.*)снялось(.*)$");
        Matcher matcher1 = pattern1.matcher(str);
        Matcher matcher2 = pattern2.matcher(str);
        //Matcher matcher3 = pattern3.matcher(str);

        if (matcher1.matches()){
            return matcher1.group(1)+"\t"+"Важное сообщение"+"\t"+"появился"; 
            //return "1.41525268554688	FALSE";
        }
        if (matcher2.matches()){
            return matcher2.group(1)+"\t"+"Внимание сообщение"+"\t"+"снялся"; 
            //return "1.41525268554688	FALSE";
        }
        return str+"\t"+"Неопределенное сообщение"+"\t"+"появился";
        //return "1.41525268554688	FALSE";
    }
}
