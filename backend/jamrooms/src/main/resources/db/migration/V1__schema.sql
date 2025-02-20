DROP TABLE IF EXISTS spotify_token;
DROP TABLE IF EXISTS room;

CREATE TABLE spotify_token (
    id              SERIAL PRIMARY KEY,
    session_id      VARCHAR(36) NOT NULL UNIQUE,
    access_token    TEXT NOT NULL,
    refresh_token   TEXT NOT NULL,
    token_type      VARCHAR(50) NOT NULL,
    expires_in      INT NOT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP
);

CREATE TABLE room (
    id              SERIAL PRIMARY KEY,
    host_session_id VARCHAR(36) NOT NULL,
    room_code       VARCHAR(50) NOT NULL UNIQUE,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_room_token FOREIGN KEY (host_session_id)
        REFERENCES spotify_token (session_id)
        ON DELETE CASCADE
);

CREATE INDEX idx_spotify_token_session_id on spotify_token (session_id);
CREATE INDEX idx_room_room_code ON room (room_code);
