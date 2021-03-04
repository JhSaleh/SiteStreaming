<%--
  Created by IntelliJ IDEA.
  User: jeanhanna
  Date: 19/02/2021
  Time: 23:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


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
            var listIdToWait = ["password", "confirmPassword", "checkMdp1", "checkMdp2"];
            waitForManyElements(listIdToWait, inscriptionEventChecksMdp); //Applique le binding aux champs de mdp
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
                <input id="nom" class="labelStyle" type="text" required name="nom"> <!--name pour récupérer le nom sous jee-->

                <label id="prenomL" for="prenom">Prenom :</label>
                <input id="prenom" class="labelStyle" type="text" required name="prenom">

                <label id="dateNaissanceL" for="dateNaissance">Date de naissance :</label>
                <input id="dateNaissance" class="labelStyle" type="date" required name="birthDate">

                <label id="civiliteL" for="civilite">Civilite :</label>

                <!--
                <input id="civilite" class="labelStyle" type="" required name="civilite">
                -->
                <!--La liste déroulante garantit l'input-->
                <select id = "civilite" class="labelStyle" size="1" name="civilite">
                    <option>Monsieur</option>
                    <option>Madame</option>
                </select>

                <label id="mailL" for="mail">Adresse mail :</label>
                <input id="mail" class="labelStyle" type="email" required name="mail">

                <label id="passwordL" for="password">Mot de passe :</label>
                <input id="password" class="labelStyle" type="password" required name="password">
                <div id = "checkMdp1"></div>

                <label id="confirmPasswordL" for="confirmPassword">Confirmation du mot de passe :</label>
                <input id="confirmPassword" class="labelStyle" type="password" required name="confirmPassword">
                <div id = "checkMdp2"></div>


                <label id="adresseFacturationL" for="adresseFacturation">Adresse de facturation :</label>
                <input id="adresseFacturation" class="labelStyle" type="text" required name="adresseFacturation">


                <input id="validateInscription" type="submit" value="Validez">
            </div>
        </form>
    </div>
</body>

<footer class="footer">© Copyright 2021 All Rights Reserved.</footer>
</html>
