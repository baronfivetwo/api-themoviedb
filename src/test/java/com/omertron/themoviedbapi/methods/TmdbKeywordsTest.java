/*
 *      Copyright (c) 2004-2015 Stuart Boston
 *
 *      This file is part of TheMovieDB API.
 *
 *      TheMovieDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      TheMovieDB API is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with TheMovieDB API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.omertron.themoviedbapi.methods;

import com.omertron.themoviedbapi.AbstractTests;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.Keyword;
import com.omertron.themoviedbapi.model.KeywordMovie;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author stuart.boston
 */
public class TmdbKeywordsTest extends AbstractTests{

    private static TmdbKeywords tmdb;
    private static final String ID_KEYWORD = "1721";

    public TmdbKeywordsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws MovieDbException {
        doConfiguration();
        tmdb = new TmdbKeywords(getApiKey(),getHttpTools());
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getKeyword method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetKeyword() throws MovieDbException {
        LOG.info("getKeyword");
        Keyword result = tmdb.getKeyword(ID_KEYWORD);
        assertEquals("fight", result.getName());
    }

    /**
     * Test of getMovieDbBasics method, of class TheMovieDbApi.
     *
     * @throws MovieDbException
     */
    @Test
    public void testGetKeywordMovies() throws MovieDbException {
        LOG.info("getKeywordMovies");
        int page = 0;
        TmdbResultsList<KeywordMovie> result = tmdb.getKeywordMovies(ID_KEYWORD, LANGUAGE_DEFAULT, page);
        assertFalse("No keyword movies found", result.isEmpty());
    }

}