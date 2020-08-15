DROP TABLE IF EXISTS UserMappingWithSne;

CREATE TABLE UserMappingWithSne (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    userId VARCHAR(250) NOT NULL,
    account VARCHAR(250) NOT NULL,
    function INT NOT NULL,
    zoomEndpoint VARCHAR(250) NOT NULL,
    zoomVerificationCode VARCHAR(250) NOT NULL,
    cosmosDbName VARCHAR(250) NOT NULL,
    appInsightName VARCHAR(250) NOT NULL,
    subscription VARCHAR(250) NOT NULL,
    groupName VARCHAR(250) NOT NULL,
    cloudSvc VARCHAR(250) NOT NULL,
    slackURL VARCHAR(250) NOT NULL
)