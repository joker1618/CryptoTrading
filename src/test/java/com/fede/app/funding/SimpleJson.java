package com.fede.app.funding;

import com.fede.app.crypto.trading.common.Const;
import org.junit.Test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static java.lang.System.out;

/**
 * Created by f.barbano on 02/11/2017.
 */
public class SimpleJson {

	@Test
	public void getClosedOrders() throws NoSuchAlgorithmException, InvalidKeyException, IOException {
		KrakenApi api = new KrakenApi();
		api.setKey(Const.KRAKEN_KEY);
		api.setSecret(Const.KRAKEN_SECRET);
		String resp = api.queryPrivate(KrakenMethod.CLOSED_ORDERS);
		out.println("DEPOSIT METHODS\n"+resp);
	}

	@Test
	public void getDepositMethods() throws NoSuchAlgorithmException, InvalidKeyException, IOException {
		KrakenApi api = new KrakenApi();
		api.setKey(Const.KRAKEN_KEY);
		api.setSecret(Const.KRAKEN_SECRET);
		String resp = api.queryPrivate(KrakenMethod.DEPOSIT_METHODS);
		out.println("DEPOSIT METHODS\n"+resp);
	}

	@Test
	public void getDepositAddresses() throws NoSuchAlgorithmException, InvalidKeyException, IOException {
		// NOT WORK
		KrakenApi api = new KrakenApi();
		api.setKey(Const.KRAKEN_KEY);
		api.setSecret(Const.KRAKEN_SECRET);
		String resp = api.queryPrivate(KrakenMethod.DEPOSIT_ADDRESSES);
		out.println("DEPOSIT ADDRESSES\n"+resp);
	}

	@Test
	public void getStatusOfRecentDeposits() throws NoSuchAlgorithmException, InvalidKeyException, IOException {
		// NOT WORK
		KrakenApi api = new KrakenApi();
		api.setKey(Const.KRAKEN_KEY);
		api.setSecret(Const.KRAKEN_SECRET);
		String resp = api.queryPrivate(KrakenMethod.DEPOSIT_STATUS);
		out.println("STATUS OF RECENT DEPOSITS\n"+resp);
	}

	@Test
	public void getWithdrawalInformation() throws NoSuchAlgorithmException, InvalidKeyException, IOException {
		// NOT WORK
		KrakenApi api = new KrakenApi();
		api.setKey(Const.KRAKEN_KEY);
		api.setSecret(Const.KRAKEN_SECRET);
		String resp = api.queryPrivate(KrakenMethod.WITHDRAW_INFO);
		out.println("WITHDRAWAL INFORMATION\n"+resp);
	}
}
