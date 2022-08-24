package ru.asanvlit.util.string;

import lombok.experimental.UtilityClass;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import static ru.asanvlit.constant.YartoneImplConstants.TEXT_PROHIBITED_TAGS;

@UtilityClass
public class StringUtil {

    public String removeProhibitedTags(String content) {
        Document result = Jsoup.parse(content);
        String prohibitedTags = String.join(",", TEXT_PROHIBITED_TAGS);
        result.select(prohibitedTags).remove();

        return result.body().text();
    }
}


// todo readme
