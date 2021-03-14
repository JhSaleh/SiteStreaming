package com.siteStreaming.SiteStreaming.DataBase;

/**
 * Classe de traitement de string pour la bdd
 */
public class S {

    /**
     * Concatene de part et d'autre un mot par le symbole '
     * @param word
     * @return
     */
    public static String c(String word){
        return "'"+word+"'";
    }

    /**
     * Concatene de part et d'autre un mot par le symbole "
     * @param word
     * @return
     */
    public static String cd(String word){
        return "\""+word+"\"";
    }

    /**
     * Vérifie qu'un objet string n'est pas null, sans quoi il est remplacé par la chaine vide
     * @param string
     * @return
     */
    public static String checkNull(String string){
        if(string == null){
            return "";
        } else {
            return string;
        }
    }

    /**
     * Transforme un string en sa forme de valeur
     * @param string
     * @return
     */
    public static String value(String string){
        if (string == null){
            return "value=\"\"";
        } else {
            return "value="+"\'"+string+"\'";
        }
    }
}
