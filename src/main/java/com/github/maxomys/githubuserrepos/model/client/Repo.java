package com.github.maxomys.githubuserrepos.model.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Repo {

    private Long id;
    private String name;
    private boolean fork;
    private User owner;

}
