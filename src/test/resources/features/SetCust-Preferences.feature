@SetCustPreferences
Feature: Set Customer - Preferences

  @Web
  Scenario Outline: Set Customer Preferences Information
    Given <scenario>
    And I use <channel>
    And I use my Extra Card <xtra_card_nbr> and <cust_Id>
    When I want to save preferences for <type> as <opt_cd>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I save opt cd as <opt_cd>
    And I save opt type cd as <opt_type_cd>
    Examples:
      | scenario                                                                                                          | xtra_card_nbr | encoded_xtra_cardnbr | cust_Id | type            | opt_cd | opt_type_cd | channel |
      | "I am a new XtraCare Customer and want to save my preferences information with beautyNotes enrolled as true"      | 900058445     | 900058445            | 80179   | "beautyNotes"   | "Y"    | "35"        | "WEB"   |
      | "I am a new XtraCare Customer and want to save my preferences information with paperlessCpns enrolled as true"    | 900058447     | 900058447            | 80181   | "paperlessCpns" | "Y"    | "34"        | "WEB"   |
      | "I am a New XtraCare Customer and want to save my preferences information with beautyNotes enrolled as false"     | 900058449     | 900058449            | 80183   | "beautyNotes"   | "N"    | "35"        | "WEB"   |
      | "I am a new XtraCare Customer and want to save my preferences information with paperlessCpns enrolled as false"   | 900058451     | 900058451            | 80185   | "paperlessCpns" | "N"    | "34"        | "WEB"   |


  @Mobile
  Scenario Outline: Set Customer Preferences Information
    Given <scenario>
    And I use <channel>
    And I use my Extra Card <xtra_card_nbr> and <cust_Id>
    When I want to save preferences for <type> as <opt_cd>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I save opt cd as <opt_cd>
    And I save opt type cd as <opt_type_cd>
    Examples:
      | scenario                                                                                                       | xtra_card_nbr | encoded_xtra_cardnbr | cust_Id | type      | opt_cd | opt_type_cd | channel  |
      | "I am a new XtraCare Customer and want to save my preferences information with sms enrolled as false"          | 900058453     | 900058453            | 80187   | "sms"     | "N"    | "37"        | "MOBILE" |
      | "I am an Existing XtraCare Customer and want to save my preferences information with optInEc enrolled as true" | 900058454     | 900058454            | 80188   | "optInEc" | "I"    | "29"        | "MOBILE" |
      | "I am a new XtraCare Customer and want to save my preferences information with sms enrolled as true"           | 900058455     | 900058455            | 80189   | "sms"     | "Y"    | "37"        | "MOBILE" |
      | "I am a Existing XtraCare Customer and want to save my preferences information with optInEc enrolled as false" | 900058456     | 900058456            | 80190   | "optInEc" | "O"    | "29"        | "MOBILE" |



