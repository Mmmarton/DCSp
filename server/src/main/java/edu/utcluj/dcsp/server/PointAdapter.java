package edu.utcluj.dcsp.server;

class PointAdapter {

  static Point toEntity(PointModel model) {
    Point entity = new Point();
    entity.setDeviceId(model.getDeviceId());
    entity.setX(model.getX());
    entity.setY(model.getY());
    return entity;
  }

  static PointModel toModel(Point entity) {
    PointModel model = new PointModel();
    model.setId(entity.getId());
    model.setX(entity.getX());
    model.setY(entity.getY());
    return model;
  }

}
