CREATE DATABASE online_shop
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Russian_Russia.1251'
       LC_CTYPE = 'Russian_Russia.1251'
       CONNECTION LIMIT = -1;

CREATE TABLE "category" (
	"id" serial NOT NULL,
	"name" varchar NOT NULL UNIQUE,
	"description" varchar,
	"rating" int NOT NULL,
	CONSTRAINT category_pk PRIMARY KEY ("id")
);



CREATE TABLE "characteristic" (
	"id" serial NOT NULL,
	"code" varchar NOT NULL UNIQUE,
	"name" varchar NOT NULL UNIQUE,
	"measure_unit" varchar NOT NULL,
	"value_data_type" varchar NOT NULL,
	CONSTRAINT characteristic_pk PRIMARY KEY ("id")
);


CREATE TABLE "characteristic_value" (
	"id" serial NOT NULL,
	"value" varchar NOT NULL,
	"characteristic_id" bigint NOT NULL,
	CONSTRAINT characteristic_value_pk PRIMARY KEY ("id")
);


CREATE TABLE "category_characteristic" (
	"category_id" bigint NOT NULL,
	"characteristic_id" bigint NOT NULL,
	"required" BOOLEAN NOT NULL,
	CONSTRAINT category_characteristic_pk PRIMARY KEY ("category_id", "characteristic_id")
);


CREATE TABLE "item" (
	"id" int NOT NULL,
	"name" varchar NOT NULL,
	"producer" varchar NOT NULL,
	"storage" int NOT NULL,
	"description" varchar,
	"price" int NOT NULL,
	"category_id" int NOT NULL,
	"photo_name_original" varchar,
	"photo_name_compressed" varchar,
	"popularity" int NOT NULL,
	"seller_id" bigint NOT NULL,
	CONSTRAINT item_pk PRIMARY KEY ("id")
);



CREATE TABLE "characteristic_item" (
	"item_id" bigint NOT NULL,
	"characteristic_id" bigint NOT NULL,
	"characteristic_value_id" bigint NOT NULL,
	CONSTRAINT item_characteristic_pk PRIMARY KEY ("item_id", "characteristic_id")
);



CREATE TABLE "order_info" (
	"id" bigint NOT NULL,
	"user_id" bigint NOT NULL,
	"status_id" bigint NOT NULL,
	"date" DATE,
	"delivery_address" varchar,
	CONSTRAINT order_info_pk PRIMARY KEY ("id")
);



CREATE TABLE "order_info_item" (
	"item_id" bigint NOT NULL,
	"order_id" bigint NOT NULL,
	"quantity" int NOT NULL,
	CONSTRAINT item_order_info_pk PRIMARY KEY ("item_id", "order_id")
);



CREATE TABLE "status" (
	"id" bigint NOT NULL,
	"name" varchar NOT NULL,
	"description" varchar,
	CONSTRAINT status_pk PRIMARY KEY ("id")
);

CREATE TABLE "role_info" (
	"id" bigint NOT NULL,
	"name" varchar NOT NULL,
	"description" varchar,
	CONSTRAINT role_pk PRIMARY KEY ("id")
);


CREATE TABLE "user_info" (
	"id" bigint NOT NULL,
	"role_id" bigint NOT NULL,
	"email" varchar NOT NULL UNIQUE,
	"password" varchar NOT NULL,
	"name" varchar,
	"surname" varchar,
	"parental_name" varchar,
	"address" varchar,
	"phone_number" varchar,
	CONSTRAINT user_info_pk PRIMARY KEY ("id")
);

CREATE TABLE "seller" (
"user_id" bigint NOT NULL,
"income" bigint NOT NULL,
CONSTRAINT seller_pk PRIMARY KEY ("user_id")
);

ALTER TABLE "category_characteristic" ADD CONSTRAINT "category_characteristic_fk0" FOREIGN KEY ("category_id") REFERENCES "category"("id");
ALTER TABLE "category_characteristic" ADD CONSTRAINT "category_characteristicc_fk1" FOREIGN KEY ("characteristic_id") REFERENCES "characteristic"("id");

ALTER TABLE "item" ADD CONSTRAINT "item_fk0" FOREIGN KEY ("category_id") REFERENCES "category"("id");
ALTER TABLE "item" ADD CONSTRAINT "item_fk1" FOREIGN KEY ("seller_id") REFERENCES "seller"("user_id");

ALTER TABLE "characteristic_item" ADD CONSTRAINT "item_characteristic_fk0" FOREIGN KEY ("item_id") REFERENCES "item"("id");
ALTER TABLE "characteristic_item" ADD CONSTRAINT "item_characteristic_fk1" FOREIGN KEY ("characteristic_id") REFERENCES "characteristic"("id");
ALTER TABLE "characteristic_item" ADD CONSTRAINT "item_characteristic_fk2" FOREIGN KEY ("characteristic_value_id") REFERENCES "characteristic_value"("id");

ALTER TABLE "order_info" ADD CONSTRAINT "order_info_fk0" FOREIGN KEY ("user_id") REFERENCES "user_info"("id");
ALTER TABLE "order_info" ADD CONSTRAINT "order_info_fk1" FOREIGN KEY ("status_id") REFERENCES "status"("id");

ALTER TABLE "order_info_item" ADD CONSTRAINT "order_info_item_fk0" FOREIGN KEY ("item_id") REFERENCES "item"("id");
ALTER TABLE "order_info_item" ADD CONSTRAINT "order_info_item_fk1" FOREIGN KEY ("order_id") REFERENCES "order_info"("id");
ALTER TABLE "user_info" ADD CONSTRAINT "user_info_fk2" FOREIGN KEY ("role_id") REFERENCES "role_info"("id");

ALTER TABLE "characteristic_value" ADD CONSTRAINT "characteristic_value_fk1" FOREIGN KEY ("characteristic_id") REFERENCES "characteristic" ("id") ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "seller" ADD CONSTRAINT "seller_fk0" FOREIGN KEY ("user_id") REFERENCES "user_info"("id");