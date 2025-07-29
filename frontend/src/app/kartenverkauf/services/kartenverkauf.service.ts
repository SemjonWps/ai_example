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

  private kartenverkaufUrl: string = '/api/kartenverkauf';

  constructor(private http: HttpClient) {
  }

  public holeVorstellung(vorstellungId: string): Observable<Vorstellung> {
    return this.http.get<Vorstellung>(`${this.kartenverkaufUrl}/vorstellungen/${vorstellungId}`);
  }

  public holeSaalplan(vorstellungId: string): Observable<Saalplan> {
    return this.http.get<Saalplan>(`${this.kartenverkaufUrl}/saalplaene/${vorstellungId}`);
  }

  public sucheZusammenhaengendePlaetze(vorstellungId: string, platzanzahl: number): Observable<ZusammenhaengendePlaetze> {
    return this.http.get<ZusammenhaengendePlaetze>(`${this.kartenverkaufUrl}/saalplaene/${vorstellungId}/suche-zusammenhaengende-plaetze`, {
      params: {
        platzanzahl,
      }
    })
  }

  public holeZahlungsanforderung(vorstellungId: string, plaetze: ZusammenhaengendePlaetze): Observable<Zahlungsanforderung> {
    const preisanfrage: Preisanfrage = {vorstellungId, plaetze};
    return this.http.post<Zahlungsanforderung>(`${this.kartenverkaufUrl}/preisanfrage`, preisanfrage);
  }

  public erstelleKinokarten(zahlungsbestaetigung: Zahlungsbestaetigung): Observable<Kinokarte[]> {
    return this.http.post<Kinokarte[]>(this.kartenverkaufUrl + '/kinokarten', zahlungsbestaetigung);
  }
}
