package edu.utcluj.dcsp.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
class PointService {

    @Autowired
    private PointRepository pointRepository;

    List<Point> getPointsBetween(String id, long from, long to) {
        return pointRepository.findBetween(id, Instant.ofEpochMilli(from), Instant.ofEpochMilli(to));
    }

    List<String> getAllDeviceIds() {
        return pointRepository.findAll()
                .stream()
                .map(Point::getDeviceId)
                .distinct()
                .collect(Collectors.toList());
    }

    void save(Point point) {
        point.setInstant(Instant.now());
        pointRepository.save(point);
    }

    void update(String id, Point point) {
        point.setId(id);
        pointRepository.save(point);
    }

    void delete(String id) {
        pointRepository.delete(id);
    }
}
