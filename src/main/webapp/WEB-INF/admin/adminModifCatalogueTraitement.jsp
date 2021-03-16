<%@ page import="com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Musique" %>
<%@ page import="com.siteStreaming.SiteStreaming.DataBase.AdminDatabase" %>
<%@ page import="com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical" %>
<%@ page import="com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Radio" %>
<%@ page import="com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Podcast" %>
<%@ page import="com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.categorie" %>
<%@ page import="com.siteStreaming.SiteStreaming.PageWebAdmin.TraitementModificationCatalogue" %>
<%@ page import="com.siteStreaming.SiteStreaming.LoggerSite" %>
<%@ page import="com.siteStreaming.SiteStreaming.Accueil.CompteAdmin" %>
<%@ page import="com.siteStreaming.SiteStreaming.Access.AdminFilter" %><%--
<%@ page import="com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Radio" %>
<%@ page import="com.siteStreaming.SiteStreaming.LoggerSite" %><%--
  Created by IntelliJ IDEA.
  User: jeanhanna
  Date: 09/03/2021
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //Récupération des données issue de la page initiale
    String action = request.getParameter("actionChoisit");
    String choixContenu = request.getParameter("choixContenuChoisit");

    String idMusique = request.getParameter("idMusique");
    String idRadio = request.getParameter("idRadio");
    String idPodcast = request.getParameter("idPodcast");

    //Results
    Boolean addSuccess = (Boolean) request.getAttribute(TraitementModificationCatalogue.addSuccess);
    Boolean modifySuccess = (Boolean) request.getAttribute(TraitementModificationCatalogue.modifySuccess);
    Boolean deleteSuccess = (Boolean) request.getAttribute(TraitementModificationCatalogue.deleteSuccess);

    CompteAdmin compteAdmin = (CompteAdmin) session.getAttribute(AdminFilter.sessionAdmin);
    LoggerSite.logger.debug("-----Sur le jsp\nAction : "+action+"\nchoixContenu : "+choixContenu+"\nidMusique : "+idMusique);
%>


<html>
<head>
    <link rel="shortcut icon" href="../pictures/logo.ico">
    <link rel="stylesheet" type="text/css" href="../css/administration.css">
    <link rel="stylesheet" type="text/css" href="../css/acceuil.css">
    <link rel="stylesheet" type="text/css" href="../css/stars.css">
    <title>Modification Catalogue</title>
</head>

