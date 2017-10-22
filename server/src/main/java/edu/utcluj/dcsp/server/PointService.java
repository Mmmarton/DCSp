package edu.utcluj.dcsp.server;

import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {

  @Autowired
  private PointRepository pointRepository;

  public List<Point> getPointsBetween(String id, long from, long to) {
    return pointRepository.findBetween(id, Instant.ofEpochMilli(from), Instant.ofEpochMilli(to));
  }

  public void save(Point point) {
    point.setInstant(Instant.now());
    pointRepository.save(point);
  }

  public void update(String id, Point point) {
    point.setId(id);
    pointRepository.save(point);
  }

  public void delete(String id) {
    pointRepository.delete(id);
  }
}
