<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../AdminHeader.jsp" />

<jsp:useBean id="wssoProfil" scope="session" class="fr.paris.lutece.plugins.mylutece.modules.wssodatabase.authentication.web.WssodatabaseJspBean" />

<% wssoProfil.init( request, wssoProfil.RIGHT_MANAGE_WSSO_PROFILS ); %>
<%= wssoProfil.getManageProfils( request ) %>

<%@ include file="../../../../AdminFooter.jsp" %>
