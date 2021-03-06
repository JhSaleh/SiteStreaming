window.addEventListener("load", function (){
    var blockedMdpA partir de sa page de profil, il peut modifier ses informations personnelles.
        Optionnel : Il peut également gérer ses playlists : créer/supprimer/renommer
    une playlist. = false; //Variable globale qui gère la validation/déblocage du bouton submit
    var blockedMailUser = false;

    var buttonSumbitProfil = document.getElementById("validateProfil");
    var cross = "✘";
    var tick = "✔";

    profilEventChecksMdp = function (){
        //les mdps
        var mdpUser = document.getElementById("passwordUser");
        var confirmMdpUser = document.getElementById("confirmPasswordUser");

        //les divs des croix
        var croix1 = document.getElementById("checkMdp1User");
        var croix2 = document.getElementById("checkMdp2User");

        mdpUser.addEventListener('input', function (){
            //Après chaque évenement maj variable pour voir évolution de la longueur du string
            mdpUser = document.getElementById("passwordUser");
            confirmMdpUser = document.getElementById("confirmPasswordUser");
            //Pour les mots de passe, il faut utiliser le champs value pour voir les entrées
            if(mdpUser.value.length > 0 && confirmMdpUser.value.length > 0){ //vérifie que les champs sont non-vides
                if(mdpUser.value == confirmMdpUser.value){
                    croix1.textContent = tick;
                    croix1.className = "goodCross";

                    croix2.textContent = tick;
                    croix2.className = "goodCross";

                    if(!blockedMail){ //Empêche qu'un champs bloqué soit débloqué par un champ correcte
                        buttonSumbitInscription.disabled = false;
                        buttonSumbitInscription.id = "validateProfil";
                    }
                    blockedMdpUser = false;
                } else {
                    croix1.textContent = cross;
                    croix1.className = "wrongCross";

                    croix2.textContent = cross;
                    croix2.className = "wrongCross";

                    buttonSumbitInscription.disabled = true;
                    buttonSumbitInscription.id = "validateProfilDisabled";

                    blockedMdpUser = true;
                }
            } else {
                croix1.textContent = "";
                croix2.textContent = "";

                if(!blockedMail){
                    buttonSumbitInscription.disabled = false;
                    buttonSumbitInscription.id = "validateProfil";
                }
                blockedMdpUser = false;
            }
        });

        confirmMdp.addEventListener('input', function (){
            mdpUser = document.getElementById("passwordUser");
            confirmMdpUser = document.getElementById("confirmPasswordUser");
            if(mdpUser.value.length > 0 && confirmMdpUser.value.length > 0){ //vérifie que les champs sont non-vides
                if(mdpUser.value == confirmMdpUser.value){
                    croix1.textContent = tick;
                    croix1.className = "goodCross";

                    croix2.textContent = tick;
                    croix2.className = "goodCross";

                    if(!blockedMail){
                        buttonSumbitProfil.disabled = false;
                        buttonSumbitProfil.id = "validateProfil";
                    }
                    blockedMdpUser = false;
                } else {
                    croix1.textContent = cross;
                    croix1.className = "wrongCross";

                    croix2.textContent = cross;
                    croix2.className = "wrongCross";

                    buttonSumbitInscription.disabled = true;
                    buttonSumbitInscription.id = "validateProfilDisabled";

                    blockedMdpUser = true;
                }
            } else {
                croix1.textContent = "";
                croix2.textContent = "";
                if(!blockedMail){
                    buttonSumbitInscription.disabled = false;
                    buttonSumbitInscription.id = "validateProfil";
                }
                blockedMdpUser = false;
            }
        });
    }

    validateEmailFormatUser = function(emailUser){
        var regExp = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/; //Expression régulière de format d'email
        return regExp.test(emailUser);
    }

    profilEventChecksEmailFormat = function (){
        var emailUser = document.getElementById("mailUser");
        var croix3 = document.getElementById('checkMailUser');

        emailUser.addEventListener('input', function (){
            if(emailUser.value.length > 0){
                if(validateEmailFormat(emailUser.value)){
                    croix3.textContent = tick;
                    croix3.className = "goodCross";

                    if(!blockedMdpUser){
                        buttonSumbitProfil.disabled = false;
                        buttonSumbitProfil.id = "validateProfil";
                    }
                    blockedMailUser = false;
                } else {
                    croix3.textContent = cross;
                    croix3.className = "wrongCross";

                    buttonSumbitProfil.disabled = true;
                    buttonSumbitProfil.id = "validateProfilDisabled";

                    blockedMailUser = true;
                }
            } else {
                croix3.textContent = "";

                if(!blockedMdpUser){
                    buttonSumbitProfil.disabled = false;
                    buttonSumbitProfil.id = "validateProfil";
                }
                blockedMailUser = false;
            }
        })
    }
})