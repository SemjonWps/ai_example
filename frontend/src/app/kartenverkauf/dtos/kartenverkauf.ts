export interface VorstellungDto {
  uuid: string
  anfangszeit: string
  saal: string
  filmname: string
}

export interface AngebotDto {
  gesamtpreis: Geldbetrag
  platzDtos: AngebotenerPlatzDto[]
  platzbelegungen: Platzbelegungen
}

export interface Platzbelegungen {
  platzbelegungen: SitzplatzStatus[][]
}

export interface Geldbetrag {
  betragInEuroCent: number
}

export interface AngebotenerPlatzDto {
  reihe: { reihennummer: number },
  sitz: { platznummer: number }
}

export enum SitzplatzStatus {
  BELEGT = 'BELEGT',
  FREI = 'FREI',
  ANGEBOTEN = 'ANGEBOTEN',
}


