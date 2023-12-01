package com.gwf.vul.service;

import com.gwf.vul.entity.BlogIndex;

import java.io.IOException;
import java.util.List;

public interface BlogService {
    String save(BlogIndex user);

    public List<BlogIndex> getblogs(String author, int pageNum, int pageSize) throws IOException;

    public void updateDocument(String id) throws IOException;

    public void deleteDocument(String id) throws IOException;

//    public void escombine() throws IOException;

    public List<BlogIndex> search(String content, String author) throws IOException;

    public List<BlogIndex> fuzzyQuerySearch(String author) throws IOException;
}