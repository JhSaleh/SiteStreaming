package com.siteStreaming.SiteStreaming.Acceuil;

import com.siteStreaming.SiteStreaming.DataBase.S;

/**
 *
 */
public class MetaErrorHandler {
    private String defaultStringAnswer = "\"\""; //Correspond à la valeur par default des champs input
    private String emailUsed;
    private String passwordUsed;

    public MetaErrorHandler(String inEmailUsed, String inPasswordUsed){
        this.emailUsed = inEmailUsed;
        this.passwordUsed = inPasswordUsed;
    }

    /**
     * Renvoit l'email
     * @return
     */
    public String getEmailUsed() {
        if(this.emailUsed != null){
            return S.cd(this.emailUsed);
        }
        return defaultStringAnswer;
    }

    /**
     * Renvoit le mot de passe
     * @return
     */
    public String getPasswordUsed() {
        if(this.passwordUsed != null){
            return S.cd(passwordUsed);
        }
        return defaultStringAnswer;
    }
}
