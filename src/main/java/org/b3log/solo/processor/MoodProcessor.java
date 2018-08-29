/*
 * Solo - A beautiful, simple, stable, fast Java blogging system.
 * Copyright (c) 2010-2018, b3log.org & hacpai.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.b3log.solo.processor;

import jodd.util.CollectionUtil;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.User;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.DoNothingRenderer;
import org.b3log.latke.servlet.renderer.JSONRenderer;
import org.b3log.latke.servlet.renderer.freemarker.AbstractFreeMarkerRenderer;
import org.b3log.latke.util.CollectionUtils;
import org.b3log.latke.util.Strings;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Mood;
import org.b3log.solo.model.Option;
import org.b3log.solo.model.UserExt;
import org.b3log.solo.processor.renderer.ConsoleRenderer;
import org.b3log.solo.processor.renderer.SkinRenderer;
import org.b3log.solo.processor.util.Filler;
import org.b3log.solo.service.MoodMgmtService;
import org.b3log.solo.service.MoodQueryService;
import org.b3log.solo.service.PreferenceQueryService;
import org.b3log.solo.service.UserQueryService;
import org.b3log.solo.util.Skins;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequestProcessor
public class MoodProcessor {

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(MoodProcessor.class);
    /**
     * Option management service.
     */
    @Inject
    private MoodQueryService moodQueryService;

    @Inject
    private MoodMgmtService moodMgmtService;
    /**
     * Filler.
     */
    @Inject
    private Filler filler;
    /**
     * Preference query service.
     */
    @Inject
    private PreferenceQueryService preferenceQueryService;

    @Inject
    private UserQueryService userQueryService;
    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    @RequestProcessing(value = "/plugins/checkMood.do", method = HTTPRequestMethod.GET)
    public void checkMood(final HTTPRequestContext context) {
        DoNothingRenderer nothingRenderer = new DoNothingRenderer();
        nothingRenderer.render(context);
        context.setRenderer(nothingRenderer);
    }

    @RequestProcessing(value = "/mood/showMood.do", method = HTTPRequestMethod.GET)
    public void showMood(final HTTPRequestContext context) throws IOException, JSONException, ServiceException {
        final HttpServletRequest request = context.getRequest();
        final HttpServletResponse response = context.getResponse();

        String destinationURL = request.getParameter(Common.GOTO);
        if (Strings.isEmptyOrNull(destinationURL)) {
            destinationURL = Latkes.getServePath() + Common.ADMIN_INDEX_URI;
        } else if (!isInternalLinks(destinationURL)) {
            destinationURL = "/";
        }

        renderPage(context, "mood.ftl", destinationURL, request);
    }

    private void renderPage(final HTTPRequestContext context, final String pageTemplate, final String destinationURL,
                            final HttpServletRequest request) throws JSONException, ServiceException {
        JSONObject preference = preferenceQueryService.getPreference();
        final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context.getRequest());
        renderer.setTemplateName(pageTemplate);
        Map<String, Object> dataModel = renderer.getDataModel();
        List<JSONObject> moods = moodQueryService.getMoods();

        Collections.reverse(moods);
        for (JSONObject mood : moods) {
            JSONObject user = userQueryService.getUserByEmail(mood.getString(User.USER_EMAIL));
            if (user != null) {
                mood.put(Mood.AUTHOR, user.getString(User.USER_NAME));
                mood.put(UserExt.USER_AVATAR, user.getString(UserExt.USER_AVATAR));
            }
        }

        dataModel.put("moods", moods);
        logger.info(moods.toString());
        filler.fillMinified(dataModel);
        Skins.fillLangs(preference.optString(Option.ID_C_LOCALE_STRING), (String) request.getAttribute(Keys.TEMAPLTE_DIR_NAME), dataModel);
        filler.fillSide(request, dataModel, preference);
        filler.fillBlogHeader(request, context.getResponse(), dataModel, preference);
        filler.fillBlogFooter(request, dataModel, preference);
        Keys.fillRuntime(dataModel);
        renderer.render(context);
        context.setRenderer(renderer);
    }

    private boolean isInternalLinks(String destinationURL) {
        return destinationURL.startsWith(Latkes.getServePath());
    }

    @RequestProcessing(value = "/admin-moods.do", method = HTTPRequestMethod.GET)
    public void showMoodsManager(final HTTPRequestContext context) {
        final AbstractFreeMarkerRenderer renderer = new ConsoleRenderer();
        context.setRenderer(renderer);
        renderer.setTemplateName("admin-mood.ftl");

        final Locale locale = Latkes.getLocale();
        final Map<String, String> langs = langPropsService.getAll(locale);
        final Map<String, Object> dataModel = renderer.getDataModel();

        dataModel.putAll(langs);
        Keys.fillRuntime(dataModel);
        dataModel.put(Option.ID_C_LOCALE_STRING, locale.toString());
    }

    @RequestProcessing(value = "/mood/addMood.do", method = HTTPRequestMethod.POST)
    public void addMood(final HTTPRequestContext context, final HttpServletRequest request, final HttpServletResponse response,
                        final JSONObject requestJSONObject) throws ServiceException, IOException, JSONException {
        JSONRenderer renderer = new JSONRenderer();
        JSONObject jsonObject = new JSONObject();

        if (!userQueryService.isLoggedIn(request, response)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        final JSONObject currentUser = userQueryService.getCurrentUser(request);
        requestJSONObject.getJSONObject(Mood.MOOD).put(User.USER_EMAIL, currentUser.getString(User.USER_EMAIL));
        requestJSONObject.getJSONObject(Mood.MOOD).put(Mood.CREATE_TIME, new Date());

        moodMgmtService.addMood(requestJSONObject);

        jsonObject.put(Keys.STATUS_CODE, true);
        renderer.setJSONObject(jsonObject);
        renderer.render(context);
        context.setRenderer(renderer);
    }

    @RequestProcessing(value = "/test.do", method = HTTPRequestMethod.GET)
    public void test(final HTTPRequestContext context) throws ServiceException {
        JSONRenderer renderer = new JSONRenderer();
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(Keys.STATUS_CODE, true);
        renderer.setJSONObject(jsonObject);
        renderer.render(context);
        context.setRenderer(renderer);
    }
}
