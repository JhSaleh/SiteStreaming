<%@ page import="com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.ContenuSonore" %>
<%@ page import="java.util.List" %>
<%@ page import="com.siteStreaming.SiteStreaming.LoggerSite" %><%--
  Created by IntelliJ IDEA.
  User: rkbcht
  Date: 3/6/2021
  Time: 12:17 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Page Admin</title>
</head>
<body>


    <p>Bienvenue dans la page d'administration</p>

    <form action="AdminWeb" method="POST">
        <div>
            <label for="nom">Entrez un nom :</label>
            <input type="text" id="nomtest" name="nom">
        </div>
        <div>
            <label for="action">Entrez une action :</label>
            <input type="text" id="action" name="actionEf">
        </div>
        <div class="button">
            <button type="submit">Envoyer</button>
        </div>
    </form>

    <p>
        <%
            if((Boolean) request.getAttribute("showAction")) {
                String attribut = (String) request.getAttribute("actionLine");
                out.println(attribut);
            }else{
                out.println("veuillez remplir les deux champs");
            }
        %>
    </p>

    <p>Visualisation des titres les plus écoutés :</p>

    <form action="${pageContext.request.contextPath}/AdminWeb" method="POST">
        <div>
            <input type="radio" id="music" name="byType" value ="music" checked>
            <label for="music">Musiques</label>
            <input type="radio" id="radio" name="byType" value ="radio">
            <label for="radio">Radios</label>
            <input type="radio" id="podcast" name="byType" value ="podcast">
            <label for="podcast">Podcasts</label>
        </div>
        <div>
            <label for="nbtitres">Nombre de titres à afficher: </label>
            <input type="text" id="nbtitres" required name="nbtitres">
        </div>
        <div>
            <input type="radio" id="byMonth" name="byPeriode" value ="byMonth" checked>
            <label for="byMonth">This month</label>
        </div>
        <div>
            <input type="radio" id="byAll" name="byPeriode" value ="byAll">
            <label for="byAll">All time</label>
        </div>
        <div class="button">
            <button type="submit">Afficher le top </button>
        </div>
    </form>

    <%

        List<ContenuSonore> listCont = (List<ContenuSonore>) request.getAttribute("topNmusique");
        int nbRes = 0;
        if (listCont != null){
            nbRes = listCont.size();
        }else{
            LoggerSite.logger.info("listCont is null");
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

            <th>Nb écoutes/Mois</th>

            <th>Nb écoutes totales</th>

        </tr>

        <%

            for (int i = 0; i < listCont.size(); i++) {

                String contenu = listCont.get(i).getContenu();

                int NbEcouteM = listCont.get(i).getNbLectureMois();

                int NbEcouteT = listCont.get(i).getNbLectureTotal();

        %>

        <tr>

            <td><%=contenu %></td>

            <td><%=NbEcouteM %></td>

            <td><%=NbEcouteT %></td>

        </tr>

        <%

            }

        %>

    </table>

    <%

    }else{

    %>

    aucun titre ne correspond au champ entré :(

    <%

        }

    %>
    <div class="gridyBody">
        <form id="profil" action="${pageContext.request.contextPath}/Profil" method="POST">  // Accueil/Profil ????
            <div class="gridyProfilForm">
                <label id="nomL" for="nom">Nom :</label>
                <input id="nom" class="labelStyle" type="text" required name="nom"> <!--name pour récupérer le nom sous jee-->

                <label id="prenomL" for="prenom">Prenom :</label>
                <input id="prenom" class="labelStyle" type="text" required name="prenom">

                <label id="dateNaissanceL" for="dateNaissance">Date de naissance :</label>
                <input id="dateNaissance" class="labelStyle" type="date" required name="birthDate">

                <label id="civiliteL" for="civilite">Civilite :</label>

                <!--
                <input id="civilite" class="labelStyle" type="" required name="civilite">
                -->
                <!--La liste déroulante garantit l'input-->
                <select id = "civilite" class="labelStyle" size="1" name="sexe">
                    <option>Homme</option>
                    <option>Femme</option>
                    <option>Non-binaire</option>
                </select>

                <label id="mailL" for="mail">Adresse mail :</label>
                <input id="mail" class="labelStyle" type="email" required name="mail">
                <div id="checkMail"></div>

                <label id="passwordL" for="password">Mot de passe :</label>
                <input id="password" class="labelStyle" type="password" required name="password">
                <div id = "checkMdp1"></div>

                <label id="confirmPasswordL" for="confirmPassword">Confirmation du mot de passe :</label>
                <input id="confirmPassword" class="labelStyle" type="password" required name="confirmPassword">
                <div id = "checkMdp2"></div>


                <label id="adresseFacturationL" for="adresseFacturation">Adresse de facturation :</label>
                <input id="adresseFacturation" class="labelStyle" type="text" required name="adresseFacturation">


                <input id="validateInscription" type="submit" value="Validez"> /////////////////////// A MODIFIER : Bouton pas encore ajouté
            </div>
        </form>
    </div>

</body>
</html>
