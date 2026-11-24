package com.ledger.ledgermicroservice.adapter.inbound.web.filter;

public class UserIdHolder {
	private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();

	public static void set(Long id) {
		USER_ID.set(id);
	}

	public static Long get() {
		return USER_ID.get();
	}

	public static void clear() {
		USER_ID.remove();
	}
}
