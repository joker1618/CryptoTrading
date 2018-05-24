package com.fede.app.temp;

import com.fede.app.crypto.trading.util.DateUtils;
import com.fede.app.crypto.trading.util.Utils;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

/**
 * Created by f.barbano on 05/11/2017.
 */
public class TestDb {
	static int NUM_ENTRY = 500;
	static int NUM_INS_THREAD = 2;

	Connection conn = createConnection();
	final Object writeMonitor = new Object();
	Random random = new Random(System.currentTimeMillis());
	AtomicInteger stopCounter = new AtomicInteger(NUM_INS_THREAD);

	@Test
	public void testDb() throws InterruptedException, SQLException {
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(NUM_INS_THREAD+1);
		AtomicInteger counter = new AtomicInteger(0);
		executorService.submit(this::doCountEntries);
		for(int i = 0; i < NUM_INS_THREAD; i++)	executorService.submit(() -> doJbdcInsert(counter.getAndIncrement(), 1000.123456789d));
		executorService.shutdown();
		while (!executorService.isTerminated())		Thread.sleep(1000);
	}

	@Test
	public void temp() {
		doCountEntries();
	}

	private void doCountEntries() {
		try {
//			Connection conn = createConnection();
			try (PreparedStatement ps = conn.prepareStatement("select count(*) as tot from jdbc_time")) {
				while (stopCounter.get() > 0) {
					ResultSet rs = ps.executeQuery();
					rs.next();
					int tot = rs.getInt("tot");
					rs.close();
					log("Count = %d", tot);
				}
			}
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void doJbdcInsert(int tid, double num) {
		long start = System.currentTimeMillis();
		
		try {
			String queryIns = "INSERT INTO JDBC_TIME (A, B, C, D, E,f,g,h,i,l,m,n,o,p,q,aa,bb,cc,dd,ee,ff,gg) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
			synchronized (writeMonitor) {
	//			Connection conn = createConnection();
				conn.setAutoCommit(false);
				try(PreparedStatement ps = conn.prepareStatement(queryIns)) {
					for (int i = 0; i < NUM_ENTRY; i++) {
	//					ps.clearParameters();
						ps.setString(1, "jdbc insert " + i);
						ps.setLong(2, start);
						ps.setInt(3, tid);
						ps.setDouble(4, num);
						ps.setBigDecimal(5, BigDecimal.valueOf(num));
						ps.setDouble(6, num);
						ps.setBigDecimal(7, BigDecimal.valueOf(num));
						ps.setDouble(8, num);
						ps.setBigDecimal(9, BigDecimal.valueOf(num));
						ps.setDouble(10, num);
						ps.setBigDecimal(11, BigDecimal.valueOf(num));
						ps.setDouble(12, num);
						ps.setBigDecimal(13, BigDecimal.valueOf(num));
						ps.setDouble(14, num);
						ps.setBigDecimal(15, BigDecimal.valueOf(num));
						ps.setDouble(16, num);
						ps.setBigDecimal(17, BigDecimal.valueOf(num));
						ps.setDouble(18, num);
						ps.setBigDecimal(19, BigDecimal.valueOf(num));
						ps.setDouble(20, num);
						ps.setBigDecimal(21, BigDecimal.valueOf(num));
						ps.setBigDecimal(22, BigDecimal.valueOf(num));
						ps.addBatch();
						if (i > 0 && i % 100 == 0) {
							log("  ...%d...%d\telapsed: %s", tid, i, Utils.humanElapsed(System.currentTimeMillis() - start, true));
						}
					}
					ps.executeBatch();
					conn.commit();
					conn.setAutoCommit(true);
				}
			}

		} catch(Exception e) {
			stopCounter.decrementAndGet();
			throw new RuntimeException(e);
		}

		long end = System.currentTimeMillis();

		log("JDBC insert: %d entries, elapsed: %s", NUM_ENTRY, Utils.humanElapsed(end-start, true));
		stopCounter.decrementAndGet();
	}



	private static Connection createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/SPIKES_DB", "user", "user");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void log(String format, Object... params) {
		System.out.println(String.format(format, params));
	}
}
