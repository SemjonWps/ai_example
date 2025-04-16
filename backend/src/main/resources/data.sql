INSERT INTO programm.filme
(titel, laufzeit, poster_url, fsk, beschreibung, genre, hauptdarsteller, regie, sprache)
VALUES ('Star Boars', 125, 'assets/Star_Boars.webp', 12, 'In einer weit, weit entfernten Galaxie kämpfen mutige Wildschweine gegen das tyrannische Imperium.
Angeführt von Luke Stywalker, müssen sie sich mit Lichttrüffeln und telepathischen Grunzkraftfähigkeiten gegen den dunklen Lord Swineous behaupten.
Ein episches Sci-Fi-Abenteuer voller Action, Humor und intergalaktischem Speckduft!',
        'Sci-Fi, Comedy', 'Luke Stywalker', 'George Laxus', 'Deutsch'),
       ('The Glitchtrix', 110, 'assets/the_glitchtrix.png', 16, 'Als die Programmiererin Nea Rebooter einen fatalen Bug entdeckt, der die digitale Realität zum Einsturz bringen könnte,
gerät sie ins Visier einer geheimnisvollen Hacker-Gesellschaft. Während die Grenzen zwischen Simulation und Wirklichkeit verschwimmen,
muss Nea lernen, die Kontrolle über den Quellcode des Universums zu übernehmen – bevor es zu spät ist.',
        'Sci-Fi, Comedy', 'Neo Rebooter', 'Lana & Lily Crashowski', 'Finnisch'),
       ('Space Farce', 140, 'assets/Space_Farce.jpg', 12,
        'Captain Han Yolo und ihre chaotische Crew sind die letzte Hoffnung des Universums – was niemandem wirklich Hoffnung macht.
Mit einem Schrott-Raumschiff, zweifelhaften Fähigkeiten und einer gehörigen Portion Pech stolpern sie von einer Mission zur nächsten,
immer einen Schritt davon entfernt, alles in die Luft zu jagen. Eine actiongeladene Sci-Fi-Parodie mit Gags, Explosionen und galaktischer Dummheit!',
        'Sci-Fi, Action, Comedy', 'Han Yolo',
        'J.J. Abrahaha', 'Deutsch'),
       ('The Termi-neigh-tor', 118, 'assets/The_Termi-neigh-tor.webp', 16,
        'Im Jahr 2049 wird die Menschheit von einem skrupellosen Unternehmen mit kybernetischen Pferden unterjocht.
Doch eines dieser Maschinenwesen – das Modell T-Equus 800 – entwickelt ein eigenes Bewusstsein und stellt sich gegen seine Schöpfer.
Mit unaufhaltsamer Entschlossenheit (und Hufen aus Titan) beginnt es einen chaotischen Feldzug gegen die Unterdrücker.',
        'Sci-Fi, Comedy', 'Arnie Horsenegger', 'James Cameroon',
        'Italienisch'),
       ('Droid Hard', 95, 'assets/Droid_Hard.jpeg', 6, 'Als eine ruchlose KI-Mafia die Stadt mit einem Computervirus bedroht, ist der kleine, aber clevere Haushaltsroboter Bleep Bloop die einzige Hoffnung.
Mit nichts als seinem eingebauten Werkzeugkasten und einem unerschütterlichen Sinn für Gerechtigkeit kämpft er sich durch Wellen von Cyber-Schurken,
um die Menschheit zu retten – oder zumindest die Toaster.',
        'Sci-Fi, Animation', 'Bleep Bloop', 'Pete Drinker', 'Deutsch'),
       ('Guardians of the Lunacy', 130, 'assets/Guardians_Of_The_Lunacy.jpg', 12,
        'Eine Truppe aus abgedrehten Außenseitern wird widerwillig zum Schutz des Universums verpflichtet – und das ist kein gutes Zeichen.
Chris Plattfall und sein chaotisches Team aus galaktischen Verrückten stürzen sich in explosive Abenteuer voller skurriler Aliens,
dummer Sprüche und unerwarteter Heldentaten. Werden sie das Universum retten? Wahrscheinlich nicht. Aber es wird lustig!',
        'Sci-Fi, Comedy', 'Chris Plattfall',
        'James Gunner', 'Englisch'),
       ('Back to the Futura', 116, 'assets/Back_To_The_Futura.jpg', 12,
        'Als die junge Marty McGigawatts mit einer experimentellen Zeitmaschine in die Zukunft reist, findet sie sich in einer dystopischen Megacity wieder,
in der Roboter die Welt regieren. Mit der Hilfe eines exzentrischen Erfinders und einem Hoverboard muss sie den Lauf der Geschichte ändern,
bevor sie in einer Endlosschleife der Zeit gefangen bleibt.', 'Sci-Fi, Adventure', 'Marty McGigawatts',
        'Robert Zoomekis', 'Deutsch'),
       ('Clown Wars: The Honk Awakens', 105, 'assets/Clown_Wars.jpg', 12,
        'Die Erde wird von einer Horde außerirdischer Clowns angegriffen, die nichts anderes wollen, als die Menschheit mit tödlichen Gags zu unterwerfen.
Nur eine Gruppe rebellischer Spaßmacher kann sich der Bedrohung entgegenstellen. Ein intergalaktisches Spektakel voller Ballontier-Kriege,
Killer-Jojos und einem epischen Showdown in der Zirkusarena des Todes.', 'Sci-Fi, Horror, Comedy', 'Penny Wisecrack',
        'Tim Burtonisch', 'Englisch'),
       ('Fast and the Curious', 135, 'assets/fast_and_the_curious.png', 16,
        'Fast and the Curious ist ein actiongeladener Film über eine Bande von hochintelligenten Straßenkatzen, die illegale Straßenrennen fahren und geheime Raubüberfälle planen.
Angeführt von der waghalsigen und charismatischen Kätzin Velo, entdeckt das Team, dass eine rivalisierende Hunde-Gang versucht, die Straßen zu übernehmen.
Während atemberaubender Verfolgungsjagden, waghalsiger Stunts und cleverer Pläne müssen die Katzen nicht nur ihre Revierhoheit verteidigen, sondern auch ein letztes, spektakuläres Rennen gewinnen, um ihre Freiheit zu sichern.
Ein rasanter Mix aus Action, Humor und katzenhafter Cleverness!',
        'Action, Adventure, Tierfilm', 'Cat Moss',
        'Rob Kitten', 'Deutsch');

