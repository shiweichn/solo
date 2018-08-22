package org.b3log.solo.repository.impl;

import org.b3log.latke.Keys;
import org.b3log.latke.repository.AbstractRepository;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.SortDirection;
import org.b3log.latke.repository.annotation.Repository;
import org.b3log.latke.util.CollectionUtils;
import org.b3log.solo.model.Mood;
import org.b3log.solo.repository.MoodRepository;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

@Repository
public class MoodRepositoryImpl extends AbstractRepository implements MoodRepository {

	/**
	 * Constructs a repository with the specified name.
	 */
	public MoodRepositoryImpl() {
		super(Mood.MOOD);
	}

	@Override
	public List<JSONObject> getMood(int fetchSize) throws RepositoryException {
		return null;
	}

	@Override
	public List<JSONObject> getMoods(int currentPageNum, int pageSize) throws RepositoryException {
		final Query query = new Query().
				addSort(Keys.OBJECT_ID, SortDirection.DESCENDING).
				setCurrentPageNum(currentPageNum).setPageSize(pageSize).setPageCount(1);

		final JSONObject result = get(query);
		final JSONArray array = result.optJSONArray(Keys.RESULTS);

		return CollectionUtils.jsonArrayToList(array);
	}
}
