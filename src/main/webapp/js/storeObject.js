/**
 * @author: Jean-Hanna SALEH
 * Il s'agit d'un module de stockage de données.
 */

/*
On pourrait utiliser des cookies pour stocker les données utilisateurs mais :
-c'est très limité en terme d'espace
-c'est moins sécurisé
-endigue moins la performance du site
 */

/*
De plus, en localStorage ne peut stocker que des strings
Il est donc nécessaire de les stringifier pour récupérer tous les champs correctement
*/
window.addEventListener('load', function (){
    /**
     * Sauvegarde un objet javascript
     * @param stringNameObject
     * @param object
     */
    saveObject = function (stringNameObject, object){ //Couple Clé/Objet
        localStorage.setItem(stringNameObject, JSON.stringify(object));
    }

    /**
     * Recupère un objet javascript
     * @param stringNameObject
     * @returns {any}
     */
    getObject = function(stringNameObject){
        return JSON.parse(localStorage.getItem(stringNameObject));
    }

    /**
     * Supprime un objet javascript
     * @param stringNameObject
     */
    deleteObject = function (stringNameObject){
        localStorage.removeItem(stringNameObject);
    }

})


