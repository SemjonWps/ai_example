export interface Programm {
  von: Date
  bis: Date
  programmeintraege: Programmeintrag[]
}

export interface Programmeintrag {
  film: Film
  vorstellungen: Vorstellung[]
}

export interface Film {
  id: number
  titel: string
  laufzeit: number
  posterUrl: string
  fsk: number
  beschreibung: string
  genre: string
  hauptdarsteller: string
  regie: string
  sprache: string
}

export interface Vorstellung {
  uuid: string
  anfangszeit: string
  preis: number
  saal: string
}
