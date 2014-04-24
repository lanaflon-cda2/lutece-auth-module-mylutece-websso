/*
 * Copyright (c) 2002-2014, Mairie de Paris
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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * This class provides Data Access methods for WssoProfil objects
 */
public final class WssoProfilDAO implements IWssoProfilDAO
{
    // Constants
    private static final String SQL_QUERY_INSERT = " INSERT INTO mylutece_wsso_profil ( code, description ) VALUES ( ?, ? ) ";
    private static final String SQL_QUERY_DELETE = " DELETE FROM mylutece_wsso_profil WHERE code = ?  ";
    private static final String SQL_QUERY_UPDATE_DESCRIPTION = " UPDATE mylutece_wsso_profil SET description = ? WHERE code = ?  ";
    private static final String SQL_QUERY_FIND_ALL = " SELECT code, description FROM mylutece_wsso_profil ORDER BY code, description";
    private static final String SQL_QUERY_FIND_BY_CODE_AND_DESCRIPTION = " SELECT code, description FROM mylutece_wsso_profil WHERE code = ? and description = ?";
    private static final String SQL_QUERY_FIND_BY_DESCRIPTION = " SELECT code, description FROM mylutece_wsso_profil WHERE description = ? ";
    private static final String SQL_QUERY_FIND_BY_CODE = " SELECT code, description FROM mylutece_wsso_profil WHERE code = ? ";
    private static final String SQL_SELECT_WSSO_PROFILS_CODE_FROM_PASSWORD = "SELECT b.mylutece_wsso_profil_code FROM mylutece_wsso_user a INNER JOIN mylutece_wsso_profil_user b on a.mylutece_wsso_user_id = b.mylutece_wsso_user_id AND b.mylutece_wsso_user_id = ? ";
    private static final String SQL_QUERY_CHECK_PROFIL_ASSIGNED_TO_USER = " SELECT count(*) FROM mylutece_wsso_profil_user WHERE mylutece_wsso_profil_code = ?";

    /** This class implements the Singleton design pattern. */
    private static WssoProfilDAO _dao = new WssoProfilDAO(  );

    /**
     * Creates a new WssoUserDAO object.
     */
    private WssoProfilDAO(  )
    {
    }

    /**
     * Returns the unique instance of the singleton.
     *
     * @return the instance
     */
    static WssoProfilDAO getInstance(  )
    {
        return _dao;
    }

