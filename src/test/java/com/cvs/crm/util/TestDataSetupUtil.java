package com.cvs.crm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.CarepassEnrollFormHist;
import com.cvs.crm.model.data.CarepassMemberSmry;
import com.cvs.crm.model.data.CarepassMemberStatusHist;
import com.cvs.crm.model.data.Customer;
import com.cvs.crm.model.data.CustomerAddress;
import com.cvs.crm.model.data.CustomerEmail;
import com.cvs.crm.model.data.CustomerPhone;
import com.cvs.crm.model.data.EmployeeCard;
import com.cvs.crm.model.data.XtraCard;
import com.cvs.crm.model.data.XtraCardActiveCoupon;
import com.cvs.crm.model.data.XtraHotCard;
import com.cvs.crm.model.data.XtraParms;
import com.cvs.crm.repository.CarePassDao;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;

import io.restassured.response.Response;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Data
@Slf4j
public class TestDataSetupUtil {
	private Response serviceResponse;

    @Autowired
    CacheRefreshUtil cacheRefreshUtil;

    @Autowired
    CarePassEnrollmentUtil carePassEnrollmentUtil;

    @Autowired
    CarePassDateUtil carePassDateUtil;

    @Autowired
    Customer customer;

    @Autowired
    CustomerAddress customerAddress;

    @Autowired
    CustomerEmail customerEmail;

    @Autowired
    CustomerPhone customerPhone;

    @Autowired
    XtraCardActiveCoupon xtraCardActiveCoupon;

    @Autowired
    CarepassEnrollFormHist carepassEnrollFormHist;

    @Autowired
    CarepassMemberSmry carepassMemberSmry;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    XtraCard xtraCard;

    @Autowired
    XtraParms xtraParms;

    @Autowired
    EmployeeCard employeeCard;

    @Autowired
    XtraHotCard xtraHotCard;

    @Autowired
    CarepassMemberStatusHist carepassMemberStatusHist;

    @Autowired
    CarePassDao carePassDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    XtraCardDao xtraCardDao;
    
	public void createUserWithXtraCardNbr(int cardNum1) throws ParseException, InterruptedException{
		String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
        // int cardNum1 = 99991234;
        int custId = 80123;

        xtraCard.setXtraCardNbr(cardNum1);
        xtraCard.setCustId(custId);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-20)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(999912345);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(custId);
        customer.setGndrCd("M");
        customer.setFirstName("Mark");
        customer.setLastName("McDowell");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(custId);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("test123@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(custId);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(custId);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("1128 Tower Ln");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("Bensenville");
        customerAddress.setStCd("IL");
        customerAddress.setZipCd("60106");
        customerDao.createCustomerAddress(customerAddress);
	}
	
	public void createUserWithXtraCardNbrForIBotta(int cardNum1) throws ParseException, InterruptedException{
		String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
        // int cardNum1 = 99991234;
        int custId = 80123;

        xtraCard.setXtraCardNbr(cardNum1);
        xtraCard.setCustId(custId);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-20)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(999912345);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(custId);
        customer.setGndrCd("M");
        customer.setFirstName("Mark");
        customer.setLastName("McDowell");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(custId);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("emailcvs17573@gmail.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(custId);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(401);
        customerPhone.setPhonePfxNbr(771);
        customerPhone.setPhoneSfxNbr(8573);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(custId);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("1128 Tower Ln");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("Bensenville");
        customerAddress.setStCd("IL");
        customerAddress.setZipCd("60106");
        customerDao.createCustomerAddress(customerAddress);
	}
	
	/**
     * Delete Test Data for Care Pass Scenario
     */
    public void deleteGetCustCarepassEnrollmentTestData() {
	  /*
	    Purge All Test Cards
	  */
        List<Integer> xtraCardNbrList = Arrays.asList(99991234, 99991230);
        List<Integer> custIdList = Arrays.asList(80123);

        customerDao.deleteCustomers(custIdList);
        xtraCardDao.deleteXtraCards(xtraCardNbrList);
        carePassDao.deleteCarePass(xtraCardNbrList);
    }
    
    /**
     * Delete Test Data for Care Pass Scenario
     */
    public void deleteIBOttaTestData() {
	  /*
	    Purge All Test Cards
	  */
        List<Integer> xtraCardNbrList = Arrays.asList(48720001);
        List<Integer> custIdList = Arrays.asList(80123);

        customerDao.deleteCustomers(custIdList);
        xtraCardDao.deleteXtraCards(xtraCardNbrList);
        carePassDao.deleteCarePass(xtraCardNbrList);
    }
    
    public Integer getEncodedExtraCardNumber(int cardNum1) throws ParseException, InterruptedException{
    	
    	return 0;
    }
}
