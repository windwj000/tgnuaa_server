package com.example.demo.domain;

public class LawCaseResponse {

    private int id;

    private String path;

    private String keyword;

    private String content;

    public LawCaseResponse(int id, String path, String keyword) {
        this.id = id;
        this.path = path;
        this.keyword = keyword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
