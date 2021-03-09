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
    <link rel="stylesheet" type="text/css" href="../../css/acceuil.css"> <!--Attention on est dans le répertoire Accueil, il faut donc remonter un cran-->
    <link rel="stylesheet" type="text/css" href="../../css/inscription.css">
    <link rel="stylesheet" type="text/css" href="../../css/researchClient.css">
    <script src="../../js/inscription.js"></script>
    <script src="../../js/waitForHTMLElementToLoad.js"></script>
    <title>Administration Profil Client</title>

    <script>
    </script>
</head>

<body>
<div class = "gridyProfilTitle">
    <div id ="title"><a href="/SiteStreaming_war_exploded/Acceuil">UsTube</a></div>
    <a href="${pageContext.request.contextPath}/Administration/AdminProfilClient"><div id = "inscriptionTitle">Modification Profil Client</div></a>
    <a href="/SiteStreaming_war_exploded/LogOut"><div id = "LogOut" class="buttonLayout changeButtonColor">Se déconnecter</div></a>
</div>

<form id="searchClient" action="${pageContext.request.contextPath}/Administration/AdminProfilClient" method="POST">
    <div id = "gridyMainBodySearchClient">

            <div id="EmailSearchBlock">
                <label id="emailResearchL" for="emailResearch">E-mail :</label>
                <input id="emailResearch" class="labelStyle" type="text" name="email">
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

<!--
        <div id = "clientsFound" class="section">

        <div class="alignResultsGreedy">

                <div id="emailUser"></div>
                <div id="nomUser"></div>
                <div id="prenomUser"></div>
                <div id="civiliteUser"></div>
                <div id="mdpUser"></div>
                <div id="birthdateUse"></div>
                <div id="adresseFacturationUser"></div>
                <div id="styleMusique"></div>

            </div>
        </div>-->

<%if(resultatResearch != null){
    if(resultatResearch.size() > 0){
%>
        <table class="tableSearchClient">
            <thead class="headColumns">
                <td>E-Mail</td>
                <td>Nom</td>
                <td>Prenom</td>
                <td>Civilite</td>
                <td>Mot de passe</td>
                <td>Date de naissance</td>
                <td>Adresse de Facturation</td>
                <td>Style de musique</td>
                <td>Valider</td>
            </thead>
        <%for(int i = 0; i<resultatResearch.size(); i++){
        CompteClient compte = resultatResearch.get(i);
        %>
        <tr class="rowTableStyle<%=i%2%>">
            <td class="columnResult"><%=compte.getMail()%></td>
            <td class="columnResult"><%=compte.getNom()%></td>
            <td class="columnResult"><%=compte.getPrenom()%></td>
            <td class="columnResult"><%=compte.getCivilite()%></td>
            <td class="columnResult"><%=compte.getPassword()%></td>
            <td class="columnResult"><%=compte.getBirthDate()%></td>
            <td class="columnResult"><%=compte.getAddress()%></td>
            <td class="columnResult"><%=compte.getStyleMusique()%></td>
            <td class="columnResult"><a href="${pageContext.request.contextPath}/Administration/AdminProfilClient?emailSelected=<%=compte.getMail()%>"><div style="height: 100%; width: 100%">Select</div></a></td>
        </tr>
        <%}%>
        </table>

<%} else {%>
<div class="errorMsgResearchClient">Aucun résultats.</div>
<%
    }
    }
%>
</body>

<footer class="footer">© Copyright 2021 All Rights Reserved.</footer>
</html>
