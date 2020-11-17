package com.example.Monitor.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Component;

@Component
@Entity
public class SalesData {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int salesid;

    @Column
    private Date salesDate;

    @Column
    private int salescount;
    
    @ManyToOne
	@JoinColumn(referencedColumnName="Id")
    private Product product;

    public SalesData() {}
    
	public int getSalesid() {
		return salesid;
	}

	public void setSalesid(int salesid) {
		this.salesid = salesid;
	}

	public Date getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}

	public int getSalescount() {
		return salescount;
	}

	public void setSalescount(int salescount) {
		this.salescount = salescount;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
