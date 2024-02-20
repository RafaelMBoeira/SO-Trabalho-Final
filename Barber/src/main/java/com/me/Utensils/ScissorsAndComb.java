/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.me.Utensils;

import com.me.members.Barber;
import com.me.members.Client;
import static java.lang.Thread.sleep;
import java.util.concurrent.Semaphore;

/**
 *
 * @author rafaelboeira
 */
public class ScissorsAndComb {
    private final Semaphore semScissors;
    private final Semaphore semComb;
    
    public ScissorsAndComb(int qtd){
        this.semScissors = new Semaphore(qtd);
        this.semComb = new Semaphore(qtd);
    }
    
    public void usingScissor(Client client) throws InterruptedException{
        semScissors.acquire();
        
        try {
            sleep(client.getCutTime()/2);
            System.out.printf("Scissor: %d\n", client.getId());
        } finally {
            // Libera a permissão do semáforo
            semScissors.release();
        }
    }
    
    public void usingComb(Client client) throws InterruptedException{
        semComb.acquire();
        
        try {
            sleep(client.getCutTime()/2);
            System.out.printf("Comb: %d\n", client.getId());
            client.quit();
        } finally {
            // Libera a permissão do semáforo
            semComb.release();
        }
    }
    
    public void cut(Client client) throws InterruptedException{
        usingScissor(client);
        usingComb(client);
    }

}
