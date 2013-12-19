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

import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.url.UrlItem;

import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;


/**
 * This class provides a filter for users search function
 */
public class WssoProfilFilter
{
    // Constants
    private static final String EQUAL = "=";
    private static final String AMPERSAND = "&";

    // Parameteres
    private static final String PARAMETER_SEARCH_CODE = "search_code";
    private static final String PARAMETER_SEARCH_DESCRIPTION = "search_description";
    private static final String PARAMETER_SEARCH_IS_SEARCH = "search_is_search";

    // Properties
    private static final String PROPERTY_ENCODING_URL = "lutece.encoding.url";
    private String _strCode;
    private String _strdescription;

    /**
     * Constructor
     */
    public WssoProfilFilter(  )
    {
    }

    /**
     * Initialize each component of the object
     */
    public void init(  )
    {
        _strCode = "";
        _strdescription = "";
    }

    /**
     * Get the code
     * @return The code
     */
    public String getCode(  )
    {
        return _strCode;
    }

    /**
     * Set the code
     * @param strCode The Code
     */
    public void setCode( String strCode )
    {
        _strCode = strCode;
    }

    /**
     * Get the
     * @return The description
     */
    public String getDescription(  )
    {
        return _strdescription;
    }

    /**
     * Set the descprition
     * @param strdescription The description
     */
    public void setLastName( String strdescription )
    {
        _strdescription = strdescription;
    }

    /**
     * Set the value of the AdminUserFilter
     * @param request HttpServletRequest
     * @return true if there is a search
     */
    public boolean setDatabaseUserFilter( HttpServletRequest request )
    {
        boolean bIsSearch = false;
        String strIsSearch = request.getParameter( PARAMETER_SEARCH_IS_SEARCH );

        if ( strIsSearch != null )
        {
            bIsSearch = true;
            _strCode = request.getParameter( PARAMETER_SEARCH_CODE );
            _strdescription = request.getParameter( PARAMETER_SEARCH_DESCRIPTION );
        }
        else
        {
            init(  );
        }

        return bIsSearch;
    }

    /**
     * Build url attributes
     * @param url the url
     */
    public void setUrlAttributes( UrlItem url )
    {
        url.addParameter( PARAMETER_SEARCH_IS_SEARCH, Boolean.TRUE.toString(  ) );

        try
        {
            url.addParameter( PARAMETER_SEARCH_CODE,
                URLEncoder.encode( _strCode, AppPropertiesService.getProperty( PROPERTY_ENCODING_URL ) ) );
            url.addParameter( PARAMETER_SEARCH_DESCRIPTION,
                URLEncoder.encode( _strdescription, AppPropertiesService.getProperty( PROPERTY_ENCODING_URL ) ) );
        }
        catch ( UnsupportedEncodingException e )
        {
            AppLogService.error( e );
        }
    }

    /**
     * Build url attributes
     * @return the url attributes
     */
    public String getUrlAttributes(  )
    {
        StringBuilder sbUrlAttributes = new StringBuilder(  );
        sbUrlAttributes.append( PARAMETER_SEARCH_IS_SEARCH + EQUAL + Boolean.TRUE );

        try
        {
            sbUrlAttributes.append( AMPERSAND + PARAMETER_SEARCH_CODE + EQUAL +
                URLEncoder.encode( _strCode, AppPropertiesService.getProperty( PROPERTY_ENCODING_URL ) ) );
            sbUrlAttributes.append( AMPERSAND + PARAMETER_SEARCH_DESCRIPTION + EQUAL +
                URLEncoder.encode( _strdescription, AppPropertiesService.getProperty( PROPERTY_ENCODING_URL ) ) );
        }
        catch ( UnsupportedEncodingException e )
        {
            AppLogService.error( e );
        }

        return sbUrlAttributes.toString(  );
    }
}
