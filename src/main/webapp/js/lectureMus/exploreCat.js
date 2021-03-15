window.addEventListener("load", function () {

    /* Bar de Playlist sur le côté */
    console.log(playlistArray);
    var j;
//On parcourt toutes les playlists de l'utilisateur
    for (j = 0; j < playlistArray.length; j++) {
        let div = document.createElement("div");
        div.id = "d" + playlistArray[j].idPlaylist;
        div.setAttribute("num", j);

        let but = document.createElement("button");
        but.id = "p" + playlistArray[j].idPlaylist;
        but.setAttribute("num", j);
        but.className = "playlist";
        but.textContent = playlistArray[j].titre;
        but.addEventListener("click", displayPlaylist, false);
        //but.addEventListener("click", chooseAction, false);

        div.appendChild(but);

        //On parcourt les musiques associées
        musiqueArray = playlistArray[j].musique;
        var i;
        for (i = 0; i < musiqueArray.length; i++) {
            let item = document.createElement("button");
            item.textContent = musiqueArray[i].titre;
            item.style.display = "none";
            item.className = "playlist sub-list";
            item.id = "p" + musiqueArray[i].id;
            item.setAttribute("num", i);
            item.addEventListener("click", chooseAction, false);
            div.appendChild(item);
        }
        document.getElementById("listplaylist").appendChild(div);

        //Ajoute au modal
        opt = document.createElement("option");
        opt.setAttribute("value", playlistArray[j].idPlaylist);
        opt.setAttribute("selected", "selected");
        opt.textContent = playlistArray[j].titre;
        document.getElementById("playList").appendChild(opt);
    }


//toggle display des musiques d'une playlist
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
    br = document.createElement("br");

//console.log(resp);
    var j = videoArray.length;
    if (j === 0) {
        if (search) {
            let message = document.createElement("p");
            message.textContent = "Aucun contenu sonore n'a été trouvé pour cette recherche.";
            document.getElementById("gridyResultatRecherche").appendChild(message);
        } else {
            let message = document.createElement("p");
            message.textContent = "Aucun contenu sonore n'a été trouvé dans cette section.";
            document.getElementById("gridyRecommendedVideos").appendChild(message);
        }
    }
// parcours de toutes les musiques chargées
    for (j = 0; j < videoArray.length; j++) {
        let div = document.createElement("div");
        let titre;
        if (videoArray[j].titre != null) {
            titre = videoArray[j].titre;
        } else {
            titre = videoArray[j].nom;
        }

        let but = document.createElement("div");
        but.id = videoArray[j].id;
        but.setAttribute("num", j);
        but.addEventListener("click", chooseAction, false);

        var img = document.createElement("img");
        var title = document.createElement("div");
        var nbLect = document.createElement("div");
        var more = document.createElement("div");
        var annee = document.createElement("div");

        img.className = "imageFormat";
        var tp = (j % 10) + 1;
        img.id = "videRec" + tp;
        img.setAttribute("src", "pictures/musicImg" + tp + ".jpg");
        img.setAttribute("alt", "VideoImage");
        but.appendChild(img);

        title.className = "in-button";
        nbLect.className = "in-button";
        more.className = "in-button";
        annee.className = "in-button";
        if (videoArray[j].titre != null && videoArray[j].anneeCreation != null) {
            //musique
            title.textContent = titre;
            nbLect.textContent = videoArray[j].nbLectureTotal + " vues";
            more.textContent = videoArray[j].interprete;
            annee.textContent = videoArray[j].anneeCreation;
            but.appendChild(title);
            but.appendChild(nbLect);
            but.appendChild(more);
            but.appendChild(annee);
        } else if (videoArray[j].titre != null) {
            //podcast
            title.textContent = titre;
            nbLect.textContent = videoArray[j].nbLectureTotal + " vues";
            more.textContent = videoArray[j].auteur;
            but.appendChild(title);
            but.appendChild(nbLect);
            but.appendChild(more);
        } else {
            //radio
            title.textContent = titre;
            nbLect.textContent = videoArray[j].nbLectureTotal + " vues";
            but.appendChild(title);
            but.appendChild(nbLect);

        }
        div.appendChild(but);

        if (search) {
            document.getElementById("gridyResultatRecherche").appendChild(div);
        } else {
            document.getElementById("gridyRecommendedVideos").appendChild(div);
        }
    }

//modal pour permettre de les ecouter
    var modal = document.getElementById("lecture");
    var span = document.getElementsByClassName("close")[0];
    span.onclick = function () {
        modal.style.display = "none";
    }

//fonction event si l'utilisateur clique sur une musique
    function chooseAction(e) {
        var bool2 = false;
        if (e.currentTarget.parentElement.parentElement.id === "listplaylist") {
            bool2 = true;
        }
        console.log(bool2);
        var TidPlaylist = -1;
        var TidMusi;
        if (bool2) {
            TidMusi = parseInt(e.currentTarget.id.slice(1, 10));
        } else {
            TidMusi = e.currentTarget.id;
        }
        var numMusi = e.currentTarget.getAttribute("num");
        console.log(TidMusi + numMusi);
        var numPlaylist = -1;
        if (bool2) {
            TidPlaylist = parseInt(e.currentTarget.parentElement.id.slice(1, 10));
            numPlaylist = e.currentTarget.parentElement.getAttribute("num");
        }
        document.getElementById("hiddenChamp2").setAttribute("value", TidPlaylist);
        document.getElementById("hiddenChamp").setAttribute("value", TidMusi);
        document.getElementById("hiddenChampBis").setAttribute("value", TidMusi);
        TitreMus = document.getElementById("TitreMus");

        if (bool2 === false) {
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
        } else {
            TitreMus.textContent = playlistArray[numPlaylist].titre + " : " + playlistArray[numPlaylist].musique[numMusi].titre;
            document.getElementById("ajoutPlaylist").style.visibility = "hidden";
        }
        modal.style.display = "block";
    }

//----------------LECTURE MUSIQUE-------------------------------------------------------

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

//on enleve le lecteur par défaut pour faire le notre
    media.removeAttribute('controls');

    var dureeMus;
    var numMusique;
    var idPlaylist = -1;
    if (paraMus !== "0") {
        if (paraPlaylist !== -1) {
            idPlaylist = document.getElementById("d" + paraPlaylist).getAttribute("num");
            console.log(idPlaylist);
            playPlaylist();
        } else {
            playMusique();
        }
    }


// on affiche les infos de la musique
    function setInfoMus(j) {
        //title.className ="in-button";
        var tt;
        if (paraMus.titre != null && paraMus.anneeCreation != null) {
            tt = "Musique : " + paraMus.titre;
        } else if (paraMus.titre != null) {
            tt = "Podcast : " + paraMus.titre;
        } else {
            tt = "Radio : " + paraMus.nom;
        }
        document.getElementById("montitremus").textContent = tt;
    }

//idem si on est en train de lire une musique d'une playlist
    function setInfoPlaylist(num) {
        document.getElementById("montitremus").textContent = playlistArray[idPlaylist].musique[num].titre;
    }

//demarrer la lecture d'une musique
    function playMusique() {
        raz = 0;
        idMusique = paraMus.id;
        controls.style.visibility = 'visible';
        media.currentTime = 0;

        setInfoMus(idMusique);
        dureeMus = paraMus.duree;
    }

//demarrer la lecture d'une musique
    function playPlaylist() {
        raz = 0;
        idMusique = paraMus.id;
        numMusique = parseInt(document.getElementById("p" + idMusique).getAttribute("num"));
        controls.style.visibility = 'visible';
        media.currentTime = 0;
        setInfoPlaylist(numMusique);
        dureeMus = paraMus.duree;
    }

// pour changer de musique dans une playlist
    function playMusiqueFunction(numMus) {
        raz = 0;
        numMusique = numMus;
        if (idPlaylist === -1) {
            //setInfoMus(idMusique);
            //dureeMus = videoArray[idMusique].duree;

        } else {
            console.log("inplayfunc");
            setInfoPlaylist(numMusique);
            dureeMus = playlistArray[idPlaylist].musique[numMusique].duree;
            play.id = "play";
            media.currentTime = 0;
            media.play();
        }
        controls.style.visibility = 'visible';
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
    media.addEventListener('ended', stopMedia1);

    function stopMedia() {
        media.pause();
        media.currentTime = 0;
        play.id = "pause";
        raz = 0;
    }

    function stopMedia1() {
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
        } else if (idPlaylist != -1) {
            // si on est dans un playlist et pas dans les cas précédents on passe à la musique suivante
            musAvant();
        } else {
            //sinon on termine la musique
            media.pause();
            media.currentTime = 0;
            play.id = "pause";
            raz = 0;
        }
    }

    rwd.addEventListener('click', musAvant);
    fwd.addEventListener('click', musSuivant);


    function musAvant() {
        //on ne peut naviguer que dans une playlist
        if (idPlaylist != -1 && (numMusique - 1) >= 0) {
            console.log("musAv2");
            playMusiqueFunction(numMusique - 1);
        }
    }


    function musSuivant() {
        //on ne peut naviguer que dans une playlist
        if (idPlaylist != -1 && playlistArray[idPlaylist].musique.length > (numMusique + 1)) {
            console.log("musSuiv2");
            playMusiqueFunction(numMusique + 1);
        }
    }

    media.addEventListener('timeupdate', setTime);

// on adapte le temps et la barre de progres à notre musique
    function setTime() {
        if ((media.duration > dureeMus && media.currentTime >= dureeMus) || raz > dureeMus) {
            // arrete la musique si on a dépassé le temps de la musique
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

        if (dureeMus != null) {
            var barLength = timerWrapper.clientWidth * ((media.currentTime + raz) / dureeMus);
            timerBar.style.width = barLength + 'px';
        }
    }
});