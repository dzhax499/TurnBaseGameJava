# 🎮 TURN-BASED BATTLE SYSTEM - Documentation

## Overview
Sistem battle lengkap untuk turn-based game dengan fitur:
- **1 vs 1 Battle** - Dua pemain berbeda bermain bergantian
- **Elemental Advantage** - Fire > Wind > Earth > Water > Fire (1.5x damage multiplier)
- **Status Effects** - Burn (damage per turn), Freeze (skip turn), dll
- **Skill System** - 4 skill per karakter: Basic Attack, Guard, Heal, Special Elemental Skill
- **Focus Points (FP)** - Resource untuk menggunakan skill khusus
- **Speed-based Turn Order** - Karakter dengan speed tinggi jalan duluan
- **Dodge & Critical Hit** - Mekanik pertarungan dinamis
- **Live Battle Log** - Menampilkan setiap aksi pertarungan

---

## Struktur Battle System

### 1. **BattleState.java** (Enum)
Merepresentasikan state pertarungan:
```
WAITING       → Menunggu pertarungan dimulai
PLAYER1_TURN  → Giliran pemain 1
PLAYER2_TURN  → Giliran pemain 2
FINISHED      → Pertarungan selesai
```

### 2. **BattleAction.java** (Class)
Merekam setiap aksi dalam pertarungan:
- Siapa yang melakukan aksi
- Jenis aksi (SKILL, SURRENDER, WAIT)
- Target
- Deskripsi detail
- Damage/Healing yang dihasilkan

Digunakan untuk logging dan replay battle.

### 3. **BattleLog.java** (Class)
Menyimpan dan menampilkan log pertarungan:
- `addAction()` - Tambah aksi ke log
- `displayLastAction()` - Tampilkan aksi terakhir
- `displayFullLog()` - Tampilkan semua aksi
- Maksimal 100 aksi untuk mencegah memory leak

### 4. **Battle.java** (Main Class)
Inti dari sistem pertarungan:

#### Inisialisasi:
```java
Battle battle = new Battle(player1, player2);
battle.start(); // Mulai pertarungan
```

#### Fitur Utama:

**a) Turn Order Determination**
```java
determineTurnOrder()
```
- Berdasarkan speed stat
- Jika speed sama, gunakan coin flip (random)
- Pemain dengan speed tinggi jalan duluan

**b) Status Display**
```java
displayStatus() // Tampilkan HP bar, FP bar, active effects
```

**c) Skill Execution**
```java
executePlayerAction(int skillIndex)
```
- Validasi FP cukup
- Eksekusi skill
- Update log
- Proses status effects
- Return true jika berhasil

**d) Status Effects Processing**
```java
processStatusEffects()
```
- Setiap turn, process active effects (Burn, Freeze)
- Kurangi durasi effect
- Remove effect jika durasi habis

**e) Turn Management**
```java
endTurn() // Pindah ke giliran berikutnya
regenerateFocusPoints() // Regenerasi 10 FP per turn
```

**f) Victory Conditions**
```java
isBattleFinished()  // Cek apakah pertarungan sudah selesai
getWinner()         // Dapatkan pemenang
```
Pertarungan selesai jika:
- HP pemain mencapai 0
- Pemain menyerah (surrender)

---

## Cara Menggunakan

### 1. **Interactive Mode** (BattleSimulator.java)
Pemain 1 dan Pemain 2 memilih karakter dan skill secara interaktif:

```bash
java -cp target/classes com.game.battle.BattleSimulator
```

Flow:
1. Pilih karakter (Fire, Water, Earth, Wind)
2. Input nama karakter
3. Lihat status pertarungan
4. Pilih skill (1-4) atau 0 untuk surrender
5. Giliran berikutnya otomatis
6. Pertarungan selesai ketika ada pemenang

### 2. **Demo Mode** (BattleDemo.java)
Menjalankan battle otomatis untuk melihat sistem bekerja:

```bash
java -cp target/classes com.game.battle.BattleDemo
```

