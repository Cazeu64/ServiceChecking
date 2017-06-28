/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exia.reception;

import com.exia.integration.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author Guillaume-PC
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/QueueTransferFile")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MessageReceiver implements MessageListener {
    
    public MessageReceiver() {
    }
    
    @Inject
    private ProcessorInterface processor;
    
    @Override
    public void onMessage(Message message)
    {
        try 
        {
            System.out.println("Message reçu : " + message.getBody(String.class));
            String fileTxt = message.getBody(String.class);

            if(processor.processChecking(createFileFromSring(fileTxt)))
            {
                //Stoper tous les test sur ce fichier, clef trouvé
                
            }
        } 
        catch (JMSException ex) 
        {
            Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public File createFileFromSring(String fileString)
    {
        String[] tabString = fileString.split("\\|");

        for(String l : tabString)
        {
            System.out.println("Split : " + l);
        }
        
        File fileRet = new File();
        fileRet.setContent(tabString[0]);
        fileRet.setName(tabString[1]);
        fileRet.setKey(tabString[2]);
        
        return fileRet;
    }
}
