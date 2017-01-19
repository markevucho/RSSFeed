package org.rss.controller;

import org.rss.service.FeedMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.rss.model.FeedMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class FeedController {

    @Autowired
    private FeedMessageService feedMessageService;

    @RequestMapping(value="/rozetka/iphones/rss", method= RequestMethod.GET)
    public ModelAndView getRSSFeed(){

        List<FeedMessage> list=feedMessageService.getAllFeedMessages();
        ModelAndView model=new ModelAndView();
        model.setViewName("feedView");
        model.addObject("feedMessageList",list);

        return model;
    }

}
