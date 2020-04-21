package sample.Java;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * DatabaseConnection is a class which makes a database connection to all classes using the DatabaseConnection class
 */
public class DatabaseConnection {
    /**
     * instance is a DatabaseConnection
     * entityManagerFactory is an EntityManagerFactory
     */
    private static DatabaseConnection instance;
    private EntityManagerFactory entityManagerFactory;

    /**
     * This constructor creates EntityManagerFactory
     */
    private DatabaseConnection(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory("Piccollect");
    }

    /**
     * This method returns an EntityManagerFactory
     * @return EntityManagerFactory
     */
    public EntityManagerFactory getEntityManagerFactory(){
        return entityManagerFactory;
    }

    /**
     * This method returns DatabaseConnection. If it is equal to null, it will be defined.
     * @return instance: DatabaseConnection
     */
    public static DatabaseConnection getInstance(){
        if(instance == null){
            instance = new DatabaseConnection();

        }
        return instance;
    }
}
