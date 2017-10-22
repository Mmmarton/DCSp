package edu.utcluj.dcsp.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/")
public class PointController {

  @RequestMapping(method = RequestMethod.GET, path = "/")
  public ResponseEntity<Object> home() {
    return new ResponseEntity<>("Home here", HttpStatus.OK);
  }
}
