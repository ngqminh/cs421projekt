CREATE INDEX categories ON event(category, title) 

CREATE INDEX eventSeats ON ticket(event_id, seat_id) CLUSTER