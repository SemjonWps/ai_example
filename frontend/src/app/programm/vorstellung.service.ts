import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';

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

export interface Saal {
  id: number,
  name: string
}

@Injectable({
  providedIn: 'root'
})
export class VorstellungService {

  private vorstellungenUrl: string = 'http://localhost:8080/api/vorstellungen';

  constructor(private http: HttpClient) {

  }

  public findAll(): Observable<Vorstellung[]> {
    return this.http.get<Vorstellung[]>(this.vorstellungenUrl);
  }

  public findTagesvorstellungen(datum : string) : Observable<Vorstellung[]> {
    return this.http.get<Vorstellung[]>(this.vorstellungenUrl + "/tag", {params: new HttpParams().set("tag", datum)})
  }

  public save(vorstellung: Vorstellung) {
    return this.http.post<Vorstellung>(this.vorstellungenUrl, vorstellung);
  }
}
