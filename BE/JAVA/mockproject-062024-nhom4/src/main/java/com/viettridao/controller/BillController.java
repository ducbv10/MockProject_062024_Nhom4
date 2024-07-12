package com.viettridao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viettridao.dto.BillDTO;
import com.viettridao.dto.BillOutput;
import com.viettridao.entity.Bill;
import com.viettridao.exception.AppException;
import com.viettridao.exception.ErrorCode;
import com.viettridao.response.ApiResponse;
import com.viettridao.service.IBillService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(value = "/bills")
public class BillController {
	
	@Autowired
	private IBillService billService;
	
	@PostMapping()
	ApiResponse<BillDTO> createBill(@RequestBody @Valid BillDTO model) {
		ApiResponse<BillDTO> apiResponse = new ApiResponse<>();
		
		// check bill is existed yet with function findById in BillService
		try {
			Bill bill = billService.findById(model.getBillId());
			// if true give exception ID is duplicated
			apiResponse.setCode(ErrorCode.ID_DUPLICATED.getCode());
			apiResponse.setMessage(ErrorCode.ID_DUPLICATED.getMessage());
		} catch (AppException e) {
			// else insert bill
			apiResponse.setData(billService.saveOrUpdate(model));
			apiResponse.setMessage("insert success");
		}
//		apiResponse.setData(billService.saveOrUpdate(model));
//		apiResponse.setMessage("insert success");
		
		return apiResponse;
	}
	
	@GetMapping()
	ApiResponse<BillOutput> getBills(@RequestParam("page") int page,
									 @RequestParam("limit") int limit) {
		ApiResponse<BillOutput> apiResponse = new ApiResponse<>();
		
		BillOutput output = new BillOutput();
		output.setPage(page);
		Pageable pageable = PageRequest.of(page - 1, limit);
		output.setResult(billService.getAllBills(pageable));
		output.setTotalPage((int) Math.ceil((double) (billService.getTotalItem()) / limit));
		apiResponse.setData(output);
		apiResponse.setMessage("success");
		
		return apiResponse;
	}
	
	@GetMapping(value = "/users/{userId}")
	ApiResponse<BillOutput> getBillsByUserId(@PathVariable("userId") String userId, 
											 @RequestParam("page") int page,
											 @RequestParam("limit") int limit) {
		ApiResponse<BillOutput> apiResponse = new ApiResponse<>();
		
		BillOutput output = new BillOutput();
		output.setPage(page);
		Pageable pageable = PageRequest.of(page - 1, limit);
		output.setResult(billService.getBillsByUserId(userId, pageable));
		output.setTotalPage((int) Math.ceil((double) (output.getResult().size()) / limit));
		
		apiResponse.setData(output);
		apiResponse.setMessage("success");
		
		return apiResponse;
	}
	
	@GetMapping(value = "/{billId}")
	ApiResponse<BillDTO> getBillById(@PathVariable("billId") String billId) {
		ApiResponse<BillDTO> apiResponse = new ApiResponse<>();
		
		apiResponse.setData(billService.getBillById(billId));
		apiResponse.setMessage("success");
		
		return apiResponse;
	}
	
	@PutMapping(value = "/{billId}")
	ApiResponse<BillDTO> updateBill(@RequestBody @Valid BillDTO model, @PathVariable("billId") String billId) {
		ApiResponse<BillDTO> apiResponse = new ApiResponse<>();
		
		model.setBillId(billId);
		
		// check Bill is deleted yet
		if (billService.findById(billId).getDeleteAt() != null) {
			apiResponse.setCode(ErrorCode.BILL_NOT_EXISTED.getCode());
			apiResponse.setMessage(ErrorCode.BILL_NOT_EXISTED.getMessage());
		} else {
			apiResponse.setData(billService.saveOrUpdate(model));
			apiResponse.setMessage("update success");
		}
		
		return apiResponse;
	}
	
	@DeleteMapping(value = "/{billId}")
	ApiResponse<String> deleteBill(@PathVariable("billId") String billId) {
		ApiResponse<String> apiResponse = new ApiResponse<>();
		
		Bill bill = billService.delete(billId);
		apiResponse.setMessage("delete success at " + bill.getDeleteAt());
		
		return apiResponse;
	}
}
