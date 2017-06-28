/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exia.reception;

import com.exia.integration.File;
import javax.ejb.Stateless;

/**
 *
 * @author Guillaume-PC
 */
@Stateless
public class Processor implements ProcessorInterface {

    @Override
    public Boolean processChecking(File file)
    {
        //Démarrer le code de validation ici
        System.out.println("Début de la vérification");
        return true;
    }
}
