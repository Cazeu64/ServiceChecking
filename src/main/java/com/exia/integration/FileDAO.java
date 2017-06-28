/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exia.integration;

import java.util.List;

/**
 *
 * @author Guillaume-PC
 */
public interface FileDAO 
{
    File add(File file);
    
    File delete(Long id);
    
    File find(Long id);
    
    List<File> getAllStoredFiles();
}
