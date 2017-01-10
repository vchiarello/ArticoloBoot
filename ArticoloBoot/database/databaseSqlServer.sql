drop table dbo.bg_lk_item_tag;
drop table bg_lk_item_property
drop table dbo.bg_tag;
drop table dbo.bg_Allegato;
drop table dbo.bg_Item;
drop table dbo.bg_Tipo_Item;
drop table dbo.bg_anag_proprieta;
drop view  bg_allegato_vw;
drop table bg_cart_detail
drop table bg_cart;
drop table bg_order_detail;
drop table bg_order;

CREATE  TABLE bg_property (
  id_prop INT NOT NULL identity,
  nome_proprieta varchar(1000) NOT NULL ,
  valore_proprieta varchar(1000) not null,
  flag_multiplo varchar(1),
  PRIMARY KEY (id_prop) );
  
CREATE  TABLE bg_Tipo_Item (
  id_Tipo INT NOT NULL identity,
  descrizione varchar(2000) NOT NULL ,
  PRIMARY KEY (id_Tipo) );
  
CREATE  TABLE bg_Item (
  id_item INT NOT NULL identity,
  testo nvarchar(max) NOT NULL ,
  titolo VARCHAR(2000) NOT NULL ,
  nome VARCHAR(100) NOT NULL ,
  riassunto VARCHAR(4000) NULL ,
  quantita int NULL ,
  autore VARCHAR(100) NOT NULL ,
  data_pubblicazione DATETIME NOT NULL ,
  data_modifica DATETIME NULL ,
  id_Tipo_Item INT NOT NULL ,
  PRIMARY KEY (id_item) );

alter table dbo.bg_item add 
data_inserimento datetime
 ,data_hidden datetime
 ,data_scadenza datetime;
  
  
ALTER TABLE [dbo].[bg_Item]  WITH CHECK ADD  CONSTRAINT [FK_Item_TipoItem] FOREIGN KEY([id_Tipo_Item])
REFERENCES [dbo].[bg_Tipo_Item] ([id_Tipo])
GO

ALTER TABLE [dbo].[bg_Item] CHECK CONSTRAINT [FK_Item_TipoItem]
GO
  
  
CREATE  TABLE bg_Allegato (
  id_Allegato INT NOT NULL identity,
  id_item INT NOT NULL,
  nome_Allegato VARCHAR(45) NOT NULL ,
  content_type VARCHAR(100) NULL,
  contenuto varbinary(max) NULL,
  contenuto_Testo nvarchar(max) NULL,
  data_pubblicazione DATETIME NOT NULL ,
  data_modifica DATETIME NULL ,
  PRIMARY KEY (id_Allegato) );
	
	
alter table bg_allegato add foreign key (id_item) references bg_item(id_item);  

CREATE  TABLE bg_tag (
  id_tag INT NOT NULL identity ,
  nome_tag VARCHAR(100) NOT NULL ,
  PRIMARY KEY (id_tag),
  unique(nome_tag));
  
CREATE  TABLE bg_lk_item_tag (
  id_lk_item_tag INT NOT NULL identity ,
  id_tag INT NOT NULL ,
  id_item INT NOT NULL ,
  PRIMARY KEY (id_lk_item_tag));
  

  
ALTER TABLE [dbo].[bg_lk_item_tag]  WITH CHECK ADD  CONSTRAINT [FK_lk_item_tag_tag] FOREIGN KEY([id_tag])
REFERENCES [dbo].[bg_tag] ([id_tag])
GO
ALTER TABLE [dbo].[bg_lk_item_tag]  WITH CHECK ADD  CONSTRAINT [FK_lk_item_tag_item] FOREIGN KEY([id_item])
REFERENCES [dbo].[bg_item] ([id_item])
GO

CREATE  TABLE bg_lk_item_property (
  id_lk_item_prop INT NOT NULL identity ,
  id_prop INT NOT NULL ,
  id_item INT NOT NULL ,
  value varchar(100) ,
  data_modifica DATETIME NULL ,
  data_inserimento DATETIME NULL ,
  PRIMARY KEY (id_lk_item_prop));

ALTER TABLE [dbo].[bg_lk_item_property]  WITH CHECK ADD  CONSTRAINT [FK_lk_item_property_item] FOREIGN KEY([id_prop])
REFERENCES [dbo].[bg_property] ([id_prop])
GO
ALTER TABLE [dbo].[bg_lk_item_property]  WITH CHECK ADD  CONSTRAINT [FK_lk_item_property_property] FOREIGN KEY([id_item])
REFERENCES [dbo].[bg_item] ([id_item])
GO

