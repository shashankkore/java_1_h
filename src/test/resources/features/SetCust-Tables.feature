@SetCustTables
Feature: Set Customer - Tables

  Scenario Outline: Inserting into the customer table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "customer" table through SetCust API using the data "tc01_setcust_insert_customer"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                        | xtra_card_nbr | channel | message                                              |
      | "Inserting details into the customer table from Web channel"    |       8875522 | "W"     | "ERROR: INSERT operation not supported on: customer" |
      | "Inserting details into the customer table from Mobile channel" |       8875522 | "M"     | "ERROR: INSERT operation not supported on: customer" |
      | "Inserting details into the customer table from Store channel"  |       8875522 | "R"     | "ERROR: INSERT operation not supported on: customer" |

  Scenario Outline: Updating the customer table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "customer" table through SetCust API using the data "tc02_setcust_update_customer"
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    And I see that the respective columns in the "customer" table updated
    Examples:
      | scenario                                                     | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Updating details in the customer table from Web channel"    |       8875522 | "4872887552211"       | "W"     |
      | "Updating details in the customer table from Mobile channel" |       8875522 | "4872887552211"       | "M"     |
      | "Updating details in the customer table from Store channel"  |       8875522 | "4872887552211"       | "R"     |

  Scenario Outline: Deleting from the customer table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "delete" details from the "customer" table through SetCust API
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                       | xtra_card_nbr | channel | message                                              |
      | "Deleting details from the customer table from Web channel"    |       8875522 | "W"     | "ERROR: DELETE operation not supported on: customer" |
      | "Deleting details from the customer table from Mobile channel" |       8875522 | "M"     | "ERROR: DELETE operation not supported on: customer" |
      | "Deleting details from the customer table from Store channel"  |       8875522 | "R"     | "ERROR: DELETE operation not supported on: customer" |

  Scenario Outline: Updating the customer_address table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "customer_address" table through SetCust API using the data "tc03_setcust_update_customer_address"
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    And I see that the respective columns in the "customer_address" table updated
    Examples:
      | scenario                                                             | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Updating details in the customer_address table from Web channel"    |       8875522 | "4872887552211"       | "W"     |
      | "Updating details in the customer_address table from Mobile channel" |       8875522 | "4872887552211"       | "M"     |
      | "Updating details in the customer_address table from Store channel"  |       8875522 | "4872887552211"       | "R"     |

  Scenario Outline: Deleting from the customer_address table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "delete" details from the "customer_address" table through SetCust API
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    Examples:
      | scenario                                                               | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Deleting details from the customer_address table from Web channel"    |       8875522 | "4872887552211"       | "W"     |
      | "Deleting details from the customer_address table from Mobile channel" |       8875522 | "4872887552211"       | "M"     |
      | "Deleting details from the customer_address table from Store channel"  |       8875522 | "4872887552211"       | "R"     |

  Scenario Outline: Updating the customer_email table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "customer_email" table through SetCust API using the data "tc05_setcust_update_customer_email"
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    And I see that the respective columns in the "customer_email" table updated
    Examples:
      | scenario                                                           | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Updating details in the customer_email table from Web channel"    |       8875522 | "4872887552211"       | "W"     |
      | "Updating details in the customer_email table from Mobile channel" |       8875522 | "4872887552211"       | "M"     |
      | "Updating details in the customer_email table from Store channel"  |       8875522 | "4872887552211"       | "R"     |

  Scenario Outline: Inserting into the customer_email table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "customer_email" table through SetCust API using the data "tc04_setcust_insert_customer_email"
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    And I see a new row inserted into the "customer_email" table
    Examples:
      | scenario                                                              | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Inserting details into the customer_email table from Web channel"    |       8875522 | "4872887552211"       | "W"     |
      | "Inserting details into the customer_email table from Mobile channel" |       8875522 | "4872887552211"       | "M"     |
      | "Inserting details into the customer_email table from Store channel"  |       8875522 | "4872887552211"       | "R"     |

  Scenario Outline: Inserting into the customer_email table without email_addr_txt
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "customer_email" table through SetCust API using the data "tc06_setcust_insert_customer_email_without_emailAddrTxt"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                              | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                               |
      | "Inserting details into the customer_email table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "Mandatory columns missing for table: customer_email" |
      | "Inserting details into the customer_email table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "Mandatory columns missing for table: customer_email" |
      | "Inserting details into the customer_email table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "Mandatory columns missing for table: customer_email" |

  Scenario Outline: Deleting from the customer_email table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "delete" details from the "customer_email" table through SetCust API
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    Examples:
      | scenario                                                             | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Deleting details from the customer_email table from Web channel"    |       8875522 | "4872887552211"       | "W"     |
      | "Deleting details from the customer_email table from Mobile channel" |       8875522 | "4872887552211"       | "M"     |
      | "Deleting details from the customer_email table from Store channel"  |       8875522 | "4872887552211"       | "R"     |

  Scenario Outline: Updating the customer_opt table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "customer_opt" table through SetCust API using the data "tc08_setcust_update_customer_opt"
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    And I see that the respective columns in the "customer_opt" table updated
    Examples:
      | scenario                                                         | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Updating details in the customer_opt table from Web channel"    |       8875522 | "4872887552211"       | "W"     |
      | "Updating details in the customer_opt table from Mobile channel" |       8875522 | "4872887552211"       | "M"     |
      | "Updating details in the customer_opt table from Store channel"  |       8875522 | "4872887552211"       | "R"     |

  Scenario Outline: Inserting into the customer_opt table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "customer_opt" table through SetCust API using the data "tc07_setcust_insert_customer_opt"
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    And I see a new row inserted into the "customer_opt" table
    Examples:
      | scenario                                                            | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Inserting details into the customer_opt table from Web channel"    |       8875522 | "4872887552211"       | "W"     |
      | "Inserting details into the customer_opt table from Mobile channel" |       8875522 | "4872887552211"       | "M"     |
      | "Inserting details into the customer_opt table from Store channel"  |       8875522 | "4872887552211"       | "R"     |

  Scenario Outline: Deleting from the customer_opt table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "delete" details from the "customer_opt" table through SetCust API
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    Examples:
      | scenario                                                           | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Deleting details from the customer_opt table from Web channel"    |       8875522 | "4872887552211"       | "W"     |
      | "Deleting details from the customer_opt table from Mobile channel" |       8875522 | "4872887552211"       | "M"     |
      | "Deleting details from the customer_opt table from Store channel"  |       8875522 | "4872887552211"       | "R"     |

  Scenario Outline: Updating the customer_phone table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "customer_phone" table through SetCust API using the data "tc10_setcust_update_customer_phone"
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    And I see that the respective columns in the "customer_phone" table updated
    Examples:
      | scenario                                                           | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Updating details in the customer_phone table from Web channel"    |       8875522 | "4872887552211"       | "W"     |
      | "Updating details in the customer_phone table from Mobile channel" |       8875522 | "4872887552211"       | "M"     |

  Scenario Outline: Inserting into the customer_phone table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "customer_phone" table through SetCust API using the data "tc09_setcust_insert_customer_phone"
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    And I see that the respective columns in the "customer_phone" table updated
    Examples:
      | scenario                                                              | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Inserting details into the customer_phone table from Web channel"    |       8875522 | "4872887552211"       | "W"     |
      | "Inserting details into the customer_phone table from Mobile channel" |       8875522 | "4872887552211"       | "M"     |
      | "Inserting details into the customer_phone table from Store channel"  |       8875522 | "4872887552211"       | "R"     |

  Scenario Outline: Inserting into the customer_phone table without the phone_pfx_nbr
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "customer_phone" table through SetCust API using the data "tc11_setcust_insert_customer_phone_without_phonePfxNbr"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                           | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                               |
      | "Inserting details into the customer_phone table from Web channel" |       8875522 | "4872887552211"       | "W"     | "Mandatory columns missing for table: customer_phone" |
      | "Inserting details into the customer_phone table from Web channel" |       8875522 | "4872887552211"       | "M"     | "Mandatory columns missing for table: customer_phone" |
      | "Inserting details into the customer_phone table from Web channel" |       8875522 | "4872887552211"       | "R"     | "Mandatory columns missing for table: customer_phone" |

  Scenario Outline: Inserting into the customer_phone table without the phone_sfx_nbr
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "customer_phone" table through SetCust API using the data "tc12_setcust_insert_customer_phone_without_phoneSfxNbr"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                           | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                               |
      | "Inserting details into the customer_phone table from Web channel" |       8875522 | "4872887552211"       | "W"     | "Mandatory columns missing for table: customer_phone" |
      | "Inserting details into the customer_phone table from Web channel" |       8875522 | "4872887552211"       | "M"     | "Mandatory columns missing for table: customer_phone" |
      | "Inserting details into the customer_phone table from Web channel" |       8875522 | "4872887552211"       | "R"     | "Mandatory columns missing for table: customer_phone" |

  Scenario Outline: Inserting into the customer_phone table without the phone_area_cd_nbr
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "customer_phone" table through SetCust API using the data "tc13_setcust_insert_customer_phone_without_phoneAreaCdNbr"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                           | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                               |
      | "Inserting details into the customer_phone table from Web channel" |       8875522 | "4872887552211"       | "W"     | "Mandatory columns missing for table: customer_phone" |
      | "Inserting details into the customer_phone table from Web channel" |       8875522 | "4872887552211"       | "M"     | "Mandatory columns missing for table: customer_phone" |
      | "Inserting details into the customer_phone table from Web channel" |       8875522 | "4872887552211"       | "R"     | "Mandatory columns missing for table: customer_phone" |

  Scenario Outline: Deleting from the customer_phone table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "delete" details from the "customer_phone" table through SetCust API
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    Examples:
      | scenario                                                             | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Deleting details from the customer_phone table from Web channel"    |       8875522 | "4872887552211"       | "W"     |
      | "Deleting details from the customer_phone table from Mobile channel" |       8875522 | "4872887552211"       | "M"     |
      | "Deleting details from the customer_phone table from Store channel"  |       8875522 | "4872887552211"       | "R"     |

  #Phase2
  @xtracard
  Scenario Outline: Updating the xtra_card_child table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "xtra_card_child" table through SetCust API using the data "tc14_setcust_update_xtra_card_child"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                            | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                           |
      | "Updating details in the xtra_card_child table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "Invalid Channel [W] for table [xtra_card_child]" |
      | "Updating details in the xtra_card_child table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "Invalid Channel [M] for table [xtra_card_child]" |
      | "Updating details in the xtra_card_child table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "Invalid Channel [R] for table [xtra_card_child]" |

  @xtracard
  Scenario Outline: Updating the xtra_card_child table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "xtra_card_child" table through SetCust API using the data "tc14_setcust_update_xtra_card_child"
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    And I see that the respective columns in the "xtra_card_child" table updated
    Examples:
      | scenario                                                                 | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Updating details in the xtra_card_child table from Call Center channel" |       8875522 | "4872887552211"       | "A"     |

  @xtracard
  Scenario Outline: Inserting into the xtra_card_child table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_card_child" table through SetCust API using the data "tc15_setcust_insert_xtra_card_child"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                               | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                           |
      | "Inserting details into the xtra_card_child table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "Invalid Channel [W] for table [xtra_card_child]" |
      | "Inserting details into the xtra_card_child table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "Invalid Channel [M] for table [xtra_card_child]" |
      | "Inserting details into the xtra_card_child table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "Invalid Channel [R] for table [xtra_card_child]" |

  @xtracard
  Scenario Outline: Inserting into the xtra_card_child table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_card_child" table through SetCust API using the data "tc15_setcust_insert_xtra_card_child"
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    And I see a new row inserted into the "xtra_card_child" table

    Examples:
      | scenario                                                                    | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Inserting details into the xtra_card_child table from Call Center channel" |       8875522 | "4872887552211"       | "A"     |

  @xtracard
  Scenario Outline: Deleting from the xtra_card_child table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "delete" details from the "xtra_card_child" table through SetCust API
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                              | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                           |
      | "Deleting details from the xtra_card_child table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "Invalid Channel [W] for table [xtra_card_child]" |
      | "Deleting details from the xtra_card_child table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "Invalid Channel [M] for table [xtra_card_child]" |
      | "Deleting details from the xtra_card_child table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "Invalid Channel [R] for table [xtra_card_child]" |

  @xtracard
  Scenario Outline: Deleting from the xtra_card_child table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "delete" details from the "xtra_card_child" table through SetCust API
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    Examples:
      | scenario                                                                   | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Deleting details from the xtra_card_child table from Call Center channel" |       8875522 | "4872887552211"       | "A"     |

  @xtracard
  Scenario Outline: Updating the xtra_card_wellness_info table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "xtra_card_wellness_info" table through SetCust API using the data "tc16_setcust_update_xtra_card_wellness_info"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                    | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                                   |
      | "Updating details in the xtra_card_wellness_info table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "Invalid Channel [W] for table [xtra_card_wellness_info]" |
      | "Updating details in the xtra_card_wellness_info table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "Invalid Channel [M] for table [xtra_card_wellness_info]" |
      | "Updating details in the xtra_card_wellness_info table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "Invalid Channel [R] for table [xtra_card_wellness_info]" |

  @xtracard
  Scenario Outline: Updating the xtra_card_wellness_info table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "xtra_card_wellness_info" table through SetCust API using the data "tc16_setcust_update_xtra_card_wellness_info"
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    And I see that the respective columns in the "xtra_card_child" table updated
    Examples:
      | scenario                                                                         | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Updating details in the xtra_card_wellness_info table from Call Center channel" |       8875522 | "4872887552211"       | "A"     |

  @xtracard
  Scenario Outline: Inserting into the xtra_card_wellness_info table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_card_wellness_info" table through SetCust API using the data "tc17_setcust_insert_xtra_card_wellness_info"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                       | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                                   |
      | "Inserting details into the xtra_card_wellness_info table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "Invalid Channel [W] for table [xtra_card_wellness_info]" |
      | "Inserting details into the xtra_card_wellness_info table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "Invalid Channel [M] for table [xtra_card_wellness_info]" |
      | "Inserting details into the xtra_card_wellness_info table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "Invalid Channel [R] for table [xtra_card_wellness_info]" |

  @xtracard
  Scenario Outline: Inserting into the xtra_card_wellness_info table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_card_wellness_info" table through SetCust API using the data "tc17_setcust_insert_xtra_card_wellness_info"
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    And I see a new row inserted into the "xtra_card_wellness_info" table
    Examples:
      | scenario                                                                            | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Inserting details into the xtra_card_wellness_info table from Call Center channel" |       8875522 | "4872887552211"       | "A"     |

  @xtracard
  Scenario Outline: Deleting from the xtra_card_wellness_info table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "delete" details from the "xtra_card_wellness_info" table through SetCust API
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                      | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                                   |
      | "Deleting details from the xtra_card_wellness_info table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "Invalid Channel [W] for table [xtra_card_wellness_info]" |
      | "Deleting details from the xtra_card_wellness_info table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "Invalid Channel [M] for table [xtra_card_wellness_info]" |
      | "Deleting details from the xtra_card_wellness_info table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "Invalid Channel [R] for table [xtra_card_wellness_info]" |

  @xtracard
  Scenario Outline: Deleting from the xtra_card_wellness_info table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "delete" details from the "xtra_card_wellness_info" table through SetCust API
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    Examples:
      | scenario                                                                           | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Deleting details from the xtra_card_wellness_info table from Call Center channel" |       8875522 | "4872887552211"       | "A"     |

  @xtracard
  Scenario Outline: Updating the xtra_card_selected_category table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "xtra_card_selected_category" table through SetCust API using the data "tc18_setcust_update_xtra_card_selected_category"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                        | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                                       |
      | "Updating details in the xtra_card_selected_category table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "Invalid Channel [W] for table [xtra_card_selected_category]" |
      | "Updating details in the xtra_card_selected_category table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "Invalid Channel [M] for table [xtra_card_selected_category]" |
      | "Updating details in the xtra_card_selected_category table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "Invalid Channel [R] for table [xtra_card_selected_category]" |

  @xtracard
  Scenario Outline: Updating the xtra_card_selected_category table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "xtra_card_selected_category" table through SetCust API using the data "tc18_setcust_update_xtra_card_selected_category"
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    And I see that the respective columns in the "xtra_card_selected_category" table updated
    Examples:
      | scenario                                                                             | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Updating details in the xtra_card_selected_category table from Call Center channel" |       8875522 | "4872887552211"       | "A"     |

  @xtracard
  Scenario Outline: Inserting into the xtra_card_selected_category table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_card_selected_category" table through SetCust API using the data "tc19_setcust_insert_xtra_card_selected_category"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                           | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                                       |
      | "Inserting details into the xtra_card_selected_category table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "Invalid Channel [W] for table [xtra_card_selected_category]" |
      | "Inserting details into the xtra_card_selected_category table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "Invalid Channel [M] for table [xtra_card_selected_category]" |
      | "Inserting details into the xtra_card_selected_category table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "Invalid Channel [R] for table [xtra_card_selected_category]" |

  @xtracard
  Scenario Outline: Inserting into the xtra_card_selected_category table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_card_selected_category" table through SetCust API using the data "tc19_setcust_insert_xtra_card_selected_category"
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    And I see a new row inserted into the "xtra_card_selected_category" table
    Examples:
      | scenario                                                                                | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Inserting details into the xtra_card_selected_category table from Call Center channel" |       8875522 | "4872887552211"       | "A"     |

  @xtracard
  Scenario Outline: Deleting from the xtra_card_selected_category table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "delete" details from the "xtra_card_selected_category" table through SetCust API
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                          | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                                       |
      | "Deleting details from the xtra_card_selected_category table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "Invalid Channel [W] for table [xtra_card_selected_category]" |
      | "Deleting details from the xtra_card_selected_category table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "Invalid Channel [M] for table [xtra_card_selected_category]" |
      | "Deleting details from the xtra_card_selected_category table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "Invalid Channel [R] for table [xtra_card_selected_category]" |

  @xtracard
  Scenario Outline: Deleting from the xtra_card_selected_category table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "delete" details from the "xtra_card_selected_category" table through SetCust API
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    Examples:
      | scenario                                                                               | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Deleting details from the xtra_card_selected_category table from Call Center channel" |       8875522 | "4872887552211"       | "A"     |

  @xtracard
  Scenario Outline: Inserting into the xtra_customer table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_customer" table through SetCust API using the data "tc20_setcust_insert_xtra_customer"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                             | xtra_card_nbr | channel | message                                                   |
      | "Inserting details into the xtra_customer table from Web channel"    |       8875522 | "W"     | "ERROR: INSERT operation not supported on: xtra_customer" |
      | "Inserting details into the xtra_customer table from Mobile channel" |       8875522 | "M"     | "ERROR: INSERT operation not supported on: xtra_customer" |
      | "Inserting details into the xtra_customer table from Store channel"  |       8875522 | "R"     | "ERROR: INSERT operation not supported on: xtra_customer" |

  @Smoke @xtracard
  Scenario Outline: Updating the xtra_customer table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "xtra_customer" table through SetCust API using the data "tc21_setcust_update_xtra_customer"
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    And I see that the respective columns in the "xtra_customer" table updated
    Examples:
      | scenario                                                          | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Updating details in the xtra_customer table from Web channel"    |       8875522 | "4872887552211"       | "W"     |
      | "Updating details in the xtra_customer table from Mobile channel" |       8875522 | "4872887552211"       | "M"     |
      | "Updating details in the xtra_customer table from Store channel"  |       8875522 | "4872887552211"       | "R"     |

  @xtracard
  Scenario Outline: Updating the xtra_customer table with the cust_stat_updt_dt
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "xtra_customer" table through SetCust API using the data "tc22_setcust_update_xtra_customer_with_cust_stat_updt_dt"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                          | xtra_card_nbr | encoded_xtra_card_nbr | channel | message              |
      | "Updating details in the xtra_customer table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "column not allowed" |
      | "Updating details in the xtra_customer table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "column not allowed" |
      | "Updating details in the xtra_customer table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "column not allowed" |

  @xtracard
  Scenario Outline: Updating the xtra_customer table with the head_of_hh_ind
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "xtra_customer" table through SetCust API using the data "tc23_setcust_update_xtra_customer_with_head_of_hh_ind"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                          | xtra_card_nbr | encoded_xtra_card_nbr | channel | message              |
      | "Updating details in the xtra_customer table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "column not allowed" |
      | "Updating details in the xtra_customer table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "column not allowed" |
      | "Updating details in the xtra_customer table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "column not allowed" |

  @xtracard
  Scenario Outline: Updating the xtra_customer table with the hh_nbr
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "xtra_customer" table through SetCust API using the data "tc24_setcust_update_xtra_customer_with_hh_nbr"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                          | xtra_card_nbr | encoded_xtra_card_nbr | channel | message              |
      | "Updating details in the xtra_customer table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "column not allowed" |
      | "Updating details in the xtra_customer table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "column not allowed" |
      | "Updating details in the xtra_customer table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "column not allowed" |

  @xtracard
  Scenario Outline: Updating the xtra_customer table with the recruit_criteria_cd
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "xtra_customer" table through SetCust API using the data "tc25_setcust_update_xtra_customer_with_recruit_criteria_cd"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                          | xtra_card_nbr | encoded_xtra_card_nbr | channel | message              |
      | "Updating details in the xtra_customer table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "column not allowed" |
      | "Updating details in the xtra_customer table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "column not allowed" |
      | "Updating details in the xtra_customer table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "column not allowed" |

  @xtracard
  Scenario Outline: Updating the xtra_customer table with the cust_id
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "xtra_customer" table through SetCust API using the data "tc26_setcust_update_xtra_customer_with_cust_id"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                          | xtra_card_nbr | encoded_xtra_card_nbr | channel | message              |
      | "Updating details in the xtra_customer table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "column not allowed" |
      | "Updating details in the xtra_customer table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "column not allowed" |
      | "Updating details in the xtra_customer table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "column not allowed" |

  @xtracard
  Scenario Outline: Deleting from the xtra_customer table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "delete" details from the "xtra_customer" table through SetCust API
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                            | xtra_card_nbr | channel | message                                                   |
      | "Deleting details from the xtra_customer table from Web channel"    |       8875522 | "W"     | "ERROR: DELETE operation not supported on: xtra_customer" |
      | "Deleting details from the xtra_customer table from Mobile channel" |       8875522 | "M"     | "ERROR: DELETE operation not supported on: xtra_customer" |
      | "Deleting details from the xtra_customer table from Store channel"  |       8875522 | "R"     | "ERROR: DELETE operation not supported on: xtra_customer" |

  @xtracard
  Scenario Outline: Inserting into the xtra_card table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_card" table through SetCust API using the data "tc27_setcust_insert_xtra_card"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                         | xtra_card_nbr | channel | message                                               |
      | "Inserting details into the xtra_card table from Web channel"    |       8875522 | "W"     | "ERROR: INSERT operation not supported on: xtra_card" |
      | "Inserting details into the xtra_card table from Mobile channel" |       8875522 | "M"     | "ERROR: INSERT operation not supported on: xtra_card" |
      | "Inserting details into the xtra_card table from Store channel"  |       8875522 | "R"     | "ERROR: INSERT operation not supported on: xtra_card" |

  @Smoke @xtracard
  Scenario Outline: Updating the xtra_card table with the xtra_card_nbr
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "xtra_card" table through SetCust API using the data "tc29_setcust_update_xtra_card_with_xtra_card_nbr"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                      | xtra_card_nbr | encoded_xtra_card_nbr | channel | message              |
      | "Updating details in the xtra_card table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "column not allowed" |
      | "Updating details in the xtra_card table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "column not allowed" |
      | "Updating details in the xtra_card table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "column not allowed" |

  @xtracard
  Scenario Outline: Updating the xtra_card table with the cust_id
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "xtra_card" table through SetCust API using the data "tc30_setcust_update_xtra_card_with_cust_id"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                      | xtra_card_nbr | encoded_xtra_card_nbr | channel | message              |
      | "Updating details in the xtra_card table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "column not allowed" |
      | "Updating details in the xtra_card table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "column not allowed" |
      | "Updating details in the xtra_card table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "column not allowed" |

  @xtracard
  Scenario Outline: Updating the xtra_card table with the card_first_scan_dt
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "xtra_card" table through SetCust API using the data "tc31_setcust_update_xtra_card_with_card_first_scan_dt"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                      | xtra_card_nbr | encoded_xtra_card_nbr | channel | message              |
      | "Updating details in the xtra_card table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "column not allowed" |
      | "Updating details in the xtra_card table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "column not allowed" |
      | "Updating details in the xtra_card table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "column not allowed" |

  @xtracard
  Scenario Outline: Updating the xtra_card table with the tot_lifetime_visit_cnt
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "xtra_card" table through SetCust API using the data "tc32_setcust_update_xtra_card_with_tot_lifetime_visit_cnt"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                      | xtra_card_nbr | encoded_xtra_card_nbr | channel | message              |
      | "Updating details in the xtra_card table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "column not allowed" |
      | "Updating details in the xtra_card table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "column not allowed" |
      | "Updating details in the xtra_card table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "column not allowed" |

  @xtracard
  Scenario Outline: Updating the xtra_card table with the referred_by_cd
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "xtra_card" table through SetCust API using the data "tc33_setcust_update_xtra_card_with_referred_by_cd"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                      | xtra_card_nbr | encoded_xtra_card_nbr | channel | message              |
      | "Updating details in the xtra_card table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "column not allowed" |
      | "Updating details in the xtra_card table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "column not allowed" |
      | "Updating details in the xtra_card table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "column not allowed" |

  @xtracard
  Scenario Outline: Updating the xtra_card table with the rank_cd
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "xtra_card" table through SetCust API using the data "tc34_setcust_update_xtra_card_with_rank_cd"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                      | xtra_card_nbr | encoded_xtra_card_nbr | channel | message              |
      | "Updating details in the xtra_card table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "column not allowed" |
      | "Updating details in the xtra_card table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "column not allowed" |
      | "Updating details in the xtra_card table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "column not allowed" |

  @xtracard
  Scenario Outline: Deleting from the xtra_card table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "delete" details from the "xtra_card" table through SetCust API
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                        | xtra_card_nbr | channel | message                                               |
      | "Deleting details from the xtra_card table from Web channel"    |       8875522 | "W"     | "ERROR: DELETE operation not supported on: xtra_card" |
      | "Deleting details from the xtra_card table from Mobile channel" |       8875522 | "M"     | "ERROR: DELETE operation not supported on: xtra_card" |
      | "Deleting details from the xtra_card table from Store channel"  |       8875522 | "R"     | "ERROR: DELETE operation not supported on: xtra_card" |

  #phase3
  Scenario Outline: Inserting into the fsa_dtl_request table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "fsa_dtl_request" table through SetCust API using the data "tc35_setcust_insert_fsa_dtl_request"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                               | xtra_card_nbr | channel | message                                           |
      | "Inserting details into the fsa_dtl_request table from Web channel"    |       8875522 | "W"     | "Invalid Channel [W] for table [fsa_dtl_request]" |
      | "Inserting details into the fsa_dtl_request table from Mobile channel" |       8875522 | "M"     | "Invalid Channel [M] for table [fsa_dtl_request]" |
      | "Inserting details into the fsa_dtl_request table from Store channel"  |       8875522 | "R"     | "Invalid Channel [R] for table [fsa_dtl_request]" |

  Scenario Outline: Inserting into the fsa_dtl_request table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "fsa_dtl_request" table through SetCust API using the data "tc35_setcust_insert_fsa_dtl_request"
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    #And I see that the respective columns in the "fsa_dtl_request" table updated
    Examples: 
      | scenario                                                                    | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Inserting details into the fsa_dtl_request table from Call Center channel" |       8875522 | "4872887552211"       | "A"     |

  Scenario Outline: Updating the fsa_dtl_request table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "fsa_dtl_request" table through SetCust API using the data "tc36_setcust_update_fsa_dtl_request"
    Then I see a Bad Request response with the <message>

    Examples:
      | scenario                                                            | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                           |
      | "Updating details in the fsa_dtl_request table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "Invalid Channel [W] for table [fsa_dtl_request]" |
      | "Updating details in the fsa_dtl_request table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "Invalid Channel [M] for table [fsa_dtl_request]" |
      | "Updating details in the fsa_dtl_request table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "Invalid Channel [R] for table [fsa_dtl_request]" |

  Scenario Outline: Updating the fsa_dtl_request table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "fsa_dtl_request" table through SetCust API using the data "tc36_setcust_update_fsa_dtl_request"
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    #And I see that the respective columns in the "fsa_dtl_request" table updated
    Examples:
      | scenario                                                                 | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Updating details in the fsa_dtl_request table from Call Center channel" |       8875522 | "4872887552211"       | "A"     |

  Scenario Outline: Deleting from the fsa_dtl_request table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "delete" details from the "fsa_dtl_request" table through SetCust API
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                              | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                           |
      | "Deleting details from the fsa_dtl_request table from Web channel"    |       8875522 | "4872887552211"       | "W"     | "Invalid Channel [W] for table [fsa_dtl_request]" |
      | "Deleting details from the fsa_dtl_request table from Mobile channel" |       8875522 | "4872887552211"       | "M"     | "Invalid Channel [M] for table [fsa_dtl_request]" |
      | "Deleting details from the fsa_dtl_request table from Store channel"  |       8875522 | "4872887552211"       | "R"     | "Invalid Channel [R] for table [fsa_dtl_request]" |

  @xtracard
  Scenario Outline: Deleting from the fsa_dtl_request table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "delete" details from the "fsa_dtl_request" table through SetCust API
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    Examples:
      | scenario                                                                   | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Deleting details from the fsa_dtl_request table from Call Center channel" |       8875522 | "4872887552211"       | "A"     |

  Scenario Outline: Inserting into the xtra_card_coupon_pending_chng table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_card_coupon_pending_chng" table through SetCust API using the data "tc37_setcust_insert_xtra_card_coupon_pending_chng"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                             | xtra_card_nbr | channel | message                                                         |
      | "Inserting details into the xtra_card_coupon_pending_chng table from Web channel"    |       8875522 | "W"     | "Invalid Channel [W] for table [xtra_card_coupon_pending_chng]" |
      | "Inserting details into the xtra_card_coupon_pending_chng table from Mobile channel" |       8875522 | "M"     | "Invalid Channel [M] for table [xtra_card_coupon_pending_chng]" |
      | "Inserting details into the xtra_card_coupon_pending_chng table from Store channel"  |       8875522 | "R"     | "Invalid Channel [R] for table [xtra_card_coupon_pending_chng]" |

  Scenario Outline: Inserting into the xtra_card_coupon_pending_chng table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_card_coupon_pending_chng" table through SetCust API using the data "tc37_setcust_insert_xtra_card_coupon_pending_chng"
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    # And I see that the respective columns in the "fsa_dtl_request" table updated
    Examples:
      | scenario                                                                                  | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Inserting details into the xtra_card_coupon_pending_chng table from Call Center channel" |       8875522 | "4872887552211"       | "A"     |

  Scenario Outline: Updating the xtra_card_coupon_pending_chng table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "update" details in the "xtra_card_coupon_pending_chng" table through SetCust API using the data "tc38_setcust_update_xtra_card_coupon_pending_chng"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                               | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                                                   |
      | "Updating details in the xtra_card_coupon_pending_chng table from Web channel"         |       8875522 | "4872887552211"       | "W"     | "ERROR: UPDATE operation not supported on: xtra_card_coupon_pending_chng" |
      | "Updating details in the xtra_card_coupon_pending_chng table from Mobile channel"      |       8875522 | "4872887552211"       | "M"     | "ERROR: UPDATE operation not supported on: xtra_card_coupon_pending_chng" |
      | "Updating details in the xtra_card_coupon_pending_chng table from Store channel"       |       8875522 | "4872887552211"       | "R"     | "ERROR: UPDATE operation not supported on: xtra_card_coupon_pending_chng" |
      | "Updating details in the xtra_card_coupon_pending_chng table from Call Center channel" |       8875522 | "4872887552211"       | "A"     | "ERROR: UPDATE operation not supported on: xtra_card_coupon_pending_chng" |

  Scenario Outline: Deleting from the xtra_card_coupon_pending_chng table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "delete" details from the "xtra_card_coupon_pending_chng" table through SetCust API
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                                 | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                                                   |
      | "Deleting details from the xtra_card_coupon_pending_chng table from Web channel"         |       8875522 | "4872887552211"       | "W"     | "ERROR: DELETE operation not supported on: xtra_card_coupon_pending_chng" |
      | "Deleting details from the xtra_card_coupon_pending_chng table from Mobile channel"      |       8875522 | "4872887552211"       | "M"     | "ERROR: DELETE operation not supported on: xtra_card_coupon_pending_chng" |
      | "Deleting details from the xtra_card_coupon_pending_chng table from Store channel"       |       8875522 | "4872887552211"       | "R"     | "ERROR: DELETE operation not supported on: xtra_card_coupon_pending_chng" |
      | "Deleting details from the xtra_card_coupon_pending_chng table from Call Center channel" |       8875522 | "4872887552211"       | "A"     | "ERROR: DELETE operation not supported on: xtra_card_coupon_pending_chng" |

  Scenario Outline: Deleting from the xtra_card_reminder table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "delete" details from the "xtra_card_reminder" table through SetCust API
    Then I receive a response with success status
    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
    Examples:
      | scenario                                                                 | xtra_card_nbr | encoded_xtra_card_nbr | channel |
      | "Deleting details from the xtra_card_reminder table from Web channel"    |       8875522 | "4872887552211"       | "W"     |
      | "Deleting details from the xtra_card_reminder table from Mobile channel" |       8875522 | "4872887552211"       | "M"     |
      | "Deleting details from the xtra_card_reminder table from Store channel"  |       8875522 | "4872887552211"       | "R"     |

  Scenario Outline: Inserting into the xtra_privacy_request table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_privacy_request" table through SetCust API using the data "tc39_setcust_insert_xtra_privacy_request"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                    | xtra_card_nbr | channel | message                                                     |
      | "Inserting details into the xtra_privacy_request table from Store channel"  |       8875522 | "R"     | "Invalid Channel [R] for table [xtra_privacy_request]" |
      | "Inserting details into the xtra_privacy_request table from Mobile channel" |       8875522 | "M"     | "Invalid Channel [M] for table [xtra_privacy_request]" |

  #@ignore
