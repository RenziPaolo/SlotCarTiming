#include <stdio.h>      /* printf, scanf, puts, NULL */
#include <stdlib.h>     /* srand, rand */
#include <time.h>       /* time */
#include <pthread>		/* pthread */

void lane(void* arg){
	struct timespec rqtp, rmtp;
	rmtp.tv_sec = 0;
	rmtp.tv_nsec = 0;
	srand (time(NULL));
	bool*go = (bool*)arg[1];
	double* lapTime = (double*)arg[2];
	int index = (int) arg[3];

	while(go){
		*lapTime = (rand() % 2000 + 12000)/1000;

		rqtp.tv_sec = (int)*lapTime;
		rqtp.tv_nsec = (int)(*(lapTime)*1000-rqtp.tv_sec*1000)*1000;
		nanosleep(&rqtp,&rqtp);
	}




int main(int argc,char* argv[]){

	int numOfLanes;
	bool go = true;
	void* arg;
	double* lanesTime;
	pthread_t thread;
    const pthread_attr_t attr;
	
	if(argc == 1){
		printf("insert number of lanes:");
		scanf("%i",numOfLanes);
		lanesTime = (double*)malloc(sizeof(double)*numOfLanes);
	} else if(argc == 2) {
			double* lanesTime = (double*)malloc(sizeof(double)*(int)argv[1]);
	} else if(argc > 2){
		printf("only the number of lanes is needed!");
		printf("insert number of lanes:");
		scanf("%i",numOfLanes);
		double* lanesTime = (double*)malloc(sizeof(double)*numOfLanes);
	}
	for(int i = 0; i < numOfLanes;i++) {
		      int pthread_create(thread,
                          attr,
                          lane,
                          void * arg);

	}
	
	return 0;
}
