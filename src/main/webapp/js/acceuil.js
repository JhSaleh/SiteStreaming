/**
 *@author: Jean-Hanna SALEH
 */

window.addEventListener("load", function () {

    acceuilCheckEventTypingMailConnection = function (){
          var mailField = document.getElementById("mailAddress");
          var statusMsg = document.getElementById("statusMsg");

        statusMsgHidden = function (){
            statusMsg.className = "statusMsgLayoutHidden"
            statusMsg.textContent = "";
        }

        statusMsgNotHidden = function (){
            statusMsg.className = "statusMsgLayout"
            statusMsg.textContent = "Email ou mot de passe incorrect.";
        }

        /**
         * Bind l'évènement de taper sur le champs au fait de cacher le msg de status
         */
        mailField.addEventListener("input", function (){
            statusMsgHidden();
        })
    }
})