#! /usr/bin/awk -f
BEGIN {
	totalTCPDrop = 0;
	totalPacketDrop = 0;
	totalCBRDrop = 0;
}
{
	if( ($1 == "d" ) && ($5 == "tcp") ) {
		totalTCPDrop++;
	
	}

	if( ($1 == "d" ) && ($5 == "cbr") ) {
		totalCBRDrop++;

	}
}
END {
	printf("Total TCP Dropped = %d\n",totalTCPDrop);
	printf("Total CBR Dropped = %d\n",totalCBRDrop);
	totalPacketDrop = totalTCPDrop + totalCBRDrop;
}

