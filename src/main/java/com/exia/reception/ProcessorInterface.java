/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exia.reception;

import com.exia.integration.File;
import java.util.List;

/**
 *
 * @author Guillaume-PC
 */
public interface ProcessorInterface
{
    public Boolean processChecking(List<File> files);
}
