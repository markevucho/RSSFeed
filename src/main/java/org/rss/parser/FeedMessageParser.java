package org.rss.parser;


import java.util.List;
import java.util.ArrayList;
import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.filters.*;
import org.rss.model.FeedMessage;


public class FeedMessageParser {

    public static List<FeedMessage> parse(String resource){

        List<FeedMessage> feedMessageList=new ArrayList<>();
        try {
            Parser parser = new Parser(resource);

            NodeList nodeList = parser.extractAllNodesThatMatch(
                    new AndFilter(new HasAttributeFilter("class", "g-i-tile-i-box-desc"),
                    new NotFilter(new HasChildFilter(new HasAttributeFilter("class", "g-i-status-wrap")))));

            FeedMessageDataVisitor visitor = new FeedMessageDataVisitor();

            for (NodeIterator it = nodeList.elements(); it.hasMoreNodes(); ) {
                NodeList childNodes = it.nextNode().getChildren();
                childNodes.visitAllNodesWith(visitor);

                feedMessageList.add(visitor.getFeedMessage());
            }
        }catch(ParserException e){
            e.printStackTrace();
        }
        return feedMessageList;

    }


}
