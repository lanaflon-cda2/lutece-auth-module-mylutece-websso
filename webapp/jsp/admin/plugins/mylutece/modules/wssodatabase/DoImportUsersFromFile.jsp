<%@page import="fr.paris.lutece.portal.web.pluginaction.DefaultPluginActionResult"%>
<%@ page errorPage="../../../../ErrorPage.jsp" %>

<jsp:useBean id="wssoUser" scope="session" class="fr.paris.lutece.plugins.mylutece.modules.wssodatabase.authentication.web.WssodatabaseJspBean" />

<%
	wssoUser.init( request, wssoUser.WSSODATABASE_MANAGEMENT_USERS ) ;
	DefaultPluginActionResult result = wssoUser.doImportUsersFromFile( request );  
	if( result.getHtmlContent( ) == null || "".equals( result.getHtmlContent( ) ) )
	{
	    response.sendRedirect( result.getRedirect( ) );
	}
%>
<jsp:include page="../../../../AdminHeader.jsp"  flush="true" />

<%= result.getHtmlContent( ) %>

<%@ include file="../../../../AdminFooter.jsp" %>
