#include "utils/NumberGenerator.hpp"
#include <cstdint>
#include <tuple>

NumberGenerator::NumberGenerator(uint64_t seed)
{
    s[0] = splitmix64(seed);
    s[1] = splitmix64(seed);
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
	const uint64_t s0 = s[0];
	uint64_t s1 = s[1];

    // create random number using the internal state
	const uint64_t result = s0 + s1;

    // mix up the internal state again
	s1 ^= s0;
	s[0] = rotl(s0, 24) ^ s1 ^ (s1 << 16); // a, b
	s[1] = rotl(s1, 37); // c

    // return, shifting result into [0,1)
	return (result >> 11) * (1.0 / (1ULL << 53));;
}

/**
 * @brief bit shifts x
 * 
 * @param x int to shift
 * @param k bits to shift by
 * @return uint64_t 
 */
inline uint64_t NumberGenerator::rotl(const uint64_t x, int k) 
{
	return (x << k) | (x >> (64 - k));
}


/* This is the jump function for the generator. It is equivalent
   to 2^64 calls to next(); it can be used to generate 2^64
   non-overlapping subsequences for parallel computations. */

void NumberGenerator::jump() 
{
	static const uint64_t JUMP[] = { 0xdf900294d8f554a5, 0x170865df4b3201fc };

	uint64_t s0 = 0;
	uint64_t s1 = 0;
	for(int i = 0; i < sizeof JUMP / sizeof *JUMP; i++)
    {
		for(int b = 0; b < 64; b++) {
			if (JUMP[i] & UINT64_C(1) << b) {
				s0 ^= s[0];
				s1 ^= s[1];
			}
			getRandomDouble();
		}
    }

	s[0] = s0;
	s[1] = s1;
}

uint64_t splitmix64(uint64_t& seed)
{
    uint64_t z = (seed += 0x9E3779B97F4A7C15ULL);
    z = (z ^ (z >> 30)) * 0xBF58476D1CE4E5B9ULL;
    z = (z ^ (z >> 27)) * 0x94D049BB133111EBULL;
    return z ^ (z >> 31);
}