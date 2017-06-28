/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exia.reception;

import com.exia.integration.File;
import com.traitement.persistance.dll.catalogmngmt.CatalogManagerService;
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
    
    public float TAUX_CONFIANCE = (float) 0.8;
    public float TAUX_ECHANTILLONNAGE = (float) 0.8;

    public Processor() {

    }
    
    @Override
    public Boolean processChecking(File file)
    {
        //Démarrer le code de validation ici
        
        System.out.println("Début de la vérification");
        //Reconstitution de l'objet File
        
        return catalogManagerService.checkFile(file, TAUX_CONFIANCE, TAUX_ECHANTILLONNAGE);
    }
}
