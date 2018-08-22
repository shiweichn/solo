package org.b3log.solo.repository;

import org.b3log.latke.repository.Repository;
import org.b3log.latke.repository.RepositoryException;
import org.json.JSONObject;

import java.util.List;

public interface MoodRepository extends Repository {

	List<JSONObject> getMood(final int fetchSize)
			throws RepositoryException;

	List<JSONObject> getMoods(
			final int currentPageNum,
			final int pageSize) throws RepositoryException;
}
