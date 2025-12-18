#ifndef NUMBER_GENERATOR_HPP
#define NUMBER_GENERATOR_HPP

#include <array>
#include <cstdint>

class NumberGenerator
{
public:
    explicit NumberGenerator(uint64_t seed);
    /**
     * @brief creates a random uniform double between [0,1)
     * 
     * @return double 
     */
    [[nodiscard]] double getRandomDouble();
private:
    // used internally for bitshifting an int
    static uint64_t rotl(const uint64_t value, int shift);
    
    static uint64_t splitmix64(uint64_t& seed);

    void jump();
    // used to store internal state of RNG
    std::array<uint64_t, 2> state;
};

#endif