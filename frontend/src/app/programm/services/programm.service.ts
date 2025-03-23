import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Programm} from '../dtos/programm';

@Injectable({
  providedIn: 'root'
})
export class ProgrammService {

  private programmUrl: string = 'http://localhost:8080/api/programm';

  constructor(private http: HttpClient) {

  }

  public holeProgramm(datum: string): Observable<Programm> {
    return this.http.get<Programm>(this.programmUrl, {params: {datum: datum}})
  }

}
