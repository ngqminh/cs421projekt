--load the data from S3 and define the schema
raw = LOAD '/user/hadoop/data2.csv' USING PigStorage(',') AS  (date, type:chararray, parl:int, prov:chararray, riding:chararray, lastname:chararray, firstname:chararray, gender:chararray, occupation:chararray, party:chararray, votes:int, percent:double, elected:int);

-- filter by winner and loser
winner = FILTER raw by elected == 1;
loser = FILTER raw by elected == 0;

-- join winner and loser, get both last names and the difference, then filter where difference < 10
cand = JOIN winner by (date, type, parl, prov, riding) LEFT OUTER, loser by (date, type, parl, prov, riding);
vDiff = foreach cand generate winner::lastname, loser::lastname, (winner::votes - loser::votes) as diff;
res = filter vDiff by diff < 10;

dump res;