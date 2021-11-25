package com.bookit.bizlogic;

import com.bookit.common.Action;
import com.bookit.common.Customer;
import com.bookit.db.DBCommand;

public class BizLogicProcess {

	public static void process(Customer co) throws Exception {
		
		switch (co.getAction()) {
		
			case Action.LOGIN:
			
				DBCommand.login(co);
				break;
			case Action.GET_FLIGHTS:
				DBCommand.getFlights(co);
				break;
		    
		}
	}

}
