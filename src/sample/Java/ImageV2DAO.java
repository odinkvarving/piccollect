package sample.Java;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

/**
 * ImageV2DAO is a class which connects ImageV2 (and Album) class to a database.
 * The class adds and collects information about the images from database.
 */
public class ImageV2DAO {

    private EntityManagerFactory emf;

    /* NB! EntityManagerFactory is thread safe, EntityManger is not!
     To make the solution thread safe use/create the EntityManger locally when needed, do not keep
     as object variable.*/

    /**
     * This constructor takes in emf as a parameter
     * @param emf the EntityManagerFactory
     */
    public ImageV2DAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * This method stores a new image.
     * It stores the image in an album (if an album is chosen)
     * @param imageV2 the image that is stored
     * @param album the album the image can be added to (NB: user doesn't need to choose an album)
     */
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

    /**
     * This method updates a given image from the user
     * @param imageV2 the image to update
     */
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

    /**
     * This method returns a list of all images in the database
     * @return a list of all images
     */
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
     * Method for creating and returning the EntityManager
     * @return an EntityManager
     */
    private EntityManager getEM() {
        return emf.createEntityManager();
    }

    /**
     * Method for closing the connection and EntityManager
     * @param em the EntityManager to be closed
     */
    private void closeEM(EntityManager em) {
        if (em != null && em.isOpen()) em.close();
    }
}
