/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.me.members;

import com.me.saloon.Saloon;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author rafaelboeira
 */
public class Client implements Runnable{
    private int id;
    private int cutTime;
    private Saloon saloon;
    private Barber barber;
    
    public Client(int id, Saloon saloon){
        this.id = id;
        Random timer = new Random();
        this.cutTime = 1*(3 + timer.nextInt(3));
        this.saloon = saloon;
    }
    
    public void setBarber(Barber barber) {
        this.barber = barber;
    }
    
    public void quit(){
        this.saloon.removeClient(this);
        try{
            this.saloon.remOrInsBarber(barber);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void run(){
        try{
            if (saloon.hasSeats(this)){
                Thread cutting = new Thread(barber);
                cutting.start();
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public int getCutTime(){
        return cutTime;
    }
    
    public int getId(){
        return id;
    }
}
