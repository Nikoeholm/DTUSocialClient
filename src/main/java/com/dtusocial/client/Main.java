package com.dtusocial.client;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.util.Scanner;
import org.json.JSONObject;

/**
 *
 * @author Nikolaj
 *
 */
public class Main {
    public static final String BASE_URL = "http://130.225.170.246:8080/DTUSocial-1.0";
    public static final String USERS_URL = BASE_URL +"/users/";
    public static final String LOGIN_URL = BASE_URL +"/login/";
    public static final String CHAT_URL = BASE_URL +"/chat/";
    
    public static final String TODOS_PATH = "/todos";
    public static String token = "";
    public static String username;
    
    static Gson gson = new Gson();
    
    public static void main(String[] args) throws UnirestException, IOException {
        String username, password;
        Scanner scan = new Scanner(System.in);
        System.out.println("Indtast studienummer: ");
        username = scan.nextLine().toLowerCase();
        System.out.println("Indtast password");
        password = scan.nextLine();
        
        JSONObject json = new Login(username, password).toJSON();
        HttpResponse response = Unirest.post(LOGIN_URL)
                .header("Content-Type", "application/json")
                .body(json).asString();
        System.out.println("Login posted");
        
        
        String authBody = (String) response.getBody();
        String autheader = gson.fromJson(authBody, String.class);

        
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
                    response = Unirest.get(USERS_URL + username + TODOS_PATH)
                            .header("Content-Type", "application/json")
                            .header("Authorization", autheader).asJson();
                    System.out.println(response.getBody());
                    break;
                    
                case "c":
                    response = Unirest.get(CHAT_URL)
                            .header("Content-Type", "application/json")
                            .header("Authorization", autheader).asJson();
                    System.out.println(response.getBody());
                    break;
                    
                case "p":
                    response = Unirest.get(USERS_URL + username)
                            .header("Content-Type", "application/json")
                            .header("Authorization", autheader).asJson();
                    System.out.println(response.getBody());
                    break;
                    
                case "e":
                    System.exit(0);
                    break;
                    
                    
                default:
                    System.out.println("**************************************\n" +
                            "Dette er ikke et ordentligt input, pr�v et af disse i stedet:\n"+
                            "Tryk T for at f� din todos\n" +
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