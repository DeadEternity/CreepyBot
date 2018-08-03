package org.deadeternity.telegrambot;

import org.telegram.telegraph.ExecutorOptions;
import org.telegram.telegraph.TelegraphContext;
import org.telegram.telegraph.TelegraphContextInitializer;
import org.telegram.telegraph.api.methods.CreateAccount;
import org.telegram.telegraph.api.methods.CreatePage;
import org.telegram.telegraph.api.objects.*;
import org.telegram.telegraph.exceptions.TelegraphException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreepyPaste {
    private String title;
    private ArrayList<String> text;
    private Integer rating;
    private Integer votes;
    private String link;
    private String telegraphLink;
    private Integer pages;
    private ArrayList<String> tags;

    public CreepyPaste() {
        pages = 1;
        text = new ArrayList<String>();
        tags = new ArrayList<String>();
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getText() {
        return text;
    }

    public Integer getRating() {
        return rating;
    }

    public Integer getVotes() {
        return votes;
    }

    public String getLink() {
        return link;
    }

    public String getTelegraphLink() {
        return telegraphLink;
    }

    public Integer getPages() {
        return pages;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addText(String textPart) {
        this.text.add(textPart);
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public ArrayList<String> getTags() {
        return this.tags;
    }

    public void setTelegraphLink(String telegraphLink) {
        this.telegraphLink = telegraphLink;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getEstTime() {
        StringBuilder stringBuilder = new StringBuilder();

        if(text.size() == 0) return -1;

        for(String part : text) {
            stringBuilder.append(part);
        }

        return new Integer(stringBuilder.toString().length()/5/350);
    }

    public String postOnTelegraph() {
        TelegraphContextInitializer.init();
        TelegraphContext.registerInstance(ExecutorOptions.class, new ExecutorOptions());

        try {
            Account account = new CreateAccount("CreepyPasteBot")
                    .setAuthorName("CreepyPasteBot")
                    .setAuthorUrl("https://t.me/CreepyPasteBot")
                    .execute();


            ArrayList<String> reversed = new ArrayList<String>(this.text);

                Node contentNode = new NodeText(reversed.get(0));

                List<Node> content = new ArrayList<Node>();
                content.add(contentNode);

                NodeElement nodeElement = new NodeElement();
                nodeElement.setTag("p");
                nodeElement.setChildren(content);

                List<Node> finalContent = new ArrayList<Node>();
                finalContent.add(nodeElement);

                Page page = new CreatePage(account.getAccessToken(), this.title, finalContent)
                        .setAuthorName("CreepyPasteBot")
                        .setReturnContent(true)
                        .execute();
                return page.getUrl();
            /*for(String page : reversed) {

            }
            */
        } catch (TelegraphException e) {
            e.printStackTrace();
        }
        return null;
    }
}
