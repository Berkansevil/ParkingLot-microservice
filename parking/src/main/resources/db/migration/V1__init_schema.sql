-- V1__init_schema.sql

-- USERS TABLE
CREATE TABLE users
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name         VARCHAR(255)                            NOT NULL,
    username     VARCHAR(255)                            NOT NULL,
    password     VARCHAR(255),
    email        VARCHAR(255),
    phone_number VARCHAR(255),
    active       BOOLEAN,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

-- VEHICLES TABLE
CREATE TABLE vehicles
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    registration_number VARCHAR(255) UNIQUE,
    type                VARCHAR(255),
    priority            VARCHAR(255),
    is_active           BOOLEAN,
    registration_date   TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_vehicles PRIMARY KEY (id)
);

-- PARKING LOTS TABLE
CREATE TABLE parking_lots
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    parking_lot_name VARCHAR(255)                            NOT NULL,
    location         VARCHAR(255)                            NOT NULL,
    total_capacity   BIGINT                                  NOT NULL,
    CONSTRAINT pk_parkinglot PRIMARY KEY (id)
);

-- PARKING FLOOR TABLE
CREATE TABLE park_floor
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    floor          INT                                     NOT NULL,
    capacity       INT                                     NOT NULL,
    floor_block    VARCHAR(255),
    parking_lot_id BIGINT                                  NOT NULL,
    CONSTRAINT pk_park_floor PRIMARY KEY (id),
    CONSTRAINT fk_park_floor_lot FOREIGN KEY (parking_lot_id) REFERENCES parking_lots (id)
);

-- PARKING SPOT TABLE
CREATE TABLE parking_spots
(
     id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
        parking_spot_type VARCHAR(255)                           NOT NULL,
        is_spot_available BOOLEAN                        NOT NULL,
        parking_spot_location VARCHAR(255)                       NOT NULL,
        created_at        TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
        updated_at        TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,

        CONSTRAINT pk_parkingspot PRIMARY KEY (id)
);


-- BOOKİNG SPOT TABLE
CREATE TABLE booking (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    vehicle_id BIGINT NOT NULL,
    parking_spot_id BIGINT NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    CONSTRAINT fk_booking_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_booking_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles(id),
    CONSTRAINT fk_booking_parking_spot FOREIGN KEY (parking_spot_id) REFERENCES parking_spots(id)
);

-- USER FEEDBACK
CREATE TABLE user_feedbacks (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_id BIGINT NOT NULL,
    feedback_text VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL ,
    CONSTRAINT pk_user_feedbacks PRIMARY KEY (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
)
