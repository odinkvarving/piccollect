package sample.Java;

import com.drew.metadata.MetadataException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.awt.*;

public class ImageV2DAO {

    private EntityManagerFactory emf;


    /* NB! EntityManagerFactory is thread safe, EntityManger is not!
     * The make the solution thread safe use/create the EntityManger locally when needed, do not keep
     * as object variable.
     */
    public ImageV2DAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void storeNewImage(ImageV2 imageV2, Album album) {
        EntityManager em = getEM();
        try {
            em.getTransaction().begin();

            imageV2.getAlbums().add(album);
            album.getImages().add(imageV2);

            em.persist(imageV2);
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


    //bare for testing
    public static void main(String[] args) throws MetadataException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Piccollect");

        ImageV2 imageV2 = new ImageV2("SexYBoy", "Summer body cool", "C:\\Users\\Player One\\Desktop\\Piccollect-team9\\piccollect\\src\\sample\\testBildeGPS.jpg");

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(imageV2);
        entityManager.getTransaction().commit();
        System.out.println();

        emf.close();
    }
}
