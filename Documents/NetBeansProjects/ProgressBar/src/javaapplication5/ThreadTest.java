/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication5;

public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        ProgresBar.getInstance().setVisible(true);
        ProgresBar.getInstance().setLocationRelativeTo(null);
        ProgresBar.getInstance().setAlwaysOnTop(true);
            
        AnotherRun anotherRun = new AnotherRun();
        Thread childTread = new Thread(anotherRun);
        childTread.start();
        
        AnotherRun2 anotherRun1 = new AnotherRun2();
        Thread childTread1 = new Thread(anotherRun1);
        childTread1.start();
        
        for (int i = 0; i > -30; i--) {
            System.out.println("main" + i);
            ProgresBar.getInstance().setVal(i);
            Thread.sleep(1000);
        }
 
        childTread.join();
        childTread1.join();
        
        System.out.println("End");
    }
    public void start() throws InterruptedException{
     
        AnotherRun anotherRun = new AnotherRun();
        Thread childTread = new Thread(anotherRun);
        childTread.start();
        
        AnotherRun2 anotherRun1 = new AnotherRun2();
        Thread childTread1 = new Thread(anotherRun1);
        childTread1.start();
         
        ProgresBarRun anotherRunBar = new ProgresBarRun();
        Thread childTread2 = new Thread(anotherRunBar);
        childTread2.start();

 
        for (int i = 0; i > -30; i--) {
            System.out.println("main" + i);
            ProgresBar.getInstance().setVal(i);
            Thread.sleep(1000);
        }
        childTread.join();
        childTread2.join(); // поднимаем первый Шкалу
       // childTread.join();
        childTread1.join();
       // childTread2.join();
        System.out.println("End");
    }
}

class AnotherRun implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println("Thread 2 " + i);
            try {
                ProgresBar.getInstance().setVal(i);
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                System.out.println("Interrupt");
            }
        }
    }
}

class AnotherRun2 implements Runnable {
    @Override
    public void run() {//
        for (int i = 0; i < 100; i++) {
            System.out.println("Thread 3 " + i);
            try {
                ProgresBar.getInstance().setVal(i);
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                System.out.println("Interrupt");
            }
        }
    }
}

class ProgresBarRun implements Runnable {
    @Override
    public void run() {
        ProgresBar.getInstance().setVisible(true);
        ProgresBar.getInstance().setLocationRelativeTo(null);
        ProgresBar.getInstance().setAlwaysOnTop(true);
    }
}
