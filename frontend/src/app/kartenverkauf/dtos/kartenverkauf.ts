export interface Vorstellung {
  uuid: string,
  anfangszeit: string,
  saal: string,
  filmname: string,
}

export interface Angebot {
  gesamtpreis: Geldbetrag,
  platzDtos: Platz[],
  saalplanDto: Saalplan,
}

export interface Saalplan {
  platzbelegungen: Platz[][];
}

export interface Platz {
  reihennummer: number,
  platznummer: number,
  sitzplatzStatus: SitzplatzStatus,
}

export interface Geldbetrag {
  betrag: number,
  waehrung: Waehrung,
}

export enum Waehrung {
  EUR
}

export interface Reihe {
  reihennummer: number,
}

export interface Sitz {
  platznummer: number,
}

export enum SitzplatzStatus {
  BELEGT = 'BELEGT',
  FREI = 'FREI',
  ANGEBOTEN = 'ANGEBOTEN',
}


