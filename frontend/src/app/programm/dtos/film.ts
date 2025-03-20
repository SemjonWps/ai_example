export interface Vorstellung {
  id: number,
  anfangszeit: string,
  preis: number,
  saal: Saal,
}

export interface Film {
  id: number,
  titel: string,
  laufzeit: number,
  posterUrl: string,
  fsk: number,
  beschreibung: string,
  genre: string,
  hauptdarsteller: string,
  regie: string,
  sprache: string,
  vorstellungen: Vorstellung[]
}

export interface Saal {
  id: number,
  name: string
}
