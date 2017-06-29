/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.traitement.persistance.dll.catalogmngmt;

import com.exia.integration.File;
import com.exia.validation.ArrayOfstring;
import com.exia.validation.IValidations;
import com.exia.validation.Validations;
import com.traitement.persistance.catalog.Result;
import com.traitement.persistance.catalog.Word;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.xml.ws.WebServiceRef;

/**
 *
 * service facade (application service).<br>
 * Fait le pont entre la présentation et la logique métier<br>
 * Composant coarse grained (forte granularité) chargé de la réalisation des processus métiers<br>
 * Expose une vue locale au travers d'une interface métier annotée @Local
 */
@Stateless(name="CatalogManager")
//options par défaut - on peut se passer de cette anotation - convention over configuration
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)//on démarrage une nouvelle transaction si le client n'en n'a pas démarrée.
@LocalBean //obligatoire si on veut que le SB expose d'autres vues (locales ou "remote")
public class CatalogManagerServiceBean implements CatalogManagerService {
     //on aurait pu utiliser @Inject vu qu'on injecte des composants co-localisés dans la même instance de JVM (car dans la même archive)

    @EJB
    private WordManagerServiceBean wordManager;

    @EJB
    private ResultManagerServiceBean resultManager;
    
    @WebServiceRef(Validations.class)
    private IValidations validation;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    
    @Override
    public List<String> getAllWords() {
       List<String> results = wordManager.getAllWords();
       results.size();
       return results;
    }
    
    @Override
    public Result createResult(String file, String echantillon, String key, Float tauxE, Float tauxR) {
        Result res = new Result();
        res.setFile(file);
        res.setKeyUsed(key);
        res.setEchantillon(echantillon);
        res.setTauxE(tauxE);
        res.setTauxR(tauxR);
        
        res = resultManager.saveResult(res); 
        
        return res;
    }

    @Override
    public Boolean checkFile(List<File> files, float tauxC, float tauxE)
    {
        boolean isValidKey = false;
        float sumTauxReel = 0;
        List<String> listEmail = new ArrayList<String>();
        
        if(ListeWords.getInstance().getListeWords().size() <= 1)
            ListeWords.getInstance().setListeWords(wordManager.getAllWords());

        for(File file : files)
        {
            //Recupérer echant
            int count = 0;

            List<Word> echantillon = getEchantillonFromFile(file, tauxE);

            for(Word w :  echantillon){
               if(ListeWords.getInstance().getListeWords().contains(w.getWord())){
                   count++;
               }
            }
            
            Float ratio = Float.valueOf(count) / Float.valueOf(echantillon.size()) ;
            float tauxReel = ratio;//* tauxC;
            
            if(tauxReel > sumTauxReel)
                sumTauxReel = tauxReel;

            //Enregistrement des resultats en base
            if(tauxReel >= tauxC)
            {
                isValidKey = true;
                
                listEmail.addAll(findEmails(file.getContent()));
                
                for(String e : listEmail)
                {
                    System.out.println("Email : " + e);
                }
                
                /* CREATE RESULT */
                Result result = new Result();
                result.setFile(file.getName());
                result.setEchantillon(convertToString(echantillon));
                result.setKeyUsed(file.getKey());
                result.setTauxE(tauxE);
                result.setTauxR(tauxReel);

                resultManager.saveResult(result); 
            }
        }
        
        float moyTauxReel = sumTauxReel / files.size();

        System.out.println("Taux de confiance : " + moyTauxReel);
        
        //Pour DEBUG
        //sendToCCharp(files, moyTauxReel, listEmail);
        
        if(isValidKey)
        {
            System.out.println("Fichier trouvé : Clef = " + files.get(0).getKey());
            
            //Appel du webservice C#
            sendToCCharp(files, moyTauxReel, listEmail);
            return true;
        }
        
        return false;
    }
    
    @Override
    public void sendToCCharp(List<File> files, float tauxR, List<String> listMail)
    {
        ArrayOfstring listName = new ArrayOfstring();
        ArrayOfstring listContent = new ArrayOfstring();
        ArrayOfstring listMailAoS = new ArrayOfstring();
        
        listName.getString().addAll(getNameArrayString(files));
        listContent.getString().addAll(getContentArrayString(files));
        listMailAoS.getString().addAll(listMail);
        
        validation.responseVal(listName, 
                files.get(0).getKey(), 
                listContent, 
                tauxR, 
                listMailAoS, 
                files.get(0).getTokenUser());
        
        //validation.responseVal(new ArrayOfstring(getNameArrayString(files)), files.get(0).getKey(), new ArrayOfstring(getContentArrayString(files)), tauxR, new ArrayOfstring((String[])listMail.toArray()), files.get(0).getTokenUser());

        return;
    }
 
    public List<String> getNameArrayString(List<File> files)
    {
        List<String> nameArray = new ArrayList<String>();
        
        for(File f : files)
        {
            nameArray.add(f.getName());
        }
  
        return nameArray;
    }
    
    public List<String> getContentArrayString(List<File> files)
    {
        List<String> nameArray = new ArrayList<String>();
        
        for(File f : files)
        {
            nameArray.add(f.getContent());
        }
  
        return nameArray;
    }
    
    public String convertToString(List<Word> l)
    {
        String list = "";
        
        for(Word w : l)
        {
            list += w.getWord() + ",";      
        }
        
        return list;
    }
    
    public List<Word> getEchantillonFromFile(File file, float tauxE){
        
        List<Word> list = fillListFromString(file.getContent());
       
        Random randomGen = new Random();
        List<Word> e = new ArrayList<>();
        int count = (int)(tauxE*list.size()) + 1;
        
        for( int i = 0 ; i < count ; i++){
            int index = randomGen.nextInt(list.size());
            e.add(list.get(index));
            //on supprime pour ne pas tirer plusieur fois le même mot dans l'échantiller
            list.remove(index);
        }

        return e;
    }
    
    public List<Word> fillListFromString(String words){
        
       // words = words.trim();
        //words = words.replaceAll(" ", "");
        String[] list = words.split(" ");
        
        List<Word> wordsList = new ArrayList<>(); 
        for (String l : list) {
            Word w = new Word();
            w.setWord(l);
            wordsList.add(w);
        }
        return wordsList;
    }
    
    @Override
    public List<String> findEmails(String words){
        List<Word> ws = Word.fillListFromString(words);
        List<String> result = new ArrayList<>();
        String mail ="";
        for(Word wd : ws){
            mail = isMail(wd);
            if(!mail.equals("")){
                result.add(mail);
            }
        }
        return result;
    }
    
    public static boolean validate(String emailStr) {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
            return matcher.find();
    }
    
    public String isMail(Word w){
        String mail="";
        if(validate(w.getWord()))
            mail = w.getWord();

        return mail;
    }
    

}//fin classe
