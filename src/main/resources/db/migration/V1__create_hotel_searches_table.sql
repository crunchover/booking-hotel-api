CREATE TABLE IF NOT EXISTS hotel_searches (
    id            BIGSERIAL       PRIMARY KEY,
    hotel_id      VARCHAR(255)    NOT NULL,
    check_in      DATE            NOT NULL,
    check_out     DATE            NOT NULL,
    hash          VARCHAR(100)    NOT NULL,
    search_count  BIGINT          NOT NULL DEFAULT 1,
    created       TIMESTAMP       NOT NULL,
    updated       TIMESTAMP,
    CONSTRAINT uq_hotel_searches_hash UNIQUE (hash)
);

CREATE TABLE IF NOT EXISTS hotel_search_ages (
    search_id   BIGINT  NOT NULL,
    age         INTEGER NOT NULL,
    age_order   INTEGER NOT NULL,
    CONSTRAINT fk_hotel_search_ages_search FOREIGN KEY (search_id) REFERENCES hotel_searches(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_hotel_searches_hash ON hotel_searches(hash);
