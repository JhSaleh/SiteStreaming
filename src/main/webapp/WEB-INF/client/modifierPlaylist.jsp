<%@ page import="com.siteStreaming.SiteStreaming.Acceuil.CompteClient" %><%--
  Created by IntelliJ IDEA.
  User: declerck
  Date: 09/03/2021
  Time: 07:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

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
    String nom;
    if(compteClient!=null){
        prenom = compteClient.getPrenom();
        nom = compteClient.getNom();
    }else{ prenom = "intrus"; nom= "inconnu";}
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



<div class="Recherche">
    <form action="${pageContext.request.contextPath}/ExploreCat" method="GET">
        <Label for="searchText">Rechercher : </Label>
        <input id="searchText" type="text" name="searchText" value=>
        <button type="submit">valider</button>
    </form>
</div>



<script>
    /* Bar de Playlist sur le côté */


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


    var modal = document.getElementById("lecture");
    var span = document.getElementsByClassName("close")[0];
    span.onclick = function () { modal.style.display = "none";}





</script>

</body>

<footer class="footer">© Copyright 2021 All Rights Reserved.</footer>
</html>