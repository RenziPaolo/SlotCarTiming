#include <stdio.h>      /* printf, scanf, puts, NULL */
#include <stdlib.h>     /* srand, rand */
#include <time.h>       /* time */


int main(int argc,char* argv[]){

	double* lapTime;
	bool* go;
	struct timespec rqtp, rmtp;
	rmtp.tv_sec = 0;
	rmtp.tv_nsec = 0;
	srand (time(NULL));
	if (argc==3){
		go = (bool*)argv[1];
		lapTime = (double*)argv[2];
		while(go){
			*lapTime = (rand() % 2000 + 12000)/1000;

			rqtp.tv_sec = (int)*lapTime;
			rqtp.tv_nsec = (int)(*(lapTime)*1000-rqtp.tv_sec*1000)*1000;
			nanosleep(&rqtp,&rqtp);
		}
	}

	return 0;
}