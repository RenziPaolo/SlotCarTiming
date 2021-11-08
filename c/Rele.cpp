#include <gpiod.h>
#include <stdio.h>
#include <unistd.h>
#include <string>
#include <cstring>
#include <fstream>
#include <iostream>

#ifndef	CONSUMER
#define	CONSUMER	"Consumer"
#endif

using namespace std;

int main(int argc, char **argv)
{
	char *chipname = "gpiochip0";

	struct gpiod_chip *chip;
	struct gpiod_line *line;
	int ret, val = 0, numberOfLanes = 0;
	ifstream sensorFile;
	string sensorRele[16];
	int* fromjava = &(val);

	sensorFile.open ("../config/settings Requiredsensors.config",ios::in);

	if (sensorFile.is_open()==0){
       cout<<"Errore nell'apertura del file: sensorFile";
       return (-1);
	}

	while (!sensorFile.eof()){
    	getline (sensorFile,sensorRele[numberOfLanes]);
		numberOfLanes++;
	}
	numberOfLanes--;

	unsigned int relePin[numberOfLanes];

	for (int i = 0; i<numberOfLanes;i++){
		relePin[i] = atoi(sensorRele[i].substr(sensorRele[i].find(','),sensorRele[i].length()).c_str());
	}

	chip = gpiod_chip_open_by_name(chipname);
	if (!chip) {
		perror("Open chip failed\n");
		return 0;
	}
	while(true){
		scanf("%i",fromjava);
		if(fromjava == 0){
			val = 1;
		}
		for (int i = 0; i<numberOfLanes;i++){
			line = gpiod_chip_get_line(chip, relePin[i]);
			if (!line) {
				perror("Get line failed\n");
				gpiod_chip_close(chip);
				return 0;
			}
		}
		for (int i = 0; i<numberOfLanes;i++){
			ret = gpiod_line_request_output(line, CONSUMER, 0);
			if (ret < 0) {
				perror("Request line as output failed\n");
				gpiod_line_release(line);
				gpiod_chip_close(chip);
				return 0;
			}
		}
		for (int i = 0; i<numberOfLanes;i++){
			ret = gpiod_line_set_value(line, val);
			if (ret < 0) {
				perror("Set line output failed\n");
				gpiod_line_release(line);
				gpiod_chip_close(chip);
				return 0;
			}
		}
	}
	return 0;
}
