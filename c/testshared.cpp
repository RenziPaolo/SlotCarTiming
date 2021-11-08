#include <sys/mman.h>
#include <sys/stat.h>        
#include <fcntl.h>
#include <random>
#include <time.h>
#include <unistd.h>
#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdlib.h>
#include <cstring>
#include <errno.h>

int main(int argc, char **argv){
	int seconds, nanoseconds, writeret, sprintfret;
	double lapTime;
	struct timespec sleep;
	struct timespec* actualsleep;
	char writebuf[1];
	void* readbuf;
	
	srand (time(NULL)); //seed for random number generator
	
	printf("staring memory sharing \n");
	int javacomunication = shm_open("TimingComunication", O_CREAT, S_IRWXU | S_IRWXG); //open shared memory
	printf("memory sharing fd = %d\n",javacomunication);
	
	
	printf("entering while \n");
	while(true){
		seconds = rand() %3 + 12;
		nanoseconds = rand() %1000;
		lapTime = ((double)nanoseconds/1000)+(double)seconds;
		printf("laptime pre = %f \n",lapTime);
		sleep = {seconds,nanoseconds};
		nanosleep(&sleep, actualsleep);
		sprintfret = sprintf(writebuf,"%f", lapTime);
		printf("writebuf = %s \n", writebuf);
		printf("writeret = %d \n",writeret);
		printf("errno pre = %d \n",errno);
		writeret = write(javacomunication, writebuf, strlen(writebuf));
		printf("errno = %d \n",errno);
		printf("writeret = %d \n",writeret);
		read(javacomunication, readbuf, strlen(writebuf));
		lapTime =  *(double*)readbuf;
		printf("laptime post = %f \n",lapTime);
	}
	
  
}