package com.example.demo.service;

import com.example.demo.mapper.LawCaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LawCaseService {

    @Autowired
    private LawCaseMapper lawCaseMapper;

    /**
     * 根据path获得case的title
     * @param path
     * @return
     */
    public String getCaseTitle(String path) {
        int fromIndex = path.lastIndexOf('/');
        int endIndex = path.lastIndexOf('.');
        return path.substring(fromIndex + 1, endIndex);
    }
}
