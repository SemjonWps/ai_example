export interface Vorstellung {
  uuid: string,
  anfangszeit: string,
  saal: string,
  filmname: string,
}

export interface Angebot {
  gesamtpreis: Geldbetrag,
  plaetze: Platz[],
  saalplan: Saalplan,
}

export interface Saalplan {
  platzbelegungen: Platz[][];
}

export interface Platz {
  reiheNr: number,
  platzNr: number,
  sitzplatzStatus: SitzplatzStatus,
}

export interface Geldbetrag {
  betrag: number,
  waehrung: Waehrung,
}

export enum Waehrung {
  EUR = 'EUR',
}

export enum SitzplatzStatus {
  BELEGT = 'BELEGT',
  FREI = 'FREI',
  ANGEBOTEN = 'ANGEBOTEN',
}

export interface Kinokarte {
  vorstellung: Vorstellung,
  reiheNr: number,
  platzNr: number,
  preis: Geldbetrag,
}


