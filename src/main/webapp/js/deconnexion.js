/**
 * @author: Jean-Hanna SALEH
 */

window.addEventListener("load", function (){

    disconnect = function (){
        var logOutDiv = document.getElementById("LogOut");

        logOutDiv.onclick = function (){ //fonction qui réalise la deconnection
            deleteObject('client');
            console.log("Utilisateur deconnecté.");
            window.location = "Acceuil"; //recharge la page d'acceuil
        }
    }
})