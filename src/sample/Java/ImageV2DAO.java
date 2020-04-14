package sample.Java;

import com.drew.metadata.MetadataException;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

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

            List<Album> albums = em.createQuery("SELECT a from Album a JOIN FETCH a.images", Album.class).getResultList();
            for(Album album1 : albums){
                if(album1.getId() == album.getId()){
                    imageV2.addAlbum(album1);
                }
            }

            em.persist(imageV2);
            em.getTransaction().commit();
        } finally {
            closeEM(em);
        }
    }

    public void updateImage(ImageV2 imageV2){
        EntityManager em = getEM();
        try {
            em.getTransaction().begin();
            em.merge(imageV2);
            em.getTransaction().commit();
        }finally {
            closeEM(em);
        }
    }

    public List<ImageV2> getImages(){
        EntityManager em = getEM();
        try {
            Query q = em.createQuery("SELECT OBJECT(o) FROM ImageV2 o");
            return q.getResultList();
        }finally {
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



}
