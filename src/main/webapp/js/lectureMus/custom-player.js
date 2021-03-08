window.addEventListener("load", function () {
    var musiques = document.getElementsByClassName("musiques");
    for(var i=0;i<musiques.length;i++){
        musiques[i].addEventListener("click",AffMod,false);
    }

    var modal = document.getElementById("lecture");
    var span = document.getElementsByClassName("close")[1];
    span.onclick = function () { modal.style.display = "none";}


    function AffMod(e){
        modal.style.display = "block";
        document.getElementById("hiddenChamp").setAttribute("value",e.currentTarget.id);
        document.getElementById("TitreMusoc").textContent = e.currentTarget.getAttribute("name");

    }

    var dureeMus = -1;
    var titreMus = "0";
    var media = document.getElementById("audioid");
    var controls = document.querySelector('.controls');

    var play = document.querySelector('.play');
    var stop = document.querySelector('.stop');
    var rwd = document.querySelector('.rwd');
    var fwd = document.querySelector('.fwd');

    var timerWrapper = document.querySelector('.timer');
    var timer = document.querySelector('.timer span');
    var timerBar = document.querySelector('.timer div');

    media.removeAttribute('controls');


    var raz = 0;
    if(lect){
        EcouterMus();
        dureeMus = m.duree;
        titreMus = m.titre;
        console.log("mes elements mus lue : "+m.duree + m.titre);
        document.getElementById("montitremus").textContent = m.titre;
    }



    function EcouterMus(){
        raz = 0;
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
    media.addEventListener('ended', stopMedia1);

    function stopMedia(){
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
        }else {
            //sinon on termine la musique
            media.pause();
            media.currentTime = 0;
            play.id = "pause";
            raz = 0;
        }
    }

    media.addEventListener('timeupdate', setTime);


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

        if(dureeMus!=null) {
            var barLength = timerWrapper.clientWidth * ((media.currentTime+raz) / dureeMus);
            timerBar.style.width = barLength + 'px';
        }
    }

})
