package com.github.maxomys.githubuserrepos.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkHeaderParser {

    public static Map<String, String> parseLinkHeader(String linkHeader) {
        Map<String, String> parsed = new HashMap<>();

        Pattern pattern = Pattern.compile("<(.*)>; *rel=\"(.*)\"");

        Arrays.stream(linkHeader.split(", *"))
            .forEach(h -> {
                Matcher m = pattern.matcher(h);
                if (m.matches()) {
                    parsed.put(m.group(2), m.group(1));
                }
            });

        return parsed;
    }

}
