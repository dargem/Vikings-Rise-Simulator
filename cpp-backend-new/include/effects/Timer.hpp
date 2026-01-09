#ifndef TIMER_HPP
#define TIMER_HPP

class Timer
{
public:
    Timer(double duration);
    bool checkAndDeincrementDuration();   
private:
    double duration;
};

#endif // TIMER_HPP