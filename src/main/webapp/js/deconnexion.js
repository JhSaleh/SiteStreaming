/**
 * @author: Jean-Hanna SALEH
 */

window.addEventListener("load", function (){

    disconnect = function (){
        var logOutDiv = document.getElementById("LogOut");

        logOutDiv.onclick = function () { //fonction qui réalise la deconnection
            /*
            //Ancien systeme
            deleteObject('client');
            console.log("Utilisateur deconnecté.");
            window.location = "Accueil"; //recharge la page d'acceuil
            */

            //Ne marche pas dans l'état avec la notion de session
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function (){
                if(xhr.readyState == 4){
                    var data = xhr.responseText;
                }
            }
            xhr.open('GET', '${pageContext.request.contextPath}/LogOut', true);
            xhr.send(null);
        }

    }
})