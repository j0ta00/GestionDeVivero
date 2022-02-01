create database Vivero
drop database Vivero
USE Vivero
CREATE TABLE Producto(
	Descripcion varchar(40),
	Codigo int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	Precio_Unitario money NOT NULL,
	Unidades_Disponibles int NULL,
)
CREATE TABLE Tipo_Planta(
Id int PRIMARY KEY NOT NULL,
Tipo varchar(15) NOT NULL
)

CREATE TABLE Planta(
	Codigo int NOT NULL PRIMARY KEY,
	CONSTRAINT FK_Producto_Planta FOREIGN KEY(Codigo) REFERENCES Producto(Codigo)
)

CREATE TABLE Tipo_Planta_Planta(
Id_Tipo_Planta int NOT NULL,
Codigo_Planta int NOT NULL,
CONSTRAINT PK_Tipo_Planta PRIMARY KEY(Id_Tipo_Planta,Codigo_Planta),
CONSTRAINT FK_Tipo_Planta FOREIGN KEY(Id_Tipo_Planta)REFERENCES Tipo_Planta(Id),
CONSTRAINT FK_Codigo_Planta FOREIGN KEY(Codigo_Planta) REFERENCES Planta(Codigo)

)

CREATE TABLE Jardineria(
	Codigo int NOT NULL,
	CONSTRAINT FK_Producto_Jardineria FOREIGN KEY(Codigo) REFERENCES Producto(Codigo)
)

CREATE TABLE Cliente(
Nombre varchar(30) NOT NULL,
Dni varchar(9) PRIMARY KEY NOT NULL,
Direccion varchar(50) NULL,
Codigo_Postal varchar(5) NULL,
Ciudad varchar(30) NULL,
Telefono varchar(9) NULL,
Correo_Electronico varchar(20) NULL

)

CREATE TABLE Usuario(
Id int IDENTITY(1,1) NOT NULL,
EsGestor bit NOT NULL,
Usuario varchar(25) NOT NULL,
Contrasenhia varchar(25) NOT NULL,
CONSTRAINT PK_Vendedores PRIMARY KEY(Id)

)


CREATE TABLE Factura(
	Id int IDENTITY(1,1) NOT NULL,
	Dni_Cliente varchar(9) NOT NULL,
	Vendedor int NOT NULL,
	Fecha Date NOT NULL,
	Importe money NULL,	
	Constraint PK_Factura PRIMARY KEY(Id),
	CONSTRAINT FK_Cliente_Factura FOREIGN KEY(Dni_Cliente) REFERENCES Cliente(Dni),
	CONSTRAINT FK_Vendedor_Factura FOREIGN KEY(Vendedor) REFERENCES Usuario(Id)
)


CREATE TABLE Producto_Factura(
	Id_Factura int NOT NULL,
	Cantidad int NOT NULL,
	Codigo_Producto int NOT NULL,
	CONSTRAINT PK_Producto_Factura PRIMARY KEY(Id_Factura,Codigo_Producto),
	CONSTRAINT FK_Codigo_Producto FOREIGN KEY(Codigo_Producto) REFERENCES Producto(Codigo),
	CONSTRAINT FK_Id_Factura FOREIGN KEY(Id_Factura) REFERENCES Factura(Id)
)


select*from Producto_Factura


INSERT INTO Usuario VALUES(0,'PAPA','A')
insert into Cliente(Nombre,Dni) values('jambo','77863714C')
INSERT INTO Producto(Descripcion,Precio_Unitario,Unidades_Disponibles) values ('s',10,20)
insert into Usuario(EsGestor,Usuario,Contrasenhia) values (0,'gumball','pelopicopata')
insert into factura(Dni_Cliente,Vendedor,Fecha) values('77863714C',1,'01/01/2000')
insert into Producto_Factura values (1,5,1) 
select* from Usuario
select*from Factura
select*from Producto_Factura
delete from Producto_Factura
delete from Factura




GO
	CREATE OR ALTER TRIGGER ImporteFactura ON Producto_Factura
	AFTER INSERT AS
	BEGIN
		UPDATE Factura SET Importe=(SELECT Precio_Unitario FROM Producto AS P INNER JOIN inserted AS I ON P.Codigo=I.Codigo_Producto)*(SELECT Cantidad FROM inserted)
		WHERE Id=(SELECT Id_Factura FROM inserted)
	END
GO