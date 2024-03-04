/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.me.utensils;

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
    
    public void cut(Client client) throws InterruptedException{
        semScissors.acquire();
        semComb.acquire();
        sleep(client.getCutTime());
        semComb.release();
        semScissors.release();
    }

}
