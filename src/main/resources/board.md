


mysql   localhost   3306  ,  db :  test  , root , 1234


CREATE TABLE board (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    userId VARCHAR(100) NOT NULL,
    title VARCHAR(100) ,
    body VARCHAR(2000),
    created_at TIMESTAMP DEFAULT now() 
);


yml 설정 

crud  를   boardController , service , mapper ,  xml    , dto 생성 


read 는 
  1) selectBoard  id 인자값 단건  리턴 
  2) selectList  where 에    userid , title , body   like  로  다건 리턴 