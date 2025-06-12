INSERT INTO categories (id, name, is_deleted) VALUES (1, 'Programming', false);

INSERT INTO books (id, title, author, isbn, price, is_deleted)
VALUES (1, 'Java', 'Java author', '9780123456789', 49.99, false);

INSERT INTO books_categories (book_id, category_id) VALUES (1, 1);