import { Http, Response, RequestOptions, Headers } from '@angular/http';

import 'rxjs/add/operator/map';
import { RequestOptionsArgs } from '@angular/http/src/interfaces';
import { Injectable } from '@angular/core';

@Injectable()
export class LoginService {
  http: Http;
  token: string;

  constructor(http: Http) {
    this.http = http;
  }

  public secureGet(url: string) {

    let headers = new Headers();
    headers.append('Authorization', "Basic " + this.token);

    let options = new RequestOptions({ headers: headers });
    return this.http.get(url, options)
  }

  public authenticate(username: string, password: string, success: () => void, error: () => void) {
    let token = btoa(username + ":" + password);

    let headers = new Headers();
    headers.append('Authorization', "Basic " + token);

    let options = new RequestOptions({ headers: headers });
    let url = "/api/authenticate";
    this.http.get(url, options).subscribe(
      res => {
        this.token = token;
        success();
      },
      err => {
        this.token = null;
        error();
      }
    );
  }
}
