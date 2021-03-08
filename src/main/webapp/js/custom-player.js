    createModal = function () {

        // Récupère le modal
        var modal = document.getElementById("lecture");


        // Recupère le bouton de fermeture du modal
        var span = document.getElementsByClassName("close")[0];

        // quand l'utilisateur appuie sur le bouton x, ferme le modal
        span.onclick = function () {
            modal.style.display = "none";
        }
    }