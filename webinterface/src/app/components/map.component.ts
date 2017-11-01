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
  selectedDevice;
  http: Http;
  devices;

  constructor(http: Http) {
    this.http = http;
    this.fetchDeviceList();
  }

  // devices = [{
  //   positions: [
  //     { x: 45, y: 24 },
  //     { x: 45.1, y: 24 },
  //     { x: 45.1, y: 24.1 },
  //     { x: 45.3, y: 24.1 }
  //   ],
  //   deviceId: "fgk303-24j-gdr9"
  // },
  // {
  //   positions: [
  //     { x: 45.3234, y: 24.3234 },
  //     { x: 46.2234, y: 24.1234 },
  //     { x: 45.1234, y: 25.11234 },
  //     { x: 44.4234, y: 24.13234 }
  //   ],
  //   deviceId: "jf8sk9-di9-nsu8"
  // }];

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
    let center = this.calculateCenter(this.selectedDevice.positions);
    this.latitude = center.x;
    this.longitude = center.y;
  }

  private onDeviceChange() {
    this.updateMapCenter();
  }

  private fetchDeviceList() {
    //this.selectedDevice = this.devices[0];
    //this.updateMapCenter();
    this.http.get('http://localhost:8080/api/points/n438er-95g-erg9/0/999999999999999999')
      .map(res => res.json())
      // Subscribe to the observable to get the parsed people object and attach it to the
      // component
      .subscribe(devices => { this.devices = devices; console.log(devices) });
  }

  private fetchPointsList() {
    //this.selectedDevice = this.devices[0];
    //this.updateMapCenter();
    this.http.get('http://localhost:8080/api/points/' + this.selectedDevice + '/0/999999999999999999')
      .map(res => res.json())
      // Subscribe to the observable to get the parsed people object and attach it to the
      // component
      .subscribe(devices => { this.devices = devices; console.log(devices) });
  }
}
