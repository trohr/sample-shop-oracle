/* Inicialni data pro 'SHOP' */
/** 
CREATE TABLE SHOP_Produkt
  (
    id   INTEGER NOT NULL ,
    name VARCHAR2 (256 CHAR) ,
    code VARCHAR2 (32) NOT NULL ,
    caption VARCHAR2 (256 CHAR) NOT NULL ,
    specification CLOB ,
    unit_price NUMBER (18,2) ,
    image_url  VARCHAR2 (256 CHAR)
  ) ;
ALTER TABLE SHOP_Produkt ADD CONSTRAINT SHOP_Produkt_PK PRIMARY KEY ( id ) ;
ALTER TABLE SHOP_Produkt ADD CONSTRAINT SHOP_Produkt_code_UN UNIQUE ( code ) ;
  */
DELETE FROM SHOP_Produkt;
INSERT INTO SHOP_Produkt (name, code, caption, specification, unit_price)
	VALUES ('Káva Brazílie Santos, zrnková', 'BRSA01',
		'Káva Brazílie Santos, zrnková',
		'Vyvážená, bez náznaku hořkosti, s minimální kyselostí a s oříškovo-čokoládovými tóny. Vhodná i pro ty, kdo se s kvalitní kávou teprve seznamují. Dopřejte si i vy legendu mezi světovými kávami, stoprocentní arabiku, jež se rodí v zemi fotbalových géniů a samby.',
		149.0);
INSERT INTO SHOP_Produkt (name, code, caption, specification, unit_price)
	VALUES ('Káva Costa Rica Tarrazu, zrnková', 'CRTA01',
		'Káva Brazílie Santos, zrnková',
		'Ve světě si ji cení jako jedné z nejlepších káv. Svoji hedvábnou, a přesto bohatou chuť, získává na svazích vyhaslých vulkánů v kostarickém kantonu Tarrazu. Právě pro jemnost ji doporučujeme dámám a pro naprostou vyváženost těm, kdo s kvalitní kávou začínají. Nechat se svést jemnou chutí kávy, které dává sílu tropické slunce a životodárné vody Pacifiku, se ale může každý...',
		159.0);
INSERT INTO SHOP_Produkt (name, code, caption, specification, unit_price)
	VALUES ('Káva Guatemala Tres Maria, zrnková', 'GUTM01',
		'Káva Brazílie Santos, zrnková',
		'Guatemalská káva Tres Maria pochází z regionu Huehuetenango a vyniká svojí plnou, delikátně sladkou, naprosto vyváženou chutí s vinnými tóny a příjemnou kyselostí, stejně jako úchvatným aroma.',
		169.0);
