/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exia.reception;

import com.exia.integration.File;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
            String fileTxt = message.getBody(String.class);

            ObjectMapper mapper = new ObjectMapper();
            System.out.println("======================================");
            System.out.println("JSON recu : " + fileTxt);
            List<File> files = mapper.readValue(fileTxt, new TypeReference<List<File>>(){});
            
            //Solution avec Json
            if(processor.processChecking(files))
            {
                //Stoper tous les test sur ce fichier, clef trouvé
                
            }

            //Solution sans JSON
            /*if(processor.processChecking(createFilesFromSring(fileTxt)))
            {
                //Stoper tous les test sur ce fichier, clef trouvé
                
            }*/
        } 
        catch (JMSException ex) 
        {
            Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) 
        {
            Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
