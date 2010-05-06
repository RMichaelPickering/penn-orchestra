DROP TABLE PLASMODB.TEMPEXPORTDB2;

CREATE TABLE PLASMODB.TEMPEXPORTDB2 
  (SOURCE_ID  VARCHAR(50),
   CONSTRAINT TEMPEXPORTDB2_PK PRIMARY KEY (SOURCE_ID));
   
INSERT INTO PLASMODB.TEMPEXPORTDB2
  SELECT SOURCE_ID
    FROM 
	  (SELECT source_id, ROWNUM num
	    FROM
	      (SELECT SOURCE_ID
		     FROM DOTS.GENEFEATURE G
			 ORDER BY na_feature_id DESC)
	   )
    WHERE NUM > 1000 AND NUM < 1030;	
    
COMMIT;

exit;