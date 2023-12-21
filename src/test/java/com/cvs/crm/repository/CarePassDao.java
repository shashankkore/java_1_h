package com.cvs.crm.repository;

import com.cvs.crm.model.data.*;
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
public class CarePassDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Create Care Pass Member Smry
     */
    public void createCarePassMemberSmry(CarepassMemberSmry carepassMemberSmry) {
        // Create new Care Pass Member Smry
        String carepassMemberSmrysql =
                "INSERT INTO CAREPASS_MEMBER_SMRY\n" +
                        "(\n" +
                        " XTRA_CARD_NBR,\n" +
                        " ENROLL_TSWTZ,\n" +
                        " CUR_STATUS,\n" +
                        " CUR_PLAN_TYPE,\n" +
                        " EXPIRE_TSWTZ,\n" +
                        " BENEFIT_ELIGIBILITY_DT,\n" +
                        " NEXT_REWARD_ISSUE_DT,\n" +
                        " MEMBER_ENROLL_PRICE_TYPE_CD\n" +
                        ")\n" +
                        " Values \n" +
                        "(\n" +
                        " :XTRA_CARD_NBR,\n" +
                        " :ENROLL_TSWTZ,\n" +
                        " :CUR_STATUS,\n" +
                        " :CUR_PLAN_TYPE,\n" +
                        " :EXPIRE_TSWTZ,\n" +
                        " :BENEFIT_ELIGIBILITY_DT,\n" +
                        " :NEXT_REWARD_ISSUE_DT,\n" +
                        " :MEMBER_ENROLL_PRICE_TYPE_CD\n" +
                        ")";

        MapSqlParameterSource cpmsParms = new MapSqlParameterSource();

        cpmsParms.addValue("XTRA_CARD_NBR", carepassMemberSmry.getXtrCardNbr(), Types.INTEGER);
        cpmsParms.addValue("ENROLL_TSWTZ", carepassMemberSmry.getEnrollTswtz(), Types.DATE);
        cpmsParms.addValue("CUR_STATUS", carepassMemberSmry.getCurStatus(), Types.VARCHAR);
        cpmsParms.addValue("CUR_PLAN_TYPE", carepassMemberSmry.getCurPlanType(), Types.VARCHAR);
        cpmsParms.addValue("EXPIRE_TSWTZ", carepassMemberSmry.getExpireTswtz(), Types.DATE);
        cpmsParms.addValue("BENEFIT_ELIGIBILITY_DT", carepassMemberSmry.getBenefitEligibilityDt(), Types.DATE);
        cpmsParms.addValue("NEXT_REWARD_ISSUE_DT", carepassMemberSmry.getNextRewardIssueDt(), Types.DATE);
        cpmsParms.addValue("MEMBER_ENROLL_PRICE_TYPE_CD", carepassMemberSmry.getMemberEnrollPriceTypeCd(), Types.VARCHAR);
        int cpmsRowCnt = jdbcTemplate.update(carepassMemberSmrysql, cpmsParms);
        log.info("Total Number of Cards inserted into CAREPASS_MEMBER_SMRY table : [{}]", cpmsRowCnt);
    }

    public void updateCarePassMemberSmry(CarepassMemberSmry carepassMemberSmry) {
        // update new Care Pass Member Smry
        String carepassMemberSmryUpsql =
                "UPDATE CAREPASS_MEMBER_SMRY\n" +
                        " SET ENROLL_TSWTZ=\n" +
                        "    :ENROLL_TSWTZ,\n" +
                        " EXPIRE_TSWTZ=\n" +
                        "    :EXPIRE_TSWTZ,\n" +
                        " BENEFIT_ELIGIBILITY_DT=\n" +
                        "    :BENEFIT_ELIGIBILITY_DT,\n" +
                        " NEXT_REWARD_ISSUE_DT=\n" +
                        "    :NEXT_REWARD_ISSUE_DT,\n" +
                        " CUR_STATUS=\n" +
                        "    :CUR_STATUS,\n" +
                        " CUR_PLAN_TYPE=\n" +
                        "    :CUR_PLAN_TYPE\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n";

        MapSqlParameterSource cpmsUpParms = new MapSqlParameterSource();

        cpmsUpParms.addValue("XTRA_CARD_NBR", carepassMemberSmry.getXtrCardNbr(), Types.INTEGER);
        cpmsUpParms.addValue("ENROLL_TSWTZ", carepassMemberSmry.getEnrollTswtz(), Types.DATE);
        cpmsUpParms.addValue("EXPIRE_TSWTZ", carepassMemberSmry.getExpireTswtz(), Types.DATE);
        cpmsUpParms.addValue("BENEFIT_ELIGIBILITY_DT", carepassMemberSmry.getBenefitEligibilityDt(), Types.DATE);
        cpmsUpParms.addValue("NEXT_REWARD_ISSUE_DT", carepassMemberSmry.getNextRewardIssueDt(), Types.DATE);
        cpmsUpParms.addValue("CUR_PLAN_TYPE", carepassMemberSmry.getCurPlanType(), Types.VARCHAR);
        cpmsUpParms.addValue("CUR_STATUS", carepassMemberSmry.getCurStatus(), Types.VARCHAR);
        int cpmsUpRowCnt = jdbcTemplate.update(carepassMemberSmryUpsql, cpmsUpParms);
        log.info("Total Number of Cards updated into CAREPASS_MEMBER_SMRY table : [{}]", cpmsUpRowCnt);
    }


    public void createCarepassEnrollFormHist(CarepassEnrollFormHist carepassEnrollFormHist) {
        // Create new Carepass Enroll Form Hist
        String cpefhSql =
                "INSERT INTO CAREPASS_ENROLL_FORM_HIST (\n" +
                        "    XTRA_CARD_NBR,\n" +
                        "    ACTION_TSWTZ,\n" +
                        "    ACTION_TSWTZ_SET_DT,\n" +
                        "    PYMT_PLAN_DUR,\n" +
                        "    ENROLL_SRC_CD,\n" +
                        "    ENROLL_STORE_NBR,\n" +
                        "    MEMBER_ENROLL_PRICE_TYPE_CD\n" +
                        ") VALUES (\n" +
                        "    :XTRA_CARD_NBR,\n" +
                        "    :ACTION_TSWTZ,\n" +
                        "    :ACTION_TSWTZ_SET_DT,\n" +
                        "    :PYMT_PLAN_DUR,\n" +
                        "    :ENROLL_SRC_CD,\n" +
                        "    :ENROLL_STORE_NBR,\n" +
                        "    :MEMBER_ENROLL_PRICE_TYPE_CD\n" +
                        ")";

        MapSqlParameterSource cpefhParms = new MapSqlParameterSource();
        cpefhParms.addValue("XTRA_CARD_NBR", carepassEnrollFormHist.getXtraCardNbr(), Types.INTEGER);
        cpefhParms.addValue("ACTION_TSWTZ", carepassEnrollFormHist.getActionTswtz(), Types.DATE);
        cpefhParms.addValue("ACTION_TSWTZ_SET_DT", carepassEnrollFormHist.getActionTswtzSetDt(), Types.DATE);
        cpefhParms.addValue("PYMT_PLAN_DUR", carepassEnrollFormHist.getPymtPlanDur(), Types.INTEGER);
        cpefhParms.addValue("ENROLL_SRC_CD", carepassEnrollFormHist.getEnrollSrcCd(), Types.VARCHAR);
        cpefhParms.addValue("ENROLL_STORE_NBR", carepassEnrollFormHist.getEnrollStoreNbr(), Types.INTEGER);
        cpefhParms.addValue("MEMBER_ENROLL_PRICE_TYPE_CD", carepassEnrollFormHist.getMemberEnrollPriceTypeCd(), Types.VARCHAR);
        int cpefhRowCnt = jdbcTemplate.update(cpefhSql, cpefhParms);
        log.info("Total Number of Cards inserted into CAREPASS_ENROLL_FORM_HIST table : [{}]", cpefhRowCnt);
    }

    public void updateCarepassEnrollFormHist(CarepassEnrollFormHist carepassEnrollFormHist) {
        // Update new Carepass Enroll Form Hist
        String cpefhUpSql =
                "UPDATE CAREPASS_ENROLL_FORM_HIST\n" +
                        " SET ACTION_TSWTZ=\n" +
                        "    :ACTION_TSWTZ,\n" +
                        " ACTION_TSWTZ_SET_DT=\n" +
                        "    :ACTION_TSWTZ_SET_DT,\n" +
                        " PYMT_PLAN_DUR=\n" +
                        " 	:PYMT_PLAN_DUR\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n";


        MapSqlParameterSource cpefhUpParms = new MapSqlParameterSource();
        cpefhUpParms.addValue("XTRA_CARD_NBR", carepassEnrollFormHist.getXtraCardNbr(), Types.INTEGER);
        cpefhUpParms.addValue("ACTION_TSWTZ", carepassEnrollFormHist.getActionTswtz(), Types.DATE);
        cpefhUpParms.addValue("ACTION_TSWTZ_SET_DT", carepassEnrollFormHist.getActionTswtzSetDt(), Types.DATE);
        cpefhUpParms.addValue("PYMT_PLAN_DUR", carepassEnrollFormHist.getPymtPlanDur(), Types.INTEGER);
        int cpefhUpRowCnt = jdbcTemplate.update(cpefhUpSql, cpefhUpParms);
        log.info("Total Number of Cards Updated into CAREPASS_ENROLL_FORM_HIST table : [{}]", cpefhUpRowCnt);
    }


    /**
     * Create Carepass Member Status Hist
     */
    public void createCarepassMemberStatusHist(CarepassMemberStatusHist carepassMemberStatusHist) {
        // Create new Carepass Member Status Hist
        String cpmshSql =
                "INSERT INTO CAREPASS_MEMBER_STATUS_HIST (\n" +
                        "XTRA_CARD_NBR,\n" +
                        "ACTION_TSWTZ,\n" +
                        "ACTION_TSWTZ_SET_DT,\n" +
                        "MBR_STATUS_CD,\n" +
                        "MBR_STATUS_RSN_CD\n" +
                        ") VALUES (\n" +
                        ":XTRA_CARD_NBR,\n" +
                        ":ACTION_TSWTZ,\n" +
                        ":ACTION_TSWTZ_SET_DT,\n" +
                        ":MBR_STATUS_CD,\n" +
                        ":MBR_STATUS_RSN_CD\n" +
                        "\n" +
                        ")";

        MapSqlParameterSource cpmshParms = new MapSqlParameterSource();

        cpmshParms.addValue("XTRA_CARD_NBR", carepassMemberStatusHist.getXtraCardNbr(), Types.INTEGER);
        cpmshParms.addValue("ACTION_TSWTZ", carepassMemberStatusHist.getActionTswtz(), Types.DATE);
        cpmshParms.addValue("ACTION_TSWTZ_SET_DT", carepassMemberStatusHist.getActionTswtzSetDt(), Types.DATE);
        cpmshParms.addValue("MBR_STATUS_CD", carepassMemberStatusHist.getMbrStatusCd(), Types.VARCHAR);
        cpmshParms.addValue("MBR_STATUS_RSN_CD", carepassMemberStatusHist.getMbrStatusRsnCd(), Types.VARCHAR);

        int cpmshRowCnt = jdbcTemplate.update(cpmshSql, cpmshParms);

        log.info("Total Number of Cards inserted into CAREPASS_MEMBER_STATUS_HIST table : [{}]", cpmshRowCnt);
    }


    /**
     * Update Carepass Member Status Hist
     */
    public void UpdateCarepassMemberStatusHist(CarepassMemberStatusHist carepassMemberStatusHist) {
        // Create new Carepass Member Status Hist
        String cpmshUpSql =
                "UPDATE CAREPASS_MEMBER_STATUS_HIST\n" +
                        " SET ACTION_TSWTZ=\n" +
                        "    :ACTION_TSWTZ,\n" +
                        " ACTION_TSWTZ_SET_DT=\n" +
                        "    :ACTION_TSWTZ_SET_DT,\n" +
                        " MBR_STATUS_CD=\n" +
                        "    :MBR_STATUS_CD\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n";
        MapSqlParameterSource cpmshUpParms = new MapSqlParameterSource();

        cpmshUpParms.addValue("XTRA_CARD_NBR", carepassMemberStatusHist.getXtraCardNbr(), Types.INTEGER);
        cpmshUpParms.addValue("ACTION_TSWTZ", carepassMemberStatusHist.getActionTswtz(), Types.DATE);
        cpmshUpParms.addValue("ACTION_TSWTZ_SET_DT", carepassMemberStatusHist.getActionTswtzSetDt(), Types.DATE);
        cpmshUpParms.addValue("MBR_STATUS_CD", carepassMemberStatusHist.getMbrStatusCd(), Types.VARCHAR);

        int cpmshUpRowCnt = jdbcTemplate.update(cpmshUpSql, cpmshUpParms);

        log.info("Total Number of Cards updated into CAREPASS_MEMBER_STATUS_HIST table : [{}]", cpmshUpRowCnt);
    }


    public String selectCarepassMemberSmryStat(CarepassMemberSmry carepassMemberSmry) {
        String carepassMemberSmrySelStatsql =
                "SELECT CUR_STATUS\n" +
                        "FROM CAREPASS_MEMBER_SMRY\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n";

        MapSqlParameterSource cpmsStatParms = new MapSqlParameterSource();
        cpmsStatParms.addValue("XTRA_CARD_NBR", carepassMemberSmry.getXtrCardNbr(), Types.INTEGER);
        return (String) jdbcTemplate.queryForObject(carepassMemberSmrySelStatsql, cpmsStatParms, String.class);
    }

    public String selectCarepassMemberSmryBEdt(CarepassMemberSmry carepassMemberSmry) {
        String carepassMemberSmrySelBEdtsql =
                "SELECT BENEFIT_ELIGIBILITY_DT\n" +
                        "FROM CAREPASS_MEMBER_SMRY\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n";

        MapSqlParameterSource cpmsBEdtParms = new MapSqlParameterSource();
        cpmsBEdtParms.addValue("XTRA_CARD_NBR", carepassMemberSmry.getXtrCardNbr(), Types.INTEGER);
        return (String) jdbcTemplate.queryForObject(carepassMemberSmrySelBEdtsql, cpmsBEdtParms, String.class);
    }

    public String selectcarepassMemberStatusHistStat(CarepassMemberStatusHist carepassMemberStatusHist) {
        String carepassMemberStatusHistSelStatsql =
                "SELECT MBR_STATUS_CD\n" +
                        "FROM CAREPASS_MEMBER_STATUS_HIST\n" +
                        "WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n" +
                        "AND MBR_STATUS_CD=\n" +
                        " :MBR_STATUS_CD\n";

        MapSqlParameterSource cpmshStatParms = new MapSqlParameterSource();
        cpmshStatParms.addValue("XTRA_CARD_NBR", carepassMemberStatusHist.getXtraCardNbr(), Types.INTEGER);
        cpmshStatParms.addValue("MBR_STATUS_CD", carepassMemberStatusHist.getMbrStatusCd(), Types.VARCHAR);
        return (String) jdbcTemplate.queryForObject(carepassMemberStatusHistSelStatsql, cpmshStatParms, String.class);
    }

    /**
     * Delete Test Cards
     */
    public void deleteCarePass(List<Integer> pXtraCardNbrList) {
        //Delete Xtra Card Records
        String cpmsdSql = "DELETE FROM CAREPASS_MEMBER_SMRY WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map cpmsdParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int cpmsdDel = jdbcTemplate.update(cpmsdSql, cpmsdParms);

        log.info("\nTotal Number of records deleted for table CAREPASS_MEMBER_SMRY : [{}] ", cpmsdDel);

        //Delete Xtra Card CAREPASS_ENROLL_FORM_HIST
        String cpefhdSql = "DELETE FROM CAREPASS_ENROLL_FORM_HIST WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map cpefhdParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int cpefhdDel = jdbcTemplate.update(cpefhdSql, cpefhdParms);

        log.info("\nTotal Number of records deleted for table CAREPASS_ENROLL_FORM_HIST : [{}] ", cpefhdDel);

        //Delete CAREPASS_MEMBER_STATUS_HIST
        String cpmshdSql = "DELETE FROM CAREPASS_MEMBER_STATUS_HIST WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map cpmshdParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int cpmshdDel = jdbcTemplate.update(cpmshdSql, cpmshdParms);

        log.info("\nTotal Number of records deleted for table CAREPASS_MEMBER_STATUS_HIST : [{}] ", cpmshdDel);
    }


}