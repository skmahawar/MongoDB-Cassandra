CREATE TYPE address (
    street_name text,
    street_number int,
    city text,
    state text,
    zip int
);


    
create type work_and_home_addresses(
    home_address address,
    work_address address
);