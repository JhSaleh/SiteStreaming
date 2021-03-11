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
<div id="gridyHeader">
    <div id="gridyHeaderBeforeSignIn">
        <div id="title"><a href="Acceuil">UsTube</a></div>
        <a href="Profil"><div id="nomPrenomUser" class="profilButtonLayout changeButtonProfilColor"><%=prenom%> <%=nom%></div></a>
        <a href="${pageContext.request.contextPath}/LogOut"><div id="SignOut" class="buttonLayout changeButtonColor">Se déconnecter</div></a>
    </div>
</div>


<div class="sectionPl">
    <div class="aside asidePl" id="listplaylist"></div>
    <div class="aside-right">
        <button onclick="dispNouv()" id="mynouvplay">Nouvelle playlist</button>
    <form action="${pageContext.request.contextPath}/ModifierPlaylist" method=POST"  onsubmit="return validateForm()" id="PlaylistNouv">
        <input type="text" name="NomPlaylist" id="NomPlaylist">
        <input id="validate" type="submit" value="Valider">
    </form>
    </br>
        <h2>Playlist : </h2>
<h3 id="h2playlist"></h3>
    <form action="${pageContext.request.contextPath}/ModifierPlaylist" method="Post" onsubmit="return validateForm1()"id="PlaylistRenom">
        <input type="hidden" name="numPlaylistRenom" id="numPlaylistRenom">
        <input type="text" name="nouvNom" id="nouvNom">
        <button class="valider" type="submit">Renommer la playlist</button>
    </form>
    </br>
        <form action="${pageContext.request.contextPath}/ModifierPlaylist" method="Post"  id="PlaylistSupp">
            <input type="hidden" name="numPlaylistSupp" id="numPlaylistSupp">
            <button class="validerR" type="submit">Supprimer la playlist</button>
        </form>
    </div>
</div>



<script>
    var nouv = document.getElementById("PlaylistNouv");
    var sup = document.getElementById("PlaylistSupp");
    var renom = document.getElementById("PlaylistRenom");
    nouv.style.display = "none";
    sup.style.display = "none";
    renom.style.display = "none";

    function dispNouv(){
        if (nouv.style.display === "none") {
           nouv.style.display = "block";
        } else {
            nouv.style.display = "none";
        }
    }
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
            div.appendChild(item);
        }
        document.getElementById("listplaylist").appendChild(div);
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

        var idPlay = e.currentTarget.id.slice(1,10);
        var hidden1 = document.getElementById("numPlaylistRenom");
        var hidden2 = document.getElementById("numPlaylistSupp");
        var h2p = document.getElementById("h2playlist");

            sup.style.display = "block";
            renom.style.display = "block";
            hidden1.value = idPlay;
            hidden2.value = idPlay;
            var x = document.getElementById("nouvNom").value =childs[0].textContent;
            h2p.textContent = childs[0].textContent;

    }

    function validateForm() {
        var x = document.getElementById("NomPlaylist").value;
        if (x == "") {
            alert("Choisir un nom pour la playlist");
            return false;
        }
    }
    function validateForm1() {
        var x = document.getElementById("nouvNom").value;
        if (x == "") {
            alert("Choisir  nouveau nom pour la playlist");
            return false;
        }
    }
</script>

</body>

<footer class="footer">© Copyright 2021 All Rights Reserved.</footer>
</html>