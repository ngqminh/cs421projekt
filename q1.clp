CREATE TABLE attendeeXML(email VARCHAR(50) NOT NULL PRIMARY KEY, Info XML)


INSERT INTO attendeeXML(email, Info) VALUES('email1@at.com', \
'<attendee email = "email1@at.com"> \
 <fname>fname1</fname> \
 <lname>lname1</lname> \
 <phone_number>phone1</phone_number> \
 <home_address>home1</home_address> \
</attendee>')

INSERT INTO attendeeXML(email, Info) VALUES('email2@at.com', \
'<attendee email = "email2@at.com"> \
 <fname>fname2</fname> \
 <lname>lname2</lname> \
 <phone_number>phone2</phone_number> \
 <home_address>home2</home_address> \
</attendee>')

INSERT INTO attendeeXML(email, Info) VALUES('email3@at.com', \
'<attendee email = "email3@at.com"> \
 <fname>fname3</fname> \
 <lname>lname3</lname> \
 <phone_number>phone3</phone_number> \
 <home_address>home3</home_address> \
</attendee>')

INSERT INTO attendeeXML(email, Info) VALUES('email7@ator.com', \
'<attendee email = "email7@ator.com"> \
 <fname>fname7</fname> \
 <lname>lname7</lname> \
 <phone_number>phone7</phone_number> \
 <home_address>home7</home_address> \
</attendee>')

INSERT INTO attendeeXML(email, Info) VALUES('email8@ator.com', \
'<attendee email = "email8@ator.com"> \
 <fname>fname8</fname> \
 <lname>lname8</lname> \
 <phone_number>phone8</phone_number> \
 <home_address>home8</home_address> \
</attendee>')

INSERT INTO attendeeXML(email, Info) VALUES('email9@ator.com', \
'<attendee email = "email9ator.com"> \
 <fname>fname9</fname> \
 <lname>lname2</lname> \
 <phone_number>phone9</phone_number> \
 <home_address>home9</home_address> \
</attendee>')

