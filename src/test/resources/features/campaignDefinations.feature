@Campaign
Feature: single campaign

 @SingleCampaignFetch
  Scenario Outline: Fetch campaign Details for a campaign ID
    Given CVS campaign id  with <campaign_ID>
    When campaign message source code as <msg_src_cd> 
    Then source location code as <src_loc_cd>
    Then user id as <user_id>
    And  fetch campaign details successfully
    Then Validate campaign details retrieved successfully
    
    Examples:
      | campaign_ID|msg_src_cd|src_loc_cd|user_id|
      | 47102      |"GB"        |90057     |"GBI"    | 
      | 47102      |"GB"        |90057     |"GBI"    |


 @SingleCampaignInvalidCampaign
  Scenario Outline: Fetch campaign Details for a campaign ID InvalidCampaign
    Given CVS invalid campaign id  with <campaign_ID>
    When campaign message source code as <msg_src_cd> 
    Then source location code as <src_loc_cd>
    Then user id as <user_id>
    And  fetch campaign details successfully
    Then Validate campaign details not retrieved successfully
    
    Examples:
      | campaign_ID|msg_src_cd|src_loc_cd|user_id|
      | 471024354      |"GB"        |90057     |"GBI"    |
      
   @SingleCampaignInvalidMsgSrcCiden
  Scenario Outline: Fetch campaign Details for a campaign ID InvalidMsgSrcCiden
    Given CVS campaign id  with <campaign_ID>
    When campaign invalid message source code as <msg_src_cd> 
    Then source location code as <src_loc_cd>
    Then user id as <user_id>
    And  fetch campaign details successfully
    Then Validate campaign throw Error
    
    Examples:
      | campaign_ID|msg_src_cd|src_loc_cd|user_id|
      | 47102      |"Gv"        |90057     |"GBI"    | 
 
 @SingleCampaignInvalidUserId
  Scenario Outline: Fetch campaign Details for a campaign ID InvalidUserId
    Given CVS campaign id  with <campaign_ID>
    When campaign invalid message source code as <msg_src_cd> 
    Then source location code as <src_loc_cd>
    Then invalid user id as <user_id>
    And  fetch campaign details successfully
    Then Validate campaign details throw access forbidden
    
    Examples:
      | campaign_ID|msg_src_cd|src_loc_cd|user_id|
      | 47102      |"GB"        |90057     |"GBS"    |     
      
      
  @CmpgnDefnBySubType
   Scenario Outline: Fetch all campaign Definations by Sub Type
    Given campaign message source code as <msg_src_cd> 
    Then source location code as <src_loc_cd>
    Then user id as <user_id>
    Then compress_ind as <compress_ind>
    Then campaign type code as <cmpgn_type_cd> and campaing subtype code as <cmpgn_subtype_cd>
    Then skip expired  as <skip_expired>
    And  submit a request to CmpgnDefnBySubType
    Then Validate campaign definations retrieved successfully
    
    Examples:
      |msg_src_cd|src_loc_cd|user_id|compress_ind|cmpgn_type_cd|cmpgn_subtype_cd|skip_expired|
      |"GB"        |90057     |"GBI"    | "true"       |"C"            |"P"               |"true"       | 
  
 @CmpgnDefnBySubTypeInvalidMsgSrcCode
   Scenario Outline: Fetch all campaign Definations by Sub Type InvalidMsgSrcCode
    Given campaign invalid message source code as <msg_src_cd> 
    Then source location code as <src_loc_cd>
    Then user id as <user_id>
    Then compress_ind as <compress_ind>
    Then campaign type code as <cmpgn_type_cd> and campaing subtype code as <cmpgn_subtype_cd>
    Then skip expired  as <skip_expired>
    And  submit a request to CmpgnDefnBySubType
    Then Validate campaign definations not retrieved successfully
    
    Examples:
      |msg_src_cd|src_loc_cd|user_id|compress_ind|cmpgn_type_cd|cmpgn_subtype_cd|skip_expired|
      |"GV"        |90057     |"GBI"    | "true"       |"C"            |"P"               |"true"        | 
  
   @CmpgnDefnBySubTypeInvalidCampaignTypeCode
    Scenario Outline: Fetch all campaign Definations by Sub Type InvalidCampaignTypeCode
    Given campaign message source code as <msg_src_cd> 
    Then source location code as <src_loc_cd>
    Then user id as <user_id>
    Then compress_ind as <compress_ind>
    Then invalid campaign type code as <cmpgn_type_cd> and campaing subtype code as <cmpgn_subtype_cd>
    Then skip expired  as <skip_expired>
    And  submit a request to CmpgnDefnBySubType
    Then Validate no campaign Definations retrieved successfully
    
    Examples:
      |msg_src_cd|src_loc_cd|user_id|compress_ind|cmpgn_type_cd|cmpgn_subtype_cd|skip_expired|
      |"GB"        |90057     |"GBI"   | "true"       |"B"            |"P"               |"true"        |     
   
   @CmpgnDefnBySubTypeInvalidCampaignSubTypeCode
    Scenario Outline: Fetch all campaign Definations by Sub Type InvalidCampaignSubTypeCode
    Given campaign message source code as <msg_src_cd> 
    Then source location code as <src_loc_cd>
    Then user id as <user_id>
    Then compress_ind as <compress_ind>
    Then campaign type code as <cmpgn_type_cd> and campaing invalid subtype code as <cmpgn_subtype_cd>
    Then skip expired  as <skip_expired>
    And  submit a request to CmpgnDefnBySubType
    Then Validate no campaign Definations retrieved successfully
    
    Examples:
      |msg_src_cd|src_loc_cd|user_id|compress_ind|cmpgn_type_cd|cmpgn_subtype_cd|skip_expired|
      |"GB"        |90057     |"GBI"    | "true"       |"C"            |"V"               |"true"        |   
  
   @CmpgnDefnBySubTypeByInvaliduserID
   Scenario Outline: Fetch all campaign Definations by Sub Type InvaliduserID
    Given campaign message source code as <msg_src_cd> 
    Then source location code as <src_loc_cd>
    Then invalid user id as <user_id>
    Then compress_ind as <compress_ind>
    Then campaign type code as <cmpgn_type_cd> and campaing subtype code as <cmpgn_subtype_cd>
    Then skip expired  as <skip_expired>
    And  submit a request to CmpgnDefnBySubType
    Then Validate campaign details throw access forbidden
    
    Examples:
      |msg_src_cd|src_loc_cd|user_id|compress_ind|cmpgn_type_cd|cmpgn_subtype_cd|skip_expired|
      |"GB"      |90057     |"GB1"  | "true"     |"C"          |"P"             |"true"       |


  @bulkCmpgnDefns
  Scenario Outline: Fetch all campaign Definations
    Given campaign message source code as <msg_src_cd>
    Then source location code as <src_loc_cd>
    Then user id as <user_id>
    Then compress_ind as <compress_ind>
    When submit Fetch all campaign Definations request
    Then validate all campaign definations retrieved successfully

    Examples:
      |msg_src_cd|src_loc_cd|user_id|compress_ind|
      |"GB"        |90057     |"GBI"    | "true"       |


  @bulkCmpgnDefnsInvalidMessageSourceCode
  Scenario Outline: Fetch all campaign Definations InvalidMessageSourceCode
    Given campaign invalid message source code as <msg_src_cd>
    Then source location code as <src_loc_cd>
    Then user id as <user_id>
    Then compress_ind as <compress_ind>
    When submit Fetch all campaign Definations request
    Then validate no campaign definations  retrieved successfully

    Examples:
      |msg_src_cd|src_loc_cd|user_id|compress_ind|
      |"GV"        |90057     |"GBI"    |"true"       |


  @bulkCmpgnDefnsInvalidUserID
  Scenario Outline: Fetch all campaign Definations InvalidUserID
    Given campaign message source code as <msg_src_cd>
    Then source location code as <src_loc_cd>
    Then invalid user id as <user_id>
    Then compress_ind as <compress_ind>
    When submit Fetch all campaign Definations request
    Then validate access forbidden error thrown

    Examples:
      |msg_src_cd|src_loc_cd|user_id|compress_ind|
      |"GB"        |90057     |"GB1"    |"true"      |
      
  
   @bulkCmpgnDefnsUpdatedAfterGivenDate
   Scenario Outline: Fetch all campaign Definations  aftergiven date
    Given campaign message source code as <msg_src_cd>
    Then source location code as <src_loc_cd>
    Then user id as <user_id>
    Then compress_ind as <compress_ind>
    Then filter date  as <filter_dt>
    When submit Fetch all campaign Definations after givendate request
    Then validate  campaign Definations after givendate retrieved successfully
    
    Examples:
      |msg_src_cd|src_loc_cd|user_id|compress_ind|filter_dt|
      |"GB"        |90057     |"GBI"    |"true"         |"2020021200:00:00"|
      
    @bulkCmpgnDefnsUpdatedAfterGivenDateInvalidMessageSourceCode
   Scenario Outline: Fetch all campaign Definations  aftergiven date InvalidMessageSourceCode
    Given campaign invalid message source code as <msg_src_cd> 
    Then source location code as <src_loc_cd>
    Then invalid user id as <user_id>
    Then compress_ind as <compress_ind>
    Then filter date  as <filter_dt>
    When submit Fetch all campaign Definations after givendate request
    Then validate campaign Definations after givendate not retrieved
    
    Examples:
      |msg_src_cd|src_loc_cd|user_id|compress_ind|filter_dt|
      |"GV"        |90057     |"GBI"    |"true"         |"2020021200:00:00"|
    
    
   @bulkCmpgnDefnsUpdatedAfterGivenDateInvalidUserID  
    Scenario Outline: Fetch all campaign Definations  aftergiven date InvalidUserID 
    Given campaign message source code as <msg_src_cd>
    Then source location code as <src_loc_cd>
    Then invalid user id as <user_id>
    Then compress_ind as <compress_ind>
    Then filter date  as <filter_dt>
    When submit Fetch all campaign Definations after givendate request
    Then validate access forbidden error thrown for campaign Definations after givendate request
    
    Examples:
      |msg_src_cd|src_loc_cd|user_id|compress_ind|filter_dt|
       |"GB"        |90057     |"GB1"    |"true"         |"2020021200:00:00"|
      
   
    @CmpgnDefnByRHBEventType      
    Scenario Outline: Fetch all campaign Definations by RHBEventType 
    Given campaign message source code as <msg_src_cd> 
    Then source location code as <src_loc_cd>
    Then user id as <user_id>
    Then compress_ind as <compress_ind>
    Then rhb event type as <rhb_event_type> 
    Then skip expired  as <skip_expired>
    When  submit Fetch all campaign Definations ByRHBEventType 
    Then Validate  campaign definations ByRHBEventType retrieved successfully
    
    Examples:
      |msg_src_cd|src_loc_cd|user_id|compress_ind|rhb_event_type|skip_expired|
      |"GB"        |90057     |"GBI"    | "true"       |"C"             |"true"        |   
  
  
   @CmpgnDefnByRHBEventTypeInvalidMessageSourceCode     
    Scenario Outline: Fetch all campaign Definations by RHBEventType InvalidMessageSourceCode
    Given campaign invalid message source code as <msg_src_cd> 
    Then source location code as <src_loc_cd>
    Then user id as <user_id>
    Then compress_ind as <compress_ind>
    Then rhb event type as <rhb_event_type> 
    Then skip expired  as <skip_expired>
    When  submit Fetch all campaign Definations ByRHBEventType 
    Then Validate  campaign definations ByRHBEventType not retrieved 
    
    Examples:
      |msg_src_cd|src_loc_cd|user_id|compress_ind|rhb_event_type|skip_expired|
      |"GV"        |90057     |"GBI"    | "true"       |"C"             |"true"        |  
      
  
   @CmpgnDefnByRHBEventTypeInvalidUserId   
    Scenario Outline: Fetch all campaign Definations by RHBEventType InvalidUserId 
    Given campaign message source code as <msg_src_cd> 
    Then source location code as <src_loc_cd>
    Then invalid user id as <user_id>
    Then compress_ind as <compress_ind>
    Then rhb event type as <rhb_event_type> 
    Then skip expired  as <skip_expired>
    When  submit Fetch all campaign Definations ByRHBEventType 
    Then validate access forbidden error thrown for campaign Definations ByRHBEventType
    
    Examples:
      |msg_src_cd|src_loc_cd|user_id|compress_ind|rhb_event_type|skip_expired|
      |"GB"        |90057     |"GB1"    | "true"       |"C"             |"true"        |   
	  
	  

  @CmpgnDefnForCmpgnIDList
  Scenario Outline: Fetch all campaign Definations for compaign id List
   Given campaign message source code as <msg_src_cd> 
    Then source location code as <src_loc_cd>
    Then user id as <user_id>
    Then campingId list as <cmpgnIds>
    When submit Fetch all campaign Definations by campid list 
    Then validate campaign details by list  retrieved successfully
    
    Examples:
      |msg_src_cd|src_loc_cd|user_id|cmpgnIds   |
      |"GB"        |90057     |"GBI"    |"39123, 47854, 98562, 52410,52411"       |   
      
  
	  
	@CmpgnDefnForCmpgnIDListNoCampidsList
  Scenario Outline: Fetch all campaign Definations for no compaign id List
   Given campaign message source code as <msg_src_cd> 
    Then source location code as <src_loc_cd>
    Then user id as <user_id>
    Then campingId list as <cmpgnIds>
    When submit Fetch all campaign Definations by campid list 
    Then validate all campaign details by list  retrieved successfully
    
    Examples:
      |msg_src_cd|src_loc_cd|user_id|cmpgnIds   |
      |"GB"        |90057     |"GBI"    |""       |   
      
      
   @CmpgnDefnForCmpgnIDListInvalidCampidsList
  Scenario Outline: Fetch all campaign Definations for Invalid compaign id List
   Given campaign message source code as <msg_src_cd> 
    Then source location code as <src_loc_cd>
    Then user id as <user_id>
    Then invalid campingId list as <cmpgnIds>
   When submit Fetch all campaign Definations by campid list 
    Then validate no campaign details by list  retrieved successfully
    
    Examples:
      |msg_src_cd|src_loc_cd|user_id|cmpgnIds   |
      |"GB"        |90057     |"GBI"| "98562"   |  
    