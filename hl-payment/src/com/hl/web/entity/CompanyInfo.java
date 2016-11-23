package com.hl.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hl.common.model.BaseBean;


/**
 * CompanyInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="company_info")
public class CompanyInfo extends BaseBean implements java.io.Serializable {

    // Constructors
	private Long companyId;
    private String companyName;
    private String companyCode;
    private String merchantNum;
    private String cloudIp;
    private Integer port;
    private String bz1;
    private String bz2;
    private String wx_mch_num;
    private String wx_appid;
    private String wx_key;
    private String wx_cert;
    private String ali_partner;
    private String ali_seller_email;
    private String ali_key;
	
	
	
    /** default constructor */
    public CompanyInfo() {
    }

	/** minimal constructor */
    public CompanyInfo(Long companyId, String companyName, String companyCode, String merchantNum, String cloudIp, Integer port) {
    	 this.companyId = companyId;
         this.companyName = companyName;
         this.companyCode = companyCode;
         this.merchantNum = merchantNum;
         this.cloudIp = cloudIp;
         this.port = port;       
    }
    
    /** full constructor */
    public CompanyInfo(Long companyId, String companyName, String companyCode, String merchantNum, String cloudIp, Integer port, String bz1, String bz2) {
    	this.companyId = companyId;
        this.companyName = companyName;
        this.companyCode = companyCode;
        this.merchantNum = merchantNum;
        this.cloudIp = cloudIp;
        this.port = port;
        this.bz1 = bz1;
        this.bz2 = bz2;      
    }
    
    @Id 
    @Column(name="COMPANY_ID", unique=true, nullable=false, precision=10, scale=0)

    public Long getCompanyId() {
        return this.companyId;
    }
    
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    
    @Column(name="COMPANY_NAME", nullable=false, length=100)

    public String getCompanyName() {
        return this.companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    @Column(name="COMPANY_CODE", nullable=false, length=10)

    public String getCompanyCode() {
        return this.companyCode;
    }
    
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
    
    @Column(name="MERCHANT_NUM", nullable=false, length=30)

    public String getMerchantNum() {
        return this.merchantNum;
    }
    
    public void setMerchantNum(String merchantNum) {
        this.merchantNum = merchantNum;
    }
    
    @Column(name="CLOUD_IP", nullable=false, length=20)

    public String getCloudIp() {
        return this.cloudIp;
    }
    
    public void setCloudIp(String cloudIp) {
        this.cloudIp = cloudIp;
    }
    
    @Column(name="PORT", nullable=false)

    public Integer getPort() {
        return this.port;
    }
    
    public void setPort(Integer port) {
        this.port = port;
    }
    
    @Column(name="BZ1", length=50)

    public String getBz1() {
        return this.bz1;
    }
    
    public void setBz1(String bz1) {
        this.bz1 = bz1;
    }
    
    @Column(name="BZ2", length=100)

    public String getBz2() {
        return this.bz2;
    }
    
    public void setBz2(String bz2) {
        this.bz2 = bz2;
    }
    
    @Column(name="WX_MCH_NUM", length=30)
	public String getWx_mch_num() {
		return wx_mch_num;
	}

	public void setWx_mch_num(String wx_mch_num) {
		this.wx_mch_num = wx_mch_num;
	}

	@Column(name="WX_APPID", length=50)
	public String getWx_appid() {
		return wx_appid;
	}

	public void setWx_appid(String wx_appid) {
		this.wx_appid = wx_appid;
	}

	@Column(name="AL_PARTNER", length=50)
	public String getAli_partner() {
		return ali_partner;
	}

	public void setAli_partner(String ali_partner) {
		this.ali_partner = ali_partner;
	}

	@Column(name="AL_SELLER_EMAIL", length=50)
	public String getAli_seller_email() {
		return ali_seller_email;
	}

	public void setAli_seller_email(String ali_seller_email) {
		this.ali_seller_email = ali_seller_email;
	}

	@Column(name="AL_KEY", length=50)
	public String getAli_key() {
		return ali_key;
	}

	public void setAli_key(String ali_key) {
		this.ali_key = ali_key;
	}

	@Column(name="WX_KEY", length=50)
	public String getWx_key() {
		return wx_key;
	}

	public void setWx_key(String wx_key) {
		this.wx_key = wx_key;
	}

	@Column(name="WX_CERT",length=50)
	public String getWx_cert() {
		return wx_cert;
	}

	public void setWx_cert(String wx_cert) {
		this.wx_cert = wx_cert;
	}
    
    
   
}
