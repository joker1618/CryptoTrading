package edu.self.kraken;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import edu.self.kraken.api.KrakenApi;
import edu.self.kraken.api.KrakenApi.Method;

public class Examples {

    public static void main(String[] args) throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        KrakenApi api = new KrakenApi();
        api.setKey("VRQ4Oe8ZNPbLEyjv26SlU7F5O8zR9DjXr07Q6EHf95ooaSszvOkISQTc"); // FIXME
        api.setSecret("E07VIMtiE1x+0m1ef67ERAB8b3Xrk3Z1hxOlWbEUEB0dqKxghci7a+bnPeEmDrBigdYQ1My24fpPeMW9fOplIw=="); // FIXME

        String response;
        Map<String, String> input = new HashMap<>();

//        input.put("pair", "XBTEUR");
//        response = api.queryPublic(Method.TICKER, input);
//        System.out.println(response);
//
//        input.clear();
//        input.put("pair", "XBTEUR");
//        response = api.queryPublic(Method.ASSET_PAIRS, input);
//        System.out.println(response);

		input.clear();
		input.put("asset", "ZEUR");
		response = api.queryPrivate(Method.BALANCE, input);
		System.out.println(response);

		input.clear();
		input.put("asset", "ZEUR");
		response = api.queryPrivate(Method.TRADE_BALANCE, input);
		System.out.println(response);
    }
}
