/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.me.saloon;

import com.me.utensils.ScissorsAndComb;
import com.me.members.Barber;
import com.me.members.Client;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author rafaelboeira
 */
public class Saloon implements Runnable {
    private ScissorsAndComb tools;
    private LinkedList<Barber> barbers;
    private LinkedList<Client> waitingClients;
    private LinkedList<Client> cuttingClients;
    private LinkedList<HashMap<String, LinkedList<Client>>> events;
    
    private int maxBarbers;
    private int maxWaitingClients;
    
    private int seatedClients = 0;
    private int totalClients = 0;
    private int attendedClients = 0;
    
    private final Semaphore newClient = new Semaphore(1);
    private final Semaphore barberAssignal = new Semaphore(1);    
    private final Semaphore concurrentCuts;
           
    public Saloon(int n, int m){
        maxBarbers = n;
        maxWaitingClients = m;
        
        tools = new ScissorsAndComb((int)(n/2));
        
        waitingClients = new LinkedList<>();
        cuttingClients = new LinkedList<>();
        barbers = new LinkedList<>();
        events = new LinkedList<>();
        
        for(int i=0; i<maxBarbers; i++)
            barbers.add(new Barber(i, tools));
        
        concurrentCuts = new Semaphore(maxBarbers);
    }
    
    public void finishCut(Barber barber, Client client){
        barbers.add(barber);
        cuttingClients.remove(client);
        saveState();
    }        
    
    public void assignBarber(Client client) throws InterruptedException {
        //System.out.printf("Barbeiro do cliente %d será escolhido\n", client.getId());
        concurrentCuts.acquire();
        barberAssignal.acquire();
        
        Barber barber = barbers.pop();
        
        seatedClients--;
        waitingClients.remove(client);
        cuttingClients.add(client);
        
        //System.out.printf("client: %d barber: %d\n", client.getId(), barber.getId());
        //System.out.printf("asb_size: %d\n", seatedClients);
        
        client.setBarber(barber);
        barber.setClient(client);        
        
        saveState();
        barberAssignal.release();
        
        Thread cutting = new Thread(barber);            
        cutting.start();
        cutting.join();
        
        //System.out.printf("cliente: %d liberadooooooooooooooo\n", client.getId());
        concurrentCuts.release();
    }
    
    public boolean hasSeats(Client client) throws InterruptedException{
        newClient.acquire();
        totalClients++;
        //System.out.printf("cliente %d chegou\n", client.getId());
        if (this.isFull()){
            //System.out.println("no seats---------------------------");
            //System.out.printf("nc_size: %d\n", seatedClients);
            newClient.release();
            return false;
        }
        waitingClients.add(client);
        seatedClients++;
        attendedClients++;
        
        saveState();
        //System.out.println("isAvailable __________________");
        //System.out.printf("nc_size: %d\n", seatedClients);
        
        newClient.release();
        return true;
    }
    
    public boolean isFull(){
        return seatedClients == maxWaitingClients;    
    }
    
    public void showReport(){
        System.out.printf("Total de Clientes: %d\nClientes Atendidos: %d\n", totalClients, attendedClients);
    }
    
    public void saveState(){
        HashMap<String, LinkedList<Client>> state = new HashMap<>();
        
        state.put("waiting", (LinkedList<Client>)waitingClients.clone());
        state.put("cutting", (LinkedList<Client>)cuttingClients.clone());
        
        events.add(state);
    }
    
    public void showEvents(){
        int i = 0;
        for(HashMap<String, LinkedList<Client>> state: events){
            System.out.printf("\n\n\n-------- Evento %d | Cadeiras de Barbear: %d | Cadeiras de Espera: %d --------\n", i+1, maxBarbers, maxWaitingClients);
            i++;
            LinkedList<Client> wc = state.get("waiting");
            LinkedList<Client> cc = state.get("cutting");
            
            for (Client client: wc){
                System.out.print("c" + client.getId() + " | ");
            }
            System.out.println(" Cadeiras de Espera\n_____________________________________________");
            for (Client client: cc){
                System.out.print("c" + client.getId() + " | ");
            }
            System.out.println(" Cadeiras de Barbear");
        }
    }
    
    @Override
    public void run(){
        int clients = 50;
        LinkedList<Thread> clientThreads = new LinkedList<>();
        
        for(int i=0; i<clients; i++){
            Client newClient = new Client(i, this);
            
            Thread clientArrival = new Thread(newClient);
            clientArrival.start();
            clientThreads.add(clientArrival);
            
            try{
                sleep(100*(new Random()).nextInt(5));
            } catch(InterruptedException e){}
        }
        
        for(Thread t: clientThreads){
            try{
                t.join();
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }    
    
}
