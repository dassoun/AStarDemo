package tool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Misc {
	public static String[][] fileTo2DArray(String fileName) {
		ArrayList<String> list = fileToStringList(fileName);
		
		int dim1 = list.size();
		int dim2 = list.get(0).length();
		
		String[][] array = new String[dim1][dim2];
		
		for (int i = 0; i < dim1; i++ ) {
			for (int j = 0; j < dim2; j++ ) {
				array[i][j] = String.valueOf(list.get(i).charAt(j));
			}
		}
		
		return array;
	}
	
	public static ArrayList<String> fileToStringList(String fileName) {
		ArrayList<String> list = new ArrayList<String>();
		
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach((i) -> list.add(i));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
