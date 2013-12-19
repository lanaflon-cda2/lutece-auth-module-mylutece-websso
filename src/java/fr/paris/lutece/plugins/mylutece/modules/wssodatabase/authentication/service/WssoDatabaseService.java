/*
 * Copyright (c) 2002-2013, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.mylutece.modules.wssodatabase.authentication.service;

import fr.paris.lutece.plugins.mylutece.authentication.MultiLuteceAuthentication;
import fr.paris.lutece.plugins.mylutece.business.attribute.IAttribute;
import fr.paris.lutece.plugins.mylutece.modules.wssodatabase.authentication.IdxWSSODatabaseAuthentication;
import fr.paris.lutece.plugins.mylutece.modules.wssodatabase.authentication.business.WssoProfilHome;
import fr.paris.lutece.plugins.mylutece.modules.wssodatabase.authentication.business.WssoUser;
import fr.paris.lutece.plugins.mylutece.modules.wssodatabase.authentication.business.WssoUserRoleHome;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.xml.XmlUtil;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Locale;


/**
 *
 * LdapDatabaseService
 *
 */
public class WssoDatabaseService
{
    private static final String AUTHENTICATION_BEAN_NAME = "mylutece-wssodatabase.authentication";

    // MARKS
    //	private static final String MARK_ENCRYPTION_ALGORITHMS_LIST = "encryption_algorithms_list";
    //	private static final String MARK_SEARCH_IS_SEARCH = "search_is_search";
    //	private static final String MARK_SORT_SEARCH_ATTRIBUTE = "sort_search_attribute";
    //	private static final String MARK_SEARCH_USER_FILTER = "search_user_filter";
    //	private static final String MARK_SEARCH_MYLUTECE_USER_FIELD_FILTER = "search_mylutece_user_field_filter";
    //	private static final String MARK_ATTRIBUTES_LIST = "attributes_list";
    //	private static final String MARK_IS_PLUGIN_JCAPTCHA_ENABLE = "is_plugin_jcatpcha_enable";
    //	private static final String MARK_LOGIN_URL = "login_url";
    //	private static final String MARK_NEW_PASSWORD = "new_password";
    //	private static final String MARK_ENABLE_JCAPTCHA = "enable_jcaptcha";
    //	private static final String MARK_SITE_LINK = "site_link";
    //	private static final String MARK_BANNED_DOMAIN_NAMES = "banned_domain_names";

    // CONSTANTS
    //	private static final String COMMA = ",";
    //	private static final String AMPERSAND = "&";
    //	private static final String PLUGIN_JCAPTCHA = "jcaptcha";
    private static final String CONSTANT_XML_USER = "user";
    private static final String CONSTANT_XML_GUID = "guid";
    private static final String CONSTANT_XML_LAST_NAME = "last_name";
    private static final String CONSTANT_XML_FIRST_NAME = "first_name";
    private static final String CONSTANT_XML_EMAIL = "email";

    //    private static final String CONSTANT_XML_PASSWORD_MAX_VALID_DATE = "password_max_valid_date";
    //    private static final String CONSTANT_XML_ACCOUNT_MAX_VALID_DATE = "account_max_valid_date";
    private static final String CONSTANT_XML_ROLES = "roles";
    private static final String CONSTANT_XML_ROLE = "role";

    //    private static final String CONSTANT_XML_GROUPS = "groups";
    //    private static final String CONSTANT_XML_GROUP = "group";
    private static final String CONSTANT_XML_PROFILS = "profils";
    private static final String CONSTANT_XML_PROFIL = "profil";

    //    private static final String CONSTANT_XML_ATTRIBUTES = "attributes";
    //    private static final String CONSTANT_XML_ATTRIBUTE = "attribute";
    //    private static final String CONSTANT_XML_ATTRIBUTE_ID = "attribute-id";
    //    private static final String CONSTANT_XML_ATTRIBUTE_FIELD_ID = "attribute-field-id";
    //    private static final String CONSTANT_XML_ATTRIBUTE_VALUE = "attribute-value";

