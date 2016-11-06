drop table dbo.bg_anag_proprieta;



CREATE  TABLE bg_anag_proprieta (
  id_anag_prop INT NOT NULL auto_increment,
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
