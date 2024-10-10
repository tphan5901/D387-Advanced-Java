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

  welcomeMessageEnglish$!: Observable<string>
  welcomeMessageFrench$!: Observable<string>
  announcePresentation$!: Observable<string>

  constructor(private httpClient:HttpClient){}
  private baseURL:string='http://localhost:4200';

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

       this.welcomeMessageFrench$ = this.httpClient.get(this.baseURL + '/welcome?lang=fr-CA', { responseType: 'text' }).pipe(
           map(message => {
             console.log('French:', message);
             return message;
           })
         );

         this.welcomeMessageEnglish$ = this.httpClient.get(this.baseURL + '/welcome?lang=en-US', { responseType: 'text' }).pipe(
           map(message => {
             console.log('Message:', message);
             return message;
           })
         );

         this.announcePresentation$ = this.httpClient.get(this.baseURL + '/presentation', { responseType: 'text' }).pipe(
           map(presentation => {
             console.log('Presentation Announcement:', presentation);
             return presentation;
           })
         );

        this.roomsearch= new FormGroup({
          checkin: new FormControl(' '),
          checkout: new FormControl(' ')
        });


    const roomsearchValueChanges$ = this.roomsearch.valueChanges;

    // subscribe to the stream
    roomsearchValueChanges$.subscribe(x => {
      this.currentCheckInVal = x.checkin;
      this.currentCheckOutVal = x.checkout;
    });
  }

    onSubmit({value,valid}:{value:Roomsearch,valid:boolean}){
      this.getAll().subscribe(

        rooms => {
          console.log(Object.values(rooms)[0]);
          this.rooms=<Room[]>Object.values(rooms)[0];
          // C2 add price
          this.rooms.forEach( room => { room.priceCAD = room.price; room.priceEUR = room.price})
        }
      );
    }
    reserveRoom(value:string){
      this.request = new ReserveRoomRequest(value, this.currentCheckInVal, this.currentCheckOutVal);

      this.createReservation(this.request);
    }
    createReservation(body:ReserveRoomRequest) {
      let bodyString = JSON.stringify(body); // Stringify payload
      let headers = new Headers({'Content-Type': 'application/json'}); // ... Set content type to JSON
     // let options = new RequestOptions({headers: headers}); // Create a request option

     const options = {
      headers: new HttpHeaders().append('key', 'value'),

    }

      this.httpClient.post(this.postUrl, body, options)
        .subscribe(res => console.log(res));
    }

  /*mapRoom(response:HttpResponse<any>): Room[]{
    return response.body;
  }*/

    getAll(): Observable<any> {
       return this.httpClient.get(this.baseURL + '/room/reservation/v1?checkin='+ this.currentCheckInVal + '&checkout='+this.currentCheckOutVal, {responseType: 'json'});
    }
  }

export interface Roomsearch{
    checkin:string;
    checkout:string;
}

export interface Room{
  id:string;
  roomNumber:string;
  price:string;
  // C2 add room object:string datatype
  priceCAD:string;
  priceEUR:string;
  links:string;
}

export class ReserveRoomRequest {
  roomId:string;
  checkin:string;
  checkout:string;

  constructor(roomId:string,
              checkin:string,
              checkout:string) {

    this.roomId = roomId;
    this.checkin = checkin;
    this.checkout = checkout;
  }
}



