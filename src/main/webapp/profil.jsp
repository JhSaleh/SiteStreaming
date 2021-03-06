<%--
  Created by IntelliJ IDEA.
  User: jeanhanna
  Date: 03/03/2021
  Time: 22:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.siteStreaming.SiteStreaming.Acceuil.CompteClient"%>
<%@ page import="com.siteStreaming.SiteStreaming.Acceuil.MetaCompteClient" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<%
    /* Compte du client connecté */
    CompteClient compteProfil = (CompteClient) request.getAttribute("compteProfil");

%>


<html>
<head>
    <title>Mon compte</title>

    <link rel="shortcut icon" href="#"> <!--favicon error-->

    <link rel="stylesheet" type="text/css" href="./css/profil.css">
    <script src="./js/profil.js"></script>
  <!-- <script src="../js/waitForHTMLElementToLoad.js"></script> -->


    <script>

        /*

    window.addEventListener("load", function () {
    //Vérification de la cohérence des mdps
    var listIdToWait = ["passwordUser", "confirmPasswordUser", "checkMdp1User", "checkMdp2User"];
    waitForManyElements(listIdToWait, profilEventChecksMdp); //Applique le binding aux champs de mdp

    //Vérification du formatage de mail
    var listIdToWait2 = ["mailUser", "checkMailUser"];
    waitForManyElements(listIdToWait2, profilEventChecksEmailFormat); //Applique le binding aux champs de mdp

    //Vérification de la date de naissance
    var listIdToWait3 = ["dateNaissanceUser", "ageStatusUser"];
    waitForManyElements(listIdToWait3, profilEventChecksBirthDate);


        }
    )

*/
    </script>

</head>


<body style ="background-color:black;">
    Page profil

    <div class = "gridyHeaderProfil">
        <div id ="title"><a href="/SiteStreaming_war/Acceuil" style="color:#FFFFFF">UsTube</a></div>
        <div id = "profilTitle" style="color:#FFFFFF">Mon compte</div>
    </div>

    <div class="gridyBodyUser">
        <form id="profil" action="${pageContext.request.contextPath}/Profil" method="POST">
            <div class="gridyProfilForm">
                <label id="nomLUser" for="nomUser">Nom :</label>
                <input id="nomUser" class="labelStyle" type="text" required name="nomUser" value="Nom du User">

                <label id="prenomLUser" for="prenomUser">Prenom :</label>
                <input id="prenomUser" class="labelStyle" type="text" required name="prenomUser" value="Prenom du User">

                <label id="dateNaissanceLUser" for="dateNaissanceUser">Date de naissance :</label>
                <input id="dateNaissanceUser" class="labelStyle" type="date" required name="birthDateUser">

                <label id="civiliteLUser" for="civiliteUser">Civilite :</label>

                <!--
                <input id="civilite" class="labelStyle" type="" required name="civilite">
                -->
                <!--La liste déroulante garantit l'input-->
                <select id = "civiliteUser" class="labelStyle" size="1" name="sexeUser">
                    <option>Homme</option>
                    <option>Femme</option>
                    <option>Non-binaire</option>
                </select>

                <label id="mailLUser" for="mailUser">Adresse mail :</label>
                <input id="mailUser" class="labelStyle" type="emailUser" required name="mailUser">
                <div id="checkMailUser"></div>

                <label id="passwordLUser" for="passwordUser">Mot de passe :</label>
                <input id="passwordUser" class="labelStyle" type="passwordUser" required name="passwordUser">
                <div id = "checkMdp1User"></div>

                <label id="confirmPasswordLUser" for="confirmPasswordUser">Confirmation du mot de passe :</label>
                <input id="confirmPasswordUser" class="labelStyle" type="passwordUser" required name="confirmPasswordUser">
                <div id = "checkMdp2User"></div>


                <label id="adresseFacturationLUser" for="adresseFacturationUser">Adresse de facturation :</label>
                <input id="adresseFacturationUser" class="labelStyle" type="text" required name="adresseFacturationUser">


                <input id="validateProfil" type="submit" value="Confirmer les modifications">
            </div>
        </form>
    </div>
</body>

<footer class="footer">© Copyright 2021 All Rights Reserved.</footer>

</html>