# @Web @Mobile @POS
# Scenario Outline: Set Customer Preferences Information
#   Given <scenario>
#   And I use <channel>
#   And I use my Extra Card <xtra_card_nbr> and <cust_Id>
#   When I want to save preferences for <type> as <opt_cd>
#   Then I see the SetCust Response
#   And I see the xtra card nbr as <xtra_card_nbr>
#   And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
#   And I save opt cd as <opt_cd>
#   Examples:
#     | scenario                                                                                        | xtra_card_nbr | encoded_xtra_cardnbr | cust_Id | type         | opt_cd |channel |
#     | "I am a new XtraCare Customer and want to save my preferences information with optInEmail as A" | 900058457     | 900058457            | 80191   | "optInEmail" | "A"    |  ""    |


  @Pos
  Scenario Outline: Set Customer Preferences Information
    Given <scenario>
    And I use <channel>
    And I use my Extra Card <xtra_card_nbr> and <cust_Id>
    When I want to save preferences for <type> as <opt_cd>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I save opt cd as <opt_cd>
    And I save opt type cd as <opt_type_cd>
    Examples:
      | scenario                                                                                          | xtra_card_nbr | encoded_xtra_cardnbr | cust_Id | type | opt_cd | opt_type_cd | channel |
      | "I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as P" | 900058460     | 900058460            | 80194   | "DR" | "P"    | "36"        | "POS"   |
      | "I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as B" | 900058461     | 900058461            | 80195   | "DR" | "B"    | "36"        | "POS"   |
      | "I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as D" | 900058462     | 900058462            | 80196   | "DR" | "D"    | "36"        | "POS"   |


  @Mobile
  Scenario Outline: Set Customer Preferences Information
    Given <scenario>
    And I use <channel>
    And I use my Extra Card <xtra_card_nbr> and <cust_Id>
    When I want to save preferences for <type> as <opt_cd>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I save opt cd as <opt_cd>
    And I save opt type cd as <opt_type_cd>
    Examples:
      | scenario                                                                                                          | xtra_card_nbr | encoded_xtra_cardnbr | cust_Id | type            | opt_cd | opt_type_cd | channel  |
      | "I am a new XtraCare Customer and want to save my preferences information with beautyNotes enrolled as true"      | 900058464     | 900058464            | 80198   | "beautyNotes"   | "Y"    | "35"        | "MOBILE" |
      | "I am a new XtraCare Customer and want to save my preferences information with paperlessCpns enrolled as true"    | 900058465     | 900058465            | 80199   | "paperlessCpns" | "Y"    | "34"        | "MOBILE" |
      | "I am a New XtraCare Customer and want to save my preferences information with beautyNotes enrolled as false"     | 900058466     | 900058466            | 80200   | "beautyNotes"   | "N"    | "35"        | "MOBILE" |
      | "I am a new XtraCare Customer and want to save my preferences information with paperlessCpns enrolled as false"   | 900058467     | 900058467            | 80201   | "paperlessCpns" | "N"    | "34"        | "MOBILE" |


  @Pos
  Scenario Outline: Set Customer Preferences Information
    Given <scenario>
    And I use <channel>
    And I use my Extra Card <xtra_card_nbr> and <cust_Id>
    When I want to save preferences for <type> as <opt_cd>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I save opt cd as <opt_cd>
    And I save opt type cd as <opt_type_cd>
    Examples:
      | scenario                                                                                                          | xtra_card_nbr | encoded_xtra_cardnbr | cust_Id | type            | opt_cd | opt_type_cd | channel |
      | "I am a new XtraCare Customer and want to save my preferences information with beautyNotes enrolled as true"      | 900058469     | 900058469            | 80203   | "beautyNotes"   | "Y"    | "35"        | "POS"   |
      | "I am a new XtraCare Customer and want to save my preferences information with paperlessCpns enrolled as true"    | 900058470     | 900058470            | 80204   | "paperlessCpns" | "Y"    | "34"        | "POS"   |
      | "I am a New XtraCare Customer and want to save my preferences information with beautyNotes enrolled as false"     | 900058471     | 900058471            | 80205   | "beautyNotes"   | "N"    | "35"        | "POS"   |
      | "I am a new XtraCare Customer and want to save my preferences information with paperlessCpns enrolled as false"   | 900058472     | 900058472            | 80206   | "paperlessCpns" | "N"    | "34"        | "POS"   |


  @Web
  Scenario Outline: Set Customer Preferences Information
    Given <scenario>
    And I use <channel>
    And I use my Extra Card <xtra_card_nbr> and <cust_Id>
    When I want to save preferences for <type> as <opt_cd>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I save opt cd as <opt_cd>
    And I save opt type cd as <opt_type_cd>
    Examples:
      | scenario                                                                                                       | xtra_card_nbr | encoded_xtra_cardnbr | cust_Id | type      | opt_cd | opt_type_cd | channel |
      | "I am a new XtraCare Customer and want to save my preferences information with sms enrolled as false"          | 900058473     | 900058473            | 80207   | "sms"     | "N"    | "37"        | "WEB"   |
      | "I am an Existing XtraCare Customer and want to save my preferences information with optInEc enrolled as true" | 900058474     | 900058474            | 80208   | "optInEc" | "I"    | "29"        | "WEB"   |
      | "I am a new XtraCare Customer and want to save my preferences information with sms enrolled as true"           | 900058475     | 900058475            | 80209   | "sms"     | "Y"    | "37"        | "WEB"   |
      | "I am a Existing XtraCare Customer and want to save my preferences information with optInEc enrolled as false" | 900058476     | 900058476            | 80210   | "optInEc" | "O"    | "29"        | "WEB"   |


  @Pos
  Scenario Outline: Set Customer Preferences Information
    Given <scenario>
    And I use <channel>
    And I use my Extra Card <xtra_card_nbr> and <cust_Id>
    When I want to save preferences for <type> as <opt_cd>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I save opt cd as <opt_cd>
    And I save opt type cd as <opt_type_cd>
    Examples:
      | scenario                                                                                                       | xtra_card_nbr | encoded_xtra_cardnbr | cust_Id | type      | opt_cd | opt_type_cd | channel |
      | "I am a new XtraCare Customer and want to save my preferences information with sms enrolled as false"          | 900058477     | 900058477            | 80211   | "sms"     | "N"    | "37"        | "POS"   |
      | "I am an Existing XtraCare Customer and want to save my preferences information with optInEc enrolled as true" | 900058478     | 900058478            | 80212   | "optInEc" | "I"    | "29"        | "POS"   |
      | "I am a new XtraCare Customer and want to save my preferences information with sms enrolled as true"           | 900058479     | 900058479            | 80213   | "sms"     | "Y"    | "37"        | "POS"   |
      | "I am a Existing XtraCare Customer and want to save my preferences information with optInEc enrolled as false" | 900058480     | 900058480            | 80214   | "optInEc" | "O"    | "29"        | "POS"   |


  @Web
  Scenario Outline: Set Customer Preferences Information
    Given <scenario>
    And I use <channel>
    And I use my Extra Card <xtra_card_nbr> and <cust_Id>
    When I want to save preferences for <type> as <opt_cd>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I save opt cd as <opt_cd>
    And I save opt type cd as <opt_type_cd>
    Examples:
      | scenario                                                                                          | xtra_card_nbr | encoded_xtra_cardnbr | cust_Id | type | opt_cd | opt_type_cd | channel |
      | "I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as P" | 900058481     | 900058481            | 80215   | "DR" | "P"    | "36"        | "WEB"   |
      | "I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as B" | 900058482     | 900058482            | 80216   | "DR" | "B"    | "36"        | "WEB"   |
      | "I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as D" | 900058483     | 900058483            | 80217   | "DR" | "D"    | "36"        | "WEB"   |


  @Mobile
  Scenario Outline: Set Customer Preferences Information
    Given <scenario>
    And I use <channel>
    And I use my Extra Card <xtra_card_nbr> and <cust_Id>
    When I want to save preferences for <type> as <opt_cd>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I save opt cd as <opt_cd>
    And I save opt type cd as <opt_type_cd>
    Examples:
      | scenario                                                                                          | xtra_card_nbr | encoded_xtra_cardnbr | cust_Id | type | opt_cd | opt_type_cd | channel  |
      | "I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as P" | 900058484     | 900058484            | 80218   | "DR" | "P"    | "36"        | "MOBILE" |
      | "I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as B" | 900058485     | 900058485            | 80219   | "DR" | "B"    | "36"        | "MOBILE" |
      | "I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as D" | 900058486     | 900058486            | 80220   | "DR" | "D"    | "36"        | "MOBILE" |




