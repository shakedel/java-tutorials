package org.eladsh.library.backend;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.NotImplementedException;
import org.eladsh.library.model.Book;
import org.eladsh.library.model.JpaUtil;
import org.eladsh.library.model.Librarian;
import org.hibernate.exception.ConstraintViolationException;

public class Library implements Closeable {
	private static Library singleton = null;
	
	public static Library get() {
		if (singleton == null) {
			synchronized (Library.class) {
				if (singleton == null) {
					singleton = new Library();
				}
			}
		}
		return singleton;
	}
	
//	private Librarian loggedInLibrarian = null;
	public void logInLibrarian(Librarian librarian) {
		throw new NotImplementedException("");
//		this.loggedInLibrarian = librarian;
	}
	public void logoutLibrarian() {
		throw new NotImplementedException("");
//		this.loggedInLibrarian = null;
	}
	private final EntityManager em;
	private final CriteriaBuilder cb;
	private final CriteriaQuery<Librarian> allLibrariansCriteria;
	private final CriteriaQuery<Book> allBooksCriteria;

	private Library() {
		em = JpaUtil.getEntityManagerFactory().createEntityManager();
		cb = em.getCriteriaBuilder();
		allLibrariansCriteria = cb.createQuery(Librarian.class);
		allBooksCriteria = cb.createQuery(Book.class);
	}

	@Override
	public void close() throws IOException {
		JpaUtil.shutdown();
	}
	
	public Librarian addLibrarian(Librarian librarian) {
		try {
			em.getTransaction().begin();
			em.persist(librarian);
			em.getTransaction().commit();
			return librarian;
		} catch(ConstraintViolationException e) {
			return null;
		} catch(PersistenceException e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				return null;
			}
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<Librarian> getLibrarians() {
	    allLibrariansCriteria.select(allLibrariansCriteria.from(Librarian.class));
	    return em.createQuery(allLibrariansCriteria).getResultList();
	}
	
	public Librarian deleteLibrarian(long id) {
		Librarian librarian = em.find(Librarian.class, id);
		if (librarian == null) {
			return null;
		} else {
			em.getTransaction().begin();
			em.remove(librarian);
			em.getTransaction().commit();
			return librarian;
		}
	}
	
	public Librarian getLibrarian(String email, String password) {
		Root<Librarian> librarian = allLibrariansCriteria.from(Librarian.class);
		CriteriaQuery<Librarian> librarianByEmailAndPass = allLibrariansCriteria.where(cb.and(cb.equal(librarian.get("email"), email), cb.equal(librarian.get("password"), password)));
		return em.createQuery(librarianByEmailAndPass).getSingleResult();
	}
	
	public Book addBook(Book book) {
		try {
			em.getTransaction().begin();
			em.persist(book);
			em.getTransaction().commit();
			return book;
		} catch(ConstraintViolationException e) {
			return null;
		} catch(PersistenceException e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				return null;
			}
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<Book> getBooks() {
	    allBooksCriteria.select(allBooksCriteria.from(Book.class));
	    return em.createQuery(allBooksCriteria).getResultList();
	}

}
