package com.framemark.controller;

import com.alibaba.fastjson.JSONObject;
import com.framemark.config.ElasticsearchUtil;
import com.framemark.constants.LogType;
import com.framemark.model.EsPage;
import com.framemark.model.LogModel;
import com.framemark.service.es.OperateLog;
import com.framemark.util.IPUtils;
import com.framemark.util.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @description
 * @author: liudawei
 * @date: 2021/2/20 9:16
 */
@RestController
@RequestMapping("/es/queryLog")
public class QueryLogController {

    @Autowired
    OperateLog operateLog;

    /**
     * 索引
     */
    private String indexName = "t_query_log";

    /**
     * 类型
     */
    private String esType = "doc";

    /**
     * 时间段分页查询
     *
     * @param startPage
     * @param pageSize
     * @param beginTime
     * @param endTime
     * @return
     */
    @RequestMapping("/queryLogPage")
    public String queryLogPage(HttpServletRequest request, String startPage, String pageSize, String beginTime, String endTime, String content) {
        operateLog.save(new LogModel(RandomUtils.generateChatAndNumberIdentifyCode(10), LogType.Constants.QUERY_VALUE, IPUtils.getIpAddr(request),
                beginTime + "-" + endTime + "-" + content, new Date()));

        if (StringUtils.isNotBlank(startPage) && StringUtils.isNotBlank(pageSize)) {
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            boolQuery.must(QueryBuilders.rangeQuery("create_time").from(beginTime)
                    .to(endTime).format("yyyy-MM-dd"));
            // 模糊查询
            boolQuery.must(QueryBuilders.matchQuery("user_name", content));
            // 模糊加分词查询
//            boolQuery.must(QueryBuilders.matchQuery("user_name", content).analyzer("ik_max_word"));
            // 不分词
//            boolQuery.must(QueryBuilders.matchPhraseQuery("user_name", content));
            EsPage list = ElasticsearchUtil.searchDataPage(indexName, esType, Integer.parseInt(startPage), Integer.parseInt(pageSize), boolQuery, null, null, null);
            return JSONObject.toJSONString(list);
        } else {
            return "startPage或者pageSize缺失";
        }
    }


}
