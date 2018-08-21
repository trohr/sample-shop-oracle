/* Inicialni data pro 'SHOP' */
/** 
 * 
CREATE OR REPLACE TYPE Adresa_type
AS
  OBJECT
  (
    street   VARCHAR2 (64 CHAR) ,
    houseNo  VARCHAR2 (32 CHAR) ,
    town     VARCHAR2 (64 CHAR) ,
    postCode VARCHAR2 (32) ,
    district VARCHAR2 (64 CHAR) ) NOT FINAL ;
  /

CREATE OR REPLACE TYPE CompanyData_type
AS
  OBJECT
  (
    companyName VARCHAR2 (256 CHAR) ,
    vatNumber   VARCHAR2 (32) ) NOT FINAL ;
  /

CREATE OR REPLACE TYPE Country_type
AS
  OBJECT
  (
    code3 VARCHAR2 (3 CHAR) ,
    name  VARCHAR2 (256 CHAR) ,
    valid CHAR (1) ) NOT FINAL ;
  /

CREATE OR REPLACE TYPE Email_type
AS
  OBJECT
  (
    value VARCHAR2 (256 CHAR) ) NOT FINAL ;
  /

CREATE OR REPLACE TYPE PersonalData_type
AS
  OBJECT
  (
    firstName   VARCHAR2 (60 CHAR) ,
    surname     VARCHAR2 (60 CHAR) ,
    titleBefore VARCHAR2 (32) ,
    titleAfter  VARCHAR2 (32) ) NOT FINAL ;
  /

CREATE OR REPLACE TYPE Phone_type
AS
  OBJECT
  (
    value VARCHAR2 (256 CHAR) ) NOT FINAL ;
  /

CREATE TABLE SHOP_Zakaznik
  (
    id   INTEGER NOT NULL ,
    type CHAR (1) NOT NULL ,
    personal_data PersonalData_type ,
    company_data CompanyData_type ,
    invoice_address Adresa_type ,
    shipping_address Adresa_type ,
    phone Phone_type ,
    email Email_type 
    -- CHECK ( personal_data.firstName  IS NOT NULL ) ,
    -- CHECK ( personal_data.surname    IS NOT NULL ) ,
    -- CHECK ( company_data.companyName IS NOT NULL ) ,
    -- CHECK ( phone.value              IS NOT NULL ) ,
    -- CHECK ( email.value              IS NOT NULL )
  ) ;
ALTER TABLE SHOP_Zakaznik ADD CONSTRAINT SHOP_Zakaznik_PK PRIMARY KEY ( id ) ;

/
  */
BEGIN
	DELETE FROM SHOP_Zakaznik; 
	
	INSERT INTO SHOP_Zakaznik ("type", personal_data, shipping_address, phone, email) 
		VALUES ('P', PersonalData_type('Tomas', 'Rohrbacher', null, null),
			Adresa_type('Sabinova', '6', 'Brno', '61600', null),
			Phone_type('777037449'), Email_type('R15425@student.osu.cz'));

END;
/