Features:
- AI memilih skill otomatis
  - Jika HP < 40%, gunakan Heal
  - Jika FP cukup, gunakan skill special
  - Kalau tidak, gunakan basic attack
- Menampilkan hasil dan analisis matchup

---

## Karakteristik Karakter

### Fire Character
- **HP**: 100 (Medium)
- **Attack**: 35 (High) ⭐
- **Defense**: 15 (Low)
- **Speed**: 30 (Medium)
- **Element**: Fire
- **Special Skill**: Fireball (deals extra damage)
- **Advantage**: Strong vs Wind
- **Weakness**: Weak vs Water

### Water Character
- **HP**: 120 (High) ⭐
- **Attack**: 25 (Medium-Low)
- **Defense**: 25 (Good)
- **Speed**: 10 (Low)
- **Element**: Water
- **Special Skill**: Ice Blast (freeze + damage)
- **Advantage**: Strong vs Fire ⭐
- **Weakness**: Weak vs Earth

### Earth Character
- **HP**: 140 (Very High) ⭐
- **Attack**: 25 (Medium-Low)
- **Defense**: 30 (Very High) ⭐
- **Speed**: 5 (Very Low)
- **Element**: Earth
- **Special Skill**: Rock Throw
- **Advantage**: Strong vs Water
- **Weakness**: Weak vs Wind

### Wind Character
- **HP**: 90 (Low)
- **Attack**: 30 (High)
- **Defense**: 10 (Very Low)
- **Speed**: 50 (Very High) ⭐
- **Element**: Wind
- **Special Skill**: Air Slash
- **Advantage**: Strong vs Earth
- **Weakness**: Weak vs Fire

---

## Skill System

Setiap karakter memiliki 4 skill:

### 1. **Basic Attack** (Cost: 0 FP)
- Damage = Character's Attack Power
- Dengan bonus elemental advantage

### 2. **Guard** (Cost: 0 FP)
- Meningkatkan defense sementara
- Mengurangi damage yang diterima

### 3. **Heal** (Cost: 10 FP)
- Heal Amount = Attack Power × 1.5
- FP otomatis dikurangi saat skill digunakan

### 4. **Elemental Special Skill** (Cost: 20-30 FP)
- Damage tinggi dengan bonus elemental
- Mungkin menambahkan status effect:
  - **Fireball**: Burn effect (damage per turn)
  - **Ice Blast**: Freeze effect (skip next turn)
  - **Rock Throw**: Stun effect
  - **Air Slash**: Speed boost

---

## Status Effects

### Burn
- **Source**: Fireball (Fire special skill)
- **Effect**: Damage 5 HP per turn
- **Duration**: 3 turn
- **Impact**: Damage continue even if target frozen

### Freeze
- **Source**: Ice Blast (Water special skill)
- **Effect**: Skip next turn (cannot move)
- **Duration**: 2 turn
- **Impact**: Pemain tidak bisa memilih skill, auto-skip

---

## Elemental Advantage System

```
       Fire
       ↙  ↖
    Wind    Water
       ↖  ↙
      Earth
```

**Damage Multipliers:**
- **Advantage** (attacker element strong vs defender): **1.5x damage**
- **Neutral** (same or no advantage): **1.0x damage**
- **Disadvantage** (defender element strong vs attacker): **0.75x damage**

**Messages:**
- "Sangat efektif!" → 1.5x multiplier
- Normal message → 1.0x multiplier
- "Kurang efektif..." → 0.75x multiplier

---

## Battle Mechanics

### 1. **Damage Calculation**
```
finalDamage = rawDamage
if (critical hit) finalDamage *= 1.5
finalDamage *= elementalMultiplier
finalDamage -= defender.defense
```

### 2. **Dodge Chance**
```
dodgeChance = (defender.speed - attacker.speed) / 100 * 100
Max dodge: 30%
```

### 3. **Critical Hit Chance**
```
critChance = speed / 4.0
Max crit: 25%
```

### 4. **Focus Points Regeneration**
```
+10 FP per turn
Max FP = 50
```

