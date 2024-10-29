import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { Observable } from 'rxjs';
import {map} from "rxjs/operators";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  private baseURL: string = 'http://localhost:8080';
  welcomeMessageEnglish$!: Observable<string>
  welcomeMessageFrench$!: Observable<string>
  announcePresentation$!: Observable<string>

  constructor(private httpClient:HttpClient){}

  private getUrl:string = this.baseURL + '/room/reservation/v1/';
  private postUrl:string = this.baseURL + '/room/reservation/v1';
  public submitted!:boolean;
  roomsearch! : FormGroup;
  rooms! : Room[];
  request!:ReserveRoomRequest;
  currentCheckInVal!:string;
  currentCheckOutVal!:string;

    ngOnInit(){

      this.welcomeMessageFrench$ = this.httpClient.get(this.baseURL + '/welcome?lang=fr-CA', {responseType: 'text'} )
      this.welcomeMessageEnglish$ = this.httpClient.get(this.baseURL + '/welcome?lang=en-US', {responseType: 'text'} )
      this.announcePresentation$ = this.httpClient.get(this.baseURL + '/presentation', {responseType: 'text'} )

    //form group
    this.roomsearch = new FormGroup({
      checkin: new FormControl(),
      checkout: new FormControl()
    });

    // Subscribe to form value changes
    this.roomsearch.valueChanges.subscribe(values => {
      this.currentCheckInVal = values.checkin;
      this.currentCheckOutVal = values.checkout;
    });
  }

  onSubmit({ value, valid }: { value: Roomsearch, valid: boolean }) {
    this.getAll().subscribe(rooms => {
      console.log('Rooms data:', rooms);
      this.rooms = <Room[]>Object.values(rooms)[0];

      // price mapping
      this.rooms.forEach(room => {
        if (room.price) {
          room.priceCAD = room.price;
          room.priceEUR = room.price;
        } else {
          console.error('Price field missing for room:', room);
        }
      });
    });

  }

  reserveRoom(value: string) {
    this.request = new ReserveRoomRequest(value, this.currentCheckInVal, this.currentCheckOutVal);
    this.createReservation(this.request);
  }

  createReservation(body: ReserveRoomRequest) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const options = { headers };

    this.httpClient.post(this.postUrl, body, options)
      .subscribe(res => console.log(res));
  }

  getAll(): Observable<any> {
    return this.httpClient.get(`${this.baseURL}/room/reservation/v1?checkin=${this.currentCheckInVal}&checkout=${this.currentCheckOutVal}`, { responseType: 'json' });
  }
}

export interface Roomsearch {
  checkin: string;
  checkout: string;
}

export interface Room {
  id: string;
  roomNumber: string;
  price: string;
  priceCAD: string;
  priceEUR: string;
  links: string;
}

export class ReserveRoomRequest {
  roomId: string;
  checkin: string;
  checkout: string;

  constructor(roomId: string, checkin: string, checkout: string) {
    this.roomId = roomId;
    this.checkin = checkin;
    this.checkout = checkout;
  }
}
