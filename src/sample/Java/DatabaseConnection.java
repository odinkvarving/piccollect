package sample.Java;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * DatabaseConnection is a class which makes a database connection to all classes using the DatabaseConnection class
 * <br>Used to obtain an EntityManagerFactory in Java SE environments (EntityManagerFactory only exists to support instantiation of an EntityManager)
 * <br>EntityManager is used to represent a connection to a database
 */
public class DatabaseConnection {

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
     * @return the EntityManagerFactory
     */
    public EntityManagerFactory getEntityManagerFactory(){
        return entityManagerFactory;
    }

    /**
     * This method returns DatabaseConnection. If there are no other existing instances of DatabaseConnection, a new connection will be created
     * @return the DatabaseConnection
     */
    public static DatabaseConnection getInstance(){
        if(instance == null){
            instance = new DatabaseConnection();

        }
        return instance;
    }
}
