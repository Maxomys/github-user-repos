package com.github.maxomys.githubuserrepos.services;

import com.github.maxomys.githubuserrepos.model.client.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestClientTest {

    @Mock
    RestTemplate restTemplate;

    RestClient restClient;

    List<Repo> repoList;
    List<Branch> branchList;

    String linkHeaderNext = "<https://api.github.com/user/1060/repos?page=2>; rel=\"next\"";
    String linkHeaderPrev = "<https://api.github.com/user/1060/repos?page=1>; rel=\"prev\"";

    @BeforeEach
    void setUp() {
        restClient = new RestClient(restTemplate);

        User user = User.builder()
                .login("username")
                .build();
        repoList = List.of(
                Repo.builder()
                        .owner(user)
                        .name("repo1")
                        .id(1L)
                        .fork(false)
                        .build(),
                Repo.builder()
                        .owner(user)
                        .name("repo2")
                        .id(2L)
                        .fork(true)
                        .build()
        );

        Commit commit = Commit.builder()
                .name("commit1")
                .sha("1")
                .build();
        branchList = List.of(
                Branch.builder()
                        .name("branch1")
                        .commit(commit)
                        .build()
        );
    }

    @Test
    void getRepoPageForUsername() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("link", linkHeaderNext);
        ResponseEntity<List<Repo>> responseEntity = new ResponseEntity<List<Repo>>(repoList, headers, HttpStatus.OK);

        when(restTemplate.exchange(any(URI.class), any(), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
                .thenReturn(responseEntity);

        SimpleRestPage<Repo> page = restClient.getRepoPageForUsername("username", 1);

        assertTrue(page.hasContent());
        assertTrue(page.hasNext());
    }

    @Test
    void getBranchesForRepo() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("link", linkHeaderPrev);
        ResponseEntity<List<Branch>> responseEntity = new ResponseEntity<>(branchList, headers, HttpStatus.OK);

        when(restTemplate.exchange(any(URI.class), any(), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
                .thenReturn(responseEntity);

        SimpleRestPage<Branch> page = restClient.getBranchPageForUsernameAndRepo("username", "repo", 1);

        assertTrue(page.hasContent());
        assertEquals(1, page.getContent().size());
        assertFalse(page.hasNext());
    }

}
