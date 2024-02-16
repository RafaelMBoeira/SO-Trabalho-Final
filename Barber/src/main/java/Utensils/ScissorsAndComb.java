/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utensils;

import com.me.members.Barber;
import com.me.members.Client;
import java.util.concurrent.Semaphore;

/**
 *
 * @author rafaelboeira
 */
public class ScissorsAndComb {
    private final Semaphore semaphore;
    
    public ScissorsAndComb(int qtd){
        this.semaphore = new Semaphore(qtd);
    }
    
    public boolean cut(Client client) throws InterruptedException{
        semaphore.acquire();

        try {
            for (int i=0; i<client.getCutTime(); i++){}
        } finally {
            // Libera a permissão do semáforo
            semaphore.release();
        }
        
        return true;
    }

}
