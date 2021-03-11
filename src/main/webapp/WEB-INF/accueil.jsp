<%--
  Created by IntelliJ IDEA.
  User: jeanhanna
  Date: 19/02/2021
  Time: 23:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.siteStreaming.SiteStreaming.DataBase.S" %>
<%@ page import="com.siteStreaming.SiteStreaming.Acceuil.MetaErrorHandler" %>
<%@ page import="com.siteStreaming.SiteStreaming.Acceuil.CompteClient" %>
<%@ page import="com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.ContenuSonore" %>
<%@ page import="java.util.List" %>
<%@ page import="com.siteStreaming.SiteStreaming.Catalogue.ContenuSonore.Musique" %>
<%@ page import="com.siteStreaming.SiteStreaming.LoggerSite" %>


<%
    //Récupération des données transmises depuis le servlet en cas de connection
    //En cas d'erreur de tentative de connection
    String mailAddressUsed = (String)request.getAttribute("mailAddressUsed");
    String passwordUsed = (String)request.getAttribute("passwordUsed");
    MetaErrorHandler metaErrorHandler = new MetaErrorHandler(mailAddressUsed, passwordUsed);

    //Création de la sessions
    CompteClient client = (CompteClient) session.getAttribute("sessionUtilisateur");

    List<Musique> listMus = (List<Musique>) request.getAttribute("listMus");


    String m = (String) request.getAttribute("musique");
    Boolean lect = (m!=null);
%>

<!DOCTYPE html>
<html>
<head>
    <title>UsTube</title>
    <link rel="shortcut icon" href="#"> <!--favicon error-->
    <link rel="stylesheet" type="text/css" href="css/acceuil.css">
    <link rel="stylesheet" type="text/css" href="css/connexion.css">
    <link rel="stylesheet" type="text/css" href="css/catalogue.css">
    <link rel="stylesheet" type="text/css" href="css/imageFormat.css">
    <link rel="stylesheet" type="text/css" href="css/lecteur.css">
    <script src="js/client.js"></script>
    <script src="js/storeObject.js"></script>
    <script src="js/titleBarCreation.js"></script>
    <script src="js/modal.js"></script>
    <script src="js/waitForHTMLElementToLoad.js"></script>
    <script src="js/acceuil.js"></script>
    <script src="js/lectureMus/custom-player.js"></script>




    <%//les appels window.addEventListener stack de base%>
    <%if(client == null){%>
    <script>
        window.addEventListener("load", function (){
            waitForElement("connexion", createModal); //Va attendre la creation du bouton SignIn avant d'executé le script du modal
            logedOut(); //Cas utilisateur non connecté
        })
    </script>
    <%} else {%>
    <script>
        window.addEventListener("load", function () {
            //Attente de la construction de la barre

            //Construction de la barre de menu en js, car l'information de connection est au niveau du client
            //const client = getObject('client');
            var nomClient = <%=S.cd(client.getNom())%>;
            var prenomClient = <%=S.cd(client.getPrenom())%>;
            logedIn(nomClient, prenomClient); //Construit la barre de navigation dans le cas où l'utilisateur est connecté
        })
    </script>
    <%}%>

    <script>
        window.addEventListener("load", function (){
            //Bind le fait d'appuyer au clavier avec la disparition du msg de status en cas d'erreur de credentials
            var listId = ["mailAddress", "statusMsg"];
            waitForManyElements(listId, acceuilCheckEventTypingMailConnection);
        })
    </script>

</head>



<%!
    String defaultValue = "VideoImage";
    String defaultValueTitle = "VideoTitle";
    String defaultValueViews = "-9999";
    String defaultValueYear = "01/02/2021";
%>


