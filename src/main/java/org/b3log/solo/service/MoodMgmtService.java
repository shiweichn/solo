package org.b3log.solo.service;

import org.b3log.latke.Keys;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.util.Ids;
import org.b3log.latke.util.Strings;
import org.b3log.solo.repository.MoodRepository;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

@Service
public class MoodMgmtService {
    private static final Logger LOGGER = Logger.getLogger(UserMgmtService.class);
    @Inject
    private MoodRepository moodRepository;

    public String addMood(final JSONObject requestJSONObject) throws ServiceException, JSONException {
        final Transaction transaction = moodRepository.beginTransaction();
        JSONObject mood = requestJSONObject.getJSONObject("mood");
        String ret = mood.optString(Keys.OBJECT_ID);

        if (Strings.isEmptyOrNull(ret)) {
            ret = Ids.genTimeMillisId();
            mood.put(Keys.OBJECT_ID, ret);
        }
        try {
            moodRepository.add(mood);
            transaction.commit();
            LOGGER.info("add mood success: " + mood);
            return mood.optString(Keys.OBJECT_ID);
        } catch (RepositoryException e) {
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback();
            }
            LOGGER.log(Level.ERROR, "Adds a user failed", e);
            throw new ServiceException(e);
        }
    }
}
