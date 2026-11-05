# Villager Cure Discounts

Make curing zombie villagers worth it again.

Since Java Edition 1.20.2 the discount for curing a zombie villager no longer grows: the first cure already gives the full discount, and only the player who cured the villager gets it. Villager Cure Discounts lets the discount grow again with every cure, up to a limit you choose, and lets a cured villager give its cure discount to every player who trades with it.

The mod runs on the server only. Players join with a plain vanilla client and see the discounted prices in the normal trading screen.

Available for NeoForge, Forge and Fabric.

## Features

- **Stacking cures.** Zombify and cure the same villager again and its prices drop again, up to `maxCures` cures (default 5, the five-cure limit Minecraft had before 1.20.2).
- **Shared discount.** A cured villager also gives its cure discount to players who did not cure it, including players who meet it later. Every cure by anyone counts.
- **No double discount.** Each player gets the larger of their own cure discount and the shared one, never both added together.
- **Vanilla everywhere else.** Prices, hitting villagers, trading, Hero of the Village and the way curing works are untouched.
- **Configurable.** Both the cure limit and the sharing can be changed in the server config. `maxCures = 1` with sharing turned off is plain vanilla.

## How it works

Villagers remember players through gossip. A cure gives the villager two kinds of positive gossip about the player who cured it: a strong one that never fades and a weaker one that fades by one point every in-game day. The more positive gossip, the lower the prices.

- **Stacking.** In vanilla, one cure already reaches the highest value of both gossip kinds. With this mod, each cure adds the same amount again until the villager has been cured `maxCures` times. Further cures keep the discount at that level.
- **Sharing.** With `shareWithAllPlayers = true`, every cure also adds to a shared cure discount stored on that villager, limited by the same `maxCures`. The shared discount stays with that villager: when villagers gossip with each other, it is never passed on.
- **The larger one wins.** When a player opens the trading screen, the villager compares that player's own cure discount with the shared one and uses the larger. All other gossip about the player still counts as in vanilla, so a player who hit the villager still pays more than one who did not.
- **Gossip between villagers.** As in vanilla, the weaker cure gossip about a player spreads to other villagers when they chat, getting smaller each time, and the strong cure gossip never spreads.
- **Fading.** The weaker part fades by one point per day as in vanilla; the strong part stays.
- **Zombification.** The stacked and shared discounts stay with the villager when it turns into a zombie villager and when it is cured again, so each new cure builds on the last.
- **What stays vanilla:** the price formula, the minimum price of 1, Hero of the Village, the price increase for hitting or killing villagers, the small discount from trading, how curing works, the chance that a killed villager turns into a zombie villager, and iron golems (they still judge each player by that player's own reputation).

## Requirements

| | NeoForge | Forge | Fabric |
|---|---|---|---|
| Minecraft | 26.2 | 26.2 | 26.2 |
| Loader | NeoForge 26.2.0.0-beta or newer | Forge 65.0.0 or newer | Fabric Loader 0.18.4 or newer |
| Other | | | Fabric API 0.152.2 or newer |
| Jar | `villagercurediscounts-neoforge-26.2-<version>.jar` | `villagercurediscounts-forge-26.2-<version>.jar` | `villagercurediscounts-fabric-26.2-<version>.jar` |

On Minecraft 26.2, NeoForge and Forge are separate loaders that do not run each other's mods, so each has its own jar. Pick the jar that matches your server's loader.

The Fabric jar bundles [NightConfig](https://github.com/TheElectronWill/night-config) (core and toml, LGPL-3.0) to read the TOML config. NeoForge and Forge already ship it.

### Client and server

Install the mod on the server only, on every loader. It adds no items, blocks, entities, commands, network messages or registries, so the server has nothing a client needs to know about:

- **NeoForge:** vanilla clients and NeoForge clients without the mod can join.
- **Forge:** vanilla clients and Forge clients without the mod can join. The mod is marked as server-only, so a Forge client without it still shows the server as compatible in the server list.
- **Fabric:** vanilla clients and Fabric clients without the mod can join. Fabric API is only needed on the server.

Installing it on a client as well does no harm. The prices are worked out on the server and sent with the trades.

In singleplayer, install it in your own game; the built-in server runs it.

## Configuration

### Server: `config/villagercurediscounts-server.toml`

Created with comments on the first server or world start:

```toml
# How many cures of the same villager add up to a bigger discount. Whole number from 1 to 20.
# 1 is vanilla: the first cure already gives the full discount.
# 5 matches the five-cure limit Minecraft had before 1.20.2.
maxCures = 5

# Give the cure discount of a cured villager to every player who trades with it,
# not only to the player who cured it. Every cure by anyone adds to this shared discount.
# Each player gets the larger of their own and the shared cure discount, never both.
shareWithAllPlayers = true
```

- Before 1.20.2 the strong cure gossip stopped growing after 5 cures and the weaker one after 8. With `maxCures = 5` both stop after 5 cures.
- Cures made while `shareWithAllPlayers = false` only count for the player who cured. Turning sharing off hides the shared discount without deleting it; turning it on again brings it back.
- Lowering `maxCures` does not lower a discount a villager already has. The next cure of that villager brings it down to the new limit.
- With `maxCures = 1`, gossip between villagers follows the vanilla rules exactly. Stacked strong cure gossip left over from a higher setting can then be passed on to other villagers, as vanilla does with any gossip above its limit.
- Changes take effect after a server restart, or after closing and reopening a singleplayer world.
- Mistakes do not crash the server. An invalid value uses the default for that setting and is logged as a warning. If the file cannot be read at all (for example a TOML syntax error), all defaults are used, the file is left untouched and the log says why.

## Removing the mod

The world opens normally. Villagers keep the cure gossip they have, so stacked discounts stay until the player who earned them cures that villager again; the next cure brings it back to the vanilla limit. The weaker part keeps fading day by day. Without the mod, vanilla gossip between villagers can also pass part of a stacked discount on to other villagers. Shared discounts stop working: they are stored under a fixed placeholder ID that belongs to no player, so vanilla ignores them. Vanilla gossip can still pass them on to other villagers while the mod is removed; if you install the mod again, those villagers give that shared discount too. The config file stays in `config/` until you delete it.

## Compatibility

- **Villager Trade Peek:** the prices of locked trades include the stacked and shared cure discount.
- **Villager Trade Swap:** rerolled trades get the stacked and shared cure discount.
- **Villager Trade Labels:** works together; discounted trades show as discounted.
- **Villager Trade Enchant:** works together. The Enchanter's prices come from its own config and never get cure or reputation discounts, with or without this mod.
- Mods that change villager gossip, reputation or trade prices may interfere. Tools that list a villager's gossip (such as `/data get entity`) show the shared discount as gossip about the placeholder ID `76636400-0000-0000-0000-000000000001`.

## Building from source

```
./gradlew build
```

The jars are written to `neoforge/build/libs/`, `forge/build/libs/` and `fabric/build/libs/`.

## License

MIT. The Fabric jar includes NightConfig, which is licensed under the LGPL-3.0.
