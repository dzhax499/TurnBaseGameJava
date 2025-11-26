# 🎮 QUICK START GUIDE

## Menjalankan Battle Game

### Option 1: Interactive Mode (Recommended)
```bash
cd C:\TurnBaseGameJava
mvn clean compile
java -cp target/classes com.game.battle.BattleSimulator
```

**Alur:**
1. Pilih karakter untuk Pemain 1 (1-4)
2. Input nama
3. Pilih karakter untuk Pemain 2
4. Input nama
5. Pertarungan dimulai otomatis
6. Setiap turn:
   - Lihat status pertarungan
   - Pilih skill (1-4)
   - Atau tekan 0 untuk surrender
7. Pertarungan selesai saat ada pemenang

### Option 2: Automated Demo Mode
```bash
cd C:\TurnBaseGameJava
mvn clean compile
java -cp target/classes com.game.battle.BattleDemo
```

**Fitur:**
- Battle berjalan otomatis (Fire vs Water)
- AI memilih skill secara cerdas
- Menampilkan setiap turn secara real-time
- Analisis matchup di akhir

---

## Karakteristik Karakter

| Karakter | HP  | ATK | DEF | SPD | Elemen | Skill Special | 
|----------|-----|-----|-----|-----|--------|---------------|
| Fire     | 100 | 35⭐| 15  | 30  | 🔥     | Fireball      |
| Water    | 120⭐| 25  | 25⭐| 10  | 💧     | Ice Blast     |
| Earth    | 140⭐| 25  | 30⭐| 5   | 🌍     | Rock Throw    |
| Wind     | 90  | 30  | 10  | 50⭐| 💨     | Air Slash     |

---

## Elemental Advantage

```
Fire > Wind (1.5x damage)
Wind > Earth (1.5x damage)
Earth > Water (1.5x damage)
Water > Fire (1.5x damage)
```

**Contoh:**
- Water serangan Fire → Sangat efektif (1.5x damage) ⭐⭐
- Fire serangan Water → Kurang efektif (0.75x damage) 😢

---

## Status Effects

### Burn 🔥
- **Source**: Fireball
- **Effect**: -5 HP per turn
- **Duration**: 3 turn
- **Impact**: Damage terus berlanjut

### Freeze ❄️
- **Source**: Ice Blast
- **Effect**: Skip turn (tidak bisa bergerak)
- **Duration**: 2 turn
- **Impact**: Kehilangan kesempatan attack

---

## Game Mechanics

### Focus Points (FP)
- **Basic Attack**: 0 FP
- **Guard**: 0 FP
- **Heal**: 10 FP
- **Special Skill**: 20-30 FP
- **Regen**: +10 FP per turn (max 50)

### Defense
- Damage = Attack - Defense
- Guard skill meningkatkan defense sementara

### Dodge & Crit
- **Dodge**: Probabilitas based on speed difference (max 30%)
- **Crit**: Speed / 4.0 (max 25%)

---

## Strategic Tips

### Untuk Fire Character
✅ High attack, jadi main aggressive
✅ Jalan duluan karena speed decent
❌ Hati-hati vs Water (weak)
💡 Use Fireball untuk burn damage

### Untuk Water Character
✅ Dominan vs Fire (1.5x damage)
✅ HP tinggi dan defense bagus
❌ Speed rendah (jalan belakangan)
💡 Guard + Freeze untuk defense strategy

### Untuk Earth Character
✅ Tank supreme (high HP & defense)
✅ Dominan vs Water
❌ Speed sangat rendah (jalan paling belakangan)
❌ Attack sedang
💡 Guard heavy strategy

### Untuk Wind Character
✅ Jalan duluan karena speed super tinggi (50)
✅ Dominan vs Earth
❌ HP dan defense rendah (glasscannon)
❌ Speed advantages bisa diminimalkan
💡 Hit and run strategy

---

## Battle Flow

```
Start
  ↓
Determine Turn Order (speed)
  ↓
--- Loop ---
Display Status
  ↓
Check if can move (freeze check)
  ↓
Input Skill
  ↓
Execute Skill (damage, effects)
  ↓
Process Status Effects
  ↓
Check Victory
  ↓
End Turn / Regenerate FP
  ↓
--- Loop End ---
  ↓
Display Result
  ↓
Offer Play Again
```

---

## Output Example

```
╔════════════════════════════════════╗
║   PERTARUNGAN DIMULAI!             ║
╚════════════════════════════════════╝

⚔️  Pyro (HP: 100/100)
⚔️  vs
⚔️  Aqua (HP: 120/120)

🎲 Penentuan Giliran:
   Pyro memiliki speed lebih tinggi! Jalan duluan!

═══════════════════════════════════════════════════════════
TURN 1 - Pyro GILIRAN
═══════════════════════════════════════════════════════════
⚔️ PLAYER 1 Pyro │ [████████████████████] 100/100 HP
           │ FP: [⚡⚡⚡⚡⚡⚡⚡⚡⚡⚡] 50/50
           
🛡️  PLAYER 2 Aqua │ [████████████████████] 120/120 HP
           │ FP: [⚡⚡⚡⚡⚡⚡⚡⚡⚡⚡] 50/50
═══════════════════════════════════════════════════════════

📋 Skill tersedia untuk Pyro:
1. Basic Attack (FP Cost: 0)
2. Guard (FP Cost: 0)
3. Heal (FP Cost: 10)
4. Fireball (FP Cost: 25)

👉 Pyro, pilih skill (1-4) atau 0 untuk menyerah: 4

Pyro menembakkan bola api ke Aqua!
Kurang efektif...
Aqua menerima 14 kerusakan.
Aqua terbakar! (Burn)

⚡ Memproses Status Effects...
Aqua menerima 5 damage dari Burn!
```

---

## Compile & Run Commands

### Compile Only
```bash
mvn clean compile
```

### Run Interactive Mode
```bash
java -cp target/classes com.game.battle.BattleSimulator
```

### Run Demo Mode
```bash
java -cp target/classes com.game.battle.BattleDemo
```

### Maven Build with Exec
```bash
mvn package
```

---

## Troubleshooting

**Q: Game tidak berjalan?**
A: Pastikan sudah compile:
```bash
mvn clean compile
```

**Q: Karakter saya selalu kalah?**
A: Cek elemental advantage dan use healing strategically

**Q: Freeze terlalu OP?**
A: Defense karakter vs elemental advantage

**Q: FP habis terlalu cepat?**
A: Balance antara special skills dengan basic attack

---

## Next Features (Future)

- [ ] Multi-player over network
- [ ] Item system
- [ ] Leveling system
- [ ] Multiplayer tournaments
- [ ] GUI dengan JavaFX
- [ ] Save/Load game state
- [ ] Replay battle system

---

**Status**: ✅ Ready to Play!
