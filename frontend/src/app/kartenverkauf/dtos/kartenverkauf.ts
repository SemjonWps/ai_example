export interface VorstellungDto {
  uuid: string
  anfangszeit: string
  saal: string
  filmname: string
}

export interface AngebotDto {
  preis: number
  zusammenhaengendePlaetze: angebotenerPlatzDto[]
  saalplan: sitzplatzStatus[][]
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


