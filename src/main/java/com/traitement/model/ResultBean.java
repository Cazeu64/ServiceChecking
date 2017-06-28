/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traitement.model;

import com.exia.integration.File;
import com.traitement.persistance.catalog.Result;
import com.traitement.persistance.catalog.Word;

import com.traitement.persistance.dll.catalogmngmt.CatalogManagerService;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;

import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Mimi
 */
@Named
@SessionScoped
public class ResultBean implements Serializable{
    
    @Inject
    private CatalogManagerService catalogManager;

    /**
     * Initie la création d'un result
     * @return chaine de navigation
     */
    public String createResult(File file, String echant, float tauxE, float tauxC){
        catalogManager.createResult(file.getName(), echant, file.getKey(), tauxE, tauxC);
        return "";
    }
}
