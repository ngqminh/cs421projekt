--load the data from S3 and define the schema
raw = LOAD 'user/hadoop/data2.csv' USING PigStorage(',') AS  (date, type:chararray, parl:int, prov:chararray, riding:chararray, lastname:chararray, firstname:chararray,
	gender:chararray, occupation:chararray, party:chararray,
	votes:int, percent:double, elected:int);

--some data entries use the middle name as well, so this way we will catch all of them
--fltrd = FILTER raw by lastname == 'Harper' and firstname matches 'Stephen.*';
-- Filter data entries with percetage greater or equal to 60
fltrd = FILTER raw BY (percent >= 60.00);

--project only the needed fields
--gen = foreach fltrd generate date, lastname, firstname;
 gen = foreach fltrd generate CONCAT(firstname, CONCAT(' ', lastname));

--unique

results = DISTINCT gen;

--sort the entries by the election date
--odred = order fltrd by date;

--choose only the smallest date
--results = limit odred 1;



--print the result tuple to the screen
STORE results INTO 's3://comp421a4.s3-website-us-east-1.amazonaws.com/Q1Result' USING PigStorage(',');

dump results;
