/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exia.integration;

import java.io.Serializable;

/**
 *
 * @author Guillaume-PC
 */
public class File implements Serializable
{
    private long id;
    private String name;
    private String content;
    private String key;
    private String tokenUser;

    public String getContent() {
        return content;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTokenUser() {
        return tokenUser;
    }

    public void setTokenUser(String tokenUser) {
        this.tokenUser = tokenUser;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() 
    {
        return content + "|" + name + "|" + key + "|" + tokenUser;
    }
}
