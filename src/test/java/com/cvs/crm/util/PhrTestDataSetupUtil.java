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
public class PhrTestDataSetupUtil {
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
        int custId = 80016;
        xtraCard.setXtraCardNbr(cardNum1);
        xtraCard.setCustId(custId);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-20)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr((int) 4879983440501L);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(custId);
        customer.setGndrCd("M");
        customer.setFirstName("Mark");
        customer.setLastName("McDowell");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(custId);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("test12341@CVS.com");
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
	
	public void deletePharmacyHealthRewardsEnrollmentTestData() {
	  	  /*
	  	    Purge All Test Cards
	  	     */
	          List<Integer> xtraCardNbrList = Arrays.asList(298344050);
	          List<Integer> custIdList = Arrays.asList(80016);
//	          List<Integer> cmpgnIdList = Arrays.asList(59726, 64355);
	          List<Integer> ephLinkIdList = Arrays.asList(80017011, 80017012, 80017111, 80017112, 80017113, 80017114, 80017211, 80017212, 80017311, 80017312, 80017313, 80017314, 80017411, 80017412, 80017511, 80017512, 80017611, 80017711, 80017811, 80017911, 80018011, 80018111, 80018211, 80018311, 80018312, 80018411, 80018412);
//	          campaignDao.deleteCampaignRecords(cmpgnIdList, xtraCardNbrList);
	          customerDao.deleteCustomers(custIdList);
//	          hRDao.deleteHRRecords(ephLinkIdList);
	          xtraCardDao.deleteXtraCards(xtraCardNbrList);
	      }
	
	/**
     * Delete Test Data for Care Pass Scenario
     */
    public void deleteGetCustCarepassEnrollmentTestData() {
	  /*
	    Purge All Test Cards
	  */
        List<Integer> xtraCardNbrList = Arrays.asList(98158276);
        List<Integer> custIdList = Arrays.asList(80016);

        customerDao.deleteCustomers(custIdList);
        xtraCardDao.deleteXtraCards(xtraCardNbrList);
        carePassDao.deleteCarePass(xtraCardNbrList);
    }
}
