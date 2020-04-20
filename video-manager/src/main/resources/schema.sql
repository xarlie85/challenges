
CREATE TABLE T_CUSTOMER (
  id    INT          GENERATED BY DEFAULT AS IDENTITY,
  name  VARCHAR(250) NOT NULL,
  bonus INT          DEFAULT 0,
  PRIMARY KEY (id)
);

CREATE TABLE T_MOVIE (
  id      INT          GENERATED BY DEFAULT AS IDENTITY,
  title   VARCHAR(250) NOT NULL,
  descr   VARCHAR(250) NOT NULL,
  avail   BOOLEAN      NOT NULL,
  avail_f DATE         NOT NULL,
  type    VARCHAR(20)  NOT NULL,
  PRIMARY KEY (id)    
);

CREATE TABLE T_RENTAL (
  id        INT          GENERATED BY DEFAULT AS IDENTITY,
  r_from    DATE         NOT NULL,
  r_until   DATE         NOT NULL,
  r_by      INT          NOT NULL,
  returned  DATE         NOT NULL,
  cost      INT          DEFAULT 0,
  bonus     INT          DEFAULT 0,
  PRIMARY KEY (id)
);

CREATE TABLE T_RENTAL_MOVIES (
    id    INT GENERATED BY DEFAULT AS IDENTITY,
    id_r  INT NOT NULL,
    id_m  INT NOT NULL,
    PRIMARY KEY (id)  
);

ALTER TABLE T_RENTAL ADD CONSTRAINT R_RBY_TCUS FOREIGN KEY (r_by) REFERENCES T_CUSTOMER
;
ALTER TABLE T_RENTAL_MOVIES ADD CONSTRAINT RM_R FOREIGN KEY (id_r) REFERENCES T_RENTAL
;
ALTER TABLE T_RENTAL_MOVIES ADD CONSTRAINT RM_M FOREIGN KEY (id_m) REFERENCES T_MOVIE
;