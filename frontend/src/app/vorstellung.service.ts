import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/user';
import { Observable } from 'rxjs/Observable';

@Injectable({
  providedIn: 'root'
})

export interface Vorstellung {
  id: number,
  anfangszeit: string,
  endzeit: string,
  film: Film,
  preis: number,
  saal: Saal,
}

export interface Film {
   id: number,
   titel: string,
   laufzeit: number,
   posterUrl: string,
   fsk: number,
   beschreibung: string,
   genre: string,
   hauptdarsteller: string,
   regie: string,
   sprache: string
}

export interface saal {
  id: number,
  name: string
}

export class VorstellungService {

  private const vorstellungenUrl: string = 'http://localhost:8080/api/vorstellungen';

  constructor(private http: HttpClient) {

  }

  public findAll(): Observable<Vorstellung[]> {
    return this.http.get<Vorstellung[]>(this.vorstellungenUrl);
  }

  public save(vorstellung: Vorstellung) {
    return this.http.post<Vorstellung>(this.vorstellungenUrl, vorstellung);
  }
}