Create view bg_allegato_vw as
SELECT id_Allegato,a.id_item,i.nome, nome_Allegato,content_type,contenuto,contenuto_Testo,a.data_pubblicazione,a.data_modifica 
FROM 
	dbo.bg_Allegato a
	, dbo.bg_item i
where 
a.id_item=i.id_item


CREATE  TABLE bg_cart (
  id_cart INT NOT NULL identity,
  utente varchar(1000) NOT NULL ,
  data_inserimento DATETIME not NULL ,
  data_modifica DATETIME NULL, 
  PRIMARY KEY (id_cart) );

CREATE  TABLE bg_cart_detail (
  id_cart_detail INT NOT NULL identity,
  id_cart int not null,
  id_item int not null,
  quantita int not null,
  costo float,
  data_inserimento DATETIME not NULL ,
  data_modifica DATETIME NULL, 
  PRIMARY KEY (id_cart_detail) );

ALTER TABLE [dbo].bg_cart_detail  WITH CHECK ADD  CONSTRAINT [FK_bg_cart_detail] FOREIGN KEY([id_item])
REFERENCES [dbo].[bg_item] ([id_item]);

ALTER TABLE [dbo].bg_cart_detail  WITH CHECK ADD  CONSTRAINT [FK1_bg_cart_detail] FOREIGN KEY(id_cart)
REFERENCES [dbo].[bg_cart] ([id_cart]);


CREATE  TABLE bg_order (
  id_order INT NOT NULL identity,
  utente varchar(1000) NOT NULL ,
  data_inserimento DATETIME not NULL ,
  data_modifica DATETIME NULL, 
  PRIMARY KEY (id_order) );

CREATE  TABLE bg_order_detail (
  id_order_detail INT NOT NULL identity,
  id_order INT NOT NULL,
  id_item int not null,
  quantita int not null,
  costo float,
  data_inserimento DATETIME not NULL ,
  data_modifica DATETIME NULL, 
  PRIMARY KEY (id_order) );

ALTER TABLE [dbo].[bg_order_detail]  WITH CHECK ADD  CONSTRAINT [FK_bg_order_detail] FOREIGN KEY([id_item])
REFERENCES [dbo].[bg_item] ([id_item]);

ALTER TABLE [dbo].[bg_order_detail]  WITH CHECK ADD  CONSTRAINT [FK1_bg_order_detail] FOREIGN KEY([id_order])
REFERENCES [dbo].[bg_order] ([id_order]);

create view bg_cart_detail_vw as
select id_cart_detail, cd.id_item, cd.quantita, lip.value price, i.titolo descrizione, c.utente, c.id_cart
from  dbo.bg_cart_detail cd
, dbo.bg_Item i
, dbo.bg_cart c
, dbo.bg_lk_item_property lip
, dbo.bg_property p
where cd.id_item = i.id_item
and c.id_cart=cd.id_cart
and lip.id_item=i.id_item
and lip.id_prop=p.id_prop
and p.nome_proprieta='Prezzo';

create view bg_order_detail_vw as
select id_order_detail, cd.id_item, cd.quantita, cd.costo, i.titolo descrizione, o.utente, o.id_order
from  dbo.bg_order_detail cd
, dbo.bg_Item i
, dbo.bg_order o
where cd.id_item = i.id_item
and cd.id_order=o.id_order;

insert into test.dbo.bg_property(nome_proprieta, valore_proprieta,flag_multiplo)values('Colore','Rosso','S');
insert into test.dbo.bg_property(nome_proprieta, valore_proprieta,flag_multiplo)values('Colore','Bianco','S');
insert into test.dbo.bg_property(nome_proprieta, valore_proprieta,flag_multiplo)values('Colore','Nero','S');
insert into test.dbo.bg_property(nome_proprieta, valore_proprieta,flag_multiplo)values('Colore','Blue','S');
insert into test.dbo.bg_property(nome_proprieta, valore_proprieta,flag_multiplo)values('Colore','Verde','S');
insert into test.dbo.bg_property(nome_proprieta, valore_proprieta,flag_multiplo)values('Colore','Giallo','S');
 
insert into test.dbo.bg_property(nome_proprieta, valore_proprieta,flag_multiplo)values('Taglia','36','S');
insert into test.dbo.bg_property(nome_proprieta, valore_proprieta,flag_multiplo)values('Taglia','38','S');
insert into test.dbo.bg_property(nome_proprieta, valore_proprieta,flag_multiplo)values('Taglia','40','S');
insert into test.dbo.bg_property(nome_proprieta, valore_proprieta,flag_multiplo)values('Taglia','42','S');
insert into test.dbo.bg_property(nome_proprieta, valore_proprieta,flag_multiplo)values('Taglia','44','S');


insert into test.dbo.bg_property(nome_proprieta, valore_proprieta,flag_multiplo)values('Prezzo','','N');