package com.game.characters;

import com.game.skills.common.AttackSkill;
import com.game.skills.common.GuardSkill;
import com.game.skills.common.HealSkill;
import com.game.skills.elemental.RockThrowSkill;
import com.utils.Constants;

/**
 * Implementasi karakter elemen Earth - Ultimate Tank.
 * 
 * Earth Character adalah tank sejati dengan HP dan defense tertinggi.
 * Trade-off: speed sangat rendah membuat karakter ini hampir selalu
 * jalan terakhir, tetapi dapat bertahan lama.
 * 
 * Stats (Total: 178):
 * - HP: 125 (tertinggi)
 * - Attack: 24
 * - Defense: 24
 * - Speed: 5 (terendah)
 * 
 * Skills:
 * 1. Basic Attack - Serangan dasar gratis
 * 2. Guard - Buff defense sementara
 * 3. Heal - Penyembuhan HP
 * 4. Rock Throw - Skill signature dengan pure damage tertinggi
 * 
 * Elemental Matchup:
 * - Strong vs: Water (1.5x damage)
 * - Weak vs: Wind (0.75x damage)
 * 
 * Playstyle: Ultimate tank yang mengandalkan HP pool besar dan defense
 * tinggi untuk outlast musuh. Rock Throw memberikan burst damage tinggi.
 * Cocok untuk pemain yang suka tank playstyle.
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see BaseCharacter
 * @see ElementType
 * @see RockThrowSkill
 */
public class EarthCharacter extends BaseCharacter {

    /**
     * Konstruktor untuk membuat karakter Earth baru.
     * Inisialisasi dengan stats Earth dari Constants:
     * HP 125, Attack 24, Defense 24, Speed 5
     * 
     * @param name Nama karakter
     */
    public EarthCharacter(String name) {
        // HP, Attack, Defense, Speed
        // Earth: Tank - High HP & Defense, Low Speed
        super(name, Constants.EARTH_MAX_HP, Constants.EARTH_ATTACK,
                Constants.EARTH_DEFENSE, Constants.EARTH_SPEED);
    }

    /**
     * Inisialisasi skill set untuk Earth Character.
     * Skill set: AttackSkill, GuardSkill, HealSkill, RockThrowSkill
     */
    @Override
    protected void initializeSkills() {
        this.addSkill(new AttackSkill());
        this.addSkill(new GuardSkill());
        this.addSkill(new HealSkill());
        this.addSkill(new RockThrowSkill());
    }

    /**
     * Mengembalikan tipe elemen Earth.
     * 
     * @return ElementType.EARTH
     */
    @Override
    public ElementType getElementType() {
        return ElementType.EARTH;
    }
}
