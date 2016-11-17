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

INSERT INTO bg_Tipo_Item (descrizione) VALUES ('Item shop type' )
