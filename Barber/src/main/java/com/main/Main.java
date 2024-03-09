/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main;

import com.me.members.Client;
import com.me.saloon.Saloon;
import java.util.Scanner;

/**
 *
 * @author rafaelboeira
 */
public class Main {
    static int maxBarbers;
    static int maxWaitingClients;
    
    static void setInput(){
        maxBarbers = 5;
        maxWaitingClients = 10;
        
        Scanner input = new Scanner(System.in);
        boolean valid = false;
        
        while(!valid){
            try{
                System.out.print("Digite o número de barbeiros: ");
                maxBarbers = Integer.parseInt(input.next());
                if (maxBarbers <= 0)
                    maxBarbers = Integer.parseInt("");                                
            } catch(NumberFormatException nfe){
                System.out.println("Entrada Inválida!");
                continue;
            }
            valid = true;
        }
        valid = false;
        
        while(!valid){
            try{
                System.out.print("Digite o numero de cadeiras de espera: ");
                maxWaitingClients = Integer.parseInt(input.next());
                if (maxWaitingClients <= 0)
                    maxWaitingClients = Integer.parseInt("");
            } catch(NumberFormatException nfe){
                System.out.println("Entrada Inválida!");
                continue;
            }
            valid = true;
        }
    } 
    
    public static void main(String[] args) {
        setInput();
        Saloon s = new Saloon(maxBarbers, maxWaitingClients);
        
        Thread day = new Thread(s);
        day.start();
        try{
            day.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        
        s.showReport();
        s.showEvents();
    }
}
