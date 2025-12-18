#include "utils/NumberGenerator.hpp"
#include <cstdint>
#include <tuple>

NumberGenerator::NumberGenerator(uint64_t seed)
{
    state[0] = splitmix64(seed);
    state[1] = splitmix64(seed);
    // suppress the warning with jump calling it
    std::ignore = getRandomDouble();
}

/**
 * @brief Creates a random number in [0,1) using the xoroshiro+ algorithm
 * from https://prng.di.unimi.it/xoroshiro128plus.c read for more details
 * @return double 
 */
double NumberGenerator::getRandomDouble() 
{
	const uint64_t state0 = state[0];
	uint64_t state1 = state[1];

    // create random number using the internal state
	const uint64_t result = state0 + state1;

    // mix up the internal state again
	state1 ^= state0;
	state[0] = rotl(state0, 24) ^ state1 ^ (state1 << 16); // a, b
	state[1] = rotl(state1, 37); // c

    // return, shifting result into [0,1)
	return (result >> 11) * (1.0 / (1ULL << 53));
}

/**
 * @brief bit shifts x
 * 
 * @param value int to shift
 * @param shift bits to shift by
 * @return uint64_t 
 */
uint64_t NumberGenerator::rotl(uint64_t value, int shift) 
{
	return (value << shift) | (value >> (64 - shift));
}


/* This is the jump function for the generator. It is equivalent
   to 2^64 calls to next(); it can be used to generate 2^64
   non-overlapping subsequences for parallel computations. */

void NumberGenerator::jump() 
{
	static const std::array<uint64_t, 2> JUMP = { 0xdf900294d8f554a5, 0x170865df4b3201fc };

	uint64_t state0 = 0;
	uint64_t state1 = 0;
	for(const auto& jump_val : JUMP)
    {
		for(int bit_idx = 0; bit_idx < 64; bit_idx++) {
			if ((jump_val & (UINT64_C(1) << bit_idx)) != 0) {
				state0 ^= state[0];
				state1 ^= state[1];
			}
			std::ignore = getRandomDouble();
		}
    }

	state[0] = state0;
	state[1] = state1;
}

uint64_t NumberGenerator::splitmix64(uint64_t& seed)
{
    uint64_t result = (seed += 0x9E3779B97F4A7C15ULL);
    result = (result ^ (result >> 30)) * 0xBF58476D1CE4E5B9ULL;
    result = (result ^ (result >> 27)) * 0x94D049BB133111EBULL;
    return result ^ (result >> 31);
}