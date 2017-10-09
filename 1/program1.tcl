#Create a new Simulation Instance 
set ns [new Simulator] 
#Turn on the Trace and the animation files 
set nf [open program1.nam w] 
$ns namtrace-all $nf
set nt [open program1.tr w]
$ns trace-all $nt  
#Define the finish procedure to perform at the end of the simulation 
proc finish { } { 
	global nt nf ns 
	$ns flush-trace 
	close $nt 
	close $nf  
	exec nam program1.nam & 
	#exec awk -f 1.awk out.tr & 
	exit 0 
} 
#Create the nodes 
set n0 [$ns node] 
set n1 [$ns node] 
set n2 [$ns node] 
#Label the nodes 
$n0 label "TCP Source" 
$n1 label "Sink" 
$n2 label "UDP Source" 
#Set the color  
$ns color 1 "red" 
$ns color 2 "yellow" 
#Create the Topology 
$ns duplex-link $n0 $n1 2Mb 10ms DropTail 
$ns duplex-link $n1 $n2 700Kb 20ms DropTail 
#Attach a Queue of size N Packets between the nodes n1 n2 
$ns queue-limit $n0 $n1 10 
$ns queue-limit $n2 $n1 10
#Make the Link Orientation 
$ns duplex-link-op $n0 $n1 orient right 
$ns duplex-link-op $n1 $n2 orient right
 
set udp0 [new Agent/UDP] 
$ns attach-agent $n2 $udp0 
set cbr0 [new Application/Traffic/CBR] 
$cbr0 attach-agent $udp0 
$cbr0 set packetSize_ 500 
$cbr0 set interval_ 0.005 

set tcp0 [new Agent/TCP] 
$ns attach-agent $n0 $tcp0 
set ftp0 [new Application/FTP] 
$ftp0 attach-agent $tcp0 
$ftp0 set maxPackets_ 500
#$ns connect $tcp0 $sink0

set null0 [new Agent/Null] 
$ns attach-agent $n1 $null0 
$ns connect $udp0 $null0
 
set sink0 [new Agent/TCPSink]
$ns attach-agent $n1 $sink0 
$ns connect $tcp0 $sink0
#$udp0 set class_ 1 
#$tcp0 set class_ 2 
$ns at 0.5 "$cbr0 start" 
$ns at 0.5 "$ftp0 start" 
$ns at 4.5 "$ftp0 stop" 
$ns at 4.5 "$cbr0 stop"
$ns at 5.0 "finish" 
$ns run



