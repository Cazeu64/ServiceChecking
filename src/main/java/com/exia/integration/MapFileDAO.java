/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exia.integration;

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
public class MapFileDAO implements FileDAO
{
    private AtomicLong count = new AtomicLong(1);
    private Map<Long, File> files = new ConcurrentHashMap<>();
    
    @Override
    public File add(File file) 
    {
        file.setId(count.getAndIncrement());

        files.put(file.getId(), file);
        return file;
    }

    @Override
    public File delete(Long id) 
    {
        File deletedFile = files.remove(id);

        if(deletedFile == null)
        {
            return null;
        }
        
        return deletedFile; 
    }

    @Override
    public File find(Long id) 
    {
        return files.get(id); 

    }

    @Override
    public List<File> getAllStoredFiles()
    {
        List<File> fileList = new ArrayList<>(files.values());
        
        return fileList;
    }    
}
