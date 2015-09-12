drop table dbo.bg_lk_item_tag;
drop table dbo.bg_tag;
drop table dbo.bg_Allegato;
drop table dbo.bg_Item;
drop table dbo.bg_Tipo_Item;

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
  autore VARCHAR(100) NOT NULL ,
  data_pubblicazione DATETIME NOT NULL ,
  data_modifica DATETIME NULL ,
  id_Tipo_Item INT NOT NULL ,
  PRIMARY KEY (id_item) );

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
 
  
  
