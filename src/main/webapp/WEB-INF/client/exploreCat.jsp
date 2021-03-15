<%@ page import="com.siteStreaming.SiteStreaming.Accueil.CompteClient" %>
<%@ page import="com.siteStreaming.SiteStreaming.LoggerSite" %><%--
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
    <link rel="stylesheet" type="text/css" href="css/stars.css">

    <script src="js/client.js"></script>
    <script src="js/storeObject.js"></script>
    <script src="js/titleBarCreation.js"></script>
    <script src="js/modal.js"></script>
    <script src="js/waitForHTMLElementToLoad.js"></script>
    <script src="js/acceuil.js"></script>

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
        LoggerSite.logger.debug("recherche : true");
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
        LoggerSite.logger.debug("paraMus"+paraMus);
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
                    <input type="submit" id="Ecouter" class="buttonLayoutC" value="Ecouter">
                </form>
                <form name="ajoutPlaylist" id="ajoutPlaylist" action="./ExploreCat" method="POST">
                    <input type="hidden" name="hiddenChampBis" id="hiddenChampBis">
                    <label style="padding-left: 3%" for="playList">Ajouter à une playlist :</label>
                    <select name="playList" id="playList" required="true">
                    </select>
                    <input id="validate" type="submit" class="buttonLayoutC" value="Valider">
                </form>
            </div>
        </div>
    </div>
</div>
<!--Fin du modal-->
<!--Partie visible du site-->
<div id="gridyHeader">
    <div id="gridyHeaderBeforeSignIn">
        <div id="title"><a href="Accueil">UsTube</a></div>
        <a href="Profil"><div id="nomPrenomUser" class="profilButtonLayout changeButtonProfilColor"><%=prenom%> <%=nom%></div></a>
        <a href="${pageContext.request.contextPath}/LogOut"><div id="SignOut" class="buttonLayout changeButtonColor">Se déconnecter</div></a>
    </div>
</div>

<div class="aside" id="listplaylist">
    <a href="./ModifierPlaylist"><button class="valider centred">Gerer mes playlists</button></a>
<h3 id="textcenter">Mes playlists :</h3>
</div>

