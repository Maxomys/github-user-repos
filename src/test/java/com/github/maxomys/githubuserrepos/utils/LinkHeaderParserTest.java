package com.github.maxomys.githubuserrepos.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LinkHeaderParserTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void parseLinkHeader() {
        String linkHeader = "<https://api.github.com/user/1060/repos?page=1>; rel=\"prev\", <https://api.github.com/user/1060/repos?page=3>; rel=\"next\", <https://api.github.com/user/1060/repos?page=11>; rel=\"last\", <https://api.github.com/user/1060/repos?page=1>; rel=\"first\"";

        Map<String, String> links = LinkHeaderParser.parseLinkHeader(linkHeader);

        assertEquals(4, links.size());
        assertEquals("https://api.github.com/user/1060/repos?page=3", links.get("next"));
        assertEquals("https://api.github.com/user/1060/repos?page=1", links.get("prev"));
    }

}
