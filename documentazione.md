# Documentazione del Progetto AuleWeb

## Introduzione
Il progetto AuleWeb è una versione semplificata e migliorata visivamente del sistema di gestione delle aule presso il nostro Ateneo. Il sistema consente la gestione delle informazioni relative alle aule, agli eventi e agli utenti-amministratori.

## Funzionalità Principali
1. ADMIN:Gestione delle Aule, comprensiva di nome, posizione, capienza, dettagli sulle attrezzature disponibili e altre informazioni pertinenti.
2. ADMIN:Gestione dei Diparatimenti, comprensiva di nome e descrizione.
3. ADMIN:Gestione degli Eventi, con specifiche dettagliate come data, ora di inizio e fine, descrizione, tipologia. In più corso,aula, e responsabile associati.  
4. Accesso in lettura libero al front-end del sito senza autenticazione.
5. Visualizzazione degli eventi associati ad una specifica aula, in una specifica settimana.
6. Visualizzazione degli eventi associati ad uno specifico corso, in una specifica settimana.
7. Visualizzazione degli eventi di ciascuna aula in un determinato giorno.
8. Visualizzazione degli eventi delle prossime tre ore.
9. Possibilità per gli utenti di esportare gli eventi in formato CSV.
10. Possibilità per gli amministratori di esportare le aule in formato CSV.

## Funzionalità Escluse
- Importazione in formato CSV Aule e Eventi.

## Utilizzo del Sito
Il sito è navigabile a partire dalla Homepage, dove un utente normale può:
- Scegliere un diparatimento.
- Eseguire la login o la Logout.
Un amministratore invece (utente che ha fatto la login) avrà la possibilità di andare sulle pagine relative alla gestione di Diparatimenti, Aule, Eventi.

Gli utenti una volta scelto il diparatimento potranno ottenere una lista di eventi in base a quello che ricercano, quindi:
1. Visualizzazione degli eventi associati ad una specifica aula, in una specifica settimana.
2. Visualizzazione degli eventi associati ad uno specifico corso, in una specifica settimana.
3. Visualizzazione degli eventi di ciascuna aula di un diparatimento in un determinato giorno.
4. Visualizzazione degli eventi delle prossime tre ore.

Abbiamo utilizzato JavaScript esclusivamente per permette agli amministratori di gestire meglio gli eventi ricorrenti.

## Tecnologie Utilizzate
- Linguaggio di programmazione: JAVA.
- Frameworks e librerie: FreeMarker, Javax.
- Database: mysql.

## Contributo dei Membri del Gruppo di Lavoro

## Contributo dei Membri del Gruppo di Lavoro
Il progetto è stato sviluppato da un gruppo di lavoro composto da quattro membri. Di seguito viene riportato il contributo effettivo offerto da ciascun componente alla realizzazione finale del progetto:
- [Nome del Membro]: [Descrizione del contributo, ad esempio programmazione server, realizzazione del layout, programmazione lato client, ecc.]
- [Nome del Membro]: [Descrizione del contributo]
- [Nome del Membro]: [Descrizione del contributo]
- [Nome del Membro]: [Descrizione del contributo]
