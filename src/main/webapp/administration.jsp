<%@ page import="com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.ContenuSonore" %>
<%@ page import="java.util.List" %><%--
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
            <input type="text" id="nom" name="nom">
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

</body>
</html>
