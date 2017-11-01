import { Component, OnInit, NgZone, ElementRef, ViewChild } from '@angular/core';
import { AgmCoreModule } from '@agm/core';
import { } from '@types/googlemaps';

@Component({
  selector: 'map-component',
  templateUrl: "map.component.html",
  styleUrls: ["map.component.scss"]
})

export class MapComponent {
  latitude: number;
  longitude: number;

  constructor() {
    let center = this.calculateCenter(this.devices[0].positions);
    this.latitude = center.x;
    this.longitude = center.y;
  }

  devices = [{
    positions: [
      { x: 45, y: 24 },
      { x: 45.1, y: 24 },
      { x: 45.1, y: 24.1 },
      { x: 45.3, y: 24.1 }
    ],
    deviceId: "fgk303-24j-gdr9"
  }];

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

  public drawPath(event, device) {
    console.log(device.deviceId);
  }
}
