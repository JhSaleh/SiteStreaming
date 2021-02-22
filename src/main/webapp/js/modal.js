/**
 * @author: Jean-Hanna SALEH
 */

window.addEventListener("load", function () {
    createModal = function () {

        // Récupère le modal
        var modal = document.getElementById("connexion");

        // Récupère le bouton ouvrant le modal
        var btn = document.getElementById("SignIn");

        // Recupère le bouton de fermeture du modal
        var span = document.getElementsByClassName("close")[0];

        // Bind le bouton SignIn au modal
        btn.onclick = function () {
            modal.style.display = "block";
        }

        // quand l'utilisateur appuie sur le bouton x, ferme le modal
        span.onclick = function () {
            modal.style.display = "none";
        }

        /*Enlever car mène à des problèmes
        // Si l'utilisateur clique en dehors du modal, le modal se ferme
        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }
        */
        }
    })