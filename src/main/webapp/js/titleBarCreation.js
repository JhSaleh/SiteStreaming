/**
 * @author: Jean-Hanna SALEH
 * Il s'agit d'un script qui construit dynamiquement la barre de navigation dépendant de la présence d'un utilisateur connecté ou pas
 */

window.addEventListener("load", function (){
    loggedOut = function (){
        var mainDiv = document.getElementById("gridyHeader");

        var div = document.createElement('div');
        div.id = "gridyHeaderBeforeSignIn";

        //Titre
        var titleDiv = document.createElement('div');
        titleDiv.id = "title";

        var linkTitleDiv = document.createElement('a');
        linkTitleDiv.href = "./Accueil";
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
        SignUpLink.href = "./Accueil/Inscription";

        //Assemblage
        SignUpLink.appendChild(SignUpDiv);
        titleDiv.appendChild(linkTitleDiv);

        div.appendChild(titleDiv);
        div.appendChild(SignInDiv);
        div.appendChild(SignUpLink);
        mainDiv.appendChild(div);

    }

    loggedIn = function (userFirstName, userLastName){
        var mainDiv = document.getElementById("gridyHeader");

        var div = document.createElement('div');
        div.id = "gridyHeaderAfterSignIn"; //Changement de l'organisation de l'affichage

        //Titre
        var titleDiv = document.createElement('div');
        titleDiv.id = "title";

        var linkTitleDiv = document.createElement('a');
        linkTitleDiv.href = "./Accueil";
        linkTitleDiv.textContent = "UsTube";

        var nomPrenomUserDiv = document.createElement('div');
        nomPrenomUserDiv.id = "nomPrenomUser";
        nomPrenomUserDiv.className = "profilButtonLayout changeButtonProfilColor";
        nomPrenomUserDiv.textContent = userFirstName+" "+userLastName;

        var nomPrenomUserDivLink = document.createElement('a');
        nomPrenomUserDivLink.href = "./Profil";

        var deconnectionDiv = document.createElement('div');
        deconnectionDiv.id = "./LogOut";
        deconnectionDiv.className = "buttonLayout changeButtonColor";
        deconnectionDiv.textContent = "Se déconnecter";

        var deconnectionDivLink = document.createElement("a");
        deconnectionDivLink.href = "./LogOut";


        //Bouton catalogue
        var catalogueMainDiv = document.getElementById("catalogue");
        var catalogueButtonDiv = document.createElement('div');
        catalogueButtonDiv.textContent = "Voir plus";
        catalogueButtonDiv.className = "buttonLayout changeButtonColor";
        catalogueButtonDiv.id = "buttonCatalogue";


        var catalogueButtonDivLink = document.createElement('a');
        catalogueButtonDivLink.href = "./ExploreCat";
        catalogueButtonDivLink.id = "buttonCatalogueLink";



        //Assemblage
        //Titre-Barre de menu
        var titleDiv = document.createElement('div');
        titleDiv.id = "title";

        var linkTitleDiv = document.createElement('a');
        linkTitleDiv.href = "./Accueil";
        linkTitleDiv.textContent = "UsTube";

        nomPrenomUserDivLink.appendChild(nomPrenomUserDiv);
        titleDiv.appendChild(linkTitleDiv);
        div.appendChild(titleDiv);
        div.appendChild(nomPrenomUserDivLink);
        deconnectionDivLink.appendChild(deconnectionDiv);
        div.appendChild(deconnectionDivLink);
        mainDiv.appendChild(div);

        //Bouton catalogue principal
        catalogueButtonDivLink.appendChild(catalogueButtonDiv);
        catalogueMainDiv.appendChild(catalogueButtonDivLink);
    }
})