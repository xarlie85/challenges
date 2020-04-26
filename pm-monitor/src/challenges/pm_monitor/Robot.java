package challenges.pm_monitor;

import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.java.Log;

@Log
public class Robot {

	private final static Integer CONFIG_REPORTING_PERIOD_IN_MINUTS = 15;
	/**
	 * Real simulation by making the current thread be sleeping during 1s of
	 * distance run each progress iteration.
	 */
	private final static boolean CONFIG_SIMULATE_TIME = false;
	/**
	 * Even though the output is just the 15min report, some measures can be
	 * retrieved at the end of the route.
	 */
	private final static boolean CONFIG_SEND_FINAL_REPORT_IF_BUFFER_NOT_EMPTY = false;
	/**
	 * A buffer of measures to report.
	 */
	private List<Measure> reportToSend = new ArrayList<>();
	/**
	 * Accumulated distance before each measuring action (max. 100)
	 */
	private int accDist = 0;
	/**
	 * Accumulated time before sending report action
	 */
	private int accTime = 0;
	// this is a variable to simplify the solution
	private boolean endLineReached = false;

	// velocity
	private int getVelocity() {
		return new Random().nextInt(3) + 1;
	}

	/**
	 * Goes through all the lines of the polyline
	 * 
	 * @param polyline The polyline to traverse
	 * @throws InterruptedException    The Exception thrown when the Thread is
	 *                                 interrupted
	 * @throws JsonProcessingException The Exception thrown when report could not be
	 *                                 sent
	 */
	public void route(String polyline) throws InterruptedException, JsonProcessingException {

		List<GeoPoint> route = GeoPoint.decodePoly(polyline);
		int i = 2;
		for (; i < route.size(); i++) {
			log.info("[" + i + "] of [" + route.size() + "]" + "from [" + route.get(i - 1) + "] to [" + route.get(i) + "]");
			goAcrossTheLine(route.get(i - 1), route.get(i));
		}

		if (CONFIG_SEND_FINAL_REPORT_IF_BUFFER_NOT_EMPTY) {
			if (!reportToSend.isEmpty()) {
				log.info("There exist: " + reportToSend.size() + " not sent measures in the buffer!");
				this.sentReport(route.get(i - 1));
			}
		}
		log.info("DONE!");
	}

	private void goAcrossTheLine(GeoPoint fromGeoPoint, GeoPoint toGeoPoint) throws InterruptedException, JsonProcessingException {
		endLineReached = false;

		for (; accTime < CONFIG_REPORTING_PERIOD_IN_MINUTS * 60; accTime++) {
			fromGeoPoint = progress(fromGeoPoint, toGeoPoint);

			// just to run on PRODUCTION (to sinchronize with the robot)
			if (CONFIG_SIMULATE_TIME)
				wait(1000);

			if (fromGeoPoint.equals(toGeoPoint) || endLineReached)
				break;

		}

		// sending CONFIG_REPORTING_PERIOD_IN_MINUTS (default 15) min average report
		if (accTime >= CONFIG_REPORTING_PERIOD_IN_MINUTS * 60) {
			sentReport(fromGeoPoint);
			accTime = 0; // restarting time
		}

		if (!fromGeoPoint.equals(toGeoPoint) && !endLineReached)
			goAcrossTheLine(fromGeoPoint, toGeoPoint);

	}

	private void sentReport(GeoPoint geoPoint) throws JsonProcessingException {
		if (reportToSend.isEmpty())
			return;
		Double pmAverage = reportToSend.stream().mapToInt(measure -> measure.getPmAmount()).average().getAsDouble();
		log.info(new Measure(geoPoint, pmAverage).toJSON());
		log.info("measures amount SENT: [" + reportToSend.size() + "]");
		reportToSend = new ArrayList<Measure>();
	}

	private GeoPoint progress(GeoPoint fromGeoPoint, GeoPoint toGeoPoint) throws JsonProcessingException {
		Integer currentSpeed = getVelocity();
		GeoPoint nextGeoPoint = calculateNextGeopoint(fromGeoPoint, toGeoPoint, currentSpeed);

		accDist += currentSpeed;
		log.info("Current speed: [" + currentSpeed + "] -> accumulated Distance: [" + accDist + "]");
		if (accDist >= 100) {
			measurePM(nextGeoPoint);
			accDist = 0;
		}

		if (nextGeoPoint.greaterThan(toGeoPoint)) {
			endLineReached = true;
			sendAliveReport(toGeoPoint);
			return toGeoPoint; // the robot surpassed the toGeoPoint -> TODO: substract the difference from
								// accDist
		}
		sendAliveReport(nextGeoPoint);

		return nextGeoPoint;
	}