<body>
    <div class = "gridyAdminTitle">
        <div id ="title"><a href="${pageContext.request.contextPath}/Accueil">UsTube</a></div>
        <a href="${pageContext.request.contextPath}/Administration/AdminGestionnaireMusical" id="buttonCatalogueLink">
            <div id = "adminTitle">Gestionnaire Catalogue</div>
        </a>
        <div id="profilAdmin" class="buttonLayoutAdmin changeButtonColorAdmin"><%=compteAdmin.getNom()+" "+compteAdmin.getPrenom()%></div>
        <a href="${pageContext.request.contextPath}/LogOut"><div id = "LogOut" class="buttonLayout changeButtonColor">Se déconnecter</div></a>
    </div>

    <div id="MetaDiv">
        <!--MUSIQUE-->
        <%
            Musique musique = null;
            if(choixContenu != null && choixContenu.equals("Musique")) {

                LoggerSite.logger.debug("Passage dans la création du formulaire");
                LoggerSite.logger.debug("action :" + action);

                if(idMusique == null){
                    musique = new Musique("",false,"","","", genreMusical.salsa,0);
                } else if(action.equals("Modifier") || action.equals("Supprimer")) {
                    AdminDatabase adminDatabase = new AdminDatabase();
                    musique = adminDatabase.getMusic(Integer.parseInt(idMusique), null).get(0);
                }
        %>

        <form id="formPage" action="${pageContext.request.contextPath}/Administration/AdminGestionnaireMusicalTraitement?idSent=<%=idMusique%>" method="POST">
            <div id="greedyMusicField">
                <label id="actionMusic"><%=action+" "+choixContenu%></label>

                <%if(!action.equals("Ajouter")){%>
                    <label id="idMusicL" for="idMusic">Id Musique :</label>
                    <input id="idMusic" class="labelStyle" type="text" value="<%=musique.getId()%>" disabled name="idMusic">
                <%} else {%>
                    <label id="idMusicL" for="idMusic">Id Musique :</label>
                    <input id="idMusic" class="labelStyle" type="text" value="Automatiquement choisit." disabled name="idMusic">
                <%}%>

                <label id="recommendationMomentL" for="recommendationMoment">Recommendation du moment :</label>
                <select id="recommendationMoment" class="labelStyle" name="recommendationMomentMusic">
                    <%if(musique.getRecommendationMoment()){%>
                        <option selected="selected">Vrai</option>
                        <option>Faux</option>
                    <%}else{%>
                        <option>Vrai</option>
                        <option selected="selected">Faux</option>
                    <%}%>
                </select>

                <label id="titreMusicL" for="titreMusic">Titre :</label>
                <input id="titreMusic" class="labelStyle" type="text" value="<%=musique.getTitre()%>" name="titreMusic">

                <label id="interMusicL" for="interMusic">Interprete :</label>
                <input id="interMusic" class="labelStyle" type="text" value="<%=musique.getInterprete()%>" name="interMusic">

                <label id="yearMusicL" for="yearMusic">Année de création :</label>
                <input id="yearMusic" class="labelStyle" type="text" value="<%=musique.getAnneeCreation()%>" name="yearMusic">

                <label id="genreMusicL" for="genreMusic">Genre musical :</label>
                <select id="genreMusic" class="labelStyle" type="text" name="genreMusic"> <!--Construit dynamiquement les énumérés-->
                    <%for(genreMusical genreMusical : genreMusical.values()){%>
                        <%if(genreMusical.equals(musique.getGenreMusical())){%>
                        <option selected="selected"><%=genreMusical%></option>
                        <%}else{%>
                        <option><%=genreMusical%></option>
                        <%}%>
                    <%}%>
                </select>

                <label id="lengthMusicL" for="lengthMusic">Durée (s):</label>
                <input id="lengthMusic" class="labelStyle" type="text" value="<%=musique.getDuree()%>" name="lengthMusic">

                <input id="validateMusic" class="buttonLayoutCatalogue" type="submit" value="Valider" name="<%=action+"Musique"+"Button"%>">
            </div>
        </form>
    <%}%>

        <!--Radio-->
        <%
            Radio radio = null;
            if(choixContenu != null && choixContenu.equals("Radio")) {

                LoggerSite.logger.debug("Passage dans la création du formulaire");
                LoggerSite.logger.debug("action :" + action);
                LoggerSite.logger.debug("idRadio :"+idRadio);

                if(idRadio == null){
                    radio = new Radio("",false,"",genreMusical.salsa);
                } else if(action.equals("Modifier") || action.equals("Supprimer")) {
                    AdminDatabase adminDatabase = new AdminDatabase();
                   LoggerSite.logger.debug(idRadio);
                    radio = adminDatabase.getRadio(Integer.parseInt(idRadio), null).get(0);
                }
        %>

        <form id="formPage" action="${pageContext.request.contextPath}/Administration/AdminGestionnaireMusicalTraitement?idSent=<%=idRadio%>" method="POST">
            <div id="greedyRadioField">
                <label id="actionRadio"><%=action+" "+choixContenu%></label>

                <%if(!action.equals("Ajouter")){%>
                <label id="idRadioL" for="idRadio">Id Musique :</label>
                <input id="idRadio" class="labelStyle" type="text" value="<%=radio.getId()%>" disabled name="idRadioF">
                <%} else {%>
                <label id="idRadioL" for="idRadio">Id Musique :</label>
                <input id="idRadio" class="labelStyle" type="text" value="Automatiquement choisit." disabled name="idRadioF">
                <%}%>

                <label id="recommendationMomentRadioL" for="recommendationMomentRadio">Recommendation du moment :</label>
                <select id="recommendationMomentRadio" class="labelStyle" name="recommendationMomentRadio">
                    <%if(radio.getRecommendationMoment()){%>
                    <option selected="selected">Vrai</option>
                    <option>Faux</option>
                    <%}else{%>
                    <option>Vrai</option>
                    <option selected="selected">Faux</option>
                    <%}%>
                </select>

                <label id="nomRadioL" for="nomRadio">Titre :</label>
                <input id="nomRadio" class="labelStyle" type="text" value="<%=radio.getNom()%>" name="nomRadio">

                <label id="genreRadioL" for="genreRadio">Genre musical :</label>
                <select id="genreRadio" class="labelStyle" type="text" name="genreRadio"> <!--Construit dynamiquement les énumérés-->
                    <%for(genreMusical genreMusical : genreMusical.values()){%>
                    <%if(genreMusical.equals(radio.getGenreMusical())){%>
                    <option selected="selected"><%=genreMusical%></option>
                    <%}else{%>
                    <option><%=genreMusical%></option>
                    <%}%>
                    <%}%>
                </select>

                <input id="validateRadio" class="buttonLayoutCatalogue" type="submit" value="Valider" name="<%=action+"Radio"+"Button"%>">
            </div>
        </form>
        <%}%>

        <%
            Podcast podcast = null;
            if(choixContenu != null && choixContenu.equals("Podcast")) {

                System.out.println("Passage dans la création du formulaire");
                System.out.println("action :" + action);

                if(idPodcast == null){
                    podcast = new Podcast("",false,"",0,"", categorie.divers);
                } else if(action.equals("Modifier") || action.equals("Supprimer")) {
                    AdminDatabase adminDatabase = new AdminDatabase();
                    podcast = adminDatabase.getPodcast(Integer.parseInt(idPodcast), null).get(0);
                }
        %>

        <form id="formPage" action="${pageContext.request.contextPath}/Administration/AdminGestionnaireMusicalTraitement?idSent=<%=idPodcast%>" method="POST">
            <div id="greedyPodcastField">
                <label id="actionPodcast"><%=action+" "+choixContenu%></label>

                <%if(!action.equals("Ajouter")){%>
                <label id="idPodcastL" for="idPodcast">Id Podcast :</label>
                <input id="idPodcast" class="labelStyle" type="text" value="<%=podcast.getId()%>" disabled name="idPodcast">
                <%} else {%>
                <label id="idPodcastL" for="idPodcast">Id Podcast :</label>
                <input id="idPodcast" class="labelStyle" type="text" value="Automatiquement choisit." disabled name="idPodcast">
                <%}%>

                <label id="recommendationMomentPodcastL" for="recommendationMomentPodcast">Recommendation du moment :</label>
                <select id="recommendationMomentPodcast" class="labelStyle" name="recommendationMomentPodcast">
                    <%if(podcast.getRecommendationMoment()){%>
                    <option selected="selected">Vrai</option>
                    <option>Faux</option>
                    <%}else{%>
                    <option>Vrai</option>
                    <option selected="selected">Faux</option>
                    <%}%>
                </select>

                <label id="titrePodcastL" for="titrePodcast">Titre :</label>
                <input id="titrePodcast" class="labelStyle" type="text" value="<%=podcast.getTitre()%>" name="titrePodcast">

                <label id="dureePodcastL" for="dureePodcast">Durée :</label>
                <input id="dureePodcast" class="labelStyle" type="text" value="<%=podcast.getDuree()%>" name="dureePodcast">

                <label id="yearPodcastL" for="yearPodcast">Année de création :</label>
                <input id="yearPodcast" class="labelStyle" type="text" value="<%=podcast.getAuteur()%>" name="auteurPodcast">

                <label id="categoriePodcastL" for="categoriePodcast">Catégorie :</label>
                <select id="categoriePodcast" class="labelStyle" type="text" name="categoriePodcast"> <!--Construit dynamiquement les énumérés-->
                    <%for(categorie categoriePodcast : categorie.values()){%>
                    <%if(categoriePodcast.equals(podcast.getCategorie())){%>
                    <option selected="selected"><%=categoriePodcast%></option>
                    <%}else{%>
                    <option><%=categoriePodcast%></option>
                    <%}%>
                    <%}%>
                </select>

                <input id="validatePodcast" class="buttonLayoutCatalogue" type="submit" value="Valider" name="<%=action+"Podcast"+"Button"%>">
            </div>
        </form>
        <%}%>
    </div>

<%if(addSuccess != null){%>
<div class="successMsg">
    Ajout réussie !
</div>
<%}%>

<%if(modifySuccess != null){%>
<div class="successMsg">
    Modification réussie !
</div>
<%}%>

<%if(deleteSuccess != null){%>
<div class="successMsg">
    Suppression réussie !
</div>
<%}%>

<!--Animation-->
<div class="animation-wrapper">
    <div class="particle particle-1"></div>
    <div class="particle particle-2"></div>
    <div class="particle particle-3"></div>
    <div class="particle particle-4"></div>
</div>
</body>

<footer class="footer">© Copyright 2021 All Rights Reserved.</footer>

</html>
