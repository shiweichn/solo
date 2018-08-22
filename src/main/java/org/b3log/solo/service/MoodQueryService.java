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
package org.b3log.solo.service;

import org.b3log.latke.Keys;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.util.CollectionUtils;
import org.b3log.solo.repository.MoodRepository;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

@Service
public class MoodQueryService {

	private static final Logger LOGGER = Logger.getLogger(MoodQueryService.class);
	@Inject
	private MoodRepository moodRepository;


	public List<JSONObject> getMoods() throws ServiceException {
		try {
			final Query query = new Query().setPageCount(1);

			final JSONObject result = moodRepository.get(query);
			final JSONArray tagArray = result.optJSONArray(Keys.RESULTS);

			return CollectionUtils.jsonArrayToList(tagArray);
		} catch (final RepositoryException e) {
			LOGGER.log(Level.ERROR, "Gets tags failed", e);

			throw new ServiceException(e);
		}
	}
}
