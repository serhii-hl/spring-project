package core.basesyntax.repository.impl;

import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.Book;
import core.basesyntax.repository.BookRepository;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;

    @Override
    public Book save(Book book) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can`t save the book: " + book, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Book> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from Book", Book.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can`t find all the books", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from Book where id = :id", Book.class);
            return Optional.ofNullable((Book) query.setParameter("id", id).getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can`t find the book by id: " + id, e);
        }
    }
}
