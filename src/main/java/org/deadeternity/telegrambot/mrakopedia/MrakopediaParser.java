package org.deadeternity.telegrambot.mrakopedia;

import org.deadeternity.telegrambot.CreepyPaste;
import org.deadeternity.telegrambot.Utils.HttpRequester;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MrakopediaParser {
    private static final String randomUrl = "https://mrakopedia.org/wiki/%D0%A1%D0%BB%D1%83%D0%B6%D0%B5%D0%B1%D0%BD%D0%B0%D1%8F:" +
            "%D0%A1%D0%BB%D1%83%D1%87%D0%B0%D0%B9%D0%BD%D0%B0%D1%8F_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0";

    public static CreepyPaste getRandomPasteText() {
        CreepyPaste paste = new CreepyPaste();
        String[] rawHtml = HttpRequester.getPage(randomUrl);
        Document document = Jsoup.parse(rawHtml[1]);

        paste.setLink(rawHtml[0]);
        for(Element element : document.select("script")) {
            element.remove();
        }

        paste.setTitle(document.select("h1#firstHeading").first().text());

        //select paste rate, find values, remove
        String rateNodeText = document.select("span[id=w4g_rb_area-1]").first().text();

        Matcher rateMatcher = Pattern.compile("(\\d+)/100.* (\\d+)").matcher(rateNodeText);
        if(rateMatcher.find()) {
            paste.setRating(Integer.parseInt(rateMatcher.group(1)));
            paste.setVotes(Integer.parseInt(rateMatcher.group(2)));
        }

        document.select("span#w4g_rb_area-1").remove();

        for(Element el : document.select("div#mw-content-text").select("p")) {
            paste.addText(el.text());
            System.out.println(el.text());
        }

        for(Element tag : document.select("div#catlinks").select("li")) {
            paste.addTag(tag.text());
        }

        return paste;
    }

}
