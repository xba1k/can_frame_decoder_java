# can_frame_decoder

## Overview

This is an example Java decoder for CAN frames broadcasted by the CANBridge via UDP. Compatible with SMA frame format.

## Installation

Will require JDK in the path. Use as follows :

```
javac FrameDecoder.java
java -cp . FrameDecoder
```

## Demo

```
java -cp . FrameDecoder 
Waiting for packets...
Thu Mar 03 19:55:08 PST 2022 192.168.1.61 BMS Name: SIConfig
Thu Mar 03 19:55:09 PST 2022 192.168.1.61 ID: chemistry Li, hw_ver 3844, capacity 66.00, sw_ver 5905
Thu Mar 03 19:55:09 PST 2022 192.168.1.61 States: stateOfCharge 85, stateOfHealth 80, stateOfChargeHighPrecision 8500
Thu Mar 03 19:55:19 PST 2022 192.168.1.61 BMS Name: SIConfig
Thu Mar 03 19:55:20 PST 2022 192.168.1.61 ID: chemistry Li, hw_ver 3844, capacity 66.00, sw_ver 5905
Thu Mar 03 19:55:20 PST 2022 192.168.1.61 States: stateOfCharge 85, stateOfHealth 80, stateOfChargeHighPrecision 8500
Thu Mar 03 19:55:30 PST 2022 192.168.1.61 BMS Name: SIConfig
Thu Mar 03 19:55:31 PST 2022 192.168.1.61 ID: chemistry Li, hw_ver 3844, capacity 66.00, sw_ver 5905
Thu Mar 03 19:55:32 PST 2022 192.168.1.61 States: stateOfCharge 85, stateOfHealth 80, stateOfChargeHighPrecision 8500
```