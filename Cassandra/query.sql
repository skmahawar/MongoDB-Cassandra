--ALTER TABLE listing DROP category_id;

ALTER TABLE listing ADD address2 varchar(50);

--ALTER TABLE listing CHANGE address address1 text;

ALTER TABLE listing ADD state varchar(50);

ALTER TABLE listing ADD phone_no2 varchar(50);

--ALTER TABLE listing ADD email varchar(50);

ALTER TABLE listing ADD owner_id varchar(50);

ALTER TABLE listing ADD price_range varchar(50);

--ALTER TABLE listing_category ALTER id int AUTO_INCREMENT;


CREATE TABLE listing_category(
	id int primary key AUTO_INCREMENT,
	pk_listing_id int,
	pk_category_id int,
	foreign key (pk_listing_id) references listing(listing_id),
	foreign key (pk_category_id) references category(category_id)
);


INSERT INTO listing_category (id, pk_listing_id, pk_category_id) VALUES();

SELECT l.business_name, l.creater, l.location, l.address, l.address2, l.city, l.state, l.pin, l.phone_no, l.phone_no2, l.time_open, l.time_close, l.website, l.facebook, l.twitter, l.instagram, l.description, l.card, l.cash, l.rating, l.date,  l.datetime, l.s_day, l.e_day, l.approved, l.rejected FROM listing as l inner join listing_category as lc on l.listing_id = ld.pk_listing_id AND ld.category_id = ?;



--SELECT a.id, a.name from test1 as a inner join jointable as b on a.id = b.pk1_id and pk2_id = 1;




 listing_id: 43
      creater: him1@gmail.com
business_name: Ankur Books
  category_id: 1
     location:
      address: Meerut
         city: Meerut
          pin: 250001
     phone_no: 123510541
    time_open: 9:42 AM
   time_close: 6:42 PM
      website: www.abc.com
     facebook: abc@gmail.com
      twitter: abc@gmail.com
    instagram: abc@gmail.com
  description: New book store
         card: Card
         cash: Cash
       rating: 0
         date: 2015-07-31
     datetime: 2015-07-31 13:43:59
        s_day: Sat
        e_day: Mon
     approved: 0
     rejected: 1



 name: Himanshu
      email: him1@gmail.com
  mobile_no: 7765955799
   kids_age: 7
   location: gaziabad
     gender: 0
   relation: brother
    role_id: dealer
social_link:
   password: himanshu
       date: 0000-00-00
   datetime: 0000-00-00 00:00:00
