/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traitement.persistance.dll.catalogmngmt;

import com.traitement.persistance.catalog.Result;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Mimi
 */
@Stateless(name="ResultManager")
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@LocalBean
public class ResultManagerServiceBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
   @PersistenceContext(unitName = "wPu")
   EntityManager em;
   
   @PreDestroy
   void prevent(){
       System.out.println("instance va être détruite");
   }

   /**
    *
    * @param r result à persister
    * sauvegarder en base l'état d'un result nouvellement créé
    * @return le result persisté
    */
    public Result saveResult(Result r){      
        em.persist(r);
        System.out.println("Result created : " + r.toString());
        return r;
    }
}
