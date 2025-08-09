ALTER TABLE tickets DROP CONSTRAINT uk_active_plate_number;


CREATE UNIQUE INDEX IF NOT EXISTS uk_active_plate_number_only 
ON tickets (plate_number) 
WHERE status = 'Active' AND deleted_at IS NULL;