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
  plaetze: Platz[][];
}

export interface Platz {
  reiheNr: number,
  platzNr: number,
  istFrei: boolean,
}

export interface PlatzId {
  reiheNr: number,
  platzNr: number,
}

export interface ZusammenhaengendePlaetze {
  plaetze: PlatzId[],
}

export interface Geldbetrag {
  betrag: number,
  waehrung: Waehrung,
}

export enum Waehrung {
  EUR = 'EUR',
}

export interface Preisanfrage {
  vorstellungUuid: string,
  plaetze: ZusammenhaengendePlaetze,
}

export interface Zahlungsanforderung {
  vorstellung: Vorstellung,
  plaetze: ZusammenhaengendePlaetze,
  betrag: Geldbetrag,
}

export interface Zahlungsbestaetigung {
  zahlungsanforderung: Zahlungsanforderung,
  status: ZahlungStatus,
}

export enum ZahlungStatus {
  ABGELEHNT = 'ABGELEHNT',
  BEZAHLT = 'BEZAHLT',
}

export interface Kinokarte {
  vorstellung: Vorstellung,
  platz: PlatzId,
}


