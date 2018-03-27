DROP procedure IF EXISTS `shard-client-tbl`;

DELIMITER $$


CREATE  PROCEDURE `shard-client-tbl`()
BEGIN
	DECLARE start_time DATE;
	DECLARE end_time DATE;
	DECLARE long_start_time BIGINT;
	DECLARE long_end_time BIGINT;
    DECLARE account_id BIGINT DEFAULT 0;
    DECLARE shard_mod BIGINT DEFAULT 0;
    DECLARE str_shard_mod VARCHAR(10);
	DECLARE existing INT DEFAULT 1;
	DECLARE loop_num INT;
    DECLARE account_cursor CURSOR FOR
		SELECT idNMSAccount FROM NMSAccount WHERE idNMSAccount != 0 AND accountStatus IN(1,2);

	DECLARE CONTINUE HANDLER FOR NOT FOUND SET existing = 0;

	
	
	SELECT CONCAT('start copy Client station data to') AS '';

	OPEN account_cursor;
    process_each_account: LOOP
		FETCH account_cursor INTO account_id;
		IF existing = 0 THEN
			CLOSE account_cursor;
            LEAVE process_each_account;
        END IF;
        SET shard_mod = (account_id MOD 5)+1;
        SET str_shard_mod = CONCAT('Clnt_0',shard_mod);
		SET start_time = str_to_date('2017-10-01','%Y-%m-%d');
		SET end_time = str_to_date('2017-11-01','%Y-%m-%d');
		SET loop_num = 1;
		WHILE loop_num <= 3 DO
		    SET long_start_time = UNIX_TIMESTAMP(start_time)*1000;
		    SET long_end_time = UNIX_TIMESTAMP(end_time)*1000;
			SET @sql = CONCAT('INSERT INTO ', str_shard_mod,
        		' (idClientStationMacAddr,apMacAddr,BSSID,SSID,idNMSAccount,radioProtocol,rfBandType,RSSI,clientStationIPv4,wlanName,idWLAN,radioSlotNum,vapName,status,statusTime,cause,archived,vendorName,isN,aPt,externalId,clientStationName) ',
                ' SELECT idClientStationMacAddr,apMacAddr,BSSID,SSID,idNMSAccount,radioProtocol,rfBandType,RSSI,clientStationIPv4,wlanName,idWLAN,radioSlotNum,vapName,status,statusTime,cause,archived,vendorName,isN,aPt,externalId,clientStationName ',
                ' FROM `Test-Clnt` WHERE idNMSAccount=', account_id ,' AND statusTime>=',long_start_time,' AND statusTime<', long_end_time);
                
			SELECT CONCAT(' execute sql : ', @sql) AS '';
			
			PREPARE pre_stmt_insert FROM @sql;
			
			EXECUTE pre_stmt_insert;
			
			DEALLOCATE PREPARE pre_stmt_insert;
			
			SET start_time =  DATE_ADD(start_time,INTERVAL 1 MONTH);
			SET end_time =  DATE_ADD(start_time,INTERVAL 1 MONTH);
			SET loop_num = loop_num+1;
		END WHILE;
		
    END LOOP process_each_account;

	 
    SELECT 'Done' AS '';

END$$
DELIMITER ;
