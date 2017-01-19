package org.rss.parser;

import org.htmlparser.util.NodeList;
import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.filters.*;
import org.htmlparser.visitors.*;
import java.util.Date;
import org.rss.model.FeedMessage;

public class FeedMessageDataVisitor extends NodeVisitor{

    private FeedMessage feedMessage;
    private StringBuilder descriptionBuilder;

    public FeedMessageDataVisitor(){super(); }
    public FeedMessageDataVisitor(boolean recurseChildren){ super(recurseChildren); }
    public FeedMessageDataVisitor(boolean recurseChildren, boolean recurseSelf){ super(recurseChildren,recurseSelf); }


    public void beginParsing(){
        feedMessage=new FeedMessage();
        descriptionBuilder=new StringBuilder();
    }

    public FeedMessage getFeedMessage(){
        if(feedMessage!=null) return feedMessage;
        else return null;
    }

    public void setFeedMessage(FeedMessage feedMessage){this.feedMessage=feedMessage; }


    public void visitTag(Tag tag){

        if(tag.getTagName().equals("DIV") && tag.getAttribute("CLASS")!=null){

            switch(tag.getAttribute("class")){

                case "g-i-tile-i-title clearfix":
                    Node node=tag.getChildren().extractAllNodesThatMatch(new TagNameFilter("a")).elementAt(0);
                    tag=(Tag)node;
                    feedMessage.setLink(tag.getAttribute("href"));
                    feedMessage.setTitle(tag.getChildren().toHtml().trim());
                    feedMessage.setGuid(feedMessage.getLink());
                    feedMessage.setPubDate(new Date());
                    break;

                case "clearfix":
                    NodeList imgNodeList=tag.getChildren().extractAllNodesThatMatch(new HasAttributeFilter("tool_tip","1"));
                    if(imgNodeList.size()!=0){
                        Tag img=(Tag)imgNodeList.elementAt(0).getChildren().extractAllNodesThatMatch(new TagNameFilter("a")).elementAt(0).getChildren().elementAt(1);
                        feedMessage.setEnclosure(img.getAttribute("data_src"));
                        descriptionBuilder.append("<img src=\""+img.getAttribute("data_src")+"\"><br>");
                    }
                    break;

                case "inline":
                    String attribute;
                    if((attribute=tag.getAttribute("name"))!=null && attribute.equals("prices_active_element_original")){
                        NodeList priceNodeList=tag.getChildren().extractAllNodesThatMatch(new HasAttributeFilter("class","inline g-addprice-text"));
                        if(priceNodeList.size()!=0){
                            Node span=priceNodeList.elementAt(0).getChildren().extractAllNodesThatMatch(new TagNameFilter("span")).elementAt(0).getChildren().elementAt(0);
                            descriptionBuilder.append("Цена по программе обмена: "+span.toHtml()+" грн.<br>");
                        }
                    }
                    break;
            }

        }
        else if(tag.getTagName().equals("UL") && tag.getAttribute("class").equals("g-i-tile-short-detail")){
            Node node=tag.getChildren().extractAllNodesThatMatch(new TagNameFilter("li")).elementAt(0).getChildren().elementAt(0);
            descriptionBuilder.append("<p align=\"justify\">"+node.toHtml()+"</p>");
            feedMessage.setDescription(descriptionBuilder.toString());

        }

    }



}
