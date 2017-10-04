package com.fede.app.time;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static java.lang.System.out;

/**
 * Created by f.barbano on 20/09/2017.
 */
public class Spikes {

	@Test
	public void testSecondsToLocalDateTime() {
		long sec = 1505935920;
		testMillisToLocalDateTime(sec*1000L);
	}
	@Test
	public void testMillisToLocalDateTime() {
		long millis = 1505669502050L;
		testMillisToLocalDateTime(millis);
	}
	public void testMillisToLocalDateTime(long millis) {
		LocalDateTime ldt = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime();
		out.println(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS").format(ldt));
	}

}
