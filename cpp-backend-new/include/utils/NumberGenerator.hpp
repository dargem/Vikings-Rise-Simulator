#ifndef NUMBER_GENERATOR_HPP
#define NUMBER_GENERATOR_HPP

#include <cstdint>

class NumberGenerator
{
public:
    NumberGenerator(uint64_t seed);
    /**
     * @brief creates a random uniform double between [0,1)
     * 
     * @return double 
     */
    [[nodiscard]] double getRandomDouble();
private:
    // used internally for bitshifting an int
    static uint64_t rotl(const uint64_t x, int k);

    void jump();
    // used to store internal state
    uint64_t s[2];
};

#endif