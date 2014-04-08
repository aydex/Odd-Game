package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileManager {

	private static String fileDirectory = "";
	
	/**
	 * returns the string-representation from the given filename
	 * @param fileName the filename of the file
	 * @return the string-representation of the given filename
	 */
	public static String getStringFromFile(String fileName){
		String fileOutput = ""; 
		Scanner in;
		 try
		 {
			 in = new Scanner(new FileReader(fileDirectory+fileName+".txt"));
			 while(in.hasNext()){
				 String tempString = in.nextLine();
				 //System.out.println("getStringFromFile("+fileName+") - tempString: "+tempString);
				 fileOutput+=tempString;
			 }
			 in.close();
			 //System.out.println("getStringFromFile - done with stringappend");
			 return fileOutput;
		 }
		 catch (FileNotFoundException e)
		 {
			 System.err.println("Error: file " + fileName + " could not be opened. Does it exist?");
			 System.exit(1);
		 }
		 return fileOutput;
	}

	
	/**
	 * returns the string-representation from the given filename with newline
	 * @param fileName the filename of the file
	 * @return the string-representation of the given filename with newline
	 */
	public static String getStringFromFileNewline(String fileName){
		String fileOutput = ""; 
		Scanner in;
		 try
		 {
			 in = new Scanner(new FileReader(fileDirectory+fileName+".txt"));
			 while(in.hasNext()){
				 String tempString = in.nextLine();
				 fileOutput+=tempString;
				 fileOutput+="\n";
			 }
			 if (fileOutput.length() > 0 && fileOutput.charAt(fileOutput.length()-1)=='n' && fileOutput.charAt(fileOutput.length()-2)=='/') {
				 fileOutput = fileOutput.substring(0, fileOutput.length()-2);
				  }
			 in.close();
			 return fileOutput;
		 }
		 catch (FileNotFoundException e)
		 {
			 System.err.println("Error: file " + fileName + " could not be opened. Does it exist?");
			 System.exit(1);
		 }
		 return fileOutput;
	}

	
	/**
	 * saves the file to the fileName
	 * @param fileName the filename of the file
	 * @param file the string to be saved
	 */
	public static void saveStringToFile(String fileName, String file){
		try
	        {
	            PrintWriter outFile = new PrintWriter(fileDirectory+fileName+".txt");
	            for(int i = 0 ; i < 15; i++){
	            	//System.out.println("saveStringToFile("+fileName+","+file+") - "+file.substring(i, i+20));
	            	outFile.println(file.substring(i, i+20));
	            }
	            outFile.close();
	        }
	        catch (FileNotFoundException e)
	        {
	            System.err.println("Error: file '"+fileName+"' could not be opened for writing.");
	            System.exit(1);
	        }
	}

}
