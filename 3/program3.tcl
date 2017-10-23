set ns [new Simulator]
set tf [open program3.tr w]
$ns trace-all $tf
set nf [open program3.nam w]
$ns namtrace-all $nf

set n0 [$ns node]
$n0 color "red"
$n0 label "src0"
set n1 [$ns node]
$n1 color "red"
$n1 label "src1"
set n2 [$ns node]
$n2 color "red"
$n2 label "src2"
set n6 [$ns node]
$n6 color "red"
$n6 label "dest2"
set n4 [$ns node]
$n4 color "yellow"
$n4 label "dest3"
set n5 [$ns node]
$n5 color "blue"
$n5 label "dest1"

$ns make-lan "$n0 $n1 $n2 $n6 $n4 " 100Mb 100ms LL Queue/DropTail Mac/802_3
$ns duplex-link $n4 $n5 1Mb 1ms DropTail


set tcp1 [new Agent/TCP]
$ns attach-agent $n1 $tcp1
set ftp1 [new Application/FTP]
$ftp1 attach-agent $tcp1
$ftp1 set packetSize_ 500
$ftp1 set interval_ 0.0001
set sink0 [new Agent/TCPSink]
$ns attach-agent $n4 $sink0

$ns connect $tcp1 $sink0


set tcp0 [new Agent/TCP]
$ns attach-agent $n0 $tcp0
set ftp0 [new Application/FTP]
$ftp0 attach-agent $tcp0
$ftp0 set packetSize_ 500
$ftp0 set interval_ 0.0001
set sink5 [new Agent/TCPSink]
$ns attach-agent $n5 $sink5

$ns connect $tcp0 $sink5

set tcp2 [new Agent/TCP]
$ns attach-agent $n2 $tcp2
set ftp2 [new Application/FTP]
$ftp2 attach-agent $tcp2
$ftp2 set packetSize_ 600
$ftp2 set interval_ 0.001
set sink3 [new Agent/TCPSink]
$ns attach-agent $n6 $sink3

$ns connect $tcp2 $sink3

set file1 [open file1.tr w]
$tcp0 attach $file1
set file2 [open file2.tr w]
$tcp1 attach $file2
set file3 [open file3.tr w]
$tcp2 attach $file3


$tcp0 trace cwnd_
$tcp1 trace cwnd_
$tcp2 trace cwnd_

proc finish { } {
global ns nf tf
$ns flush-trace
close $tf
close $nf
exec nam program3.nam &
exit 0
}


$ns at 0.1 "$ftp0 start"
$ns at 5 "$ftp0 stop"
$ns at 0.5 "$ftp1 start"
$ns at 6.2 "$ftp1 stop"

$ns at 7 "$ftp0 start"
$ns at 0.2 "$ftp2 start"
$ns at 8 "$ftp2 stop"
$ns at 14 "$ftp0 stop"
$ns at 10 "$ftp2 start"
$ns at 15 "$ftp2 stop"
$ns at 16 "finish"
$ns run



























