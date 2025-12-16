-- Create a dedicated app user and grant privileges (run as a privileged user)
-- Replace 'app_pass' with a secure password of your choice

CREATE DATABASE IF NOT EXISTS event_club_db;
CREATE USER IF NOT EXISTS 'appuser'@'localhost' IDENTIFIED BY 'app_pass';
GRANT ALL PRIVILEGES ON event_club_db.* TO 'appuser'@'localhost';
FLUSH PRIVILEGES;

-- After running this, copy config.properties.example -> config.properties
-- and set:
-- db.user=appuser
-- db.password=app_pass
