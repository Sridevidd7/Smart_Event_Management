-- Create database
CREATE DATABASE IF NOT EXISTS event_club_db;
USE event_club_db;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    role ENUM('ADMIN', 'STUDENT') NOT NULL DEFAULT 'STUDENT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Clubs table
CREATE TABLE IF NOT EXISTS clubs (
    club_id INT PRIMARY KEY AUTO_INCREMENT,
    club_name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_by INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(user_id)
);

-- Events table
CREATE TABLE IF NOT EXISTS events (
    event_id INT PRIMARY KEY AUTO_INCREMENT,
    event_title VARCHAR(150) NOT NULL,
    club_id INT NOT NULL,
    event_date DATE NOT NULL,
    venue VARCHAR(150),
    description TEXT,
    status ENUM('UPCOMING', 'ONGOING', 'COMPLETED', 'CANCELLED') DEFAULT 'UPCOMING',
    created_by INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (club_id) REFERENCES clubs(club_id),
    FOREIGN KEY (created_by) REFERENCES users(user_id)
);

-- Registrations table
CREATE TABLE IF NOT EXISTS registrations (
    registration_id INT PRIMARY KEY AUTO_INCREMENT,
    event_id INT NOT NULL,
    user_id INT NOT NULL,
    qr_token VARCHAR(255) UNIQUE NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('REGISTERED', 'CHECKED_IN', 'CANCELLED') DEFAULT 'REGISTERED',
    FOREIGN KEY (event_id) REFERENCES events(event_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    UNIQUE KEY unique_registration (event_id, user_id)
);

-- Attendance table
CREATE TABLE IF NOT EXISTS attendance (
    attendance_id INT PRIMARY KEY AUTO_INCREMENT,
    registration_id INT NOT NULL,
    check_in_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    check_out_time TIMESTAMP NULL,
    FOREIGN KEY (registration_id) REFERENCES registrations(registration_id)
);

-- Admin Approval Requests table
CREATE TABLE IF NOT EXISTS admin_approval_requests (
    request_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    requested_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reviewed_by INT NULL,
    reviewed_at TIMESTAMP NULL,
    comments TEXT,
    FOREIGN KEY (reviewed_by) REFERENCES users(user_id)
);

-- Insert sample admin user
INSERT INTO users (email, password, name, role) 
VALUES ('admin@college.edu', 'admin123', 'Admin User', 'ADMIN')
ON DUPLICATE KEY UPDATE user_id = user_id;

-- Insert sample student user
INSERT INTO users (email, password, name, role) 
VALUES ('student@college.edu', 'student123', 'Student User', 'STUDENT')
ON DUPLICATE KEY UPDATE user_id = user_id;

-- Create indexes for better performance
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_event_club ON events(club_id);
CREATE INDEX idx_registration_event ON registrations(event_id);
CREATE INDEX idx_registration_user ON registrations(user_id);
CREATE INDEX idx_attendance_registration ON attendance(registration_id);
