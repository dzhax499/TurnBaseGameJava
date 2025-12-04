package com.game.characters;

import com.game.skills.elemental.AirSlashSkill;
import com.game.skills.common.AttackSkill;
import com.game.skills.common.GuardSkill;
import com.game.skills.common.HealSkill;
import com.utils.Constants;

/**
 * Implementasi karakter elemen Wind - Glass Cannon Speedster.
 * 
 * Wind Character adalah speedster dengan speed tertinggi dan damage yang baik.
 * Trade-off: HP dan defense rendah membuat karakter ini sangat fragile.
 * 
 * Stats (Total: 182):
 * - HP: 100
 * - Attack: 30
 * - Defense: 12 (terendah)
 * - Speed: 40 (tertinggi)
 * 
 * Skills:
 * 1. Basic Attack - Serangan dasar gratis
 * 2. Guard - Buff defense sementara
 * 3. Heal - Penyembuhan HP
 * 4. Air Slash - Skill signature spam dengan FP cost rendah
 * 
 * Elemental Matchup:
 * - Strong vs: Earth (1.5x damage)
 * - Weak vs: Fire (0.75x damage)
 * 
 * Playstyle: Glass cannon speedster yang mengandalkan speed tinggi untuk
 * dodge dan crit. Air Slash dengan FP cost rendah memungkinkan spam skill
 * untuk pressure konsisten. Cocok untuk pemain yang suka high-risk high-reward.
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see BaseCharacter
 * @see ElementType
 * @see AirSlashSkill
 */
public class WindCharacter extends BaseCharacter {

    /**
     * Konstruktor untuk membuat karakter Wind baru.
     * Inisialisasi dengan stats Wind dari Constants:
     * HP 100, Attack 30, Defense 12, Speed 40
     * 
     * @param name Nama karakter
     */
    public WindCharacter(String name) {
        // HP, Attack, Defense, Speed
        // Wind: Speedster - High Speed, Low HP & Defense
        super(name, Constants.WIND_MAX_HP, Constants.WIND_ATTACK,
                Constants.WIND_DEFENSE, Constants.WIND_SPEED);
    }

    /**
     * Inisialisasi skill set untuk Wind Character.
     * Skill set: AttackSkill, GuardSkill, HealSkill, AirSlashSkill
     */
    @Override
    protected void initializeSkills() {
        this.addSkill(new AttackSkill());
        this.addSkill(new GuardSkill());
        this.addSkill(new HealSkill());
        this.addSkill(new AirSlashSkill());
    }

    /**
     * Mengembalikan tipe elemen Wind.
     * 
     * @return ElementType.WIND
     */
    @Override
    public ElementType getElementType() {
        return ElementType.WIND;
    }
}
