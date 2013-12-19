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
package fr.paris.lutece.plugins.mylutece.modules.wssodatabase.authentication.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.paris.lutece.plugins.mylutece.modules.wssodatabase.authentication.IdxWSSODatabaseUser;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.security.LuteceAuthentication;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.util.sql.DAOUtil;


/**
 * This class provides Data Access methods for authentication (role retrieval).
 * 
 */
public class IdxWSSODatabaseDAO implements IIdxWSSODatabaseDAO
{
    public static final String SQL_QUERY_FIND_USER_BY_GUID = "SELECT mylutece_wsso_user_id, last_name, first_name, email FROM mylutece_wsso_user WHERE guid like ? ";

    public static final String SQL_QUERY_FIND_ALL_USERS = "SELECT guid, last_name, first_name, email FROM mylutece_wsso_user";

    public static final String SQL_QUERY_FIND_ROLES_FROM_GUID = "SELECT a.role FROM mylutece_wsso_user_role a, mylutece_wsso_user b"
            + " WHERE b.mylutece_wsso_user_id = a.mylutece_wsso_user_id AND b.guid like ? ";

    public static final String SQL_QUERY_FIND_ROLES_BY_PROFIL = "SELECT b.role_key FROM mylutece_wsso_profil a, mylutece_wsso_profil_role b"
            + " WHERE a.code = b.mylutece_wsso_profil_code AND a.code like ? ";

    private static final String SQL_QUERY_UPDATE_DATE_LAST_LOGIN = " UPDATE mylutece_wsso_user SET date_last_login = ? WHERE guid like ? ";

    private static final String SQL_QUERY_DELETE_ROLES_FOR_PROFIL = "DELETE FROM mylutece_wsso_profil_role WHERE mylutece_wsso_profil_code = ? ";

    private static final String SQL_QUERY_ADD_ROLE_FOR_PROFIL = "INSERT INTO mylutece_wsso_profil_role ( mylutece_wsso_profil_code, role_key ) VALUES ( ?, ? ) ";

    private static final String SQL_QUERY_FIND_USERS_FOR_PROFIL = "SELECT a.mylutece_wsso_user_id, a.guid, a.last_name, a.first_name, a.email, a.date_last_login FROM mylutece_wsso_user a "
            + "INNER JOIN mylutece_wsso_profil_user b ON a.mylutece_wsso_user_id = b.mylutece_wsso_user_id WHERE b.mylutece_wsso_profil_code = ?";

    private static final String SQL_QUERY_ADD_USER_FOR_PROFIL = "INSERT INTO mylutece_wsso_profil_user ( mylutece_wsso_user_id, mylutece_wsso_profil_code ) VALUES ( ?, ? ) ";

    private static final String SQL_QUERY_DELETE_USER_FOR_PROFIL = "DELETE FROM mylutece_wsso_profil_user WHERE mylutece_wsso_user_id = ? AND mylutece_wsso_profil_code = ?";

    private static final String SQL_QUERY_DELETE_PROFILS_FOR_USER = "DELETE FROM mylutece_wsso_profil_user WHERE mylutece_wsso_user_id = ? ";

    /** This class implements the Singleton design pattern. */
    private static IdxWSSODatabaseDAO _dao = new IdxWSSODatabaseDAO( );

    /**
     * Returns the unique instance of the singleton.
     * 
     * @return the instance
     */
    static IdxWSSODatabaseDAO getInstance( )
    {
        return _dao;
    }

