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

export enum SitzplatzStatus {
  BELEGT = 'BELEGT',
  FREI = 'FREI',
  ANGEBOTEN = 'ANGEBOTEN',
}

export interface Kinokarte {
  vorstellung: Vorstellung,
  reihenummer: number,
  platznummer: number,
  preis: Geldbetrag,
}


