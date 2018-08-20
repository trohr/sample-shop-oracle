/* Inicialni data pro 'SHOP' */
/** 
 * 
CREATE TABLE SHOP_Kosik
  (
    id           INTEGER NOT NULL ,
    uuid         VARCHAR2 (60 CHAR) ,
    customer_uri VARCHAR2 (256 CHAR) ,
    last_used    DATE ,
    created_on   DATE ,
    valid_until  DATE
  ) ;
ALTER TABLE SHOP_Kosik ADD CONSTRAINT SHOP_Kosik_PK PRIMARY KEY ( id ) ;


CREATE TABLE SHOP_Kosik_Item
  (
    SHOP_Kosik_id   INTEGER NOT NULL ,
    SHOP_Produkt_id INTEGER NOT NULL ,
    quantity        INTEGER NOT NULL ,
    unit_price      NUMBER (18,2) NOT NULL ,
    line_no         INTEGER NOT NULL
  ) ;
ALTER TABLE SHOP_Kosik_Item ADD CONSTRAINT SHOP_Kosik_Item_PK PRIMARY KEY ( SHOP_Kosik_id, SHOP_Produkt_id ) ;

CREATE SEQUENCE Kosik_id_SEQ START WITH 1 NOCACHE ORDER ;
CREATE OR REPLACE TRIGGER Kosik_id_TRG BEFORE
  INSERT ON SHOP_Kosik FOR EACH ROW BEGIN :NEW.id := Kosik_id_SEQ.NEXTVAL;
END;
/
  */

CREATE OR REPLACE PROCEDURE SHOP_Kosik_remove_line (
	p_kosik_id INTEGER,
	p_line_no INTEGER
) AS
	found_productid INTEGER;
	ex_not_found EXCEPTION;
	PRAGMA EXCEPTION_INIT (ex_not_found, -20005);
BEGIN  -- executable part starts here
	SELECT SHOP_Produkt_id INTO found_productid
		FROM SHOP_Kosik_Item WHERE SHOP_Kosik_id = p_kosik_id and line_no = p_line_no;
	IF found_productid IS NULL THEN
		RAISE ex_not_found;
	END IF;

	DELETE FROM SHOP_Kosik_Item WHERE SHOP_Kosik_id = p_kosik_id and line_no = p_line_no;
EXCEPTION
	WHEN ex_not_found THEN
		DBMS_OUTPUT.PUT_LINE('Kosik_item not found!');
		RAISE_APPLICATION_ERROR(-20005,'Kosik_item not found');
	WHEN OTHERS THEN
		NULL; -- for other exceptions do nothing
END SHOP_Kosik_remove_line;
/

