package com.cvs.crm.repository;

import com.cvs.crm.model.data.XtraCardSummaryData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class XtraCardSummaryDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Create Xtra Card Summary
     */
    public void createXtraCard(XtraCardSummaryData xtraCardSummaryData) {

        // Create new Xtra Card Record
        String xtraCardsql =
                "INSERT INTO XTRA_CARD\n" +
                        "(\n" +
                        " XTRA_CARD_NBR,\n" +
                        " CUST_ID,\n" +
                        " CARD_MBR_DT,\n" +
                        " TOT_YTD_SAVE_AMT,\n" +
                        " MKTG_TYPE_CD,\n" +
                        " TOT_LIFETIME_SAVE_AMT\n" +
                        ")\n" +
                        " Values \n" +
                        "(\n" +
                        " :XTRA_CARD_NBR,\n" +
                        " :CUST_ID,\n" +
                        " :CARD_MBR_DT,\n" +
                        " :TOT_YTD_SAVE_AMT,\n" +
                        " :MKTG_TYPE_CD,\n" +
                        " :TOT_LIFETIME_SAVE_AMT\n" +
                        ")";

        MapSqlParameterSource xcParms = new MapSqlParameterSource();

        xcParms.addValue("XTRA_CARD_NBR", xtraCardSummaryData.getXtraCardNbr(), Types.INTEGER);
        xcParms.addValue("CUST_ID", xtraCardSummaryData.getCustId(), Types.INTEGER);
        xcParms.addValue("TOT_YTD_SAVE_AMT", xtraCardSummaryData.getTotYtdSaveAmt(), Types.DOUBLE);
        xcParms.addValue("TOT_LIFETIME_SAVE_AMT", xtraCardSummaryData.getTotLifetimeSaveAmt(), Types.DOUBLE);
        xcParms.addValue("CARD_MBR_DT", xtraCardSummaryData.getCardMbrDt(), Types.DATE);
        xcParms.addValue("MKTG_TYPE_CD", xtraCardSummaryData.getMktgTypeCd(), Types.VARCHAR);

        int xtraCardRowCnt = jdbcTemplate.update(xtraCardsql, xcParms);
        log.info("Total Number of Cards inserted into XTRA_CARD table : [{}]", xtraCardRowCnt);

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

        custParms.addValue("CUST_ID", xtraCardSummaryData.getCustId(), Types.INTEGER);
        custParms.addValue("GNDR_CD", xtraCardSummaryData.getGndrCd(), Types.VARCHAR);
        custParms.addValue("FIRST_NAME", xtraCardSummaryData.getFirstName(), Types.VARCHAR);
        custParms.addValue("LAST_NAME", xtraCardSummaryData.getLastName(), Types.VARCHAR);

        int custRowCnt = jdbcTemplate.update(customerSql, custParms);
        log.info("Total Number of Cards inserted into CUSTOMER table : [{}]", custRowCnt);

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
        xcrsParms.addValue("XTRA_CARD_NBR", xtraCardSummaryData.getXtraCardNbr(), Types.INTEGER);
        xcrsParms.addValue("TOT_EB_AVL_AMT", xtraCardSummaryData.getTotEbAvlAmt(), Types.DOUBLE);
        int smryRowCnt = jdbcTemplate.update(xcrsSql, xcrsParms);

        log.info("Total Number of Cards inserted into XTRA_CARD_REWARD_SUMMARY table : [{}]", smryRowCnt);
    }

    /**
     * Create Xtra Hot Card
     */
    public void createXtraHotCard(XtraCardSummaryData xtraCardSummaryData) {
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

        xhcParms.addValue("XTRA_CARD_NBR", xtraCardSummaryData.getXtraCardNbr(), Types.INTEGER);
        xhcParms.addValue("ADDED_DT", xtraCardSummaryData.getAddedDt(), Types.DATE);
        int hcRowCnt = jdbcTemplate.update(xhcSql, xhcParms);

        log.info("Total Number of Cards inserted into xtra_hot_card table : [{}]", hcRowCnt);
    }

    /**
     * Delete Test Cards
     */
    public void deleteXtraCards(List<Integer> pXtraCardNbrList, List<Integer> pCustIdList) {
        // Delete Xtra Card Records
        String xcSql = "DELETE FROM XTRA_CARD WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map xcParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int xcDel = jdbcTemplate.update(xcSql, xcParms);

        log.info("Total Number of records deleted for table xtra_card : [{}] ", xcDel);

        // Delete Customer Records
        String custSql = "DELETE FROM CUSTOMER WHERE CUST_ID IN (:CUST_ID)";

        Map custParms = Collections.singletonMap("CUST_ID", pCustIdList);
        int custDel = jdbcTemplate.update(custSql, custParms);

        log.info("Total Number of records deleted for table Customer : [{}] ", custDel);

        // Delete Xtra Card Rewards Summary Records
        String xcrsSql = "DELETE FROM XTRA_CARD_REWARD_SUMMARY WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map xcrsParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int xcsDel = jdbcTemplate.update(xcrsSql, xcrsParms);

        log.info("Total Number of records deleted for table XTRA_CARD_REWARD_SUMMARY : [{}] ", xcsDel);


        // Delete Xtra Hot Card Records
        String xhcSql = "DELETE FROM XTRA_HOT_CARD WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map xhcParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int xhcdel = jdbcTemplate.update(xhcSql, xhcParms);

        log.info("Total Number of records deleted for table XTRA_HOT_CARD : [{}] ", xhcdel);
    }
}
