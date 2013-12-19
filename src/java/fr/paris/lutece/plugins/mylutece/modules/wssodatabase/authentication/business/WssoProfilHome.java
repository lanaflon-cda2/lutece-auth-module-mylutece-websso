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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.List;


/**
 * This class provides instances management methods (create, find, ...) for
 * WssoProfil objects
 */
public final class WssoProfilHome
{
    // Static variable pointed at the DAO instance
    private static IWssoProfilDAO _dao = (IWssoProfilDAO) SpringContextService.getBean( 
            "mylutece-wssodatabase.wssoProfilDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private WssoProfilHome(  )
    {
    }

    /**
     * Creation of an instance of wssoProfil
     *
     * @param wssoProfil The instance of the wssoProfil which contains the
     *            informations to store
     * @param plugin The current plugin using this method
     * @return The instance of wssoProfil which has been created with its
     *         primary key.
     */
    public static WssoProfil create( WssoProfil wssoProfil, Plugin plugin )
    {
        _dao.insert( wssoProfil, plugin );

        return wssoProfil;
    }

    /**
     * Update of the wssoProfil which is specified in parameter
     *
     * @param wssoProfil The instance of the wssoProfil which contains the data
     *            to store
     * @param plugin The current plugin using this method
     * @return The instance of the wssoProfil which has been updated
     */
    public static WssoProfil update( WssoProfil wssoProfil, Plugin plugin )
    {
        _dao.store( wssoProfil, plugin );

        return wssoProfil;
    }

    /**
     * Remove the WssoProfil whose identifier is specified in parameter
     *
     * @param wssoProfil The WssoProfil object to remove
     * @param plugin The current plugin using this method
     */
    public static void remove( WssoProfil wssoProfil, Plugin plugin )
    {
        _dao.delete( wssoProfil, plugin );
    }

    // /////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a wssoProfil whose identifier is specified in
     * parameter
     *
     * @param nKey The Primary key of the wssoProfil
     * @param plugin The current plugin using this method
     * @return An instance of wssoProfil
     */
    public static WssoProfil findWssoProfilByCode( String nKey, Plugin plugin )
    {
        return _dao.load( nKey, plugin );
    }

    /**
     * Returns a collection of wssoProfils objects
     * @param plugin The current plugin using this method
     * @return A collection of wssoProfils
     */
    public static List<WssoProfil> findWssoProfilsList( Plugin plugin )
    {
        return _dao.selectWssoProfilList( plugin );
    }

    //	/**
    //	 * Returns a collection of wssoProfils objects for a role
    //	 * @param nIdRole The role id of the wssoProfil
    //	 * @param plugin The current plugin using this method
    //	 * @return A collection of wssoProfils
    //	 */
    //	public static Collection findWssoProfilsListForRole( int nIdRole, Plugin plugin )
    //	{
    //		return _dao.selectWssoProfilsListForRole( nIdRole, plugin );
    //	}

    //	/**
    //	 * Returns a collection of wssoProfils objects for a guid
    //	 * @param strGuid The guid of the wssoProfil
    //	 * @param plugin The current plugin using this method
    //	 * @return A collection of wssoProfils
    //	 */
    //	public static Collection findWssoProfilsListForGuid( String strGuid, Plugin plugin )
    //	{
    //		return _dao.selectWssoProfilListForGuid( strGuid, plugin );
    //	}

    //	 public static WssoProfil findWssoProfilByCode( String strCode, Plugin plugin ){
    //		return _dao.findWssoProfilByCode( strCode, plugin );
    //	 }

    /**
     * Find profil by code and description
     *
     * @param strCode the code
     * @param strDescription the description
     * @param plugin the current plugin
     * @return the profil
     */
    public static WssoProfil findWssoProfilByCodeAndDescription( String strCode, String strDescription, Plugin plugin )
    {
        return _dao.findWssoProfilByCodeAndDescription( strCode, strDescription, plugin );
    }

    /**
     * Find the profils by description
     *
     * @param strDescription the description
     * @param plugin the current plugin
     * @return the profil list for the description
     */
    public static List<WssoProfil> findWssoProfilsByDescription( String strDescription, Plugin plugin )
    {
        return _dao.findWssoProfilsByDescription( strDescription, plugin );
    }

    /**
     *
     * Find profils for a user
     *
     * @param nWssoUserId the user id
     * @param plugin the current plugin
     * @return the profils's list for a user
     */
    public static List<String> findWssoProfilsForUser( int nWssoUserId, Plugin plugin )
    {
        return _dao.findWssoProfilsForUser( nWssoUserId, plugin );
    }

    /**
     * Check if the specified profil is assigned to a user
     *
     * @param strCode profil code
     * @param plugin the current plugin
     * @return true if the profil is assigned, else false
     */
    public static boolean checkProfilAssigned( String strCode, Plugin plugin )
    {
        return _dao.checkProfilAssigned( strCode, plugin );
    }
}
