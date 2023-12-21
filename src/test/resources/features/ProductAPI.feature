#Author: Vani.Puranam@CVSHealth.com

@HealthlyExtraCareProducts
Feature: View Health & Wellness Products for ExtraCare Customers

  @Web
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when product_type_cd is H (Healthy products)"
    And I use "WEB"
    And I use my Extra Card 98168319
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 999835 |
      | 999836 |
      | 999837 |
      | 999838 |
      | 999839 |
      | 999840 |
      | 999841 |
      | 999842 |
      | 999843 |
      | 999844 |

  @Web
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when product_type_cd is G (General)"
    And I use "WEB"
    And I use my Extra Card 98168319
    And product_type_cd as "G"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 889835 |
      | 999836 |
      | 889837 |
      | 889838 |
      | 999837 |
      | 889840 |
      | 889841 |
      | 889842 |
      | 889843 |
      | 889844 |

  @Web
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when product_type_cd parameter is missing"
    And I use "WEB"
    And I use my Extra Card 98168319
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 889835 |
      | 999836 |
      | 889837 |
      | 889838 |
      | 999837 |
      | 889840 |
      | 889841 |
      | 889842 |
      | 889843 |
      | 889844 |

  @Web
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the same product is present in HealthCare SKU and General SKU columns with Same Rank"
    And I use "WEB"
    And I use my Extra Card 98168319
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 999835 |
      | 999836 |
      | 999837 |
      | 999838 |
      | 999839 |
      | 999840 |
      | 999841 |
      | 999842 |
      | 999843 |
      | 999844 |

  @Web
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the same product is present in HealthCare SKU and General SKU columns with different Rank"
    And I use "WEB"
    And I use my Extra Card 98168319
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 999835 |
      | 999836 |
      | 999837 |
      | 999838 |
      | 999839 |
      | 999840 |
      | 999841 |
      | 999842 |
      | 999843 |
      | 999844 |

  @Web
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when the same product is present in HealthCare SKU and General SKU columns with different Rank"
    And I use "WEB"
    And I use my Extra Card 98168319
    And product_type_cd as "G"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 889835 |
      | 999836 |
      | 889837 |
      | 889838 |
      | 999837 |
      | 889840 |
      | 889841 |
      | 889842 |
      | 889843 |
      | 889844 |

  @Web
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when the same product is present in HealthCare SKU and General SKU columns with Same Rank"
    And I use "WEB"
    And I use my Extra Card 98168319
    And product_type_cd as "G"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 889835 |
      | 999836 |
      | 889837 |
      | 889838 |
      | 999837 |
      | 889840 |
      | 889841 |
      | 889842 |
      | 889843 |
      | 889844 |

  @Web
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the different healthcare products are present with same Rank"
    And I use "WEB"
    And I use my Extra Card 98168320
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 999845 |
      | 999846 |
      | 999847 |
      | 999848 |
      | 999849 |


  @Web
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant healthcare and personal care products when only General products are present"
    And I use "WEB"
    And I use my Extra Card 98168321
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as empty

  @Web
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant healthcare and personal care products when no products are present"
    And I use "WEB"
    And I use my Extra Card 98168322
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as empty

  @Web
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant general products when no products are present"
    And I use "WEB"
    And I use my Extra Card 98168322
    And product_type_cd as "G"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as empty

  @Web
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when I try to retrieve healthcare and personal care products with product_type_cd = x"
    And I use "WEB"
    And I use my Extra Card 98168322
    And product_type_cd as "x"
    When I want to view Recommended SKUs
    Then I don't see my products
    And I see Error Message as "Invalid Product type code: "

  @Web
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when I try to retrieve healthcare and personal care products with product_type_cd = h"
    And I use "WEB"
    And I use my Extra Card 98168322
    And product_type_cd as "h"
    When I want to view Recommended SKUs
    Then I don't see my products
    And I see Error Message as "Invalid Product type code: "

  @Web
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when I try to retrieve healthcare and personal care products with product_type_cd = !"
    And I use "WEB"
    And I use my Extra Card 98168322
    And product_type_cd as "!"
    When I want to view Recommended SKUs
    Then I don't see my products
    And I see Error Message as "Invalid Product type code: "

  @Web
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a hot Card member, I want to see error message when I try to retrieve healthcare and personal care products"
    And I use "WEB"
    And I use my Extra Card 900058650
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I don't see my products
    And I see Error Message as "HOT XC Card"

  @Web
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Walmart Card member, I want to see error message when I try to retrieve healthcare and personal care products"
    And I use "WEB"
    And I use my Extra Card 59000000
    When I want to view Recommended SKUs
    Then I don't see my products
    And I see Error Message as "Card Not on File"


  @Mobile
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when product_type_cd is H (Healthy products)"
    And I use "MOBILE"
    And I use my Extra Card 98168319
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 999835 |
      | 999836 |
      | 999837 |
      | 999838 |
      | 999839 |
      | 999840 |
      | 999841 |
      | 999842 |
      | 999843 |
      | 999844 |

  @Mobile
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when product_type_cd is G (General)"
    And I use "MOBILE"
    And I use my Extra Card 98168319
    And product_type_cd as "G"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 889835 |
      | 999836 |
      | 889837 |
      | 889838 |
      | 999837 |
      | 889840 |
      | 889841 |
      | 889842 |
      | 889843 |
      | 889844 |

  @Mobile
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when product_type_cd parameter is missing"
    And I use "MOBILE"
    And I use my Extra Card 98168319
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 889835 |
      | 999836 |
      | 889837 |
      | 889838 |
      | 999837 |
      | 889840 |
      | 889841 |
      | 889842 |
      | 889843 |
      | 889844 |

  @Mobile
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the same product is present in HealthCare SKU and General SKU columns with Same Rank"
    And I use "MOBILE"
    And I use my Extra Card 98168319
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 999835 |
      | 999836 |
      | 999837 |
      | 999838 |
      | 999839 |
      | 999840 |
      | 999841 |
      | 999842 |
      | 999843 |
      | 999844 |

  @Mobile
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the same product is present in HealthCare SKU and General SKU columns with different Rank"
    And I use "MOBILE"
    And I use my Extra Card 98168319
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 999835 |
      | 999836 |
      | 999837 |
      | 999838 |
      | 999839 |
      | 999840 |
      | 999841 |
      | 999842 |
      | 999843 |
      | 999844 |

  @Mobile
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when the same product is present in HealthCare SKU and General SKU columns with different Rank"
    And I use "MOBILE"
    And I use my Extra Card 98168319
    And product_type_cd as "G"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 889835 |
      | 999836 |
      | 889837 |
      | 889838 |
      | 999837 |
      | 889840 |
      | 889841 |
      | 889842 |
      | 889843 |
      | 889844 |

  @Mobile
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when the same product is present in HealthCare SKU and General SKU columns with Same Rank"
    And I use "MOBILE"
    And I use my Extra Card 98168319
    And product_type_cd as "G"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 889835 |
      | 999836 |
      | 889837 |
      | 889838 |
      | 999837 |
      | 889840 |
      | 889841 |
      | 889842 |
      | 889843 |
      | 889844 |

  @Mobile
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the different healthcare products are present with same Rank"
    And I use "MOBILE"
    And I use my Extra Card 98168320
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 999845 |
      | 999846 |
      | 999847 |
      | 999848 |
      | 999849 |


  @Mobile
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant healthcare and personal care products when only General products are present"
    And I use "MOBILE"
    And I use my Extra Card 98168321
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as empty

  @Mobile
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant healthcare and personal care products when no products are present"
    And I use "MOBILE"
    And I use my Extra Card 98168322
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as empty

  @Mobile
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant general products when no products are present"
    And I use "MOBILE"
    And I use my Extra Card 98168322
    And product_type_cd as "G"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as empty

  @Mobile
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when I try to retrieve healthcare and personal care products with product_type_cd = x"
    And I use "MOBILE"
    And I use my Extra Card 98168322
    And product_type_cd as "x"
    When I want to view Recommended SKUs
    Then I don't see my products
    And I see Error Message as "Invalid Product type code: "

  @Mobile
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when I try to retrieve healthcare and personal care products with product_type_cd = h"
    And I use "MOBILE"
    And I use my Extra Card 98168322
    And product_type_cd as "h"
    When I want to view Recommended SKUs
    Then I don't see my products
    And I see Error Message as "Invalid Product type code: "

  @Mobile
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when I try to retrieve healthcare and personal care products with product_type_cd = !"
    And I use "MOBILE"
    And I use my Extra Card 98168322
    And product_type_cd as "!"
    When I want to view Recommended SKUs
    Then I don't see my products
    And I see Error Message as "Invalid Product type code: "

  @Mobile @todo
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a hot Card member, I want to see error message when I try to retrieve healthcare and personal care products"
    And I use "MOBILE"
    And I use my Extra Card 900058650
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I don't see my products
    And I see Error Message as "HOT XC Card"

  @Mobile
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Walmart Card member, I want to see error message when I try to retrieve healthcare and personal care products"
    And I use "MOBILE"
    And I use my Extra Card 59000000
    When I want to view Recommended SKUs
    Then I don't see my products
    And I see Error Message as "Card Not on File"


  @GBI
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when product_type_cd is H (Healthy products)"
    And I use "GBI"
    And I use my Extra Card 98168319
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 999835 |
      | 999836 |
      | 999837 |
      | 999838 |
      | 999839 |
      | 999840 |
      | 999841 |
      | 999842 |
      | 999843 |
      | 999844 |

  @GBI
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when product_type_cd is G (General)"
    And I use "GBI"
    And I use my Extra Card 98168319
    And product_type_cd as "G"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 889835 |
      | 999836 |
      | 889837 |
      | 889838 |
      | 999837 |
      | 889840 |
      | 889841 |
      | 889842 |
      | 889843 |
      | 889844 |

  @GBI
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when product_type_cd parameter is missing"
    And I use "GBI"
    And I use my Extra Card 98168319
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 889835 |
      | 999836 |
      | 889837 |
      | 889838 |
      | 999837 |
      | 889840 |
      | 889841 |
      | 889842 |
      | 889843 |
      | 889844 |

  @GBI
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the same product is present in HealthCare SKU and General SKU columns with Same Rank"
    And I use "GBI"
    And I use my Extra Card 98168319
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 999835 |
      | 999836 |
      | 999837 |
      | 999838 |
      | 999839 |
      | 999840 |
      | 999841 |
      | 999842 |
      | 999843 |
      | 999844 |

  @GBI
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the same product is present in HealthCare SKU and General SKU columns with different Rank"
    And I use "GBI"
    And I use my Extra Card 98168319
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 999835 |
      | 999836 |
      | 999837 |
      | 999838 |
      | 999839 |
      | 999840 |
      | 999841 |
      | 999842 |
      | 999843 |
      | 999844 |

  @GBI
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when the same product is present in HealthCare SKU and General SKU columns with different Rank"
    And I use "GBI"
    And I use my Extra Card 98168319
    And product_type_cd as "G"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 889835 |
      | 999836 |
      | 889837 |
      | 889838 |
      | 999837 |
      | 889840 |
      | 889841 |
      | 889842 |
      | 889843 |
      | 889844 |

  @GBI
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when the same product is present in HealthCare SKU and General SKU columns with Same Rank"
    And I use "GBI"
    And I use my Extra Card 98168319
    And product_type_cd as "G"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 889835 |
      | 999836 |
      | 889837 |
      | 889838 |
      | 999837 |
      | 889840 |
      | 889841 |
      | 889842 |
      | 889843 |
      | 889844 |

  @GBI
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the different healthcare products are present with same Rank"
    And I use "GBI"
    And I use my Extra Card 98168320
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as
      | 999845 |
      | 999846 |
      | 999847 |
      | 999848 |
      | 999849 |


  @GBI
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant healthcare and personal care products when only General products are present"
    And I use "GBI"
    And I use my Extra Card 98168321
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as empty

  @GBI
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant healthcare and personal care products when no products are present"
    And I use "GBI"
    And I use my Extra Card 98168322
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as empty

  @GBI
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant general products when no products are present"
    And I use "GBI"
    And I use my Extra Card 98168322
    And product_type_cd as "G"
    When I want to view Recommended SKUs
    Then I see relevant healthcare and personal care products
    And I see my products as empty

  @GBI
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when I try to retrieve healthcare and personal care products with product_type_cd = x"
    And I use "GBI"
    And I use my Extra Card 98168322
    And product_type_cd as "x"
    When I want to view Recommended SKUs
    Then I don't see my products
    And I see Error Message as "Invalid Product type code: "

  @GBI
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when I try to retrieve healthcare and personal care products with product_type_cd = h"
    And I use "GBI"
    And I use my Extra Card 98168322
    And product_type_cd as "h"
    When I want to view Recommended SKUs
    Then I don't see my products
    And I see Error Message as "Invalid Product type code: "

  @GBI
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when I try to retrieve healthcare and personal care products with product_type_cd = !"
    And I use "GBI"
    And I use my Extra Card 98168322
    And product_type_cd as "!"
    When I want to view Recommended SKUs
    Then I don't see my products
    And I see Error Message as "Invalid Product type code: "

  @GBI
  Scenario: View Recommended relevant healthcare and personal care products for ExtraCare Customers
    Given "As a hot Card member, I want to see error message when I try to retrieve healthcare and personal care products"
    And I use "GBI"
    And I use my Extra Card 900058650
    And product_type_cd as "H"
    When I want to view Recommended SKUs
    Then I don't see my products
    And I see Error Message as "HOT XC Card"

  @GBI
  Scenario: View Buy It Again products for ExtraCare Customers
    Given "As a Walmart Card member, I want to see error message when I try to retrieve healthcare and personal care products"
    And I use "GBI"
    And I use my Extra Card 59000000
    When I want to view Recommended SKUs
    Then I don't see my products
    And I see Error Message as "Card Not on File"
    
  #Buy It Again
  
  @Web
  Scenario: View Recommended relevant Buy It Again skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Buy It Again products when multiple products are present"
    And I use "WEB"
    And I use my Extra Card 98168323
    And Recommendation Type as "buyItAgain"
    When I want to view "buyItAgain" SKUs
    Then I see relevant products
    And I see my "buyItAgain" products as
      | 789830 |
      | 789831 |
      | 789832 |
      | 789833 |
      | 789834 |
      | 789835 |
      | 789836 |
      | 789837 |
      | 789838 |
      | 789839 | 
      
  @Web
  Scenario: View Recommended relevant Buy It Again skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Buy It Again products when single product is present"
    And I use "WEB"
    And I use my Extra Card 98168324
    And Recommendation Type as "buyItAgain"
    When I want to view "buyItAgain" SKUs
    Then I see relevant products
    And I see my "buyItAgain" products as
      | 789840 |
      
  @Web
  Scenario: View Recommended relevant Buy It Again skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Buy It Again products when no product is present"
    And I use "WEB"
    And I use my Extra Card 98168325
    And Recommendation Type as "buyItAgain"
    When I want to view "buyItAgain" SKUs
    Then I see relevant products
    And I see no products in response
    
  @Web
  Scenario: View Recommended relevant Buy It Again skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Buy It Again products when single product is present and without filters in request"
    And I use "WEB"
    And I use my Extra Card 98168324
    And Recommendation Type as "buyItAgainNoFilter"
    When I want to view "buyItAgain" SKUs
    Then I see relevant products
    And I see my "buyItAgain" products as
      | 789840 |
      
  @Web
  Scenario: View Recommended relevant Buy It Again skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when invalid search card type is sent in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And I use search card type as "A"
    And Recommendation Type as "buyItAgain"
    When I want to view "buyItAgain" SKUs
    Then I don't see my products
    And I see Error Message as "Invalid XC Card Type Search Criteria"
    
  @Web
  Scenario: View Recommended relevant Buy It Again skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when invalid message source code is sent in request"
    And I use "invalid"
    And I use my Extra Card 98168325
    And Recommendation Type as "buyItAgain"
    When I want to view "buyItAgain" SKUs
    Then I don't see my products
    And I see Error Message as "Invalid Message source code: "
    
  @Web
  Scenario: View Recommended relevant Buy It Again skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when invalid XC is sent in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And Recommendation Type as "buyItAgainInvalidXCCard"
    When I want to view "buyItAgain" SKUs
    Then I don't see my products
    And I see Error Message as "Invalid XC Card"
    
  @Web
  Scenario: View Recommended relevant Buy It Again skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when blank XC is sent in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And Recommendation Type as "buyItAgainBlankXCCard"
    When I want to view "buyItAgain" SKUs
    Then I don't see my products
    And I see Error Message as "Card details are expected in request"
    
  @Web
  Scenario: View Recommended relevant Buy It Again skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when invalid search card type is sent in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And I use search card type as "0004"
    And Recommendation Type as "buyItAgain"
    When I want to view "buyItAgain" SKUs
    Then I don't see my products
    And I see Error Message as "Invalid XC Card"
        
  @Web
  Scenario: View Recommended relevant Buy It Again skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when card type is missing in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And I use search card type as " "
    And Recommendation Type as "buyItAgain"
    When I want to view "buyItAgain" SKUs
    Then I don't see my products
    And I see Error Message as "Card details are expected in request" 
    
    #Recently Viewed
      
  @Web
  Scenario: View Recently Viewed skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Recently Viewed products when single product is present"
    And I use "WEB"
    And I use my Extra Card 98168326
    And Recommendation Type as "recentlyViewed"
    When I want to view "recentlyViewed" SKUs
    Then I see relevant products
    And I see my "recentlyViewed" products as
      | 689840 |
      
  @Web
  Scenario: View Recently Viewed skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Recently Viewed products when multiple skus are present and max 20 are displayed"
    And I use "WEB"
    And I use my Extra Card 98168327
    And Recommendation Type as "recentlyViewed"
    When I want to view "recentlyViewed" SKUs
    Then I see relevant products
    And I see my "recentlyViewed" products as
      | 689860 |
      | 689859 |
      | 689858 |
      | 689857 |
      | 689856 |
      | 689855 |
      | 689854 |
      | 689853 |
      | 689852 |
      | 689851 |
      | 689850 |
      | 689849 |
      | 689848 |
      | 689847 |
      | 689846 |
      | 689845 |
      | 689844 |
      | 689843 |
      | 689842 |
      | 689841 |
      
  @Web
  Scenario: View Recently Viewed skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Recently Viewed products when no product is present"
    And I use "WEB"
    And I use my Extra Card 98168325
    And Recommendation Type as "recentlyViewed"
    When I want to view "recentlyViewed" SKUs
    Then I see relevant products
    And I see no products in response
    
  @GB
  Scenario: View Recently Viewed skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Recently Viewed products when single product is present with source code GB"
    And I use "GB"
    And I use my Extra Card 98168326
    And Recommendation Type as "recentlyViewed"
    When I want to view "recentlyViewed" SKUs
    Then I see relevant products
    And I see my "recentlyViewed" products as
      | 689840 |
      
  @Mobile
  Scenario: View Recently Viewed skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Recently Viewed products when single product is present with source code M"
    And I use "MOBILE"
    And I use my Extra Card 98168326
    And Recommendation Type as "recentlyViewed"
    When I want to view "recentlyViewed" SKUs
    Then I see relevant products
    And I see my "recentlyViewed" products as
      | 689840 |
    
  @Web
  Scenario: View Recently Viewed skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when no xtra card is present"
    And I use "WEB"
    And I use my Extra Card 528941574
    And Recommendation Type as "recentlyViewed"
    When I want to view "recentlyViewed" SKUs
    Then I don't see my products
    And I see Error Message as "Card Not on File" 
    
    @Web
  Scenario: View Recently Viewed skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when invalid XC is sent in request"
    And I use "WEB"
    And I use my Extra Card 98168326
    And Recommendation Type as "recentlyViewedInvalidXC"
    When I want to view "recentlyViewed" SKUs
    Then I don't see my products
    And I see Error Message as "Invalid XC Card"
    
  @Web
  Scenario: View Recently Viewed skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when blank XC is sent in request"
    And I use "WEB"
    And I use my Extra Card 98168326
    And Recommendation Type as "recentlyViewedBlankXC"
    When I want to view "recentlyViewed" SKUs
    Then I don't see my products
    And I see Error Message as "Card details are expected in request"
    
  @Web
  Scenario: View Recently Viewed skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when invalid message source code is sent in request"
    And I use "invalid"
    And I use my Extra Card 98168325
    And Recommendation Type as "recentlyViewed"
    When I want to view "recentlyViewed" SKUs
    Then I don't see my products
    And I see Error Message as "Invalid Message source code: "
    
  @Web
  Scenario: View Recently Viewed skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when Hot XC card is sent in request"
    And I use "WEB"
    And I use my Extra Card 900058650
    And Recommendation Type as "recentlyViewed"
    When I want to view "recentlyViewed" SKUs
    Then I don't see my products
    And I see Error Message as "HOT XC Card"
    
    @Web
  Scenario: View Recently Viewed skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when invalid search card type is sent in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And I use search card type as "A"
    And Recommendation Type as "recentlyViewed"
    When I want to view "recentlyViewed" SKUs
    Then I don't see my products
    And I see Error Message as "Invalid XC Card Type Search Criteria"
    
  @Web
  Scenario: View Recently Viewed skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when invalid search card type is sent in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And I use search card type as "0004"
    And Recommendation Type as "recentlyViewed"
    When I want to view "recentlyViewed" SKUs
    Then I don't see my products
    And I see Error Message as "Invalid XC Card"
        
  @Web
  Scenario: View Recently Viewed skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when card type is missing in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And I use search card type as " "
    And Recommendation Type as "recentlyViewed"
    When I want to view "recentlyViewed" SKUs
    Then I don't see my products
    And I see Error Message as "Card details are expected in request" 
    
    #Frequently Bought Together
    
  @Web
  Scenario: View Frequently Bought Together skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Frequently Bought Together skus when single product is present"
    And I use "WEB"
    And I use my Extra Card 98168325
    And I use Sku Number 105712
    And Recommendation Type as "frequentlyBoughtTogether"
    When I want to view "frequentlyBoughtTogether" SKUs
    Then I see relevant products
    And I see my "frequentlyBoughtTogether" products as
      | 123456 | 
      
  @Web
  Scenario: View Frequently Bought Together skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Frequently Bought Together skus when multiple products are present"
    And I use "WEB"
    And I use my Extra Card 98168325
    And I use Sku Number 105711
    And Recommendation Type as "frequentlyBoughtTogether"
    When I want to view "frequentlyBoughtTogether" SKUs
    Then I see relevant products
    And I see my "frequentlyBoughtTogether" products as
      | 123451 |
      | 123452 |
      | 123453 |
      | 123454 |
      | 123455 |
      
  @Web
  Scenario: View Frequently Bought Together skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Frequently Bought Together skus when no product is present"
    And I use "WEB"
    And I use my Extra Card 98168325
    And I use Sku Number 105710
    And Recommendation Type as "frequentlyBoughtTogether"
    When I want to view "frequentlyBoughtTogether" SKUs
    Then I see relevant products
    And I see no products in response
    
  @Web
  Scenario: View Frequently Bought Together skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when card type is missing in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And I use search card type as " "
    And I use Sku Number 105712
    And Recommendation Type as "frequentlyBoughtTogether"
    When I want to view "frequentlyBoughtTogether" SKUs
    Then I see relevant products
    And I see my "frequentlyBoughtTogether" products as
      | 123456 | 
      
  @Web
  Scenario: View Frequently Bought Together skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when blank sku is sent in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And I use Sku Number 105712
    And Recommendation Type as "frequentlyBoughtTogetherBlankSKU"
    When I want to view "frequentlyBoughtTogether" SKUs
    Then I don't see my products
    And I see Error Message as "Sku number is expected in request"
    
  @Web
  Scenario: View Frequently Bought Together skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when invalid sku is sent in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And I use Sku Number 105712
    And Recommendation Type as "frequentlyBoughtTogetherinvalidSKU"
    When I want to view "frequentlyBoughtTogether" SKUs
    Then I don't see my products
    
  @Web
  Scenario: View Frequently Bought Together skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when blank XC is sent in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And I use Sku Number 105712
    And Recommendation Type as "frequentlyBoughtTogetherBlankXcNbr"
    When I want to view "frequentlyBoughtTogether" SKUs
    Then I see relevant products
    And I see my "frequentlyBoughtTogether" products as
      | 123456 | 
      
  @Web
  Scenario: View Frequently Bought Together skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when invalid XC is sent in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And I use Sku Number 105712
    And Recommendation Type as "frequentlyBoughtTogetherInvalidXcNbr"
    When I want to view "frequentlyBoughtTogether" SKUs
    Then I don't see my products
    And I see Error Message as "Invalid XC Card"
      
    #Affinity
    
  @Web
  Scenario: View Affinity skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Affinity skus when single product is present"
    And I use "WEB"
    And I use my Extra Card 98168328
    And Recommendation Type as "affinity"
    When I want to view "affinity" SKUs
    Then I see relevant products
    And I see my "affinity" products as
      | 599844 | 
      
  @Web
  Scenario: View Affinity skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Affinity skus when multiple products are present"
    And I use "WEB"
    And I use my Extra Card 98168329
    And Recommendation Type as "affinity"
    When I want to view "affinity" SKUs
    Then I see relevant products
    And I see my "affinity" products as
      | 599845 |
      | 599846 |
      | 599847 |
      | 599848 |
      | 599849 |
      
  @Web
  Scenario: View Affinity skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when XC is missing in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And Recommendation Type as "affinitymissingXC"
    When I want to view "affinity" SKUs
    Then I don't see my products
    And I see Error Message as "Card details are expected in request"
    
  @Web
  Scenario: View Affinity skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when XC is blank in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And Recommendation Type as "affinityblankXC"
    When I want to view "affinity" SKUs
    Then I don't see my products
    And I see Error Message as "Card details are expected in request"
    
  @Web
  Scenario: View Affinity skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when XC is invalid in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And Recommendation Type as "affinityinvalidXC"
    When I want to view "affinity" SKUs
    Then I don't see my products
    And I see Error Message as "Invalid XC Card"
    
  @Web
  Scenario: View Affinity skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when HOT XC is sent in request"
    And I use "WEB"
    And I use my Extra Card 900058650
    And Recommendation Type as "affinityHOTXCandNotPresent"
    When I want to view "affinity" SKUs
    Then I don't see my products
    And I see Error Message as "HOT XC Card"
    
  @Web
  Scenario: View Affinity skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when card type is missing in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And Recommendation Type as "cardTypeMissing"
    When I want to view "affinity" SKUs
    Then I don't see my products
    And I see Error Message as "Card details are expected in request"
    
  @Web
  Scenario: View Affinity skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when card type is blank in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And Recommendation Type as "cardTypeBlank"
    When I want to view "affinity" SKUs
    Then I don't see my products
    And I see Error Message as "Card details are expected in request"
    
  @Web
  Scenario: View Affinity skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when card type is invalid in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And Recommendation Type as "cardTypeInvalid"
    When I want to view "affinity" SKUs
    Then I don't see my products
    And I see Error Message as "Invalid XC Card Type Search Criteria"
    
  @Web
  Scenario: View Affinity skus for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when card type is invalid in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And I use search card type as "0006"
    And Recommendation Type as "affinity"
    When I want to view "affinity" SKUs
    Then I don't see my products
    And I see Error Message as "Invalid XC Card"


