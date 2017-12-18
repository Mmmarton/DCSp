import { Component } from '@angular/core';
import { AgmCoreModule } from '@agm/core';
import { Http, Response, RequestOptions, Headers } from '@angular/http';
import { MatDatepicker, MatOption, MatSelect } from '@angular/material';
import { HttpClient, HttpHeaders } from "@angular/common/http";

import 'rxjs/add/operator/map';
import { RequestOptionsArgs } from '@angular/http/src/interfaces';
import { LoginService } from "./login.service";

@Component({
  selector: 'map-component',
  templateUrl: "map.component.html",
  providers: [LoginService],
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
  login: LoginService;
  username: string;
  password: string;

  constructor(http: Http, login: LoginService) {
    this.dateFrom = new Date();
    this.dateTo = new Date();
    this.http = http;
    this.login = login;
  }

  onCriteriaChange() {
    this.fetchPoints();
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

  private authenticate(username: string, password: string) {
    this.login.authenticate(username, password, () => this.fetchDeviceIds(), () => this.failLogin());
  }

  private failLogin() {
    this.username = "";
    this.password = "";
  }
  
  private fetchDeviceIds() {
    this.login.secureGet('/api/points/deviceIds')
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
    this.login.secureGet('/api/points/' + this.selectedDeviceId + '/' + this.dateFrom.getTime() + '/' + dateTo.getTime())
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
