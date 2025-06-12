INSERT INTO categories (id, name, is_deleted) VALUES (1, 'Programming', false);
INSERT INTO categories (id, name, is_deleted) VALUES (2, 'History', false);

INSERT INTO books (id, title, author, isbn, price, is_deleted)
VALUES (1, 'Java', 'Java author', '9780123456789', 49.99, false);
INSERT INTO books (id, title, author, isbn, price, is_deleted)
VALUES (2, 'History of UA', 'History author', '9780123456780', 59.99, false);

INSERT INTO books_categories (book_id, category_id) VALUES (1, 1);
INSERT INTO books_categories (book_id, category_id) VALUES (2, 2);