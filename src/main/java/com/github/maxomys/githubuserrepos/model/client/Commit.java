package com.github.maxomys.githubuserrepos.model.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Commit {

    private String name;
    private String sha;

}