---

## Turn Flow

1. Display status (HP, FP, active effects)
2. Display available skills
3. Get player input (1-4 untuk skill, 0 untuk surrender)
4. Validate skill (FP check)
5. Execute skill
6. Process status effects
7. Check victory condition
8. End turn (next player)
9. Regenerate FP
10. Kembali ke step 1

---

## Contoh Battle Output

```
╔════════════════════════════════════╗
║   PERTARUNGAN DIMULAI!             ║
╚════════════════════════════════════╝

⚔️  Pyro (HP: 100/100)
⚔️  vs
⚔️  Aqua (HP: 120/120)

🎲 Penentuan Giliran:
   Pyro memiliki speed lebih tinggi! Jalan duluan!

═══════════════════════════════════════════════════════
TURN 1 - Pyro GILIRAN
═══════════════════════════════════════════════════════
⚔️ PLAYER 1 Pyro │ [████████████████████] 100/100 HP
           │ FP: [⚡⚡⚡⚡⚡⚡⚡⚡⚡⚡] 50/50
           │ Effects: 

🛡️  PLAYER 2 Aqua │ [████████████████████] 120/120 HP
           │ FP: [⚡⚡⚡⚡⚡⚡⚡⚡⚡⚡] 50/50
═══════════════════════════════════════════════════════

📋 Skill tersedia untuk Pyro:
1. Basic Attack (FP Cost: 0)
2. Guard (FP Cost: 0)
3. Heal (FP Cost: 10)
4. Fireball (FP Cost: 25)

Pyro menembakkan bola api ke Aqua!
Kurang efektif...
Aqua menerima 14 kerusakan.
Aqua terbakar! (Burn)

⚡ Memproses Status Effects...
```

---

## Victory Screen

```
╔════════════════════════════════════╗
║       PERTARUNGAN SELESAI!         ║
╚════════════════════════════════════╝

🎉 PEMENANG: Aqua 🎉
   HP Tersisa: 30/120

Total Turn: 10
Total Aksi: 35
```

---

## Classes Integration

```
Battle
├── player1: BaseCharacter
│   └── skills: List<Skill>
│   └── activeEffects: List<StatusEffect>
├── player2: BaseCharacter
│   └── skills: List<Skill>
│   └── activeEffects: List<StatusEffect>
├── currentState: BattleState
└── battleLog: BattleLog
    └── actions: List<BattleAction>
```

---

## Tips Bermain

1. **Fire vs Water**: Water memiliki advantage → defensive play
2. **Wind vs Earth**: Wind memiliki advantage → aggressive early
3. **Manage FP**: 10 FP per turn, special skill butuh 20-30 FP
4. **Heal strategy**: Gunakan heal saat HP < 40%
5. **Freeze danger**: Perhatikan Freeze, akan skip turn
6. **Speed advantage**: Jalan duluan berarti bisa setup advantage

---

## File Struktur

```
src/main/java/com/game/battle/
├── Battle.java          → Main battle logic
├── BattleState.java     → Enum untuk state
├── BattleAction.java    → Record untuk action
├── BattleLog.java       → Log management
├── BattleSimulator.java → Interactive mode
└── BattleDemo.java      → Automated demo
```

---

## Cara Menambah Fitur

### Tambah Skill Baru
1. Buat class extends `Skill`
2. Implement `use()` method
3. Add ke karakter di `initializeSkills()`

### Tambah Status Effect Baru
1. Buat class extends `StatusEffect`
2. Implement `apply()`, `tick()`, `remove()`
3. Add ke skill yang sesuai

### Tambah Karakter Baru
1. Buat class extends `BaseCharacter`
2. Implement `initializeSkills()` dan `getElementType()`
3. Gunakan di BattleSimulator

---

## Performance

- Battle log maksimal 100 aksi
- No memory leaks dari long running battle
- Efficient status effect processing
- Optimal turn calculation

---

**Author**: Battle System Developer
**Version**: 1.0
**Status**: Production Ready ✅
