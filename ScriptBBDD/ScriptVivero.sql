
GO
create database MunozArenas
GO
GO
USE MunozArenas
CREATE TABLE Productos(
                          Descripcion varchar(40),
                          Codigo int IDENTITY(1,1) PRIMARY KEY NOT NULL,
                          Precio_Unitario money NOT NULL,
                          Unidades_Disponibles int NULL,
)
CREATE TABLE Tipo_Plantas(
                             Id int IDENTITY(1,1) PRIMARY KEY NOT NULL,
                             Tipo varchar(15) NOT NULL
)

CREATE TABLE ProductosPlanta(
                                Codigo int NOT NULL PRIMARY KEY,
                                CONSTRAINT FK_Producto_Planta FOREIGN KEY(Codigo) REFERENCES Productos(Codigo) ON UPDATE CASCADE ON DELETE NO ACTION
)

CREATE TABLE Tipo_Plantas_Plantas(
                                     Id_Tipo_Planta int NOT NULL,
                                     Codigo_Planta int NOT NULL,
                                     CONSTRAINT PK_Tipo_Planta PRIMARY KEY(Id_Tipo_Planta,Codigo_Planta),
                                     CONSTRAINT FK_Tipo_Planta FOREIGN KEY(Id_Tipo_Planta)REFERENCES Tipo_Plantas(Id)  ON UPDATE CASCADE ON DELETE NO ACTION,
                                     CONSTRAINT FK_Codigo_Planta FOREIGN KEY(Codigo_Planta) REFERENCES ProductosPlanta(Codigo)  ON UPDATE CASCADE ON DELETE NO ACTION

)

CREATE TABLE ProductosJardineria(
                                    Codigo int NOT NULL PRIMARY KEY,
                                    CONSTRAINT FK_Producto_Jardineria FOREIGN KEY(Codigo) REFERENCES Productos(Codigo) ON UPDATE CASCADE ON DELETE NO ACTION
)

CREATE TABLE Clientes(
                         Nombre varchar(30) NOT NULL,
                         Dni varchar(9) PRIMARY KEY NOT NULL,
                         Direccion varchar(50) NULL,
                         Codigo_Postal varchar(5) NULL,
                         Ciudad varchar(30) NULL,
                         Telefono varchar(9) NULL,
                         Correo_Electronico varchar(20) NULL

)

CREATE TABLE Usuarios(
                         Id int IDENTITY(1,1) NOT NULL,
                         EsGestor bit NOT NULL,
                         Usuario varchar(25) NOT NULL,
                         Contrasenhia varchar(25) NOT NULL,
                         Nombre varchar(30) NOT NULL,
                         Dni varchar(9) NOT NULL,
                         Direccion varchar(50) NULL,
                         Codigo_Postal varchar(5) NULL,
                         Ciudad varchar(30) NULL,
                         Telefono varchar(9) NULL,
                         Correo_Electronico varchar(20) NULL,
                         CONSTRAINT PK_Usuarios PRIMARY KEY(Id)

)


CREATE TABLE Facturas(
                         Id int IDENTITY(1,1) NOT NULL,
                         Dni_Cliente varchar(9) NOT NULL,
                         Vendedor int NOT NULL,
                         Fecha Date NOT NULL,
                         Importe money NULL,
                         Constraint PK_Factura PRIMARY KEY(Id),
                         CONSTRAINT FK_Cliente_Factura FOREIGN KEY(Dni_Cliente) REFERENCES Clientes(Dni)  ON UPDATE CASCADE ON DELETE NO ACTION,
                         CONSTRAINT FK_Vendedor_Factura FOREIGN KEY(Vendedor) REFERENCES Usuarios(Id)  ON UPDATE CASCADE ON DELETE NO ACTION
)


