import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Angebot, Kinokarte, Platz, Vorstellung} from '../dtos/kartenverkauf';

@Injectable({
  providedIn: 'root'
})
export class KartenverkaufService {

  private kartenverkaufUrl: string = 'http://localhost:8080/api/kartenverkauf';

  constructor(private http: HttpClient) {

  }

  public holeZusammenhaengendePlaetze(platzanzahl: number, vorstellungUuid: string): Observable<Angebot> {
    return this.http.get<Angebot>(this.kartenverkaufUrl, {params: {platzanzahl, vorstellungUuid}})
  }

  public holeVorstellung(vorstellungUuid: string): Observable<Vorstellung> {
    return this.http.get<Vorstellung>(this.kartenverkaufUrl + '/vorstellung', {params: {vorstellungUuid}});
  }

  speichereVerkauftePlaetze(platzDtos: Platz[], vorstellungUuid: string): Observable<Kinokarte[]> {
    return this.http.post<Kinokarte[]>(this.kartenverkaufUrl + '/kinokarten', platzDtos);
  }
}
