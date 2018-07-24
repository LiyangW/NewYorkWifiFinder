
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This is the version with importing data set, sort and search algorithms done
 * 
 * @author Group 12
 *
 */
public class Sort_And_Search {

	/**
	 * 
	 * @param args argument
	 * @throws FileNotFoundException cannot read from a non-existed file
	 * @throws IOException IO exception
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("NYCfwhs.xls"));
		HSSFSheet sheet = workbook.getSheetAt(0);
		String[] typeArray = new String[2061]; // Array of wifi type
		String[] locArray = new String[2061]; // Array of locations
		double[] latArray = new double[2061]; // Array of latitudes
		double[] lonArray = new double[2061]; // Array of longitudes

		for (int i = 0; i < 2061; i++) {//get the type of WIFI from the .csv file(DATASET)
			HSSFRow row = sheet.getRow(i + 1);
			if (row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_STRING) {
				typeArray[i] = row.getCell(0).getStringCellValue();
			}
		}

		for (int i = 0; i < 2061; i++) {//get the location of WIFI from the .csv file(DATASET)
			HSSFRow row = sheet.getRow(i + 1);
			if (row.getCell(2).getCellType() == HSSFCell.CELL_TYPE_STRING) {
				locArray[i] = row.getCell(2).getStringCellValue();
			}
		}

		for (int i = 0; i < 2061; i++) {//get the Lat of WIFIi from the .csv file(DATASET)
			HSSFRow row = sheet.getRow(i + 1);
			if (row.getCell(3).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				latArray[i] = row.getCell(3).getNumericCellValue();
			}
		}

		for (int i = 0; i < 2061; i++) {//get the Lon of WIFI from the .csv file(DATASET)
			HSSFRow row = sheet.getRow(i + 1);
			if (row.getCell(4).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				lonArray[i] = row.getCell(4).getNumericCellValue();
			}
		}

		String str = "";//Use split to split the string in to small arrays
		String[][] arr = new String[2061][5];
		for (int i = 0; i < 2061; i++) {
			str = typeArray[i] + "__" + locArray[i] + "__" + latArray[i] + "__" + lonArray[i];
			arr[i] = str.split("__");
		}
		//System.out.println((arr[6][0]));

		// following is the sort
		//sort(arr);
		//System.out.println(isSorted(arr));

		// following is the search
		System.out.println(Arrays.toString(search(arr, 40.60, -74.20)));

	}

	/**
	 * This method search in the data set for the available wifi in range by the
	 * given latitude and longitude
	 * 
	 * @param list
	 *            The list of wifi sorted by latitude
	 * @param x
	 *            The latitude
	 * @param y
	 *            The longitude
	 * @return Return the wifis in range
	 */
	public static String[] search(String[][] list, double x, double y) {
		double r = 0.06; // the range - radius of circle
		ArrayList<String[]> xRange = new ArrayList<String[]>(); // the list of
																// wifi in x
																// range
		ArrayList<String[]> yRange = new ArrayList<String[]>(); // the list of
																// wifi in range

		for (int i = 0; i < list.length; i++) {
			if (Double.parseDouble(list[i][2]) - r <= x && Double.parseDouble(list[i][2]) + r >= x) {
				xRange.add(list[i]);
			}
		}
		System.out.println("Size of xRange: " + xRange.size());

		for (int i = 0; i < xRange.size(); i++) {
			if (Double.parseDouble(xRange.get(i)[3]) + r >= y && Double.parseDouble(xRange.get(i)[3]) - r <= y) {
				yRange.add(xRange.get(i));
			}
		}
		System.out.println("Size of yRange: " + yRange.size());

		for (int i = 0; i < yRange.size(); i++) {
			return yRange.get(i);
		}
		return null;

	}

	/**
	 * 
	 * @param v Comparable
	 * @param w Comparable
	 * @return -1 if v is smaller than w; 0 if they are the same; 1 if v is greater than w
	 */
	public static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	/**
	 * 
	 * @param a two  dimensional array a[][]
	 * @param i current index
	 * @param j index prior to i
	 */
	private static void exch(Comparable[][] a, int i, int j) {
		Comparable[] t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	/**
	 * 
	 * @param a two  dimensional array a[][]
	 */
	private static void show(Comparable[][] a) { // Print the array, on a single
													// line.
		for (int i = 0; i < a.length; i++)
			StdOut.print(Arrays.toString(a[i]) + " ");
		StdOut.println();
	}

	/**
	 * 
	 * @param a two  dimensional array a[][]
	 * @return true if the array is sorted, false if not
	 */
	public static boolean isSorted(Comparable[][] a) { // Test whether the array
														// entries are in order.
		for (int i = 1; i < a.length; i++)
			if (less(a[i][2], a[i - 1][2])) // 2 is the position of latitude in
											// array
				return false;
		return true;
	}

	/**
	 * 
	 * @param a two  dimensional array a[][]
	 */
	public static void sort(Comparable[][] a) { // Sort a[] into increasing
												// order.
		int N = a.length; // array length
		for (int i = 0; i < N; i++) { // Exchange a[i] with smallest entry in
										// a[i+1...N).
			int min = i; // index of minimal entr.
			for (int j = i + 1; j < N; j++)
				if (less(a[j][2], a[min][2]))
					min = j;
			exch(a, i, min);
		}
	}
}
