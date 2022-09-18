package com.github.maxomys.githubuserrepos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"ACCESS_TOKEN = foo"})
class GithubUserReposApplicationTests {

    @Test
    void contextLoads() {
    }

}