    /**
     * Insert a new record in the table.
     *
     * @param wssoProfil The wssoProfil object
     * @param plugin The Plugin using this data access service
     */
    public void insert( WssoProfil wssoProfil, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        daoUtil.setString( 1, wssoProfil.getCode(  ) );
        daoUtil.setString( 2, wssoProfil.getDescription(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    //	/**
    //	 * Load the data of WssoProfil from the table
    //	 * 
    //	 * @param nWssoProfilId The identifier of WssoUser
    //	 * @param plugin The Plugin using this data access service
    //	 * @return the instance of the WssoUser
    //	 */
    //	public WssoProfil load( int nWssoProfilId, Plugin plugin )
    //	{
    //		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_ID, plugin );
    //		daoUtil.setInt( 1, nWssoProfilId );
    //		daoUtil.executeQuery( );
    //
    //		WssoProfil wssoProfil = null;
    //
    //		if ( daoUtil.next( ) )
    //		{
    //			wssoProfil = new WssoProfil( );
    //			wssoProfil.setMyluteceWssoProfilId( daoUtil.getInt( 1 ) );
    //			wssoProfil.setCode(daoUtil.getString( 2 ));
    //			wssoProfil.setDescription(daoUtil.getString( 3 ));
    //		}
    //
    //		daoUtil.free( );
    //
    //		return wssoProfil;
    //	}

    /**
     * Delete a record from the table
     * @param wssoProfil The WssoProfil object
     * @param plugin The Plugin using this data access service
     */
    public void delete( WssoProfil wssoProfil, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setString( 1, wssoProfil.getCode(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update the record in the table
     * @param wssoProfil The reference of wssoProfil
     * @param plugin The Plugin using this data access service
     */
    public void store( WssoProfil wssoProfil, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE_DESCRIPTION, plugin );
        daoUtil.setString( 1, wssoProfil.getDescription(  ) );
        daoUtil.setString( 2, wssoProfil.getCode(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the list of wssoProfils
     * @param plugin The Plugin using this data access service
     * @return The Collection of the WssoProfils
     */
    public List<WssoProfil> selectWssoProfilList( Plugin plugin )
    {
        List<WssoProfil> listWssoProfils = new ArrayList<WssoProfil>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_ALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            WssoProfil wssoProfil = new WssoProfil(  );
            wssoProfil.setCode( daoUtil.getString( 1 ) );
            wssoProfil.setDescription( daoUtil.getString( 2 ) );

            listWssoProfils.add( wssoProfil );
        }

        daoUtil.free(  );

        return listWssoProfils;
    }

    //	/**
    //	 * Load the list of wssoUsers for a role
    //	 * @param nIdRole The role of WssoUser
    //	 * @param plugin The Plugin using this data access service
    //	 * @return The Collection of the WssoUsers
    //	 */
    //	public Collection<WssoUser> selectWssoUsersListForRole( int nIdRole, Plugin plugin )
    //	{
    //		Collection<WssoUser> listWssoUsers = new ArrayList<WssoUser>( );
    //		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_FOR_ROLE, plugin );
    //		daoUtil.setInt( 1, nIdRole );
    //		daoUtil.executeQuery( );
    //
    //		while ( daoUtil.next( ) )
    //		{
    //			WssoUser wssoUser = new WssoUser( );
    //			wssoUser.setMyluteceWssoUserId( daoUtil.getInt( 1 ) );
    //			wssoUser.setGuid( daoUtil.getString( 2 ) );
    //			wssoUser.setLastName( daoUtil.getString( 3 ) );
    //			wssoUser.setFirstName( daoUtil.getString( 4 ) );
    //			wssoUser.setEmail( daoUtil.getString( 5 ) );
    //			wssoUser.setDateLastLogin( daoUtil.getDate( 6 ) );
    //
    //			listWssoUsers.add( wssoUser );
    //		}
    //
    //		daoUtil.free( );
    //
    //		return listWssoUsers;
    //	}

    /**
     * Find the data of WssoProfil from the table
     *
     * @param strCode The code of WssoProfil
     * @param plugin The Plugin using this data access service
     * @return the instance of the WssoProfil
     */
    public WssoProfil load( String strCode, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_CODE, plugin );
        daoUtil.setString( 1, strCode );
        daoUtil.executeQuery(  );

        WssoProfil wssoProfil = null;

        if ( daoUtil.next(  ) )
        {
            wssoProfil = new WssoProfil(  );
            wssoProfil.setCode( daoUtil.getString( 1 ) );
            wssoProfil.setDescription( daoUtil.getString( 2 ) );
        }

        daoUtil.free(  );

        return wssoProfil;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public WssoProfil findWssoProfilByCodeAndDescription( String strCode, String strDescription, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_CODE_AND_DESCRIPTION, plugin );
        daoUtil.setString( 1, strCode );
        daoUtil.setString( 2, strDescription );
        daoUtil.executeQuery(  );

        WssoProfil wssoProfil = null;

        while ( daoUtil.next(  ) )
        {
            wssoProfil = new WssoProfil(  );
            wssoProfil.setCode( daoUtil.getString( 1 ) );
            wssoProfil.setDescription( daoUtil.getString( 2 ) );
        }

        daoUtil.free(  );

        return wssoProfil;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public List<WssoProfil> findWssoProfilsByDescription( String strDescription, Plugin plugin )
    {
        List<WssoProfil> listWssoProfils = new ArrayList<WssoProfil>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_DESCRIPTION, plugin );
        daoUtil.setString( 1, strDescription );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            WssoProfil wssoProfil = new WssoProfil(  );
            wssoProfil.setCode( daoUtil.getString( 1 ) );
            wssoProfil.setDescription( daoUtil.getString( 2 ) );

            listWssoProfils.add( wssoProfil );
        }

        daoUtil.free(  );

        return listWssoProfils;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public List<String> findWssoProfilsForUser( int nWssoUserId, Plugin plugin )
    {
        List<String> listCodeProfils = new ArrayList<String>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_SELECT_WSSO_PROFILS_CODE_FROM_PASSWORD, plugin );
        daoUtil.setInt( 1, nWssoUserId );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            listCodeProfils.add( daoUtil.getString( 1 ) );
        }

        daoUtil.free(  );

        return listCodeProfils;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public boolean checkProfilAssigned( String strCode, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_CHECK_PROFIL_ASSIGNED_TO_USER, plugin );
        daoUtil.setString( 1, strCode );
        daoUtil.executeQuery(  );

        WssoProfil wssoProfil = null;

        int nbrAssignation = 0;

        while ( daoUtil.next(  ) )
        {
            nbrAssignation = daoUtil.getInt( 1 );
        }

        daoUtil.free(  );

        if ( nbrAssignation > 0 )
        {
            return true;
        }

        return false;
    }
}
