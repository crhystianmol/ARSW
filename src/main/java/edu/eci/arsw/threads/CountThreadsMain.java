/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThreadsMain {
    
    public static void main(String a[]){
        int A = 0;
        int B = 15;
        
        Thread n = new Thread(new CountThread(A,B));
        Thread n1 = new Thread(new CountThread(0,99));
        Thread n2 = new Thread(new CountThread(99,199));
        Thread n3 = new Thread(new CountThread(200,299));
        
        //n.run(A,B);
        
        /*Cambie el incio con 'start()' por 'run()'. Cómo cambia la salida?
        con start() se puede ver que se ejecutan los hilos casi al tiempo
        con run() se imprime segun el orden de ejecucion 
        , por qué?
        porque el start es un metodo ya creado que usa el paralelismo
        y el metodo run fue modificado por nosotros y lo ejecuta por llamado en 
        serie.
        */
        
        n1.start();
        n2.start();
        n3.start();
        //n1.run();
        //n2.run();
        //n3.run();
    }
    
    
    
}
