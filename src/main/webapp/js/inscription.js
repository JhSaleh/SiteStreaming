/**
 * @author: Jean-Hanna SALEH
 */

window.addEventListener("load", function (){
    var sameMdp = false;
    var buttonSumbitInscription = document.getElementById("validateInscription");
    inscriptionEventChecksMdp = function (){
        //les mdps
        var cross = "✘";
        var tick = "✔";
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
                    buttonSumbitInscription.disabled = false;
                    buttonSumbitInscription.id = "validateInscription";

                } else {
                    croix1.textContent = cross;
                    croix1.className = "wrongCross";

                    croix2.textContent = cross;
                    croix2.className = "wrongCross";

                    buttonSumbitInscription.disabled = true;
                    buttonSumbitInscription.id = "validateInscriptionDisabled";
                }
            } else {
                croix1.textContent = "";
                croix2.textContent = "";
                buttonSumbitInscription.disabled = false;
                buttonSumbitInscription.id = "validateInscription";
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
                    buttonSumbitInscription.disabled = false;
                    buttonSumbitInscription.id = "validateInscription";

                } else {
                    croix1.textContent = cross;
                    croix1.className = "wrongCross";

                    croix2.textContent = cross;
                    croix2.className = "wrongCross";

                    buttonSumbitInscription.disabled = true;
                    buttonSumbitInscription.id = "validateInscriptionDisabled";
                }
            } else {
                croix1.textContent = "";
                croix2.textContent = "";
                buttonSumbitInscription.disabled = false;
                buttonSumbitInscription.id = "validateInscription";
            }
        });
    }
})