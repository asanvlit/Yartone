package ru.asanvlit.config;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import ru.asanvlit.model.mongo.MongoAbstractEntity;

import java.util.UUID;

@Configuration
public class MongoConfig {

    @Bean
    public GridFSBucket getGridFSBucket(MongoDatabaseFactory databaseFactory) {
        return GridFSBuckets.create(databaseFactory.getMongoDatabase());
    }

    @Bean
    public BeforeConvertCallback<MongoAbstractEntity> beforeSaveCallback() {
        return (entity, collection) -> {
            if (entity.getId() == null) {
                entity.setId(UUID.randomUUID());
            }
            return entity;
        };
    }
}
