<%--
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

    <form action="AdminWeb" method="post">
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
                out.println("veuillez remplir les deux chqmps");
            }
        %>
    </p>

    <p>Visualisation des titres les plus écoutés :</p>

    <form action="AdminWeb" method="post">
        <div>
            <label for="nbtitres">Nombre de titres à afficher: </label>
            <input type="text" id="nbtitres" name="nbtitres" required>
        </div>
        <div class="button">
            <button type="submit">Afficher le top </button>
        </div>
    </form>


</body>
</html>
