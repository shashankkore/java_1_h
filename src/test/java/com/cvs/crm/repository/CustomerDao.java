package com.cvs.crm.repository;

import com.cvs.crm.model.data.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Thread.sleep;

import java.sql.Types;

@Repository
@Slf4j
public class CustomerDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    /**
     * Create Customer
     */
    public void createCustomer(Customer customer) {
        // Create new Customer Record
        String customerSql =
                "INSERT INTO CUSTOMER\n" +
                        "(\n" +
                        "CUST_ID,\n" +
                        "GNDR_CD,\n" +
                        "FIRST_NAME,\n" +
                        "LAST_NAME\n" +
                        ")\n" +
                        "values\n" +
                        "(\n" +
                        ":CUST_ID,\n" +
                        ":GNDR_CD,\n" +
                        ":FIRST_NAME,\n" +
                        ":LAST_NAME\n" +
                        ")";


        MapSqlParameterSource custParms = new MapSqlParameterSource();

        custParms.addValue("CUST_ID", customer.getCustId(), Types.INTEGER);
        custParms.addValue("GNDR_CD", customer.getGndrCd(), Types.VARCHAR);
        custParms.addValue("FIRST_NAME", customer.getFirstName(), Types.VARCHAR);
        custParms.addValue("LAST_NAME", customer.getLastName(), Types.VARCHAR);

        int custRowCnt = jdbcTemplate.update(customerSql, custParms);
        log.info("Total Number of records inserted into CUSTOMER table : [{}]", custRowCnt);
    }

    public void updateCustomer(Customer customer) {
        // TODO Auto-generated method stub
        String customerUpsql =
                "UPDATE CUSTOMER\n" +
                        " SET MIDDLE_INITIAL_TXT=\n" +
                        "    :MIDDLE_INITIAL_TXT,\n" +
                        "    BIRTH_DT=\n" +
                        "    :BIRTH_DT\n" +
                        "WHERE CUST_ID=\n" +
                        " :CUST_ID\n";

        MapSqlParameterSource cUpParms = new MapSqlParameterSource();

        cUpParms.addValue("CUST_ID", customer.getCustId(), Types.INTEGER);
        cUpParms.addValue("MIDDLE_INITIAL_TXT", customer.getMiddleInitialTxt(), Types.VARCHAR);
        cUpParms.addValue("BIRTH_DT", customer.getBirthDt(), Types.DATE);
        int customerUpRowCnt = jdbcTemplate.update(customerUpsql, cUpParms);

        log.info("Total Number of Customers Updated into Customer table : [{}]", customerUpRowCnt);
    }

    /**
     * Create Customer Email
     */
    public void createCustomerEmail(CustomerEmail customerEmail) {
        // Create new Customer Email Record
        String customerEmailSql =
                "INSERT INTO CUSTOMER_EMAIL\n" +
                        "(\n" +
                        "CUST_ID,\n" +
                        "EMAIL_ADDR_TYPE_CD,\n" +
                        "EMAIL_PREF_SEQ_NBR,\n" +
                        "EMAIL_ADDR_TXT,\n" +
                        "EMAIL_STATUS_CD\n" +
                        ")\n" +
                        "VALUES\n" +
                        "(\n" +
                        ":CUST_ID,\n" +
                        ":EMAIL_ADDR_TYPE_CD,\n" +
                        ":EMAIL_PREF_SEQ_NBR,\n" +
                        ":EMAIL_ADDR_TXT,\n" +
                        ":EMAIL_STATUS_CD\n" +
                        ")";


        MapSqlParameterSource custEmailParms = new MapSqlParameterSource();

        custEmailParms.addValue("CUST_ID", customerEmail.getCustId(), Types.INTEGER);
        custEmailParms.addValue("EMAIL_ADDR_TYPE_CD", customerEmail.getEmailAddrTypeCd(), Types.VARCHAR);
        custEmailParms.addValue("EMAIL_PREF_SEQ_NBR", customerEmail.getEmailPrefSeqNbr(), Types.INTEGER);
        custEmailParms.addValue("EMAIL_ADDR_TXT", customerEmail.getEmailAddrTxt(), Types.VARCHAR);
        custEmailParms.addValue("EMAIL_STATUS_CD", customerEmail.getEmailStatusCd(), Types.VARCHAR);
        int custEmailRowCnt = jdbcTemplate.update(customerEmailSql, custEmailParms);
        log.info("Total Number of Cards inserted into CUSTOMER EMAIL table : [{}]", custEmailRowCnt);
    }

    /**
     * Create Customer Phone
     */
    public void createCustomerPhone(CustomerPhone customerPhone) {
        // Create new Customer Phone Record
        String customerPhoneSql =
                "INSERT INTO CUSTOMER_PHONE\n" +
                        "(\n" +
                        "CUST_ID,\n" +
                        "PHONE_TYPE_CD,\n" +
                        "PHONE_PREF_SEQ_NBR,\n" +
                        "PHONE_AREA_CD_NBR,\n" +
                        "PHONE_PFX_NBR,\n" +
                        "PHONE_SFX_NBR\n" +
                        ")\n" +
                        "VALUES\n" +
                        "(\n" +
                        ":CUST_ID,\n" +
                        ":PHONE_TYPE_CD,\n" +
                        ":PHONE_PREF_SEQ_NBR,\n" +
                        ":PHONE_AREA_CD_NBR,\n" +
                        ":PHONE_PFX_NBR,\n" +
                        ":PHONE_SFX_NBR\n" +
                        ")";


        MapSqlParameterSource custPhoneParms = new MapSqlParameterSource();

        custPhoneParms.addValue("CUST_ID", customerPhone.getCustId(), Types.INTEGER);
        custPhoneParms.addValue("PHONE_TYPE_CD", customerPhone.getPhoneTypeCd(), Types.VARCHAR);
        custPhoneParms.addValue("PHONE_PREF_SEQ_NBR", customerPhone.getPhonePrefSeqNbr(), Types.INTEGER);
        custPhoneParms.addValue("PHONE_AREA_CD_NBR", customerPhone.getPhoneAreaCdNbr(), Types.INTEGER);
        custPhoneParms.addValue("PHONE_PFX_NBR", customerPhone.getPhonePfxNbr(), Types.INTEGER);
        custPhoneParms.addValue("PHONE_SFX_NBR", customerPhone.getPhoneSfxNbr(), Types.INTEGER);

        int custPhoneRowCnt = jdbcTemplate.update(customerPhoneSql, custPhoneParms);
        log.info("Total Number of Cards inserted into CUSTOMER PHONE table : [{}]", custPhoneRowCnt);
    }

    /**
     * Create Customer Address
     */
    public void createCustomerAddress(CustomerAddress customerAddress) {
        // Create new customerAddress Record
        String customerAddressSql =
                "INSERT INTO CUSTOMER_ADDRESS\n" +
                        "(\n" +
                        "CUST_ID,\n" +
                        "ADDR_TYPE_CD,\n" +
                        "ADDR_PREF_SEQ_NBR,\n" +
                        "ADDR1_TXT,\n" +
                        "ADDR2_TXT,\n" +
                        "CITY_NAME,\n" +
                        "ST_CD,\n" +
                        "ZIP_CD\n" +
                        ")\n" +
                        "values\n" +
                        "(\n" +
                        ":CUST_ID,\n" +
                        ":ADDR_TYPE_CD,\n" +
                        ":ADDR_PREF_SEQ_NBR,\n" +
                        ":ADDR1_TXT,\n" +
                        ":ADDR2_TXT,\n" +
                        ":CITY_NAME,\n" +
                        ":ST_CD,\n" +
                        ":ZIP_CD\n" +
                        ")";


        MapSqlParameterSource custAddrParms = new MapSqlParameterSource();

        custAddrParms.addValue("CUST_ID", customerAddress.getCustId(), Types.INTEGER);
        custAddrParms.addValue("ADDR_TYPE_CD", customerAddress.getAddrTypeCd(), Types.VARCHAR);
        custAddrParms.addValue("ADDR_PREF_SEQ_NBR", customerAddress.getAddrPrefSeqNbr(), Types.INTEGER);
        custAddrParms.addValue("ADDR1_TXT", customerAddress.getAddr1Txt(), Types.VARCHAR);
        custAddrParms.addValue("ADDR2_TXT", customerAddress.getAddr2Txt(), Types.VARCHAR);
        custAddrParms.addValue("CITY_NAME", customerAddress.getCityName(), Types.VARCHAR);
        custAddrParms.addValue("ST_CD", customerAddress.getStCd(), Types.VARCHAR);
        custAddrParms.addValue("ZIP_CD", customerAddress.getZipCd(), Types.VARCHAR);

        int custAddrRowCnt = jdbcTemplate.update(customerAddressSql, custAddrParms);
        log.info("Total Number of Cards inserted into CUSTOMER ADDRESS table : [{}]", custAddrRowCnt);
        try {
            sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create Customer Opt
     */
    public void createCustomerOpt(CustomerOpt customerOpt) {
        String customerOptSql =
                "INSERT INTO CUSTOMER_OPT\n" +
                        "(\n" +
                        "CUST_ID,\n" +
                        "OPT_TYPE_CD,\n" +
                        "OPT_CD,\n" +
                        "OPT_SRC_CD,\n" +
                        "LAST_UPDT_SRC_CD,\n" +
                        "LAST_UPDT_DT,\n" +
                        "LAST_UPDT_BY_ID\n" +
                        ")\n" +
                        "values\n" +
                        "(\n" +
                        ":CUST_ID,\n" +
                        ":OPT_TYPE_CD,\n" +
                        ":OPT_CD,\n" +
                        ":OPT_SRC_CD,\n" +
                        ":LAST_UPDT_SRC_CD,\n" +
                        ":LAST_UPDT_DT,\n" +
                        ":LAST_UPDT_BY_ID\n" +
                        ")";


        MapSqlParameterSource custOptParms = new MapSqlParameterSource();

        custOptParms.addValue("CUST_ID", customerOpt.getCustId(), Types.INTEGER);
        custOptParms.addValue("OPT_TYPE_CD", customerOpt.getOptTypeCd(), Types.VARCHAR);
        custOptParms.addValue("OPT_CD", customerOpt.getOptCd(), Types.VARCHAR);
        custOptParms.addValue("OPT_SRC_CD", customerOpt.getOptSrcCd(), Types.VARCHAR);
        custOptParms.addValue("LAST_UPDT_SRC_CD", customerOpt.getLastUpdtSrcCd(), Types.VARCHAR);
        custOptParms.addValue("LAST_UPDT_DT", customerOpt.getLastUpdtDt(), Types.DATE);
        custOptParms.addValue("LAST_UPDT_BY_ID", customerOpt.getLastUpdtById(), Types.VARCHAR);


        int custOptRowCnt = jdbcTemplate.update(customerOptSql, custOptParms);
        log.info("Total Number of Cards inserted into CUSTOMER OPT table : [{}]", custOptRowCnt);
    }


    public String selectCustomerOptCd(CustomerOpt customerOpt) {
        String customerOptSelOptCdsql =
                "SELECT OPT_CD\n" +
                        "FROM CUSTOMER_OPT\n" +
                        "WHERE CUST_ID=\n" +
                        " :CUST_ID\n";

        MapSqlParameterSource coSeloptcdParms = new MapSqlParameterSource();
        coSeloptcdParms.addValue("CUST_ID", customerOpt.getCustId(), Types.INTEGER);
        return (String) jdbcTemplate.queryForObject(customerOptSelOptCdsql, coSeloptcdParms, String.class);
    }

    public String selectCustomerOptTypCd(CustomerOpt customerOpt) {
        String customerOptSelOptTypCdsql =
                "SELECT OPT_TYPE_CD\n" +
                        "FROM CUSTOMER_OPT\n" +
                        "WHERE CUST_ID=\n" +
                        " :CUST_ID\n";

        MapSqlParameterSource coSeloptTypcdParms = new MapSqlParameterSource();
        coSeloptTypcdParms.addValue("CUST_ID", customerOpt.getCustId(), Types.INTEGER);
        return (String) jdbcTemplate.queryForObject(customerOptSelOptTypCdsql, coSeloptTypcdParms, String.class);
    }

    public String selectCustomerOptUpdtSrcCd(CustomerOpt customerOpt) {
        String customerOptSelLastUpdtSrcCdsql =
                "SELECT LAST_UPDT_SRC_CD\n" +
                        "FROM CUSTOMER_OPT\n" +
                        "WHERE CUST_ID=\n" +
                        " :CUST_ID\n";

        MapSqlParameterSource coSellastUpdtSrcCdParms = new MapSqlParameterSource();
        coSellastUpdtSrcCdParms.addValue("CUST_ID", customerOpt.getCustId(), Types.INTEGER);
        return (String) jdbcTemplate.queryForObject(customerOptSelLastUpdtSrcCdsql, coSellastUpdtSrcCdParms, String.class);
    }

    /**
     * Delete Test Cards
     */
    public void deleteCustomers(List<Integer> pCustIdList) {
        // Delete Customer Records
        String custSql = "DELETE FROM CUSTOMER WHERE CUST_ID IN (:CUST_ID)";

        Map custParms = Collections.singletonMap("CUST_ID", pCustIdList);
        int custDel = jdbcTemplate.update(custSql, custParms);

        log.info("Total Number of records deleted for table CUSTOMER : [{}] ", custDel);

        // Delete Customer Phone Records
        String custPhSql = "DELETE FROM CUSTOMER_PHONE WHERE CUST_ID IN (:CUST_ID)";

        Map custPhParms = Collections.singletonMap("CUST_ID", pCustIdList);
        int custPhDel = jdbcTemplate.update(custPhSql, custPhParms);

        log.info("Total Number of records deleted for table CUSTOMER_PHONE : [{}] ", custPhDel);

        // Delete Customer Email Records
        String custEmSql = "DELETE FROM CUSTOMER_EMAIL WHERE CUST_ID IN (:CUST_ID)";

        Map custEmParms = Collections.singletonMap("CUST_ID", pCustIdList);
        int custEmDel = jdbcTemplate.update(custEmSql, custEmParms);

        log.info("Total Number of records deleted for table CUSTOMER_EMAIL : [{}] ", custEmDel);


        // Delete Customer Opt Records
        String custOptSql = "DELETE FROM CUSTOMER_OPT WHERE CUST_ID IN (:CUST_ID)";

        Map custOptParms = Collections.singletonMap("CUST_ID", pCustIdList);
        int custOptdel = jdbcTemplate.update(custOptSql, custOptParms);

        log.info("Total Number of records deleted for table CUSTOMER_OPT : [{}] ", custOptdel);

        // Delete Customer Address Records
        String custAddrSql = "DELETE FROM CUSTOMER_ADDRESS WHERE CUST_ID IN (:CUST_ID)";

        Map custAddrParms = Collections.singletonMap("CUST_ID", pCustIdList);
        int custAddrdel = jdbcTemplate.update(custAddrSql, custAddrParms);

        log.info("Total Number of records deleted for table CUSTOMER_ADDRESS : [{}] ", custAddrdel);
    }
    
    
    public List<Map<String, Object>> selectDataFromCustomerTable(List<Integer> pCustIdList, String tableName) {
    	int custId = pCustIdList.get(0);
    	String customerRecord = "";
    	if(tableName.toLowerCase().contains("phone")) {
    		customerRecord ="SELECT * FROM "+tableName+" WHERE CUST_ID = "+custId+" order by PHONE_PREF_SEQ_NBR";}
    	else { customerRecord = "SELECT * FROM "+tableName+" WHERE CUST_ID = "+custId+" order by LAST_UPDT_DT";}
        
    	MapSqlParameterSource mvSelParms = new MapSqlParameterSource();
        mvSelParms.addValue("CUST_ID", custId, Types.INTEGER);
        return jdbcTemplate.queryForList(customerRecord, mvSelParms);
    }
    
    public Integer getCountOfRecordsInCustomerTable(String tableName, int customerId) {
        String recordCount =
                "SELECT COUNT(*) FROM "+tableName+" WHERE CUST_ID IN (:CUST_ID)";
        MapSqlParameterSource mvSelParms = new MapSqlParameterSource();
        mvSelParms.addValue("CUST_ID", customerId, Types.INTEGER);
        return (Integer) jdbcTemplate.queryForObject(recordCount, mvSelParms, Integer.class);
    }
    
    public List<Map<String, Object>> selectDataFromCustomerTable(List<Integer> pCustIdList, String tableName, String seqNbr) {
        String customerRecord =
                "SELECT * FROM "+tableName+" WHERE CUST_ID IN (:CUST_ID) and ";
        Map custParms = Collections.singletonMap("CUST_ID", pCustIdList);
        return jdbcTemplate.queryForList(customerRecord, custParms);
    }

    public List<CustomerAddress> getDataFromCustomerAddressTable(int xtraCardNumber) {
        String sql =
                "select * from customer_address " +
                        "where cust_id = ( select cust_id from xtra_card " +
                            "where xtra_card_nbr = " +
                        ":XTRA_CARD_NBR) ORDER BY addr_pref_seq_nbr";

        MapSqlParameterSource coSellastUpdtSrcCdParms = new MapSqlParameterSource();
        coSellastUpdtSrcCdParms.addValue("XTRA_CARD_NBR", xtraCardNumber, Types.INTEGER);
        return jdbcTemplate.query(sql, coSellastUpdtSrcCdParms,
                new BeanPropertyRowMapper<>(CustomerAddress.class));
    }

    public List<CustomerEmail> getDataFromCustomerEmailTable(int xtraCardNumber) {
        String sql =
                "select * from customer_email " +
                        "where cust_id = ( select cust_id from xtra_card " +
                        "where xtra_card_nbr = " +
                        ":XTRA_CARD_NBR) ORDER BY email_pref_seq_nbr";

        MapSqlParameterSource coSellastUpdtSrcCdParms = new MapSqlParameterSource();
        coSellastUpdtSrcCdParms.addValue("XTRA_CARD_NBR", xtraCardNumber, Types.INTEGER);
        return jdbcTemplate.query(sql, coSellastUpdtSrcCdParms,
                new BeanPropertyRowMapper<>(CustomerEmail.class));
    }

    public List<CustomerPhone> getDataFromCustomerPhoneTable(int xtraCardNumber) {
        String sql =
                "select * from customer_phone " +
                        "where cust_id = ( select cust_id from xtra_card " +
                        "where xtra_card_nbr = " +
                        ":XTRA_CARD_NBR) ORDER BY phone_pref_seq_nbr";

        MapSqlParameterSource coSellastUpdtSrcCdParms = new MapSqlParameterSource();
        coSellastUpdtSrcCdParms.addValue("XTRA_CARD_NBR", xtraCardNumber, Types.INTEGER);
        return jdbcTemplate.query(sql, coSellastUpdtSrcCdParms,
                new BeanPropertyRowMapper<>(CustomerPhone.class));
    }

    public List<Customer> getDataFromCustomerTable(int xtraCardNumber) {
        String sql =
                "select * from customer " +
                        "where cust_id = ( select cust_id from xtra_card " +
                        "where xtra_card_nbr = " +
                        ":XTRA_CARD_NBR)";

        MapSqlParameterSource coSellastUpdtSrcCdParms = new MapSqlParameterSource();
        coSellastUpdtSrcCdParms.addValue("XTRA_CARD_NBR", xtraCardNumber, Types.INTEGER);
        return jdbcTemplate.query(sql, coSellastUpdtSrcCdParms,
                new BeanPropertyRowMapper<>(Customer.class));
    }


    public void updateCustomerAddress(String addr2Txt, int xtraCardNumber) {
        String sql =
                "UPDATE CUSTOMER_ADDRESS SET ADDR2_TXT=:ADDR2_TXT WHERE cust_id = " +
                        "( select cust_id from xtra_card where xtra_card_nbr = :XTRA_CARD_NBR)";
        MapSqlParameterSource parms = new MapSqlParameterSource();
        parms.addValue("XTRA_CARD_NBR", xtraCardNumber, Types.INTEGER);
        parms.addValue("ADDR2_TXT", addr2Txt, Types.VARCHAR);

        int count = jdbcTemplate.update(sql, parms);
        log.info("Total Number of Cards updated into CUSTOMER ADDRESS table : [{}]", count);
    }

    public void updateCustomerEmail(String email, int xtraCardNumber) {
        String sql =
                "UPDATE CUSTOMER_EMAIL SET EMAIL_ADDR_TXT=:EMAIL_ADDR_TXT WHERE cust_id = " +
                        "( select cust_id from xtra_card where xtra_card_nbr = :XTRA_CARD_NBR)";
        MapSqlParameterSource parms = new MapSqlParameterSource();
        parms.addValue("XTRA_CARD_NBR", xtraCardNumber, Types.INTEGER);
        parms.addValue("EMAIL_ADDR_TXT", email, Types.VARCHAR);

        int count = jdbcTemplate.update(sql, parms);
        log.info("Total Number of Cards updated into CUSTOMER EMAIL table : [{}]", count);
    }

    public void updateCustomerPhone(String phone, int xtraCardNumber) {
        int areaCode = Integer.parseInt(phone.substring(0,3));
        int prefix = Integer.parseInt(phone.substring(3,6));
        int suffix = Integer.parseInt(phone.substring(6));

        String sql =
                "UPDATE CUSTOMER_PHONE SET phone_area_cd_nbr=:AREA_CODE, phone_pfx_nbr=:PRE_FIX, " +
                        "phone_sfx_nbr=:SUFFIX WHERE cust_id = " +
                        "( select cust_id from xtra_card where xtra_card_nbr = :XTRA_CARD_NBR)";
        MapSqlParameterSource parms = new MapSqlParameterSource();
        parms.addValue("XTRA_CARD_NBR", xtraCardNumber, Types.INTEGER);
        parms.addValue("AREA_CODE", areaCode, Types.INTEGER);
        parms.addValue("PRE_FIX", prefix, Types.INTEGER);
        parms.addValue("SUFFIX", suffix, Types.INTEGER);

        int count = jdbcTemplate.update(sql, parms);
        log.info("Total Number of Cards updated into CUSTOMER PHONE table : [{}]", count);
    }

    public void updateCustomerDOB(String dob, int xtraCardNumber) {
    	SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
    	SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
        String sql;
        try {
        	Date inputdate = inputFormatter.parse(dob);
        	Date  date =formatter.parse(formatter.format(inputdate));
            sql = "UPDATE CUSTOMER SET BIRTH_DT=:DOB WHERE cust_id = " +
                            "( select cust_id from xtra_card where xtra_card_nbr = :XTRA_CARD_NBR)";
            MapSqlParameterSource parms = new MapSqlParameterSource();
            parms.addValue("XTRA_CARD_NBR", xtraCardNumber, Types.INTEGER);
            parms.addValue("DOB", date, Types.DATE);
            int count = jdbcTemplate.update(sql, parms);
            log.info("Total Number of Cards updated into CUSTOMER PHONE table : [{}]", count);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomerAddress(int xtraCardNumber, int seqNum) {
        String sql = "DELETE FROM CUSTOMER_ADDRESS WHERE CUST_ID  = " +
                                "( select cust_id from xtra_card where xtra_card_nbr = :XTRA_CARD_NBR) " +
                                " AND ADDR_PREF_SEQ_NBR = :SEQ_NBR";

        MapSqlParameterSource parms = new MapSqlParameterSource();
        parms.addValue("XTRA_CARD_NBR", xtraCardNumber, Types.INTEGER);
        parms.addValue("SEQ_NBR", seqNum, Types.INTEGER);
        int count = jdbcTemplate.update(sql, parms);

        log.info("Total Number of records deleted for table CUSTOMER_ADDRESS : [{}] ", count);
    }

    public void deleteCustomerPhoneNumber(int xtraCardNumber, int seqNum) {
        String sql = "DELETE FROM CUSTOMER_PHONE WHERE CUST_ID  = " +
                "( select cust_id from xtra_card where xtra_card_nbr = :XTRA_CARD_NBR) " +
                " AND PHONE_PREF_SEQ_NBR = :SEQ_NBR";

        MapSqlParameterSource parms = new MapSqlParameterSource();
        parms.addValue("XTRA_CARD_NBR", xtraCardNumber, Types.INTEGER);
        parms.addValue("SEQ_NBR", seqNum, Types.INTEGER);
        int count = jdbcTemplate.update(sql, parms);

        log.info("Total Number of records deleted for table CUSTOMER_PHONE : [{}] ", count);
    }

    public void deleteCustomerEmailAddress(int xtraCardNumber, int seqNum) {
        String sql = "DELETE FROM CUSTOMER_EMAIL WHERE CUST_ID  = " +
                "( select cust_id from xtra_card where xtra_card_nbr = :XTRA_CARD_NBR) " +
                " AND EMAIL_PREF_SEQ_NBR = :SEQ_NBR";

        MapSqlParameterSource parms = new MapSqlParameterSource();
        parms.addValue("XTRA_CARD_NBR", xtraCardNumber, Types.INTEGER);
        parms.addValue("SEQ_NBR", seqNum, Types.INTEGER);
        int count = jdbcTemplate.update(sql, parms);

        log.info("Total Number of records deleted for table CUSTOMER_EMAIL : [{}] ", count);
    }

    public List<Customer> getData_Customer_table(String xtraCardNbr, String columnName) {
        String xtraCardsql =
                "SELECT "+ columnName + " FROM CUSTOMER WHERE CUST_ID IN " +
                        "(SELECT CUST_ID FROM XTRA_CARD WHERE XTRA_CARD_NBR = :XTRA_CARD_NBR)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("XTRA_CARD_NBR", xtraCardNbr, Types.INTEGER);
        return jdbcTemplate.query(xtraCardsql, mapSqlParameterSource, new BeanPropertyRowMapper<>(Customer.class));
    }

    public List<CustomerEmail> getData_CustomerEmail_table(String xtraCardNbr, String columnName) {
        String xtraCardsql =
                "SELECT "+ columnName + " FROM CUSTOMER_EMAIL WHERE CUST_ID IN " +
                        "(SELECT CUST_ID FROM XTRA_CARD WHERE XTRA_CARD_NBR = :XTRA_CARD_NBR)" +
                        "ORDER BY EMAIL_PREF_SEQ_NBR";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("XTRA_CARD_NBR", xtraCardNbr, Types.INTEGER);
        return jdbcTemplate.query(xtraCardsql, mapSqlParameterSource, new BeanPropertyRowMapper<>(CustomerEmail.class));
    }

    public List<CustomerAddress> getData_CustomerAddress_table(String xtraCardNbr, String columnName) {
        String xtraCardsql =
                "SELECT "+ columnName + " FROM CUSTOMER_ADDRESS WHERE CUST_ID IN " +
                        "(SELECT CUST_ID FROM XTRA_CARD WHERE XTRA_CARD_NBR = :XTRA_CARD_NBR)" +
                        "ORDER BY ADDR_PREF_SEQ_NBR";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("XTRA_CARD_NBR", xtraCardNbr, Types.INTEGER);
        return jdbcTemplate.query(xtraCardsql, mapSqlParameterSource, new BeanPropertyRowMapper<>(CustomerAddress.class));
    }

    public List<CustomerPhone> getData_CustomerPhone_table(String xtraCardNbr, String columnName) {
        String xtraCardsql =
                "SELECT "+ columnName + " FROM CUSTOMER_PHONE WHERE CUST_ID IN " +
                        "(SELECT CUST_ID FROM XTRA_CARD WHERE XTRA_CARD_NBR = :XTRA_CARD_NBR)" +
                        "ORDER BY PHONE_PREF_SEQ_NBR";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("XTRA_CARD_NBR", xtraCardNbr, Types.INTEGER);
        return jdbcTemplate.query(xtraCardsql, mapSqlParameterSource, new BeanPropertyRowMapper<>(CustomerPhone.class));
    }

    public List<CustomerOpt> getData_CustomerOpt_table(String xtraCardNbr, String columnName) {
        String xtraCardsql =
                "SELECT "+ columnName + " FROM CUSTOMER_OPT WHERE CUST_ID IN " +
                        "(SELECT CUST_ID FROM XTRA_CARD WHERE XTRA_CARD_NBR = :XTRA_CARD_NBR)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("XTRA_CARD_NBR", xtraCardNbr, Types.INTEGER);
        return jdbcTemplate.query(xtraCardsql, mapSqlParameterSource, new BeanPropertyRowMapper<>(CustomerOpt.class));
    }

    public List<CustomerEmail> getData_CustomerEmail_table_with_PrefSeqNbr(String xtraCardNbr, int prefSeqNbr) {
        String xtraCardsql =
                "SELECT * FROM CUSTOMER_EMAIL WHERE CUST_ID IN " +
                        "(SELECT CUST_ID FROM XTRA_CARD WHERE XTRA_CARD_NBR = :XTRA_CARD_NBR)" +
                        "AND EMAIL_PREF_SEQ_NBR = :PREF_SEQ_NBR";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("XTRA_CARD_NBR", xtraCardNbr, Types.INTEGER);
        mapSqlParameterSource.addValue("PREF_SEQ_NBR", prefSeqNbr, Types.INTEGER);
        return jdbcTemplate.query(xtraCardsql, mapSqlParameterSource, new BeanPropertyRowMapper<>(CustomerEmail.class));
    }

    public List<CustomerAddress> getData_CustomerAddress_table_with_PrefSeqNbr(String xtraCardNbr, int prefSeqNbr) {
        String xtraCardsql =
                "SELECT * FROM CUSTOMER_ADDRESS WHERE CUST_ID IN " +
                        "(SELECT CUST_ID FROM XTRA_CARD WHERE XTRA_CARD_NBR = :XTRA_CARD_NBR)" +
                        "AND ADDR_PREF_SEQ_NBR = :PREF_SEQ_NBR";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("XTRA_CARD_NBR", xtraCardNbr, Types.INTEGER);
        mapSqlParameterSource.addValue("PREF_SEQ_NBR", prefSeqNbr, Types.INTEGER);
        return jdbcTemplate.query(xtraCardsql, mapSqlParameterSource, new BeanPropertyRowMapper<>(CustomerAddress.class));
    }

    public List<CustomerPhone> getData_CustomerPhone_table_with_PrefSeqNbr(String xtraCardNbr, int prefSeqNbr) {
        String xtraCardsql =
                "SELECT * FROM CUSTOMER_PHONE WHERE CUST_ID IN " +
                        "(SELECT CUST_ID FROM XTRA_CARD WHERE XTRA_CARD_NBR = :XTRA_CARD_NBR)" +
                        "AND PHONE_PREF_SEQ_NBR = :PREF_SEQ_NBR";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("XTRA_CARD_NBR", xtraCardNbr, Types.INTEGER);
        mapSqlParameterSource.addValue("PREF_SEQ_NBR", prefSeqNbr, Types.INTEGER);
        return jdbcTemplate.query(xtraCardsql, mapSqlParameterSource, new BeanPropertyRowMapper<>(CustomerPhone.class));
    }

    public void delete_CustomerEmail_with_PrefSeqNbr(int xtraCardNumber, int prefSeqNum) {
        String sql = "DELETE FROM CUSTOMER_EMAIL WHERE CUST_ID  IN " +
                "(SELECT CUST_ID FROM XTRA_CARD WHERE XTRA_CARD_NBR = :XTRA_CARD_NBR)" +
                "AND EMAIL_PREF_SEQ_NBR = :PREF_SEQ_NBR";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("XTRA_CARD_NBR", xtraCardNumber, Types.INTEGER);
        mapSqlParameterSource.addValue("PREF_SEQ_NBR", prefSeqNum, Types.INTEGER);
        int count = jdbcTemplate.update(sql, mapSqlParameterSource);
        log.info("Total Number of records deleted for table CUSTOMER_EMAIL : [{}] ", count);
    }

    public void delete_CustomerPhone_with_PrefSeqNbr(int xtraCardNumber, int prefSeqNum) {
        String sql = "DELETE FROM CUSTOMER_PHONE WHERE CUST_ID  IN " +
                "(SELECT CUST_ID FROM XTRA_CARD WHERE XTRA_CARD_NBR = :XTRA_CARD_NBR)" +
                "AND PHONE_PREF_SEQ_NBR = :PREF_SEQ_NBR";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("XTRA_CARD_NBR", xtraCardNumber, Types.INTEGER);
        mapSqlParameterSource.addValue("PREF_SEQ_NBR", prefSeqNum, Types.INTEGER);
        int count = jdbcTemplate.update(sql, mapSqlParameterSource);
        log.info("Total Number of records deleted for table CUSTOMER_PHONE : [{}] ", count);
    }

    public void createCustomer_NEW(Customer customer) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(defaultTransactionDefinition);

        // Create new record in Customer table
        String customerSql =
                "Insert into CUSTOMER (" +
                    "CUST_ID, GNDR_CD, FIRST_NAME, MIDDLE_INITIAL_TXT, LAST_NAME, SUR_NAME, PFX_TXT, " +
                    "BIRTH_DT, LAST_UPDT_SRC_CD, LAST_UPDT_DT, LAST_UPDT_BY_ID, FIRST_UPDT_BY_ID) \n" +
                "values (" +
                    ":CUST_ID, :GNDR_CD, :FIRST_NAME, :MIDDLE_INITIAL_TXT, :LAST_NAME, :SUR_NAME, :PFX_TXT, " +
                    ":BIRTH_DT, :LAST_UPDT_SRC_CD, :LAST_UPDT_DT, :LAST_UPDT_BY_ID, :FIRST_UPDT_BY_ID)";

        MapSqlParameterSource xcParms = new MapSqlParameterSource();
        xcParms.addValue("CUST_ID", customer.getCustId(), Types.INTEGER);
        xcParms.addValue("GNDR_CD", customer.getGndrCd(), Types.CHAR);
        xcParms.addValue("FIRST_NAME", customer.getFirstName(), Types.VARCHAR);
        xcParms.addValue("MIDDLE_INITIAL_TXT", customer.getMiddleInitialTxt(), Types.CHAR);
        xcParms.addValue("LAST_NAME", customer.getLastName(), Types.VARCHAR);
        xcParms.addValue("SUR_NAME", customer.getSurName(), Types.VARCHAR);
        xcParms.addValue("PFX_TXT", customer.getPfxTxt(), Types.VARCHAR);
        xcParms.addValue("BIRTH_DT", customer.getBirthDt(), Types.DATE);
        xcParms.addValue("LAST_UPDT_SRC_CD", customer.getLastUpdtSrcCd(), Types.VARCHAR);
        xcParms.addValue("LAST_UPDT_DT", customer.getLastUpdtDt(), Types.DATE);
        xcParms.addValue("LAST_UPDT_BY_ID", customer.getLastUpdtById(), Types.VARCHAR);
        xcParms.addValue("FIRST_UPDT_BY_ID", customer.getFirstUpdtById(), Types.VARCHAR);

        try {
            int xtraCardRowCnt = jdbcTemplate.update(customerSql, xcParms);
            platformTransactionManager.commit(transactionStatus);
            log.info("\nCards inserted into CUSTOMER table : [{}], cust_id : [{}]",
                    xtraCardRowCnt, customer.getCustId());
        } catch (Exception e) {
            platformTransactionManager.rollback(transactionStatus);
            log.info("Exception - " + e);
            throw e;
        }
    }

    public void createCustomerAddress_NEW(CustomerAddress customerAddress) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(defaultTransactionDefinition);

        // Create new record in CustomerAddress table
        String customerAddressSql =
                "Insert into CUSTOMER_ADDRESS (" +
                        "CUST_ID, ADDR_TYPE_CD, ADDR_PREF_SEQ_NBR, ADDR1_TXT, ADDR2_TXT, CITY_NAME, " +
                        "ST_CD, ZIP_CD, ADDR_QLTY_CD, LAST_UPDT_SRC_CD, LAST_UPDT_DT, LAST_UPDT_BY_ID) \n" +
                        "values (" +
                        ":CUST_ID, :ADDR_TYPE_CD, :ADDR_PREF_SEQ_NBR, :ADDR1_TXT, :ADDR2_TXT, :CITY_NAME, " +
                        ":ST_CD, :ZIP_CD, :ADDR_QLTY_CD, :LAST_UPDT_SRC_CD, :LAST_UPDT_DT, :LAST_UPDT_BY_ID)";

        MapSqlParameterSource xcParms = new MapSqlParameterSource();
        xcParms.addValue("CUST_ID", customerAddress.getCustId(), Types.INTEGER);
        xcParms.addValue("ADDR_TYPE_CD", customerAddress.getAddrTypeCd(), Types.VARCHAR);
        xcParms.addValue("ADDR_PREF_SEQ_NBR", customerAddress.getAddrPrefSeqNbr(), Types.INTEGER);
        xcParms.addValue("ADDR1_TXT", customerAddress.getAddr1Txt(), Types.VARCHAR);
        xcParms.addValue("ADDR2_TXT", customerAddress.getAddr2Txt(), Types.VARCHAR);
        xcParms.addValue("CITY_NAME", customerAddress.getCityName(), Types.VARCHAR);
        xcParms.addValue("ST_CD", customerAddress.getStCd(), Types.VARCHAR);
        xcParms.addValue("ZIP_CD", customerAddress.getZipCd(), Types.VARCHAR);
        xcParms.addValue("ADDR_QLTY_CD", customerAddress.getAddrQltyCd(), Types.CHAR);
        xcParms.addValue("LAST_UPDT_SRC_CD", customerAddress.getLastUpdtSrcCd(), Types.VARCHAR);
        xcParms.addValue("LAST_UPDT_DT", customerAddress.getLastUpdtDt(), Types.DATE);
        xcParms.addValue("LAST_UPDT_BY_ID", customerAddress.getLastUpdtById(), Types.VARCHAR);

        try {
            int xtraCardRowCnt = jdbcTemplate.update(customerAddressSql, xcParms);
            platformTransactionManager.commit(transactionStatus);
            log.info("\nCards inserted into CUSTOMER_ADDRESS table : [{}], cust_id : [{}]",
                    xtraCardRowCnt, customerAddress.getCustId());
        } catch (Exception e) {
            platformTransactionManager.rollback(transactionStatus);
            log.info("Exception - " + e);
            throw e;
        }
    }

    public void createCustomerEmail_NEW(CustomerEmail customerEmail) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(defaultTransactionDefinition);

        // Create new record in CustomerEmail table
        String customerEmailSql =
                "Insert into CUSTOMER_EMAIL (" +
                     "CUST_ID, EMAIL_ADDR_TYPE_CD, EMAIL_PREF_SEQ_NBR, EMAIL_ADDR_TXT, EMAIL_STATUS_CD, LAST_UPDT_SRC_CD, " +
                     "LAST_UPDT_DT, LAST_UPDT_BY_ID, FIRST_UPDT_DT, FIRST_UPDT_SRC_CD, FIRST_UPDT_BY_ID) " +
                "values (" +
                     ":CUST_ID, :EMAIL_ADDR_TYPE_CD, :EMAIL_PREF_SEQ_NBR, :EMAIL_ADDR_TXT, :EMAIL_STATUS_CD, :LAST_UPDT_SRC_CD, " +
                     ":LAST_UPDT_DT, :LAST_UPDT_BY_ID, :FIRST_UPDT_DT, :FIRST_UPDT_SRC_CD, :FIRST_UPDT_BY_ID)";

        MapSqlParameterSource xcParms = new MapSqlParameterSource();
        xcParms.addValue("CUST_ID", customerEmail.getCustId(), Types.INTEGER);
        xcParms.addValue("EMAIL_ADDR_TYPE_CD", customerEmail.getEmailAddrTypeCd(), Types.VARCHAR);
        xcParms.addValue("EMAIL_PREF_SEQ_NBR", customerEmail.getEmailPrefSeqNbr(), Types.INTEGER);
        xcParms.addValue("EMAIL_ADDR_TXT", customerEmail.getEmailAddrTxt(), Types.VARCHAR);
        xcParms.addValue("EMAIL_STATUS_CD", customerEmail.getEmailStatusCd(), Types.VARCHAR);
        xcParms.addValue("LAST_UPDT_SRC_CD", customerEmail.getLastUpdtSrcCd(), Types.VARCHAR);
        xcParms.addValue("LAST_UPDT_DT", customerEmail.getLastUpdtDt(), Types.DATE);
        xcParms.addValue("LAST_UPDT_BY_ID", customerEmail.getLastUpdtById(), Types.VARCHAR);
        xcParms.addValue("FIRST_UPDT_DT", customerEmail.getFirstUpdtDt(), Types.DATE);
        xcParms.addValue("FIRST_UPDT_SRC_CD", customerEmail.getFirstUpdtSrcCd(), Types.VARCHAR);
        xcParms.addValue("FIRST_UPDT_BY_ID", customerEmail.getFirstUpdtById(), Types.VARCHAR);

        try {
            int xtraCardRowCnt = jdbcTemplate.update(customerEmailSql, xcParms);
            platformTransactionManager.commit(transactionStatus);
            log.info("\nCards inserted into CUSTOMER_EMAIL table : [{}], CUST_ID : [{}]",
                    xtraCardRowCnt, customerEmail.getCustId());
        } catch (Exception e) {
            platformTransactionManager.rollback(transactionStatus);
            log.info("Exception - " + e);
            throw e;
        }
    }

    public void createCustomerPhone_NEW(CustomerPhone customerPhone) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(defaultTransactionDefinition);

        // Create new record in CustomerEmail table
        String customerPhoneSql =
                "Insert into Customer_phone (" +
                     "CUST_ID, PHONE_TYPE_CD, PHONE_PREF_SEQ_NBR, PHONE_AREA_CD_NBR, PHONE_PFX_NBR, " +
                     "PHONE_SFX_NBR, LAST_UPDT_SRC_CD, LAST_UPDT_DT, LAST_UPDT_BY_ID) " +
                "values (" +
                     ":CUST_ID, :PHONE_TYPE_CD, :PHONE_PREF_SEQ_NBR, :PHONE_AREA_CD_NBR, :PHONE_PFX_NBR, " +
                     ":PHONE_SFX_NBR, :LAST_UPDT_SRC_CD, :LAST_UPDT_DT, :LAST_UPDT_BY_ID)";

        MapSqlParameterSource xcParms = new MapSqlParameterSource();
        xcParms.addValue("CUST_ID", customerPhone.getCustId(), Types.INTEGER);
        xcParms.addValue("PHONE_TYPE_CD", customerPhone.getPhoneTypeCd(), Types.VARCHAR);
        xcParms.addValue("PHONE_PREF_SEQ_NBR", customerPhone.getPhonePrefSeqNbr(), Types.INTEGER);
        xcParms.addValue("PHONE_AREA_CD_NBR", customerPhone.getPhoneAreaCdNbr(), Types.INTEGER);
        xcParms.addValue("PHONE_PFX_NBR", customerPhone.getPhonePfxNbr(), Types.INTEGER);
        xcParms.addValue("PHONE_SFX_NBR", customerPhone.getPhoneSfxNbr(), Types.INTEGER);
        xcParms.addValue("LAST_UPDT_SRC_CD", customerPhone.getLastUpdtSrcCd(), Types.VARCHAR);
        xcParms.addValue("LAST_UPDT_DT", customerPhone.getLastUpdtDt(), Types.DATE);
        xcParms.addValue("LAST_UPDT_BY_ID", customerPhone.getLastUpdtById(), Types.VARCHAR);

        try {
            int xtraCardRowCnt = jdbcTemplate.update(customerPhoneSql, xcParms);
            platformTransactionManager.commit(transactionStatus);
            log.info("\nCards inserted into CUSTOMER_PHONE table : [{}], CUST_ID : [{}]",
                    xtraCardRowCnt, customerPhone.getCustId());
        } catch (Exception e) {
            platformTransactionManager.rollback(transactionStatus);
            log.info("Exception - " + e);
            throw e;
        }
    }

    public void deleteCustomer_NEW(List<Integer> custId, String tableName) {
        // Delete CUST_ID from tableName
        try {
            String Sql = "DELETE FROM " + tableName + " WHERE CUST_ID IN (:CUST_ID)";
            Map xcParms = Collections.singletonMap("CUST_ID", custId);
            int deleted_count = jdbcTemplate.update(Sql, xcParms);
            log.info("\nDELETED CUST_ID: {} from {} table. Deleted Cust_Id Count : {}.", custId, tableName, deleted_count);
        } catch (Exception e) {
            log.info("Exception - " + e);
            throw e;
        }
    }

    public void delete_customer(List<Integer> custIdList) {
        deleteCustomer_NEW(custIdList, "CUSTOMER");
        deleteCustomer_NEW(custIdList, "CUSTOMER_ADDRESS");
        deleteCustomer_NEW(custIdList, "CUSTOMER_EMAIL");
        deleteCustomer_NEW(custIdList, "CUSTOMER_PHONE");
        deleteCustomer_NEW(custIdList, "CUSTOMER_OPT");
    }
}