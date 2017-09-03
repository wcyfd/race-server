package com.randioo.race_server.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class ParameterFilter extends Filter {

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
		Map<String, Object> parameters = new HashMap<>();
		parseGetParameters(exchange, parameters);
//		parsePostParameters(exchange, parameters);
		fillAttribute(exchange, parameters);
		chain.doFilter(exchange);
	}

	private void fillAttribute(HttpExchange httpExchange, Map<String, Object> parameters) {
		for (Map.Entry<String, Object> entrySet : parameters.entrySet()) {
			String key = entrySet.getKey();
			Object value = entrySet.getValue();
			httpExchange.setAttribute(key, value);
		}
	}

	private void parseGetParameters(HttpExchange exchange, Map<String, Object> parameters)
			throws UnsupportedEncodingException {
		URI requestedUri = exchange.getRequestURI();
		String query = requestedUri.getRawQuery();
		parseQuery(query, parameters);
		exchange.setAttribute("parameters", parameters);
	}

	private void parsePostParameters(HttpExchange exchange, Map<String, Object> parameters) throws IOException {
		if ("post".equalsIgnoreCase(exchange.getRequestMethod())) {
			InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			parseQuery(query, parameters);
		}
	}

	@SuppressWarnings("unchecked")
	private void parseQuery(String query, Map<String, Object> parameters) throws UnsupportedEncodingException {
		if (query != null) {
			String pairs[] = query.split("[&]");

			for (String pair : pairs) {
				String[] param = pair.split("[=]");

				String key = null;
				String value = null;
				if (param.length > 0) {
					key = URLDecoder.decode(param[0], System.getProperty("file.encoding"));
				}

				if (param.length > 1) {
					value = URLDecoder.decode(param[1], System.getProperty("file.encoding"));
				}

				if (parameters.containsKey(key)) {
					Object obj = parameters.get(key);
					if (obj instanceof List<?>) {
						List<String> values = (List<String>) obj;
						values.add(value);
					} else if (obj instanceof String) {
						List<String> values = new ArrayList<>();
						values.add((String) obj);
						values.add(value);
						parameters.put(key, values);
					}
				} else {
					parameters.put(key, value);
				}
			}
		}
	}

	public static void main(String[] args) {
		
	}
}
