/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.traitement.persistance.dll.catalogmngmt;


import com.exia.integration.File;
import com.traitement.persistance.catalog.Result;
import java.util.List;
import javax.ejb.Local;


/**
 *
 * @author asbriglio
 */
@Local
public interface CatalogManagerService {

    public Result createResult(String file, String echantillon, String key, Float tauxE, Float tauxC);

    public Boolean checkFile(List<File> files, float tauxC, float tauxE);
    
    public List<String> getAllWords();
    
    public List<String>findEmails(String words);
    
    public void sendToCCharp(List<File> files, float tauxR, List<String> listMail);
}
