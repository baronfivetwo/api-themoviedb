package com.omertron.themoviedbapi.methods;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.Artwork;
import com.omertron.themoviedbapi.model.person.PersonMovieCredits;
import com.omertron.themoviedbapi.model.person.PersonMovieOld;
import com.omertron.themoviedbapi.model.type.ArtworkType;
import com.omertron.themoviedbapi.results.TmdbResultsList;
import com.omertron.themoviedbapi.tools.ApiUrl;
import static com.omertron.themoviedbapi.tools.ApiUrl.*;
import com.omertron.themoviedbapi.wrapper.WrapperImages;
import com.omertron.themoviedbapi.wrapper.person.WrapperPersonList;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.CommonHttpClient;

public class TmdbPeople extends AbstractMethod {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbPeople.class);
    // API URL Parameters
    private static final String BASE_PERSON = "person/";

    public TmdbPeople(String apiKey, CommonHttpClient httpClient) {
        super(apiKey, httpClient);
    }

    /**
     * This method is used to retrieve all of the basic person information.
     *
     * It will return the single highest rated profile image.
     *
     * @param personId
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    /**
     * This method is used to retrieve all of the basic person information.It will return the single highest rated profile image.
     *
     * @param personId
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public PersonMovieOld getPersonInfo(int personId, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON);

        apiUrl.addArgument(PARAM_ID, personId);
        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, PersonMovieOld.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get movie info: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the movie credits for a specific person id.
     *
     * @param personId
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public PersonMovieCredits getPersonMovieCredits(int personId, String language, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON, "/movie_credits");

        apiUrl.addArgument(PARAM_ID, personId);
        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }
        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            PersonMovieCredits pc = mapper.readValue(webpage, PersonMovieCredits.class);
            return pc;
        } catch (IOException ex) {
            LOG.warn("Failed to get movie credits: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the TV credits for a specific person id.
     * <p>
     * To get the expanded details for each record, call the /credit method with the provided credit_id.
     * <p>
     * This will provide details about which episode and/or season the credit is for.
     *
     * @param personId
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public PersonMovieCredits getPersonTvCredits(int personId, String language, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON, "/tv_credits");

        apiUrl.addArgument(PARAM_ID, personId);
        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }
        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            PersonMovieCredits pc = mapper.readValue(webpage, PersonMovieCredits.class);
            return pc;
        } catch (IOException ex) {
            LOG.warn("Failed to get TV credits: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the combined (movie and TV) credits for a specific person id.<p>
     * To get the expanded details for each record, call the /credit method with the provided credit_id.
     * <p>
     * This will provide details about which episode and/or season the credit is for.
     *
     * @param personId
     * @param language
     * @param appendToResponse
     * @return
     * @throws MovieDbException
     */
    public PersonMovieCredits getPersonCombinedCredits(int personId, String language, String... appendToResponse) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON, "/combined_credits");

        apiUrl.addArgument(PARAM_ID, personId);
        if (StringUtils.isNotBlank(language)) {
            apiUrl.addArgument(PARAM_LANGUAGE, language);
        }
        apiUrl.appendToResponse(appendToResponse);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            PersonMovieCredits pc = mapper.readValue(webpage, PersonMovieCredits.class);
            return pc;
        } catch (IOException ex) {
            LOG.warn("Failed to get combined credits: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * This method is used to retrieve all of the profile images for a person.
     *
     * @param personId
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<Artwork> getPersonImages(int personId) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON, "/images");

        apiUrl.addArgument(PARAM_ID, personId);

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperImages wrapper = mapper.readValue(webpage, WrapperImages.class);
            TmdbResultsList<Artwork> results = new TmdbResultsList<Artwork>(wrapper.getAll(ArtworkType.PROFILE));
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get person images: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the changes for a specific person id.
     *
     * Changes are grouped by key, and ordered by date in descending order.
     *
     * By default, only the last 24 hours of changes are returned.
     *
     * The maximum number of days that can be returned in a single request is 14.
     *
     * The language is present on fields that are translatable.
     *
     * @param personId
     * @param startDate
     * @param endDate
     * @return
     * @throws MovieDbException
     */
    public String getPersonChanges(int personId, String startDate, String endDate) throws MovieDbException {
        throw new MovieDbException(MovieDbException.MovieDbExceptionType.UNKNOWN_CAUSE, "Not implemented yet");
    }

    /**
     * Get the list of popular people on The Movie Database.
     *
     * This list refreshes every day.
     *
     * @param page
     * @return
     * @throws MovieDbException
     */
    public TmdbResultsList<PersonMovieOld> getPersonPopular(int page) throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON, "/popular");

        if (page > 0) {
            apiUrl.addArgument(PARAM_PAGE, page);
        }

        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            WrapperPersonList wrapper = mapper.readValue(webpage, WrapperPersonList.class);
            TmdbResultsList<PersonMovieOld> results = new TmdbResultsList<PersonMovieOld>(wrapper.getPersonList());
            results.copyWrapper(wrapper);
            return results;
        } catch (IOException ex) {
            LOG.warn("Failed to get person images: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

    /**
     * Get the latest person id.
     *
     * @return
     * @throws MovieDbException
     */
    public PersonMovieOld getPersonLatest() throws MovieDbException {
        ApiUrl apiUrl = new ApiUrl(apiKey, BASE_PERSON, "/latest");
        URL url = apiUrl.buildUrl();
        String webpage = requestWebPage(url);

        try {
            return mapper.readValue(webpage, PersonMovieOld.class);
        } catch (IOException ex) {
            LOG.warn("Failed to get latest person: {}", ex.getMessage());
            throw new MovieDbException(MovieDbException.MovieDbExceptionType.MAPPING_FAILED, webpage, ex);
        }
    }

}
