#include "utils/NumberGeneratorFactory.hpp"
#include <array>

NumberGeneratorFactory::NumberGeneratorFactory(uint64_t seed)
    : master_state(seed)
{}

NumberGenerator NumberGeneratorFactory::createNumberGenerator()
{
    NumberGenerator worker = master_state;

    master_state.jump();

    return worker;
}