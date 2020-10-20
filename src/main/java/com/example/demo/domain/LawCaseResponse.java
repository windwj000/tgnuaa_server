package com.example.demo.domain;

import java.util.ArrayList;

public class LawCaseResponse {

    private int id;

    private String title;

    private String path;

    private String keyword;

    private ArrayList<String> keywordsList;

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

    public ArrayList<String> getKeywordsList() {
        return keywordsList;
    }

    public void setKeywordsList(ArrayList<String> keywordsList) {
        this.keywordsList = keywordsList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
