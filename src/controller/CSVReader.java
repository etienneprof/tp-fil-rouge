package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class CSVReader {
	private static final String CSV_SEP = ";";
	
	public static List<String[]> readCSV(String path) {
		List<String[]> result = new ArrayList<String[]>();
		File file = new File(path);
		try (Scanner fileReader = new Scanner(file)) {
			if (fileReader.hasNext()) fileReader.next();
			
			while (fileReader.hasNext()) {
				String line = fileReader.nextLine();
				if (line.isBlank()) continue;
				
				String[] data = line.split(CSV_SEP);
				result.add(data);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