<body>
<!--Modal : ou page superposée-->
<%if(mailAddressUsed == null){%>
<div id="connexion" class="modal modalHidden">
        <%}else{ //En cas d'erreur de connection, réaffichage automatique du modal%>
    <div id="connexion" class="modal modalNotHidden">
        <%}%>
        <div class="modal-content gridyModal"> <!--Contenu du modal-->
            <div id="SignUpModal">Se connecter</div>
            <div class="close">&times;</div> <!--syntaxe pour le bouton x-->
            <form id="formSignIn" action="${pageContext.request.contextPath}/Acceuil" method="POST">

                <div class="gridyFields">
                    <label id="mailAddressLabel" for="mailAddress">Adresse mail :</label>
                    <input id="mailAddress" class="labelStyle" type="email" value=<%=metaErrorHandler.getEmailUsed()%> required name="mailAddress">

                    <label id="mailPasswordLabel" for="password">Mot de passe :</label>
                    <input id="password" class="labelStyle" type="password" value=<%=metaErrorHandler.getPasswordUsed()%> required name="password">

                    <input id="validate" type="submit" value="Validez">
                </div>
            </form>
            <%if(mailAddressUsed == null){%>
            <div id="statusMsg" class="statusMsgLayoutHidden"></div>
            <%}else{%>
            <div id="statusMsg" class="statusMsgLayout">Email ou mot de passe incorrect.</div>
            <%}%>
        </div>
    </div>

            <div id="lecture" class="modal modalHidden">
                <div id="formlecture" class="modal modalNotHidden">
                    <div class="modal-content"> <!--Contenu du modal-->
                        <div class="close" style="float:right">&times;</div> <!--syntaxe pour le bouton x-->
                        <h3 id="TitreMusoc" style="float:left; padding-left: 3%; padding-right: 3%;"></h3>
                        </br>
                        <div class="forms">
                            <form action="${pageContext.request.contextPath}/Acceuil" method=POST">
                                <input type="hidden" id="hiddenChamp" name="hiddenChamp">
                                <input type="submit" id="Ecouter" value="Ecouter">
                            </form>
                        </div>
                    </div>
                </div>
            </div>
    </div>
    <!--Fin du modal-->


    <!--Partie visible du site-->
    <div id="gridyHeader"> <!--Construit dans titleBarCreation.js-->

    </div>



    <!--à générer automatiquement plus tard-->
    <div class="section">
        <h4>Recommandations du moment :</h4>
        <div id="gridyRecommendedVideos">
            <%
                String videoRec = "";
                String linkImg = "";
                String baseLink = "pictures/musicImg";
                Musique temp;
                String tptitle;
                int tpNbViewsRec;
                String tpannee;
                int id = -1;
                for(int i = 1;i<6;i++){
                    if(listMus!=null && i<listMus.size() && listMus.get(i)!=null) {
                            linkImg = baseLink + Integer.toString(i) + ".jpg"; //génère le lien de l'image
                            videoRec = "vidRec" + Integer.toString(i);
                            try {
                                temp = (Musique) listMus.get(i);
                                tptitle = temp.getTitre();
                                tpannee = temp.getAnneeCreation();
                                tpNbViewsRec = temp.getNbLectureTotal();
                                id = temp.getId();
                            }catch (Exception e){
                                LoggerSite.logger.debug("ceci n'est pas une musique !");
                                tptitle = defaultValueTitle;
                                tpannee = defaultValueYear;
                                tpNbViewsRec = Integer.parseInt(defaultValueViews);
                            }
                            out.println("<div class=\"musiques\" id=" + id + "  name=\""+tptitle+"\">\n");
                            out.println("<img id =" + videoRec + " class=\"imageFormat\" src=" +
                                    linkImg + " alt=\"" + defaultValue + "\">\n");
                            out.println(" <div id =" + tptitle + ">" + tptitle + "</div>\n");
                            out.println(" <div id =" + tpNbViewsRec + ">" + tpNbViewsRec + " vues</div>\n");
                            out.println(" <div id = " + tpannee + ">" + tpannee + "</div>\n</div>\n");
                        }
                }
            %>
        </div>
    </div>



    <div class="section">
        <h4>Morceaux Populaires :</h4>
        <div id = "gridyPopularVideos">
            <%
                for(int i = 6;i<11;i++){
                    if(listMus!=null  && i<listMus.size() && listMus.get(i)!=null) {
                            linkImg = baseLink + Integer.toString(i) + ".jpg";
                            videoRec = "vidRec" + Integer.toString(i);
                            try {
                                temp = (Musique) listMus.get(i);
                                tptitle = temp.getTitre();
                                tpannee = temp.getAnneeCreation();
                                tpNbViewsRec = temp.getNbLectureTotal();
                                id = temp.getId();
                            }catch (Exception e){
                                LoggerSite.logger.debug("ceci n'est pas une musique !");
                                tptitle = defaultValueTitle;
                                tpannee = defaultValueYear;
                                tpNbViewsRec = Integer.parseInt(defaultValueViews);
                            }
                            out.println("<div class=\"musiques\" id=" + id + " name=\""+tptitle+"\">\n");
                            out.println("<img id =" + videoRec + " class=\"imageFormat\" src=" +
                                    linkImg + " alt=\"" + defaultValue + "\">\n");
                            out.println(" <div id =" + tptitle + ">" + tptitle + "</div>\n");
                            out.println(" <div id =" + tpNbViewsRec + ">" + tpNbViewsRec + " vues</div>\n");
                            out.println(" <div id = " + tpannee + ">" + tpannee + "</div>\n</div>\n");

                    }
                }
            %>
        </div>
    </div>
            <div id = "catalogue">
            </div>
</br>
            <div class="audio"  style="bottom: 0; position: sticky;">
                <div class="player">
                    <audio id="audioid" controls>
                        <source src="https://dl5.webmfiles.org/big-buck-bunny_trailer.webm" type="audio/webm" id="srceId">
                    </audio>
                    <div class="controls">
                        <button class="play"  aria-label="bascule lecture pause"></button>
                        <button class="stop"  aria-label="stop"></button>
                        <div class="timer">
                            <div></div>
                            <span aria-label="timer">00:00</span>
                            <span aria-label="timer2" id="montitremus" style="padding-left: 5%;"></span>
                        </div>
                        <button class="rwd"  aria-label="retour prec"></button>
                        <button class="fwd" aria-label="avance suivante"></button>
                    </div>
                </div>
            </div>



    <script language="JavaScript">
        const m = <%=m%>;
        var lect = <%=lect%>;
    </script>
</body>

<footer class="footer">© Copyright 2021 All Rights Reserved.</footer>
</html>