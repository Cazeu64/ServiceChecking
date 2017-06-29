/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exia.reception;

import com.exia.integration.File;
import com.traitement.persistance.dll.catalogmngmt.CatalogManagerService;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Guillaume-PC
 */
@Stateless
public class Processor implements ProcessorInterface {

    @Inject
    private CatalogManagerService catalogManagerService;
    
    public float TAUX_CONFIANCE = (float) 0.4;
    public float TAUX_ECHANTILLONNAGE = (float) 0.1;

    public Processor() {

    }
    
    @Override
    public Boolean processChecking(List<File> files)
    {
        //Démarrer le code de validation ici
        
        System.out.println("Début de la vérification, Clef : " + files.get(0).getKey());

        return catalogManagerService.checkFile(files, TAUX_CONFIANCE, TAUX_ECHANTILLONNAGE);
    }
}