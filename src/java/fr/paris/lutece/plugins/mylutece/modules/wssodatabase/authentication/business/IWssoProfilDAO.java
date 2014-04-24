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

import java.util.List;


/**
 *
 * @author ehelouaret
 *
 */
public interface IWssoProfilDAO
{
    /**
     * Insert a new record in the table.
     *
     * @param wssoProfil The wssoProfil object
     * @param plugin The Plugin using this data access service
     */
    void insert( WssoProfil wssoProfil, Plugin plugin );

    /**
     * Load the data of WssoProfil from the table
     *
     * @param nWssoProfilCode The identifier of WssoProfil
     * @param plugin The Plugin using this data access service
     * @return the instance of the WssoProfil
     */
    WssoProfil load( String nWssoProfilCode, Plugin plugin );

    /**
     * Delete a record from the table
     * @param wssoProfil The WssoProfil object
     * @param plugin The Plugin using this data access service
     */
    void delete( WssoProfil wssoProfil, Plugin plugin );

    /**
     * Update the record in the table
     * @param wssoProfil The reference of wssoProfil
     * @param plugin The Plugin using this data access service
     */
    void store( WssoProfil wssoProfil, Plugin plugin );

    /**
     * Load the list of wssoProfils
     * @param plugin The Plugin using this data access service
     * @return The Collection of the WssoProfils
     */
    List<WssoProfil> selectWssoProfilList( Plugin plugin );

    /**
     * Load the list of wssoProfils for a role
     * @param nIdRole The role of WssoProfil
     * @param plugin The Plugin using this data access service
     * @return The Collection of the WssoProfils
     */

    //	Collection selectWssoProfilsListForRole( int nIdRole, Plugin plugin );

    //	 WssoProfil findWssoProfilByCode( String strCode, Plugin plugin );

    /**
     * Find profil by code and description
     *
     * @param strCode the code
     * @param strDescription the description
     * @param plugin the current plugin
     * @return the profil
     */
    WssoProfil findWssoProfilByCodeAndDescription( String strCode, String strDescription, Plugin plugin );

    /**
     * Find the profils by description
     *
     * @param strDescription the description
     * @param plugin the current plugin
     * @return the profil list for the description
     */
    List<WssoProfil> findWssoProfilsByDescription( String strDescription, Plugin plugin );

    /**
     *
     * Find profils for a user
     *
     * @param nWssoUserId the user id
     * @param plugin the current plugin
     * @return the profils's list for a user
     */
    List<String> findWssoProfilsForUser( int nWssoUserId, Plugin plugin );

    /**
     * Check if the specified profil is assigned to a user
     *
     * @param strCode profil code
     * @param plugin the current plugin
     * @return true if the profil is assigned, else false
     */
    boolean checkProfilAssigned( String strCode, Plugin plugin );
}
