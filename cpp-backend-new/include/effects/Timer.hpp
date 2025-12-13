#ifndef TIMER_HPP
#define TIMER_HPP

class Timer
{
    public:
        Timer(short duration);
        bool checkAndDeincrementDuration();   
    private:
        short duration;
};

#endif // TIMER_HPP