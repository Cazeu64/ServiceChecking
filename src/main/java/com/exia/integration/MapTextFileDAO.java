/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exia.integration;

import com.exia.util.TextFile;
import com.exia.util.ValidationStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author Guillaume-PC
 */

@ApplicationScoped
public class MapTextFileDAO implements TextFileDAO
{
    private AtomicLong count = new AtomicLong(1);
    private Map<Long, TextFile> textFiles = new ConcurrentHashMap<>();
    
    @Override
    public TextFile add(TextFile textfile) 
    {
        textfile.setId(count.getAndIncrement());
        textfile.setStatus(ValidationStatus.VALIDE);

        textFiles.put(textfile.getId(), textfile);
        return textfile;
    }

    @Override
    public TextFile delete(Long id) 
    {
        TextFile deletedTextFile = textFiles.remove(id);

        if(deletedTextFile == null)
        {
            return null;
        }
        deletedTextFile.setStatus(ValidationStatus.INVALIDE);
        return deletedTextFile; 
    }

    @Override
    public TextFile find(Long id) 
    {
        return textFiles.get(id); 

    }

    @Override
    public List<TextFile> getAllStoredTextFiles()
    {
        List<TextFile> textFileList = new ArrayList<>(textFiles.values());

        for(TextFile p : textFileList)
        {
            System.out.println(p);
        }
        return textFileList;
    }
    
}
