/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.traitement.persistance.dll.catalogmngmt;



import com.exia.integration.File;
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
    
    //Liste chargée lors de l'instanciation
    private List<String> listWords;
    
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
    public Boolean checkFile(File file, float tauxC, float tauxE)
    {
        //Recupérer echant
        int count = 0;
        
        List<Word> echantillon = getEchantillonFromFile(file, tauxE);

        if(listWords == null){
            System.out.println("Chargement de la liste de mots");
            listWords = wordManager.getAllWords();
        }
        
        for(Word w :  echantillon){
           if(listWords.contains(w.getWord())){
               count++;
           }
           else
               System.out.println("Non trouvé batard de ta soeur : " + w.getWord());
        }
        
        System.out.println("Nombre de mot trouvés : "+count);
        //ratio de mots trouvés en fr sur le nombre de mots dans la liste
        Float ratio = Float.valueOf(count) / Float.valueOf(echantillon.size()) ;
        System.out.println("Ratio mot trouvé / nbr de mots dans echantillon : "+ratio.toString());
        float tauxReel = ratio * tauxC;
        System.out.println("Taux de confiance reel :"+ tauxReel);

        if(tauxReel >= tauxC)
        {
            /* CREATE RESULT */
            Result result = new Result();
            result.setFile(file.getName());
            result.setEchantillon(convertToString(echantillon));
            result.setKeyUsed(file.getKey());
            result.setTauxE(tauxE);
            result.setTauxR(tauxReel);

            resultManager.saveResult(result);
            
            //Appel du webservice C#
            //Retour : Mail, name, key, rapport
            List<Word> listEmail = findEmails(file.getContent());
            
            //sendToCCharp(file.getName(), file.getKey(), file.getContent(), tauxReel, listEmail);
            return true;
        }
        
        return false;
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

        System.out.println("getContent : "+ file.getContent());
        
        List<Word> list = fillListFromString(file.getContent());
        
        System.out.println("Taille liste mot : "+ list.size());
       
        Random randomGen = new Random();
        List<Word> e = new ArrayList<>();
        int count = (int)(tauxE*list.size());
        
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
            System.out.println(w.getWord());
        }
        return wordsList;
    }
    
    @Override
    public List<Word>findEmails(String words){
        List<Word> ws = Word.fillListFromString(words);
        List<Word> result = new ArrayList<>();
        String mail ="";
        for(Word wd : ws){
            mail = isMail(wd);
            if(!mail.equals("")){
                Word m = new Word();
                m.setWord(mail);
                result.add(m);
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
