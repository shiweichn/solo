package org.b3log.solo.cache;

import org.b3log.latke.Keys;
import org.b3log.latke.cache.Cache;
import org.b3log.latke.cache.CacheFactory;
import org.b3log.latke.ioc.inject.Named;
import org.b3log.latke.ioc.inject.Singleton;
import org.b3log.solo.model.Mood;
import org.b3log.solo.util.JSONs;
import org.json.JSONObject;

@Named
@Singleton
public class MoodCache {
	private Cache cache = CacheFactory.getCache(Mood.MOODS);

	public void putMood(final JSONObject mood) {
		cache.put(mood.optString(Keys.OBJECT_ID), JSONs.clone(mood));
	}

	public JSONObject getMood(final String id) {
		final JSONObject mood = cache.get(id);
		if (null == mood) {
			return null;
		}

		return JSONs.clone(mood);
	}
}
