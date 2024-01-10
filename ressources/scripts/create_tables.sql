DROP TABLE IF EXISTS commandes, reservations, utilisateurs, tables, horaires, restaurants, compositions_cartes, cartes, produits, categories;

CREATE TABLE restaurants (
	id				INT				PRIMARY KEY		IDENTITY,
	nom				VARCHAR(30)		NOT NULL,
	adresse			VARCHAR(80)		NOT NULL,
	url_img			VARCHAR(256)	NOT NULL,
	id_carte		INT				NOT NULL		DEFAULT 1
);

CREATE TABLE cartes (
	id				INT				PRIMARY KEY		IDENTITY,
	titre			VARCHAR(40)
);

CREATE TABLE horaires (
	id				INT				PRIMARY KEY		IDENTITY,
	jour			VARCHAR(10)		NOT NULL,
	ouverture		TIME			NOT NULL,
	fermeture		TIME			NOT NULL,
	id_restaurant	INT				NOT NULL,
	CHECK (fermeture > ouverture)
);

CREATE TABLE produits (
	id				INT				PRIMARY KEY		IDENTITY,
	nom				VARCHAR(40)		NOT NULL,
	prix			NUMERIC(5,2)	NOT NULL,
	description		VARCHAR(180)	,
	id_categorie	INT
);

CREATE TABLE categories (
	id				INT				PRIMARY KEY		IDENTITY,
	libelle			VARCHAR			NOT NULL		UNIQUE
);

CREATE TABLE compositions_cartes (
	id				INT				PRIMARY KEY		IDENTITY,
	id_carte		INT				NOT NULL,
	id_produit		INT				NOT NULL
);

CREATE TABLE utilisateurs (
	id				INT				PRIMARY KEY		IDENTITY,
	role			VARCHAR(16)		NOT NULL,
	mail			VARCHAR(64)		NOT NULL,
	mdp				VARCHAR(64)		NOT NULL,
	id_restau		INT,
	CHECK (role IN ('PUBLIC', 'EQUIPE', 'ADMIN')),
	CHECK (		(id_restau IS NULL AND role = 'PUBLIC')
			OR	(id_restau IS NOT NULL AND role <> 'PUBLIC'))
);

CREATE TABLE reservations (
	id				INT				PRIMARY KEY		IDENTITY,
	id_utilisateur	INT				NOT NULL,
	id_table		INT,
	id_restau		INT,
	heure			DATETIME,
	etat			VARCHAR(20)		NOT NULL
									CHECK (etat IN ('PASSEE', 'VALIDEE', 'PRESENT', 'CLOTUREE'))
);

CREATE TABLE tables (
	id				INT				PRIMARY KEY		IDENTITY,
	id_restau		INT				NOT NULL,
	nb_places		INT				NOT NULL,
	etat			VARCHAR(20)		NOT NULL
									CHECK (etat IN ('LIBRE', 'OCCUPEE'))
);

CREATE TABLE commandes (
	id				INT				PRIMARY KEY		IDENTITY,
	id_table		INT				NOT NULL,
	id_produit		INT				NOT NULL,
	heure			DATETIME		NOT NULL		DEFAULT GETDATE(),
	etat			VARCHAR(20)		NOT NULL,
									CHECK (etat IN ('PASSEE', 'PRETE', 'SERVIE', 'REGLEE'))
);

ALTER TABLE restaurants
	WITH CHECK ADD
		FOREIGN KEY (id_carte) REFERENCES cartes(id)
		ON DELETE SET DEFAULT;

ALTER TABLE horaires
	WITH CHECK ADD
		FOREIGN KEY (id_restaurant) REFERENCES restaurants(id)
		ON DELETE CASCADE;

ALTER TABLE produits
	WITH CHECK ADD
		FOREIGN KEY (id_categorie) REFERENCES categories(id);

ALTER TABLE compositions_cartes
	WITH CHECK ADD
		FOREIGN KEY (id_carte) REFERENCES cartes(id),
		FOREIGN KEY (id_produit) REFERENCES produits(id);

ALTER TABLE utilisateurs
	WITH CHECK ADD
		FOREIGN KEY (id_restau) REFERENCES restaurants(id);

ALTER TABLE reservations
	WITH CHECK ADD
		FOREIGN KEY (id_utilisateur) REFERENCES utilisateurs(id),
		FOREIGN KEY (id_table) REFERENCES tables(id),
		FOREIGN KEY (id_restau) REFERENCES restaurants(id);

ALTER TABLE tables
	WITH CHECK ADD
		FOREIGN KEY (id_restau) REFERENCES restaurants(id);

ALTER TABLE commandes
	WITH CHECK ADD
		FOREIGN KEY (id_table) REFERENCES tables(id),
		FOREIGN KEY (id_produit) REFERENCES produits(id);
