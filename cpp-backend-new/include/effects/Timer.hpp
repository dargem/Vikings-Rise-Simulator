#ifndef TIMER_HPP
#define TIMER_HPP

class Timer
{
public:
    Timer(double duration);
    // returns false if the timer has run out
    bool checkAndDeincrementDuration();   
private:
    double duration;
};

#endif // TIMER_HPP