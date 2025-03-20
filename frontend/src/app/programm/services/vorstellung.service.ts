import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Film} from '../dtos/film';

@Injectable({
  providedIn: 'root'
})
export class VorstellungService {

  private vorstellungenUrl: string = 'http://localhost:8080/api/vorstellungen';

  constructor(private http: HttpClient) {

  }

  public holeVorstellungenFuerTag(datum: string): Observable<Film[]> {
    console.log(datum)
    return this.http.get<Film[]>(this.vorstellungenUrl, {params: {tag: datum}})
  }

}
