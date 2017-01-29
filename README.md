# MinVakt
Dette prosjektet gjennomføres av 8 studenter som går andre året på dataingeniørlinja på NTNU i Trondheim. For å få godkjent faget Systemutvikling 2 med web-applikasjoner skal vi gjennomføre et Scrum-prosjekt som går over 3 uker. Scrum er et enkelt rammeverk for å optimalisere produktutviklingen slik at prosjektet skal bli effektivt og får et resultat av høy kvalitet.

Oppgaven vi fikk av vår produkteier var å lage et digitalt vaktlistesystem for helsetjenesten i Trondheim kommune. Dagens system er manuelt og gjøres med penn og papir. Vi skal lage et system som skal gjøre det lettere for ansatte og administrasjonen å håndtere arbeidsliste, vaktbytte, tilgjengelighet og registrere fravær.

# Install:
1. Last ned eller klon prosjektet
2. Åpne det prosjektet i en valgfri IDE
2. Høyreklikk pom.xml -> Add as Maven Project
3. Sett opp lokal Tomcat Server
4. Sjekk at MinVakt-artifakten har alle library filene i seg:
  - **IntelliJ**: File -> Project structure -> Artifacts -> minvakt:war exploded -> WEB-INF -> lib -> add library files
  - Legg til alle library filene i lib mappen og apply/ok
5. Kjør serveren og logg inn:
  - Brukernavn: **admin**
  - Passord: **12345678**
