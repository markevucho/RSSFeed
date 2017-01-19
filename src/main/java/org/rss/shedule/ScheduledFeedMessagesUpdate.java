package org.rss.shedule;


import org.rss.model.FeedMessage;
import org.rss.service.FeedMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.rss.parser.FeedMessageParser;

import java.util.List;

@Component
public class ScheduledFeedMessagesUpdate  {

    @Autowired
    FeedMessageService feedMessageService;

    @Scheduled(fixedRate = 1000*20)
    public void updateFeedMessages(){

        List<FeedMessage> feedMessageList=FeedMessageParser.parse("http://rozetka.com.ua/mobile-phones/apple/c80003/v069/");
        if(feedMessageList.size()!=0)
            feedMessageService.updateFeedMessages(feedMessageList);

    }

}