<div class="Recherche">
    <form action="${pageContext.request.contextPath}/ExploreCat" method="GET">
        <Label for="searchText">Rechercher : </Label>
        <input id="searchText" type="text" class="labelStyle" name="searchText" value="<%=search%>">
        <button type="submit" class="buttonLayoutC">valider</button>
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
    /* Bar de Playlist sur le côté */
    //console.log(<%=resp%>);

    const playlistArray = <%=mesplaylists%>;
    console.log(playlistArray);
    var j;
    for(j=0;j<playlistArray.length;j++){
        let div = document.createElement("div");
        div.id = "d"+playlistArray[j].idPlaylist;
        div.setAttribute("num",j);

        let but = document.createElement("button");
        but.id = "p"+playlistArray[j].idPlaylist;
        but.setAttribute("num",j);
        but.className = "playlist";
        but.textContent =playlistArray[j].titre;
        but.addEventListener("click", displayPlaylist, false);
        //but.addEventListener("click", chooseAction, false);

        div.appendChild(but);

        musiqueArray = playlistArray[j].musique;
        var i;
        for(i= 0;i<musiqueArray.length;i++){
            let item = document.createElement("button");
            item.textContent =musiqueArray[i].titre;
            item.style.display = "none";
            item.className = "playlist sub-list";
            item.id="p"+musiqueArray[i].id;
            item.setAttribute("num",i);
            item.addEventListener("click",chooseAction,false);
            div.appendChild(item);
        }
        document.getElementById("listplaylist").appendChild(div);

        //Ajoute au modal
        opt = document.createElement("option");
        opt.setAttribute("value",playlistArray[j].idPlaylist);
        opt.setAttribute("selected","selected");
        opt.textContent = playlistArray[j].titre;
        document.getElementById("playList").appendChild(opt);
    }


    function displayPlaylist(e) {
        thisObject = e.currentTarget.parentElement;
        var childs = thisObject.childNodes;
        var i;
        for (i = 1; i < childs.length; i++) {
            //console.log(childs[i]);
            if (childs[i].style.display === "none") {
                childs[i].style.display = "block";
            } else {
                childs[i].style.display = "none";
            }
        }
    }

    /* Vidéos sur la page  */
    br =document.createElement("br");
    search = <%=!search.equals("")%>;
    console.log(search);

    const videoArray = <%=resp%>;
    //console.log(resp);
    var j = videoArray.length;
    if(j===0){
        if(search){
            let message = document.createElement("p");
            message.textContent = "Aucun contenu sonore n'a été trouvé pour cette recherche.";
            document.getElementById("gridyResultatRecherche").appendChild(message);
        }else{
            let message = document.createElement("p");
            message.textContent = "Aucun contenu sonore n'a été trouvé dans cette section.";
            document.getElementById("gridyRecommendedVideos").appendChild(message);
        }
    }
    for(j=0;j<videoArray.length;j++){
        let div = document.createElement("div");
        let titre;
        if(videoArray[j].titre!=null){
            titre = videoArray[j].titre;
        }else{
            titre = videoArray[j].nom;
        }


        let but = document.createElement("div");
        but.id =videoArray[j].id;
        but.setAttribute("num",j);
        but.addEventListener("click", chooseAction, false);

        var img = document.createElement("img");
        var title = document.createElement("div");
        var nbLect = document.createElement("div");
        var more = document.createElement("div");
        var annee = document.createElement("div");

        img.className = "imageFormat";
        var tp = (j%10)+1;
        img.id = "videRec"+tp;
        img.setAttribute("src","pictures/musicImg"+tp+".jpg");
        img.setAttribute("alt","VideoImage");
        but.appendChild(img);

        title.className ="in-button";
        nbLect.className ="in-button";
        more.className ="in-button";
        annee.className = "in-button";
        if(videoArray[j].titre!=null && videoArray[j].anneeCreation!=null) {
            //musique
            title.textContent = titre;
            nbLect.textContent = videoArray[j].nbLectureTotal+" vues" ;
            more.textContent = videoArray[j].interprete;
            annee.textContent =videoArray[j].anneeCreation;
            but.appendChild(title);
            but.appendChild(nbLect);
            but.appendChild(more);
            but.appendChild(annee);
        }else if(videoArray[j].titre!=null){
            //podcast
            title.textContent = titre;
            nbLect.textContent = videoArray[j].nbLectureTotal+" vues" ;
            more.textContent = videoArray[j].auteur;
            but.appendChild(title);
            but.appendChild(nbLect);
            but.appendChild(more);
        }else{
            //radio
            title.textContent = titre;
            nbLect.textContent = videoArray[j].nbLectureTotal+" vues" ;
            but.appendChild(title);
            but.appendChild(nbLect);

        }
        div.appendChild(but);

        if(search) {
            document.getElementById("gridyResultatRecherche").appendChild(div);
        }else{
            document.getElementById("gridyRecommendedVideos").appendChild(div);
        }
    }
    var modal = document.getElementById("lecture");
    var span = document.getElementsByClassName("close")[0];
    span.onclick = function () { modal.style.display = "none";}

    function chooseAction(e)
    {
        var bool2=false;
        if(e.currentTarget.parentElement.parentElement.id==="listplaylist"){
            bool2 =true;
        }
        console.log(bool2);
        var TidPlaylist =-1;
        var TidMusi;
        if(bool2){
            TidMusi = parseInt(e.currentTarget.id.slice(1,10));
        }else{
            TidMusi = e.currentTarget.id;
        }
        var numMusi = e.currentTarget.getAttribute("num");
        console.log(TidMusi+numMusi);
        var numPlaylist=-1;
        if(bool2){
            TidPlaylist = parseInt(e.currentTarget.parentElement.id.slice(1,10));
            numPlaylist = e.currentTarget.parentElement.getAttribute("num");
        }
        document.getElementById("hiddenChamp2").setAttribute("value",TidPlaylist);
        document.getElementById("hiddenChamp").setAttribute("value",TidMusi);
        document.getElementById("hiddenChampBis").setAttribute("value",TidMusi);
        TitreMus = document.getElementById("TitreMus");

        if(bool2===false) {
            if (videoArray[numMusi].titre != null) {
                TitreMus.textContent = videoArray[numMusi].titre;
                if (videoArray[numMusi].anneeCreation != null) {
                    document.getElementById("ajoutPlaylist").style.visibility = "visible";
                } else {
                    document.getElementById("ajoutPlaylist").style.visibility = "hidden";
                }
            } else {
                TitreMus.textContent = videoArray[numMusi].nom;
                document.getElementById("ajoutPlaylist").style.visibility = "hidden";
            }
        }else{
            TitreMus.textContent = playlistArray[numPlaylist].titre + " : "+playlistArray[numPlaylist].musique[numMusi].titre;
            document.getElementById("ajoutPlaylist").style.visibility = "hidden";
        }
        modal.style.display = "block";
    }

    console.log("herehere");
    //----------------LECTURE MUSIQUE-------------------------------------------------------
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
    var media = document.getElementById("audioid");
    var controls = document.querySelector('.controls');

    var play = document.querySelector('.play');
    var stop = document.querySelector('.stop');
    var rwd = document.querySelector('.rwd');
    var fwd = document.querySelector('.fwd');

    var timerWrapper = document.querySelector('.timer');
    var timer = document.querySelector('.timer span');
    var timerBar = document.querySelector('.timer div');

    var raz = 0;


    media.removeAttribute('controls');
    controls.style.visibility = 'hidden';


    var dureeMus;
    var numMusique;
    var idPlaylist =-1;
    if (paraMus !== "0") {
        if (paraPlaylist !== -1) {
            idPlaylist = document.getElementById("d"+paraPlaylist).getAttribute("num");
            console.log(idPlaylist);
            playPlaylist();
        } else {
            playMusique();
        }
    }




    function setInfoMus(j) {
        //title.className ="in-button";
        var minutes = Math.floor(dureeMus / 60);
        var seconds = Math.floor(dureeMus - minutes * 60);
        var tt;
        if (paraMus.titre != null && paraMus.anneeCreation != null) {
            tt= "/"+convert(minutes,seconds)+" Musique : "+paraMus.titre;
        } else if (paraMus.titre != null) {
            tt = "/"+convert(minutes,seconds)+" Podcast : "+paraMus.titre;
        } else {
            tt="Radio : " +paraMus.nom;
        }
        document.getElementById("montitremus").textContent = tt;
    }

    function setInfoPlaylist(num) {
        var minutes = Math.floor(dureeMus / 60);
        var seconds = Math.floor(dureeMus - minutes * 60);
        document.getElementById("montitremus").textContent = "/"+convert(minutes,seconds) +" "+playlistArray[idPlaylist].musique[num].titre;
    }

      function playMusique() {
          raz = 0;
          idMusique = paraMus.id;
          controls.style.visibility = 'visible';
          media.currentTime = 0;
          dureeMus = paraMus.duree;
          setInfoMus(idMusique);
      }

    function playPlaylist() {
        raz = 0;
        idMusique = paraMus.id;
        numMusique = parseInt(document.getElementById("p" + idMusique).getAttribute("num"));
        controls.style.visibility = 'visible';
        media.currentTime = 0;
        dureeMus = paraMus.duree;
        setInfoPlaylist(numMusique);
    }


    function playMusiqueFunction(numMus) {
        raz = 0;
        numMusique =numMus;
        if (idPlaylist === -1) {
            //setInfoMus(idMusique);
             //dureeMus = videoArray[idMusique].duree;

        } else {
            console.log("inplayfunc");
            dureeMus = playlistArray[idPlaylist].musique[numMusique].duree;
            setInfoPlaylist(numMusique);
            play.id = "play";
            media.currentTime = 0;
            media.play();
        }
        controls.style.visibility = 'visible';
    }


    play.addEventListener('click', playPauseMedia);
    play.style.backgroundImage = "url( pictures/playButton.svg )";

    rwd.id = "avance";
    fwd.id = "recule";

    stopchild = document.createElement("canvas");
    stopchild.id = "stopchild";
    stop.appendChild(stopchild);
    stop.id = "stop";
    play.id = "pause";


    function playPauseMedia() {
        if (media.paused) {
            play.id = "play";
            play.style.backgroundImage = "url( pictures/pauseButton.svg )";
            media.play();
        } else {
            play.id = "pause";
            play.style.backgroundImage = "url( pictures/playButton.svg )";
            media.pause();
        }
    }

    stop.addEventListener('click', stopMedia);
    media.addEventListener('ended', stopMedia1);

    function stopMedia(){
        media.pause();
        media.currentTime = 0;
        play.id = "pause";
        play.style.backgroundImage = "url( pictures/playButton.svg )";
        raz = 0;
    }

    function stopMedia1() {
        if (paraMus.nom != null) {
            //si c'est une radio on tourne en boucle
            raz = raz + media.currentTime;
            media.currentTime = 0;
            media.play();
            console.log("radio");
        }else if (media.duration < dureeMus && raz < dureeMus) {
            //si la musique à encore du temps on reprend la musique
            raz = raz + media.duration;
            media.currentTime = 0;
            media.play();
            console.log("boucle");
        } else if (idPlaylist !=-1) {
            // si on est dans un playlist et pas dans les cas précédents on pass à la musique suivante
            musAvant();
        } else {
            //sinon on termine la musique
            media.pause();
            media.currentTime = 0;
            play.id = "pause";
            raz = 0;
            play.style.backgroundImage = "url( pictures/playButton.svg )";
        }
    }

    rwd.addEventListener('click', musAvant);
    fwd.addEventListener('click', musSuivant);


    rwd.style.backgroundImage = "url( pictures/playButton.svg )";
    fwd.style.backgroundImage = "url( pictures/playButton.svg )";


    function musAvant() {
        //on ne peut naviguer que dans une playlist
        if (idPlaylist!=-1 && (numMusique - 1)>=0) {
            console.log("musAv2");
            playMusiqueFunction(numMusique - 1);
        }
    }


    function musSuivant() {
        //on ne peut naviguer que dans une playlist
        if (idPlaylist!=-1 && playlistArray[idPlaylist].musique.length > (numMusique + 1)){
            console.log("musSuiv2");
            playMusiqueFunction(numMusique + 1);
        }
    }

    media.addEventListener('timeupdate', setTime);

    function convert(minutes,seconds){
        var minuteValue;
        var secondValue;

        if (minutes < 10) {
            minuteValue = '0' + minutes;
        } else {
            minuteValue = minutes;
        }
        if (seconds < 10) {
            secondValue = '0' + seconds;
        } else {
            secondValue = seconds;
        }
        return minuteValue + ':' + secondValue;
    }

    function setTime() {
        if ((media.currentTime +raz) >= dureeMus) {
            // arrete la musique si on a dépassé le temps de la musique
            stopMedia();
        }

        var minutes = Math.floor((media.currentTime + raz) / 60);
        var seconds = Math.floor((media.currentTime + raz) - minutes * 60);

        timer.textContent =convert(minutes,seconds);

        if(dureeMus!=null) {
            var barLength = timerWrapper.clientWidth * ((media.currentTime+raz) / dureeMus);
            timerBar.style.width = barLength + 'px';
        }
    }


</script>


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