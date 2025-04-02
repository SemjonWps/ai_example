INSERT INTO filme
(titel, laufzeit, poster_url, fsk, beschreibung, genre, hauptdarsteller, regie, sprache)
VALUES ('Star Boars', 125, 'assets/Star_Boars.webp', 12, 'In einer weit, weit entfernten Galaxie kämpfen mutige Wildschweine gegen das tyrannische Imperium.
Angeführt von Luke Stywalker, müssen sie sich mit Lichttrüffeln und telepathischen Grunzkraftfähigkeiten gegen den dunklen Lord Swineous behaupten.
Ein episches Sci-Fi-Abenteuer voller Action, Humor und intergalaktischem Speckduft!',
        'Sci-Fi, Comedy', 'Luke Stywalker', 'George Laxus', 'Deutsch'),
       ('The Glitchtrix', 110, 'assets/the_glitchtrix.png', 16, 'Als die Programmiererin Nea Rebooter einen fatalen Bug entdeckt, der die digitale Realität zum Einsturz bringen könnte,
gerät sie ins Visier einer geheimnisvollen Hacker-Gesellschaft. Während die Grenzen zwischen Simulation und Wirklichkeit verschwimmen,
muss Nea lernen, die Kontrolle über den Quellcode des Universums zu übernehmen – bevor es zu spät ist.',
        'Sci-Fi, Comedy', 'Neo Rebooter', 'Lana & Lily Crashowski', 'Finnisch'),
       ('Space Farce', 140, 'assets/Space_Farce.jpeg', 12,
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
       ('Guardians of the Lunacy', 130, 'assets/Guardians_of_the_Lunacy.jpeg', 12,
        'Eine Truppe aus abgedrehten Außenseitern wird widerwillig zum Schutz des Universums verpflichtet – und das ist kein gutes Zeichen.
Chris Plattfall und sein chaotisches Team aus galaktischen Verrückten stürzen sich in explosive Abenteuer voller skurriler Aliens,
dummer Sprüche und unerwarteter Heldentaten. Werden sie das Universum retten? Wahrscheinlich nicht. Aber es wird lustig!',
        'Sci-Fi, Comedy', 'Chris Plattfall',
        'James Gunner', 'Englisch'),
       ('Back to the Futura', 116, 'assets/Back_to_the_Futura.jpeg', 12,
        'Als die junge Marty McGigawatts mit einer experimentellen Zeitmaschine in die Zukunft reist, findet sie sich in einer dystopischen Megacity wieder,
in der Roboter die Welt regieren. Mit der Hilfe eines exzentrischen Erfinders und einem Hoverboard muss sie den Lauf der Geschichte ändern,
bevor sie in einer Endlosschleife der Zeit gefangen bleibt.', 'Sci-Fi, Adventure', 'Marty McGigawatts',
        'Robert Zoomekis', 'Deutsch'),
       ('Clown Wars: The Honk Awakens', 105, 'assets/Clown_Wars_The_Honk_Awakens.webp', 12,
        'Die Erde wird von einer Horde außerirdischer Clowns angegriffen, die nichts anderes wollen, als die Menschheit mit tödlichen Gags zu unterwerfen.
Nur eine Gruppe rebellischer Spaßmacher kann sich der Bedrohung entgegenstellen. Ein intergalaktisches Spektakel voller Ballontier-Kriege,
Killer-Jojos und einem epischen Showdown in der Zirkusarena des Todes.', 'Sci-Fi, Horror, Comedy', 'Penny Wisecrack',
        'Tim Burtonisch', 'Englisch'),
       ('Fast and the Curious', 135, 'assets/fast_and_the_curious.png', 16,
        'Fast and the Curious ist ein actiongeladener Film über eine Bande von hochintelligenten Straßenkatzen, die illegale Straßenrennen fahren und geheime Raubüberfälle planen. Angeführt von der waghalsigen und charismatischen Kätzin Velo, entdeckt das Team, dass eine rivalisierende Hunde-Gang versucht, die Straßen zu übernehmen. Während atemberaubender Verfolgungsjagden, waghalsiger Stunts und cleverer Pläne müssen die Katzen nicht nur ihre Revierhoheit verteidigen, sondern auch ein letztes, spektakuläres Rennen gewinnen, um ihre Freiheit zu sichern. Ein rasanter Mix aus Action, Humor und katzenhafter Cleverness!',
        'Action, Adventure, Tierfilm', 'Cat Moss',
        'Rob Kitten', 'Deutsch');

INSERT INTO saele
    (name)
VALUES ('großer Saal'),
       ('kleiner Saal'),
       ('Keller');

INSERT INTO vorstellungen
    (anfangszeit, film_id, preis, saal_id)
VALUES ('2025-03-17 15:30:00', 2, 450, 2),
       ('2025-03-18 14:30:00', 2, 750, 1),
       ('2025-03-18 15:30:00', 8, 750, 2),
       ('2025-03-19 14:30:00', 3, 999, 2),
       ('2025-03-19 15:30:00', 7, 500, 3),
       ('2025-03-16 14:30:00', 1, 550, 1),
       ('2025-03-20 14:30:00', 9, 750, 1),
       ('2025-03-16 15:30:00', 4, 500, 2),
       ('2025-03-17 14:30:00', 1, 550, 1),
       ('2025-03-16 14:30:00', 3, 999, 2),
       ('2025-03-19 20:30:00', 7, 500, 1),
       ('2025-03-20 15:30:00', 5, 695, 3),
       ('2025-03-22 19:30:00', 6, 1300, 1),
       ('2025-03-22 15:30:00', 8, 800, 2),
       ('2025-03-21 14:30:00', 1, 1000, 3),
       ('2025-03-21 15:30:00', 6, 750, 1),
       ('2025-03-22 15:45:00', 3, 700, 3),
       ('2025-03-23 15:30:00', 9, 750, 1),
       ('2025-03-23 15:45:00', 9, 750, 2),
       ('2025-03-23 11:30:00', 4, 650, 1);
