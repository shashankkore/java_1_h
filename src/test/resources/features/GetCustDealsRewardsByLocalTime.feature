@GetCustDealsRewardsByLocalTime
Feature: Get Customer - GetCustDealsRewardsByLocalTime

  @dealByLocalTime
  Scenario Outline: GET Customer deals by local time  API for
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I use transactionTS <txnTs>
    And I filter for <FILTER> in the request
    When I use the get customer IVR API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return <NODE> nodes
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    |txnTs| FILTER  | CHANNEL | NODE                              | SCENARIO           |
      | "XC_99901" | "createExtraCard" |"2019021118:46:22-05:00"     |"dealByLocalTime"|"I"      |  "mfrCpnAvailPool" | "dealByLocalTimeMultiple" |
      | "XC_99901" | "createExtraCard" |"2019021118:46:22-05:00"     |"dealByLocalTime"|"W"      |  "mfrCpnAvailPool" | "dealByLocalTimeMultiple" |
      | "XC_99901" | "createExtraCard" |"2019021118:46:22-05:00"     |"dealByLocalTime"|"M"      |  "mfrCpnAvailPool" | "dealByLocalTimeMultiple" |
      | "XC_99901" | "createExtraCard" |"2019021118:46:22-05:00"     |"dealByLocalTime"|"R"      |  "mfrCpnAvailPool" | "dealByLocalTimeMultiple" |



  @dealByLocalTimeInvalidCard
  Scenario Outline: GET Customer deals by local time  API for
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    When I use transactionTS <txnTs>
    And I filter for <FILTER> in the request
    When I use the get customer IVR API for channel <CHANNEL>
    Then I receive a response with fail status
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    |txnTs                        | FILTER          | CHANNEL |  SCENARIO           |
      | "XC_99900" | "createExtraCard" |"2019021118:46:22-05:00"     |"dealByLocalTime"|"W"      | "dealByLocalTimeInvalidCard" |
      | "XC_99900" | "createExtraCard" |"2019021118:46:22-05:00"     |"dealByLocalTime"|"I"      | "dealByLocalTimeInvalidCard" |
      | "XC_99900" | "createExtraCard" |"2019021118:46:22-05:00"     |"dealByLocalTime"|"M"      | "dealByLocalTimeInvalidCard" |
      | "XC_99900" | "createExtraCard" |"2019021118:46:22-05:00"     |"dealByLocalTime"|"R"      | "dealByLocalTimeInvalidCard" |



  @dealByLocalTimeInvalidCardType
  Scenario Outline: GET Customer deals by local time  API for
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I use XCardType <cardType>
    And I use transactionTS <txnTs>
    And I filter for <FILTER> in the request
    When I use the get customer IVR API for channel <CHANNEL>
    Then I receive a response with fail status
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    |txnTs                        | FILTER          | CHANNEL |cardType|  SCENARIO           |
      | "XC_99900" | "createExtraCard" |"2019021118:46:22-05:00"     |"dealByLocalTime"|"W"      | "0007"        |"dealByLocalTimeInvalidCardType" |
      | "XC_99900" | "createExtraCard" |"2019021118:46:22-05:00"     |"dealByLocalTime"|"I"      | "0007"        |"dealByLocalTimeInvalidCardType" |
      | "XC_99900" | "createExtraCard" |"2019021118:46:22-05:00"     |"dealByLocalTime"|"M"      | "0007"        |"dealByLocalTimeInvalidCardType" |
      | "XC_99900" | "createExtraCard" |"2019021118:46:22-05:00"     |"dealByLocalTime"|"R"      | "0007"        |"dealByLocalTimeInvalidCardType" |

  Scenario Outline: GET Customer deals by local time  API for single
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I use transactionTS <txnTs>
    And I filter for <FILTER> in the request
    When I use the get customer IVR API for channel <CHANNEL>
    Then I receive a response with success status

    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    |txnTs                        | FILTER          | CHANNEL |  SCENARIO                         |
      | "XC_99901" | "createExtraCard" |"2019021118:46:22-05:00"     |"pts"            |"I"      |  "GetCustDealsRewardsByLocalTimeSingle" |
      | "XC_99901" | "createExtraCard" |"2019021118:46:22-05:00"     |"cpns"           |"W"      |  "GetCustDealsRewardsByLocalTimeSingle" |
      | "XC_99901" | "createExtraCard" |"2019021118:46:22-05:00"     |"mfrCpnAvailPool"|"M"      |  "GetCustDealsRewardsByLocalTimeSingle" |
      | "XC_99901" | "createExtraCard" |"2019021118:46:22-05:00"     |"pebAvailPool"   |"R"      |  "GetCustDealsRewardsByLocalTimeSingle" |
      | "XC_99901" | "createExtraCard" |"2019021118:46:22-05:00"     |"hrMembers"      |"R"      |  "GetCustDealsRewardsByLocalTimeSingle" |
      | "XC_99901" | "createExtraCard" |"2019021118:46:22-05:00"     |"hrEvtDtl"       |"R"      |  "GetCustDealsRewardsByLocalTimeSingle" |
      | "XC_99901" | "createExtraCard" |"2019021118:46:22-05:00"     |"carePassCpns"   |"R"      |  "GetCustDealsRewardsByLocalTimeSingle" |




      