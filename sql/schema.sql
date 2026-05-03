CREATE TABLE clothes (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    name VARCHAR(255) NOT NULL,
    size VARCHAR(10) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    material VARCHAR(255) NOT NULL,
    waist_size DECIMAL(10, 2),
    sleeve_length DECIMAL(10, 2),
    pocket_count INTEGER,
    brim_width DECIMAL(10, 2),
    CONSTRAINT chk_clothes_type
        CHECK (type IN ('Pants', 'Shirt', 'Jacket', 'Hat')),
    CONSTRAINT chk_clothes_price
        CHECK (price > 0),
    CONSTRAINT chk_clothes_waist_size
        CHECK (waist_size IS NULL OR waist_size > 0),
    CONSTRAINT chk_clothes_sleeve_length
        CHECK (sleeve_length IS NULL OR sleeve_length > 0),
    CONSTRAINT chk_clothes_pocket_count
        CHECK (pocket_count IS NULL OR (pocket_count >= 0 AND pocket_count <= 10)),
    CONSTRAINT chk_clothes_brim_width
        CHECK (brim_width IS NULL OR brim_width > 0)
);
