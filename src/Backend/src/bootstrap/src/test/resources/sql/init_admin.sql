INSERT INTO users (
    name,
    email, 
    username, 
    password, 
    is_active, 
    created_at, 
) VALUES (
    'Admin',  
    'admin@arka.com', 
    'admin', 
    '$2a$10$TQzN2qvQJ0y3lXyeBev7VuvVtqtJZSz1S.rCIkN3P1dE5xso8RpuG', -- password: admin123
    true, 
    NOW(), 
);