package org.rss.service.impl;

import org.rss.model.FeedMessage;
import org.rss.service.FeedMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Service
@Transactional
public class FeedMessageServiceImpl implements FeedMessageService {

    @PersistenceContext
    private EntityManager em;
    

   public List<FeedMessage> getAllFeedMessages(){
    TypedQuery<FeedMessage> query=em.createNamedQuery("getAllFeedMessages",FeedMessage.class);
	return query.getResultList();
    }

   public void updateFeedMessages(List<FeedMessage> messageList) {

           em.createNamedQuery("deleteAllFeedMessages").executeUpdate();
           for (FeedMessage feedMessage : messageList)
                    em.persist(feedMessage);
           }

}
