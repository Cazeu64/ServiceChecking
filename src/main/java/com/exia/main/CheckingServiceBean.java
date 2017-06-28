/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exia.main;

import com.exia.integration.TextFileDAO;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;

/**
 *
 * @author Guillaume-PC
 */
@Stateless
@WebService(endpointInterface="com.exia.main.CheckingServiceEndpointInterface", portName="CheckingPort", serviceName="CheckingService")
public class CheckingServiceBean implements CheckingServiceEndpointInterface
{
    @Inject
    private TextFileDAO textFileDAO;

    @Override
    public Boolean addTextFile(String file) 
    {
        if(file != null)
        {
            //Traiter l'objet File
            System.out.println("Fichier bien reçu : " + file);
            return true;
        }
        else
        {
            System.out.println("Erreur de reception de fichier");
            return false;
        }
    }
}
