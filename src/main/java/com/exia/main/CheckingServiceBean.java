/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exia.main;

import com.exia.integration.TextFileDAO;
import com.exia.util.TextFile;
import com.exia.util.ValidationStatus;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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
    
    @Inject
    private JMSContext context;

    @Resource(lookup = "jms/TextFileQueue")
    private Queue textFileQueue; 
    
    @Override
    public Boolean addTextFile(String text) 
    {
        if(text != null && text.length() > 0)
        {
            TextFile textFile = new TextFile();

            textFile.setText(text);
            textFile.setStatus(ValidationStatus.PENDING);

            textFileDAO.add(textFile);

            sendTextFile(textFile);
            
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private void sendTextFile(TextFile textFile)
    {
        JAXBContext jaxbContext;
        
        try 
        {
            jaxbContext = JAXBContext.newInstance(TextFile.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            StringWriter writer = new StringWriter();

            jaxbMarshaller.marshal(textFile, writer);
            String xmlMessage = writer.toString();

            System.out.println(xmlMessage);

            TextMessage msg = context.createTextMessage(xmlMessage);

            context.createProducer().send(textFileQueue, msg);
        } 
        catch(JAXBException ex)
        {
            Logger.getLogger(CheckingServiceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public TextFile deleteStoredTextFile(Long id) 
    {
        TextFile t = textFileDAO.delete(id);
        
        if(t != null)
        {
            sendTextFile(t);
        }
        
        return t;
    }
}
