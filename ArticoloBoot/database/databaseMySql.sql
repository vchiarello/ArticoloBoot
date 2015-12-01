



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
 