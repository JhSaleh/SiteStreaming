/**
 * @author: Jean-Hanna SALEH
 */

/**
 * Mise en place d'un système de vérification des champs
 */
window.addEventListener("load", function (){
    var blockedMdp = false; //Variable globale qui gère la validation/déblocage du bouton submit
    var blockedStrengthMdp = false;
    var blockedMail = false;

    var buttonSumbitInscription = document.getElementById("validateInscription");
    var cross = "✘";
    var tick = "✔";

    /**
     * Bloque le bouton d'envoie du formulaire
     */
    blockSubmitButton = function (){
        buttonSumbitInscription.disabled = true;
        buttonSumbitInscription.id = "validateInscriptionDisabled";
    }

    /**
     * Debloque le bouton d'envoie du formulaire
     */
    unblockSubmitButton = function(){
        buttonSumbitInscription.disabled = false;
        buttonSumbitInscription.id = "validateInscription";
    }

    //Les formats doivent également être vérifié côté serveur
    validateMdpFormat = function (mdp){
        var regExp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/; //Expression régulière de format de mdp
        /**
         * (?=.{8,20}) taille du mdp : min 8 characters, max 20
         * (?=.*[a-z]) Doit contenir au moins une lettre minuscule
         * (?=.*[A-Z]) Doit contenir au moins une lettre majuscule
         * (?=.*\d) Des chiffres
         * (?=.*[@$!%*?&]) Un caractère spéciale
         */
        return regExp.test(mdp);
    }

    inscriptionEventChecksMdp = function (){
        //les mdps
        var mdp = document.getElementById("password");
        var confirmMdp = document.getElementById("confirmPassword");

        //les divs des croix
        var croix1 = document.getElementById("checkMdp1");
        var croix2 = document.getElementById("checkMdp2");

        //Les messages de status
        var mdpStatus = document.getElementById("mdpStatus");
        var confirmMdpStatus = document.getElementById("confirmMdpStatus");
        var mdpStrengthStatus = document.getElementById("mdpStrengthStatus");

        /**
         * Remise à normal des messages de status
         */
        sameMdpStatusMsg = function (){
            mdpStatus.className = "statusMsgLayoutHidden";
            confirmMdpStatus.className = "statusMsgLayoutHidden";

            mdpStatus.textContent = "";
            confirmMdpStatus.textContent = "";
        }

        /**
         * Affiches les msg d'erreurs si les mdps sont différents
         */
        incorrectMdpStatusMsg = function (){
            mdpStatus.className = "statusMsgLayout";
            confirmMdpStatus.className = "statusMsgLayout";

            let errorMsg = "Mot de passe non identique."
            mdpStatus.textContent = errorMsg;
            confirmMdpStatus.textContent = errorMsg;
        }

        /**
         * Affiche le msg d'erreur si la force du mot de passe est faible
         */
        notStrongMdpMsg = function (){
            mdpStrengthStatus.className = "statusMsgLayout";

            let errorMsg = "Mot de passe de taille 8 ayant:\n-1 chiffre\n-1 minuscule\n-1 majuscule\n-un caratère spécial"
            mdpStrengthStatus.textContent = errorMsg;
        }

        /**
         * Cache le msg d'erreur si le mdp est fort
         */
        strongMdpMsg = function (){
            mdpStrengthStatus.className = "statusMsgHiddenLayout";
            mdpStrengthStatus.textContent = "";
        }

        /**
         * Vérifie que le mdp rentré est le même que celui de la confirmation
         * tout en cohabitant avec le msg pour le mail
         */
        mdpCheckIfSame = function (){
            if(mdp.value == confirmMdp.value){
                if(blockedStrengthMdp){
                    croix1.textContent = cross;
                    croix1.className = "wrongCross";
                } else {
                    croix1.textContent = tick;
                    croix1.className = "goodCross";
                }

                croix2.textContent = tick;
                croix2.className = "goodCross";
                if(!blockedMail && !blockedStrengthMdp){ //Empêche qu'un champs bloqué soit débloqué par un champ correcte
                    unblockSubmitButton();
                }



                blockedMdp = false;

                //Traitement du texte de status
                sameMdpStatusMsg();
            } else {
                croix1.textContent = cross;
                croix1.className = "wrongCross";

                croix2.textContent = cross;
                croix2.className = "wrongCross";

                blockSubmitButton();
                blockedMdp = true;

                //Traitement du texte de status
                incorrectMdpStatusMsg();
            }
        }

        /**
         * Vérifie si le mdp est fort, sans quoi affichage d'un msg d'erreur
         */
        mdpCheckIfStrong = function (){
            if(mdp.value.length > 0){
                if(validateMdpFormat(mdp.value)){
                    strongMdpMsg();
                    blockedStrengthMdp = false;
                    if(!blockedMdp && !blockedMail){
                        unblockSubmitButton();
                        croix1.textContent = tick;
                        croix1.className = "goodCross";
                    }
                } else {
                    notStrongMdpMsg();
                    blockSubmitButton();
                    blockedStrengthMdp = true;
                    croix1.textContent = cross;
                    croix1.className = "wrongCross";
                }
            } else {
                strongMdpMsg(); //Efface le msg
                blockedStrengthMdp = false;
                if(!blockedMdp && !blockedMail){
                    unblockSubmitButton();
                }
            }
        }

        mdp.addEventListener('input', function (){
            //Après chaque évenement maj variable pour voir évolution de la longueur du string
            mdp = document.getElementById("password");
            confirmMdp = document.getElementById("confirmPassword");
            //Pour les mots de passe, il faut utiliser le champs value pour voir les entrées
            if(mdp.value.length > 0 && confirmMdp.value.length > 0){ //vérifie que les champs sont non-vides
                mdpCheckIfSame();
            } else {
                croix1.textContent = "";
                croix2.textContent = "";

                if(!blockedMail && !blockedStrengthMdp){
                    unblockSubmitButton();
                }
                blockedMdp = false;

                //Traitement du texte de status
                sameMdpStatusMsg();
            }

            mdpCheckIfStrong();
        });

        confirmMdp.addEventListener('input', function (){
            mdp = document.getElementById("password");
            confirmMdp = document.getElementById("confirmPassword");
            if(mdp.value.length > 0 && confirmMdp.value.length > 0){ //vérifie que les champs sont non-vides
                mdpCheckIfSame();
            } else {
                croix1.textContent = "";
                croix2.textContent = "";
                if(!blockedMail && !blockedStrengthMdp){
                    unblockSubmitButton();
                }
                blockedMdp = false;

                //Traitement du texte de status
                sameMdpStatusMsg();
            }
        });
    }

    //Les formats doivent également être vérifié côté serveur
    validateEmailFormat = function(email){
        var regExp = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/; //Expression régulière de format d'email
        return regExp.test(email);
    }

    inscriptionEventChecksEmailFormat = function (){
        var email = document.getElementById("mail");
        var croix3 = document.getElementById('checkMail');

        var mailStatus = document.getElementById("mailStatus");

        /**
         * Remise à zéros du champs de status pour le mail
         */
        correctMailStatus = function (){
            mailStatus.className = "statusMsgLayoutHidden";
            mailStatus.textContent = "";
        }

        incorrectMailFormatStatus = function(){
            mailStatus.className = "statusMsgLayout";
            mailStatus.textContent = "Format de mail incorrect.";
        }

        email.addEventListener('input', function (){
            if(email.value.length > 0){
                if(validateEmailFormat(email.value)){
                    croix3.textContent = tick;
                    croix3.className = "goodCross";

                    if(!blockedMdp && !blockedStrengthMdp){
                        unblockSubmitButton();
                    }
                    blockedMail = false;

                    //Traitement de status
                    correctMailStatus();
                } else {
                    croix3.textContent = cross;
                    croix3.className = "wrongCross";

                    blockSubmitButton();
                    blockedMail = true;

                    //Traitement de status
                    incorrectMailFormatStatus();
                }
            } else {
                croix3.textContent = "";

                if(!blockedMdp && !blockedStrengthMdp){
                    unblockSubmitButton();
                }
                blockedMail = false;

                //Traitement de status
                correctMailStatus();
            }
        })
    }
})