INSERT INTO filmauswahl.filme
(titel, laufzeit, poster_url, fsk, beschreibung, genre, hauptdarsteller, regie, sprache)
VALUES ('Star Boars', 125, 'assets/Star_Boars.jpeg', 12, 'In einer weit, weit entfernten Galaxie kämpfen mutige Wildschweine gegen das tyrannische Imperium.
Angeführt von Luke Stywalker, müssen sie sich mit Lichttrüffeln und telepathischen Grunzkraftfähigkeiten gegen den dunklen Lord Swineous behaupten.
Ein episches Sci-Fi-Abenteuer voller Action, Humor und intergalaktischem Speckduft!',
        'Sci-Fi, Comedy', 'Luke Stywalker', 'George Laxus', 'deutsch'),
       ('Guardians of the Lunacy', 95, 'assets/Guardians_Of_The_Lunacy.jpeg', 6,
        'Eine Truppe aus abgedrehten Außenseitern wird widerwillig zum Schutz des Universums verpflichtet – und das ist kein gutes Zeichen.
Chris Plattfall und sein chaotisches Team aus galaktischen Verrückten stürzen sich in explosive Abenteuer voller skurriler Aliens,
dummer Sprüche und unerwarteter Heldentaten. Werden sie das Universum retten? Wahrscheinlich nicht. Aber es wird lustig!',
        'Sci-Fi, Comedy', 'Chris Plattfall',
        'James Gunner', 'englisch'),
       ('Back to the Futura', 116, 'assets/Back_To_The_Futura.jpeg', 12,
        'Als die junge Marty McGigawatts mit einer experimentellen Zeitmaschine in die Zukunft reist, findet sie sich in einer dystopischen Megacity wieder,
in der Roboter die Welt regieren. Mit der Hilfe eines exzentrischen Erfinders und einem Hoverboard muss sie den Lauf der Geschichte ändern,
bevor sie in einer Endlosschleife der Zeit gefangen bleibt.', 'Sci-Fi, Adventure', 'Marty McGigawatts',
        'Robert Zoomekis', 'deutsch'),
       ('Clown Wars', 105, 'assets/Clown_Wars.jpeg', 18,
        'Die Erde wird von einer Horde außerirdischer Clowns angegriffen, die nichts anderes wollen, als die Menschheit mit tödlichen Gags zu unterwerfen.
Nur eine Gruppe rebellischer Spaßmacher kann sich der Bedrohung entgegenstellen. Ein intergalaktisches Spektakel voller Ballontier-Kriege,
Killer-Jojos und einem epischen Showdown in der Zirkusarena des Todes.', 'Sci-Fi, Horror, Comedy', 'Penny Wisecrack',
        'Tim Burtonisch', 'englisch'),
       ('The Fast and the Curious', 135, 'assets/The_Fast_And_The_Curious.jpeg', 16,
        'The Fast and the Curious ist ein actiongeladener Film über eine Bande von hochintelligenten Straßenkatzen, die illegale Straßenrennen fahren und geheime Raubüberfälle planen.
Angeführt von der waghalsigen und charismatischen Kätzin Velo, entdeckt das Team, dass eine rivalisierende Hunde-Gang versucht, die Straßen zu übernehmen.
Während atemberaubender Verfolgungsjagden, waghalsiger Stunts und cleverer Pläne müssen die Katzen nicht nur ihre Revierhoheit verteidigen, sondern auch ein letztes, spektakuläres Rennen gewinnen, um ihre Freiheit zu sichern.
Ein rasanter Mix aus Action, Humor und katzenhafter Cleverness!',
        'Action, Adventure, Tierfilm', 'Cat Moss',
        'Rob Kitten', 'deutsch');


INSERT INTO filmauswahl.saele
    (name)
VALUES ('großer Saal'),
       ('kleiner Saal');


INSERT INTO kartenverkauf.saele
    (name, reihen, spalten)
VALUES ('großer Saal', 6, 20),
       ('kleiner Saal', 4, 8);


