package edu.utcluj.dcsp.server;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
class Point {

  @Id
  private String id;
  private String deviceId;
  private Instant instant;
  private double x;
  private double y;

  Point() {
  }

  String getId() {
    return id;
  }

  void setId(String id) {
    this.id = id;
  }

  String getDeviceId() {
    return deviceId;
  }

  void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  Instant getInstant() {
    return instant;
  }

  void setInstant(Instant instant) {
    this.instant = instant;
  }

  double getX() {
    return x;
  }

  void setX(double x) {
    this.x = x;
  }

  double getY() {
    return y;
  }

  void setY(double y) {
    this.y = y;
  }
}
