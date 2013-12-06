import java.util.*;
import java.io.*;

public class CSVReader {

	public static void main(String[] args) {
		int amount = 0;

		Scanner scanner = new Scanner(System.in);

		System.out.print("Please enter the location of your .csv file: ");

		String path = scanner.nextLine();

		File file = loadFile(path);

		if (file == null) {
			return;
		}

		String[] students = readStudents(file);

		if (students == null) {
			return;
		} else {
			System.out.println("Successfully loaded students!");
		}

		students = shuffle(students);

		int amountOfStudents = 1;

		System.out.println("NOTE: Typing 0 will exit the program, and typing -1 will choose 1 random student.");
		System.out.print("Choose how many students you want per group: ");

		try {
			amountOfStudents = scanner.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Invalid input (must be a number!)");
			return;
		}

		if (amountOfStudents == 0) {
			return;
		} else if (amountOfStudents == -1) {
			System.out.println(nextStudent(students, amount));
			return;
		}

		int group = 0;
		double classSize = students.length * 1.0;
		double studentsPerGroup = amountOfStudents * 1.0;
		int numOfGroups = (int) Math.round(classSize / studentsPerGroup);

		if (studentsPerGroup > (classSize / 2)) {
			numOfGroups = (int) Math.ceil(classSize / studentsPerGroup);
		} else if (studentsPerGroup == 2) {
			numOfGroups = (int) Math.floor(classSize / studentsPerGroup);
		}
		
		ArrayList<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();

		for (int i = 0 ; i < numOfGroups ; i++) {
			groups.add(i, new ArrayList<String>());
		}

		int studentIndex = 0;

		for (int j = 0 ; j < students.length ; j++) {
			groups.get(group).add(studentIndex, nextStudent(students, amount));
			amount++;
			if (group == (numOfGroups - 1)) {
				group = 0;
				studentIndex++;
			} else {
				group++;
			}
		}

		printList(groups);
	}

	public static File loadFile(String path) {
		File input = null;
		try {
			input = new File(path);

			if (!input.getName().endsWith(".csv")) {
				System.out.println("Not a valid .csv file!");
				return null;
			}
		} catch (NullPointerException e) {
			System.out.println("Could not load file at path: " + path);
			e.printStackTrace();
		}

		return input;
	}

	public static String[] shuffle(String[] data) {
		Random r = new Random();

		for (int i = data.length - 1 ; i >= 0 ; i--) {
			int index = r.nextInt(i + 1);
			String temp = data[index];
			data[index] = data[i];
			data[i] = temp;
		}

		return data;
	}

	public static String nextStudent(String[] students, int amountChosen) {
		return students[amountChosen];
	}

	public static String[] readStudents(File file) {
		Scanner reader = null;
		try {
			reader = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Could not find specified file.");
		}

		String data = "";

		while (reader.hasNext()) {
			data += reader.nextLine();
		}

		String[] students = data.split(",");

		return students;
		
	}

	public static void printList(ArrayList<ArrayList<String>> list) {
		int groupNum = 1;
		for (ArrayList<String> group : list) {
			System.out.println("");
			System.out.println("Group " + groupNum + " (" + group.size() + " students) :");
			for (String student : group) {
				System.out.println(student);
			}
			groupNum++;
		}
	} 

}