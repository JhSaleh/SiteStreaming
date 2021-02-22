/**
 * @author: Jean-Hanna SALEH
 * Il s'agit d'un script qui construit dynamiquement la barre de navigation dépendant de la présence d'un utilisateur connecté ou pas
 */

/* Ancien bloc
<div id = "gridyHeaderBeforeSignIn">
    <div id ="title"><a href="${pageContext.request.contextPath}/Acceuil">UsTube</a></div>
    <div id="nomPrenomUser"></div> <!--A laisse vide, sinon null pointer exception-->
    <div id="SignIn" class="buttonLayout changeButtonColor">Se connecter</div>
    <a href="${pageContext.request.contextPath}/Acceuil/Inscription"><div id="SignUp" class="buttonLayout changeButtonColor">S'inscrire</div></a>
</div>
* */

window.addEventListener("load", function (){
    logedOut = function (){
        var mainDiv = document.getElementById("gridyHeader");

        var div = document.createElement('div');
        div.id = "gridyHeaderBeforeSignIn";

        //Titre
        var titleDiv = document.createElement('div');
        titleDiv.id = "title";

        var linkTitleDiv = document.createElement('a');
        linkTitleDiv.href = "Acceuil";
        linkTitleDiv.textContent = "UsTube";

        //Bouton sign in
        var SignInDiv = document.createElement('div');
        SignInDiv.id = "SignIn";
        SignInDiv.className = "buttonLayout changeButtonColor";
        SignInDiv.textContent = "Se connecter";

        //Bouton sign up
        var SignUpDiv = document.createElement('div');
        SignUpDiv.id = "SignUp";
        SignUpDiv.className = "buttonLayout changeButtonColor";
        SignUpDiv.textContent = "S'inscrire";

        var SignUpLink = document.createElement('a');
        SignUpLink.href = "Acceuil/Inscription";

        //Assemblage
        SignUpLink.appendChild(SignUpDiv);
        titleDiv.appendChild(linkTitleDiv);

        div.appendChild(titleDiv);
        div.appendChild(SignInDiv);
        div.appendChild(SignUpLink);
        mainDiv.appendChild(div);

    }

    logedIn = function (userFirstName, userLastName){
        var mainDiv = document.getElementById("gridyHeader");

        var div = document.createElement('div');
        div.id = "gridyHeaderAfterSignIn"; //Changement de l'organisation de l'affichage

        //Titre
        var titleDiv = document.createElement('div');
        titleDiv.id = "title";

        var linkTitleDiv = document.createElement('a');
        linkTitleDiv.href = "Acceuil";
        linkTitleDiv.textContent = "UsTube";

        var nomPrenomUserDiv = document.createElement('div');
        nomPrenomUserDiv.id = "nomPrenomUser";
        nomPrenomUserDiv.textContent = userFirstName+" "+userLastName;

        var deconnectionDiv = document.createElement('div');
        deconnectionDiv.id = "LogOut";
        deconnectionDiv.className = "buttonLayout changeButtonColor";
        deconnectionDiv.textContent = "Se déconnecter";

        //Assemblage
        //Titre
        var titleDiv = document.createElement('div');
        titleDiv.id = "title";

        var linkTitleDiv = document.createElement('a');
        linkTitleDiv.href = "Acceuil";
        linkTitleDiv.textContent = "UsTube";


        titleDiv.appendChild(linkTitleDiv);
        div.appendChild(titleDiv);
        div.appendChild(nomPrenomUserDiv);
        div.appendChild(deconnectionDiv);
        mainDiv.appendChild(div);
    }
})