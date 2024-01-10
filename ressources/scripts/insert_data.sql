INSERT INTO categories
		(libelle)
	VALUES
		('Boisson'),
		('Boisson alcoolisée'),
		('Entrée'),
		('Plat'),
		('Déssert');

INSERT INTO produits
		(nom					, prix	, description										, id_categorie)
	VALUES
		('Hoegardeen'			, 3.5	, 'Une bière blanche légère'						, 2),
		('Grimmbergen'			, 4.0	, 'Une bière blonde de base'						, 2),
		('IPA'					, 4.5	, 'Une bière IPA pas bonne'							, 2),
		('Soda 25cl'			, 3.0	, 'Fanta, Breizh cola, Oasis...'					, 1),
		('Sirop'				, 2.0	, 'Grenadine, Menthe, Mûre'							, 1),
		('Salade césar'			, 6.5	, 'Une salade poignardée dans le dos'				, 3), 
		('Foie gras'			, 7.0	, 'Toasts de foie gras avec confits d''oignons'		, 3),
		('Fruits de mer'		, 7.0	, 'De délicieuses crevettes croquantes au paprika'	, 3),
		('Assiette du boucher'	, 18	, 'De la barbaque avec des frites maisons !'		, 4),
		('Pizza de la mer'		, 17.5	, 'Base crème, saumon, poireaux, citron'			, 4),
		('Spaghetti bolognaise'	, 16	, 'Sauce tomate maison'								, 4),
		('Café ou thé gourmand'	, 8		, NULL												, 5),
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
