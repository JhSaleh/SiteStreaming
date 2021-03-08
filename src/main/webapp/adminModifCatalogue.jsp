<%@ page import="com.siteStreaming.SiteStreaming.DataBase.CatalogueDatabase" %>
<%@ page import="com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.ContenuSonore" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: rkbcht
  Date: 3/7/2021
  Time: 4:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CatalogueDatabase catalogue = (CatalogueDatabase) request.getAttribute("compteInscription"); //Récupération du compte qu'on a essayé d'inscrire
%>
<html>
<head>
    <title>Modification Catalogue</title>
    <link rel="stylesheet" type="text/css" href="../css/administration.css">
    <link rel="stylesheet" type="text/css" href="../css/acceuil.css">

    <script src="../js/adminModifCatalogueGridy.js"></script>
    <script>
        function initMain() {

                function acceuilCheckEventTypingMailConnection() {
                    var CreerBtn = document.getElementById("ADM_creer");
                    var SuppBtn = document.getElementById("ADM_supprimer");
                    var ModBtn = document.getElementById("ADM_modifier");
                    console.log("Recupération des elements du .jsp");

                    function hideElement(id) {
                        document.getElementById(id).hidden = true;
                        console.log("élément d'id : " + id + " caché");
                    }


                    /**
                     * Bind l'évènement de taper sur le champs au fait de cacher le msg de status
                     */
                    CreerBtn.addEventListener("click", function () {
                        hideElement("gridyHeaderInscriptionID");
                    })
                }
                acceuilCheckEventTypingMailConnection();
        }
    </script>
</head>
<body onload="initMain()">
    <div id = "gridyHeaderInscriptionID" class = "gridyHeaderInscription">
        <div id ="title"><a href="/SiteStreaming_war/Acceuil">UsTube</a></div>
        <div id = "inscriptionTitle">Modification Catalogue :</div>
    </div>

    <div class="gridyBody_CatADM">
        <div id = "choixActionTypeID">
            <div id = "gridyChoixActionCatalogueID" class = "gridyChoixActionCatalogue">
                <input id="ADM_creer" type="submit" value="Créer un élément">
                <input id="ADM_supprimer" type="submit" value="Supprimer un élément">
                <input id="ADM_modifier" type="submit" value="Modifier un élément">
            </div>

            <div id = "gridyChoixTypeID" class = "gridyChoixType">
                <input id="ADM_cPodcast" type="submit" value="Podcast">
                <input id="ADM_cRadio" type="submit" value="Radio">
                <input id="ADM_cMusique" type="submit" value="Musique">
                <input id="ADM_cRetour" type="submit" value="Retour">
                <input id="ADM_cValider" type="submit" value="Valider">
            </div>

        </div>
        <div id ="UtilisationActionTypeID">
            <div id="gridyModMusID" class ="gridyModMus">
                <form action="ModificationCatalogue" method="POST">
                    <select id = "ADM_modmustypeSearch" class="labelStyle" size="1" name="searchType" required>
                        <option>Nom</option>
                        <option>ID</option>
                    </select>
                    <label id="ADM_modmusSearchL" for="ADM_modmusSearch">Recherche :</label>
                    <input id="ADM_modmusSearch" class="labelStyle" type="text" name="ADM_modmustypeSearchL" required>
                    <input id="ADM_modmusValidateSearch" type="submit" value="Valider">
                </form>

                <div id="ADM_modmusResults" >

                <%
                    int nbRes = 0;
                    List<ContenuSonore> listCont = (List<ContenuSonore>) request.getAttribute("topNmusique");
                    if (listCont != null){
                        nbRes = listCont.size();
                    }else{
                        System.out.println("listCont is null");
                    }

                %>
                <%
                    if (nbRes > 0) {
                        if (nbRes > 1){
                %>

                liste des titres (<%=nbRes %> résultats):

                <%

                }else{

                %>

                <%=nbRes %> livre trouvé :

                <%

                    }

                %>

                <table border="3">

                    <tr>

                        <th>ID</th>

                        <th>Nom</th>

                    </tr>

                    <%

                        for (int i = 0; i < listCont.size(); i++) {

                            String contenu = listCont.get(i).getContenu();

                            int ID = listCont.get(i).getId();

                    %>

                    <tr>

                        <td><%=ID %></td>

                        <td><%=contenu %></td>

                    </tr>

                    <%

                        }

                    %>

                </table>

                <%

                }else{

                %>

                aucun titre ne correspond au champ entré

                <%

                    }

                %>
                </div>

                <form action="ModificationCatalogue" method="POST">

                    <label id="ADM_modmusSelectedElemL" for="ADM_modmusSelectedElem">Entrer ID choisi</label>
                    <input id="ADM_modmusSelectedElem" class="labelStyle" type="text" name="ADM_modmusSelectedElem" required>
                    <input id="sendID" type="submit">

                </form>





            </div>

        </div>
    </div>

</body>
</html>
