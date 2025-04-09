export interface VorstellungDto {
  uuid: string
  anfangszeit: string
  saal: string
  filmname: string
}

export interface AngebotDto {
  gesamtpreis: Geldbetrag
  zusammenhaengendePlaetze: angebotenerPlatzDto[]
  platzbelegungen: Platzbelegungen
}

export interface Platzbelegungen {
  platzbelegungen: sitzplatzStatus[][]
}

export interface Geldbetrag {
  betragInEuroCent: number
}

export interface angebotenerPlatzDto {
  reihennummer: number
  platznummer: number
}

export enum sitzplatzStatus {
  BELEGT,
  FREI,
  ANGEBOTEN,
}


