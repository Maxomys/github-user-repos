package com.github.maxomys.githubuserrepos.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotFoundResponse {

    private int status;
    private String message;

}