INSERT INTO programm.saele
    (name)
VALUES ('großer Saal'),
       ('kleiner Saal'),
       ('Keller');

INSERT INTO programm.vorstellungen
    (uuid, anfangszeit, film_id, preis, saal_id)
VALUES ('70f79a3c-eb2f-48e4-af59-cda7a353635f', '2025-03-17 15:30:00', 2, 1650, 2),
       ('f142de00-f3ec-4a42-9493-d406b3062b4a', '2025-03-18 14:30:00', 2, 1650, 1),
       ('57d71cca-91c9-4876-a1b3-5628da00abd3', '2025-03-18 15:30:00', 8, 750, 2),
       ('f00cd3c6-b059-4138-9f10-4ba2813fa162', '2025-03-19 14:30:00', 3, 999, 2),
       ('0299be34-d6ea-4eba-b4e8-cdd41afc7da8', '2025-03-19 15:30:00', 7, 500, 3),
       ('503f9e1f-4575-4cb5-8162-748bb8b8c26f', '2025-03-16 14:30:00', 1, 550, 1),
       ('80b9d36c-374f-4569-9ff1-8a2ed2f02b3d', '2025-03-20 14:30:00', 9, 750, 1),
       ('b167534a-8d9a-4219-ba92-69d9a2c1212b', '2025-03-16 15:30:00', 4, 500, 2),
       ('bb98a2af-3d0a-4cb8-9418-61d58ecccf02', '2025-03-17 14:30:00', 1, 550, 1),
       ('66640528-1d70-4564-bae9-a72de8a9a4de', '2025-03-16 14:30:00', 3, 999, 2),
       ('fc5e4025-aee4-42fe-8e81-17628e9b478e', '2025-03-19 20:30:00', 7, 500, 1),
       ('e7c0edd7-0904-470c-954e-cb9e0030ca12', '2025-03-20 15:30:00', 5, 695, 3),
       ('15c935ac-dddc-4fc3-b47f-baa5a642075c', '2025-03-22 19:30:00', 6, 1300, 1),
       ('75e542da-92c0-4559-bfbf-e707af724f7d', '2025-03-22 15:30:00', 8, 800, 2),
       ('6e568065-1850-40fb-8930-2bd93f5ac242', '2025-03-21 14:30:00', 1, 1000, 3),
       ('791df6e3-15c6-410d-9a77-548f4eb9db48', '2025-03-21 15:30:00', 6, 750, 1),
       ('44884037-b0c8-422d-90b6-da107659981e', '2025-03-22 15:45:00', 3, 700, 3),
       ('090c173a-3636-4980-865a-1ec859eb4f90', '2025-03-23 15:30:00', 9, 750, 1),
       ('f711a38d-e792-4016-9463-286c96ce824e', '2025-03-23 15:45:00', 9, 750, 2),
       ('c728df78-e6a6-4715-be16-0fb56698af08', '2025-03-23 11:30:00', 4, 650, 1),
       ('20ad7f5f-7167-46b9-ada6-e7e7c4bd65aa', '2025-03-23 22:30:00', 2, 1650, 1);



