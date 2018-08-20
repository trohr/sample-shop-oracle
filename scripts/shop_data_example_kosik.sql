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
DECLARE
	kosik_id NUMBER;
BEGIN
	DELETE FROM SHOP_Kosik; -- mela by byt cascada 'delete' 
	
	INSERT INTO SHOP_Kosik (uuid, customer_uri, created_on, last_used, valid_until) 
		VALUES ('UUID', 'session://12345cosToHonzoCosToSned', 
			current_timestamp, current_timestamp, current_timestamp + 1);
	
	kosik_id := Kosik_id_SEQ.currval;
	
	-- TODO vytvorit proceduru na vkladani do kosiku
	-- println kosik_id
	
	INSERT INTO SHOP_Kosik_Item (SHOP_Kosik_id, SHOP_Produkt_id, quantity, unit_price, line_no) 
		VALUES (kosik_id, (select id from SHOP_Produkt where code = 'BRSA01'), 1, 10.0, 1);
	INSERT INTO SHOP_Kosik_Item (SHOP_Kosik_id, SHOP_Produkt_id, quantity, unit_price, line_no) 
		VALUES (kosik_id, (select id from SHOP_Produkt where code = 'CRTA01'), 1, 20.0, 2);

END;
/
