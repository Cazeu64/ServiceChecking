/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exia.main;

import com.exia.integration.File;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 *
 * @author Guillaume-PC
 */

@WebService(name = "CheckingEndpoint")
public interface CheckingServiceEndpointInterface 
{
    @WebMethod(operationName = "checkValidity")
    @WebResult(name = "textValidity")
    Boolean addTextFile(@WebParam(name = "textToCheck") String file);
}
