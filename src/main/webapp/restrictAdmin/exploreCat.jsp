<%@ page import="com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Musique" %>
<%@ page import="java.util.List" %>
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
    <%
        String mesplaylists = (String) request.getAttribute("mesplaylists");
        CompteClient compteClient = (CompteClient) session.getAttribute("sessionUtilisateur");
        String prenom;
        if(compteClient!=null){ prenom = compteClient.getPrenom();}else{ prenom = "intrus";}
        String defaultValue = "VideoImage";
        String defaultValueTitle = "VideoTitle";
        String defaultValueViews = "-9999";
        String defaultValueYear = "01/02/2021";

    %>
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

<div class="section">
    <form id="mysearch">
        <input type="text" name="searchText">
        <button type="submit" onclick="fetchJSON()">valider</button>
    </form>
</div>

<!--à générer automatiquement plus tard-->
<div class="section">
    <h4>Recommandations du moment :</h4>
    <div id="gridyRecommendedVideos">
        <%
            String videoRec = "";
            String titleRec = "";
            String nbViewsRec = "";
            String yearRec = "";
            for(int i = 1; i<6; i++){
                videoRec = "vidRec" + Integer.toString(i);
                titleRec = "titleRec" + Integer.toString(i);
                nbViewsRec = "nbViewsRec" + Integer.toString(i);
                yearRec = "yearRec" + Integer.toString(i);

        %>

        <div id = <%=videoRec%>><%=defaultValue %></div>
        <div id = <%=titleRec%>><%=defaultValueTitle%></div>
        <div id = <%=nbViewsRec%>><%=defaultValueViews%></div>
        <div id = <%=yearRec%>><%=defaultValueYear%></div>

        <%
            }
        %>
    </div>
</div>



<div class="section">
    <h4>Morceaux Populaires :</h4>
    <div id = "gridyPopularVideos">
        <%
            String videoPop = "";
            String titlePop = "";
            String nbViewsPop = "";
            String yearPop = "";
            String playPop = "";
            for(int i = 1; i<6; i++){
                videoPop = "vidPop" + Integer.toString(i);
                titlePop = "titlePop" + Integer.toString(i);
                nbViewsPop = "nbViewsPop" + Integer.toString(i);
                yearPop = "yearPop" + Integer.toString(i);
                playPop = "playPop" + Integer.toString(i);
        %>

        <div id = <%=videoPop%>><%=defaultValue%></div>
        <div id = <%=titlePop%>><%=defaultValueTitle%></div>
        <div id = <%=nbViewsPop%>><%=defaultValueViews%></div>
        <div id = <%=yearPop%>><%=defaultValueYear%></div>
        <div id = <%=playPop%>>BoutonPlay</div>
        <%
            }
        %>
    </div>
</div>


<script>
    mesplaylist = <%=mesplaylists%>;
    const playlistArray = mesplaylist;
    console.log(playlistArray);
    var j;
    for(j=0;j<playlistArray.length;j++){
        let div = document.createElement("div");
        div.id = playlistArray[j].titre+"Div";

        let but = document.createElement("button");
        but.id = playlistArray[j].titre;
        but.class = "titre";
        var str = playlistArray[j].titre;
        but.textContent =playlistArray[j].titre;
        but.addEventListener("click", () => { displayPlaylist(str); }, false);
        div.appendChild(but);

        musiqueArray = playlistArray[j].musique;
        var i;
        for(i= 0;i<musiqueArray.length;i++){
            let item = document.createElement("button");
            item.textContent =musiqueArray[i].titre;
            item.style.display = "none";
            item.class = playlistArray[j].titre;
            div.appendChild(item);
        }
        document.getElementById("listplaylist").appendChild(div);

    }


    function displayPlaylist(id) {
        thisObject = document.getElementById(id+"Div");
        console.log(id);
        var childs = thisObject.childNodes;
        var i;
        for (i = 1; i < childs.length; i++) {
            console.log(childs[i]);
            if (childs[i].style.display === "none") {
                childs[i].style.display = "block";
            } else {
                childs[i].style.display = "none";
            }
        }
    }



    async function fetchJSON() {
        oForm = document.forms[0];
        value = oForm.elements["searchtext"].value;
        const data = 'searchtext='+value;
        const response = await
            fetch("/AjaxHandler", {
                method: 'post',
                headers: {'Content-Type':'application/x-www-form-urlencoded'},
                body: data,
            });
        let rep = JSON.parse(await response.text());
        console.log(rep);
    }
</script>
</body>

<footer class="footer">© Copyright 2021 All Rights Reserved.</footer>
</html>