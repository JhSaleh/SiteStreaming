<%--
  Created by IntelliJ IDEA.
  User: jeanhanna
  Date: 19/02/2021
  Time: 23:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.siteStreaming.SiteStreaming.DataBase.S" %>


<% //Récupération des données transmises depuis le servlet
    Boolean signedInSent = (Boolean)request.getAttribute("signedInSent");
    Boolean signedIn = false;
    String[] infoClient = (String[])request.getAttribute("infoClient");
%>

<!DOCTYPE html>
<html>
<head>
    <title>UsTube</title>
    <link rel="shortcut icon" href="#"> <!--favicon error-->
    <link rel="stylesheet" type="text/css" href="css/acceuil.css">
    <link rel="stylesheet" type="text/css" href="css/connexion.css">
    <script src="js/client.js"></script>
    <script src="js/storeObject.js"></script>
    <script src="js/titleBarCreation.js"></script>
    <script src="js/modal.js"></script>
    <script src="js/waitForHTMLElementToLoad.js"></script>




    <%System.out.println("@infoClient"+infoClient);%>
    <%if(signedInSent != null && signedInSent == true){ //Si l'info envoyé correspond à la connection de l'utilisateur, execution d'un script js%>
    <script>
        window.addEventListener("load", function (){
            const userConnecte = new Client(<%=S.c(infoClient[2])%>, <%=S.c(infoClient[3])%>, <%=S.c(infoClient[1])%>, <%=S.c(infoClient[0])%>, <%=S.c(infoClient[4])%>, <%=S.c(infoClient[5])%>, <%=S.c(infoClient[6])%>);
            saveObject('client', userConnecte);
            <%System.out.println("Informations client sauvegardé.\nClient connecté.");
            signedInSent = false;
            %>
            //ajouté système pour déconnecter
        }, false)
    </script>
    <%
        } //les appels window.addEventListener stack de base
    %>


    <script>
        window.addEventListener("load", function (){
            //Attente de la construction de la barre
            waitForElement("connexion", createModal); //Va attendre la creation du bouton SignIn avant d'executé le script du modal

            //Construction de la barre de menu en js, car l'information de connexion est au niveau du client
            const client = getObject('client');
            if (client != undefined) { //Donc un utilisateur est connecté
                var nomClient = client.nom;
                var prenomClient = client.prenom;
                var greetingsAcceuil = document.getElementById("nomPrenomUser"); //Récupération de l'objet div
                logedIn(nomClient, prenomClient); //Construit la barre de navigation dans le cas où l'utilisateur est connecté

            } else {
                logedOut();
            }
        });
    </script>

</head>



<%
    String defaultValue = "VideoImage";
    String defaultValueTitle = "VideoTitle";
    String defaultValueViews = "-9999";
    String defaultValueYear = "01/02/2021";

%>


<body>
    <!--Modal : ou page superposée-->
    <div id="connexion" class="modal">
        <div class="modal-content gridyModal"> <!--Contenu du modal-->
            <div id="SignUpModal">Se connecter</div>
            <div class="close">&times;</div> <!--syntaxe pour le bouton x-->
            <form id="formSignIn" action="${pageContext.request.contextPath}/Acceuil" method="POST">

                <div class="gridyFields">
                    <label id="mailAddressLabel" for="mailAddress">Adresse mail</label>
                    <input id="mailAddress" class="labelStyle" type="email" required name="mailAddress">

                    <label id="mailPasswordLabel" for="password">Mot de passe</label>
                    <input id="password" class="labelStyle" type="password" required name="password">

                    <input id="validate" type="submit" value="Validez">
                </div>
            </form>
        </div>
    </div>
    <!--Fin du modal-->


<!--Partie visible du site-->
    <div id="gridyHeader">

    </div>



    <!--à générer automatiquement plus tard-->
    <div class="section">
        <h4>Recommandations du moment :</h4>
        <div id="gridyRecommendedVideos">
            <%
                String videoRec = "";
                String titleRec = "";
                String nbViewsRec = "";
                String yearRec = "";
                for(int i = 1; i<6; i++){
                videoRec = "vidRec" + Integer.toString(i);
                titleRec = "titleRec" + Integer.toString(i);
                nbViewsRec = "nbViewsRec" + Integer.toString(i);
                yearRec = "yearRec" + Integer.toString(i);

            %>

            <div id = <%=videoRec%>><%=defaultValue %></div>
            <div id = <%=titleRec%>><%=defaultValueTitle%></div>
            <div id = <%=nbViewsRec%>><%=defaultValueViews%></div>
            <div id = <%=yearRec%>><%=defaultValueYear%></div>

            <%
            }
            %>
        </div>
    </div>



    <div class="section">
        <h4>Morceaux Populaires :</h4>
        <div id = "gridyPopularVideos">
            <%
                String videoPop = "";
                String titlePop = "";
                String nbViewsPop = "";
                String yearPop = "";
                String playPop = "";
                for(int i = 1; i<6; i++){
                    videoPop = "vidPop" + Integer.toString(i);
                    titlePop = "titlePop" + Integer.toString(i);
                    nbViewsPop = "nbViewsPop" + Integer.toString(i);
                    yearPop = "yearPop" + Integer.toString(i);
                    playPop = "playPop" + Integer.toString(i);
            %>

            <div id = <%=videoPop%>><%=defaultValue%></div>
            <div id = <%=titlePop%>><%=defaultValueTitle%></div>
            <div id = <%=nbViewsPop%>><%=defaultValueViews%></div>
            <div id = <%=yearPop%>><%=defaultValueYear%></div>
            <div id = <%=playPop%>>BoutonPlay</div>
            <%
                }
            %>
        </div>
    </div>
</body>

<footer class="footer">© Copyright 2021 All Rights Reserved.</footer>
</html>