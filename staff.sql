CREATE DATABASE staff;

USE staff;

CREATE TABLE staff_info (
id INT,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR (50) NOT NULL,
nickname VARCHAR (50),
street_address VARCHAR(100),
city VARCHAR(50),
country VARCHAR(50),
postal_code VARCHAR(10),
email VARCHAR(50) UNIQUE
);

DESCRIBE staff_info;
DROP TABLE staff_info;

INSERT INTO staff_info (id, first_name, last_name, nickname, street_address, city, country, postal_code, email) VALUES
('1', 'Anna', 'Runnander', 'Solen', 'Stradsvägen 1', 'Stockholm', 'Sverige', '114 51', 'solen@staff.se'),
('2', 'Simon', 'Jämtgård', 'Merkurius', 'Strandvägen 2', 'Stockholm', 'Sverige', '114 51', 'merkurius@staff.se'),
('3', 'Sara', 'Andersson', 'Venus', 'Strandvägen 3', 'Stockholm', 'Sverige', '114 51', 'venus@staff.se'),
('4', 'Amanda', 'Aronsson', 'Jorden', 'Strandvägen 4', 'Stockholm', 'Sverige', '114 51', 'jorden@staff.se'),
('5', 'Alexander', 'Westerberg Svedberg', 'Mars', 'Strandvägen 5', 'Stockholm', 'Sverige', '114 51', 'mars@staff.se');

SELECT * FROM staff_info;


