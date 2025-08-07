
CREATE INDEX IF NOT EXISTS idx_tickets_active_lookup 
ON tickets (plate_number, status, deleted_at) 
WHERE deleted_at IS NULL;

ALTER TABLE tickets 
ADD CONSTRAINT uk_active_plate_number 
UNIQUE (plate_number, status);


ALTER TABLE tickets 
ADD CONSTRAINT chk_ticket_status 
CHECK (status IN ('Active', 'Completed', 'Cancelled'));