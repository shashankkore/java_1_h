package com.cvs.crm.repository;

import com.cvs.crm.model.data.*;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Types;
import java.util.*;

@Repository
@Slf4j
public class XtraCardDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    
    private int currentCustId;

    private int getXtraCard(String custId) {
        String query = "SELECT count(*) FROM XTRA_CARD where" +
                "CUST_ID = ?";
        // int count = this.jdbcTemplate.queryForObject(query, new Object[] { custId }, Integer.class);
        return 0;
    }

    /**
     * Create Xtra Card Summary
     */
    public void createXtraCard(XtraCard xtraCard) {
        // Create new Xtra Card Record
        String xtraCardsql =
                "INSERT INTO XTRA_CARD\n" +
                        "(\n" +
                        " XTRA_CARD_NBR,\n" +
                        " CUST_ID,\n" +
                        " CARD_MBR_DT,\n" +
                        " TOT_YTD_SAVE_AMT,\n" +
                        " MKTG_TYPE_CD,\n" +
                        " ENCODED_XTRA_CARD_NBR,\n" +
                        " TOT_LIFETIME_SAVE_AMT\n" +
                        ")\n" +
                        " Values \n" +
                        "(\n" +
                        " :XTRA_CARD_NBR,\n" +
                        " :CUST_ID,\n" +
                        " :CARD_MBR_DT,\n" +
                        " :TOT_YTD_SAVE_AMT,\n" +
                        " :MKTG_TYPE_CD,\n" +
                        " :ENCODED_XTRA_CARD_NBR,\n" +
                        " :TOT_LIFETIME_SAVE_AMT\n" +
                        ")";

        MapSqlParameterSource xcParms = new MapSqlParameterSource();

        xcParms.addValue("XTRA_CARD_NBR", xtraCard.getXtraCardNbr(), Types.INTEGER);
        xcParms.addValue("ENCODED_XTRA_CARD_NBR", xtraCard.getEncodedXtraCardNbr2(), Types.BIGINT);
        xcParms.addValue("CUST_ID", xtraCard.getCustId(), Types.INTEGER);
        currentCustId = xtraCard.getCustId();
        xcParms.addValue("TOT_YTD_SAVE_AMT", xtraCard.getTotYtdSaveAmt(), Types.DOUBLE);
        xcParms.addValue("TOT_LIFETIME_SAVE_AMT", xtraCard.getTotLifetimeSaveAmt(), Types.DOUBLE);
        xcParms.addValue("CARD_MBR_DT", xtraCard.getCardMbrDt(), Types.DATE);
        xcParms.addValue("MKTG_TYPE_CD", xtraCard.getMktgTypeCd(), Types.VARCHAR);
        try {
            int xtraCardRowCnt = jdbcTemplate.update(xtraCardsql, xcParms);
            log.info("Total Number of Cards inserted into XTRA_CARD table : [{}]", xtraCardRowCnt);
        } catch (Exception e) {
            log.warn("The xtra card data already exists: "+xtraCard.getXtraCardNbr());
            e.getMessage();
        }

        String maskedXtraCardsql =
                "INSERT INTO MASKED_XTRA_CARD\n" +
                        "(\n" +
                        " XTRA_CARD_NBR,\n" +
                        " XTRA_CARD_MASK_NBR,\n" +
                        " VISIBLE_IND,\n" +
                        " XTRA_CARD_SHA1_NBR,\n" +
                        " XTRA_CARD_SHA2_NBR\n" +
                        ")\n" +
                        " Values \n" +
                        "(\n" +
                        " :XTRA_CARD_NBR,\n" +
                        " :XTRA_CARD_MASK_NBR,\n" +
                        " :VISIBLE_IND,\n" +
                        " :XTRA_CARD_SHA1_NBR,\n" +
                        " :XTRA_CARD_SHA2_NBR\n" +
                        ")";
        MapSqlParameterSource xcParms2 = new MapSqlParameterSource();

        xcParms2.addValue("XTRA_CARD_NBR", xtraCard.getXtraCardNbr(), Types.INTEGER);
        xcParms2.addValue("XTRA_CARD_MASK_NBR", xtraCard.getXtraCardMaskNbr(), Types.VARCHAR);
        xcParms2.addValue("VISIBLE_IND", "Y", Types.VARCHAR);
        xcParms2.addValue("XTRA_CARD_SHA1_NBR", xtraCard.getXtraCardSh1Nbr(), Types.VARCHAR);
        xcParms2.addValue("XTRA_CARD_SHA2_NBR", xtraCard.getXtraCardSh2Nbr(), Types.VARCHAR);
        try {
            int xtraCardRowCnt2 = jdbcTemplate.update(maskedXtraCardsql, xcParms2);
            log.info("Total Number of Cards inserted into MASKED_XTRA_CARD table : [{}]", xtraCardRowCnt2);
        } catch (Exception e) {
            e.getMessage();
            log.warn("Masked xtra card The data already exists: "+xtraCard.getXtraCardNbr());
        }

    }


    public void createXtraCardChild(XtraCardChild xtraCardChild) {
        // Create new Xtra Card Child Record
        String xtraCardChildsql =
                "INSERT INTO xtra_card_child\n" +
                        "(\n" +
                        " XTRA_CARD_NBR,\n" +
                        " BIRTHDAY_DT\n" +
                        ")\n" +
                        " Values \n" +
                        "(\n" +
                        " :XTRA_CARD_NBR,\n" +
                        " :BIRTHDAY_DT\n" +
                        ")";

        MapSqlParameterSource xccParms = new MapSqlParameterSource();

        xccParms.addValue("XTRA_CARD_NBR", xtraCardChild.getXtraCardNbr(), Types.INTEGER);
        xccParms.addValue("BIRTHDAY_DT", xtraCardChild.getBirthdayDt(), Types.DATE);

        int xtraCardChildRowCnt = jdbcTemplate.update(xtraCardChildsql, xccParms);
        log.info("Total Number of Cards inserted into XTRA_CARD_CHILD table : [{}]", xtraCardChildRowCnt);

    }

    public List<Map<String, Object>> selectDataFromXtraCardChildTable(List<Integer> xCardList, String tableName) {
    	int xtraCard = xCardList.get(0);
    	String xtraCardRecord = "";
    	xtraCardRecord ="SELECT * FROM "+tableName+" WHERE XTRA_CARD_NBR = "+xtraCard;
        
    	MapSqlParameterSource mvSelParms = new MapSqlParameterSource();
        mvSelParms.addValue("CUST_ID", xtraCard, Types.INTEGER);
        return jdbcTemplate.queryForList(xtraCardRecord, mvSelParms);
    }
    
    public List<Map<String, Object>> selectDataFromXtraCardSelectCategoryTable(List<Integer> xCardList, String tableName) {
    	int xtraCard = xCardList.get(0);
    	String xtraCardRecord = "";
    	xtraCardRecord ="SELECT * FROM "+tableName+" WHERE XTRA_CARD_NBR = "+xtraCard;
        
    	MapSqlParameterSource mvSelParms = new MapSqlParameterSource();
        return jdbcTemplate.queryForList(xtraCardRecord, mvSelParms);
    }

    public void createXtraCardAnalyticEvent(XtraCardAnalyticEvent xtraCardAnalyticEvent) {
        // Create new xtra_card_analytic_event Record
        String xtraCardAnalyEventsql =
                "INSERT INTO xtra_card_analytic_event\n" +
                        "(\n" +
                        " XTRA_CARD_NBR,\n" +
                        " CPN_SEQ_NBR,\n" +
                        " ANALYTIC_EVENT_TYPE_CD,\n" +
                        " ANALYTIC_EVENT_TS,\n" +
                        " FIRST_UPDT_SRC_CD,\n" +
                        " EVENT_DATA_JSON,\n" +
                        " EVENT_META_DATA_JSON,\n" +
                        " LOAD_TS\n" +
                        ")\n" +
                        " Values \n" +
                        "(\n" +
                        " :XTRA_CARD_NBR,\n" +
                        " :CPN_SEQ_NBR,\n" +
                        " :ANALYTIC_EVENT_TYPE_CD,\n" +
                        " :ANALYTIC_EVENT_TS,\n" +
                        " :FIRST_UPDT_SRC_CD,\n" +
                        " :EVENT_DATA_JSON,\n" +
                        " :EVENT_META_DATA_JSON,\n" +
                        " :LOAD_TS\n" +
                        ")";

        MapSqlParameterSource xcaeParms = new MapSqlParameterSource();

        xcaeParms.addValue("XTRA_CARD_NBR", xtraCardAnalyticEvent.getXtraCardNbr(), Types.INTEGER);
        xcaeParms.addValue("CPN_SEQ_NBR", xtraCardAnalyticEvent.getCpnSeqNbr(), Types.INTEGER);
        xcaeParms.addValue("ANALYTIC_EVENT_TYPE_CD", xtraCardAnalyticEvent.getAnalyticEventTypeCd(), Types.INTEGER);
        xcaeParms.addValue("ANALYTIC_EVENT_TS", xtraCardAnalyticEvent.getAnalyticEventTs(), Types.TIMESTAMP_WITH_TIMEZONE);
        xcaeParms.addValue("FIRST_UPDT_SRC_CD", xtraCardAnalyticEvent.getFirstUpdtSrcCd(), Types.VARCHAR);
        xcaeParms.addValue("EVENT_DATA_JSON", xtraCardAnalyticEvent.getEventDataJson(), Types.VARCHAR);
        xcaeParms.addValue("EVENT_META_DATA_JSON", xtraCardAnalyticEvent.getEventMetaDataJson(), Types.VARCHAR);
        xcaeParms.addValue("LOAD_TS", xtraCardAnalyticEvent.getLoadTs(), Types.TIMESTAMP);
        int xtraCardAnalyticEventRowCnt = jdbcTemplate.update(xtraCardAnalyEventsql, xcaeParms);
        log.info("Total Number of Cards inserted into XTRA_CARD_ANALYTIC_EVENT table : [{}]", xtraCardAnalyticEventRowCnt);
    }


    /**
     * Create EmployeeCard Summary
     */
    public void createEmployeeCard(EmployeeCard employeeCard) {
        // Create new Xtra Card Record
        String employeeCardsql =
                "INSERT INTO EMPLOYEE_CARD\n" +
                        "(\n" +
                        "EMP_CARD_NBR,\n" +
                        "CUST_ID\n" +
                        ")\n" +
                        " Values \n" +
                        "(\n" +
                        " :EMP_CARD_NBR,\n" +
                        " :CUST_ID\n" +
                        ")";

        MapSqlParameterSource ecParms = new MapSqlParameterSource();

        ecParms.addValue("EMP_CARD_NBR", employeeCard.getEmpCardNbr(), Types.INTEGER);
        ecParms.addValue("CUST_ID", employeeCard.getCustId(), Types.INTEGER);

        int employeeCardRowCnt = jdbcTemplate.update(employeeCardsql, ecParms);
        log.info("Total Number of Cards inserted into EMPLOYEE_CARD table : [{}]", employeeCardRowCnt);

    }


    public void updateXtraCard(XtraCard xtraCard) {
        // TODO Auto-generated method stub
        String xtraCardUpsql =
                "UPDATE XTRA_CARD\n" +
                        " SET ENCODED_XTRA_CARD_NBR=\n" +
                        "    :ENCODED_XTRA_CARD_NBR\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n";

        MapSqlParameterSource xcUpParms = new MapSqlParameterSource();

        xcUpParms.addValue("XTRA_CARD_NBR", xtraCard.getXtraCardNbr(), Types.INTEGER);
        xcUpParms.addValue("ENCODED_XTRA_CARD_NBR", xtraCard.getEncodedXtraCardNbr(), Types.INTEGER);
        int xtraCardUpRowCnt = jdbcTemplate.update(xtraCardUpsql, xcUpParms);

        log.info("Total Number of Cards Updated into xtraCard table : [{}]", xtraCardUpRowCnt);
    }

    public void createXtraCardRewardSummary(XtraCardRewardSummary xtraCardRewardSummary) {
        // Create new Xtra Card Rewards Summary
        String xcrsSql =
                "INSERT INTO XTRA_CARD_REWARD_SUMMARY (\n" +
                        "    XTRA_CARD_NBR,\n" +
                        "    TOT_EB_AVL_AMT\n" +
                        ") VALUES (\n" +
                        "    :XTRA_CARD_NBR,\n" +
                        "    :TOT_EB_AVL_AMT\n" +
                        ")";

        MapSqlParameterSource xcrsParms = new MapSqlParameterSource();
        xcrsParms.addValue("XTRA_CARD_NBR", xtraCardRewardSummary.getXtraCardNbr(), Types.INTEGER);
        xcrsParms.addValue("TOT_EB_AVL_AMT", xtraCardRewardSummary.getTotEbAvlAmt(), Types.DOUBLE);
        int smryRowCnt = jdbcTemplate.update(xcrsSql, xcrsParms);

        log.info("Total Number of Cards inserted into XTRA_CARD_REWARD_SUMMARY table : [{}]", smryRowCnt);
    }

    /**
     * Create Xtra Hot Card
     */
    public void createXtraHotCard(XtraHotCard xtraHotCard) {
        // Create new Hot Card
        String xhcSql =
                "INSERT INTO XTRA_HOT_CARD (\n" +
                        "    XTRA_CARD_NBR,\n" +
                        "    ADDED_DT\n" +
                        ") VALUES (\n" +
                        "    :XTRA_CARD_NBR,\n" +
                        "    sysdate\n" +
                        "\n" +
                        ")";

        MapSqlParameterSource xhcParms = new MapSqlParameterSource();

        xhcParms.addValue("XTRA_CARD_NBR", xtraHotCard.getXtraCardNbr(), Types.INTEGER);
        xhcParms.addValue("ADDED_DT", xtraHotCard.getAddedDt(), Types.DATE);
        int hcRowCnt = jdbcTemplate.update(xhcSql, xhcParms);

        log.info("Total Number of Cards inserted into xtra_hot_card table : [{}]", hcRowCnt);
    }

    /**
     * update Xtra Parms
     */
    public void updateXtraParms(XtraParms xtraParms) {
        // Create new Hot Card
        String xpSql =
                "UPDATE XTRA_PARMS\n" +
                        " SET PARM_VALUE_TXT=\n" +
                        "    :PARM_VALUE_TXT\n" +
                        "WHERE PARM_NAME=\n" +
                        " :PARM_NAME\n";


        MapSqlParameterSource xpParms = new MapSqlParameterSource();

        xpParms.addValue("PARM_NAME", xtraParms.getParmName(), Types.VARCHAR);
        xpParms.addValue("PARM_VALUE_TXT", xtraParms.getParmValueTxt(), Types.VARCHAR);
        int xpRowCnt = jdbcTemplate.update(xpSql, xpParms);

        log.info("updated PARM_VALUE_TXT of LST_CLONE_DT in xtra_parms table : [{}]", xpSql);
    }


    /**
     * Create XtraCardActiveCoupon;
     */
    public void createXtraCardActiveCoupon(XtraCardActiveCoupon xtraCardActiveCoupon) {
        // Create new Xtra Card Record
        String xtraCardActiveCouponsql =
                "INSERT INTO XTRA_CARD_ACTIVE_COUPON\n" +
                        "(\n" +
                        " XTRA_CARD_NBR,\n" +
                        " CPN_SEQ_NBR,\n" +
                        " CMPGN_ID,\n" +
                        " CPN_NBR,\n" +
                        " CMPGN_CPN_SEQ_NBR,\n" +
                        " CPN_SRC_CD,\n" +
                        " CPN_CREATE_DT,\n" +
                        " REG_RETL_AMT,\n" +
                        " EMAIL_CMPGN_ID,\n" +
                        " RE_ISSUE_NBR,\n" +
                        " STORE_SEEN_NBR,\n" +
                        " STORE_SEEN_TS,\n" +
                        " VIEW_ACTL_TSWTZ,\n" +
                        " VIEW_ACTL_TSWTZ_SET_DT,\n" +
                        " VIEW_ACTL_DEST_CD,\n" +
                        " VIEW_ACTL_DEST_SUB_SRC_CD,\n" +
                        " VIEW_ACTL_STORE_NBR,\n" +
                        " PRNT_START_TSWTZ,\n" +
                        " PRNT_END_TSWTZ,\n" +
                        " PRNT_ACTL_TSWTZ,\n" +
                        " PRNT_ACTL_TSWTZ_SET_DT,\n" +
                        " PRNT_DEST_CD,\n" +
                        " PRNT_ACTL_DEST_SUB_SRC_CD,\n" +
                        " PRNT_PRIORITY_NBR,\n" +
                        " PRNT_ACTL_REFER_BY_CD,\n" +
                        " PRNT_STORE_NBR,\n" +
                        " LOAD_ACTL_TSWTZ,\n" +
                        " LOAD_ACTL_TSWTZ_SET_DT,\n" +
                        " LOAD_ACTL_DEST_CD,\n" +
                        " LOAD_ACTL_DEST_SUB_SRC_CD,\n" +
                        " LOAD_ACTL_REFER_BY_CD,\n" +
                        " LOAD_ACTL_STORE_NBR,\n" +
                        " MFR_LOAD_NOTIF_STATUS_CD,\n" +
                        " MFR_LOAD_NOTIF_CSP_STATUS_CD,\n" +
                        " MFR_LOAD_NOTIF_STATUS_TS,\n" +
                        " REDEEM_START_TSWTZ,\n" +
                        " REDEEM_END_TSWTZ,\n" +
                        " REDEEM_ACTL_TSWTZ,\n" +
                        " REDEEM_ACTL_TSWTZ_SET_DT,\n" +
                        " REDEEM_STORE_NBR,\n" +
                        " REDEEM_ACTL_AMT,\n" +
                        " REDEEM_PRG_CD,\n" +
                        " REDEEM_MATCH_CD,\n" +
                        " REPRNT_CD,\n" +
                        " REDEEM_ACTL_XTRA_CARD_NBR,\n" +
                        " REDEEM_OVRD_RSN_NBR,\n" +
                        " REDEEM_ACTL_CASHIER_NBR,\n" +
                        " MFR_REDEEM_NOTIF_STATUS_CD,\n" +
                        " MFR_REDEEM_NOTIF_CSP_STATUS_CD,\n" +
                        " MFR_REDEEM_NOTIF_STATUS_TS\n" +
                        ")\n" +
                        " Values \n" +
                        "(\n" +
                        " :XTRA_CARD_NBR,\n" +
                        " :CPN_SEQ_NBR,\n" +
                        " :CMPGN_ID,\n" +
                        " :CPN_NBR,\n" +
                        " :CMPGN_CPN_SEQ_NBR,\n" +
                        " :CPN_SRC_CD,\n" +
                        " :CPN_CREATE_DT,\n" +
                        " :REG_RETL_AMT,\n" +
                        " :EMAIL_CMPGN_ID,\n" +
                        " :RE_ISSUE_NBR,\n" +
                        " :STORE_SEEN_NBR,\n" +
                        " :STORE_SEEN_TS,\n" +
                        " :VIEW_ACTL_TSWTZ,\n" +
                        " :VIEW_ACTL_TSWTZ_SET_DT,\n" +
                        " :VIEW_ACTL_DEST_CD,\n" +
                        " :VIEW_ACTL_DEST_SUB_SRC_CD,\n" +
                        " :VIEW_ACTL_STORE_NBR,\n" +
                        " :PRNT_START_TSWTZ,\n" +
                        " :PRNT_END_TSWTZ,\n" +
                        " :PRNT_ACTL_TSWTZ,\n" +
                        " :PRNT_ACTL_TSWTZ_SET_DT,\n" +
                        " :PRNT_DEST_CD,\n" +
                        " :PRNT_ACTL_DEST_SUB_SRC_CD,\n" +
                        " :PRNT_PRIORITY_NBR,\n" +
                        " :PRNT_ACTL_REFER_BY_CD,\n" +
                        " :PRNT_STORE_NBR,\n" +
                        " :LOAD_ACTL_TSWTZ,\n" +
                        " :LOAD_ACTL_TSWTZ_SET_DT,\n" +
                        " :LOAD_ACTL_DEST_CD,\n" +
                        " :LOAD_ACTL_DEST_SUB_SRC_CD,\n" +
                        " :LOAD_ACTL_REFER_BY_CD,\n" +
                        " :LOAD_ACTL_STORE_NBR,\n" +
                        " :MFR_LOAD_NOTIF_STATUS_CD,\n" +
                        " :MFR_LOAD_NOTIF_CSP_STATUS_CD,\n" +
                        " :MFR_LOAD_NOTIF_STATUS_TS,\n" +
                        " :REDEEM_START_TSWTZ,\n" +
                        " :REDEEM_END_TSWTZ,\n" +
                        " :REDEEM_ACTL_TSWTZ,\n" +
                        " :REDEEM_ACTL_TSWTZ_SET_DT,\n" +
                        " :REDEEM_STORE_NBR,\n" +
                        " :REDEEM_ACTL_AMT,\n" +
                        " :REDEEM_PRG_CD,\n" +
                        " :REDEEM_MATCH_CD,\n" +
                        " :REPRNT_CD,\n" +
                        " :REDEEM_ACTL_XTRA_CARD_NBR,\n" +
                        " :REDEEM_OVRD_RSN_NBR,\n" +
                        " :REDEEM_ACTL_CASHIER_NBR,\n" +
                        " :MFR_REDEEM_NOTIF_STATUS_CD,\n" +
                        " :MFR_REDEEM_NOTIF_CSP_STATUS_CD,\n" +
                        " :MFR_REDEEM_NOTIF_STATUS_TS\n" +
                        ")";

        MapSqlParameterSource xcParms = new MapSqlParameterSource();
        xcParms.addValue("XTRA_CARD_NBR", xtraCardActiveCoupon.getXtraCardNbr(), Types.INTEGER);
        xcParms.addValue("CPN_SEQ_NBR", xtraCardActiveCoupon.getCpnseqNbr(), Types.LONGNVARCHAR);
        xcParms.addValue("CMPGN_ID", xtraCardActiveCoupon.getCmpgnId(), Types.INTEGER);
        xcParms.addValue("CPN_NBR", xtraCardActiveCoupon.getCpnNbr(), Types.INTEGER);
        xcParms.addValue("CMPGN_CPN_SEQ_NBR", xtraCardActiveCoupon.getCmpgnCpnSeqNbr(), Types.INTEGER);
        xcParms.addValue("CPN_SRC_CD", xtraCardActiveCoupon.getCpnSrcCd(), Types.VARCHAR);
        xcParms.addValue("CPN_CREATE_DT", xtraCardActiveCoupon.getCpnCreateDt(), Types.DATE);
        xcParms.addValue("REG_RETL_AMT", xtraCardActiveCoupon.getRegRetlAmt(), Types.INTEGER);
        xcParms.addValue("EMAIL_CMPGN_ID", xtraCardActiveCoupon.getEmailCmpgnId(), Types.INTEGER);
        xcParms.addValue("RE_ISSUE_NBR", xtraCardActiveCoupon.getReIssueNbr(), Types.INTEGER);
        xcParms.addValue("STORE_SEEN_NBR", xtraCardActiveCoupon.getStoreSeenNbr(), Types.INTEGER);
        xcParms.addValue("STORE_SEEN_TS", xtraCardActiveCoupon.getStoreSeenNbr(), Types.TIMESTAMP);
        xcParms.addValue("VIEW_ACTL_TSWTZ", xtraCardActiveCoupon.getViewActlTswtz(), Types.TIMESTAMP_WITH_TIMEZONE);
        xcParms.addValue("VIEW_ACTL_TSWTZ_SET_DT", xtraCardActiveCoupon.getViewActlTswtzSetDt(), Types.DATE);
        xcParms.addValue("VIEW_ACTL_DEST_CD", xtraCardActiveCoupon.getViewActlDestCd(), Types.VARCHAR);
        xcParms.addValue("VIEW_ACTL_DEST_SUB_SRC_CD", xtraCardActiveCoupon.getViewActlDestSubSrcCd(), Types.VARCHAR);
        xcParms.addValue("VIEW_ACTL_STORE_NBR", xtraCardActiveCoupon.getViewActlStoreNbr(), Types.INTEGER);
        xcParms.addValue("PRNT_START_TSWTZ", xtraCardActiveCoupon.getPrntStartTswtz(), Types.DATE);
        xcParms.addValue("PRNT_END_TSWTZ", xtraCardActiveCoupon.getPrntEndTswtz(), Types.DATE);
        xcParms.addValue("PRNT_ACTL_TSWTZ", xtraCardActiveCoupon.getPrntActlTswtz(), Types.TIMESTAMP_WITH_TIMEZONE);
        xcParms.addValue("PRNT_ACTL_TSWTZ_SET_DT", xtraCardActiveCoupon.getPrntActlTswtzSetDt(), Types.DATE);
        xcParms.addValue("PRNT_DEST_CD", xtraCardActiveCoupon.getPrntDestCd(), Types.VARCHAR);
        xcParms.addValue("PRNT_ACTL_DEST_SUB_SRC_CD", xtraCardActiveCoupon.getPrntActlDestSubSrcCd(), Types.DATE);
        xcParms.addValue("PRNT_PRIORITY_NBR", xtraCardActiveCoupon.getPrntPriorityNbr(), Types.INTEGER);
        xcParms.addValue("PRNT_ACTL_REFER_BY_CD", xtraCardActiveCoupon.getPrntActlReferByCd(), Types.VARCHAR);
        xcParms.addValue("PRNT_STORE_NBR", xtraCardActiveCoupon.getPrntStoreNbr(), Types.INTEGER);
        xcParms.addValue("LOAD_ACTL_TSWTZ", xtraCardActiveCoupon.getLoadActlTswtz(), Types.DATE);
        xcParms.addValue("LOAD_ACTL_TSWTZ_SET_DT", xtraCardActiveCoupon.getLoadActlTswtzSetDt(), Types.DATE);
        xcParms.addValue("LOAD_ACTL_DEST_CD", xtraCardActiveCoupon.getLoadActlDestCd(), Types.VARCHAR);
        xcParms.addValue("LOAD_ACTL_DEST_SUB_SRC_CD", xtraCardActiveCoupon.getLoadActlDestSubSrcCd(), Types.VARCHAR);
        xcParms.addValue("LOAD_ACTL_REFER_BY_CD", xtraCardActiveCoupon.getLoadActlReferByCd(), Types.VARCHAR);
        xcParms.addValue("LOAD_ACTL_STORE_NBR", xtraCardActiveCoupon.getLoadActlStoreNbr(), Types.INTEGER);
        xcParms.addValue("MFR_LOAD_NOTIF_STATUS_CD", xtraCardActiveCoupon.getMfrLoadNotifStatusCd(), Types.VARCHAR);
        xcParms.addValue("MFR_LOAD_NOTIF_CSP_STATUS_CD", xtraCardActiveCoupon.getMfrLoadNotifCspStatusCd(), Types.INTEGER);
        xcParms.addValue("MFR_LOAD_NOTIF_STATUS_TS", xtraCardActiveCoupon.getMfrLoadNotifStatusCd(), Types.TIMESTAMP);
        xcParms.addValue("REDEEM_START_TSWTZ", xtraCardActiveCoupon.getRedeemStartTswtz(), Types.DATE);
        xcParms.addValue("REDEEM_END_TSWTZ", xtraCardActiveCoupon.getRedeemEndTswtz(), Types.DATE);
        xcParms.addValue("REDEEM_ACTL_TSWTZ", xtraCardActiveCoupon.getRedeemActlTswtz(), Types.TIMESTAMP_WITH_TIMEZONE);
        xcParms.addValue("REDEEM_ACTL_TSWTZ_SET_DT", xtraCardActiveCoupon.getRedeemActlTswtzSetDt(), Types.DATE);
        xcParms.addValue("REDEEM_STORE_NBR", xtraCardActiveCoupon.getRedeemStoreNbr(), Types.INTEGER);
        xcParms.addValue("REDEEM_ACTL_AMT", xtraCardActiveCoupon.getRedeemActlAmt(), Types.DOUBLE);
        xcParms.addValue("REDEEM_PRG_CD", xtraCardActiveCoupon.getRedeemPrgCd(), Types.VARCHAR);
        xcParms.addValue("REDEEM_MATCH_CD", xtraCardActiveCoupon.getRedeemMatchCd(), Types.VARCHAR);
        xcParms.addValue("REPRNT_CD", xtraCardActiveCoupon.getReprntCd(), Types.VARCHAR);
        xcParms.addValue("REDEEM_ACTL_XTRA_CARD_NBR", xtraCardActiveCoupon.getRedeemActlXtraCardNbr(), Types.INTEGER);
        xcParms.addValue("REDEEM_OVRD_RSN_NBR", xtraCardActiveCoupon.getRedeemOvrdRsnNbr(), Types.INTEGER);
        xcParms.addValue("REDEEM_ACTL_CASHIER_NBR", xtraCardActiveCoupon.getRedeemActlCashierNbr(), Types.INTEGER);
        xcParms.addValue("MFR_REDEEM_NOTIF_STATUS_CD", xtraCardActiveCoupon.getMfrLoadNotifStatusCd(), Types.VARCHAR);
        xcParms.addValue("MFR_REDEEM_NOTIF_CSP_STATUS_CD", xtraCardActiveCoupon.getMfrRedeemNotifCspStatusCd(), Types.INTEGER);
        xcParms.addValue("MFR_REDEEM_NOTIF_STATUS_TS", xtraCardActiveCoupon.getMfrRedeemNotifStatusTs(), Types.TIMESTAMP);

        int xtraCardActiveCouponRowCnt = jdbcTemplate.update(xtraCardActiveCouponsql, xcParms);
        log.info("Total Number of Cards inserted into xtraCardActiveCoupon table : [{}]", xtraCardActiveCouponRowCnt);

    }

    public void updateXtraCardActiveCoupon(XtraCardActiveCoupon xtraCardActiveCoupon) {
        // TODO Auto-generated method stub
        String xtraCardActiveCouponUpsql =
                "UPDATE XTRA_CARD_ACTIVE_COUPON\n" +
                        " SET CPN_CREATE_DT=\n" +
                        "    :CPN_CREATE_DT,\n" +
                        " PRNT_START_TSWTZ=\n" +
                        "    :PRNT_START_TSWTZ,\n" +
                        " PRNT_END_TSWTZ=\n" +
                        "    :PRNT_END_TSWTZ,\n" +
                        " LOAD_ACTL_TSWTZ=\n" +
                        "    :LOAD_ACTL_TSWTZ,\n" +
                        " LOAD_ACTL_TSWTZ_SET_DT=\n" +
                        "    :LOAD_ACTL_TSWTZ_SET_DT,\n" +
                        " REDEEM_START_TSWTZ=\n" +
                        "    :REDEEM_START_TSWTZ,\n" +
                        " REDEEM_END_TSWTZ=\n" +
                        "    :REDEEM_END_TSWTZ,\n" +
                        " REDEEM_ACTL_TSWTZ=\n" +
                        "    :REDEEM_ACTL_TSWTZ,\n" +
                        " REDEEM_ACTL_TSWTZ_SET_DT=\n" +
                        "    :REDEEM_ACTL_TSWTZ_SET_DT,\n" +
                        " CPN_SRC_CD=\n" +
                        "    :CPN_SRC_CD\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n" +
                        " AND CMPGN_ID=\n" +
                        " :CMPGN_ID\n";

        MapSqlParameterSource xcUpParms = new MapSqlParameterSource();

        xcUpParms.addValue("XTRA_CARD_NBR", xtraCardActiveCoupon.getXtraCardNbr(), Types.INTEGER);
        xcUpParms.addValue("CMPGN_ID", xtraCardActiveCoupon.getCmpgnId(), Types.INTEGER);
        xcUpParms.addValue("CPN_CREATE_DT", xtraCardActiveCoupon.getCpnCreateDt(), Types.DATE);
        xcUpParms.addValue("PRNT_START_TSWTZ", xtraCardActiveCoupon.getPrntStartTswtz(), Types.DATE);
        xcUpParms.addValue("PRNT_END_TSWTZ", xtraCardActiveCoupon.getPrntEndTswtz(), Types.DATE);
        xcUpParms.addValue("LOAD_ACTL_TSWTZ", xtraCardActiveCoupon.getLoadActlTswtz(), Types.DATE);
        xcUpParms.addValue("LOAD_ACTL_TSWTZ_SET_DT", xtraCardActiveCoupon.getLoadActlTswtzSetDt(), Types.DATE);
        xcUpParms.addValue("REDEEM_START_TSWTZ", xtraCardActiveCoupon.getRedeemStartTswtz(), Types.DATE);
        xcUpParms.addValue("REDEEM_END_TSWTZ", xtraCardActiveCoupon.getRedeemEndTswtz(), Types.DATE);
        xcUpParms.addValue("REDEEM_ACTL_TSWTZ", xtraCardActiveCoupon.getRedeemActlTswtz(), Types.TIMESTAMP_WITH_TIMEZONE);
        xcUpParms.addValue("REDEEM_ACTL_TSWTZ_SET_DT", xtraCardActiveCoupon.getRedeemActlTswtzSetDt(), Types.DATE);
        xcUpParms.addValue("CPN_SRC_CD", xtraCardActiveCoupon.getCpnSrcCd(), Types.CHAR);
        int xtraCardActiveCouponUpRowCnt = jdbcTemplate.update(xtraCardActiveCouponUpsql, xcUpParms);

        log.info("Total Number of Cards Updated into xtraCardActiveCoupon table : [{}]", xtraCardActiveCouponUpRowCnt);
    }
    
    public void updateXtraCardActiveCouponLoad(long cpnSeqNbr) {
        String xtraCardActiveCouponUpsql =
                "UPDATE XTRA_CARD_ACTIVE_COUPON SET LOAD_ACTL_TSWTZ = SYSDATE WHERE CPN_SEQ_NBR = " + cpnSeqNbr + "";

        MapSqlParameterSource xcUpParms = new MapSqlParameterSource();
        int xtraCardActiveCouponUpRowCnt = jdbcTemplate.update(xtraCardActiveCouponUpsql, xcUpParms);

        log.info("Total Number of Cards Updated into xtraCardActiveCoupon table : [{}]", xtraCardActiveCouponUpRowCnt);
    }
    
    public void updateXtraCardActiveCouponRedeem(long cpnSeqNbr) {
        String xtraCardActiveCouponUpsql =
                "UPDATE XTRA_CARD_ACTIVE_COUPON SET LOAD_ACTL_TSWTZ = SYSDATE, REDEEM_START_TSWTZ = SYSDATE-1, REDEEM_END_TSWTZ = SYSDATE+1  WHERE CPN_SEQ_NBR = " + cpnSeqNbr + "";

        MapSqlParameterSource xcUpParms = new MapSqlParameterSource();
        int xtraCardActiveCouponUpRowCnt = jdbcTemplate.update(xtraCardActiveCouponUpsql, xcUpParms);

        log.info("Total Number of Cards Updated into xtraCardActiveCoupon table : [{}]", xtraCardActiveCouponUpRowCnt);
    }

    public Long selectXtraCardActiveCoupon(XtraCardActiveCoupon xtraCardActiveCoupon) {
        Long seq;
        String xtraCardActiveCouponSelsql =
                "SELECT CPN_SEQ_NBR\n" +
                        "FROM XTRA_CARD_ACTIVE_COUPON\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n" +
                        "AND CMPGN_ID=\n" +
                        " :CMPGN_ID\n";

        MapSqlParameterSource xcSelParms = new MapSqlParameterSource();
        xcSelParms.addValue("XTRA_CARD_NBR", xtraCardActiveCoupon.getXtraCardNbr(), Types.INTEGER);
        xcSelParms.addValue("CMPGN_ID", xtraCardActiveCoupon.getCmpgnId(), Types.INTEGER);
        return (Long) jdbcTemplate.queryForObject(xtraCardActiveCouponSelsql, xcSelParms, Long.class);
    }


    public Integer selectXtraCardSegment(XtraCardSegment xtraCardSegment) {
        String xtraCardSegmentSelsql =
                "SELECT COUNT (*)\n" +
                        "FROM XTRA_CARD_SEGMENT\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n";

        MapSqlParameterSource xcSelParms = new MapSqlParameterSource();
        xcSelParms.addValue("XTRA_CARD_NBR", xtraCardSegment.getXtraCardNbr(), Types.INTEGER);
        return (Integer) jdbcTemplate.queryForObject(xtraCardSegmentSelsql, xcSelParms, Integer.class);
    }

    public Integer selectXtraCardCount(XtraCard xtraCard) {
        String xtraCardSelsql =
                "SELECT COUNT (*)\n" +
                        "FROM XTRA_CARD\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n";

        MapSqlParameterSource xcSelParms = new MapSqlParameterSource();
        xcSelParms.addValue("XTRA_CARD_NBR", xtraCard.getXtraCardNbr(), Types.INTEGER);
        return (Integer) jdbcTemplate.queryForObject(xtraCardSelsql, xcSelParms, Integer.class);
    }


    public Long selectXtraCardCouponAudit(XtraCardCouponAudit xtraCardCouponAudit) {
        Long seq;
        String xtraCardCouponAuditSelsql =
                "SELECT CPN_SEQ_NBR\n" +
                        "FROM XTRA_CARD_COUPON_AUDIT\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n";

        MapSqlParameterSource xccaSelParms = new MapSqlParameterSource();
        xccaSelParms.addValue("XTRA_CARD_NBR", xtraCardCouponAudit.getXtraCardNbr(), Types.INTEGER);
        return (Long) jdbcTemplate.queryForObject(xtraCardCouponAuditSelsql, xccaSelParms, Long.class);
    }


    public Long selectMaxXtraCardActiveCoupon(XtraCardActiveCoupon xtraCardActiveCoupon) {
        Long seq;
        String xtraCardActiveCouponSelMaxsql =
                "SELECT MAX(CPN_SEQ_NBR)\n" +
                        "FROM XTRA_CARD_ACTIVE_COUPON\n";

        MapSqlParameterSource xcSelMaxParms = new MapSqlParameterSource();
        return (Long) jdbcTemplate.queryForObject(xtraCardActiveCouponSelMaxsql, xcSelMaxParms, Long.class);
    }

    public void createXtraCardSegment(XtraCardSegment xtraCardSegment) {
        // Create new Xtra Card Segment Record
        String xtraCardSegsql =
                "INSERT INTO XTRA_CARD_SEGMENT\n" +
                        "(\n" +
                        " XTRA_CARD_NBR,\n" +
                        " XTRA_CARD_SEG_NBR,\n" +
                        " CTL_GRP_CD\n" +
                        ")\n" +
                        " Values \n" +
                        "(\n" +
                        " :XTRA_CARD_NBR,\n" +
                        " :XTRA_CARD_SEG_NBR,\n" +
                        " :CTL_GRP_CD\n" +
                        ")";

        MapSqlParameterSource xcsParms = new MapSqlParameterSource();

        xcsParms.addValue("XTRA_CARD_NBR", xtraCardSegment.getXtraCardNbr(), Types.INTEGER);
        xcsParms.addValue("XTRA_CARD_SEG_NBR", xtraCardSegment.getXtraCardSegNbr(), Types.INTEGER);
        xcsParms.addValue("CTL_GRP_CD", xtraCardSegment.getCtlGrpCd(), Types.VARCHAR);

        int xtraCardSegRowCnt = jdbcTemplate.update(xtraCardSegsql, xcsParms);
        log.info("Total Number of Cards inserted into XTRA_CARD SEGMENT table : [{}]", xtraCardSegRowCnt);
    }
    

    public void createXtraCardRewardPhrTxnDtl(XtraCardRewardPHRTxnDtl xtraCardRewardPhrTxnDtl) {
        // Create new Xtra Card Reward PHR Txn Dtl
        String xtraCardRPhrTdsql =
                "INSERT INTO XTRA_CARD_REWARD_PHR_TXN_DTL\n" +
                        "(\n" +
                        " XTRA_CARD_NBR,\n" +
                        " EPH_LINK_ID,\n" +
                        " TXN_DTTM,\n" +
                        " CMPGN_ID,\n" +
                        " CPN_NBR,\n" +
                        " TXN_TYP_CD,\n" +
                        " EVENT_ID,\n" +
                        " PTS_QTY,\n" +
                        " TXN_AMT,\n" +
                        " CREATE_DTTM\n" +
                        ")\n" +
                        " Values \n" +
                        "(\n" +
                        " :XTRA_CARD_NBR,\n" +
                        " :EPH_LINK_ID,\n" +
                        " :TXN_DTTM,\n" +
                        " :CMPGN_ID,\n" +
                        " :CPN_NBR,\n" +
                        " :TXN_TYP_CD,\n" +
                        " :EVENT_ID,\n" +
                        " :PTS_QTY,\n" +
                        " :TXN_AMT,\n" +
                        " :CREATE_DTTM\n" +
                        ")";

        MapSqlParameterSource xcrphrtdParms = new MapSqlParameterSource();

        xcrphrtdParms.addValue("XTRA_CARD_NBR", xtraCardRewardPhrTxnDtl.getXtraCardNbr(), Types.INTEGER);
        xcrphrtdParms.addValue("EPH_LINK_ID", xtraCardRewardPhrTxnDtl.getEphLinkId(), Types.INTEGER);
        xcrphrtdParms.addValue("TXN_DTTM", xtraCardRewardPhrTxnDtl.getTxnDttm(), Types.DATE);
        xcrphrtdParms.addValue("CMPGN_ID", xtraCardRewardPhrTxnDtl.getCmpgnId(), Types.INTEGER);
        xcrphrtdParms.addValue("CPN_NBR", xtraCardRewardPhrTxnDtl.getCpnNbr(), Types.INTEGER);
        xcrphrtdParms.addValue("TXN_TYP_CD", xtraCardRewardPhrTxnDtl.getTxnTypCd(), Types.VARCHAR);
        xcrphrtdParms.addValue("EVENT_ID", xtraCardRewardPhrTxnDtl.getEventId(), Types.INTEGER);
        xcrphrtdParms.addValue("PTS_QTY", xtraCardRewardPhrTxnDtl.getPtsQty(), Types.INTEGER);
        xcrphrtdParms.addValue("TXN_AMT", xtraCardRewardPhrTxnDtl.getTxnAmt(), Types.INTEGER);
        xcrphrtdParms.addValue("CREATE_DTTM", xtraCardRewardPhrTxnDtl.getCreateDttm(), Types.TIMESTAMP);

        int xtraCardPhrTDRowCnt = jdbcTemplate.update(xtraCardRPhrTdsql, xcrphrtdParms);
        log.info("Total Number of Cards inserted into xtra_card_reward_phr_txn_dtl table : [{}]", xtraCardPhrTDRowCnt);
    }

    public void createXtraCardRewardBcTxnDtl(XtraCardRewardBCTxnDtl xtraCardRewardBCTxnDtl) {
        // Create new Xtra Card Reward BC Txn Dtl
        String xtraCardRBcTdsql =
                "INSERT INTO XTRA_CARD_REWARD_BC_TXN_DTL\n" +
                        "(\n" +
                        " XTRA_CARD_NBR,\n" +
                        " TXN_DTTM,\n" +
                        " CMPGN_ID,\n" +
                        " CPN_NBR,\n" +
                        " TXN_TYP_CD,\n" +
                        " QUALIFIED_TXN_AMT,\n" +
                        " CREATE_DTTM\n" +
                        ")\n" +
                        " Values \n" +
                        "(\n" +
                        " :XTRA_CARD_NBR,\n" +
                        " :TXN_DTTM,\n" +
                        " :CMPGN_ID,\n" +
                        " :CPN_NBR,\n" +
                        " :TXN_TYP_CD,\n" +
                        " :QUALIFIED_TXN_AMT,\n" +
                        " :CREATE_DTTM\n" +
                        ")";

        MapSqlParameterSource xcrbctdParms = new MapSqlParameterSource();

        xcrbctdParms.addValue("XTRA_CARD_NBR", xtraCardRewardBCTxnDtl.getXtraCardNbr(), Types.INTEGER);
        xcrbctdParms.addValue("TXN_DTTM", xtraCardRewardBCTxnDtl.getTxnDttm(), Types.DATE);
        xcrbctdParms.addValue("CMPGN_ID", xtraCardRewardBCTxnDtl.getCmpgnId(), Types.INTEGER);
        xcrbctdParms.addValue("CPN_NBR", xtraCardRewardBCTxnDtl.getXtraCardNbr(), Types.INTEGER);
        xcrbctdParms.addValue("TXN_TYP_CD", xtraCardRewardBCTxnDtl.getTxnTypCd(), Types.VARCHAR);
        xcrbctdParms.addValue("QUALIFIED_TXN_AMT", xtraCardRewardBCTxnDtl.getQualifiedTxnAmt(), Types.INTEGER);
        xcrbctdParms.addValue("CREATE_DTTM", xtraCardRewardBCTxnDtl.getCreateDttm(), Types.TIMESTAMP);

        int xtraCardBCTDRowCnt = jdbcTemplate.update(xtraCardRBcTdsql, xcrbctdParms);
        log.info("Total Number of Cards inserted into xtra_card_reward_bc_txn_dtl table : [{}]", xtraCardBCTDRowCnt);
    }


    public void createXtraCardRewardQebTxnDtl(XtraCardRewardQEBTxnDtl xtraCardRewardQEBTxnDtl) {
        // Create new Xtra Card Reward QEB Txn Dtl
        String xtraCardQebTdsql =
                "INSERT INTO XTRA_CARD_REWARD_QEB_TXN_DTL\n" +
                        "(\n" +
                        " XTRA_CARD_NBR,\n" +
                        " TXN_DTTM,\n" +
                        " CMPGN_ID,\n" +
                        " CPN_NBR,\n" +
                        " TXN_TYP_CD,\n" +
                        " QUALIFIED_TXN_AMT,\n" +
                        " CREATE_DTTM\n" +
                        ")\n" +
                        " Values \n" +
                        "(\n" +
                        " :XTRA_CARD_NBR,\n" +
                        " :TXN_DTTM,\n" +
                        " :CMPGN_ID,\n" +
                        " :CPN_NBR,\n" +
                        " :TXN_TYP_CD,\n" +
                        " :QUALIFIED_TXN_AMT,\n" +
                        " :CREATE_DTTM\n" +
                        ")";

        MapSqlParameterSource xcrqebtdParms = new MapSqlParameterSource();

        xcrqebtdParms.addValue("XTRA_CARD_NBR", xtraCardRewardQEBTxnDtl.getXtraCardNbr(), Types.INTEGER);
        xcrqebtdParms.addValue("TXN_DTTM", xtraCardRewardQEBTxnDtl.getTxnDttm(), Types.DATE);
        xcrqebtdParms.addValue("CMPGN_ID", xtraCardRewardQEBTxnDtl.getCmpgnId(), Types.INTEGER);
        xcrqebtdParms.addValue("CPN_NBR", xtraCardRewardQEBTxnDtl.getCpnNbr(), Types.INTEGER);
        xcrqebtdParms.addValue("TXN_TYP_CD", xtraCardRewardQEBTxnDtl.getTxnTypCd(), Types.VARCHAR);
        xcrqebtdParms.addValue("QUALIFIED_TXN_AMT", xtraCardRewardQEBTxnDtl.getQualifiedTxnAmt(), Types.INTEGER);
        xcrqebtdParms.addValue("CREATE_DTTM", xtraCardRewardQEBTxnDtl.getCreateDttm(), Types.TIMESTAMP);

        int xtraCardQebTDRowCnt = jdbcTemplate.update(xtraCardQebTdsql, xcrqebtdParms);
        log.info("Total Number of Cards inserted into xtra_card_reward_qeb_txn_dtl table : [{}]", xtraCardQebTDRowCnt);
    }

    public Integer selectCountXtraCardSummary(XtraCardSummary xtraCardSummary) {
        String xtraCardSummarySelsql =
                "SELECT count(*)\n" +
                        "FROM XTRA_CARD_SUMMARY\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n";

        MapSqlParameterSource xcSelCpnParms = new MapSqlParameterSource();
        xcSelCpnParms.addValue("XTRA_CARD_NBR", xtraCardSummary.getXtraCardNbr(), Types.INTEGER);
        return (Integer) jdbcTemplate.queryForObject(xtraCardSummarySelsql, xcSelCpnParms, Integer.class);
    }

    public Integer selectCouponsXtraCardActiveCoupon(XtraCardActiveCoupon xtraCardActiveCoupon) {
        String xtraCardActiveCouponSelCpnsql =
                "SELECT count(*)\n" +
                        "FROM XTRA_CARD_ACTIVE_COUPON\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n" +
                        "AND CMPGN_ID in (\n" +
                        " :CMPGN_ID\n" +
                        ")\n";

        MapSqlParameterSource xcSelCpnParms = new MapSqlParameterSource();
        xcSelCpnParms.addValue("XTRA_CARD_NBR", xtraCardActiveCoupon.getXtraCardNbr(), Types.INTEGER);
        xcSelCpnParms.addValue("CMPGN_ID", xtraCardActiveCoupon.getCmpgnId(), Types.INTEGER);
        return (Integer) jdbcTemplate.queryForObject(xtraCardActiveCouponSelCpnsql, xcSelCpnParms, Integer.class);
    }

    public Long selectCpnSeqNbrXtraCardActiveCoupon(XtraCardActiveCoupon xtraCardActiveCoupon) {
        String xtraCardActiveCouponSelCpnsql =
                "SELECT CPN_SEQ_NBR\n" +
                        "FROM XTRA_CARD_ACTIVE_COUPON\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n" +
                        "AND CMPGN_ID in (\n" +
                        " :CMPGN_ID\n" +
                        ") AND CPN_NBR in (\n" +
                        " :CPN_NBR\n" +
                        ")\n";

        MapSqlParameterSource xcSelCpnParms = new MapSqlParameterSource();
        xcSelCpnParms.addValue("XTRA_CARD_NBR", xtraCardActiveCoupon.getXtraCardNbr(), Types.INTEGER);
        xcSelCpnParms.addValue("CMPGN_ID", xtraCardActiveCoupon.getCmpgnId(), Types.INTEGER);
        xcSelCpnParms.addValue("CPN_NBR", xtraCardActiveCoupon.getCpnNbr(), Types.INTEGER);
        return (Long) jdbcTemplate.queryForObject(xtraCardActiveCouponSelCpnsql, xcSelCpnParms, Long.class);
    }
    
    public Long selectCampaignCpnSeqNbrXtraCardActiveCoupon(XtraCardActiveCoupon xtraCardActiveCoupon) {
        String xtraCardActiveCouponSelCpnsql =
                "SELECT CMPGN_CPN_SEQ_NBR\n" +
                        "FROM XTRA_CARD_ACTIVE_COUPON\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n" +
                        "AND CMPGN_ID in (\n" +
                        " :CMPGN_ID\n" +
                        ") AND CPN_NBR in (\n" +
                        " :CPN_NBR\n" +
                        ")\n";

        MapSqlParameterSource xcSelCpnParms = new MapSqlParameterSource();
        xcSelCpnParms.addValue("XTRA_CARD_NBR", xtraCardActiveCoupon.getXtraCardNbr(), Types.INTEGER);
        xcSelCpnParms.addValue("CMPGN_ID", xtraCardActiveCoupon.getCmpgnId(), Types.INTEGER);
        xcSelCpnParms.addValue("CPN_NBR", xtraCardActiveCoupon.getCpnNbr(), Types.INTEGER);
        //System.out.println("xtraCardActiveCouponSelCpnsql:" + xtraCardActiveCouponSelCpnsql);
        return (Long) jdbcTemplate.queryForObject(xtraCardActiveCouponSelCpnsql, xcSelCpnParms, Long.class);
    }

    public String selectPrintStartXtraCardActiveCoupon(XtraCardActiveCoupon xtraCardActiveCoupon) {
        String xtraCardActiveCouponSelCpnsql =
                "SELECT PRNT_START_TSWTZ\n" +
                        "FROM XTRA_CARD_ACTIVE_COUPON\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n" +
                        "AND CMPGN_ID in (\n" +
                        " :CMPGN_ID\n" +
                        ") AND CPN_NBR in (\n" +
                        " :CPN_NBR\n" +
                        ")\n";

        MapSqlParameterSource xcSelCpnParms = new MapSqlParameterSource();
        xcSelCpnParms.addValue("XTRA_CARD_NBR", xtraCardActiveCoupon.getXtraCardNbr(), Types.INTEGER);
        xcSelCpnParms.addValue("CMPGN_ID", xtraCardActiveCoupon.getCmpgnId(), Types.INTEGER);
        xcSelCpnParms.addValue("CPN_NBR", xtraCardActiveCoupon.getCpnNbr(), Types.INTEGER);
        return (String) jdbcTemplate.queryForObject(xtraCardActiveCouponSelCpnsql, xcSelCpnParms, String.class);
    }


    public Date selectPrintEndXtraCardActiveCoupon(XtraCardActiveCoupon xtraCardActiveCoupon) {
        String xtraCardActiveCouponSelCpnsql =
                "SELECT PRNT_END_TSWTZ\n" +
                        "FROM XTRA_CARD_ACTIVE_COUPON\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n" +
                        "AND CMPGN_ID in (\n" +
                        " :CMPGN_ID\n" +
                        ") AND CPN_NBR in (\n" +
                        " :CPN_NBR\n" +
                        ")\n";

        MapSqlParameterSource xcSelCpnParms = new MapSqlParameterSource();
        xcSelCpnParms.addValue("XTRA_CARD_NBR", xtraCardActiveCoupon.getXtraCardNbr(), Types.INTEGER);
        xcSelCpnParms.addValue("CMPGN_ID", xtraCardActiveCoupon.getCmpgnId(), Types.INTEGER);
        xcSelCpnParms.addValue("CPN_NBR", xtraCardActiveCoupon.getCpnNbr(), Types.INTEGER);
        return (Date) jdbcTemplate.queryForObject(xtraCardActiveCouponSelCpnsql, xcSelCpnParms, Date.class);
    }

    public String selectRedeemStartXtraCardActiveCoupon(XtraCardActiveCoupon xtraCardActiveCoupon) {
        String xtraCardActiveCouponSelCpnsql =
                "SELECT REDEEM_START_TSWTZ\n" +
                        "FROM XTRA_CARD_ACTIVE_COUPON\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n" +
                        "AND CMPGN_ID in (\n" +
                        " :CMPGN_ID\n" +
                        ") AND CPN_NBR in (\n" +
                        " :CPN_NBR\n" +
                        ")\n";

        MapSqlParameterSource xcSelCpnParms = new MapSqlParameterSource();
        xcSelCpnParms.addValue("XTRA_CARD_NBR", xtraCardActiveCoupon.getXtraCardNbr(), Types.INTEGER);
        xcSelCpnParms.addValue("CMPGN_ID", xtraCardActiveCoupon.getCmpgnId(), Types.INTEGER);
        xcSelCpnParms.addValue("CPN_NBR", xtraCardActiveCoupon.getCpnNbr(), Types.INTEGER);
        return (String) jdbcTemplate.queryForObject(xtraCardActiveCouponSelCpnsql, xcSelCpnParms, String.class);
    }

    public String selectRedeemEndXtraCardActiveCoupon(XtraCardActiveCoupon xtraCardActiveCoupon) {
        String xtraCardActiveCouponSelCpnsql =
                "SELECT REDEEM_END_TSWTZ\n" +
                        "FROM XTRA_CARD_ACTIVE_COUPON\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n" +
                        "AND CMPGN_ID in (\n" +
                        " :CMPGN_ID\n" +
                        ") AND CPN_NBR in (\n" +
                        " :CPN_NBR\n" +
                        ")\n";

        MapSqlParameterSource xcSelCpnParms = new MapSqlParameterSource();
        xcSelCpnParms.addValue("XTRA_CARD_NBR", xtraCardActiveCoupon.getXtraCardNbr(), Types.INTEGER);
        xcSelCpnParms.addValue("CMPGN_ID", xtraCardActiveCoupon.getCmpgnId(), Types.INTEGER);
        xcSelCpnParms.addValue("CPN_NBR", xtraCardActiveCoupon.getCpnNbr(), Types.INTEGER);
        return (String) jdbcTemplate.queryForObject(xtraCardActiveCouponSelCpnsql, xcSelCpnParms, String.class);
    }
    
    public void createXtraCardSkuRank(XtraCardSkuRank xtraCardSkuRank) {
        String xtraCardSkuRanksql =
                "INSERT INTO XTRA_CARD_SKU_RANK\n" +
                        "(\n" +
                        " XTRA_CARD_NBR,\n" +
                        " RANK_TYPE_CD,\n" +
                        " SKU_NBR,\n" +
                        " SKU_RANK_NBR\n" +
                        ")\n" +
                        " Values \n" +
                        "(\n" +
                        " :XTRA_CARD_NBR,\n" +
                        " :RANK_TYPE_CD,\n" +
                        " :SKU_NBR,\n" +
                        " :SKU_RANK_NBR\n" +
                        ")";

        MapSqlParameterSource xcSkuRankParms = new MapSqlParameterSource();

        xcSkuRankParms.addValue("XTRA_CARD_NBR", xtraCardSkuRank.getXtraCardNbr(), Types.INTEGER);
        xcSkuRankParms.addValue("RANK_TYPE_CD", xtraCardSkuRank.getRankTypeCode(), Types.VARCHAR);
        xcSkuRankParms.addValue("SKU_NBR", xtraCardSkuRank.getSkuNbr(), Types.INTEGER);
        xcSkuRankParms.addValue("SKU_RANK_NBR", xtraCardSkuRank.getSkuRankNbr(), Types.INTEGER);

        int xtraCardSkuRankRowCnt = jdbcTemplate.update(xtraCardSkuRanksql, xcSkuRankParms);
        log.info("Total Number of Cards inserted into XTRA_CARD_SKU_RANK table : [{}]", xtraCardSkuRankRowCnt);
    }
    
    public void createXtraCardRecentlyViewedSku(XtraCardRecentlyViewedSku xtraCardRecentlyViewedSku) {
        String xtraCardRecentlyViewedSkusql =
                "INSERT INTO XC_RECENTLY_VIEWED_SKU\n" +
                        "(\n" +
                        " XTRA_CARD_NBR,\n" +
                        " VIEW_ACTL_DEST_CD,\n" +
                        " LAST_VIEW_SRC_CD,\n" +
                        " SKU_NBR,\n" +
                        " VIEWED_TS,\n" +
                        " TOT_VIEWED_NBR\n" +
                        ")\n" +
                        " Values \n" +
                        "(\n" +
                        " :XTRA_CARD_NBR,\n" +
                        " :VIEW_ACTL_DEST_CD,\n" +
                        " :LAST_VIEW_SRC_CD,\n" +
                        " :SKU_NBR,\n" +
                        " :VIEWED_TS,\n" +
                        " :TOT_VIEWED_NBR\n" +
                        ")";

        MapSqlParameterSource xcRecentlyViewedSkuParms = new MapSqlParameterSource();

        xcRecentlyViewedSkuParms.addValue("XTRA_CARD_NBR", xtraCardRecentlyViewedSku.getXtraCardNbr(), Types.INTEGER);
        xcRecentlyViewedSkuParms.addValue("VIEW_ACTL_DEST_CD", xtraCardRecentlyViewedSku.getViewActlDestCd(), Types.VARCHAR);
        xcRecentlyViewedSkuParms.addValue("LAST_VIEW_SRC_CD", xtraCardRecentlyViewedSku.getLastViewSrcCd(), Types.VARCHAR);
        xcRecentlyViewedSkuParms.addValue("SKU_NBR", xtraCardRecentlyViewedSku.getSkuNbr(), Types.INTEGER);
        xcRecentlyViewedSkuParms.addValue("VIEWED_TS", xtraCardRecentlyViewedSku.getViewedTS(), Types.TIMESTAMP);
        xcRecentlyViewedSkuParms.addValue("TOT_VIEWED_NBR", xtraCardRecentlyViewedSku.getTotalViewedNbr(), Types.INTEGER);

        int xtraCardRecentlyViewedSkuRowCnt = jdbcTemplate.update(xtraCardRecentlyViewedSkusql, xcRecentlyViewedSkuParms);
        log.info("Total Number of Cards inserted into XTRA_CARD_SKU_RANK table : [{}]", xtraCardRecentlyViewedSkuRowCnt);
    }
    
    public void createFrequentlyBoughtTogetherSku(FrequentlyBoughtTogetherSku frequentlyBoughtTogetherSku) {
        String frequentlyBoughtTogetherSkusql =
                "INSERT INTO FREQUENTLY_BOUGHT_TOGETHER_SKU\n" +
                        "(\n" +
                        " SKU_NBR,\n" +
                        " ASSOCIATED_SKU_NBR,\n" +
                        " SKU_RANK_NBR\n" +
                        ")\n" +
                        " Values \n" +
                        "(\n" +
                        " :SKU_NBR,\n" +
                        " :ASSOCIATED_SKU_NBR,\n" +
                        " :SKU_RANK_NBR\n" +
                        ")";

        MapSqlParameterSource frequentlyBoughtTogetherSkuparams = new MapSqlParameterSource();

        frequentlyBoughtTogetherSkuparams.addValue("SKU_NBR", frequentlyBoughtTogetherSku.getSkuNbr(), Types.INTEGER);
        frequentlyBoughtTogetherSkuparams.addValue("ASSOCIATED_SKU_NBR", frequentlyBoughtTogetherSku.getAssociatedSkuNbr(), Types.INTEGER);
        frequentlyBoughtTogetherSkuparams.addValue("SKU_RANK_NBR", frequentlyBoughtTogetherSku.getSkuRankNbr(), Types.INTEGER);

        int frequentlyBoughtTogetherSkuRowCnt = jdbcTemplate.update(frequentlyBoughtTogetherSkusql, frequentlyBoughtTogetherSkuparams);
        log.info("Total Number of Cards inserted into XTRA_CARD_SKU_RANK table : [{}]", frequentlyBoughtTogetherSkuRowCnt);
    }
    
    public void createStoreSkuRank(StoreSkuRank storeSkuRank) {
        String storeSkuRanksql =
                "INSERT INTO STORE_SKU_RANK\n" +
                        "(\n" +
                        " STORE_NBR,\n" +
                        " RANK_TYPE_CD,\n" +
                        " SKU_NBR,\n" +
                        " SKU_RANK_NBR\n" +
                        ")\n" +
                        " Values \n" +
                        "(\n" +
                        " :STORE_NBR,\n" +
                        " :RANK_TYPE_CD,\n" +
                        " :SKU_NBR,\n" +
                        " :SKU_RANK_NBR\n" +
                        ")";

        MapSqlParameterSource storeSkuRankParms = new MapSqlParameterSource();

        storeSkuRankParms.addValue("STORE_NBR", storeSkuRank.getStoreNbr(), Types.INTEGER);
        storeSkuRankParms.addValue("RANK_TYPE_CD", storeSkuRank.getRankTypeCode(), Types.VARCHAR);
        storeSkuRankParms.addValue("SKU_NBR", storeSkuRank.getSkuNbr(), Types.INTEGER);
        storeSkuRankParms.addValue("SKU_RANK_NBR", storeSkuRank.getSkuRankNbr(), Types.INTEGER);

        int storeSkuRankRowCnt = jdbcTemplate.update(storeSkuRanksql, storeSkuRankParms);
        log.info("Total Number of Cards inserted into XTRA_CARD_SKU_RANK table : [{}]", storeSkuRankRowCnt);
    }

    /**
     * Delete Test Cards
     */
    public void deleteXtraCards(List<Integer> pXtraCardNbrList) {
        System.out.println("pXtraCardNbrList:" + pXtraCardNbrList);
        // Delete Xtra Card Records
        String xcSql = "DELETE FROM XTRA_CARD WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map xcParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int xcDel = jdbcTemplate.update(xcSql, xcParms);

        log.info("Total Number of records deleted for table xtra_card : [{}] ", xcDel);

        // Delete Xtra Card Records
        String xcsmSql = "DELETE FROM XTRA_CARD_SUMMARY WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map xcsmParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int xcsmDel = jdbcTemplate.update(xcsmSql, xcsmParms);

        log.info("Total Number of records deleted for table xtra_card_summary : [{}] ", xcsmDel);

        // Delete Xtra Card Rewards Summary Records
        String xcrsSql = "DELETE FROM XTRA_CARD_REWARD_SUMMARY WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map xcrsParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int xcrsDel = jdbcTemplate.update(xcrsSql, xcrsParms);

        log.info("Total Number of records deleted for table XTRA_CARD_REWARD_SUMMARY : [{}] ", xcrsDel);


        // Delete Xtra Hot Card Records
        String xhcSql = "DELETE FROM XTRA_HOT_CARD WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map xhcParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int xhcdel = jdbcTemplate.update(xhcSql, xhcParms);

        log.info("Total Number of records deleted for table XTRA_HOT_CARD : [{}] ", xhcdel);


        // Delete XTRA_CARD_ACTIVE_COUPON Records
        String xcacdSql = "DELETE FROM XTRA_CARD_ACTIVE_COUPON WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map xcacdParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int xcacddel = jdbcTemplate.update(xcacdSql, xcacdParms);

        log.info("Total Number of records deleted for table XTRA_CARD_ACTIVE_COUPON : [{}] ", xcacddel);


        // Delete XTRA_CARD_COUPON_AUDIT Records
        String xccadSql = "DELETE FROM XTRA_CARD_COUPON_AUDIT WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map xccadParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int xccaddel = jdbcTemplate.update(xccadSql, xccadParms);

        log.info("Total Number of records deleted for table XTRA_CARD_COUPON_AUDIT : [{}] ", xccaddel);

        // Delete XTRA_CARD_SEGMENT Records
        String xcsSql = "DELETE FROM XTRA_CARD_SEGMENT WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map xcsParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int xcsdel = jdbcTemplate.update(xcsSql, xcsParms);

        log.info("Total Number of records deleted for table XTRA_CARD_SEGMENT : [{}] ", xcacddel);


        // Delete xtra_card_reward_bc_txn_dtl Records
        String xcrbtdSql = "DELETE FROM xtra_card_reward_bc_txn_dtl WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map xcrbtdParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int xcrbtddel = jdbcTemplate.update(xcrbtdSql, xcrbtdParms);

        log.info("Total Number of records deleted for table xtra_card_reward_bc_txn_dtl : [{}] ", xcrbtddel);


        // Delete xtra_card_reward_phr_txn_dtl Records
        String xcrptdSql = "DELETE FROM xtra_card_reward_phr_txn_dtl WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map xcrptdParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int xcrptddel = jdbcTemplate.update(xcrptdSql, xcrptdParms);

        log.info("Total Number of records deleted for table xtra_card_reward_phr_txn_dtl : [{}] ", xcrptddel);


        // Delete xtra_card_reward_qeb_txn_dtl Records
        String xcqebtdSql = "DELETE FROM xtra_card_reward_qeb_txn_dtl WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map xcqebtdParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int xcqebtddel = jdbcTemplate.update(xcqebtdSql, xcqebtdParms);

        log.info("Total Number of records deleted for table xtra_card_reward_phr_txn_dtl : [{}] ", xcqebtddel);


        // Delete xtra_card_Child Records
        String xccSql = "DELETE FROM xtra_card_child WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map xccParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int xccdel = jdbcTemplate.update(xccSql, xccParms);

        log.info("Total Number of records deleted for table xtra_card_child : [{}] ", xccdel);

        
     // Delete xtra_privacy_request Records
		/*
		 * String xprSql =
		 * "DELETE FROM xtra_privacy_request WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";
		 * 
		 * Map xprParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
		 * int xprdel = jdbcTemplate.update(xprSql, xprParms);
		 * 
		 * log.
		 * info("Total Number of records deleted for table xtra_privacy_request : [{}] "
		 * , xprdel);
		 */

        // Delete xtra_card_analytic_Event Records
        String xcaeSql = "DELETE FROM xtra_card_analytic_event WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map xcaeParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int xcaedel = jdbcTemplate.update(xcaeSql, xcaeParms);
        log.info("Total Number of records deleted from table xtra_card_analytic_event : [{}] ", xcaedel);
        
        String xcChild = "DELETE FROM xtra_card_child WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";
        Map xcChildParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int xcChildCount = jdbcTemplate.update(xcChild, xcChildParms);
        
        String xcWelNess = "DELETE FROM xtra_card_wellness_info WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";
        Map xcWellnessParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int xcWellnessCount = jdbcTemplate.update(xcWelNess, xcWellnessParms);
        
        String xtra_card_selected_category = "DELETE FROM xtra_card_selected_category WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";
        Map xcSelcatParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int xcSelcatParmsCount = jdbcTemplate.update(xtra_card_selected_category, xcSelcatParms);
        
        String xtra_customer = "DELETE FROM xtra_customer WHERE CUST_ID="+currentCustId;
        Map xcCustomerParms = Collections.singletonMap("CUSTID", currentCustId);
        int xcCustCount = jdbcTemplate.update(xtra_customer, xcCustomerParms);
        
//        String xcsrSql = "DELETE FROM XTRA_CARD_SKU_RANK WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";
//
//        Map xcsrParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
//        int xcsrDel = jdbcTemplate.update(xcsrSql, xcsrParms);
//
//        log.info("Total Number of records deleted for table XTRA_CARD_SKU_RANK : [{}] ", xcsrDel);


  //      log.info("Total Number of records deleted from table xtra_card_analytic_event : [{}] ", xcaedel);
    }

    public void deleteEmployeeCards(List<Integer> pEmpCardNbrList) {
        // Delete Employee Card Records
        String ecSql = "DELETE FROM EMPLOYEE_CARD WHERE EMP_CARD_NBR IN (:EMP_CARD_NBR)";

        Map ecParms = Collections.singletonMap("EMP_CARD_NBR", pEmpCardNbrList);
        int ecDel = jdbcTemplate.update(ecSql, ecParms);

        log.info("Total Number of records deleted for table EMPLOYEE_CARD : [{}] ", ecDel);
    }
    
    public void deleteXtraCardSkuRanks(List<Integer> xtraCardNbrList) {
    	// Delete Xtra Card SKU Rank Records
        String xcsrSql = "DELETE FROM XTRA_CARD_SKU_RANK WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map xcsrParms = Collections.singletonMap("XTRA_CARD_NBR", xtraCardNbrList);
        int xcsrDel = jdbcTemplate.update(xcsrSql, xcsrParms);

        log.info("Total Number of records deleted for table XTRA_CARD_SKU_RANK : [{}] ", xcsrDel);
    }
    
    public void deleteXtraCardRecentlyViewedSkus(List<Integer> xtraCardNbrList) {
//    	log.info("I'm here");
    	// Delete Xtra Card SKU Rank Records
        String xcsrSql = "DELETE FROM XC_RECENTLY_VIEWED_SKU WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map xcsrParms = Collections.singletonMap("XTRA_CARD_NBR", xtraCardNbrList);
        int xcsrDel = jdbcTemplate.update(xcsrSql, xcsrParms);

        log.info("Total Number of records deleted for table XC_RECENTLY_VIEWED_SKU : [{}] ", xcsrDel);
    }
    
    public void deleteFrequentlyBoughtTogetherSku(List<Integer> skuList) {
//    	log.info("I'm here");
    	// Delete Xtra Card SKU Rank Records
        String fbtsSql = "DELETE FROM FREQUENTLY_BOUGHT_TOGETHER_SKU WHERE SKU_NBR IN (:SKU_NBR)";

        Map fbtsParms = Collections.singletonMap("SKU_NBR", skuList);
        int fbtsDel = jdbcTemplate.update(fbtsSql, fbtsParms);

        log.info("Total Number of records deleted for table FREQUENTLY_BOUGHT_TOGETHER_SKU : [{}] ", fbtsDel);
    }
    
    public void deleteStoreSkuRanks(List<Integer> storeNbrList) {
    	// Delete Store SKU Rank Records
        String storesrSql = "DELETE FROM STORE_SKU_RANK WHERE STORE_NBR IN (:STORE_NBR)";

        Map storesrParms = Collections.singletonMap("STORE_NBR", storeNbrList);
        int storesrDel = jdbcTemplate.update(storesrSql, storesrParms);

        log.info("Total Number of records deleted for table XTRA_CARD_SKU_RANK : [{}] ", storesrDel);
    }

   
    public void updateMaskedXtraCard(MaskedXtraCard xtraCard) {

        String xtraCardUpsql =
                "UPDATE MASKED_XTRA_CARD\n" +
                        " SET XTRA_CARD_SHA2_NBR=\n" +
                        "    :XTRA_CARD_SHA2_NBR\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n";

        MapSqlParameterSource xcUpParms = new MapSqlParameterSource();

        xcUpParms.addValue("XTRA_CARD_NBR", xtraCard.getXtraCardNbr(), Types.INTEGER);
        xcUpParms.addValue("XTRA_CARD_SHA2_NBR", xtraCard.getXtraCardSh2Nbr(), Types.VARCHAR);
        int xtraCardUpRowCnt = jdbcTemplate.update(xtraCardUpsql, xcUpParms);

        log.info("Total Number of Cards Updated into maked_xtraCard table : [{}]", xtraCardUpRowCnt);
    }
    
    public void createMaskedXtraCard(MaskedXtraCard maskedXtraCard) {
        // Create new Xtra Card Record
        String maskedXtraCardsql =
                "Insert into MASKED_XTRA_CARD (XTRA_CARD_NBR,XTRA_CARD_MASK_NBR," +
                        "VISIBLE_IND,XTRA_CARD_SHA1_NBR,XTRA_CARD_SHA2_NBR) " +
                        " Values \n" +
                        "(\n" +
                        " :XTRA_CARD_NBR,\n" +
                        " :XTRA_CARD_MASK_NBR,\n" +
                        " :VISIBLE_IND,\n" +
                        " :XTRA_CARD_SHA1_NBR,\n" +
                        " :XTRA_CARD_SHA2_NBR,\n" +
                        ")";

        MapSqlParameterSource xcParms = new MapSqlParameterSource();

        xcParms.addValue("XTRA_CARD_NBR", maskedXtraCard.getXtraCardNbr(), Types.INTEGER);
        xcParms.addValue("XTRA_CARD_MASK_NBR", maskedXtraCard.getXtraCardMaskNbr(), Types.VARCHAR);
        xcParms.addValue("VISIBLE_IND", "Y", Types.VARCHAR);
        xcParms.addValue("XTRA_CARD_SHA1_NBR", maskedXtraCard.getXtraCardSh1Nbr(), Types.VARCHAR);
        xcParms.addValue("XTRA_CARD_SHA2_NBR", maskedXtraCard.getXtraCardSh2Nbr(), Types.VARCHAR);

        int xtraCardRowCnt = jdbcTemplate.update(maskedXtraCardsql, xcParms);
        log.info("Total Number of Cards inserted into MASKED_XTRA_CARD table : [{}]", xtraCardRowCnt);

    }
    
    public void createXtraCardWellnessInfo(XtraCardWellnessInfo xtraCardWellnessInfo) {
        // Create new Xtra Card Record
        String xtraCardsql =
                "Insert into xtra_card_wellness_info (XTRA_CARD_NBR,WELLNESS_INFO_CD) " +
                        " Values \n" +
                        "(\n" +
                        " :XTRA_CARD_NBR,\n" +
                        " :WELLNESS_INFO_CD\n"+
                        ")";

        MapSqlParameterSource xcParms = new MapSqlParameterSource();

        xcParms.addValue("XTRA_CARD_NBR", xtraCardWellnessInfo.getXtraCardNbr(), Types.INTEGER);
        xcParms.addValue("WELLNESS_INFO_CD", xtraCardWellnessInfo.getWellnessInfoCd(), Types.VARCHAR);

        int xtraCardRowCnt = jdbcTemplate.update(xtraCardsql, xcParms);
        log.info("Total Number of Cards inserted into xtra_card_wellness_info table : [{}]", xtraCardRowCnt);
    }
    
    public void createXtraCardSelectCat(XtraCardSelectCategory xtraCardSelectCategory) {
        // Create new Xtra Card Record
        String xtraCardsql =
                "Insert into xtra_card_selected_category (XTRA_CARD_NBR,SEL_CAT_SEQ_NBR, SEL_CAT_NBR) " +
                        " Values \n" +
                        "(\n" +
                        " :XTRA_CARD_NBR,\n" +
                        " :SEL_CAT_SEQ_NBR,\n"+
                        " :SEL_CAT_NBR\n"+
                        ")";

        MapSqlParameterSource xcParms = new MapSqlParameterSource();

        xcParms.addValue("XTRA_CARD_NBR", xtraCardSelectCategory.getXtraCardNbr(), Types.INTEGER);
        xcParms.addValue("SEL_CAT_SEQ_NBR", xtraCardSelectCategory.getSelectCatSeqNbr(), Types.VARCHAR);
        xcParms.addValue("SEL_CAT_NBR", xtraCardSelectCategory.getSelectCatNbr(), Types.VARCHAR);

        int xtraCardRowCnt = jdbcTemplate.update(xtraCardsql, xcParms);
        log.info("Total Number of Cards inserted into xtra_card_selected_category table : [{}]", xtraCardRowCnt);

    }
    
    public void createXtraCardCustomer(XtraCardCustomer xtraCardCustomer) {
        // Create new Xtra Card Record
        String xtraCardsql =
                "Insert into xtra_customer (CUST_ID,RECRUIT_CRITERIA_CD,HH_NBR,HEAD_OF_HH_IND,CUST_STAT_CD,CUST_STAT_UPDT_DT,CUST_FAV_CAT_NBR,CUST_FAV_WI_CD,HH_CNT) " +
                        " Values \n" +
                        "(\n" +
                        " :CUST_ID,\n" +
                        " :RECRUIT_CRITERIA_CD,\n"+
                        " :HH_NBR,\n"+
                        " :HEAD_OF_HH_IND,\n"+
                        " :CUST_STAT_CD,\n"+
                        " :CUST_STAT_UPDT_DT,\n"+
                        " :CUST_FAV_CAT_NBR,\n"+
                        " :CUST_FAV_WI_CD,\n"+
                        " :HH_CNT\n"+
                        ")";

        MapSqlParameterSource xcParms = new MapSqlParameterSource();

        xcParms.addValue("CUST_ID", xtraCardCustomer.getCustId(), Types.INTEGER);
        xcParms.addValue("RECRUIT_CRITERIA_CD", xtraCardCustomer.getRecruiteCriteriaCd(), Types.VARCHAR);
        xcParms.addValue("HH_NBR", xtraCardCustomer.getHhNbr(), Types.VARCHAR);
        xcParms.addValue("HEAD_OF_HH_IND", xtraCardCustomer.getHeadOfHHInd(), Types.VARCHAR);
        xcParms.addValue("CUST_STAT_CD", xtraCardCustomer.getCustStatCd(), Types.VARCHAR);
        xcParms.addValue("CUST_STAT_UPDT_DT", xtraCardCustomer.getCustStatUpdtDt(), Types.DATE);
        xcParms.addValue("CUST_FAV_CAT_NBR", xtraCardCustomer.getCustFavCatNbr(), Types.VARCHAR);
        xcParms.addValue("CUST_FAV_WI_CD", xtraCardCustomer.getCustFavWiCd(), Types.VARCHAR);
        xcParms.addValue("HH_CNT", xtraCardCustomer.getHhCnt(), Types.VARCHAR);

        int xtraCardRowCnt = jdbcTemplate.update(xtraCardsql, xcParms);
        log.info("Total Number of Cards inserted into xtra_customer table : [{}]", xtraCardRowCnt);

    }
    

    /**
     * Delete Test Cards
     */
    public void deleteMaskedAndXtraCards(List<Integer> pXtraCardNbrList) {
        System.out.println("pXtraCardNbrList:" + pXtraCardNbrList);
        // Delete Masked Xtra Card Records
        String mxcSql = "DELETE FROM MASKED_XTRA_CARD WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";
        Map mxcParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int mxcDel = jdbcTemplate.update(mxcSql, mxcParms);
        log.info("Total Number of records deleted for table xtra_card : [{}] ", mxcDel);

        String xcSql = "DELETE FROM XTRA_CARD_SUMMARY WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";
        Map xcParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int xcDel = jdbcTemplate.update(xcSql, xcParms);
        log.info("Total Number of records deleted for table xtra_card : [{}] ", xcDel);
    }
    
    public List<Map<String, Object>> selectDataFromXtraCardTable(List<Integer> pXtracardNbrs, String tableName) {
    	int xtraCardNbr = pXtracardNbrs.get(0);
    	String customerRecord ="SELECT * FROM "+tableName+" WHERE xtra_card_nbr = "+xtraCardNbr;
    	
      
    	MapSqlParameterSource mvSelParms = new MapSqlParameterSource();
        mvSelParms.addValue("xtra_card_nbr", xtraCardNbr, Types.INTEGER);
        return jdbcTemplate.queryForList(customerRecord, mvSelParms);
    }
    
    public Integer getCountOfRecordsInXtraCardTable(String tableName, int xtraCardNumber) {
        String recordCount =
                "SELECT COUNT(*) FROM "+tableName+" WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";
        MapSqlParameterSource mvSelParms = new MapSqlParameterSource();
        mvSelParms.addValue("XTRA_CARD_NBR", xtraCardNumber, Types.INTEGER);
        return (Integer) jdbcTemplate.queryForObject(recordCount, mvSelParms, Integer.class);
    }

    public List<XtraPrivacyRequest> getData_XtraPrivacyRequest(String xtraCardNbr) {
        String xtraCardsql =
                "SELECT * FROM XTRA_PRIVACY_REQUEST WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("XTRA_CARD_NBR", xtraCardNbr, Types.INTEGER);
        return jdbcTemplate.query(xtraCardsql, mapSqlParameterSource, new BeanPropertyRowMapper<>(XtraPrivacyRequest.class));
    }

    // Column_name = CARD_LAST_SCAN_DT
    public void updateXtraCard_NEW(JSONObject jsonObject, String columnName, String value) {
        String xtraCardNbr = jsonObject.get("XTRA_CARD_NBR").toString();
        String xtraCardsql;
        DateTime dateTime = new DateTime();
        if(value.equalsIgnoreCase("null")){
            value = "";
        } else {
            value = dateTime.toString("dd-MMM-yy");
        }
        xtraCardsql =
                "update XTRA_CARD set " + columnName  + " = \'" + value + "\' " +
                        "where XTRA_CARD_NBR in (:XTRA_CARD_NBR)";

        MapSqlParameterSource xcParms = new MapSqlParameterSource();
        xcParms.addValue("XTRA_CARD_NBR", xtraCardNbr, Types.INTEGER);
        try {
            int xtraCardRowCnt = jdbcTemplate.update(xtraCardsql, xcParms);
            log.info("[{}] column updated for an XTRA_CARD_NBR : [{}] in XTRA_CARD table. Updated Rows : [{}]",
                    columnName.toUpperCase(), xtraCardNbr, xtraCardRowCnt);
        } catch (Exception e) {
            log.info("Exception - " + e);
            throw e;
        }
    }
    
    public void updateXtraCard_mktgTypeBenefits(XtraCard xtraCard, String columnName, String value) {
        // Create new Xtra Card Record
        String xtraCardsql =
        		"update XTRA_CARD set " + columnName  + " = \'" + value + "\' " +
                        "where XTRA_CARD_NBR in (:XTRA_CARD_NBR)";

        MapSqlParameterSource xcParms = new MapSqlParameterSource();
        xcParms.addValue("XTRA_CARD_NBR", xtraCard.getXtraCardNbr(), Types.INTEGER);

        int xtraCardRowCnt = jdbcTemplate.update(xtraCardsql, xcParms);
        log.info("Total Number of Cards updated into xtra_customer table : [{}]", xtraCardRowCnt);

    }

    public List<Map<String, Object>> getXtraCardDetails(int xtraCardNumber, String tableName) {
        String sqlQuery =
                "SELECT * FROM " + tableName + " WHERE XTRA_CARD_NBR = " + xtraCardNumber;

        MapSqlParameterSource  mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("XTRA_CARD_NBR", xtraCardNumber, Types.INTEGER);
        return jdbcTemplate.queryForList(sqlQuery, mapSqlParameterSource);
    }

    public String getData_XtraCard_table(String xtraCardNbr, String columnName) {
        String xtraCardsql =
                "SELECT "+ columnName + " FROM XTRA_CARD where XTRA_CARD_NBR in (:XTRA_CARD_NBR)";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("XTRA_CARD_NBR", xtraCardNbr, Types.INTEGER);
        return jdbcTemplate.queryForObject(xtraCardsql, mapSqlParameterSource, String.class);
    }

    public void createXtraCard_NEW(XtraCard xtraCard) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(defaultTransactionDefinition);
        // Create new Xtra Card Record
        String xtraCardsql =
                "Insert into XTRA_CARD(" +
                    "XTRA_CARD_NBR, CUST_ID, TRGT_GEO_MKT_CD, ENROLL_SRC_CD, SURVEY_COMPLETION_DT, CARD_MBR_DT, " +
                    "CARD_STAT_CD, MKTG_TYPE_CD, ENCODED_XTRA_CARD_NBR, LAST_UPDT_SRC_CD, LAST_UPDT_DT, LAST_UPDT_BY_ID, CARD_LAST_SCAN_DT) \n" +
                "values (" +
                    ":XTRA_CARD_NBR, :CUST_ID, :TRGT_GEO_MKT_CD, :ENROLL_SRC_CD, :SURVEY_COMPLETION_DT, :CARD_MBR_DT, " +
                    ":CARD_STAT_CD, :MKTG_TYPE_CD, :ENCODED_XTRA_CARD_NBR, :LAST_UPDT_SRC_CD, :LAST_UPDT_DT, :LAST_UPDT_BY_ID, :CARD_LAST_SCAN_DT)";

        MapSqlParameterSource xcParms = new MapSqlParameterSource();
        currentCustId = xtraCard.getCustId();
        xcParms.addValue("XTRA_CARD_NBR", xtraCard.getXtraCardNbr(), Types.INTEGER);
        xcParms.addValue("CUST_ID", xtraCard.getCustId(), Types.INTEGER);
        xcParms.addValue("TRGT_GEO_MKT_CD", xtraCard.getTrgtGeoMktCd(), Types.LONGNVARCHAR);
        xcParms.addValue("ENROLL_SRC_CD", xtraCard.getEnrollSrcCd(), Types.LONGVARCHAR);
        xcParms.addValue("SURVEY_COMPLETION_DT", xtraCard.getSurveyCompletionDt(), Types.DATE);
        xcParms.addValue("CARD_MBR_DT", xtraCard.getCardMbrDt(), Types.DATE);
        xcParms.addValue("CARD_STAT_CD", xtraCard.getCardStatCd(), Types.CHAR);
        xcParms.addValue("MKTG_TYPE_CD", xtraCard.getMktgTypeCd(), Types.LONGVARCHAR);
        xcParms.addValue("ENCODED_XTRA_CARD_NBR", xtraCard.getEncodedXtraCardNbr2(), Types.DOUBLE);
        xcParms.addValue("LAST_UPDT_SRC_CD", xtraCard.getLastUpdtSrcCd(), Types.VARCHAR);
        xcParms.addValue("LAST_UPDT_DT", xtraCard.getLastUpdtDt(), Types.DATE);
        xcParms.addValue("LAST_UPDT_BY_ID", xtraCard.getLastUpdtById(), Types.VARCHAR);
        xcParms.addValue("CARD_LAST_SCAN_DT", xtraCard.getCardLastScanDt(), Types.DATE);

        try {
            int xtraCardRowCnt = jdbcTemplate.update(xtraCardsql, xcParms);
            platformTransactionManager.commit(transactionStatus);
            log.info("\nCards inserted into XTRA_CARD table : [{}], Xtra_card_nbr : [{}]",
                    xtraCardRowCnt, xtraCard.getXtraCardNbr());
        } catch (Exception e) {
            platformTransactionManager.rollback(transactionStatus);
            log.info("Exception - " + e);
            throw e;
        }
    }

    public void createMaskedXtraCard_NEW(MaskedXtraCard maskedXtraCard) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(defaultTransactionDefinition);

        // Create new Xtra Card Record
        String maskedXtraCardsql =
                "Insert into MASKED_XTRA_CARD (" +
                     "XTRA_CARD_NBR, XTRA_CARD_MASK_NBR, VISIBLE_IND, XTRA_CARD_SHA1_NBR, XTRA_CARD_SHA2_NBR) " +
                "values (" +
                     ":XTRA_CARD_NBR, :XTRA_CARD_MASK_NBR, :VISIBLE_IND, :XTRA_CARD_SHA1_NBR, :XTRA_CARD_SHA2_NBR)";

        MapSqlParameterSource xcParms = new MapSqlParameterSource();
        xcParms.addValue("XTRA_CARD_NBR", maskedXtraCard.getXtraCardNbr(), Types.INTEGER);
        xcParms.addValue("XTRA_CARD_MASK_NBR", maskedXtraCard.getXtraCardMaskNbr(), Types.VARCHAR);
        xcParms.addValue("VISIBLE_IND", maskedXtraCard.getVisibleIndicator(), Types.CHAR);
        xcParms.addValue("XTRA_CARD_SHA1_NBR", maskedXtraCard.getXtraCardSh1Nbr(), Types.VARCHAR);
        xcParms.addValue("XTRA_CARD_SHA2_NBR", maskedXtraCard.getXtraCardSh2Nbr(), Types.VARCHAR);

        try {
            int xtraCardRowCnt = jdbcTemplate.update(maskedXtraCardsql, xcParms);
            platformTransactionManager.commit(transactionStatus);
            log.info("\nCards inserted into MASKED_XTRA_CARD table : [{}], Xtra_card_nbr : [{}]",
                    xtraCardRowCnt, maskedXtraCard.getXtraCardNbr());
        } catch (Exception e) {
            platformTransactionManager.rollback(transactionStatus);
            log.info("Exception - " + e);
            throw e;
        }
    }

    public void createXtraCardCustomer_NEW(XtraCardCustomer xtraCardCustomer) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(defaultTransactionDefinition);

        // Create new record in XtraCardCustomer table
        String xtraCardCustomerSql =
                "Insert into XTRA_CUSTOMER (" +
                     "CUST_ID, HH_NBR, HEAD_OF_HH_IND, CUST_STAT_CD, CUST_STAT_UPDT_DT) " +
                "values (" +
                     ":CUST_ID, :HH_NBR, :HEAD_OF_HH_IND, :CUST_STAT_CD, :CUST_STAT_UPDT_DT)";

        MapSqlParameterSource xcParms = new MapSqlParameterSource();
        xcParms.addValue("CUST_ID", xtraCardCustomer.getCustId(), Types.INTEGER);
        xcParms.addValue("HH_NBR", xtraCardCustomer.getHhNbr(), Types.INTEGER);
        xcParms.addValue("HEAD_OF_HH_IND", xtraCardCustomer.getHeadOfHHInd(), Types.CHAR);
        xcParms.addValue("CUST_STAT_CD", xtraCardCustomer.getCustStatCd(), Types.CHAR);
        xcParms.addValue("CUST_STAT_UPDT_DT", xtraCardCustomer.getCustStatUpdtDt(), Types.DATE);

        try {
            int xtraCardRowCnt = jdbcTemplate.update(xtraCardCustomerSql, xcParms);
            platformTransactionManager.commit(transactionStatus);
            log.info("\nCards inserted into XTRA_CUSTOMER table : [{}], CUST_ID : [{}]",
                    xtraCardRowCnt, xtraCardCustomer.getCustId());
        } catch (Exception e) {
            platformTransactionManager.rollback(transactionStatus);
            log.info("Exception - " + e);
            throw e;
        }
    }

    public void deleteXtraCard_NEW(List<Integer> xtraCardNbr, String tableName) {
        // Delete XTRA_CARD from tableName
        try {
            String Sql = "DELETE FROM " + tableName + " WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";
            Map xcParms = Collections.singletonMap("XTRA_CARD_NBR", xtraCardNbr);
            int deleted_count = jdbcTemplate.update(Sql, xcParms);
            log.info("\nDELETED XTRA_CARD: {} from {} table. Deleted Cust_Id Count : {}", xtraCardNbr, tableName, deleted_count);
        } catch (Exception e) {
            log.info("Exception - " + e);
            throw e;
        }
    }

    public void deleteXtraCardCustomer_NEW(List<Integer> custId) {
        // Delete XTRA_CARD_NBR from XTRA_CARD table
        try {
            String Sql = "DELETE FROM XTRA_CUSTOMER WHERE CUST_ID IN (:CUST_ID)";
            Map xcParms = Collections.singletonMap("CUST_ID", custId);
            int deleted_count = jdbcTemplate.update(Sql, xcParms);
            log.info("\nDELETED CUST_ID: {} from XTRA_CUSTOMER table. Deleted CUST_ID Count : {}.", custId, deleted_count);
        } catch (Exception e) {
            log.info("Exception - " + e);
            throw e;
        }
    }

    public void delete_xtraCard(List<Integer> xtraCardNbrList, List<Integer> custIdList) {
        deleteXtraCard_NEW(xtraCardNbrList, "XTRA_CARD");
        deleteXtraCard_NEW(xtraCardNbrList, "MASKED_XTRA_CARD");
        deleteXtraCard_NEW(xtraCardNbrList, "XTRA_CARD_SUMMARY");
        deleteXtraCard_NEW(xtraCardNbrList, "XTRA_CARD_ACTIVE_COUPON");
        deleteXtraCard_NEW(xtraCardNbrList, "XTRA_CARD_COUPON_AUDIT");
        deleteXtraCard_NEW(xtraCardNbrList, "DIGITIZE_COUPON_AUDIT");
        deleteXtraCard_NEW(xtraCardNbrList, "PUSH_NOTIF_AUDIT");
        deleteXtraCard_NEW(xtraCardNbrList, "XTRA_PRIVACY_REQUEST");
        deleteXtraCardCustomer_NEW(custIdList);
    }
}
