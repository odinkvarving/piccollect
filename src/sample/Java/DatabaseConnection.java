package sample.Java;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private EntityManagerFactory entityManagerFactory;

    private DatabaseConnection(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory("Piccollect");
    }


    public EntityManagerFactory getEntityManagerFactory(){
        return entityManagerFactory;
    }

    public static DatabaseConnection getInstance(){
        if(instance == null){
            instance = new DatabaseConnection();

        }
        return instance;
    }
}
