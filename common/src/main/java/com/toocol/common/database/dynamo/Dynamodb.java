package com.toocol.common.database.dynamo;

import com.toocol.common.database.IDocument;
import org.springframework.stereotype.Repository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Joezeo
 * @date 2020/12/20 15:27
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repository
public @interface Dynamodb {
    /**
     * the document class of dynamo db dao
     */
    Class<? extends IDocument> documentClass();
}
