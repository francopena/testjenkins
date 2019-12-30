package cl.mallplaza.salesforcemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SendMailStatusEnum {

	NOT_SENT(0, "NOT_SENT"),
	SENT(1, "SENT" );
	
	private int code;
	private String description;
}
