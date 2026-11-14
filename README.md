# Laxombat

*a community-realized continuation of a ninth combat snapshot*

## Special Thanks

* The [rose gold palette](https://lospec.com/palette-list/rose-gold) by MirruTatep on Lospec.
* [Dr. gigaherz](https://gigaherz.dev) for texture review & help.

## Changes

Changes not present in Combat Test Snapshot 8c are *italicized*.

### Attacking

#### Attack Cooldown

- [ ] Attacking is no longer possible until the attack cooldown has reached 100%.
    - [ ] In a case where a player attacks after 80% charge but before 100% charge, an attack will be buffered to come
      out at 100% charge.
- [ ] The attack cooldown can now further charge up to 200%.
    - [ ] Attacks at 195% or greater charge are given an additional block of reach.
        - The damage of attacks remain the same as 100% charge.
    - [ ] The cooldown requirement for sweep attacks has also been increased, and is dependent on the weapon.
        - [ ] Sword sweep attacks require 185% or greater charge,
        - [ ] while axe sweep attacks require 190% or greater charge.
- [ ] Holding down the attack key now attacks automatically 1 game tick (0.05 seconds) slower than optimal timing.
- [ ] The attack cooldown is now shorter when missing an attack, at 4 game ticks (0.2 seconds), regardless of weapon
  attack speed. When using hold-to-attack, the missing cooldown is 5 game ticks (0.25 seconds) instead.
- [ ] Weapons with attack cooldowns that are shorter than 10 game ticks (0.5 seconds) now give the target less attack
  invulnerability time (invincibility frames), to allow the weapon's faster attack speed to connect all of its attacks
  appropriately.
- [ ] The attack cooldown no longer resets when switching items, now only resetting when the player performs an attack.
- [ ] The first 100% charge of the attack cooldown no longer displays the attack indicator, and instead the attack
  indicator is used to indicate 130% to 200% charge.
- [ ] The swing animation that is displayed when attacking has been changed to emphasize the new rhythm of attacks.
    - [ ] The initial swing no longer moves the weapon downwards off the screen as much.
    - [ ] The swing now lingers out longer visually.
    - [ ] The slow rising-up animation of the weapon after swinging now begins at 120% charge, rather than immediately.
- [ ] The attack cooldown is now repeatedly reset when breaking blocks, rather than only resetting when successfully
  breaking a block.

#### Attack reach

- The distance that players can attack from is now dependent on the attack reach of their weapon.
    - [x] Swords have 3 attack reach,
    - [x] tridents and hoes have 3.5,
    - [x] and axes, pickaxes, and shovels have 2.5.
- [x] The default unarmed attack reach of players is now 2.5, down from 3.
- [ ] Attack reach is increased by 1 when the attack cooldown percentage is at 195% or greater.
- [ ] Weapons now display their attack reach stat. *Half-done; weapons show reach relative to 2.5m.*
    - [x] Weapons with an attack reach of 2.5 do not display their attack reach stat, such as axes.
- [ ] The increased attack reach that was present in Creative has been removed.

#### Attack knockback

- [ ] Attacks dealt against entities that are already in the air now deal additional vertical knockback, rather than no
  vertical knockback.
    - This is similar to how knockback is handled in Bedrock Edition, and how it was handled before the Combat Update.

#### Critical hits and sprint-knockback attacks

- [ ] Can now be performed simultaneously, as it was in older versions and how it is in Bedrock Edition.

#### Sweep attacks

- [ ] Are now activated regardless of whether or not an entity is hit directly.
- [ ] Now require the Sweeping Edge enchantment to activate.
    - [ ] This also means sweep attacks can now be performed by any item with the Sweeping Edge enchantment, rather than
      only swords.
- [ ] Now require an attack cooldown charge of 185% or greater for swords ~~and 190% or greater for axes~~, up from
  84.8%.
- [ ] *Enable sweeping edge for hoes at a lower mark than swords, and consider not enabling them at all for axes.*
    - [ ] *If enabled for any non-sword item, an exclusive sword enchantment must be added instead.*

### Healing

#### Hunger

- [x] Saturation is no longer used to regenerate quickly, and is instead now only relevant for its purpose of pausing
  hunger loss.
- [x] Saturation is no longer accumulated by eating multiple instances of food. Instead, saturation is set to the eaten
  food's saturation value, as long as said saturation value exceeds the player's current saturation.
- [ ] Hunger no longer decreases along with saturation. Instead saturation is used first and after being completely used
  up, the hunger bar begins depleting as normal. *Possibly how this works in vanilla now?*
- [x] Starvation damage now damages the player faster, at every 40 game ticks (2 seconds) rather than every 80 game
  ticks (4 seconds).

#### Natural regeneration

- [x] Natural regeneration now uses hunger to heal every 40 game ticks (2 seconds) rather than every 80 game ticks (4
  seconds).
- [x] Natural regeneration no longer uses saturation before using hunger, and instead ignores saturation.
- [x] The consumption of hunger by natural regeneration is now 1:1, taking 1 foodpoint to heal 1 HP (half a heart). This
  ignores the player's current saturation value, making it so hunger bars can be lost even if food was just consumed.
    - [ ] ~~The chance of 1 foodpoint being consumed when healing 1 HP (half a heart) is random, at a 50% chance. This
      means that the amount of healing players can do during a fight is heavily influenced by the RNG of hunger loss
      when healing.~~
- [x] Natural regeneration now continues until a hunger level of 7 foodpoints rather than the previous 18 foodpoints.

#### Eating

- [ ] Eating and drinking consumables is now interrupted and reset when getting attacked.
    - Note that this does not take effect when being damaged by all damage types, only when taking damage from mobs and
      other players.
- [x] Liquid consumables (such as stews, honey bottles, milk buckets, and potions) are now consumed faster than other
  consumable types (20 ticks rather than the usual 32 ticks).

### Melee weapons

| Weapon                     | Damage | Speed[^1] | Reach[^2] | Vanilla Damage | Vanilla Speed[^1] |
|:---------------------------|:------:|:---------:|:---------:|:--------------:|:-----------------:|
| Empty hand                 |  2 HP  |  2.50a/s  |   2.5m    |      1 HP      |      4.0a/s       |
| Wooden and Golden Swords   |  4 HP  |  3.00a/s  |   3.0m    |      4 HP      |      1.6a/s       |
| Stone and Copper Swords    |  4 HP  |  3.00a/s  |   3.0m    |      5 HP      |      1.6a/s       |
| Iron Sword                 |  5 HP  |  3.00a/s  |   3.0m    |      6 HP      |      1.6a/s       |
| _Rose-Golden Sword_        | 5.5 HP |  3.00a/s  |   3.0m    |       —        |         —         |
| Diamond Sword              |  6 HP  |  3.00a/s  |   3.0m    |      7 HP      |      1.6a/s       |
| Netherite Sword            |  7 HP  |  3.00a/s  |   3.0m    |      8 HP      |      1.6a/s       |
| Wooden and Golden Axes[^3] |  5 HP  |  2.00a/s  |   2.5m    |      7 HP      |      0.8a/s       |
| Stone Axe[^3]              |  5 HP  |  2.00a/s  |   2.5m    |      9 HP      |      0.8a/s       |
| Iron Axe[^3]               |  6 HP  |  2.00a/s  |   2.5m    |      9 HP      |      0.8a/s       |
| _Rose-Golden Axe_          | 6.5 HP |  2.00a/s  |   2.5m    |       —        |         —         |
| Diamond Axe[^3]            |  7 HP  |  2.00a/s  |   2.5m    |      9 HP      |      0.8a/s       |
| Netherite Axe[^3]          |  8 HP  |  2.00a/s  |   2.5m    |     10 HP      |      0.8a/s       |
| Trident[^4]                |  7 HP  |  2.00a/s  |   3.5m    |     11 HP      |      1.1a/s       |
| Wooden Hoe                 |  2 HP  |  2.00a/s  |   3.5m    |      1 HP      |      1.0a/s       |
| Stone and Copper Hoes      |  2 HP  |  2.50a/s  |   3.5m    |      1 HP      |      2.0a/s       |
| Golden Hoe                 |  2 HP  |  3.50a/s  |   3.5m    |      1 HP      |      1.0a/s       | 
| Iron Hoe                   |  3 HP  |  3.00a/s  |   3.5m    |      1 HP      |      3.0a/s       |
| _Rose-Golden Hoe_          |  3 HP  |  3.25a/s  |   3.5m    |       —        |         —         |
| Diamond Hoe                |  3 HP  |  3.50a/s  |   3.5m    |      1 HP      |      4.0a/s       |
| Netherite Hoe              |  4 HP  |  3.50a/s  |   3.5m    |      1 HP      |      4.0a/s       |
| Wooden and Golden Pickaxes |  3 HP  |  2.50a/s  |   2.5m    |      2 HP      |      1.2a/s       |
| Stone and Copper Pickaxes  |  3 HP  |  2.50a/s  |   2.5m    |      3 HP      |      1.2a/s       |
| Iron Pickaxe               |  4 HP  |  2.50a/s  |   2.5m    |      4 HP      |      1.2a/s       |
| _Rose-Golden Pickaxe_      | 4.5 HP |  2.50a/s  |   2.5m    |       —        |         —         |
| Diamond Pickaxe            |  5 HP  |  2.50a/s  |   2.5m    |      5 HP      |      1.2a/s       |
| Netherite Pickaxe          |  6 HP  |  2.50a/s  |   2.5m    |      6 HP      |      1.2a/s       |
| Wooden and Golden Shovels  |  2 HP  |  2.00a/s  |   2.5m    |     2.5 HP     |      1.0a/s       |
| Stone and Copper Shovels   |  2 HP  |  2.00a/s  |   2.5m    |     3.5 HP     |      1.0a/s       |
| Iron Shovel                |  3 HP  |  2.00a/s  |   2.5m    |     4.5 HP     |      1.0a/s       |
| _Rose-Golden Shovel_       | 3.5 HP |  2.00a/s  |   2.5m    |       —        |         —         |
| Diamond Shovel             |  4 HP  |  2.00a/s  |   2.5m    |     5.5 HP     |      1.0a/s       |
| Netherite Shovel           |  5 HP  |  2.00a/s  |   2.5m    |     6.5 HP     |      1.0a/s       |

Currently, spears and maces remain unchanged.

[^1]: Measured in attacks per second.

[^2]: Vanilla reach for all non-spear weapons was 3.0m.

[^3]: Axes no longer take double durability damage from usage in combat.

[^4]: - [ ] Can now be shot from dispensers.

#### Empty hand

- [x] Decreased attack speed to 2.5 (0.4 seconds), was 4 (0.25 seconds).
- [x] Increased attack damage to 2 HP, was 1 HP.
- [x] Decreased attack reach to 2.5, was 3.
  ...etc...
