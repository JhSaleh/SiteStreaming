<%@ page import="com.siteStreaming.SiteStreaming.Acceuil.CompteClient" %><%--
  Created by IntelliJ IDEA.
  User: declerck
  Date: 06/03/2021
  Time: 08:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>UsTube</title>
    <link rel="shortcut icon" href="#"> <!--favicon error-->
    <link rel="stylesheet" type="text/css" href="css/acceuil.css">
    <link rel="stylesheet" type="text/css" href="css/connexion.css">
    <link rel="stylesheet" type="text/css" href="css/profil.css">
    <link rel="stylesheet" type="text/css" href="css/catalogue.css">
    <link rel="stylesheet" type="text/css" href="css/exploreur.css">
    <link rel="stylesheet" type="text/css" href="css/lecteur.css">
    <link rel="stylesheet" type="text/css" href="css/imageFormat.css">
    <script src="js/client.js"></script>
    <script src="js/storeObject.js"></script>
    <script src="js/titleBarCreation.js"></script>
    <script src="js/modal.js"></script>
    <script src="js/waitForHTMLElementToLoad.js"></script>
    <script src="js/acceuil.js"></script>
    <script src="js/lectureMus/exploreCat.js"></script>


</head>
<body>
<%
    String mesplaylists = (String) request.getAttribute("mesplaylists");
    if(mesplaylists==null){mesplaylists="";}
    CompteClient compteClient = (CompteClient) session.getAttribute("sessionUtilisateur");
    String prenom;
    String nom;
    if(compteClient!=null){
        prenom = compteClient.getPrenom();
        nom = compteClient.getNom();
    }else{ prenom = "intrus"; nom= "inconnu";}
    String resp = "";
    String search = "";
    if(request.getAttribute("musiques")!=null) {
        resp = (String) request.getAttribute("musiques");
        System.out.println("recherche : true");
    }else{
        resp = (String) request.getAttribute("musiquesDefault");
    }
    if(request.getAttribute("search")!=null){
        search = (String) request.getAttribute("search");
    }
    String paraMus ="0";
    int paraPlaylist = -1;
    Boolean lect = false;
    if(request.getAttribute("Musique")!=null) {
        lect = true;
        paraMus = (String) request.getAttribute("Musique");
        System.out.println("paraMus"+paraMus);
        if (request.getAttribute("idPlaylist") != null) {
            paraPlaylist = (int) request.getAttribute("idPlaylist");
        }
    }

%>

<!--Modal : ou page superposée-->

<div id="lecture" class="modal modalHidden">
    <div id="formlecture" class="modal modalNotHidden">
        <div class="modal-content"> <!--Contenu du modal-->
            <div class="close" style="float:right">&times;</div> <!--syntaxe pour le bouton x-->
            <h3 id="TitreMus" style="float:left; padding-left: 3%; padding-right: 3%;"></h3>
            </br>
            <div class="forms">
                <form action="./ExploreCat" method=POST">
                    <input type="hidden" id="hiddenChamp2" name="hiddenChamp2">
                    <input type="hidden" id="hiddenChamp" name="hiddenChamp">
                    <input type="submit" id="Ecouter" value="Ecouter">
                </form>
                <form name="ajoutPlaylist" id="ajoutPlaylist" action="./ExploreCat" method="POST">
                    <input type="hidden" name="hiddenChampBis" id="hiddenChampBis">
                    <label style="padding-left: 3%" for="playList">Choisir une playlist :</label>
                    <select name="playList" id="playList" required="true">
                    </select>
                    <input id="validate" type="submit" value="Valider">
                </form>
            </div>
        </div>
    </div>
</div>
<!--Fin du modal-->
<!--Partie visible du site-->
<div id="gridyHeader">
    <div id="gridyHeaderBeforeSignIn">
        <div id="title"><a href="Acceuil">UsTube</a></div>
        <a href="Profil"><div id="nomPrenomUser" class="profilButtonLayout changeButtonProfilColor"><%=prenom%> <%=nom%></div></a>
        <div id="SignOut" class="buttonLayout changeButtonColor">Se déconnecter</div>
    </div>
</div>

<div class="aside" id="listplaylist">


</div>

<div class="Recherche">
    <form action="${pageContext.request.contextPath}/ExploreCat" method="GET">
        <Label for="searchText">Rechercher : </Label>
        <input id="searchText" type="text" name="searchText" value="<%=search%>">
        <button type="submit">valider</button>
    </form>
</div>

<!--à générer automatiquement plus tard-->
<div class="section">
    <%
        if (search.equals("")) {
            out.println("<h4>Recommandations pour vous :</h4>");
            out.println("<div id=\"gridyRecommendedVideos\"> </div></div>");
        }else{
            out.println("<h4>Résultats de la recherche :</h4>");
            out.println("<div id=\"gridyResultatRecherche\"> </div>");
        }

    %>
</div>
<div class="audio">
    <div class="player">
        <audio id="audioid" controls>
            <source src="https://dl5.webmfiles.org/big-buck-bunny_trailer.webm" type="audio/webm" id="srceId">
        </audio>
        <div class="controls">
            <button class="play"  aria-label="bascule lecture pause"></button>
            <button class="stop"  aria-label="stop"></button>
            <div class="timer">
                <div></div>
                <span aria-label="timer">00:00</span>
                <span aria-label="timer2" id="montitremus" style="padding-left: 5%;"></span>
            </div>
            <button class="rwd"  aria-label="retour prec"></button>
            <button class="fwd" aria-label="avance suivante"></button>
        </div>
    </div>
</div>
<script>
    // Information Pour la lecture des musiques
    var idPlaylist = -1;
    var lect = <%=lect%>;

    console.log(lect);
    let paraMus;
    let paraPlaylist;
    if(lect){
        paraMus =<%=paraMus%>;
        console.log("paraMus : "+paraMus);
        paraPlaylist = <%=paraPlaylist%>;
        console.log("paraPlay : "+paraPlaylist);
    }else{
        paraMus ="0";
        paraPlaylist=-1;
    }

    //les playlists de l'utilisateur
    const playlistArray = <%=mesplaylists%>;

    //les vidéos
    search = <%=!search.equals("")%>;
    const videoArray = <%=resp%>;




</script>

</body>

<footer class="footer">© Copyright 2021 All Rights Reserved.</footer>
</html>