#  Scenario Outline: Inserting into the xtra_privacy_request table
#    Given <scenario>
#    And I use EC <xtra_card_nbr> from the channel <channel>
#    When I try to "insert" details in the "xtra_privacy_request" table through SetCust API using the data "tc39_setcust_insert_xtra_privacy_request"
#    Then I receive a response with success status
#    And I see the <xtra_card_nbr> and <encoded_xtra_card_nbr> in the response
#    #  And I see that the respective columns in the "xtra_privacy_request" table updated
#    Examples:
#      | scenario                                                                         | xtra_card_nbr | encoded_xtra_card_nbr | channel |
#      | "Inserting details into the xtra_privacy_request table from Call Center channel" |       8875522 | "4872887552211"       | "A"     |
#      | "Inserting details into the xtra_privacy_request table from Web channel"         |       8875522 | "4872887552211"       | "W"     |

  #@ignore
#  Scenario Outline: Inserting into the xtra_privacy_request table second time
#    Given <scenario>
#    And I use EC <xtra_card_nbr> from the channel <channel>
#    When I try to "insert" details in the "xtra_privacy_request" table through SetCust API using the data "tc39_setcust_insert_xtra_privacy_request"
#    Then I see a Bad Request response with the <message>
#    Examples:
#      | scenario                                                                         | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                             		                       |
#      | "Inserting details into the xtra_privacy_request table from Call Center channel" |       8875522 | "4872887552211"       | "A"     | "CCPA purge request has already been submitted for this card" |
#      | "Inserting details into the xtra_privacy_request table from Web channel"         |       8875522 | "4872887552211"       | "W"     | "CCPA purge request has already been submitted for this card" |

  Scenario Outline: Inserting into the xtra_privacy_request table without the rqst_dt
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_privacy_request" table through SetCust API using the data "tc40_setcust_insert_xtra_privacy_request_without_rqst_dt"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                         | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                                     |
      | "Inserting details into the xtra_privacy_request table from Web channel"         |       8875522 | "4872887552211"       | "W"     | "Mandatory columns missing for table: xtra_privacy_request" |
      | "Inserting details into the xtra_privacy_request table from Call Center channel" |       8875522 | "4872887552211"       | "A"     | "Mandatory columns missing for table: xtra_privacy_request" |

  Scenario Outline: Inserting into the xtra_privacy_request table without the purge_rqst_ind
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_privacy_request" table through SetCust API using the data "tc41_setcust_insert_xtra_privacy_request_without_purge_rqst_ind"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                         | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                                     |
      | "Inserting details into the xtra_privacy_request table from Web channel"         |       8875522 | "4872887552211"       | "W"     | "Mandatory columns missing for table: xtra_privacy_request" |
      | "Inserting details into the xtra_privacy_request table from Call Center channel" |       8875522 | "4872887552211"       | "A"     | "Mandatory columns missing for table: xtra_privacy_request" |

  Scenario Outline: Inserting into the xtra_privacy_request table without the rpt_rqst_ind
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_privacy_request" table through SetCust API using the data "tc42_setcust_insert_xtra_privacy_request_without_rpt_rqst_ind"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                         | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                                     |
      | "Inserting details into the xtra_privacy_request table from Web channel"         |       8875522 | "4872887552211"       | "W"     | "Mandatory columns missing for table: xtra_privacy_request" |
      | "Inserting details into the xtra_privacy_request table from Call Center channel" |       8875522 | "4872887552211"       | "A"     | "Mandatory columns missing for table: xtra_privacy_request" |

  Scenario Outline: Inserting into the xtra_privacy_request table without the first_updt_src_cd
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_privacy_request" table through SetCust API using the data "tc43_setcust_insert_xtra_privacy_request_without_first_updt_src_cd"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                         | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                                     |
      | "Inserting details into the xtra_privacy_request table from Web channel"         |       8875522 | "4872887552211"       | "W"     | "Mandatory columns missing for table: xtra_privacy_request" |
      | "Inserting details into the xtra_privacy_request table from Call Center channel" |       8875522 | "4872887552211"       | "A"     | "Mandatory columns missing for table: xtra_privacy_request" |

  Scenario Outline: Inserting into the xtra_privacy_request table without the first_updt_by_id
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_privacy_request" table through SetCust API using the data "tc44_setcust_insert_xtra_privacy_request_without_first_updt_by_id"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                         | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                                     |
      | "Inserting details into the xtra_privacy_request table from Web channel"         |       8875522 | "4872887552211"       | "W"     | "Mandatory columns missing for table: xtra_privacy_request" |
      | "Inserting details into the xtra_privacy_request table from Call Center channel" |       8875522 | "4872887552211"       | "A"     | "Mandatory columns missing for table: xtra_privacy_request" |

  Scenario Outline: Inserting into the xtra_privacy_request table without the first_updt_dt
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_privacy_request" table through SetCust API using the data "tc45_setcust_insert_xtra_privacy_request_without_first_updt_dt"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                         | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                                     |
      | "Inserting details into the xtra_privacy_request table from Web channel"         |       8875522 | "4872887552211"       | "W"     | "Mandatory columns missing for table: xtra_privacy_request" |
      | "Inserting details into the xtra_privacy_request table from Call Center channel" |       8875522 | "4872887552211"       | "A"     | "Mandatory columns missing for table: xtra_privacy_request" |

  Scenario Outline: Inserting into the xtra_privacy_request table without the last_updt_src_cd
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_privacy_request" table through SetCust API using the data "tc46_setcust_insert_xtra_privacy_request_without_last_updt_src_cd"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                         | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                                     |
      | "Inserting details into the xtra_privacy_request table from Web channel"         |       8875522 | "4872887552211"       | "W"     | "Mandatory columns missing for table: xtra_privacy_request" |
      | "Inserting details into the xtra_privacy_request table from Call Center channel" |       8875522 | "4872887552211"       | "A"     | "Mandatory columns missing for table: xtra_privacy_request" |

  Scenario Outline: Inserting into the xtra_privacy_request table without the last_updt_by_id
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_privacy_request" table through SetCust API using the data "tc47_setcust_insert_xtra_privacy_request_without_last_updt_by_id"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                         | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                                     |
      | "Inserting details into the xtra_privacy_request table from Web channel"         |       8875522 | "4872887552211"       | "W"     | "Mandatory columns missing for table: xtra_privacy_request" |
      | "Inserting details into the xtra_privacy_request table from Call Center channel" |       8875522 | "4872887552211"       | "A"     | "Mandatory columns missing for table: xtra_privacy_request" |

  Scenario Outline: Inserting into the xtra_privacy_request table without the last_updt_dt
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "insert" details in the "xtra_privacy_request" table through SetCust API using the data "tc48_setcust_insert_xtra_privacy_request_without_last_updt_dt"
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                         | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                                     |
      | "Inserting details into the xtra_privacy_request table from Web channel"         |       8875522 | "4872887552211"       | "W"     | "Mandatory columns missing for table: xtra_privacy_request" |
      | "Inserting details into the xtra_privacy_request table from Call Center channel" |       8875522 | "4872887552211"       | "A"     | "Mandatory columns missing for table: xtra_privacy_request" |

  #@ignore
