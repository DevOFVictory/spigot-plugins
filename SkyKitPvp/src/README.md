# **SkyKitPvP – Hilfe**

## **Permissions:**
```
skykitpvp.cmd.setup - /setup Befehl Benutzten
skykitpvp.eco.coins.own – Seine eigenen Coins sehen
skykitpvp.eco.coins.other – Coins von anderen Spielern sehen
skykitpvp.stats.own – Seine eigenen Statistiken sehen
skykitpvp.stats.other – Statistiken von anderen Spielern sehen
skykitpvp.cmd.build – Um den /build Befehl zu nutzen
skykitpvp.eco.admin – Um Coins zu setzten und zu verändern
```


## **Befehle:**
```
/stats <Spieler> - Statistiken sehen
/coins oder /money <Spieler> - Coins sehen
/eco <set/give/take> <Name> <Anzahl> - Coins verändern
/build – Baumodus aktivieren/deaktivieren
/setup – Weitere Infois jetzt:
```

**/setup Befehl:**
```
/setup setspawn – Setzt den Spawn auf die aktuelle Position
/setup registerframes – Erkennet und erneuert die Bilderahmen
/setup reloadConfig – Läd die Config Datei neu
/setup reloadKits – Läd die Kits neu
/setup help – Gibt Hilfe über das Plugin
/setup spawnNPC <Villager/Witch> - Spawnt den Kit-Villager und die Items Hexe
/setup removeNPC <Villager/Witch> - Entfernt NPCs im Umkreis von 5 Blöcken
```

### **SkyKitPvP Economy API**
```java
EcoManager.setCoins(Player player, int coins)       // [Void] Setzt Coins von Spieler
EcoManager.addCoins(Player player, int coins)       // [Void] Gibt dem Spieler Coins
EcoManager.takeCoins(Player player, int coins)      // [Void] Nimmt dem Spieler Coins weg
EcoManager.hasEnough(Player player, int coins)      // [Boolean] Gibt true zrücke, wenn der Spieler mind. die angegebenen Coins besitzt
EcoManager.getCoins(Player player)                  // [Integer] Gibt die Coins von dem Spieler zurück
EcoManager.saveToMySql(Player p, boolean asnyc)     // [Void] Sichert den Kontostand von dem Spieler in der Datenbank, async = Passiert dieser Vorgang asyncron zum Main Thread?
EcoManager.loadFromMySql(Player p, boolean async)   // [Void] Läd die Coins aus der Datenbank, async = Passiert dieser Vorgang ansyncron zum Main Thread?
```


### **Kits bearbeiten/hinzufügen:**
```yml
1:
   DisplayName: '&9&lFreezer' – Anzeige Name des Kits
   Price: 0 – Preis (Wenn 0 = Starterkit)
   MinKillsForUpgrade: 10 - Wie viele Kills braucht man um das Kit aufzuleveln?
   SuperPower: 'ENEMY_FREEZE' – Welche Superfähigkeit ist dem Kit zugewiesen
   DisplayType: 'ICE' – Welches Item Wird vom Villager angezeigt?
   LevelValue: - Werte für unterschiedliche Kit Fähigkeiten 
      1: 2
      2: 4
      3: 6
      4: 8
      5: 10
   Inventory:
      0: 'STONE_SWORD;&bSchwert;1' – Format: ‚Material;Name;Anzahl‘
      1: 'GOLDEN_APPLE;&6Goldapfel;3'
   Armor:
      Helmet: 'AIR' – Format: ‘Material’
      Chestplate: 'LEATHER_CHESTPLATE;&7Brustplatte;1'
      Leggings: 'AIR'
      Boots: 'AIR'
```

Itemliste: [https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html)

## **Config Erklärung:**

### MySQL Konfiguration

```yml
MySQL:
  host: localhost
  user: skykitpvp
  password: password
  database: skykitpvp
```

### Stats Wand Schilder Layout (%rank%, %name%, %kills%, %deaths%, %kd%)

```yml
SignLayout:
  0: &6&l%rank%
  1: &3%name%
  2: &2%name%
  3: &4%deaths%
```

### Die Namen und Guis der NPCs am Spawn

```yml
NpcNames:
  Villager: '&e&lKits' – Name von Villager
  Witch: '&d&lWitch' – Name von Hexe
  Zombie: '&2&lArena' – Name von Zombie (Coming soon…)
  
Prefix: '&4[&eSky&6Kit&cPvP&4] ' – Plugin Prefix
FallDamage: false – Soll Fallschaden aktiv sein?
JumpPadPower: 3.0 – Wie stark ist das Jumppad?
PvPHeight: 117 – Ab welcher Y-Koordinate ist PvP aktiviert 
DeathHight: 80 – Ab welcher Y-Koordianate stirbt man?
CoinsPerFrame: 10 – Wie viele Coins soll ein ItemFrame geben?
CoinsPerKill: 30 – Wie Viele Coins bekommt man für einen Kill?
ItemFrameAmount: 5 – Wie viele befüllte Itemframes sollen immer da sein?
```

## **Fähigkeiten hinzufügen:**
```
1. Erstelle im Enum de.devofvictory.skykitpvp.objects.Superpower einen neuen Eintrag, mit dem Namen der Fähigkeit.
2. Erstelle eine neue Klasse im Paket namens de.devofvictory.skykitpvp.superpowers mit dem gleichen Namen wie in Schritt 1
3. Implementiere das Interface „SuperPowerExecutor&quot; und füge alle abstracten Methoden hinzu.
4. An dieser Stelle schreibst du den Code, der ausgeführt wird, wenn ein Spieler mit dem Kit, was die Superfähigkeit hat, sneaked.
5. Jetzt erstellst du ein Kit, indem du den letzten Eintrag in SkyKitPvp/kits.yml kopierst und entsprechende Variablen ersetzt.
6. Die Variable „SuperPower&quot; setzt du auf den Namen im Enum.
7. Reloade den Server
```
