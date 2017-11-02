import { Component, OnInit, NgZone, ElementRef, ViewChild } from '@angular/core';
import { AgmCoreModule } from '@agm/core';
import { Http } from '@angular/http';

import 'rxjs/add/operator/map';

@Component({
  selector: 'map-component',
  templateUrl: "map.component.html",
  styleUrls: ["map.component.scss"]
})

export class MapComponent {
  latitude: number;
  longitude: number;
  selectedDeviceId;
  http: Http;
  deviceIds;
  positions;

  constructor(http: Http) {
    this.http = http;
    this.fetchDeviceIds();
  }

  private calculateCenter(points) {
    let center = { x: 0, y: 0 };

    for (let point of points) {
      center.x += point.x;
      center.y += point.y;
    }
    center.x /= points.length;
    center.y /= points.length;

    return center;
  }

  private updateMapCenter() {
    let center = this.calculateCenter(this.positions);
    this.latitude = center.x;
    this.longitude = center.y;
  }

  private onDeviceIdChange() {
    this.fetchPoints();
  }

  private fetchDeviceIds() {
    this.http.get('http://localhost:8080/api/points/deviceIds')
      .map(res => res.json())
      .subscribe(deviceIds => {
        this.deviceIds = deviceIds;
        this.selectedDeviceId = deviceIds[0];
        this.fetchPoints();
      });
  }

  private fetchPoints() {
    this.http.get('http://localhost:8080/api/points/' + this.selectedDeviceId + '/0/999999999999999999')
      .map(res => res.json())
      .subscribe(positions => {
        console.log(positions);
        this.positions = positions;
        this.updateMapCenter();
      });
  }
}
