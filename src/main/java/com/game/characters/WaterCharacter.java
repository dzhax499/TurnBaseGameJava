package com.game.characters;

import com.game.skills.common.AttackSkill;
import com.game.skills.common.GuardSkill;
import com.game.skills.common.HealSkill;
import com.game.skills.elemental.IceBlastSkill;
import com.utils.Constants;

/**
 * Implementasi karakter elemen Water - Defensive Controller.
 * 
 * Water Character adalah karakter defensif dengan HP dan defense tinggi.
 * Memiliki crowd control kuat melalui Freeze effect yang membuat musuh
 * kehilangan giliran.
 * 
 * Stats (Total: 182):
 * - HP: 115
 * - Attack: 30
 * - Defense: 22
 * - Speed: 15
 * 
 * Skills:
 * 1. Basic Attack - Serangan dasar gratis
 * 2. Guard - Buff defense sementara
 * 3. Heal - Penyembuhan HP
 * 4. Ice Blast - Skill signature dengan Freeze effect
 * 
 * Elemental Matchup:
 * - Strong vs: Fire (1.5x damage)
 * - Weak vs: Earth (0.75x damage)
 * 
 * Playstyle: Defensive controller yang mengandalkan HP pool besar dan
 * Freeze untuk mengontrol tempo battle. Cocok untuk pemain yang suka
 * defensive dan strategic playstyle.
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see BaseCharacter
 * @see ElementType
 * @see IceBlastSkill
 */
public class WaterCharacter extends BaseCharacter {

    /**
     * Konstruktor untuk membuat karakter Water baru.
     * Inisialisasi dengan stats Water dari Constants:
     * HP 115, Attack 30, Defense 22, Speed 15
     * 
     * @param name Nama karakter
     */
    public WaterCharacter(String name) {
        // HP, Attack, Defense, Speed
        // Water: Balanced, Good Defense
        super(name, Constants.WATER_MAX_HP, Constants.WATER_ATTACK,
                Constants.WATER_DEFENSE, Constants.WATER_SPEED);
    }

    /**
     * Inisialisasi skill set untuk Water Character.
     * Skill set: AttackSkill, GuardSkill, HealSkill, IceBlastSkill
     */
    @Override
    protected void initializeSkills() {
        this.addSkill(new AttackSkill());
        this.addSkill(new GuardSkill());
        this.addSkill(new HealSkill());
        this.addSkill(new IceBlastSkill());
    }

    /**
     * Mengembalikan tipe elemen Water.
     * 
     * @return ElementType.WATER
     */
    @Override
    public ElementType getElementType() {
        return ElementType.WATER;
    }
}
