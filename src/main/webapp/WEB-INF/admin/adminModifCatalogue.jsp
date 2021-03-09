<%@ page import="com.siteStreaming.SiteStreaming.DataBase.CatalogueDatabase" %>
<%@ page import="com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.ContenuSonore" %>
<%@ page import="java.util.List" %>
<%@ page import="com.siteStreaming.SiteStreaming.PageWebAdmin.ModificationCatalogue" %><%--
  Created by IntelliJ IDEA.
  User: rkbcht
  Date: 3/7/2021
  Time: 4:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CatalogueDatabase catalogue = (CatalogueDatabase) request.getAttribute("compteInscription"); //Récupération du compte qu'on a essayé d'inscrire
    String choix = (String) request.getAttribute(ModificationCatalogue.choixModif);
    String action = (String) request.getAttribute(ModificationCatalogue.actionModif);
    Boolean firstStep = (Boolean) request.getAttribute(ModificationCatalogue.firstStep);
%>
<html>
<head>
    <title>Modification Catalogue</title>
    <link rel="stylesheet" type="text/css" href="../../css/administration.css">
    <link rel="stylesheet" type="text/css" href="../../css/acceuil.css">

    <script src="../../js/adminModifCatalogueGridy.js"></script>

<body>
    <div class = "gridyHeaderInscription">
        <div id ="title"><a href="/SiteStreaming_war_exploded/Acceuil">UsTube</a></div>
        <div id = "inscriptionTitle">Profil</div>
        <a href="/SiteStreaming_war_exploded/LogOut"><div id = "LogOut" class="buttonLayout changeButtonColor">Se déconnecter</div></a>
    </div>


    <form id="choiceContenuModification" action="${pageContext.request.contextPath}/Administration/AdminGestionnaireMusical" method="POST">
        <div id="gridyOptionsModifications">

            <div id ="action" >
                <label id="actionL" for="action">Action :</label>
                <select class="labelStyle" size="1"  name="action">
                    <option>Ajouter</option>
                    <option>Modifier</option>
                    <option>Supprimer</option>
                </select>
            </div>

            <div id="choixContenu">
                <label id="choixContenuL" for="choixContenu">Choix du contenu :</label>
                <select class="labelStyle" size="1"  name="choixContenu">
                    <option>Musique</option>
                    <option>Radio</option>
                    <option>Podcast</option>
                </select>
            </div>
            <input id="validateInscription" type="submit" value="Valider">
            </div>
        </form>


<%if(firstStep){
    if(action.equals(ModificationCatalogue.Modifier) || action.equals(ModificationCatalogue.Supprimer)){%>
        <form id="ajouterContenuModification" action="${pageContext.request.contextPath}/Administration/AdminGestionnaireMusical" method="POST">
            <div id="gridyModifierSupprimer">
                <label id="rechercherPar" for="choixContenu">Rechercher par:</label>
                <select id ='champsSelectModifierSupprimer' class="labelStyle" size="1"  name="rechercherModifierSupprimer">
                    <option>Id</option>
                    <option>Nom</option>
                </select>

                <input id="fieldModifierSupprimer" class="labelStyle" type="text" required name="fieldModifierSupprimer">
                <input id="validateModifierSupprimer" type="submit" value="Valider">
            </div>
        </form>
    <%}%>



<%}%>
</body>

</html>
