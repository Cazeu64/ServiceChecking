/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exia.integration;

import com.exia.util.TextFile;
import java.util.List;

/**
 *
 * @author Guillaume-PC
 */
public interface TextFileDAO 
{
    TextFile add(TextFile textfile);
    
    TextFile delete(Long id);
    
    TextFile find(Long id);
    
    List<TextFile> getAllStoredTextFiles();
}
