<%@ page import="com.siteStreaming.SiteStreaming.Acceuil.CompteClient" %><%--
  Created by IntelliJ IDEA.
  User: jeanhanna
  Date: 19/02/2021
  Time: 23:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.siteStreaming.SiteStreaming.Acceuil.CompteClient"%>
<%@ page import="com.siteStreaming.SiteStreaming.Acceuil.MetaCompteClient" %>
<%
    CompteClient compteInscription = (CompteClient) request.getAttribute("compteInscription"); //Récupération du compte qu'on a essayé d'inscrire
    MetaCompteClient compte = new MetaCompteClient(compteInscription);
    Boolean successSignUp = (Boolean) request.getAttribute("successSignUp");

    CompteClient client = (CompteClient) session.getAttribute("client");
%>



<html>
<head>
    <link rel="shortcut icon" href="#"> <!--favicon error-->
    <link rel="stylesheet" type="text/css" href="../css/acceuil.css"> <!--Attention on est dans le répertoire Accueil, il faut donc remonter un cran-->
    <link rel="stylesheet" type="text/css" href="../css/inscription.css">
    <script src="../js/inscription.js"></script>
    <script src="../js/waitForHTMLElementToLoad.js"></script>
    <title>SignUp</title>

    <script>
        window.addEventListener("load", function () {
            //Vérification de la cohérence des mdps
            var listIdToWait = ["password", "confirmPassword", "checkMdp1", "checkMdp2"];
            waitForManyElements(listIdToWait, inscriptionEventChecksMdp); //Applique le binding aux champs de mdp

            //Vérification du formatage de mail
            var listIdToWait2 = ["mail", "checkMail"];
            waitForManyElements(listIdToWait2, inscriptionEventChecksEmailFormat); //Applique le binding aux champs de mdp

            //Vérification de la date de naissance
            var listIdToWait3 = ["dateNaissance", "ageStatus"];
            waitForManyElements(listIdToWait3, inscriptionEventChecksBirthDate);
        })
    </script>
</head>

<body>
    <div class = "gridyHeaderInscription">
        <div id ="title"><a href="/SiteStreaming_war_exploded/Acceuil">UsTube</a></div>
        <div id = "inscriptionTitle">Inscription</div>
    </div>

    <div class="gridyBody">
        <form id="inscription" action="${pageContext.request.contextPath}/Acceuil/Inscription" method="POST">
            <div class="gridyInscriptionForm">

                <label id="nomL" for="nom">Nom :</label>
                <input id="nom" class="labelStyle" type="text" value=<%=compte.getNom()%> required name="nom"> <!--name pour récupérer le nom sous jee-->

                <label id="prenomL" for="prenom">Prenom :</label>
                <input id="prenom" class="labelStyle" type="text" value=<%=compte.getPrenom()%> required name="prenom">

                <label id="dateNaissanceL" for="dateNaissance">Date de naissance :</label>
                <input id="dateNaissance" class="labelStyle" type="date" value=<%=compte.getBirthDate()%> required name="birthDate" min="1900-01-01">

                <label id="civiliteL" for="civilite">Civilite :</label>

                <!--La liste déroulante garantit l'input-->
                <select id = "civilite" class="labelStyle" size="1" value=<%=compte.getCivilite()%> name="civilite">
                    <option>Monsieur</option>
                    <option>Madame</option>
                </select>

                <label id="mailL" for="mail">Adresse mail :</label>
                <input id="mail" class="labelStyle" type="email" value=<%=compte.getMail()%>required name="mail">
                <div id="checkMail"></div>

                <label id="passwordL" for="password">Mot de passe :</label>
                <input id="password" class="labelStyle" type="password" value=<%=compte.getPassword()%> required name="password">
                <div id = "checkMdp1"></div>

                <label id="confirmPasswordL" for="confirmPassword">Confirmation du mot de passe :</label>
                <input id="confirmPassword" class="labelStyle" type="password" value=<%=compte.getPassword()%> required name="confirmPassword">
                <div id = "checkMdp2"></div>


                <label id="adresseFacturationL" for="adresseFacturation">Adresse de facturation :</label>
                <input id="adresseFacturation" class="labelStyle" type="text" value=<%=compte.getAddress()%> required name="adresseFacturation">

                <label id ="styleMusiqueL" for="styleMusique">Style de musique préféré</label>
                <select id ="styleMusique" class="labelStyle" size="1" value=<%=compte.getStyleMusique()%> name="styleMusique">
                    <option>House</option>
                    <option>Pop</option>
                    <option>Metal</option>
                    <option>Classique</option>
                    <option>Jazz</option>
                </select>

                <input id="validateInscription" type="submit" value="Validez">
            </div>
        </form>

        <div id="statusFieldsRight" class="gridyStatusFieldsRight">
            <div id="mailStatus" class="statusMsgLayoutHidden"></div>
            <div id="mdpStatus" class="statusMsgLayoutHidden"></div>
            <div id="confirmMdpStatus" class="statusMsgLayoutHidden"></div>
            <div id="ageStatus" class="statusMsgLayoutHidden"></div>
        </div>

        <div id="statusFieldsLeft" class="gridyStatusFieldsLeft">
            <div id="mdpStrengthStatus" class="statusMsgLayoutHidden"></div>
            <%if(compteInscription != null){%>
            <div id="mailAlreadyTaken" class="statusMsgLayout">Mail non disponible.</div>
            <%}%>
        </div>

        <%if(successSignUp != null && successSignUp == true){%>
            <div id = successSignUp>Inscription réussit !</div>
        <%}%>
    </div>


</body>

<footer class="footer">© Copyright 2021 All Rights Reserved.</footer>
</html>
