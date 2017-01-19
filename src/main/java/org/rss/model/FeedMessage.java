package org.rss.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name="getAllFeedMessages",query="select feed from FeedMessage feed"),
        @NamedQuery(name="deleteAllFeedMessages",query="delete from FeedMessage feed")
})
@Table(name="RSSFeed")
public class FeedMessage{

private String title;
private String link;
private String description;
private Date pubDate;
private String guid;
private String enclosure;

public FeedMessage(){}

public FeedMessage(String title,String link,String description, Date pubDate,String guid, String enclosure){
this.title=title;
this.link=link;
this.description=description;
this.pubDate=pubDate;
this.guid=guid;
this.enclosure=enclosure;
}
    @Column(name="TITLE")
public String getTitle(){return title;}
    @Column(name="LINK")
public String getLink(){return link;}
    @Column(name="DESCRIPTION")
public String getDescription(){return description;}
    @Column(name="PUBDATE")
    @Temporal(TemporalType.DATE)
public Date getPubDate(){return pubDate;}
    @Id
    @Column(name="GUID")
public String getGuid(){return guid;}
    @Column(name="ENCLOSURE")
public String getEnclosure(){return enclosure;}

public void setTitle(String title){this.title=title; }
public void setLink(String link){this.link=link; }
public void setDescription(String description){this.description=description;}
public void setPubDate(Date pubDate){this.pubDate=pubDate;}
public void setGuid(String guide){this.guid=guide; }
public void setEnclosure(String enclosure){this.enclosure=enclosure; }

    @Override
    public String toString(){
        return "RSS message:\n"+title+"\n"+link+"\n"+description+"\n"+pubDate+"\n"+guid+"\n"+enclosure+"\n\n";
    }
}
