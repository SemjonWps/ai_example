export interface Programm {
  von: Date
  bis: Date
  filmvorstellungen: Film[]
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
  vorstellungen: Vorstellung[]
}

export interface Vorstellung {
  uuid: string
  anfangszeit: string
  preis: number
  saal: string
}