    // PROPERTIES
    //	private static final String PROPERTY_ENCRYPTION_ALGORITHMS_LIST = "encryption.algorithmsList";

    // PARAMETERS
    //	private static final String PARAMETER_ACCOUNT_CREATION_VALIDATION_EMAIL = "account_creation_validation_email";
    //	private static final String PARAMETER_ACCOUNT_REACTIVATED_MAIL_SENDER = "account_reactivated_mail_sender";
    //	private static final String PARAMETER_ACCOUNT_REACTIVATED_MAIL_SUBJECT = "account_reactivated_mail_subject";
    //	private static final String PARAMETER_ACCOUNT_REACTIVATED_MAIL_BODY = "mylutece_database_account_reactivated_mail";
    //    private static final String PARAMETER_MAIL_PASSWORD_ENCRYPTION_CHANGED = "mylutece_database_mailPasswordEncryptionChanged";
    //    private static final String PARAMETER_MAIL_PASSWORD_ENCRYPTION_CHANGED_SENDER = "mail_password_encryption_changed_sender";
    //    private static final String PARAMETER_MAIL_PASSWORD_ENCRYPTION_CHANGED_SUBJECT = "mail_password_encryption_changed_subject";
    private static WssoDatabaseService _singleton = new WssoDatabaseService(  );

    /**
     * Initialize the WssoDatabase service
     *
     */
    public void init(  )
    {
        WssoUser.init(  );

        IdxWSSODatabaseAuthentication baseAuthentication = (IdxWSSODatabaseAuthentication) SpringContextService.getPluginBean( WssoDatabasePlugin.PLUGIN_NAME,
                AUTHENTICATION_BEAN_NAME );

        if ( baseAuthentication != null )
        {
            MultiLuteceAuthentication.registerAuthentication( baseAuthentication );
        }
        else
        {
            AppLogService.error( 
                "IdxWSSODatabaseAuthentication not found, please check your wssodatabase_context.xml configuration" );
        }
    }

    /**
     * Returns the instance of the singleton
     *
     * @return The instance of the singleton
     */
    public static WssoDatabaseService getInstance(  )
    {
        return _singleton;
    }