#  Scenario Outline: Inserting into the xtra_privacy_request table with the rpt_mailed_conf_name
#    Given <scenario>
#    And I use EC <xtra_card_nbr> from the channel <channel>
#    When I try to "insert" details in the "xtra_privacy_request" table through SetCust API using the data "tc49_setcust_insert_xtra_privacy_request_with_rpt_mailed_conf_name"
#    Then I see a Bad Request response with the <message>
#    Examples:
#      | scenario                                                                         | xtra_card_nbr | encoded_xtra_card_nbr | channel | message              |
#      | "Inserting details into the xtra_privacy_request table from Web channel"         |       8875522 | "4872887552211"       | "W"     | "column not allowed" |
#      | "Inserting details into the xtra_privacy_request table from Call Center channel" |       8875522 | "4872887552211"       | "A"     | "column not allowed" |
#
  #@ignore
#  Scenario Outline: Inserting into the xtra_privacy_request table with the rpt_mailed_conf_dt
#    Given <scenario>
#    And I use EC <xtra_card_nbr> from the channel <channel>
#    When I try to "insert" details in the "xtra_privacy_request" table through SetCust API using the data "tc50_setcust_insert_xtra_privacy_request_with_rpt_mailed_conf_dt"
#    Then I see a Bad Request response with the <message>
#    Examples:
#      | scenario                                                                         | xtra_card_nbr | encoded_xtra_card_nbr | channel | message              |
#      | "Inserting details into the xtra_privacy_request table from Web channel"         |       8875522 | "4872887552211"       | "W"     | "column not allowed" |
#      | "Inserting details into the xtra_privacy_request table from Call Center channel" |       8875522 | "4872887552211"       | "A"     | "column not allowed" |

  Scenario Outline: Deleting from the xtra_privacy_request table
    Given <scenario>
    And I use EC <xtra_card_nbr> from the channel <channel>
    When I try to "delete" details from the "xtra_privacy_request" table through SetCust API
    Then I see a Bad Request response with the <message>
    Examples:
      | scenario                                                                        | xtra_card_nbr | encoded_xtra_card_nbr | channel | message                                                          |
      | "Deleting details from the xtra_privacy_request table from Web channel"         |       8875522 | "4872887552211"       | "W"     | "ERROR: DELETE operation not supported on: xtra_privacy_request" |
      | "Deleting details from the xtra_privacy_request table from Mobile channel"      |       8875522 | "4872887552211"       | "M"     | "ERROR: DELETE operation not supported on: xtra_privacy_request" |
      | "Deleting details from the xtra_privacy_request table from Store channel"       |       8875522 | "4872887552211"       | "R"     | "ERROR: DELETE operation not supported on: xtra_privacy_request" |
      | "Deleting details from the xtra_privacy_request table from Call Center channel" |       8875522 | "4872887552211"       | "A"     | "ERROR: DELETE operation not supported on: xtra_privacy_request" |
