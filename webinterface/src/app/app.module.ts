import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AgmCoreModule } from "@agm/core";
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { MapComponent } from './components/map.component';
import { SignPipe } from './components/sign.pipe';

@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    SignPipe
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AgmCoreModule.forRoot({
      apiKey: "AIzaSyAocJuuzUmP8NC4IOMpANfvFEoSoj3uubU"
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }