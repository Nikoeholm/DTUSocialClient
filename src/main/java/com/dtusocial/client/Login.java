/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dtusocial.client;

import org.json.JSONObject;

/**
 *
 * @author Nikolaj
 */
public class Login {

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String username;
    public String password;


    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("username", username);
        jo.put("password", password);
        return jo;
    }
}
