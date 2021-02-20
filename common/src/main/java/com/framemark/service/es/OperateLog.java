package com.framemark.service.es;

import com.framemark.model.LogModel;
import org.springframework.data.repository.CrudRepository;

/**
 * @description
 * @author: liudawei
 * @date: 2021/2/20 15:57
 */
public interface OperateLog extends CrudRepository<LogModel, String> {
}
