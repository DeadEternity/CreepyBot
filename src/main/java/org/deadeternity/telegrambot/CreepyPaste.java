package org.deadeternity.telegrambot;

import org.telegram.telegraph.ExecutorOptions;
import org.telegram.telegraph.TelegraphContext;
import org.telegram.telegraph.TelegraphContextInitializer;
import org.telegram.telegraph.api.methods.CreateAccount;
import org.telegram.telegraph.api.methods.CreatePage;
import org.telegram.telegraph.api.objects.*;
import org.telegram.telegraph.exceptions.TelegraphException;

import javax.xml.bind.Element;
import java.util.*;

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

        ArrayList<Node> node = new ArrayList<Node>();
        Integer length = 0;
        ArrayList<ArrayList<Node>> formatPaste = new ArrayList<ArrayList<Node>>();

        for(String pastePart : this.text) {
            if(length >= 10000) {
                formatPaste.add(node);
                length = 0;
                node = new ArrayList<Node>();
            }

            length += pastePart.length();

            NodeText nodeText = new NodeText(pastePart);
            ArrayList<Node> textContent = new ArrayList<Node>();
            textContent.add(nodeText);

            NodeElement p = new NodeElement()
                    .setTag("p")
                    .setChildren(textContent);

            node.add(p);
        }
        formatPaste.add(node);

        this.setPages(formatPaste.size());

        try {
            Account account = new CreateAccount("CreepyPasteBot")
                    .setAuthorName("CreepyPasteBot")
                    .setAuthorUrl("https://t.me/CreepyPasteBot")
                    .execute();

            Collections.reverse(formatPaste);

            Page telegraphPage;
            String nextLink = "";

            for(ArrayList<Node> page : formatPaste) {
                if(formatPaste.indexOf(page) == 0) {
                    NodeText originalHref = new NodeText("Читать на Мракопедии");
                    ArrayList<Node> hrefContent = new ArrayList<Node>();
                    hrefContent.add(originalHref);

                    NodeElement originalNode = new NodeElement()
                            .setTag("a");

                    HashMap<String, String> href = new HashMap<String, String>();
                    href.put("href", this.link);

                    originalNode.setAttrs(href);

                    originalNode.setChildren(hrefContent);

                    page.add(originalNode);
                } else {
                    NodeText nextHref = new NodeText("Продолжение...");
                    ArrayList<Node> hrefContent = new ArrayList<Node>();
                    hrefContent.add(nextHref);

                    NodeElement originalNode = new NodeElement()
                            .setTag("a");
                    HashMap<String, String> href = new HashMap<String, String>();
                    href.put("href", nextLink);

                    originalNode.setAttrs(href);

                    originalNode.setChildren(hrefContent);

                    page.add(originalNode);
                }
                StringBuilder title = new StringBuilder(this.title);
                title.append(".");
                if(formatPaste.indexOf(page) != formatPaste.size()-1) {
                    title.append(" Часть " + (formatPaste.size() - formatPaste.indexOf(page)) + "/" + formatPaste.size());
                }

                telegraphPage = new CreatePage(account.getAccessToken(), title.toString(), page)
                        .setAuthorName("CreepyPasteBot")
                        .setReturnContent(true)
                        .execute();

                nextLink = telegraphPage.getUrl();
            }
            this.setTelegraphLink(nextLink);
            return this.getTelegraphLink();
        } catch (TelegraphException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getMessageView() {
        StringBuilder result = new StringBuilder();
        result.append("[" + this.getTitle() + "](" + this.getTelegraphLink() + ")\n\n");
        result.append("*Примерное время на прочтение*: " + this.getEstTime() + " минут(ы)\n");
        result.append(this.getRating() + "%(_" + this.getVotes() + " оценок_)\n\n");
        for(String tag : this.tags) {
            StringBuilder formatedTag = new StringBuilder(tag.replaceAll("[\\s-()]", "_"));
            while(formatedTag.indexOf("_") != -1) formatedTag.replace(formatedTag.indexOf("_"), formatedTag.indexOf("_") + 1, "\\_");
            result.append("#" + formatedTag.toString() + " ");
        }
        return result.toString();
    }
}
