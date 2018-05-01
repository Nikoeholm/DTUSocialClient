/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.mycompany.dtusocialclient;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import static com.mashape.unirest.http.Unirest.post;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Nikolaj
 * 
 */
public class Main {
    public static final String getUsersURL = "http://localhost:8080/DTUSocial/users/";
    public static final String loginURL = "http://localhost:8080/DTUSocial/login/";
    public static final String getTodosURL = "http://localhost:8080/DTUSocial/users/usertodo/";
    public static final String getChatURL = "http://localhost:8080/DTUSocial/chat/";
    public static String token = "";
    public static String username;
    static Gson gson = new Gson();
    
    public static void main(String[] args) throws UnirestException, IOException {
        String username, password;
        Scanner scan = new Scanner(System.in);
        System.out.println("Indtast studienummer: ");
        username = scan.nextLine();
        System.out.println("Indtast password");
        password = scan.nextLine();
        
        JSONObject json = new Login(username, password).toJSON();
        HttpResponse response = Unirest.post(loginURL)
                .header("Content-Type", "application/json")
                .body(json).asString();
        System.out.println("Login posted " + json.toString());
          
       
        String authBody = (String) response.getBody();
        String autheader = gson.fromJson(authBody, String.class);
        
        System.out.println(autheader);
        
        if(response.getStatus() != 200){
            String exceptionRes = "Der skete en fejl under login!" + "/n" + "Status code: " + response.getStatus() +"\n"+ response.getStatusText();
            System.out.println(exceptionRes);
            System.exit(1);
            
        }
        System.out.println("********************\n" +
                "Du er logget ind\n" +
                "********************\n");
        
        System.out.println("**************************************\n" +
                "Tryk T for at se dine todos\n" +
                "Tryk C for at se dine chatbeskeder\n" +
                "Tryk P for at se dine profil\n" +
                "Tryk E for at lukke programmet\n" +
                "**************************************\n");
        
        String tap = scan.nextLine().toLowerCase();
        while (true) {
            switch(tap) {
                case "t":
                    response = Unirest.get(getTodosURL)
                            .header("Content-Type", "application/json")
                            .header("Authorization", autheader).asJson();
                    
                    
                    
                    System.out.println(response.getBody());
                    break;
                    
                case "c":
                    response = Unirest.get(getChatURL)
                            .header("Content-Type", "application/json")
                            .header("Authorization", autheader).asJson();
                    System.out.println(response.getBody());
                    break;
                    
                case "p":
                    response = Unirest.get(getUsersURL + username)
                             .header("Content-Type", "application/json")
                            .header("Authorization", autheader).asJson();
                    System.out.println(username);
                    System.out.println(response.getBody());
                    
                    break;
                    
                case "e":
                    System.exit(0);
                    break;
                        
                    
                default:
                    System.out.println("**************************************\n" +
                            "Dette er ikke et ordentligt input, prøv et af disse i stedet:\n"+
                            "Tryk T for at få din todos\n" +
                            "Tryk C for at se dine chatbeskeder\n" +
                            "Tryk P for at se dine profil\n" +  
                            "Tryk E for at lukke programmet\n" + 
                            "**************************************\n");
                    break;
            }
            tap = scan.nextLine().toLowerCase();
        }
    }


}