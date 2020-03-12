package test_to_work_git.Thread;

import thread.ProgresBarMy;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        /*ProgresBar.getInstance().setVisible(true);
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
        */
        
        ThreadTest start = new ThreadTest();
        try
        {
        start.start();
         }
            catch (InterruptedException e) {
                System.out.println("Interrupt");
            }
    }
    public void start() throws InterruptedException{
        //ProgresBar.getInstance().setVisible(true);
        //ProgresBar.getInstance().setLocationRelativeTo(null);
        //ProgresBar.getInstance().setAlwaysOnTop(true);
        
        AnotherRun anotherRun = new AnotherRun();
        Thread childTread = new Thread(anotherRun);
        childTread.start();
        
        
        AnotherRun2 anotherRun1 = new AnotherRun2();
        Thread childTread1 = new Thread(anotherRun1);
        childTread1.start();
        /* 
        ProgresBarRun anotherRunBar = new ProgresBarRun();
        Thread childTread2 = new Thread(anotherRunBar);
        childTread2.start();
        */
 
        for (int i = 0; i > -10; i--) {
            System.out.println("main" + i);
            ProgresBarMy.getInstance().setVal(i);
            Thread.sleep(1000);
        }
        
        
        childTread.join();
        childTread1.join();
        //childTread2.join(); // поднимаем первый Шкалу
        System.out.println("End");
    }
}

class AnotherRun implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 15; i++) {
            System.out.println("Thread 2 " + i);
            try {
                ProgresBarMy.getInstance().setVal(i);
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
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("Thread 3 " + i);
            try {
                ProgresBarMy.getInstance().setVal(i);
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                System.out.println("Interrupt");
            }
        }
    }
}

class AnotherRunFrame implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 15; i++) {
            System.out.println("Thread AnotherRunFrame " + i);
            try {
                ProgresBar.getInstance().setVal(i);
                Thread.sleep(500);
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
