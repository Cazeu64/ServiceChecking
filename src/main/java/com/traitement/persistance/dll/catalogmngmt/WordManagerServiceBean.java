/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traitement.persistance.dll.catalogmngmt;

import com.traitement.persistance.catalog.Word;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Mimi
 */
@Stateless(name="WordManager")
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@LocalBean //facultatif
public class WordManagerServiceBean {

   @PersistenceContext(unitName = "wPu")
   EntityManager em;

   @PreDestroy
   void prevent(){
       System.out.println("instance va être détruite");
   }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS) //méthode pouvant joindre le contexte transactionnel de l'appelant
    public List<String> getAllWords() {
        String q= "SELECT w FROM Word w";
        TypedQuery<Word> query = em.createQuery(q,Word.class);
       
        List<Word> results = query.getResultList();
        List<String> listWords = new ArrayList<>();
        
        for(Word w : results){
            listWords.add(w.getWord());
        }
        return listWords;
    }


    
     /**
     * retourner le mot correspondant à la recherche
     * Comportement transactionnel redéfini (SUPPORTS)
     * @param word le mot à trouver
     * @return le mot trouvé
     */ 
    @TransactionAttribute(TransactionAttributeType.SUPPORTS) //méthode pouvant joindre le contexte transactionnel de l'appelant
    public Word findWord(String research) {
        research = "'"+research+"'";
        String q= "SELECT w From Word w where w.word = "+research;
        TypedQuery<Word> query = em.createQuery(q,Word.class);
        //query.setParameter("research", research);
        List<Word> words = query.getResultList();
          
        if(words.isEmpty()){
            return null;
        }else{
             return words.get(0);
        }

    }   
}
