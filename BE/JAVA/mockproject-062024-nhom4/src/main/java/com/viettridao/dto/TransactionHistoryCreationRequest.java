package com.viettridao.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.viettridao.entity.AuctionDetail;
import com.viettridao.entity.TransactionHistory;
import com.viettridao.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class TransactionHistoryCreationRequest {
    private String transactionHistoryId;
    private String userId;
    private String auctionDetailId;
    private LocalDateTime tradeDate;
    private double tradeCost;
    private double depositCost;
    private String note;
    private String status;
    public enum Status {
        SUCCESSFUL,
        FAILED
    }
    private User user;
	
    private AuctionDetail auctionDetail;
	private LocalDateTime deleteAt;
    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AuctionDetail getAuctionDetail() {
		return auctionDetail;
	}

	public void setAuctionDetail(AuctionDetail auctionDetail) {
		this.auctionDetail = auctionDetail;
	}

	public LocalDateTime getDeleteAt() {
		return deleteAt;
	}

	public void setDeleteAt(LocalDateTime deleteAt) {
		this.deleteAt = deleteAt;
	}

	public void setStatus(String status) {
		this.status = status;
	}



    public String getTransactionHistoryId() {
        return transactionHistoryId;
    }

    public void setTransactionHistoryId(String transactionHistoryId) {
        this.transactionHistoryId = transactionHistoryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuctionDetailId() {
        return auctionDetailId;
    }

    public void setAuctionDetailId(String auctionDetailId) {
        this.auctionDetailId = auctionDetailId;
    }

    public LocalDateTime getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDateTime tradeDate) {
        this.tradeDate = tradeDate;
    }

    public double getTradeCost() {
        return tradeCost;
    }

    public void setTradeCost(double tradeCost) {
        this.tradeCost = tradeCost;
    }

    public double getDepositCost() {
        return depositCost;
    }

    public void setDepositCost(double depositCost) {
        this.depositCost = depositCost;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }
}
