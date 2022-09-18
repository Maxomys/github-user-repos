package com.github.maxomys.githubuserrepos.model.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Branch {

    private String name;
    private Commit commit;

}
