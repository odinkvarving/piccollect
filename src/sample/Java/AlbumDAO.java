package sample.Java;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

/**
 * AlbumDAO is a class which is used as a connection between the Album class and the database.
 * This way we can add albums and get all albums from the database and use them.
 */
public class AlbumDAO {

    /**
     * emf is an EntityManagerFactory interface
     */
    private EntityManagerFactory emf;

    /**
     * Constructor which takes in an EntityManagerFactory as a parameter
     * @param emf: EntityManagerFactory
     */
    public AlbumDAO(EntityManagerFactory emf){
        this.emf = emf;
    }

    /**
     * This method returns all albums from the database.
     * @return q.getResultList(): a list of all albums.
     */
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

    /**
     * This method returns all albums and their images
     * @return q.getResultList(): a list of all albums and their images
     */
    public List<Album> getAlbumsAndTheirImages(){
        EntityManager em = getEM();
        try {
            Query q = em.createQuery("SELECT a FROM Album a JOIN FETCH a.images", Album.class);
            return q.getResultList();
        }finally {
            closeEM(em);
        }
    }

    /**
     * This method stores new album, and takes in an album, which is going to be added, as parameter
     * @param album: Album
     * @return boolean: true or false, depending on if the sequence was a success
     */
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

    /**
     * This method finds an album by using their ID
     * @param id: Album ID
     * @return album: album with given ID
     */
    public Album findAlbum(Integer id){
        EntityManager em = getEM();
        try {
            return em.find(Album.class, id);
        }finally {
            closeEM(em);
        }
    }

    /**
     * This method creates new album, and adds an image to the album
     * @param album: Album added
     * @param imageV2: Image added
     */
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
     * This method creates and returns an EntityManager
     * @return EntityManager
     */
    private EntityManager getEM() {
        return emf.createEntityManager();
    }

    /**
     * Metode for å lukke connectionen
     * This method closes the EntityManager
     * @param em: EntityManager
     */
    private void closeEM(EntityManager em) {
        if (em != null && em.isOpen()) em.close();
    }
}
