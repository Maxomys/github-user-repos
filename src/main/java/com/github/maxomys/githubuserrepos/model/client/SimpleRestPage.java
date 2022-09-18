package com.github.maxomys.githubuserrepos.model.client;

import lombok.AllArgsConstructor;

import java.util.List;


@AllArgsConstructor
public class SimpleRestPage<T> {

    private List<T> content;

    private int number;
    private boolean hasNext;

    public boolean hasContent() {
        return !content.isEmpty();
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

}
