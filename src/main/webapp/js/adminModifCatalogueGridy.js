
window.addEventListener("load", function () {

    acceuilCheckEventTypingMailConnection = function (){
        var CreerBtn = document.getElementById("ADM_creer");
        var SuppBtn = document.getElementById("ADM_supprimer");
        var ModBtn = document.getElementById("ADM_modifier");
        console.log("Recupération des elements du .jsp");

        statusMsgHidden = function (){
            statusMsg.className = "statusMsgLayoutHidden"
            statusMsg.textContent = "";
        }

        statusMsgNotHidden = function (){
            statusMsg.className = "statusMsgLayout"
            statusMsg.textContent = "Email ou mot de passe incorrect.";
        }

        hideElement = function (id){
            document.getElementById(id).hidden;
            console.log("élément d'id : "+id+" caché");
        }


        /**
         * Bind l'évènement de taper sur le champs au fait de cacher le msg de status
         */
        CreerBtn.addEventListener("click", function (){
            hideElement("ADM_supprimer");
        })
    }
})