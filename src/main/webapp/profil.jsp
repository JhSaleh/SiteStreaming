<%--
  Created by IntelliJ IDEA.
  User: jeanhanna
  Date: 03/03/2021
  Time: 22:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>




<html>
<head>
    <title>Mon compte</title>

    <link rel="shortcut icon" href="#"> <!--favicon error-->

    <link rel="stylesheet" type="text/css" href="./css/profil.css">
    <!--     <script src="../js/inscription.js"></script>                          JS à créer-->
    <!--     <script src="../js/waitForHTMLElementToLoad.js"></script>                       -->

    <!--
    <script>
        window.addEventListener("load", function () {
            //Vérification de la cohérence des mdps
            var listIdToWait = ["password", "confirmPassword", "checkMdp1", "checkMdp2"];
            waitForManyElements(listIdToWait, inscriptionEventChecksMdp); //Applique le binding aux champs de mdp

            //Vérification du formatage de mail
            var listIdToWait2 = ["mail", "checkMail"];
            waitForManyElements(listIdToWait, inscriptionEventChecksEmailFormat); //Applique le binding aux champs de mdp
        })
    </script>
    -->
</head>


<body>
    Page profil

    <div class = "gridyHeaderProfil">
        <div id ="title"><a href="/SiteStreaming_war_exploded/Acceuil">UsTube</a></div>
        <div id = "profilTitle">Mon compte</div>
    </div>

    <div class="gridyBody">
        <form id="profil" action="${pageContext.request.contextPath}/Profil" method="POST">  // Acceuil/Profil ????
            <div class="gridyProfilForm">
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
                <select id = "civilite" class="labelStyle" size="1" name="sexe">
                    <option>Homme</option>
                    <option>Femme</option>
                    <option>Non-binaire</option>
                </select>

                <label id="mailL" for="mail">Adresse mail :</label>
                <input id="mail" class="labelStyle" type="email" required name="mail">
                <div id="checkMail"></div>

                <label id="passwordL" for="password">Mot de passe :</label>
                <input id="password" class="labelStyle" type="password" required name="password">
                <div id = "checkMdp1"></div>

                <label id="confirmPasswordL" for="confirmPassword">Confirmation du mot de passe :</label>
                <input id="confirmPassword" class="labelStyle" type="password" required name="confirmPassword">
                <div id = "checkMdp2"></div>


                <label id="adresseFacturationL" for="adresseFacturation">Adresse de facturation :</label>
                <input id="adresseFacturation" class="labelStyle" type="text" required name="adresseFacturation">


                <input id="validateInscription" type="submit" value="Validez"> /////////////////////// A MODIFIER : Bouton pas encore ajouté
            </div>
        </form>
    </div>
</body>

<footer class="footer">© Copyright 2021 All Rights Reserved.</footer>

</html>
