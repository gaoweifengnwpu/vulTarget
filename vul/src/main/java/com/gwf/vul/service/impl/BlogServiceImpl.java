package com.gwf.vul.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;

import com.gwf.vul.entity.BlogIndex;
import com.gwf.vul.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    //    https://www.lijunyi.xyz/docs/middleware/elasticSearch/es8-java-api.html#%E6%89%B9%E9%87%8F%E6%96%B0%E5%A2%9E
    @Autowired
    private ElasticsearchClient elasticsearchClient;

    /**
     * 模糊查询
     *
     * @throws IOException ioexception
     */
    @Override
    public List<BlogIndex> fuzzyQuerySearch(String author) throws IOException {
        SearchResponse<BlogIndex> response = elasticsearchClient.search(builder -> builder.index("blogs").query(q -> q.wildcard(w -> w.field("author").value(author))), BlogIndex.class);
        List<BlogIndex> list = new ArrayList<>();
        List<Hit<BlogIndex>> hits = response.hits().hits();
        for (Hit<BlogIndex> hit : hits) {
            BlogIndex product = hit.source();
            list.add(product);
        }
        return list;
    }

    /**
     * 保存doc
     *
     * @throws IOException ioexception
     */
    @Override
    public String save(BlogIndex blog) {
        IndexResponse indexResponse = null;
        try {
            indexResponse = elasticsearchClient.index(s ->
                    // 索引
                    s.index("blogs")
                            // ID
                            .id(blog.getId())
                            // 文档
                            .document(blog));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return indexResponse.result().jsonValue();
    }

    /**
     * 多条件匹配
     *
     * @throws IOException ioexception
     */
    @Override
    public List<BlogIndex> search(String content, String author) throws IOException {
        // Search by product name
        Query byContent = MatchQuery.of(m -> m.field("author").query(author))._toQuery();
        Query byAuthor = MatchQuery.of(m -> m.field("content").query(content))._toQuery();
        List<BlogIndex> list = new ArrayList<>();
        SearchResponse<BlogIndex> response = elasticsearchClient.search(s -> s.index("blogs").query(q -> q.bool(b -> b.must(byContent).must(byAuthor))), BlogIndex.class);
        List<Hit<BlogIndex>> hits = response.hits().hits();
        for (Hit<BlogIndex> hit : hits) {
            BlogIndex product = hit.source();
            list.add(product);
        }
        return list;
    }

    /**
     * 查找doc
     * 完全匹配字符串
     *
     * @throws IOException ioexception
     */
    @Override
    public List<BlogIndex> getblogs(String author, int pageNum, int pageSize) throws IOException {
        SearchResponse<BlogIndex> response = elasticsearchClient.search(s -> s.index("blogs").query(q -> q.match(t -> t.field("author").query(author))).from((pageNum - 1) * pageSize).size(pageSize), BlogIndex.class);
        TotalHits total = response.hits().total();
        boolean isExactResult = total.relation() == TotalHitsRelation.Eq;
        if (isExactResult) {
        } else {
        }
        List<BlogIndex> list = new ArrayList<>();
        List<Hit<BlogIndex>> hits = response.hits().hits();
        for (Hit<BlogIndex> hit : hits) {
            BlogIndex product = hit.source();
            assert product != null;
            System.out.println(product.getAuthor());
            list.add(product);
        }
        return list;
    }

    /**
     * 更新doc
     *
     * @throws IOException ioexception
     */
    @Override
    public void updateDocument(String id) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("author", "tangwenkuai1111111111111");
        // 构建修改文档的请求
        UpdateResponse<BlogIndex> response = elasticsearchClient.update(e -> e.index("blogs").id(id).doc(map), BlogIndex.class);
    }


    /**
     * 删除doc
     *
     * @throws IOException ioexception
     */
    @Override
    public void deleteDocument(String id) throws IOException {
        DeleteResponse deleteResponse = elasticsearchClient.delete(s -> s.index("blogs").id(id));
    }

//    @Override
//    public void escombine() throws IOException {
//        String searchText = "bike";
//        double maxPrice = 200.0;
//
//        // Search by product name
//        Query byName = MatchQuery.of(m -> m.field("name").query(searchText))._toQuery();
//
//// Search by max price
//        Query byMaxPrice = RangeQuery.of(r -> r.field("price").gte(JsonData.of(maxPrice)))._toQuery();
//// Combine name and price queries to search the product index
//        SearchResponse<BlogIndex> response = elasticsearchClient.search(s -> s.index("blogs").query(q -> q.bool(b -> b.must(byName).must(byMaxPrice))), BlogIndex.class);
//
//        List<Hit<BlogIndex>> hits = response.hits().hits();
//        for (Hit<BlogIndex> hit : hits) {
//            BlogIndex product = hit.source();
//        }
//    }


}