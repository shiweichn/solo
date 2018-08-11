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

import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.DoNothingRenderer;
import org.b3log.latke.servlet.renderer.freemarker.AbstractFreeMarkerRenderer;
import org.b3log.latke.util.Strings;
import org.b3log.solo.SoloServletListener;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Mood;
import org.b3log.solo.model.Option;
import org.b3log.solo.model.Skin;
import org.b3log.solo.processor.renderer.SkinRenderer;
import org.b3log.solo.processor.util.Filler;
import org.b3log.solo.service.MoodService;
import org.b3log.solo.service.PreferenceQueryService;
import org.b3log.solo.util.Skins;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

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
    private MoodService moodService;
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
        List<Mood> moods = moodService.getAll();
        dataModel.put("moods", moods);
        dataModel.put("test", "testValue");

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
}
