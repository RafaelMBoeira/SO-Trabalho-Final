/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.me.saloon;

import com.me.Utensils.ScissorsAndComb;
import com.me.members.Barber;
import com.me.members.Client;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;

/**
 *
 * @author rafaelboeira
 */
public class Saloon {
    private ScissorsAndComb tools;
    private LinkedList<Barber> barbers;
    private LinkedList<Client> clients;
    private int maxCapacity;
    private final Semaphore newClient = new Semaphore(1);
    private final Semaphore modify = new Semaphore(1);
    
    public Saloon(int n){
        maxCapacity = n;
        tools = new ScissorsAndComb(n/2);
        clients = new LinkedList<>();
        barbers = new LinkedList<>();
        for(int i=0; i<n; i++)
            barbers.add(new Barber(i, tools));
    }
    
    public Barber removeBarber(){
        Barber barber;
        try{
            barber = barbers.pop();
        } catch(Exception e){
            return null;
        }
           
        return barber;
    }
    
    public Barber remOrInsBarber(Object x) throws InterruptedException{
        modify.acquire();
        if (x == null){
            Barber barber = removeBarber();
            modify.release();
            return barber;
        }
        
        barbers.push((Barber) x);
        modify.release();
        return null;
    }
    
    public boolean hasSeats(Client client) throws InterruptedException{
        newClient.acquire();
        System.out.printf("client: %d\n",client.getId());
        Barber barber = remOrInsBarber(null);
        if (barber == null){
            System.out.println("isnt available---------------------------");
            System.out.printf("size: %d\n", clients.size());
            newClient.release();
            return false;
        }
        
        client.setBarber(barber);
        barber.setClient(client);
        clients.add(client);
        System.out.println("isAvailable");
        System.out.printf("barber: %d\n", barber.getId());
        System.out.printf("size: %d\n", clients.size());
        newClient.release();
        return true;
    }
    /*
    public boolean hasSeats(Client client) throws InterruptedException{
        newClient.acquire();
        
        boolean flag = true;
        System.out.printf("client: %d\n",client.getId());
        Barber barber = null;
        try{
            barber = barbers.pop();
        } catch(NoSuchElementException e){
            System.out.println("isnt available---------------------------");
            System.out.printf("size: %d\n", clients.size());
            flag = false;
        }
        if (!flag){
            newClient.release();
            return false;
        }
        System.out.println("isAvailable");
        System.out.printf("barber: %d\n", barber.getId());
        client.setBarber(barber);
        barber.setClient(client);
        clients.add(client);
        
        System.out.printf("size: %d\n", clients.size());
        newClient.release();
        return flag;
    }
    */
    /*
    public boolean addNewClient(Client client) throws InterruptedException{
        newClient.acquire();
        System.out.printf("client: %d\n",client.getId());
        for(Barber barber: barbers){
            if (barber.isAvailable()){
                System.out.println("isAvailable");
                System.out.printf("barber: %d\n", barber.getId());
                
                clients.add(client);
                client.setBarber(barber);
                barber.setClient(client);
                
                System.out.printf("size: %d\n", clients.size());
                
                newClient.release();
                return true;
            }
        }
        newClient.release();
        System.out.println("isnt available");
        System.out.printf("size: %d\n", clients.size());
        return false;
    }
    */
    public void releaseBarber(Barber barber) throws InterruptedException{

        this.barbers.push(barber);
        

    }
    
    public void removeClient(Client client){
        this.clients.remove(client);
    }
    
    public static void main(String[] args) {
        int n = 10;
        int clients = 50;
        Saloon s = new Saloon(n);
        
        for(int i=0; i<clients; i++){
            Client newClient = new Client(i, s);
            Thread clientArrival = new Thread(newClient);
            clientArrival.start();
        }
      
    }
}
