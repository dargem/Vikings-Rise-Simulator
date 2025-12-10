#ifndef TIMER
#define TIMER

class Timer
{
    public:
        Timer(short duration);
        bool checkAndDeincrementDuration();   
    private:
        short duration;
};

#endif