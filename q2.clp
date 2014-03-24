--This trigger will check/update the Venue's capacity based on how many seats exist for corresponding venue.
CREATE TRIGGER updateCapacity
AFTER INSERT on Seat
Referencing new as nSeat
for each row
begin atomic
UPDATE venue v SET Capacity = (Select count(*) from Seat s where s.venue_id = v.venue_id group by Venue_id)
end