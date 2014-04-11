--load the data from S3 and define the schema
raw = LOAD 'user/hadoop/ngrams421.csv' USING PigStorage(',') AS (word:chararray,year:chararray,occurences:int);

-- Filter year
fltrd = FILTER raw BY year == '1990';

-- Group
fltrdGroup = GROUP fltrd ALL;

-- sum up 
counts = FOREACH fltrdGroup GENERATE SUM(fltrd.occurences) as wordCount;

dump counts;

