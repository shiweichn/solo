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
package org.b3log.solo.processor.renderer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.renderer.freemarker.AbstractFreeMarkerRenderer;
import org.b3log.solo.SoloServletListener;

import javax.servlet.ServletContext;
import java.io.IOException;

public class MoodRenderer extends AbstractFreeMarkerRenderer {
	/**
	 * FreeMarker configuration.
	 */
	public static final Configuration TEMPLATE_CFG;

	/**
	 * Logger.
	 */
	private static final Logger logger = Logger.getLogger(MoodRenderer.class);

	static {
		TEMPLATE_CFG = new Configuration(Configuration.VERSION_2_3_28);
		TEMPLATE_CFG.setDefaultEncoding("UTF-8");
		final ServletContext servletContext = SoloServletListener.getServletContext();
		TEMPLATE_CFG.setServletContextForTemplateLoading(servletContext, "/skins/Medium/");
		TEMPLATE_CFG.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		TEMPLATE_CFG.setLogTemplateExceptions(true);
	}

	@Override
	public Template getTemplate(String templateDirName, String templateName) {
		try {
			Template template = TEMPLATE_CFG.getTemplate(templateName);
			return template;
		} catch (final IOException e) {
			return null;
		}
	}

	@Override
	protected void beforeRender(HTTPRequestContext context) throws Exception {

	}

	@Override
	protected void afterRender(HTTPRequestContext context) throws Exception {

	}
}
