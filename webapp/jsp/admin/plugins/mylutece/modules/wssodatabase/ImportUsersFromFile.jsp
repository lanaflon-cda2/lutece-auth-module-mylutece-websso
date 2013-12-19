<%@ page errorPage="../../../../ErrorPage.jsp" %>

<jsp:useBean id="wssoUser" scope="session" class="fr.paris.lutece.plugins.mylutece.modules.wssodatabase.authentication.web.WssodatabaseJspBean" />

<%
	wssoUser.init( request, wssoUser.WSSODATABASE_MANAGEMENT_USERS ) ;
	String strContent = wssoUser.getImportUsersFromFile( request );
%>
<jsp:include page="../../../../AdminHeader.jsp"  flush="true" />

<%= strContent %>

<%@ include file="../../../../AdminFooter.jsp" %>