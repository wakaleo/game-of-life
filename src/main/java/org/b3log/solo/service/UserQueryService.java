/*
 * Copyright (c) 2010-2018, b3log.org & hacpai.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.b3log.solo.service;


import org.b3log.latke.Keys;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Pagination;
import org.b3log.latke.model.User;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.user.GeneralUser;
import org.b3log.latke.user.UserService;
import org.b3log.latke.user.UserServiceFactory;
import org.b3log.latke.util.Paginator;
import org.b3log.solo.repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * User query service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.3, Jul 10, 2013
 * @since 0.4.0
 */
@Service
public class UserQueryService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(UserQueryService.class);

    /**
     * User service.
     */
    private UserService userService = UserServiceFactory.getUserService();

    /**
     * User repository.
     */
    @Inject
    private UserRepository userRepository;

    /**
     * User management service.
     */
    @Inject
    private UserMgmtService userMgmtService;

    /**
     * Determines whether if exists multiple users in current Solo.
     *
     * @return {@code true} if exists, {@code false} otherwise
     * @throws ServiceException service exception
     */
    public boolean hasMultipleUsers() throws ServiceException {
        final Query query = new Query().setPageCount(1);

        try {
            final JSONArray users = userRepository.get(query).getJSONArray(Keys.RESULTS);

            return 1 != users.length();
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Determines multiple users failed", e);

            throw new ServiceException(e);
        } catch (final JSONException e) {
            LOGGER.log(Level.ERROR, "Determines multiple users failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Checks whether the current request is made by a logged in user
     * (including default user and administrator lists in <i>users</i>).
     * 
     * <p>
     * Invokes this method will try to login with cookie first.
     * </p>
     *
     * @param request the specified request
     * @param response the specified response
     * @return {@code true} if the current request is made by logged in user,
     * returns {@code false} otherwise
     */
    public boolean isLoggedIn(final HttpServletRequest request, final HttpServletResponse response) {
        userMgmtService.tryLogInWithCookie(request, response);

        final GeneralUser currentUser = userService.getCurrentUser(request);

        return null != currentUser;
    }

    /**
     * Checks whether the current request is made by logged in administrator.
     *
     * @param request the specified request
     * @return {@code true} if the current request is made by logged in
     * administrator, returns {@code false} otherwise
     */
    public boolean isAdminLoggedIn(final HttpServletRequest request) {
        return userService.isUserLoggedIn(request) && userService.isUserAdmin(request);
    }

    /**
     * Gets the current user.
     *
     * @param request the specified request
     * @return the current user, {@code null} if not found
     */
    public JSONObject getCurrentUser(final HttpServletRequest request) {
        final GeneralUser currentUser = userService.getCurrentUser(request);

        if (null == currentUser) {
            return null;
        }

        final String email = currentUser.getEmail();

        try {
            return userRepository.getByEmail(email);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets current user by request failed, returns null", e);

            return null;
        }
    }

    /**
     * Gets the administrator.
     * 
     * @return administrator, returns {@code null} if not found
     * @throws ServiceException service exception
     */
    public JSONObject getAdmin() throws ServiceException {
        try {
            return userRepository.getAdmin();
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets admin failed", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Gets a user by the specified email.
     *
     * @param email the specified email
     * @return user, returns {@code null} if not found
     * @throws ServiceException service exception
     */
    public JSONObject getUserByEmail(final String email) throws ServiceException {
        try {
            return userRepository.getByEmail(email);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets user by email[" + email + "] failed", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Gets users by the specified request json object.
     *
     * @param requestJSONObject the specified request json object, for example,
     * <pre>
     * {
     *     "paginationCurrentPageNum": 1,
     *     "paginationPageSize": 20,
     *     "paginationWindowSize": 10,
     * }, see {@link Pagination} for more details
     * </pre>
     * @return for example,
     * <pre>
     * {
     *     "pagination": {
     *         "paginationPageCount": 100,
     *         "paginationPageNums": [1, 2, 3, 4, 5]
     *     },
     *     "users": [{
     *         "oId": "",
     *         "userName": "",
     *         "userEmail": "",
     *         "userPassword": "",
     *         "roleName": ""
     *      }, ....]
     * }
     * </pre>
     * @throws ServiceException service exception
     * @see Pagination
     */
    public JSONObject getUsers(final JSONObject requestJSONObject) throws ServiceException {
        final JSONObject ret = new JSONObject();

        final int currentPageNum = requestJSONObject.optInt(Pagination.PAGINATION_CURRENT_PAGE_NUM);
        final int pageSize = requestJSONObject.optInt(Pagination.PAGINATION_PAGE_SIZE);
        final int windowSize = requestJSONObject.optInt(Pagination.PAGINATION_WINDOW_SIZE);
        final Query query = new Query().setCurrentPageNum(currentPageNum).setPageSize(pageSize);

        JSONObject result = null;

        try {
            result = userRepository.get(query);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets users failed", e);

            throw new ServiceException(e);
        }

        final int pageCount = result.optJSONObject(Pagination.PAGINATION).optInt(Pagination.PAGINATION_PAGE_COUNT);

        final JSONObject pagination = new JSONObject();

        ret.put(Pagination.PAGINATION, pagination);
        final List<Integer> pageNums = Paginator.paginate(currentPageNum, pageSize, pageCount, windowSize);

        pagination.put(Pagination.PAGINATION_PAGE_COUNT, pageCount);
        pagination.put(Pagination.PAGINATION_PAGE_NUMS, pageNums);

        final JSONArray users = result.optJSONArray(Keys.RESULTS);

        ret.put(User.USERS, users);

        return ret;
    }

    /**
     * Gets a user by the specified user id.
     *
     * @param userId the specified user id
     * @return for example,
     * <pre>
     * {
     *     "user": {
     *         "oId": "",
     *         "userName": "",
     *         "userEmail": "",
     *         "userPassword": ""
     *     }
     * }
     * </pre>, returns {@code null} if not found
     * @throws ServiceException service exception
     */
    public JSONObject getUser(final String userId) throws ServiceException {
        final JSONObject ret = new JSONObject();

        JSONObject user = null;

        try {
            user = userRepository.get(userId);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets a user failed", e);
            throw new ServiceException(e);
        }

        if (null == user) {
            return null;
        }

        ret.put(User.USER, user);

        return ret;
    }

    /**
     * Gets the URL of user logout.
     *
     * @return logout URL, returns {@code null} if the user is not logged in
     */
    public String getLogoutURL() {
        return userService.createLogoutURL("/");
    }

    /**
     * Gets the URL of user login.
     *
     * @param redirectURL redirect URL after logged in
     * @return login URL
     */
    public String getLoginURL(final String redirectURL) {
        return userService.createLoginURL(redirectURL);
    }

    /**
     * Sets the user management service with the specified user management service.
     * 
     * @param userMgmtService the specified user management service
     */
    public void setUserMgmtService(final UserMgmtService userMgmtService) {
        this.userMgmtService = userMgmtService;
    }

    /**
     * Sets the user repository with the specified user repository.
     * 
     * @param userRepository the specified user repository
     */
    public void setUserRepository(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
