package org.rss.test;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rss.parser.FeedMessageParser;
import org.rss.service.FeedMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_CLASS)
@WebAppConfiguration
@ContextConfiguration(locations = "/META-INF/test-spring-context.xml")
public class ITRssFeed {

    @Autowired
    private FeedMessageService feedMessageService;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webAppContext;

    @Before
    public void loadFeedMessagesToDatabase(){

            feedMessageService.updateFeedMessages(FeedMessageParser.parse("src/integration-test/resources/META-INF/TestHtml.html"));
    }

    @Before
    public void initMockMvc(){
        mockMvc= MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    public void testRssFeed() throws Exception{

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/rozetka/iphones").characterEncoding("utf-8"));
        response.andExpect(content().contentType("application/rss+xml"))
                .andExpect(xpath("//rss/channel/title").string("IPhones on Rozetka shop"))
                .andExpect(xpath("//rss/channel/description").string("IPhones in stock"))
                .andExpect(xpath("//rss/channel/link").string("http://rozetka.com.ua/mobile-phones/apple/c80003/v069/"))
                .andExpect(xpath("//rss/channel/webMaster").string("markevucho@gmail.com"))
                .andExpect(xpath("//rss/channel/generator").string("RSSFeed Generator"))
                .andExpect(xpath("//rss/channel/item").nodeCount(2));

        response.andExpect(xpath("//rss/channel/item[1]/link").string("link1"))
                .andExpect(xpath("//rss/channel/item[1]/guid").string("link1"))
                .andExpect(xpath("//rss/channel/item[1]/title").string("title1"))
                .andExpect(xpath("//rss/channel/item[1]/enclosure/@url").string("enclosure1"));

        response.andExpect(xpath("//rss/channel/item[2]/link").string("link2"))
                .andExpect(xpath("//rss/channel/item[2]/guid").string("link2"))
                .andExpect(xpath("//rss/channel/item[2]/title").string("title2"))
                .andExpect(xpath("//rss/channel/item[2]/enclosure/@url").string("enclosure2"));


    }


}
