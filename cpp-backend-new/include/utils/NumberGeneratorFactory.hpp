#ifndef NUMBER_GENERATOR_FACTORY_HPP
#define NUMBER_GENERATOR_FACTORY_HPP

#include "utils/NumberGenerator.hpp"
#include <array>

class NumberGeneratorFactory
{
public:
    explicit NumberGeneratorFactory(uint64_t seed);

    NumberGenerator createNumberGenerator();
private:
    NumberGenerator master_state;
};

#endif