/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.me.saloon;

import Utensils.ScissorsAndComb;
import com.me.members.Barber;
import com.me.members.Client;
import java.util.ArrayList;

/**
 *
 * @author rafaelboeira
 */
public class Saloon {
    private ScissorsAndComb tools;
    private ArrayList barbers;
    
    public Saloon(int n){
        tools = new ScissorsAndComb(n/2);
        barbers = new ArrayList<Barber>(n);
        
        for(int i=0; i<n; i++){
            barbers.set(i, new Barber(tools));
        }
    }
    
    public static void main(String[] args) {
        int n = 10;
        int clients = 100;
        
        
        for(int i=0; i<clients; i++){
            Client new_client = new Client(i);
            
        }
    }
}
