#include <stdio.h>      /* printf, scanf, puts, NULL */
#include <stdlib.h>     /* srand, rand */
#include <time.h>       /* time */
#include <pthread.h>		/* pthread */

void* lane(void* arg){
	struct timespec rqtp, rmtp;
	rmtp.tv_sec = 0;
	rmtp.tv_nsec = 0;
	double lapTime = 0;
	int* laneNum = (int*)arg;
	while(true){
		lapTime = ((double)rand()/RAND_MAX)*2+12;
		//printf("%i\n",*laneNum);
		rqtp.tv_sec = (int)lapTime;
		rqtp.tv_nsec = (int)(lapTime*1000-rqtp.tv_sec*1000)*1000;
		nanosleep(&rqtp,&rqtp);
		printf("%f:%i\n",lapTime,*laneNum);
		fflush(stdout);
	}

}


int main(int argc,char* argv[]){
	int numOfLanes;
	void* arg;
	pthread_t* thread;
	void** ret;
	srand (time(NULL));
	int* lanes;

	if(argc == 1){
		printf("insert number of lanes:");
		scanf("%i",numOfLanes);
		printf("%i\n",numOfLanes);

		thread = (pthread_t*)malloc(sizeof(pthread_t)*numOfLanes);
		lanes = (int*)malloc(sizeof(int)*numOfLanes);
	} else if(argc == 2) {
		numOfLanes = atoi(argv[1]);
		thread = (pthread_t*)malloc(sizeof(pthread_t)*numOfLanes);
		lanes = (int*)malloc(sizeof(int)*numOfLanes);
	} else if(argc > 2){
		printf("only the number of lanes is needed!");
		printf("insert number of lanes:");
		scanf("%i",numOfLanes);

		thread = (pthread_t*)malloc(sizeof(pthread_t)*numOfLanes);
		lanes = (int*)malloc(sizeof(int)*numOfLanes);
	}

	for(int i = 0; i < numOfLanes;i++) {
		lanes[i] = i;
		pthread_create(&thread[i], NULL, lane, (void*)&(lanes[i]));

	}
	for(int i = 0; i < numOfLanes;i++) {
		pthread_join(thread[i],ret);
	}
	
	return 0;
}
