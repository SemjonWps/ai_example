export interface VorstellungDto {
  uuid: string
  anfangszeit: string
  saal: string
  filmname: string
}

export interface AngebotDto {
  gesamtpreis: Geldbetrag
  zusammenhaengendePlaetze: AngebotenerPlatzDto[]
  platzbelegungen: Platzbelegungen
}

export interface Platzbelegungen {
  platzbelegungen: SitzplatzStatus[][]
}

export interface Geldbetrag {
  betragInEuroCent: number
}

export interface AngebotenerPlatzDto {
  reihennummer: number
  platznummer: number
}

export enum SitzplatzStatus {
  BELEGT = 'BELEGT',
  FREI = 'FREI',
  ANGEBOTEN = 'ANGEBOTEN',
}


