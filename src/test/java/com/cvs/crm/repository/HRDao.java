package com.cvs.crm.repository;

import com.cvs.crm.model.data.HrMemberSmry;
import com.cvs.crm.model.data.HrMemberEnroll;
import com.cvs.crm.model.data.HrMemberProfile;
import com.cvs.crm.model.data.HrMemberHippa;
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
public class HRDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Create HR_MEMBER_SMRY
     */
    public void createHrMemberSmry(HrMemberSmry hrMemberSmry) {
        // Create new hrMemberSmry Record
        String hrMemSmrySql =
                "INSERT INTO HR_MEMBER_SMRY\n" +
                        "(\n" +
                        "EPH_LINK_ID,\n" +
                        "XTRA_CARD_NBR,\n" +
                        "ENROLL_STATUS_CD,\n" +
                        "ENROLL_STATUS_TS,\n" +
                        "HIPPA_STATUS_CD,\n" +
                        "HIPPA_SIGN_TS,\n" +
                        "HIPPA_EXPIRE_DT,\n" +
                        "RX_CAP_STATUS_CD,\n" +
                        "RX_CAP_TS,\n" +
                        "LAST_UPDT_TS\n" +
                        ")\n" +
                        "values\n" +
                        "(\n" +
                        ":EPH_LINK_ID,\n" +
                        ":XTRA_CARD_NBR,\n" +
                        ":ENROLL_STATUS_CD,\n" +
                        ":ENROLL_STATUS_TS,\n" +
                        ":HIPPA_STATUS_CD,\n" +
                        ":HIPPA_SIGN_TS,\n" +
                        ":HIPPA_EXPIRE_DT,\n" +
                        ":RX_CAP_STATUS_CD,\n" +
                        ":RX_CAP_TS,\n" +
                        ":LAST_UPDT_TS\n" +
                        ")";


        MapSqlParameterSource hrMemSmryParms = new MapSqlParameterSource();

        hrMemSmryParms.addValue("EPH_LINK_ID", hrMemberSmry.getEphLinkId(), Types.INTEGER);
        hrMemSmryParms.addValue("XTRA_CARD_NBR", hrMemberSmry.getXtraCardNbr(), Types.INTEGER);
        hrMemSmryParms.addValue("ENROLL_STATUS_CD", hrMemberSmry.getEnrollStatusCd(), Types.VARCHAR);
        hrMemSmryParms.addValue("ENROLL_STATUS_TS", hrMemberSmry.getEnrollStatusTs(), Types.TIMESTAMP);
        hrMemSmryParms.addValue("HIPPA_STATUS_CD", hrMemberSmry.getHippaStatusCd(), Types.VARCHAR);
        hrMemSmryParms.addValue("HIPPA_SIGN_TS", hrMemberSmry.getHippaSignTs(), Types.TIMESTAMP);
        hrMemSmryParms.addValue("HIPPA_EXPIRE_DT", hrMemberSmry.getHippaExpireDt(), Types.DATE);
        hrMemSmryParms.addValue("RX_CAP_STATUS_CD", hrMemberSmry.getRxCapStatusCd(), Types.VARCHAR);
        hrMemSmryParms.addValue("RX_CAP_TS", hrMemberSmry.getRxCapTs(), Types.TIMESTAMP);
        hrMemSmryParms.addValue("LAST_UPDT_TS", hrMemberSmry.getLastUpdtTs(), Types.TIMESTAMP);


        int hrMemSmryRowCnt = jdbcTemplate.update(hrMemSmrySql, hrMemSmryParms);
        log.info("Total Number of Cards inserted into hrMemberSmry table : [{}]", hrMemSmryRowCnt);
    }

    /**
     * Create HR_MEMBER_HIPPA
     */
    public void createHrMemberHippa(HrMemberHippa hrMemberHippa) {
        // Create new HrMemberHippa Record
        String HrMemberHippaSql =
                "INSERT INTO HR_MEMBER_HIPPA\n" +
                        "(\n" +
                        "HIPPA_SIGN_TS,\n" +
                        "ID_TYPE_CD,\n" +
                        "ID_NBR,\n" +
                        "HIPPA_STATUS_CD,\n" +
                        "HIPPA_STATUS_RSN_CD,\n" +
                        "HIPPA_STATUS_TS,\n" +
                        "HIPPA_EXPIRE_DT,\n" +
                        "HIPPA_FORM_VER_NBR,\n" +
                        "HIPPA_FORM_LOC_CD,\n" +
                        "EPH_LINK_ID,\n" +
                        "HIPPA_SIGN_BY_CD,\n" +
                        "ESIG_FMT_CD\n" +
                        //   "ESIG_BLOB\n" +
                        ")\n" +
                        "values\n" +
                        "(\n" +
                        ":HIPPA_SIGN_TS,\n" +
                        ":ID_TYPE_CD,\n" +
                        ":ID_NBR,\n" +
                        ":HIPPA_STATUS_CD,\n" +
                        ":HIPPA_STATUS_RSN_CD,\n" +
                        ":HIPPA_STATUS_TS,\n" +
                        ":HIPPA_EXPIRE_DT,\n" +
                        ":HIPPA_FORM_VER_NBR,\n" +
                        ":HIPPA_FORM_LOC_CD,\n" +
                        ":EPH_LINK_ID,\n" +
                        ":HIPPA_SIGN_BY_CD,\n" +
                        ":ESIG_FMT_CD\n" +
                        //   ":ESIG_BLOB\n" +
                        ")";


        MapSqlParameterSource HrMemHippaParms = new MapSqlParameterSource();

        HrMemHippaParms.addValue("HIPPA_SIGN_TS", hrMemberHippa.getHippaSignTs(), Types.TIMESTAMP);
        HrMemHippaParms.addValue("ID_TYPE_CD", hrMemberHippa.getIdTypeCd(), Types.VARCHAR);
        HrMemHippaParms.addValue("ID_NBR", hrMemberHippa.getIdNbr(), Types.INTEGER);
        HrMemHippaParms.addValue("HIPPA_STATUS_CD", hrMemberHippa.getHippaStatusCd(), Types.VARCHAR);
        HrMemHippaParms.addValue("HIPPA_STATUS_RSN_CD", hrMemberHippa.getHippaStatusRsnCd(), Types.VARCHAR);
        HrMemHippaParms.addValue("HIPPA_STATUS_TS", hrMemberHippa.getHippaStatusTs(), Types.TIMESTAMP);
        HrMemHippaParms.addValue("HIPPA_EXPIRE_DT", hrMemberHippa.getHippaExpireDt(), Types.DATE);
        HrMemHippaParms.addValue("HIPPA_FORM_VER_NBR", hrMemberHippa.getHippaFormVerNbr(), Types.INTEGER);
        HrMemHippaParms.addValue("HIPPA_FORM_LOC_CD", hrMemberHippa.getHippaFormLocCd(), Types.VARCHAR);
        HrMemHippaParms.addValue("EPH_LINK_ID", hrMemberHippa.getEphLinkId(), Types.INTEGER);
        HrMemHippaParms.addValue("HIPPA_SIGN_BY_CD", hrMemberHippa.getHippaSignByCd(), Types.VARCHAR);
        HrMemHippaParms.addValue("ESIG_FMT_CD", hrMemberHippa.getEsigFmtCd(), Types.VARCHAR);
        //HrMemHippaParms.addValue("ESIG_BLOB" , hrMemberHippa.getEsigBlob() , Types.BLOB);

        int HrMemHippaRowCnt = jdbcTemplate.update(HrMemberHippaSql, HrMemHippaParms);
        log.info("Total Number of Cards inserted into hrMemberHippa table : [{}]", HrMemHippaRowCnt);
    }


    /**
     * Create HR_MEMBER_PROFILE
     */
    public void createHrMemberProfile(HrMemberProfile hrMemberProfile) {
        // Create new hrMemberProfile Record
        String hrMemProfileSql =
                "INSERT INTO HR_MEMBER_PROFILE\n" +
                        "(\n" +
                        "EPH_LINK_ID,\n" +
                        "FIRST_NAME,\n" +
                        "LAST_NAME,\n" +
                        "PREF_PHONE_NBR,\n" +
                        "CREATE_DTTM,\n" +
                        "LAST_UPDATE_DTTM\n" +
                        ")\n" +
                        "values\n" +
                        "(\n" +
                        ":EPH_LINK_ID,\n" +
                        ":FIRST_NAME,\n" +
                        ":LAST_NAME,\n" +
                        ":PREF_PHONE_NBR,\n" +
                        ":CREATE_DTTM,\n" +
                        ":LAST_UPDATE_DTTM\n" +
                        ")";


        MapSqlParameterSource hrMemProfileParms = new MapSqlParameterSource();

        hrMemProfileParms.addValue("EPH_LINK_ID", hrMemberProfile.getEphLinkId(), Types.INTEGER);
        hrMemProfileParms.addValue("FIRST_NAME", hrMemberProfile.getFirstName(), Types.VARCHAR);
        hrMemProfileParms.addValue("LAST_NAME", hrMemberProfile.getLastName(), Types.VARCHAR);
        hrMemProfileParms.addValue("PREF_PHONE_NBR", hrMemberProfile.getPrefPhoneNbr(), Types.VARCHAR);
        hrMemProfileParms.addValue("CREATE_DTTM", hrMemberProfile.getCreateDttm(), Types.TIMESTAMP);
        hrMemProfileParms.addValue("LAST_UPDATE_DTTM", hrMemberProfile.getLastUpdateDttm(), Types.TIMESTAMP);


        int hrMemProfRowCnt = jdbcTemplate.update(hrMemProfileSql, hrMemProfileParms);
        log.info("Total Number of Cards inserted into hrMemberProfile table : [{}]", hrMemProfRowCnt);
    }


    /**
     * Create HR_MEMBER_ENROLL
     */
    public void createHrMemberEnroll(HrMemberEnroll hrMemberEnroll) {
        // Create new hrMemberEnroll Record
        String hrMemberEnrollSql =
                "INSERT INTO HR_MEMBER_ENROLL\n" +
                        "(\n" +
                        "EPH_LINK_ID,\n" +
                        "ENROLL_STATUS_TS,\n" +
                        "ENROLL_STATUS_CD,\n" +
                        "ID_TYPE_CD,\n" +
                        "ID_NBR,\n" +
                        "XTRA_CARD_NBR,\n" +
                        "ENROLL_SRC_CD,\n" +
                        "ENROLL_STORE_NBR,\n" +
                        "ENROLL_SRC_ACCT_NBR\n" +
                        ")\n" +
                        "values\n" +
                        "(\n" +
                        ":EPH_LINK_ID,\n" +
                        ":ENROLL_STATUS_TS,\n" +
                        ":ENROLL_STATUS_CD,\n" +
                        ":ID_TYPE_CD,\n" +
                        ":ID_NBR,\n" +
                        ":XTRA_CARD_NBR,\n" +
                        ":ENROLL_SRC_CD,\n" +
                        ":ENROLL_STORE_NBR,\n" +
                        ":ENROLL_SRC_ACCT_NBR\n" +
                        ")";


        MapSqlParameterSource hrMemEnrollParms = new MapSqlParameterSource();

        hrMemEnrollParms.addValue("EPH_LINK_ID", hrMemberEnroll.getEphLinkId(), Types.INTEGER);
        hrMemEnrollParms.addValue("ENROLL_STATUS_TS", hrMemberEnroll.getEnrollStatusTs(), Types.TIMESTAMP);
        hrMemEnrollParms.addValue("ENROLL_STATUS_CD", hrMemberEnroll.getEnrollStatusCd(), Types.VARCHAR);
        hrMemEnrollParms.addValue("ID_TYPE_CD", hrMemberEnroll.getIdTypeCd(), Types.VARCHAR);
        hrMemEnrollParms.addValue("ID_NBR", hrMemberEnroll.getIdNbr(), Types.INTEGER);
        hrMemEnrollParms.addValue("XTRA_CARD_NBR", hrMemberEnroll.getXtraCardNbr(), Types.INTEGER);
        hrMemEnrollParms.addValue("ENROLL_SRC_CD", hrMemberEnroll.getEnrollSrcCd(), Types.VARCHAR);
        hrMemEnrollParms.addValue("ENROLL_STORE_NBR", hrMemberEnroll.getEnrollStoreNbr(), Types.INTEGER);
        hrMemEnrollParms.addValue("ENROLL_SRC_ACCT_NBR", hrMemberEnroll.getEnrollSrcAcctNbr(), Types.INTEGER);

        int hrMemEnrollRowCnt = jdbcTemplate.update(hrMemberEnrollSql, hrMemEnrollParms);
        log.info("Total Number of Cards inserted into hrMemberEnroll table : [{}]", hrMemEnrollRowCnt);
    }


    /**
     * Delete Test Data from HR tables
     */
    public void deleteHRRecords(List<Integer> pEphLinkIdList) {
        // Delete HR_MEMBER_ENROLL Records
        String hmeSql = "DELETE FROM HR_MEMBER_ENROLL WHERE EPH_LINK_ID IN (:EPH_LINK_ID)";

        Map hmeParms = Collections.singletonMap("EPH_LINK_ID", pEphLinkIdList);
        int hmeDel = jdbcTemplate.update(hmeSql, hmeParms);

        log.info("Total Number of records deleted for table HR_MEMBER_ENROLL : [{}] ", hmeDel);

        // Delete HR_MEMBER_PROFILE Records
        String hmpSql = "DELETE FROM HR_MEMBER_PROFILE WHERE EPH_LINK_ID IN (:EPH_LINK_ID)";

        Map hmpParms = Collections.singletonMap("EPH_LINK_ID", pEphLinkIdList);
        int hmpDel = jdbcTemplate.update(hmpSql, hmpParms);

        log.info("Total Number of records deleted for table HR_MEMBER_PROFILE : [{}] ", hmpDel);


        // Delete HR_MEMBER_SMRY Records
        String hmsSql = "DELETE FROM HR_MEMBER_SMRY WHERE EPH_LINK_ID IN (:EPH_LINK_ID)";

        Map hmsParms = Collections.singletonMap("EPH_LINK_ID", pEphLinkIdList);
        int hmsdel = jdbcTemplate.update(hmsSql, hmsParms);

        log.info("Total Number of records deleted for table HR_MEMBER_SMRY : [{}] ", hmsdel);

        // Delete HR_MEMBER_HIPPA Records
        String hmhSql = "DELETE FROM HR_MEMBER_HIPPA WHERE EPH_LINK_ID IN (:EPH_LINK_ID)";

        Map hmhParms = Collections.singletonMap("EPH_LINK_ID", pEphLinkIdList);
        int hmhdel = jdbcTemplate.update(hmhSql, hmhParms);

        log.info("Total Number of records deleted for table HR_MEMBER_HIPPA : [{}] ", hmhdel);
    }
    
    public void deleteHRMemberProfiles(List<Integer> pEphLinkIdList) {
        // Delete HR_MEMBER_PROFILE Records
        String hmpSql = "DELETE FROM HR_MEMBER_PROFILE WHERE EPH_LINK_ID IN (:EPH_LINK_ID)";

        Map hmpParms = Collections.singletonMap("EPH_LINK_ID", pEphLinkIdList);
        int hmpDel = jdbcTemplate.update(hmpSql, hmpParms);

        log.info("Total Number of records deleted for table HR_MEMBER_PROFILE : [{}] ", hmpDel);
    }
    
    public void deleteHRMemberSummary(List<Integer> pEphLinkIdList) {
    	// Delete HR_MEMBER_SMRY Records
        String hmsSql = "DELETE FROM HR_MEMBER_SMRY WHERE EPH_LINK_ID IN (:EPH_LINK_ID)";

        Map hmsParms = Collections.singletonMap("EPH_LINK_ID", pEphLinkIdList);
        int hmsdel = jdbcTemplate.update(hmsSql, hmsParms);

        log.info("Total Number of records deleted for table HR_MEMBER_SMRY : [{}] ", hmsdel);
    }

    public void deleteHRMemberSummary_with_xtraCardNbr(List<Integer> xtraCardNbr) {
        // Delete XTRA_CARD from HR_MEMBER_SMRY
        try {
            String Sql = "DELETE FROM HR_MEMBER_SMRY WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";
            Map xcParms = Collections.singletonMap("XTRA_CARD_NBR", xtraCardNbr);
            int deleted_count = jdbcTemplate.update(Sql, xcParms);
            log.info("DELETED XTRA_CARD_NBR: {} from HR_MEMBER_SMRY table. \nDeleted Xtra_Card_Nbr Count : {}", xtraCardNbr, deleted_count);
        } catch (Exception e) {
            log.info("Exception - " + e);
            throw e;
        }
    }

    public void deleteHrRecords_from_table(List<Integer> xtraCardNbr, String tableName) {
        // Delete CUST_ID from tableName
        try {
            String Sql = "DELETE FROM " + tableName + " WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";
            Map xcParms = Collections.singletonMap("XTRA_CARD_NBR", xtraCardNbr);
            int deleted_count = jdbcTemplate.update(Sql, xcParms);
            log.info("\nDELETED XTRA_CARD_NBR: {} from {} table. Deleted Cust_Id Count : {}.", xtraCardNbr, tableName, deleted_count);
        } catch (Exception ex) {
            log.info("Exception - " + ex);
            throw ex;
        }
    }

    public void delete_HRRecords(List<Integer> xtraCardNbrList) {
        deleteHrRecords_from_table(xtraCardNbrList, "HR_MEMBER_ENROLL");
        deleteHrRecords_from_table(xtraCardNbrList, "HR_MEMBER_SMRY");
    }


}
