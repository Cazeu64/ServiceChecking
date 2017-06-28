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
    private String name;
    private String content;
    private String extract = "";
    private String key;

    public String getContent() {
        return content;
    }

    public String getExtract() {
        if(extract.equals(""))
        {
            //Calculer l'extrait
            return extract;
        }
        else
        {
            return extract;
        }
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

    public void setExtract(String extract) {
        this.extract = extract;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() 
    {
        return content + "|" + name + "|" + key;
    }
    
    
}
