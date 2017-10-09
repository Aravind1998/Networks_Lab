set ns [new Simulator]

set tr [open program1.tr w]
set nf [open program1.nam w]

$ns trace-all $tr
$ns namtrace-all $nf

proc finish { } {
	global ns tr nf
	$ns flush-trace
	close $tr
	close $nf
	exec nam program1.nam &
	exit 0
}

set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]

$ns color 1 "Red"
$ns color 2 "Blue"

$n0 label "TCP Source"
$n1 label "UDP Source"
$n2 label "R1"
$n3 label "TCP Sink/Null"

$ns duplex-link $n0 $n2 2Mb 10ms DropTail
$ns duplex-link $n1 $n2 2Mb 10ms DropTail
$ns duplex-link $n2 $n3 3Mb 20ms DropTail

$ns queue-limit $n2 $n3 10

$ns duplex-link-op $n0 $n2 orient right-down
$ns duplex-link-op $n1 $n2 orient right-up
$ns duplex-link-op $n2 $n3 orient right

set tcp0 [new Agent/TCP]
$ns attach-agent $n0 $tcp0

set ftp0 [new Application/FTP]
$ftp0 attach-agent $tcp0

set sink3 [new Agent/TCPSink]
$ns attach-agent $n3 $sink3
$ns connect $tcp0 $sink3

set udp1 [new Agent/UDP]
$ns attach-agent $n1 $udp1

set cbr1 [new Application/Traffic/CBR]
$cbr1 attach-agent $udp1

set null3 [new Agent/Null]
$ns attach-agent $n3 $null3

$ns connect $udp1 $null3

$ftp0 set packetSize_ 1000
$cbr1 set packetSize_ 500
$cbr1 set interval_ 0.002
$tcp0 set class_ 1
$udp1 set class_ 2

$ns at 1.0 "$cbr1 start"
$ns at 2.0 "$ftp0 start"
$ns at 8.0 "$ftp0 stop"
$ns at 9.0 "$cbr1 stop"

$ns at 10.0 "finish"
$ns run
