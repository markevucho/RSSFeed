package org.rss.service;

import org.rss.model.FeedMessage;
import java.util.List;


public interface FeedMessageService {

    List<FeedMessage> getAllFeedMessages();
    void updateFeedMessages(List<FeedMessage> list);

}
