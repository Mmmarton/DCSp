package edu.utcluj.dcsp.server;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
interface PointRepository extends MongoRepository<Point, String> {

    @Query("{ 'deviceId' : ?0, 'instant' : { $gt: ?1, $lt: ?2 } }")
    List<Point> findBetween(String deviceId, Instant from, Instant to);
}
