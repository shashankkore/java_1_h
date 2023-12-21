@Phrdashboard
Feature: Validate benefits on Dashboard

  Scenario Outline: Dashboard service for PHR enrolled patient
    Given "I am a CVS customer with 0 PHR dependents"
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "false"
    And I see my PHR maxCredits as 0.0
    And I see my PHR maxRewardAmount as 0.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 44496
    And I see my PHR "2021 - PHR Regular"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 10.0
    And I see my PHR Reward Amount as 5.0
    And I see my PHR Points required to get next coupon as 10.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 0.0
    And I see my PHR year To Date Earned as 0.0
    And I see my PHR year To Date Credits as 0.0
    
    Examples:
      | scenario                    								| xtra_card_nbr |message_source_code	|
      | "I am a CVS customer with 0 PHR dependents"	| 298344807     |STORE								|
 
 
  Scenario Outline: Dashboard service for PHR enrolled patient
    Given "I am a CVS customer with 1 PHR dependent without points earning"
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my Member maxCredits as 100.0
    And I see my Member maxRewardAmount as 50.0
    And I see my PHR maxCredits as 0.0
    And I see my PHR maxRewardAmount as 0.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 44496
    And I see my PHR "2021 - PHR Regular"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 10.0
    And I see my PHR Reward Amount as 5.0
    And I see my PHR Points required to get next coupon as 10.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 0.0
    And I see my PHR year To Date Earned as 0.0
    And I see my PHR year To Date Credits as 0.0
    
    Examples:
      | scenario                    																			| xtra_card_nbr |message_source_code	|
      | "I am a CVS customer with 1 PHR dependent without points earning"	| 298344808     |STORE								|
 
 
  Scenario Outline: Dashboard service for PHR enrolled patient
    Given "I am a CVS customer with 1 PHR dependent with 6 points progress"
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my Member maxCredits as 100.0
    And I see my Member maxRewardAmount as 50.0
    And I see my PHR maxCredits as 100.0
    And I see my PHR maxRewardAmount as 50.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 44496
    And I see my PHR "2021 - PHR Regular"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 10.0
    And I see my PHR Reward Amount as 5.0
    And I see my PHR Points required to get next coupon as 4.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 6.0
    And I see my PHR year To Date Earned as 0.0
    And I see my PHR year To Date Credits as 6.0
    
    Examples:
      | scenario                    																			| xtra_card_nbr |message_source_code	|
      | "I am a CVS customer with 1 PHR dependent with 6 points progress"	| 298344809     |STORE								|
 
 
  Scenario Outline: Dashboard service for PHR enrolled patient
    Given "I am a CVS customer with 1 PHR dependent with 16 points progress"
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my Member maxCredits as 100.0
    And I see my Member maxRewardAmount as 50.0
    And I see my PHR maxCredits as 100.0
    And I see my PHR maxRewardAmount as 50.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 44496
    And I see my PHR "2021 - PHR Regular"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 10.0
    And I see my PHR Reward Amount as 5.0
    And I see my PHR Points required to get next coupon as 4.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 6.0
    And I see my PHR year To Date Earned as 5.0
    And I see my PHR year To Date Credits as 16.0
    
    Examples:
      | scenario                    																				| xtra_card_nbr |message_source_code	|
      | "I am a CVS customer with 1 PHR dependent with 16 points progress"	| 298344810     |STORE								|
 
  
  Scenario Outline: Dashboard service for PHR enrolled patient
    Given "I am a CVS customer with 1 PHR dependent with 103 points progress"
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my Member maxCredits as 100.0
    And I see my Member maxRewardAmount as 50.0
    And I see my PHR maxCredits as 100.0
    And I see my PHR maxRewardAmount as 50.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 44496
    And I see my PHR "2021 - PHR Regular"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 10.0
    And I see my PHR Reward Amount as 5.0
    And I see my PHR Points required to get next coupon as 7.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 3.0
    And I see my PHR year To Date Earned as 50.0
    And I see my PHR year To Date Credits as 103.0
    
    Examples:
      | scenario                    																				| xtra_card_nbr |message_source_code	|
      | "I am a CVS customer with 1 PHR dependent with 103 points progress"	| 298344811     |STORE								|
  
  
  Scenario Outline: Dashboard service for PHR enrolled patient
    Given "I am a CVS customer with 2 PHR dependents without points earning"
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my Member maxCredits as 100.0
    And I see my Member maxRewardAmount as 50.0
    And I see my PHR maxCredits as 0.0
    And I see my PHR maxRewardAmount as 0.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 44496
    And I see my PHR "2021 - PHR Regular"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 10.0
    And I see my PHR Reward Amount as 5.0
    And I see my PHR Points required to get next coupon as 10.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 0.0
    And I see my PHR year To Date Earned as 0.0
    And I see my PHR year To Date Credits as 0.0
    
    Examples:
      | scenario                    																				| xtra_card_nbr |message_source_code	|
      | "I am a CVS customer with 2 PHR dependents without points earning"	| 298344812     |STORE								|
  
  
  Scenario Outline: Dashboard service for PHR enrolled patient
    Given "I am a CVS customer with 2 PHR dependents with 6 points progress"
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my Member maxCredits as 100.0
    And I see my Member maxRewardAmount as 50.0
    And I see my PHR maxCredits as 200.0
    And I see my PHR maxRewardAmount as 100.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 44496
    And I see my PHR "2021 - PHR Regular"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 10.0
    And I see my PHR Reward Amount as 5.0
    And I see my PHR Points required to get next coupon as 4.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 6.0
    And I see my PHR year To Date Earned as 0.0
    And I see my PHR year To Date Credits as 6.0
    And I see under my PHR who all enrolled as Hari1 and Hari2
    	 | firstName | capped | maxCredits | maxRewardAmount |
       | Hari1     | false  | 100.0      | 50.0            |
       | Hari2     | false  | 100.0      | 50.0            |
    
    Examples:
      | scenario                    																				| xtra_card_nbr |message_source_code	|
      | "I am a CVS customer with 2 PHR dependents with 6 points progress"	| 298344813     |STORE								|
  
  
  Scenario Outline: Dashboard service for PHR enrolled patient
    Given "I am a CVS customer with 2 PHR dependents who reached max reward amount"
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my Member maxCredits as 100.0
    And I see my Member maxRewardAmount as 50.0
    And I see my PHR maxCredits as 200.0
    And I see my PHR maxRewardAmount as 100.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 44496
    And I see my PHR "2021 - PHR Regular"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 10.0
    And I see my PHR Reward Amount as 5.0
    And I see my PHR Points required to get next coupon as 7.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 3.0
    And I see my PHR year To Date Earned as 100.0
    And I see my PHR year To Date Credits as 203.0
    And I see under my PHR who all enrolled as Hari1 and Hari2
    	 | firstName | capped | maxCredits | maxRewardAmount |
       | Hari1     | false  | 100.0      | 50.0            |
       | Hari2     | false  | 100.0      | 50.0            |
    
    Examples:
      | scenario                    																				| xtra_card_nbr |message_source_code	|
      | "CVS customer with 2 PHR dependents who reached max reward amount"	| 298344814     |STORE								|
    
      
   Scenario Outline: Dashboard service for PHR enrolled patient
    Given "I am a CVS customer with 1 PHR dependent who is capped"
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my first Member capped as "true"
    And I see my PHR capped as "true"
    And I see my PHR Campaign Id as 44496
    And I see my PHR "2021 - PHR Regular"
    
    Examples:
      | scenario                    												| xtra_card_nbr |message_source_code	|
      | "CVS customer with 1 PHR dependents who is capped"	| 298344953     |STORE								|
    
      
   Scenario Outline: Dashboard service for PHR enrolled patient
    Given "I am a CVS customer with 2 PHR dependents with one member capped"
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 44496
    And I see my PHR "2021 - PHR Regular"
    And I see under my PHR who all enrolled as Hari1 and Hari2
    	 | firstName | capped | maxCredits | maxRewardAmount |
       | Hari1     | true   | 100.0      | 50.0            |
       | Hari2     | false  | 100.0      | 50.0            |
    
    Examples:
      | scenario                    																| xtra_card_nbr |message_source_code	|
      | "CVS customer with 2 PHR dependents with one member capped"	| 298344955     |STORE								|
      
  
  Scenario Outline: Dashboard service for PHR enrolled patient
    Given "I am a CVS customer with 2 PHR dependents with both members capped"
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my PHR capped as "true"
    And I see my PHR Campaign Id as 44496
    And I see my PHR "2021 - PHR Regular"
    And I see under my PHR who all enrolled as Hari1 and Hari2
    	 | firstName | capped | maxCredits | maxRewardAmount |
       | Hari1     | true   | 100.0      | 50.0            |
       | Hari2     | true   | 100.0      | 50.0            |
    
    Examples:
      | scenario                    																| xtra_card_nbr |message_source_code	|
      | "CVS customer with 2 PHR dependents with one member capped"	| 298344957     |STORE								|
      
 #Pilot PHR program

  Scenario Outline: Dashboard service for PHR enrolled patient - Pilot program
    Given "I am a CVS customer with 1 PHR dependent with 3 points progress - Pilot"
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my Member maxCredits as 100.0
    And I see my Member maxRewardAmount as 50.0
    And I see my PHR maxCredits as 100.0
    And I see my PHR maxRewardAmount as 50.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 44497
    And I see my PHR "2021 - Pilot PHR campaign"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 4.0
    #And I see my PHR Reward Amount as 2.0
    And I see my PHR Points required to get next coupon as 1.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 3.0
    And I see my PHR year To Date Earned as 0.0
    And I see my PHR year To Date Credits as 3.0
    
    Examples:
      | scenario                    																							| xtra_card_nbr |message_source_code	|
      | "I am a CVS customer with 1 PHR dependent with 3 points progress - Pilot"	| 298344993     |STORE								|
 
  
  Scenario Outline: Dashboard service for PHR enrolled patient
    Given "I am a CVS customer with 1 PHR dependent with 7 points progress - Pilot"
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my Member maxCredits as 100.0
    And I see my Member maxRewardAmount as 50.0
    And I see my PHR maxCredits as 100.0
    And I see my PHR maxRewardAmount as 50.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 44497
    And I see my PHR "2021 - Pilot PHR campaign"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 4.0
    #And I see my PHR Reward Amount as 2.0
    And I see my PHR Points required to get next coupon as 1.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 3.0
    And I see my PHR year To Date Earned as 2.0
    And I see my PHR year To Date Credits as 7.0
    
    Examples:
      | scenario                    																							| xtra_card_nbr |message_source_code	|
      | "I am a CVS customer with 1 PHR dependent with 7 points progress - Pilot"	| 298345035     |STORE								|
 
  
  Scenario Outline: Dashboard service for PHR enrolled patient
    Given "I am a CVS customer with 1 PHR dependent with 103 points progress - Pilot"
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my Member maxCredits as 100.0
    And I see my Member maxRewardAmount as 50.0
    And I see my PHR maxCredits as 100.0
    And I see my PHR maxRewardAmount as 50.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 44497
    And I see my PHR "2021 - Pilot PHR campaign"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 4.0
    #And I see my PHR Reward Amount as 2.0
    And I see my PHR Points required to get next coupon as 1.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 3.0
    And I see my PHR year To Date Earned as 50.0
    And I see my PHR year To Date Credits as 103.0
    
    Examples:
      | scenario                    																								| xtra_card_nbr |message_source_code	|
      | "I am a CVS customer with 1 PHR dependent with 103 points progress - Pilot"	| 298345036     |STORE								|
 
  
  Scenario Outline: Dashboard service for PHR enrolled patient
    Given "I am a CVS customer with 2 PHR dependents with 7 points progress - Pilot"
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my Member maxCredits as 100.0
    And I see my Member maxRewardAmount as 50.0
    And I see my PHR maxCredits as 200.0
    And I see my PHR maxRewardAmount as 100.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 44497
    And I see my PHR "2021 - Pilot PHR campaign"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 4.0
    #And I see my PHR Reward Amount as 2.0
    And I see my PHR Points required to get next coupon as 1.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 3.0
    And I see my PHR year To Date Earned as 2.0
    And I see my PHR year To Date Credits as 7.0
    And I see under my PHR who all enrolled as Hari1 and Hari2
    	 | firstName | capped | maxCredits | maxRewardAmount |
       | Hari1     | false  | 100.0      | 50.0            |
       | Hari2     | false  | 100.0      | 50.0            |
    
    Examples:
      | scenario                    																								| xtra_card_nbr |message_source_code	|
      | "I am a CVS customer with 2 PHR dependents with 7 points progress - Pilot"	| 298345054     |STORE								|
  
 
  Scenario Outline: Dashboard service for PHR enrolled patient
    Given "I am a CVS customer with 2 PHR dependents who reached max reward amount - Pilot"
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my Member maxCredits as 100.0
    And I see my Member maxRewardAmount as 50.0
    And I see my PHR maxCredits as 200.0
    And I see my PHR maxRewardAmount as 100.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 44497
    And I see my PHR "2021 - Pilot PHR campaign"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 4.0
    #And I see my PHR Reward Amount as 2.0
    And I see my PHR Points required to get next coupon as 1.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 3.0
    And I see my PHR year To Date Earned as 50.0
    And I see my PHR year To Date Credits as 103.0
    And I see under my PHR who all enrolled as Hari1 and Hari2
    	 | firstName | capped | maxCredits | maxRewardAmount |
       | Hari1     | false  | 100.0      | 50.0            |
       | Hari2     | false  | 100.0      | 50.0            |
    
    Examples:
      | scenario                    																								| xtra_card_nbr |message_source_code	|
      | "CVS customer with 2 PHR dependents who reached max reward amount - Pilot"	| 298345055     |STORE								|
      
   
   Scenario Outline: Dashboard service for PHR enrolled patient
    Given "I am a CVS customer with 1 PHR dependent who is capped - Pilot"
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my first Member capped as "true"
    And I see my PHR capped as "true"
    And I see my PHR Campaign Id as 44497
    And I see my PHR "2021 - Pilot PHR campaign"
    
    Examples:
      | scenario                    																| xtra_card_nbr |message_source_code	|
      | "CVS customer with 1 PHR dependents who is capped - Pilot"	| 298345056     |STORE								|
      
  
   Scenario Outline: Dashboard service for PHR enrolled patient
    Given "I am a CVS customer with 2 PHR dependents with one member capped - Pilot"
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 44497
    And I see my PHR "2021 - Pilot PHR campaign"
    And I see under my PHR who all enrolled as Hari1 and Hari2
    	 | firstName | capped | maxCredits | maxRewardAmount |
       | Hari1     | true   | 100.0      | 50.0            |
       | Hari2     | false  | 100.0      | 50.0            |
    
    Examples:
      | scenario                    																				| xtra_card_nbr |message_source_code	|
      | "CVS customer with 2 PHR dependents with one member capped - Pilot"	| 298345057     |STORE								|
      
  Scenario Outline: Dashboard service for PHR enrolled patient
    Given "I am a CVS customer with 2 PHR dependents with both members capped - Pilot"
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my PHR capped as "true"
    And I see my PHR Campaign Id as 44497
    And I see my PHR "2021 - Pilot PHR campaign"
    And I see under my PHR who all enrolled as Hari1 and Hari2
    	 | firstName | capped | maxCredits | maxRewardAmount |
       | Hari1     | true   | 100.0      | 50.0            |
       | Hari2     | true   | 100.0      | 50.0            |
    
    Examples:
      | scenario                    																					| xtra_card_nbr |message_source_code	|
      | "CVS customer with 2 PHR dependents with both members capped - Pilot"	| 298345058     |STORE								|
 
     