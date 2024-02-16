/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.me.members;

import Utensils.ScissorsAndComb;
import java.util.concurrent.Semaphore;

/**
 *
 * @author rafaelboeira
 */
public class Barber implements Runnable{
    private ScissorsAndComb tools;
    private Client client;
    private boolean status = false;
    private final Semaphore semaphore = new Semaphore(1);
    
    public Barber(ScissorsAndComb tools){
        this.tools = tools;
    }
    
    public void setClient(Client client) throws InterruptedException{
        semaphore.acquire();

        try {
            this.status = true;
            this.client = client;
        } finally {
            semaphore.release();
        }
        
        this.run();
        
    }
    
    public Client getClient(){
        return client;
    }
    
    @Override
    public void run() {
        try {
            this.tools.cut(client);
            System.out.printf("Ive just cutted the hair of client %d\n", client.getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
