package org.rss.view;


import com.rometools.rome.feed.rss.*;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.rss.model.FeedMessage;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class FeedViewer extends  AbstractRssFeedView {

    Logger log=Logger.getLogger(FeedViewer.class);

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed,
                                     HttpServletRequest request) {

        feed.setTitle("IPhones on Rozetka shop");
        feed.setDescription("IPhones in stock");
        feed.setLink("http://rozetka.com.ua/mobile-phones/apple/c80003/v069/");
        feed.setWebMaster("markevucho@gmail.com");
        feed.setPubDate(new Date());
        feed.setGenerator("RSSFeed Generator");

        super.buildFeedMetadata(model, feed, request);
    }
    @Override
    protected List<Item> buildFeedItems(Map<String,Object> model,HttpServletRequest request,HttpServletResponse response)
            throws Exception {
        response.setContentType("application/rss+xml;charset=utf-8");
        List<FeedMessage> feedMessageList = (List<FeedMessage>) model.get("feedMessageList");
        List<Item> items = new ArrayList<>(feedMessageList.size());

        for(FeedMessage message : feedMessageList){

            Item item = new Item();

            item.setTitle(message.getTitle());
            item.setLink(message.getLink());
            item.setPubDate(message.getPubDate());

            Description description=new Description();
            description.setType("text/xml");
            description.setValue(message.getDescription());
            item.setDescription(description);

            ArrayList<Enclosure> enclosures=new ArrayList<>();
            Enclosure enclosure=new Enclosure();
            enclosure.setType("image/jpeg");
            try {
                enclosure.setLength(new URL(message.getEnclosure()).openConnection().getContentLength());
            }catch(MalformedURLException e){
                enclosure.setLength(0);
                if(log.isInfoEnabled()) log.info("Error URL connection");
            }
            enclosure.setUrl(message.getEnclosure());
            enclosures.add(enclosure);
            item.setEnclosures(enclosures);

            Guid guid=new Guid();
            guid.setPermaLink(true);
            guid.setValue(message.getGuid());
            item.setGuid(guid);

            items.add(item);
        }
        return items;
    }

}
