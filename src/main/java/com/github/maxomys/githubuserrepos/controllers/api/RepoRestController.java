package com.github.maxomys.githubuserrepos.controllers.api;

import com.github.maxomys.githubuserrepos.model.api.NotFoundResponse;
import com.github.maxomys.githubuserrepos.model.api.RepoDto;
import com.github.maxomys.githubuserrepos.services.RepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/repo")
public class RepoRestController {

    private final RepoService repoService;

    @GetMapping("/username/{username}")
    public List<RepoDto> getNonForkReposForUsername(@PathVariable String username){
        return repoService.getNonForkReposForUsername(username);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public NotFoundResponse handleException() {
        return new NotFoundResponse(HttpStatus.NOT_FOUND.value(), "Not Found");
    }

}
