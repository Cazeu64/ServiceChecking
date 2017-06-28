/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traitement.persistance.catalog;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Mimi
 */
@Entity
@Table(name = "results")
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    
    @Column(name="fileName")  
    private String file;
    
    @Column(name="words")
    private String echantillon;
    
    @Column(name="keyUsed")
    private String keyUsed;
    
    @Column(name="tauxEchantillonnage")
    private Float tauxE;     
    
    @Column(name="tauxConfiance")
    private Float tauxR;

    public Result() {
        
    }


    /******************/
    /* Geter / Setter */
    /******************/

    
    public Float getTauxE() {
        return tauxE;
    }

    public void setTauxE(Float tauxE) {
        this.tauxE = tauxE;
    }


    public Float getTauxR() {
        return tauxR;
    }

    public void setTauxR(Float tauxR) {
        this.tauxR = tauxR;
    }
    
    

    public Long getId() {
        return id;
    }
    
    
    public String getFile() {
        return file;
    }

    public String getEchantillon() {
        return echantillon;
    }

    public String getKeyUsed() {
        return keyUsed;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setEchantillon(String echantillon) {
        this.echantillon = echantillon;
    }

    public void setKeyUsed(String key) {
        this.keyUsed = key;
    }

    
     @Override
    public int hashCode() {
        
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
        
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Result other = (Result) obj;
        if (!Objects.equals(this.file, other.file)) {
            return false;
        }
        if (!Objects.equals(this.echantillon, other.echantillon)) {
            return false;
        }
        if (!Objects.equals(this.keyUsed, other.keyUsed)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Result{" + "id=" + id + ", file=" + file + ", words=" + echantillon + ", key=" + keyUsed + '}';
    }

    
    
}
