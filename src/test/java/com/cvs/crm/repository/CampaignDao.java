package com.cvs.crm.repository;

import com.cvs.crm.model.data.*;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.internal.OracleTimestampWithTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.*;

@Repository
@Slf4j
public class CampaignDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Create Campaign
     */
    public void createCampaign(Campaign campaign) {
        // Create new Campaign Record
        String campaignSql =
                "INSERT INTO CAMPAIGN\n" +
                        "(\n" +
                        "CMPGN_ID,\n" +
                        "CMPGN_TYPE_CD,\n" +
                        "CMPGN_SUBTYPE_CD,\n" +
                        "CMPGN_DSC,\n" +
                        "RECPT_PRNT_IND,\n" +
                        "RECPT_PRNT_PRIORITY_NBR,\n" +
                        "RECPT_TXT,\n" +
                        "RECPT_SCALE_NBR,\n" +
                        "RWRD_REDIR_IND,\n" +
                        "START_DT,\n" +
                        "END_DT,\n" +
                        "LAST_UPDT_TS,\n" +
                        "ISSUE_FREQ_TYPE_CD,\n" +
                        "ISSUE_FREQ_CNT,\n" +
                        "FIRST_ISSUE_DT,\n" +
                        "LAST_ISSUE_DT,\n" +
                        "PREV_ISSUE_DT,\n" +
                        "NEXT_ISSUE_DT,\n" +
                        "DAYS_TO_PRNT_CNT,\n" +
                        "DAYS_TO_REDEEM_CNT,\n" +
                        "IN_HOME_DT,\n" +
                        "TOTAL_RWRD_EARN_AMT,\n" +
                        "BONUS_SKU_CALC_DT,\n" +
                        "CPN_REDEEM_CALC_DT,\n" +
                        "CPN_BASE_DSC,\n" +
                        "PARENT_CMPGN_ID,\n" +
                        "CPN_CAT_NBR,\n" +
                        "CPN_SEG_NBR,\n" +
                        "CPN_FNDG_CD,\n" +
                        "BILLING_TYPE_CD,\n" +
                        "INDIV_RWRD_AMT,\n" +
                        "CPN_AUTOGEN_IND,\n" +
                        "RWRD_LAST_CALC_DT,\n" +
                        "CSR_VISIBLE_IND,\n" +
                        "CMPGN_TERMS_TXT,\n" +
                        "WEB_DSC,\n" +
                        "WEB_DISP_IND,\n" +
                        "PAY_VENDOR_NBR,\n" +
                        "CPN_OLTP_HOLD_IND,\n" +
                        "CPN_PURGE_CD,\n" +
                        "DFLT_CPN_TERMS_CD,\n" +
                        "CAT_MGR_ID,\n" +
                        "VENDOR_NBR,\n" +
                        "MULTI_VENDOR_IND,\n" +
                        "CPN_FIXED_DSC_IND,\n" +
                        "CPN_PRNT_START_DELAY_DAY_CNT,\n" +
                        "CPN_REDM_START_DELAY_DAY_CNT,\n" +
                        "CPN_PRIORITY_NBR,\n" +
                        "CPN_QUAL_TXT,\n" +
                        "REQ_SKU_LIST,\n" +
                        "MAX_VISIT_RWRD_QTY,\n" +
                        "MAX_RWRD_QTY,\n" +
                        "RWRD_RECALC_IND,\n" +
                        "CMPGN_QUAL_TXT,\n" +
                        "TRGT_PRNT_DEST_CD,\n" +
                        "CPN_MIN_PURCH_AMT,\n" +
                        "LAST_FEED_ACCPT_DT,\n" +
                        "ADV_MAX_RWRD_QTY,\n" +
                        "PROMO_LINK_NBR,\n" +
                        "AMT_TYPE_CD,\n" +
                        "PCT_OFF_AMT,\n" +
                        "FSA_CPN_IND,\n" +
                        "PRVT_LABEL_CPN_IND,\n" +
                        "DFLT_ALWAYS_IND,\n" +
                        "DFLT_FREQ_DAY_CNT,\n" +
                        "DFLT_FREQ_EMP_DAY_CNT,\n" +
                        "LOADABLE_IND,\n" +
                        "CARD_TYPE_CD,\n" +
                        "CPN_RECPT_TXT,\n" +
                        "CPN_VAL_RQRD_CD,\n" +
                        "ABS_AMT_IND,\n" +
                        "ITEM_LIMIT_QTY,\n" +
                        "CPN_FMT_CD,\n" +
                        "DFLT_CPN_CAT_JSON,\n" +
                        "FREE_ITEM_IND,\n" +
                        "MKTG_PRG_CD,\n" +
                        "MOBILE_DISP_IND,\n" +
                        "OVRD_PAPERLESS_IND,\n" +
                        "ANALYTIC_EVENT_TYPE_CD,\n" +
                        "WEB_REDEEMABLE_IND,\n" +
                        "MFR_CPN_SRC_CD,\n" +
                        "XTRA_CARD_SEG_NBR,\n" +
                        "PRODUCT_CRITERIA_ID,\n" +
                        "DFLT_CPN_CAT_XML,\n" +
                        "SEG_INC_EXC_CD,\n" +
                        "SEG_SRC_OWNER_NAME,\n" +
                        "SEG_SRC_TABLE_NAME,\n" +
                        "SEG_RELOAD_RQST_TS,\n" +
                        "SEG_LAST_PROC_START_TS,\n" +
                        "SEG_LAST_PROC_END_TS,\n" +
                        "SEG_LAST_PROC_STAT_CD,\n" +
                        "SEG_LAST_PROC_ROW_CNT,\n" +
                        "FIXED_REDEEM_START_DT,\n" +
                        "FIXED_REDEEM_END_DT,\n" +
                        "LAST_AUTO_REISSUE_DT,\n" +
                        "AUTO_REISSUE_IND,\n" +
                        "TRGT_PRNT_REG_TYPE_CD,\n" +
                        "FACEBOOK_DISP_IND,\n" +
                        "INSTANT_CMPGN_EARNING_IND,\n" +
                        "PE_OPTIMIZE_TYPE_CD\n" +
                        ")\n" +
                        "values\n" +
                        "(\n" +
                        ":CMPGN_ID,\n" +
                        ":CMPGN_TYPE_CD,\n" +
                        ":CMPGN_SUBTYPE_CD,\n" +
                        ":CMPGN_DSC,\n" +
                        ":RECPT_PRNT_IND,\n" +
                        ":RECPT_PRNT_PRIORITY_NBR,\n" +
                        ":RECPT_TXT,\n" +
                        ":RECPT_SCALE_NBR,\n" +
                        ":RWRD_REDIR_IND,\n" +
                        ":START_DT,\n" +
                        ":END_DT,\n" +
                        ":LAST_UPDT_TS,\n" +
                        ":ISSUE_FREQ_TYPE_CD,\n" +
                        ":ISSUE_FREQ_CNT,\n" +
                        ":FIRST_ISSUE_DT,\n" +
                        ":LAST_ISSUE_DT,\n" +
                        ":PREV_ISSUE_DT,\n" +
                        ":NEXT_ISSUE_DT,\n" +
                        ":DAYS_TO_PRNT_CNT,\n" +
                        ":DAYS_TO_REDEEM_CNT,\n" +
                        ":IN_HOME_DT,\n" +
                        ":TOTAL_RWRD_EARN_AMT,\n" +
                        ":BONUS_SKU_CALC_DT,\n" +
                        ":CPN_REDEEM_CALC_DT,\n" +
                        ":CPN_BASE_DSC,\n" +
                        ":PARENT_CMPGN_ID,\n" +
                        ":CPN_CAT_NBR,\n" +
                        ":CPN_SEG_NBR,\n" +
                        ":CPN_FNDG_CD,\n" +
                        ":BILLING_TYPE_CD,\n" +
                        ":INDIV_RWRD_AMT,\n" +
                        ":CPN_AUTOGEN_IND,\n" +
                        ":RWRD_LAST_CALC_DT,\n" +
                        ":CSR_VISIBLE_IND,\n" +
                        ":CMPGN_TERMS_TXT,\n" +
                        ":WEB_DSC,\n" +
                        ":WEB_DISP_IND,\n" +
                        ":PAY_VENDOR_NBR,\n" +
                        ":CPN_OLTP_HOLD_IND,\n" +
                        ":CPN_PURGE_CD,\n" +
                        ":DFLT_CPN_TERMS_CD,\n" +
                        ":CAT_MGR_ID,\n" +
                        ":VENDOR_NBR,\n" +
                        ":MULTI_VENDOR_IND,\n" +
                        ":CPN_FIXED_DSC_IND,\n" +
                        ":CPN_PRNT_START_DELAY_DAY_CNT,\n" +
                        ":CPN_REDM_START_DELAY_DAY_CNT,\n" +
                        ":CPN_PRIORITY_NBR,\n" +
                        ":CPN_QUAL_TXT,\n" +
                        ":REQ_SKU_LIST,\n" +
                        ":MAX_VISIT_RWRD_QTY,\n" +
                        ":MAX_RWRD_QTY,\n" +
                        ":RWRD_RECALC_IND,\n" +
                        ":CMPGN_QUAL_TXT,\n" +
                        ":TRGT_PRNT_DEST_CD,\n" +
                        ":CPN_MIN_PURCH_AMT,\n" +
                        ":LAST_FEED_ACCPT_DT,\n" +
                        ":ADV_MAX_RWRD_QTY,\n" +
                        ":PROMO_LINK_NBR,\n" +
                        ":AMT_TYPE_CD,\n" +
                        ":PCT_OFF_AMT,\n" +
                        ":FSA_CPN_IND,\n" +
                        ":PRVT_LABEL_CPN_IND,\n" +
                        ":DFLT_ALWAYS_IND,\n" +
                        ":DFLT_FREQ_DAY_CNT,\n" +
                        ":DFLT_FREQ_EMP_DAY_CNT,\n" +
                        ":LOADABLE_IND,\n" +
                        ":CARD_TYPE_CD,\n" +
                        ":CPN_RECPT_TXT,\n" +
                        ":CPN_VAL_RQRD_CD,\n" +
                        ":ABS_AMT_IND,\n" +
                        ":ITEM_LIMIT_QTY,\n" +
                        ":CPN_FMT_CD,\n" +
                        ":DFLT_CPN_CAT_JSON,\n" +
                        ":FREE_ITEM_IND,\n" +
                        ":MKTG_PRG_CD,\n" +
                        ":MOBILE_DISP_IND,\n" +
                        ":OVRD_PAPERLESS_IND,\n" +
                        ":ANALYTIC_EVENT_TYPE_CD,\n" +
                        ":WEB_REDEEMABLE_IND,\n" +
                        ":MFR_CPN_SRC_CD,\n" +
                        ":XTRA_CARD_SEG_NBR,\n" +
                        ":PRODUCT_CRITERIA_ID,\n" +
                        ":DFLT_CPN_CAT_XML,\n" +
                        ":SEG_INC_EXC_CD,\n" +
                        ":SEG_SRC_OWNER_NAME,\n" +
                        ":SEG_SRC_TABLE_NAME,\n" +
                        ":SEG_RELOAD_RQST_TS,\n" +
                        ":SEG_LAST_PROC_START_TS,\n" +
                        ":SEG_LAST_PROC_END_TS,\n" +
                        ":SEG_LAST_PROC_STAT_CD,\n" +
                        ":SEG_LAST_PROC_ROW_CNT,\n" +
                        ":FIXED_REDEEM_START_DT,\n" +
                        ":FIXED_REDEEM_END_DT,\n" +
                        ":LAST_AUTO_REISSUE_DT,\n" +
                        ":AUTO_REISSUE_IND,\n" +
                        ":TRGT_PRNT_REG_TYPE_CD,\n" +
                        ":FACEBOOK_DISP_IND,\n" +
                        ":INSTANT_CMPGN_EARNING_IND,\n" +
                        ":PE_OPTIMIZE_TYPE_CD\n" +
                        ")";


        MapSqlParameterSource campParms = new MapSqlParameterSource();

        campParms.addValue("CMPGN_ID", campaign.getCmpgnId(), Types.INTEGER);
        campParms.addValue("CMPGN_TYPE_CD", campaign.getCmpgnTypeCd(), Types.VARCHAR);
        campParms.addValue("CMPGN_SUBTYPE_CD", campaign.getCmpgnSubtypeCd(), Types.VARCHAR);
        campParms.addValue("CMPGN_DSC", campaign.getCmpgnDsc(), Types.VARCHAR);
        campParms.addValue("RECPT_PRNT_IND", campaign.getRecptPrntInd(), Types.VARCHAR);
        campParms.addValue("RECPT_PRNT_PRIORITY_NBR", campaign.getRecptPrntPriorityNbr(), Types.INTEGER);
        campParms.addValue("RECPT_TXT", campaign.getRecptRxt(), Types.VARCHAR);
        campParms.addValue("RECPT_SCALE_NBR", campaign.getRecptScaleNbr(), Types.INTEGER);
        campParms.addValue("RWRD_REDIR_IND", campaign.getRwrdRedirInd(), Types.VARCHAR);
        campParms.addValue("START_DT", campaign.getStartDt(), Types.DATE);
        campParms.addValue("END_DT", campaign.getEndDt(), Types.DATE);
        //  campParms.addValue("END_DT", campaign.getEndDt(), Types.DATE);
        campParms.addValue("LAST_UPDT_TS", campaign.getLastUpdtTs(), Types.TIMESTAMP);
        campParms.addValue("ISSUE_FREQ_TYPE_CD", campaign.getIssueFreqTypeCd(), Types.VARCHAR);
        campParms.addValue("ISSUE_FREQ_CNT", campaign.getIssueFreqCnt(), Types.INTEGER);
        campParms.addValue("FIRST_ISSUE_DT", campaign.getFirstIssueDt(), Types.DATE);
        campParms.addValue("LAST_ISSUE_DT", campaign.getLastIssueDt(), Types.DATE);
        campParms.addValue("PREV_ISSUE_DT", campaign.getPrevIssueDt(), Types.DATE);
        campParms.addValue("NEXT_ISSUE_DT", campaign.getNextIssueDt(), Types.DATE);
        campParms.addValue("DAYS_TO_PRNT_CNT", campaign.getDaysToPrintCnt(), Types.INTEGER);
        campParms.addValue("DAYS_TO_REDEEM_CNT", campaign.getDaysToRedeemCnt(), Types.INTEGER);
        campParms.addValue("IN_HOME_DT", campaign.getInHomeDt(), Types.DATE);
        campParms.addValue("TOTAL_RWRD_EARN_AMT", campaign.getTotlaRwrdEarnAmt(), Types.INTEGER);
        campParms.addValue("BONUS_SKU_CALC_DT", campaign.getBonusSkuCalcDt(), Types.DATE);
        campParms.addValue("CPN_REDEEM_CALC_DT", campaign.getCpnRedeemCalcDt(), Types.DATE);
        campParms.addValue("CPN_BASE_DSC", campaign.getCpnBaseDsc(), Types.VARCHAR);
        campParms.addValue("PARENT_CMPGN_ID", campaign.getParentCmpgnId(), Types.INTEGER);
        campParms.addValue("CPN_CAT_NBR", campaign.getCpnCatNbr(), Types.INTEGER);
        campParms.addValue("CPN_SEG_NBR", campaign.getCpnSegNbr(), Types.INTEGER);
        campParms.addValue("CPN_FNDG_CD", campaign.getCpnFndgCd(), Types.VARCHAR);
        campParms.addValue("BILLING_TYPE_CD", campaign.getBillingTypeCd(), Types.VARCHAR);
        campParms.addValue("INDIV_RWRD_AMT", campaign.getIndivRwrdAmt(), Types.INTEGER);
        campParms.addValue("CPN_AUTOGEN_IND", campaign.getCpnAutoGenInd(), Types.VARCHAR);
        campParms.addValue("RWRD_LAST_CALC_DT", campaign.getRwrdLastCalcDt(), Types.DATE);
        campParms.addValue("CSR_VISIBLE_IND", campaign.getCsrVisibleInd(), Types.VARCHAR);
        campParms.addValue("CMPGN_TERMS_TXT", campaign.getCmpgnTermsTxt(), Types.VARCHAR);
        campParms.addValue("WEB_DSC", campaign.getWebDsc(), Types.VARCHAR);
        campParms.addValue("WEB_DISP_IND", campaign.getWebDispInd(), Types.VARCHAR);
        campParms.addValue("PAY_VENDOR_NBR", campaign.getPayVendorNbr(), Types.INTEGER);
        campParms.addValue("CPN_OLTP_HOLD_IND", campaign.getCpnOltpHoldInd(), Types.VARCHAR);
        campParms.addValue("CPN_PURGE_CD", campaign.getCpnPurgeCd(), Types.VARCHAR);
        campParms.addValue("DFLT_CPN_TERMS_CD", campaign.getDfltCpnTermscd(), Types.INTEGER);
        campParms.addValue("CAT_MGR_ID", campaign.getCatMgrId(), Types.VARCHAR);
        campParms.addValue("VENDOR_NBR", campaign.getVendorNbr(), Types.INTEGER);
        campParms.addValue("MULTI_VENDOR_IND", campaign.getMultiVendorInd(), Types.VARCHAR);
        campParms.addValue("CPN_FIXED_DSC_IND", campaign.getCpnFixedDscInd(), Types.VARCHAR);
        campParms.addValue("CPN_PRNT_START_DELAY_DAY_CNT", campaign.getCpnPrntStartDelayDayCnt(), Types.INTEGER);
        campParms.addValue("CPN_REDM_START_DELAY_DAY_CNT", campaign.getCpnRedmStartDelayDayCnt(), Types.INTEGER);
        campParms.addValue("CPN_PRIORITY_NBR", campaign.getCpnPriorityNbr(), Types.INTEGER);
        campParms.addValue("CPN_QUAL_TXT", campaign.getCpnQualTxt(), Types.VARCHAR);
        campParms.addValue("REQ_SKU_LIST", campaign.getReqSkuList(), Types.VARCHAR);
        campParms.addValue("MAX_VISIT_RWRD_QTY", campaign.getMaxVisitRwrdQty(), Types.INTEGER);
        campParms.addValue("MAX_RWRD_QTY", campaign.getMaxRwrdQty(), Types.INTEGER);
        campParms.addValue("RWRD_RECALC_IND", campaign.getRwrdRecalcInd(), Types.VARCHAR);
        campParms.addValue("CMPGN_QUAL_TXT", campaign.getCmpgnQualTxt(), Types.VARCHAR);
        campParms.addValue("TRGT_PRNT_DEST_CD", campaign.getTrgtPrntDestCd(), Types.VARCHAR);
        campParms.addValue("CPN_MIN_PURCH_AMT", campaign.getCpnMinPurchAmt(), Types.INTEGER);
        campParms.addValue("LAST_FEED_ACCPT_DT", campaign.getLastFeedAccptDt(), Types.DATE);
        campParms.addValue("ADV_MAX_RWRD_QTY", campaign.getAdvMaxRwrdQty(), Types.INTEGER);
        campParms.addValue("PROMO_LINK_NBR", campaign.getPromoLinkNbr(), Types.INTEGER);
        campParms.addValue("AMT_TYPE_CD", campaign.getAmtTypeCd(), Types.VARCHAR);
        campParms.addValue("PCT_OFF_AMT", campaign.getPctOffAmt(), Types.INTEGER);
        campParms.addValue("FSA_CPN_IND", campaign.getFsaCpnInd(), Types.VARCHAR);
        campParms.addValue("PRVT_LABEL_CPN_IND", campaign.getPrtLabrlCpnInd(), Types.VARCHAR);
        campParms.addValue("DFLT_ALWAYS_IND", campaign.getDfltAlwaysInd(), Types.VARCHAR);
        campParms.addValue("DFLT_FREQ_DAY_CNT", campaign.getDfltFreqDayCnt(), Types.INTEGER);
        campParms.addValue("DFLT_FREQ_EMP_DAY_CNT", campaign.getDfltFreqEmpDayCnt(), Types.INTEGER);
        campParms.addValue("LOADABLE_IND", campaign.getLoadableInd(), Types.VARCHAR);
        campParms.addValue("CARD_TYPE_CD", campaign.getCardTypeCd(), Types.VARCHAR);
        campParms.addValue("CPN_RECPT_TXT", campaign.getCpnRecptTxt(), Types.VARCHAR);
        campParms.addValue("CPN_VAL_RQRD_CD", campaign.getCpnValRqrdCd(), Types.VARCHAR);
        campParms.addValue("ABS_AMT_IND", campaign.getAbsAmtInd(), Types.VARCHAR);
        campParms.addValue("ITEM_LIMIT_QTY", campaign.getItemLimitQty(), Types.INTEGER);
        campParms.addValue("CPN_FMT_CD", campaign.getCpnFmtCd(), Types.VARCHAR);
        campParms.addValue("DFLT_CPN_CAT_JSON", campaign.getDfltCpnCatJson(), Types.VARCHAR);
        campParms.addValue("FREE_ITEM_IND", campaign.getFreeItemInd(), Types.VARCHAR);
        campParms.addValue("MKTG_PRG_CD", campaign.getMktgPrgCd(), Types.VARCHAR);
        campParms.addValue("MOBILE_DISP_IND", campaign.getMobileDispInd(), Types.VARCHAR);
        campParms.addValue("OVRD_PAPERLESS_IND", campaign.getOvrdPaperLessInd(), Types.VARCHAR);
        campParms.addValue("ANALYTIC_EVENT_TYPE_CD", campaign.getAnalyticEventTypeCd(), Types.INTEGER);
        campParms.addValue("WEB_REDEEMABLE_IND", campaign.getWebRedeemableInd(), Types.VARCHAR);
        campParms.addValue("MFR_CPN_SRC_CD", campaign.getMfrCpnSrcCd(), Types.VARCHAR);
        campParms.addValue("XTRA_CARD_SEG_NBR", campaign.getXtraCardSegNbr(), Types.INTEGER);
        campParms.addValue("PRODUCT_CRITERIA_ID", campaign.getProductCriteriaId(), Types.INTEGER);
        campParms.addValue("DFLT_CPN_CAT_XML", campaign.getDfltCpnCatXml(), Types.VARCHAR);
        campParms.addValue("SEG_INC_EXC_CD", campaign.getSegIncExcCd(), Types.VARCHAR);
        campParms.addValue("SEG_SRC_OWNER_NAME", campaign.getSegSrcOwnerName(), Types.VARCHAR);
        campParms.addValue("SEG_SRC_TABLE_NAME", campaign.getSegSrcTableName(), Types.VARCHAR);
        campParms.addValue("SEG_RELOAD_RQST_TS", campaign.getSegReloadRqstTs(), Types.TIMESTAMP);
        campParms.addValue("SEG_LAST_PROC_START_TS", campaign.getSegLastProcStartTs(), Types.TIMESTAMP);
        campParms.addValue("SEG_LAST_PROC_END_TS", campaign.getSegLastProcEndTs(), Types.TIMESTAMP);
        campParms.addValue("SEG_LAST_PROC_STAT_CD", campaign.getSegLastProcStatCd(), Types.VARCHAR);
        campParms.addValue("SEG_LAST_PROC_ROW_CNT", campaign.getSegLastProcRowCnt(), Types.INTEGER);
        campParms.addValue("FIXED_REDEEM_START_DT", campaign.getFixedRedeemStartDt(), Types.DATE);
        campParms.addValue("FIXED_REDEEM_END_DT", campaign.getFixedRedeemEndDt(), Types.DATE);
        campParms.addValue("LAST_AUTO_REISSUE_DT", campaign.getLastAutoReissueDt(), Types.DATE);
        campParms.addValue("AUTO_REISSUE_IND", campaign.getAutoReissueInd(), Types.VARCHAR);
        campParms.addValue("TRGT_PRNT_REG_TYPE_CD", campaign.getTrgtPrntRegCd(), Types.VARCHAR);
        campParms.addValue("FACEBOOK_DISP_IND", campaign.getFacebookDispInd(), Types.VARCHAR);
        campParms.addValue("INSTANT_CMPGN_EARNING_IND", campaign.getInstantCmpgnEarnigInd(), Types.VARCHAR);
        campParms.addValue("PE_OPTIMIZE_TYPE_CD", campaign.getPeOptimizeTypeCd(), Types.VARCHAR);

        int campRowCnt = jdbcTemplate.update(campaignSql, campParms);
        log.info("Total Number of Cards inserted into campaign table : [{}]", campRowCnt);
    }

    /**
     * Create CAMPAIGN_REWARD_THRESHOLD
     */
    public void createCampaignRewardThreshold(CampaignRewardThreshold campaignRewardThreshold) {
        // Create new CampaignRewardThreshold Record
        String campRewardThreSql =
                "INSERT INTO CAMPAIGN_REWARD_THRESHOLD\n" +
                        "(\n" +
                        "CMPGN_ID,\n" +
                        "THRSHLD_LIM_NBR,\n" +
                        "RWRD_QTY,\n" +
                        "RWRD_THRSHLD_CYCL_IND,\n" +
                        "THRSHLD_TYPE_CD\n" +
                        ")\n" +
                        "values\n" +
                        "(\n" +
                        ":CMPGN_ID,\n" +
                        ":THRSHLD_LIM_NBR,\n" +
                        ":RWRD_QTY,\n" +
                        ":RWRD_THRSHLD_CYCL_IND,\n" +
                        ":THRSHLD_TYPE_CD\n" +
                        ")";

        MapSqlParameterSource campRewardThreParms = new MapSqlParameterSource();

        campRewardThreParms.addValue("CMPGN_ID", campaignRewardThreshold.getCmpgnId(), Types.INTEGER);
        campRewardThreParms.addValue("THRSHLD_LIM_NBR", campaignRewardThreshold.getThrshldLimNbr(), Types.INTEGER);
        campRewardThreParms.addValue("RWRD_QTY", campaignRewardThreshold.getRwrdQty(), Types.INTEGER);
        campRewardThreParms.addValue("RWRD_THRSHLD_CYCL_IND", campaignRewardThreshold.getRwrdThrshldCyclInd(), Types.VARCHAR);
        campRewardThreParms.addValue("THRSHLD_TYPE_CD", campaignRewardThreshold.getThrshldTypeCd(), Types.VARCHAR);

        int camprewardthreRowCnt = jdbcTemplate.update(campRewardThreSql, campRewardThreParms);
        log.info("Total Number of Cards inserted into CAMPAIGN_REWARD_THRESHOLD table : [{}]", camprewardthreRowCnt);
    }

    /**
     * Create CAMPAIGN_ACTIVE_POINT_BASE
     */
    public void createCampaignActivePointBase(CampaignActivePointBase campaignActivePointBase) {
        // Create new CampaignActivePointBase Record
        String campActivePointBaseSql =
                "INSERT INTO CAMPAIGN_ACTIVE_POINT_BASE\n" +
                        "(\n" +
                        "XTRA_CARD_NBR,\n" +
                        "CMPGN_ID,\n" +
                        "PTS_QTY,\n" +
                        "ACTIVATION_TS,\n" +
                        "ACTIVATION_SRC_CD\n" +
                        ")\n" +
                        "values\n" +
                        "(\n" +
                        ":XTRA_CARD_NBR,\n" +
                        ":CMPGN_ID,\n" +
                        ":PTS_QTY,\n" +
                        ":ACTIVATION_TS,\n" +
                        ":ACTIVATION_SRC_CD\n" +
                        ")";

        MapSqlParameterSource campActivePointBaseParms = new MapSqlParameterSource();

        campActivePointBaseParms.addValue("XTRA_CARD_NBR", campaignActivePointBase.getXtraCardNbr(), Types.INTEGER);
        campActivePointBaseParms.addValue("CMPGN_ID", campaignActivePointBase.getCmpgnId(), Types.INTEGER);
        campActivePointBaseParms.addValue("PTS_QTY", campaignActivePointBase.getPtsQty(), Types.DOUBLE);
        campActivePointBaseParms.addValue("ACTIVATION_TS", campaignActivePointBase.getActivationTs(), Types.TIMESTAMP);
        campActivePointBaseParms.addValue("ACTIVATION_SRC_CD", campaignActivePointBase.getActivationSrcCd(), Types.VARCHAR);

        int campActivePointBaseRowCnt = jdbcTemplate.update(campActivePointBaseSql, campActivePointBaseParms);
        log.info("Total Number of Cards inserted into CAMPAIGN_ACTIVE_POINT_BASE table : [{}]", campActivePointBaseRowCnt);
    }


    /**
     * Create CAMPAIGN_ACTIVE_POINT_BASE
     */
    public void createCampaignCoupon(CampaignCoupon campaignCoupon) {
        // Create new CAMPAIGN_COUPON Record
        String campaignCouponSql =
                "INSERT INTO CAMPAIGN_COUPON\n" +
                        "(\n" +
                        "CMPGN_ID,\n" +
                        "RWRD_QTY,\n" +
                        "CPN_NBR,\n" +
                        "START_DT,\n" +
                        "END_DT,\n" +
                        "CPN_DSC,\n" +
                        "MAX_REDEEM_AMT,\n" +
                        "CPN_TERMS_CD,\n" +
                        "AMT_TYPE_CD,\n" +
                        "PCT_OFF_AMT,\n" +
                        "LOADABLE_IND,\n" +
                        "FNDG_CD,\n" +
                        "BILLING_TYPE_CD,\n" +
                        "CPN_RECPT_TXT,\n" +
                        "CPN_VAL_RQRD_CD,\n" +
                        "ABS_AMT_IND,\n" +
                        "ITEM_LIMIT_QTY,\n" +
                        "CPN_FMT_CD,\n" +
                        "FREE_ITEM_IND,\n" +
                        "IMAGE_URL_TXT,\n" +
                        "LAST_UPDT_TS,\n" +
                        "CPN_CAT_LIST_XML,\n" +
                        "CPN_CAT_LIST_JSON,\n" +
                        "CPN_OLTP_HOLD_IND,\n" +
                        "CARD_VAL_CD,\n" +
                        "CAT_MGR_ID,\n" +
                        "MAX_ISSUE_CNT,\n" +
                        "FIRST_UPDT_BY_ID,\n" +
                        "LAST_UPDT_BY_ID,\n" +
                        "CPN_PRNT_SUPPRESS_IND,\n" +
                        "DISCLAIMER_TXT\n" +
                        ")\n" +
                        "values\n" +
                        "(\n" +
                        ":CMPGN_ID,\n" +
                        ":RWRD_QTY,\n" +
                        ":CPN_NBR,\n" +
                        ":START_DT,\n" +
                        ":END_DT,\n" +
                        ":CPN_DSC,\n" +
                        ":MAX_REDEEM_AMT,\n" +
                        ":CPN_TERMS_CD,\n" +
                        ":AMT_TYPE_CD,\n" +
                        ":PCT_OFF_AMT,\n" +
                        ":LOADABLE_IND,\n" +
                        ":FNDG_CD,\n" +
                        ":BILLING_TYPE_CD,\n" +
                        ":CPN_RECPT_TXT,\n" +
                        ":CPN_VAL_RQRD_CD,\n" +
                        ":ABS_AMT_IND,\n" +
                        ":ITEM_LIMIT_QTY,\n" +
                        ":CPN_FMT_CD,\n" +
                        ":FREE_ITEM_IND,\n" +
                        ":IMAGE_URL_TXT,\n" +
                        ":LAST_UPDT_TS,\n" +
                        ":CPN_CAT_LIST_XML,\n" +
                        ":CPN_CAT_LIST_JSON,\n" +
                        ":CPN_OLTP_HOLD_IND,\n" +
                        ":CARD_VAL_CD,\n" +
                        ":CAT_MGR_ID,\n" +
                        ":MAX_ISSUE_CNT,\n" +
                        ":FIRST_UPDT_BY_ID,\n" +
                        ":LAST_UPDT_BY_ID,\n" +
                        ":CPN_PRNT_SUPPRESS_IND,\n" +
                        ":DISCLAIMER_TXT\n" +
                        ")";

        MapSqlParameterSource campaignCouponParms = new MapSqlParameterSource();

        campaignCouponParms.addValue("CMPGN_ID", campaignCoupon.getCmpgnId(), Types.INTEGER);
        campaignCouponParms.addValue("RWRD_QTY", campaignCoupon.getRwrdQty(), Types.INTEGER);
        campaignCouponParms.addValue("CPN_NBR", campaignCoupon.getCpnNbr(), Types.INTEGER);
        campaignCouponParms.addValue("START_DT", campaignCoupon.getStartDt(), Types.DATE);
        campaignCouponParms.addValue("END_DT", campaignCoupon.getEndDt(), Types.DATE);
        campaignCouponParms.addValue("CPN_DSC", campaignCoupon.getCpnDsc(), Types.VARCHAR);
        campaignCouponParms.addValue("MAX_REDEEM_AMT", campaignCoupon.getMaxRedeemAmt(), Types.INTEGER);
        campaignCouponParms.addValue("CPN_TERMS_CD", campaignCoupon.getCpnTermsCd(), Types.INTEGER);
        campaignCouponParms.addValue("AMT_TYPE_CD", campaignCoupon.getAmtTypeCd(), Types.VARCHAR);
        campaignCouponParms.addValue("PCT_OFF_AMT", campaignCoupon.getPctOffAmt(), Types.INTEGER);
        campaignCouponParms.addValue("LOADABLE_IND", campaignCoupon.getLoadableInd(), Types.VARCHAR);
        campaignCouponParms.addValue("FNDG_CD", campaignCoupon.getFndgCd(), Types.VARCHAR);
        campaignCouponParms.addValue("BILLING_TYPE_CD", campaignCoupon.getBillingTypeCd(), Types.VARCHAR);
        campaignCouponParms.addValue("CPN_RECPT_TXT", campaignCoupon.getCpnRecptTxt(), Types.VARCHAR);
        campaignCouponParms.addValue("CPN_VAL_RQRD_CD", campaignCoupon.getCpnValRqrdCd(), Types.VARCHAR);
        campaignCouponParms.addValue("ABS_AMT_IND", campaignCoupon.getAbsAmtInd(), Types.VARCHAR);
        campaignCouponParms.addValue("ITEM_LIMIT_QTY", campaignCoupon.getItemLimitQty(), Types.INTEGER);
        campaignCouponParms.addValue("CPN_FMT_CD", campaignCoupon.getCpnFmtCd(), Types.VARCHAR);
        campaignCouponParms.addValue("FREE_ITEM_IND", campaignCoupon.getFreeItemInd(), Types.VARCHAR);
        campaignCouponParms.addValue("IMAGE_URL_TXT", campaignCoupon.getImageUrlTxt(), Types.VARCHAR);
        campaignCouponParms.addValue("LAST_UPDT_TS", campaignCoupon.getLastUpdtTs(), Types.TIMESTAMP);
        campaignCouponParms.addValue("CPN_CAT_LIST_XML", campaignCoupon.getCpnCatListXml(), Types.VARCHAR);
        campaignCouponParms.addValue("CPN_CAT_LIST_JSON", campaignCoupon.getCpnCatListJson(), Types.VARCHAR);
        campaignCouponParms.addValue("CPN_OLTP_HOLD_IND", campaignCoupon.getCpnOltpHoldInd(), Types.VARCHAR);
        campaignCouponParms.addValue("CARD_VAL_CD", campaignCoupon.getCardValCd(), Types.INTEGER);
        campaignCouponParms.addValue("CAT_MGR_ID", campaignCoupon.getCatMgrId(), Types.VARCHAR);
        campaignCouponParms.addValue("MAX_ISSUE_CNT", campaignCoupon.getMaxIssueCnt(), Types.INTEGER);
        campaignCouponParms.addValue("FIRST_UPDT_BY_ID", campaignCoupon.getFirstUpdtById(), Types.VARCHAR);
        campaignCouponParms.addValue("LAST_UPDT_BY_ID", campaignCoupon.getLastUpdtById(), Types.VARCHAR);
        campaignCouponParms.addValue("CPN_PRNT_SUPPRESS_IND", campaignCoupon.getCpnPrntSuppressInd(), Types.VARCHAR);
        campaignCouponParms.addValue("DISCLAIMER_TXT", campaignCoupon.getDisclaimerTxt(), Types.VARCHAR);


        int campaignCouponRowCnt = jdbcTemplate.update(campaignCouponSql, campaignCouponParms);
        log.info("Total Number of Cards inserted into CAMPAIGN_COUPON table : [{}]", campaignCouponRowCnt);
    }

    public Integer selectMVCampaignActiveInstant(MvCampaignActiveInstant mvCampaignActiveInstant) {
        String mvCampaignActiveInstantsql =
                "SELECT COUNT(*)\n" +
                        "FROM MV_CAMPAIGN_ACTIVE_INSTANT\n" +
                        "WHERE CMPGN_ID=\n" +
                        " :CMPGN_ID\n";
        MapSqlParameterSource mvSelParms = new MapSqlParameterSource();
        mvSelParms.addValue("CMPGN_ID", mvCampaignActiveInstant.getCmpgnId(), Types.INTEGER);
        return (Integer) jdbcTemplate.queryForObject(mvCampaignActiveInstantsql, mvSelParms, Integer.class);
    }


    public Integer checkCampaign(Campaign campaign) {
        String checkcampaignsql =
                "SELECT COUNT(*)\n" +
                        "FROM CAMPAIGN\n" +
                        "WHERE CMPGN_ID=\n" +
                        " :CMPGN_ID\n";
        MapSqlParameterSource checkCmpgnParms = new MapSqlParameterSource();
        checkCmpgnParms.addValue("CMPGN_ID", campaign.getCmpgnId(), Types.INTEGER);
        return (Integer) jdbcTemplate.queryForObject(checkcampaignsql, checkCmpgnParms, Integer.class);
    }

    public Integer checkCampaignCoupon(CampaignCoupon campaignCoupon) {
        String checkCampaignCouponsql =
                "SELECT COUNT(*)\n" +
                        "FROM CAMPAIGN_COUPON\n" +
                        " WHERE CPN_NBR=\n" +
                        " :CPN_NBR\n" +
                        " AND CMPGN_ID=\n" +
                        " :CMPGN_ID \n";
        MapSqlParameterSource checkCmpgnParms = new MapSqlParameterSource();
        checkCmpgnParms.addValue("CMPGN_ID", campaignCoupon.getCmpgnId(), Types.INTEGER);
        checkCmpgnParms.addValue("CPN_NBR", campaignCoupon.getCpnNbr(), Types.INTEGER);
        return (Integer) jdbcTemplate.queryForObject(checkCampaignCouponsql, checkCmpgnParms, Integer.class);
    }

    public Integer checkQEBCampaign(Campaign campaign) {
        String checkQEBCampaignsql =
                "SELECT CMPGN_ID FROM CAMPAIGN\n" +
                        " WHERE CMPGN_TYPE_CD=\n" +
                        " :CMPGN_TYPE_CD\n" +
                        " AND CMPGN_SUBTYPE_CD=\n" +
                        " :CMPGN_SUBTYPE_CD\n" +
                        "AND MKTG_PRG_CD=\n" +
                        " :MKTG_PRG_CD\n" +
                        "AND SYSDATE >= START_DT\n" +
                        "AND CMPGN_ID <> \n" +
                        " :CMPGN_ID \n" +
                        "ORDER BY END_DT DESC\n " +
                        "FETCH FIRST 1 ROWS ONLY\n";
        MapSqlParameterSource checkCmpgnParms = new MapSqlParameterSource();
        checkCmpgnParms.addValue("CMPGN_ID", campaign.getCmpgnId(), Types.INTEGER);
        checkCmpgnParms.addValue("CMPGN_TYPE_CD", campaign.getCmpgnTypeCd(), Types.VARCHAR);
        checkCmpgnParms.addValue("CMPGN_SUBTYPE_CD", campaign.getCmpgnSubtypeCd(), Types.VARCHAR);
        checkCmpgnParms.addValue("MKTG_PRG_CD", campaign.getMktgPrgCd(), Types.VARCHAR);
        return (Integer) jdbcTemplate.queryForObject(checkQEBCampaignsql, checkCmpgnParms, Integer.class);
    }

    public String selectCampaignOMCoupon(CampaignOMCoupon campaignOMCoupon) {
        String campaignOMCouponSql =
                "SELECT MFR_LAST_REDEEM_TSWTZ FROM CAMPAIGN_O_M_COUPON\n" +
                        " WHERE CMPGN_ID=\n" +
                        " :CMPGN_ID\n" +
                        " AND CPN_NBR=\n" +
                        " :CPN_NBR\n";
        MapSqlParameterSource checkCmpgnParms = new MapSqlParameterSource();
        checkCmpgnParms.addValue("CMPGN_ID", campaignOMCoupon.getCmpgnId(), Types.INTEGER);
        checkCmpgnParms.addValue("CPN_NBR", campaignOMCoupon.getCpnNbr(), Types.VARCHAR);
        checkCmpgnParms.addValue("MFR_LAST_REDEEM_TSWTZ", campaignOMCoupon.getMfrLastRedeemTswtz(), Types.TIMESTAMP_WITH_TIMEZONE);
        return (String) jdbcTemplate.queryForObject(campaignOMCouponSql, checkCmpgnParms, String.class);
    }
    
    public List<Map<String, Object>> getActiveCmpgnOMCoupons(int cmpgnID) {
        String sqlQuery =
                "SELECT * FROM CAMPAIGN_O_M_COUPON where CMPGN_ID= " + cmpgnID + " and sysdate between MFR_FIRST_ISSUE_TSWTZ and MFR_LAST_ISSUE_TSWTZ";

        MapSqlParameterSource  mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("CMPGN_ID", cmpgnID, Types.INTEGER);
        return jdbcTemplate.queryForList(sqlQuery, mapSqlParameterSource);
    }
    
    public List<Map<String, Object>> getCmpgnCpnsData(int cmpgnID, int cpnNbr) {
        String sqlQuery =
                "SELECT * FROM CAMPAIGN_COUPON where CMPGN_ID= " + cmpgnID + " and CPN_NBR= " + cpnNbr + "";

        MapSqlParameterSource  mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("CMPGN_ID", cmpgnID, Types.INTEGER);
        mapSqlParameterSource.addValue("CPN_NBR", cpnNbr, Types.INTEGER);
        return jdbcTemplate.queryForList(sqlQuery, mapSqlParameterSource);
    }
    
    public List<Map<String, Object>> getCmpgnOMCpnsData(int cmpgnID, int cpnNbr) {
        String sqlQuery =
                "SELECT * FROM CAMPAIGN_O_M_COUPON where CMPGN_ID= " + cmpgnID + " and CPN_NBR= " + cpnNbr + "";

        MapSqlParameterSource  mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("CMPGN_ID", cmpgnID, Types.INTEGER);
        mapSqlParameterSource.addValue("CPN_NBR", cpnNbr, Types.INTEGER);
        return jdbcTemplate.queryForList(sqlQuery, mapSqlParameterSource);
    }
    
    public String getCmpgnCpnsData1(String value, int cmpgnID, int cpnNbr) {
        String sqlQuery =
                "SELECT " + value + " FROM CAMPAIGN_COUPON where CMPGN_ID= " + cmpgnID + " and CPN_NBR= " + cpnNbr + "";

        MapSqlParameterSource  mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("CMPGN_ID", cmpgnID, Types.INTEGER);
        mapSqlParameterSource.addValue("CPN_NBR", cpnNbr, Types.INTEGER);
        return (String) jdbcTemplate.queryForObject(sqlQuery, mapSqlParameterSource, String.class);
    }
    
