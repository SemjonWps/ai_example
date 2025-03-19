INSERT INTO filme
    (titel, laufzeit, poster_url, fsk, beschreibung, genre, hauptdarsteller, regie, sprache)
VALUES
    ('Star Boars', 125, 'assets/Star_Boars.webp', 12, 'Intergalaktische Wildschweine kaempfen gegen das Imperium!', 'Sci-Fi, Comedy', 'Luke Stywalker', 'George Laxus', 'Deutsch'),
    ('The Glitchtrix', 110, 'assets/the_glitchtrix.png', 16, 'Ein Software-Bug offenbart eine digitale Dystopie.', 'Sci-Fi, Comedy', 'Neo Rebooter', 'Lana & Lily Crashowski', 'Finnisch'),
    ('Space Farce', 140, 'assets/Space_Farce.jpeg', 12, 'Eine Crew voller Idioten versucht, das Universum zu retten.', 'Sci-Fi, Action, Comedy', 'Han Yolo', 'J.J. Abrahaha', 'Deutsch'),
    ('The Termi-neigh-tor', 118, 'assets/The_Termi-neigh-tor.webp', 16, 'Ein Cyborg-Pferd aus der Zukunft sorgt fuer Chaos.', 'Sci-Fi, Comedy', 'Arnie Horsenegger', 'James Cameroon', 'Italienisch'),
    ('Droid Hard', 95, 'assets/Droid_Hard.jpeg', 6, 'Ein kleiner Roboter nimmt es mit der KI-Mafia auf.', 'Sci-Fi, Animation', 'Bleep Bloop', 'Pete Drinker', 'Deutsch'),
    ('Guardians of the Lunacy', 130, 'assets/Guardians_of_the_Lunacy.jpeg', 12, 'Eine chaotische Truppe schuetzt das Universum – irgendwie.', 'Sci-Fi, Comedy', 'Chris Plattfall', 'James Gunner', 'Englisch'),
    ('Back to the Futura', 116, 'assets/Back_to_the_Futura.jpeg', 12, 'Ein Zeitreisender landet in einer dystopischen Zukunft.', 'Sci-Fi, Adventure', 'Marty McGigawatts', 'Robert Zoomekis', 'Deutsch'),
    ('Clown Wars: The Honk Awakens', 105, 'assets/Clown_Wars_The_Honk_Awakens.webp', 12, 'Ein Krieg zwischen ausserirdischen Clowns und der Menschheit.', 'Sci-Fi, Horror, Comedy', 'Penny Wisecrack', 'Tim Burtonisch', 'Englisch');

INSERT INTO saele
    (name)
VALUES
    ('großer Saal'),
    ('kleiner Saal'),
    ('Keller');

INSERT INTO vorstellungen
    (anfangszeit, endzeit, film_id, preis, saal_id)
VALUES
    ('2025-03-16 14:30:00', '2025-03-16 17:00:00', 1, 550, 1),
    ('2025-03-16 15:30:00', '2025-03-16 17:50:00', 4, 500, 2),
    ('2025-03-17 14:30:00', '2025-03-17 17:00:00', 1, 550, 1),
    ('2025-03-17 15:30:00', '2025-03-17 17:45:00', 2, 450, 2),
    ('2025-03-18 14:30:00', '2025-03-18 16:45:00', 2, 750, 1),
    ('2025-03-18 15:30:00', '2025-03-18 17:30:00', 8, 750, 2),
    ('2025-03-19 14:30:00', '2025-03-19 17:15:00', 3, 999, 2),
    ('2025-03-19 15:30:00', '2025-03-19 18:00:00', 7, 500, 3),
    ('2025-03-20 14:30:00', '2025-03-20 16:50:00', 4, 600, 1),
    ('2025-03-20 15:30:00', '2025-03-20 17:15:00', 5, 695, 3),
    ('2025-03-21 14:30:00', '2025-03-21 17:00:00', 1, 1000, 3),
    ('2025-03-21 15:30:00', '2025-03-21 18:00:00', 6, 750, 1),
    ('2025-03-22 19:30:00', '2025-03-22 22:00:00', 6, 1300, 1),
    ('2025-03-22 15:30:00', '2025-03-22 17:30:00', 8, 800, 2),
    ('2025-03-22 15:45:00', '2025-03-22 18:30:00', 3, 700, 3);
