/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.me.members;

import com.me.utensils.ScissorsAndComb;
import java.util.concurrent.Semaphore;

/**
 *
 * @author rafaelboeira
 */
public class Barber implements Runnable{
    private int id;
    private ScissorsAndComb tools;
    private Client client;
    
    public Barber(int id, ScissorsAndComb tools){
        this.id = id;
        this.tools = tools;
    }
    
    public void setClient(Client client){
        this.client = client;
    }
    
    public Client getClient(){
        return client;
    }
    
    public int getId(){
        return id;
    }
    
    @Override
    public void run() {
        try {
            this.tools.cut(client);
            client.quit();            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
