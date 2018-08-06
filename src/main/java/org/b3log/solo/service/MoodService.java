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

import org.b3log.latke.service.annotation.Service;
import org.b3log.solo.model.Mood;

import java.util.ArrayList;
import java.util.List;

@Service
public class MoodService {

	public List<Mood> getAll() {
		Mood m1 = new Mood("simon", "the first thing", System.currentTimeMillis(), null);
		Mood m2 = new Mood("chanwin", "the sec thing", System.currentTimeMillis(), null);
		List<Mood> moods = new ArrayList<>();
		moods.add(m1);
		moods.add(m2);
		return moods;
	}
}
