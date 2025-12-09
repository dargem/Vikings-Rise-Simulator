#ifndef TIMED_EFFECT_HPP
#define TIMED_EFFECT_HPP

class Timer
{
    public:
        Timer(short duration);
        // member functions
        bool checkAndDeincrementDuration();
    
    private:
        short duration;
};

#endif