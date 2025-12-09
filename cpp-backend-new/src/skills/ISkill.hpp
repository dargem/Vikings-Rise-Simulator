#ifndef I_SKILL_HPP
#define I_SKILL_HPP

// forward declaration to avoid circular dependency
class Combatant;

class ISkill
{
public:
    ISkill() {}
    virtual ~ISkill() {}

    virtual void onDependent(Combatant& friendly_combatant, Combatant& enemy_combatant) const = 0;
    virtual bool operator==(const ISkill& other) const = 0;
};

#endif