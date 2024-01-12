package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import bo.Restaurant;

public abstract class CSVReader {
	private static final String CSV_SEP = ";";
	
	public static List<Restaurant> readCSV(String path) {
		List<Restaurant> result = new ArrayList<>();
		Restaurant current = new Restaurant();
		
		File file = new File(path);
		try (Scanner fileReader = new Scanner(file)) {
			if (fileReader.hasNext()) fileReader.next();
			
			while (fileReader.hasNext()) {
				String line = fileReader.nextLine();
				if (line.isBlank()) continue;
				
				String[] data = line.split(CSV_SEP);
				
				if (data[0].equals(current.getNom())) {
					
				} else {
					if (current.getNom() != null) {
						result.add(current);
					}
					current = new Restaurant();
					current.setNom(data[0]);
					current.setAdresse(data[1]);
				}
				
				// TODO Terminer la lecture du CSV
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