INSERT INTO kartenverkauf.vorstellungen
    (uuid, anfangszeit, saal, filmname, eintrittspreis)
VALUES ('70f79a3c-eb2f-48e4-af59-cda7a353635f', '2025-03-17 15:30:00', 'kleiner Saal', 'The Glitchtrix', 450),
       ('f142de00-f3ec-4a42-9493-d406b3062b4a', '2025-03-18 14:30:00', 'großer Saal', 'The Glitchtrix', 750),
       ('57d71cca-91c9-4876-a1b3-5628da00abd3', '2025-03-18 15:30:00', 'kleiner Saal', 'Clown Wars: The Honk Awakens', 750),
       ('f00cd3c6-b059-4138-9f10-4ba2813fa162', '2025-03-19 14:30:00', 'kleiner Saal', 'Space Farce', 999),
       ('0299be34-d6ea-4eba-b4e8-cdd41afc7da8', '2025-03-19 15:30:00', 'Keller', 'Back to the Futura', 500),
       ('503f9e1f-4575-4cb5-8162-748bb8b8c26f', '2025-03-16 14:30:00', 'großer Saal', 'Star Boars', 550),
       ('80b9d36c-374f-4569-9ff1-8a2ed2f02b3d', '2025-03-20 14:30:00', 'großer Saal', 'Fast and the Curious', 750),
       ('b167534a-8d9a-4219-ba92-69d9a2c1212b', '2025-03-16 15:30:00', 'kleiner Saal', 'The Termi-neigh-tor', 500),
       ('bb98a2af-3d0a-4cb8-9418-61d58ecccf02', '2025-03-17 14:30:00', 'großer Saal', 'Star Boars', 550),
       ('66640528-1d70-4564-bae9-a72de8a9a4de', '2025-03-16 14:30:00', 'kleiner Saal', 'Space Farce', 999),
       ('fc5e4025-aee4-42fe-8e81-17628e9b478e', '2025-03-19 20:30:00', 'großer Saal', 'Back to the Futura', 500),
       ('e7c0edd7-0904-470c-954e-cb9e0030ca12', '2025-03-20 15:30:00', 'Keller', 'Droid Hard', 695),
       ('15c935ac-dddc-4fc3-b47f-baa5a642075c', '2025-03-22 19:30:00', 'großer Saal', 'Guardians of the Lunacy', 1300),
       ('75e542da-92c0-4559-bfbf-e707af724f7d', '2025-03-22 15:30:00', 'kleiner Saal', 'Clown Wars: The Honk Awakens', 800),
       ('6e568065-1850-40fb-8930-2bd93f5ac242', '2025-03-21 14:30:00', 'Keller', 'Star Boars', 1000),
       ('791df6e3-15c6-410d-9a77-548f4eb9db48', '2025-03-21 15:30:00', 'großer Saal', 'Guardians of the Lunacy', 750),
       ('44884037-b0c8-422d-90b6-da107659981e', '2025-03-22 15:45:00', 'Keller', 'Space Farce', 700),
       ('090c173a-3636-4980-865a-1ec859eb4f90', '2025-03-23 15:30:00', 'großer Saal', 'Fast and the Curious', 750),
       ('f711a38d-e792-4016-9463-286c96ce824e', '2025-03-23 15:45:00', 'kleiner Saal', 'Fast and the Curious', 750),
       ('c728df78-e6a6-4715-be16-0fb56698af08', '2025-03-23 11:30:00', 'großer Saal', 'The Termi-neigh-tor', 650),
       ('20ad7f5f-7167-46b9-ada6-e7e7c4bd65aa', '2025-03-23 22:30:00', 'großer Saal', 'The Glitchtrix', 1650);

