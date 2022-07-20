package com.toocol.common.database.mongo;

import com.toocol.common.database.IDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author ZhaoZhe
 * @email joezane.cn@gmail.com
 * @date 2020/12/16 11:30
 */
public interface AsyncMongoRepo<K, T extends IDocument> extends ReactiveMongoRepository<T, K> {

}
