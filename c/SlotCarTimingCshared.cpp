#include <gpiod.h>
#include <stdio.h>
#include <unistd.h>
#include <iostream>
#include <pthread.h>
#include <fstream>
#include <string>
#include <ctime>
#include <iostream>
#include <sstream>
#include <sys/mman.h>
#include <sys/stat.h>        
#include <fcntl.h> 
#include <cstring>

#ifndef	CONSUMER
#define	CONSUMER	"Consumer"
#endif

using namespace std;

double numPlacesD(int n){
    if(n < 0) n = (n ==INT8_MIN) ? INT8_MAX : -n;
    if(n < 10) return (double)100;
    if(n < 100) return (double)1000;
    if(n < 1000) return (double)10000;
    if(n < 10000) return (double)100000;
    if(n < 100000) return (double)1000000;
    if(n < 1000000) return (double)10000000;
    if(n < 10000000) return (double)100000000;
    return (double)1000000000;
}

float numPlacesF(int n){
    if(n < 0) n = (n ==INT8_MIN) ? INT8_MAX : -n;
    if(n < 10) return (float)100;
    if(n < 100) return (float)1000;
    if(n < 1000) return (float)10000;
    if(n < 10000) return (float)100000;
    if(n < 100000) return (float)1000000;
    if(n < 1000000) return (float)10000000;
    if(n < 10000000) return (float)100000000;
    return (float)1000000000;
}

//void send(zmq::socket_t socket, double ProvisionallapTime, int lane){}

int bulkPinSens(unsigned int pinLines[], int numberOfLanes, double interruptTime[], double lapTime[],float minLapTime)
{
	int ret;
    double interrupt;
    char *chipname = "gpiochip0";
	struct gpiod_line_event event;
	struct timespec ts = { 1000, 0 };
	struct gpiod_line_bulk linesToListen; 
	struct gpiod_line *lineReaded; 
    struct gpiod_chip *chip = gpiod_chip_open_by_name(chipname);
	ofstream logFile;
	time_t dateForLog;
	struct tm *date;
	char buffer[80], toJava[120];

	time(&dateForLog);
	date = localtime(&dateForLog);
	strftime(buffer, sizeof(buffer),"%H-%d-%m-%Y",date);
	string str(buffer);

	logFile.open("logs/logFile"+str+".txt");

	if (!chip) {
		perror("Open chip failed\n");
		return -1;
	}

	struct gpiod_line *line; 
	gpiod_line_bulk_init(&linesToListen);

	for (int i = 0; i<numberOfLanes;i++){
		line = gpiod_chip_get_line(chip, pinLines[i]);

		gpiod_line_bulk_add(&linesToListen,line);
	}
	
	ret = gpiod_line_request_bulk_rising_edge_events_flags(&linesToListen, CONSUMER,GPIOD_LINE_REQUEST_FLAG_BIAS_PULL_DOWN);
	
	if (ret >= 0) {
		ret = 0;
        while (true) {
			struct gpiod_line_bulk eventLines;
	        ret = gpiod_line_event_wait_bulk(&linesToListen, &ts, &eventLines);
		    if (ret < 0) {
	    	    perror("Wait event notification failed\n");
		        ret = -1;
		    } 
			else if (ret == 0) {
		        printf("Wait event notification timeout\n");
	        }
			else {
				for(unsigned int j = 0;j<eventLines.num_lines;j++){
					lineReaded = gpiod_line_bulk_get_line(&eventLines,j);
	            	ret = gpiod_line_event_read(lineReaded, &event);
//		            printf("Got event notification on line #%u at time %d,%d \n",gpiod_line_offset(lineReaded),event.ts.tv_sec,event.ts.tv_nsec);
					logFile << "Got event notification on line #" <<gpiod_line_offset(lineReaded) << " at time "<< event.ts.tv_sec << "," << event.ts.tv_nsec << endl;
	    	        interrupt = (double)(event.ts.tv_sec-((event.ts.tv_sec/10000)+10000)) + (((double)event.ts.tv_nsec)/numPlacesD(event.ts.tv_nsec));
                    for (int k = 0;k<numberOfLanes;k++){
                        if (pinLines[k] == gpiod_line_offset(lineReaded)){
//							cout << k << ") ";
//							cout << interruptTime[k] << ", ";
							double ProvisionallapTime = interrupt-interruptTime[k];
							if (ProvisionallapTime >= minLapTime && ProvisionallapTime< minLapTime*1000){
								if (interruptTime[k] != (float)0 || interruptTime[k]!=interrupt){
                              		lapTime[k] = ProvisionallapTime;
                             		cout << lapTime[k] << endl; 
									logFile << lapTime[k] << endl;
        							cout << "sending " << endl;
        							sprintf(toJava, "%f:%d",lapTime[k],k);
									write(1, toJava, strlen(toJava));
                           			interruptTime[k] = interrupt;
								}
							}

                        }
                        
                    }
                    
                    if (ret < 0) {
		    	        perror("Read last event notification failed\n");
		        	    ret = -1;
	            	}
				}

			}
        }
    }
	else {
	    perror("Request event notification failed\n");
	    ret = -1;
	}
	logFile.close();
	return ret;
}

int main(int argc, char **argv){
	ifstream sensorFile, minLapTimeFile;
	string sensorRele[16];
	string minLapTimeString;
	int numberOfLanes = 0;

	sensorFile.open ("config/settings Requiredsensors.config",ios::in);
	minLapTimeFile.open("config/settings minlaptime.config",ios::in);

	if (sensorFile.is_open()==0){
       perror("Errore nell'apertura del file: sensorFile");
       return (-1);
	}
	if (minLapTimeFile.is_open()==0){
       perror("Errore nell'apertura del file: minLapTimeFile");
       return (-1);
	}
    
	getline (minLapTimeFile, minLapTimeString);
	
	int intPart = atoi(minLapTimeString.substr(0,minLapTimeString.find('.')).c_str());
	int deciamlPart = atoi(minLapTimeString.substr(minLapTimeString.find('.'),minLapTimeString.length()).c_str());

	float minLapTime = intPart + (((float)deciamlPart)/numPlacesF(deciamlPart));

	while (!sensorFile.eof()){
    	getline (sensorFile,sensorRele[numberOfLanes]);
		numberOfLanes++;
	}
	numberOfLanes--;

    double interruptTime[numberOfLanes], lapTime[numberOfLanes] ;
	unsigned int sensorPin[numberOfLanes];

	for (int i = 0; i<numberOfLanes;i++){
		sensorPin[i] = atoi(sensorRele[i].substr(0,sensorRele[i].find(',')).c_str());
        interruptTime[i] = (double)0;
	}	

	int ret = bulkPinSens(sensorPin, numberOfLanes, interruptTime, lapTime, minLapTime);
	return ret;
}
