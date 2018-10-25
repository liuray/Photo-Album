package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import models.User;



/**
 * To read and write the data in txt file 
 * @author Jiwei Chen
 * @author Pengrui Liu
 *
 */
public class fileReaderWriter {

	//public static ArrayList<String> temp = new ArrayList<String>();

	/**
	 * To read data from file
	 */
	public fileReaderWriter() {
	}
// read data 
	public static ArrayList<String> readData(String file) {
		ArrayList<String> users = new ArrayList<String>();
		// TODO Auto-generated method stub
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line = br.readLine();

			while (line != null) {
				try {
					users.add(line);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("wrong file");
				}
				line = br.readLine();
			
				//System.out.println("....."+line);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Failed to write to file.");
		} catch (NoSuchElementException e) {
			System.out.println("No Such Element Exception");
		}
		return users;

	}
	// write data
	/**
	 * To write data in file
	 * @param as An ArrayList to store data
	 */
	public static void writeData(ArrayList<String> as) {

		try {
			FileWriter ef = new FileWriter("./data/UserProfiles/user.txt");
			for (int i = 0; i < as.size(); i++) {
				ef.write(as.get(i));
				
				ef.write(System.getProperty( "line.separator" ));
			}

			ef.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//delete data
	/**
	 * To delete the data from file
	 * @param s Data
	 * @throws IOException
	 */
	public static void removeCertainLine(String s) throws IOException {
		File inputFile = new File("./data/UserProfiles/user.txt");
		File tempFile = new File("./data/UserProfiles/temp.txt");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String lineToRemove = s;
		String currentLine;

		while ((currentLine = reader.readLine()) != null) {
			String trimmedLine = currentLine.trim();
			if (trimmedLine.equals(lineToRemove))
				continue;
			writer.write(currentLine + System.getProperty("line.separator"));
		}
		writer.close();
		reader.close();
		BufferedWriter out = new BufferedWriter(new FileWriter("./data/UserProfiles/user.txt", false));
		out.write("");
		out.close();
		InputStream inStream = null;
		OutputStream outStream = null;

		try {

			File afile = new File("./data/UserProfiles/temp.txt");
			File bfile = new File("./data/UserProfiles/user.txt");

			inStream = new FileInputStream(afile);
			outStream = new FileOutputStream(bfile);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {

				outStream.write(buffer, 0, length);

			}

			inStream.close();
			outStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		out = new BufferedWriter(new FileWriter("./data/UserProfiles/temp.txt", false));
		out.write("");
		out.close();
	}

}
