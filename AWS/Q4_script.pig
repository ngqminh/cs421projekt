--load the data from S3 and define the schema
raw = LOAD 'user/hadoop/data2.csv' USING PigStorage(',') AS  (date, type:chararray, parl:int, prov:chararray, riding:chararray, lastname:chararray, firstname:chararray,
	gender:chararray, occupation:chararray, party:chararray,
	votes:int, percent:double, elected:int);

-- Group by Parliament #
parliamentGroup = GROUP raw BY parl;
parliamentGroupParty = GROUP raw BY (parl, party);

-- Count number for each parliament
smmd = FOREACH parliamentGroup GENERATE group, COUNT(raw) as parlCount;


-- Search each parliament for party and prints number of members for each

Members = FOREACH parliamentGroupParty {

	GENERATE group.parl, group.party, COUNT(raw) as MemberCount;
}

--Join the two results
resultTemp = JOIN smmd BY group, Members BY parl;

-- Create proper output format
result = foreach resultTemp GENERATE Members::parl, Members::party, Members::MemberCount, smmd::parlCount;

--print the result tuple to the screen
STORE result INTO 's3://comp421a4.s3-website-us-east-1.amazonaws.com/Q4Result' USING PigStorage(',');