    /**
     * Find users by guid
     * 
     * @param strGuid the WSSO guid
     * @param plugin The Plugin using this data access service
     * @param authenticationService the LuteceAuthentication object
     * @return IdxWSSODatabaseUser the user corresponding to the guid
     */
    public IdxWSSODatabaseUser findUserByGuid( String strGuid, Plugin plugin, LuteceAuthentication authenticationService )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_USER_BY_GUID, plugin );
        daoUtil.setString( 1, strGuid );
        daoUtil.executeQuery( );

        if ( !daoUtil.next( ) )
        {
            daoUtil.free( );

            return null;
        }

        String strLastName = daoUtil.getString( 2 );
        String strFirstName = daoUtil.getString( 3 );
        String strEmail = daoUtil.getString( 4 );

        IdxWSSODatabaseUser user = new IdxWSSODatabaseUser( strGuid, authenticationService );
        user.setUserInfo( LuteceUser.NAME_FAMILY, strLastName );
        user.setUserInfo( LuteceUser.NAME_GIVEN, strFirstName );
        user.setUserInfo( LuteceUser.BUSINESS_INFO_ONLINE_EMAIL, strEmail );
        daoUtil.free( );

        return user;
    }

    /**
     * Find user's roles by guid
     * 
     * @param strGuid the WSSO guid
     * @param plugin The Plugin using this data access service
     * @param authenticationService the LuteceAuthentication object
     * @return ArrayList the roles list corresponding to the guid
     */
    public List<String> findUserRolesFromGuid( String strGuid, Plugin plugin, LuteceAuthentication authenticationService )
    {
        List<String> arrayRoles = new ArrayList<String>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_ROLES_FROM_GUID, plugin );
        daoUtil.setString( 1, strGuid );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            arrayRoles.add( daoUtil.getString( 1 ) );
        }

        daoUtil.free( );

        return arrayRoles;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void updateDateLastLogin( String strGuid, java.util.Date dateLastLogin, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE_DATE_LAST_LOGIN, plugin );
        java.sql.Date dateSql = new java.sql.Date( dateLastLogin.getTime( ) );
        daoUtil.setDate( 1, dateSql );
        daoUtil.setString( 2, strGuid );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * Find users list
     * 
     * @param plugin The Plugin using this data access service
     * @param authenticationService the LuteceAuthentication object
     * @return A Collection of users
     */
    public Collection<IdxWSSODatabaseUser> findUsersList( Plugin plugin, LuteceAuthentication authenticationService )
    {
        Collection<IdxWSSODatabaseUser> usersList = new ArrayList<IdxWSSODatabaseUser>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_ALL_USERS, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            String strGuid = daoUtil.getString( 1 );
            String strLastName = daoUtil.getString( 2 );
            String strFirstName = daoUtil.getString( 3 );
            String strEmail = daoUtil.getString( 4 );

            IdxWSSODatabaseUser user = new IdxWSSODatabaseUser( strGuid, authenticationService );

            user.setUserInfo( LuteceUser.NAME_FAMILY, strLastName );
            user.setUserInfo( LuteceUser.NAME_GIVEN, strFirstName );
            user.setUserInfo( LuteceUser.BUSINESS_INFO_ONLINE_EMAIL, strEmail );

            user.setRoles( findUserRolesFromGuid( strGuid, plugin, authenticationService ) );

            usersList.add( user );
        }

        daoUtil.free( );

        return usersList;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public List<String> findRolesFromProfil( String codeProfil, Plugin plugin )
    {
        List<String> listRoleKeys = new ArrayList<String>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_ROLES_BY_PROFIL, plugin );
        daoUtil.setString( 1, codeProfil );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            listRoleKeys.add( daoUtil.getString( 1 ) );
        }

        daoUtil.free( );

        return listRoleKeys;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void removeRolesForProfil( String codeProfil, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_ROLES_FOR_PROFIL, plugin );
        daoUtil.setString( 1, codeProfil );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void addRoleForProfil( String codeProfil, String codeRole, Plugin plugin )
    {

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_ADD_ROLE_FOR_PROFIL, plugin );
        daoUtil.setString( 1, codeProfil );
        daoUtil.setString( 2, codeRole );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * Find assigned users to the given profil
     * @param codeProfil The profil code
     * @param plugin Plugin
     * @return a list of WssoUser
     */
    public List<WssoUser> findWssoUsersForProfil( String codeProfil, Plugin plugin )
    {

        List<WssoUser> listUsers = new ArrayList<WssoUser>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_USERS_FOR_PROFIL, plugin );
        daoUtil.setString( 1, codeProfil );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            WssoUser wssoUser = new WssoUser( );
            wssoUser.setMyluteceWssoUserId( daoUtil.getInt( 1 ) );
            wssoUser.setGuid( daoUtil.getString( 2 ) );
            wssoUser.setLastName( daoUtil.getString( 3 ) );
            wssoUser.setFirstName( daoUtil.getString( 4 ) );
            wssoUser.setEmail( daoUtil.getString( 5 ) );
            wssoUser.setDateLastLogin( daoUtil.getDate( 6 ) );
            listUsers.add( wssoUser );
        }

        daoUtil.free( );

        return listUsers;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void addUserForProfil( int wssoUserId, String codeProfil, Plugin plugin )
    {

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_ADD_USER_FOR_PROFIL, plugin );
        daoUtil.setInt( 1, wssoUserId );
        daoUtil.setString( 2, codeProfil );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void removeUserForProfil( int wssoUserId, String codeProfil, Plugin plugin )
    {

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_USER_FOR_PROFIL, plugin );
        daoUtil.setInt( 1, wssoUserId );
        daoUtil.setString( 2, codeProfil );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void removeProfilsForUser( int wssoUserId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_PROFILS_FOR_USER, plugin );
        daoUtil.setInt( 1, wssoUserId );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

}
