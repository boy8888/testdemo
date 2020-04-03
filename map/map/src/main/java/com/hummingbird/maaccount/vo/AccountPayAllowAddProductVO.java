package com.hummingbird.maaccount.vo;

import com.hummingbird.commonbiz.vo.AppBaseVO;
import com.hummingbird.commonbiz.vo.Decidable;

public class AccountPayAllowAddProductVO extends AppBaseVO implements Decidable{
	  private AccountPayAllowAddProductBodyVO body ;

	  public AccountPayAllowAddProductBodyVO getBody() {
		return body;
	  }

	  public void setBody(AccountPayAllowAddProductBodyVO body) {
		this.body = body;
	  }
	  @Override
	  public String toString() {
	      return " AccountPayAllowAddProductBodyVO[body=" + body + ", app="
		  + app + "]";
	  }

      
}
