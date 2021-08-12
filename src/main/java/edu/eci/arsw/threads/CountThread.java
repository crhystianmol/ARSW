/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThread implements Runnable{
    private int A;
    private int B;
    
    public CountThread(int A,int B){
        this.A = A;
        this.B = B;
        
    }
    
    @Override
    public void run(){
        try {
            for (int contar=A; contar<=B; contar++){
                Thread.sleep(100);
                System.out.println(contar);
            }
        }catch (InterruptedException exc){
            System.out.println("Interrumpido.");
        }
        System.out.println("Terminando ");
    }
    
}
