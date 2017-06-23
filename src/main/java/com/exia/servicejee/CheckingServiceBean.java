/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exia.servicejee;

import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 *
 * @author Guillaume-PC
 */
@Stateless
@WebService(endpointInterface="com.exia.servicejee.CheckingServiceEndpointInterface", portName="CheckingPort", serviceName="CheckingService")
public class CheckingServiceBean implements CheckingServiceEndpointInterface
{
    @Override
    public Boolean isValideText(String text) 
    {
        return text.length() > 10 ? true : false;
    }
}
