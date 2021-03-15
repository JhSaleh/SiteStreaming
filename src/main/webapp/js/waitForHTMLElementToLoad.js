/**
 * @author: Jean-Hanna SALEH
 */
window.addEventListener("load", function(){
    /**
     * Permet d'attendre un container d'un id donné avant l'application d'une fonction qui en dépend
     * @param elementId
     * @param callBack
     */
    waitForElement = function (elementId, callBack){
        window.setTimeout(function(){
            var element = document.getElementById(elementId);
            if(element){
                callBack(elementId, element);
            }else{
                waitForElement(elementId, callBack);
            }
        },500)
    }

    /**
     * Permet d'attendre 2 containers avec 2 ids différents avant l'application d'une fonction qui en dépend
     * @param elementId1
     * @param elementId2
     * @param callBack
     */
    waitForTwoElements = function (elementId1, elementId2, callBack){
        window.setTimeout(function(){
            var element1 = document.getElementById(elementId1);
            var element2 = document.getElementById(elementId2);

            if(element1 && element2){ //Si les deux éléments existent alors appel du callback
                callBack();
            }else{
                waitForTwoElement(elementId1, elementId2, callBack);
            }
        },500)
    }

    /**
     * Permet d'attendre autont de containers d'id unique différent avant l'application d'une fonction qui en dépend
     * @param listId
     * @param callBack
     */
    waitForManyElements = function (listId, callBack){
        window.setTimeout(function(){
            var elementList = [];
            var ready = true;
            for(var i = 0; i<listId.length; i++){
                elementList.push(document.getElementById(listId[i]));
            }

            //Vérification du chargement de chacun des containers
            let j = 0;
            while(j<elementList.length && ready){
                if(elementList[j]){
                    j++
                } else {
                    ready = false;
                }
            }

            if(ready){ //Si les deux éléments existent alors appel du callback
                callBack();
            }else{
                waitForManyElement(listId, callBack);
            }
        },500)
    }
})