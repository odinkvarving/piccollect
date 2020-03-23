package sample.Java;

import com.drew.metadata.MetadataException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class AlbumDAO {

    private EntityManagerFactory emf;

    public AlbumDAO(EntityManagerFactory emf){
        this.emf = emf;
    }

    public List<Album> getAlbums(){
        EntityManager em = getEM();
        try{
            Query q = em.createQuery("SELECT OBJECT(o) FROM Album o");
            return q.getResultList();
        }
        finally {
            closeEM(em);
        }
    }

    public void storeNewAlbum(Album album) {
        EntityManager em = getEM();
        try {
            em.getTransaction().begin();
            em.persist(album);
            em.getTransaction().commit();
        } finally {
            closeEM(em);
        }
    }

    /**
     * Metode for å lage og hente entitymanageren.
     *
     * @return en entitymanager
     */
    private EntityManager getEM() {
        return emf.createEntityManager();
    }

    /**
     * Metode for å lukke connectionen
     *
     * @param em
     */
    private void closeEM(EntityManager em) {
        if (em != null && em.isOpen()) em.close();
    }

    public static void main(String[] args) throws MetadataException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Piccollect");

        Album all = new Album("none");
        Album snow = new Album("Snow");
        Album dogs = new Album("Dogs");

        ImageV2 imageV2 = new ImageV2("SexYBoy", "Summer body cool", "C:\\Users\\Player One\\Desktop\\piccollect\\src\\sample\\testBildeGPS.jpg");
        ImageV2 imageV22 = new ImageV2("HotBoyMan!", "Nature philosophy heres my key", "C:\\Users\\Player One\\Desktop\\piccollect\\src\\sample\\testBildeGPS.jpg");



        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(all);

        imageV2.getAlbums().add(all);
        imageV2.getAlbums().add(snow);

        snow.getImages().add(imageV2);
        all.getImages().add(imageV2);


        imageV22.getAlbums().add(dogs);
        imageV22.getAlbums().add(snow);

        snow.getImages().add(imageV22);
        dogs.getImages().add(imageV22);

        entityManager.persist(imageV22);
        entityManager.persist(imageV2);

        entityManager.getTransaction().commit();


        emf.close();
    }


}
