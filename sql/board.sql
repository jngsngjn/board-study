CREATE TABLE board (
    board_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    author_id INT NOT NULL,
    content TEXT NOT NULL,
    created_date TIMESTAMP NOT NULL,
    modified_date TIMESTAMP NOT NULL,
    view_count INT NOT NULL DEFAULT 0,
    FOREIGN KEY (author_id) REFERENCES user(user_id)
);