CREATE TABLE Productos_Facturas(
                                   Id_Factura int NOT NULL,
                                   Cantidad int NOT NULL,
                                   Codigo_Producto int NOT NULL,
                                   CONSTRAINT PK_Producto_Factura PRIMARY KEY(Id_Factura,Codigo_Producto),
                                   CONSTRAINT FK_Codigo_Producto FOREIGN KEY(Codigo_Producto) REFERENCES Productos(Codigo)  ON UPDATE CASCADE ON DELETE NO ACTION,
                                   CONSTRAINT FK_Id_Factura FOREIGN KEY(Id_Factura) REFERENCES Facturas(Id)  ON UPDATE CASCADE ON DELETE NO ACTION
)


GO

GO
CREATE OR ALTER PROCEDURE BorrarFactura
    @idFacturaABorrar int
    AS
BEGIN
DELETE FROM Productos_Facturas Where Id_Factura=@idFacturaABorrar
DELETE FROM Facturas Where Id=@idFacturaABorrar
END
GO


GO
CREATE OR ALTER TRIGGER BorradoProductoFactura ON Productos_Facturas
	AFTER DELETE AS
BEGIN
UPDATE Productos SET
    Unidades_Disponibles+=(SELECT Cantidad FROM deleted Where Codigo_Producto=Codigo) WHERE Codigo=(SELECT Codigo_Producto FROM deleted Where Codigo_Producto=Codigo)

END
GO


GO
CREATE OR ALTER TRIGGER ImporteFactura ON Productos_Facturas
	AFTER INSERT AS
BEGIN
UPDATE Facturas SET
    Importe+=(SELECT I.Cantidad*P.Precio_Unitario FROM inserted AS I INNER JOIN Productos AS P ON I.Codigo_Producto=P.Codigo)
WHERE Id=(SELECT Id_Factura FROM inserted)
UPDATE Productos SET Unidades_Disponibles-=(SELECT Cantidad  FROM inserted) WHERE Codigo=(SELECT Codigo_Producto FROM inserted)
END
GO

GO
CREATE OR ALTER FUNCTION TotalVentasPorMesEnTodosLosProductos(@Mes SMALLINT,@Year SMALLINT)
    RETURNS TABLE
    AS
RETURN
(SELECT  SUM(PF.Cantidad) AS CantidadTotal,SUM(PF.CANTIDAD*P.Precio_Unitario) AS ImporteTotal
FROM Productos_Facturas as PF INNER JOIN Productos AS P ON PF.Codigo_Producto=P.Codigo INNER
JOIN Facturas AS F ON PF.Id_Factura=F.Id WHERE YEAR(F.Fecha)=@Year AND MONTH(F.Fecha)=@Mes )
GO


GO
CREATE OR ALTER FUNCTION TotalVentasPorMesEnLosProductosPlanta(@Mes SMALLINT,@Year SMALLINT)
    RETURNS TABLE
    AS
RETURN
(SELECT  SUM(PF.Cantidad) AS CantidadTotalPlantas,SUM(PF.CANTIDAD*P.Precio_Unitario) AS ImporteTotalPlantas
FROM Productos_Facturas as PF INNER JOIN Productos AS P ON PF.Codigo_Producto=P.Codigo INNER
JOIN ProductosPlanta AS PP ON P.Codigo=PP.Codigo INNER JOIN Facturas AS F ON PF.Id_Factura=F.Id WHERE YEAR(F.Fecha)=@Year AND MONTH(F.Fecha)=@Mes )
GO


GO
CREATE OR ALTER FUNCTION TotalVentasPorMesEnLosProductosJardineria(@Mes SMALLINT,@Year SMALLINT)
    RETURNS TABLE
    AS
RETURN
(SELECT  SUM(PF.Cantidad) AS CantidadTotalJardineria,SUM(PF.CANTIDAD*P.Precio_Unitario) AS ImporteTotalJardineria
FROM Productos_Facturas as PF  INNER JOIN Productos AS P ON PF.Codigo_Producto=P.Codigo
INNER JOIN ProductosJardineria AS PJ ON P.Codigo=PJ.Codigo INNER JOIN Facturas AS F ON PF.Id_Factura=F.Id WHERE YEAR(F.Fecha)=@Year AND MONTH(F.Fecha)=@Mes)
GO

GO
CREATE OR ALTER FUNCTION TotalVentasPorAnhioEnTodosLosProductos(@Year SMALLINT)
    RETURNS TABLE
    AS
