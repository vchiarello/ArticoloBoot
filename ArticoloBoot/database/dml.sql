INSERT INTO "dbo"."bg_Item" (testo,titolo,nome,riassunto,autore,data_pubblicazione,data_modifica,id_Tipo_Item) VALUES 
('Testo 1' ,'Titolo 1','Nome 1' ,'Riassunto 1','Vito' ,getDate(),getDate(),1 /*not nullable*/)
GO


INSERT INTO "dbo"."bg_Item" (testo,titolo,nome,riassunto,autore,data_pubblicazione,data_modifica,id_Tipo_Item) VALUES 
('Testo' ,'Titolo','Nome' ,'Riassunto','Vito' ,getDate(),getDate(),1)
GO


INSERT INTO "dbo"."bg_Tipo_Item" (descrizione) VALUES ('Tipo 1' )
GO

INSERT INTO "dbo"."bg_tag" (nome_tag) VALUES ('Appunti' )
GO

INSERT INTO "dbo"."bg_tag" (nome_tag) VALUES ('Tag 1' )
GO

INSERT INTO "dbo"."bg_tag" (nome_tag) VALUES ('Tag 2' )
GO
