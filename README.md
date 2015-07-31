## Introduction

This is the HiveMQ Plugin project of the [myCoffee IoT Framework](http://www.wbss.ch/mycoffee/de/index.html).

The other components of the myCoffee IoT Framework are:
* [myCoffee](https://github.com/WBSS/myCoffee)
* [myCoffeeStorm](https://github.com/WBSS/myCoffeeStorm)

## Prerequisites

* [HiveMQ](http://www.hivemq.com/)

## Installation

### HiveMQ
* [Unpack and install](http://www.hivemq.com/documentations/getting-started/)

### Plugin Jar file
* Adjust MySQL-DB settings in `dbconfig.properties` and copy the file to `C:\opt\hivemq\plugins\`
* Build jar file: `mvn package`
* Copy `myCoffeeDBPlugin-0.0.1.jar` to `[HiveMQ installation directory]\plugin\`
* Edit file `[HiveMQ installation directory]\ conf\configuration.properties`. Set: `websockets.enabled = true`

## Run
```
-------------------------------------------------------------------------

                  _    _  _              __  __   ____
                 | |  | |(_)            |  \/  | / __ \
                 | |__| | _ __   __ ___ | \  / || |  | |
                 |  __  || |\ \ / // _ \| |\/| || |  | |
                 | |  | || | \ V /|  __/| |  | || |__| |
                 |_|  |_||_|  \_/  \___||_|  |_| \___\_\

-------------------------------------------------------------------------

  HiveMQ Start Script for Windows v1.1

-------------------------------------------------------------------------

  HIVEMQ_HOME: "C:\Program Files\hivemq-2.1.0"

  JAVA_OPTS: -Djava.net.preferIPv4Stack=true

-------------------------------------------------------------------------

2015-07-31 10:58:02,275 INFO  - HiveMQ home directory: C:\Program Files\hivemq-2.1.0
2015-07-31 10:58:02,282 INFO  - Starting HiveMQ Server
2015-07-31 10:58:03,364 INFO  - Connecting
2015-07-31 10:58:03,502 INFO  - Connected
2015-07-31 10:58:03,503 INFO  - HikariCP pool HikariPool-0 is starting.
2015-07-31 10:58:03,817 WARN  - No license file found. Using free personal licensing with restrictions to 25 connections.
2015-07-31 10:58:03,901 INFO  - Activating statistics callbacks with an interval of 60 seconds
2015-07-31 10:58:03,901 INFO  - Activating $SYS topics with an interval of 60 seconds
2015-07-31 10:58:04,045 INFO  - Starting on all interfaces and port 1883
2015-07-31 10:58:04,059 INFO  - Starting with Websockets support on all interfaces and port 8000
**2015-07-31 10:58:04,072 INFO  - Loaded Plugin myCoffee HiveMQ Plugin - v0.0.1**
2015-07-31 10:58:04,073 INFO  - Started HiveMQ 2.1.0 in 1800ms
2015-07-31 10:58:04,267 INFO  - A new HiveMQ Version (2.3.1) is available. Visit http://www.hivemq.com/ for more details.
```