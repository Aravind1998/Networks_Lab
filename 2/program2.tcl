
set ns [new Simulator]

set tr [open program2.tr w]
set nm [open program2.nam w]

$ns trace-all $tr
$ns namtrace-all $nm

proc finish {} {
	global ns tr nm
	$ns flush-trace
	close $tr
	close $nm
	exec nam program2.nam &
	exit 0
}

set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]
set n5 [$ns node]


$ns color 1 "Red"
$ns color 2 "Green"
$ns color 3 "Blue"
$ns color 4 "Yellow"

$n0 label "ping 1"
$n1 label "ping 2"
$n2 label "router"
$n3 label "router"
$n4 label "ping 3"
$n5 label "ping 4"

$ns duplex-link $n0 $n2 2Mb 10ms DropTail
$ns duplex-link $n1 $n3 2Mb 10ms DropTail
$ns duplex-link $n2 $n3 2.5Mb 10ms DropTail
$ns duplex-link $n4 $n2 2.5Mb 10ms DropTail
$ns duplex-link $n5 $n3 2.5Mb 10ms DropTail


set ping1 [new Agent/Ping]
set ping2 [new Agent/Ping]
set ping3 [new Agent/Ping]
set ping4 [new Agent/Ping]

$ns attach-agent $n0 $ping1
$ns attach-agent $n1 $ping2
$ns attach-agent $n4 $ping3
$ns attach-agent $n5 $ping4

$ns connect $ping1 $ping4
$ns connect $ping2 $ping3
#$ns connect $ping1 $ping2
#$ns connect $ping4 $ping3

#makes the agent to send a ping pack after 0.01s
proc sendPing {} {
	global ns ping1 ping2 ping3 ping4
	set interval 0.01
	#returns current time
	set timeNow [$ns now]
	$ns at [expr $timeNow + $interval] "$ping1 send"
	$ns at [expr $timeNow + $interval] "$ping2 send"
	$ns at [expr $timeNow + $interval] "$ping3 send"
	$ns at [expr $timeNow + $interval] "$ping4 send"
	
	$ns at [expr $timeNow + $interval] "sendPing"
}

set seq 1
#method name recv to be overidden to display some message
Agent/Ping instproc recv {from rtt} {
	global seq
	$self instvar node_
	puts "64 bytes from$node_ id] : icmp_seq=$seq ttl=64 time=$rtt ms"
	incr seq
}

$ping1 set class_ 1
$ping2 set class_ 2
$ping3 set class_ 3
$ping4 set class_ 4

$ns at 0.01 "$ping1 send"
$ns at 0.01 "$ping2 send"
$ns at 0.01 "$ping3 send"
$ns at 0.01 "$ping4 send"

$ns at 0.01 "sendPing"

#make this link fail
$ns rtmodel-at 5.0 down $n2 $n3 
#repair the link
$ns rtmodel-at 6.0 up $n2 $n3

$ns at 10.0 "finish"
$ns run

#in lab make congestion by adding tcp and cbr and simulate the tcp drop	
