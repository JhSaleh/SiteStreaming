<%@ page import="com.siteStreaming.SiteStreaming.DataBase.CatalogueDatabase" %>
<%@ page import="com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.ContenuSonore" %>
<%@ page import="java.util.List" %>
<%@ page import="com.siteStreaming.SiteStreaming.PageWebAdmin.ModificationCatalogue" %>
<%@ page import="com.siteStreaming.SiteStreaming.DataBase.S" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Musique" %>
<%@ page import="com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Radio" %>
<%@ page import="com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Podcast" %><%--
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
    Boolean secondStep = (Boolean) request.getAttribute(ModificationCatalogue.secondStep);

    String champsRechercheCompleter = (String) request.getAttribute(ModificationCatalogue.fieldResearchCompleted);
    String dropDownModifierSupprimer = (String) request.getAttribute(ModificationCatalogue.dropDownListSupprimerModifier);

    choix = S.checkNull(choix);
    action = S.checkNull(action);
    champsRechercheCompleter = S.checkNull(champsRechercheCompleter);
    dropDownModifierSupprimer = S.checkNull(dropDownModifierSupprimer);

    ArrayList<Musique> resultatListeMusique = (ArrayList<Musique>) request.getAttribute("resultatListeMusique");
    ArrayList<Radio> resultatListeRadio = (ArrayList<Radio>) request.getAttribute("resultatListeRadio");
    ArrayList<Podcast> resultatListePodcast = (ArrayList<Podcast>) request.getAttribute("resultatListePodcast");
%>

<html>
<head>
    <title>Modification Catalogue</title>
    <link rel="stylesheet" type="text/css" href="../css/administration.css">
    <link rel="stylesheet" type="text/css" href="../css/acceuil.css">
</head>

<body>
    <div class = "gridyHeaderInscription">
        <div id ="title"><a href="${pageContext.request.contextPath}/Accueil">UsTube</a></div>
        <div id = "modifTitle">Gestionnaire Catalogue</div>
        <a href="${pageContext.request.contextPath}/LogOut"><div id = "LogOut" class="buttonLayout changeButtonColor">Se déconnecter</div></a>
    </div>


    <form id="choiceContenuModification" action="${pageContext.request.contextPath}/Administration/AdminGestionnaireMusical" method="POST">
        <div id="gridyOptionsModifications">

            <div id ="action">
                <label id="actionL" for="action">Action :</label>
                <select class="labelStyle" size="1" value=<%=S.cd(action)%> name="action">
                    <%if(action.equals("Ajouter")){%>
                        <option selected="selected">Ajouter</option>
                        <option>Modifier</option>
                        <option> Supprimer</option>

                    <%} else if(action.equals("Modifier")){%>
                        <option >Ajouter</option>
                        <option selected="selected" >Modifier</option>
                        <option> Supprimer</option>
                    <%}else if(action.equals("Supprimer")){%>
                        <option selected="selected">Ajouter</option>
                        <option>Modifier</option>
                        <option selected="selected"> Supprimer</option>
                    <%}else{%>
                        <option selected="selected">Ajouter</option>
                        <option>Modifier</option>
                        <option> Supprimer</option>
                    <%}%>
                </select>
            </div>

            <div id="choixContenu">
                <label id="choixContenuL" for="choixContenu">Choix du contenu :</label>
                <select class="labelStyle" size="1" value=<%=S.cd(choix)%> name="choixContenu">
                    <%if(choix.equals("Musique")){%>
                        <option selected="selected">Musique</option>
                        <option>Radio</option>
                        <option>Podcast</option>
                    <%}else if(choix.equals("Radio")) {%>
                        <option>Musique</option>
                        <option selected="selected">Radio</option>
                        <option>Podcast</option>
                    <%} else if(choix.equals("Podcast")){%>
                        <option>Musique</option>
                        <option>Radio</option>
                        <option selected="selected">Podcast</option>
                    <%} else {%>
                        <option>Musique</option>
                        <option>Radio</option>
                        <option>Podcast</option>
                    <%}%>
                </select>
            </div>
            <input id="validateInscription" class="buttonLayoutCatalogue" type="submit" value="Valider">
            </div>
        </form>


