/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.me.members;

import com.me.saloon.Saloon;
import java.util.Random;

/**
 *
 * @author rafaelboeira
 */
public class Client implements Runnable{
    private int id;
    private int entryTime;
    private int cutTime;
    private Saloon room;
    
    public Client(int id, Saloon room){
        Random timer = new Random();
        this.cutTime = timer.nextInt(5);
        this.room = room;
    }
    
    @Override
    public void run(){
        
    }
    
    public int getCutTime(){
        return cutTime;
    }
    
    public int getId(){
        return id;
    }
}
