package com.github.maxomys.githubuserrepos.services;

import com.github.maxomys.githubuserrepos.mappers.BranchMapper;
import com.github.maxomys.githubuserrepos.model.api.RepoDto;
import com.github.maxomys.githubuserrepos.model.client.Repo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepoServiceImpl implements RepoService {

    private final RestClient restClient;

    @Override
    public List<RepoDto> getNonForkReposForUsername(String username) {
        List<Repo> repos = restClient.getReposForUsernameAllPages(username).stream()
                .filter(repo -> !repo.isFork())
                .collect(Collectors.toList());

        List<RepoDto> repoDtos = new ArrayList<>();

        repos.forEach(repo -> {
            String name = repo.getName();
            String ownerLogin = repo.getOwner().getLogin();
            repoDtos.add(new RepoDto(name, ownerLogin, restClient.getBranchesForRepoAllPages(ownerLogin, name).stream()
                    .map(BranchMapper::branchToBranchDto)
                    .collect(Collectors.toList())));
        });

        return repoDtos;
    }

}
