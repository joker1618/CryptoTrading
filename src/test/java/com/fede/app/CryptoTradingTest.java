package com.fede.app;

import com.fede.app.crypto.trading.common.Const;
import com.fede.app.crypto.trading.facade.IKrakenCaller;
import com.fede.app.crypto.trading.facade.KrakenCallerImpl;
import com.fede.app.crypto.trading.model._public.Ticker;
import com.fede.app.crypto.trading.util.DateUtils;
import com.fede.app.crypto.trading.util.StrUtils;
import com.fede.app.crypto.trading.util.Utils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

/**
 * Created by f.barbano on 07/09/2017.
 */
public class CryptoTradingTest {

	private static IKrakenCaller krakenCaller;

	@BeforeClass
	public static void beforeClass() {
		krakenCaller = new KrakenCallerImpl(Const.KRAKEN_KEY, Const.KRAKEN_SECRET);
	}


	@Test
	public void testWithSleep() throws IOException, InterruptedException {
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
		ScheduledFuture<?> scheduledFuture = executor.scheduleAtFixedRate(downloadTickersRunnable(), 5, 10, TimeUnit.SECONDS);
		while(!scheduledFuture.isCancelled()) {
			Thread.sleep(1000);
		}
		out.println("END");
	}

	private Runnable downloadTickersRunnable() {
		return () -> {
			long start = System.currentTimeMillis();
			printOut("%s\tStart downloading tickers", DateUtils.toString(start, "HH:mm:ss.SSS"));
			try {
				List<Ticker> tickers = krakenCaller.getTickers(Arrays.asList("XXBTZEUR", "XXBTZUSD", "BCHEUR", "BCHUSD", "EOSETH", "EOSXBT"));
				long end = System.currentTimeMillis();
				printOut(tickers, "Tickers", start, end);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		};
	}

	private void printOut(String format, Object... params) {
		System.out.println(String.format(format, params));
	}
	private void printOut(List<?> list, String str, long starttm, long endtm) {
		list.forEach(elem -> {
			String s = ToStringBuilder.reflectionToString(elem, ToStringStyle.SHORT_PREFIX_STYLE);
			printOut(s);
		});
		printOut("\nElapsed: %.3f sec", ((double)(endtm-starttm)/1000));
		printOut("%s\t%s downloaded in %.3f sec", DateUtils.toString(endtm, "HH:mm:ss.SSS"), str, ((double)(endtm-starttm)/1000d));
	}
}