RETURN
(SELECT  SUM(PF.Cantidad) AS CantidadTotal,SUM(PF.CANTIDAD*P.Precio_Unitario) AS ImporteTotal
FROM Productos_Facturas as PF INNER JOIN Productos AS P ON PF.Codigo_Producto=P.Codigo INNER
JOIN Facturas AS F ON PF.Id_Factura=F.Id WHERE YEAR(F.Fecha)=@Year)
GO


GO
CREATE OR ALTER FUNCTION TotalVentasPorAnhioEnLosProductosPlanta(@Year SMALLINT)
    RETURNS TABLE
    AS
RETURN
(SELECT  SUM(PF.Cantidad) AS CantidadTotalPlantas,SUM(PF.CANTIDAD*P.Precio_Unitario) AS ImporteTotalPlantas
FROM Productos_Facturas as PF INNER JOIN Productos AS P ON PF.Codigo_Producto=P.Codigo INNER
JOIN ProductosPlanta AS PP ON P.Codigo=PP.Codigo INNER JOIN Facturas AS F ON PF.Id_Factura=F.Id WHERE YEAR(F.Fecha)=@Year)
GO


    GO
CREATE OR ALTER PROCEDURE IntroducirDatosPorDefecto
    AS
BEGIN
INSERT INTO Productos(Descripcion,Precio_Unitario,Unidades_Disponibles) values('Substrato',5,100),
                                                                              ('Regadera',1,125),('Carretilla',25,55),('Pala',10,85),('Rastrillo',15,105),('Maceta',8,200),
                                                                              ('Cactus',3,500),('Margarita',2,625),('Rosa',25,55),('Amapola',6,200),('Girasol',4,350),('Manzano',3,400)
    INSERT INTO ProductosPlanta SELECT TOP 6 Codigo FROM Productos GROUP BY Codigo ORDER BY Codigo DESC
    INSERT INTO ProductosJardineria SELECT TOP 6 Codigo FROM Productos GROUP BY Codigo ORDER BY Codigo
    INSERT INTO Clientes VALUES ('Cliente Genérico','77863714C','C/Cabeza de Vaca Nº11','41330','Sevilla',
                                        '663424318','juanjomz@hotmail.es'),('Antonio','56976220E','C/Profesor Buenaventura Pinillos Nº6','41330','Sevilla',
                                        '749279341','antonio@hotmail.es'),('Jesús','44681347Y','C/Azafrán Nº69','41330','Sevilla',
                                        '755099046','jesusito@hotmail.es'),('Federico','18542660Z','C/Amargura Nº13','41330','Sevilla',
                                        '605936241','fedez@hotmail.es'),('Luisa','17411341L','C/Petunia Nº71','41330','Sevilla',
                                        '714031768','luisa@hotmail.es'),('Jaime','73506792B','C/Los tres reyes magos Nº6','41330','Sevilla',
                                        '626383466','jaimesote@hotmail.es'),('Laura','72611197J','C/Ay mi madre el bicho Nº29','41330','Sevilla',
                                        '678815659','laurilla@hotmail.es')
                                    INSERT INTO Usuarios VALUES (0,'vendedor','abc','Vendedor','35522368H','C/Lepanto Nº92','41330',
                                        'Sevilla','31528830Q','vendedor@hotmail.es'),
                                        (1,'root','toor','Root','X4018866F','C/Ordenador Nº92','41330',
                                        'Sevilla','660365478','root@hotmail.es'),(0,'luis20','luiso20','Luis','73277824P','C/Ordenador Nº92','41330',
                                        'Sevilla','684391289','luisepe@hotmail.es'),(0,'Juan30','juanete30','Juan','46599416J','C/Rumbba Nº32','41330',
                                        'Sevilla','628150752','juanaso@hotmail.es'),(0,'Felipe00','felix360','Felipe','67902504H','C/Pared Nº52','41330',
                                        'Sevilla','787181560','erFeli@hotmail.es'),(0,'Susana2','susix34','Susana','59616212M','C/Almirante Nº3','41330',
                                        'Sevilla','732871101','susilla@hotmail.es'),(0,'Arnald0','BOOM30','Arnaldo','81060873H','C/Capitan Nº7','41330',
                                        'Sevilla','784434258','otegi@hotmail.es'),(1,'Jeremiasx34','yeremi34','Jeremias','36477033E','C/Nieve Nº39','41330',
                                        'Sevilla','786957762','yermix@hotmail.es'),(1,'Juli0','juli0','Julio','78823491F','C/Agua Nº10','41330',
                                        'Sevilla','760087574','yulio0@hotmail.es'),(1,'Germanex','futbol2','Germán','78823491F','C/Azucena Nº20','41330',
                                        'Sevilla','609933642','germanFut@hotmail.es'),(1,'Rosita23','Freskito2','Rosa','38117227V','C/Lucero Nº99','41330',
                                        'Sevilla','777931702','esaRosa2@hotmail.es'),(1,'AntoniaKLK','klkbb33','Antonia','20243337W','C/Pineda Nº108','41330',
                                        'Sevilla','609165847','klkantO@hotmail.es')
                                    INSERT INTO Facturas VALUES('77863714C',1,CONVERT(Date,GETDATE()),0),
                                        ('77863714C',1,CONVERT(Date,GETDATE()),0),
                                        ('56976220E',2,'4/11/2010',0),
                                        ('56976220E',2,'14/4/2010',0),
                                        ('44681347Y',3,'11/3/2015',0),
                                        ('44681347Y',3,CONVERT(Date,GETDATE()),0),
                                        ('18542660Z',4,CONVERT(Date,GETDATE()),0),
                                        ('18542660Z',4,'21/3/2015',0),
                                        ('17411341L',5,CONVERT(Date,GETDATE()),0),
                                        ('17411341L',5,'26/2/2015',0),
                                        ('73506792B',6,CONVERT(Date,GETDATE()),0),
                                        ('73506792B',6,'13/11/2010',0),
                                        ('72611197J',1,'19/10/2015',0),
                                        ('72611197J',1,'10/11/2010',0)

                                    insert into Productos_Facturas VALUES(1,3,1)
                                    insert into Productos_Facturas VALUES(1,3,4)
                                    insert into Productos_Facturas VALUES (2,13,6)
                                    insert into Productos_Facturas VALUES(2,6,4)
                                    insert into Productos_Facturas VALUES(3,3,10)
                                    insert into Productos_Facturas VALUES(3,2,8)
                                    insert into Productos_Facturas VALUES(4,6,7)
                                    insert into Productos_Facturas VALUES(4,9,3)
                                    insert into Productos_Facturas VALUES(5,13,4)
                                    insert into Productos_Facturas  VALUES(5,15,6)
                                    insert into Productos_Facturas  VALUES(6,3,2)
                                    insert into Productos_Facturas  VALUES(6,5,11)
                                    insert into Productos_Facturas  VALUES(7,13,6)
                                    insert into Productos_Facturas  VALUES(7,6,4)
                                    insert into Productos_Facturas  VALUES(8,10,10)
                                    insert into Productos_Facturas  VALUES(8,2,4)
                                    insert into Productos_Facturas  VALUES(9,6,7)
                                    insert into Productos_Facturas  VALUES(9,9,3)
                                    insert into Productos_Facturas  VALUES(10,10,3)
                                    insert into Productos_Facturas  VALUES(10,10,1)
                                    insert into Productos_Facturas  VALUES(11,5,1)
                                    insert into Productos_Facturas  VALUES(11,2,11)
                                    insert into Productos_Facturas  VALUES(12,2,4)
                                    insert into Productos_Facturas  VALUES(12,20,12)
                                    insert into Productos_Facturas  VALUES(14,12,7)
                                    insert into Productos_Facturas  VALUES(13,10,12)
                                    insert into Productos_Facturas  VALUES(13,2,10)
                                    insert into Productos_Facturas  VALUES(14,10,9)
UPDATE Facturas SET Importe=Importe*0.95 WHERE Dni_Cliente!='77863714C'
END
GO
