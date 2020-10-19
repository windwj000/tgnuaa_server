package com.example.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class Keyword {

    private int id;

    private String name;

    private int rank;

    private int priority;

    private String identity;

    private int count;

    private int pid;

    private List<Keyword> children = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public List<Keyword> getChildren() {
        return children;
    }

    public void setChildren(List<Keyword> children) {
        this.children = children;
    }
}
