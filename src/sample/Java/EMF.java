package sample.Java;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {

    public static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Piccollect");
}
