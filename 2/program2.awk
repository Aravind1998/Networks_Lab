#! /usr/bin/awk -f
BEGIN {
        totalPacketDrop = 0;        
}
{
        if ($1 == "d" ) {
                totalPacketDrop++;

        }

}
END {
        printf("Total Packet Dropped %d\n",totalPacketDrop);
        
}
