package edu.utcluj.dcsp.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/")
@CrossOrigin(origins = "http://localhost:4200")
class PointController {

    @Autowired
    private PointService pointService;

    @RequestMapping(method = RequestMethod.GET, path = "points/{deviceId}/{from}/{to}")
    ResponseEntity<Object> getPoints(@PathVariable("deviceId") String deviceId,
            @PathVariable("from") Long from, @PathVariable("to") Long to) {
        List<PointModel> points = pointService.getPointsBetween(deviceId, from, to).stream()
                .map(PointAdapter::toModel).collect(Collectors.toList());
        return new ResponseEntity<>(points, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "points/deviceIds")
    ResponseEntity<Object> getDeviceIds() {
        List<String> deviceIds = pointService.getAllDeviceIds();
        return new ResponseEntity<>(deviceIds, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "points")
    ResponseEntity<Object> savePoint(@RequestBody PointModel point) {
        pointService.save(PointAdapter.toEntity(point));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "points/{id}")
    ResponseEntity<Object> updatePoint(@PathVariable String id,
            @RequestBody PointModel point) {
        pointService.update(id, PointAdapter.toEntity(point));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "points/{id}")
    ResponseEntity<Object> deletePoint(@PathVariable String id) {
        pointService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
