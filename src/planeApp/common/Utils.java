package planeApp.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.Arrays;
import java.util.Random;

public class Utils {
	/*
	 * Ported from my javascript utilities
	 * Should replace date stuff with Java-specific libs
	 */
	public static String randomJMBG(int seed) {
		Random rand = seed == 0 ? new Random() : new Random(seed);
		int year = rand.nextInt(Year.now().getValue() - 1899) + 1900; // Assuming no one's older
		int month = rand.nextInt(12) + 1;
		int dayBound = 0;
		if (month <= 7) {
			if (month % 2 != 0)
				dayBound = 31;
			else if (month % 2 == 0) {
				if (month == 2) {
					if (year % 4 == 0)
						dayBound = 29;
					else
						dayBound = 28;
				} else
					dayBound = 30;
			}
		} else if (month % 2 == 0)
			dayBound = 31;
		else
			dayBound = 30;
		int day = rand.nextInt(dayBound);
		int cityCode = rand.nextInt(97); //96 that I know of
		int gender = rand.nextInt(1000);
		
		String jmbg = String.format("%02d%02d%03d%02d%03d",day,month,year % 1000,cityCode,gender);
		int[] a = Arrays.stream(jmbg.split("")).map(String::trim).mapToInt(Integer::parseInt).toArray();
		int checksum = 11 - (( 7*(a[0]+a[6]) + 6*(a[1]+a[7]) + 5*(a[2]+a[8]) + 4*(a[3]+a[9]) + 3*(a[4]+a[10]) + 2*(a[5]+a[11]) ) % 11);
		checksum = checksum > 9 ? 0 : checksum;
		
		return jmbg+=checksum;
	}
	/*
	 * From https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
	 */
	public static double distance(double lat1, double lat2, double lon1,
	        double lon2, double el1, double el2) {
	    final int R = 6371;
	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000;
	    double height = el1 - el2;
	    distance = Math.pow(distance, 2) + Math.pow(height, 2);
	    return Math.sqrt(distance);
	}
	/*
	 * From http://www.baeldung.com/java-round-decimal-number
	 */
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	public static void main(String[] args) {
		System.out.println(distance(42.43, 37.75, 19.26, -122.44, 0, 0)/620000);
	}
}
