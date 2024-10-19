DROP TABLE IF EXISTS job_ad_history;
CREATE TABLE job_ad_history
(
    id       BIGINT      NOT NULL AUTO_INCREMENT,
    title VARCHAR(1024),
    time DATETIME,
    description TEXT,
    link    VARCHAR(2048),
    company    VARCHAR(1024),
    location    VARCHAR(1024),
    PRIMARY KEY (id)
);