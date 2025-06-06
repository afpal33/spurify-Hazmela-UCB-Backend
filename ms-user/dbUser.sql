-- Create the database



-- Base table for users (general data)
CREATE TABLE "user" (
                        user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        first_name VARCHAR(100) NOT NULL,
                        last_name VARCHAR(100) NOT NULL,
                        email VARCHAR(150) UNIQUE NOT NULL,
                        phone VARCHAR(20),
                        registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        user_type VARCHAR(20) CHECK (user_type IN ('STUDENT', 'ADMIN')),
                        active BOOLEAN DEFAULT TRUE
);

-- Specific table for students (complete profile)
CREATE TABLE student (
                         user_id UUID PRIMARY KEY REFERENCES "user"(user_id) ON DELETE CASCADE,
                         address TEXT NOT NULL,
                         birth_date DATE NOT NULL,
                         gender VARCHAR(20) CHECK (gender IN ('MALE', 'FEMALE', 'OTHER')),
                         country VARCHAR(100) NOT NULL,
                         city VARCHAR(100) NOT NULL,
                         university VARCHAR(255) NOT NULL,
                         career VARCHAR(255) NOT NULL,
                         semester INT CHECK (semester > 0),
                         average DECIMAL(3,2) CHECK (average BETWEEN 0 AND 10),
                         study_mode VARCHAR(50) CHECK (study_mode IN ('ON-CAMPUS', 'ONLINE', 'HYBRID')),
                         experience TEXT,
                         highlighted_projects TEXT,
                         courses_certifications TEXT,
                         academic_interests TEXT,
                         linkedin TEXT,
                         github TEXT,
                         website TEXT,
                         profile_picture TEXT,
                         document_university  TEXT,
                         complete_profile BOOLEAN DEFAULT FALSE
);

-- Specific table for administrators
CREATE TABLE administrator (
                               user_id UUID PRIMARY KEY REFERENCES "user"(user_id) ON DELETE CASCADE,
                               role VARCHAR(100) NOT NULL,
                               permissions TEXT[]
);

-- Skills table
CREATE TABLE skill (
                       skill_id SERIAL PRIMARY KEY,
                       name VARCHAR(100) UNIQUE NOT NULL
);

-- Many-to-Many relationship between students and skills
CREATE TABLE student_skill (
                               user_id UUID REFERENCES student(user_id) ON DELETE CASCADE,
                               skill_id INT REFERENCES skill(skill_id) ON DELETE CASCADE,
                               PRIMARY KEY (user_id, skill_id)
);

-- User audit table
CREATE TABLE user_audit (
                            audit_id SERIAL PRIMARY KEY,
                            user_id UUID NOT NULL,
                            action VARCHAR(50) CHECK (action IN ('CREATE', 'UPDATE', 'DELETE')),
                            action_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            previous_data JSONB,
                            device_address VARCHAR(100) NOT NULL,
                            ip_address VARCHAR(100) NOT NULL
);
