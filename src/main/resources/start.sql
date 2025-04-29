-- Create owners table
CREATE TABLE IF NOT EXISTS owners (
                                      id SERIAL PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL,
                                      email VARCHAR(255) NOT NULL UNIQUE,
                                      phone VARCHAR(50)
);

-- Create cars table
CREATE TABLE IF NOT EXISTS cars (
                                    id SERIAL PRIMARY KEY,
                                    model VARCHAR(255) NOT NULL,
                                    year INTEGER NOT NULL,
                                    color VARCHAR(50),
                                    owner_id BIGINT,
                                    FOREIGN KEY (owner_id) REFERENCES owners(id) ON DELETE CASCADE
);

-- Insert sample data for owners
INSERT INTO owners (name, email, phone) VALUES
                                            ('John Doe', 'john.doe@example.com', '+1234567890'),
                                            ('Jane Smith', 'jane.smith@example.com', '+0987654321');

-- Insert sample data for cars
INSERT INTO cars (model, year, color, owner_id) VALUES
                                                    ('Toyota Camry', 2020, 'Black', 1),
                                                    ('Honda Civic', 2019, 'Blue', 1),
                                                    ('Tesla Model 3', 2021, 'White', 2);