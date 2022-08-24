package ru.asanvlit.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.asanvlit.model.mongo.CommentEntity;

import java.util.UUID;

public interface CommentRepository extends MongoRepository<CommentEntity, UUID> {
}
