package org.b3log.solo.service;

import org.b3log.latke.Keys;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.solo.repository.MoodRepository;
import org.json.JSONObject;

@Service
public class MoodMgmtService {
	private static final Logger LOGGER = Logger.getLogger(UserMgmtService.class);
	@Inject
	private MoodRepository moodRepository;

	public String addUser(final JSONObject requestJSONObject) throws ServiceException {
		final Transaction transaction = moodRepository.beginTransaction();

		try {
			final JSONObject mood = new JSONObject();
			final String author = "Simon";
			final String moodContent = "666666666666666666666666666666666666666666666666666666666666666666";
			final String createTime = "2018年8月22日17点00分";
			mood.put("author", author);
			mood.put("moodContent", moodContent);
			mood.put("createTime", createTime);

			moodRepository.add(mood);
			transaction.commit();

			return mood.optString(Keys.OBJECT_ID);
		} catch (final RepositoryException e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			LOGGER.log(Level.ERROR, "Adds a user failed", e);
			throw new ServiceException(e);
		}
	}
}
