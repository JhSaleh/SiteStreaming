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
}
