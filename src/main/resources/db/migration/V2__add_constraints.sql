ALTER TABLE bookings
    ADD CONSTRAINT uc_cfeff974d23ab051b7d0f4026 UNIQUE (court_id, start_at, end_at);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE bookings
    ADD CONSTRAINT FK_BOOKINGS_ON_COURT FOREIGN KEY (court_id) REFERENCES court (id);

ALTER TABLE bookings
    ADD CONSTRAINT FK_BOOKINGS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE court
    ADD CONSTRAINT FK_COURT_ON_ARENA FOREIGN KEY (arena_id) REFERENCES arenas (id);