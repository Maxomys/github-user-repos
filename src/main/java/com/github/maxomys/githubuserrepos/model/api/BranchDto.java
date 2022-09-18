package com.github.maxomys.githubuserrepos.model.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BranchDto {

    private String name;
    private String lastCommitSha;

}
