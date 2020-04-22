package sample.Java;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
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
     * @param emf the EntityManagerFactory
     */
    public AlbumDAO(EntityManagerFactory emf){
        this.emf = emf;
    }

    /**
     * This method returns all albums from the database.
     * @return a list of all albums.
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
     * @return a list of all albums and their images
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
     * Method for finding the path for the first image in an album
     * @param album the album that is used
     * @return the path if the first image
     */
    public String getAlbumsFirstImagePath(Album album){
        EntityManager em = getEM();
        try {
            Query q = em.createQuery("SELECT a FROM Album a JOIN FETCH a.images WHERE a.id = :albumID", Album.class);
            q.setParameter("albumID", album.getId());
            Album a = (Album) q.getSingleResult();
            if(!a.getImages().isEmpty()){
                return a.getImages().get(0).getFilePath();
            }else {
                return "";
            }
        }finally {
            closeEM(em);
        }
    }

    /**
     * Method for making a list of paths for every first images in all albums
     * @return a list of paths for every album
     */
    public List<String> getAllAlbumsFirstImagePath(){
        EntityManager em = getEM();
        List<String> albumsFirstImagePaths = new ArrayList<>();
        try {
            List<Album> albums = em.createQuery("SELECT a FROM Album a JOIN FETCH a.images", Album.class).getResultList();
            albums.forEach(a -> {
                if(a.getImages().isEmpty()){
                    albumsFirstImagePaths.add("");
                }else{
                    albumsFirstImagePaths.add(a.getImages().get(0).getFilePath());
                }
            });
            return  albumsFirstImagePaths;
        }finally {
            closeEM(em);
        }
    }

    /**
     * This method stores new album, and takes in an album, which is going to be added, as parameter
     * @param album the album to be stored
     * @return true or false, depending on if the sequence was a success or not
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
     * @param id the id of the album
     * @return album with given ID
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
     * @param album the album that is created
     * @param imageV2 the image that is added
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
     * @return an EntityManager
     */
    private EntityManager getEM() {
        return emf.createEntityManager();
    }

    /**
     * Method for closing the connection (closes the EntityManager)
     * @param em the EntityManager
     */
    private void closeEM(EntityManager em) {
        if (em != null && em.isOpen()) em.close();
    }
}
