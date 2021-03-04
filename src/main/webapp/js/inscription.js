/**
 * @author: Jean-Hanna SALEH
 */

/**
 * Mise en place d'un système de vérification des champs
 */
window.addEventListener("load", function (){
    var blockedMdp = false; //Variable globale qui gère la validation/déblocage du bouton submit
    var blockedMail = false;

    var buttonSumbitInscription = document.getElementById("validateInscription");
    var cross = "✘";
    var tick = "✔";

    inscriptionEventChecksMdp = function (){
        //les mdps
        var mdp = document.getElementById("password");
        var confirmMdp = document.getElementById("confirmPassword");

        //les divs des croix
        var croix1 = document.getElementById("checkMdp1");
        var croix2 = document.getElementById("checkMdp2");

        mdp.addEventListener('input', function (){
            //Après chaque évenement maj variable pour voir évolution de la longueur du string
            mdp = document.getElementById("password");
            confirmMdp = document.getElementById("confirmPassword");
            //Pour les mots de passe, il faut utiliser le champs value pour voir les entrées
            if(mdp.value.length > 0 && confirmMdp.value.length > 0){ //vérifie que les champs sont non-vides
                if(mdp.value == confirmMdp.value){
                    croix1.textContent = tick;
                    croix1.className = "goodCross";

                    croix2.textContent = tick;
                    croix2.className = "goodCross";

                    if(!blockedMail){ //Empêche qu'un champs bloqué soit débloqué par un champ correcte
                        buttonSumbitInscription.disabled = false;
                        buttonSumbitInscription.id = "validateInscription";
                    }
                    blockedMdp = false;
                } else {
                    croix1.textContent = cross;
                    croix1.className = "wrongCross";

                    croix2.textContent = cross;
                    croix2.className = "wrongCross";

                    buttonSumbitInscription.disabled = true;
                    buttonSumbitInscription.id = "validateInscriptionDisabled";

                    blockedMdp = true;
                }
            } else {
                croix1.textContent = "";
                croix2.textContent = "";

                if(!blockedMail){
                    buttonSumbitInscription.disabled = false;
                    buttonSumbitInscription.id = "validateInscription";
                }
                blockedMdp = false;
            }
        });

        confirmMdp.addEventListener('input', function (){
            mdp = document.getElementById("password");
            confirmMdp = document.getElementById("confirmPassword");
            if(mdp.value.length > 0 && confirmMdp.value.length > 0){ //vérifie que les champs sont non-vides
                if(mdp.value == confirmMdp.value){
                    croix1.textContent = tick;
                    croix1.className = "goodCross";

                    croix2.textContent = tick;
                    croix2.className = "goodCross";

                    if(!blockedMail){
                        buttonSumbitInscription.disabled = false;
                        buttonSumbitInscription.id = "validateInscription";
                    }
                    blockedMdp = false;
                } else {
                    croix1.textContent = cross;
                    croix1.className = "wrongCross";

                    croix2.textContent = cross;
                    croix2.className = "wrongCross";

                    buttonSumbitInscription.disabled = true;
                    buttonSumbitInscription.id = "validateInscriptionDisabled";

                    blockedMdp = true;
                }
            } else {
                croix1.textContent = "";
                croix2.textContent = "";
                if(!blockedMail){
                    buttonSumbitInscription.disabled = false;
                    buttonSumbitInscription.id = "validateInscription";
                }
                blockedMdp = false;
            }
        });
    }

    validateEmailFormat = function(email){
        var regExp = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/; //Expression régulière de format d'email
        return regExp.test(email);
    }

    inscriptionEventChecksEmailFormat = function (){
        var email = document.getElementById("mail");
        var croix3 = document.getElementById('checkMail');

        email.addEventListener('input', function (){
            if(email.value.length > 0){
                if(validateEmailFormat(email.value)){
                    croix3.textContent = tick;
                    croix3.className = "goodCross";

                    if(!blockedMdp){
                        buttonSumbitInscription.disabled = false;
                        buttonSumbitInscription.id = "validateInscription";
                    }
                    blockedMail = false;
                } else {
                    croix3.textContent = cross;
                    croix3.className = "wrongCross";

                    buttonSumbitInscription.disabled = true;
                    buttonSumbitInscription.id = "validateInscriptionDisabled";

                    blockedMail = true;
                }
            } else {
                croix3.textContent = "";

                if(!blockedMdp){
                    buttonSumbitInscription.disabled = false;
                    buttonSumbitInscription.id = "validateInscription";
                }
                blockedMail = false;
            }
        })
    }
})