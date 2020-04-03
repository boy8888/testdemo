package com.hummingbird.maaccount.vo;

import com.hummingbird.commonbiz.vo.AppBaseVO;
import com.hummingbird.commonbiz.vo.Decidable;

public class AccountPayAllowAddTerminalVO extends AppBaseVO implements Decidable{
	  private AccountPayAllowAddTerminalBodyVO body ;

	  public AccountPayAllowAddTerminalBodyVO getBody() {
		return body;
	  }

	  public void setBody(AccountPayAllowAddTerminalBodyVO body) {
		this.body = body;
	  }
	  @Override
	  public String toString() {
	      return " AccountPayAllowAddTerminalBodyVO[body=" + body + ", app="
		  + app + "]";
	  }

      
}
