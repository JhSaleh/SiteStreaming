<%@ page import="com.siteStreaming.SiteStreaming.Acceuil.CompteClient" %><%--
  Created by IntelliJ IDEA.
  User: jeanhanna
  Date: 19/02/2021
  Time: 23:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.siteStreaming.SiteStreaming.Acceuil.CompteClient"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.siteStreaming.SiteStreaming.PageWebAdmin.adminPageProfil" %>
<%
    ArrayList<CompteClient> resultatResearch = (ArrayList<CompteClient>) request.getAttribute(adminPageProfil.groupeUtilisateurEnvoye);
%>


<html>
<head>
    <link rel="shortcut icon" href="#"> <!--favicon error-->
    <link rel="stylesheet" type="text/css" href="../css/acceuil.css"> <!--Attention on est dans le répertoire Accueil, il faut donc remonter un cran-->
    <link rel="stylesheet" type="text/css" href="../css/inscription.css">
    <link rel="stylesheet" type="text/css" href="../css/researchClient.css">
    <script src="../js/inscription.js"></script>
    <script src="../js/waitForHTMLElementToLoad.js"></script>
    <title>Administration Profil Client</title>

    <script>
    </script>
</head>

<body>
<div class = "gridyProfilTitle">
    <div id ="title"><a href="/SiteStreaming_war_exploded/Acceuil">UsTube</a></div>
    <div id = "inscriptionTitle">Modification Profil Client</div>
    <a href="/SiteStreaming_war_exploded/LogOut"><div id = "LogOut" class="buttonLayout changeButtonColor">Se déconnecter</div></a>
</div>

<form id="searchClient" action="${pageContext.request.contextPath}/Administration/AdminProfilClient" method="POST">
    <div id = "gridyMainBodySearchClient">

            <div id="EmailSearchBlock">
                <label id="emailResearchL" for="emailResearch">E-mail :</label>
                <input id="emailResearch" class="labelStyle" type="email" name="email">
            </div>

            <div id="LastNameSearchBlock">
                <label id="nomResearchL" for="nomResearch">Nom :</label>
                <input id="nomResearch" class="labelStyle" type="text" name="nom"> <!--name pour récupérer le nom sous jee-->
            </div>

            <div id="FirstNameSearchBlock">
                <label id="prenomResearchL" for="prenomResearch">Prenom :</label>
                <input id="prenomResearch" class="labelStyle" type="text" name="prenom">
            </div>

            <input id="ResearchButton" type="submit" value="Recherchez">
    </div>
</form>

<%if(resultatResearch != null){%>
    <div id = "clientsFound" class="section">
        <div>Resultats :</div>
        <%for(int i = 0; i<resultatResearch.size(); i++){
        CompteClient compte = resultatResearch.get(i);
        %>
            <div class="alignResultsGreedy">
                <div id="emailUser"><%=compte.getMail()%></div>
                <div id="nomUser"><%=compte.getNom()%></div>
                <div id="prenomUser"><%=compte.getPrenom()%></div>
                <div id="civiliteUser"><%=compte.getCivilite()%></div>
                <div id="mdpUser"><%=compte.getPassword()%></div>
                <div id="birthdateUse"><%=compte.getBirthDate()%></div>
                <div id="adresseFacturationUser"><%=compte.getAddress()%></div>
                <div id="styleMusique"><%=compte.getStyleMusique()%></div>
            </div>
        <%}%>
    </div>
<%}%>
</body>

<footer class="footer">© Copyright 2021 All Rights Reserved.</footer>
</html>
