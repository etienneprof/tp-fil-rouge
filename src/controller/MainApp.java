package controller;

import java.util.List;
import java.util.Scanner;

import bll.BLLException;
import bll.CarteBLL;
import bll.RestaurantBLL;
import bo.Carte;
import bo.Restaurant;

public class MainApp {
	private static Scanner scan;
	private static CarteBLL carteBll;
	private static RestaurantBLL restauBll;
	
	private static final int LINE_SIZE = 80;
	private static final String SEPARATOR = "*".repeat(LINE_SIZE);
	
	public static void main(String[] args) {
		scan = new Scanner(System.in);
		try {
			carteBll = new CarteBLL();
			restauBll = new RestaurantBLL();
			afficherMenuPrincipal();
		} catch (BLLException e) {
			System.out.println("Echec lors de l'ouverture de l'application.");
			System.out.println("Veuillez contacter votre administrateur. Cause :");
			System.out.println(e.getMessage());
		}
	}
	
	private static void afficherMenuPrincipal() {
		int choix;
		do {
			choix = afficherMenu(
				"Menu principal",
				"Ajouter un restaurant",
				"Modifier un restaurant existant",
				"Supprimer un restaurant existant",
				"Créer une carte",
				"Modifier une carte",
				"Supprimer une carte",
				"Quitter"
			);
			
			switch(choix) {
			case 1: 
				ajouterRestaurant();
				break;
			case 2:
				modifierRestaurant();
				break;
			case 3:
				supprimerRestaurant();
				break;
			case 4:
				creerCarte();
				break;
			case 5:
				modifierCarte();
				break;
			case 6:
				supprimerCarte();
				break;
			case 7:
				System.out.println("Fermeture de l'application");
				break;
			}
		} while (choix != 7);
	}

	private static void ajouterRestaurant() {
		int choix = afficherMenu("Menu d'ajout de restaurant", "Saisie manuelle", "Saisie par fichier", "Annuler");
		switch (choix) {
		case 1:
			ajouterRestaurantManuel();
			break;
		case 2:
			ajouterRestaurantAuto();
			break;	
		}
	}

	private static void ajouterRestaurantManuel() {
		System.out.println("Ajout d'un restaurant. (Saisie manuelle)");
		
		System.out.print("Nom : ");
		String nom = scan.nextLine();
		
		System.out.print("Adresse : ");
		String adresse = scan.nextLine();
		
		try {
			Restaurant rest = restauBll.insert(nom, adresse);
			System.out.println("Restaurant créé : " + rest);
		} catch (BLLException e) {
			System.out.println("Impossible d'ajouter le restaurant. Cause : " + e.getMessage());
		}
	}

	private static void ajouterRestaurantAuto() {
		System.out.println("Ajout d'un restaurant. (Depuis un fichier)");
		
		System.out.print("Nom de fichier : ");
		String path = scan.nextLine();
		
		List<String[]> data = CSVReader.readCSV(path);
		int totalLines = 0;
		int successLines = 0;
		for (String[] current : data) {
			totalLines++;
			try {
				restauBll.insert(current[0], current[1]);
				successLines++;
			} catch (BLLException e) {
				System.out.println("Echec d'insertion de la ligne : " + current);
			} catch (Exception e) {
				System.out.println("Echec d'insertion de la ligne : " + current);
			}
		}
		System.out.println("Nombre de lignes traitées : " + totalLines);
		System.out.println("Succès : " + successLines);
		System.out.println("Echecs : " + (totalLines - successLines));
	}

	private static void modifierRestaurant() {
		System.out.println("Cette fonctionnalité n'est pas encore implémentée.");
	}

	private static void supprimerRestaurant() {
		System.out.println("Suppression d'un restaurant.");
		
		afficherRestaurants();
		
		System.out.print("Id du restaurant à supprimer : ");
		int id = scan.nextInt();
		scan.nextLine();
		
		try {
			restauBll.delete(id);
			System.out.println("Restaurant " + id + " supprimé.");
		} catch (BLLException e) {
			System.out.println("Impossible de supprimer le restaurant " + id);
		}
	}
	
	private static void afficherRestaurants() {
		try {
			List<Restaurant> restos = restauBll.selectAll();
			if (restos == null || restos.size() == 0) {
				System.out.println("[Aucun restaurant à consulter]");
			} else {
				for (Restaurant current : restauBll.selectAll()) {
					System.out.println(current.getId() + ". " + current.getNom() + ". " + current.getAdresse());
				}
			}
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}

	private static void creerCarte() {
		System.out.println("Ajout d'une carte.");
		
		System.out.print("Nom : ");
		String nom = scan.nextLine();
		
		try {
			Carte carte = carteBll.insert(nom);
			System.out.println("Carte créée : " + carte.getTitre() + "(" + carte.getId() + ").");
		} catch (BLLException e) {
			// TODO Auto-generated catch block
			System.out.println("Impossible d'ajouter la carte. Cause : " + e.getMessage());
		}
	}

	private static void modifierCarte() {
		System.out.println("Cette fonctionnalité n'est pas encore implémentée.");
	}

	private static void supprimerCarte() {
		System.out.println("Suppression d'une carte.");
		
		afficherCartes();
		
		System.out.print("Id de la carte à supprimer : ");
		int id = scan.nextInt();
		scan.nextLine();
		
		try {
			carteBll.delete(id);
			System.out.println("Carte " + id + " supprimée.");
		} catch (BLLException e) {
			System.out.println("Impossible de supprimer la carte " + id);
		}
	}

	private static void afficherCartes() {
		try {
			List<Carte> cartes = carteBll.selectAll();
			if (cartes == null || cartes.size() == 0) {
				System.out.println("[Aucune carte à consulter]");
			} else {
				for (Carte current : carteBll.selectAll()) {
					System.out.println(current.getId() + ". " + current.getTitre());
				}
			}
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}
	
	private static void afficherTitre(String titre) {
		System.out.println(SEPARATOR);
		String pad = " ".repeat((LINE_SIZE - titre.length()) / 2);
		System.out.println(pad + titre.toUpperCase());
		System.out.println(SEPARATOR);
	}
	
	private static int afficherMenu(String titre, String... options) {
		afficherTitre(titre);
		int choix;
		do {
			System.out.println("Veuillez sélectionner une action :");
			for (int i = 0; i < options.length; i++) {
				String opt = options[i];
				System.out.println("\t" + (i+1) + ". " + opt);
			}
			choix = scan.nextInt();
			scan.nextLine();
			if (choix < 1 || choix > options.length) {
				System.out.println("Saisie incorrecte.");
			}
		} while (choix < 1 || choix > options.length);
		return choix;
	}
}
