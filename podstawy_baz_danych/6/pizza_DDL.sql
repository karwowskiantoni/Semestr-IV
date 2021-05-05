create table MENU(
	pizza character varying(255) NOT NULL,
       	price real NOT NULL,
       	country character varying(255),
        base character varying(255) NOT NULL,
	CONSTRAINT menu_pk PRIMARY KEY (pizza)
);
create table ITEMS(
	ingredient character varying(255) NOT NULL, 
	type character varying(255) NOT NULL, 
	CONSTRAINT items_pk PRIMARY KEY (ingredient)
);
create table RECIPES(
	pizza character varying(255) NOT NULL,
	ingredient character varying(255) NOT NULL,
       	amount integer NOT NULL,
	CONSTRAINT recipes_pk PRIMARY KEY (pizza, ingredient),
	CONSTRAINT recipes_fk_1 FOREIGN KEY (pizza) REFERENCES MENU(pizza),
	CONSTRAINT recipes_fk_2 FOREIGN KEY (ingredient) REFERENCES ITEMS(ingredient)
);
