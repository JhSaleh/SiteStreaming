<%--
  Created by IntelliJ IDEA.
  User: jeanhanna
  Date: 19/02/2021
  Time: 23:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.siteStreaming.SiteStreaming.DataBase.S" %>
<%@ page import="com.siteStreaming.SiteStreaming.Acceuil.MetaErrorHandler" %>
<%@ page import="com.siteStreaming.SiteStreaming.Acceuil.CompteClient" %>

<%
    //Récupération des données transmises depuis le servlet en cas de connection
    //En cas d'erreur de tentative de connection
    String mailAddressUsed = (String)request.getAttribute("mailAddressUsed");
    String passwordUsed = (String)request.getAttribute("passwordUsed");
    MetaErrorHandler metaErrorHandler = new MetaErrorHandler(mailAddressUsed, passwordUsed);

    //Création de la sessions
    CompteClient client = (CompteClient) session.getAttribute("sessionUtilisateur");
%>

<!DOCTYPE html>
<html>
<head>
    <title>UsTube</title>
    <link rel="shortcut icon" href="#"> <!--favicon error-->
    <link rel="stylesheet" type="text/css" href="css/acceuil.css">
    <link rel="stylesheet" type="text/css" href="css/connexion.css">
    <link rel="stylesheet" type="text/css" href="css/catalogue.css">
    <script src="js/client.js"></script>
    <script src="js/storeObject.js"></script>
    <script src="js/titleBarCreation.js"></script>
    <script src="js/modal.js"></script>
    <script src="js/waitForHTMLElementToLoad.js"></script>
    <script src="js/acceuil.js"></script>



    <%//les appels window.addEventListener stack de base%>
    <%if(client == null){%>
        <script>
            window.addEventListener("load", function (){
                waitForElement("connexion", createModal); //Va attendre la creation du bouton SignIn avant d'executé le script du modal
                logedOut(); //Cas utilisateur non connecté
            })
        </script>
    <%} else {%>
    <script>
        window.addEventListener("load", function () {
            //Attente de la construction de la barre

            //Construction de la barre de menu en js, car l'information de connection est au niveau du client
            //const client = getObject('client');
            var nomClient = <%=S.cd(client.getNom())%>;
            var prenomClient = <%=S.cd(client.getPrenom())%>;
            logedIn(nomClient, prenomClient); //Construit la barre de navigation dans le cas où l'utilisateur est connecté
        })
    </script>
    <%}%>

    <script>
        window.addEventListener("load", function (){
            //Bind le fait d'appuyer au clavier avec la disparition du msg de status en cas d'erreur de credentials
            var listId = ["mailAddress", "statusMsg"];
            waitForManyElements(listId, acceuilCheckEventTypingMailConnection);
        })
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
    <%if(mailAddressUsed == null){%>
        <div id="connexion" class="modal modalHidden">
    <%}else{ //En cas d'erreur de connection, réaffichage automatique du modal%>
        <div id="connexion" class="modal modalNotHidden">
    <%}%>
        <div class="modal-content gridyModal"> <!--Contenu du modal-->
            <div id="SignUpModal">Se connecter</div>
            <div class="close">&times;</div> <!--syntaxe pour le bouton x-->
            <form id="formSignIn" action="${pageContext.request.contextPath}/Acceuil" method="POST">

                <div class="gridyFields">
                    <label id="mailAddressLabel" for="mailAddress">Adresse mail :</label>
                    <input id="mailAddress" class="labelStyle" type="email" value=<%=metaErrorHandler.getEmailUsed()%> required name="mailAddress">

                    <label id="mailPasswordLabel" for="password">Mot de passe :</label>
                    <input id="password" class="labelStyle" type="password" value=<%=metaErrorHandler.getPasswordUsed()%> required name="password">

                    <input id="validate" type="submit" value="Validez">
                </div>
            </form>
            <%if(mailAddressUsed == null){%>
                <div id="statusMsg" class="statusMsgLayoutHidden"></div>
            <%}else{%>
                <div id="statusMsg" class="statusMsgLayout">Email ou mot de passe incorrect.</div>
            <%}%>
        </div>
    </div>
    <!--Fin du modal-->


<!--Partie visible du site-->
    <div id="gridyHeader"> <!--Construit dans titleBarCreation.js-->

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

    <div id = "catalogue">

    </div>
</body>

<footer class="footer">© Copyright 2021 All Rights Reserved.</footer>
</html>