export interface Vorstellung {
  uuid: string,
  beginn: string,
  saal: string,
  film: string,
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
  reihe: number,
  platz: number,
  istFrei: boolean,
}

export interface PlatzId {
  reihe: number,
  platz: number,
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
  vorstellungId: string,
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
  film: string,
  beginn: string,
  saal: string,
  reihe: number,
  platz: number,
}


