package com.viettridao.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)
public class BillDTO {
	@Size(max = 10, message = "ID_INVALID")
	@NotBlank(message = "ID_NOT_BLANK")
	private String billId;	
	private String userId;	
	private String userName;
	private String assetId;
    private String assetName;
	private Double totalAmount;	
	private String status;	
	private LocalDateTime paymentTime;	
	private String taxId;	
	private String taxName;	
	private Double taxValue;
}
