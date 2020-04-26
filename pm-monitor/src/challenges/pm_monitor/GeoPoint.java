package challenges.pm_monitor;

import java.text.DecimalFormat;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

/**
 * Simplifications for this exercise: longitud(lng) in spherical coordinates is
 * treated like x in cartesian coordinates latitude(lat) in spherical
 * coordinates is treated like y in cartesian coordinates.
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class GeoPoint {

	public static double EARTH_RADIUS = 6378.137 * 1E3;

	@JsonIgnore
	private Integer ilng = 0;
	@JsonIgnore
	private Integer ilat = 0;
	private String lat = "0";
	private String lng = "0";

	public GeoPoint(Integer lng, Integer lat) {
		this.ilng = lng;
		this.ilat = lat;
		String patternLat = "#.#####";
		String patternLng = "#.####";
		DecimalFormat decimalFormat = new DecimalFormat(patternLat);
		this.lat = decimalFormat.format(lat * 1E-6);
		decimalFormat.applyPattern(patternLng);
		this.lng = decimalFormat.format(lng * 1E-6);
	}

	public boolean greaterThan(GeoPoint anotherGeopPoint) {
		// TODO: proper calculation needed -> is this a further geoPoint according to
		// the line direction => further in terms of distance than previous as reference
		if (this.ilat > anotherGeopPoint.getIlat() || this.ilng > anotherGeopPoint.getIlng())
			return false;
		return true;
	}

	/**
	 * Checks if the distance from <code>this</code> point to another is less than
	 * the parameter distance in meters. Used
	 * 
	 * @param anotherGeopoint This is a {@code GeoPoint} to calculate the distance
	 *                        from/to
	 * @param distance        The distance in meters to compare with calculated one
	 * @return True if distance between points is less than distance provided, False
	 *         otherwise
	 */
	public boolean nearerThan(GeoPoint anotherGeopoint, Integer distance) {

		Double dist = haversine(anotherGeopoint);

		if (dist <= distance)
			return true;
		return false;
	}

	/**
	 * Distance in km between to this and other points using:
	 * https://en.wikipedia.org/wiki/Haversine_formula d is the distance between the
	 * two points along a great circle of the sphere r is the radius of the sphere
	 * φ1, φ2 are the latitude of point 1 and latitude of point 2 (in radians) λ1,
	 * λ2 are the longitude of point 1 and longitude of point 2 (in radians)
	 * 
	 * @return Haversine formula result
	 */
	public Double haversine(GeoPoint anotherGeopoint) {

		Double coefDegToRad = 1E-6 * Math.PI / 180;

		Double thisRadLng = this.getIlng() * coefDegToRad;
		Double thisRadLat = this.getIlat() * coefDegToRad;

		Double otherRadLng = anotherGeopoint.getIlng() * coefDegToRad;
		Double otherRadLat = anotherGeopoint.getIlat() * coefDegToRad;

		Double lngRadDiff = (thisRadLng - otherRadLng) / 2;
		Double latRadDiff = (thisRadLat - otherRadLat) / 2;

		Double inArcSin = Math
				.sqrt(Math.pow(Math.asin(latRadDiff), 2) + Math.cos(thisRadLat) * Math.cos(otherRadLat) * Math.pow(Math.sin(lngRadDiff), 2));
		if (inArcSin > 1 || inArcSin < 0)
			throw new RuntimeException("Haversine formula failed to calculate distance");
		Double d = 2 * EARTH_RADIUS * Math.asin(inArcSin);

		return d;
	}

	/**
	 * Decodes polylines from google maps:
	 * http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
	 * 
	 * @author Jeffrey Sambells
	 * @param encoded polyline encoded
	 * @return {@code List<GeoPoint>} represented by the input encoded polyline
	 */
	public static List<GeoPoint> decodePoly(String encoded) {

		List<GeoPoint> poly = new ArrayList<GeoPoint>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			GeoPoint p = new GeoPoint((int) (((double) lng / 1E5) * 1E6), (int) (((double) lat / 1E5) * 1E6));
			poly.add(p);
		}

		return poly;
	}

	@Override
	public String toString() {
		return "{" + "\"lat\":" + getLat() + ", \"lng\":" + getLng() + "}";
	}

}
