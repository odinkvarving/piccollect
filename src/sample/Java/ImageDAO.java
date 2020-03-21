package sample.Java;

public class ImageDAO {

    /**
     * 1. I vår applikasjon er Image-klassen entiteten.
     * 2. Vi bruker et interface kalt EntityManager som tilbyr metoder som feks å laste opp til data base og
     * hente ut data osv.
     * 3. Vi kan gjøre dette via en EntityManagerFactory som har en metode som kalles createEntityManager.
     * Denne metoden returnerer et EntityManager-objekt vi kan bruke.
     * 4.Får å kunne bruke en EntityManagerFactory må vi bruke klassen Persistance. Vi bruker denne
     * metoden EntityManagerFactory createEntityManagerFactory(String persistenceUnitName).
     * 5. Som vi ser i forrige punkt trenger createEntityManagerFactory en string input. Dette skal være en xml fil som er vår persistance.
     */
}
