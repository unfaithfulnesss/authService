-- Clear existing users (optional)
DELETE FROM users;

-- Insert test users with proper BCrypt passwords
-- Password for all users: "password123"
INSERT INTO users (id, first_name, last_name, email, password, role, account_locked, account_enabled, failed_attempts, created_at) VALUES
                                                                                                                                   (1, 'John', 'Doe', 'admin@test.com', '$2a$12$8mjDKXHmf5DuwgljHfypiux.mzgnRAZZ00sy5JRsTbWPVqKKD5tqO', 'ADMIN', false, true, 0, NOW()),
                                                                                                                                   (2,'Jane', 'Smith', 'jane.smith@example.com', '$2a$12$8mjDKXHmf5DuwgljHfypiux.mzgnRAZZ00sy5JRsTbWPVqKKD5tqO', 'USER', false, true, 0, NOW()),
                                                                                                                                   (3,'Bob', 'Johnson', 'bob.johnson@example.com', '$2a$12$8mjDKXHmf5DuwgljHfypiux.mzgnRAZZ00sy5JRsTbWPVqKKD5tqO', 'USER', true, true, 3, NOW()), -- Locked account
                                                                                                                                   (4,'Alice', 'Williams', 'alice.w@example.com', '$2a$12$8mjDKXHmf5DuwgljHfypiux.mzgnRAZZ00sy5JRsTbWPVqKKD5tqO', 'USER', false, false, 0, NOW()), -- Disabled account
                                                                                                                                   (5,'David', 'Miller', 'david.m@example.com', '$2a$12$8mjDKXHmf5DuwgljHfypiux.mzgnRAZZ00sy5JRsTbWPVqKKD5tqO', 'USER', false, true, 0, NOW()),
                                                                                                                                   (6,'Emma', 'Wilson', 'emma.w@example.com', '$2a$12$8mjDKXHmf5DuwgljHfypiux.mzgnRAZZ00sy5JRsTbWPVqKKD5tqO', 'USER', false, true, 0, NOW()),
                                                                                                                                   (7,'Frank', 'Taylor', 'frank.t@example.com', '$2a$12$8mjDKXHmf5DuwgljHfypiux.mzgnRAZZ00sy5JRsTbWPVqKKD5tqO', 'USER', false, true, 1, NOW()),
                                                                                                                                   (8,'Grace', 'Anderson', 'grace.a@example.com', '$2a$12$8mjDKXHmf5DuwgljHfypiux.mzgnRAZZ00sy5JRsTbWPVqKKD5tqO', 'USER', false, true, 0, NOW()),
                                                                                                                                   (9,'Henry', 'Thomas', 'henry.t@example.com', '$2a$12$8mjDKXHmf5DuwgljHfypiux.mzgnRAZZ00sy5JRsTbWPVqKKD5tqO', 'USER', true, true, 5, NOW()); -- Locked after many attempts