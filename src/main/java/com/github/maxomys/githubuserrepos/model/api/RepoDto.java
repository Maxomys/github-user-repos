package com.github.maxomys.githubuserrepos.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RepoDto {

    private String repositoryName;
    private String ownerLogin;
    private List<BranchDto> branches;

}
