export interface Vorstellung {
  uuid: string,
  anfangszeit: string,
  saal: string,
  filmname: string,
}

export interface Angebot {
  gesamtpreis: Geldbetrag,
  platzDtos: AngebotenerPlatz[],
  platzbelegungen: Platzbelegungen,
}

export interface Platzbelegungen {
  platzbelegungen: SitzplatzStatus[][],
}

export interface Geldbetrag {
  betragInEuroCent: number,
}

export interface AngebotenerPlatz {
  reihe: Reihe,
  sitz: Sitz,
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


