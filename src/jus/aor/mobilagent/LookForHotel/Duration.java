package jus.aor.mobilagent.LookForHotel;

import jus.aor.mobilagent.kernel._Service;

public class Duration implements _Service<Long>{
	public Duration(Object... args){
	}
	
	@Override
	public Long call(Object... params) throws IllegalArgumentException {
		return System.currentTimeMillis();
	}
}