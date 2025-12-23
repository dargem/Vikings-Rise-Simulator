#ifndef NUMBER_GENERATOR_HPP
#define NUMBER_GENERATOR_HPP

#include <array>
#include <cstdint>

// forward declaration
class NumberGeneratorFactory;

class NumberGenerator
{
public:
    // creates and seeds a generator
    explicit NumberGenerator(uint64_t seed);

    /**
     * @brief creates a random uniform double between [0,1)
     * 
     * @return double 
     */
    [[nodiscard]] double getRandomDouble();

private:
    // used internally for bitshifting an int
    static uint64_t rotl(uint64_t value, int shift);
    
    static uint64_t splitmix64(uint64_t& seed);

    // allows the generator factory to access jump
    // only a generator factory should be able to jump a number generator
    friend class NumberGeneratorFactory;

    // used to jump the state forward by 2^64 states
    // should only be used by generator factories
    void jump();

    // used to store internal state of RNG
    std::array<uint64_t, 2> state;
};

#endif