INSERT INTO categories
		(libelle)
	VALUES
		('Boisson'),
		('Boisson alcoolis�e'),
		('Entr�e'),
		('Plat'),
		('D�ssert');

INSERT INTO produits
		(nom					, prix	, description										, id_categorie)
	VALUES
		('Hoegardeen'			, 3.5	, 'Une bi�re blanche l�g�re'						, 2),
		('Grimmbergen'			, 4.0	, 'Une bi�re blonde de base'						, 2),
		('IPA'					, 4.5	, 'Une bi�re IPA pas bonne'							, 2),
		('Soda 25cl'			, 3.0	, 'Fanta, Breizh cola, Oasis...'					, 1),
		('Sirop'				, 2.0	, 'Grenadine, Menthe, M�re'							, 1),
		('Salade c�sar'			, 6.5	, 'Une salade poignard�e dans le dos'				, 3), 
		('Foie gras'			, 7.0	, 'Toasts de foie gras avec confits d''oignons'		, 3),
		('Fruits de mer'		, 7.0	, 'De d�licieuses crevettes croquantes au paprika'	, 3),
		('Assiette du boucher'	, 18	, 'De la barbaque avec des frites maisons !'		, 4),
		('Pizza de la mer'		, 17.5	, 'Base cr�me, saumon, poireaux, citron'			, 4),
		('Spaghetti bolognaise'	, 16	, 'Sauce tomate maison'								, 4),
		('Caf� ou th� gourmand'	, 8		, NULL												, 5),
		('Boule de glace'		, 6		, 'Vanille, chocolat, menthe, mangue, fraise'		, 5),
		('Fondant au chocolat'	, 7.5	, NULL												, 5);

cartes
compositions_cartes
restaurants
horaires
tables
utilisateurs
reservations
commandes
