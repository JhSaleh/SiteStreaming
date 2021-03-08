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
    if(compteClient!=null){ prenom = compteClient.getPrenom();}else{ prenom = "intrus";}
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
                    <label syle="padding-left: 3%" for="playList">Choisir une playlist :</label>
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
        <a href="Profil"><div id="nomPrenomUser" class="profilButtonLayout changeButtonProfilColor"><%=prenom%></div></a>
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
        <div id="info"></div>
        <div class="controls">
            <button class="play"  aria-label="bascule lecture pause"></button>
            <button class="stop"  aria-label="stop"></button>
            <div class="timer">
                <div></div>
                <span aria-label="timer">00:00</span>
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
        div.id = playlistArray[j].idPlaylist+"Div";
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
            item.id=musiqueArray[i].id;
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

    /* Vidéos sur la pages  */
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


        let but = document.createElement("button");
        but.id =videoArray[j].id;
        but.setAttribute("num",j);
        but.addEventListener("click", chooseAction, false);


        var title = document.createElement("p");
        var nbLect = document.createElement("p");
        var more = document.createElement("p");
        var genre = document.createElement("p");
        title.className ="in-button";
        nbLect.className ="in-button";
        more.className ="in-button";
        genre.className ="in-button";
        if(videoArray[j].titre!=null && videoArray[j].anneeCreation!=null) {
            but.textContent = "Musique";
            title.textContent = titre;
            nbLect.textContent = videoArray[j].nbLectureTotal+" vues" ;
            more.textContent = videoArray[j].interprete + " -- " + videoArray[j].anneeCreation ;
            genre.textContent =  videoArray[j].genreMusical;
            but.appendChild(title);
            but.appendChild(nbLect);
            but.appendChild(more);
            but.appendChild(genre);
        }else if(videoArray[j].titre!=null){
            but.textContent = "Podcast";
            title.textContent = titre;
            nbLect.textContent = videoArray[j].nbLectureTotal+" vues" ;
            more.textContent = videoArray[j].auteur;
            genre.textContent = videoArray[j].categorie ;
            but.appendChild(title);
            but.appendChild(nbLect);
            but.appendChild(more);
            but.appendChild(genre);
        }else{
            but.textContent = "Radio";
            title.textContent = titre;
            nbLect.textContent = videoArray[j].nbLectureTotal+" vues" ;
            genre.textContent = videoArray[j].genreMusical ;
            but.appendChild(title);
            but.appendChild(nbLect);
            but.appendChild(genre);

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
        var TidMusi = e.currentTarget.id;
        var numMusi = e.currentTarget.getAttribute("num");
        console.log(TidMusi+numMusi);
        var numPlaylist=-1;
        if(bool2){
            TidPlaylist = parseInt(e.currentTarget.parentElement.id.slice(1), 10);
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
    }else{
         paraMus ="0";
         paraPlaylist=-1;
    }


        if (paraMus !== "0") {
            if (paraPlaylist !== -1) {
                idPlaylist = getNumPlaylist(paraPlaylist);
                playPlaylist();
            } else {
                playMusique();
            }
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

    var dureeMus;
    const isPlaylist = (idPlaylist!==-1);
    var info = document.getElementById("info");


    function getNumPlaylist(idPlaylist){
        playlistEl = document.getElementById("p"+idPlaylist);
        return playlistEl.num;
    }

    function setInfoMus(j) {
        var title = document.createElement("p");
        //title.className ="in-button";
        if (paraMus.titre != null && paraMus.anneeCreation != null) {
            info.textContent = "Musique";
            title.textContent = paraMus.titre + " -- " + paraMus.duree;
        } else if (paraMus.titre != null) {
            info.textContent = "Podcast";
            title.textContent = paraMus.titre + " -- " + paraMus.duree;
        } else {
            info.textContent = "Radio";
            title.textContent = paraMus.nom;
        }
        info.appendChild(title);
    }

    function setInfoPlaylist(j,i) {
        var title = document.createElement("p");
        //title.className ="in-button";
        if (paraMus.titre != null && paraMus.anneeCreation != null) {
            info.textContent = "Musique";
            title.textContent = paraMus.titre + " -- " + paraMus.duree;
        }
        info.appendChild(title);
    }

      function playMusique() {
          raz = 0;
          idMusique = paraMus.id;
          controls.style.visibility = 'visible';
          media.currentTime = 0;

          setInfoMus(idMusique);
          console.log("here idmus: " + idMusique);
          dureeMus = paraMus.duree;
      }

    function playPlaylist() {
        raz = 0;
        idMusique = paraMus.id;
        controls.style.visibility = 'visible';
        media.currentTime = 0;
        setInfoPlaylist(idMusique);
        dureeMus = paraMus.duree;
    }


    function playMusiqueFunction(idMus) {
        raz = 0;
        idMusique = idMus;
        if (isPlaylist === false) {
            //setInfoMus(idMusique);
             //dureeMus = videoArray[idMusique].duree;

        } else {
            setInfoPlaylist(idMusique);
            dureeMus = playlistArray[idPlaylist].musique[idMusique].duree;
        }
        controls.style.visibility = 'visible';
        media.currentTime = 0;
    }


    play.addEventListener('click', playPauseMedia);

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
            media.play();
        } else {
            play.id = "pause";
            media.pause();
        }
    }

    stop.addEventListener('click', stopMedia);
    media.addEventListener('ended', stopMedia);

    function stopMedia() {
        if (dureeMus === null) {
            //si c'est une radio on tourne en boucle
            raz = raz + media.currentTime;
            media.currentTime = 0;
            media.play();
        } else if (media.duration < dureeMus && raz < dureeMus) {
            //si la musique à encore du temps on reprend la musique
            raz = raz + media.duration;
            media.currentTime = 0;
            media.play();
        } else if (isPlaylist === true) {
            // si on est dans un playlist et pas dans les cas précédents on pass à la musique suivante
            musAvant();
        } else {
            //sinon on termine la musique
            media.pause();
            media.currentTime = 0;
            play.id = "pause";
            raz = 0;
        }
    }

    rwd.addEventListener('click', musSuivant);
    fwd.addEventListener('click', musAvant);

    var intervalFwd;
    var intervalRwd;


    function musAvant() {
       // if (isPlaylist === false && videoArray[idMusique - 1] != null) {
           // playMusiqueFunction(idMusique - 1)
       // }
        //on ne peut naviguer que dans une playlist
        if (isPlaylist === true && playlistArray[idPlaylist].musique[idMus - 1].id != null) {
            playMusiqueFunction(idMusique - 1);
        }
    }


    function musSuivant() {
       // if (isPlaylist === false && videoArray[idMusique + 1] != null) {
            //playMusiqueFunction(idMusique + 1)
        //}
        //on ne peut naviguer que dans une playlist
        if (isPlaylist === true && playlistArray[idPlaylist].musique[idMus + 1].id != null) {
            playMusiqueFunction(idMusique + 1);
        }
    }

    media.addEventListener('timeupdate', setTime);


    function setTime() {
        if ((media.duration > dureeMus && media.currentTime >= dureeMus) || raz > dureeMus) {
            // arrete la musique si on a dépassé le temps de la musqieu
            stopMedia();
        }

        var minutes = Math.floor((media.currentTime + raz) / 60);
        var seconds = Math.floor((media.currentTime + raz) - minutes * 60);
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

        var mediaTime = minuteValue + ':' + secondValue;
        timer.textContent = mediaTime;

        if(dureeMus!=null) {
            var barLength = timerWrapper.clientWidth * ((media.currentTime+raz) / dureeMus);
            timerBar.style.width = barLength + 'px';
        }
    }


</script>

</body>

<footer class="footer">© Copyright 2021 All Rights Reserved.</footer>
</html>