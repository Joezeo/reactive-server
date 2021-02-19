package com.toocol.server.common.database;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.toocol.server.common.utils.CastUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @author ZhaoZhe
 * @email joezane.cn@gmail.com
 * @date 2020/12/16 11:30
 */
@Slf4j
public abstract class BaseMongoDao<K, T extends IDocument> {

    protected final Class<T> docClazz;

    protected final MongoOperations mongoOperations;

    public BaseMongoDao(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;

        Mongodb mongdb = this.getClass().getAnnotation(Mongodb.class);
        if (mongdb == null) {
            docClazz = null;
            log.error("the database dao {} should annotate @Mongodb, dao was invalid", this.getClass().getName());
        } else {
            docClazz = CastUtil.cast(mongdb.documentClass());
        }
    }

    public Query query(CriteriaDefinition criteriaDefinition) {
        return new Query(criteriaDefinition);
    }

    public Criteria where(String key) {
        return Criteria.where(key);
    }

    public T insert(T document) {
        return mongoOperations.insert(document);
    }

    public boolean delete(T document) {
        DeleteResult result = mongoOperations.remove(document);
        return result.getDeletedCount() == 1;
    }

    public boolean update(K id, Update updateOperation) {
        UpdateResult result = mongoOperations.updateFirst(query(where("_id").is(id)), updateOperation, docClazz);
        return result.getModifiedCount() == 1;
    }

    public T read(K key) {
        return mongoOperations.findOne(query(where("_id").is(key)), docClazz);
    }

    public List<T> readAll() {
        return mongoOperations.findAll(docClazz);
    }
}
