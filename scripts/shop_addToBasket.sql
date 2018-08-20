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
CREATE OR REPLACE PROCEDURE SHOP_Kosik_add_item (
	kosik_id INTEGER,
	produkt_id INTEGER,
	quantity INTEGER DEFAULT 1
) AS
	kosik_item_id INTEGER;
	nextLineNumber INTEGER;
	unit_price     NUMBER (18,2);
BEGIN  -- executable part starts here
	SELECT max (line_no + 1) INTO nextLineNumber FROM SHOP_Kosik_Item
	 WHERE SHOP_Kosik_id = kosik_id;
	select unit_price into unit_price from SHOP_Produkt where id = produkt_id;

	IF nextLineNumber IS NULL THEN
		nextLineNumber := 1;
	END IF;
	
	-- TODO osetrit pridavani existujiciho produktu do kosiku
	INSERT INTO SHOP_Kosik_Item (SHOP_Kosik_id, SHOP_Produkt_id, quantity, unit_price, line_no) 
		VALUES (kosik_id, produkt_id, quantity, unit_price, nextLineNumber);

END SHOP_Kosik_add_item;
/
