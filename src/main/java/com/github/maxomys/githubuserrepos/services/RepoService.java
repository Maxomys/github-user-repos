package com.github.maxomys.githubuserrepos.services;

import com.github.maxomys.githubuserrepos.model.api.RepoDto;

import java.util.List;

public interface RepoService {

    List<RepoDto> getNonForkReposForUsername(String username);

}