//    public List<Map<String, Object>> getCmpgnOMCpnsData(int cmpgnID, int cpnNbr) {
//        String sqlQuery =
//                "SELECT * FROM CAMPAIGN_O_M_COUPON where CMPGN_ID= " + cmpgnID + " and CPN_NBR= " + cpnNbr + "";
//
//        MapSqlParameterSource  mapSqlParameterSource = new MapSqlParameterSource();
//        mapSqlParameterSource.addValue("CMPGN_ID", cmpgnID, Types.INTEGER);
//        mapSqlParameterSource.addValue("CPN_NBR", cpnNbr, Types.INTEGER);
//        return jdbcTemplate.queryForList(sqlQuery, mapSqlParameterSource);
//    }
    
    public Integer selectRecentCampaignOMCoupon() {
        String campaignOMCouponSql =
                "SELECT CPN_NBR FROM CAMPAIGN_O_M_COUPON\n" +
                        " WHERE SYSDATE BETWEEN MFR_FIRST_ISSUE_TSWTZ AND MFR_LAST_ISSUE_TSWTZ\n" +
                        "FETCH FIRST 1 ROWS ONLY";
        MapSqlParameterSource checkCmpgnParms = new MapSqlParameterSource();
//        checkCmpgnParms.addValue("CMPGN_ID", campaignOMCoupon.getCmpgnId(), Types.INTEGER);
//        checkCmpgnParms.addValue("CPN_NBR", campaignOMCoupon.getCpnNbr(), Types.VARCHAR);
//        checkCmpgnParms.addValue("MFR_LAST_REDEEM_TSWTZ", campaignOMCoupon.getMfrLastRedeemTswtz(), Types.TIMESTAMP_WITH_TIMEZONE);
        return (Integer) jdbcTemplate.queryForObject(campaignOMCouponSql, checkCmpgnParms, Integer.class);
    }


    public Integer selectBCCampaign(Campaign campaign) {
        String checkBCCampaignsql =
                "SELECT CMPGN_ID FROM CAMPAIGN\n" +
                        " WHERE CMPGN_TYPE_CD='F' AND CMPGN_SUBTYPE_CD='L' AND MKTG_PRG_CD='B'\n" +
                        " AND TRUNC(SYSDATE) >= TRUNC(START_DT)\n" +
                        " AND TRUNC(SYSDATE) <= TRUNC(END_DT)\n" +
                        " AND XTRA_CARD_SEG_NBR IS NOT NULL\n";
        MapSqlParameterSource checkCmpgnParms = new MapSqlParameterSource();
        checkCmpgnParms.addValue("CMPGN_ID", campaign.getCmpgnId(), Types.INTEGER);
        checkCmpgnParms.addValue("CMPGN_TYPE_CD", campaign.getCmpgnTypeCd(), Types.VARCHAR);
        checkCmpgnParms.addValue("CMPGN_SUBTYPE_CD", campaign.getCmpgnSubtypeCd(), Types.VARCHAR);
        checkCmpgnParms.addValue("MKTG_PRG_CD", campaign.getMktgPrgCd(), Types.VARCHAR);
        return (Integer) jdbcTemplate.queryForObject(checkBCCampaignsql, checkCmpgnParms, Integer.class);
    }
    
    public Integer selectInstantQEBCampaign(Campaign campaign) {
        String checkBCCampaignsql =
                "SELECT CMPGN_ID FROM CAMPAIGN\n" +
                        " WHERE CMPGN_TYPE_CD='E' AND CMPGN_SUBTYPE_CD='S'\n" +
                        " AND SYSDATE BETWEEN START_DT AND END_DT\n" +
                        " AND SYSDATE BETWEEN FIRST_ISSUE_DT AND LAST_ISSUE_DT\n" +
                        " AND IN_HOME_DT BETWEEN START_DT AND END_DT\n" +
                        "FETCH FIRST 1 ROWS ONLY";
        MapSqlParameterSource checkCmpgnParms = new MapSqlParameterSource();
        checkCmpgnParms.addValue("CMPGN_ID", campaign.getCmpgnId(), Types.INTEGER);
        checkCmpgnParms.addValue("CMPGN_TYPE_CD", campaign.getCmpgnTypeCd(), Types.VARCHAR);
        checkCmpgnParms.addValue("CMPGN_SUBTYPE_CD", campaign.getCmpgnSubtypeCd(), Types.VARCHAR);
        checkCmpgnParms.addValue("MKTG_PRG_CD", campaign.getMktgPrgCd(), Types.VARCHAR);
        return (Integer) jdbcTemplate.queryForObject(checkBCCampaignsql, checkCmpgnParms, Integer.class);
        
//        select CMPGN_ID from campaign where CMPGN_TYPE_CD ='E' and CMPGN_SUBTYPE_CD= 'S' and sysdate between START_DT and END_DT  
//        		and sysdate between FIRST_ISSUE_DT and  LAST_ISSUE_DT and  IN_HOME_DT between START_DT and END_DT;
//
//        		"SELECT CPN_NBR FROM CAMPAIGN_O_M_COUPON\n" +
//        		                        " WHERE SYSDATE BETWEEN MFR_FIRST_ISSUE_TSWTZ AND MFR_LAST_ISSUE_TSWTZ\n" +
//        		                        "FETCH FIRST 1 ROWS ONLY";
    }


    public Integer selectBCMaxRewrdAmtCampaign(Campaign campaign) {
        String checkBCCampaignsql =
                "SELECT MAX_RWRD_AMT FROM CAMPAIGN\n" +
                        " WHERE CMPGN_ID=\n" +
                        " :CMPGN_ID\n";
        MapSqlParameterSource checkCmpgnParms = new MapSqlParameterSource();
        checkCmpgnParms.addValue("CMPGN_ID", campaign.getCmpgnId(), Types.INTEGER);
        checkCmpgnParms.addValue("MAX_RWRD_AMT", campaign.getMaxRwrdAmt(), Types.INTEGER);
        return (Integer) jdbcTemplate.queryForObject(checkBCCampaignsql, checkCmpgnParms, Integer.class);
    }

    public Integer selectDaystoPrintCntCampaign(Campaign campaign) {
        String checkDaystoPrintCntCampaignsql =
                "SELECT DAYS_TO_PRNT_CNT FROM CAMPAIGN\n" +
                        " WHERE CMPGN_ID=\n" +
                        " :CMPGN_ID\n";
        MapSqlParameterSource checkCmpgnParms = new MapSqlParameterSource();
        checkCmpgnParms.addValue("CMPGN_ID", campaign.getCmpgnId(), Types.INTEGER);
        return (Integer) jdbcTemplate.queryForObject(checkDaystoPrintCntCampaignsql, checkCmpgnParms, Integer.class);
    }


    public Integer selectPercentBCCampaign(Campaign campaign) {
        String checkPercentBCCampaignsql =
                "SELECT CMPGN_ID FROM CAMPAIGN\n" +
                        " WHERE CMPGN_TYPE_CD='E' AND (CMPGN_SUBTYPE_CD='L' OR CMPGN_SUBTYPE_CD='P')\n" +
                        " AND MKTG_PRG_CD='B'\n" +
                        " AND TRUNC(SYSDATE) >= TRUNC(START_DT)\n" +
                        " AND TRUNC(SYSDATE) <= TRUNC(END_DT)\n" +
                        " AND XTRA_CARD_SEG_NBR IS NOT NULL\n";
        MapSqlParameterSource checkCmpgnParms = new MapSqlParameterSource();
        checkCmpgnParms.addValue("CMPGN_ID", campaign.getCmpgnId(), Types.INTEGER);
        checkCmpgnParms.addValue("CMPGN_TYPE_CD", campaign.getCmpgnTypeCd(), Types.VARCHAR);
        checkCmpgnParms.addValue("CMPGN_SUBTYPE_CD", campaign.getCmpgnSubtypeCd(), Types.VARCHAR);
        checkCmpgnParms.addValue("MKTG_PRG_CD", campaign.getMktgPrgCd(), Types.VARCHAR);
        return (Integer) jdbcTemplate.queryForObject(checkPercentBCCampaignsql, checkCmpgnParms, Integer.class);
    }


    public Integer selectSegBCCampaign(Campaign campaign) {
        String checkPercentBCCampaignsql =
                "SELECT XTRA_CARD_SEG_NBR FROM CAMPAIGN\n" +
                        " WHERE CMPGN_ID=\n" +
                        " :CMPGN_ID\n";
        MapSqlParameterSource checkCmpgnParms = new MapSqlParameterSource();
        checkCmpgnParms.addValue("XTRA_CARD_SEG_NBR", campaign.getXtraCardSegNbr(), Types.INTEGER);
        checkCmpgnParms.addValue("CMPGN_ID", campaign.getCmpgnId(), Types.VARCHAR);
        return (Integer) jdbcTemplate.queryForObject(checkPercentBCCampaignsql, checkCmpgnParms, Integer.class);
    }

    public Date selectStartDtBCCampaign(Campaign campaign) {
        String checkBCCampaignsql =
                "SELECT START_DT FROM CAMPAIGN\n" +
                        " WHERE CMPGN_ID=\n" +
                        " :CMPGN_ID\n";
        MapSqlParameterSource checkCmpgnParms = new MapSqlParameterSource();
        checkCmpgnParms.addValue("START_DT", campaign.getStartDt(), Types.DATE);
        checkCmpgnParms.addValue("CMPGN_ID", campaign.getCmpgnId(), Types.VARCHAR);
        return (Date) jdbcTemplate.queryForObject(checkBCCampaignsql, checkCmpgnParms, Date.class);
    }


    public Date selectEndDtBCCampaign(Campaign campaign) {
        String checkBCCampaignsql =
                "SELECT END_DT FROM CAMPAIGN\n" +
                        " WHERE CMPGN_ID=\n" +
                        " :CMPGN_ID\n";
        MapSqlParameterSource checkCmpgnParms = new MapSqlParameterSource();
        checkCmpgnParms.addValue("END_DT", campaign.getEndDt(), Types.DATE);
        checkCmpgnParms.addValue("CMPGN_ID", campaign.getCmpgnId(), Types.VARCHAR);
        return (Date) jdbcTemplate.queryForObject(checkBCCampaignsql, checkCmpgnParms, Date.class);
    }

    public Integer selectPercentBCSegCampaign(Campaign campaign) {
        String checkPercentBCCampaignsql =
                "SELECT XTRA_CARD_SEG_NBR FROM CAMPAIGN\n" +
                        " WHERE CMPGN_TYPE_CD='F'\n " +
                        "AND (CMPGN_SUBTYPE_CD='L' OR CMPGN_SUBTYPE_CD='P')\n" +
                        " AND MKTG_PRG_CD='B'\n" +
                        " AND SYSDATE >= START_DT\n" +
                        " AND SYSDATE <= END_DT\n" +
                        " AND XTRA_CARD_SEG_NBR IS NOT NULL\n";
        MapSqlParameterSource checkCmpgnParms = new MapSqlParameterSource();
        checkCmpgnParms.addValue("XTRA_CARD_SEG_NBR", campaign.getXtraCardSegNbr(), Types.INTEGER);
        checkCmpgnParms.addValue("CMPGN_ID", campaign.getCmpgnId(), Types.INTEGER);
        checkCmpgnParms.addValue("CMPGN_TYPE_CD", campaign.getCmpgnTypeCd(), Types.VARCHAR);
        checkCmpgnParms.addValue("CMPGN_SUBTYPE_CD", campaign.getCmpgnSubtypeCd(), Types.VARCHAR);
        checkCmpgnParms.addValue("MKTG_PRG_CD", campaign.getMktgPrgCd(), Types.VARCHAR);
        return (Integer) jdbcTemplate.queryForObject(checkPercentBCCampaignsql, checkCmpgnParms, Integer.class);
    }


    public Integer selectFreeBCSegCampaign(Campaign campaign) {
        String checkBCCampaignsql =
                "SELECT XTRA_CARD_SEG_NBR FROM CAMPAIGN\n" +
                        " WHERE CMPGN_TYPE_CD='F'\n" + 
                        "AND CMPGN_SUBTYPE_CD='L'\n" +
                        " AND MKTG_PRG_CD='B'\n" +
                        " AND TRUNC(SYSDATE) >= TRUNC(START_DT)\n" +
                        " AND TRUNC(SYSDATE) <= TRUNC(END_DT)\n" +
                        " AND XTRA_CARD_SEG_NBR IS NOT NULL\n";
        MapSqlParameterSource checkCmpgnParms = new MapSqlParameterSource();
        checkCmpgnParms.addValue("XTRA_CARD_SEG_NBR", campaign.getXtraCardSegNbr(), Types.INTEGER);
        checkCmpgnParms.addValue("CMPGN_ID", campaign.getCmpgnId(), Types.INTEGER);
        checkCmpgnParms.addValue("CMPGN_TYPE_CD", campaign.getCmpgnTypeCd(), Types.VARCHAR);
        checkCmpgnParms.addValue("CMPGN_SUBTYPE_CD", campaign.getCmpgnSubtypeCd(), Types.VARCHAR);
        checkCmpgnParms.addValue("MKTG_PRG_CD", campaign.getMktgPrgCd(), Types.VARCHAR);
        return (Integer) jdbcTemplate.queryForObject(checkBCCampaignsql, checkCmpgnParms, Integer.class);
    }

    public Integer checkBCCampaign(Campaign campaign) {
        String checkBCCampaignsql =
                "SELECT count(*) FROM CAMPAIGN\n" +
                        " WHERE CMPGN_TYPE_CD='F' \n" +
                        "AND CMPGN_SUBTYPE_CD='L'\n" +
                        " AND MKTG_PRG_CD='B'\n" +
                        " AND TRUNC(SYSDATE) >= TRUNC(START_DT)\n" +
                        " AND TRUNC(SYSDATE) <= TRUNC(END_DT)\n" +
                        " AND XTRA_CARD_SEG_NBR IS NOT NULL\n";
        MapSqlParameterSource checkCmpgnParms = new MapSqlParameterSource();
        checkCmpgnParms.addValue("CMPGN_ID", campaign.getCmpgnId(), Types.INTEGER);
        checkCmpgnParms.addValue("CMPGN_TYPE_CD", campaign.getCmpgnTypeCd(), Types.VARCHAR);
        checkCmpgnParms.addValue("CMPGN_SUBTYPE_CD", campaign.getCmpgnSubtypeCd(), Types.VARCHAR);
        checkCmpgnParms.addValue("MKTG_PRG_CD", campaign.getMktgPrgCd(), Types.VARCHAR);
        return (Integer) jdbcTemplate.queryForObject(checkBCCampaignsql, checkCmpgnParms, Integer.class);
    }


    public Integer checkBCPercentCampaign(Campaign campaign) {
        String checkBCCampaignsql =
                "SELECT count(*) FROM CAMPAIGN\n" +
                        " WHERE CMPGN_TYPE_CD='E'\n" +
                        "AND (CMPGN_SUBTYPE_CD='L' OR CMPGN_SUBTYPE_CD='P')\n" +
                        " AND MKTG_PRG_CD='B'\n" +
                        " AND SYSDATE >= START_DT\n" +
                        " AND SYSDATE <= END_DT\n" +
                        " AND XTRA_CARD_SEG_NBR IS NOT NULL\n";
        MapSqlParameterSource checkCmpgnParms = new MapSqlParameterSource();
        checkCmpgnParms.addValue("CMPGN_TYPE_CD", campaign.getCmpgnTypeCd(), Types.VARCHAR);
        checkCmpgnParms.addValue("CMPGN_SUBTYPE_CD", campaign.getCmpgnSubtypeCd(), Types.VARCHAR);
        checkCmpgnParms.addValue("CMPGN_SUBTYPE_CD", campaign.getCmpgnSubtypeCd(), Types.VARCHAR);
        checkCmpgnParms.addValue("MKTG_PRG_CD", campaign.getMktgPrgCd(), Types.VARCHAR);
        return (Integer) jdbcTemplate.queryForObject(checkBCCampaignsql, checkCmpgnParms, Integer.class);
    }

    public Integer selectCriteriaSku(CriteriaSku criteriaSku) {
        String criteriaSkusql =
                "SELECT count(*)\n" +
                        "FROM CRITERIA_SKU\n" +
                        "WHERE CRITERIA_ID=\n" +
                        " :CRITERIA_ID\n";
        MapSqlParameterSource criteriaSelParms = new MapSqlParameterSource();
        criteriaSelParms.addValue("CRITERIA_ID", criteriaSku.getCriteriaId(), Types.INTEGER);
        return (Integer) jdbcTemplate.queryForObject(criteriaSkusql, criteriaSelParms, Integer.class);
    }


    public Integer selectCampaignStoreSku(CampaignStoreSku campaignStoreSku) {
        // Count CAMPAIGN_STORE_SKU Record
        String campaignStoreSkuSql =
                "SELECT count(*) FROM CAMPAIGN_STORE_SKU\n" +
                        " WHERE SKU_NBR=\n" +
                        " :SKU_NBR \n" +
                        " AND CMPGN_ID=\n" +
                        " :CMPGN_ID \n";

        MapSqlParameterSource campaignStoreSkuParms = new MapSqlParameterSource();
        campaignStoreSkuParms.addValue("CMPGN_ID", campaignStoreSku.getCmpgnId(), Types.INTEGER);
        //campaignStoreSkuParms.addValue("CPN_NBR", campaignCoupon.getCpnNbr(), Types.INTEGER);
        campaignStoreSkuParms.addValue("SKU_NBR", campaignStoreSku.getSkuNbr(), Types.INTEGER);
        return (Integer) jdbcTemplate.queryForObject(campaignStoreSkuSql, campaignStoreSkuParms, Integer.class);
    }

    public void createCampaignStoreSku(CampaignStoreSku campaignStoreSku) {
        // Create new CampaignStoreSku Record
        String campStoreSkuSql =
                "INSERT INTO CAMPAIGN_STORE_SKU\n" +
                        "(\n" +
                        "CMPGN_ID,\n" +
                        "STORE_NBR,\n" +
                        "SKU_NBR,\n" +
                        "START_DT,\n" +
                        "END_DT\n" +
                        ")\n" +
                        "values\n" +
                        "(\n" +
                        ":CMPGN_ID,\n" +
                        ":STORE_NBR,\n" +
                        ":SKU_NBR,\n" +
                        ":START_DT,\n" +
                        ":END_DT\n" +
                        ")";

        MapSqlParameterSource campStoreSkuParms = new MapSqlParameterSource();

        campStoreSkuParms.addValue("CMPGN_ID", campaignStoreSku.getCmpgnId(), Types.INTEGER);
        campStoreSkuParms.addValue("STORE_NBR", campaignStoreSku.getStoreNbr(), Types.INTEGER);
        campStoreSkuParms.addValue("SKU_NBR", campaignStoreSku.getSkuNbr(), Types.INTEGER);
        campStoreSkuParms.addValue("START_DT", campaignStoreSku.getStartDt(), Types.TIMESTAMP);
        campStoreSkuParms.addValue("END_DT", campaignStoreSku.getEndDt(), Types.TIMESTAMP);

        int campStoreSkuRowCnt = jdbcTemplate.update(campStoreSkuSql, campStoreSkuParms);
        log.info("Total Number of Cards inserted into CAMPAIGN_STORE_SKU table : [{}]", campStoreSkuRowCnt);
    }

    public void CreateCampaignCouponCriteria(CampaignCouponCriteria campaignCouponCriteria) {
        // Create new CampaigncouponCriteria Record
        String campCouponCriteriaSql =
                "INSERT INTO CAMPAIGN_COUPON_CRITERIA\n" +
                        "(\n" +
                        "CMPGN_ID,\n" +
                        "CPN_NBR,\n" +
                        "CRITERIA_USAGE_CD,\n" +
                        "ALL_ITEM_IND,\n" +
                        "CRITERIA_ID,\n" +
                        "INC_EXC_CD,\n" +
                        "RQRD_PURCH_AMT,\n" +
                        "AMT_TYPE_CD,\n" +
                        "LAST_CHNG_DT\n" +
                        ")\n" +
                        "values\n" +
                        "(\n" +
                        ":CMPGN_ID,\n" +
                        ":CPN_NBR,\n" +
                        ":CRITERIA_USAGE_CD,\n" +
                        ":ALL_ITEM_IND,\n" +
                        ":CRITERIA_ID,\n" +
                        ":INC_EXC_CD,\n" +
                        ":RQRD_PURCH_AMT,\n" +
                        ":AMT_TYPE_CD,\n" +
                        ":LAST_CHNG_DT\n" +
                        ")";

        MapSqlParameterSource campCouponCriteriaParms = new MapSqlParameterSource();

        campCouponCriteriaParms.addValue("CMPGN_ID", campaignCouponCriteria.getCmpgnId(), Types.INTEGER);
        campCouponCriteriaParms.addValue("CPN_NBR", campaignCouponCriteria.getCpnNbr(), Types.INTEGER);
        campCouponCriteriaParms.addValue("CRITERIA_USAGE_CD", campaignCouponCriteria.getCriteriaUsageCd(), Types.VARCHAR);
        campCouponCriteriaParms.addValue("ALL_ITEM_IND", campaignCouponCriteria.getAllItemInd(), Types.VARCHAR);
        campCouponCriteriaParms.addValue("CRITERIA_ID", campaignCouponCriteria.getCriteriaId(), Types.INTEGER);
        campCouponCriteriaParms.addValue("INC_EXC_CD", campaignCouponCriteria.getIncExcCd(), Types.VARCHAR);
        campCouponCriteriaParms.addValue("RQRD_PURCH_AMT", campaignCouponCriteria.getRqrdPurchAmt(), Types.INTEGER);
        campCouponCriteriaParms.addValue("AMT_TYPE_CD", campaignCouponCriteria.getAmtTypeCd(), Types.VARCHAR);
        campCouponCriteriaParms.addValue("LAST_CHNG_DT", campaignCouponCriteria.getLastChngDt(), Types.DATE);

        int campCouponsCriteriaCnt = jdbcTemplate.update(campCouponCriteriaSql, campCouponCriteriaParms);
        log.info("Total Number of Cards inserted into CAMPAIGN_COUPON_CRITERIA table : [{}]", campCouponsCriteriaCnt);
    }
    
    public void CreateCampaignCouponSkuRank(CampaignCouponSkuRank campaignCouponSkuRank) {
        // Create new CampaigncouponCriteria Record
        String campCouponSkuRankSql =
                "INSERT INTO CAMPAIGN_COUPON_SKU_RANK\n" +
                        "(\n" +
                        "CMPGN_ID,\n" +
                        "CPN_NBR,\n" +
                        "SKU_NBR,\n" +
                        "SKU_RANK_NBR\n" +
                        ")\n" +
                        "values\n" +
                        "(\n" +
                        ":CMPGN_ID,\n" +
                        ":CPN_NBR,\n" +
                        ":SKU_NBR,\n" +
                        ":SKU_RANK_NBR\n" +
                        ")";

        MapSqlParameterSource campCouponSkuRankParms = new MapSqlParameterSource();

        campCouponSkuRankParms.addValue("CMPGN_ID", campaignCouponSkuRank.getCmpgnId(), Types.INTEGER);
        campCouponSkuRankParms.addValue("CPN_NBR", campaignCouponSkuRank.getCpnNbr(), Types.INTEGER);
        campCouponSkuRankParms.addValue("SKU_NBR", campaignCouponSkuRank.getSkuNbr(), Types.INTEGER);
        campCouponSkuRankParms.addValue("SKU_RANK_NBR", campaignCouponSkuRank.getSkuRankNbr(), Types.INTEGER);
       
        int campCouponsSkuRankCnt = jdbcTemplate.update(campCouponSkuRankSql, campCouponSkuRankParms);
        log.info("Total Number of SKUs inserted into CAMPAIGN_COUPON_SKU_RANK table : [{}]", campCouponsSkuRankCnt);
    }

    public void createCampaignPoint(CampaignPoint campaignPoint) {
        // Create new CampaignPoint Record
        String campPointSql =
                "INSERT INTO CAMPAIGN_POINT\n" +
                        "(\n" +
                        "CMPGN_ID,\n" +
                        "XTRA_CARD_NBR,\n" +
                        "PTS_QTY,\n" +
                        "PTS_ADJ_QTY,\n" +
                        "PTS_OTHER_QTY,\n" +
                        "LAST_UPDT_DT,\n" +
                        "PTS_PEAK_QTY,\n" +
                        "PTS_XFER_OUT_QTY,\n" +
                        "PTS_ADJ_XFER_IN_QTY,\n" +
                        "PTS_ADJ_XFER_OUT_QTY,\n" +
                        "PTS_OTHER_XFER_IN_QTY,\n" +
                        "PTS_OTHER_XFER_OUT_QTY,\n" +
                        "ACTIVATION_TS,\n" +
                        "ACTIVATION_SRC_CD\n" +

                        ")\n" +
                        "values\n" +
                        "(\n" +
                        ":CMPGN_ID,\n" +
                        ":XTRA_CARD_NBR,\n" +
                        ":PTS_QTY,\n" +
                        ":PTS_ADJ_QTY,\n" +
                        ":PTS_OTHER_QTY,\n" +
                        ":LAST_UPDT_DT,\n" +
                        ":PTS_PEAK_QTY,\n" +
                        ":PTS_XFER_OUT_QTY,\n" +
                        ":PTS_ADJ_XFER_IN_QTY,\n" +
                        ":PTS_ADJ_XFER_OUT_QTY,\n" +
                        ":PTS_OTHER_XFER_IN_QTY,\n" +
                        ":PTS_OTHER_XFER_OUT_QTY,\n" +
                        ":ACTIVATION_TS,\n" +
                        ":ACTIVATION_SRC_CD\n" +
                        ")";

        MapSqlParameterSource campPointParms = new MapSqlParameterSource();

        campPointParms.addValue("CMPGN_ID", campaignPoint.getCmpgnId(), Types.INTEGER);
        campPointParms.addValue("XTRA_CARD_NBR", campaignPoint.getXtraCardNbr(), Types.INTEGER);
        campPointParms.addValue("PTS_QTY", campaignPoint.getPtsQty(), Types.INTEGER);
        campPointParms.addValue("PTS_ADJ_QTY", campaignPoint.getPtsAdjQty(), Types.INTEGER);
        campPointParms.addValue("PTS_OTHER_QTY", campaignPoint.getPtsOtherQty(), Types.INTEGER);
        campPointParms.addValue("LAST_UPDT_DT", campaignPoint.getLastLastUpdtDt(), Types.DATE);
        campPointParms.addValue("PTS_PEAK_QTY", campaignPoint.getPtSpeakQty(), Types.INTEGER);
        campPointParms.addValue("LAST_CHNG_PTS_QTY", campaignPoint.getLastChngPtsQty(), Types.INTEGER);
        campPointParms.addValue("PTS_XFER_IN_QTY", campaignPoint.getPtsXferInQty(), Types.INTEGER);
        campPointParms.addValue("PTS_XFER_OUT_QTY", campaignPoint.getPtsXrefOutQty(), Types.INTEGER);
        campPointParms.addValue("PTS_ADJ_XFER_IN_QTY", campaignPoint.getPtsAdjXferInQty(), Types.INTEGER);
        campPointParms.addValue("PTS_ADJ_XFER_OUT_QTY", campaignPoint.getPtsAdjXferOutQty(), Types.INTEGER);
        campPointParms.addValue("PTS_OTHER_XFER_IN_QTY", campaignPoint.getPtsOtherXferInqty(), Types.INTEGER);
        campPointParms.addValue("PTS_OTHER_XFER_OUT_QTY", campaignPoint.getPtsOtherXferOutQty(), Types.INTEGER);
        campPointParms.addValue("ACTIVATION_TS", campaignPoint.getActivationTs(), Types.TIMESTAMP);
        campPointParms.addValue("ACTIVATION_SRC_CD", campaignPoint.getActivationSrcCd(), Types.VARCHAR);

        int campPointRowCnt = jdbcTemplate.update(campPointSql, campPointParms);
        log.info("Total Number of Cards inserted into CAMPAIGN_POINT table : [{}]", campPointRowCnt);
    }


    /**
     * Update CAMPAIGN_COUPON
     */
    public void updateCampaignCoupon(CampaignCoupon campaignCoupon) {
        // Create new CAMPAIGN_COUPON Record
        String campaignCouponUpSql =
                "UPDATE CAMPAIGN_COUPON\n" +
                        " SET CPN_DSC=\n" +
                        "    :CPN_DSC,\n" +
                        "CPN_RECPT_TXT=\n" +
                        "    :CPN_RECPT_TXT,\n" +
                        "START_DT=\n" +
                        "    :START_DT,\n" +
                        "END_DT=\n" +
                        "    :END_DT\n" +
                        " WHERE CPN_NBR=\n" +
                        " :CPN_NBR\n" +
                        " AND CMPGN_ID=\n" +
                        " :CMPGN_ID \n";

        MapSqlParameterSource campaignCouponUpParms = new MapSqlParameterSource();
        campaignCouponUpParms.addValue("CMPGN_ID", campaignCoupon.getCmpgnId(), Types.INTEGER);
        campaignCouponUpParms.addValue("CPN_NBR", campaignCoupon.getCpnNbr(), Types.INTEGER);
        campaignCouponUpParms.addValue("START_DT", campaignCoupon.getStartDt(), Types.DATE);
        campaignCouponUpParms.addValue("END_DT", campaignCoupon.getEndDt(), Types.DATE);
        campaignCouponUpParms.addValue("CPN_DSC", campaignCoupon.getCpnDsc(), Types.VARCHAR);
        campaignCouponUpParms.addValue("CPN_RECPT_TXT", campaignCoupon.getCpnRecptTxt(), Types.VARCHAR);

        int campaignCouponUpRowCnt = jdbcTemplate.update(campaignCouponUpSql, campaignCouponUpParms);
        log.info("Total Number of Cards updated in CAMPAIGN_COUPON table : [{}]", campaignCouponUpRowCnt);
    }
    
    public void updateCampaignCouponMFRAvailPool(CampaignCoupon campaignCoupon) {
        // Create new CAMPAIGN_COUPON Record
        String campaignCouponUpSql =
                "UPDATE CAMPAIGN_COUPON\n" +
                        " SET FREE_ITEM_IND=\n" +
                        "    :FREE_ITEM_IND\n" +
                        " WHERE CPN_NBR=\n" +
                        " :CPN_NBR\n" +
                        " AND CMPGN_ID=\n" +
                        " :CMPGN_ID \n";

        MapSqlParameterSource campaignCouponUpParms = new MapSqlParameterSource();
        campaignCouponUpParms.addValue("CMPGN_ID", campaignCoupon.getCmpgnId(), Types.INTEGER);
        campaignCouponUpParms.addValue("CPN_NBR", campaignCoupon.getCpnNbr(), Types.INTEGER);
        campaignCouponUpParms.addValue("FREE_ITEM_IND", campaignCoupon.getFreeItemInd(), Types.VARCHAR);

        int campaignCouponUpRowCnt = jdbcTemplate.update(campaignCouponUpSql, campaignCouponUpParms);
        log.info("Total Number of Cards updated in CAMPAIGN_COUPON table : [{}]", campaignCouponUpRowCnt);
    }
    
    public void updateCampaignOMCouponMFRAvailPool(CampaignOMCoupon campaignOMCoupon) {
        // Create new CAMPAIGN_COUPON Record
        String campaignOMCouponUpSql =
                "UPDATE CAMPAIGN_O_M_COUPON\n" +
                        " SET MFR_LAST_REDEEM_TSWTZ=\n" +
                        "    :MFR_LAST_REDEEM_TSWTZ\n" +
                        " WHERE CPN_NBR=\n" +
                        " :CPN_NBR\n" +
                        " AND CMPGN_ID=\n" +
                        " :CMPGN_ID \n";

        MapSqlParameterSource campaignOMCouponUpParms = new MapSqlParameterSource();
        campaignOMCouponUpParms.addValue("CMPGN_ID", campaignOMCoupon.getCmpgnId(), Types.INTEGER);
        campaignOMCouponUpParms.addValue("CPN_NBR", campaignOMCoupon.getCpnNbr(), Types.INTEGER);
        campaignOMCouponUpParms.addValue("MFR_LAST_REDEEM_TSWTZ", campaignOMCoupon.getMfrLastRedeemTswtz(), Types.VARCHAR);

        int campaignCouponUpRowCnt = jdbcTemplate.update(campaignOMCouponUpSql, campaignOMCouponUpParms);
        log.info("Total Number of Cards updated in CAMPAIGN_COUPON table : [{}]", campaignCouponUpRowCnt);
    }


    /**
     * select CAMPAIGN_COUPON
     */
    public Integer selectCampaignCoupon(CampaignCoupon campaignCoupon) {
        // Create new CAMPAIGN_COUPON Record
        String campaignCouponUpSql =
                "SELECT CPN_NBR FROM CAMPAIGN_COUPON\n" +
                        " WHERE RWRD_QTY=\n" +
                        " :RWRD_QTY\n" +
                        " AND CMPGN_ID=\n" +
                        " :CMPGN_ID \n";

        MapSqlParameterSource campaignCouponUpParms = new MapSqlParameterSource();
        campaignCouponUpParms.addValue("CMPGN_ID", campaignCoupon.getCmpgnId(), Types.INTEGER);
        //campaignCouponUpParms.addValue("CPN_NBR", campaignCoupon.getCpnNbr(), Types.INTEGER);
        campaignCouponUpParms.addValue("RWRD_QTY", campaignCoupon.getRwrdQty(), Types.INTEGER);
        return (Integer) jdbcTemplate.queryForObject(campaignCouponUpSql, campaignCouponUpParms, Integer.class);
    }
    
    /**
     * select CAMPAIGN_COUPON
     */
    public Integer selectCampaignCouponBK(CampaignCoupon campaignCoupon,double rwdQty) {
        // Create new CAMPAIGN_COUPON Record
        String campaignCouponUpSql =
                "SELECT CPN_NBR FROM CAMPAIGN_COUPON\n" +
                        " WHERE CMPGN_ID=\n" +
                        " :CMPGN_ID\n" +
                        " AND RWRD_QTY=" + rwdQty;

        MapSqlParameterSource campaignCouponUpParms = new MapSqlParameterSource();
        campaignCouponUpParms.addValue("CMPGN_ID", campaignCoupon.getCmpgnId(), Types.INTEGER);
        //campaignCouponUpParms.addValue("CPN_NBR", campaignCoupon.getCpnNbr(), Types.INTEGER);
//        campaignCouponUpParms.addValue("RWRD_QTY", campaignCoupon.getRwrdQty(), Types.INTEGER);
        return (Integer) jdbcTemplate.queryForObject(campaignCouponUpSql, campaignCouponUpParms, Integer.class);
    }
    
    /**
     * select CAMPAIGN_COUPON
     */
    public String selectCampaignCouponDesc(CampaignCoupon campaignCoupon) {
        String campaignCouponDsccSql =
                "SELECT CPN_DSC FROM CAMPAIGN_COUPON\n" +
                        " WHERE CMPGN_ID=\n" +
                        " :CMPGN_ID\n" +
                        " AND CPN_NBR=\n" +
                        " :CPN_NBR \n";

        MapSqlParameterSource campaignCouponDscParms = new MapSqlParameterSource();
        campaignCouponDscParms.addValue("CMPGN_ID", campaignCoupon.getCmpgnId(), Types.INTEGER);
        //campaignCouponUpParms.addValue("CPN_NBR", campaignCoupon.getCpnNbr(), Types.INTEGER);
        campaignCouponDscParms.addValue("CPN_NBR", campaignCoupon.getCpnNbr(), Types.INTEGER);
        return (String) jdbcTemplate.queryForObject(campaignCouponDsccSql, campaignCouponDscParms, String.class);
    }


    /**
     * Update CAMPAIGN_COUPON
     */
    public void updateCampaign(Campaign campaign) {
        // Create new CAMPAIGN Record
        String campaignUpSql =
                "UPDATE CAMPAIGN\n" +
                        " SET END_DT=\n" +
                        "    :END_DT\n" +
                        " WHERE CMPGN_ID=\n" +
                        " :CMPGN_ID \n";

        MapSqlParameterSource campaignUpParms = new MapSqlParameterSource();
        campaignUpParms.addValue("CMPGN_ID", campaign.getCmpgnId(), Types.INTEGER);
        campaignUpParms.addValue("END_DT", campaign.getEndDt(), Types.DATE);

        int campaignUpRowCnt = jdbcTemplate.update(campaignUpSql, campaignUpParms);
        log.info("Total Number of Cards updated in CAMPAIGN table : [{}]", campaignUpRowCnt);
    }
    
    /**
     * Create Campaign
     */
    public void updateCampaign1(Campaign campaign) {
        // Update Campaign Record
        String campaignUpSql =
                "UPDATE CAMPAIGN\n" +
                		" SET CMPGN_TYPE_CD=\n" +
                        "    :CMPGN_TYPE_CD,\n" +
                        "CMPGN_SUBTYPE_CD=\n" +
                        "    :CMPGN_SUBTYPE_CD,\n" +
                        "CMPGN_DSC=\n" +
                        "    :CMPGN_DSC,\n" +
                        "RECPT_PRNT_IND=\n" +
                        "    :RECPT_PRNT_IND,\n" +
                        "RECPT_PRNT_PRIORITY_NBR=\n" +
                        "    :RECPT_PRNT_PRIORITY_NBR,\n" +
                        "RECPT_TXT=\n" +
                        "    :RECPT_TXT,\n" +
                        "RECPT_SCALE_NBR=\n" +
                        "    :RECPT_SCALE_NBR,\n" +
                        "RWRD_REDIR_IND=\n" +
                        "    :RWRD_REDIR_IND,\n" +
                        "START_DT=\n" +
                        "    :START_DT,\n" +
                        "END_DT=\n" +
                        "    :END_DT,\n" +
                        "LAST_UPDT_TS=\n" +
                        "    :LAST_UPDT_TS,\n" +
                        "ISSUE_FREQ_TYPE_CD=\n" +
                        "    :ISSUE_FREQ_TYPE_CD,\n" +
                        "ISSUE_FREQ_CNT=\n" +
                        "    :ISSUE_FREQ_CNT,\n" +
                        "FIRST_ISSUE_DT=\n" +
                        "    :FIRST_ISSUE_DT,\n" +
                        "LAST_ISSUE_DT=\n" +
                        "    :LAST_ISSUE_DT,\n" +
                        "PREV_ISSUE_DT=\n" +
                        "    :PREV_ISSUE_DT,\n" +
                        "NEXT_ISSUE_DT=\n" +
                        "    :NEXT_ISSUE_DT,\n" +
                        "DAYS_TO_PRNT_CNT=\n" +
                        "    :DAYS_TO_PRNT_CNT,\n" +
                        "DAYS_TO_REDEEM_CNT=\n" +
                        "    :DAYS_TO_REDEEM_CNT,\n" +
                        "IN_HOME_DT=\n" +
                        "    :IN_HOME_DT,\n" +
                        "TOTAL_RWRD_EARN_AMT=\n" +
                        "    :TOTAL_RWRD_EARN_AMT,\n" +
                        "BONUS_SKU_CALC_DT=\n" +
                        "    :BONUS_SKU_CALC_DT,\n" +
                        "CPN_REDEEM_CALC_DT=\n" +
                        "    :CPN_REDEEM_CALC_DT,\n" +
                        "CPN_BASE_DSC=\n" +
                        "    :CPN_BASE_DSC,\n" +
                        "PARENT_CMPGN_ID=\n" +
                        "    :PARENT_CMPGN_ID,\n" +
                        "CPN_CAT_NBR=\n" +
                        "    :CPN_CAT_NBR,\n" +
                        "CPN_SEG_NBR=\n" +
                        "    :CPN_SEG_NBR,\n" +
                        "CPN_FNDG_CD=\n" +
                        "    :CPN_FNDG_CD,\n" +
                        "BILLING_TYPE_CD=\n" +
                        "    :BILLING_TYPE_CD,\n" +
                        "INDIV_RWRD_AMT=\n" +
                        "    :INDIV_RWRD_AMT,\n" +
                        "CPN_AUTOGEN_IND=\n" +
                        "    :CPN_AUTOGEN_IND,\n" +
                        "RWRD_LAST_CALC_DT=\n" +
                        "    :RWRD_LAST_CALC_DT,\n" +
                        "CSR_VISIBLE_IND=\n" +
                        "    :CSR_VISIBLE_IND,\n" +
                        "CMPGN_TERMS_TXT=\n" +
                        "    :CMPGN_TERMS_TXT,\n" +
                        "WEB_DSC=\n" +
                        "    :WEB_DSC,\n" +
                        "WEB_DISP_IND=\n" +
                        "    :WEB_DISP_IND,\n" +
                        "PAY_VENDOR_NBR=\n" +
                        "    :PAY_VENDOR_NBR,\n" +
                        "CPN_OLTP_HOLD_IND=\n" +
                        "    :CPN_OLTP_HOLD_IND,\n" +
                        "CPN_PURGE_CD=\n" +
                        "    :CPN_PURGE_CD,\n" +
                        "DFLT_CPN_TERMS_CD=\n" +
                        "    :DFLT_CPN_TERMS_CD,\n" +
                        "CAT_MGR_ID=\n" +
                        "    :CAT_MGR_ID,\n" +
                        "VENDOR_NBR=\n" +
                        "    :VENDOR_NBR,\n" +
                        "MULTI_VENDOR_IND=\n" +
                        "    :MULTI_VENDOR_IND,\n" +
                        "CPN_FIXED_DSC_IND=\n" +
                        "    :CPN_FIXED_DSC_IND,\n" +
                        "CPN_PRNT_START_DELAY_DAY_CNT=\n" +
                        "    :CPN_PRNT_START_DELAY_DAY_CNT,\n" +
                        "CPN_REDM_START_DELAY_DAY_CNT=\n" +
                        "    :CPN_REDM_START_DELAY_DAY_CNT,\n" +
                        "CPN_PRIORITY_NBR=\n" +
                        "    :CPN_PRIORITY_NBR,\n" +
                        "CPN_QUAL_TXT=\n" +
                        "    :CPN_QUAL_TXT,\n" +
                        "REQ_SKU_LIST=\n" +
                        "    :REQ_SKU_LIST,\n" +
                        "MAX_VISIT_RWRD_QTY=\n" +
                        "    :MAX_VISIT_RWRD_QTY,\n" +
                        "MAX_RWRD_QTY=\n" +
                        "    :MAX_RWRD_QTY,\n" +
                        "RWRD_RECALC_IND=\n" +
                        "    :RWRD_RECALC_IND,\n" +
                        "CMPGN_QUAL_TXT=\n" +
                        "    :CMPGN_QUAL_TXT,\n" +
                        "TRGT_PRNT_DEST_CD=\n" +
                        "    :TRGT_PRNT_DEST_CD,\n" +
                        "CPN_MIN_PURCH_AMT=\n" +
                        "    :CPN_MIN_PURCH_AMT,\n" +
                        "LAST_FEED_ACCPT_DT=\n" +
                        "    :LAST_FEED_ACCPT_DT,\n" +
                        "ADV_MAX_RWRD_QTY=\n" +
                        "    :ADV_MAX_RWRD_QTY,\n" +
                        "PROMO_LINK_NBR=\n" +
                        "    :PROMO_LINK_NBR,\n" +
                        "AMT_TYPE_CD=\n" +
                        "    :AMT_TYPE_CD,\n" +
                        "PCT_OFF_AMT=\n" +
                        "    :PCT_OFF_AMT,\n" +
                        "FSA_CPN_IND=\n" +
                        "    :FSA_CPN_IND,\n" +
                        "PRVT_LABEL_CPN_IND=\n" +
                        "    :PRVT_LABEL_CPN_IND,\n" +
                        "DFLT_ALWAYS_IND=\n" +
                        "    :DFLT_ALWAYS_IND,\n" +
                        "DFLT_FREQ_DAY_CNT=\n" +
                        "    :DFLT_FREQ_DAY_CNT,\n" +
                        "DFLT_FREQ_EMP_DAY_CNT=\n" +
                        "    :DFLT_FREQ_EMP_DAY_CNT,\n" +
                        "LOADABLE_IND=\n" +
                        "    :LOADABLE_IND,\n" +
                        "CARD_TYPE_CD=\n" +
                        "    :CARD_TYPE_CD,\n" +
                        "CPN_RECPT_TXT=\n" +
                        "    :CPN_RECPT_TXT,\n" +
                        "CPN_VAL_RQRD_CD=\n" +
                        "    :CPN_VAL_RQRD_CD,\n" +
                        "ABS_AMT_IND=\n" +
                        "    :ABS_AMT_IND,\n" +
                        "ITEM_LIMIT_QTY=\n" +
                        "    :ITEM_LIMIT_QTY,\n" +
                        "CPN_FMT_CD=\n" +
                        "    :CPN_FMT_CD,\n" +
                        "DFLT_CPN_CAT_JSON=\n" +
                        "    :DFLT_CPN_CAT_JSON,\n" +
                        "FREE_ITEM_IND=\n" +
                        "    :FREE_ITEM_IND,\n" +
                        "MKTG_PRG_CD=\n" +
                        "    :MKTG_PRG_CD,\n" +
                        "MOBILE_DISP_IND=\n" +
                        "    :MOBILE_DISP_IND,\n" +
                        "OVRD_PAPERLESS_IND=\n" +
                        "    :OVRD_PAPERLESS_IND,\n" +
                        "ANALYTIC_EVENT_TYPE_CD=\n" +
                        "    :ANALYTIC_EVENT_TYPE_CD,\n" +
                        "WEB_REDEEMABLE_IND=\n" +
                        "    :WEB_REDEEMABLE_IND,\n" +
                        "MFR_CPN_SRC_CD=\n" +
                        "    :MFR_CPN_SRC_CD,\n" +
                        "XTRA_CARD_SEG_NBR=\n" +
                        "    :XTRA_CARD_SEG_NBR,\n" +
                        "PRODUCT_CRITERIA_ID=\n" +
                        "    :PRODUCT_CRITERIA_ID,\n" +
                        "DFLT_CPN_CAT_XML=\n" +
                        "    :DFLT_CPN_CAT_XML,\n" +
                        "SEG_INC_EXC_CD=\n" +
                        "    :SEG_INC_EXC_CD,\n" +
                        "SEG_SRC_OWNER_NAME=\n" +
                        "    :SEG_SRC_OWNER_NAME,\n" +
                        "SEG_SRC_TABLE_NAME=\n" +
                        "    :SEG_SRC_TABLE_NAME,\n" +
                        "SEG_RELOAD_RQST_TS=\n" +
                        "    :SEG_RELOAD_RQST_TS,\n" +
                        "SEG_LAST_PROC_START_TS=\n" +
                        "    :SEG_LAST_PROC_START_TS,\n" +
                        "SEG_LAST_PROC_END_TS=\n" +
                        "    :SEG_LAST_PROC_END_TS,\n" +
                        "SEG_LAST_PROC_STAT_CD=\n" +
                        "    :SEG_LAST_PROC_STAT_CD,\n" +
                        "SEG_LAST_PROC_ROW_CNT=\n" +
                        "    :SEG_LAST_PROC_ROW_CNT,\n" +
                        "FIXED_REDEEM_START_DT=\n" +
                        "    :FIXED_REDEEM_START_DT,\n" +
                        "FIXED_REDEEM_END_DT=\n" +
                        "    :FIXED_REDEEM_END_DT,\n" +
                        "LAST_AUTO_REISSUE_DT=\n" +
                        "    :LAST_AUTO_REISSUE_DT,\n" +
                        "AUTO_REISSUE_IND=\n" +
                        "    :AUTO_REISSUE_IND,\n" +
                        "TRGT_PRNT_REG_TYPE_CD=\n" +
                        "    :TRGT_PRNT_REG_TYPE_CD,\n" +
                        "FACEBOOK_DISP_IND=\n" +
                        "    :FACEBOOK_DISP_IND,\n" +
                        "INSTANT_CMPGN_EARNING_IND=\n" +
                        "    :INSTANT_CMPGN_EARNING_IND,\n" +
                        "PE_OPTIMIZE_TYPE_CD=\n" +
                        "    :PE_OPTIMIZE_TYPE_CD\n" +
                        " WHERE CMPGN_ID=\n" +
                        " :CMPGN_ID \n";
        
        MapSqlParameterSource campParms = new MapSqlParameterSource();

        campParms.addValue("CMPGN_ID", campaign.getCmpgnId(), Types.INTEGER);
        campParms.addValue("CMPGN_TYPE_CD", campaign.getCmpgnTypeCd(), Types.VARCHAR);
        campParms.addValue("CMPGN_SUBTYPE_CD", campaign.getCmpgnSubtypeCd(), Types.VARCHAR);
        campParms.addValue("CMPGN_DSC", campaign.getCmpgnDsc(), Types.VARCHAR);
        campParms.addValue("RECPT_PRNT_IND", campaign.getRecptPrntInd(), Types.VARCHAR);
        campParms.addValue("RECPT_PRNT_PRIORITY_NBR", campaign.getRecptPrntPriorityNbr(), Types.INTEGER);
        campParms.addValue("RECPT_TXT", campaign.getRecptRxt(), Types.VARCHAR);
        campParms.addValue("RECPT_SCALE_NBR", campaign.getRecptScaleNbr(), Types.INTEGER);
        campParms.addValue("RWRD_REDIR_IND", campaign.getRwrdRedirInd(), Types.VARCHAR);
        campParms.addValue("START_DT", campaign.getStartDt(), Types.DATE);
        campParms.addValue("END_DT", campaign.getEndDt(), Types.DATE);
        //  campParms.addValue("END_DT", campaign.getEndDt(), Types.DATE);
        campParms.addValue("LAST_UPDT_TS", campaign.getLastUpdtTs(), Types.TIMESTAMP);
        campParms.addValue("ISSUE_FREQ_TYPE_CD", campaign.getIssueFreqTypeCd(), Types.VARCHAR);
        campParms.addValue("ISSUE_FREQ_CNT", campaign.getIssueFreqCnt(), Types.INTEGER);
        campParms.addValue("FIRST_ISSUE_DT", campaign.getFirstIssueDt(), Types.DATE);
        campParms.addValue("LAST_ISSUE_DT", campaign.getLastIssueDt(), Types.DATE);
        campParms.addValue("PREV_ISSUE_DT", campaign.getPrevIssueDt(), Types.DATE);
        campParms.addValue("NEXT_ISSUE_DT", campaign.getNextIssueDt(), Types.DATE);
        campParms.addValue("DAYS_TO_PRNT_CNT", campaign.getDaysToPrintCnt(), Types.INTEGER);
        campParms.addValue("DAYS_TO_REDEEM_CNT", campaign.getDaysToRedeemCnt(), Types.INTEGER);
        campParms.addValue("IN_HOME_DT", campaign.getInHomeDt(), Types.DATE);
        campParms.addValue("TOTAL_RWRD_EARN_AMT", campaign.getTotlaRwrdEarnAmt(), Types.INTEGER);
        campParms.addValue("BONUS_SKU_CALC_DT", campaign.getBonusSkuCalcDt(), Types.DATE);
        campParms.addValue("CPN_REDEEM_CALC_DT", campaign.getCpnRedeemCalcDt(), Types.DATE);
        campParms.addValue("CPN_BASE_DSC", campaign.getCpnBaseDsc(), Types.VARCHAR);
        campParms.addValue("PARENT_CMPGN_ID", campaign.getParentCmpgnId(), Types.INTEGER);
        campParms.addValue("CPN_CAT_NBR", campaign.getCpnCatNbr(), Types.INTEGER);
        campParms.addValue("CPN_SEG_NBR", campaign.getCpnSegNbr(), Types.INTEGER);
        campParms.addValue("CPN_FNDG_CD", campaign.getCpnFndgCd(), Types.VARCHAR);
        campParms.addValue("BILLING_TYPE_CD", campaign.getBillingTypeCd(), Types.VARCHAR);
        campParms.addValue("INDIV_RWRD_AMT", campaign.getIndivRwrdAmt(), Types.INTEGER);
        campParms.addValue("CPN_AUTOGEN_IND", campaign.getCpnAutoGenInd(), Types.VARCHAR);
        campParms.addValue("RWRD_LAST_CALC_DT", campaign.getRwrdLastCalcDt(), Types.DATE);
        campParms.addValue("CSR_VISIBLE_IND", campaign.getCsrVisibleInd(), Types.VARCHAR);
        campParms.addValue("CMPGN_TERMS_TXT", campaign.getCmpgnTermsTxt(), Types.VARCHAR);
        campParms.addValue("WEB_DSC", campaign.getWebDsc(), Types.VARCHAR);
        campParms.addValue("WEB_DISP_IND", campaign.getWebDispInd(), Types.VARCHAR);
        campParms.addValue("PAY_VENDOR_NBR", campaign.getPayVendorNbr(), Types.INTEGER);
        campParms.addValue("CPN_OLTP_HOLD_IND", campaign.getCpnOltpHoldInd(), Types.VARCHAR);
        campParms.addValue("CPN_PURGE_CD", campaign.getCpnPurgeCd(), Types.VARCHAR);
        campParms.addValue("DFLT_CPN_TERMS_CD", campaign.getDfltCpnTermscd(), Types.INTEGER);
        campParms.addValue("CAT_MGR_ID", campaign.getCatMgrId(), Types.VARCHAR);
        campParms.addValue("VENDOR_NBR", campaign.getVendorNbr(), Types.INTEGER);
        campParms.addValue("MULTI_VENDOR_IND", campaign.getMultiVendorInd(), Types.VARCHAR);
        campParms.addValue("CPN_FIXED_DSC_IND", campaign.getCpnFixedDscInd(), Types.VARCHAR);
        campParms.addValue("CPN_PRNT_START_DELAY_DAY_CNT", campaign.getCpnPrntStartDelayDayCnt(), Types.INTEGER);
        campParms.addValue("CPN_REDM_START_DELAY_DAY_CNT", campaign.getCpnRedmStartDelayDayCnt(), Types.INTEGER);
        campParms.addValue("CPN_PRIORITY_NBR", campaign.getCpnPriorityNbr(), Types.INTEGER);
        campParms.addValue("CPN_QUAL_TXT", campaign.getCpnQualTxt(), Types.VARCHAR);
        campParms.addValue("REQ_SKU_LIST", campaign.getReqSkuList(), Types.VARCHAR);
        campParms.addValue("MAX_VISIT_RWRD_QTY", campaign.getMaxVisitRwrdQty(), Types.INTEGER);
        campParms.addValue("MAX_RWRD_QTY", campaign.getMaxRwrdQty(), Types.INTEGER);
        campParms.addValue("RWRD_RECALC_IND", campaign.getRwrdRecalcInd(), Types.VARCHAR);
        campParms.addValue("CMPGN_QUAL_TXT", campaign.getCmpgnQualTxt(), Types.VARCHAR);
        campParms.addValue("TRGT_PRNT_DEST_CD", campaign.getTrgtPrntDestCd(), Types.VARCHAR);
        campParms.addValue("CPN_MIN_PURCH_AMT", campaign.getCpnMinPurchAmt(), Types.INTEGER);
        campParms.addValue("LAST_FEED_ACCPT_DT", campaign.getLastFeedAccptDt(), Types.DATE);
        campParms.addValue("ADV_MAX_RWRD_QTY", campaign.getAdvMaxRwrdQty(), Types.INTEGER);
        campParms.addValue("PROMO_LINK_NBR", campaign.getPromoLinkNbr(), Types.INTEGER);
        campParms.addValue("AMT_TYPE_CD", campaign.getAmtTypeCd(), Types.VARCHAR);
        campParms.addValue("PCT_OFF_AMT", campaign.getPctOffAmt(), Types.INTEGER);
        campParms.addValue("FSA_CPN_IND", campaign.getFsaCpnInd(), Types.VARCHAR);
        campParms.addValue("PRVT_LABEL_CPN_IND", campaign.getPrtLabrlCpnInd(), Types.VARCHAR);
        campParms.addValue("DFLT_ALWAYS_IND", campaign.getDfltAlwaysInd(), Types.VARCHAR);
        campParms.addValue("DFLT_FREQ_DAY_CNT", campaign.getDfltFreqDayCnt(), Types.INTEGER);
        campParms.addValue("DFLT_FREQ_EMP_DAY_CNT", campaign.getDfltFreqEmpDayCnt(), Types.INTEGER);
        campParms.addValue("LOADABLE_IND", campaign.getLoadableInd(), Types.VARCHAR);
        campParms.addValue("CARD_TYPE_CD", campaign.getCardTypeCd(), Types.VARCHAR);
        campParms.addValue("CPN_RECPT_TXT", campaign.getCpnRecptTxt(), Types.VARCHAR);
        campParms.addValue("CPN_VAL_RQRD_CD", campaign.getCpnValRqrdCd(), Types.VARCHAR);
        campParms.addValue("ABS_AMT_IND", campaign.getAbsAmtInd(), Types.VARCHAR);
        campParms.addValue("ITEM_LIMIT_QTY", campaign.getItemLimitQty(), Types.INTEGER);
        campParms.addValue("CPN_FMT_CD", campaign.getCpnFmtCd(), Types.VARCHAR);
        campParms.addValue("DFLT_CPN_CAT_JSON", campaign.getDfltCpnCatJson(), Types.VARCHAR);
        campParms.addValue("FREE_ITEM_IND", campaign.getFreeItemInd(), Types.VARCHAR);
        campParms.addValue("MKTG_PRG_CD", campaign.getMktgPrgCd(), Types.VARCHAR);
        campParms.addValue("MOBILE_DISP_IND", campaign.getMobileDispInd(), Types.VARCHAR);
        campParms.addValue("OVRD_PAPERLESS_IND", campaign.getOvrdPaperLessInd(), Types.VARCHAR);
        campParms.addValue("ANALYTIC_EVENT_TYPE_CD", campaign.getAnalyticEventTypeCd(), Types.INTEGER);
        campParms.addValue("WEB_REDEEMABLE_IND", campaign.getWebRedeemableInd(), Types.VARCHAR);
        campParms.addValue("MFR_CPN_SRC_CD", campaign.getMfrCpnSrcCd(), Types.VARCHAR);
        campParms.addValue("XTRA_CARD_SEG_NBR", campaign.getXtraCardSegNbr(), Types.INTEGER);
        campParms.addValue("PRODUCT_CRITERIA_ID", campaign.getProductCriteriaId(), Types.INTEGER);
        campParms.addValue("DFLT_CPN_CAT_XML", campaign.getDfltCpnCatXml(), Types.VARCHAR);
        campParms.addValue("SEG_INC_EXC_CD", campaign.getSegIncExcCd(), Types.VARCHAR);
        campParms.addValue("SEG_SRC_OWNER_NAME", campaign.getSegSrcOwnerName(), Types.VARCHAR);
        campParms.addValue("SEG_SRC_TABLE_NAME", campaign.getSegSrcTableName(), Types.VARCHAR);
        campParms.addValue("SEG_RELOAD_RQST_TS", campaign.getSegReloadRqstTs(), Types.TIMESTAMP);
        campParms.addValue("SEG_LAST_PROC_START_TS", campaign.getSegLastProcStartTs(), Types.TIMESTAMP);
        campParms.addValue("SEG_LAST_PROC_END_TS", campaign.getSegLastProcEndTs(), Types.TIMESTAMP);
        campParms.addValue("SEG_LAST_PROC_STAT_CD", campaign.getSegLastProcStatCd(), Types.VARCHAR);
        campParms.addValue("SEG_LAST_PROC_ROW_CNT", campaign.getSegLastProcRowCnt(), Types.INTEGER);
        campParms.addValue("FIXED_REDEEM_START_DT", campaign.getFixedRedeemStartDt(), Types.DATE);
        campParms.addValue("FIXED_REDEEM_END_DT", campaign.getFixedRedeemEndDt(), Types.DATE);
        campParms.addValue("LAST_AUTO_REISSUE_DT", campaign.getLastAutoReissueDt(), Types.DATE);
        campParms.addValue("AUTO_REISSUE_IND", campaign.getAutoReissueInd(), Types.VARCHAR);
        campParms.addValue("TRGT_PRNT_REG_TYPE_CD", campaign.getTrgtPrntRegCd(), Types.VARCHAR);
        campParms.addValue("FACEBOOK_DISP_IND", campaign.getFacebookDispInd(), Types.VARCHAR);
        campParms.addValue("INSTANT_CMPGN_EARNING_IND", campaign.getInstantCmpgnEarnigInd(), Types.VARCHAR);
        campParms.addValue("PE_OPTIMIZE_TYPE_CD", campaign.getPeOptimizeTypeCd(), Types.VARCHAR);

        int campRowCnt = jdbcTemplate.update(campaignUpSql, campParms);
        log.info("Total Number of Cards updated in campaign table : [{}]", campRowCnt);
    }
    
    /**
     * Update CAMPAIGN_REWARD_THRESHOLD
     */
    public void updateCampaignRewardThreshold(CampaignRewardThreshold campaignRewardThreshold) {
        // Update CampaignRewardThreshold Record
        String campRewardThreSql =
                "UPDATE CAMPAIGN_REWARD_THRESHOLD\n" +
                		" SET THRSHLD_LIM_NBR=\n" +
                        "    :THRSHLD_LIM_NBR,\n" +
                        "RWRD_QTY=\n" +
                        "    :RWRD_QTY,\n" +
                        "RWRD_THRSHLD_CYCL_IND=\n" +
                        "    :RWRD_THRSHLD_CYCL_IND,\n" +
                        "THRSHLD_TYPE_CD=\n" +
                        "    :THRSHLD_TYPE_CD\n" +
                        " WHERE CMPGN_ID=\n" +
                        " :CMPGN_ID \n";

        MapSqlParameterSource campRewardThreParms = new MapSqlParameterSource();

        campRewardThreParms.addValue("CMPGN_ID", campaignRewardThreshold.getCmpgnId(), Types.INTEGER);
        campRewardThreParms.addValue("THRSHLD_LIM_NBR", campaignRewardThreshold.getThrshldLimNbr(), Types.INTEGER);
        campRewardThreParms.addValue("RWRD_QTY", campaignRewardThreshold.getRwrdQty(), Types.INTEGER);
        campRewardThreParms.addValue("RWRD_THRSHLD_CYCL_IND", campaignRewardThreshold.getRwrdThrshldCyclInd(), Types.VARCHAR);
        campRewardThreParms.addValue("THRSHLD_TYPE_CD", campaignRewardThreshold.getThrshldTypeCd(), Types.VARCHAR);

        int camprewardthreRowCnt = jdbcTemplate.update(campRewardThreSql, campRewardThreParms);
        log.info("Total Number of Cards updated into CAMPAIGN_REWARD_THRESHOLD table : [{}]", camprewardthreRowCnt);
    }
    
    public void updateCampaignCoupon1(CampaignCoupon campaignCoupon) {
        // Update CAMPAIGN_COUPON Record
        String campaignCouponSql =
                "UPDATE CAMPAIGN_COUPON\n" +
                		" SET RWRD_QTY=\n" +
                        "    :RWRD_QTY,\n" +
                        "CPN_NBR=\n" +
                        "    :CPN_NBR,\n" +
                        "START_DT=\n" +
                        "    :START_DT,\n" +
                        "END_DT=\n" +
                        "    :END_DT,\n" +
                        "CPN_DSC=\n" +
                        "    :CPN_DSC,\n" +
                        "MAX_REDEEM_AMT=\n" +
                        "    :MAX_REDEEM_AMT,\n" +
//                        "CPN_TERMS_CD=\n" +
//                        "    :CPN_TERMS_CD,\n" +
//                        "AMT_TYPE_CD=\n" +
//                        "    :AMT_TYPE_CD,\n" +
//                        "PCT_OFF_AMT=\n" +
//                        "    :AMT_TYPE_CD,\n" +
//                        "LOADABLE_IND=\n" +
//                        "    :LOADABLE_IND,\n" +
//                        "FNDG_CD=\n" +
//                        "    :FNDG_CD,\n" +
//                        "BILLING_TYPE_CD=\n" +
//                        "    :BILLING_TYPE_CD,\n" +
//                        "CPN_RECPT_TXT=\n" +
//                        "    :CPN_RECPT_TXT,\n" +
//                        "CPN_VAL_RQRD_CD=\n" +
//                        "    :CPN_VAL_RQRD_CD,\n" +
//                        "ABS_AMT_IND=\n" +
//                        "    :ABS_AMT_IND,\n" +
//                        "ITEM_LIMIT_QTY=\n" +
//                        "    :ITEM_LIMIT_QTY,\n" +
//                        "CPN_FMT_CD=\n" +
//                        "    :CPN_FMT_CD,\n" +
//                        "FREE_ITEM_IND=\n" +
//                        "    :FREE_ITEM_IND,\n" +
//                        "IMAGE_URL_TXT=\n" +
//                        "    :IMAGE_URL_TXT,\n" +
                        "LAST_UPDT_TS=\n" +
                        "    :LAST_UPDT_TS\n" +
//                        "CPN_CAT_LIST_XML=\n" +
//                        "    :CPN_CAT_LIST_XML,\n" +
//                        "CPN_CAT_LIST_JSON=\n" +
//                        "    :CPN_CAT_LIST_JSON,\n" +
//                        "CPN_OLTP_HOLD_IND=\n" +
//                        "    :CPN_OLTP_HOLD_IND,\n" +
//                        "CARD_VAL_CD=\n" +
//                        "    :CARD_VAL_CD,\n" +
//                        "CAT_MGR_ID=\n" +
//                        "    :CAT_MGR_ID,\n" +
//                        "MAX_ISSUE_CNT=\n" +
//                        "    :MAX_ISSUE_CNT,\n" +
//                        "FIRST_UPDT_BY_ID=\n" +
//                        "    :FIRST_UPDT_BY_ID,\n" +
//                        "LAST_UPDT_BY_ID=\n" +
//                        "    :LAST_UPDT_BY_ID,\n" +
//                        "CPN_PRNT_SUPPRESS_IND=\n" +
//                        "    :CPN_PRNT_SUPPRESS_IND,\n" +
//                        "DISCLAIMER_TXT=\n" +
//                        "    :DISCLAIMER_TXT\n" +
                        " WHERE CPN_NBR=\n" +
                        " :CPN_NBR\n" +
                        " AND CMPGN_ID=\n" +
                        " :CMPGN_ID \n";

        MapSqlParameterSource campaignCouponParms = new MapSqlParameterSource();

        campaignCouponParms.addValue("CMPGN_ID", campaignCoupon.getCmpgnId(), Types.INTEGER);
        campaignCouponParms.addValue("RWRD_QTY", campaignCoupon.getRwrdQty(), Types.INTEGER);
        campaignCouponParms.addValue("CPN_NBR", campaignCoupon.getCpnNbr(), Types.INTEGER);
        campaignCouponParms.addValue("START_DT", campaignCoupon.getStartDt(), Types.DATE);
        campaignCouponParms.addValue("END_DT", campaignCoupon.getEndDt(), Types.DATE);
        campaignCouponParms.addValue("CPN_DSC", campaignCoupon.getCpnDsc(), Types.VARCHAR);
        campaignCouponParms.addValue("MAX_REDEEM_AMT", campaignCoupon.getMaxRedeemAmt(), Types.INTEGER);
        campaignCouponParms.addValue("CPN_TERMS_CD", campaignCoupon.getCpnTermsCd(), Types.INTEGER);
        campaignCouponParms.addValue("AMT_TYPE_CD", campaignCoupon.getAmtTypeCd(), Types.VARCHAR);
        campaignCouponParms.addValue("PCT_OFF_AMT", campaignCoupon.getPctOffAmt(), Types.INTEGER);
        campaignCouponParms.addValue("LOADABLE_IND", campaignCoupon.getLoadableInd(), Types.VARCHAR);
        campaignCouponParms.addValue("FNDG_CD", campaignCoupon.getFndgCd(), Types.VARCHAR);
        campaignCouponParms.addValue("BILLING_TYPE_CD", campaignCoupon.getBillingTypeCd(), Types.VARCHAR);
        campaignCouponParms.addValue("CPN_RECPT_TXT", campaignCoupon.getCpnRecptTxt(), Types.VARCHAR);
        campaignCouponParms.addValue("CPN_VAL_RQRD_CD", campaignCoupon.getCpnValRqrdCd(), Types.VARCHAR);
        campaignCouponParms.addValue("ABS_AMT_IND", campaignCoupon.getAbsAmtInd(), Types.VARCHAR);
        campaignCouponParms.addValue("ITEM_LIMIT_QTY", campaignCoupon.getItemLimitQty(), Types.INTEGER);
        campaignCouponParms.addValue("CPN_FMT_CD", campaignCoupon.getCpnFmtCd(), Types.VARCHAR);
        campaignCouponParms.addValue("FREE_ITEM_IND", campaignCoupon.getFreeItemInd(), Types.VARCHAR);
        campaignCouponParms.addValue("IMAGE_URL_TXT", campaignCoupon.getImageUrlTxt(), Types.VARCHAR);
        campaignCouponParms.addValue("LAST_UPDT_TS", campaignCoupon.getLastUpdtTs(), Types.TIMESTAMP);
        campaignCouponParms.addValue("CPN_CAT_LIST_XML", campaignCoupon.getCpnCatListXml(), Types.VARCHAR);
        campaignCouponParms.addValue("CPN_CAT_LIST_JSON", campaignCoupon.getCpnCatListJson(), Types.VARCHAR);
        campaignCouponParms.addValue("CPN_OLTP_HOLD_IND", campaignCoupon.getCpnOltpHoldInd(), Types.VARCHAR);
        campaignCouponParms.addValue("CARD_VAL_CD", campaignCoupon.getCardValCd(), Types.INTEGER);
        campaignCouponParms.addValue("CAT_MGR_ID", campaignCoupon.getCatMgrId(), Types.VARCHAR);
        campaignCouponParms.addValue("MAX_ISSUE_CNT", campaignCoupon.getMaxIssueCnt(), Types.INTEGER);
        campaignCouponParms.addValue("FIRST_UPDT_BY_ID", campaignCoupon.getFirstUpdtById(), Types.VARCHAR);
        campaignCouponParms.addValue("LAST_UPDT_BY_ID", campaignCoupon.getLastUpdtById(), Types.VARCHAR);
        campaignCouponParms.addValue("CPN_PRNT_SUPPRESS_IND", campaignCoupon.getCpnPrntSuppressInd(), Types.VARCHAR);
        campaignCouponParms.addValue("DISCLAIMER_TXT", campaignCoupon.getDisclaimerTxt(), Types.VARCHAR);


        int campaignCouponRowCnt = jdbcTemplate.update(campaignCouponSql, campaignCouponParms);
        log.info("Total Number of Cards updated into CAMPAIGN_COUPON table : [{}]", campaignCouponRowCnt);
    }
    
    public void updateCampaignCouponCriteria(CampaignCouponCriteria campaignCouponCriteria) {
        // Create new CampaigncouponCriteria Record
        String campCouponCriteriaSql =
                "UPDATE CAMPAIGN_COUPON_CRITERIA\n" +
                		" SET CPN_NBR=\n" +
                        "    :CPN_NBR,\n" +
                        "CRITERIA_USAGE_CD=\n" +
                        "    :CRITERIA_USAGE_CD,\n" +
                        "ALL_ITEM_IND=\n" +
                        "    :ALL_ITEM_IND,\n" +
                        "CRITERIA_ID=\n" +
                        "    :CRITERIA_ID,\n" +
                        "INC_EXC_CD=\n" +
                        "    :INC_EXC_CD,\n" +
                        "RQRD_PURCH_AMT=\n" +
                        "    :RQRD_PURCH_AMT,\n" +
                        "AMT_TYPE_CD=\n" +
                        "    :AMT_TYPE_CD,\n" +
                        "LAST_CHNG_DT=\n" +
                        "    :LAST_CHNG_DT\n" +
                        " WHERE CPN_NBR=\n" +
                        " :CPN_NBR\n" +
                        " AND CMPGN_ID=\n" +
                        " :CMPGN_ID \n";

        MapSqlParameterSource campCouponCriteriaParms = new MapSqlParameterSource();

        campCouponCriteriaParms.addValue("CMPGN_ID", campaignCouponCriteria.getCmpgnId(), Types.INTEGER);
        campCouponCriteriaParms.addValue("CPN_NBR", campaignCouponCriteria.getCpnNbr(), Types.INTEGER);
        campCouponCriteriaParms.addValue("CRITERIA_USAGE_CD", campaignCouponCriteria.getCriteriaUsageCd(), Types.VARCHAR);
        campCouponCriteriaParms.addValue("ALL_ITEM_IND", campaignCouponCriteria.getAllItemInd(), Types.VARCHAR);
        campCouponCriteriaParms.addValue("CRITERIA_ID", campaignCouponCriteria.getCriteriaId(), Types.INTEGER);
        campCouponCriteriaParms.addValue("INC_EXC_CD", campaignCouponCriteria.getIncExcCd(), Types.VARCHAR);
        campCouponCriteriaParms.addValue("RQRD_PURCH_AMT", campaignCouponCriteria.getRqrdPurchAmt(), Types.INTEGER);
        campCouponCriteriaParms.addValue("AMT_TYPE_CD", campaignCouponCriteria.getAmtTypeCd(), Types.VARCHAR);
        campCouponCriteriaParms.addValue("LAST_CHNG_DT", campaignCouponCriteria.getLastChngDt(), Types.DATE);

        int campCouponsCriteriaCnt = jdbcTemplate.update(campCouponCriteriaSql, campCouponCriteriaParms);
        log.info("Total Number of Cards updated into CAMPAIGN_COUPON_CRITERIA table : [{}]", campCouponsCriteriaCnt);
    }
    
    public void updateCampaignPoint(CampaignPoint campaignPoint) {
        // Create new CampaignPoint Record
        String campPointSql =
                "UPDATE CAMPAIGN_POINT\n" +
                		" SET XTRA_CARD_NBR=\n" +
                        "    :XTRA_CARD_NBR,\n" +
                        "PTS_QTY=\n" +
                        "    :PTS_QTY,\n" +
                        "PTS_ADJ_QTY=\n" +
                        "    :PTS_ADJ_QTY,\n" +
                        "PTS_OTHER_QTY=\n" +
                        "    :PTS_OTHER_QTY,\n" +
                        "LAST_UPDT_DT=\n" +
                        "    :LAST_UPDT_DT,\n" +
                        "PTS_PEAK_QTY=\n" +
                        "    :PTS_PEAK_QTY,\n" +
                        "PTS_XFER_OUT_QTY=\n" +
                        "    :PTS_XFER_OUT_QTY,\n" +
                        "PTS_ADJ_XFER_IN_QTY=\n" +
                        "    :PTS_ADJ_XFER_IN_QTY,\n" +
                        "PTS_ADJ_XFER_OUT_QTY=\n" +
                        "    :PTS_ADJ_XFER_OUT_QTY,\n" +
                        "PTS_OTHER_XFER_IN_QTY=\n" +
                        "    :PTS_OTHER_XFER_IN_QTY,\n" +
                        "PTS_OTHER_XFER_OUT_QTY=\n" +
                        "    :PTS_OTHER_XFER_OUT_QTY,\n" +
                        "ACTIVATION_TS=\n" +
                        "    :ACTIVATION_TS,\n" +
                        "ACTIVATION_SRC_CD=\n" +
                        "    :ACTIVATION_SRC_CD\n" +
                        " WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n" +
                        " AND CMPGN_ID=\n" +
                        " :CMPGN_ID \n";

        MapSqlParameterSource campPointParms = new MapSqlParameterSource();

        campPointParms.addValue("CMPGN_ID", campaignPoint.getCmpgnId(), Types.INTEGER);
        campPointParms.addValue("XTRA_CARD_NBR", campaignPoint.getXtraCardNbr(), Types.INTEGER);
        campPointParms.addValue("PTS_QTY", campaignPoint.getPtsQty(), Types.INTEGER);
        campPointParms.addValue("PTS_ADJ_QTY", campaignPoint.getPtsAdjQty(), Types.INTEGER);
        campPointParms.addValue("PTS_OTHER_QTY", campaignPoint.getPtsOtherQty(), Types.INTEGER);
        campPointParms.addValue("LAST_UPDT_DT", campaignPoint.getLastLastUpdtDt(), Types.DATE);
        campPointParms.addValue("PTS_PEAK_QTY", campaignPoint.getPtSpeakQty(), Types.INTEGER);
        campPointParms.addValue("LAST_CHNG_PTS_QTY", campaignPoint.getLastChngPtsQty(), Types.INTEGER);
        campPointParms.addValue("PTS_XFER_IN_QTY", campaignPoint.getPtsXferInQty(), Types.INTEGER);
        campPointParms.addValue("PTS_XFER_OUT_QTY", campaignPoint.getPtsXrefOutQty(), Types.INTEGER);
        campPointParms.addValue("PTS_ADJ_XFER_IN_QTY", campaignPoint.getPtsAdjXferInQty(), Types.INTEGER);
        campPointParms.addValue("PTS_ADJ_XFER_OUT_QTY", campaignPoint.getPtsAdjXferOutQty(), Types.INTEGER);
        campPointParms.addValue("PTS_OTHER_XFER_IN_QTY", campaignPoint.getPtsOtherXferInqty(), Types.INTEGER);
        campPointParms.addValue("PTS_OTHER_XFER_OUT_QTY", campaignPoint.getPtsOtherXferOutQty(), Types.INTEGER);
        campPointParms.addValue("ACTIVATION_TS", campaignPoint.getActivationTs(), Types.TIMESTAMP);
        campPointParms.addValue("ACTIVATION_SRC_CD", campaignPoint.getActivationSrcCd(), Types.VARCHAR);

        int campPointRowCnt = jdbcTemplate.update(campPointSql, campPointParms);
        log.info("Total Number of Cards updated into CAMPAIGN_POINT table : [{}]", campPointRowCnt);
    }
    
    public void updateCampaignReward(CampaignReward campaignReward) {
        // Create new CampaignReward Record
        String campRewardSql =
                "UPDATE CAMPAIGN_REWARD\n" +
                		" SET XTRA_CARD_NBR=\n" +
                        "    :XTRA_CARD_NBR,\n" +
                        "RWRD_EARN_QTY=\n" +
                        "    :RWRD_EARN_QTY,\n" +
                        "RWRD_ISSUED_QTY=\n" +
                        "    :RWRD_ISSUED_QTY,\n" +
                        "LAST_EARN_UPDT_DT=\n" +
                        "    :LAST_EARN_UPDT_DT,\n" +
                        "LAST_ISSUED_UPDT_DT=\n" +
                        "    :LAST_ISSUED_UPDT_DT,\n" +
                        "LAST_EARN_CHNG_QTY=\n" +
                        "    :LAST_EARN_CHNG_QTY,\n" +
                        "MAX_CMPGN_CPN_SEQ_NBR=\n" +
                        "    :MAX_CMPGN_CPN_SEQ_NBR\n" +
                        " WHERE XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR\n" +
                        " AND CMPGN_ID=\n" +
                        " :CMPGN_ID \n";

        MapSqlParameterSource campRewardParms = new MapSqlParameterSource();

        campRewardParms.addValue("CMPGN_ID", campaignReward.getCmpgnId(), Types.INTEGER);
        campRewardParms.addValue("XTRA_CARD_NBR", campaignReward.getXtraCardNbr(), Types.INTEGER);
        campRewardParms.addValue("RWRD_EARN_QTY", campaignReward.getRwrdEarnQty(), Types.INTEGER);
        campRewardParms.addValue("RWRD_ISSUED_QTY", campaignReward.getRwrdIssuedQty(), Types.INTEGER);
        campRewardParms.addValue("LAST_EARN_UPDT_DT", campaignReward.getLastEarnUpdtDt(), Types.DATE);
        campRewardParms.addValue("LAST_ISSUED_UPDT_DT", campaignReward.getLastIssuedUpdtDt(), Types.DATE);
        campRewardParms.addValue("LAST_EARN_CHNG_QTY", campaignReward.getLastEarnchngQty(), Types.INTEGER);
        campRewardParms.addValue("MAX_CMPGN_CPN_SEQ_NBR", campaignReward.getMaxCmpgnCpnSeqNbr(), Types.INTEGER);

        int campRewardRowCnt = jdbcTemplate.update(campRewardSql, campRewardParms);
        log.info("Total Number of Cards updated into CAMPAIGN_REWARD table : [{}]", campRewardRowCnt);
    }


    public void updateMvCampaignActiveInstant(MvCampaignActiveInstant mvCampaignActiveInstant) {
        // update new Care Pass Member Smry
        String mvCampaignActiveInstantUpsql =
                "UPDATE MV_CAMPAIGN_ACTIVE_INSTANT\n" +
                        " SET WEB_DSC=\n" +
                        "    :WEB_DSC,\n" +
                        " START_DT=\n" +
                        "    :START_DT,\n" +
                        " END_DT=\n" +
                        "    :END_DT\n" +
                        "WHERE CMPGN_ID=\n" +
                        " :CMPGN_ID\n";

        MapSqlParameterSource mvUpParms = new MapSqlParameterSource();

        mvUpParms.addValue("WEB_DSC", mvCampaignActiveInstant.getWebDesc(), Types.VARCHAR);
        mvUpParms.addValue("START_DT", mvCampaignActiveInstant.getStartDt(), Types.DATE);
        mvUpParms.addValue("END_DT", mvCampaignActiveInstant.getEndDt(), Types.DATE);
        mvUpParms.addValue("CMPGN_ID", mvCampaignActiveInstant.getCmpgnId(), Types.INTEGER);
        int mvUpRowCnt = jdbcTemplate.update(mvCampaignActiveInstantUpsql, mvUpParms);
        log.info("Total Number of Cards updated into MV_CAMPAIGN_ACTIVE_INSTANT table : [{}]", mvUpRowCnt);
    }

    public void createCampaignReward(CampaignReward campaignReward) {
        // Create new CampaignReward Record
        String campRewardSql =
                "INSERT INTO CAMPAIGN_REWARD\n" +
                        "(\n" +
                        "CMPGN_ID,\n" +
                        "XTRA_CARD_NBR,\n" +
                        "RWRD_EARN_QTY,\n" +
                        "RWRD_ISSUED_QTY,\n" +
                        "LAST_EARN_UPDT_DT,\n" +
                        "LAST_ISSUED_UPDT_DT,\n" +
                        "LAST_EARN_CHNG_QTY,\n" +
                        "MAX_CMPGN_CPN_SEQ_NBR\n" +
                        ")\n" +
                        "values\n" +
                        "(\n" +
                        ":CMPGN_ID,\n" +
                        ":XTRA_CARD_NBR,\n" +
                        ":RWRD_EARN_QTY,\n" +
                        ":RWRD_ISSUED_QTY,\n" +
                        ":LAST_EARN_UPDT_DT,\n" +
                        ":LAST_ISSUED_UPDT_DT,\n" +
                        ":LAST_EARN_CHNG_QTY,\n" +
                        ":MAX_CMPGN_CPN_SEQ_NBR\n" +
                        ")";

        MapSqlParameterSource campRewardParms = new MapSqlParameterSource();

        campRewardParms.addValue("CMPGN_ID", campaignReward.getCmpgnId(), Types.INTEGER);
        campRewardParms.addValue("XTRA_CARD_NBR", campaignReward.getXtraCardNbr(), Types.INTEGER);
        campRewardParms.addValue("RWRD_EARN_QTY", campaignReward.getRwrdEarnQty(), Types.INTEGER);
        campRewardParms.addValue("RWRD_ISSUED_QTY", campaignReward.getRwrdIssuedQty(), Types.INTEGER);
        campRewardParms.addValue("LAST_EARN_UPDT_DT", campaignReward.getLastEarnUpdtDt(), Types.DATE);
        campRewardParms.addValue("LAST_ISSUED_UPDT_DT", campaignReward.getLastIssuedUpdtDt(), Types.DATE);
        campRewardParms.addValue("LAST_EARN_CHNG_QTY", campaignReward.getLastEarnchngQty(), Types.INTEGER);
        campRewardParms.addValue("MAX_CMPGN_CPN_SEQ_NBR", campaignReward.getMaxCmpgnCpnSeqNbr(), Types.INTEGER);

        int campRewardRowCnt = jdbcTemplate.update(campRewardSql, campRewardParms);
        log.info("Total Number of Cards inserted into CAMPAIGN_REWARD table : [{}]", campRewardRowCnt);
    }
    
    public Integer selectCampaignPoint(CampaignPoint campaignPoint) {
        String campaignPointSql =
                "SELECT CMPGN_ID FROM CAMPAIGN_POINT\n" +
                        " WHERE CMPGN_ID=\n" +
                        " :CMPGN_ID\n" +
                        " AND XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR \n";

        MapSqlParameterSource campaignPointParms = new MapSqlParameterSource();
        campaignPointParms.addValue("CMPGN_ID", campaignPoint.getCmpgnId(), Types.INTEGER);
        //campaignCouponUpParms.addValue("CPN_NBR", campaignCoupon.getCpnNbr(), Types.INTEGER);
        campaignPointParms.addValue("XTRA_CARD_NBR", campaignPoint.getXtraCardNbr(), Types.INTEGER);
        try {
        return (Integer) jdbcTemplate.queryForObject(campaignPointSql, campaignPointParms, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            log.debug("No record found in database");
            return 0;
        }
    }
    
    public Integer selectCampaignReward(CampaignPoint campaignReward) {
        String campaignRewardSql =
                "SELECT CMPGN_ID FROM CAMPAIGN_REWARD\n" +
                        " WHERE CMPGN_ID=\n" +
                        " :CMPGN_ID\n" +
                        " AND XTRA_CARD_NBR=\n" +
                        " :XTRA_CARD_NBR \n";

        MapSqlParameterSource campaignRewardParms = new MapSqlParameterSource();
        campaignRewardParms.addValue("CMPGN_ID", campaignReward.getCmpgnId(), Types.INTEGER);
        //campaignCouponUpParms.addValue("CPN_NBR", campaignCoupon.getCpnNbr(), Types.INTEGER);
        campaignRewardParms.addValue("XTRA_CARD_NBR", campaignReward.getXtraCardNbr(), Types.INTEGER);
//        return (Integer) jdbcTemplate.queryForObject(campaignRewardSql, campaignRewardParms, Integer.class);
        try {
            return (Integer) jdbcTemplate.queryForObject(campaignRewardSql, campaignRewardParms, Integer.class);
            } catch (EmptyResultDataAccessException e) {
                log.debug("No record found in database");
                return 0;
            }
    }


    /**
     * Delete Test Data from CAMPAIGN tables
     */
    public void deleteCampaignRecords(List<Integer> pCmpgnIdList, List<Integer> pXtraCardNbrList) {
        // Delete CAMPAIGN Records
        String camSql = "DELETE FROM CAMPAIGN WHERE CMPGN_ID IN (:CMPGN_ID)";

        Map camParms = Collections.singletonMap("CMPGN_ID", pCmpgnIdList);
        int camDel = jdbcTemplate.update(camSql, camParms);

        log.info("Total Number of records deleted for table CAMPAIGN : [{}] ", camDel, camParms);

        // Delete QEB CAMPAIGN Records
        String camQebSql = "DELETE FROM CAMPAIGN WHERE CMPGN_ID IN (:CMPGN_ID) and end_dt>sysdate";

        Map camQebParms = Collections.singletonMap("CMPGN_ID", pCmpgnIdList);
        int camQebDel = jdbcTemplate.update(camQebSql, camQebParms);

        log.info("Total Number of records deleted for table CAMPAIGN : [{}] ", camQebDel, camParms);


        // Delete CAMPAIGN_COUPON Records
        String ccSql = "DELETE FROM CAMPAIGN_COUPON WHERE CMPGN_ID IN (:CMPGN_ID)";

        Map ccParms = Collections.singletonMap("CMPGN_ID", pCmpgnIdList);
        int ccDel = jdbcTemplate.update(ccSql, ccParms);

        log.info("Total Number of records deleted for table CAMPAIGN_COUPON : [{}] ", ccDel);


        // Delete CAMPAIGN_REWARD_THRESHOLD Records
        String crtSql = "DELETE FROM CAMPAIGN_REWARD_THRESHOLD WHERE CMPGN_ID IN (:CMPGN_ID)";

        Map crtParms = Collections.singletonMap("CMPGN_ID", pCmpgnIdList);
        int crtdel = jdbcTemplate.update(crtSql, crtParms);

        log.info("Total Number of records deleted for table CAMPAIGN_REWARD_THRESHOLD : [{}] ", crtdel);

        // Delete CAMPAIGN_ACTIVE_POINT_BASE Records
        String capbSql = "DELETE FROM CAMPAIGN_ACTIVE_POINT_BASE WHERE CMPGN_ID IN (:CMPGN_ID)";

        Map capbParms = Collections.singletonMap("CMPGN_ID", pCmpgnIdList);
        int capbdel = jdbcTemplate.update(capbSql, capbParms);

        log.info("Total Number of records deleted for table CAMPAIGN_ACTIVE_POINT_BASE : [{}] ", capbdel);

        // Delete CAMPAIGN_POINT Records
        String cpSql = "DELETE FROM CAMPAIGN_POINT WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map cpParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int cpdel = jdbcTemplate.update(cpSql, cpParms);

        log.info("Total Number of records deleted for table CAMPAIGN_POINT : [{}] ", cpdel);

        // Delete CAMPAIGN_STORE_SKU Records
        String cssSql = "DELETE FROM CAMPAIGN_STORE_SKU WHERE CMPGN_ID IN (:CMPGN_ID)";

        Map cssParms = Collections.singletonMap("CMPGN_ID", pCmpgnIdList);
        int cssdel = jdbcTemplate.update(cssSql, cssParms);

        log.info("Total Number of records deleted for table CAMPAIGN_STORE_SKU : [{}] ", cssdel);

        // Delete CAMPAIGN_COUPON_CRITERIA Records
        String cccSql = "DELETE FROM CAMPAIGN_COUPON_CRITERIA WHERE CMPGN_ID IN (:CMPGN_ID)";

        Map cccParms = Collections.singletonMap("CMPGN_ID", pCmpgnIdList);
        int cccdel = jdbcTemplate.update(cccSql, cccParms);

        log.info("Total Number of records deleted for table CAMPAIGN_COUPON_CRITERIA : [{}] ", cccdel);
    }


    public void deleteCampaignRecordsDealsInProgress(List<Integer> pCmpgnIdList, List<Integer> pXtraCardNbrList) {


        // Delete QEB CAMPAIGN Records
        //String camQebSql = "DELETE FROM CAMPAIGN WHERE CMPGN_ID IN (:CMPGN_ID) and end_dt>sysdate";
        String camQebSql = "DELETE FROM CAMPAIGN WHERE CMPGN_ID IN (:CMPGN_ID)";
        Map camQebParms = Collections.singletonMap("CMPGN_ID", pCmpgnIdList);
        int camQebDel = jdbcTemplate.update(camQebSql, camQebParms);

        log.info("Total Number of records deleted for table CAMPAIGN : [{}] ", camQebDel, camQebParms);


        // Delete CAMPAIGN_COUPON Records
        String ccSql = "DELETE FROM CAMPAIGN_COUPON WHERE CMPGN_ID IN (:CMPGN_ID)";

        Map ccParms = Collections.singletonMap("CMPGN_ID", pCmpgnIdList);
        int ccDel = jdbcTemplate.update(ccSql, ccParms);

        log.info("Total Number of records deleted for table CAMPAIGN_COUPON : [{}] ", ccDel);


        // Delete CAMPAIGN_REWARD_THRESHOLD Records
        String crtSql = "DELETE FROM CAMPAIGN_REWARD_THRESHOLD WHERE CMPGN_ID IN (:CMPGN_ID)";

        Map crtParms = Collections.singletonMap("CMPGN_ID", pCmpgnIdList);
        int crtdel = jdbcTemplate.update(crtSql, crtParms);

        log.info("Total Number of records deleted for table CAMPAIGN_REWARD_THRESHOLD : [{}] ", crtdel);

        // Delete CAMPAIGN_ACTIVE_POINT_BASE Records
        String capbSql = "DELETE FROM CAMPAIGN_ACTIVE_POINT_BASE WHERE CMPGN_ID IN (:CMPGN_ID)";

        Map capbParms = Collections.singletonMap("CMPGN_ID", pCmpgnIdList);
        int capbdel = jdbcTemplate.update(capbSql, capbParms);

        log.info("Total Number of records deleted for table CAMPAIGN_ACTIVE_POINT_BASE : [{}] ", capbdel);

        // Delete CAMPAIGN_POINT Records
        String cpSql = "DELETE FROM CAMPAIGN_POINT WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map cpParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int cpdel = jdbcTemplate.update(cpSql, cpParms);

        log.info("Total Number of records deleted for table CAMPAIGN_POINT : [{}] ", cpdel);

        // Delete CAMPAIGN_STORE_SKU Records
        String cssSql = "DELETE FROM CAMPAIGN_STORE_SKU WHERE CMPGN_ID IN (:CMPGN_ID)";

        Map cssParms = Collections.singletonMap("CMPGN_ID", pCmpgnIdList);
        int cssdel = jdbcTemplate.update(cssSql, cssParms);

        log.info("Total Number of records deleted for table CAMPAIGN_STORE_SKU : [{}] ", cssdel);

        // Delete CAMPAIGN_REWARD Records
        String crSql = "DELETE FROM CAMPAIGN_REWARD WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map crParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int crdel = jdbcTemplate.update(crSql, crParms);

        log.info("Total Number of records deleted for table CAMPAIGN_ACTIVE_POINT_BASE : [{}] ", crdel);


    }
    
    public void deleteCampaignRecordsDealsInProgress1(List<Integer> pCmpgnIdList, List<Integer> pXtraCardNbrList) {


        // Delete QEB CAMPAIGN Records
        //String camQebSql = "DELETE FROM CAMPAIGN WHERE CMPGN_ID IN (:CMPGN_ID) and end_dt>sysdate";
//        String camQebSql = "DELETE FROM CAMPAIGN WHERE CMPGN_ID IN (:CMPGN_ID)";
//        Map camQebParms = Collections.singletonMap("CMPGN_ID", pCmpgnIdList);
//        int camQebDel = jdbcTemplate.update(camQebSql, camQebParms);
//
//        log.info("Total Number of records deleted for table CAMPAIGN : [{}] ", camQebDel, camQebParms);


        // Delete CAMPAIGN_COUPON Records
        String ccSql = "DELETE FROM CAMPAIGN_COUPON WHERE CMPGN_ID IN (:CMPGN_ID)";

        Map ccParms = Collections.singletonMap("CMPGN_ID", pCmpgnIdList);
        int ccDel = jdbcTemplate.update(ccSql, ccParms);

        log.info("Total Number of records deleted for table CAMPAIGN_COUPON : [{}] ", ccDel);


        // Delete CAMPAIGN_REWARD_THRESHOLD Records
        String crtSql = "DELETE FROM CAMPAIGN_REWARD_THRESHOLD WHERE CMPGN_ID IN (:CMPGN_ID)";

        Map crtParms = Collections.singletonMap("CMPGN_ID", pCmpgnIdList);
        int crtdel = jdbcTemplate.update(crtSql, crtParms);

        log.info("Total Number of records deleted for table CAMPAIGN_REWARD_THRESHOLD : [{}] ", crtdel);

        // Delete CAMPAIGN_ACTIVE_POINT_BASE Records
        String capbSql = "DELETE FROM CAMPAIGN_ACTIVE_POINT_BASE WHERE CMPGN_ID IN (:CMPGN_ID)";

        Map capbParms = Collections.singletonMap("CMPGN_ID", pCmpgnIdList);
        int capbdel = jdbcTemplate.update(capbSql, capbParms);

        log.info("Total Number of records deleted for table CAMPAIGN_ACTIVE_POINT_BASE : [{}] ", capbdel);

        // Delete CAMPAIGN_POINT Records
        String cpSql = "DELETE FROM CAMPAIGN_POINT WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map cpParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int cpdel = jdbcTemplate.update(cpSql, cpParms);

        log.info("Total Number of records deleted for table CAMPAIGN_POINT : [{}] ", cpdel);

        // Delete CAMPAIGN_STORE_SKU Records
        String cssSql = "DELETE FROM CAMPAIGN_STORE_SKU WHERE CMPGN_ID IN (:CMPGN_ID)";

        Map cssParms = Collections.singletonMap("CMPGN_ID", pCmpgnIdList);
        int cssdel = jdbcTemplate.update(cssSql, cssParms);

        log.info("Total Number of records deleted for table CAMPAIGN_STORE_SKU : [{}] ", cssdel);

        // Delete CAMPAIGN_REWARD Records
        String crSql = "DELETE FROM CAMPAIGN_REWARD WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";

        Map crParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int crdel = jdbcTemplate.update(crSql, crParms);

        log.info("Total Number of records deleted for table CAMPAIGN_ACTIVE_POINT_BASE : [{}] ", crdel);
        
     // Delete CAMPAIGN_COUPON_CRITERIA Records
        String cccSql = "DELETE FROM CAMPAIGN_COUPON_CRITERIA WHERE CMPGN_ID IN (:CMPGN_ID)";

        Map cccParms = Collections.singletonMap("CMPGN_ID", pCmpgnIdList);
        int cccdel = jdbcTemplate.update(cccSql, cccParms);

        log.info("Total Number of records deleted for table CAMPAIGN_COUPON_CRITERIA : [{}] ", cccdel);



    }



    public void deleteCampaignActivePointBaseRecords(List<Integer> pXtraCardNbrList) {
        // Delete CAMPAIGN_ACTIVE_POINT_BASE Records
        String capbSql = "DELETE FROM CAMPAIGN_ACTIVE_POINT_BASE WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";
        Map capbParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int capbdel = jdbcTemplate.update(capbSql, capbParms);
        log.info("Total Number of records deleted for table CAMPAIGN_ACTIVE_POINT_BASE : [{}] ", capbdel);
    }

    public void deleteCampaignPointRecords(List<Integer> pXtraCardNbrList) {
        // Delete CAMPAIGN_ACTIVE_POINT_BASE Records
        String capbSql = "DELETE FROM CAMPAIGN_POINT WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";
        Map capbParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int capbdel = jdbcTemplate.update(capbSql, capbParms);
        log.info("Total Number of records deleted for table CAMPAIGN_POINT : [{}] ", capbdel);
    }

    public void deleteCampaignRewardsRecords(List<Integer> pXtraCardNbrList) {
        // Delete CAMPAIGN_REWARD Records
        String capbSql = "DELETE FROM CAMPAIGN_REWARD WHERE XTRA_CARD_NBR IN (:XTRA_CARD_NBR)";
        Map capbParms = Collections.singletonMap("XTRA_CARD_NBR", pXtraCardNbrList);
        int capbdel = jdbcTemplate.update(capbSql, capbParms);
        log.info("Total Number of records deleted for table CAMPAIGN_REWARD : [{}] ", capbdel);
    }
    
    public void deleteCampaignCouponCriteria(List<Integer> cmpgnList) {
        // Delete CAMPAIGN_COUPON_CRITERIA Records
        String capbSql = "DELETE FROM CAMPAIGN_COUPON_CRITERIA WHERE CMPGN_ID IN (:CMPGN_ID)";
        Map capbParms = Collections.singletonMap("CMPGN_ID", cmpgnList);
        int capbdel = jdbcTemplate.update(capbSql, capbParms);
        log.info("Total Number of records deleted for table CAMPAIGN_COUPON_CRITERIA : [{}] ", capbdel);
    }
    
    public void deleteCampaignCouponSkuRank(List<Integer> cmpgnList) {
        // Delete CAMPAIGN_COUPON_CRITERIA Records
        String capbSql = "DELETE FROM CAMPAIGN_COUPON_SKU_RANK WHERE CMPGN_ID IN (:CMPGN_ID)";
        Map capbParms = Collections.singletonMap("CMPGN_ID", cmpgnList);
        int capbdel = jdbcTemplate.update(capbSql, capbParms);
        log.info("Total Number of records deleted for table CAMPAIGN_COUPON_SKU_RANK : [{}] ", capbdel);
    }
}