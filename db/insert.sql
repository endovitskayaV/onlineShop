INSERT INTO role_info  (id, name) VALUES (1, 'seller'), (2, 'customer');

INSERT INTO user_info (id, role_id, email, password) VALUES 
(1,1, 'seller@mail.ru','$2a$10$U3u5rvm0M1Wk4xMkSfixR.Qb3HrMAuLdsueRWOgYCCez9WYyG7LLe'),
(2,2, 'customer@mail.ru','$2a$10$U3u5rvm0M1Wk4xMkSfixR.Qb3HrMAuLdsueRWOgYCCez9WYyG7LLe'),
(3,1, 'seller2@mail.ru','$2a$10$U3u5rvm0M1Wk4xMkSfixR.Qb3HrMAuLdsueRWOgYCCez9WYyG7LLe'),
(4,2, 'customer2@mail.ru','$2a$10$U3u5rvm0M1Wk4xMkSfixR.Qb3HrMAuLdsueRWOgYCCez9WYyG7LLe');

INSERT INTO seller (user_id, income) VALUES 
(1,0),
(3,0);

INSERT INTO status (id, description, name) VALUES 
(1, '', 'busket'),
(2, '', 'processing'),
(3, '', 'ready to ship'),
(4, '', 'sent'),
(5, '', 'delivered');

INSERT INTO order_info (id, user_id, status_id) VALUES 
(1, 1, 1),
(2, 2, 1);


INSERT INTO category (id, name, description,rating) VALUES 
(1, 'smartphone', 'description', 1),
(2, 'appliance', 'description', 3),
(3, 'food', 'description', 10),
(4, 'cloth', 'description', 5);

INSERT INTO characteristic (id, code, name, measure_unit, value_data_type) VALUES 
(1,'wght', 'weight', 'g', 'FRACTIONAL'),
(3, 'lngth','length', 'm', 'FRACTIONAL'),
(4, 'hght', 'height', 'm', 'FRACTIONAL'),
(5, 'clr','color', '', 'STRING'),
(6, 'diag', 'diagonal', 'inch', 'FRACTIONAL');


INSERT INTO characteristic_value (id,  characteristic_id, value) VALUES 
(1,1, '150'),
(2,6, '6'),
(3,1, '120'),
(4,6, '5.5'),
(5,1, '2000'),
(6,5, 'red'),
(7,1, '1000'),
(8,5, 'black'),
(9,5, 'blue');

INSERT INTO category_characteristic (category_id, characteristic_id, required) VALUES
(1, 1,true),
(1, 6,true),
(2, 1,true),
(2, 5,true),
(3, 1,true),
(4, 5,true);

INSERT INTO item (id, name, producer, storage,description, price, category_id, popularity, seller_id, photo_name_original,photo_name_compressed) VALUES
(1, 'm3s mini','meizu', 335,'description', 10000, 1,1,1, 'no_photo.png', 'no_photo_compressed.jpg'),
(2, 'galaxy s8','samsung', 100,'description', 30000, 1,0,1, 'no_photo.png','no_photo_compressed.jpg'),
(3, 'd4','borch', 12,'description', 23000, 2,5,1,'no_photo.png','no_photo_compressed.jpg'),
(4, 'apple','bag', 1234,'description', 100, 3,10,1,'no_photo.png','no_photo_compressed.jpg'),
(5, 'dress cute','lime', 15,'description', 20000, 4,6,1,'no_photo.png','no_photo_compressed.jpg'),
(6, 'jeans','zolla', 15,'description', 2000, 4,2,3,'no_photo.png','no_photo_compressed.jpg');

INSERT INTO characteristic_item (item_id, characteristic_id, characteristic_value_id) VALUES 
(1,1, 1),
(1,6, 2),
(2,1, 3),
(2,6, 4),
(3,1, 5),
(3,5, 6),
(4,1, 7),
(5,5, 8),
(6,5, 9);

INSERT INTO order_info_item (item_id, order_id, quantity) VALUES 
(1, 1, 1),
(2, 1, 1),
(5, 1, 2),
(1, 2, 1),
(3, 2, 1);