DROP TABLE IF EXISTS user_mapping_with_sne;

CREATE TABLE user_mapping_with_sne (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    userId VARCHAR(250) NOT NULL,
    account VARCHAR(250) NOT NULL,
    function INT NOT NULL,
    zoomEndpoint VARCHAR(250),
    zoomVerificationCode VARCHAR(250)
)

DROP TABLE IF EXISTS functions;

CREATE TABLE functions (
    functionID INT PRIMARY KEY,
    functionName VARCHAR(250) NOT NULL,
    account VARCHAR(250) NOT NULL
)