<%@ page import="com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Musique" %>
<%@ page import="com.siteStreaming.SiteStreaming.DataBase.AdminDatabase" %>
<%@ page import="com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Enumérations.genreMusical" %>
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

    String action = request.getParameter("actionChoisit");
    String choixContenu = request.getParameter("choixContenuChoisit");


    String idMusique = request.getParameter("idMusique");
    String idRadio = request.getParameter("idRadio");
    String idPodcast = request.getParameter("idPodcast");

    LoggerSite.logger.debug("-----Sur le jsp\nAction : "+action+"\nchoixContenu : "+choixContenu+"\nidMusique : "+idMusique);
%>


<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/administration.css">
    <link rel="stylesheet" type="text/css" href="../css/acceuil.css">
    <title>Modification Catalogue</title>
</head>

<body>
    <div class = "gridyHeaderInscription">
        <div id ="title"><a href="${pageContext.request.contextPath}/Acceuil">UsTube</a></div>
        <a href="${pageContext.request.contextPath}/Administration/AdminGestionnaireMusical" id="buttonCatalogueLink">
            <div id = "inscriptionTitle">Gestionnaire Catalogue</div>
        </a>
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


    </div>


<div>

</div>

</body>

<footer class="footer">© Copyright 2021 All Rights Reserved.</footer>

</html>
