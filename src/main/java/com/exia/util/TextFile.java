/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exia.util;

import java.io.Serializable;

/**
 *
 * @author Guillaume-PC
 */
public class TextFile implements Serializable
{
    private long id;
    private String text;
    private ValidationStatus status;

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public ValidationStatus getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setStatus(ValidationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Text ID : " + id + " , text content decrypted : " + text;
    } 
}
