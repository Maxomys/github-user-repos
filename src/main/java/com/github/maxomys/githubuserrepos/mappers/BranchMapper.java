package com.github.maxomys.githubuserrepos.mappers;

import com.github.maxomys.githubuserrepos.model.api.BranchDto;
import com.github.maxomys.githubuserrepos.model.client.Branch;

public class BranchMapper {

    public static BranchDto branchToBranchDto(Branch branch) {
        return BranchDto.builder()
                .name(branch.getName())
                .lastCommitSha(branch.getCommit().getSha())
                .build();
    }

}
