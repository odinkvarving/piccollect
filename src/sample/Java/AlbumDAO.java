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

    public List<Album> getAlbumsAndTheirImages(){
        EntityManager em = getEM();
        try {
            Query q = em.createQuery("SELECT a FROM Album a JOIN FETCH a.images", Album.class);
            return q.getResultList();
        }finally {
            closeEM(em);
        }
    }
    public boolean storeNewAlbum(Album album) {
        EntityManager em = getEM();
        try {
            em.getTransaction().begin();
            em.persist(album);
            em.getTransaction().commit();
            return true;
        } catch (Exception e){
            return false;
        }finally {
            closeEM(em);
        }
    }

    public Album findAlbum(Integer id){
        EntityManager em = getEM();
        try {
            return em.find(Album.class, id);
        }finally {
            closeEM(em);
        }
    }

    public void createNewAlbumWithImages(Album album, ImageV2 imageV2){
        EntityManager em = getEM();
        try {
            em.getTransaction().begin();

            ImageV2 imageV22 = em.find(ImageV2.class, imageV2.getId());
            Album album2 = em.find(Album.class, album.getId());
            imageV22.addAlbum(album2);

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



}
