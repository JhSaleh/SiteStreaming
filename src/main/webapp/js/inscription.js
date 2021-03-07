/**
 * @author: Jean-Hanna SALEH
 */

/**
 * Mise en place d'un système de vérification des champs
 */
window.addEventListener("load", function (){
    //Variable globale qui gère la validation/déblocage du bouton submit
    var blockedMdp = false; //Indique si le bouton submit est bloqué à cause de mdp non identique
    var blockedStrengthMdp = false; //Indique si le bouton submit est bloqué à cause d'un mdp faible
    var blockedMail = false; //Indique si le bouton submit est bloqué à cause d'un mauvais format de mail
    var blockedBirthDate = false; //Indique si le bouton submit est bloqué à cause d'une mauvaise date de naissance

    var buttonSumbitInscription = document.getElementById("validateInscription");
    var cross = "✘";
    var tick = "✔";

    var ageLimit = 13; //Site interdit au moins de 13 ans

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

    //Les formats doivent également être vérifiés côté serveur
    validateMdpFormat = function (mdp){
        var regExp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{8,30}$/; //Expression régulière de format de mdp
        /**
         * (?=.{8,30}) taille du mdp : min 8 characters, max 20
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

            let errorMsg = "Mot de passe de taille 8 ayant:\n-1 chiffre\n-1 minuscule\n-1 majuscule"
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
                if(!blockedMail && !blockedStrengthMdp && !blockedBirthDate){ //Empêche qu'un champs bloqué soit débloqué par un champ correcte
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
                    if(!blockedMdp && !blockedMail && !blockedBirthDate){
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
                if(!blockedMdp && !blockedMail && !blockedBirthDate){
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

                if(!blockedMail && !blockedStrengthMdp && !blockedBirthDate){
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
                if(!blockedMail && !blockedStrengthMdp && !blockedBirthDate){
                    unblockSubmitButton();
                }
                blockedMdp = false;

                //Traitement du texte de status
                sameMdpStatusMsg();
            }
        });
    }

    //Les formats doivent également être vérifié côté serveur
    /**
     * Pour un email donné, vérifie qu'il respecte le bon format
     * @param email
     * @returns {boolean}
     */
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

        /**
         * Affiche le msg de mail au format incorrect
         */
        incorrectMailFormatStatus = function(){
            mailStatus.className = "statusMsgLayout";
            mailStatus.textContent = "Format de mail incorrect.";
        }

        /**
         * Efface le msg de status de mail déjà utilisé
         */
        erraseMailAlreadyTakenStatus = function (){
            var mailAlreadyTaken = document.getElementById("mailAlreadyTaken");
            if(mailAlreadyTaken != undefined){ //Si le div existe
                mailAlreadyTaken.className = "statusMsgLayoutHidden";
                mailAlreadyTaken.textContent = "";
            }
        }

        email.addEventListener('input', function (){
            erraseMailAlreadyTakenStatus(); //Dès qu'une touche est préssé, le msg est effacé
            if(email.value.length > 0){
                if(validateEmailFormat(email.value)){
                    croix3.textContent = tick;
                    croix3.className = "goodCross";

                    if(!blockedMdp && !blockedStrengthMdp && !blockedBirthDate){
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

                if(!blockedMdp && !blockedStrengthMdp && !blockedBirthDate){
                    unblockSubmitButton();
                }
                blockedMail = false;

                //Traitement de status
                correctMailStatus();
            }
        })
    }


    /**
     * Calcule l'age d'une personne selon sa date de naissance
     * @param dateString
     * @returns {number}
     */
    ageCalculator = function (dateString){
        var birthday = +new Date(dateString);
        let lengthYear = 24*3600*365.25*1000
        return ~~((Date.now() - birthday) / (lengthYear)); //~~ correspond à la fonction floor()
    }


    inscriptionEventChecksBirthDate = function (){
        var birthDate = document.getElementById("dateNaissance");
        var birthDateStatus = document.getElementById("ageStatus");

        /**
         * Affiche le msg d'erreur si la date selectionné est incorrecte
         */
        incorrectBirthDateStatusMsg = function (){
            birthDateStatus.className = "statusMsgLayout";
            birthDateStatus.textContent = "Inscription interdite au moins de 13 ans.";
        }

        /**
         * Supprime le msg d'erreur lié au choix de la date
         */
        correctBirthDateStatusMsg = function (){
            birthDateStatus.className = "statusMsgLayoutHidden";
            birthDateStatus.textContent = "";
        }

        /**
         * Bind l'évènement de changement de date avec la vérification de l'age
         */
        birthDate.addEventListener('input', function (){
            if(birthDate.value.length > 0){ //S'assure que le champs est remplie
                if(ageCalculator(birthDate.value) < ageLimit){
                    blockSubmitButton();
                    incorrectBirthDateStatusMsg();
                    blockedBirthDate = true;
                } else {
                    if(!blockedMdp && !blockedStrengthMdp && !blockedMail){
                        unblockSubmitButton();
                    }
                    correctBirthDateStatusMsg();
                    blockedBirthDate = false;
                }
            } else {
                if(!blockedMdp && !blockedStrengthMdp && !blockedMail){
                    unblockSubmitButton();
                }
                correctBirthDateStatusMsg();
                blockedBirthDate = false;
            }
        })
    }
})