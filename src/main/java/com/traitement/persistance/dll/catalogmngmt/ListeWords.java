/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traitement.persistance.dll.catalogmngmt;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guillaume-PC
 */
public class ListeWords
{
    private static ListeWords listWordsInstance;
    private List<String> listWord;
    
    public ListeWords()
    {
        listWord = new ArrayList<String>();
    }
    
    public static ListeWords getInstance()
    {
        if(listWordsInstance == null)
            listWordsInstance = new ListeWords();

        return listWordsInstance;
    }
    
    public List<String> getListeWords()
    {
        return listWord;
    }
    
    public void setListeWords(List<String> list)
    {
        listWord = list;
    }
}
