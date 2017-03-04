drop table dbo.bg_anag_proprieta;
drop view  bg_allegato_vw;



CREATE  TABLE bg_anag_proprieta (
  id_prop INT NOT NULL auto_increment,
  nome_proprieta varchar(1000) NOT NULL ,
  valore_proprieta varchar(1000) NOT NULL ,
  flag_multiplo varchar(1),
  PRIMARY KEY (id_anag_prop) );
  


CREATE TABLE bg_item
(
   id_item int PRIMARY KEY NOT NULL,
   testo longtext NOT NULL,
   titolo text NOT NULL,
   nome varchar(100) NOT NULL,
   riassunto text,
   quantita int NULL ,
   autore varchar(100) NOT NULL,
   data_pubblicazione timestamp NOT NULL,
   data_modifica timestamp,
   id_Tipo_Item int NOT NULL
)
;
ALTER TABLE bg_item
ADD CONSTRAINT FK_Item_TipoItem
FOREIGN KEY (id_Tipo_Item)
REFERENCES bg_tipo_item(id_Tipo)
;
CREATE UNIQUE INDEX PRIMARY ON bg_item(id_item)
;
CREATE INDEX FK_Item_TipoItem ON bg_item(id_Tipo_Item)
;

alter table bg_item add (
data_inserimento timestamp
 ,data_hidden timestamp
 ,data_scadenza timestamp);

CREATE  TABLE bg_lk_item_property (
  id_lk_item_prop INT NOT NULL auto_increment,
  id_prop INT NOT NULL ,
  id_item INT NOT NULL ,
  value varchar(100) ,
  data_modifica timestamp,
  data_inserimento timestamp,
  PRIMARY KEY (id_lk_item_prop));

ALTER TABLE bg_lk_item_property   ADD  CONSTRAINT FK_lk_item_property_item FOREIGN KEY(id_prop)
REFERENCES bg_property (id_prop);

ALTER TABLE bg_lk_item_property ADD  CONSTRAINT FK_lk_item_property_property FOREIGN KEY(id_item)
REFERENCES bg_item (id_item);  

Create view bg_allegato_vw as
SELECT id_Allegato,a.id_item,i.nome, nome_Allegato,content_type,contenuto,contenuto_Testo,a.data_pubblicazione,a.data_modifica 
FROM 
	dbo.bg_Allegato a
	, dbo.bg_item i
where 
a.id_item=i.id_item

--da cancellare dopo il lancio
alter table bg_lk_item_property add value varchar(100);
alter table bg_lk_item_property add data_modifica timestamp;
alter table bg_lk_item_property add data_inserimento timestamp;
--Fine da cancellare dopo il lancio



CREATE  TABLE bg_cart (
  id_carrello INT NOT NULL auto_increment,
  utente varchar(1000) NOT NULL ,
  data_inserimento timestamp not NULL ,
  data_modifica timestamp NULL, 
  PRIMARY KEY (id_carrello) );

CREATE  TABLE bg_cart_detail (
  id_carrello INT NOT NULL,
  progressivo int not null,
  id_item int not null,
  quantita int not null,
  costo float,
  data_inserimento timestamp not NULL ,
  data_modifica timestamp NULL, 
  PRIMARY KEY (id_carrello, progressivo) );

ALTER TABLE bg_cart_detail ADD  CONSTRAINT FK_bg_cart_detail FOREIGN KEY(id_item) REFERENCES bg_item (id_item);

ALTER TABLE bg_cart_detail ADD CONSTRAINT FK1_bg_cart_detail FOREIGN KEY(id_carrello) REFERENCES bg_cart(id_carrello);

CREATE  TABLE bg_order (
  id_ordine INT NOT NULL auto_increment,
  utente varchar(1000) NOT NULL ,
  data_inserimento timestamp not NULL ,
  data_modifica timestamp NULL, 
  PRIMARY KEY (id_ordine) );

CREATE  TABLE bg_order_detail (
  id_ordine INT NOT NULL,
  progressivo int not null,
  id_item int not null,
  quantita int not null,
  costo float,
  data_inserimento timestamp not NULL ,
  data_modifica timestamp NULL, 
  PRIMARY KEY (id_ordine,progressivo) );

ALTER TABLE bg_order_detail ADD CONSTRAINT FK_bg_order_detail FOREIGN KEY(id_item) REFERENCES bg_item (id_item);
ALTER TABLE bg_order_detail ADD CONSTRAINT FK1_bg_order_detail FOREIGN KEY(id_ordine) REFERENCES bg_order(id_ordine);



insert into bg_anag_proprieta(nome_proprieta, valore_proprieta,flag_multiplo)values('Colore','Rosso','S');
insert into bg_anag_proprieta(nome_proprieta, valore_proprieta,flag_multiplo)values('Colore','Bianco','S');
insert into bg_anag_proprieta(nome_proprieta, valore_proprieta,flag_multiplo)values('Colore','Nero','S');
insert into bg_anag_proprieta(nome_proprieta, valore_proprieta,flag_multiplo)values('Colore','Blue','S');
insert into bg_anag_proprieta(nome_proprieta, valore_proprieta,flag_multiplo)values('Colore','Verde','S');
insert into bg_anag_proprieta(nome_proprieta, valore_proprieta,flag_multiplo)values('Colore','Giallo','S');
 
insert into bg_anag_proprieta(nome_proprieta, valore_proprieta,flag_multiplo)values('Taglia','36','S');
insert into bg_anag_proprieta(nome_proprieta, valore_proprieta,flag_multiplo)values('Taglia','38','S');
insert into bg_anag_proprieta(nome_proprieta, valore_proprieta,flag_multiplo)values('Taglia','40','S');
insert into bg_anag_proprieta(nome_proprieta, valore_proprieta,flag_multiplo)values('Taglia','42','S');
insert into bg_anag_proprieta(nome_proprieta, valore_proprieta,flag_multiplo)values('Taglia','44','S');

insert into blog.bg_property(nome_proprieta, valore_proprieta,flag_multiplo)values('Prezzo','','N');


INSERT INTO bg_Tipo_Item (descrizione) VALUES ('Item shop type' )


drop table bg_cart_detail;
drop table bg_cart;
CREATE  TABLE bg_cart (
  id_cart INT NOT NULL auto_increment,
  utente varchar(1000) NOT NULL ,
  data_inserimento DATETIME not NULL ,
  data_modifica DATETIME NULL, 
  PRIMARY KEY (id_cart) );

  
CREATE  TABLE bg_cart_detail (
  id_cart_detail INT NOT NULL auto_increment,
  id_cart int not null,
  id_item int not null,
  quantita int not null,
  costo float,
  data_inserimento DATETIME not NULL ,
  data_modifica DATETIME NULL, 
  PRIMARY KEY (id_cart_detail) );

ALTER TABLE bg_cart_detail ADD  CONSTRAINT FK_bg_cart_detail FOREIGN KEY(id_item)
REFERENCES bg_item (id_item);

ALTER TABLE bg_cart_detail ADD  CONSTRAINT FK1_bg_cart_detail FOREIGN KEY(id_cart)
REFERENCES bg_cart (id_cart);

create view bg_cart_detail_vw as
select id_cart_detail, cd.id_item, cd.quantita, lip.value price, i.titolo descrizione, c.utente, c.id_cart
from  bg_cart_detail cd
, bg_Item i
, bg_cart c
, bg_lk_item_property lip
, bg_property p
where cd.id_item = i.id_item
and c.id_cart=cd.id_cart
and lip.id_item=i.id_item
and lip.id_prop=p.id_prop
and p.nome_proprieta='Prezzo';