INSERT INTO kartenverkauf.saalplaene
    (vorstellungUUID)
VALUES ('95b21a30-64bf-4df1-a0a2-e769bd7c5ea1');

INSERT INTO kartenverkauf.plaetze
(platznummer, reihennummer, ist_verkauft, reservierungsnummer, saalplan_id)
VALUES (1, 1, false, null, 1L),
       (2, 1, false, null, 1L),
       (3, 1, false, null, 1L),
       (4, 1, false, null, 1L),
       (5, 1, false, null, 1L),
       (6, 1, false, null, 1L),
       (7, 1, false, null, 1L),
       (8, 1, false, null, 1L),
       (9, 1, false, null, 1L),
       (10, 1, false, null, 1L),
       (11, 1, false, null, 1L),
       (12, 1, false, null, 1L),
       (1, 2, false, null, 1L),
       (2, 2, false, null, 1L),
       (3, 2, false, null, 1L),
       (4, 2, false, null, 1L),
       (5, 2, false, null, 1L),
       (6, 2, false, null, 1L),
       (7, 2, false, null, 1L),
       (8, 2, false, null, 1L),
       (9, 2, true, null, 1L),
       (10, 2, true, null, 1L),
       (11, 2, false, null, 1L),
       (12, 2, false, null, 1L),
       (1, 3, false, null, 1L),
       (2, 3, false, null, 1L),
       (3, 3, true, null, 1L),
       (4, 3, true, null, 1L),
       (5, 3, false, null, 1L),
       (6, 3, false, null, 1L),
       (7, 3, true, null, 1L),
       (8, 3, true, null, 1L),
       (9, 3, false, null, 1L),
       (10, 3, false, null, 1L),
       (11, 3, false, null, 1L),
       (12, 3, false, null, 1L),
       (1, 4, false, null, 1L),
       (2, 4, false, null, 1L),
       (3, 4, false, null, 1L),
       (4, 4, true, null, 1L),
       (5, 4, true, null, 1L),
       (6, 4, true, null, 1L),
       (7, 4, true, null, 1L),
       (8, 4, false, null, 1L),
       (9, 4, false, null, 1L),
       (10, 4, false, null, 1L),
       (11, 4, false, null, 1L),
       (12, 4, false, null, 1L);