#HTTP.POST recently viewed products - Resource Details

  @Web
  Scenario: Post recently viewed products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to Post  recently viewed products when single product is present"
    And I use "WEB"
    And I use my Extra Card 98168330
    When I want to post "single" SKU
    Then I see relevant status code
    And Recommendation Type as "recentlyViewed"
    When I want to view "recentlyViewed" SKUs
    Then I see relevant products
    And I see my "recentlyViewed" products as
       | 12361 |

       
  @Web
  Scenario: Post recently viewed products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to Post  recently viewed products when multiple products are present"
    And I use "WEB"
    And I use my Extra Card 98168331
    When I want to post "multiple" SKU
    Then I see relevant status code
    And Recommendation Type as "recentlyViewed"
    When I want to view "recentlyViewed" SKUs
    Then I see relevant products
    And I see my "recentlyViewed" products as
       | 12362 |
       | 12363 |
       | 12364 |
       
  @Web
  Scenario: Post recently viewed products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when dateViewed is blank"
    And I use "WEB"
    And I use my Extra Card 98168325
    When I want to post "blankdateViewed" SKU
    Then I don't see my products
    And I see Error Message as "dateViewed is expected in request"
    
  @Web
  Scenario: Post recently viewed products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when skuNumber is blank"
    And I use "WEB"
    And I use my Extra Card 98168325
    When I want to post "blankSKUNumber" SKU
    Then I don't see my products
    And I see Error Message as "Invalid SKU Nbr"
    
  @Web
  Scenario: Post recently viewed products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when HOT XC is used"
    And I use "WEB"
    And I use my Extra Card 900058650
    When I want to post "single" SKU
    Then I don't see my products
    And I see Error Message as "HOT XC Card"
    
  @Web
  Scenario: Post recently viewed products for ExtraCare Customers
    Given "As a Rx.COM ExtraCare Customers I want to see error message when card type is invalid in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And I use search card type as "0006"
    When I want to post "single" SKU
    Then I don't see my products
    And I see Error Message as "Invalid XC Card"
    
   #TopSellingByStore
    
  @Web
  Scenario: View Top Selling skus
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Top Selling skus when single SKU is present"
    And I use "WEB"
    And Recommendation Type as "topSellingSingle"
    When I want to view "topSelling" SKUs
    Then I see relevant products
    And I see my "topSellingByStore" products as
      | 4001 | 
      
  @Web
  Scenario: View Top Selling skus
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Top Selling skus when multiple SKUs are present"
    And I use "WEB"
    And Recommendation Type as "topSellingMultiple"
    When I want to view "topSelling" SKUs
    Then I see relevant products
    And I see my "topSellingByStore" products as
      | 4010 | 
      | 4011 |
      | 4012 |
      | 4013 |
      | 4014 |
      
  @Web
  Scenario: View Top Selling skus
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Top Selling skus when no SKU is present"
    And I use "WEB"
    And Recommendation Type as "topSellingnoSKU"
    When I want to view "topSelling" SKUs
    Then I see relevant products
    
  @Web
  Scenario: View Top Selling skus
    Given "As a Rx.COM ExtraCare Customers I want to see error message when SKU is blank in request"
    And I use "WEB"
    And Recommendation Type as "topSellingBlankStoreNbr"
    When I want to view "topSelling" SKUs
    Then I don't see my products
    And I see Error Message as "Store number is expected in request"
    
  @Mobile
  Scenario: View Top Selling skus
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Top Selling skus when single SKU is present"
    And I use "MOBILE"
    And Recommendation Type as "topSellingSingle"
    When I want to view "topSelling" SKUs
    Then I see relevant products
    And I see my "topSellingByStore" products as
      | 4001 | 
      
  @GB
  Scenario: View Top Selling skus
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Top Selling skus when single SKU is present"
    And I use "GB"
    And Recommendation Type as "topSellingSingle"
    When I want to view "topSelling" SKUs
    Then I see relevant products
    And I see my "topSellingByStore" products as
      | 4001 | 
      
  @Web
  Scenario: View Top Selling skus
    Given "As a Rx.COM ExtraCare Customers I want to see error message when invalid source code is sent in request"
    And I use "invalid"
    And Recommendation Type as "topSellingBlankStoreNbr"
    When I want to view "topSelling" SKUs
    Then I don't see my products
    And I see Error Message as "Invalid Message source code: "
    
    #CouponRecommendations
    
  @Web
  Scenario: View Coupon Recommendations
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Coupon Recommended skus for validating YES"
    And I use "WEB"
    And I use my Extra Card 98168332
    And Recommendation Type as "validating"
    When I want to view "couponRecommendations" SKUs
    Then I see relevant products
    And I see my "couponRecommendations" products as
      | 101012 |
      | 101013 |
      | 101011 |
      | 101014 |
      | 101015 |
      | 101016 |
      | 101017 |
      
  @Web
  Scenario: View Coupon Recommendations
    Given "As a Rx.COM ExtraCare Customers I want to retrieve relevant Coupon Recommended skus for validating NO"
    And I use "WEB"
    And I use my Extra Card 98168333
    And Recommendation Type as "validating"
    When I want to view "couponRecommendations" SKUs
    Then I see relevant products
    And I see my "couponRecommendations" products as
      | 101011 |
      | 101012 |
      | 101013 |
      | 101014 |
      | 101015 |
      | 101016 |
      | 101017 |
    
  @Web
  Scenario: View Coupon Recommendations
    Given "As a Rx.COM ExtraCare Customers I want to see error message when non existing XC number is sent in request"
    And I use "WEB"
    And I use my Extra Card 21212121
    And Recommendation Type as "nonExistingXCCard"
    When I want to view "couponRecommendations" SKUs
    Then I don't see my products
    And I see Error Message as "Card Not on File"
    
  @Web
  Scenario: View Coupon Recommendations
    Given "As a Rx.COM ExtraCare Customers I want to see error message when non existing XC number is sent in request"
    And I use "WEB"
    And I use my Extra Card 21212121
    And Recommendation Type as "invalidXC"
    When I want to view "couponRecommendations" SKUs
    Then I don't see my products
    And I see Error Message as "Invalid XC Card"
    
  @Web
  Scenario: View Coupon Recommendations
    Given "As a Rx.COM ExtraCare Customers I want to see error message when HOT XC number is sent in request"
    And I use "WEB"
    And I use my Extra Card 900058650
    And Recommendation Type as "hotXC"
    When I want to view "couponRecommendations" SKUs
    Then I don't see my products
    And I see Error Message as "HOT XC Card"
    
  @Web
  Scenario: View Coupon Recommendations
    Given "As a Rx.COM ExtraCare Customers I want to see error message when invalid card type is sent in request"
    And I use "WEB"
    And I use my Extra Card 21212121
    And Recommendation Type as "invalidCardType"
    When I want to view "couponRecommendations" SKUs
    Then I don't see my products
    And I see Error Message as "Invalid XC Card"
    
  @Web
  Scenario: View Coupon Recommendations
    Given "As a Rx.COM ExtraCare Customers I want to see error message when no Xtra card is sent in request"
    And I use "WEB"
    And Recommendation Type as "blankXtraCard"
    When I want to view "couponRecommendations" SKUs
    Then I don't see my products
    And I see Error Message as "Card details are expected in request"
    
  @Web
  Scenario: View Coupon Recommendations
    Given "As a Rx.COM ExtraCare Customers I want to see error message when no coupon is sent in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And Recommendation Type as "blankCoupon"
    When I want to view "couponRecommendations" SKUs
    Then I don't see my products
    And I see Error Message as "Cpn details not present"
    
  @Web
  Scenario: View Coupon Recommendations
    Given "As a Rx.COM ExtraCare Customers I want to see error message when no campaign is sent in request"
    And I use "WEB"
    And I use my Extra Card 98168325
    And Recommendation Type as "blankCampaign"
    When I want to view "couponRecommendations" SKUs
    Then I don't see my products
    And I see Error Message as "Cpn details not present"


      