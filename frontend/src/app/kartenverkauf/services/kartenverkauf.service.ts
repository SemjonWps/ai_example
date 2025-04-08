import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AngebotDto} from '../dtos/kartenverkauf';

@Injectable({
  providedIn: 'root'
})
export class KartenverkaufService {

  private kartenverkaufUrl: string = 'http://localhost:8080/api/kartenverkauf';

  constructor(private http: HttpClient) {

  }

  public holeZusammenhaengendePlaetze(platzanzahl: number, vorstellungUuid: string): Observable<AngebotDto> {
    return this.http.get<AngebotDto>(this.kartenverkaufUrl, {params: {platzanzahl, vorstellungUuid}})
  }
}
