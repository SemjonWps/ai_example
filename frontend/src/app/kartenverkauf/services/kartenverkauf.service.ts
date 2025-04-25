import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {
  Kinokarte,
  Preisanfrage,
  Saalplan,
  Vorstellung,
  Zahlungsanforderung,
  Zahlungsbestaetigung,
  ZusammenhaengendePlaetze
} from '../dtos/kartenverkauf';

@Injectable({
  providedIn: 'root'
})
export class KartenverkaufService {

  private kartenverkaufUrl: string = 'http://localhost:8080/api/kartenverkauf';

  constructor(private http: HttpClient) {
  }

  public holeVorstellung(vorstellungUuid: string): Observable<Vorstellung> {
    return this.http.get<Vorstellung>(`${this.kartenverkaufUrl}/vorstellungen/${vorstellungUuid}`);
  }

  public holeSaalplan(vorstellungUuid: string): Observable<Saalplan> {
    return this.http.get<Saalplan>(`${this.kartenverkaufUrl}/saalplaene/${vorstellungUuid}`);
  }

  public sucheZusammenhaengendePlaetze(vorstellungUuid: string, platzanzahl: number): Observable<ZusammenhaengendePlaetze> {
    return this.http.get<ZusammenhaengendePlaetze>(`${this.kartenverkaufUrl}/saalplaene/${vorstellungUuid}/suche-zusammenhaengende-plaetze`, {
      params: {
        platzanzahl,
      }
    })
  }

  public holeZahlungsanforderung(vorstellungUuid: string, plaetze: ZusammenhaengendePlaetze): Observable<Zahlungsanforderung> {
    const preisanfrage: Preisanfrage = {vorstellungUuid, plaetze};
    return this.http.post<Zahlungsanforderung>(`${this.kartenverkaufUrl}/preisanfrage`, preisanfrage);
  }

  public erstelleKinokarten(zahlungsbestaetigung: Zahlungsbestaetigung): Observable<Kinokarte[]> {
    return this.http.post<Kinokarte[]>(this.kartenverkaufUrl + '/kinokarten', zahlungsbestaetigung);
  }
}
