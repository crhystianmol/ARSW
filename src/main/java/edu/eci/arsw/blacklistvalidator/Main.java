/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import java.util.List;


public class Main {
    
    public static void main(String a[]){
        HostBlackListsValidator hblv=new HostBlackListsValidator();
        List<Integer> blackListOcurrences=hblv.checkHost("200.24.34.55",50);
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);

        blackListOcurrences=hblv.checkHost("202.24.34.55",120);
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);

        blackListOcurrences=hblv.checkHost("212.24.24.55",30);
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);

    }
    
}
