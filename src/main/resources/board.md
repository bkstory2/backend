


CREATE TABLE board (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    userId VARCHAR(100) NOT NULL,
    title VARCHAR(100) ,
    body VARCHAR(2000),
    created_at TIMESTAMP DEFAULT now() 
);

