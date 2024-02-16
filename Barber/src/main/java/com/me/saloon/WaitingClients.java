/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.me.saloon;


import com.me.members.Client;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
/**
 *
 * @author rafaelboeira
 */
public class WaitingClients {
    private int occupiedChairs;
    private int maxCapacity;
    private ArrayList clientQueue; 
    private final Semaphore semaphore;
    
    public WaitingClients(int maxCapacity){
        this.maxCapacity = maxCapacity;
        this.occupiedChairs = 0;
        this.semaphore = new Semaphore(maxCapacity/2);
        this.clientQueue = new ArrayList<Client>(maxCapacity/2);
    }
    
    public boolean clientArrived(Client person) throws InterruptedException{
        if (occupiedChairs == maxCapacity)
            return false;
        occupiedChairs++;
        clientQueue.add(person);
        
        semaphore.acquire();
        try {
            // Seção crítica: operações no recurso compartilhado
            
            System.out.println("Counter: " + counter);
        } finally {
            // Libera a permissão do semáforo
            semaphore.release();
            occupiedChairs--;
        }
        
    }
    
}
