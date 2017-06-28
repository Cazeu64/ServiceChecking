package com.traitement.persistance.catalog;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;



/**
 *
 * @author Mimi
 */
@Entity
@Table(name = "mots")
public class Word implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    
    @Column(name="mot")
    private String word;

    public Word() {
        
    }
    
    public static List<Word> fillListFromString(String words){
        
        System.out.println("String to list");
        words = words.trim();
        words = words.replaceAll(" ", "");
        String[] list = words.split(",");
        List<Word> wordsList = new ArrayList<>(); 
        for (String l : list) {
            Word w = new Word();
            w.setWord(l);
            wordsList.add(w);
            System.out.println(w.getWord());
        }
        return wordsList;
    }
    
    /*********************/
    /*  Getter / Setter */
    /********************/
    
    public Long getId() {
        return id;
    }
    
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

    @Override
    public int hashCode() {
        
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
        
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Word other = (Word) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.word, other.word);
    }

    @Override
    public String toString() {
        return "Word{" + "id=" + id + ", word=" + word + '}';
    }
    
    

}
