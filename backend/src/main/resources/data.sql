-- Clear all tables in filmauswahl and kartenverkauf schemas
-- H2 does not support TRUNCATE ... CASCADE, so use DELETE in dependency order
DELETE FROM kartenverkauf.plaetze;
DELETE FROM kartenverkauf.saalplaene;
DELETE FROM kartenverkauf.saele;
DELETE FROM kartenverkauf.vorstellungen;
DELETE FROM kartenverkauf.zahlungsvorgaenge;
DELETE FROM filmauswahl.vorstellungen;
DELETE FROM filmauswahl.filme;
DELETE FROM filmauswahl.saele;

-- Reset identity/auto-increment counters so IDs are deterministic for seed data
ALTER TABLE filmauswahl.saele ALTER COLUMN id RESTART WITH 1;
ALTER TABLE filmauswahl.filme ALTER COLUMN id RESTART WITH 1;