	private void sendAliveReport(GeoPoint geoPoint) throws JsonProcessingException {
		GeoPoint buckingHamGPoint = new GeoPoint(-141935, 51501299);
		GeoPoint templeStationGPoint = new GeoPoint(-114165, 51510852);

		if (geoPoint.nearerThan(buckingHamGPoint, 100)) {
			log.info("Buckingham:" + buckingHamGPoint);
			log.info(new Measure(geoPoint, new Random().nextInt(200)).toJSON());
		}
		if (geoPoint.nearerThan(templeStationGPoint, 100)) {
			log.info("Temple Station:" + templeStationGPoint);
			log.info(new Measure(geoPoint, new Random().nextInt(200)).toJSON());
		}

	}

	private void measurePM(GeoPoint nextGeoPoint) {
		reportToSend.add(new Measure(nextGeoPoint, new Random().nextInt(200)));
		log.info("MEASURE PERFORMED: [" + reportToSend.size() + "]");
	}

	/**
	 * Next point is calculated following M.R.U
	 * https://es.wikipedia.org/wiki/Movimiento_rectil%C3%ADneo_uniforme
	 * 
	 * @param fromGeoPoint The initial point
	 * @param toGeoPoint   The final point of the line to traverse
	 * @param currentSpeed The speed of the Robot at this current time
	 * @return The Next GeoPoint in the line reached at the current speed
	 */
	private GeoPoint calculateNextGeopoint(GeoPoint fromGeoPoint, GeoPoint toGeoPoint, Integer currentSpeed) {
		// angle
		double angle = Math.atan2(toGeoPoint.getIlat() - fromGeoPoint.getIlat(), toGeoPoint.getIlng() - fromGeoPoint.getIlng());
		// (cLng, cLat) ~ (cx,cy) = (ax,ay) + v*(cos(angle),sin(angle))
		GeoPoint nextGeopoint = new GeoPoint((int) (fromGeoPoint.getIlng() + currentSpeed * Math.cos(angle)),
				(int) (fromGeoPoint.getIlat() + currentSpeed * Math.sin(angle)));
		log.info("nextPoing: " + nextGeopoint);
		return nextGeopoint;
	}

	public static void main(String[] args) throws InterruptedException, JsonProcessingException {
		String polyline = "mpjyHx`i@VjAVKnAh@BHHX@LZR@Bj@Ml@WWc@]w@bAyAfBmCb@o@pLeQfCsDVa@@ODQR}AJ{A?{BGu AD_@FKb@MTUX]Le@^kBVcAVo@Ta@|EaFh@m@FWaA{DCo@q@mCm@cC{A_GWeA}@sGSeAcA_EOSMa @}A_GsAwFkAiEoAaFaBoEGo@]_AIWW{AQyAUyBQqAI_BFkEd@aHZcDlAyJLaBPqDDeD?mBEiA}@F]yKWqGSkI CmCIeZIuZi@_Sw@{WgAoXS{DOcAWq@KQGIFQDGn@Y`@MJEFIHyAVQVOJGHgFRJBBCCSKBcAKoACyA?m@^y VJmLJ{FGGWq@e@eBIe@Ei@?q@Bk@Hs@Le@Rk@gCuIkJcZsDwLd@g@Oe@o@mB{BgHQYq@qBQYOMSM GBUBGCYc@E_@H]DWJST?JFFHBDNBJ?LED?LBv@WfAc@@EDGNK|@e@hAa@`Bk@b@OEk@Go@IeACoA@ a@PyB`@yDDc@e@K{Bi@oA_@w@]m@_@]QkBoAwC{BmAeAo@s@uAoB_AaBmAwCa@mAo@iCgAwFg@iD q@}G[uEU_GBuP@cICmA?eI?qCB{FBkCI}BOyCMiAGcAC{AN{YFqD^}FR}CNu@JcAHu@b@_E`@}DVsB^mBTsAQ KkCmAg@[YQOIOvAi@[m@e@s@g@GKCKAEJIn@g@GYGIc@ScBoAf@{A`@uAlBfAG`@";
		new Robot().route(polyline);
	}

}
