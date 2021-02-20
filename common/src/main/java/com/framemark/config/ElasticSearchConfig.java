package com.framemark.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

/**
 * @description
 * @author: liudawei
 * @date: 2021/2/18 18:03
 */
@Configuration
@Slf4j
public class ElasticSearchConfig {

    @Value("${elasticsearch.firstIp}")
    private String firstIp;
    @Value("${elasticsearch.secondIp}")
    private String secondIp;
    @Value("${elasticsearch.firstPort}")
    private Integer firstPort;
    @Value("${elasticsearch.secondPort}")
    private Integer secondPort;
    @Value("${elasticsearch.clusterName}")
    private String clusterName;
    @Value("${elasticsearch.poolSize}")
    private Integer poolSize;

    /**
     * 1、es设计阶段调优
     *  1.1根据业务增量需求，采取基于日期模板创建索引，通过roll over API滚动索引
     *  1.2使用别名进行索引管理
     *  1.3每人凌晨定时对索引做force_merge操作，以释放空间
     *  1.4采用冷热分离机制，热数据存储到ssd，提高检索效率；冷数据定期进行shrink操作，以缩减存储
     *  1.5采取curator进行索引的生命周期管理
     *  1.6仅针对需要分词的字段，合理的设置分词器
     *  1.7Mapping阶段充分结合各个字段的属性，是否需要检索，是否需要存储等
     *
     * 2、写入调优
     *  2.1写入前副本数设置为0
     *  2.2写入前关闭refresh_interval设置为-1，禁用刷新机制
     *  2.3写入过程中，采用bulk批量写入
     *  2.4写入后恢复副本数和刷新间隔
     *  2.5尽量使用自动生成的id
     *
     * 3、查询调优
     *  3.1禁用wildcard
     *  3.2禁用批量terms（成百上千的场景）
     *  3.3充分利用倒排序引机制，能keyword类型尽量keyword
     *  3.4数据量大的时候，可以先基于时间敲定索引再检索
     *  3.5设置合理的路由机制
     *
     *
     *
     * @return
     */
    @Bean(name = "transportClient")
    public TransportClient transportClient() {
        log.info("Elasticsearch初始化开始。。。。。");
        TransportClient transportClient = null;
        try {
            // 配置信息
            Settings esSetting = Settings.builder()
                    .put("cluster.name", clusterName)
                    .put("client.transport.sniff", true)
                    .put("thread_pool.search.size", poolSize)
                    .build();
            //配置信息Settings自定义
            transportClient = new PreBuiltTransportClient(esSetting);
            TransportAddress transportAddress1 = new TransportAddress(InetAddress.getByName(firstIp), Integer.valueOf(firstPort));
            TransportAddress transportAddress2 = new TransportAddress(InetAddress.getByName(secondIp), Integer.valueOf(secondPort));
            transportClient.addTransportAddresses(transportAddress1);
            transportClient.addTransportAddresses(transportAddress2);

        } catch (Exception e) {
            log.error("elasticsearch TransportClient create error!!", e);
        }
        return transportClient;
    }
}

