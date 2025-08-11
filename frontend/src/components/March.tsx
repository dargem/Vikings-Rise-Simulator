import React, { useState } from 'react';
import { March as MarchType, Commander, Skill } from '../types';
import {
  MarchCard,
  MarchHeader,
  MarchTitle,
  FormGroup,
  Label,
  Input,
  Select,
  Button
} from '../styled';

interface MarchProps {
  march: MarchType;
  commanders: Commander[];
  skills: Skill[];
  onUpdate: (march: MarchType) => void;
  onRemove: () => void;
  onClone: () => void;
}

export const MarchComponent: React.FC<MarchProps> = ({
  march,
  commanders,
  skills,
  onUpdate,
  onRemove,
  onClone
}) => {
  const [localMarch, setLocalMarch] = useState<MarchType>(march);

  const handleChange = (field: keyof MarchType, value: any) => {
    const updated = { ...localMarch, [field]: value };
    setLocalMarch(updated);
    onUpdate(updated);
  };

  const handleSkillChange = (index: number, value: string) => {
    const newSkills = [...localMarch.skills];
    newSkills[index] = value;
    handleChange('skills', newSkills);
  };

  const normalSkills = skills.filter(s => s.type === 'normal');
  const mountSlot1Skills = skills.filter(s => s.type === 'mountSlot1');
  const mountSlot2Skills = skills.filter(s => s.type === 'mountSlot2');

  return (
    <MarchCard>
      <MarchHeader>
        <MarchTitle isFriendly={localMarch.isFriendly}>
          {localMarch.isFriendly ? 'Friendly March' : 'Enemy March'}
        </MarchTitle>
        <div style={{ display: 'flex', gap: '5px' }}>
          <Button variant="secondary" onClick={onClone}>Clone</Button>
          <Button variant="danger" onClick={onRemove}>×</Button>
        </div>
      </MarchHeader>

      <FormGroup>
        <Label>Primary Commander</Label>
        <Select
          value={localMarch.primaryCommander}
          onChange={(e) => handleChange('primaryCommander', e.target.value)}
        >
          <option value="N/A">N/A</option>
          {commanders.map(cmd => (
            <option key={cmd.name} value={cmd.name}>{cmd.name}</option>
          ))}
        </Select>
      </FormGroup>

      <FormGroup>
        <Label>Secondary Commander</Label>
        <Select
          value={localMarch.secondaryCommander}
          onChange={(e) => handleChange('secondaryCommander', e.target.value)}
        >
          <option value="N/A">N/A</option>
          {commanders.map(cmd => (
            <option key={cmd.name} value={cmd.name}>{cmd.name}</option>
          ))}
        </Select>
      </FormGroup>

      <FormGroup>
        <Label>Attack</Label>
        <Input
          type="number"
          value={localMarch.attack}
          onChange={(e) => handleChange('attack', parseInt(e.target.value) || 0)}
        />
      </FormGroup>

      <FormGroup>
        <Label>Defense</Label>
        <Input
          type="number"
          value={localMarch.defense}
          onChange={(e) => handleChange('defense', parseInt(e.target.value) || 0)}
        />
      </FormGroup>

      <FormGroup>
        <Label>Health</Label>
        <Input
          type="number"
          value={localMarch.health}
          onChange={(e) => handleChange('health', parseInt(e.target.value) || 0)}
        />
      </FormGroup>

      <FormGroup>
        <Label>Troops</Label>
        <Input
          type="number"
          value={localMarch.troopSize}
          onChange={(e) => handleChange('troopSize', parseInt(e.target.value) || 0)}
        />
      </FormGroup>

      <FormGroup>
        <Label>Team</Label>
        <Select
          value={localMarch.isFriendly ? 'Friendly' : 'Enemy'}
          onChange={(e) => handleChange('isFriendly', e.target.value === 'Friendly')}
        >
          <option value="Friendly">Friendly</option>
          <option value="Enemy">Enemy</option>
        </Select>
      </FormGroup>

      <FormGroup>
        <Label>Type</Label>
        <Select
          value={localMarch.isRally ? 'Rally' : 'Field'}
          onChange={(e) => handleChange('isRally', e.target.value === 'Rally')}
        >
          <option value="Field">Field</option>
          <option value="Rally">Rally</option>
        </Select>
      </FormGroup>

      <div style={{ marginTop: '15px' }}>
        <Label>Skills (4)</Label>
        {[0, 1, 2, 3].map(index => (
          <FormGroup key={index}>
            <Select
              value={localMarch.skills[index] || 'N/A'}
              onChange={(e) => handleSkillChange(index, e.target.value)}
            >
              <option value="N/A">N/A</option>
              {normalSkills.map(skill => (
                <option key={skill.name} value={skill.name}>{skill.name}</option>
              ))}
            </Select>
          </FormGroup>
        ))}
      </div>

      <div style={{ marginTop: '15px' }}>
        <Label>Mount Slot 1 Skills (2)</Label>
        {[4, 5].map(index => (
          <FormGroup key={index}>
            <Select
              value={localMarch.skills[index] || 'N/A'}
              onChange={(e) => handleSkillChange(index, e.target.value)}
            >
              <option value="N/A">N/A</option>
              {mountSlot1Skills.map(skill => (
                <option key={skill.name} value={skill.name}>{skill.name}</option>
              ))}
            </Select>
          </FormGroup>
        ))}
      </div>

      <div style={{ marginTop: '15px' }}>
        <Label>Mount Slot 2 Skills (2)</Label>
        {[6, 7].map(index => (
          <FormGroup key={index}>
            <Select
              value={localMarch.skills[index] || 'N/A'}
              onChange={(e) => handleSkillChange(index, e.target.value)}
            >
              <option value="N/A">N/A</option>
              {mountSlot2Skills.map(skill => (
                <option key={skill.name} value={skill.name}>{skill.name}</option>
              ))}
            </Select>
          </FormGroup>
        ))}
      </div>
    </MarchCard>
  );
};
