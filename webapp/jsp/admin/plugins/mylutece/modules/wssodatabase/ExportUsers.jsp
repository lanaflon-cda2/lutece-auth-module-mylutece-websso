<%@ page errorPage="../../../../ErrorPage.jsp" %>

<jsp:useBean id="wssoProfil" scope="session" class="fr.paris.lutece.plugins.mylutece.modules.wssodatabase.authentication.web.WssodatabaseJspBean" />
<%
	wssoProfil.init( request, wssoProfil.WSSODATABASE_MANAGEMENT_USERS );
	String strContent = wssoProfil.getExportUsers( request );
%>
<jsp:include page="../../../../AdminHeader.jsp"  flush="true" />

<%= strContent %>

<%@ include file="../../../../AdminFooter.jsp" %>

