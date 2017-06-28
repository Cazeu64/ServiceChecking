/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.traitement.persistance.dll.catalogmngmt;


import com.exia.integration.File;
import com.traitement.persistance.catalog.Result;
import com.traitement.persistance.catalog.Word;
import java.util.List;
import javax.ejb.Local;


/**
 *
 * @author asbriglio
 */
@Local
public interface CatalogManagerService {

    public Result createResult(String file, String echantillon, String key, Float tauxE, Float tauxC);

    public Boolean checkFile(File file, float tauxC, float tauxE);
    
    public List<String> getAllWords();
    
    public List<Word>findEmails(String words);
}