<%if(firstStep){
    if(action.equals(ModificationCatalogue.Modifier) || action.equals(ModificationCatalogue.Supprimer)){%>
        <form id="ajouterContenuModification" action="${pageContext.request.contextPath}/Administration/AdminGestionnaireMusical?actionChoisit=<%=action%>&choixContenuChoisit=<%=choix%>" method="POST">
            <div id="gridyModifierSupprimer">
                <label id="rechercherPar" for="choixContenu">Rechercher par:</label>
                <select id ='champsSelectModifierSupprimer' class="labelStyle" value=<%=S.cd(dropDownModifierSupprimer)%> size="1"  name="dropDownListModifierSupprimer">
                    <%if(dropDownModifierSupprimer.equals("Id")){%>
                        <option selected="selected">Id</option>
                        <option>Nom</option>
                    <%}else if(dropDownModifierSupprimer.equals("Nom")){%>
                        <option>Id</option>
                        <option selected="selected">Nom</option>
                    <%}else{%>
                        <option>Id</option>
                        <option>Nom</option>
                    <%}%>
                </select>

                <input id="fieldModifierSupprimer" class="labelStyle" type="text" value="<%=champsRechercheCompleter%>" required name="fieldModifierSupprimer">
                <input id="validateModifierSupprimer" class="buttonLayoutCatalogue" type="submit" value="Valider">
            </div>
        </form>
    <%}%>

<%}%>

<%
    if(secondStep != null && secondStep){
    if(resultatListeMusique != null && resultatListeMusique.size() > 0){
%>
    <table class="musicListResult">
        <thead class="headColumns">
            <td>Id Musique</td>
            <td>Titre</td>
            <td>Interprete</td>
            <td>Année de création</td>
            <td>Genre musical</td>
            <td>Durée</td>
            <td>Valider</td>
        </thead>

        <%for(int i = 0; i<resultatListeMusique.size(); i++){
            Musique musique = resultatListeMusique.get(i);
        %>
        <tr class="rowTableStyle<%=i%2%>">
            <td class="columnResult"><%=musique.getId()%></td>
            <td class="columnResult"><%=musique.getTitre()%></td>
            <td class="columnResult"><%=musique.getInterprete()%></td>
            <td class="columnResult"><%=musique.getAnneeCreation()%></td>
            <td class="columnResult"><%=musique.getGenreMusical()%></td>
            <td class="columnResult"><%=musique.getDuree()%></td>
            <td class="columnResult"><a href="${pageContext.request.contextPath}/Administration/AdminGestionnaireMusicalTraitement?actionChoisit=<%=action%>&choixContenuChoisit=<%=choix%>&idMusique=<%=musique.getId()%>"><div style="height: 100%; width: 100%">Select</div></a></td>
        </tr>
        <%}%>
    </table>
    <%}else if(resultatListeRadio != null && resultatListeRadio.size() > 0){%>
        <table class="radioListResult">
        <thead class="headColumns">
        <td>Id Radio</td>
        <td>Nom</td>
        <td>Genre musical</td>
        <td>Valider</td>
        </thead>

        <%for(int i = 0; i<resultatListeRadio.size(); i++){
            Radio radio = resultatListeRadio.get(i);
        %>
        <tr class="rowTableStyle<%=i%2%>">
            <td class="columnResult"><%=radio.getId()%></td>
            <td class="columnResult"><%=radio.getNom()%></td>
            <td class="columnResult"><%=radio.getGenreMusical()%></td>
            <td class="columnResult"><a href="${pageContext.request.contextPath}/Administration/AdminGestionnaireMusicalTraitement?actionChoisit=<%=action%>&choixContenuChoisit=<%=choix%>&idRadio=<%=radio.getId()%>"><div style="height: 100%; width: 100%">Select</div></a></td>
        </tr>
        <%}%>
    </table>

    <%} else if(resultatListePodcast != null && resultatListePodcast.size() > 0) {%>
    <table class="podcastListResult">
        <thead class="headColumns">
        <td>Id Podcast</td>
        <td>Titre</td>
        <td>Durée</td>
        <td>Auteur</td>
        <td>Catégorie</td>
        <td>Valider</td>
        </thead>

        <%for(int i = 0; i<resultatListePodcast.size(); i++){
            Podcast podcast = resultatListePodcast.get(i);
        %>
        <tr class="rowTableStyle<%=i%2%>">
            <td class="columnResult"><%=podcast.getId()%></td>
            <td class="columnResult"><%=podcast.getTitre()%></td>
            <td class="columnResult"><%=podcast.getDuree()%></td>
            <td class="columnResult"><%=podcast.getAuteur()%></td>
            <td class="columnResult"><%=podcast.getCategorie()%></td>
            <td class="columnResult"><a href="${pageContext.request.contextPath}/Administration/AdminGestionnaireMusicalTraitement?actionChoisit=<%=action%>&choixContenuChoisit=<%=choix%>&idPodcast=<%=podcast.getId()%>"><div style="height: 100%; width: 100%">Select</div></a></td>
        </tr>
        <%}%>
    </table>


    <%}else{%>
    <div class="errorMsgResearchClient">Aucun résultats.</div>
    <%
}
}
%>
</body>
<footer class="footer">© Copyright 2021 All Rights Reserved.</footer>
</html>
