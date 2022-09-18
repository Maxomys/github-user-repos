package com.github.maxomys.githubuserrepos.services;

import com.github.maxomys.githubuserrepos.model.client.Branch;
import com.github.maxomys.githubuserrepos.model.client.Repo;
import com.github.maxomys.githubuserrepos.model.client.SimpleRestPage;
import com.github.maxomys.githubuserrepos.utils.LinkHeaderParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Component
public class RestClient {

    public static final String API_REPOS_URL = "https://api.github.com/users/{username}/repos";
    public static final String API_BRANCHES_URL = "https://api.github.com/repos/{username}/{repo}/branches";

    @Value("${ACCESS_TOKEN}")
    private String token;

    private final RestTemplate restTemplate;

    public List<Repo> getReposForUsernameAllPages(String username) {
        List<Repo> repoList = new ArrayList<>();

        int pageNumber = 1;
        SimpleRestPage<Repo> repoPage;
        do {
            repoPage = getRepoPageForUsername(username, pageNumber);
            repoList.addAll(repoPage.getContent());
            pageNumber++;
        } while (repoPage.hasNext());

        return repoList;
    }

    public SimpleRestPage<Repo> getRepoPageForUsername(String username, int pageNumber) {
        ParameterizedTypeReference<List<Repo>> typeReference = new ParameterizedTypeReference<>() {};

        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("username", username);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(API_REPOS_URL)
                .queryParam("page", pageNumber);

        return returnPage(typeReference, pageNumber, uriBuilder.buildAndExpand(urlParams).toUri());
    }

    public List<Branch> getBranchesForRepoAllPages(String username, String repo) {
        List<Branch> branchList = new ArrayList<>();

        int pageNumber = 1;
        SimpleRestPage<Branch> branchPage;
        do {
            branchPage = getBranchPageForUsernameAndRepo(username, repo, pageNumber);
            branchList.addAll(branchPage.getContent());
            pageNumber++;
        } while (branchPage.hasNext());

        return branchList;
    }

    public SimpleRestPage<Branch> getBranchPageForUsernameAndRepo(String username, String repo, int pageNumber) {
        ParameterizedTypeReference<List<Branch>> typeReference = new ParameterizedTypeReference<>() {};

        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("username", username);
        urlParams.put("repo", repo);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(API_BRANCHES_URL)
                .queryParam("page", pageNumber);

        return returnPage(typeReference, pageNumber, uriBuilder.buildAndExpand(urlParams).toUri());
    }

    private <T> SimpleRestPage<T> returnPage(ParameterizedTypeReference<List<T>> typeReference, int pageNumber, URI uri) {
        ResponseEntity<List<T>> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, buildRequest(), typeReference);
        HttpHeaders responseHeaders = responseEntity.getHeaders();

        List<T> data = responseEntity.getBody();

        if (data == null) {
            return new SimpleRestPage<>(null, pageNumber, false);
        }

        if (responseHeaders.get("link") == null) {
            return new SimpleRestPage<>(data, pageNumber, false);
        }

        Map<String, String> links = LinkHeaderParser.parseLinkHeader(responseHeaders.get("link").get(0));

        return new SimpleRestPage<>(data, pageNumber, links.containsKey("next"));
    }

    private HttpEntity<String> buildRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "spring-boot-app");
        headers.add("authorization", "Bearer " + token);

        return new HttpEntity<>(headers);
    }

}
