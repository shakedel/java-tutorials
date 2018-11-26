package org.eladsh.library.model;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public class Temp {
	public static void main(String[] args) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
	    em.getTransaction().begin();

	    // Check database version
	    String sql = "select version()";

	    String result = (String) em.createNativeQuery(sql).getSingleResult();
	    System.out.println(result);

	    em.getTransaction().commit();

	    Librarian librarian;
	    
	    librarian = new Librarian();
	    librarian.setEmail("a");
	    librarian.setName("b");
	    librarian.setPassword("c");
	    
	    em.getTransaction().begin();
	    em.persist(librarian);
	    em.getTransaction().commit();
	    
	    librarian = new Librarian();
	    librarian.setEmail("kkk");
	    librarian.setName("b");
	    librarian.setPassword("c");
	    
	    em.getTransaction().begin();
	    em.persist(librarian);
	    em.getTransaction().commit();

	    CriteriaQuery<Librarian> criteria = em.getCriteriaBuilder().createQuery(Librarian.class);
	    criteria.select(criteria.from(Librarian.class));
	    List<Librarian> librarians = em.createQuery(criteria).getResultList();
	    System.out.println(Arrays.toString(librarians.toArray()));;
	    

	    em.close();

	    JpaUtil.shutdown();
		
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction();
// 
//        // Add new Employee object
//        Librarian lib = new Librarian();
//        lib.setEmail("demo-user@mail.com");
//        lib.setName("demo-username");
//        lib.setPassword("mysecretpass");
// 
//        session.save(lib);
// 
//        session.getTransaction().commit();
//        HibernateUtil.shutdown();
    }
}