    /**
     * Get a XML string describing a given user
     * @param user The user to get the XML of.
     * @param bExportRoles True to export roles of the user, false otherwise.
     * @param bExportGroups True to export groups of the user, false otherwise.
     * @param bExportProfils True to export profils of the user, false
     *            otherwise.
     * @param listAttributes The list of attributes to export.
     * @param locale The locale
     * @return A string of XML with the information of the user.
     */
    public String getXmlFromUser( WssoUser user, boolean bExportRoles, boolean bExportGroups, boolean bExportProfils,
        List<IAttribute> listAttributes, Locale locale )
    {
        Plugin databasePlugin = PluginService.getPlugin( WssoDatabasePlugin.PLUGIN_NAME );

        //        Plugin mylutecePlugin = PluginService.getPlugin( MyLutecePlugin.PLUGIN_NAME );
        StringBuffer sbXml = new StringBuffer(  );
        //        DateFormat dateFormat = new SimpleDateFormat( );
        XmlUtil.beginElement( sbXml, CONSTANT_XML_USER );
        XmlUtil.addElement( sbXml, CONSTANT_XML_GUID, user.getGuid(  ) );
        XmlUtil.addElement( sbXml, CONSTANT_XML_LAST_NAME, user.getLastName(  ) );
        XmlUtil.addElement( sbXml, CONSTANT_XML_FIRST_NAME, user.getFirstName(  ) );
        XmlUtil.addElement( sbXml, CONSTANT_XML_EMAIL, user.getEmail(  ) );

        //        XmlUtil.addElement( sbXml, CONSTANT_XML_STATUS, Integer.toString( user.getStatus( ) ) );

        //        String strPasswordMaxValidDate = StringUtils.EMPTY;
        //        if ( user.getPasswordMaxValidDate( ) != null )
        //        {
        //            strPasswordMaxValidDate = dateFormat.format( user.getPasswordMaxValidDate( ) );
        //        }
        //        XmlUtil.addElement( sbXml, CONSTANT_XML_PASSWORD_MAX_VALID_DATE, strPasswordMaxValidDate );
        //
        //        String strAccountMaxValidDate = StringUtils.EMPTY;
        //        if ( user.getAccountMaxValidDate( ) != null )
        //        {
        //            strAccountMaxValidDate = dateFormat.format( user.getAccountMaxValidDate( ) );
        //        }
        //        XmlUtil.addElement( sbXml, CONSTANT_XML_ACCOUNT_MAX_VALID_DATE, strAccountMaxValidDate );
        if ( bExportRoles )
        {
            Collection<String> userRoleList = WssoUserRoleHome.findRolesListForUser( user.getMyluteceWssoUserId(  ),
                    databasePlugin );

            if ( CollectionUtils.isNotEmpty( userRoleList ) )
            {
                XmlUtil.beginElement( sbXml, CONSTANT_XML_ROLES );

                for ( String strRole : userRoleList )
                {
                    XmlUtil.addElement( sbXml, CONSTANT_XML_ROLE, strRole );
                }

                XmlUtil.endElement( sbXml, CONSTANT_XML_ROLES );
            }
        }

        //        if ( bExportGroups ) // TODO
        //        {
        //            List<String> listGroups = DatabaseHome.findUserGroupsFromLogin( user.getLogin( ), databasePlugin );
        //            XmlUtil.beginElement( sbXml, CONSTANT_XML_GROUPS );
        //            for ( String strGoup : listGroups )
        //            {
        //                XmlUtil.addElement( sbXml, CONSTANT_XML_GROUP, strGoup );
        //            }
        //            XmlUtil.endElement( sbXml, CONSTANT_XML_GROUPS );
        //        }
        if ( bExportProfils )
        {
            Collection<String> userProfilList = WssoProfilHome.findWssoProfilsForUser( user.getMyluteceWssoUserId(  ),
                    databasePlugin );

            if ( CollectionUtils.isNotEmpty( userProfilList ) )
            {
                XmlUtil.beginElement( sbXml, CONSTANT_XML_PROFILS );

                for ( String strProfil : userProfilList )
                {
                    XmlUtil.addElement( sbXml, CONSTANT_XML_PROFIL, strProfil );
                }

                XmlUtil.endElement( sbXml, CONSTANT_XML_PROFILS );
            }
        }

        //        if ( bExportProfils )
        //        {
        //            XmlUtil.beginElement( sbXml, CONSTANT_XML_ATTRIBUTES );
        //            for ( IAttribute attribute : listAttributes )
        //            {
        //                List<MyLuteceUserField> listUserFields = MyLuteceUserFieldHome.selectUserFieldsByIdUserIdAttribute(
        //                        user.getMyluteceWssoUserId(), attribute.getIdAttribute( ), mylutecePlugin );
        //                for ( MyLuteceUserField userField : listUserFields )
        //                {
        //                    XmlUtil.beginElement( sbXml, CONSTANT_XML_ATTRIBUTE );
        //                    XmlUtil.addElement( sbXml, CONSTANT_XML_ATTRIBUTE_ID,
        //                            Integer.toString( attribute.getIdAttribute( ) ) );
        //                    XmlUtil.addElement( sbXml, CONSTANT_XML_ATTRIBUTE_FIELD_ID, userField.getAttributeField( )
        //                            .getIdField( ) );
        //                    XmlUtil.addElement( sbXml, CONSTANT_XML_ATTRIBUTE_VALUE, userField.getValue( ) );
        //                    XmlUtil.endElement( sbXml, CONSTANT_XML_ATTRIBUTE );
        //                }
        //            }
        //            XmlUtil.endElement( sbXml, CONSTANT_XML_ATTRIBUTES );
        //        }
        XmlUtil.endElement( sbXml, CONSTANT_XML_USER );

        return sbXml.toString(  );
    }
}
