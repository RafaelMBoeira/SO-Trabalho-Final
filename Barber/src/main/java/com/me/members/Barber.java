/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.me.members;

import com.me.Utensils.ScissorsAndComb;
import java.util.concurrent.Semaphore;

/**
 *
 * @author rafaelboeira
 */
public class Barber implements Runnable{
    private int id;
    private ScissorsAndComb tools;
    private Client client;
    private final Semaphore semaphore = new Semaphore(1);
    
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
            System.out.printf("%d just cutted the hair of client %d, in %d seconds\n",id,  client.getId(), client.getCutTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
