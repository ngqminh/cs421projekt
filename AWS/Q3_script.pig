--load the data from S3 and define the schema
raw = LOAD '/user/hadoop/data2.csv' USING PigStorage(',') AS  (date, type:chararray, parl:int, prov:chararray, riding:chararray, lastname:chararray, firstname:chararray,
	gender:chararray, occupation:chararray, party:chararray,
	votes:int, percent:double, elected:int);

--filter for general elections and only members that had won
filtered = FILTER raw by type == 'Gen' and elected == 1;

grouped = GROUP filtered BY parl;
--schema looks nested immediately after grouping

--count the number of parliament members in each session of parliament
counted = FOREACH grouped GENERATE group, COUNT(filtered);

--order by the parliament number
ordered = ORDER counted BY group ASC;

dump ordered;
