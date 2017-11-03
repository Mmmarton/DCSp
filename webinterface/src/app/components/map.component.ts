import { Component } from '@angular/core';
import { AgmCoreModule } from '@agm/core';
import { Http } from '@angular/http';
import { MatDatepicker, MatOption, MatSelect } from '@angular/material';

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
  dateFrom: Date;
  dateTo: Date;
  message: String;

  constructor(http: Http) {
    this.dateFrom = new Date();
    this.dateTo = new Date();
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

  private onCriteriaChange() {
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
    let dateTo = new Date();
    dateTo.setDate(this.dateTo.getDate() + 1);
    this.http.get('http://localhost:8080/api/points/' + this.selectedDeviceId + '/' + this.dateFrom.getTime() + '/' + dateTo.getTime())
      .map(res => res.json())
      .subscribe(positions => {
        if (positions.length == 0) {
          this.message = "No data found for device in given time period.";
          this.latitude = 46.7712;
          this.longitude = 23.6236;
        }
        else {
          this.message = "";
          this.positions = positions;
          this.updateMapCenter();
        }
      });
  }
}
