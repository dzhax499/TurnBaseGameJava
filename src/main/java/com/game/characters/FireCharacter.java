package com.game.characters;

import com.game.skills.common.AttackSkill;
import com.game.skills.elemental.FireballSkill;
import com.game.skills.common.GuardSkill;
import com.game.skills.common.HealSkill;
import com.utils.Constants;

/**
 * Implementasi karakter elemen Fire - Aggressive Attacker.
 * 
 * Fire Character adalah karakter ofensif dengan attack power tinggi
 * dan speed balanced. Trade-off: defense rendah membuat karakter ini
 * rentan terhadap burst damage.
 * 
 * Stats (Total: 180):
 * - HP: 105
 * - Attack: 32
 * - Defense: 18
 * - Speed: 25
 * 
 * Skills:
 * 1. Basic Attack - Serangan dasar gratis
 * 2. Guard - Buff defense sementara
 * 3. Heal - Penyembuhan HP
 * 4. Fireball - Skill signature dengan Burn effect
 * 
 * Elemental Matchup:
 * - Strong vs: Wind (1.5x damage)
 * - Weak vs: Water (0.75x damage)
 * 
 * Playstyle: Aggressive attacker yang mengandalkan damage tinggi dan
 * Burn DoT untuk pressure musuh. Cocok untuk pemain yang suka offensive
 * playstyle.
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see BaseCharacter
 * @see ElementType
 * @see FireballSkill
 */
public class FireCharacter extends BaseCharacter {

    /**
     * Konstruktor untuk membuat karakter Fire baru.
     * Inisialisasi dengan stats Fire dari Constants:
     * HP 105, Attack 32, Defense 18, Speed 25
     * 
     * @param name Nama karakter
     */
    public FireCharacter(String name) {
        // HP, Attack, Defense, Speed
        // Fire: High Attack, Balanced Speed, Low Defense
        super(name, Constants.FIRE_MAX_HP, Constants.FIRE_ATTACK,
                Constants.FIRE_DEFENSE, Constants.FIRE_SPEED);
    }

    /**
     * Inisialisasi skill set untuk Fire Character.
     * Skill set: AttackSkill, GuardSkill, HealSkill, FireballSkill
     */
    @Override
    protected void initializeSkills() {
        // 3 Common Skills
        this.addSkill(new AttackSkill());
        this.addSkill(new GuardSkill());
        this.addSkill(new HealSkill());

        // 1 Unique Skill
        this.addSkill(new FireballSkill());
    }

    /**
     * Mengembalikan tipe elemen Fire.
     * 
     * @return ElementType.FIRE
     */
    @Override
    public ElementType getElementType() {
        return ElementType.FIRE;
    }
}
